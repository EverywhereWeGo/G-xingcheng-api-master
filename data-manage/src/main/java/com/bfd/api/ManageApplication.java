package com.bfd.api;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableEurekaClient
public class ManageApplication  {
	public static void main(String[] args) {
		String LOG4J_HOME = "LOG4J_HOME";
		String log4jhome = System.getenv(LOG4J_HOME);
		if(StringUtils.isNotBlank(log4jhome)){
			System.setProperty(LOG4J_HOME,log4jhome);
		}else{
			System.setProperty(LOG4J_HOME,".");
		}
		SpringApplication.run(ManageApplication.class, args);
	}
}
