package com.castiel.service.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.core.util.DateUtil;
import com.castiel.core.util.DateUtil.DATE_PATTERN;
import com.castiel.core.util.RedisUtil;
import com.castiel.model.generator.PBasicStu;
import com.castiel.model.sys.PBasicStuBean;
import com.castiel.provider.PBasicStuProvider;
import com.github.pagehelper.PageInfo;

@Service
public class PBasicStuService extends BaseService<PBasicStuProvider, PBasicStu> {

    @DubboReference
    public void setProvider(PBasicStuProvider provider) {
        this.provider = provider;
    }

    public void SaveStuData(PBasicStu stu) {
        stu.setId(createId("id_"));
        provider.SaveStuData(stu);
    }

    public void insertStu(PBasicStu stu) {
        provider.AddStu(stu);
    }

    public boolean IsExistThisStu(String stuId) {
        return provider.isExistThisStu(stuId);
    }

    public PageInfo<PBasicStuBean> queryBean(Map<String, Object> params) {
        return provider.queryBean(params);
    }

    public Map<String, Object> detail(String id) {
        return provider.detail(id);
    }

    public List<PBasicStuBean> getAllDepart() {
        return provider.qCombobox();
    }

    public boolean deleteStu(String stuId) {
        boolean isDelete = provider.delete(stuId);
        return isDelete;
    }

    public void updateStu(PBasicStu stu) {
        provider.update(stu);
    }

    public PBasicStuBean getStuById(String stuId) {
        return provider.getStuById(stuId);
    }

    public String getDeptNameByStuId(String stuId) {
        return provider.getDeptNameByStuId(stuId);
    }


    /** 生成主键策略 */
    private String createId(String key) {
        String redisKey = "REDIS_TBL_" + key;
        String dateTime = DateUtil.getDateTime(DATE_PATTERN.YYYYMMDDHHMMSSSSS);
        return dateTime + RedisUtil.incr(redisKey);
    }

    public int getStuTypeById(String stuid) {
        // TODO Auto-generated method stub
        return provider.getStuTypeById(stuid);
    }
}
