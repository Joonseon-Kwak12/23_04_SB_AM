package com.KoreaIT.kjs.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.kjs.demo.repository.ReplyRepository;
import com.KoreaIT.kjs.demo.util.Ut;
import com.KoreaIT.kjs.demo.vo.Reply;
import com.KoreaIT.kjs.demo.vo.ResultData;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyRepository replyRepository;

	public ResultData<Integer> writeReply(int actorId, String relTypeCode, Integer relId, String body) {
		
		replyRepository.writeReply(actorId, relTypeCode, relId, body);
		
		int id = replyRepository.getLastInsertId();
		
		return ResultData.from("S-1", Ut.f("%d번 댓글이 생성되었습니다.", id), "id", id);
	}

	public List<Reply> getForPrintReplies(int id) {
		return null;
	}
	
	
}
