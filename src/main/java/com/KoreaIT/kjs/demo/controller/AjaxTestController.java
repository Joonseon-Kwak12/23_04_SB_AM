package com.KoreaIT.kjs.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//만약 getAgeByName, postAgeByName 메소드에 @ResponseBody 붙이지 않을 경우, 준비된 경고창 'Request Error!' 띄워짐
//@ResponeBody를 붙이지 않으려면 @Controller 대신 @RestController를 붙여줘야 한다.
//하지만 이 경우 String ajaxTest1() 메소드가 제대로 동작하지 않는다. -> jsp 파일로 보내주지 않음
//https://mangkyu.tistory.com/49 - @Controller와 @RestController의 차이
@Controller
public class AjaxTestController {
	
	@RequestMapping("ajaxTest1")
	public String ajaxTest1() {
		return "/ajaxTest/ajaxTest1";
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
