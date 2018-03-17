package com.castiel.dao.scheduler;

import java.util.List;
import java.util.Map;

import com.castiel.model.generator.TaskGroup;
import com.castiel.model.generator.TaskScheduler;
import com.github.pagehelper.Page;

public interface TaskSchedulerExpandMapper {
	Page<String> queryGroup(Map<String, Object> params);

	Page<String> queryScheduler(Map<String, Object> params);

	Page<String> queryLog(Map<String, Object> params);
	
	List<TaskGroup> getTaskGroupsByTaskGroupName(String taskGroupName);
	
	List<TaskScheduler> getTaskSchedulersByTaskSchedulerName(String taskName);

}
