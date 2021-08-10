package com.javaex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.GuestbookService;
import com.javaex.vo.GuestbookVo;

@Controller
@RequestMapping(value="/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService gBookService;
	
	//방명록 리스트
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {
		System.out.println("[gBookController.list]");
		List<GuestbookVo> list = gBookService.getList();
		//System.out.println(list);
		model.addAttribute("gBookVo", list);
		
		return "guestbook/addList";
	}
	
	//방명록 저장
	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write(@ModelAttribute GuestbookVo gBookVo) {
		System.out.println("[gBookController.write]");
		gBookService.write(gBookVo);
		
		return "redirect:/guestbook/list";
	}
	
	//삭제폼
	@RequestMapping(value = "/deleteForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteForm() {
		System.out.println("[gBookController.deleteForm]");
		
		return "/guestbook/deleteForm";
	}
	
	//삭제
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@ModelAttribute GuestbookVo gBookVo) {
		System.out.println("[gBookController.delete]");
		//System.out.println(gBookVo);
		gBookService.delete(gBookVo);
		
		return "redirect:/guestbook/list";
	}
	
	//ajax 방명록 메인페이지
	@RequestMapping(value = "/ajaxMain", method = { RequestMethod.GET, RequestMethod.POST })
	public String ajaxMain() {
		System.out.println("[gBookController.ajaxMain]");	
		
		
		return "/guestbook/ajaxList";
	}
	
	

}
