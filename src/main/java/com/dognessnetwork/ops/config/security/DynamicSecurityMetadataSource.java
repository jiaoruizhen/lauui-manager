package com.dognessnetwork.ops.config.security;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dognessnetwork.ops.domain.Resource;
import com.dognessnetwork.ops.domain.Resource.type;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.repository.ResourceRepository;
import com.dognessnetwork.ops.utils.ApplicationContextUtil;

public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private final FilterInvocationSecurityMetadataSource superMetadataSource;
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();
	private static Map<String, String> urlRoleMap;

	public DynamicSecurityMetadataSource(
			FilterInvocationSecurityMetadataSource expressionBasedFilterInvocationSecurityMetadataSource) {
		this.superMetadataSource = expressionBasedFilterInvocationSecurityMetadataSource;
		LoadUrlRoleMap();
	}

	public static void LoadUrlRoleMap() {
		ResourceRepository resourceRepository = ApplicationContextUtil.getBean(ResourceRepository.class);
		urlRoleMap = resourceRepository.findAll()
									   .stream()
									   .filter(e -> e.getType() != type.menu)
									   .collect(Collectors.toMap(Resource::getUrl, Resource::getName));
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		FilterInvocation fi = (FilterInvocation) object;
		String url = fi.getRequestUrl();

		for (Map.Entry<String, String> entry : urlRoleMap.entrySet()) {
			if (antPathMatcher.match(entry.getKey(), url)) {
				return SecurityConfig.createList(entry.getValue());
			}
		}

		return superMetadataSource.getAttributes(object);// 返回代码定义的默认配置
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@RestController
	public static class SecurityController {
		@RequestMapping("/loadUrlRoleMap")
		public Response loadUrlRoleMap() {
			DynamicSecurityMetadataSource.LoadUrlRoleMap();
			return Response.SUCCESS();
		}
	}
}