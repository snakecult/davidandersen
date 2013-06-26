package us.davidandersen.mybatis;

import javax.naming.NamingException;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * @Configuration
 * @ComponentScan("mooc.dao")
 * @MapperScan("net.learnsy.mapper")
 * 
 * @author dandersen
 * 
 */
public abstract class JndiDataSourceConfig
{
	/**
	 * e.g. "java:/comp/env/jdbc/dsname"
	 * 
	 * @return
	 */
	public abstract String getJndiName();

	@Bean
	public DataSource dataSource() throws IllegalArgumentException, NamingException
	{
		final JndiObjectFactoryBean factory = new JndiObjectFactoryBean();
		factory.setJndiName(getJndiName());
		// factory.setJndiName("jdbc/mooc");
		// factory.setResourceRef(true);
		// factory.setCache(true);
		factory.setProxyInterface(DataSource.class);
		factory.setExpectedType(DataSource.class);
		// factory.setLookupOnStartup(false);
		factory.afterPropertiesSet();
		final DataSource object = (DataSource)factory.getObject();
		return object;
	}

	@Bean
	public DataSourceTransactionManager getTransactionManager(final DataSource dataSource)
	{
		final DataSourceTransactionManager tx = new DataSourceTransactionManager();
		tx.setDataSource(dataSource);

		return tx;
	}
}
