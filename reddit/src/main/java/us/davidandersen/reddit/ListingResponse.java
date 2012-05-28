package us.davidandersen.reddit;

import java.util.List;

public class ListingResponse
{
	public String kind;
	public ListingData data;

	static class ListingData
	{
		public String modhash;
		public List<ListingChild> children;

		static class ListingChild
		{
			public String kind;
			public T3Data data;

			static class T3Data
			{
				public String title;
				public long created_utc;
				public String permalink;
				public String author;
				public String name;
				public String selftext;
			}
		}
	}
}
