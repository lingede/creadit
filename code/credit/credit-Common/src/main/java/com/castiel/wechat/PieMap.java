package com.castiel.wechat;

import java.io.Serializable;

public class PieMap implements Serializable{
	private static final long serialVersionUID = 1L;
	private String label;
	private Long data;
	
	public PieMap(String label, Long data) {
		super();
		this.label = label;
		this.data = data;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Long getData() {
		return data;
	}
	public void setData(Long data) {
		this.data = data;
	} 
	
}
