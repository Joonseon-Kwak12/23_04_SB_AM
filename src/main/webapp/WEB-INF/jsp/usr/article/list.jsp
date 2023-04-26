<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${board.code != null}">
		<c:set var="pageTitle" value="${board.code }" />
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="전체 글 보기" />
	</c:otherwise>
</c:choose>
<%@ include file="../common/head.jspf"%>


<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box">
			<div>${articlesCount }개</div>

			<ul class="w-10/12 mx-auto">
				<li class="border-solid border-y-[1px] border-slate-400 border-collapse">
					<div class="flex gap-x-11 my-2">
						<div>번호</div>
						<div>작성자</div>
						<div>작성일</div>
					</div>
					<div class="font-semibold my-2">제목</div>
				</li>
				<c:forEach var="article" items="${articles }">
					<li style="border: solid rgb(161 161 170); border-width: 1px 0 1px 0; ">
					<!-- <li class="border-solid border-y-[1px] border-slate-400"> tailwind border가 안 돼서 임시 저장-->
					<!-- 자연스럽게 하려면 border 말고 devide로 가야하는 듯, okky에서도 테일윈드 divide 쓴 듯하다 -->
						<div class="flex gap-x-11 my-2">
							<div>${article.id}</div>
							<div>${article.extra__writer}</div>
							<div>${article.regDate.substring(0,16)}</div>
						</div>
						<div class="font-semibold my-2"><a class="hover:underline" href="../article/detail?id=${article.id}">${article.title}</a></div>
					</li>
				</c:forEach>
			</ul>
			
		</div>
		<a href="/usr/article/write" class="block w-24 h-6 bg-black text-white text-sm text-center rounded">글쓰기</a>
		<div> <!-- 게시판 바깥 공간 시작-->
		
			<div class="flex"> <!-- 페이지네이션 시작 -->
				<c:choose>
					<c:when test="${boardId != null}">
						<c:forEach begin="${startPage }" end="${endPage }" var="i">
							<a ${page==i ? 'class="text-pink-700"':'' } href="?boardId=${boardId }&page=${i }">${i }</a>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach begin="${startPage }" end="${endPage }" var="i">
							<a ${page==i ? 'class="text-pink-700"':'' } href="?page=${i }">${i }</a>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div> <!-- 페이지네이션 끝 -->
			
			<div>
				<form action="list">
					<input type="hidden" name="boardId" value="${boardId }"/>
					<input type="text" name="searchKeyword" class="rounded w-full" />
					<button type="submit" class="w-24 h-6 border-solid border-gray-400 bg-black text-white text-sm rounded">검색</button>
				</form>
			</div>
			
		</div> <!-- 게시판 바깥 공간 끝 -->
	</div>
	<div class="h-40"><!-- 스크롤 아래로 내리기 위한 빈 공간 --></div>
</section>


<%@ include file="../common/foot.jspf"%>