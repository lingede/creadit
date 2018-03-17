package com.castiel.model.wechat;

import java.util.List;

import com.castiel.model.generator.WechatMenu;

@SuppressWarnings("serial")
public class WechatMenuBean extends WechatMenu {
	
	private List<WechatMenuBean>  menuBeans;
	
	private Integer leaf = 1;
	
	private String parentMenu;

	public String getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(String parentMenu) {
		this.parentMenu = parentMenu;
	}

	public List<WechatMenuBean> getMenuBeans() {
		return menuBeans;
	}

	public void setMenuBeans(List<WechatMenuBean> menuBeans) {
		this.menuBeans = menuBeans;
	}

	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}
}
