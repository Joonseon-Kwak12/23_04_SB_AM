package com.KoreaIT.kjs.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BeforActionInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
			throws Exception {
		
		System.out.println("==========실행됨??=============\n\\n\\n\\n\\n\\n==========실행됨??=============");
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
	
}
