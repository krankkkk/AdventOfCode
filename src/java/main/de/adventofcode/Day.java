package de.adventofcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Day implements Challenge
{

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "{" + "Part1: " + this.solvePart1() + " Part2: " + this.solvePart2() + "}";
	}

	protected List<String> getInput()
	{
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(getClass().getResource("input.txt").toURI()))))
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
