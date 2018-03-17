package com.castiel.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.castiel.core.base.BaseExpandMapper;
import com.castiel.model.generator.PBasicDept;
import com.castiel.model.sys.PBasicStuBean;

public interface PBasicStuExpandMapper extends BaseExpandMapper {

    List<PBasicStuBean> qCombobox();


    List<PBasicDept> getPBasicStusByStuID(String stuID);

    String searchStuNameByStuId(@Param(value = "stuId") String stuId);

    int deletePBasicStusByStuId(String stuId);

    int getStuTypeById(String stuId);

    PBasicStuBean getStuById(@Param(value = "stuId") String stuId);

    String getDeptNameByStuId(@Param(value = "stuId") String stuId);

}
