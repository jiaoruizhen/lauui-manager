package com.dognessnetwork.ops.config.security;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dognessnetwork.ops.common.Constant;
import com.dognessnetwork.ops.dto.User;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthFailureHandler authFailureHandler;
	@Autowired
	private AuthSuccessHandler authSuccessHandler;
	@Autowired
	private UserService userService;

    
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage(Constant.DEFAULT_LOGIN_JSON_URL).permitAll()
			.loginProcessingUrl(Constant.DEFAULT_LOGIN_URL).permitAll()
			.successHandler(authSuccessHandler)
			.failureHandler(authFailureHandler)
			.and()
			.authorizeRequests()
			.accessDecisionManager(accessDecisionManager())
            .withObjectPostProcessor(getObjectPostProcessor())
			.anyRequest().authenticated()
			.and()
			.logout()
			.logoutSuccessUrl(Constant.DEFAULT_LOGIN_JSON_URL).permitAll().and().csrf().disable();
		
		http.headers().frameOptions().sameOrigin();
	}
	
	private ObjectPostProcessor<FilterSecurityInterceptor> getObjectPostProcessor(){
		return new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(
                O fsi) {
                fsi.setSecurityMetadataSource(new DynamicSecurityMetadataSource(fsi.getSecurityMetadataSource()));
                return fsi;
            }
        };
	}
	
    private AccessDecisionManager accessDecisionManager() {
		RoleVoter roleVoter = new RoleVoter();
		roleVoter.setRolePrefix("");
		
        return new UnanimousBased(Arrays.asList(
	            roleVoter,
	            new WebExpressionVoter(),
	            new AuthenticatedVoter()));
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/public/**",
				"/js/**",
				"/css/**",
				"/fonts/**",
				"/images/**",
				"/lib/**"
				);
	}
	
	@RestController
	public static class WebSecurityController {
		@GetMapping("/getLoginUser")
		public Object getLoginUser() throws Exception{
			User user  = SecurityUtils.getUser();
			return user;
		}
	}
}