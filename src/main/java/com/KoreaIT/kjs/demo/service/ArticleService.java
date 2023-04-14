package com.KoreaIT.kjs.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.kjs.demo.repository.UsrArticleRepository;
import com.KoreaIT.kjs.demo.vo.Article;

@Service
public class ArticleService {

	@Autowired
	UsrArticleRepository usrArticleRepository;

	private int lastArticleId;
	private List<Article> articles;
	
	//생성자
	public ArticleService(UsrArticleRepository usrArticleRepository) {

		lastArticleId = 0;
		articles = new ArrayList<>();

		makeTestData();
	}
	
	//서비스 메서드
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

	public Article getArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}
		return null;
	}

	public void deleteArticle(int id) {
		Article article = getArticle(id);
		articles.remove(article);
	}

	public void modifyArticle(int id, String title, String body) {
		Article article = getArticle(id);
		article.setTitle(title);
		article.setBody(body);
	}

	public List<Article> articles() {
		return articles;
	}
}
