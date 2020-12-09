package de.adventofcode.day9;

import de.adventofcode.Day;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalLong;


public class Day9 extends Day
{

	private Long resultP1 = null;

	@Override
	public Long solvePart1()
	{
		final int PREAMBLE_SIZE = 25;

		for (int i = PREAMBLE_SIZE; i < this.input.size(); i++)
		{
			final Long current = Long.parseLong(this.input.get(i));
			boolean isValid = false;

			for (int j = i - PREAMBLE_SIZE; j < i; j++)
			{
				final Long middle = Long.parseLong(this.input.get(j));
				final long toSearch = current - middle;

				Long result = null;


				for (int k = i - PREAMBLE_SIZE + 1; k < i; k++)
				{
					final String inner = this.input.get(k);
					if (inner.equals(Long.toString(toSearch)))
					{
						result = Long.parseLong(inner);
						break;
					}
				}

				if (result != null)
				{
					isValid = true;
					break;
				}
			}

			if (!isValid)
			{
				this.resultP1 = current;
				return current;
			}
		}

		return -1L;
	}

	@Override
	public Long solvePart2()
	{
		final List<Long> candidates = new ArrayList<>();

		final Iterator<String> iterator = this.input.iterator();

		while (sum(candidates) < this.resultP1)
		{
			candidates.add(Long.parseLong(iterator.next()));
		}

		while (true)
		{
			final Long sum = sum(candidates);

			if (this.resultP1.equals(sum))
			{
				final OptionalLong min = candidates.stream().mapToLong(Long::longValue).min();
				final OptionalLong max = candidates.stream().mapToLong(Long::longValue).max();
				return min.getAsLong() + max.getAsLong();
			}
			else if (sum > this.resultP1)
			{
				candidates.remove(0);
			}
			else
			{
				candidates.add(Long.parseLong(iterator.next()));
			}
		}
	}

	private static Long sum(final List<Long> toSum)
	{
		long sum = 0L;
		for (Long aLong : toSum)
		{
			sum += aLong;
		}
		return sum;
	}
}
