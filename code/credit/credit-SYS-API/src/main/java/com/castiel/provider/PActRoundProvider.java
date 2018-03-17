package com.castiel.provider;



import java.util.Date;
import java.util.List;
import java.util.Map;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.PActRound;
import com.castiel.model.sys.PActRoundBean;
import com.github.pagehelper.PageInfo;

public interface PActRoundProvider extends BaseProvider<PActRound> {
    public PageInfo<PActRoundBean> queryBean(Map<String, Object> params);

    public PageInfo<PActRoundBean> queryBean2(Map<String, Object> params);

    public void review(String id, int isPass, Date date, String reviewReason, String deptId, String reviewName);

    public void review2(String id, int isPass, Date date, String reviewReason, String deptId, String reviewName);

    public void reviewAll(int isPass, Date date, String reviewReason, String deptId, String reviewName);

    public void reviewAll2(int isPass, Date date, String reviewReason, String deptId, String reviewName);

    public void reviewByStuId(String stuId, int isPass, Date date, String reviewReason, String deptId, String reviewName);

    public void reviewByStuId2(String stuId, int isPass, Date date, String reviewReason, String deptId, String reviewName);

    public List<PActRoundBean> queryNonReviewStudent(String deptId);

    public List<PActRoundBean> queryNonReviewStudent2(String deptId);

    public PActRoundBean searchByStuId(String stuId);

    public Map<String, Object> searchByStuIdAndTime(String stuId, Date start, Date end);

    void applyScore(int score, Date date, String stuId);

    void applyScore2(int score, Date date, String stuId);

    String qExists(String stuId, Date start, Date end);

}
