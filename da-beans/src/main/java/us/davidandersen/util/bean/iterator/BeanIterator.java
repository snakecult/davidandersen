package us.davidandersen.util.bean.iterator;

import java.beans.IntrospectionException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanIterator implements Iterator<PropertyComparisonContext>
{
	private static Logger log = LoggerFactory.getLogger(BeanIterator.class);

	private final BeanIteratorContextStack context = new BeanIteratorContextStack();

	private BeanExclusions exclusions = new BeanExclusions();

	public BeanIterator(final Object actual, final Object expected) throws IntrospectionException
	{
		this(actual, expected, new BeanExclusions());
	}

	public BeanIterator(final Object actual, final Object expected, final BeanExclusions exclusions)
			throws IntrospectionException
	{
		context.push(new BeanIterationContext(actual, expected, exclusions, ""));
		this.exclusions = exclusions;
	}

	@Override
	public boolean hasNext()
	{
		log.info("BeanIterator.hasNext()");
		while (!context.hasNext() && !context.isEmpty())
		{
			context.pop();
		}

		final boolean hasNext = context.hasNext();
		log.info("hasnext=" + hasNext + ", prop=" + getPropertyName());
		return hasNext;
	}

	@Override
	public PropertyComparisonContext next()
	{
		log.info("BeanIterator.next()");
		if (!hasNext())
		{
			throw new NoSuchElementException();
		}

		final PropertyComparisonContext propertyContext = context.next();

		if (propertyContext.isList())
		{
			final Object actual = propertyContext.getWorkingObjectInfo().getObject();
			final Object expected = propertyContext.getBaseObjectInfo().getObject();
			push(new ListIterationContext(actual, expected));

			final PropertyComparisonContext x = context.next();
			final Object a1 = x.getWorkingObjectInfo().getObject();
			final Object e1 = x.getBaseObjectInfo().getObject();
			final String propertyNameWithoutIndex = context.getPropertyNameWithoutIndex();
			push(new BeanIterationContext(a1, e1, exclusions, propertyNameWithoutIndex));

			return next();
		}
		else if (!propertyContext.isSimpleType())
		{
			final String propertyNameNoIndex = context.getPropertyNameWithoutIndex();
			final String propertyName = context.getPropertyName();
			propertyContext.setPropertyName(propertyName);
			final Object actual = propertyContext.getWorkingObjectInfo().getObject();
			final Object expected = propertyContext.getBaseObjectInfo().getObject();
			push(new BeanIterationContext(actual, expected, exclusions, propertyNameNoIndex));

			return propertyContext;
		}

		log.info("Returned next=" + getPropertyName());
		return propertyContext;
	}

	public String getPropertyName()
	{
		return context.getPropertyName();
	}

	public void push(final BeanIteratorContext beanIterationContext)
	{
		context.push(beanIterationContext);
	}

	public void pop()
	{
		context.pop();
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException("remove");
	}
}
