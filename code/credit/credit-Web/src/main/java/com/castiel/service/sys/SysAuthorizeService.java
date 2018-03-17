package com.castiel.service.sys;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castiel.core.util.WebUtil;
import com.castiel.model.generator.SysRole;
import com.castiel.model.generator.SysUserRole;
import com.castiel.model.sys.SysMenuBean;
import com.castiel.provider.SysAuthorizeProvider;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:16:00
 */
@Service
public class SysAuthorizeService {
    @Autowired
    private SysAuthorizeProvider sysAuthorizeProvider;

    public List<SysMenuBean> queryAuthorizeByUserId(String id) {
        String currentUserId = WebUtil.getCurrentUser();
        return sysAuthorizeProvider.queryAuthorizeByUserId(currentUserId, id);
    }

    public List<String> queryPermissionByUserId(String userId) {
        return sysAuthorizeProvider.queryPermissionByUserId(userId);
    }

    public void updateUserMenu(String userId, String permissionInfo) {
        // 获取当前操作用户
        String currentUserId = WebUtil.getCurrentUser();
        sysAuthorizeProvider.updateUserMenu(userId, permissionInfo, currentUserId);
    }

    public void updateUserRole(List<SysUserRole> sysUserRoles) {
        String userId = WebUtil.getCurrentUser();
        Date date = new Date();
        for (SysUserRole sysUserRole : sysUserRoles) {
            sysUserRole.setCreateBy(userId);
            sysUserRole.setCreateTime(date);
            sysUserRole.setUpdateBy(userId);
            sysUserRole.setUpdateTime(date);
        }
        sysAuthorizeProvider.updateUserRole(sysUserRoles);
    }

    /**
     * 用户管理点击编辑权限时查询用户权限
     */
    public LinkedHashMap<String, String> userMenuDetail(String userId) {
        // 查询当前登陆用户角色包含的权限,在页面中只能操作这些权限
        String currentUserId = WebUtil.getCurrentUser();
        return sysAuthorizeProvider.userMenuDetail(currentUserId, userId);
    }

    /**
     * 角色管理点击编辑权限时查询用户权限
     */
    public LinkedHashMap<String, String> roleMenuDetail(String roleId) {
        // 查询当前登陆用户角色包含的权限,在页面中只能操作这些权限
        String userId = WebUtil.getCurrentUser();
        return sysAuthorizeProvider.roleMenuDetail(roleId, userId);
    }

    public void updateRoleMenu(String roleId, String permissionInfo) {
        // 获取当前操作用户
        String userId = WebUtil.getCurrentUser();
        sysAuthorizeProvider.updateRoleMenu(roleId, permissionInfo, userId);
    }

    public SysUserRole querySysUserRoleByUserId(String userId) {
        return sysAuthorizeProvider.querySysUserRoleByUserId(userId);
    }

    public SysRole querySysRoleByUserId(String userId) {
        return sysAuthorizeProvider.querySysRoleByUserId(userId);
    }

    // 查询用户所属角色
    public Map<String, Object> userRoleDetail(String userId) {
        return sysAuthorizeProvider.userRoleDetail(userId);
    }
}
