<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8" />
	<title>Ajax Test</title>
	<style type="text/css">
		.rs {
			border: 5px solid black;
			margin-top: 3px;
			padding: 20px;
			font-size: 2rem;
		}
	</style>
</head>
<body>
<h1>Ajax Test</h1>
<form action="./test">
	<input type="text" placeholder="정수 1"/>
	<input type="text" placeholder="정수 2"/>
	<input type="submit" placeholder="더하기"/>
</form>

<h2>더한 결과</h2>

<div class="rs"></div>
	
</body>
</html>