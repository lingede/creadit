package com.castiel.provider;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.dao.sys.PXytjExpandMapper;
import com.castiel.model.generator.PCardFlow;
import com.castiel.model.sys.PActEnrolBean;
import com.castiel.model.sys.PActRoundBean;
import com.castiel.model.sys.PBasicActBean;
import com.castiel.model.sys.PXytjActStatisticsBean;
import com.castiel.model.sys.PXytjCardFlowBean;
import com.castiel.model.sys.PXytjCreditStatisticsBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

@DubboService(interfaceClass = PXytjProvider.class)
@CacheConfig(cacheNames = "pXytj")
public class PXytjProviderImpl extends BaseProviderImpl<PCardFlow> implements PXytjProvider {

    @Autowired
    private PXytjExpandMapper pXytjExpandMapper;
    @DubboReference
    private PBasicStuProvider pBasicStuProvider;
    @DubboReference
    private PBasicActProvider pBasicActProvider;
    @DubboReference
    private PActEnrolProvider pActEnrolProvider;
    @DubboReference
    private PCardFlowProvider pCardFlowProvider;



    public PageInfo<PXytjCardFlowBean> queryCardFlow(Map<String, Object> params) {
        this.startPage(params);

        Page<String> XytjCardFlowIds = pXytjExpandMapper.cardFlowSearch(params);
        PageInfo<PXytjCardFlowBean> pageInfo = getPage(XytjCardFlowIds, PXytjCardFlowBean.class);

        for (PXytjCardFlowBean pXytjCardFlowBean : pageInfo.getList()) {
            if (pXytjCardFlowBean.getStuId() != null) {
                if (pBasicStuProvider.queryById(pXytjCardFlowBean.getStuId()) != null) {
                    pXytjCardFlowBean.setStuMajor(pBasicStuProvider.queryById(pXytjCardFlowBean.getStuId()).getStuMajor());
                }
            }
            if (pXytjCardFlowBean.getActName() != null) {
                if (pBasicActProvider.queryActNoByActName(pXytjCardFlowBean.getActName()) != null) {
                    pXytjCardFlowBean.setActNo(pBasicActProvider.queryActNoByActName(pXytjCardFlowBean.getActName()));
                }
            }
        }
        return pageInfo;
    }


    public PageInfo<PBasicActBean> searchAct(Map<String, Object> params) {
        this.startPage(params);

        List<PBasicActBean> list = pBasicActProvider.searchAct(params);

        return new PageInfo<PBasicActBean>(list);
    }

    @Override
    public PageInfo<PActEnrolBean> searchActEnrol(Map<String, Object> params) {
        this.startPage(params);
        PageInfo<PActEnrolBean> pageInfo = pActEnrolProvider.search(params);
        return pageInfo;
    }



    public PageInfo<PActRoundBean> queryCredit(Map<String, Object> params) {
        this.startPage(params);
        List<PActRoundBean> beans = pXytjExpandMapper.pActRoundSearch(params);
        return new PageInfo<PActRoundBean>(beans);
    }

    public List<PXytjCardFlowBean> queryCardFlowAll(String deptId, String actStartDate, String actEndDate) {
        List<PXytjCardFlowBean> list = pXytjExpandMapper.queryCardFlowAll(deptId, actStartDate, actEndDate);
        return list;
    }

    public List<String> query1ScoreStu(String deptId, String reviewDateStart, String reviewDateEnd) {
        List<String> list = pXytjExpandMapper.query1ScoreStu(deptId, reviewDateStart, reviewDateEnd);
        return list;
    }

    public List<String> query2ScoreStu(String deptId, String reviewDateStart, String reviewDateEnd) {
        List<String> list = pXytjExpandMapper.query2ScoreStu(deptId, reviewDateStart, reviewDateEnd);
        return list;
    }

    public PageInfo<PXytjActStatisticsBean> queryActStatistics(Map<String, Object> params) {
        this.startPage(params);
        List<PXytjActStatisticsBean> list = pXytjExpandMapper.queryAct(params);
        for (PXytjActStatisticsBean bean : list) {
            bean.setEnrollment(pActEnrolProvider.countById(bean.getId()));
            bean.setArrival(pCardFlowProvider.countByActName(bean.getActName()));
        }
        return new PageInfo<PXytjActStatisticsBean>(list);
    }

    public PXytjCreditStatisticsBean queryCreditStatistics(String deptId, String scoreCycleStartDate, String scoreCycleEndDate) {
        PXytjCreditStatisticsBean bean = new PXytjCreditStatisticsBean();
        bean.setAmountOf0Score(pXytjExpandMapper.count0ScoreStu(deptId, scoreCycleStartDate, scoreCycleEndDate));
        bean.setAmountOf1Score(pXytjExpandMapper.count1ScoreStu(deptId, scoreCycleStartDate, scoreCycleEndDate));
        bean.setAmountOf2Score(pXytjExpandMapper.count2ScoreStu(deptId, scoreCycleStartDate, scoreCycleEndDate));
        bean.setAmountOf0Score2(pXytjExpandMapper.count0ScoreStu2(deptId, scoreCycleStartDate, scoreCycleEndDate));
        bean.setAmountOf1Score2(pXytjExpandMapper.count1ScoreStu2(deptId, scoreCycleStartDate, scoreCycleEndDate));
        bean.setAmountOf2Score2(pXytjExpandMapper.count2ScoreStu2(deptId, scoreCycleStartDate, scoreCycleEndDate));
        return bean;
    }


    @Override
    public List<PActEnrolBean> searchEnrolList(Map<String, Object> params) {
        return pActEnrolProvider.searchEnrolList(params);
    }

}
