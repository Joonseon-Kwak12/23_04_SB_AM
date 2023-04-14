package com.KoreaIT.kjs.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.KoreaIT.kjs.demo.repository.ArticleRepository;
import com.KoreaIT.kjs.demo.vo.Article;

@Service
public class ArticleService {
	
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
	
	//서비스 메서드
	public int writeArticle(String title, String body) {
		
		articleRepository.writeArticle(title, body);
		
		return articleRepository.getLastInsertId();
	}

	public Article getArticle(int id) {

		return articleRepository.getArticle(id);
	}

	public void deleteArticle(int id) {
		
		articleRepository.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		
		articleRepository.modifyArticle(id, title, body);
	}

	public List<Article> getArticles() {
		
		return articleRepository.getArticles();
	}
}
