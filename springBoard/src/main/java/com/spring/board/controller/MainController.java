package com.spring.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	/**
	 * 메인 페이지 (게시판 / MBTI 선택)
	 */
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String main() throws Exception{
		return "home";
	}

	/**
	 * MBTI 질문 화면 이동
	 */
	@RequestMapping(value = "/mbti/mbtiQuestionView.do", method = RequestMethod.GET)
	public String mbtiQuestionView() throws Exception{
		return "mbti/mbtiQuestionView";
	}
}
