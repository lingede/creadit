package com.castiel.provider;

import java.util.List;
import java.util.Map;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.PBasicDept;
import com.castiel.model.sys.PBasicDeptBean;
import com.github.pagehelper.PageInfo;

public interface PBasicDeptProvider extends BaseProvider<PBasicDept> {

    List<PBasicDeptBean> qCombobox();

    public PageInfo<PBasicDeptBean> queryBean(Map<String, Object> params);

    public Map<String, Object> detail(String id);

    public boolean checkDeptName(String deptId, String deptName);

    public boolean checkDeptNo(String deptId, String deptNo);

    public boolean delete(String deptId);

    public String getDeptNameById(String deptId);

    String getDeptIdByName(String deptName);
}
