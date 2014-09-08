package us.davidandersen.util.bean.iterator;

import java.util.Iterator;
import us.davidandersen.util.bean.PropertyComparisonContext;

public interface BeanIteratorContext extends Iterator<PropertyComparisonContext>
{
	boolean isList();

	String getPropertyName();
}
