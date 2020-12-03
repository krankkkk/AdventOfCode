package de.adventofcode;

import de.adventofcode.day1.Day1;
import de.adventofcode.day2.Day2;
import de.adventofcode.day3.Day3;

import java.util.List;

public class Launcher
{

	private static final List<Challenge> challenges = List.of(new Day1(), new Day2(), new Day3());

	public static void main(String[] args)
	{
		challenges.forEach(Launcher::printDay);
	}

	private static void printDay(final Challenge day)
	{
		System.out.println(String.format("%15s%s%15s", "", day.getClass().getSimpleName(), "").replace(' ', '-'));
		System.out.println("Part 1: " + day.solvePart1());
		System.out.println("Part 2: " + day.solvePart2());
	}
}
