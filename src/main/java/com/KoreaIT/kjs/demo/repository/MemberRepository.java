package com.KoreaIT.kjs.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.KoreaIT.kjs.demo.vo.Member;

@Mapper
public interface MemberRepository {
	
	// 에러 해결 -> xml 파일에서 join이 아니라 Join으로 적었었음, Pw가 아니라 PW로 적었었음
	public void join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);
	
	public Member getMemberById(int id);

	public int getLastInsertId();
	
//	public int getCountOfLoginId(String loginId);

	@Select("""
			SELECT *
			FROM `member`
			WHERE loginId = #{loginId}
			""")
	public Member getMemberByLoginId(String loginId);
}
