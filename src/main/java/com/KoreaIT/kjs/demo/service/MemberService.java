package com.KoreaIT.kjs.demo.service;

import org.springframework.stereotype.Service;

import com.KoreaIT.kjs.demo.repository.MemberRepository;

@Service
public class MemberService {
	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNum, email);
	}
	
	

}
