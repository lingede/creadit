package com.castiel.provider;

import org.springframework.cache.annotation.CacheConfig;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.model.generator.SysUserRole;

/**
 * @author YangBin
 * @version 2016年10月17日 下午3:19:19
 */
@CacheConfig(cacheNames = "sysUserRole")
@DubboService(interfaceClass = SysUserRoleProvider.class)
public class SysUserRoleProviderImpl extends BaseProviderImpl<SysUserRole> implements SysUserRoleProvider {
	
}
