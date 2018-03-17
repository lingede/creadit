package com.castiel.provider;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.dao.sys.PActEnrolExpandMapper;
import com.castiel.dao.sys.PBasicActExpandMapper;
import com.castiel.dao.sys.PBasicDeptExpandMapper;
import com.castiel.dao.sys.PBasicStuExpandMapper;
import com.castiel.model.generator.PActEnrol;
import com.castiel.model.generator.PBasicAct;
import com.castiel.model.sys.PActEnrolBean;
import com.github.pagehelper.PageInfo;

@CacheConfig(cacheNames = "pActEnrol")
@DubboService(interfaceClass = PActEnrolProvider.class)
public class PActEnrolPoviderImpl extends BaseProviderImpl<PActEnrol> implements PActEnrolProvider {
    @Autowired
    private PActEnrolExpandMapper pActEnrolExpandMapper;
    @Autowired
    private PBasicDeptExpandMapper pBasicDeptExpandMapper;
    @Autowired
    private PBasicStuExpandMapper pBasicStuExpandMapper;
    @Autowired
    private PBasicActExpandMapper pBasicActExpandMapper;

    @Override
    public Integer countById(String id) {
        return pActEnrolExpandMapper.countById(id);
    }

    @Override
    public PActEnrol isenrol(String stuid, String actid) {
        return pActEnrolExpandMapper.isenrol(stuid, actid);
    }

    @Override
    public PageInfo<PActEnrolBean> search(Map<String, Object> params) {
        this.startPage(params);

        List<PActEnrolBean> list = pActEnrolExpandMapper.search(params);

        for (PActEnrolBean bean : list) {
            if (bean.getDeptId() != null) {
                bean.setDeptName(pBasicDeptExpandMapper.getDeptNameById(bean.getDeptId()));
            }
            if (bean.getStuId() != null) {
                bean.setStuName(pBasicStuExpandMapper.searchStuNameByStuId(bean.getStuId()));
            }
        }

        return new PageInfo<PActEnrolBean>(list);
    }

    @Override
    public Map<String, Object> getActsByStuId(String stuId, int offset, int limit) {
        // TODO Auto-generated method stub
        List<PActEnrolBean> pActEnrolBeans = pActEnrolExpandMapper.searchByStuId(stuId, offset, limit);
        for (PActEnrolBean pActEnrolBean : pActEnrolBeans) {
            PBasicAct pBasicAct = pBasicActExpandMapper.getActByActId(pActEnrolBean.getActId());
            pActEnrolBean.setActName(pBasicAct.getActName());
            Date nowDate = new Date();
            if (pActEnrolBean.getIsenrol() == 1) {
                // the value 0 if the argument Date is equal to this Date;
                // a value less than 0 if this Date is before the Date argument;
                // and a value greater than 0 if this Date is after the Date argument
				if (pBasicAct.getActCancleTime().compareTo(nowDate) > 0) {
                    pActEnrolBean.setStatus("10");
                    // 已报名且报名未结束
                } else if (pBasicAct.getActEndTime().compareTo(nowDate) > 0) {
                    pActEnrolBean.setStatus("11");
                    // 已报名，报名时间已结束，活动未结束
                } else {
                    pActEnrolBean.setStatus("12");
                    // 已报名且活动已结束
                }
            } else if (pActEnrolBean.getIsenrol() == 0) {
                if (pBasicAct.getActEnrolEndTime().compareTo(nowDate) > 0) {
                    pActEnrolBean.setStatus("00");
                    // 未报名且报名未结束
                } else if (pBasicAct.getActEndTime().compareTo(nowDate) > 0) {
                    pActEnrolBean.setStatus("01");
                    // 未报名，报名时间已结束，活动未结束
                } else {
                    pActEnrolBean.setStatus("02");
                    // 未报名且活动已结束
                }
            }
        }
        int total = pActEnrolBeans.size();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pActEnrolBeans", pActEnrolBeans);
        map.put("total", total);
        return map;
    }

    @Override
    public void updateById(String actId, String stuId, int isenrol) {
        pActEnrolExpandMapper.updateById(actId, stuId, isenrol);
    }

    @Override
    public int enrollCount(int type, String actid) {
        return pActEnrolExpandMapper.enrollCount(type, actid);
    }

    @Override
    public List<PActEnrolBean> searchEnrolList(Map<String, Object> params) {
        List<PActEnrolBean> list = pActEnrolExpandMapper.search(params);
        for (PActEnrolBean bean : list) {
            if (bean.getDeptId() != null) {
                bean.setDeptName(pBasicDeptExpandMapper.getDeptNameById(bean.getDeptId()));
            }
            if (bean.getStuId() != null) {
                bean.setStuName(pBasicStuExpandMapper.searchStuNameByStuId(bean.getStuId()));
            }
        }
        return list;
    }

}
