package com.castiel.service.sys;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.core.util.DateUtil;
import com.castiel.core.util.DateUtil.DATE_PATTERN;
import com.castiel.core.util.RedisUtil;
import com.castiel.model.generator.PScoreApplySet;
import com.castiel.provider.PScoreApllyProvider;

@Service
public class PScoreApplyWebService extends BaseService<PScoreApllyProvider, PScoreApplySet> {

	@DubboReference
	public void setProvider(PScoreApllyProvider provider) {
		this.provider = provider;
	}

	public String CreateId() {
		String redisKey = "REDIS_TBL_" + "scoreApplyId";
		String dateTime = DateUtil.getDateTime(DATE_PATTERN.YYYYMMDDHHMMSSSSS);
		return dateTime + RedisUtil.incr(redisKey);
	}

	public String searchByCurrentTime() {
		Date currentTime = new Date();
		String id = provider.searchByScoreApplyTime(currentTime);
		return id;
	}

}
