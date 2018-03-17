package com.castiel.web.sys;

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
import com.castiel.core.util.Request2ListUtil;
import com.castiel.model.generator.SysUserRole;
import com.castiel.service.sys.SysAuthorizeService;
import com.castiel.service.sys.SysCacheService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 权限管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:14:05
 */
@RestController
@Api(value = "权限管理", description = "权限管理")
public class SysAuthorizeController extends BaseController {
    @Autowired
    private SysAuthorizeService sysAuthorizeService;
    @Autowired
    private SysCacheService sysCacheService;

    // 必须拥有用户菜单的查看权限
    @ApiOperation(value = "查询用户菜单")
    @RequiresPermissions("user.menu.read")
    @RequestMapping(value = "/user/menu/read/detail", method = RequestMethod.POST)
    public Object userMenuDetail(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) String userId) {
        LinkedHashMap<String, String> returnMap = sysAuthorizeService.userMenuDetail(userId);
        return setSuccessModelMap(modelMap, returnMap);
    }

    // 必须拥有用户菜单的编辑权限
    @ApiOperation(value = "修改用户菜单")
    @RequiresPermissions("user.menu.update")
    @RequestMapping(value = "/user/menu/update", method = RequestMethod.POST)
    public Object updateUserMenu(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) String userId, @RequestParam(value = "permissionInfo", required = false) String permissionInfo) {
        sysAuthorizeService.updateUserMenu(userId, permissionInfo);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "查看用户角色")
    @RequiresPermissions("user.role.read")
    @RequestMapping(value = "/user/role/read/detail", method = RequestMethod.POST)
    public Object userRoleDetail(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) String userId) {
        Map<String, Object> returnMap = sysAuthorizeService.userRoleDetail(userId);
        return setSuccessModelMap(modelMap, returnMap);
    }

    @ApiOperation(value = "修改用户角色")
    @RequiresPermissions("user.role.update")
    @RequestMapping(value = "/user/role/update", method = RequestMethod.POST)
    public Object userRole(HttpServletRequest request, ModelMap modelMap) {
        List<SysUserRole> list = Request2ListUtil.covert(SysUserRole.class, request);
        sysAuthorizeService.updateUserRole(list);
        // 更新用户菜单权限
        LinkedHashMap<String, String> returnMap = sysAuthorizeService.roleMenuDetail(list.get(0).getRoleId());
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
        sysAuthorizeService.updateUserMenu(list.get(0).getUserId(), sBuffer.toString());
        return setSuccessModelMap(modelMap);
    }

    // 必须拥有角色菜单的查看权限
    @ApiOperation(value = "查询角色菜单")
    @RequiresPermissions("role.menu.read")
    @RequestMapping(value = "/role/menu/read/detail", method = RequestMethod.POST)
    public Object roleMenuDetail(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) String roleId) {
        LinkedHashMap<String, String> returnMap = sysAuthorizeService.roleMenuDetail(roleId);
        return setSuccessModelMap(modelMap, returnMap);
    }

    // 必须拥有角色菜单的编辑权限
    @ApiOperation(value = "修改角色菜单")
    @RequiresPermissions("role.menu.update")
    @RequestMapping(value = "/role/menu/update", method = RequestMethod.POST)
    public Object updateRoleMenu(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) String roleId, @RequestParam(value = "permissionInfo", required = false) String permissionInfo) {
        sysAuthorizeService.updateRoleMenu(roleId, permissionInfo);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "清理缓存")
    @RequiresPermissions("sys.cache.update")
    @RequestMapping(value = "/cache/update", method = RequestMethod.POST)
    public Object flush(HttpServletRequest request, ModelMap modelMap) {
        sysCacheService.flushCache();
        return setSuccessModelMap(modelMap);
    }
}
