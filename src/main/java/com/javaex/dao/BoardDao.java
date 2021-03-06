package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;
	
	//전체 게시물 갯수 구하기
	public int selectTotalCnt(String keyword) {
		System.out.println("[BoardDao.selectTotalCnt]");
		
		return sqlSession.selectOne("board.selectTotalCnt", keyword); 
	}
	
	//게시판 페이징 연습용 리스트
	public List<BoardVo> boardList2(int startRnum, int endRnum, String keyword){
		System.out.println("[BoardDao.boarList]");
		//System.out.println(startRnum + "~" + endRnum);
		
		Map<String, Object> pagingMap = new HashMap<>();
		pagingMap.put("startRnum", startRnum);
		pagingMap.put("endRnum", endRnum);
		pagingMap.put("keyword", keyword);
		System.out.println(pagingMap);
		
		return sqlSession.selectList("board.boardList2", pagingMap);
	}
	
	//전체리스트
	public List<BoardVo> boardList(String keyword) {
		System.out.println("[BoardDao.boardList]");

		return sqlSession.selectList("board.boardList", keyword);
	}
	
	//글쓰기
	public int boardInsert(BoardVo boardVo) {
		System.out.println("[BoardDao.boardInsert]");
		
		return sqlSession.insert("board.boardInsert", boardVo);
	}
	
	//조회수 올리기
	public int updateHit(int no) {
		System.out.println("[BoardDao.updateHit]");
		
		return sqlSession.update("board.updateHit", no);
	}
	
	//게시판 1개 정보 가져오기
	public BoardVo selectBoard(int no) {
		System.out.println("[BoardDao.selectBoard]");
		
		return sqlSession.selectOne("board.selectBoard", no);
	}
	
	//수정
	public int updateBoard(BoardVo boardVo) {
		System.out.println("[BoardDao.updateBoard]");
		
		return sqlSession.update("board.updateBoard", boardVo);
	}
	
	//삭제
	public int delete(int no) {
		System.out.println("[BoardDao.delete");
		
		return sqlSession.delete("board.delete", no);
	}
	
	
	
	
	
}
