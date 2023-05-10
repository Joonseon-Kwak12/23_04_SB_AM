$('select[data-value]').each(function(index, el) { // list.jsp에서 검색 조건 때문에 넣은 스크립트이다
	// select 태그 중 data-value att.를 가진 요소를 선택
	const $el = $(el); // $el 변수 선언 - 위에서 받은 인자 el 할당 - 메소드 체이닝 사용하기 위해 준비한 것

	const defaultValue = $el.attr('data-value').trim(); 

	if (defaultValue.length > 0) {
		$el.val(defaultValue);
	}
});



function ajax() {
	var xhr = new XMLHttpRequest(); // XMLHttpRequest 객체 생성, 함수 내 지역변수로 선언 권장

	// onreadystatechange는 서버와의 통신이 끝났을 때 호출 (XMLHttpRequest xhr의 메소드임) 
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4) { // xhr 필드 readyState 0, 1, 2, 3, 4 중 4는 통신완료
			if (xhr.status === 200) { // xhr 필드 status 200은 통신성공
				console.log("통신 완료 및 성공")
			}
		}
	}
	// 세팅 - http메소드 / URL / true(비동기)-false(동기)
	xhr.open("POST", "usr/ajaxExample2", true);

	// HTTP 요청 헤더 설정
	// open다음에 사용해야함
	// POST 방식에서만 사용 
	// 서버로 전송할 데이터 타입의 형식(MIME)을 지정
	// xhr.setRequestHeader('Content-Type', "application/x-www-form-urlencoded");
	// - & 기호로 분리되고, = 기호로 값과 키를 연결하는 key-value 형태 전송시 사용
	// xhr.setRequestHeader("Content-Type", "application/json"); - JSON전송시 사용
	// xhr.setRequestHeader("Content-Type", "text/plain; charset=UTF-8");
	xhr.setRequestHeader(
		"Content-Type",
		"application/x-www-form-urlencoded"	
	);
	
	// send 메소드가 호출될 때 XMLHttpRequest 객체가 통신을 시작함
	// 데이터는 문자열로 raw하게 날아가므로, 반드시 해당 타입에 맞게 인코딩해야함
	// ex) xhr.send(JSON.parse(data));
	xhr.send(null);
}


/* popup 관련*/
$('.popup').click(function() {
	//	$('.layer').css('display','block');
	$('.layer').show();
	$('.layer-bg').show();
});

$('.close-btn').click(function() {
	//	$('.layer').css('display','none');
	$('.layer').hide();
	$('.layer-bg').hide();
});

$('.layer-bg').click(function() {
	//	$('.layer').css('display','none');
	$('.layer').hide();
	$('.layer-bg').hide();
});

