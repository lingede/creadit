package com.castiel.model.generator;

import com.github.pagehelper.PageInfo;

public class AdministerPageInfo {
	private PageInfo<AdministerFrontPage> info1;
	private PageInfo<AdminFrontPage2> info2;

	public PageInfo<AdministerFrontPage> getInfo1() {
		return info1;
	}

	public void setInfo1(PageInfo<AdministerFrontPage> info1) {
		this.info1 = info1;
	}

	public PageInfo<AdminFrontPage2> getInfo2() {
		return info2;
	}

	public void setInfo2(PageInfo<AdminFrontPage2> info2) {
		this.info2 = info2;
	}



}
