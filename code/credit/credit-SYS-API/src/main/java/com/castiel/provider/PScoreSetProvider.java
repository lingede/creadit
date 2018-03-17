package com.castiel.provider;

import java.util.List;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.PScoreSet;
import com.castiel.model.generator.PScoreSetApllyUnion;

public interface PScoreSetProvider extends BaseProvider<PScoreSet> {

	public List<PScoreSet> TestFunction();

	public String isAffirmTime(String currentTime);
	
	public PScoreSetApllyUnion queryNewestData();
}
