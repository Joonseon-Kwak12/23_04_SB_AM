package com.KoreaIT.kjs.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {

	public void join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);
// 오류 나서 안 됨 왜 안 되는지 파악하자
}
