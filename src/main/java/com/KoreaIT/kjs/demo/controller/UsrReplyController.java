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
import com.KoreaIT.kjs.demo.service.ReactionPointService;
import com.KoreaIT.kjs.demo.service.ReplyService;
import com.KoreaIT.kjs.demo.util.Ut;
import com.KoreaIT.kjs.demo.vo.Article;
import com.KoreaIT.kjs.demo.vo.Board;
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
	
}
