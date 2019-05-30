package com.bfd.api;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.bdos.commons.utils.BeanUtil;
import com.bdos.jupiter.oauth.web.filter.RequestFilter;
import com.bfd.bdos.common.config.spring.SpringCommonPropertyPlaceholderConfigurer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages={"com.bfd.bdos.*","com.bdos.jupiter.*","com.bfd.api.*"})
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
//		RedisUtil.get("bfd_api");//启动初始化redis
//		ServiceUtil.writeServiceInfo2ZK();
	}

    @Bean(name = "beanUtil")
    public BeanUtil getBean() {
        return new BeanUtil();
    }

	@Bean(name="springCommonPropertyPlaceholderConfigurer")
	public SpringCommonPropertyPlaceholderConfigurer getConfig(){
		boolean ignoreResourceNotFound = true;
		String[] customizeLocations = {"bdos-config-common/bdos_common_config","bdos-config-global/bdos_global_config"};
		SpringCommonPropertyPlaceholderConfigurer commons = new SpringCommonPropertyPlaceholderConfigurer();
		commons.setCustomizeLocations(customizeLocations);
		commons.setIgnoreResourceNotFound(ignoreResourceNotFound);
		return commons;
	}
	
	@Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestFilter());
        registration.addUrlPatterns("/*");
        registration.setName("requestFilter");
        return registration;
    }
	  
}
