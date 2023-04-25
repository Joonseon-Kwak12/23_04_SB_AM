package com.KoreaIT.kjs.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.KoreaIT.kjs.demo.interceptor.BeforeActionInterceptor;
import com.KoreaIT.kjs.demo.interceptor.NeedLoginInterceptor;
import com.KoreaIT.kjs.demo.interceptor.NotNeedLoginInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
	//BeforeActionInterceptor 불러오기
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;
	//NeedLoginInterceptor 불러오기
	@Autowired
	NeedLoginInterceptor needLoginInterceptor;
	@Autowired
	NotNeedLoginInterceptor notNeedLoginInterceptor;
	
	//인터셉터 적용
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**")
			.excludePathPatterns("/error");
		//모든 것이 이 인터셉터 거쳐가는데 resource랑 error만 제외
		
		registry.addInterceptor(needLoginInterceptor)
			.addPathPatterns("/usr/article/write").addPathPatterns("/usr/article/doWrite")
			.addPathPatterns("/usr/article/modify").addPathPatterns("/usr/article/doModify")
			.addPathPatterns("/usr/article/delete").addPathPatterns("/usr/article/doDelete");
		//로그인 필요한 것만 거쳐가는 인터셉터
		
		registry.addInterceptor(notNeedLoginInterceptor)
			.addPathPatterns("/usr/member/login");
		//로그인 중에는 안 되는 것만 거쳐가는 인터셉터
	}

}