package com.castiel.dao.sys;

import java.util.List;
import java.util.Map;

import com.castiel.core.base.BaseExpandMapper;
import com.castiel.model.generator.SysMenu;

public interface SysMenuExpandMapper extends BaseExpandMapper {
    /** 获取所有权限 */
    public List<Map<String, String>> getPermissions();
    
    public List<SysMenu> querySysMenusByParentId(String parentId);
    
    public List<SysMenu> getSysMenusByMenuName(String menuName);
}
