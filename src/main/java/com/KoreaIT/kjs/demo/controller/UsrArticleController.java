package com.KoreaIT.kjs.demo.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kjs.demo.vo.Article;

@Controller
public class UsrArticleController {
//	@Autowired //new JdbcTemplate(dataSource); 안 하고 주입 받아서 사용해보자
//	private JdbcTemplate jdbcTemplate;
	
	int lastArticleId;
	List<Article> articles;

	public UsrArticleController() {
		lastArticleId = 0;
		articles = new ArrayList<>();
		
		makeTestData();
	}
	
	private void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목 " + i;
			String body = "제목 " + i;
			
			writeArticle(title, body);
		}
	}
	
	public Article writeArticle(String title, String body) {
		int id = ++lastArticleId;

		Article article = new Article(id, title, body);
		articles.add(article);

		return article;
	}
	
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		return writeArticle(title, body);
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articles;
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				articles.remove(article);
				return id + "번 글이 삭제되었습니다.";
			}
		}
		return id + "번 글은 존재하지 않습니다.";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return id + "번 글이 수정되었습니다.";
			}
		}
		return id + "번 글은 존재하지 않습니다.";
	}
	
}