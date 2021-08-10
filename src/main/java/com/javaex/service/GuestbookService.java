package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookDao gBookDao;
	
	//전체리스트
	public List<GuestbookVo> getList(){
		System.out.println("[gBookService.getList]");
		List<GuestbookVo> list = gBookDao.getList();
		return list;
	}
	
	//저장
	public int write(GuestbookVo gBookVo) {
		System.out.println("[gBookService.write]");
		int count = gBookDao.write(gBookVo);
		
		return count;
	}
	
	//삭제
	public int delete(GuestbookVo gBookVo) {
		System.out.println("[gBookService.delete]");
		int count = gBookDao.delete(gBookVo);
		
		return count;
	}
	
	//방명록 글 저장 --> 게시글 가져오기
	public GuestbookVo writeResultVo(GuestbookVo gBookVo) {
		System.out.println("[gBookService.writeResultVo]");
		//글저장
		int count = gBookDao.inserGuestbookKey(gBookVo);
		
		int no = gBookVo.getNo();	//방금 저장한 글 번호
		
		//글가져오기(방금등록한 번호)
		GuestbookVo resultVo = gBookDao.selectGuestbook(no);
		
		return resultVo;
	}
	
	
	
	
	
	
	
}
