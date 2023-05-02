package com.KoreaIT.kjs.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.kjs.demo.repository.ReactionPointRepository;
import com.KoreaIT.kjs.demo.vo.ResultData;

@Service
public class ReactionPointService {

	@Autowired
	private ReactionPointRepository reactionPointRepository;
	@Autowired
	private ArticleService articleService;
	

	public ResultData actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		
		if (actorId == 0) {
			return ResultData.from("F-1", "로그인이 필요합니다.");
		}
		
		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode, relId);
		
		if (sumReactionPointByMemberId != 0) {
			return ResultData.from("F-2", "추천/비추천 불가", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}
		return ResultData.from("S-1", "추천/비추천 가능", "sumReactionPointByMemberId", sumReactionPointByMemberId);
	}
	
	public int getActorReaction(int actorId, String relTypeCode, int relId) {
		
		return reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode, relId);
	}

	public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {

		int affectedRow = reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);
		
		if (affectedRow != 1) {
			return ResultData.from("F-2", "좋아요 실패");
		}
		
		switch (relTypeCode) {
		case "article": 
			articleService.increaseGoodReactionPoint(relId);
			break;
		}
		
		return ResultData.from("S-1", "좋아요 처리 완료");
	}

	public ResultData addBadReactionPoint(int actorId, String relTypeCode, int relId) {

		int affectedRow = reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);

		if (affectedRow != 1) {
			return ResultData.from("F-2", "싫어요 실패");
		}

		switch (relTypeCode) {
		case "article":
			articleService.increaseBadReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 처리 완료");
	}

	public ResultData cancelReaction(int loginedMemberId, String relTypeCode, int relId, int actorReaction) {

		int affectedRow = reactionPointRepository.cancelReaction(loginedMemberId, relTypeCode, relId);
		
		if (affectedRow != 1) {
			return ResultData.from("F-2", "반응 취소 실패");
		}
		
		switch (relTypeCode) {
		case "article":
			if (actorReaction == 1) {
				articleService.cancelGoodReactionPoint(relId);
				break;
			}
			if (actorReaction == -1) {
				articleService.cancelBadReactionPoint(relId);
				break;
			}
		}
		
		return ResultData.from("S-1", "반응 취소 완료");
	}

}
