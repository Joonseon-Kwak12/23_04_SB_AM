<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE WRITE" />
<%@ include file="../common/head.jspf"%>
<%@ include file="../common/toastUIEditorLib.jspf"%>
<hr />



<!-- Article write 관련 폼체크 -->
<script type="text/javascript">
	let ArticleWrite__submitFormDone = false;
	function ArticleWrite__submit(form) {
		if (ArticleWrite__submitFormDone) {
			return;
		}
		form.title.value = form.title.value.trim();
		if (form.title.value == 0) {
			alert('제목을 입력해주세요');
			return;
		}
		const editor = $(form).find('.toast-ui-editor').data(
				'data-toast-editor');
		const markdown = editor.getMarkdown().trim();
		if (markdown.length == 0) {
			alert('내용을 입력해주세요');
			editor.focus();
			return;
		}
		form.body.value = markdown;
		ArticleWrite__submitFormDone = true;
		form.submit();
	}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form action="../article/doWrite" method="POST" onsubmit="ArticleWrite__submit(this); return false;">
			<input type="hidden" name="body">
			제목 <span>현재 작성자 ${rq.getLoginedMember().getNickname() } </span>
			<input type="text" name="title" class="rounded w-full" />
			게시판 선택
			<select name="stringBoardId" class="rounded w-full">
				<option disabled selected>=== 게시판을 선택해주세요. ===</option>
				<option value="1">공지사항</option>
				<option value="2">자유게시판</option>
				<option value="3">질의응답</option>
			</select>
			내용
			<!-- <textarea name="body" rows="15" class="rounded w-full"></textarea> -->
			<div class="toast-ui-editor">
				<script type="text/x-template"></script>
			</div>
			<button type="submit" class="w-24 h-6 border-solid border-gray-400 bg-black text-white text-sm rounded">작성 완료</button>
		</form>
	</div>
</section>



<%@ include file="../common/foot.jspf"%>