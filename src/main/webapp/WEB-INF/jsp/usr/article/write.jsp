<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE WRITE" />
<%@ include file="../common/head.jspf"%>
<hr />



<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form action="doWrite" method="post">
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
			<textarea name="body" rows="15" class="rounded w-full"></textarea>
			<button type="submit" class="w-24 h-6 border-solid border-gray-400 bg-black text-white text-sm rounded">작성 완료</button>
		</form>
	</div>
</section>



<%@ include file="../common/foot.jspf"%>