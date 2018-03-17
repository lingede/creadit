package com.castiel.service.sys;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.model.generator.PActEnrol;
import com.castiel.provider.PActEnrolProvider;

@Service
public class PActEnrolService extends BaseService<PActEnrolProvider, PActEnrol> {

    @DubboReference
    public void setProvider(PActEnrolProvider provider) {
        this.provider = provider;
    }

    public PActEnrol isenrol(String stuid, String actid) {
        return provider.isenrol(stuid, actid);
    }

    public Map<String, Object> getActsByStuId(String stuId, int offset, int limit) {
        return provider.getActsByStuId(stuId, offset, limit);
    }

    public void updateById(String actId, String stuId, int isenrol) {
        provider.updateById(actId, stuId, isenrol);
    }

    public int enrollCount(int type, String actid) {
        return provider.enrollCount(type, actid);
    }
}
