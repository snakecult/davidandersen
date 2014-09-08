package us.davidandersen.util.bean.iterator;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanExclusions
{
	private static Logger log = LoggerFactory.getLogger(BeanIterationContext.class);

	private List<String> excludedProperties = new ArrayList<String>();

	boolean isPropertyExcluded(final String propertyName)
	{
		if (propertyName.endsWith("class"))
		{
			log.info(propertyName + " was excluded");
			return true;
		}

		for (final String excludedProperty : excludedProperties)
		{
			if (propertyName.equals(excludedProperty))
			{
				log.info(propertyName + " was excluded");
				return true;
			}
		}

		log.info(propertyName + " was not excluded");

		return false;
	}

	public void addExcludedProperty(final String excludedProperty)
	{
		excludedProperties.add(excludedProperty);
	}

	public void setExcludedProperties(final List<String> excludedProperties)
	{
		this.excludedProperties = excludedProperties;
	}
}
