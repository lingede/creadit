package com.castiel.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.util.InstanceUtil;
import com.castiel.dao.generator.TaskFireLogMapper;
import com.castiel.dao.generator.TaskGroupMapper;
import com.castiel.dao.generator.TaskSchedulerMapper;
import com.castiel.dao.scheduler.TaskSchedulerExpandMapper;
import com.castiel.model.generator.TaskFireLog;
import com.castiel.model.generator.TaskGroup;
import com.castiel.model.generator.TaskScheduler;
import com.castiel.model.scheduler.TaskSchedulerBean;
import com.castiel.scheduler.manager.DefaultSchedulerManager;

import org.quartz.CronExpression;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * @author ShenHuaJie
 * @version 2016年7月1日 上午11:34:59
 */
@Service
public class SchedulerService extends BaseProviderImpl<TaskGroup> {
    @Autowired
    private TaskSchedulerExpandMapper expandMapper;
    @Autowired
    private TaskGroupMapper taskGroupMapper;
    @Autowired
    private TaskSchedulerMapper taskSchedulerMapper;
    @Autowired
    private TaskFireLogMapper logMapper;
    @Autowired
    private DefaultSchedulerManager defaultSchedulerManager;

    @Cacheable("taskGroup")
    public TaskGroup getGroupById(String id) {
        return taskGroupMapper.selectByPrimaryKey(id);
    }

    @Cacheable("taskScheduler")
    public TaskScheduler getSchedulerById(String id) {
        return taskSchedulerMapper.selectByPrimaryKey(id);
    }

    @Cacheable("taskFireLog")
    public TaskFireLog getFireLogById(String id) {
        return logMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @CachePut("taskGroup")
    public TaskGroup updateGroup(TaskGroup record) {
        record.setEnable(true);
        if (StringUtils.isBlank(record.getId())) {
            record.setId(createId("TaskGroup"));
            record.setCreateTime(new Date());
            taskGroupMapper.insert(record);
        } else {
            record.setUpdateTime(new Date());
            taskGroupMapper.updateByPrimaryKey(record);
        }
        return record;
    }

    @Transactional
    @CachePut("taskScheduler")
    public TaskScheduler updateScheduler(TaskScheduler record) {
        record.setEnable(true);
        if (StringUtils.isBlank(record.getId())) {
            record.setId(createId("TaskScheduler"));
            record.setCreateTime(new Date());
            //任务的上次执行时间和下次執行時間start
            CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();  
            try {
				cronTriggerImpl.setCronExpression(record.getTaskCron());
			} catch (ParseException e) {
				e.printStackTrace();
			} 
            Calendar calendar = Calendar.getInstance();  
            Date now = calendar.getTime();  
            calendar.add(Calendar.YEAR,2);//把统计的区间段设置为从现在到2年后的今天（主要是为了方法通用考虑，如那些1个月跑一次的任务，如果时间段设置的较短就不足20条)  
            List<Date> dates = TriggerUtils.computeFireTimesBetween(cronTriggerImpl, null, now, calendar.getTime());//这个是重点，一行代码搞定~~  
            for(int i =0;i < dates.size();i ++){ 
                if(i == 0){//这个是提示的日期个数  
                   record.setTaskNextFireTime(dates.get(i));
                   break;  
                }  
            } 
            record.setTaskPreviousFireTime(now);
            ////任务的上次执行时间和下次執行時間end
            taskSchedulerMapper.insert(record);
        } else {
            record.setUpdateTime(new Date());
            //任务的上次执行时间和下次執行時間start
            CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();  
            try {
				cronTriggerImpl.setCronExpression(record.getTaskCron());
			} catch (ParseException e) {
				e.printStackTrace();
			} 
            Calendar calendar = Calendar.getInstance();  
            Date taskPreviousFireTime = record.getTaskPreviousFireTime();  
            calendar.add(Calendar.YEAR,2);//把统计的区间段设置为从现在到2年后的今天（主要是为了方法通用考虑，如那些1个月跑一次的任务，如果时间段设置的较短就不足20条)  
            List<Date> dates = TriggerUtils.computeFireTimesBetween(cronTriggerImpl, null, taskPreviousFireTime, calendar.getTime());//这个是重点，一行代码搞定~~  
            for(int i =0;i < dates.size();i ++){ 
                if(i == 0){//这个是提示的日期个数  
                   record.setTaskNextFireTime(dates.get(i));
                   break;  
                }  
            }
          //任务的上次执行时间和下次執行時間end
            taskSchedulerMapper.updateByPrimaryKey(record);
        }
        //重新装载任务
        try {
			defaultSchedulerManager.afterPropertiesSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return record;
    }
    
    @Transactional
    @CacheEvict(value = {"taskScheduler"}, allEntries = true)
    public void deleteScheduler(String id,String userId) {
    	try {
            TaskScheduler record = taskSchedulerMapper.selectByPrimaryKey(id);
            record.setEnable(false);
            record.setUpdateTime(new Date());
            record.setUpdateBy(userId);
            taskSchedulerMapper.updateByPrimaryKey(record);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public Map<String, Object> detail(String id){
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	if(!id.trim().equals("0")){
    		TaskScheduler record = taskSchedulerMapper.selectByPrimaryKey(id);
    		returnMap.put("record", record);
		}else{
			TaskScheduler record = new TaskScheduler();
			record.setGroupId(null);
			returnMap.put("record", record);
		}
		List<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		TaskGroup taskGroup = new TaskGroup();
		taskGroup.setId(null);
		taskGroup.setGroupName("--请选择任务组--");
		taskGroups.add(taskGroup);
		//循环把数据库的数据装入taskGroups中
		List<TaskGroup> this_taskGroups = taskGroupMapper.selectAll();
		for(TaskGroup this_taskGroup:this_taskGroups){
			taskGroups.add(this_taskGroup);
		} 
		returnMap.put("taskGroups",taskGroups);
		return returnMap;
    }
    
    //taskCron验证
    public boolean validTaskCron(String taskCron) {
    	return CronExpression.isValidExpression(taskCron);
    }

    @Transactional
    @CachePut("taskFireLog")
    public TaskFireLog updateLog(TaskFireLog record) {
        if (StringUtils.isBlank(record.getId())) {
            record.setId(createId("TaskFireLog"));
            logMapper.insert(record);
        } else {
            logMapper.updateByPrimaryKey(record);
        }
        return record;
    }

    public PageInfo<TaskGroup> queryGroup(Map<String, Object> params) {
        Page<String> ids = expandMapper.queryGroup(params);
        Page<TaskGroup> page = new Page<TaskGroup>(ids.getPageNum(), ids.getPageSize());
        page.setTotal(ids.getTotal());
        if (ids != null) {
            page.clear();
            for (String id : ids) {
                page.add(InstanceUtil.getBean(getClass()).getGroupById(id));
            }
        }
        return new PageInfo<TaskGroup>(page);
    }

    public PageInfo<TaskSchedulerBean> queryScheduler(Map<String, Object> params) {
        Page<String> ids = expandMapper.queryScheduler(params);
        Page<TaskSchedulerBean> page = new Page<TaskSchedulerBean>(ids.getPageNum(), ids.getPageSize());
        page.setTotal(ids.getTotal());
        if (ids != null) {
            page.clear();
            for (String id : ids) {
                TaskScheduler taskScheduler = InstanceUtil.getBean(getClass()).getSchedulerById(id);
                TaskSchedulerBean bean = InstanceUtil.to(taskScheduler, TaskSchedulerBean.class);
                TaskGroup taskGroup = InstanceUtil.getBean(getClass()).getGroupById(bean.getGroupId());
                bean.setGroupName(taskGroup.getGroupName());
                bean.setTaskDesc(taskGroup.getGroupDesc() + ":" + bean.getTaskDesc());
                page.add(bean);
            }
        }
        return new PageInfo<TaskSchedulerBean>(page);
    }

    public PageInfo<TaskFireLog> queryLog(Map<String, Object> params) {
        Page<String> ids = expandMapper.queryLog(params);
        Page<TaskFireLog> page = new Page<TaskFireLog>(ids.getPageNum(), ids.getPageSize());
        page.setTotal(ids.getTotal());
        if (ids != null) {
            page.clear();
            for (String id : ids) {
                page.add(InstanceUtil.getBean(getClass()).getFireLogById(id));
            }
        }
        return new PageInfo<TaskFireLog>(page);
    }
    
    //任务组重名验证
    public boolean checkGroupName(String id,String taskGroupName){
    	//数据库是否存在
    	boolean isExist = false;
    	List<TaskGroup> taskGroups = expandMapper.getTaskGroupsByTaskGroupName(taskGroupName);
    	if(taskGroups.size()>0){
    		//id==0则为添加操作
    		TaskGroup taskGroup = taskGroups.get(0);
    		if(id!=null&&!id.trim().equals("0")){
				if(!taskGroup.getId().trim().equals(id)){
					isExist = true;
				}
    		}else{
    			isExist = true;
    		}
    	}else{
    		isExist = false;
    	}
    	return isExist;
    }
    
    //任务重名验证
    public boolean checkSchedulerName(String id,String taskName){
    	//数据库是否存在
    	boolean isExist = false;
    	List<TaskScheduler> taskSchedulers = expandMapper.getTaskSchedulersByTaskSchedulerName(taskName);
    	if(taskSchedulers.size()>0){
    		//id==0则为添加操作
    		TaskScheduler taskScheduler = taskSchedulers.get(0);
    		if(id!=null&&!id.trim().equals("0")){
				if(!taskScheduler.getId().trim().equals(id)){
					isExist = true;
				}
    		}else{
    			isExist = true;
    		}
    	}else{
    		isExist = false;
    	}
    	return isExist;
    }
}
