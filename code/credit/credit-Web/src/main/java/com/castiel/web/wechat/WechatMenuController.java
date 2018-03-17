package com.castiel.web.wechat;

import java.util.List;
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
import com.castiel.model.generator.WechatMenu;
import com.castiel.service.wechat.WechatMenuService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(value = "微信自定义菜单管理", description = "微信自定义菜单管理")
@RequestMapping(value = "wechat/menu", method = RequestMethod.POST)
public class WechatMenuController extends BaseController {

	@Autowired
	private WechatMenuService wechatMenuService;

	// 查询微信自定义菜单列表
	@ApiOperation(value = "查询微信自定义菜单列表")
	@RequiresPermissions("wechat.menu.read")
	@RequestMapping(value = "/read/list")
	public Object list(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = WebUtil.getParameterMap(request);
		PageInfo<?> list = wechatMenuService.queryBean(params);
		return setSuccessModelMap(modelMap, list);
	}


	// 查询微信自定义菜单详细信息
	@ApiOperation(value = "查询微信自定义菜单详细信息")
	@RequiresPermissions("wechat.menu.read")
	@RequestMapping(value = "/read/detail")
	public Object detail(ModelMap modelMap, @RequestParam(value = "id", required = false) String id) {
		Map<String, Object> returnMap = wechatMenuService.detail(id);
		return setSuccessModelMap(modelMap, returnMap);
	}


	// 锁定微信自定义菜单
	@ApiOperation(value = "锁定微信自定义菜单")
	@RequiresPermissions("wechat.menu.update")
	@RequestMapping(value = "/lock")
	public Object lock(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) String id) {
		WechatMenu record = wechatMenuService.queryById(id);
		record.setLocked(true);
		wechatMenuService.update(record);
		return setSuccessModelMap(modelMap);
	}

	// 新增菜单
	@ApiOperation(value = "添加菜单")
	@RequiresPermissions("wechat.menu.add")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object add(HttpServletRequest request, ModelMap modelMap) {
		WechatMenu record = Request2ModelUtil.covert(WechatMenu.class, request);
		// 判断字段填写是否完整
		if (record.getType().equals("view") || record.getType().equals("click")) {
			if (record.getType().equals("view") && (record.getUrl() == null || record.getUrl().equals(""))) {
				System.out.println("跳转URL类型中菜单URL不能为空！");
			} else if (record.getType().equals("click") && (record.getKey() == null || record.getKey().equals(""))) {
				System.out.println("点击推类型中菜单Key值不能为空！");
			}
		} else {
			if (record.getType().equals("media_id") && (record.getMediaId() == null || record.getMediaId().equals(""))) {
				System.out.println("下发消息（除文本消息）类型中media_id不能为空！");
			}

		}
		if (record.getParentId() != null && record.getParentId().trim().equals("0")) {
			record.setLevel(1);
			record.setParentId(null);
		} else {
			record.setLevel(2);
		}
		if (record.getType().trim().equals("history")) {
			record.setType("view");
		}
		record.setLocked(false);
		if (record.getLevel() == 1) {
			List<WechatMenu> wechatMenus = wechatMenuService.queryTopLevelWechatMenus();
			if (wechatMenus.size() == 3) {
				System.out.println("不能创建更多的一级菜单！");
			} else {
				wechatMenuService.add(record);
			}
		} else {
			List<WechatMenu> wechatMenus = wechatMenuService.queryWechatMenusByParentId(record.getParentId());
			if (wechatMenus.size() == 5) {
				System.out.println("不能创建更多的二级菜单！");
			} else {
				wechatMenuService.add(record);
			}
		}
		return setSuccessModelMap(modelMap);
	}

	// 修改菜单
	@ApiOperation(value = "修改菜单")
	@RequiresPermissions("wechat.menu.update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object update(HttpServletRequest request, ModelMap modelMap) {
		WechatMenu record = Request2ModelUtil.covert(WechatMenu.class, request);
		if (record.getParentId() != null && record.getParentId().trim().equals("0")) {
			record.setLevel(1);
			record.setParentId(null);
		} else {
			record.setLevel(2);
		}
		if (record.getType().trim().equals("history")) {
			record.setType("view");
		}
		wechatMenuService.update(record);
		return setSuccessModelMap(modelMap);
	}

	// 同步设置到微信
	@ApiOperation(value = "同步设置到微信")
	@RequiresPermissions("wechat.menu.update")
	@RequestMapping(value = "/sync", method = RequestMethod.GET)
	public Object sync(HttpServletRequest request, ModelMap modelMap) {
		wechatMenuService.sync(request);
		return setSuccessModelMap(modelMap);
	}
}
