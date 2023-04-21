<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="LOGIN" />
<%@ include file="../common/head.jspf"%>



<!-- <script type="text/javascript">
	var LoginForm__submitDone = false;
	function LoginForm__submit(form) {
		if (LoginForm__submitDone) {
			alert('처리 중입니다.');
			return;
		}
		var loginId = form.loginId.value.trim();
		var loginPw = form.loginPw.value.trim();
		if (loginId.length == 0) {
			alert('아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}
		if (loginPw.length == 0) {
			alert('비밀번호를 입력해주세요.');
			form.loginPw.focus();
			return;
		}
		LoginForm__submitDone = true;
		form.submit();
	}
</script> -->

<div class="m-auto p-6 w-[300px] bg-[#3B5998] rounded text-center">
  <!-- onsubmit 필요 없음 <form method="post" action="doLogin" onsubmit="LoginForm__submit(this); return false;" class="flex flex-col items-center gap-3"> -->
  <form method="post" action="doLogin" onsubmit="LoginForm__submit(this); return false;" class="flex flex-col items-center gap-3">
    <input type="text" placeholder="아이디를 입력해주세요." name="loginId" class="rounded w-full"/>
    <input type="text" placeholder="비밀번호를 입력해주세요." name="loginPw" class="rounded w-full"/>
    <button type="submit" class="w-24 h-6 bg-white text-sm rounded">로그인</button>
  </form>
  <a class="text-xs text-white" href="#"> 비밀번호를 잊어버리셨나요? </a>
</div>



<%@ include file="../common/foot.jspf"%>