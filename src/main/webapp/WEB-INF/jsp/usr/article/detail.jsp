<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf"%>
<hr />



<!-- <iframe src="http://localhost:8081/usr/article/doIncreaseHitCountRd?id=2" frameborder="0"></iframe> -->
<!-- 조회수 관련 AJAX 스크립트 시작 -->
<script>
	const params = {}
	params.id = parseInt('${param.id}')
</script>
<script>
	function ArticleDetail__increaseHitCount() {
		const localStorageKey = 'article__' + params.id + '__alreadyView';

		if (localStorage.getItem(localStorageKey)) {
			return;
		}

		localStorage.setItem(localStorageKey, true);

		$.get('../article/doIncreaseHitCountRd', { // 해당 url에 get 메소드 요청
			id : params.id, // id로 params.id 넘기고
			ajaxMode : 'Y' // ajax 사용
		}, function(data) { // 통신 성공 시 실행할 콜백함수
			$('.article-detail__hit-count').empty().html(data.data1); // html 요소 중 article-detail__hit-count 클래스 가진 요소 선택해서, empty로 비운 후, 들어온 data에서 data.data1만 뽑아내서 html로 넣음  
		}, 'json'); // 형식은 json 형식 사용
	}

	$(function() { // 위 ArticleDetail__increaseHitCount()을 실행시킨다.
		// 연습 확인용 코드
		// setTimeout(ArticleDetail__increaseHitCount, 2000);
		// 실제로 넣을 코드
		ArticleDetail__increaseHitCount();
	})
</script>
<!-- 조회수 관련 AJAX 스크립트 끝 -->
<!-- 좋아요 관련 AJAX 스크립트 시작 -->
<!-- <script>
	function ArticleDetail__like() {
		
		$.post('../article/doIncreaseLike', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__like').empty().html(data.data1);
		},'json');
	}
	
	$(function() {
		ArticleDetail__increaseHitCount();
	})
</script> -->
<!-- 좋아요 관련 AJAX 스크립트 끝 -->

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
						<th>조회수</th>
						<td>
							<span class="article-detail__hit-count">${article.hitCount }</span>
						</td>
					</tr>
					<tr>
						<th>추천</th>
						<td>
							<span>좋아요: ${article.goodReactionPoint }</span>
							<span>싫어요: ${article.badReactionPoint }</span>

							<c:if test="${rq.isLogined() }">
								<div class="flex justify-around">
									<c:choose>
										<c:when test="${actorReaction==1 }">
											<div class="bg-blue-500 px-2">
												<a href="/usr/reactionPoint/cancelGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}" class="btn btn-xs">△</a>
											</div>
										</c:when>
										<c:otherwise>
											<div class="bg-blue-50 px-2">
												<a href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}" class="btn btn-xs">△</a>
											</div>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${actorReaction==-1 }">
											<div class="bg-red-500 px-2">
												<a href="/usr/reactionPoint/cancelBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}" class="btn btn-xs">▽</a>
											</div>
										</c:when>
										<c:otherwise>
											<div class="bg-red-50 px-2">
												<a href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}" class="btn btn-xs">▽</a>
											</div>
										</c:otherwise>
									</c:choose>
								</div>
							</c:if>

						</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>${article.title }</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>${article.body }</td>
					</tr>
				</tbody>

			</table>
		</div>
		<div class="btns">
			<button class="btn-text-link" type="button" onclick="history.back();">뒤로가기</button>
			<!-- 			<button class="btn-text-link article-detail__like" type="button" onclick="article_like();">좋아요</button>
			<button class="btn-text-link article-detail__dislike" type="button" onclick="article_dislike();">싫어요</button> -->
			<c:if test="${article.actorCanModify }">
				<button class="btn-text-link" type="button" onclick="location.href = '../article/modify?id=${article.id}'">수정</button>
			</c:if>
			<c:if test="${article.actorCanDelete }">
				<button class="btn-text-link" type="button"
					onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false; location.href = '../article/doDelete?id=${article.id}'">삭제</button>
			</c:if>
		</div>
	</div>
</section>



<%@ include file="../common/foot.jspf"%>