package us.davidandersen.util.bean.iterator;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.davidandersen.util.bean.compare.ReflectionMatcher;

public class BeanIteratorContextStack
{
	private final List<BeanIteratorContext> propertyStack = new ArrayList<BeanIteratorContext>();

	private static Logger log = LoggerFactory.getLogger(ReflectionMatcher.class);

	BeanIteratorContextStack()
	{
	}

	BeanIteratorContext top()
	{
		return propertyStack.get(propertyStack.size() - 1);
	}

	void pop()
	{
		final BeanIteratorContext top = top();
		log.info("Popped " + top.toString());
		propertyStack.remove(top);
	}

	public void push(final BeanIteratorContext objectContext)
	{
		log.info("Pushed " + objectContext.toString());
		propertyStack.add(objectContext);
	}

	boolean isEmpty()
	{
		return propertyStack.size() == 0;
	}

	boolean hasNext()
	{
		if (isEmpty())
		{
			return false;
		}

		final BeanIteratorContext top = top();
		final boolean hasNext = top.hasNext();
		log.info("Top.hasNext=" + hasNext + ", context=" + top.toString());
		return hasNext;
	}

	PropertyComparisonContext next()
	{
		final BeanIteratorContext top = top();
		final PropertyComparisonContext next = top.next();
		log.info("Top.next=" + top.toString());
		return next;
	}

	String getPropertyName()
	{
		final StringBuilder sb = new StringBuilder();
		final int size = propertyStack.size();

		for (int i = 0; i < size; i++)
		{
			final BeanIteratorContext ps = propertyStack.get(i);
			final String propertyName = ps.getPropertyName();
			if (propertyName.equals(""))
			{
				continue;
			}
			if (i > 0 && !ps.isList())
			{
				sb.append(".");
			}

			sb.append(propertyName);
		}

		return sb.toString();
	}

	String getPropertyNameWithoutIndex()
	{
		final StringBuilder sb = new StringBuilder();
		final int size = propertyStack.size();

		for (int i = 0; i < size; i++)
		{
			final BeanIteratorContext ps = propertyStack.get(i);
			if (ps.isList())
			{
				continue;
			}
			final String propertyName = ps.getPropertyName();
			if (propertyName.equals(""))
			{
				continue;
			}
			if (i > 0 && !ps.isList())
			{
				sb.append(".");
			}

			sb.append(propertyName);
		}

		return sb.toString();
	}
}
