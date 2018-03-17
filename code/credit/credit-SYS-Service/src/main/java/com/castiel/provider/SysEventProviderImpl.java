package com.castiel.provider;

import java.util.Map;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.model.generator.SysEvent;
import com.castiel.provider.SysEventProvider;

import org.springframework.cache.annotation.CacheConfig;

import com.github.pagehelper.PageInfo;

@CacheConfig(cacheNames = "sysEvent")
@DubboService(interfaceClass = SysEventProvider.class)
public class SysEventProviderImpl extends BaseProviderImpl<SysEvent> implements SysEventProvider {
	
	public PageInfo<SysEvent> query(Map<String, Object> params) {
		return null;
	}
}
