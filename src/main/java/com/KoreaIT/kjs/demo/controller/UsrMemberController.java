package com.KoreaIT.kjs.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kjs.demo.service.MemberService;
import com.KoreaIT.kjs.demo.util.Ut;
import com.KoreaIT.kjs.demo.vo.Member;
import com.KoreaIT.kjs.demo.vo.ResultData;

@Controller
public class UsrMemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
//		int countOfLoginId = memberService.getCountOfLoginId(loginId);
//		
//		if (countOfLoginId != 0) {
//			return "이미 사용중인 아이디입니다.";
//		}
		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요.");
		}
		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 입력해주세요");
		}
		if (Ut.empty(name)) {
			return ResultData.from("F-3", "이름을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return ResultData.from("F-4", "닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return ResultData.from("F-5", "전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return ResultData.from("F-6", "이메일을 입력해주세요");
		}
		
		ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if (joinRd.isFail()) {
			return joinRd;
		}
		
		Member member = memberService.getMemberById((int)joinRd.getData1());
		System.out.println(joinRd);
		
		return ResultData.newData(joinRd, member);
		// 성공했을 때 반환받은 ResultData에는 data1 자리에 회원 id만 들어가 있음,
		// 회원 객체 전체를 반환해주려고 시도하려면 원래 ResultData 인스턴스 data1 자리에 있는 것을 member로 교체
		// 위 같은 행위를 해주는 newData 메소드 새로 만들어줬음
	}
}
