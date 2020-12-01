package de.adventofcode.day1;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * # JMH version: 1.26
 * # VM version: JDK 15, OpenJDK 64-Bit Server VM, 15+36
 * <p>
 * Benchmark           Mode  Cnt    Score   Error  Units
 * Day1.defaultSearch  avgt    5  225,546 ± 3,132  us/op
 * Day1.mapSearch      avgt    5   16,677 ± 0,417  us/op
 * Day1.sortSearch     avgt    5   18,709 ± 0,441  us/op <- Sorting in Method
 * Day1.sortSearch     avgt    5    0,176 ± 0,009  us/op <- Sorting before
 */
public class Day1
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
	public static void mapSearch(final MapWrapper inputWrapper,
	                             final Blackhole blackhole)
	{
		final Map<Integer, Integer> input = inputWrapper.map;
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
					blackhole.consume(new Triplet<>(outer, middle, rest));
					return;
				}
			}
		}
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
	public static void defaultSearch(final ListWrapper inputWrapper,
	                                 final Blackhole blackhole)
	{
		final List<Integer> input = inputWrapper.list;
		for (final Integer outer : input)
		{
			for (final Integer middle : input)
			{
				for (final Integer inner : input)
				{
					if (outer + middle + inner == 2020)
					{
						blackhole.consume(new Triplet<>(outer, middle, inner));
						return;
					}
				}
			}
		}
	}


	@Benchmark
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@Warmup(iterations = 2)
	@Measurement(iterations = 5)
	@BenchmarkMode(Mode.AverageTime)
	@Fork(value = 1)
	/**
	 * Sorting within the Method:
	 *  Result "de.adventofcode.day1.Day1.sortSearch":
	 *   18,709 ±(99.9%) 0,441 us/op [Average]
	 *   (min, avg, max) = (18,579, 18,709, 18,853), stdev = 0,114
	 *   CI (99.9%): [18,269, 19,150] (assumes normal distribution)
	 *
	 * With sorting before:
	 *  Result "de.adventofcode.day1.Day1.sortSearch":
	 *   0,233 ±(99.9%) 0,005 us/op [Average]
	 *   (min, avg, max) = (0,231, 0,233, 0,234), stdev = 0,001
	 *   CI (99.9%): [0,228, 0,238] (assumes normal distribution)
	 */
	public static void sortSearch(final ArrayWrapper inputWrapper,
	                              final Blackhole blackhole)
	{
		final List<Integer> input = inputWrapper.list;
		final Object[] objects = inputWrapper.array;

		for (int i = 0; i < input.size(); i++)
		{
			final Integer first = input.get(i);
			final int maxSecond = 2020 - first;
			final int location = Math.abs(Arrays.binarySearch(objects, maxSecond));

			for (int j = 0; j < location; j++)
			{
				final Integer second = input.get(j);

				if (first + second > 2020)
				{
					continue;
				}

				final int rest = 2020 - first - second;
				final int locationLast = Math.abs(Arrays.binarySearch(objects, rest));

				if (input.get(locationLast) == rest)
				{
					blackhole.consume(new Triplet<>(first, second, input.get(locationLast)));
					return;
				}
			}
		}
	}

	@State(Scope.Benchmark)
	public static class ListWrapper
	{
		List<Integer> list;

		@Setup(Level.Invocation)
		public void setup()
		{
			this.list = getInput();
		}
	}

	@State(Scope.Benchmark)
	public static class ArrayWrapper
	{
		List<Integer> list;
		Object[] array;

		@Setup(Level.Invocation)
		public void setup()
		{
			this.list = getInput();
			Collections.sort(this.list);
			this.array = this.list.toArray();
		}
	}

	@State(Scope.Benchmark)
	public static class MapWrapper
	{
		Map<Integer, Integer> map;

		@Setup(Level.Invocation)
		public void setup()
		{
			final List<Integer> input = getInput();
			final Map<Integer, Integer> temp = new HashMap<>(input.size());
			input.forEach(i -> temp.put(i, i));
			this.map = Collections.unmodifiableMap(temp);
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
