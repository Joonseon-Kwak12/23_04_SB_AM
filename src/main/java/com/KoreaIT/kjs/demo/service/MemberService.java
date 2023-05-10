package com.KoreaIT.kjs.demo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.KoreaIT.kjs.demo.repository.MemberRepository;
import com.KoreaIT.kjs.demo.util.Ut;
import com.KoreaIT.kjs.demo.vo.Member;
import com.KoreaIT.kjs.demo.vo.ResultData;

@Service
public class MemberService {
	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		// 로그인 아이디 중복체크
		Member existsMember = getMemberByLoginId(loginId);
		
		if (existsMember != null) {
			return ResultData.from("F-7", Ut.f("이미 사용 중인 아이디(%s)입니다.", loginId));
		}
		
		// 이름 + 이메일 중복체크
		existsMember = getMemberByNameAndEmail(name, email);
		
		if (existsMember != null) {
			return ResultData.from("F-8", Ut.f("동일한 이름(%s)과 이메일(%s)이 사용 중입니다.", name, email));
		}
		
		loginPw = Ut.sha256(loginPw);
		
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		int id = memberRepository.getLastInsertId();
		
		return ResultData.from("S-1", "회원가입이 완료되었습니다.", "id", id);
	}
	
	public ResultData modify(int id, String loginPw, String name, String nickname, String cellphoneNum, String email) {

		memberRepository.modify(id, loginPw, name, nickname, cellphoneNum, email);
				
		return ResultData.from("S-1", "회원정보 수정이 완료되었습니다.");
	}

	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	public void login(HttpServletRequest request) {
		
		String loginId = (String) request.getParameter("loginId");
		String loginPw = (String) request.getParameter("loginPw");
		
		Member foundMember = memberRepository.getMemberByLoginId(loginId);
		if (foundMember.getLoginPw() != loginPw) {
			
		}
		
		
		HttpSession session = request.getSession();
		
		session.setAttribute("loginId", loginId);
		session.setAttribute("loginPw", loginPw);
	}

	public String checkLoginIdDup(String loginId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
//	public int getCountOfLoginId(String loginId) {
//		return memberRepository.getCountOfLoginId(loginId);
//	}
}
