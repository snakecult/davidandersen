package us.davidandersen.random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RandomizerTest
{
	@Mock
	Random random;

	@Mock
	Randomizer randomizer;

	private Randomizer cut;

	private SomeClass m;

	@Before
	public void before()
	{
		cut = new Randomizer(random, randomizer);

		m = new SomeClass();
	}

	@Test
	public void randomize1000()
	{
		when(random.between(1000, 9999)).thenReturn(1000);

		assertEquals("1000", cut.randomize(""));
	}

	@Test
	public void randomize9999()
	{
		when(random.between(1000, 9999)).thenReturn(9999);

		assertEquals("test9999", cut.randomize("test"));
	}

	@Test
	public void randomizeEmptyString()
	{
		when(randomizer.randomize("")).thenReturn("1000");

		cut.randomize(m);

		assertEquals("1000", m.x);
	}

	@Test
	public void randomizeString()
	{
		when(randomizer.randomize("bob")).thenReturn("bob9999");

		cut.randomize(m);

		assertEquals("bob9999", m.y);
	}

	@Test
	public void randomizeInt()
	{
		when(random.between(0, 9999)).thenReturn(0);

		cut.randomize(m);

		assertEquals(0, m.a);
	}

	@Test
	public void randomizeInt2()
	{
		when(random.between(0, 9999)).thenReturn(9999);

		cut.randomize(m);

		assertEquals(9999, m.a);
	}

	class SomeClass
	{
		@Randomize
		private String x;

		@Randomize("bob")
		private String y;

		@Randomize()
		private int a;
	}
}
