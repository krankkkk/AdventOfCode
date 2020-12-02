package de.adventofcode.util;

public class Doublet<T>
{
	final T one;
	final T two;

	public Doublet(final T one,
	               final T two)
	{
		this.one = one;
		this.two = two;
	}

	public T getOne()
	{
		return one;
	}

	public T getTwo()
	{
		return two;
	}
}
