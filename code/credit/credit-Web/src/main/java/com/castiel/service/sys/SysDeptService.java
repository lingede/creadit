package com.castiel.service.sys;

import java.util.Map;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.model.generator.SysDept;
import com.castiel.model.sys.SysDeptBean;
import com.castiel.provider.SysDeptProvider;
import com.github.pagehelper.PageInfo;

import org.springframework.stereotype.Service;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:16:07
 */
@Service
public class SysDeptService extends BaseService<SysDeptProvider, SysDept> {
	@DubboReference
	public void setProvider(SysDeptProvider provider) {
		this.provider = provider;
	}
	
	public PageInfo<SysDeptBean> queryBean(Map<String, Object> params){
		return provider.queryBean(params);
	}

	public Map<String, Object> detail(String id){
		return provider.detail(id);
	}
	
	//部门名称重名验证
    public boolean checkDeptName(String deptId,String deptName){
    	return provider.checkDeptName(deptId, deptName);
    }
}
