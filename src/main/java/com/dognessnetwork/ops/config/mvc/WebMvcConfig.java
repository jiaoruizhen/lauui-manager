package com.dognessnetwork.ops.config.mvc;

import java.nio.charset.Charset;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
 public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
	public	void addResourceHandlers(ResourceHandlerRegistry registry) {
	      registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
	
	  }
    
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }
  
}