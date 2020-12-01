package de.adventofcode;

import de.adventofcode.day1.Day1;

import java.util.List;

public class Launcher
{

	private static final List<Challenge> challenges = List.of(new Day1());

	public static void main(String[] args)
	{
		challenges.forEach(System.out::println);
	}
}