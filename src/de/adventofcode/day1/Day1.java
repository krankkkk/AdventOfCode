package de.adventofcode.day1;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * # JMH version: 1.26
 * # VM version: JDK 15, OpenJDK 64-Bit Server VM, 15+36
 * <p>
 * Benchmark            Mode  Cnt    Score   Error  Units
 * Day1.containsSearch  avgt    5  16,677 ± 0,417  us/op
 * Day1.defaultSearch   avgt    5  265,980 ± 11,514  us/op
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
	public static void containsSearch(final MapWrapper inputWrapper,
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
	public static class MapWrapper
	{
		Map<Integer, Integer> map;

		@Setup(Level.Invocation)
		public void setup()
		{
			final List<Integer> input = getInput();
			final Map<Integer, Integer> map = new HashMap<>(input.size());
			input.forEach(i -> map.put(i, i));
			this.map = map;
		}
	}

	private static class Triplet<T>
	{
		final T one;
		final T two;
		final T three;

		public Triplet(T one, T two, T three)
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
			return reader.lines().map(Integer::parseInt).collect(Collectors.toUnmodifiableList());
		}
		catch (IOException | URISyntaxException e)
		{
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
}
