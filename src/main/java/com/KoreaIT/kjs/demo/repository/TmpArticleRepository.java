package com.KoreaIT.kjs.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.KoreaIT.kjs.demo.vo.Article;

@Component
public class TmpArticleRepository {
	
	private int lastArticleId;
	private List<Article> articles;
	
	public TmpArticleRepository() {

		lastArticleId = 0;
		articles = new ArrayList<>();
	}
	
	//메서드
	public void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목 " + i;
			String body = "제목 " + i;

			writeArticle(title, body);
		}
	}

	public Article writeArticle(String title, String body) {
		int id = ++lastArticleId;

		Article article = new Article(id, "2013-04-18", "2013-04-18", 1, title, body);
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

	public List<Article> getArticles() {
		return articles;
	}

}
