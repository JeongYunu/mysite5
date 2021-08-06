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
	public BoardVo getBoard(int no, String mode) {
		System.out.println("[BoardService.getBoard]");
		BoardVo boardVo = null;
		if("read".equals(mode)) {
			System.out.println("read");
			//조회수 올리기
			boardDao.updateHit(no);
			//읽기 게시판 정보 가져오기
			boardVo = boardDao.selectBoard(no);
		}else if("modify".equals(mode)) {
			System.out.println("modify");
			//수정폼 계시판 정보가져오
			boardVo = boardDao.selectBoard(no);
		}
		
		return boardVo;
	}
	
	/*
	//수정폼
	public BoardVo getBoardInfo(int no) {
		System.out.println("[BoardService.getBoardInfo]");

		//게시판 정보 가져오기
		BoardVo boardVo = boardDao.selectBoard(no);
		
		return boardVo;
	}
	*/
	
	//수정
	public int updateBoard(BoardVo boardVo) {
		System.out.println("[BoardService.updateBoard]");
		int count = boardDao.updateBoard(boardVo);
		
		return count;
	}
	
	//삭제
	public int delete(int no) {
		System.out.println("[BoardService.delete]");
		int count = boardDao.delete(no);
		
		return count;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
