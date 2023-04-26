<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf"%>
<hr />



<section>
	<header class=articleInfo>
		<div>글번호: ${article.id }</div>
		<div>작성일: ${article.regDate }</div>
		<div>수정일: ${article.updateDate }</div>
		<div>작성자: ${article.memberId }</div>
	</header>
	<article>${article.body }</article>
</section>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<table border="1">
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr>
						<th>번호</th>
						<td>${article.id }</td>
					</tr>
					<tr>
						<th>작성날짜</th>
						<td>${article.regDate }</td>
					</tr>
					<tr>
						<th>수정날짜</th>
						<td>${article.updateDate }</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>${article.extra__writer }</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>${article.title }</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>${article.body }</td>
					</tr>
					<tr>
						<th>조회수</th>
						<td>${article.hitCount }</td>
					</tr>
				</tbody>

			</table>
		</div>
		<div class="btns">
			<button class="btn-text-link" type="button" onclick="history.back();">뒤로가기</button>
			<c:if test="${article.actorCanModify }">
				<button class="btn-text-link" type="button"
				onclick="location.href = '../article/modify?id=${article.id}'">수정</button>
			</c:if>
			<c:if test="${article.actorCanDelete }">
				<button class="btn-text-link" type="button"
					onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false; location.href = '../article/doDelete?id=${article.id}'">삭제</button>
			</c:if>
		</div>
	</div>
</section>



<%@ include file="../common/foot.jspf"%>