package de.adventofcode;

public abstract class Day implements Challenge
{

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "{" + "Part1: " + this.solvePart1() + " Part2: " + this.solvePart2() + "}";
	}
}
