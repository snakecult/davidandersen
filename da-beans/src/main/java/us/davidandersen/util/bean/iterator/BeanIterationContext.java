package us.davidandersen.util.bean.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.davidandersen.util.bean.BeanInspector;
import us.davidandersen.util.bean.ObjectInfo;
import us.davidandersen.util.bean.ObjectInfoStatus;
import us.davidandersen.util.bean.PropertyComparisonContext;

public class BeanIterationContext extends AbstractBeanIteratorContext
{
	private static Logger log = LoggerFactory.getLogger(BeanIterationContext.class);

	private final BeanInspector actualInspector;

	private final BeanInspector expectedInspector;

	private final Iterator<String> propertyNameIterator;

	private String propertyName = "";

	public BeanIterationContext(final Object actual, final Object expected, final BeanExclusions exclusions,
			final String basePropertyName)
	{
		actualInspector = new BeanInspector(actual);
		expectedInspector = new BeanInspector(expected);
		final BeanMap map = new BeanMap(expected);
		final ArrayList<String> properties = new ArrayList<String>();
		log.info("checking exclusions base=" + basePropertyName);
		for (final Object name : map.keySet())
		{
			String fullname;
			if (basePropertyName.equals(""))
			{
				fullname = name.toString();
			}
			else
			{
				fullname = basePropertyName + "." + name;
			}
			if (!exclusions.isPropertyExcluded(fullname))
			{
				log.info("BeanIterationContext included " + name);
				properties.add(name.toString());
			}
			else
			{
				log.info("BeanIterationContext excluded " + name);
			}
		}
		propertyNameIterator = properties.iterator();
	}

	@Override
	public boolean hasNext()
	{
		final boolean hasNext = propertyNameIterator.hasNext();
		log.info("BeanIterationContext.hasNext() " + toString());
		return hasNext;
	}

	@Override
	public PropertyComparisonContext next()
	{
		// propertyNameIterator.
		propertyName = propertyNameIterator.next();
		log.info("BeanIterationContext.next() " + toString());

		return newComparisonContext(actualInspector, expectedInspector, propertyName);
	}

	@Override
	public boolean isList()
	{
		return false;
	}

	@Override
	public String getPropertyName()
	{
		return propertyName;
	}

	private PropertyComparisonContext newComparisonContext(final BeanInspector actualInspector,
			final BeanInspector expectedInspecor,
			final String propertyName)
	{
		final ObjectInfo workingObjectInfo = newObjectInfo(actualInspector, propertyName);
		final ObjectInfo baseObjectInfo = newObjectInfo(expectedInspecor, propertyName);

		return new PropertyComparisonContext(workingObjectInfo, baseObjectInfo);
	}

	private ObjectInfo newObjectInfo(final BeanInspector beanInspector, final String propertyName)
	{
		log.info("newprop=" + propertyName);

		if (beanInspector.hasProperty(propertyName))
		{
			final Object value = beanInspector.getSimpleValue(propertyName);

			if (value == null)
			{
				return new ObjectInfo(ObjectInfoStatus.NULL);
			}

			return new ObjectInfo(value);
		}

		return new ObjectInfo(ObjectInfoStatus.MISSING);
	}

	@Override
	public String toString()
	{
		return "BeanIterationContext[propertyName=" + propertyName + "]";
	}
}
