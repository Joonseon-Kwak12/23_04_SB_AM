package com.KoreaIT.kjs.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {

	@Select("""
			SELECT IFNULL(SUM(RP.point), 0)
			FROM reactionPoint AS RP
			WHERE RP.relTypeCode = #{relTypeCode}
			AND RP.relId = #{relId}
			AND RP.memberId = #{actorId}
			""")
	public int getSumReactionPointByMemberId(int actorId, String relTypeCode, int relId);

	@Insert("""
			INSERT INTO reactionPoint
			SET regDate = NOW(),
			updateDate = NOW(),
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			memberId = #{actorId},
			`point` = 1
			""")
	public int addGoodReactionPoint(int actorId, String relTypeCode, int relId);
	
	@Insert("""
			INSERT INTO reactionPoint
			SET regDate = NOW(),
			updateDate = NOW(),
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			memberId = #{actorId},
			`point` = -1
			""")
	public int addBadReactionPoint(int actorId, String relTypeCode, int relId);

	@Delete("""
			DELETE FROM reactionPoint
			WHERE memberId = #{loginedMemberId}
			AND relTypeCode = #{relTypeCode}
			AND relId = #{relId}
			""")
	public int cancelReaction(int loginedMemberId, String relTypeCode, int relId);
}
