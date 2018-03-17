package com.castiel.web.sys;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import com.castiel.model.generator.SysUser;
import com.castiel.model.generator.SysUserRole;
import com.castiel.model.sys.SysMenuBean;
import com.castiel.service.sys.SysAuthorizeService;
import com.castiel.service.sys.SysUserService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "学院普通用户管理", description = "用户管理")
@RequestMapping(value = "/deptuser", method = RequestMethod.POST)
public class DeptUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysAuthorizeService authorizeService;

    // 查询用户
    @ApiOperation(value = "查询用户")
    @RequiresPermissions("platform.deptuser.read")
    @RequestMapping(value = "/read/list")
    public Object get(HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        PageInfo<?> list = sysUserService.queryBean(params);
        return setSuccessModelMap(modelMap, list);
    }

    // 添加用户信息
    @ApiOperation(value = "添加用户信息")
    @RequiresPermissions("sys.user.add")
    @RequestMapping(value = "/add")
    public Object add(HttpServletRequest request, ModelMap modelMap) {
        SysUser record = Request2ModelUtil.covert(SysUser.class, request);
        if (record.getSex() == null) {
            record.setSex(0);
        }
        record.setPassword(sysUserService.encryptPassword(record.getAccount() + "ffff"));
        record.setUserType(1);
        String userId = sysUserService.add(record);
        // 添加默认角色为学院普通管理用户
        List<SysUserRole> list = new ArrayList<>();
        SysUserRole role = new SysUserRole();
        role.setUserId(userId);
        role.setRoleId("4");
        list.add(role);
        authorizeService.updateUserRole(list);
        // 添加默认用户菜单
        LinkedHashMap<String, String> returnMap = authorizeService.roleMenuDetail("4");
        StringBuffer sBuffer = new StringBuffer();
        for (Iterator<String> it = returnMap.keySet().iterator(); it.hasNext();) {
            String val = returnMap.get(it.next());
            String menuId = val.substring(0, val.indexOf(":"));
            if (val.substring(val.indexOf(":")).contains("null")) {
                continue;
            }
            String pre = val.substring(val.indexOf(":") + 1).trim();
            pre.replace("(submenu)", "");
            sBuffer.append(menuId + ":" + pre + "|");

        }
        sBuffer.deleteCharAt(sBuffer.length() - 1);
        authorizeService.updateUserMenu(userId, sBuffer.toString());
        return setSuccessModelMap(modelMap);
    }

    // 修改用户信息
    @ApiOperation(value = "修改用户信息")
    @RequiresPermissions("sys.user.update")
    @RequestMapping(value = "/update")
    public Object update(HttpServletRequest request, ModelMap modelMap) {
        SysUser sysUser = Request2ModelUtil.covert(SysUser.class, request);
        sysUserService.updateUserInfo(sysUser);
        return setSuccessModelMap(modelMap);
    }

    // 修改用户数据状态为禁用
    @ApiOperation(value = "修改用户数据状态为禁用")
    @RequiresPermissions("sys.user.delete")
    @RequestMapping(value = "/delete")
    public Object delete(ModelMap modelMap, @RequestParam(value = "id", required = false) String id) {
        sysUserService.delete(id);
        return setSuccessModelMap(modelMap);
    }

    // 修改用户锁定状态
    @ApiOperation(value = "修改用户锁定状态")
    @RequiresPermissions("sys.user.update")
    @RequestMapping(value = "/update/locked")
    public Object updateLocked(ModelMap modelMap, @RequestParam(value = "id", required = false) String id, @RequestParam(value = "locked") boolean locked) {
        sysUserService.updateLocked(id, locked);
        return setSuccessModelMap(modelMap);
    }

    // 修改密码
    @ApiOperation(value = "修改密码")
    @RequiresPermissions("sys.user.update")
    @RequestMapping(value = "/update/password")
    public Object updatePassword(ModelMap modelMap, @RequestParam(value = "id", required = false) String id, @RequestParam(value = "password", required = false) String password) {
        sysUserService.updatePassword(id, password);
        return setSuccessModelMap(modelMap);
    }

    // 用户详细信息
    @ApiOperation(value = "用户详细信息")
    @RequiresPermissions("sys.user.read")
    @RequestMapping(value = "/read/detail")
    public Object detail(ModelMap modelMap, @RequestParam(value = "id", required = false) String id) {
        Map<String, Object> returnMap = sysUserService.detail(id);
        if (returnMap.get("record") != null) {
            SysUser sysUser = (SysUser) returnMap.get("record");
            sysUser.setPassword(null);
        }
        return setSuccessModelMap(modelMap, returnMap);
    }

    // 账户名重名验证
    @ApiOperation(value = "账户名重名验证")
    @RequiresPermissions("sys.user.read")
    @RequestMapping(value = "/checkName")
    public Object checkName(ModelMap modelMap, @RequestParam(value = "id", required = false) String id, @RequestParam(value = "value", required = false) String account) {
        boolean isExist = sysUserService.checkUserName(id, account);
        return setSuccessModelMap(modelMap, isExist);
    }

    // 当前用户
    @ApiOperation(value = "当前用户信息")
    @RequestMapping(value = "/read/current")
    public Object current(ModelMap modelMap) {
        String id = getCurrUser();
        SysUser sysUser = sysUserService.queryById(id);
        if (sysUser != null) {
            sysUser.setPassword(null);
        }
        List<SysMenuBean> menus = authorizeService.queryAuthorizeByUserId(id);
        modelMap.put("user", sysUser);
        modelMap.put("menus", menus);
        return setSuccessModelMap(modelMap);
    }
}
