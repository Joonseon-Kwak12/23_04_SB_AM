<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE MODIFY" />
<%@ include file="../common/head.jspf"%>
<hr />



<script type="text/javascript"> /* 폼체크 */
	let ArticleModify__submitFormDone = false;
	function ArticleModify__submit(form) {
		if (ArticleModify__submitFormDone) {
			return;
		}
		form.body.value = form.body.value.trim();
		if (form.body.value.length == 0) {
			alert('내용을 입력해주세요');
			form.body.focus();
			return;
		}
		ArticleModify__submitFormDone = true;
		form.submit();
	}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form action="doModify" method="post" onsubmit="ArticleModify__submit(this); return false;">
			<input type="hidden" name="id" value="${article.id }"/>
			제목
			<input type="text" name="title" value="${article.title }" class="rounded w-full" />
			내용
			<textarea name="body" rows="15" class="rounded w-full">${article.body }</textarea>
			<button type="submit" class="w-24 h-6 border-solid border-gray-400 bg-black text-white text-sm rounded">수정</button>
		</form>
	</div>
</section>



<%@ include file="../common/foot.jspf"%>