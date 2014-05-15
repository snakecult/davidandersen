package us.davidandersen.http;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientFactory
{
	public CloseableHttpClient createClient()
	{
		return HttpClients.createDefault();
	}
}
