package cloud.seckilled.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class SimpleApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(SimpleApplication.class, args);
	}

}
