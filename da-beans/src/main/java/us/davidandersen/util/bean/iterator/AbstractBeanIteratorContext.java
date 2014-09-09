package us.davidandersen.util.bean.iterator;

abstract class AbstractBeanIteratorContext implements BeanIteratorContext
{
	@Override
	public void remove()
	{
		throw new UnsupportedOperationException("remove");
	}
}
