package de.adventofcode.day6;

import de.adventofcode.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6 extends Day
{
	@Override
	public Long solvePart1()
	{
		final List<List<String>> groups = getGroups();

		long sum = 0L;
		for (final List<String> group : groups)
		{
			final Map<Character, Boolean> answers = new HashMap<>();
			for (final String line : group)
			{
				for (char c : line.toCharArray())
				{
					answers.putIfAbsent(c, Boolean.TRUE);
				}
			}
			sum += answers.size();
		}


		return sum;
	}

	@Override
	public Long solvePart2()
	{
		final List<List<String>> groups = getGroups();

		long sum = 0L;
		for (final List<String> group : groups)
		{
			List<Character> answers = null;
			for (final String line : group)
			{
				if (!line.isBlank())
				{
					final List<Character> chars = new ArrayList<>();
					for (char c : line.toCharArray())
					{
						chars.add(c);
					}

					if (answers == null)
					{
						answers = chars;
					}
					else
					{
						answers.retainAll(chars);
					}
				}

			}
			sum += answers.size();
		}

		return sum;
	}

	private List<List<String>> getGroups()
	{
		final List<String> input = getInput();
		input.add(" ");

		final List<List<String>> groups = new ArrayList<>();

		int lastBlank = 0;
		for (int i = 0; i < input.size(); i++)
		{
			final String line = input.get(i);
			if (line.isBlank())
			{
				List<String> group = new ArrayList<>();
				for (int j = lastBlank; j < i; j++)
				{
					group.add(input.get(j));
				}
				groups.add(group);
				lastBlank = i;
			}
		}
		return groups;
	}
}
