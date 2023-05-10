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

<div class="m-auto p-6 pb-3 w-[300px] bg-[#3B5998] rounded text-center">
	<!-- form의 att로 onsubmit 필요 없음 onsubmit="LoginForm__submit(this); return false;" -->
	<form method="post" action="doLogin" class="flex flex-col items-center gap-3">
	<!-- 브라우저에서 비밀번호 암호화해서 넘길 경우 -->
	<!-- <form onsubmit="submitLoginForm(this); return false;"
		method="post" action="doLogin" class="flex flex-col items-center gap-3"> -->
		<input type="hidden" name="afterLoginUri" value="${param.afterLoginUri }" />
		<input type="hidden" name="encPw" value="" />
		<input type="text" placeholder="아이디를 입력해주세요." name="loginId" class="rounded w-full" />
		<input type="text" placeholder="비밀번호를 입력해주세요." name="loginPw" class="rounded w-full" />
		<button type="submit" class="w-24 h-6 bg-white text-sm rounded">로그인</button>
	</form>
	<div>
		<a class="text-xs text-white" href="${rq.getFindLoginIdUri() }"> 아이디를 잊어버리셨나요? </a>
	</div>
	<div>
		<a class="text-xs text-white" href="${rq.getFindLoginPwUri() }"> 비밀번호를 잊어버리셨나요? </a>
	</div>
</div>

<!-- 브라우저에서 암호화 해서 넘길 경우 -->
<!-- <script	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script> -->
<!-- 위 부분은 script로 불러오지 않고, 직접 해시 펑션을 사용할 수도 있음 -->
<!--
<script>

	var loginFormSubmitted = false;

	function submitLoginForm(form) {
		if (loginFormSubmitted) {
			alert('처리 중입니다.');
			return;
		}

		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value.length == 0) {
			alert('비밀번호를 입력해주세요.');
			form.loginPw.focus();

			return;
		}
		
		
		form.encPw.value = sha256(form.loginPw.value);
		from.loginPw.value = '';
		
		form.submit();
		loginFormSubmitted = true;
	}
    
</script>
-->

<%@ include file="../common/foot.jspf"%>