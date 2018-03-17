package com.castiel.service.sys;

import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.provider.SysCacheProvider;
import org.springframework.stereotype.Service;

@Service
public class SysCacheService {
    @DubboReference
	private SysCacheProvider sysCacheProvider;

	public void flushCache() {
		sysCacheProvider.flush();
	}
}
