package us.davidandersen;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil
{
	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

	static String readFromCon(final HttpURLConnection c) throws IOException
	{
		final BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		String inputLine;
		String line = "";
		while ((inputLine = in.readLine()) != null)
		{
			line += inputLine;
		}
		in.close();
		return line;
	}

	public static String get(final URL u) throws IOException
	{
		final HttpURLConnection c = (HttpURLConnection)u.openConnection();
		c.setRequestMethod("GET");
		final String json = readFromCon(c);
		return json;
	}

	public static String post(final URL u, final Map<String, String> params) throws IOException
	{
		final HttpURLConnection c = (HttpURLConnection)u.openConnection();
		c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		c.setRequestMethod("POST");
		c.setDoOutput(true);

		writeCon(c, getParams(params));

		final String response = readFromCon(c);

		return response;
	}

	private static String getParams(final Map<String, String> params) throws UnsupportedEncodingException
	{
		final Set<String> keySet = params.keySet();
		String content = "";
		for (final String key : keySet)
		{
			if (content != "")
			{
				content += "&";
			}
			log.info("key=" + key);
			final String s = params.get(key);
			if (s != null)
			{
				content += key + "=" + URLEncoder.encode(s, "UTF-8");
			}
			else
			{
				content += key + "=" + URLEncoder.encode("", "UTF-8");
			}
		}

		return content;
	}

	private static void writeCon(final HttpURLConnection c, final String content) throws IOException
	{
		DataOutputStream printout;
		printout = new DataOutputStream(c.getOutputStream());
		printout.writeBytes(content);
		printout.flush();
		printout.close();
	}
}
