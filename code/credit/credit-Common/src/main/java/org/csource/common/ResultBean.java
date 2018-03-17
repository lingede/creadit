package org.csource.common;

import java.util.List;

public class ResultBean {

	private Integer retcode;
	private String retmsg;
	private List<retData> retData;

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

	public List<retData> getRetData() {
		return retData;
	}

	public void setRetData(List<retData> retData) {
		this.retData = retData;
	}

	@Override
	public String toString() {
		return "ResultBean [retcode=" + retcode + ", retmsg=" + retmsg + ", retData=" + retData + "]";
	}



}
