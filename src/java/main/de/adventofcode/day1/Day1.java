package de.adventofcode.day1;

import de.adventofcode.Day;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * # JMH version: 1.26
 * # VM version: JDK 15, OpenJDK 64-Bit Server VM, 15+36
 * <p>
 * Benchmark                    Mode  Cnt    Score    Error  Units
 * Day1.benchmarkDefaultSearch  avgt   50  611,385 ± 169,035  us/op <- all over the place from 600 to 20
 * Day1.benchmarkMapSearch      avgt    5  20,119 ± 0,475  us/op
 * Day1.benchmarkSortSearch     avgt    5   5,323 ± 0,027  us/op
 * Day1.benchmarkSortedSearch   avgt    5  38,586 ± 0,464  ns/op
 */
public class Day1 extends Day
{
	@Benchmark
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@Warmup(iterations = 2)
	@Measurement(iterations = 5)
	@BenchmarkMode(Mode.AverageTime)
	@Fork(value = 1)
	/**
	 * Result "de.adventofcode.day1.Day1.benchmarkMapSearch":
	 *   21,585 ±(99.9%) 0,207 us/op [Average]
	 *   (min, avg, max) = (21,530, 21,585, 21,675), stdev = 0,054
	 *   CI (99.9%): [21,379, 21,792] (assumes normal distribution)
	 */
	public static void benchmarkMapSearch(final InputWrapper wrapper, final Blackhole blackhole)
	{
		blackhole.consume(mapSearch(getMap(wrapper.input)));
	}

	@Benchmark
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@Warmup(iterations = 2)
	@Measurement(iterations = 5)
	@BenchmarkMode(Mode.AverageTime)
	@Fork(value = 10)
	/**
	 * Result "de.adventofcode.day1.Day1.defaultSearch":
	 *   611,385 ±(99.9%) 169,035 us/op [Average]
	 *   (min, avg, max) = (174,917, 611,385, 1292,914), stdev = 341,459
	 *   CI (99.9%): [442,350, 780,420] (assumes normal distribution)
	 */
	public static void benchmarkDefaultSearch(final InputWrapper wrapper, final Blackhole blackhole)
	{
		blackhole.consume(defaultSearch(getArray(wrapper.input)));
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
	 *    5,323 ±(99.9%) 0,027 us/op [Average]
	 *   (min, avg, max) = (5,319, 5,323, 5,336), stdev = 0,007
	 *   CI (99.9%): [5,296, 5,350] (assumes normal distribution)
	 *
	 */
	public static void benchmarkSortSearch(final InputWrapper wrapper, final Blackhole blackhole)
	{
		final long[] array = getArray(wrapper.input);
		quickSort(array, 0, array.length - 1);
		blackhole.consume(sortSearch(array));
	}

	@Benchmark
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	@Warmup(iterations = 3)
	@Measurement(iterations = 5)
	@BenchmarkMode(Mode.AverageTime)
	@Fork(value = 1)

	/**
	 * Result "de.adventofcode.day1.Day1.benchmarkSortedSearch":
	 *   38,586 ±(99.9%) 0,464 ns/op [Average]
	 *   (min, avg, max) = (38,467, 38,586, 38,755), stdev = 0,120
	 *   CI (99.9%): [38,122, 39,049] (assumes normal distribution)
	 */
	public static void benchmarkSortedSearch(final SortedWrapper wrapper, final Blackhole blackhole)
	{
		blackhole.consume(sortSearch(wrapper.input));
	}

	private static long mapSearch(final Map<Long, String> input)
	{
		for (final long outer : input.keySet())
		{
			for (final long middle : input.keySet())
			{
				final long combo = outer + middle;
				if (combo > 2020)
				{
					continue;
				}

				final long rest = 2020 - combo;

				if (input.get(rest) != null)
				{
					return outer * middle * rest;
				}
			}
		}
		return -1;
	}

	private static long defaultSearch(final long[] input)
	{
		for (final long outer : input)
		{
			for (final long middle : input)
			{
				for (final long inner : input)
				{
					if (outer + middle + inner == 2020)
					{
						return outer * middle * inner;
					}
				}
			}
		}
		return -1;
	}

	private static long sortSearch(final long[] sorted)
	{
		for (int i = 0; i < sorted.length; i++)
		{
			final long first = sorted[i];
			final long maxSecond = 2020 - first;
			final long location = Math.abs(Arrays.binarySearch(sorted, maxSecond));

			for (int j = i; j < location; j++)
			{
				final long second = sorted[j];

				final long rest = 2020 - first - second;
				final long locationLast = Math.abs(Arrays.binarySearch(sorted, rest));

				final long last = sorted[(int) locationLast];
				if (last == rest)
				{
					return first * second * last;
				}
			}
		}
		return -1;
	}

	private static long miniSortSearch(long[] input)
	{
		for (final long aLong1 : input)
		{
			final long rest = 2020 - aLong1;
			final int index = Math.abs(Arrays.binarySearch(input, rest));
			if (input[index] == rest)
			{
				return rest * aLong1;
			}
		}
		return -1;
	}


	private static Map<Long, String> getMap(final List<String> input)
	{
		final Map<Long, String> temp = new HashMap<>(input.size());
		input.forEach(i -> temp.put(Long.parseLong(i), i));
		return temp;
	}

	private static long[] getArray(final List<String> input)
	{
		final int size = input.size();
		final long[] arr = new long[size];
		for (int i = 0; i < size; i++)
		{
			arr[i] = Long.parseLong(input.get(i));
		}
		return arr;
	}

	public static int partition(long[] a, int beg, int end)
	{
		int left;
		int right;
		long temp;
		int loc;
		int flag;
		loc = left = beg;
		right = end;
		flag = 0;
		while (flag != 1)
		{
			while ((a[loc] <= a[right]) && (loc != right))
			{
				right--;
			}
			if (loc == right)
			{
				flag = 1;
			}
			else if (a[loc] > a[right])
			{
				temp = a[loc];
				a[loc] = a[right];
				a[right] = temp;
				loc = right;
			}
			if (flag != 1)
			{
				while ((a[loc] >= a[left]) && (loc != left))
				{
					left++;
				}
				if (loc == left)
				{
					flag = 1;
				}
				else if (a[loc] < a[left])
				{
					temp = a[loc];
					a[loc] = a[left];
					a[left] = temp;
					loc = left;
				}
			}
		}
		return loc;
	}

	static void quickSort(long[] a, int beg, int end)
	{
		int loc;
		if (beg < end)
		{
			loc = partition(a, beg, end);
			quickSort(a, beg, loc - 1);
			quickSort(a, loc + 1, end);
		}
	}

	@Override
	public Long solvePart1()
	{
		final long[] array = getArray(getInput());
		quickSort(array, 0, array.length - 1);
		return miniSortSearch(array);
	}

	@Override
	public Long solvePart2()
	{
		final long[] array = getArray(getInput());
		quickSort(array, 0, array.length - 1);
		return sortSearch(array);
	}

	@State(Scope.Benchmark)
	public static class InputWrapper
	{
		List<String> input = null;

		@Setup
		public void setup()
		{
			this.input = new Day1().getInput();
			Collections.shuffle(this.input);
		}
	}

	@State(Scope.Benchmark)
	public static class SortedWrapper
	{
		long[] input = null;

		@Setup
		public void setup()
		{
			final long[] integers = getArray(new Day1().getInput());
			quickSort(integers, 0, integers.length - 1);
			this.input = integers;
		}
	}
}
