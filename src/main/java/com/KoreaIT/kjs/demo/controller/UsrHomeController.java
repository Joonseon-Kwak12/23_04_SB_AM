package com.KoreaIT.kjs.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrHomeController {
	private int count = 0;
	
	public UsrHomeController(int count) {
		this.count = 0;
	}

	@RequestMapping("/usr/home/main")
	@ResponseBody
	public String showMain() {
		return "안녕하세요";
	}
	
	@RequestMapping("/usr/home/main2")
	@ResponseBody
	public String showMain2() {
		return "잘가";
	}
	
	@RequestMapping("/usr/home/main3")
	@ResponseBody
	public int showMain3() {
		int a = 1;
		int b = 2;
		return a + b;
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
