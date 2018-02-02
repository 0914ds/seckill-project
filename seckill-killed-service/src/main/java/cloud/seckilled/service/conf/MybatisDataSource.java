package cloud.seckilled.service.conf;

import java.beans.PropertyVetoException;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import cloud.seckilled.service.dao.RedisDao;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
//mybaits dao 搜索路径
@MapperScan("cloud.seckilled.service.dao")
public class MybatisDataSource {
	@Autowired
	private DataSourceProperties dataSourceProperties;
	//mybaits mapper xml搜索路径
	private final static String mapperLocations="classpath:cloud/seckilled/service/dao/*.xml"; 

	private com.mchange.v2.c3p0.ComboPooledDataSource pool;
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {		
		DataSourceProperties config = dataSourceProperties;
		this.pool = new com.mchange.v2.c3p0.ComboPooledDataSource();
		try {
			this.pool.setDriverClass(config.getDriverClassName());
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.pool.setJdbcUrl(config.getJdbcUrl());
		if (config.getUser()!= null) {
			this.pool.setUser(config.getUser());
		}
		if (config.getPassword() != null) {
			this.pool.setPassword(config.getPassword());
		}
		this.pool.setMaxPoolSize(config.getMaxPoolSize());
		this.pool.setMinPoolSize(config.getMinPoolSie());
		this.pool.setAutoCommitOnClose(config.isAutoCommitOnClose());
		this.pool.setCheckoutTimeout(config.getCheckoutTimeout());
		this.pool.setAcquireRetryAttempts(config.getAcquireRetryAttempts());
		return this.pool;
	}

	@PreDestroy
	public void close() {
		if (this.pool != null) {
			this.pool.close();
		}
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {		
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());		
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));
		sqlSessionFactoryBean.setTypeAliasesPackage("cloud.seckilled.service.model");
		UrlResource urlresource = new UrlResource("classpath:mybatis-config.xml");
		sqlSessionFactoryBean.setConfigLocation(urlresource);
		 return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public RedisDao redisDao() {
      return  new RedisDao("localhost",6379);
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
	
}
