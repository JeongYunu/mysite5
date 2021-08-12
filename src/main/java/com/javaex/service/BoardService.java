package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	BoardDao boardDao;
	
	//게시판 페이징 연습용 리스트
	public Map<String, Object> boardList2(int crtPage, String keyword){
		System.out.println("[BoardService.boardList2]");
		final int listCnt = 10;
		
		//crtPage가 음수일경우 1로 처리
		crtPage = (crtPage > 0) ? crtPage : 1;
		
		//시작번호 계산기
		int startRnum = (crtPage - 1) * listCnt + 1;
		
		//끝번호 계산기
		int endRnum = crtPage * listCnt;
		
		//페이징 계산
		int totalCount = boardDao.selectTotalCnt(keyword);
		
		//페이지당 버튼 갯수
		int pageBtnCnt = 5;
		
		//마지막 버튼 번호
		int endPageBtnNo = (int)Math.ceil((crtPage/(double)pageBtnCnt)) * pageBtnCnt;
		
		//시작 버튼 번호
		int startPageBtnNo = endPageBtnNo - (pageBtnCnt - 1);
		
		//다음 화살표 표현 유무
		boolean next = false;
		if((endPageBtnNo * listCnt) < totalCount) {
			next = true;
		}else {
			// 다음 화살표 버튼이 없을때 endPageBtnNo를 다시 계산한
			endPageBtnNo = (int)Math.ceil(totalCount/(double)listCnt);
			//  검증
			//	totlaCount --> 127 (double)listCnt --> 10.0 // -- 127 / 10.0 = 12.7
		    //	(int)Math.ceil( 12.7 ) --> 13
		    //	endPageBtnNo = 13
		}
		
		//이전 화살표 표현 유무
		boolean prev = false;
		if(startPageBtnNo != 1) {
			prev = true;
		}
		
		//리스트 가져오기
		List<BoardVo> boardList = boardDao.boardList2(startRnum, endRnum, keyword);
		
		//리턴하기
		Map<String, Object> listMap = new HashMap<>();
		listMap.put("startPageBtnNo", startPageBtnNo);
		listMap.put("endPageBtnNo", endPageBtnNo);
		listMap.put("prev", prev);
		listMap.put("next", next);
		listMap.put("boardList", boardList);
		
		return listMap;
	}
	
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

		/*
		for(int i=0; i<127; i++) {
			boardVo.setTitle(i + "번째 제목 입니다.");
			boardVo.setContent(i + "번째 글 입니다.");
			boardDao.boardInsert(boardVo);
		}
		*/
		
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
