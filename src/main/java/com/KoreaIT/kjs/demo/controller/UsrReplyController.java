package com.KoreaIT.kjs.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kjs.demo.service.ArticleService;
import com.KoreaIT.kjs.demo.service.ReplyService;
import com.KoreaIT.kjs.demo.util.Ut;
import com.KoreaIT.kjs.demo.vo.Article;
import com.KoreaIT.kjs.demo.vo.Reply;
import com.KoreaIT.kjs.demo.vo.ResultData;
import com.KoreaIT.kjs.demo.vo.Rq;

@Controller
public class UsrReplyController {
//	@Autowired //new JdbcTemplate(dataSource); 안 하고 주입 받아서 사용해보자
//	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Rq rq;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private ArticleService articleService;

	
	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	public String doWrite(String relTypeCode, String body, Integer relId) {
		
		if (Ut.empty(relTypeCode)) {
			return rq.jsHistoryBack("F-1", "relTypeCode을 입력해주세요.");
		}
		if (Ut.empty(relId)) {
			return rq.jsHistoryBack("F-2", "relId을 입력해주세요.");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("F-3", "댓글 내용을 입력해주세요.");
		}

		ResultData<Integer> writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);
		
//		int id = (int) writeReplyRd.getData1();
		
//		if (Ut.empty(replaceUri)) {
//			replaceUri = Ut.f("../article/detail?id=%d", relId);
//		}
		return rq.jsReplace(writeReplyRd.getMsg(), Ut.f("../article/detail?id=%d", relId));
	}
	
	@RequestMapping("/usr/reply/doDelete")
	@ResponseBody
	public String doDelete(int id, String replaceUri) {

		Reply reply = replyService.getReply(id);

		if (reply == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 댓글은 존재하지 않습니다", id));
		}

		if (reply.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("F-2", Ut.f("%d번 댓글에 대한 권한이 없습니다", id));
		}

		ResultData deleteReplyRd = replyService.deleteReply(id);

		if (Ut.empty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}

		return Ut.jsReplace(deleteReplyRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reply/modify")
	public String showModify(Model model, int id, String replaceUri) {

		Reply reply = replyService.getForPrintReply(rq.getLoginedMemberId(), id);

		if (reply == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 댓글은 존재하지 않습니다!", id));
		}

		ResultData actorCanModifyRd = replyService.actorCanModify(rq.getLoginedMemberId(), reply);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBackOnView(actorCanModifyRd.getMsg());
		}

		Article article = articleService.getArticle(reply.getRelId());

		model.addAttribute("reply", reply);
		model.addAttribute("article", article);

		return "usr/reply/modify";
	}
	
	@RequestMapping("/ur/reply/doModify")
	public String doModify(int id, String body, String replaceUri) {
		
		Reply reply = replyService.getReply(id);

		if (reply == null) {
			return rq.jsHistoryBack("F-1", Ut.f("%d번 댓글은 존재하지 않습니다", id));
		}

		ResultData actorCanModifyRd = replyService.actorCanModify(rq.getLoginedMemberId(), reply);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
		}

		ResultData modifyReplyRd = replyService.modifyReply(id, body);

		if (Ut.empty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}
		
		return rq.jsReplace(modifyReplyRd.getMsg(), replaceUri);
	}
	//==========================================================
	//==========================================================
	//==========================================================
	//==========================================================
	//==========================================================
	//==========================================================
	//==========================================================
	@GetMapping(value="/usr/reply/getList", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<List<Reply>> getList(int actorId, String relTypeCode, int relId) {
				
		return new ResponseEntity<>(replyService.getForPrintReplies(actorId, relTypeCode, relId), HttpStatus.OK);
	}
}
