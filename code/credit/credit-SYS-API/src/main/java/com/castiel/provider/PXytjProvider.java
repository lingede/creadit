package com.castiel.provider;

import java.util.List;
import java.util.Map;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.PCardFlow;
import com.castiel.model.sys.PActEnrolBean;
import com.castiel.model.sys.PActRoundBean;
import com.castiel.model.sys.PBasicActBean;
import com.castiel.model.sys.PXytjActStatisticsBean;
import com.castiel.model.sys.PXytjCardFlowBean;
import com.castiel.model.sys.PXytjCreditStatisticsBean;
import com.github.pagehelper.PageInfo;

public interface PXytjProvider extends BaseProvider<PCardFlow> {
    public PageInfo<PXytjCardFlowBean> queryCardFlow(Map<String, Object> params);

    public PageInfo<PActRoundBean> queryCredit(Map<String, Object> params);

    public PageInfo<PBasicActBean> searchAct(Map<String, Object> params);

    public PageInfo<PActEnrolBean> searchActEnrol(Map<String, Object> params);

    public List<PXytjCardFlowBean> queryCardFlowAll(String deptId, String actStartDate, String actEndDate);

    public List<String> query1ScoreStu(String deptId, String reviewDateStart, String reviewDateEnd);

    public List<String> query2ScoreStu(String deptId, String reviewDateStart, String reviewDateEnd);

    public PageInfo<PXytjActStatisticsBean> queryActStatistics(Map<String, Object> params);

    public PXytjCreditStatisticsBean queryCreditStatistics(String deptId, String scoreCycleStartDate, String scoreCycleEndDate);

    public List<PActEnrolBean> searchEnrolList(Map<String, Object> params);
}
