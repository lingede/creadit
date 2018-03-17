package com.castiel.provider;

import java.util.Map;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.dao.sys.SysParamExpandMapper;
import com.castiel.model.generator.SysParam;
import com.castiel.provider.SysParamProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.github.pagehelper.PageInfo;

/**
 * @author ShenHuaJie
 * @version 2016年5月31日 上午11:01:33
 */
@CacheConfig(cacheNames = "sysParam")
@DubboService(interfaceClass = SysParamProvider.class)
public class SysParamProviderImpl extends BaseProviderImpl<SysParam> implements SysParamProvider {
	@Autowired
	private SysParamExpandMapper sysParamExpandMapper;

	public PageInfo<SysParam> query(Map<String, Object> params) {
		startPage(params);
		return getPage(sysParamExpandMapper.query(params));
	}
}
