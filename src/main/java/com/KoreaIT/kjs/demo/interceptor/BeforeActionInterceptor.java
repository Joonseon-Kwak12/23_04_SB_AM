package com.KoreaIT.kjs.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.KoreaIT.kjs.demo.vo.Rq;

@Component
public class BeforeActionInterceptor implements HandlerInterceptor{

	@Autowired
	private Rq rq;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
			throws Exception {

		rq.initOnbeforeActionInterceptor();
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
	
}
