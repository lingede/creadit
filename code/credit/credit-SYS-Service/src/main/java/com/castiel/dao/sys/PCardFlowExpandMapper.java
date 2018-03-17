package com.castiel.dao.sys;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.castiel.core.base.BaseExpandMapper;
import com.castiel.model.sys.PCardFlowBean;

public interface PCardFlowExpandMapper extends BaseExpandMapper {

    Date storageTime();

    List<PCardFlowBean> round(@Param("begin") Date begin, @Param("end") Date end, @Param("contype") String contype);

    Integer countByActName(@Param("actName") String actName);

    PCardFlowBean getIsvalidAndReason(@Param("actName") String actName, @Param("stuId") String stuId);

    int qExiest(@Param("stuId") String stuId, @Param("actName") String actName);
}
