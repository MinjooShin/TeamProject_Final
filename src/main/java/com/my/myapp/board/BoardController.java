package com.my.myapp.board;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BoardController {
		@Autowired
		BoardDAO boardDAO;
		
		private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
		

		@RequestMapping(value = "/board/list", method = RequestMethod.GET)
		public String board(Locale locale, Model model) {
			logger.info("Welcome list page! The client locale is {}.", locale);
			
			Date date = new Date();
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
			
			String formattedDate = dateFormat.format(date);
			
			model.addAttribute("serverTime", formattedDate );
			model.addAttribute("list", boardDAO.getBoardList());
			
			return "list";
		}
		
		@RequestMapping(value="/board/add", method=RequestMethod.GET)
		public String addPost() {
			return "addpostform";
		}
		
		@RequestMapping(value="/board/addok", method=RequestMethod.POST)
		public String addPostOK(BoardVO vo) {
			int i = boardDAO.insertBoard(vo);
			if(i==0) System.out.println("데이터 추가 실패..");
			else System.out.println("데이터 추가 성공!!!");
			return "redirect:list";
		}
		
		@RequestMapping(value="/board/editpost/{id}", method=RequestMethod.GET)
		public String editPost(@PathVariable("id") int id, Model model) {
			BoardVO boardVO = boardDAO.getBoard(id);
			model.addAttribute("boardVO", boardVO);
			return "editform";
		}
		
		@RequestMapping(value="/board/rank", method=RequestMethod.GET)
		public String rankPost(Model model) {
			List<BoardVO> list = boardDAO.getBoardList_vote();
			model.addAttribute("list", list);
			return "rank";
		}
		
		@RequestMapping(value="/board/votepost/{id}", method=RequestMethod.GET)
		public String votePost(@PathVariable("id") int id, Model model) {
			BoardVO boardVO = boardDAO.getBoard(id);
			model.addAttribute("boardVO", boardVO);
			return "voteform";
		}
		
		@RequestMapping(value="/board/editok", method=RequestMethod.POST)
		public String editPostOK(BoardVO vo) {
			int i=boardDAO.updateBoard(vo);
			if(i==0) System.out.println("데이터 수정 실패.."); 
			else System.out.println("데이터 수정 성공!!!");
			return "redirect:list";
		}
		
		@RequestMapping(value="/board/voteok", method=RequestMethod.POST)
		public String votePostOK(BoardVO vo) {
			int i=boardDAO.voteBoard(vo);
			if(i==0) System.out.println("데이터 수정 실패.."); 
			else System.out.println("데이터 수정 성공!!!");
			return "redirect:list";
		}
		
		@RequestMapping(value="/board/deleteok/{id}", method=RequestMethod.GET)
		public String deletePost(@PathVariable("id") int id, Model model) {
			int i = boardDAO.deleteBoard(id);
			if(i==0) System.out.println("데이터 삭제 실패.."); 
			else System.out.println("데이터 삭제 성공!!!");
			return "redirect:../list";
		}
		
}
