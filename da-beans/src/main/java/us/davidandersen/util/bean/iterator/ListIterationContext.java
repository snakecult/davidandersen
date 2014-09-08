package us.davidandersen.util.bean.iterator;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.davidandersen.util.bean.ObjectInfo;
import us.davidandersen.util.bean.PropertyComparisonContext;

public class ListIterationContext extends AbstractBeanIteratorContext
{
	private static Logger log = LoggerFactory.getLogger(ListIterationContext.class);

	private final List<?> actual;

	private final List<?> expected;

	private int index = -1;

	public ListIterationContext(final Object actual, final Object expected)
	{
		this.actual = (List<?>)actual;
		this.expected = (List<?>)expected;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(final int index)
	{
		this.index = index;
	}

	@Override
	public boolean hasNext()
	{
		final boolean b = index < (expected.size() - 1);
		log.info("ListIterationContext.hasNext() " + toString());
		return b;
	}

	@Override
	public PropertyComparisonContext next()
	{
		index++;
		final Object workingObject = actual.get(index);
		final Object baseObject = expected.get(index);
		final PropertyComparisonContext beanComparisonContext = new PropertyComparisonContext(new ObjectInfo(
				workingObject), new ObjectInfo(baseObject));
		log.info("ListIterationContext.next() " + toString());

		return beanComparisonContext;
	}

	@Override
	public String getPropertyName()
	{
		return "[" + getIndex() + "]";
	}

	@Override
	public boolean isList()
	{
		return true;
	}

	@Override
	public String toString()
	{
		return "ListIterationContext[index=" + index + "]";
	}
}
