package com.KoreaIT.kjs.demo.vo;

import java.io.IOException;
import java.util.Map;

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
	@Getter
	private Member loginedMember;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	
	private Map<String, String> paramMap;
	
	public Rq(HttpServletRequest req, HttpServletResponse resp) {
	//public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;
		
		this.session = req.getSession();
		
		paramMap = Ut.getParamMap(req);
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		Member loginedMember = null;
		
		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = (Member) session.getAttribute("loginedMember");
			// loginedMember = memberService.getMemberById(loginedMemberId);
			// session에 loginedMember를 저장하는 게 나은가 아니면 memberService를 끌고 오는 게 나은가?
			
			this.req.setAttribute("rq", this);
		}
		
		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;
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
		session.setAttribute("loginedMember", member);
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
	
	public void jsPrintReplace(String msg, String replaceUri) {
		
		resp.setContentType("text/html; charset=UTF-8");
		
		print(Ut.jsReplace(msg, replaceUri));
	}
	
	public String getCurrentUri() {
		
		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();
		
		System.out.println(currentUri);
		System.out.println(queryString);
		
		if (queryString != null && queryString.length() >0) {
			currentUri += "?" + queryString;
		}
		
		System.out.println(currentUri);
		return currentUri;
	}
	
	public String getEncodedCurrentUri() {
		
		return Ut.getEncodedCurrentUri(getCurrentUri());
	}

	// Rq 객체 생성 유도
	// 삭제 x, BeforeActionInterceptor에서 강제 호출
	public void initOnbeforeActionInterceptor() {
		
	}

	public void run() {
		System.out.println("===========================run A");
	}
	
	public String getLoginUri() {
		return "../member/login?afterLoginUri=" + getAfterLoginUri();
	}

	private String getAfterLoginUri() {
		// 로그인 후 접근 불가 페이지
		
		String requestUri = req.getRequestURI();
		
		switch (requestUri) {
		case "/usr/member/login":
		case "/usr/member/join":
			return Ut.getEncodedUri(paramMap.get("afterLoginUri"));
		}
		
		return getEncodedCurrentUri();
	}

}
