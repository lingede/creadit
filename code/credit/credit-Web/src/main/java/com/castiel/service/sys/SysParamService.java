package com.castiel.service.sys;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.model.generator.SysParam;
import com.castiel.provider.SysParamProvider;

import org.springframework.stereotype.Service;

/**
 * @author ShenHuaJie
 * @version 2016年5月31日 上午11:09:40
 */
@Service
public class SysParamService extends BaseService<SysParamProvider, SysParam> {
	@DubboReference
	public void setProvider(SysParamProvider provider) {
		this.provider = provider;
	}
}
