package com.example.demo.repository;
import java.util.List;

import com.example.demo.dto.BoardResponse;
public interface BoardCustom {
	 List<BoardResponse> findBoardByMember(Long id);
}
