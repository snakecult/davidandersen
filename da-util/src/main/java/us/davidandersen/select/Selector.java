package us.davidandersen.select;

import java.util.ArrayList;
import java.util.List;

public class Selector
{
	public static <T> List<T> select(final List<T> objects, final Matcher<T> matcher)
	{
		final List<T> matches = new ArrayList<T>();

		for (final T object : objects)
		{
			if (matcher.isMatch(object))
			{
				matches.add(object);
			}
		}

		return matches;
	}

	public static <T> List<T> selectNot(final List<T> objects, final Matcher<T> matcher)
	{
		final List<T> matches = new ArrayList<T>();

		for (final T object : objects)
		{
			if (!matcher.isMatch(object))
			{
				matches.add(object);
			}
		}

		return matches;
	}
}
