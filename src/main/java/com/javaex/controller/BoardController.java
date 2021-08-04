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
	public String writeForm() {
		System.out.println("[BoardController.writeForm]");
		
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
		BoardVo boardVo = boardService.getBoard(no);
		model.addAttribute("boardVo", boardVo);
		
		return "board/read";
	}
}
