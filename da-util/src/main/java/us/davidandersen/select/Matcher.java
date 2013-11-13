package us.davidandersen.select;

public interface Matcher<T>
{
	boolean isMatch(T object);
}
