package org.csource.common;

public class retData {

	private String stu_id;
	private String stu_name;
	private String dept_name;
	private String stu_type;
	private String act_name;
	private String act_time;
	private String act_place;
	private String sign_time;
	private Integer isvalid;
	private String invalid_reason;
	private String typename;

	public String getStu_id() {
		return stu_id;
	}

	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}

	public String getStu_name() {
		return stu_name;
	}

	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getStu_type() {
		return stu_type;
	}

	public void setStu_type(String stu_type) {
		this.stu_type = stu_type;
	}

	public String getAct_name() {
		return act_name;
	}

	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}

	public String getAct_time() {
		return act_time;
	}

	public void setAct_time(String act_time) {
		this.act_time = act_time;
	}

	public String getAct_place() {
		return act_place;
	}

	public void setAct_place(String act_place) {
		this.act_place = act_place;
	}

	public String getSign_time() {
		return sign_time;
	}

	public void setSign_time(String sign_time) {
		this.sign_time = sign_time;
	}

	public Integer getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(Integer isvalid) {
		this.isvalid = isvalid;
	}

	public String getInvalid_reason() {
		return invalid_reason;
	}

	public void setInvalid_reason(String invalid_reason) {
		this.invalid_reason = invalid_reason;
	}


	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	@Override
	public String toString() {
		return "retData [stu_id=" + stu_id + ", stu_name=" + stu_name + ", dept_name=" + dept_name + ", stu_type=" + stu_type + ", act_name=" + act_name + ", act_time=" + act_time + ", act_place=" + act_place + ", sign_time=" + sign_time + ", isvalid=" + isvalid + ", invalid_reason=" + invalid_reason + "]";
	}



}
