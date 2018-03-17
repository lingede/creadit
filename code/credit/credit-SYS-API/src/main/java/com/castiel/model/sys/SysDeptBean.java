package com.castiel.model.sys;

import com.castiel.model.generator.SysDept;

@SuppressWarnings("serial")
public class SysDeptBean extends SysDept {
	
	private String parentDept;

	public String getParentDept() {
		return parentDept;
	}

	public void setParentDept(String parentDept) {
		this.parentDept = parentDept;
	}
}
