package de.adventofcode.day5;

import de.adventofcode.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day5 extends Day
{

	@Override
	public Long solvePart1()
	{
		final List<String> input = getInput();

		long max = 0;

		for (final String line : input)
		{
			final long id = getId(line);

			if (id > max)
			{
				max = id;
			}
		}

		return max;
	}

	@Override
	public Long solvePart2()
	{
		final List<String> input = getInput();

		final Map<Long, Boolean> seats = new HashMap<>(input.size());

		for (final String line : input)
		{
			seats.put(getId(line), Boolean.TRUE);
		}

		for (final Long lower : seats.keySet())
		{
			if (seats.get(lower + 1) == null && seats.get(lower + 2) != null)
			{
				return lower + 1;
			}
		}

		return -1L;
	}

	private long getId(String line)
	{
		final char[] chars = line.toCharArray();
		int rowHighest = getRow(chars, 'F', 0, 127, 0, chars.length - 3);
		int column = getRow(chars, 'L', 0, 7, chars.length - 3, chars.length);
		return rowHighest * 8 + column;
	}

	private int getRow(final char[] chars, final char toFilter, int lowest, int highest, final int start, final int stop)
	{
		for (int i = start; i < stop; i++)
		{
			if (chars[i] == toFilter)
			{
				highest = Math.floorDiv(highest + lowest, 2);
			}
			else
			{
				lowest = Math.floorDiv(highest + lowest, 2) + 1;
			}
		}
		return highest;
	}
}
