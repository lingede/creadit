package com.castiel.provider;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.dao.sys.PScoreApplySetExpandMapper;
import com.castiel.model.generator.PScoreApplySet;

@CacheConfig(cacheNames = "PScoreAplly")
@DubboService(interfaceClass = PScoreApllyProvider.class)
public class PScoreApllyProviderImp extends BaseProviderImpl<PScoreApplySet> implements PScoreApllyProvider {

	@Autowired
	private PScoreApplySetExpandMapper pScoreApplySetExpandMapper;


	public String searchByScoreApplyTime(Date currentTime) {
		return pScoreApplySetExpandMapper.queryByScoreApplayTime(currentTime);
	}


}
