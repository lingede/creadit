package com.castiel.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.dao.generator.PBasicStuMapper;
import com.castiel.dao.sys.PBasicDeptExpandMapper;
import com.castiel.dao.sys.PBasicStuExpandMapper;
import com.castiel.model.generator.PBasicDept;
import com.castiel.model.generator.PBasicStu;
import com.castiel.model.sys.PBasicDeptBean;
import com.castiel.model.sys.PBasicStuBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

@CacheConfig(cacheNames = "pBasicStu")
@DubboService(interfaceClass = PBasicStuProvider.class)
public class PBasicStuProviderImpl extends BaseProviderImpl<PBasicStu> implements PBasicStuProvider {
    @Autowired
    private PBasicStuExpandMapper pBasicStuExpandMapper;
    @Autowired
    private PBasicStuMapper pBasicStuMapper;
    @Autowired
    private PBasicDeptProvider pbasicDeptProvider;
    @Autowired
    private PBasicDeptExpandMapper pBasicDeptExpandMapper;

    @Override
    public void SaveStuData(PBasicStu stu) {
        pBasicStuMapper.insert(stu);
    }

    @Override
    public boolean isExistThisStu(String stuId) {
        PBasicStu stu = pBasicStuMapper.selectByPrimaryKey(stuId);
        if (stu != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void AddStu(PBasicStu stu) {
        pBasicStuMapper.insert(stu);
    }

    @Override
    public List<PBasicStuBean> qCombobox() {
        return pBasicStuExpandMapper.qCombobox();
    }

    @Override
    public PageInfo<PBasicStuBean> queryBean(Map<String, Object> params) {
        this.startPage(params);
        Page<String> ids = pBasicStuExpandMapper.query(params);
        PageInfo<PBasicStuBean> pageInfo = getPage(ids, PBasicStuBean.class);
        for (PBasicStuBean stuBean : pageInfo.getList()) {
            PBasicDept dept = pbasicDeptProvider.queryById(stuBean.getDeptId());
            if (dept != null) {
                stuBean.setDeptName(dept.getDeptName());
            }

        }
        return pageInfo;
    }

    @Override
    public Map<String, Object> detail(String id) {
        // TODO Auto-generated method stub/* 获取所有部门信息 */
        List<PBasicDeptBean> pBasicDeptBeans = pBasicDeptExpandMapper.qCombobox();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (!id.trim().equals("0")) {
            PBasicStu record = queryById(id);
            returnMap.put("record", record);
            returnMap.put("pBasicDeptBeans", pBasicDeptBeans);
        } else {
            PBasicStu record = new PBasicStu();
            returnMap.put("record", record);
            returnMap.put("pBasicDeptBeans", pBasicDeptBeans);
        }
        return returnMap;
    }

    @Override
    public boolean delete(String stuID) {
        int isDelete = pBasicStuExpandMapper.deletePBasicStusByStuId(stuID);
        if (isDelete > 0)
            return true;
        else
            return false;
    }

    @Override
    public int getStuTypeById(String stuid) {
        return pBasicStuExpandMapper.getStuTypeById(stuid);
    }

    @Override
    public PBasicStuBean getStuById(String stuId) {
        return pBasicStuExpandMapper.getStuById(stuId);
    }

    @Override
    public String getDeptNameByStuId(String stuId) {
        return pBasicStuExpandMapper.getDeptNameByStuId(stuId);
    }

}
