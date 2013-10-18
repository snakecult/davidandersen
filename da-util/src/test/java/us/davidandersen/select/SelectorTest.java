package us.davidandersen.select;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SelectorTest
{
	// private Boolean boolFalse;
	//
	// private Boolean boolTrue;

	// private List<Boolean> booleans;

	private List<Integer> integers;

	@Before
	public void setUp()
	{
		// boolFalse = false;
		// boolTrue = true;
		//
		// booleans = new ArrayList<Boolean>();
		// booleans.add(boolFalse);
		// booleans.add(boolTrue);

		integers = new ArrayList<Integer>();
		integers.add(1);
		integers.add(2);
		integers.add(3);
		integers.add(4);
		integers.add(5);
		integers.add(6);
	}

	@Test
	public void testSelectOdd()
	{
		// test
		final List<Integer> results = Selector.select(integers, new OddMatcher());

		// verify
		assertEquals(3, results.size());
		assertTrue(results.contains(1));
		assertTrue(results.contains(3));
		assertTrue(results.contains(5));
	}

	@Test
	public void testSelectEven()
	{
		// test
		final List<Integer> results = Selector.select(integers, new EvenMatcher());

		// verify
		assertEquals(3, results.size());
		assertTrue(results.contains(2));
		assertTrue(results.contains(4));
		assertTrue(results.contains(6));
	}

	@Test
	public void testSelectNotOdd()
	{
		// test
		final List<Integer> results = Selector.selectNot(integers, new OddMatcher());

		// verify
		assertEquals(3, results.size());
		assertTrue(results.contains(2));
		assertTrue(results.contains(4));
		assertTrue(results.contains(6));
	}

	@Test
	public void testSelectNotEven()
	{
		// test
		final List<Integer> results = Selector.selectNot(integers, new EvenMatcher());

		// verify
		assertEquals(3, results.size());
		assertTrue(results.contains(1));
		assertTrue(results.contains(3));
		assertTrue(results.contains(5));
	}

	static class OddMatcher implements Matcher<Integer>
	{
		@Override
		public boolean isMatch(final Integer number)
		{
			return number % 2 == 1;
		}
	}

	static class EvenMatcher implements Matcher<Integer>
	{
		@Override
		public boolean isMatch(final Integer number)
		{
			return number % 2 == 0;
		}
	}

	static class DivisibleBy3Matcher implements Matcher<Integer>
	{
		@Override
		public boolean isMatch(final Integer number)
		{
			return number % 3 == 0;
		}
	}
}
