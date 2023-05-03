package com.KoreaIT.kjs.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kjs.demo.service.MemberService;
import com.KoreaIT.kjs.demo.util.Ut;
import com.KoreaIT.kjs.demo.vo.Member;
import com.KoreaIT.kjs.demo.vo.ResultData;
import com.KoreaIT.kjs.demo.vo.Rq;

@Controller
public class UsrMemberController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout() {
		
		if (!rq.isLogined()) {
			return Ut.jsHistoryBack("F-1", "이미 로그아웃 상태입니다.");
		}
		
		rq.logout();
		
		return Ut.jsReplace("S-1", "로그아웃 되었습니다.", "/");
	}
	
	@RequestMapping("/usr/member/login")
	public String login() {
		
		return "usr/member/login";
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw) throws Exception {
		
		if (rq.isLogined()) {
			return Ut.jsHistoryBack("F-5", "이미 로그인 상태입니다.");
		}
		
		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("F-1", "아이디를 입력해주세요.");
		}
		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member == null) {
			return Ut.jsHistoryBack("F-3", Ut.f("%s는 존재하지 않는 아이디입니다.", loginId));
		}
		
		if(member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-4", Ut.f("비밀번호가 일치하지 않습니다.", loginId));
		}
		
		rq.login(member);
		
		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getName()), "/");
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
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
		
		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if (joinRd.isFail()) {
			return (ResultData) joinRd; // 어차피 isFail() == true이면 null이라서 return 가능하긴 함...
		}
				
		Member member = memberService.getMemberById(joinRd.getData1());

		return ResultData.newData(joinRd, "member", member);
		// 성공했을 때 반환받은 ResultData에는 data1 자리에 회원 id만 들어가 있음,
		// 회원 객체 전체를 반환해주려고 시도하려면 원래 ResultData 인스턴스 data1 자리에 있는 것을 member로 교체
		// 위 같은 행위를 해주는 newData 메소드 새로 만들어줬음
	}
	
	
	@RequestMapping("/usr/member/myPage")
	public String showMyPage() {
		
		return "usr/member/myPage";
	}
	
	@RequestMapping("/usr/member/checkPw")
	public String showCheckPw() {
		
		return "usr/member/checkPw";
	}
}
