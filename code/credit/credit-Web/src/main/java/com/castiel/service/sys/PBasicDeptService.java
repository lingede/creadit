package com.castiel.service.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.model.generator.PBasicDept;
import com.castiel.model.sys.BasicActBean;
import com.castiel.model.sys.PBasicDeptBean;
import com.castiel.provider.PBasicDeptProvider;
import com.github.pagehelper.PageInfo;

@Service
public class PBasicDeptService extends BaseService<PBasicDeptProvider, PBasicDept> {
    @DubboReference
    public void setProvider(PBasicDeptProvider provider) {
        this.provider = provider;
    }

    public PageInfo<PBasicDeptBean> queryBean(Map<String, Object> params) {
        return provider.queryBean(params);
    }

    public Map<String, Object> detail(String id) {
        return provider.detail(id);
    }

    public List<PBasicDeptBean> getAllDepart() {
        return provider.qCombobox();
    }

    // 部门名称重名验证
    public boolean checkDeptName(String deptId, String deptName) {
        return provider.checkDeptName(deptId, deptName);
    }

    public boolean checkDeptNo(String deptId, String deptNo) {
        return provider.checkDeptNo(deptId, deptNo);
    }

    public boolean deleteDept(String deptId) {
        boolean isDelete = provider.delete(deptId);
        return isDelete;
    }

    public PageInfo<?> getDeptName(PageInfo<?> list) {
        List<?> basicActBeans = list.getList();
        for (int i = 0; i < basicActBeans.size(); i++) {
            BasicActBean basicActBean = (BasicActBean) basicActBeans.get(i);
            basicActBean.setDeptName(provider.getDeptNameById(basicActBean.getDeptId()));
        }
        return list;
    }

    public String getDeptIdByName(String deptName) {
        return provider.getDeptIdByName(deptName);
    }
}
