package com.castiel.service.sys;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.model.generator.PScoreSet;
import com.castiel.model.generator.PScoreSetApllyUnion;
import com.castiel.provider.PScoreSetProvider;

@Service
public class PScoreSetWebService extends BaseService<PScoreSetProvider, PScoreSet> {

	@DubboReference
	public void setProvider(PScoreSetProvider provider) {
		this.provider = provider;
	}

	public List<PScoreSet> testFunction() {
		return provider.TestFunction();
	}

	// 查询是否是审核时间
	public int isAffirmTime() {
		String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String id = provider.isAffirmTime(currentTime);
		if (id == null) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public PScoreSetApllyUnion queryNewestData(){
		return this.provider.queryNewestData();
	}

}
