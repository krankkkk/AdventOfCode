package de.adventofcode.day7;

import de.adventofcode.Day;

import java.util.*;

public class Day7 extends Day
{

	@Override
	public Long solvePart1()
	{
		final Map<String, String[]> bags = new HashMap<>(input.size());//All Bags with all direct Sub-Bags and Count

		for (final String line : input)
		{
			final String[] split = line.split(" contain ");
			final String[] split1 = split[1].split(", ");
			if (!split1[0].equals("no other bags."))
			{
				String[] config = new String[split1.length];
				for (int i = 0; i < split1.length; i++)
				{
					config[i]=split1[i].substring(2).split(" bag")[0];
				}
				bags.put(split[0].split(" bag")[0], config);
			}
		}

		long count = 0L;
		for (String key : bags.keySet().toArray(String[]::new))
		{
			if (leadsToGoldBag(key, bags))
			{
				count++;
			}
		}
		return count;
	}

	private static boolean leadsToGoldBag(final String key, final Map<String, String[]> bags)
	{
		final String[] children = bags.get(key);

		if (children == null)
		{
			return false;
		}

		for (final String subKey : children)
		{
			if (leadsToGoldBag(subKey, bags))
			{
				return true;
			}
		}

		for (String str : children)
		{
			if (str.equals("shiny gold"))
			{
				return true;
			}
		}
		bags.remove(key);//Dont check again
		return false;
	}

	@Override
	public Long solvePart2()
	{
		final Map<String, Bag> bags = new HashMap<>(input.size());

		for (final String line : this.input)
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
			bags.put(currentColor, new Bag(currentColor, config));
		}

		return bags.get("shiny gold").subBags.entrySet().stream().mapToLong(entry -> getCount(entry, bags) * entry.getValue()).sum();
	}

	private static long getCount(Map.Entry<String, Integer> entry, final Map<String, Bag> bags)
	{
		return bags.get(entry.getKey()).subBags.entrySet().stream().mapToLong(entry2 -> getCount(entry2, bags) * entry2.getValue()).sum() + 1L;
	}

	private static class Bag
	{
		final String name;
		final Map<String, Integer> subBags;

		public Bag(String name, Map<String, Integer> subBags)
		{
			this.name = name;
			this.subBags = subBags;
		}
	}
}
