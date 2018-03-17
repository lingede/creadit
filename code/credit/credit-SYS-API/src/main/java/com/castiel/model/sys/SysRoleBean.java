package com.castiel.model.sys;

import com.castiel.model.generator.SysRole;

@SuppressWarnings("serial")
public class SysRoleBean extends SysRole {
	private String permission;
	
	private String dept;

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
}
