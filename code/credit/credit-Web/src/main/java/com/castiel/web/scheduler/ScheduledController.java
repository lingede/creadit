/**
 * 
 */
package com.castiel.web.scheduler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.castiel.core.base.BaseController;
import com.castiel.core.util.WebUtil;
import com.castiel.model.scheduler.TaskScheduled;
import com.castiel.service.scheduler.SchedulerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 内存调度管理
 * 
 * @author ShenHuaJie
 * @version 2016年4月2日 下午4:20:10
 */
@RestController
@Api(value = "调度管理", description = "调度管理")
@RequestMapping(value = "/scheduled", method = RequestMethod.POST)
public class ScheduledController extends BaseController {
	@Autowired
	private SchedulerService schedulerService;

	@RequestMapping("/read/tasks")
	@ApiOperation(value = "调度列表")
	@RequiresPermissions("task.scheduled.read")
	public Object list(ModelMap modelMap) {
		PageInfo<TaskScheduled> jobs = schedulerService.getAllTaskDetail();
		return setSuccessModelMap(modelMap, jobs);
	}

	// 执行
	@RequestMapping("/run/task")
	@ApiOperation(value = "立即执行调度")
	@RequiresPermissions("task.scheduled.run")
	public Object exec(ModelMap modelMap, @RequestParam(value = "taskGroup", required = false) String taskGroup,
			@RequestParam(value = "taskName", required = false) String taskName) {
		schedulerService.execTask(taskGroup, taskName);
		return setSuccessModelMap(modelMap);
	}

	// 启动
	@RequestMapping("/open/task")
	@ApiOperation(value = "启动调度")
	@RequiresPermissions("task.scheduled.open")
	public Object open(ModelMap modelMap, @RequestParam(value = "taskGroup", required = false) String taskGroup,
			@RequestParam(value = "taskName", required = false) String taskName) {
		schedulerService.openTask(taskGroup, taskName);
		return setSuccessModelMap(modelMap);
	}

	// 暂停
	@RequestMapping("/close/task")
	@ApiOperation(value = "暂停调度")
	@RequiresPermissions("task.scheduled.close")
	public Object close(ModelMap modelMap, @RequestParam(value = "taskGroup", required = false) String taskGroup,
			@RequestParam(value = "taskName", required = false) String taskName) {
		schedulerService.closeTask(taskGroup, taskName);
		return setSuccessModelMap(modelMap);
	}

	// 执行记录
	@RequestMapping("/read/log")
	@ApiOperation(value = "调度执行记录")
	@RequiresPermissions("task.log.read")
	public Object getFireLog(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = WebUtil.getParameterMap(request);
		PageInfo<?> list = schedulerService.queryLog(params);
		return setSuccessModelMap(modelMap, list);
	}
}
