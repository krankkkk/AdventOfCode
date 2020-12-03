package de.adventofcode.util;

import java.util.List;
import java.util.stream.Collectors;

public class ListUtils
{
	private ListUtils()
	{
	}

	public static List<Integer> mapToInt(final List<String> strings)
	{
		return strings.stream().map(Integer::parseInt).collect(Collectors.toList());
	}

	public static List<Long> mapToLong(final List<String> strings)
	{
		return strings.stream().map(Long::parseLong).collect(Collectors.toList());
	}
}
