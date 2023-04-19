<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf"%>


<hr />
<header>
	<h2 class=tac>${article.title }</h2>
</header>
<section>
	<header class=articleInfo>
		<div>글번호: ${article.id }</div>
		<div>작성일: ${article.regDate }</div>
		<div>수정일: ${article.updateDate }</div>
		<div>작성자: ${article.memberId }</div>
	</header>
	<article>
		${article.body }
	</article>
</section>


<%@ include file="../common/foot.jspf"%>