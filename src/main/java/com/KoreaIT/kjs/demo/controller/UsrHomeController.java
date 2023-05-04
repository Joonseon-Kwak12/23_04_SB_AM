package com.KoreaIT.kjs.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KoreaIT.kjs.demo.vo.Rq;

@Controller
public class UsrHomeController {
	
	private Rq rq;
	
	public UsrHomeController(Rq rq) {
		this.rq = rq;
	}//무조건 만드는 게 아니라 필요할 때만 생성하는 방식으로 변경

	@RequestMapping("/usr/home/main")
	public String showMain() {
		
		rq.run();
		return "usr/home/main";
	}
	
	@RequestMapping("/")
	public String showRoot() {
		
		return "redirect:/usr/home/main";
	}
}