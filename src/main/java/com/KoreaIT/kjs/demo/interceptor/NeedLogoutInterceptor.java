package com.KoreaIT.kjs.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.KoreaIT.kjs.demo.vo.Rq;

@Component
public class NeedLogoutInterceptor implements HandlerInterceptor{
	
	@Autowired
	Rq rq;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
			throws Exception {
		
		if (rq.isLogined()) {
			//resp.getWriter().append("<script> ~~~")
			
			rq.printHistoryBackJS("로그인 중에는 불가능합니다.");
			// resp.sendRedirect("/usr/member/doLogin");
			return false;
		}
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
	
}
