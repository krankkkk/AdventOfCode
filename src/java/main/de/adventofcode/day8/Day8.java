package de.adventofcode.day8;

import de.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day8 extends Day
{
	@Override
	public Long solvePart1()
	{

		AtomicLong accumulator = new AtomicLong(0L);

		doOperations(new ArrayList<>(), accumulator, input);

		return accumulator.get();
	}

	private static boolean doOperations(List<Integer> visited, AtomicLong accumulator, final List<String> operations)
	{
		int currentIndex = 0;
		String line = operations.get(currentIndex);
		try
		{
			while (true)
			{
				if (visited.contains(currentIndex))
				{
					return false;
				}
				visited.add(currentIndex);

				final String instruction = line.substring(0, 3);
				final int number = Integer.parseInt(line.substring(4));

				switch (instruction)
				{
					case "nop":
						currentIndex++;
						break;
					case "acc":
						accumulator.addAndGet(number);
						currentIndex++;
						break;
					case "jmp":
						currentIndex += number;
						break;
					default:
						throw new IllegalStateException("Unexpected instruction: " + instruction + " at Index: " + currentIndex);
				}

				line = operations.get(currentIndex);
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			return true;
		}
		catch (NumberFormatException | IllegalStateException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Long solvePart2()
	{
		final List<Integer> visited = new ArrayList<>(input.size());

		AtomicLong accumulator = new AtomicLong(0L);

		doOperations(visited, accumulator, input);

		for (final int i : visited)
		{
			final String line = input.get(i);
			final String instruction = line.substring(0, 3);
			final int number = Integer.parseInt(line.substring(4));
			final String newLine;

			if ("jmp".equals(instruction))
			{
				newLine = "nop " + number;
			}
			else if ("nop".equals(instruction))
			{
				newLine = "jmp " + number;
			}
			else
			{
				continue;
			}

			final List<String> newList = new ArrayList<>(input);
			newList.set(i, newLine);
			accumulator.set(0L);
			if (doOperations(new ArrayList<>(), accumulator, newList))
			{
				break;
			}
		}


		return accumulator.get();
	}
}
