package de.adventofcode;

import de.adventofcode.day1.Day1;
import de.adventofcode.day2.Day2;
import de.adventofcode.day3.Day3;
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
}
