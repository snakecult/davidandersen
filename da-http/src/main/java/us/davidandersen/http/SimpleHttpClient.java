package us.davidandersen.http;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleHttpClient
{
	private final HttpClientFactory httpClientFactory;

	Logger log = LoggerFactory.getLogger(SimpleHttpClient.class);

	public SimpleHttpClient(final HttpClientFactory httpClient)
	{
		this.httpClientFactory = httpClient;
	}

	public String get(final String url) throws ClientProtocolException, IOException
	{
		final CloseableHttpClient httpClient = httpClientFactory.createClient();
		final HttpGet httpGet = new HttpGet(url);
		log.info("GET " + url);
		final CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		final String response = EntityUtils.toString(httpResponse.getEntity());
		log.info("response=" + response);
		httpResponse.close();
		httpClient.close();

		return response;
	}
}
