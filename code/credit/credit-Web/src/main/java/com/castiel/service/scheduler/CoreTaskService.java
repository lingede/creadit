package com.castiel.service.scheduler;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.csource.common.ActBean;
import org.csource.common.ActList;
import org.csource.common.HttpUtil;
import org.csource.common.ResultBean;
import org.csource.common.retData;

import com.alibaba.fastjson.JSON;
import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.core.util.DateUtil;
import com.castiel.core.util.DateUtil.DATE_PATTERN;
import com.castiel.core.util.RedisUtil;
import com.castiel.model.generator.PActRound;
import com.castiel.model.generator.PBasicAct;
import com.castiel.model.generator.PCardFlow;
import com.castiel.model.sys.PCardFlowBean;
import com.castiel.model.sys.PScoreSetBean;
import com.castiel.provider.PCardFlowProvider;
import com.castiel.provider.scheduler.CoreTaskProvider;


@DubboService(interfaceClass = CoreTaskProvider.class)
public class CoreTaskService extends BaseService<PCardFlowProvider, PCardFlow> implements CoreTaskProvider {
    private final Logger logger = LogManager.getLogger();

    @DubboReference
    public void setProvider(PCardFlowProvider provider) {
        this.provider = provider;
    }

    @Override
    public void sysnch() {
        String begindate = DateUtil.format(provider.storageTime(), "yyyyMMddHHmmss");
        String enddate = DateUtil.getDateTime("yyyyMMddHHmmss");
        String param = "begindate=" + begindate + "&enddate=" + enddate;
        String ret = HttpUtil.sendPost("http://222.197.164.93/yktpre/services/activity/list", param);
        logger.info("ret:" + ret);
        ResultBean resultBean = JSON.parseObject(ret, ResultBean.class);
        if (resultBean != null && resultBean.getRetcode() == 0) {
            // 往数据库写入数据
            if (resultBean.getRetData().size() > 0) {
                for (retData data : resultBean.getRetData()) {
                    // 查询是否有对应数据(根据学号和活动id)
                    if (!provider.qExists(data.getStu_id(), data.getAct_name())) {
                        PCardFlow pCardFlow = new PCardFlow();
                        pCardFlow.setStorageTime(new Date());
                        pCardFlow.setActName(data.getAct_name());
                        pCardFlow.setActPlace(data.getAct_place());
                        try {
                            pCardFlow.setActTime(DateUtil.strToDateFormat(data.getAct_time()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        pCardFlow.setDeptName(data.getDept_name());
                        pCardFlow.setInvalidReason(data.getInvalid_reason());
                        pCardFlow.setIsvalid(data.getIsvalid());
                        String signTime = data.getAct_time() + data.getSign_time();
                        try {
                            pCardFlow.setSignTime(DateUtil.strlongToDateFormat(signTime));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        pCardFlow.setStuId(data.getStu_id());
                        pCardFlow.setStuName(data.getStu_name());
                        String type = data.getStu_type();
                        if ("本科生".equals(type)) {
                            pCardFlow.setStuType(1);
                        } else if ("硕士研究生".equals(type)) {
                            pCardFlow.setStuType(2);
                        } else if ("博士研究生".equals(type)) {
                            pCardFlow.setStuType(3);
                        } else if ("教职工".equals(type)) {
                            pCardFlow.setStuType(4);
                        } else {
                            pCardFlow.setStuType(5);
                        }
                        pCardFlow.setConType(data.getTypename());
                        provider.update(pCardFlow);
                    }
                }
                logger.info("------pcardFlow数据写入完成------");
                // 查询学分周期时间
                PScoreSetBean pScoreSetBean = provider.scycle();
                logger.info("start:" + pScoreSetBean.getStart() + ",end:" + pScoreSetBean.getEnd());
                Calendar now = Calendar.getInstance();
                String starttime = String.valueOf(now.get(Calendar.YEAR)) + "-" + pScoreSetBean.getStart();
                Date start = DateUtil.strToDate(starttime);
                String endtime = String.valueOf(now.get(Calendar.YEAR) + 1) + "-" + pScoreSetBean.getEnd();
                Date end = DateUtil.strToDate(endtime);
                // 查询统计信息
                List<PCardFlowBean> pCardFlowBeans = provider.round(start, end, "讲座");
                for (PCardFlowBean pCardFlowBean : pCardFlowBeans) {
                    String stuId = provider.qExists(pCardFlowBean.getStuId(), start, end);
                    if (stuId != null) {
                        provider.updateTotal(stuId, pCardFlowBean.getTotal());// 讲座总次数
                    } else {
                        PActRound pActRound = new PActRound();
                        String key = pActRound.getClass().getSimpleName();
                        String redisKey = "REDIS_TBL_" + key;
                        String dateTime = DateUtil.getDateTime(DATE_PATTERN.YYYYMMDDHHMMSSSSS);
                        String id = dateTime + RedisUtil.incr(redisKey);
                        pActRound.setId(id);
                        pActRound.setStuId(pCardFlowBean.getStuId());
                        pActRound.setDeptId(pCardFlowBean.getDeptName());
                        pActRound.setScoreCycleStartTime(start);
                        pActRound.setScoreCycleEndTime(end);
                        pActRound.setTotal(pCardFlowBean.getTotal());
                        pActRound.setTotal2(0);
                        provider.inserRound(pActRound);
                    }
                }

                List<PCardFlowBean> pCardFlowBeans2 = provider.round(start, end, "演出");
                for (PCardFlowBean pCardFlowBean : pCardFlowBeans2) {
                    String stuId = provider.qExists(pCardFlowBean.getStuId(), start, end);
                    if (stuId != null) {
                        provider.updateTotal2(stuId, pCardFlowBean.getTotal());// 演出总次数
                    } else {
                        PActRound pActRound = new PActRound();
                        String key = pActRound.getClass().getSimpleName();
                        String redisKey = "REDIS_TBL_" + key;
                        String dateTime = DateUtil.getDateTime(DATE_PATTERN.YYYYMMDDHHMMSSSSS);
                        String id = dateTime + RedisUtil.incr(redisKey);
                        pActRound.setId(id);
                        pActRound.setStuId(pCardFlowBean.getStuId());
                        pActRound.setDeptId(pCardFlowBean.getDeptName());
                        pActRound.setScoreCycleStartTime(start);
                        pActRound.setScoreCycleEndTime(end);
                        pActRound.setTotal2(pCardFlowBean.getTotal());
                        pActRound.setTotal(0);
                        provider.inserRound(pActRound);
                    }
                }

                logger.info("------更新统计信息完毕------");
            }
        }
    }

    @Override
    public void getact() {
        String begindate = DateUtil.format(provider.actStorageTime(), "yyyyMMddHHmmss");
        String enddate = DateUtil.getDateTime("yyyyMMddHHmmss");
        String param = "begindate=" + begindate + "&enddate=" + enddate;
        String ret = HttpUtil.sendPost("http://222.197.164.93/yktpre/services/activity/activitylist", param);
        ActList actList = JSON.parseObject(ret, ActList.class);
        if (actList != null && actList.getRetcode() == 0) {
            if (actList.getRetData().size() > 0) {
                for (ActBean act : actList.getRetData()) {
                    PBasicAct pBasicAct = new PBasicAct();
                    String key = pBasicAct.getClass().getSimpleName();
                    String redisKey = "REDIS_TBL_" + key;
                    String dateTime = DateUtil.getDateTime(DATE_PATTERN.YYYYMMDDHHMMSSSSS);
                    String id = dateTime + RedisUtil.incr(redisKey);
                    pBasicAct.setId(id);
                    pBasicAct.setStorageTime(new Date());
                    pBasicAct.setActName(act.getCon_name());
                    pBasicAct.setActNo(act.getCon_id());
                    pBasicAct.setActPlace(act.getRoom_name());
                    String start = act.getCon_begindate() + act.getCon_begintime();
                    String end = act.getCon_begindate() + act.getCon_endtime();
                    try {
                        pBasicAct.setActStartTime(DateUtil.strToDatehhmm(start));
                        pBasicAct.setActEndTime(DateUtil.strToDatehhmm(end));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // 根据学院名称查询id
                    pBasicAct.setDeptId(provider.getIdByDeptName(act.getDeptname()));
                    pBasicAct.setActContent(act.getCon_explain());
                    pBasicAct.setConType(act.getType_name());
                    provider.updateact(pBasicAct);
                }
                logger.info("---------活动更新完成-------------");
            }
        }
    }
}
