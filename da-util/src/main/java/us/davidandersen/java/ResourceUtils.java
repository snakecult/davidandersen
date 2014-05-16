package us.davidandersen.java;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.io.FileUtils;

public class ResourceUtils
{
	public static String readFile(final String name) throws IOException, URISyntaxException
	{
		final URL url = ResourceUtils.class.getResource(name);
		final String json = FileUtils.readFileToString(new File(url.toURI()));

		return json;
	}
}
