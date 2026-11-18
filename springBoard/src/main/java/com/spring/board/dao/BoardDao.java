package com.spring.board.dao;

import java.util.List;

import com.spring.board.vo.BoardVo;
import com.spring.board.vo.PageVo;

public interface BoardDao {

	public String selectTest() throws Exception;

	public List<BoardVo> selectBoardList(PageVo pageVo) throws Exception;

	public BoardVo selectBoard(BoardVo boardVo) throws Exception;

	public int selectBoardCnt() throws Exception;

	public int boardInsert(BoardVo boardVo) throws Exception;
	
	/**
	 * 수정
	 */
	public int boardUpdate(BoardVo boardVo) throws Exception;

	/**
	 * 삭제
	 */
	public int boardDelete(BoardVo boardVo) throws Exception;

	/**
	 * MBTI 문항 목록
	 */
	public List<BoardVo> selectMbtiQuestionList(PageVo pageVo) throws Exception;

	/**
	 * MBTI 전체 문항 수
	 */
	public int selectMbtiQuestionCnt() throws Exception;
}
