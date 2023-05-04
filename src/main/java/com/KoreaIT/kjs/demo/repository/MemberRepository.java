package com.KoreaIT.kjs.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
			""") // 어노테이션 이용할 때 여러줄 필요하면 큰따옴표 세번 """ 쿼리문 """
	public Member getMemberByLoginId(String loginId);

	@Select("""
			SELECT *
			FROM `member`
			WHERE `name` = #{name}
			AND email = #{email}
			""")
	public Member getMemberByNameAndEmail(String name, String email);
	
	@Update("""
			UPDATE `member`
			<set>
				<if test="loginPw != null">
					loginPw = #{loginPw},
				</if>
				<if test="name != null">
					name = #{name},
				</if>
				<if test="nickname != null">
					nickname = #{nickname},
				</if>
				<if test="cellphoneNum != null">
					cellphoneNum = #{cellphoneNum},
				</if>
				<if test="email != null">
					email = #{email},
				</if>
				updateDate= NOW()
			</set>
			WHERE id = #{id}
			""")
	public void modify(int id, String loginPw, String name, String nickname, String cellphoneNum, String email);
}
