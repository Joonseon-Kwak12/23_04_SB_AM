package com.KoreaIT.kjs.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrHomeController {
	private int count = 0;
	
	public UsrHomeController() {
		this.count = 0;
	}
	
	@RequestMapping("/usr/home/main4")
	@ResponseBody
	public String showMain4() {
		return "count: " + count++;
	}
	
	@RequestMapping("/usr/home/main4_0")
	@ResponseBody
	public String showMain4_0() {
		count = 0;
		return "/usr/home/main4의 count가 0으로 설정되었습니다.";
	}
}
