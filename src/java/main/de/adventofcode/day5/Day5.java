package de.adventofcode.day5;

import de.adventofcode.Day;
import de.adventofcode.util.ArrayUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark             Mode  Cnt   Score   Error  Units
 * Day5.benchmarkPart2   avgt   15  53,164 ± 3,317  us/op
 * Day5.benchmarkPart22  avgt   15  68,392 ± 1,662  us/op
 */
public class Day5 extends Day
{

	@Benchmark
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@Warmup(iterations = 2)
	@Measurement(iterations = 3)
	@BenchmarkMode(Mode.AverageTime)
	/**
	 * Result "de.adventofcode.day5.Day5.benchmarkPart2":
	 *   53,164 ±(99.9%) 3,317 us/op [Average]
	 *   (min, avg, max) = (48,296, 53,164, 56,965), stdev = 3,103
	 *   CI (99.9%): [49,847, 56,481] (assumes normal distribution)
	 */
	public static void benchmarkPart2(final InputWrapper wrapper, final Blackhole blackhole)
	{
		blackhole.consume(part2(wrapper.input));
	}

	@Benchmark
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@Warmup(iterations = 2)
	@Measurement(iterations = 3)
	@BenchmarkMode(Mode.AverageTime)
	/**
	 * Result "de.adventofcode.day5.Day5.benchmarkPart22":
	 *   68,392 ±(99.9%) 1,662 us/op [Average]
	 *   (min, avg, max) = (66,344, 68,392, 71,421), stdev = 1,554
	 *   CI (99.9%): [66,731, 70,054] (assumes normal distribution)
	 */
	public static void benchmarkPart22(final InputWrapper wrapper, final Blackhole blackhole)
	{
		blackhole.consume(part22(wrapper.input));
	}

	@Override
	public Long solvePart1()
	{
		final List<String> input = getInput();

		return part1(input);
	}

	private static long part1(List<String> input)
	{
		long max = 0;

		for (final String line : input)
		{
			final long id = getId(line);

			if (id > max)
			{
				max = id;
			}
		}

		return max;
	}

	@Override
	public Long solvePart2()
	{
		final List<String> input = getInput();

		return part2(input);
	}

	private static long part2(List<String> input)
	{
		final Map<Long, Boolean> seats = new HashMap<>(input.size());

		for (final String line : input)
		{
			seats.put(getId(line), Boolean.TRUE);
		}

		for (final Long lower : seats.keySet())
		{
			if (seats.get(lower + 1) == null && seats.get(lower + 2) != null)
			{
				return lower + 1;
			}
		}

		return -1L;
	}

	public static Long part22(final List<String> input)
	{
		final long[] arr = new long[input.size()];
		for (int i = 0; i < input.size(); i++)
		{
			arr[i] = getId(input.get(i));
		}
		ArrayUtils.quickSort(arr, 0, arr.length - 1);

		for (int i = 0; i < arr.length - 1; i++)
		{
			final long current = arr[i];
			final long next = arr[i + 1];

			if (current + 2 == next)
			{
				return current + 1;
			}
		}

		return -1L;
	}

	private static long getId(String line)
	{
		final char[] chars = line.toCharArray();

		for (int i = 0; i < 10; i++)
		{
			switch (chars[i])
			{
				case 'F','L' -> chars[i] = '0';
				case 'B','R' -> chars[i] = '1';
			}
		}
		return Integer.parseInt(new String(chars), 2);
	}

	private static int getRow(final char[] chars, final char toFilter, int lowest, int highest, final int start, final int stop)
	{
		for (int i = start; i < stop; i++)
		{
			if (chars[i] == toFilter)
			{
				highest = Math.floorDiv(highest + lowest, 2);
			}
			else
			{
				lowest = Math.floorDiv(highest + lowest, 2) + 1;
			}
		}
		return highest;
	}

	@State(Scope.Benchmark)
	public static class InputWrapper
	{
		List<String> input = null;

		@Setup
		public void setup()
		{
			this.input = new Day5().getInput();
			Collections.shuffle(this.input);
		}
	}
}
