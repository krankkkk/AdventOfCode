package de.adventofcode.day2;

import de.adventofcode.Day;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Day2 extends Day
{
	private static boolean isValidPart1(final String line)
	{
		final String[] s = line.split(" ");
		final int[] count = Arrays.stream(s[0].split("-")).mapToInt(Integer::parseInt).sorted().toArray();
		final char letter = s[1].charAt(0);
		final char[] password = s[2].toCharArray();
		final int counter = (int) IntStream.range(0, password.length).filter(i -> password[i] == letter).count();

		return count[0] <= counter && count[1] >= counter;
	}

	private static boolean isValidPart2(final String line)
	{
		final String[] s = line.split(" ");
		final Integer[] place = Arrays.stream(s[0].split("-")).map(Integer::parseInt).toArray(Integer[]::new);
		final char letter = s[1].charAt(0);
		final char[] password = s[2].toCharArray();

		final int firstCharacter = place[0] - 1;
		final int secondCharacter = place[1] - 1;
		return password[firstCharacter] == letter ^ password[secondCharacter] == letter;
	}

	@Override
	public long solvePart1()
	{
		return getInput().stream().filter(Day2::isValidPart1).count();
	}

	@Override
	public long solvePart2()
	{
		return getInput().stream().filter(Day2::isValidPart2).count();
	}
}
