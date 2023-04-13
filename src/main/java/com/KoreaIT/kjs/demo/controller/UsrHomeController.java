package com.KoreaIT.kjs.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
public class UsrHomeController {
	private int count = 0;
	
	public UsrHomeController() {
		this.count = 0;
	}
	
	@RequestMapping("/usr/home/getCount")
	@ResponseBody
	public String getCount() {
		return "count: " + count++;
	}
	
	@RequestMapping("/usr/home/setCount")
	@ResponseBody
	public String setCount(int count) {
		this.count = count;
		return "count의 값이 " + this.count + 	"으로 설정되었습니다.";
	}
	
	@RequestMapping("/usr/home/getInt")
	@ResponseBody
	public int getInt() {
		return 12454315;
	}
	
	@RequestMapping("/usr/home/getChar")
	@ResponseBody
	public char getChar() {
		return 65;
	}
	
	@RequestMapping("/usr/home/getString")
	@ResponseBody
	public String getString() {
		return "아무 String";
	}
	
	@RequestMapping("/usr/home/getDouble")
	@ResponseBody
	public Double getDouble() {
		return 4.11456124651;
	}
	
	@RequestMapping("/usr/home/getMap")
	@ResponseBody
	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("철수 나이", 22);
		map.put("영희 나이", 33);
		return map;
	}
	
	@RequestMapping("/usr/home/getArticle")
	@ResponseBody
	public Article getArticle() {
		Article article = new Article();
		return article;
	}
	
	@RequestMapping("/usr/home/getList")
	@ResponseBody
	public List<Article> getList() {
		List<Article> articleList = new ArrayList<>();
		articleList.add(new Article(1, "제목1", "내용1"));
		articleList.add(new Article(2, "제목2", "내용2"));
		return articleList;
	}
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Article {
	private int id;
	private String title;
	private String body;
}