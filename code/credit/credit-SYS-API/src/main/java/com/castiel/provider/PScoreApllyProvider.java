package com.castiel.provider;

import java.util.Date;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.PScoreApplySet;

public interface PScoreApllyProvider extends BaseProvider<PScoreApplySet> {
	String searchByScoreApplyTime(Date currentTime);

}
