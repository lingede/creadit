package com.castiel.model.sys;

import com.castiel.model.generator.PBasicAct;

@SuppressWarnings("serial")
public class PXytjActStatisticsBean extends PBasicAct {
	private Integer enrollment; // 报名人数
	private Integer arrival; // 到场人数

	public Integer getEnrollment() {
		return this.enrollment;
	}

	public Integer getArrival() {
		return this.arrival;
	}

	public void setEnrollment(Integer enrollment) {
		this.enrollment = enrollment;
	}

	public void setArrival(Integer arrival) {
		this.arrival = arrival;
	}
}
