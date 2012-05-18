package us.davidandersen.reddit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class RedditApiClient {

	private Logger log = Logger.getLogger(RedditApiClient.class);

	public List<Listing> getNewListings(String subReddit) throws IOException {
		return getListings("http://www.reddit.com/r/" + subReddit + "/new.json?sort=new");
	}

	public List<Listing> getRisingListings(String subReddit) throws IOException {
		return getListings("http://www.reddit.com/r/" + subReddit + "/new.json?sort=rising");
	}

	public List<Listing> getHotListings(String subReddit) throws IOException {
		return getListings("http://www.reddit.com/r/" + subReddit + ".json");
	}

	private List<Listing> getListings(String sUrl) throws MalformedURLException, IOException {
		final URL url = new URL(sUrl);
		log.debug("url=" + sUrl);
		final String json = getJson(url);
		final List<Listing> listings = convertListings(json);
		log.debug("results=" + listings.size());
		return listings;
	}

	private List<Listing> convertListings(String json) {

		final List<Listing> listings = new ArrayList<Listing>();
		final Gson gson = new Gson();
		final ListingResponse newListings = gson.fromJson(json, ListingResponse.class);
		for (ListingResponse.ListingData.ListingChild c : newListings.data.children) {
			final Listing listing = new Listing();
			listing.title = c.data.title;
			listing.created_utc = c.data.created_utc;
			listing.permalink = c.data.permalink;
			listing.author = c.data.author;
			listing.name = c.data.name;
			listings.add(listing);
		}

		return listings;
	}

	private String getJson(URL url) throws IOException {
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("User-Agent", "onebot by /u/onebit");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		String line = "";
		while ((inputLine = in.readLine()) != null) {
			line += inputLine;
		}
		in.close();

		return line;
	}
}
