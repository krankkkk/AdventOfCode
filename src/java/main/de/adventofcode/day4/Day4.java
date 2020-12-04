package de.adventofcode.day4;

import de.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4 extends Day
{
	@Override
	public long solvePart1()
	{
		long count = 0L;
		for (PassPort passPort : getPassPorts())
		{
			if (passPort.isValidPart1())
			{
				count++;
			}
		}
		return count;
	}

	@Override
	public long solvePart2()
	{
		long count = 0L;
		for (PassPort passPort : getPassPorts())
		{
			if (passPort.isValidPart2())
			{
				count++;
			}
		}
		return count;
	}

	private List<PassPort> getPassPorts()
	{
		final List<String> input = getInput();
		input.add("");//So last line gets added

		final List<List<String>> collectRaw = new ArrayList<>();
		int lastBlank = 0;
		for (int i = 0; i < input.size(); i++)
		{
			final String line = input.get(i);
			if (line.isBlank())
			{
				final List<String> l = new ArrayList<>();
				for (int j = lastBlank; j < i; j++)
				{
					l.add(input.get(j));
				}
				collectRaw.add(l);
				lastBlank = i;
			}
		}

		final List<PassPort> results = new ArrayList<>();

		for (List<String> list : collectRaw)
		{
			String byr = null;
			String iyr = null;
			String eyr = null;
			String hgt = null;
			String hcl = null;
			String ecl = null;
			String pid = null;
			String cid = null;

			for (String str : list)
			{
				if (str.isBlank())
				{
					continue;
				}

				final String[] split = str.split(" ");
				for (String mini : split)
				{
					final String[] split1 = mini.split(":");

					switch (split1[0])
					{
						case "byr" -> byr = split1[1];
						case "iyr" -> iyr = split1[1];
						case "eyr" -> eyr = split1[1];
						case "hgt" -> hgt = split1[1];
						case "hcl" -> hcl = split1[1];
						case "ecl" -> ecl = split1[1];
						case "pid" -> pid = split1[1];
						case "cid" -> cid = split1[1];
						default -> System.err.println("Unknown key: " + Arrays.toString(split1));
					}

				}
			}
			results.add(new PassPort(byr, iyr, eyr, hgt, hcl, ecl, pid, cid));
		}
		return results;
	}

	private static class PassPort
	{
		String byr;
		String iyr;
		String eyr;
		String hgt;
		String hcl;
		String ecl;
		String pid;
		String cid;

		private PassPort(String byr, String iyr, String eyr, String hgt, String hcl, String ecl, String pid, String cid)
		{
			this.byr = byr;
			this.iyr = iyr;
			this.eyr = eyr;
			this.hgt = hgt;
			this.hcl = hcl;
			this.ecl = ecl;
			this.pid = pid;
			this.cid = cid;
		}

		public boolean isValidPart1()
		{
			for (String s : new String[]{byr, iyr, eyr, hgt, hcl, ecl, pid})
			{
				if (s == null)
				{
					return false;
				}
			}
			return true;
		}

		public boolean isValidPart2()
		{
			if (!isValidPart1())
			{
				return false;
			}

			return isBirthYearValid() && isExpYearValid() && isEyeValid() && isHairValid() && isIssueYearValid() && isPassIDValid() && isHeightValid();
		}

		public boolean isBirthYearValid()
		{
			final int i = Integer.parseInt(this.byr);
			return 1920 <= i && i <= 2002;
		}

		public boolean isIssueYearValid()
		{
			final int i = Integer.parseInt(this.iyr);
			return 2010 <= i && i <= 2020;
		}

		public boolean isExpYearValid()
		{
			final int i = Integer.parseInt(this.eyr);
			return 2020 <= i && i <= 2030;
		}

		public boolean isHeightValid()
		{
			final int i = Integer.parseInt(this.hgt.substring(0, this.hgt.length() - 2));
			final String substring = this.hgt.substring(this.hgt.length() - 2);

			if (substring.equals("in"))
			{
				return 59 <= i && i <= 76;
			}
			else if (substring.equals("cm"))
			{
				return 150 <= i && i <= 193;
			}
			else
			{
				return false;
			}
		}

		public boolean isHairValid()
		{
			if ('#' != this.hcl.charAt(0))
			{
				return false;
			}

			if (this.hcl.length() != 7)
			{
				return false;
			}

			for (int i = 1; i < this.hcl.length() - 1; i++)
			{
				final char c = this.hcl.charAt(i);
				if (!(('0' <= c && c <= '9') || ('a' <= c && c <= 'f')))
				{
					return false;
				}
			}

			return true;
		}

		public boolean isEyeValid()
		{
			return switch (this.ecl)
					{
						case "amb", "blu", "brn", "gry", "grn", "hzl", "oth" -> true;
						default -> false;
					};
		}

		public boolean isPassIDValid()
		{
			if (this.pid.length() != 9)
			{
				return false;
			}

			for (int i = 0; i < 9; i++)
			{
				final char c = this.pid.charAt(i);
				if (!(48 <= c && c <= 57))
				{
					return false;
				}
			}
			return true;
		}
	}
}
