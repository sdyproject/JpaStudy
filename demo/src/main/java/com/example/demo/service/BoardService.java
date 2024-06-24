package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.BoardResponse;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.utils.JwtUtil;
import java.sql.Date;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	private final JwtUtil jwtUtil;
	
	private final RefreshService refreshService;
	
	public List<BoardResponse> findBoardByMember (Long id) {
		
		return boardRepository.findBoardByMember(id);
	}
	
	
	public String writeBoard(Board board, HttpServletRequest request) {
		String token = refreshService.extractTokenFromRequest(request);
		System.out.println(token);
		 Long id = jwtUtil.getId(token);
		 
		 String boardname = board.getBoardname();
		 String boardcontext = board.getBoardcontext();
		 Date boardwrite = board.getBoardwrite();
		 Date boardschedule = board.getBoardschedule();
		 
		 
		 Board data = new Board();
		 data.setBoardname(boardname);
		 data.setBoardcontext(boardcontext);
		 data.setBoardwrite(boardwrite);
		 data.setId(id);
		 data.setBoardschedule(boardschedule);
		 boardRepository.save(data);
		 
		 return "SUCCESS";
	}
	
	 
}
