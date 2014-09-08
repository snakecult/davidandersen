package us.davidandersen.util.bean;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import us.davidandersen.util.bean.iterator.BeanIterator;

public class BeanComparer
{
	boolean compareAllProperties(final BeanIterator beanIterator, final ComparisonResult result)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		while (beanIterator.hasNext())
		{
			final PropertyComparisonContext comparisonContext = beanIterator.next();
			final ObjectInfo workingObjectInfo = comparisonContext.getWorkingObjectInfo();
			final ObjectInfo baseObjectInfo = comparisonContext.getBaseObjectInfo();

			if (workingObjectInfo.isMissing())
			{
				// result.propertyName = comparisonContext.getPropertyName();
				result.propertyName = beanIterator.getPropertyName();
				return false;
			}

			final Object actualValue = workingObjectInfo.getObject();
			final Object expectedValue = baseObjectInfo.getObject();

			if (isLeaf(workingObjectInfo, baseObjectInfo) &&
					!comparePrimitive(actualValue, expectedValue))
			{
				result.propertyName = beanIterator.getPropertyName();
				// result.propertyName = comparisonContext.getPropertyName();
				// result.propertyName = beanIterator.getPropertyName();
				return false;
			}
		}

		return true;
	}

	private boolean isLeaf(final ObjectInfo actualStatus, final ObjectInfo expectedStatus)
			throws IntrospectionException
	{
		return actualStatus.isPrimitive() || expectedStatus.isPrimitive();
	}

	boolean comparePrimitive(final Object actual, final Object expected)
	{
		return Objects.equals(actual, expected);
	}

}
