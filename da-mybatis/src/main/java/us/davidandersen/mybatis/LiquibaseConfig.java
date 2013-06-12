package us.davidandersen.mybatis;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;

/**
 * @Configuration
 * @ComponentScan("mooc.dao")
 * @MapperScan("net.learnsy.mapper")
 * 
 * @author dandersen
 * 
 */
public class LiquibaseConfig
{
	@Bean
	public SpringLiquibase getSpringLiquibase(final DataSource dataSource)
	{
		final SpringLiquibase springLiquibase = new SpringLiquibase();
		springLiquibase.setDataSource(dataSource);
		springLiquibase.setChangeLog("classpath:db-changelog.xml");

		return springLiquibase;
	}
}
