package us.davidandersen.util.bean.compare;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.davidandersen.util.bean.iterator.BeanExclusions;
import us.davidandersen.util.bean.iterator.BeanIterator;

public class ReflectionMatcher extends TypeSafeMatcher<Object>
{
	private final Object expected;

	private static Logger log = LoggerFactory.getLogger(ReflectionMatcher.class);

	final BeanComparer beanComparer = new BeanComparer();

	private final ComparisonResult comparisonResult = new ComparisonResult();

	private final List<String> excludedProperties;

	public ReflectionMatcher(final Object expected)
	{
		this(expected, new ArrayList<String>());
	}

	public ReflectionMatcher(final Object expected, final String excludedProperty)
	{
		this.expected = expected;
		this.excludedProperties = new ArrayList<String>();
		if (excludedProperty != null)
		{
			withExcludedProperty(excludedProperty);
		}
	}

	public ReflectionMatcher(final Object expected, final List<String> excludedProperties)
	{
		this.expected = expected;
		this.excludedProperties = excludedProperties;
	}

	private void withExcludedProperty(final String excludedProperty)
	{
		this.excludedProperties.add(excludedProperty);
	}

	@Override
	protected boolean matchesSafely(final Object actual)
	{
		try
		{
			final BeanExclusions exclusions = new BeanExclusions();
			exclusions.setExcludedProperties(excludedProperties);
			final BeanIterator beanIterator = new BeanIterator(actual, expected, exclusions);
			// beanIterator.exclusions.setExcludedProperties(excludedProperties);
			if (!beanComparer.compareAllProperties(beanIterator, comparisonResult))
			{
				return false;
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public void describeTo(final Description description)
	{

		description.appendText("is ").appendText(describeObject(expected, comparisonResult));
	}

	@Override
	protected void describeMismatchSafely(final Object item, final Description mismatchDescription)
	{
		mismatchDescription.appendText("was ").appendText(describeObject(item, comparisonResult));
	}

	private String describeObject(final Object object, final ComparisonResult comparisonResult)
	{
		final PropertyUtilsBean pub = new PropertyUtilsBean();
		final String path = comparisonResult.getPropertyName();
		log.info("--------------------------");
		log.info("path=" + path);
		final String simpleName = object.getClass().getSimpleName();
		try
		{
			final Object value = pub.getProperty(object, path);
			return simpleName + "[" + path + "=" + value + "]";
		}
		catch (final Exception e)
		{
			return simpleName + " is missing property " + path;
		}
	}

	@Factory
	public static Matcher<Object> equalToByReflection(final Object expected)
	{
		return new ReflectionMatcher(expected);
	}

	@Factory
	public static Matcher<Object> equalToByReflection(final Object expected, final String excludedProperty)
	{
		return new ReflectionMatcher(expected, excludedProperty);
	}
}