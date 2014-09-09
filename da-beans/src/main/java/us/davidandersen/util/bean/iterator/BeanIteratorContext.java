package us.davidandersen.util.bean.iterator;

import java.util.Iterator;

public interface BeanIteratorContext extends Iterator<PropertyComparisonContext>
{
	boolean isList();

	String getPropertyName();
}
