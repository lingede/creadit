package com.castiel.web.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.castiel.core.base.BaseController;
import com.castiel.core.util.Request2ModelUtil;
import com.castiel.core.util.WebUtil;
import com.castiel.model.generator.SysDic;
import com.castiel.model.generator.SysDicIndex;
import com.castiel.service.sys.SysDicService;

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
 * 字典管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:14:34
 */
@RestController
@Api(value = "字典管理", description = "字典管理")
@RequestMapping(method = RequestMethod.POST)
public class SysDicController extends BaseController {
	@Autowired
	private SysDicService sysDicService;

	@ApiOperation(value = "查询字典")
	@RequiresPermissions("sys.dic.read")
	@RequestMapping(value = "dicIndex/read/list")
	public Object getDicIndex(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = WebUtil.getParameterMap(request);
		PageInfo<?> list = sysDicService.queryDicIndex(params);
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "字典详情")
	@RequiresPermissions("sys.dic.read")
	@RequestMapping(value = "dicIndex/read/detail")
	public Object detail(ModelMap modelMap, @RequestParam(value = "id", required = false) String id) {
		SysDicIndex record = sysDicService.queryDicIndexById(id);
		return setSuccessModelMap(modelMap, record);
	}

	@ApiOperation(value = "根据关键字查询字典列表")
	@RequiresPermissions("sys.dic.read")
	@RequestMapping(value = "dic/read/key")
	public Object getDicByKey(HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "key", required = false) String key) {
		Map<String, String> result = sysDicService.queryDicByDicIndexKey(key);
		return setSuccessModelMap(modelMap, result);
	}

	@ApiOperation(value = "添加字典")
	@RequiresPermissions("sys.dic.add")
	@RequestMapping(value = "dicIndex/add", method = RequestMethod.POST)
	public Object addDicIndex(HttpServletRequest request, ModelMap modelMap) {
		SysDicIndex record = Request2ModelUtil.covert(SysDicIndex.class, request);
		sysDicService.addDicIndex(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "修改字典")
	@RequiresPermissions("sys.dic.update")
	@RequestMapping(value = "dicIndex/update", method = RequestMethod.POST)
	public Object updateDicIndex(HttpServletRequest request, ModelMap modelMap) {
		SysDicIndex record = Request2ModelUtil.covert(SysDicIndex.class, request);
		sysDicService.updateDicIndex(record);
		return setSuccessModelMap(modelMap);
	}

	//字典名重名验证
	@ApiOperation(value = "字典名重名验证")
	@RequiresPermissions("sys.dic.read")
	@RequestMapping(value = "dicIndex/update/checkDicIndexName")
	public Object checkDicIndexName(ModelMap modelMap, @RequestParam(value = "id", required = false) String id,@RequestParam(value = "value", required = false) String dicIndexName) {
		boolean isExist = sysDicService.checkDicIndexName(id,dicIndexName);
		return setSuccessModelMap(modelMap,isExist);
	}
	
	//字典关键字重名验证
	@ApiOperation(value = "字典关键字重名验证")
	@RequiresPermissions("sys.dic.read")
	@RequestMapping(value = "dicIndex/update/checkDicIndexKey")
	public Object checkDicIndexKey(ModelMap modelMap, @RequestParam(value = "id", required = false) String id,@RequestParam(value = "value", required = false) String dicIndexKey) {
		boolean isExist = sysDicService.checkDicIndexKey(id,dicIndexKey);
		return setSuccessModelMap(modelMap,isExist);
	}
	
	@ApiOperation(value = "删除字典")
	@RequiresPermissions("sys.dic.delete")
	@RequestMapping(value = "dicIndex/delete", method = RequestMethod.POST)
	public Object deleteDicIndex(HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "id", required = false) String id) {
		sysDicService.deleteDicIndex(id);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "查询字典项")
	@RequiresPermissions("sys.dic.read")
	@RequestMapping(value = "dic/read/list")
	public Object getDic(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = WebUtil.getParameterMap(request);
		PageInfo<?> list = sysDicService.queryDic(params);
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "字典项详情")
	@RequiresPermissions("sys.dic.read")
	@RequestMapping(value = "dic/read/detail")
	public Object dicDetail(ModelMap modelMap, @RequestParam(value = "id", required = false) String id) {
		SysDic record = sysDicService.queryDicById(id);
		return setSuccessModelMap(modelMap, record);
	}

	@ApiOperation(value = "添加字典项")
	@RequiresPermissions("sys.dic.add")
	@RequestMapping(value = "dic/add", method = RequestMethod.POST)
	public Object addDic(HttpServletRequest request, ModelMap modelMap) {
		SysDic record = Request2ModelUtil.covert(SysDic.class, request);
		sysDicService.addDic(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "修改字典项")
	@RequiresPermissions("sys.dic.update")
	@RequestMapping(value = "dic/update", method = RequestMethod.POST)
	public Object updateDic(HttpServletRequest request, ModelMap modelMap) {
		SysDic record = Request2ModelUtil.covert(SysDic.class, request);
		sysDicService.updateDic(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "删除字典项")
	@RequiresPermissions("sys.dic.delete")
	@RequestMapping(value = "dic/delete", method = RequestMethod.POST)
	public Object deleteDic(HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "id", required = false) String id) {
		sysDicService.deleteDic(id);
		return setSuccessModelMap(modelMap);
	}
}
