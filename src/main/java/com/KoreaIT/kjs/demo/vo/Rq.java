package com.KoreaIT.kjs.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.KoreaIT.kjs.demo.util.Ut;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {

	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	
	public Rq(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		
		this.session = req.getSession();
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			
			this.req.setAttribute("rq", this);
		}
		
		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
	}

	public void printHistoryBackJS(String msg) throws IOException {
		resp.setContentType("text/html; charset=UTF-8"); // 한글 인식
		println("<script>");
		if (!Ut.empty(msg)) {
			print("alert('" + msg + "');");
		}
		println("history.back();");
		println("</script>");
	}

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void println(String str) {
		
		print(str + "\n");
	}
	
	public void login(Member member) {
		//isLogined 안 바꿔줘도 되나??
		session.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {
		//isLogined 안 바꿔줘도 되나??
		session.removeAttribute("loginedMemberId");
	}

	public String jsHistoryBackOnView(String msg) {
		
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		
		return "usr/common/js";
	}
	
	public String jsHistoryBack(String resultCode, String msg) {
		
		return Ut.jsHistoryBack(resultCode, msg);
	}

	public String jsReplace(String msg, String uri) {
		
		return Ut.jsReplace(msg, uri);
	}

	public void initOnbeforeActionInterceptor() {
		
	}

}
