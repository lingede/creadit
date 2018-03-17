package com.castiel.dao.sys;



import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.castiel.core.base.BaseExpandMapper;
import com.castiel.model.generator.PActRound;
import com.castiel.model.sys.PActRoundBean;
import com.github.pagehelper.Page;

public interface PActRoundExpandMapper extends BaseExpandMapper {
    Page<String> search(Map<String, Object> params);

    Page<String> search2(Map<String, Object> params);

    void review(@Param(value = "id") String id, @Param(value = "isPass") int isPass, @Param(value = "date") Date date, @Param(value = "reviewReason") String reviewReason, @Param(value = "deptId") String deptId, @Param(value = "reviewName") String reviewName);

    void review2(@Param(value = "id") String id, @Param(value = "isPass") int isPass, @Param(value = "date") Date date, @Param(value = "reviewReason") String reviewReason, @Param(value = "deptId") String deptId, @Param(value = "reviewName") String reviewName);

    void reviewAll(@Param(value = "isPass") int isPass, @Param(value = "date") Date date, @Param(value = "reviewReason") String reviewReason, @Param(value = "deptId") String deptId, @Param(value = "reviewName") String reviewName);

    void reviewAll2(@Param(value = "isPass") int isPass, @Param(value = "date") Date date, @Param(value = "reviewReason") String reviewReason, @Param(value = "deptId") String deptId, @Param(value = "reviewName") String reviewName);

    void reviewByStuId(@Param(value = "stuId") String StuId, @Param(value = "isPass") int isPass, @Param(value = "date") Date date, @Param(value = "reviewReason") String reviewReason, @Param(value = "deptId") String deptId, @Param(value = "reviewName") String reviewName);

    void reviewByStuId2(@Param(value = "stuId") String StuId, @Param(value = "isPass") int isPass, @Param(value = "date") Date date, @Param(value = "reviewReason") String reviewReason, @Param(value = "deptId") String deptId, @Param(value = "reviewName") String reviewName);

    void applyScore(@Param(value = "score") int score, @Param(value = "date") Date date, @Param("stuId") String stuId);

    void applyScore2(@Param(value = "score") int score, @Param(value = "date") Date date, @Param("stuId") String stuId);

    List<PActRoundBean> queryStudentByReviewStatus(@Param(value = "reviewStatus") String reviewStatus, @Param(value = "deptId") String deptId);

    List<PActRoundBean> queryStudentByReviewStatus2(@Param(value = "reviewStatus") String reviewStatus, @Param(value = "deptId") String deptId);

    // 根据学号查询最新记录
    PActRoundBean searchByStuId(@Param(value = "stuId") String stuId);

    PActRound searchByStuIdAndTime(@Param("stuId") String stuId, @Param("start") Date start, @Param("end") Date end);

    // 根据学号查询计分周期内是否存在相应记录
    String qExists(@Param("stuId") String stuId, @Param("start") Date start, @Param("end") Date end);

    // 新增记录
    Integer inserRound(PActRound pActRound);

    // 更新讲坐活动次数
    Integer updateTotal(@Param("stuId") String stuId, @Param("total") Integer total);

    // 更新演出活动次数
    Integer updateTotal2(@Param("stuId") String stuId, @Param("total") Integer total);
}
