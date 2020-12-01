package de.adventofcode.day1;

import de.adventofcode.Challenge;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * # JMH version: 1.26
 * # VM version: JDK 15, OpenJDK 64-Bit Server VM, 15+36
 * <p>
 * Benchmark                    Mode  Cnt    Score    Error  Units
 * Day1.benchmarkDefaultSearch  avgt    5  264,391 ± 17,445  us/op
 * Day1.benchmarkMapSearch      avgt    5    2,512 ±  0,040  us/op
 * Day1.benchmarkSortSearch     avgt    5   17,106 ±  0,328  us/op
 */
public class Day1 implements Challenge
{
	@Benchmark
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@Warmup(iterations = 2)
	@Measurement(iterations = 5)
	@BenchmarkMode(Mode.AverageTime)
	@Fork(value = 1)
	/**
	 * Result "de.adventofcode.day1.Day1.containsSearch":
	 *   16,677 ±(99.9%) 0,417 us/op [Average]
	 *   (min, avg, max) = (16,546, 16,677, 16,805), stdev = 0,108
	 *   CI (99.9%): [16,261, 17,094] (assumes normal distribution)
	 */
	public static void benchmarkMapSearch(final Blackhole blackhole)
	{
		blackhole.consume(getMap());
	}

	@Benchmark
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@Warmup(iterations = 2)
	@Measurement(iterations = 5)
	@BenchmarkMode(Mode.AverageTime)
	@Fork(value = 1)
	/**
	 * Result "de.adventofcode.day1.Day1.defaultSearch":
	 *   265,333 ±(99.9%) 3,947 us/op [Average]
	 *   (min, avg, max) = (264,324, 265,333, 266,582), stdev = 1,025
	 *   CI (99.9%): [261,386, 269,281] (assumes normal distribution)
	 */
	public static void benchmarkDefaultSearch(final Blackhole blackhole)
	{
		blackhole.consume(defaultSearch(getInput()));
	}

	@Benchmark
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@Warmup(iterations = 3)
	@Measurement(iterations = 5)
	@BenchmarkMode(Mode.AverageTime)
	@Fork(value = 1)
	/**
	 * Sorting within the Method:
	 *  Result "de.adventofcode.day1.Day1.sortSearch":
	 *   17,106 ±(99.9%) 0,328 us/op [Average]
	 *   (min, avg, max) = (17,001, 17,106, 17,224), stdev = 0,085
	 *   CI (99.9%): [16,778, 17,434] (assumes normal distribution)
	 *
	 * With sorting before:
	 *  Result "de.adventofcode.day1.Day1.sortSearch":
	 *   139,844 ±(99.9%) 4,848 ns/op [Average]
	 *   (min, avg, max) = (138,747, 139,844, 141,744), stdev = 1,259
	 *   CI (99.9%): [134,996, 144,692] (assumes normal distribution)
	 */
	public static void benchmarkSortSearch(final Blackhole blackhole)
	{
		final Integer[] array = getArray();
		Arrays.sort(array);
		blackhole.consume(sortSearch(array));
	}

	private static Triplet<Integer> mapSearch(final Map<Integer, Integer> input)
	{
		for (final Integer outer : input.keySet())
		{
			for (final Integer middle : input.keySet())
			{
				if (outer + middle > 2020)
				{
					continue;
				}

				final int rest = 2020 - outer - middle;

				if (input.get(rest) != null)
				{
					return new Triplet<>(outer, middle, rest);
				}
			}
		}
		return null;
	}

	private static Triplet<Integer> defaultSearch(final List<Integer> input)
	{
		for (final Integer outer : input)
		{
			for (final Integer middle : input)
			{
				for (final Integer inner : input)
				{
					if (outer + middle + inner == 2020)
					{
						return new Triplet<>(outer, middle, inner);
					}
				}
			}
		}
		return null;
	}

	private static Triplet<Integer> sortSearch(final Integer[] sorted)
	{
		for (int i = 0; i < sorted.length; i++)
		{
			final Integer first = sorted[i];
			final int maxSecond = 2020 - first;
			final int location = Math.abs(Arrays.binarySearch(sorted, maxSecond));

			for (int j = i; j < location; j++)
			{
				final Integer second = sorted[j];

				final int rest = 2020 - first - second;
				final int locationLast = Math.abs(Arrays.binarySearch(sorted, rest));

				final Integer last = sorted[locationLast];
				if (last == rest)
				{
					return new Triplet<>(first, second, last);
				}
			}
		}
		return null;
	}

	private static Doublet<Integer> miniSearchSort(Map<Integer, Integer> input)
	{
		for (final Integer i : input.keySet())
		{
			final int rest = 2020 - i;
			if (input.get(rest) != null)
			{
				return new Doublet<>(i, rest);
			}
		}
		return null;
	}

	private static Map<Integer, Integer> getMap()
	{
		final List<Integer> input = getInput();
		final Map<Integer, Integer> temp = new HashMap<>(input.size());
		input.forEach(i -> temp.put(i, i));
		return temp;
	}

	private static Integer[] getArray()
	{
		return getInput().toArray(new Integer[0]);
	}


	private static List<Integer> getInput()
	{
		return InputDay1.input;
		/*
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(Day1.class.getResource("input.txt").toURI()))))
		{
			return reader.lines().map(Integer::parseInt).collect(Collectors.toList());
		}
		catch (IOException | URISyntaxException e)
		{
			e.printStackTrace();
		}
		return Collections.emptyList();
		 */
	}

	@Override
	public int solvePart1()
	{
		final Doublet<Integer> doublet = miniSearchSort(getMap());
		if (doublet == null)
		{
			throw new RuntimeException("Search Went Wrong");
		}
		return doublet.one * doublet.two;
	}

	@Override
	public int solvePart2()
	{
		final Integer[] array = getArray();
		Arrays.sort(array);
		final Triplet<Integer> triplet = sortSearch(array);
		if (triplet == null)
		{
			throw new RuntimeException("Search Went Wrong");
		}

		return triplet.one * triplet.two * triplet.three;
	}

	@Override
	public String toString()
	{
		return "Day1{" + "Part1: " + this.solvePart1() + " Part2: " + this.solvePart2() + "}";
	}

	private static class Doublet<T>
	{
		final T one;
		final T two;

		public Doublet(final T one,
		               final T two)
		{
			this.one = one;
			this.two = two;
		}
	}

	private static class Triplet<T>
	{
		final T one;
		final T two;
		final T three;

		public Triplet(final T one,
		               final T two,
		               final T three)
		{
			this.one = one;
			this.two = two;
			this.three = three;
		}
	}

}
