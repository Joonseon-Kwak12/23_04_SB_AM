package com.KoreaIT.kjs.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kjs.demo.service.ArticleService;

//만약 getAgeByName, postAgeByName 메소드에 @ResponseBody 붙이지 않을 경우, 준비된 경고창 'Request Error!' 띄워짐
//@ResponeBody를 붙이지 않으려면 @Controller 대신 @RestController를 붙여줘야 한다.
//하지만 이 경우 String ajaxTest1() 메소드가 제대로 동작하지 않는다. -> jsp 파일로 보내주지 않음
//https://mangkyu.tistory.com/49 - @Controller와 @RestController의 차이
@Controller
public class AjaxTestController {
	
	@Autowired
	ArticleService articleService;
	
	@RequestMapping("/ajaxTest1")
	public String ajaxTest1() {
		return "/ajaxTest/ajaxTest1";
	}
	
	// https://jo-coder.tistory.com/31 ajax게시판 만들기(list,페이징,검색,selectbox)(JSON형태로 AJAX비동기 통신)
//	@RequestMapping("/ajaxTest2")
//	public String testListView() {
//		
//		return "/ajaxTest/ajaxListTest";
//	}
//	
//	@RequestMapping(value = "/ajaxList", produces = "application/json")
//	@ResponseBody
//	public ResponseEntity<HashMap<String, Object>> getTestList() throws Exception {
//
//		HashMap<String, Object> result = new HashMap<>();
//
//		// 전체 게시글 개수를 얻어와 listCnt에 저장
//		int listCnt = testServiceImpl.getBoardListCnt(search);
//
//		// 검색
//		search.pageInfo(listCnt);
//
//		// 페이징
//		result.put("pagination", search);
//
//		// 게시글 화면 출력
//		result.put("list", testServiceImpl.selectTest(search));
//
//		return ResponseEntity.ok();
//	}
	// https://jo-coder.tistory.com/31 끝
	
	@RequestMapping("ajaxArticleListTest")
	public String ajaxArticleListTest() {
		return "/ajaxTest/ajaxArticleListTest";
	}
	
	@GetMapping("/getAgeByName")
	@ResponseBody
	public Map<String, Object> getAgeByName(String inputName) {
		Map<String, Integer> ageMap = new HashMap<>();
		ageMap.put("tom", 10);
		ageMap.put("bob", 20);
		ageMap.put("kim", 30);
		
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put("name", inputName);
		returnMap.put("age", ageMap.get(inputName));
		
		return returnMap;
	}
	
	@PostMapping("/postAgeByName")
	@ResponseBody
    /* inputMap 파라미터를 받아, 미리 저장된 ageMap에서 해당 이름에 맵핑된 나이를 리턴해주는 메소드 */
	public Map<String,Object> postAgeByName(Map<String,Object> inputMap) {
		Map<String, Integer> ageMap = new HashMap<>();
		ageMap.put("tom", 10); ageMap.put("bob", 20); ageMap.put("kim", 30);

		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put("name", inputMap.get("name"));
		returnMap.put("age", ageMap.get(inputMap.get("name")));
		
		return returnMap;
	}

}
