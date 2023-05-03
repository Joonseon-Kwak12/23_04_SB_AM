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
<!-- 댓글 폼 체크 스크립트 시작 -->
<script type="text/javascript">
	let ReplyWrite__submitFormDone = false;
	
	function ReplyWrite__submitForm(form) {
		
		if (ReplyWrite__submitFormDone) {
			return;
		}
		form.body.value = form.body.value.trim();
		
		if (form.body.value.length < 5) {
			alert("4글자 이상 입력하세요");
			form.body.focus();
			return;
		}
		
		ReplyWrite__submitFormDone = true;
		form.submit();
	}
</script>
<!-- 댓글 폼 체크 스크립트 끝 -->

<section class="w-5/6 mx-auto">
	<header class="articleInfo">
		<div class="articleInfoWrapper flex items-stretch border border-solid h-16">
			<div id=writer-image>ㅁㅁㅁ</div>
			<div class="grow">
				<div>작성자: ${article.extra__writer }</div>
				<div class="flex justify-start border border-solid gap-x-4">
					<div>글번호: ${article.id }</div>
					<div>작성일: ${article.regDate }</div>
					<div>수정일: ${article.updateDate }</div>
				</div>
			</div>
		</div>
	</header>
	<article class="article border border-solid border-amber-200">
		<div id="article-title-part">
			<h1 class="ml-4 mt-4 text-2xl">
				${article.title }
			</h1>
		</div>
		<div id="article-body-part">
			<div class="ml-4 mt-4">
				${article.body }
			</div>
			<div id="reaction-part" class="py-6">
				<div class="flex justify-between mx-auto w-40">
					<span>좋아요: ${article.goodReactionPoint }</span>
					<span>싫어요: ${article.badReactionPoint }</span>
				</div>
				<div class="mx-auto w-40">
					<c:if test="${rq.isLogined() }">
						<div class="flex justify-around">
							<c:choose>
								<c:when test="${actorReaction==1 }">
									<div class="bg-blue-500 px-2">
										<a
											href="/usr/reactionPoint/cancelReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
											class="btn btn-xs">∧</a>
									</div>
								</c:when>
								<c:otherwise>
									<div class="px-2 hover:text-blue-700 border border-solid">
										<a
											href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
											class="btn btn-xs">∧</a>
									</div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${actorReaction==-1 }">
									<div class="bg-red-500 px-2">
										<a
											href="/usr/reactionPoint/cancelReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
											class="btn btn-xs">∨</a>
									</div>
								</c:when>
								<c:otherwise>
									<div class="px-2 hover:text-red-500 border border-solid">
										<a
											href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
											class="btn btn-xs">∨</a>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>
				</div>


			</div>
		</div>
		<div id="article-modify-part" class="ml-4">
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
		<div id="reply-part">
			<div id="reply-write-window" class="mt-6 border-y border-solid border-amber-200">
				<form action="../reply/doWrite" method="post" onsubmit="ReplyWrite__submitForm(this); return false;" class="flex p-2 gap-4">
					<input type="hidden" name="relId" value="${article.id }"/>
					<input type="hidden" name="relTypeCode" value="article"/>
					<div>댓글</div>
					<c:choose>
						<c:when test="${rq.isLogined() }">
							<textarea placeholder="댓글 내용을 입력해주세요." name="body" rows="3" class="rounded resize-none grow border border-solid border-gray-400"></textarea>
						</c:when>
						<c:when test="${!rq.isLogined() }">
							<textarea disabled placeholder="로그인 후 이용 가능합니다." name="body" rows="3" class="rounded resize-none grow border border-solid border-gray-400"></textarea>
						</c:when>
					</c:choose>
					<button type="submit" class="w-24 h-6 border-solid border-gray-400 bg-black text-white text-sm rounded">댓글쓰기</button>
				</form>
			</div>
			<div id="reply-list">
				<ul class="divide-y divide-amber-200">
					<c:forEach var="reply" items="${replies }">
						<li>
							<div class="flex gap-x-11 my-2 justify-around text-base">
								<div>${reply.extra__writer }</div>
								<div>${reply.regDate.substring(0,16) }</div>
							</div>
							<div class="my-2 w-5/6 inline-block">
								<div class="break-all">${reply.body }</div>
							</div>
							<div class="inline-block mx-2 px-2]">
								<c:if test="${reply.actorCanModify }">
									<a href="../reply/modify?id=${reply.id }">수정</a>
								</c:if>
							</div>
							<div class="inline-block mx-2 px-2">
								<c:if test="${reply.actorCanDelete }">
									<a onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false;"
										href="../reply/doDelete?id=${reply.id }">삭제</a>
								</c:if>
							</div>

						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</article>
</section>
<hr />
<div class="h-10"></div>
<hr />
<div>
	<script type="text/javascript"> //https://hdg3052.tistory.com/87
		
	</script>

</div>
<div class="h-10"></div>
<hr />


<%@ include file="../common/foot.jspf"%>