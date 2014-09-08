package us.davidandersen.util.bean;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import us.davidandersen.util.bean.iterator.IterableUtils;
import com.google.common.primitives.Primitives;

public class BeanInspector
{
	private final Object bean;

	private final BeanMap beanMap;

	public BeanInspector(final Object bean)
	{
		this.bean = bean;
		beanMap = new BeanMap(bean);
	}

	public boolean hasProperty(final String propertyName)
	{
		return beanMap.containsKey(propertyName);
	}

	public Object getSimpleValue(final String propertyName)
	{
		return beanMap.get(propertyName);
	}

	public Object get(final String propertyName, final int index) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException
	{
		return BeanUtils.getIndexedProperty(bean, propertyName, index);
	}

	public boolean isIterable()
	{
		return IterableUtils.isObjectIterable(bean);
	}

	public Object getBean()
	{
		return bean;
	}

	public boolean isSimpleType()
	{
		final Class<? extends Object> class1 = bean.getClass();
		final boolean wrapperType = Primitives.isWrapperType(class1);
		return wrapperType || CharSequence.class.isAssignableFrom(class1);
	}

	public boolean isIndexedProperty()
	{
		return IterableUtils.isObjectIterable(bean);
	}
}
