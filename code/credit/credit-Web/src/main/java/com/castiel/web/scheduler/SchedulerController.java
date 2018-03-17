package com.castiel.web.scheduler;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.castiel.core.base.BaseController;
import com.castiel.core.util.Request2ModelUtil;
import com.castiel.core.util.WebUtil;
import com.castiel.model.generator.TaskGroup;
import com.castiel.model.generator.TaskScheduler;
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
 * 调度管理
 * 
 * @author ShenHuaJie
 * @version 2016年6月1日 下午3:14:24
 */
@RestController
@Api(value = "调度声明管理", description = "调度声明管理")
@RequestMapping(value = "/task", method = RequestMethod.POST)
public class SchedulerController extends BaseController {
	@Autowired
	private SchedulerService schedulerService;

	@RequestMapping("/read/groups")
	@ApiOperation(value = "任务组列表")
	@RequiresPermissions("task.group.read")
	public Object getGroup(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = WebUtil.getParameterMap(request);
		PageInfo<?> list = schedulerService.queryGroup(params);
		return setSuccessModelMap(modelMap, list);
	}

	// 详细信息
	@ApiOperation(value = "任务组详情")
	@RequiresPermissions("task.group.read")
	@RequestMapping(value = "/read/group")
	public Object detail(ModelMap modelMap,
			@RequestParam(value = "id", required = false) String id) {
		TaskGroup record = schedulerService.queryGroupById(id);
		return setSuccessModelMap(modelMap, record);
	}

	// 新增任务组
	@ApiOperation(value = "添加任务组")
	@RequiresPermissions("task.group.add")
	@RequestMapping(value = "/add/group", method = RequestMethod.POST)
	public Object add(HttpServletRequest request, ModelMap modelMap) {
		TaskGroup record = Request2ModelUtil.covert(TaskGroup.class, request);
		schedulerService.addGroup(record);
		return setSuccessModelMap(modelMap);
	}

	// 修改任务组
	@ApiOperation(value = "修改任务组")
	@RequiresPermissions("task.group.update")
	@RequestMapping(value = "/update/group", method = RequestMethod.POST)
	public Object update(HttpServletRequest request, ModelMap modelMap) {
		TaskGroup record = Request2ModelUtil.covert(TaskGroup.class, request);
		schedulerService.updateGroup(record);
		return setSuccessModelMap(modelMap);
	}
	
	//任务组重名验证
	@ApiOperation(value = "任务组重名验证")
	@RequiresPermissions("task.group.read")
	@RequestMapping(value = "/update/group/checkName")
	public Object checkGroupName(ModelMap modelMap, @RequestParam(value = "id", required = false) String id,@RequestParam(value = "value", required = false) String taskGroupName) {
		boolean isExist = schedulerService.checkGroupName(id,taskGroupName);
		return setSuccessModelMap(modelMap,isExist);
	}

	@ApiOperation(value = "任务列表")
	@RequestMapping("/read/schedulers")
	@RequiresPermissions("task.scheduler.read")
	public Object getScheduler(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = WebUtil.getParameterMap(request);
		PageInfo<?> list = schedulerService.queryScheduler(params);
		return setSuccessModelMap(modelMap, list);
	}

	// 详细信息
	@ApiOperation(value = "任务详情")
	@RequiresPermissions("task.scheduler.read")
	@RequestMapping(value = "/read/scheduler")
	public Object detailScheduler(ModelMap modelMap,
			@RequestParam(value = "id", required = false) String id) {
		Map<String, Object> returnMap = schedulerService.detail(id);
		return setSuccessModelMap(modelMap, returnMap);
	}

	// 新增任务
	@ApiOperation(value = "添加任务")
	@RequiresPermissions("task.scheduler.add")
	@RequestMapping(value = "/add/scheduler", method = RequestMethod.POST)
	public Object addScheduler(HttpServletRequest request, ModelMap modelMap) {
		TaskScheduler record = Request2ModelUtil.covert(TaskScheduler.class, request);
		schedulerService.addScheduler(record);
		return setSuccessModelMap(modelMap);
	}

	// 修改任务
	@ApiOperation(value = "修改任务")
	@RequiresPermissions("task.scheduler.update")
	@RequestMapping(value = "/update/scheduler", method = RequestMethod.POST)
	public Object updateScheduler(HttpServletRequest request, ModelMap modelMap) {
		TaskScheduler record = Request2ModelUtil.covert(TaskScheduler.class, request);
		schedulerService.updateScheduler(record);
		return setSuccessModelMap(modelMap);
	}
	
	//禁用任务
	@ApiOperation(value = "禁用任务")
	@RequiresPermissions("task.scheduler.delete")
	@RequestMapping(value = "/delete/scheduler", method = RequestMethod.POST)
	public Object delete(HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "id", required = false) String id) {
		schedulerService.deleteScheduler(id);
		return setSuccessModelMap(modelMap);
	}
	
	//任务重名验证
	@ApiOperation(value = "任务重名验证")
	@RequiresPermissions("task.scheduler.read")
	@RequestMapping(value = "/update/scheduler/checkName")
	public Object checkSchedulerName(ModelMap modelMap, @RequestParam(value = "id", required = false) String id,@RequestParam(value = "value", required = false) String taskName) {
		boolean isExist = schedulerService.checkSchedulerName(id,taskName);
		return setSuccessModelMap(modelMap,isExist);
	}
	
	//任务周期格式验证
	@ApiOperation(value = "任务周期格式验证")
	@RequiresPermissions("task.scheduler.read")
	@RequestMapping(value = "/read/scheduler/validTaskCron", method = RequestMethod.POST)
	public Object validTaskCron(ModelMap modelMap,@RequestParam(value = "taskCron", required = false) String taskCron) {
		return setSuccessModelMap(modelMap,schedulerService.validTaskCron(taskCron));
	}
}
