package com.castiel.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.dao.sys.PScoreSetExpandMapper;
import com.castiel.model.generator.PScoreSet;
import com.castiel.model.generator.PScoreSetApllyUnion;

@CacheConfig(cacheNames = "PScoreSet")
@DubboService(interfaceClass = PScoreSetProvider.class)
public class PScoreSetProviderImp extends BaseProviderImpl<PScoreSet> implements PScoreSetProvider {
	@Autowired
	private PScoreSetExpandMapper scoreSetExpandMapper;

	@Override
	public List<PScoreSet> TestFunction() {
		List<PScoreSet> list = scoreSetExpandMapper.testMapper();
		return list;
	}

	public String isAffirmTime(String currentTime) {
		return scoreSetExpandMapper.queryByCurrentTime(currentTime);
	}

	@Override
	public PScoreSetApllyUnion queryNewestData() {
		return scoreSetExpandMapper.queryNewestData();
	}


}
