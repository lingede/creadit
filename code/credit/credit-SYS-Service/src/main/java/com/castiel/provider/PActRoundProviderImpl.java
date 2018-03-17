package com.castiel.provider;



import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.core.util.DateUtil;
import com.castiel.dao.sys.PActRoundExpandMapper;
import com.castiel.dao.sys.PScoreSetExpandMapper;
import com.castiel.model.generator.PActRound;
import com.castiel.model.sys.PActRoundBean;
import com.castiel.model.sys.PScoreSetBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;


@DubboService(interfaceClass = PActRoundProvider.class)
@CacheConfig(cacheNames = "pActRound")
public class PActRoundProviderImpl extends BaseProviderImpl<PActRound> implements PActRoundProvider {

    private final Logger logger = LogManager.getLogger();
    @Autowired
    private PActRoundExpandMapper pActRoundExpandMapper;
    @Autowired
    private PScoreSetExpandMapper scoreSetExpandMapper;
    @DubboReference
    private PBasicStuProvider pBasicStuProvider;

    @Override
    public PageInfo<PActRoundBean> queryBean(Map<String, Object> params) {

        this.startPage(params);
        Page<String> actRoundIds = pActRoundExpandMapper.search(params);
        PageInfo<PActRoundBean> pageInfo = getPage(actRoundIds, PActRoundBean.class);

        for (PActRoundBean pActRoundBean : pageInfo.getList()) {
            if (pActRoundBean.getStuId() != null) {
                if (pBasicStuProvider.queryById(pActRoundBean.getStuId()) != null) {
                    pActRoundBean.setStuName(pBasicStuProvider.queryById(pActRoundBean.getStuId()).getStuName());
                }
            }
        }
        return pageInfo;
    }

    @Override
    public PageInfo<PActRoundBean> queryBean2(Map<String, Object> params) {

        this.startPage(params);
        Page<String> actRoundIds = pActRoundExpandMapper.search2(params);
        PageInfo<PActRoundBean> pageInfo = getPage(actRoundIds, PActRoundBean.class);

        for (PActRoundBean pActRoundBean : pageInfo.getList()) {
            if (pActRoundBean.getStuId() != null) {
                if (pBasicStuProvider.queryById(pActRoundBean.getStuId()) != null) {
                    pActRoundBean.setStuName(pBasicStuProvider.queryById(pActRoundBean.getStuId()).getStuName());
                }
            }
        }
        return pageInfo;
    }

    @Override
    public void review(String id, int isPass, Date date, String reviewReason, String deptId, String reviewName) {
        pActRoundExpandMapper.review(id, isPass, date, reviewReason, deptId, reviewName);
    }

    @Override
    public void review2(String id, int isPass, Date date, String reviewReason, String deptId, String reviewName) {
        pActRoundExpandMapper.review2(id, isPass, date, reviewReason, deptId, reviewName);
    }

    @Override
    public void reviewAll(int isPass, Date date, String reviewReason, String deptId, String reviewName) {
        pActRoundExpandMapper.reviewAll(isPass, date, reviewReason, deptId, reviewName);
    }

    @Override
    public void reviewAll2(int isPass, Date date, String reviewReason, String deptId, String reviewName) {
        pActRoundExpandMapper.reviewAll2(isPass, date, reviewReason, deptId, reviewName);
    }

    @Override
    public void reviewByStuId(String stuId, int isPass, Date date, String reviewReason, String deptId, String reviewName) {
        pActRoundExpandMapper.reviewByStuId(stuId, isPass, date, reviewReason, deptId, reviewName);
    }

    @Override
    public void reviewByStuId2(String stuId, int isPass, Date date, String reviewReason, String deptId, String reviewName) {
        pActRoundExpandMapper.reviewByStuId2(stuId, isPass, date, reviewReason, deptId, reviewName);
    }

    @Override
    public PActRoundBean searchByStuId(String stuId) {
        PActRoundBean bean = pActRoundExpandMapper.searchByStuId(stuId);
        if (bean != null && bean.getId() != null) {
            bean.setScoreApplySetValue1(scoreSetExpandMapper.queryScoreApplySetValue1());
            bean.setScoreApplySetValue2(scoreSetExpandMapper.queryScoreApplySetValue2());
            bean.setScoreApplySetValue3(scoreSetExpandMapper.queryScoreApplySetValue3());
            bean.setScoreApplySetValue4(scoreSetExpandMapper.queryScoreApplySetValue4());
        } else {
            PScoreSetBean scoreSetBean = scoreSetExpandMapper.scycle();
            if (scoreSetBean != null) {
                Calendar now = Calendar.getInstance();
                String starttime = String.valueOf(now.get(Calendar.YEAR)) + "-" + scoreSetBean.getStart();
                Date start = DateUtil.strToDate(starttime);
                String endtime = String.valueOf(now.get(Calendar.YEAR) + 1) + "-" + scoreSetBean.getEnd();
                Date end = DateUtil.strToDate(endtime);
                bean = new PActRoundBean();
                bean.setScoreCycleStartTime(start);
                bean.setScoreCycleEndTime(end);
                bean.setScoreApplySetValue1(scoreSetExpandMapper.queryScoreApplySetValue1());
                bean.setScoreApplySetValue2(scoreSetExpandMapper.queryScoreApplySetValue2());
                bean.setScoreApplySetValue3(scoreSetExpandMapper.queryScoreApplySetValue3());
                bean.setScoreApplySetValue4(scoreSetExpandMapper.queryScoreApplySetValue4());
                bean.setTotal(0);
                bean.setTotal2(0);
            }
        }
        return bean;
    }

    @Override
    public String qExists(String stuId, Date start, Date end) {
        return pActRoundExpandMapper.qExists(stuId, start, end);
    }

    @Override
    public void applyScore(int score, Date date, String stuId) {
        pActRoundExpandMapper.applyScore(score, date, stuId);
    }

    @Override
    public void applyScore2(int score, Date date, String stuId) {
        pActRoundExpandMapper.applyScore2(score, date, stuId);
    }

    @Override
    public Map<String, Object> searchByStuIdAndTime(String stuId, Date start, Date end) {
        PActRound pActRound = pActRoundExpandMapper.searchByStuIdAndTime(stuId, start, end);
        Map<String, Object> map = new HashMap<String, Object>();
        String message = "";
        if (pActRound != null) {
            map.put("total", pActRound.getTotal());
            map.put("total2", pActRound.getTotal2());
            map.put("affirmScore", pActRound.getAffirmScore());
            map.put("affirmScore2", pActRound.getAffirmScore2());
            map.put("scoreValue", pActRound.getScoreValue());
            map.put("scoreValue2", pActRound.getScoreValue2());
            if (pActRound.getReviewStatus() == 0) {
                message += "讲座学分申请管理员审核中<br/>";
            } else if (pActRound.getReviewStatus() == 1) {
                if (pActRound.getAffirmScore() == (pActRound.getScoreValue() == null ? 0 : Integer.parseInt(pActRound.getScoreValue()))) {
                    message += "讲座学分申请已审核通过<br/>";
                }
            } else if (pActRound.getReviewStatus() == 2) {
                message += "讲座学分申请未通过：" + pActRound.getReviewReason() + "<br/>";
            }

            if (pActRound.getReviewStatus2() == 0) {
                message += "演出学分申请管理员审中核<br/>";
            } else if (pActRound.getReviewStatus2() == 1) {
                if (pActRound.getAffirmScore2() == (pActRound.getScoreValue2() == null ? 0 : Integer.parseInt(pActRound.getScoreValue2()))) {
                    message += "演出学分申请已审核通过<br/>";
                }
            } else if (pActRound.getReviewStatus2() == 2) {
                message += "演出学分申请未通过：" + pActRound.getReviewReason2() + "<br/>";
            }
        } else {
            map.put("total", 0);
            map.put("total2", 0);
            map.put("affirmScore", 0);
            map.put("affirmScore2", 0);
            map.put("scoreValue", 0);
            map.put("scoreValue2", 0);
        }
        map.put("message", message);
        return map;
    }

    @Override
    public List<PActRoundBean> queryNonReviewStudent(String deptId) {
        List<PActRoundBean> list = pActRoundExpandMapper.queryStudentByReviewStatus("0", deptId);
        for (PActRoundBean pActRoundBean : list) {
            if (pActRoundBean.getStuId() != null) {
                if (pBasicStuProvider.queryById(pActRoundBean.getStuId()) != null) {
                    pActRoundBean.setStuName(pBasicStuProvider.queryById(pActRoundBean.getStuId()).getStuName());
                }
            }
        }
        return list;
    }

    @Override
    public List<PActRoundBean> queryNonReviewStudent2(String deptId) {
        List<PActRoundBean> list = pActRoundExpandMapper.queryStudentByReviewStatus2("0", deptId);
        for (PActRoundBean pActRoundBean : list) {
            if (pActRoundBean.getStuId() != null) {
                if (pBasicStuProvider.queryById(pActRoundBean.getStuId()) != null) {
                    pActRoundBean.setStuName(pBasicStuProvider.queryById(pActRoundBean.getStuId()).getStuName());
                }
            }
        }
        return list;
    }
}
