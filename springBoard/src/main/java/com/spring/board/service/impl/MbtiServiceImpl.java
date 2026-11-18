package com.spring.board.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.BoardDao;
import com.spring.board.service.MbtiService;
import com.spring.board.vo.BoardVo;
import com.spring.board.vo.PageVo;

@Service
public class MbtiServiceImpl implements MbtiService{
	
	@Autowired
	BoardDao boardDao;

	/**
	 * 선택 결과는 모든 질문의 유형점수를 각 지표에서	더 높은 점수를 받은성격 유형이라고 판단
		
		EI, IE, NS, SN, FT, TF, JP, PJ
		
		EI 의 질문인 경우
		매우 동의 E 3 모르겠음 0 매우비동의 I 3
		
		합산 점수가 같거나 모두 0점인경우 사전순으로 빠른 유형 출력
	 */
	// mbtiAnswers::{FT_2=7, FT_1=7, TF_3=7, TF_2=7, ...} 유형_번호 = 점수
	@Override
	public String mbtiCalculate(Map<String, Integer> answers) throws Exception {
		// 유형 하나 당 점수 계산
	    Map<String, Integer> score = new HashMap<String, Integer>();
	    
	    for (Map.Entry<String, Integer> entry : answers.entrySet()) {
	    	// Key = FT_2, value = 7
	    	String type = entry.getKey().split("_")[0]; // type = FT
	    	int value = 4 - entry.getValue(); // 7 -> -4. (범위: 3 ~ 0 ~ -3)
	    	
	    	String frontType = type.substring(0, 1);
	    	String backType = type.substring(1, 2);
	    	
	    	if (value > 0) { // 앞유형
	    		if (score.get(frontType) == null) {
		    		score.put(frontType, value);
	    		} else {
	    			score.put(frontType, score.get(frontType) + value);
	    		}
	    	} else { // 뒷유형
	    		if (score.get(backType) == null) {
		    		score.put(backType, - value);
	    		} else {
	    			score.put(backType, score.get(backType) - value);
	    		}
	    	}
	    }
	    
		// 각 유형 페어에서 더 높은 점수를 받은 성격 유형 찾기
	    // EI, NS, FT, JP
	    String[][] pairs = { {"E","I"}, {"S","N"}, {"T","F"}, {"J","P"} };
	    
	    String result = "";
	    
	    for (String[] pair : pairs) {
	    	int front = 0;
	    	int back = 0;
	    	
	    	if (score.get(pair[0]) != null) {
	    		front = score.get(pair[0]);
	    	}
	    	if (score.get(pair[1]) != null) {
	    		back = score.get(pair[1]);
	    	}
	    	
	    	System.out.println("======================" + pair[0] + "=" + front + " vs " + pair[1] + "=" + back);
	    	
	    	if (front > back) {
	    		result += pair[0];
	    	} else if (back > front){
	    		result += pair[1];
	    	} else {
	    		if (pair[0].compareTo(pair[1]) < 0) {
	    			result += pair[0];
	    		}
	    		else {
	    			result += pair[1];
	    		}
	    	}
	    }
		
	    System.out.println("======================" + result);
		
		return result;
	}

	@Override
	public List<BoardVo> selectQuestionList(PageVo pageVo) throws Exception {
	    return boardDao.selectMbtiQuestionList(pageVo);
	}

	@Override
	public int selectQuestionCnt() throws Exception {
	    return boardDao.selectMbtiQuestionCnt();
	}
	
}
