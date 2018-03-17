package com.castiel.provider;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.dao.sys.SysUserMenuExpandMapper;
import com.castiel.model.generator.SysUserMenu;

/**
 * @author YangBin
 * @version 2016年10月13日 上午11:01:33
 */
@CacheConfig(cacheNames = "sysUserMenu")
@DubboService(interfaceClass = SysUserMenuProvider.class)
public class SysUserMenuProviderImpl extends BaseProviderImpl<SysUserMenu> implements SysUserMenuProvider {
	
	@Autowired
	private SysUserMenuExpandMapper sysUserMenuExpandMapper;

	@Transactional
	@CacheEvict(value = {"userMenuDetail" }, allEntries = true)
	public SysUserMenu insert(SysUserMenu record, String userId) {
		// TODO Auto-generated method stub
		try {
            record.setEnable(true);
            record.setCreateBy(userId);
            record.setCreateTime(new Date());
            record.setUpdateBy(userId);
            record.setUpdateTime(new Date());
            sysUserMenuExpandMapper.insert(record);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return record;
	}

}
