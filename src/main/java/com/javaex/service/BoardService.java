package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	BoardDao boardDao;
	
	//전체리스트
	public List<BoardVo> boardList(String keyword) {
		System.out.println("[BoardService.boardList]");
		List<BoardVo> boardList = boardDao.boardList(keyword);
		
		return boardList;
	}
	
	//글쓰기
	public int boardInsert(BoardVo boardVo) {
		System.out.println("[BoardService.boardInsert]");
		int count = boardDao.boardInsert(boardVo);
		return count;
	}
	
	
	//read
	public BoardVo getBoard(int no) {
		System.out.println("[BoardService.getBoard]");
		//조회수 올리기
		boardDao.updateHit(no);
		
		//게시판 정보 가져오기
		BoardVo boardVo = boardDao.selectBoard(no);
		
		return boardVo;
	}
}
