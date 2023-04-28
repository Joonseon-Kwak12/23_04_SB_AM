package com.KoreaIT.kjs.demo.repository;

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
}
