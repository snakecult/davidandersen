package us.davidandersen.select;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SelectorTest
{
	private Boolean boolFalse;

	private Boolean boolTrue;

	private List<Boolean> booleans;

	@Before
	public void setUp()
	{
		boolFalse = false;
		boolTrue = true;

		booleans = new ArrayList<Boolean>();
		booleans.add(boolFalse);
		booleans.add(boolTrue);
	}

	@Test
	public void testSelectTrue()
	{
		// test
		final List<Boolean> results = Selector.select(booleans, new TrueSelector());

		// verify
		assertEquals(1, results.size());
		assertTrue(results.contains(boolTrue));
	}

	@Test
	public void testSelectFalse()
	{
		// test
		final List<Boolean> results = Selector.select(booleans, new FalseSelector());

		// verify
		assertEquals(1, results.size());
		assertTrue(results.contains(boolFalse));
	}

	@Test
	public void testSelectNotTrue()
	{
		// test
		final List<Boolean> results = Selector.selectNot(booleans, new TrueSelector());

		// verify
		assertEquals(1, results.size());
		assertTrue(results.contains(boolFalse));
	}

	@Test
	public void testSelectNotFalse()
	{
		// test
		final List<Boolean> results = Selector.selectNot(booleans, new FalseSelector());

		// verify
		assertEquals(1, results.size());
		assertTrue(results.contains(boolTrue));
	}

	static class TrueSelector implements Matcher<Boolean>
	{
		@Override
		public boolean isMatch(final Boolean object)
		{
			return object == true;
		}
	}

	static class FalseSelector implements Matcher<Boolean>
	{
		@Override
		public boolean isMatch(final Boolean object)
		{
			return object == false;
		}
	}
}
