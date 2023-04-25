package com.KoreaIT.kjs.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.kjs.demo.repository.BoardRepository;
import com.KoreaIT.kjs.demo.vo.Board;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	
	public Board getBoardById(int boardId) {
		
		return boardRepository.getBoardById(boardId);
	}

}
