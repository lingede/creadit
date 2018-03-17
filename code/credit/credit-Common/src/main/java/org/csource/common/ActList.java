package org.csource.common;

import java.util.List;

public class ActList {

	private Integer retcode;
	private String retmsg;
	private List<ActBean> retData;

	public Integer getRetcode() {
		return retcode;
	}

	public void setRetcode(Integer retcode) {
		this.retcode = retcode;
	}

	public String getRetmsg() {
		return retmsg;
	}

	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}

	public List<ActBean> getRetData() {
		return retData;
	}

	public void setRetData(List<ActBean> retData) {
		this.retData = retData;
	}


}
