package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.BoardResponse;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	
	public List<BoardResponse> findBoardByMember (Long id) {
		
		return boardRepository.findBoardByMember(id);
	}
	
	@Transactional
	public String writeBoard(Board board) {
		
		 String boardname = board.getBoardname();
		 String boardcontext = board.getBoardcontext();
		 
		 
		 
		 Board data = new Board();
		 data.setBoardname(boardname);
		 data.setBoardcontext(boardcontext);
		 boardRepository.save(data);
		 
		 return "SUCCESS";
	}
	
	 
}
