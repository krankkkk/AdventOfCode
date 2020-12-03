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
		challenges.forEach(System.out::println);
	}
}
