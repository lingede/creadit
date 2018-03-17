package com.castiel.model.generator;

import java.util.List;

import com.castiel.model.sys.PBasicDeptBean;

public class PWechatStu {
	private PBasicStu stu;
	private List<PBasicDeptBean> departs;
	
	public PBasicStu getStu() {
		return stu;
	}
	public void setStu(PBasicStu stu) {
		this.stu = stu;
	}
	public List<PBasicDeptBean> getDeparts() {
		return departs;
	}
	public void setDeparts(List<PBasicDeptBean> departs) {
		this.departs = departs;
	}
	
	
}
