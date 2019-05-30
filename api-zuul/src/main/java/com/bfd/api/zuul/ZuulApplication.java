package com.bfd.api.zuul;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.bfd.api.zuul.filter.ApiErrorFilter;
import com.bfd.api.zuul.filter.ApiFilter;
import com.bfd.api.zuul.filter.ApiPostFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableFeignClients
public class ZuulApplication {
	public static void main(String[] args) {
		String LOG4J_HOME = "LOG4J_HOME";
		String log4jhome = System.getenv(LOG4J_HOME);
		if (StringUtils.isNotBlank(log4jhome)) {
			System.setProperty(LOG4J_HOME, log4jhome);
		} else {
			System.setProperty(LOG4J_HOME, ".");
		}
		SpringApplication.run(ZuulApplication.class, args);
	}

	@Bean
	public ApiFilter apiFilter() {
		return new ApiFilter();
	}

	@Bean
	public ApiPostFilter apiPostFilter() {
		return new ApiPostFilter();
	}

	@Bean
	public ApiErrorFilter apiErrorFilter() {
		return new ApiErrorFilter();
	}
}
