package us.davidandersen.random;

public class Random
{
	private final java.util.Random random;

	public Random()
	{
		this(new java.util.Random());
	}

	public Random(final java.util.Random random)
	{
		this.random = random;
	}

	public int between(final int min, final int max)
	{
		final int num = random.nextInt(max - min + 1) + min;

		return num;
	}

	public int nextInt(final int n)
	{
		return random.nextInt(n);
	}

	public int nextInt()
	{
		return random.nextInt();
	}

	public float nextFloat()
	{
		return random.nextFloat();
	}
}
