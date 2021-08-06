package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	//전체리스트
	@RequestMapping(value = "/board/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
		System.out.println("[BoardController.list]");
		List<BoardVo> boardList = boardService.boardList(keyword);
		model.addAttribute("boardList", boardList);

		return "board/list";
	}
	
	//글쓰기폼
	@RequestMapping(value = "/board/writeForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String writeForm(HttpSession session) {
		System.out.println("[BoardController.writeForm]");
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		//로그인 안한경우 --> 메인
	    if(authUser == null) {
	    	return "redirect:/user/loginForm";
	    }
		
		return "board/writeForm";
	}
	
	//쓰기
	@RequestMapping(value = "/board/write", method = {RequestMethod.GET, RequestMethod.POST})
	public String write(@ModelAttribute BoardVo boardVo, HttpSession session) {
		System.out.println("[Boardcontroller.write]");
		int no = ((UserVo)session.getAttribute("authUser")).getNo();
		boardVo.setUserNo(no);
		boardService.boardInsert(boardVo);
		
		return "redirect:/board/list";
	}
	
	//글읽기 / 조회수++
	@RequestMapping(value = "/board/read", method = {RequestMethod.GET, RequestMethod.POST})
	public String read(@RequestParam("no") int no, Model model) {
		System.out.println("[BoardController.read]");
		String mode = "read";
		BoardVo boardVo = boardService.getBoard(no, mode);
		model.addAttribute("boardVo", boardVo);
		
		return "board/read";
	}
	
	//수정폼
	@RequestMapping(value = "/board/modifyForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(@RequestParam("no") int no, Model model, HttpSession session) {
		System.out.println("[BoardController.modifyForm]");
		String mode = "modify";
	    BoardVo boardVo = boardService.getBoard(no, mode);
	    UserVo authUser = (UserVo)session.getAttribute("authUser");
	    
	    //로그인 안한경우 --> 메인
	    if(authUser == null) {
	    	return "redirect:/main";
	    }
	    
	    //로그인사용자 == 글작성자
	    if(authUser.getNo() == boardVo.getUserNo()) { 
	    	model.addAttribute("boardVo", boardVo);
	    	return "board/modifyForm";
	    }else { //본인글 아닌경우
	    	return "redirect:/main";
	    }
	    
	}
	
	//수정
	@RequestMapping(value = "/board/modify", method = {RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute BoardVo boardVo, HttpSession session) {
		System.out.println("[BoardController.modify]");
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		boardVo.setUserNo(authUser.getNo());
		boardService.updateBoard(boardVo);
		
		return "redirect:/board/read?no=" + boardVo.getNo();
	}
	
	//삭제
	@RequestMapping(value = "/board/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("no") int no) {
		System.out.println("[BoardController.delete");
		boardService.delete(no);
		
		return "redirect:/board/list";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
