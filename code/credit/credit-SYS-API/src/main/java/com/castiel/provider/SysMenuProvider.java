package com.castiel.provider;

import java.util.List;
import java.util.Map;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.SysMenu;
import com.castiel.model.sys.SysMenuBean;
import com.github.pagehelper.PageInfo;

public interface SysMenuProvider extends BaseProvider<SysMenu> {

    /** 获取所有权限选项(value-text) */
    public List<Map<String, String>> getPermissions();
    
    public PageInfo<SysMenuBean> queryBean(Map<String, Object> params);
    
    //查询菜单详情
    public Map<String, Object> detail(String id);
    
    public List<SysMenu> querySysMenusByParentId(String parentId);
    
    public boolean checkMenuName(String id,String menuName);
}
