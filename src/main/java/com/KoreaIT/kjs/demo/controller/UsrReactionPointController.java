package com.KoreaIT.kjs.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kjs.demo.service.ReactionPointService;
import com.KoreaIT.kjs.demo.vo.ResultData;
import com.KoreaIT.kjs.demo.vo.Rq;

@Controller
public class UsrReactionPointController {
	
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;
	
	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public String doGoodReaction(String relTypeCode, int relId, String replaceUri) {
				
		boolean actorCanMakeReaction = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId).isSuccess();
		if (!actorCanMakeReaction) {
			return rq.jsHistoryBack("F-1", "이미 반응한 게시물입니다.");
		}
		
		ResultData rd =  reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "좋아요 실패");
		}
		
		return rq.jsReplace("좋아요!", replaceUri);
	}
	
	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId, String replaceUri) {
				
		boolean actorCanMakeReaction = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId).isSuccess();
		if (!actorCanMakeReaction) {
			return rq.jsHistoryBack("F-1", "이미 반응한 게시물입니다.");
		}
		
		ResultData rd =  reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "싫어요 실패");
		}
		
		return rq.jsReplace("싫어요!", replaceUri);
	}
	
	@RequestMapping("/usr/reactionPoint/cancelReaction")
	@ResponseBody
	public String cancelReaction(String relTypeCode, int relId, String replaceUri) {
				
		int actorReaction = reactionPointService.getActorReaction(rq.getLoginedMemberId(), relTypeCode, relId);
		boolean actorCanCancelReaction = (actorReaction != 0);
		
		if (!actorCanCancelReaction) {
			return rq.jsHistoryBack("F-1", "취소할 수 없습니다.");
		}
		
		ResultData rd =  reactionPointService.cancelReaction(rq.getLoginedMemberId(), relTypeCode, relId, actorReaction);
		
		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "반응 취소 실패");
		}
		
		return rq.jsReplace("반응 취소", replaceUri);
	}
	
}
