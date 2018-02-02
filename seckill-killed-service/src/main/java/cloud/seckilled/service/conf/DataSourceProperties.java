package cloud.seckilled.service.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = DataSourceProperties.DS, ignoreUnknownFields = false)
public class DataSourceProperties {

	//对应配置文件里的配置键
	public final static String DS="mysqldb.datasource";	
	private String driverClassName ="com.mysql.jdbc.Driver";
	
	private String jdbcUrl; 
	private String user; 
	private String password;
	
	private int maxPoolSize=30;
	private int minPoolSie=10;
	private boolean autoCommitOnClose =false;
	private int checkoutTimeout =10000;
	private int  acquireRetryAttempts =3;
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMaxPoolSize() {
		return maxPoolSize;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
	public int getMinPoolSie() {
		return minPoolSie;
	}
	public void setMinPoolSie(int minPoolSie) {
		this.minPoolSie = minPoolSie;
	}
	public boolean isAutoCommitOnClose() {
		return autoCommitOnClose;
	}
	public void setAutoCommitOnClose(boolean autoCommitOnClose) {
		this.autoCommitOnClose = autoCommitOnClose;
	}
	public int getCheckoutTimeout() {
		return checkoutTimeout;
	}
	public void setCheckoutTimeout(int checkoutTimeout) {
		this.checkoutTimeout = checkoutTimeout;
	}
	public int getAcquireRetryAttempts() {
		return acquireRetryAttempts;
	}
	public void setAcquireRetryAttempts(int acquireRetryAttempts) {
		this.acquireRetryAttempts = acquireRetryAttempts;
	}
	public static String getDs() {
		return DS;
	}
	
	
}