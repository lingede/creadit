package com.castiel.provider;

import java.util.List;

import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.model.scheduler.TaskScheduled;
import com.castiel.provider.scheduler.SchedulerProvider;
import com.castiel.scheduler.manager.SchedulerManager;
import com.castiel.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 定时任务管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:59
 */
@DubboService(interfaceClass = SchedulerProvider.class)
public class SchedulerProviderImpl extends SchedulerService implements SchedulerProvider {
	@Autowired
	private SchedulerManager schedulerManager;

	// 获取所有作业
	public List<TaskScheduled> getAllTaskDetail() {
		return schedulerManager.getAllJobDetail();
	}

	// 执行作业
	public boolean execTask(String taskGroup, String taskName) {
		TaskScheduled taskScheduler = new TaskScheduled();
		taskScheduler.setTaskGroup(taskGroup);
		taskScheduler.setTaskName(taskName);
		return schedulerManager.runJob(taskScheduler);
	}

	// 暂停/恢复作业
	public boolean openCloseTask(String taskGroup, String taskName, String status) {
		TaskScheduled taskScheduler = new TaskScheduled();
		taskScheduler.setTaskGroup(taskGroup);
		taskScheduler.setTaskName(taskName);
		if ("start".equals(status)) {
			return schedulerManager.resumeJob(taskScheduler);
		} else if ("stop".equals(status)) {
			return schedulerManager.stopJob(taskScheduler);
		}
		return false;
	}
}
