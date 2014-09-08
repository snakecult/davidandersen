package us.davidandersen.util.bean;

public class PropertyComparisonContext
{
	private final ObjectInfo workingProperty;

	private final ObjectInfo baseProperty;

	private String propertyName;

	public PropertyComparisonContext(final ObjectInfo workingProperty, final ObjectInfo baseProperty)
	{
		this.workingProperty = workingProperty;
		this.baseProperty = baseProperty;
	}

	public ObjectInfo getWorkingObjectInfo()
	{
		return workingProperty;
	}

	public ObjectInfo getBaseObjectInfo()
	{
		return baseProperty;
	}

	public boolean isList()
	{
		return baseProperty.isList();
	}

	public boolean isSimpleType()
	{
		return baseProperty.isPrimitive();
	}

	public String getPropertyName()
	{
		return propertyName;
	}

	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}
}
