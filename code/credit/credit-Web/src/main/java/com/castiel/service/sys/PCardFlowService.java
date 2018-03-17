package com.castiel.service.sys;

import org.springframework.stereotype.Service;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.model.generator.PCardFlow;
import com.castiel.model.sys.PCardFlowBean;
import com.castiel.provider.PCardFlowProvider;

@Service
public class PCardFlowService extends BaseService<PCardFlowProvider, PCardFlow> {
	@DubboReference
	public void setProvider(PCardFlowProvider provider) {
		this.provider = provider;
	}

	public PCardFlowBean getIsvalidAndReason(String actName, String stuId) {
		// TODO Auto-generated method stub
		return provider.getIsvalidAndReason(actName, stuId);
	}


}
