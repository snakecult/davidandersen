package us.davidandersen.json;

import java.lang.reflect.ParameterizedType;
import com.google.gson.Gson;

public abstract class AbstractGsonJsonReader<T> implements JsonReader<T>
{
	private final String json;

	public AbstractGsonJsonReader(final String json)
	{
		this.json = json;
	}

	@Override
	public T readObject()
	{
		final Gson gson = new Gson();

		return gson.fromJson(json, getJsonType());
	}

	private Class<T> getJsonType()
	{
		final ParameterizedType genericSuperClass = (ParameterizedType)getClass().getGenericSuperclass();
		@SuppressWarnings("unchecked")
		final Class<T> genericClass = (Class<T>)genericSuperClass.getActualTypeArguments()[0];

		return genericClass;
	}
}
