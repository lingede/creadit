package com.castiel.model.sys;

import java.io.Serializable;

public class PBasicActBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String actid;
	private String actName;
	private String deptId;
	private String actNo;
	private String actEnrolStartTime;
	private String actEnrolEndTime;
	private String actStartTime;
	private String actEndTime;
	private String actContent;
	private String actPlace;

	public String getActid() {
		return actid;
	}

	public void setActid(String actid) {
		this.actid = actid;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getActEnrolStartTime() {
		return actEnrolStartTime;
	}

	public void setActEnrolStartTime(String actEnrolStartTime) {
		this.actEnrolStartTime = actEnrolStartTime;
	}

	public String getActEnrolEndTime() {
		return actEnrolEndTime;
	}

	public void setActEnrolEndTime(String actEnrolEndTime) {
		this.actEnrolEndTime = actEnrolEndTime;
	}

	public String getActStartTime() {
		return actStartTime;
	}

	public void setActStartTime(String actStartTime) {
		this.actStartTime = actStartTime;
	}

	public String getActEndTime() {
		return actEndTime;
	}

	public void setActEndTime(String actEndTime) {
		this.actEndTime = actEndTime;
	}

	public String getActContent() {
		return actContent;
	}

	public void setActContent(String actContent) {
		this.actContent = actContent;
	}

	public String getActPlace() {
		return actPlace;
	}

	public void setActPlace(String actPlace) {
		this.actPlace = actPlace;
	}


}
