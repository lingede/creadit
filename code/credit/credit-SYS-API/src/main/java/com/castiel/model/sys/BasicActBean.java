package com.castiel.model.sys;

import com.castiel.model.generator.PBasicAct;

@SuppressWarnings("serial")
public class BasicActBean extends PBasicAct {
	private String deptName;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
