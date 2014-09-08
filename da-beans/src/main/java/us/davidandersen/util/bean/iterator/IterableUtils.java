package us.davidandersen.util.bean.iterator;

public class IterableUtils
{
	public static boolean isObjectIterable(final Object actualValue)
	{
		return actualValue instanceof Iterable;
	}
}
