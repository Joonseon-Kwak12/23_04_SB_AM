package com.KoreaIT.kjs.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kjs.demo.vo.Rq;

@Controller
public class UsrAjaxTestController {

	@RequestMapping("usr/home/plus")
	String showTestPage() {
		return "usr/home/AjaxTest";
	}
	
	@RequestMapping("usr/home/doPlus")
	@ResponseBody
	String doPlus() {
		return "usr/home/AjaxTest";
	}
}