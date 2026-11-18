package com.spring.board.service;

import java.util.List;
import java.util.Map;

import com.spring.board.vo.BoardVo;
import com.spring.board.vo.PageVo;

public interface MbtiService {
	
	public String mbtiCalculate(Map<String, Integer> answers) throws Exception;
	
	public List<BoardVo> selectQuestionList(PageVo pageVo) throws Exception;

	public int selectQuestionCnt() throws Exception;
}
