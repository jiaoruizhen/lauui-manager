package com.dognessnetwork.ops.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dognessnetwork.ops.common.Constant;
import com.dognessnetwork.ops.utils.MD5;



@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthFailureHandler authFailureHandler;
	@Autowired
	private AuthSuccessHandler authSuccessHandler;
	@Autowired
	private UserService userService;

    
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new PasswordEncoder(){

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5.md5((String)rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5.md5((String)rawPassword));
            }});
    }

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest().permitAll()// 其他的路径都是登录后即可访问
				.and().formLogin().loginPage(Constant.DEFAULT_LOGIN_JSON_URL)
				
				.successHandler(authSuccessHandler)
				.failureHandler(authFailureHandler)
				.loginProcessingUrl(Constant.DEFAULT_LOGIN_URL).permitAll()
				.and().logout()
				.logoutSuccessUrl("/login.html").permitAll().and().csrf().disable();
			http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/public/**",
				"/js/**"
				);
	}
}