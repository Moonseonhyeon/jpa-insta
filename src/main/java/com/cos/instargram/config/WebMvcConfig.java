package com.cos.instargram.config;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.cos.instargram.config.auth.CosAnnotation;
import com.cos.instargram.config.auth.LoginUserAnnotation;
import com.cos.instargram.config.auth.dto.LoginUser;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer{ //-> web.xml

	private final HttpSession httpSession;	
	
	// 이미지 경로 찾기를 위해 추가 시작
		@Value("${file.path}")
		private String uploadFolder;

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);

			registry.addResourceHandler("/upload/**").addResourceLocations("file:///" + uploadFolder).setCachePeriod(3600)
					.resourceChain(true).addResolver(new PathResourceResolver());
		}
		// 이미지 경로 찾기를 위해 추가 끝
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) { //-> 요청한 주소에 어떤 Argument proceedjoin
		resolvers.add(new HandlerMethodArgumentResolver() {
			
			// 1. supportsParameter() 에서 true가 리턴되면!!
			@Override
			public boolean supportsParameter(MethodParameter parameter) {
				boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUserAnnotation.class) != null;
				boolean isUserClass = LoginUser.class.equals(parameter.getParameterType());
				return isLoginUserAnnotation && isUserClass;
			}
			
			// addArgumentResolvers return 값이 true이면 resolveArgument 호출된다.
			// 2. 세션을 만들어서 @LoginUserAnnotation에 주입해준다. 
			@Override
			public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
				
				return httpSession.getAttribute("loginUser");
			}
		});
		
		resolvers.add(new HandlerMethodArgumentResolver() {
			
			@Override
			public boolean supportsParameter(MethodParameter parameter) {				
				boolean isCosAnnotation = parameter.getParameterAnnotation(CosAnnotation.class) != null;
				//return isCosAnnotation;
				return false;
			}
			
			@Override
			public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
				
				return "cos";
			}
		});
	}
}
		

	
	

