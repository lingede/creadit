package com.castiel.dao.sys;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.castiel.core.base.BaseExpandMapper;
import com.castiel.model.generator.PActEnrol;
import com.castiel.model.sys.PActEnrolBean;

public interface PActEnrolExpandMapper extends BaseExpandMapper {

    Integer countById(@Param(value = "id") String id);

    PActEnrol isenrol(@Param(value = "stuid") String stuid, @Param(value = "actid") String actid);

    List<PActEnrolBean> search(Map<String, Object> params);

    List<PActEnrolBean> searchByStuId(@Param(value = "stuId") String stuId, @Param(value = "offset") int offset, @Param(value = "limit") int limit);

    void updateById(@Param(value = "actId") String actId, @Param(value = "stuId") String stuId, @Param(value = "isenrol") int isenrol);

    int enrollCount(@Param(value = "type") int type, @Param(value = "actId") String actId);
}
