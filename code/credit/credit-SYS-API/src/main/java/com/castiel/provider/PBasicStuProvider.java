package com.castiel.provider;

import java.util.List;
import java.util.Map;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.PBasicStu;
import com.castiel.model.sys.PBasicStuBean;
import com.github.pagehelper.PageInfo;

public interface PBasicStuProvider extends BaseProvider<PBasicStu> {
    public void SaveStuData(PBasicStu stu);

    public boolean isExistThisStu(String stuId);

    public void AddStu(PBasicStu stu);

    List<PBasicStuBean> qCombobox();

    public PageInfo<PBasicStuBean> queryBean(Map<String, Object> params);

    public Map<String, Object> detail(String id);

    public boolean delete(String stuID);

    public int getStuTypeById(String stuid);

    PBasicStuBean getStuById(String stuId);

    String getDeptNameByStuId(String stuId);
}
