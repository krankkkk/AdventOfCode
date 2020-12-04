package de.adventofcode.day3;

import de.adventofcode.Day;

import java.util.List;

public class Day3 extends Day
{

	private static final char TREE = '#';

	@Override
	public Long solvePart1()
	{
		return getCounter(getInput(), 3, 1);
	}

	private long getCounter(final List<String> input,
	                        final int STEP_RIGHT,
	                        final int STEP_DOWN)
	{
		long counter = 0;
		int place = 0;
		for (int i = 0; i < input.size(); i = i + STEP_DOWN)
		{
			final String line = input.get(i);
			final int length = line.length();
			if (place >= length)
			{
				place -= length;
			}

			final char toCheck = line.charAt(place);
			if (toCheck == TREE)
			{
				counter++;
			}

			place = place + STEP_RIGHT;
		}
		return counter;
	}

	@Override
	public Long solvePart2()
	{
		final List<String> input = getInput();

		final long r1d1 = getCounter(input, 1, 1);
		final long r3d1 = getCounter(input, 3, 1);
		final long r5d1 = getCounter(input, 5, 1);
		final long r7d1 = getCounter(input, 7, 1);
		final long r1d2 = getCounter(input, 1, 2);


		return r1d1 * r3d1 * r5d1 * r7d1 * r1d2;
	}
}
