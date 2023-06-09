<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE MODIFY" />
<%@ include file="../common/head.jspf"%>
<hr />



<script type="text/javascript">
	/* 폼체크 */
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
		<div class="table-box-type-1">
			<form action="../reply/doModify" method="POST" onsubmit="ReplyModify__submit(this); return false;">
				<input type="hidden" name="id" value="${reply.id }" />
				<input type="hidden" name="replaceUri" value="${param.replaceUri }" />
				<table>
					<colgroup>
						<col width="200" />
					</colgroup>

					<tbody>
						<tr>
							<th>게시물 번호</th>
							<td>
								<div class="badge">${article.id}</div>
							</td>
						</tr>
						<tr>
							<th>게시물 제목</th>
							<td>
								<div class="badge">${article.title}</div>
							</td>
						</tr>
						<tr>
							<th>게시물 내용</th>
							<td>
								<div class="badge">${article.body}</div>
							</td>
						</tr>
						<tr>
							<th>댓글 작성날짜</th>
							<td>${reply.regDate }</td>
						</tr>
						<tr>
							<th>댓글 작성자</th>
							<td>${reply.extra__writer }</td>
						</tr>
						<tr>
							<th>댓글 내용</th>
							<td>
								<textarea class="input input-bordered w-full max-w-xs" name="body" placeholder="내용을 입력해주세요">${reply.body }</textarea>
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<button type="submit" value="수정">수정</button>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="btns">
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>


		</div>
	</div>
</section>



<%@ include file="../common/foot.jspf"%>