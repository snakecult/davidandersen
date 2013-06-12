package us.davidandersen.mybatis;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @Configuration
 * @ComponentScan("mooc.dao")
 * @MapperScan("net.learnsy.mapper")
 * 
 * @author dandersen
 * 
 */
public class MybatisConfig
{
	@Bean
	public SqlSessionFactory getSqlSessionFactoryBean(final DataSource dataSource) throws Exception
	{
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		final Resource configLocation = new ClassPathResource("mybatis-config.xml");
		sqlSessionFactoryBean.setConfigLocation(configLocation);

		return sqlSessionFactoryBean.getObject();
	}
}
