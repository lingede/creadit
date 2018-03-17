package com.castiel.service.sys;

import org.springframework.stereotype.Service;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.model.generator.SysUserRole;
import com.castiel.provider.SysUserRoleProvider;


/**
 * @author YangBin
 * @version 2016年10月17日 下午3:47:21
 */
@Service
public class SysUserRoleService extends BaseService<SysUserRoleProvider,SysUserRole> {
	
	@DubboReference
	private SysUserRoleProvider provider;

	public SysUserRoleProvider getProvider() {
		return provider;
	}

	public void setProvider(SysUserRoleProvider provider) {
		this.provider = provider;
	}
}
