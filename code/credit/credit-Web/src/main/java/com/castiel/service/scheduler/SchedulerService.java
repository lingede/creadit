package com.castiel.service.scheduler;

import java.util.Date;
import java.util.Map;
import com.castiel.core.support.Assert;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.core.util.WebUtil;
import com.castiel.model.generator.TaskFireLog;
import com.castiel.model.generator.TaskGroup;
import com.castiel.model.generator.TaskScheduler;
import com.castiel.model.scheduler.TaskScheduled;
import com.castiel.model.scheduler.TaskSchedulerBean;
import com.castiel.provider.scheduler.SchedulerProvider;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageInfo;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:16:20
 */
@Service
public class SchedulerService {
    @DubboReference // 依赖调度服务
	private SchedulerProvider schedulerProvider;

	public PageInfo<TaskScheduled> getAllTaskDetail() {
		PageInfo<TaskScheduled> pageInfo = new PageInfo<TaskScheduled>();
		pageInfo.setList(schedulerProvider.getAllTaskDetail());
		pageInfo.setPages(1);
		pageInfo.setSize(pageInfo.getList().size());
		return pageInfo;
	}

	public boolean execTask(String taskGroup, String taskName) {
		Assert.notNull(taskGroup, "TASKGROUP");
		Assert.notNull(taskName, "TASKNAME");
		return schedulerProvider.execTask(taskGroup, taskName);
	}

	public boolean openTask(String taskGroup, String taskName) {
		Assert.notNull(taskGroup, "TASKGROUP");
		Assert.notNull(taskName, "TASKNAME");
		return schedulerProvider.openCloseTask(taskGroup, taskName, "start");
	}

	public boolean closeTask(String taskGroup, String taskName) {
		Assert.notNull(taskGroup, "TASKGROUP");
		Assert.notNull(taskName, "TASKNAME");
		return schedulerProvider.openCloseTask(taskGroup, taskName, "stop");
	}

	public PageInfo<TaskGroup> queryGroup(Map<String, Object> params) {
		return schedulerProvider.queryGroup(params);
	}

	public PageInfo<TaskSchedulerBean> queryScheduler(Map<String, Object> params) {
		return schedulerProvider.queryScheduler(params);
	}

	public PageInfo<TaskFireLog> queryLog(Map<String, Object> params) {
		return schedulerProvider.queryLog(params);
	}

	/**
	 * @param id
	 * @return
	 */
	public TaskGroup queryGroupById(String id) {
		Assert.notNull(id, "ID");
		return schedulerProvider.getGroupById(id);
	}

	/**
	 * @param record
	 */
	public void addGroup(TaskGroup record) {
		String userId = WebUtil.getCurrentUser();
		record.setCreateBy(userId);
		record.setCreateTime(new Date());
		record.setUpdateBy(userId);
		record.setUpdateTime(new Date());
		schedulerProvider.updateGroup(record);
	}

	/**
	 * @param record
	 */
	public void updateGroup(TaskGroup record) {
		String userId = WebUtil.getCurrentUser();
		record.setUpdateBy(userId);
		record.setUpdateTime(new Date());
		Assert.isNotBlank(record.getId(), "ID");
		schedulerProvider.updateGroup(record);
	}

	/**
	 * @param id
	 * @return
	 */
	public TaskScheduler querySchedulerById(String id) {
		Assert.isNotBlank(id, "ID");
		return schedulerProvider.getSchedulerById(id);
	}
	
	/**
	 * @param id
	 * @return Map<String,Object>
	 */
	public Map<String, Object> detail(String id){
		return schedulerProvider.detail(id);
	}

	/**
	 * @param record
	 */
	public void addScheduler(TaskScheduler record) {
		String userId = WebUtil.getCurrentUser();
		record.setCreateBy(userId);
		record.setUpdateBy(userId);
		record.setUpdateTime(new Date());
		schedulerProvider.updateScheduler(record);
	}

	/**
	 * @param record
	 */
	public void updateScheduler(TaskScheduler record) {
		Assert.notNull(record.getId(), "ID");
		String userId = WebUtil.getCurrentUser();
        record.setUpdateBy(userId);
        record.setUpdateTime(new Date());
		schedulerProvider.updateScheduler(record);
	}
	
	/**
	 * @param String id
	 */
	public void deleteScheduler(String id) {
		String userId = WebUtil.getCurrentUser();
		schedulerProvider.deleteScheduler(id, userId);
	}
	
	/**
	 * @param String taskCron
	 */
	public boolean validTaskCron(String taskCron) {
		return schedulerProvider.validTaskCron(taskCron);
	}
	
	//任务组重名验证
	public boolean checkGroupName(String id,String taskGroupName){
		return schedulerProvider.checkGroupName(id, taskGroupName);
	}	
	
	//任务重名验证
	public boolean checkSchedulerName(String id,String taskName){
		return schedulerProvider.checkSchedulerName(id, taskName);
	}
}
