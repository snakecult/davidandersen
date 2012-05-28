package us.davidandersen.util;

import java.util.Date;

public class TimeUtil
{
	public static long getUtcInSeconds()
	{
		return getTimeInMillis() / 1000;
	}

	public static long getTimeInMillis()
	{
		final Date date = new Date();

		return date.getTime();
	}

	public static long getElapsedTime(final long startTime)
	{
		return getUtcInSeconds() - startTime;
	}

	public static String formatTime(final long seconds)
	{
		if (seconds < 60)
			return seconds + "s";
		if (seconds < 3600)
			return seconds / 60 + "m";
		if (seconds < 86400)
			return seconds / 3600 + "h";

		return seconds / 86400 + "d";
	}
}
