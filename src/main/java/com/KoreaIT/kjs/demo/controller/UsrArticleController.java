package com.KoreaIT.kjs.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kjs.demo.service.ArticleService;
import com.KoreaIT.kjs.demo.service.BoardService;
import com.KoreaIT.kjs.demo.util.Ut;
import com.KoreaIT.kjs.demo.vo.Article;
import com.KoreaIT.kjs.demo.vo.Board;
import com.KoreaIT.kjs.demo.vo.ResultData;
import com.KoreaIT.kjs.demo.vo.Rq;

@Controller
public class UsrArticleController {
//	@Autowired //new JdbcTemplate(dataSource); 안 하고 주입 받아서 사용해보자
//	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private Rq rq;
	
	
//	// 생성자
//	public UsrArticleController(ArticleService articleService) {
//		// this.usrArticleService = new usrArticleService( ); // new ~()할 필요 없이 @Autowired로 주입 받아서 사용 가능
//	}
 
	// 액션 메서드
	@RequestMapping("/usr/article/modify") //@RequestBody 없음
	public String showModify(Model model, int id) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		//getForPrintArticle 과정에 actorCanModify 포함되어 있음
		
		if (article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다.", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBackOnView(actorCanModifyRd.getMsg());
		}
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}
	
	
	@RequestMapping("/usr/article/doModify") //@RequestBody 있음, replace로 보냄
	@ResponseBody
	public String doModify(int id, String title, String body) {
		
//		Integer memberId = (Integer) httpSession.getAttribute("loginedMemberId");

		Article article = articleService.getArticle(id);
		if (article == null) {
			return rq.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다....", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
		}
		
		articleService.modifyArticle(id, title, body);
		
		return rq.jsReplace(Ut.f("%d번 글을 수정했습니다.", id), Ut.f("../article/detail?id=%d", id));
	}

	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		
//		Integer memberId = (Integer) httpSession.getAttribute("loginedMemberId");
		
		Article article = articleService.getArticle(id);
		if (article == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다.", id));
		}
		
		if (rq.getLoginedMemberId() != article.getMemberId()) {
			return Ut.jsHistoryBack("F-2", Ut.f("%d번 글에 대한 권한이 없습니다.", id));
		}
		
		articleService.deleteArticle(id);
		return Ut.jsReplace(Ut.f("%d번 글이 삭제되었습니다.", id), "../article/list");
	}

	
	@RequestMapping("/usr/article/write")
	public String showWrite() {
		
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(String stringBoardId, String title, String body) {
		
//		Integer loginedMemberId = (Integer) httpSession.getAttribute("loginedMemberId");
		
		if (Ut.empty(title)) {
			return rq.jsHistoryBack("F-1", "제목을 입력해주세요.");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("F-2", "내용을 입력해주세요.");
		}
		if (Ut.empty(stringBoardId)) {
			return rq.jsHistoryBack("F-3", "게시판을 선택해주세요.");
		}
		
		int boardId = Integer.parseInt(stringBoardId);
		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);
		
		int id = (int) writeArticleRd.getData1();
		
		return rq.jsReplace(Ut.f("%d번 글을 작성했습니다.", id), Ut.f("../article/detail?id=%d", id));
	}

	
	@RequestMapping("/usr/article/list")
	public String showList(Model model, Integer boardId, Integer page, @RequestParam(defaultValue = "title,body") String searchKeywordTypeCode, String searchKeyword) {
//	public String showList(Model model, @RequestParam(defaultValue = "1")Integer boardId, @RequestParam(defaultValue = "1")Integer page) {

		int articlesPerPage;
		articlesPerPage = 10;
		
		if (page == null || page <= 0) {
			page = 1;
		}
		
//		if (boardId == null) {
//			List<Article> articles = articleService.getForPrintArticles(boardId, page, articlesPerPage);
//			
//			model.addAttribute("articles", articles);
//			model.addAttribute("page", page);
//			
//			return "usr/article/list";
//		}
		Board board = null;
		
		if (boardId != null) {
			board = boardService.getBoardById(boardId);
			
			if (board == null) {
				return rq.jsHistoryBackOnView("존재하지 않는 게시판입니다.");
			}
		}		
				
		int articlesCount = 0;
		List<Article> articles = null;
		
		if (boardId != null) {
			articlesCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
			articles = articleService.getForPrintArticles(boardId, page, articlesPerPage, searchKeywordTypeCode, searchKeyword);
			model.addAttribute("board", board);
			model.addAttribute("boardId", boardId);			
		} else {
			articlesCount = articleService.getArticlesCount(searchKeywordTypeCode, searchKeyword);
			articles = articleService.getForPrintAllArticles(page, articlesPerPage, searchKeywordTypeCode, searchKeyword);
		}
		
		int pagesCount = (int) Math.ceil((articlesCount) / (double)articlesPerPage);
		
		int startPage = (page - 2 > 1) ? page - 2 : 1;
		int endPage = (page + 2 < pagesCount) ? page + 2 : pagesCount;
		
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("articles", articles);
		model.addAttribute("page", page);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("hitCount", endPage);
		
		return "usr/article/list";
		
//		int articlesPerPage;
//		articlesPerPage = 10;
//		
//		Board board = boardService.getBoardById(boardId);
//
//		int articlesCount = articleService.getArticlesCount(boardId);
//		List<Article> articles = articleService.getForPrintArticles(boardId, page, articlesPerPage);
//		model.addAttribute("board", board);
//
//		model.addAttribute("articlesCount", articlesCount);
//		model.addAttribute("articles", articles);
//		model.addAttribute("page", page);
//
//		return "usr/article/list";
	}

	
	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {
		
		articleService.increaseHitCount(id);
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}
}
