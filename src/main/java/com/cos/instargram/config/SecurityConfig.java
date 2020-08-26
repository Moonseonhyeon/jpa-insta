package com.cos.instargram.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.cos.instargram.config.oauth.PrincipalOAuth2UserService;
import com.cos.instargram.util.Script;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalOAuth2UserService principalOAuth2UserService;
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/", "/user/**", "/follow/**", "/image/**")
			.authenticated()
			.antMatchers("/admin/**")
			.access("hasRole('ROLE_ADMIN')")
			.anyRequest()
			.permitAll()
		.and()
			.formLogin()
			.loginPage("/auth/loginForm")
			.loginProcessingUrl("/auth/login")
			.defaultSuccessUrl("/")
			.failureHandler(new AuthenticationFailureHandler() {
				
				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					response.setContentType("text/html, charsset=utf-8");
					PrintWriter out = response.getWriter();
					out.print(Script.back("유저네임 혹은 비밀번호를 찾을 수 없습니다."));
					return;
			}
			})
		.and()
			.logout()
			.logoutUrl("/auth/logout")
			.logoutSuccessUrl("/auth/loginForm")
		.and()
			.oauth2Login() //페이스북이나 구글쪽으로 요청하는 주소(oauth요청주소)가 다 활성화됨.
			.userInfoEndpoint()//oauth 로그인 성공 이후 사용자정보를 가져오기 위한 설정을 담당하느데 
			.userService(principalOAuth2UserService); //담당할 서비스를 등록한다.(로그인 후 후처리 되는 곳) Authentication직전에
		
		
		
	}
	
}
