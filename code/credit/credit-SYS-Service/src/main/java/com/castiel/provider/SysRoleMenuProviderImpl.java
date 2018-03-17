package com.castiel.provider;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.dao.sys.SysRoleMenuExpandMapper;
import com.castiel.model.generator.SysRoleMenu;


@CacheConfig(cacheNames = "sysRoleMenu")
@DubboService(interfaceClass = SysRoleMenuProvider.class)
public class SysRoleMenuProviderImpl extends BaseProviderImpl<SysRoleMenu> implements SysRoleMenuProvider {

	@Autowired
	private SysRoleMenuExpandMapper sysRoleMenuExpandMapper;

	@Transactional
	@CacheEvict(value = {"getAuthorize", "sysPermission", "roleMenuDetail"}, allEntries = true)
	public SysRoleMenu insert(SysRoleMenu record, String userId) {
		try {
			record.setEnable(true);
			record.setCreateBy(userId);
			record.setCreateTime(new Date());
			record.setUpdateBy(userId);
			record.setUpdateTime(new Date());
			sysRoleMenuExpandMapper.insert(record);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return record;
	}

}
