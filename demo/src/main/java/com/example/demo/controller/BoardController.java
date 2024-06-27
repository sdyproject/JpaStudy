package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BoardResponse;
import com.example.demo.entity.Board;
import com.example.demo.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	
	
	
	@GetMapping("/board/{id}")
	public ResponseEntity<List<BoardResponse>> getBoardByMember (@PathVariable(name = "id") Long id){
	List<BoardResponse> boardlist = boardService.findBoardByMember(id);
	
	
	return ResponseEntity.ok(boardlist);
	}
	
	@PostMapping("/board")
    public ResponseEntity<String> writeBoard(HttpServletRequest request,@RequestBody Board board) {
       
		String token = request.getHeader("access");
		System.out.println("boardaccess:" +token);
		boardService.writeBoard(token, board);
        return ResponseEntity.ok().body("작성 완료");
    }
	
	
	
}
