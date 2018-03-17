package com.castiel.provider;

import java.util.List;
import java.util.Map;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.SysRole;
import com.castiel.model.sys.SysRoleBean;
import com.github.pagehelper.PageInfo;

public interface SysRoleProvider extends BaseProvider<SysRole> {
    public PageInfo<SysRoleBean> queryBean(Map<String, Object> params);

    /** 根据角色Id获取权限选项value */
    public List<String> getPermissions(String id);
    //查询角色详情
    public Map<String, Object> detail(String id);
    
    //角色名重名验证
    public boolean checkRoleName(String roleId,String roleName);
}
