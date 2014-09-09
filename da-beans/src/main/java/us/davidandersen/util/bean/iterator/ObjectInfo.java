package us.davidandersen.util.bean.iterator;

import us.davidandersen.util.bean.BeanInspector;

public class ObjectInfo
{
	private final Object object;

	private final ObjectInfoStatus status;

	public ObjectInfo(final Object object)
	{
		this.object = object;
		status = ObjectInfoStatus.EXISTS;
	}

	public ObjectInfo(final ObjectInfoStatus status)
	{
		this.status = status;
		object = null;
	}

	public Object getObject()
	{
		return object;
	}

	public ObjectInfoStatus getStatus()
	{
		return status;
	}

	public boolean isMissing()
	{
		return status == ObjectInfoStatus.MISSING;
	}

	public boolean isPrimitive()
	{
		if (object == null)
		{
			return true;
		}

		if (isList())
		{
			return false;
		}

		return getBeanInspector().isSimpleType();
	}

	public boolean isList()
	{
		return IterableUtils.isObjectIterable(object);
	}

	private BeanInspector getBeanInspector()
	{
		if (object == null)
		{
			return null;
		}
		return new BeanInspector(object);
	}
}
