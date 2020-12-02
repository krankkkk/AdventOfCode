package de.adventofcode;

import de.adventofcode.day1.Day1;
import de.adventofcode.day2.Day2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DaysTest
{

	@Test
	void testDay1()
	{
		final Day day = new Day1();
		Assertions.assertEquals(1009899, day.solvePart1());
		Assertions.assertEquals(44211152, day.solvePart2());
	}

	@Test
	void testDay2()
	{
		final Day day = new Day2();
		Assertions.assertEquals(458, day.solvePart1());
		Assertions.assertEquals(342, day.solvePart2());
	}
}
