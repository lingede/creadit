package com.castiel.model.generator;

import com.castiel.core.base.BaseModel;

@SuppressWarnings("serial")
public class AdminFrontPage2 extends BaseModel {
	private String DEPT_NO;
	private String DEPT_NAME;
	private String ACT_NAME;
	private String ACT_NO;
	private String ACT_START_TIME;
	private String ACT_END_TIME;
	private String APPLY_NUMBER;
	private String REALITY_NUMBER;
	private int ABSENT_NUMBER;

	public String getDEPT_NO() {
		return DEPT_NO;
	}

	public void setDEPT_NO(String dEPT_NO) {
		DEPT_NO = dEPT_NO;
	}


	public String getACT_NAME() {
		return ACT_NAME;
	}

	public void setACT_NAME(String aCT_NAME) {
		ACT_NAME = aCT_NAME;
	}

	public String getACT_NO() {
		return ACT_NO;
	}

	public void setACT_NO(String aCT_NO) {
		ACT_NO = aCT_NO;
	}

	public String getACT_START_TIME() {
		return ACT_START_TIME;
	}

	public void setACT_START_TIME(String aCT_START_TIME) {
		ACT_START_TIME = aCT_START_TIME;
	}

	public String getACT_END_TIME() {
		return ACT_END_TIME;
	}

	public void setACT_END_TIME(String aCT_END_TIME) {
		ACT_END_TIME = aCT_END_TIME;
	}

	public String getAPPLY_NUMBER() {
		return APPLY_NUMBER;
	}

	public void setAPPLY_NUMBER(String aPPLY_NUMBER) {
		APPLY_NUMBER = aPPLY_NUMBER;
	}

	public String getREALITY_NUMBER() {
		return REALITY_NUMBER;
	}

	public void setREALITY_NUMBER(String rEALITY_NUMBER) {
		REALITY_NUMBER = rEALITY_NUMBER;
	}

	public int getABSENT_NUMBER() {
		return ABSENT_NUMBER;
	}

	public void setABSENT_NUMBER(int aBSENT_NUMBER) {
		ABSENT_NUMBER = aBSENT_NUMBER;
	}

	public String getDEPT_NAME() {
		return DEPT_NAME;
	}

	public void setDEPT_NAME(String dEPT_NAME) {
		DEPT_NAME = dEPT_NAME;
	}

	public void caculateAbsentPeople() {
		this.ABSENT_NUMBER = Integer.parseInt(APPLY_NUMBER) - Integer.parseInt(REALITY_NUMBER);
	}

}
