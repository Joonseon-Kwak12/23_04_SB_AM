package com.KoreaIT.kjs.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String doLogout(@RequestParam(defaultValue = "/")String afterLogoutUri) {
		
		rq.logout();
		
		return Ut.jsReplace("S-1", "로그아웃 되었습니다.", afterLogoutUri);
	}
	
	@RequestMapping("/usr/member/login")
	public String showLogin(HttpSession httpSession) {
		
		return "usr/member/login";
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue = "/")String afterLoginUri ) throws Exception {
		
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
		
		System.out.println(Ut.sha256(loginPw));

		if (member.getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return Ut.jsHistoryBack("F-4", Ut.f("비밀번호가 일치하지 않습니다."));
		}
		
		rq.login(member);
		
		// 우리가 갈 수 있는 경로를 경우의 수로 표현
		// 인코딩
		// 그 외에는 처리 불가 -> 메인으로 보내자
		
		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getName()), afterLoginUri);
	}
	
	@RequestMapping("/usr/member/join")
	public String join() {
		
		return "usr/member/join";
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email, @RequestParam(defaultValue = "/") String afterLoginUri) {
//		int countOfLoginId = memberService.getCountOfLoginId(loginId);
//		
//		if (countOfLoginId != 0) {
//			return "이미 사용중인 아이디입니다.";
//		}
		if (Ut.empty(loginId)) {
			return rq.jsHistoryBack("F-1", "아이디를 입력해주세요.");
		}
		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}
		if (Ut.empty(name)) {
			return rq.jsHistoryBack("F-3", "이름을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return rq.jsHistoryBack("F-4", "닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHistoryBack("F-5", "전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return rq.jsHistoryBack("F-6", "이메일을 입력해주세요");
		}
		
		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if (joinRd.isFail()) {
			return rq.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg()); // 어차피 isFail() == true이면 null이라서 return 가능하긴 함...
		}
				
		Member member = memberService.getMemberById(joinRd.getData1());
		
		String afterJoinUri = "../member/login?afterLoginUri=" + Ut.getEncodedUri(afterLoginUri);

		// return ResultData.newData(joinRd, "member", member);
		// 성공했을 때 반환받은 ResultData에는 data1 자리에 회원 id만 들어가 있음,
		// 회원 객체 전체를 반환해주려고 시도하려면 원래 ResultData 인스턴스 data1 자리에 있는 것을 member로 교체
		// 위 같은 행위를 해주는 newData 메소드 새로 만들어줬음
		
		//방식 바꾸면서 String return
		return Ut.jsReplace("S-1", Ut.f("회원가입이 완료되었습니다"), afterJoinUri);
	}
	
	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData checkLoginIdDup(String loginId) {
		
		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요.");
		}
		
		Member existMember = memberService.getMemberByLoginId(loginId);
		
		if (existMember != null) {
			
			return ResultData.from("F-2", "해당 아이디는 이미 사용 중입니다.", "loginId", loginId);
		}
				
		return ResultData.from("S-1", "사용 가능한 아이디입니다.", "loginId", loginId);
	}
	
	@RequestMapping("/usr/member/myPage")
	public String showMyPage() {
		
		return "usr/member/myPage";
	}
	
	@RequestMapping("/usr/member/checkPw")
	public String showCheckPw() {
		
		return "usr/member/checkPw";
	}
	
	@RequestMapping("/usr/member/doCheckPw")
	@ResponseBody
	public String doCheckPw(String loginPw, String replaceUri) {
		
		if(Ut.empty(loginPw)) {
			return rq.jsHistoryBack("", "비밀번호를 입력해주세요.");
		}
		
		if (rq.getLoginedMember().getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return rq.jsHistoryBack("", "비밀번호가 일치하지 않습니다.");
		}
		
		return rq.jsReplace("", replaceUri);
	}
	
	@RequestMapping("/usr/member/modify")
	public String showModify() {
		
		return "usr/member/modify";
	}
	
	@RequestMapping("/usr/member/doModify")
	public String doModify(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		if (Ut.empty(loginPw)) {
			loginPw = null;
		} else {
			loginPw = Ut.sha256(loginPw);
		}
		if (Ut.empty(name)) {
			return rq.jsHistoryBackOnView("name 입력해주세요.");
		}
		if (Ut.empty(nickname)) {
			return rq.jsHistoryBackOnView("nickname 입력해주세요.");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHistoryBackOnView("cellphoneNum 입력해주세요.");
		}
		if (Ut.empty(email)) {
			return rq.jsHistoryBackOnView("email 입력해주세요.");
		}
		
		ResultData modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum, email);
		
		return rq.jsReplace(modifyRd.getMsg(), "../member/myPage");
	}
	
	@RequestMapping("/usr/member/findLoginId")
	public String showFindLoginId() {

		return "usr/member/findLoginId";
	}

	@RequestMapping("/usr/member/doFindLoginId")
	@ResponseBody
	public String doFindLoginId(@RequestParam(defaultValue = "/") String afterFindLoginIdUri, String name, String email) {

		Member member = memberService.getMemberByNameAndEmail(name, email);

		if (member == null) {
			return Ut.jsHistoryBack("F-1", "존재하지 않는 회원정보입니다.");
		}

		return Ut.jsReplace("S-1", Ut.f("아이디는 [ %s ]입니다", member.getLoginId()), afterFindLoginIdUri);
	}
	
	@RequestMapping("/usr/member/findLoginPw")
	public String showFindLoginPw() {

		return "usr/member/findLoginPw";
	}

	@RequestMapping("/usr/member/doFindLoginPw")
	@ResponseBody
	public String doFindLoginPw(@RequestParam(defaultValue = "/") String afterFindLoginPwUri, String loginId, String email) {

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-1", "존재하지 않는 회원정보입니다.");
		}
		
		if (!member.getEmail().equals(email)) {
			return Ut.jsHistoryBack("F-2", "이메일 주소가 일치하지 않습니다.");
		}
		
		ResultData notifyTempLoginPwByEmailRd = memberService.notifyTempLoginPwByEmail(member);

		return Ut.jsReplace(notifyTempLoginPwByEmailRd.getResultCode(), notifyTempLoginPwByEmailRd.getMsg(), afterFindLoginPwUri);
	}
	
}
