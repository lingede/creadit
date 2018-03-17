package com.castiel.service.sys;

import java.util.List;
import java.util.Map;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.model.generator.SysMenu;
import com.castiel.model.sys.SysMenuBean;
import com.castiel.provider.SysMenuProvider;
import com.github.pagehelper.PageInfo;

import org.springframework.stereotype.Service;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:16:07
 */
@Service
public class SysMenuService extends BaseService<SysMenuProvider, SysMenu> {
    @DubboReference
    public void setProvider(SysMenuProvider provider) {
        this.provider = provider;
    }

    public List<Map<String, String>> getPermissions() {
        return provider.getPermissions();
    }
    
    public PageInfo<SysMenuBean> queryBean(Map<String, Object> params){
    	return provider.queryBean(params);
    };
    
    //查询菜单详情
    public Map<String, Object> detail(String id){
    	return provider.detail(id);
    }
    
    public boolean checkMenuName(String id,String menuName){
    	return provider.checkMenuName(id,menuName);
    }
}
