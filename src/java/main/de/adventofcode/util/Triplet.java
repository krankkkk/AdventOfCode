package de.adventofcode.util;

public class Triplet<T>
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

	public T getOne()
	{
		return one;
	}

	public T getTwo()
	{
		return two;
	}

	public T getThree()
	{
		return three;
	}
}
