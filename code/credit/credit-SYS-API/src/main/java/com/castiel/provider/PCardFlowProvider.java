package com.castiel.provider;

import java.util.Date;
import java.util.List;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.PActRound;
import com.castiel.model.generator.PBasicAct;
import com.castiel.model.generator.PCardFlow;
import com.castiel.model.sys.PCardFlowBean;
import com.castiel.model.sys.PScoreSetBean;

public interface PCardFlowProvider extends BaseProvider<PCardFlow> {

    Date storageTime();

    List<PCardFlowBean> round(Date begin, Date end, String contype);

    String qExists(String stuId, Date start, Date end);

    boolean qExists(String stuId, String actName);

    boolean inserRound(PActRound pActRound);

    boolean updateTotal(String stuId, Integer total);

    boolean updateTotal2(String stuId, Integer total);

    PScoreSetBean scycle();

    public Integer countByActName(String actName);

    public int updateact(PBasicAct act);

    Date actStorageTime();

    String getIdByDeptName(String deptName);

    PCardFlowBean getIsvalidAndReason(String actName, String stuId);
}
