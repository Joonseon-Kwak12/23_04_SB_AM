package com.KoreaIT.kjs.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {
	
	// 에러 해결 -> xml 파일에서 join이 아니라 Join으로 적었었음, Pw가 아니라 PW로 적었었음
	public void join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);
}
