<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보기</title>
<link rel="stylesheet" href="/resource/common.css" />
<script src="resource/common.js" defer="defer"></script>
<style>
	.tac {
		text-align: center;
	}
	header {
		display: flex;
		justify-content: space-around;
	}
</style>
</head>
<body>
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
</body>
</html>