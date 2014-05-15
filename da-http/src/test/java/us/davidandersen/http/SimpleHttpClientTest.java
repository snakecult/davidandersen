package us.davidandersen.http;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import us.davidandersen.random.Randomize;
import us.davidandersen.random.Randomizer;

@RunWith(MockitoJUnitRunner.class)
public class SimpleHttpClientTest
{
	private SimpleHttpClient objectUnderTest;

	@Mock
	HttpClientFactory httpClient;

	@Mock
	private CloseableHttpClient client;

	@Captor
	private ArgumentCaptor<HttpGet> request;

	private final Randomizer randomizer = new Randomizer();

	@Randomize("example")
	private String domain;

	@Mock
	private CloseableHttpResponse response;

	@Mock
	private HttpEntity entity;

	@Randomize("response")
	private String string;

	private boolean isStreamClosed;

	@Mock
	Logger log;

	@Before
	public void setUp()
	{
		randomizer.randomize(this);
		objectUnderTest = new SimpleHttpClient(httpClient);
		objectUnderTest.log = log;
	}

	@Test
	public void getFromExamplecom() throws ClientProtocolException, IOException
	{
		final String expectedUrl = "http://www." + domain + ".com";
		when(httpClient.createClient()).thenReturn(client);
		when(client.execute(Mockito.any(HttpUriRequest.class))).thenReturn(response);
		when(response.getEntity()).thenReturn(entity);
		final InputStream stream = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8)) {

			@Override
			public void close() throws IOException
			{
				isStreamClosed = true;
				super.close();
			}
		};
		when(entity.getContent()).thenReturn(stream);

		// test
		final String actualResponse = objectUnderTest.get(expectedUrl);

		// verify
		final InOrder inOrder = inOrder(client, response);
		inOrder.verify(client).execute(request.capture());
		inOrder.verify(response).close();
		inOrder.verify(client).close();
		assertTrue(isStreamClosed);

		assertThat(expectedUrl, equalTo(request.getValue().getURI().toString()));
		assertThat(actualResponse, equalTo(string));

		verify(log).info("GET " + expectedUrl);
		verify(log).info("response=" + actualResponse);
	}
}
