package com.castiel.model.sys;

import com.castiel.model.generator.PCardFlow;

@SuppressWarnings("serial")
public class PXytjCardFlowBean extends PCardFlow {
	private String stuMajor;
	private String actNo;

	public String getStuMajor() {
		return this.stuMajor;
	}

	public String getActNo() {
		return this.actNo;
	}



	public void setStuMajor(String stuMajor) {
		this.stuMajor = stuMajor;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}


}
