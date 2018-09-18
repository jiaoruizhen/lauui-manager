package com.dognessnetwork.ops;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.dognessnetwork.ops.config.OverallFilter;


@SpringBootApplication
@EnableJpaAuditing
public class OperatesApplication extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OperatesApplication.class);
    }
	public static void main(String[] args) {
		SpringApplication.run(OperatesApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean<Filter> addFilterRegistration() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(new OverallFilter());
		registration.addUrlPatterns("/*");
		registration.setName("OverallFilter");
		registration.setOrder(-100);
		return registration;
	}
}
