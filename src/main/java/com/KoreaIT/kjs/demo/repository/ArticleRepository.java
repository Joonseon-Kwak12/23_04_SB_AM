package com.KoreaIT.kjs.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.kjs.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public void writeArticle(int memberId, int boardId, String title, String body);
	
	public List<Article> getArticles();

	public Article getArticle(int id);
	
	public List<Article> getForPrintAllArticles(int articleFrom, int articlesPerPage, String searchKeyword);
	
	public List<Article> getForPrintArticles(Integer boardId, int articleFrom, int articlesPerPage, String searchKeyword);
	
	public Article getForPrintArticle(int id);
	
	public void deleteArticle(int id);

	public void modifyArticle(int id, String title, String body);
	
	public int getLastInsertId();
	
	public int getAllArticlesCount(String searchKeyword);
	
	public int getArticlesCount(int boardId, String searchKeyword);


}
