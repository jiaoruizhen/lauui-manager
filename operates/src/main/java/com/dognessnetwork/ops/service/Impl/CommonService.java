package com.dognessnetwork.ops.service.Impl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dognessnetwork.ops.utils.StringToolsUtil;

public class CommonService {
	public static <T, R extends JpaRepository<T, Long>> Page<T> getPageContent(Integer page, Integer limit, T obj,
			R repository) {
		try {
			obj = StringToolsUtil.setNullValue(obj);
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
			e.printStackTrace();
		}

		ExampleMatcher matcher = ExampleMatcher.matching();
		Example<T> example = Example.of(obj, matcher);
		Page<T> content = repository.findAll(example, PageRequest.of(--page, limit));
		return content;
	}
}
