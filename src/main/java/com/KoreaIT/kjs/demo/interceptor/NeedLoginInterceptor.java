package com.KoreaIT.kjs.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.KoreaIT.kjs.demo.util.Ut;
import com.KoreaIT.kjs.demo.vo.Rq;

@Component
public class NeedLoginInterceptor implements HandlerInterceptor{
	
	@Autowired
	private Rq rq;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
			throws Exception {
		
		if (!rq.isLogined()) {
			//resp.getWriter().append("<script> ~~~")
			
			// resp.sendRedirect("/usr/member/doLogin");
			// rq.printHistoryBackJS("로그인 후 이용해주세요");
			String afterLoginUri = rq.getEncodedCurrentUri();
			rq.jsPrintReplace("로그인 후 이용해주세요", "/usr/member/login?afterLoginUri=" + afterLoginUri);
			return false;
		}
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
	
}
