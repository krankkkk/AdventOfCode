package de.adventofcode.day1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day1
{
	public static void main(String[] args)
	{
		final List<Integer> input = getInput();

		final long start = System.currentTimeMillis();
		for (final Integer outer : input)
		{
			for (final Integer middle : input)
			{
				for (final Integer inner : input)
				{
					if (outer + middle + inner == 2020)
					{
						System.out.println("Outer: " + outer);
						System.out.println("Inner: " + middle);
						System.out.println("Innermost: " + inner);
						System.out.println("Multiplicative: " + outer * middle * inner);
						System.out.println("Took: " + (System.currentTimeMillis() - start) + " ms");
						return;
					}
				}
			}
		}
	}

	private static List<Integer> getInput()
	{
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(Day1.class.getResource("input.txt").toURI()))))
		{
			return reader.lines().map(Integer::parseInt).collect(Collectors.toList());
		}
		catch (IOException | URISyntaxException e)
		{
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
}
