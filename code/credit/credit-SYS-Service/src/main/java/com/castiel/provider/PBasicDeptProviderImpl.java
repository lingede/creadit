package com.castiel.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.dao.sys.PBasicDeptExpandMapper;
import com.castiel.model.generator.PBasicDept;
import com.castiel.model.sys.PBasicDeptBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

@CacheConfig(cacheNames = "pBasicDept")
@DubboService(interfaceClass = PBasicDeptProvider.class)
public class PBasicDeptProviderImpl extends BaseProviderImpl<PBasicDept> implements PBasicDeptProvider {

    @Autowired
    private PBasicDeptExpandMapper pBasicDeptExpandMapper;

    @Override
    public List<PBasicDeptBean> qCombobox() {
        return pBasicDeptExpandMapper.qCombobox();
    }

    @Override
    public PageInfo<PBasicDeptBean> queryBean(Map<String, Object> params) {
        this.startPage(params);
        Page<String> ids = pBasicDeptExpandMapper.query(params);
        PageInfo<PBasicDeptBean> pageInfo = getPage(ids, PBasicDeptBean.class);
        return pageInfo;
    }

    @Override
    public Map<String, Object> detail(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (!id.trim().equals("0")) {
            PBasicDept record = queryById(id);
            returnMap.put("record", record);
        } else {
            PBasicDept record = new PBasicDept();
            returnMap.put("record", record);
        }
        return returnMap;
    }

    @Override
    public boolean checkDeptName(String deptId, String deptName) {
        // TODO Auto-generated method stub
        boolean isExist = false;
        List<PBasicDept> pBasicDepts = pBasicDeptExpandMapper.getPBasicDeptsByDeptName(deptName);
        if (pBasicDepts.size() > 0) {
            // id==0则为添加操作
            PBasicDept pBasicDept = pBasicDepts.get(0);
            if (deptId != null && !deptId.trim().equals("0")) {
                if (!pBasicDept.getId().trim().equals(deptId)) {
                    isExist = true;
                }
            } else {
                isExist = true;
            }
        } else {
            isExist = false;
        }
        return isExist;
    }

    @Override
    public boolean checkDeptNo(String deptId, String deptNo) {
        // TODO Auto-generated method stub
        boolean isExist = false;
        List<PBasicDept> pBasicDepts = pBasicDeptExpandMapper.getPBasicDeptsByDeptNo(deptNo);
        if (pBasicDepts.size() > 0) {
            // id==0则为添加操作
            PBasicDept pBasicDept = pBasicDepts.get(0);
            if (deptId != null && !deptId.trim().equals("0")) {
                if (!pBasicDept.getId().trim().equals(deptId)) {
                    isExist = true;
                }
            } else {
                isExist = true;
            }
        } else {
            isExist = false;
        }
        return isExist;
    }

    @Override
    public boolean delete(String deptId) {
        // TODO Auto-generated method stub
        int isDelete = pBasicDeptExpandMapper.deletePBasicDeptsByDeptId(deptId);
        if (isDelete > 0)
            return true;
        else
            return false;
    }

    @Override
    public String getDeptNameById(String deptId) {
        // TODO Auto-generated method stub
        return pBasicDeptExpandMapper.getDeptNameById(deptId);
    }

    @Override
    public String getDeptIdByName(String deptName) {
        return pBasicDeptExpandMapper.getDeptIdByName(deptName);
    }

}
