package com.castiel.model.generator;

public class AdminPageInfo {
	private int PageNum;
	private int Size = 0;
	private int Total = 0;
	private int Pages = 0;
	private int PageSize = 10;
	private String OrderBy = "id_";
	private int startRow = 0;
	private String dept = "";

	public int getPageNum() {
		return PageNum;
	}

	public void setPageNum(int pageNum) {
		PageNum = pageNum;
	}

	public int getSize() {
		return Size;
	}

	public void setSize(int size) {
		Size = size;
	}

	public int getTotal() {
		return Total;
	}

	public void setTotal(int total) {
		Total = total;
	}

	public int getPages() {
		return Pages;
	}

	public void setPages(int pages) {
		Pages = pages;
	}

	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	public String getOrderBy() {
		return OrderBy;
	}

	public void setOrderBy(String orderBy) {
		OrderBy = orderBy;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}


}
