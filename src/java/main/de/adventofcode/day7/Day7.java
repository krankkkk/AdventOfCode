package de.adventofcode.day7;

import de.adventofcode.Day;

import java.util.*;

public class Day7 extends Day
{
	@Override
	public Long solvePart1()
	{
		final Map<String, Map<String, Integer>> bags = new HashMap<>(input.size());//All Bags with all direct Sub-Bags and Count
		final List<String> bagsToCheck = new ArrayList<>();//Where the shiny Gold Bags are

		for (final String line : input)
		{
			final String[] split = line.split(" contain ");
			final String currentColor = split[0].split(" bag")[0];

			final String[] split1 = split[1].split(", ");
			final Map<String, Integer> config;
			if (!split1[0].equals("no other bags."))
			{
				config = new HashMap<>(split1.length);
				for (String s : split1)
				{
					String substring = s.substring(2).split(" bag")[0];

					if (substring.equals("shiny gold"))
					{
						bagsToCheck.add(currentColor);
					}
					config.put(substring, Integer.valueOf(Character.toString(s.charAt(0))));
				}
			}
			else
			{
				config = Collections.emptyMap();
			}
			bags.put(currentColor, config);
		}


		while (fillUp(bags, bagsToCheck) != 0)
		{
		}

		return (long) bagsToCheck.size();
	}

	private int fillUp(Map<String, Map<String, Integer>> bags, List<String> bagsToCheck)
	{
		final List<String> add = new ArrayList<>();
		for (final String container : bagsToCheck)
		{
			bags.entrySet().stream().filter(e -> e.getValue().containsKey(container))
					.filter(e -> !bagsToCheck.contains(e.getKey()) && !add.contains(e.getKey()))
					.forEach(e -> add.add(e.getKey()));
		}
		bagsToCheck.addAll(add);
		return add.size();
	}

	@Override
	public Long solvePart2()
	{
		final Map<String, Map<String, Integer>> bags = new HashMap<>(input.size());

		for (final String line : input)
		{
			final String[] split = line.split(" contain ");
			final String currentColor = split[0].split(" bag")[0];

			final String[] split1 = split[1].split(", ");
			final Map<String, Integer> config;
			if (!split1[0].equals("no other bags."))
			{
				config = new HashMap<>(split1.length);
				for (String s : split1)
				{
					String substring = s.substring(2).split(" bag")[0];
					config.put(substring, Integer.valueOf(Character.toString(s.charAt(0))));
				}
			}
			else
			{
				config = Collections.emptyMap();
			}
			bags.put(currentColor, config);
		}

		long sum = 0L;
		for (Map.Entry<String, Integer> entry : bags.get("shiny gold").entrySet())
		{
			sum += getCount(entry, bags) * entry.getValue();
		}

		return sum;
	}

	private long getCount(Map.Entry<String, Integer> entry, Map<String, Map<String, Integer>> bags)
	{
		long sum = 1L;
		for (Map.Entry<String, Integer> entry2 : bags.get(entry.getKey()).entrySet())
		{
			sum += getCount(entry2, bags) * entry2.getValue();
		}
		return sum;
	}
}
