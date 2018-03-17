package com.castiel.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.castiel.core.base.BaseExpandMapper;
import com.castiel.model.generator.PBasicDept;
import com.castiel.model.sys.PBasicDeptBean;

public interface PBasicDeptExpandMapper extends BaseExpandMapper {
    List<PBasicDeptBean> qCombobox();

    List<PBasicDept> getPBasicDeptsByDeptName(@Param(value = "deptName") String deptName);

    List<PBasicDept> getPBasicDeptsByDeptNo(@Param(value = "deptNo") String deptNo);

    int deletePBasicDeptsByDeptId(String deptId);

    String getDeptNameById(@Param(value = "deptId") String deptId);

    String getDeptIdByName(@Param(value = "deptName") String deptName);
}
