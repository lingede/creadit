package com.castiel.dao.sys;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.castiel.core.base.BaseExpandMapper;
import com.castiel.model.sys.PActRoundBean;
import com.castiel.model.sys.PXytjActStatisticsBean;
import com.castiel.model.sys.PXytjCardFlowBean;
import com.github.pagehelper.Page;

public interface PXytjExpandMapper extends BaseExpandMapper {
    Page<String> cardFlowSearch(Map<String, Object> params);

    List<PActRoundBean> pActRoundSearch(Map<String, Object> params);

    List<PXytjCardFlowBean> queryCardFlowAll(@Param(value = "deptId") String deptId, @Param(value = "actStartDate") String actStartDate, @Param(value = "actEndDate") String actEndDate);

    List<String> query1ScoreStu(@Param(value = "deptId") String deptId, @Param(value = "reviewDateStart") String reviewDateStart, @Param(value = "reviewDateEnd") String reviewDateEnd);

    List<String> query2ScoreStu(@Param(value = "deptId") String deptId, @Param(value = "reviewDateStart") String reviewDateStart, @Param(value = "reviewDateEnd") String reviewDateEnd);

    List<PXytjActStatisticsBean> queryAct(Map<String, Object> params);

    Integer count0ScoreStu(@Param(value = "deptId") String deptId, @Param(value = "scoreCycleStartDate") String scoreCycleStartDate, @Param(value = "scoreCycleEndDate") String scoreCycleEndDate);

    Integer count1ScoreStu(@Param(value = "deptId") String deptId, @Param(value = "scoreCycleStartDate") String scoreCycleStartDate, @Param(value = "scoreCycleEndDate") String scoreCycleEndDate);

    Integer count2ScoreStu(@Param(value = "deptId") String deptId, @Param(value = "scoreCycleStartDate") String scoreCycleStartDate, @Param(value = "scoreCycleEndDate") String scoreCycleEndDate);

    Integer count0ScoreStu2(@Param(value = "deptId") String deptId, @Param(value = "scoreCycleStartDate") String scoreCycleStartDate, @Param(value = "scoreCycleEndDate") String scoreCycleEndDate);

    Integer count1ScoreStu2(@Param(value = "deptId") String deptId, @Param(value = "scoreCycleStartDate") String scoreCycleStartDate, @Param(value = "scoreCycleEndDate") String scoreCycleEndDate);

    Integer count2ScoreStu2(@Param(value = "deptId") String deptId, @Param(value = "scoreCycleStartDate") String scoreCycleStartDate, @Param(value = "scoreCycleEndDate") String scoreCycleEndDate);
}
