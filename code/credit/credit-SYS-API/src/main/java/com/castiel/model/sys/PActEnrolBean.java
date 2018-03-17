package com.castiel.model.sys;

import com.castiel.model.generator.PActEnrol;

@SuppressWarnings("serial")
public class PActEnrolBean extends PActEnrol {

	private String deptName;
	private String stuName;
	private String status;
	private String actName;

	public String getDeptName() {
		return deptName;
	}

	public String getStuName() {
		return stuName;
	}



	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}
}
