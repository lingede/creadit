package com.castiel.provider;

import java.util.List;
import java.util.Map;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.PActEnrol;
import com.castiel.model.sys.PActEnrolBean;
import com.github.pagehelper.PageInfo;

public interface PActEnrolProvider extends BaseProvider<PActEnrol> {
    public Integer countById(String id);

    public PageInfo<PActEnrolBean> search(Map<String, Object> params);

    PActEnrol isenrol(String stuid, String actid);

    public Map<String, Object> getActsByStuId(String stuId, int offset, int limit);

    public void updateById(String actId, String stuId, int isenrol);

    public int enrollCount(int type, String actid);

    public List<PActEnrolBean> searchEnrolList(Map<String, Object> params);
}
