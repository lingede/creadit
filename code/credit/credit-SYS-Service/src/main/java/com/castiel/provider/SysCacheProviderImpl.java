package com.castiel.provider;

import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.core.util.RedisUtil;
import com.castiel.provider.SysCacheProvider;

@DubboService(interfaceClass = SysCacheProvider.class)
public class SysCacheProviderImpl implements SysCacheProvider {

	// 清缓存
	public void flush() {
		RedisUtil.delAll("*:sysDics:*");
		RedisUtil.delAll("*:sysDicMap:*");
		RedisUtil.delAll("*:getAuthorize:*");
		RedisUtil.delAll("*:sysPermission:*");
		RedisUtil.delAll("*:*:*");
	}
}