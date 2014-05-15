package us.davidandersen.http;

import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class SimpleHttpClientTest
{
	@Mock
	HttpClientFactory httpClient;

	private SimpleHttpClient objectUnderTest;

	@Before
	public void setUp()
	{
		objectUnderTest = new SimpleHttpClient(httpClient);
	}

	@Test
	public void test()
	{
		HttpClients.createDefault();
		objectUnderTest.get("http://www.example.com");
	}
}
