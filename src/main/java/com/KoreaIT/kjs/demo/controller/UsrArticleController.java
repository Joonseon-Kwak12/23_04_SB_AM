package com.KoreaIT.kjs.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kjs.demo.service.ArticleService;
import com.KoreaIT.kjs.demo.util.Ut;
import com.KoreaIT.kjs.demo.vo.Article;
import com.KoreaIT.kjs.demo.vo.ResultData;

@Controller
public class UsrArticleController {
//	@Autowired //new JdbcTemplate(dataSource); 안 하고 주입 받아서 사용해보자
//	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ArticleService articleService;

//	// 생성자
//	public UsrArticleController(ArticleService articleService) {
//		// this.usrArticleService = new usrArticleService( ); // new ~()할 필요 없이 @Autowired로 주입 받아서 사용 가능
//	}
 
	// 액션 메서드
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Integer> doModify(int id, String title, String body) {
		
		Article article = articleService.getArticle(id);
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다.", id), id);
		}
		
		articleService.modifyArticle(id, title, body);
		return ResultData.from("S-1", Ut.f("%d번 글이 수정되었습니다.", id), id);
	}

	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(int id) {
		
		Article article = articleService.getArticle(id);
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다.", id), id);
		}
		
		articleService.deleteArticle(id);
		return ResultData.from("S-1", Ut.f("%d번 글이 삭제되었습니다.", id), id);
	}

	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public ResultData<Article> doWrite(String title, String body) {
		if (Ut.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요.");
		}
		if (Ut.empty(title)) {
			return ResultData.from("F-2", "내용을 입력해주세요.");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body);
		
		int id = (int) writeArticleRd.getData1();
		Article article = articleService.getArticle(id);
		
		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), article);
	}

	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {
		
		List<Article> articles = articleService.getArticles();
		
		if (articles == null) {
			return ResultData.from("F-1", Ut.f("현재 게시글이 존재하지 않습니다."));
		}
		
		return ResultData.from("S-1", Ut.f("Article List"), articles);
	}

	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {
		
		Article article = articleService.getArticle(id);
		
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다.", id));
		}
		
		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), article);
	}
}
