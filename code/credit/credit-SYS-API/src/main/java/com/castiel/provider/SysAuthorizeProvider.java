package com.castiel.provider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.castiel.model.generator.SysRole;
import com.castiel.model.generator.SysUserRole;
import com.castiel.model.sys.SysMenuBean;

public interface SysAuthorizeProvider {

	public void updateUserRole(List<SysUserRole> sysUserRoles);

	public List<SysMenuBean> queryAuthorizeByUserId(String currentUserId, String userId);

	public List<String> queryPermissionByUserId(String userId);

	public LinkedHashMap<String, String> roleMenuDetail(String roleId, String userId);

	public void updateRoleMenu(String roleId, String permissionInfo, String userId);

	public void updateUserMenu(String userId, String permissionInfo, String currentUserId);

	public SysUserRole querySysUserRoleByUserId(String userId);

	public SysRole querySysRoleByUserId(String userId);

	public Map<String, Object> userRoleDetail(String userId);

	public LinkedHashMap<String, String> userMenuDetail(String currentUserId, String userId);
}
