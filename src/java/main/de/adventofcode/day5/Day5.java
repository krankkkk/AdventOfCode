package de.adventofcode.day5;

import de.adventofcode.Day;

import java.util.*;

public class Day5 extends Day
{

	@Override
	public Long solvePart1()
	{
		final List<String> input = getInput();

		long max = 0;

		for (final String line : input)
		{
			final char[] chars = line.toCharArray();
			int rowHighest = getRow(chars);
			int column = getColumn(chars);
			final int id = rowHighest * 8 + column;

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

		final Map<Integer, Boolean> seats = new HashMap<>(input.size());

		for (final String line : input)
		{
			final char[] chars = line.toCharArray();
			int rowHighest = getRow(chars);
			int column = getColumn(chars);
			final int id = rowHighest * 8 + column;

			seats.put(id, Boolean.TRUE);
		}

		for (final Integer lower : seats.keySet())
		{
			if (seats.get(lower + 1) == null && seats.get(lower + 2))
			{
				return Long.valueOf(lower + 1);
			}
		}

		return -1L;
	}

	private int getRow(char[] chars)
	{
		int rowLowest = 0;
		int rowHighest = 127;
		for (int i = 0; i < chars.length - 3; i++)
		{
			if (chars[i] == 'F')
			{
				rowHighest = Math.floorDiv(rowHighest + rowLowest, 2);
			}
			else
			{
				rowLowest = Math.floorDiv(rowHighest + rowLowest, 2) + 1;
			}
		}
		return rowHighest;
	}

	private int getColumn(char[] chars)
	{
		int columnLowest = 0;
		int columnHighest = 7;
		for (int i = chars.length - 3; i < chars.length; i++)
		{
			final int diff = Math.floorDiv(columnLowest + columnHighest, 2);
			if (chars[i] == 'L')
			{
				columnHighest = diff;
			}
			else
			{
				columnLowest = diff + 1;
			}
		}
		return columnHighest;
	}
}
