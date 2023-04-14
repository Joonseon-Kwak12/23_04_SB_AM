package com.KoreaIT.kjs.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kjs.demo.vo.Article;

@Controller
public class UsrArticleController {
//	@Autowired //new JdbcTemplate(dataSource); 안 하고 주입 받아서 사용해보자
//	private JdbcTemplate jdbcTemplate;

	int lastArticleId;
	List<Article> articles;

	// 생성자
	public UsrArticleController() {
		lastArticleId = 0;
		articles = new ArrayList<>();

		makeTestData();
	}

	// 서비스 메서드
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

	private Article getArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}
		return null;
	}

	private void deleteArticle(int id) {
		Article article = getArticle(id);
		articles.remove(article);
	}

	private void modifyArticle(int id, String title, String body) {
		Article article = getArticle(id);
		article.setTitle(title);
		article.setBody(body);
	}

	// 액션 메서드
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {
		Article article = getArticle(id);
		if (article == null) {
			return id + "번 글은 존재하지 않습니다.";
		}
		modifyArticle(id, title, body);
		return article;
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = getArticle(id);
		if (article == null) {
			return id + "번 글은 존재하지 않습니다.";
		}
		deleteArticle(id);
		return id + "번 글이 삭제되었습니다.";
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

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Object showDetail(int id) {
		Article article = getArticle(id);
		if (article == null) {
			return id + "번 글은 존재하지 않습니다.";
		}
		return article;
	}
}
