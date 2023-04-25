package com.KoreaIT.kjs.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.KoreaIT.kjs.demo.vo.Board;

@Mapper
public interface BoardRepository {

	@Select("""
			SELECT *
			FROM board
			WHERE id = #{boardId}
			AND delStatus = 0;
			""")
	Board getBoardById(int boardId);
}
