<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER MODIFY" />
<%@ include file="../common/head.jspf"%>



<!-- Member modify 관련 폼 체크 -->
<script>
	let MemberModify__submitFormDone = false;

	function MemberModify__submit(form) {

		if (MemberModify__submitFormDone) {
			return;
		}

		form.loginPw.value = form.loginPw.value.trim();

		if (form.loginPw.value.length > 0) {
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
			if (form.loginPwConfirm.value.length == 0) {
				alert('비밀번호 확인을 입력해주세요');
				form.loginPwConfirm.focus();
				return;
			}
			if (form.loginPw.value != form.loginPwConfirm.value) {
				alert('비밀번호가 일치하지 않습니다.');
				form.loginPw.focus();
				return;
			}
		}

		form.name.value = form.name.value.trim();
		form.nickname.value = form.nickname.value.trim();
		form.cellphoneNum.value = form.cellphoneNum.value.trim();
		form.email.value = form.email.value.trim();

		if (form.name.value.length == 0) {
			alert('name을 입력해주세요.');
			form.name.focus();
		}
		if (form.nickname.value.length == 0) {
			alert('nickname을 입력해주세요.');
			form.nickname.focus();
		}
		if (form.cellphoneNum.value.length == 0) {
			alert('cellphoneNum을 입력해주세요.');
			form.cellphoneNum.focus();
		}
		if (form.email.value.length == 0) {
			alert('email을 입력해주세요.');
			form.email.focus();
		}

		MemberModify__submitFormDone = true;
		form.submit();
	}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<form action="../member/doModify" method="post">
				<table border="1">
					<colgroup>
						<col width="200" />
					</colgroup>

					<tbody>
						<tr>
							<th>가입일</th>
							<td>${rq.loginedMember.regDate }</td>
						</tr>
						<tr>
							<th>아이디</th>
							<td>${rq.loginedMember.loginId }</td>
						</tr>
						<tr>
							<th>새 비밀번호</th>
							<td>
								<input name="loginPw" placeholder="새 비밀번호를 입력해주세요." type="text" />
							</td>
						</tr>
						<tr>
							<th>새 비밀번호 확인</th>
							<td>
								<input name="loginPwConfirm" placeholder="새 비밀번호 확인을 입력해주세요." type="text" />
							</td>
						</tr>
						<tr>
							<th>이름</th>
							<td>
								<input name="name" value="${rq.loginedMember.name }" placeholder="이름을 입력해주세요." type="text" />
							</td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td>
								<input name="nickname" value="${rq.loginedMember.nickname }" placeholder="닉네임을 입력해주세요" type="text" />
							</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td>
								<input name="cellphoneNum" value="${rq.loginedMember.cellphoneNum }" placeholder="전화번호를 입력해주세요" type="text" />
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td>
								<input name="email" value="${rq.loginedMember.email }" placeholder="이메일을 입력해주세요" type="text" />
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<button type="submit" class="w-24 h-6 border-solid border-gray-400 bg-black text-white text-sm rounded">수정</button>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="btns">
			<button type="button" onclick="history.back();">뒤로가기</button>
		</div>
	</div>
</section>



<%@ include file="../common/foot.jspf"%>