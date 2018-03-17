package com.castiel.service.sys;

import java.util.List;
import java.util.Map;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.model.generator.SysRole;
import com.castiel.model.sys.SysRoleBean;
import com.castiel.provider.SysRoleProvider;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

/**
 * @author ShenHuaJie
 * @version 2016年5月31日 上午11:09:29
 */
@Service
public class SysRoleService extends BaseService<SysRoleProvider, SysRole> {
    @DubboReference
    public void setProvider(SysRoleProvider provider) {
        this.provider = provider;
    }

    public PageInfo<SysRoleBean> queryBean(Map<String, Object> params) {
        return provider.queryBean(params);
    }

    public List<String> getPermissions(String id) {
        return provider.getPermissions(id);
    }
    
    //查询用户详情
    public Map<String, Object> detail(String id){
    	return provider.detail(id);
    }
    
    //角色名重名验证
    public boolean checkRoleName(String roleId,String roleName){
    	return provider.checkRoleName(roleId, roleName);
    }
}
