package de.adventofcode.day1;

import de.adventofcode.Day;
import de.adventofcode.util.Doublet;
import de.adventofcode.util.Triplet;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static de.adventofcode.util.ListUtils.mapToInt;
import static de.adventofcode.util.ListUtils.mapToLong;

/**
 * # JMH version: 1.26
 * # VM version: JDK 15, OpenJDK 64-Bit Server VM, 15+36
 * <p>
 * Benchmark                    Mode  Cnt    Score    Error  Units
 * Day1.benchmarkDefaultSearch  avgt    5  675,929 ± 174,776  us/op
 * Day1.benchmarkMapSearch      avgt    5   21,585 ±   0,207  us/op
 * Day1.benchmarkSortSearch     avgt    5    4,358 ±   0,076  us/op
 * Day1.benchmarkSortedSearch   avgt    5  75,375  ±   1,366  ns/op (that's 0,034% of the default-search)
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
	@Fork(value = 1)
	/**
	 * Result "de.adventofcode.day1.Day1.defaultSearch":
	 *   675,929 ±(99.9%) 174,776 us/op [Average]
	 *   (min, avg, max) = (623,864, 675,929, 719,857), stdev = 45,389
	 *   CI (99.9%): [501,153, 850,705] (assumes normal distribution)
	 */
	public static void benchmarkDefaultSearch(final InputWrapper wrapper, final Blackhole blackhole)
	{
		blackhole.consume(defaultSearch(wrapper.input));
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
	 *   4,358 ±(99.9%) 0,076 us/op [Average]
	 *   (min, avg, max) = (4,332, 4,358, 4,380), stdev = 0,020
	 *   CI (99.9%): [4,282, 4,434] (assumes normal distribution)
	 *
	 */
	public static void benchmarkSortSearch(final InputWrapper wrapper, final Blackhole blackhole)
	{
		final Long[] array = getArray(wrapper.input);
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
	 *   75,375 ±(99.9%) 1,366 ns/op [Average]
	 *   (min, avg, max) = (74,889, 75,375, 75,846), stdev = 0,355
	 *   CI (99.9%): [74,009, 76,741] (assumes normal distribution)
	 */
	public static void benchmarkSortedSearch(final SortedWrapper wrapper, final Blackhole blackhole)
	{
		blackhole.consume(sortSearch(wrapper.input));
	}

	private static Triplet<Long> mapSearch(final Map<Long, Long> input)
	{
		for (final Long outer : input.keySet())
		{
			for (final Long middle : input.keySet())
			{
				if (outer + middle > 2020)
				{
					continue;
				}

				final Long rest = 2020 - outer - middle;

				if (input.get(rest) != null)
				{
					return new Triplet<>(outer, middle, rest);
				}
			}
		}
		return null;
	}

	private static Triplet<Long> defaultSearch(final List<Long> input)
	{
		for (final Long outer : input)
		{
			for (final Long middle : input)
			{
				for (final Long inner : input)
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

	private static Triplet<Long> sortSearch(final Long[] sorted)
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
					return new Triplet<>(first, second, last);
				}
			}
		}
		return null;
	}

	private static Optional<Doublet<Integer>> streamSearch(List<Integer> input)
	{
		return input.stream()
				.filter(in -> input.contains(2020 - in))
				.findFirst()
				.map(i -> new Doublet<>(i, 2020 - i));
	}


	private static Map<Long, Long> getMap(final List<Long> input)
	{
		final Map<Long, Long> temp = new HashMap<>(input.size());
		input.forEach(i -> temp.put(i, i));
		return temp;
	}

	private static Long[] getArray(final List<Long> input)
	{
		return input.toArray(new Long[0]);
	}

	public static int partition(Long[] a, int beg, int end)
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

	static void quickSort(Long[] a, int beg, int end)
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
	public long solvePart1()
	{
		final Optional<Doublet<Integer>> optional = streamSearch(mapToInt(getInput()));
		if (optional.isEmpty())
		{
			throw new RuntimeException("Search Went Wrong");
		}
		final Doublet<Integer> doublet = optional.get();
		return doublet.getOne() * doublet.getTwo();
	}

	@Override
	public long solvePart2()
	{
		final Long[] array = getArray(mapToLong(getInput()));
		quickSort(array, 0, array.length - 1);
		final Triplet<Long> triplet = sortSearch(array);
		if (triplet == null)
		{
			throw new RuntimeException("Search Went Wrong");
		}

		return triplet.getOne() * triplet.getTwo() * triplet.getThree();
	}

	@State(Scope.Benchmark)
	public static class InputWrapper
	{
		List<Long> input = null;

		@Setup
		public void setup()
		{
			this.input = mapToLong(new Day1().getInput());
			Collections.shuffle(this.input);
		}
	}

	@State(Scope.Benchmark)
	public static class SortedWrapper
	{
		Long[] input = null;

		@Setup
		public void setup()
		{
			final Long[] integers = mapToLong(new Day1().getInput()).toArray(new Long[0]);
			quickSort(integers, 0, integers.length - 1);
			this.input = integers;
		}
	}
}
