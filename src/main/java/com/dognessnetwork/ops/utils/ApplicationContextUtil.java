package com.dognessnetwork.ops.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext APPLICATION_CONTEXT;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		APPLICATION_CONTEXT = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return APPLICATION_CONTEXT;
	}
	
	public static Object getBean(String name) {
		return APPLICATION_CONTEXT.getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return APPLICATION_CONTEXT.getBean(clazz);
	}
}