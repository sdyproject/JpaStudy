package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.BoardResponse;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.utils.JwtUtil;
import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	private final JwtUtil jwtUtil;
	

	
	public List<BoardResponse> findBoardByMember (Long id) {
		
		return boardRepository.findBoardByMember(id);
	}
	
	@Transactional
	public String writeBoard(String token,Board board) {
		
	
		 Long mid = jwtUtil.getId(token);
		 String boardname = board.getBoardname();
		 String boardcontext = board.getBoardcontext();
		 LocalDateTime localDateTime= LocalDateTime.now();
		 Date boardschedule = board.getBoardschedule();
		 
		 Board data = new Board();
		 data.setBoardname(boardname);
		 data.setBoardcontext(boardcontext);
		 data.setId(mid);
		 data.setBoardwrite(localDateTime);;
		 data.setBoardschedule(boardschedule);
		 boardRepository.save(data);
		 
		 return "SUCCESS";
	}
	
	 
}
