package com.spring.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.board.HomeController;
import com.spring.board.service.MbtiService;
import com.spring.board.service.boardService;
import com.spring.board.vo.BoardVo;
import com.spring.board.vo.PageVo;

@Controller
public class MbtiController {
	
	@Autowired 
	boardService boardService;
	
	@Autowired
	MbtiService mbtiService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/mbti/mbtiQuestionList.do", method = RequestMethod.GET)
	public String mbtiQuestionList(Locale locale, Model model, PageVo pageVo,
			HttpSession session) throws Exception{
		
		model.addAttribute("answers", session.getAttribute("mbtiAnswers"));

	    if(pageVo.getPageNo() == 0){
	        pageVo.setPageNo(1);
	    }
	    pageVo.setPageSize(5);

	    List<BoardVo> questionList = mbtiService.selectQuestionList(pageVo);
	    
	    int totalCnt = mbtiService.selectQuestionCnt();
	    int totalPage = (totalCnt + pageVo.getPageSize() - 1) / pageVo.getPageSize();

	    model.addAttribute("questionList", questionList);
	    model.addAttribute("pageNo", pageVo.getPageNo());
	    model.addAttribute("totalPage", totalPage);

	    return "mbti/mbtiQuestionList";
	}
	
	@RequestMapping(value = "/mbti/mbtiAnswerAction.do", method = RequestMethod.POST) 
	public String mbtiAnswerAction(@RequestParam Map<String, String> params, HttpSession session) throws Exception{
		// 세션에서 응답 꺼내고, 없으면 새로 만들기
		Map<String, Integer> answers = (Map<String, Integer>) session.getAttribute("mbtiAnswers");
		if (answers == null) {
			answers = new HashMap<String, Integer>();
		}
		
		for (Map.Entry<String, String> e : params.entrySet()) {
			String entryKey = e.getKey();
			if (entryKey.startsWith("answer_")) { // "answer_EI_1".  answer_BOARD_TYPE_BOARD_NUM 형식
				String key = entryKey.substring("answer_".length()); // "EI_1".  BOARD_TYPE_BOARD_NUM
				answers.put(key, Integer.parseInt(e.getValue())); // {BOARD_TYPE_BOARD_NUM, 점수}
			}
		}
	    session.setAttribute("mbtiAnswers", answers);
	    
	    System.out.println("=============== mbtiAnswers::" + answers);
		
	    int pageNo = Integer.parseInt(params.get("pageNo"));
	    int totalCnt = mbtiService.selectQuestionCnt();
	    int totalPage = (totalCnt + 5 - 1) / 5;

	    if (pageNo < totalPage) {
	        return "redirect:/mbti/mbtiQuestionList.do?pageNo=" + (pageNo + 1);
	    }
	    return "redirect:/mbti/mbtiResult.do"; 
	}
	
	@RequestMapping(value = "/mbti/mbtiResult.do", method = RequestMethod.GET)
	public String mbtiResult(Model model, HttpSession session) throws Exception{
		Map<String, Integer> answers = (Map<String, Integer>) session.getAttribute("mbtiAnswers");

		model.addAttribute("result", mbtiService.mbtiCalculate(answers));
		
		return "mbti/mbtiResult";
	}
	
	
	@RequestMapping(value = "/mbti/mbtiRestart.do", method = RequestMethod.GET)
	public String mbtiRestart(HttpSession session) throws Exception {
	    session.removeAttribute("mbtiAnswers");
	    
	    return "redirect:/mbti/mbtiQuestionList.do";
	}
}
