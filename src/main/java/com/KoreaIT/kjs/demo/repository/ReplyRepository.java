package com.KoreaIT.kjs.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.KoreaIT.kjs.demo.vo.Reply;

@Mapper
public interface ReplyRepository {
	
	@Insert("""
			INSERT INTO reply
			SET regDate = NOW(),
			updateDate = NOW(),
			memberId = #{actorId},
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			`body` = #{body};
			""")
	void writeReply(int actorId, String relTypeCode, int relId, String body);

	@Select("""
			SELECT LAST_INSERT_ID()
			""")
	int getLastInsertId();

	@Select("""
			SELECT R.*, M.nickname AS extra__writer
			FROM reply AS R
			INNER JOIN `member` AS M ON R.memberId = M.id
			WHERE relId = #{relId}
			""")
	List<Reply> getForPrintReplies(int actorId, String relTypeCode, int relId);

	@Select("""
				SELECT R.*
				FROM reply AS R
				WHERE R.id = #{id}
			""")
	Reply getReply(int id);

	@Delete("""
				DELETE FROM reply
				WHERE id = #{id}
			""")
	void deleteReply(int id);

	@Select("""
			SELECT R.*, M.nickname AS extra__writer
			FROM reply AS R
			INNER JOIN `member` AS M
			ON R.memberId = M.id
			WHERE R.id = #{id}
		""")
	Reply getForPrintReply(int id);
	
	@Update("""
			UPDATE reply
			<set>
				<if test="body != null and body != ''">`body` = #{body},</if>
				updateDate= NOW()
			</set>
			WHERE id = #{id}
			""")
	void modifyReply(int id, String body);
}
