package de.adventofcode.day10;

import de.adventofcode.Day;

import java.util.List;
import java.util.stream.Collectors;

public class Day10 extends Day
{
	@Override
	public Long solvePart1()
	{
		final List<Integer> sorted = this.input.stream().map(Integer::parseInt).sorted().collect(Collectors.toList());

		int currentVolt = 0;
		int countOne = 0;
		int countTwo = 0;
		int countThree = 1;

		for (Integer next : sorted)
		{
			switch (next - currentVolt)
			{
				case 1 -> countOne++;
				case 2 -> countTwo++;
				case 3 -> countThree++;
				default -> throw new IllegalArgumentException(String.valueOf(next - currentVolt));
			}
			currentVolt = next;
		}


		return (long) (countOne * countThree);
	}

	@Override
	public Long solvePart2()
	{
		final List<Long> sorted = this.input.stream().map(Long::parseLong).sorted().collect(Collectors.toList());

		final long[] count = new long[sorted.size()];

		count[0] = 1;
		if (sorted.size() >= 2 && sorted.get(1) <= 3)
		{
			count[1] = 1;
		}
		if (sorted.size() >= 3 && sorted.get(2) <= 3)
		{
			count[2] = 1;
		}

		for (int i = 0; i < sorted.size() - 1; i++)
		{
			count[i + 1] += count[i];
			if (i + 2 < sorted.size() && sorted.get(i + 2) <= sorted.get(i) + 3)
			{
				count[i + 2] += count[i];
			}
			if (i + 3 < sorted.size() && sorted.get(i + 3) <= sorted.get(i) + 3)
			{
				count[i + 3] += count[i];
			}
		}

		return count[count.length - 1];
	}
}
