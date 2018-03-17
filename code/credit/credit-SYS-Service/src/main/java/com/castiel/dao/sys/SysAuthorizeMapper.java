package com.castiel.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.castiel.model.generator.SysMenu;
import com.castiel.model.generator.SysRole;
import com.castiel.model.generator.SysRoleMenu;
import com.castiel.model.generator.SysUserMenu;
import com.castiel.model.generator.SysUserRole;

public interface SysAuthorizeMapper {

	void deleteUserMenu(String userId);

	void deleteUserRole(String userId);

	void deleteRoleMenu(String roleId);

	List<String> getAuthorize(String userId);

	List<String> queryPermissionByUserId(@Param("userId") String userId);
	
	/**角色权限查看辅助方法**/
	List<SysRoleMenu> querySysRoleMenusByRoleId(String roleId);
	
	SysUserRole querySysUserRoleByUserId(String userId);
	
	SysRole querySysRoleByUserId(String userId);
	
	List<SysMenu> queryMogamiSysMenusByRoleId(String roleId);
	
	List<SysMenu> querySysMenusByRoleIdAndParentId(String roleId,String parentId);
	
	List<SysRoleMenu> querySysRoleMenusByMenuId(String roleId,String menuId);
	
	/**用户权限查看辅助方法**/
	List<SysUserMenu> querySysUserMenusByUserId(String userId);
	
	List<SysMenu> queryMogamiSysMenusByUserId(String userId);
	
	List<SysMenu> querySysMenusByUserIdAndParentId(String userId,String parentId);
	
	List<SysUserMenu> querySysUserMenusByMenuId(String userId,String menuId);

}
