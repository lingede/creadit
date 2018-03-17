package com.castiel.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.castiel.core.base.BaseExpandMapper;
import com.castiel.model.generator.PScoreSet;
import com.castiel.model.generator.PScoreSetApllyUnion;
import com.castiel.model.sys.PScoreSetBean;

public interface PScoreSetExpandMapper extends BaseExpandMapper {

    public List<PScoreSet> testMapper();

    PScoreSetApllyUnion queryNewestData();

    PScoreSetBean scycle();

    // 查询当前时间是否在审核时间内
    String queryByCurrentTime(@Param("currentTime") String currentTime);

    Integer queryScoreApplySetValue1();

    Integer queryScoreApplySetValue3();

    Integer queryScoreApplySetValue2();

    Integer queryScoreApplySetValue4();
}
