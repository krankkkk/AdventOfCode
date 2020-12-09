package de.adventofcode;

import de.adventofcode.day1.Day1;
import de.adventofcode.day2.Day2;
import de.adventofcode.day3.Day3;
import de.adventofcode.day4.Day4;
import de.adventofcode.day5.Day5;
import de.adventofcode.day6.Day6;
import de.adventofcode.day7.Day7;
import de.adventofcode.day8.Day8;
import de.adventofcode.day9.Day9;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DaysTest
{

	@Test
	void testDay1()
	{
		final Day day = new Day1();
		assertEquals(1009899, day.solvePart1());
		assertEquals(44211152, day.solvePart2());
	}

	@Test
	void testDay2()
	{
		final Day day = new Day2();
		assertEquals(458, day.solvePart1());
		assertEquals(342, day.solvePart2());
	}

	@Test
	void testDay3()
	{
		final Day day = new Day3();
		assertEquals(244, day.solvePart1());
		assertEquals(9406609920L, day.solvePart2());
	}

	@Test
	void testDay4()
	{
		final Day day = new Day4();
		assertEquals(237, day.solvePart1());
		assertEquals(172, day.solvePart2());
	}

	@Test
	void testDay5()
	{
		final Day day = new Day5();
		assertEquals(951, day.solvePart1());
		assertEquals(653, day.solvePart2());
	}

	@Test
	void testDay6()
	{
		final Day day = new Day6();
		assertEquals(6768, day.solvePart1());
		assertEquals(3489, day.solvePart2());
	}

	@Test
	void testDay7()
	{
		final Day day = new Day7();
		assertEquals(144, day.solvePart1());
		assertEquals(5956, day.solvePart2());
	}

	@Test
	void testDay8()
	{
		final Day day = new Day8();
		assertEquals(1262, day.solvePart1());
		assertEquals(1643, day.solvePart2());
	}


	@Test
	void testDay9()
	{
		final Day day = new Day9();
		assertEquals(373803594, day.solvePart1());
		assertEquals(51152360, day.solvePart2());
	}

}
