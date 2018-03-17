package com.castiel.provider;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.dao.generator.PBasicActMapper;
import com.castiel.dao.sys.PActRoundExpandMapper;
import com.castiel.dao.sys.PBasicActExpandMapper;
import com.castiel.dao.sys.PBasicDeptExpandMapper;
import com.castiel.dao.sys.PCardFlowExpandMapper;
import com.castiel.dao.sys.PScoreSetExpandMapper;
import com.castiel.model.generator.PActRound;
import com.castiel.model.generator.PBasicAct;
import com.castiel.model.generator.PBasicDept;
import com.castiel.model.generator.PCardFlow;
import com.castiel.model.sys.PCardFlowBean;
import com.castiel.model.sys.PScoreSetBean;

@CacheConfig(cacheNames = "pCardFlow")
@DubboService(interfaceClass = PCardFlowProvider.class)
public class PCardFlowProviderImpl extends BaseProviderImpl<PCardFlow> implements PCardFlowProvider {

    @Autowired
    private PCardFlowExpandMapper pCardFlowExpandMapper;

    @Autowired
    private PActRoundExpandMapper pActRoundExpandMapper;

    @Autowired
    private PScoreSetExpandMapper pScoreSetExpandMapper;

    @Autowired
    private PBasicActMapper pBasicActMapper;

    @Autowired
    private PBasicActExpandMapper pBasicActExpandMapper;

    @Autowired
    private PBasicDeptExpandMapper pBasicDeptExpandMapper;


    @Override
    public Date storageTime() {
        return pCardFlowExpandMapper.storageTime();
    }


    @Override
    public List<PCardFlowBean> round(Date begin, Date end, String contype) {
        return pCardFlowExpandMapper.round(begin, end, contype);
    }


    @Override
    public String qExists(String stuId, Date start, Date end) {
        return pActRoundExpandMapper.qExists(stuId, start, end);
    }


    @Override
    public boolean inserRound(PActRound pActRound) {
        return pActRoundExpandMapper.inserRound(pActRound) == 1 ? true : false;
    }


    @Override
    public boolean updateTotal(String stuId, Integer total) {
        return pActRoundExpandMapper.updateTotal(stuId, total) == 1 ? true : false;
    }

    @Override
    public boolean updateTotal2(String stuId, Integer total) {
        return pActRoundExpandMapper.updateTotal2(stuId, total) == 1 ? true : false;
    }


    @Override
    public PScoreSetBean scycle() {
        return pScoreSetExpandMapper.scycle();
    }

    @Override
    public Integer countByActName(String actName) {
        return pCardFlowExpandMapper.countByActName(actName);
    }


    @Override
    public int updateact(PBasicAct record) {
        return pBasicActMapper.insert(record);
    }


    @Override
    public Date actStorageTime() {
        return pBasicActExpandMapper.actStorageTime();
    }


    @Override
    public String getIdByDeptName(String deptName) {
        List<PBasicDept> depts = pBasicDeptExpandMapper.getPBasicDeptsByDeptName(deptName);
        if (depts != null && !depts.isEmpty()) {
            return depts.get(0).getId();
        }
        return null;
    }



    @Override
    public PCardFlowBean getIsvalidAndReason(String actName, String stuId) {
        PCardFlowBean res = pCardFlowExpandMapper.getIsvalidAndReason(actName, stuId);
        if (res == null) {
            res = new PCardFlowBean();
            res.setIsValid(0);
            res.setInvalidReason("没有找到到场记录");
        }
        return res;
    }


    @Override
    public boolean qExists(String stuId, String actName) {
        return pCardFlowExpandMapper.qExiest(stuId, actName) == 1 ? true : false;
    }

}
