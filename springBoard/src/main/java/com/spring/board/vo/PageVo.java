package com.spring.board.vo;

public class PageVo {
	
	private int pageNo = 0;
	
	// 한 페이지에 보여줄 개수
	private int pageSize = 10;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
