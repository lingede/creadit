package com.castiel.model.sys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.castiel.core.base.BaseModel;

@SuppressWarnings("serial")
public class PBasicActUnion extends BaseModel {

	protected final Logger logger = LogManager.getLogger(this.getClass());

	private String actEnrolStartTime;
	private String actEnrolEndTime;
	private String actStartTime;
	private String actEndTime;
	private String actName;
	private String deptId;
	private String actNo;
	private String actContent;
	private String actPlace;
	private String actCancleTime;
	private String actPeopleNumber;
	private String actTeacherNumber;
	private String actStudentNumber;

	public Date getActCancleTime() {
		return dateFormat(actCancleTime);
	}

	public void setActCancleTime(String actCancleTime) {
		this.actCancleTime = actCancleTime;
	}

	public String getActPeopleNumber() {
		return actPeopleNumber;
	}

	public void setActPeopleNumber(String actPeopleNumber) {
		this.actPeopleNumber = actPeopleNumber;
	}

	public String getActTeacherNumber() {
		return actTeacherNumber;
	}

	public void setActTeacherNumber(String actTeacherNumber) {
		this.actTeacherNumber = actTeacherNumber;
	}

	public String getActStudentNumber() {
		return actStudentNumber;
	}

	public void setActStudentNumber(String actStudentNumber) {
		this.actStudentNumber = actStudentNumber;
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

	public Date getActEnrolStartTime() {
		return dateFormat(actEnrolStartTime);
	}

	public void setActEnrolStartTime(String actEnrolStartTime) {
		this.actEnrolStartTime = actEnrolStartTime;
	}

	public Date getActEnrolEndTime() {
		return dateFormat(actEnrolEndTime);
	}

	public void setActEnrolEndTime(String actEnrolEndTime) {
		this.actEnrolEndTime = actEnrolEndTime;
	}

	public Date getActStartTime() {
		return dateFormat(actStartTime);
	}

	public void setActStartTime(String actStartTime) {
		this.actStartTime = actStartTime;
	}

	public Date getActEndTime() {
		return dateFormat(actEndTime);
	}

	public void setActEndTime(String actEndTime) {
		this.actEndTime = actEndTime;
	}

	private Date dateFormat(String goalDate) {
		Date date = new Date();
		String dateStr = goalDate.substring(4, goalDate.indexOf("GMT") - 1);
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm:ss", Locale.ENGLISH);
		logger.info("dateFormat source:" + dateStr);
		try {
			date = sdf.parse(dateStr);
			logger.info("dateFormat answer:" + date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}
