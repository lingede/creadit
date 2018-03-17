package com.castiel.web.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.castiel.core.base.BaseController;
import com.castiel.core.util.Request2ModelUtil;
import com.castiel.core.util.WebUtil;
import com.castiel.model.generator.PBasicDept;
import com.castiel.service.sys.PBasicDeptService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "学院管理", description = "学院管理")
@RequestMapping(value = "pdept", method = RequestMethod.POST)
public class PBasicDeptController extends BaseController {
	@Autowired
	private PBasicDeptService pBasicDeptService;

	@ApiOperation(value = "查询学院")
	@RequiresPermissions("platform.dept.read")
	@RequestMapping(value = "read/list")
	public Object get(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = WebUtil.getParameterMap(request);
		PageInfo<?> list = pBasicDeptService.queryBean(params);
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "添加学院")
	@RequiresPermissions("platform.dept.add")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object add(HttpServletRequest request, ModelMap modelMap) {
		PBasicDept record = Request2ModelUtil.covert(PBasicDept.class, request);
		pBasicDeptService.add(record);
		return setSuccessModelMap(modelMap);
	}

	// 详细信息
	@ApiOperation(value = "学院详情")
	@RequiresPermissions("platform.dept.read")
	@RequestMapping(value = "/read/detail")
	public Object detail(ModelMap modelMap, @RequestParam(value = "id", required = false) String id) {
		Map<String, Object> returnMap = pBasicDeptService.detail(id);
		return setSuccessModelMap(modelMap, returnMap);
	}

	// 修改学院
	@ApiOperation(value = "修改学院")
	@RequiresPermissions("platform.dept.update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object update(HttpServletRequest request, ModelMap modelMap) {
		PBasicDept record = Request2ModelUtil.covert(PBasicDept.class, request);
		pBasicDeptService.update(record);
		return setSuccessModelMap(modelMap);
	}

	// 删除学院
	@ApiOperation(value = "删除学院")
	@RequiresPermissions("platform.dept.delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object delete(HttpServletRequest request, ModelMap modelMap, String id) {
		pBasicDeptService.deleteDept(id);
		return setSuccessModelMap(modelMap);
	}

	// 部门名称重名验证
	@ApiOperation(value = "重名验证")
	@RequiresPermissions("platform.dept.read")
	@RequestMapping(value = "/checkName")
	public Object checkName(ModelMap modelMap, @RequestParam(value = "id", required = false) String id, @RequestParam(value = "value", required = false) String deptName) {
		boolean isExist = pBasicDeptService.checkDeptName(id, deptName);
		return setSuccessModelMap(modelMap, isExist);
	}

	@ApiOperation(value = "重名验证")
	@RequiresPermissions("platform.dept.read")
	@RequestMapping(value = "/checkNo")
	public Object checkNo(ModelMap modelMap, @RequestParam(value = "id", required = false) String id, @RequestParam(value = "value", required = false) String deptNo) {
		boolean isExist = pBasicDeptService.checkDeptNo(id, deptNo);
		return setSuccessModelMap(modelMap, isExist);
	}
}
