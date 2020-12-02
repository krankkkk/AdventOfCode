package de.adventofcode.day2;

import de.adventofcode.Day;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day2 extends Day
{
	@Override
	public int solvePart1()
	{
		return (int) getInput().stream().filter(Day2::isValidPart1).count();
	}

	private static boolean isValidPart1(final String line)
	{
		final String[] s = line.split(" ");
		final int[] count = Arrays.stream(s[0].split("-")).mapToInt(Integer::parseInt).sorted().toArray();
		final char letter = s[1].charAt(0);
		final char[] password = s[2].toCharArray();
		final int counter = (int) IntStream.range(0, password.length).filter(i -> password[i] == letter).count();

		return count[0] <= counter && count[1] >= counter;
	}

	@Override
	public int solvePart2()
	{
		return (int) getInput().stream().filter(Day2::isValidPart2).count();
	}

	static boolean isValidPart2(final String line)
	{
		final String[] s = line.split(" ");
		final Integer[] place = Arrays.stream(s[0].split("-")).map(Integer::parseInt).toArray(Integer[]::new);
		final char letter = s[1].charAt(0);
		final char[] password = s[2].toCharArray();

		final int firstCharacter = place[0] - 1;
		final int secondCharacter = place[1] - 1;
		return password[firstCharacter] == letter ^ password[secondCharacter] == letter;
	}


	public static List<String> getInput()
	{
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(Day2.class.getResource("input.txt").toURI()))))
		{
			return reader.lines().collect(Collectors.toList());
		}
		catch (IOException | URISyntaxException e)
		{
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
}
