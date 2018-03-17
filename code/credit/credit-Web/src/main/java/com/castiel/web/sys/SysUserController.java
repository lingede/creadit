/**
 * 
 */
package com.castiel.web.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.castiel.core.base.BaseController;
import com.castiel.core.util.Request2ModelUtil;
import com.castiel.core.util.UploadUtil;
import com.castiel.core.util.WebUtil;
import com.castiel.model.generator.SysUser;
import com.castiel.model.sys.SysMenuBean;
import com.castiel.service.sys.SysAuthorizeService;
import com.castiel.service.sys.SysUserService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户管理控制器
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:12:12
 */
@RestController
@Api(value = "用户管理", description = "用户管理")
@RequestMapping(value = "/user", method = RequestMethod.POST)
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysAuthorizeService authorizeService;

    // 添加用户信息
    @ApiOperation(value = "添加用户信息")
    @RequiresPermissions("sys.user.add")
    @RequestMapping(value = "/add")
    public Object add(HttpServletRequest request, ModelMap modelMap) {
        SysUser record = Request2ModelUtil.covert(SysUser.class, request);
        if (record.getSex() == null) {
            record.setSex(0);
        }
        record.setPassword(sysUserService.encryptPassword(record.getPassword()));
        sysUserService.add(record);
        return setSuccessModelMap(modelMap);
    }

    // 修改用户信息
    @ApiOperation(value = "修改用户信息")
    @RequiresPermissions("sys.user.update")
    @RequestMapping(value = "/update")
    public Object update(HttpServletRequest request, ModelMap modelMap) {
        SysUser sysUser = Request2ModelUtil.covert(SysUser.class, request);
        if (StringUtils.isNotBlank(sysUser.getAvatar()) && !sysUser.getAvatar().contains("/")) {
            String avatar = UploadUtil.remove2DFS("sysUser", "user" + sysUser.getId(), UploadUtil.getUploadDir(request) + sysUser.getAvatar()).getRemotePath();
            sysUser.setAvatar(avatar);
        }
        sysUser.setPassword(sysUserService.encryptPassword(sysUser.getPassword()));
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

    // 查询用户
    @ApiOperation(value = "查询用户")
    @RequiresPermissions("sys.user.read")
    @RequestMapping(value = "/read/list")
    public Object get(HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        PageInfo<?> list = sysUserService.queryAdmin(params);
        return setSuccessModelMap(modelMap, list);
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
