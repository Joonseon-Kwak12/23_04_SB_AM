<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>ajaxTest1</title>
<script type="text/javascript">
	window.onload = function() {
		var httpRequest;
		/* button이 클릭되었을때 이벤트 */
		document.getElementById("ajaxCall1").addEventListener('click', () => {
			/* textBox에 작성된 name 데이터를 가져옴 */
			var inputName = document.getElementById("inputName1").value;
			/* 통신에 사용 될 XMLHttpRequest 객체 정의 */
			httpRequest = new XMLHttpRequest();
			/* httpRequest의 readyState가 변화했을때 함수 실행 */
		    httpRequest.onreadystatechange = () => {
		    	/* readyState가 Done이고 응답 값이 200일 때, 받아온 response로 name과 age를 그려줌 */
			    if (httpRequest.readyState === XMLHttpRequest.DONE) {
				      if (httpRequest.status === 200) {
				    	var result = httpRequest.response;
				        document.getElementById("name").innerText = result.name;
				        document.getElementById("age").innerText = result.age;
				      } else {
				        alert('Request Error!');
				      }
			    }
		    };
		    /* Get 방식으로 name 파라미터와 함께 요청 */
		    httpRequest.open('GET', '/getAgeByName?inputName=' + inputName);
		    /* Response Type을 Json으로 사전 정의 */
		    httpRequest.responseType = "json";
		    /* 정의된 서버에 요청을 전송 */
		    httpRequest.send();
		});
		
		
		/* button이 클릭되었을때 이벤트 */
		document.getElementById("ajaxCall2").addEventListener('click', () => {
			/* textBox에 작성된 name 데이터를 가져옴 */
			var inputName = document.getElementById("inputName2").value;
			/* 입력된 데이터 Json 형식으로 변경 */
			var reqJson = new Object();
			reqJson.name = inputName;
			/* 통신에 사용 될 XMLHttpRequest 객체 정의 */
			httpRequest = new XMLHttpRequest();
			/* httpRequest의 readyState가 변화했을때 함수 실행 */
		    httpRequest.onreadystatechange = () => {
		    	/* readyState가 Done이고 응답 값이 200일 때, 받아온 response로 name과 age를 그려줌 */
			    if (httpRequest.readyState === XMLHttpRequest.DONE) {
				      if (httpRequest.status === 200) {
				    	var result = httpRequest.response;
				        document.getElementById("name").innerText = result.name;
				        document.getElementById("age").innerText = result.age;
				      } else {
				        alert('request에 뭔가 문제가 있어요.');
				      }
				}
		    };
		    /* Post 방식으로 요청 */
		    httpRequest.open('POST', '/postAgeByName', true);
		    /* Response Type을 Json으로 사전 정의 */
		    httpRequest.responseType = "json";
		    /* 요청 Header에 컨텐츠 타입은 Json으로 사전 정의 */
		    httpRequest.setRequestHeader('Content-Type', 'application/json');
		    /* 정의된 서버에 Json 형식의 요청 Data를 포함하여 요청을 전송 */
		    httpRequest.send(JSON.stringify(reqJson));
			
		});
	}
	</script>
</head>
<body>
		<h1>AJAX GET TEST</h1>
		Name:
		<input type="text" id="inputName1"></input>
		<button id="ajaxCall1">호출</button>
		<!-- 응답 받은 name 및 age 데이터를 추가해서 작성할 간단한 결과 텍스트 -->
		<h2>
				<span id="name"></span>
				의 나이는
				<span id="age"></span>
				입니다.
		</h2>


		<h1>AJAX POST TEST</h1>
		Name:
		<input type="text" id="inputName2"></input>
		<button id="ajaxCall2">호출</button>
		<!-- 응답 받은 name 및 age 데이터를 추가해서 작성할 간단한 결과 텍스트 -->
		<h2>
				<span id="name"></span>
				의 나이는
				<span id="age"></span>
				입니다.
		</h2>
</body>
</html>