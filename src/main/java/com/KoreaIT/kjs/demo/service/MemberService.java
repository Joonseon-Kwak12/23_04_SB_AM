package com.KoreaIT.kjs.demo.service;

import org.springframework.stereotype.Service;

import com.KoreaIT.kjs.demo.repository.MemberRepository;
import com.KoreaIT.kjs.demo.vo.Member;

@Service
public class MemberService {
	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		
		Member existsMember = getMemberByLoginId(loginId);
		
		if (existsMember != null) {
			return -1;
		}
		
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		return memberRepository.getLastInsertId();
	}

	private Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}
	
//	public int getCountOfLoginId(String loginId) {
//		return memberRepository.getCountOfLoginId(loginId);
//	}
}
