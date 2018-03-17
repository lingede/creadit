package com.castiel.model.sys;

import java.util.List;

import com.castiel.model.generator.SysMenu;

@SuppressWarnings("serial")
public class SysMenuBean extends SysMenu {
	private Integer leaf = 1;
	private List<SysMenuBean> menuBeans;
	private String parentMenu;

	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}

	public List<SysMenuBean> getMenuBeans() {
		return menuBeans;
	}

	public void setMenuBeans(List<SysMenuBean> menuBeans) {
		this.menuBeans = menuBeans;
	}

	public String getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(String parentMenu) {
		this.parentMenu = parentMenu;
	}
}
