package com.castiel.provider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.core.util.InstanceUtil;
import com.castiel.dao.generator.SysMenuMapper;
import com.castiel.dao.generator.SysRoleMapper;
import com.castiel.dao.generator.SysUserRoleMapper;
import com.castiel.dao.sys.SysAuthorizeMapper;
import com.castiel.model.generator.SysMenu;
import com.castiel.model.generator.SysRole;
import com.castiel.model.generator.SysRoleMenu;
import com.castiel.model.generator.SysUser;
import com.castiel.model.generator.SysUserMenu;
import com.castiel.model.generator.SysUserRole;
import com.castiel.model.sys.SysMenuBean;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@CacheConfig(cacheNames = "sysMenu")
@DubboService(interfaceClass = SysAuthorizeProvider.class)
public class SysAuthorizeProviderImpl extends BaseProviderImpl<SysMenu> implements SysAuthorizeProvider {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysAuthorizeMapper sysAuthorizeMapper;
    @Autowired
    private SysMenuProvider sysMenuProvider;
    @Autowired
    private SysRoleProvider sysRoleProvider;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuProvider sysRoleMenuProvider;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserProvider sysUserProvider;
    @Autowired
    private SysUserMenuProvider sysUserMenuProvider;

    @Transactional
    @CacheEvict(value = {"getAuthorize", "sysPermission", "userMenuDetail"}, allEntries = true)
    public void updateUserMenu(String userId, String permissionInfo, String currentUserId) {
        List<SysUserMenu> sysUserMenus = new ArrayList<SysUserMenu>();
        if (permissionInfo != null && !permissionInfo.equals("")) {
            String[] sysUserMenuArray = permissionInfo.split("\\|");
            for (int i = 0; i < sysUserMenuArray.length; i++) {
                String[] splitMenuIdAndPermissionArray = sysUserMenuArray[i].split(":");
                String menuId = splitMenuIdAndPermissionArray[0];
                String this_permission = splitMenuIdAndPermissionArray[1];
                // 分割权限信息
                String[] this_permissionArray = this_permission.split(",");
                for (int j = 0; j < this_permissionArray.length; j++) {
                    SysUserMenu sysUserMenu = new SysUserMenu();
                    sysUserMenu.setUserId(userId);
                    sysUserMenu.setMenuId(menuId);
                    sysUserMenu.setPermission(this_permissionArray[j]);
                    sysUserMenu.setRemark("");
                    sysUserMenus.add(sysUserMenu);
                }
            }
        }
        if (sysUserMenus.size() > 0) {
            sysAuthorizeMapper.deleteUserMenu(sysUserMenus.get(0).getUserId());
            for (SysUserMenu sysUserMenu : sysUserMenus) {
                sysUserMenuProvider.insert(sysUserMenu, currentUserId);
            }
        } else {
            sysAuthorizeMapper.deleteUserMenu(userId);
        }
    }

    @Transactional
    @CacheEvict(value = {"getAuthorize", "sysPermission"}, allEntries = true)
    public void updateUserRole(List<SysUserRole> sysUserRoles) {
        sysAuthorizeMapper.deleteUserRole(sysUserRoles.get(0).getUserId());
        for (SysUserRole sysUserRole : sysUserRoles) {
            sysUserRoleMapper.insert(sysUserRole);
        }
    }

    @Cacheable(value = "userMenuDetail")
    public LinkedHashMap<String, String> userMenuDetail(String currentUserId, String userId) {
        // 当前登陆的用户
        SysUser currentUser = sysUserProvider.queryById(currentUserId);
        // 当前登陆用户的最上级菜单
        List<SysMenu> currentMogamiSysMenus = InstanceUtil.newArrayList();
        if (currentUser.getUserType() == 3) {
            currentMogamiSysMenus = sysMenuProvider.querySysMenusByParentId("0");
            // 当前角色为系统内置角色，赋予所有菜单的修改权限，没有权限的立即增加权限
            List<SysUserMenu> currentSysUserMenus = InstanceUtil.newArrayList();
            currentSysUserMenus = packageSysUserMenus(currentSysUserMenus, currentUserId, currentMogamiSysMenus);
            addPermissionToAdminUser(currentSysUserMenus, currentUserId);
        } else {
            currentMogamiSysMenus = queryMogamiSysMenusByUserId(currentUserId);
        }
        // 存储当前登陆的角色权限信息
        List<SysUserMenu> currentSysUserMenus = InstanceUtil.newArrayList();
        currentSysUserMenus = packageSysUserMenus(currentSysUserMenus, currentUserId, currentMogamiSysMenus);
        // 查询当前操作的角色菜单信息
        // 当前登陆角色的最上级菜单
        List<SysMenu> operatingMogamiSysMenus = queryMogamiSysMenusByUserId(userId);
        List<SysUserMenu> operatingSysUserMenus = InstanceUtil.newArrayList();
        operatingSysUserMenus = packageSysUserMenus(operatingSysUserMenus, userId, operatingMogamiSysMenus);
        /**************************************** 封装前台数据start ****************************************/
        // 处理当前登陆的角色包含那些权限
        LinkedHashMap<String, String> currentPermissionMap = packageUserPermissionMap(currentSysUserMenus);
        // 处理当前操作中的角色包含那些权限
        LinkedHashMap<String, String> operatingPermissionMap = packageUserPermissionMap(operatingSysUserMenus);
        /**************************************** 封装前台数据start ****************************************/
        // 返回值
        LinkedHashMap<String, String> returnMap = new LinkedHashMap<String, String>();
        // 获取所有菜单
        List<SysMenu> allSysMenus = sysMenuMapper.selectAll();
        for (String key : currentPermissionMap.keySet()) {
            if (operatingPermissionMap.get(key) != null && !operatingPermissionMap.get(key).equals("")) {
                returnMap.put(key, operatingPermissionMap.get(key));
            } else {
                String permissionInfo_id = "";
                for (SysMenu sysMenu : allSysMenus) {
                    if (sysMenu.getMenuName().trim().equals(key.trim())) {
                        permissionInfo_id = sysMenu.getId();
                        break;
                    }
                }
                String permissionInfo = "";
                if (currentPermissionMap.get(key).indexOf("(submenu)") != -1) {
                    permissionInfo = permissionInfo_id + ":null(submenu)";
                } else {
                    permissionInfo = permissionInfo_id + ":null";
                }
                returnMap.put(key, permissionInfo);
            }
        }
        // 处理菜单
        return returnMap;
    }

    @Cacheable(value = "roleMenuDetail")
    public LinkedHashMap<String, String> roleMenuDetail(String id, String userId) {
        String currentRoleId = querySysUserRoleByUserId(userId).getRoleId();
        // 当前登陆的角色
        SysRole currentRole = sysRoleProvider.queryById(currentRoleId);
        // 当前登陆角色的最上级菜单
        List<SysMenu> currentMogamiSysMenus = InstanceUtil.newArrayList();
        if (currentRole.getRoleType() == 3) {
            currentMogamiSysMenus = sysMenuProvider.querySysMenusByParentId("0");
            // 当前角色为系统内置角色，赋予所有菜单的修改权限，没有权限的立即增加权限
            List<SysRoleMenu> currentSysRoleMenus = InstanceUtil.newArrayList();
            currentSysRoleMenus = packageSysRoleMenus(currentSysRoleMenus, currentRoleId, currentMogamiSysMenus);
            addPermissionToAdminRole(currentRoleId, currentSysRoleMenus, userId);
        } else {
            currentMogamiSysMenus = queryMogamiSysMenusByRoleId(currentRoleId);
        }
        // 存储当前登陆的角色权限信息
        List<SysRoleMenu> currentSysRoleMenus = InstanceUtil.newArrayList();
        currentSysRoleMenus = packageSysRoleMenus(currentSysRoleMenus, currentRoleId, currentMogamiSysMenus);
        // 查询当前操作的角色菜单信息
        // 当前登陆角色的最上级菜单
        List<SysMenu> operatingMogamiSysMenus = queryMogamiSysMenusByRoleId(id);
        List<SysRoleMenu> operatingSysRoleMenus = InstanceUtil.newArrayList();
        operatingSysRoleMenus = packageSysRoleMenus(operatingSysRoleMenus, id, operatingMogamiSysMenus);
        /**************************************** 封装前台数据start ****************************************/
        // 处理当前登陆的角色包含那些权限
        LinkedHashMap<String, String> currentPermissionMap = packageRolePermissionMap(currentSysRoleMenus);
        // 处理当前操作中的角色包含那些权限
        LinkedHashMap<String, String> operatingPermissionMap = packageRolePermissionMap(operatingSysRoleMenus);
        /**************************************** 封装前台数据start ****************************************/
        // 返回值
        LinkedHashMap<String, String> returnMap = new LinkedHashMap<String, String>();
        // 获取所有菜单
        List<SysMenu> allSysMenus = sysMenuMapper.selectAll();
        for (String key : currentPermissionMap.keySet()) {
            if (operatingPermissionMap.get(key) != null && !operatingPermissionMap.get(key).equals("")) {
                returnMap.put(key, operatingPermissionMap.get(key));
            } else {
                String permissionInfo_id = "";
                for (SysMenu sysMenu : allSysMenus) {
                    if (sysMenu.getMenuName().trim().equals(key.trim())) {
                        permissionInfo_id = sysMenu.getId();
                        break;
                    }
                }
                String permissionInfo = "";
                if (currentPermissionMap.get(key).indexOf("(submenu)") != -1) {
                    permissionInfo = permissionInfo_id + ":null(submenu)";
                } else {
                    permissionInfo = permissionInfo_id + ":null";
                }
                returnMap.put(key, permissionInfo);
            }
        }
        // 处理菜单
        return returnMap;
    }

    @Transactional
    @CacheEvict(value = {"getAuthorize", "sysPermission", "roleMenuDetail"}, allEntries = true)
    public void updateRoleMenu(String roleId, String permissionInfo, String userId) {
        List<SysRoleMenu> sysRoleMenus = new ArrayList<SysRoleMenu>();
        if (permissionInfo != null && !permissionInfo.equals("")) {
            String[] sysRoleMenuArray = permissionInfo.split("\\|");
            for (int i = 0; i < sysRoleMenuArray.length; i++) {
                String[] splitMenuIdAndPermissionArray = sysRoleMenuArray[i].split(":");
                String menuId = splitMenuIdAndPermissionArray[0];
                String this_permission = splitMenuIdAndPermissionArray[1];
                // 分割权限信息
                String[] this_permissionArray = this_permission.split(",");
                for (int j = 0; j < this_permissionArray.length; j++) {
                    SysRoleMenu sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setRoleId(roleId);
                    sysRoleMenu.setMenuId(menuId);
                    sysRoleMenu.setPermission(this_permissionArray[j]);
                    sysRoleMenu.setRemark("");
                    sysRoleMenus.add(sysRoleMenu);
                }
            }
        }
        if (sysRoleMenus.size() > 0) {
            sysAuthorizeMapper.deleteRoleMenu(sysRoleMenus.get(0).getRoleId());
            for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
                sysRoleMenuProvider.insert(sysRoleMenu, userId);
            }
        } else {
            sysAuthorizeMapper.deleteRoleMenu(roleId);
        }
    }

    @Cacheable(value = "getAuthorize")
    public List<SysMenuBean> queryAuthorizeByUserId(String currentUserId, String userId) {

        // 当前登陆的用户
        SysUser currentUser = sysUserProvider.queryById(currentUserId);

        List<String> menuIds = sysAuthorizeMapper.getAuthorize(userId);

        List<String> adminMenuIds = InstanceUtil.newArrayList();
        // 如果为内置管理员用户，不显示管理用户(201706261214373475)菜单和学院用户管理(201702081150257863)菜单
        if (currentUser.getUserType() == 3) {
            for (String ids : menuIds) {
                if (ids.equals("201702081150257863") || ids.equals("201706261214373475")) {
                    continue;
                } else {
                    adminMenuIds.add(ids);
                }
            }
        }


        List<SysMenuBean> menus = InstanceUtil.newArrayList();
        if (currentUser.getUserType() == 3) {
            menus = sysMenuProvider.getList(adminMenuIds, SysMenuBean.class);
        } else {
            menus = sysMenuProvider.getList(menuIds, SysMenuBean.class);
        }
        Map<String, List<SysMenuBean>> map = InstanceUtil.newHashMap();
        for (SysMenuBean sysMenuBean : menus) {
            if (map.get(sysMenuBean.getParentId()) == null) {
                List<SysMenuBean> menuBeans = InstanceUtil.newArrayList();
                map.put(sysMenuBean.getParentId(), menuBeans);
            }
            map.get(sysMenuBean.getParentId()).add(sysMenuBean);
        }
        List<SysMenuBean> result = InstanceUtil.newArrayList();
        for (SysMenuBean sysMenuBean : menus) {
            if ("0".equals(sysMenuBean.getParentId())) {
                sysMenuBean.setLeaf(0);
                sysMenuBean.setMenuBeans(getChildMenu(map, sysMenuBean.getId()));
                result.add(sysMenuBean);
            }
        }
        return result;
    }

    // 递归获取子菜单
    private List<SysMenuBean> getChildMenu(Map<String, List<SysMenuBean>> map, String id) {
        List<SysMenuBean> menus = map.get(id);
        if (menus != null) {
            for (SysMenuBean sysMenuBean : menus) {
                sysMenuBean.setMenuBeans(getChildMenu(map, sysMenuBean.getId()));
            }
        }
        return menus;
    }

    @Cacheable("sysPermission")
    public List<String> queryPermissionByUserId(String userId) {
        return sysAuthorizeMapper.queryPermissionByUserId(userId);
    }

    /****************************** 角色权限查看辅助方法start ******************************/
    /** 根据角色ID得到所有该角色的最上级菜单 **/
    @Cacheable("queryMogamiSysMenusByRoleId")
    public List<SysMenu> queryMogamiSysMenusByRoleId(String roleId) {
        List<SysMenu> result = sysAuthorizeMapper.queryMogamiSysMenusByRoleId(roleId);
        return result;
    }

    /** 按照父菜单ID得到指定角色的下级菜单 */
    @Cacheable("querySysMenusByRoleIdAndParentId")
    public List<SysMenu> querySysMenusByRoleIdAndParentId(String roleId, String parentId) {
        List<SysMenu> result = sysAuthorizeMapper.querySysMenusByRoleIdAndParentId(roleId, parentId);
        return result;
    }

    /** 按照菜单id查询该角色拥有的权限信息 **/
    public List<SysRoleMenu> querySysRoleMenusByMenuId(String roleId, String menuId) {
        List<SysRoleMenu> result = sysAuthorizeMapper.querySysRoleMenusByMenuId(roleId, menuId);
        return result;
    }


    /** 根据角色ID得到所有该角色的菜单 **/
    @Cacheable("querySysRoleMenusByRoleId")
    public List<SysRoleMenu> querySysRoleMenusByRoleId(String roleId) {
        List<SysRoleMenu> result = sysAuthorizeMapper.querySysRoleMenusByRoleId(roleId);
        return result;
    }

    /** 根据userId查询所属角色 */
    @Cacheable("querySysUserRoleByUserId")
    public SysUserRole querySysUserRoleByUserId(String userId) {
        return sysAuthorizeMapper.querySysUserRoleByUserId(userId);
    }

    /** 根据userId查询角色信息 **/
    public SysRole querySysRoleByUserId(String userId) {
        return sysAuthorizeMapper.querySysRoleByUserId(userId);
    }

    public List<SysRoleMenu> packageSysRoleMenus(List<SysRoleMenu> sysRoleMenus, String roleId, List<SysMenu> fatherSysMenus) {
        Map<String, Object> params = InstanceUtil.newHashMap();
        for (SysMenu sysMenu : fatherSysMenus) {
            List<SysRoleMenu> sysRoleMenusByMenuId = querySysRoleMenusByMenuId(roleId, sysMenu.getId());
            for (SysRoleMenu sysRoleMenu : sysRoleMenusByMenuId) {
                sysRoleMenus.add(sysRoleMenu);
            }
            params = InstanceUtil.newHashMap();
            params.put("parentId", sysMenu.getId());
            List<SysMenu> this_fatherSysMenus = sysMenuProvider.querySysMenusByParentId(sysMenu.getId());
            if (this_fatherSysMenus.size() > 0) {
                packageSysRoleMenus(sysRoleMenus, roleId, this_fatherSysMenus);
            }
        }
        return sysRoleMenus;
    }


    public LinkedHashMap<String, String> packageRolePermissionMap(List<SysRoleMenu> sysRoleMenus) {
        LinkedHashMap<String, String> permissionMap = new LinkedHashMap<String, String>();
        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
            SysMenu sysMenu = sysMenuProvider.queryById(sysRoleMenu.getMenuId());
            if (permissionMap.get(sysMenu.getMenuName()) != null) {
                String permissionInfo = permissionMap.get(sysMenu.getMenuName());
                if (sysMenu.getParentId() == null || sysMenu.getParentId().trim().equals("0")) {
                    permissionInfo = permissionInfo + "," + sysRoleMenu.getPermission();
                } else {
                    permissionInfo = permissionInfo + "," + sysRoleMenu.getPermission() + "(submenu)";
                }
                permissionMap.put(sysMenu.getMenuName(), permissionInfo);
            } else {
                String permissionInfo = sysRoleMenu.getPermission();
                if (sysMenu.getParentId() == null || sysMenu.getParentId().trim().equals("0")) {
                    permissionInfo = sysMenu.getId() + ": " + permissionInfo;
                } else {
                    permissionInfo = sysMenu.getId() + ": " + permissionInfo + "(submenu)";
                }
                permissionMap.put(sysMenu.getMenuName(), permissionInfo);
            }
        }

        return permissionMap;
    }

    // 当前角色为系统内置角色，赋予所有菜单的修改权限，没有权限的立即增加权限
    public void addPermissionToAdminRole(String roleId, List<SysRoleMenu> sysRoleMenus, String userId) {
        List<SysMenu> sysMenus = sysMenuMapper.selectAll();
        for (int i = 0; i < sysMenus.size(); i++) {
            boolean ifAddSysRoleMenu = true;
            for (int j = 0; j < sysRoleMenus.size(); j++) {
                if (sysRoleMenus.get(j).getMenuId().trim().equals(sysMenus.get(i).getId().trim())) {
                    ifAddSysRoleMenu = false;
                }
            }
            if (ifAddSysRoleMenu) {
                for (int k = 0; k < 4; k++) {
                    SysRoleMenu record = new SysRoleMenu();
                    record.setMenuId(sysMenus.get(i).getId());
                    if (k == 0) {
                        record.setPermission("read");
                    } else if (k == 1) {
                        record.setPermission("add");
                    } else if (k == 2) {
                        record.setPermission("update");
                    } else if (k == 3) {
                        record.setPermission("delete");
                    }
                    record.setRoleId(roleId);
                    record.setRemark("自动增加系统内置角色的角色权限");
                    record = sysRoleMenuProvider.insert(record, userId);
                    sysRoleMenus.add(record);
                }
            }
        }
    }

    /****************************** 角色权限查看辅助方法end ******************************/



    /****************************** 用户权限查看辅助方法start ******************************/
    /** 根据角色ID得到所有该角色的最上级菜单 **/
    @Cacheable("queryMogamiSysMenusByUserId")
    public List<SysMenu> queryMogamiSysMenusByUserId(String userId) {
        List<SysMenu> result = sysAuthorizeMapper.queryMogamiSysMenusByUserId(userId);
        return result;
    }

    /** 按照父菜单ID得到指定角色的下级菜单 */
    @Cacheable("querySysMenusByUserIdAndParentId")
    public List<SysMenu> querySysMenusByUserIdAndParentId(String userId, String parentId) {
        List<SysMenu> result = sysAuthorizeMapper.querySysMenusByUserIdAndParentId(userId, parentId);
        return result;
    }

    /** 按照菜单id查询该角色拥有的权限信息 **/
    public List<SysUserMenu> querySysUserMenusByMenuId(String userId, String menuId) {
        List<SysUserMenu> result = sysAuthorizeMapper.querySysUserMenusByMenuId(userId, menuId);
        return result;
    }


    /** 根据用户ID得到所有该用户的菜单 **/
    @Cacheable("querySysUserMenusByUserId")
    public List<SysUserMenu> querySysUserMenusByUserId(String userId) {
        List<SysUserMenu> result = sysAuthorizeMapper.querySysUserMenusByUserId(userId);
        return result;
    }

    public List<SysUserMenu> packageSysUserMenus(List<SysUserMenu> sysUserMenus, String userId, List<SysMenu> fatherSysMenus) {
        Map<String, Object> params = InstanceUtil.newHashMap();
        for (SysMenu sysMenu : fatherSysMenus) {
            List<SysUserMenu> sysUserMenusByMenuId = querySysUserMenusByMenuId(userId, sysMenu.getId());
            for (SysUserMenu sysUserMenu : sysUserMenusByMenuId) {
                sysUserMenus.add(sysUserMenu);
            }
            params = InstanceUtil.newHashMap();
            params.put("parentId", sysMenu.getId());
            List<SysMenu> this_fatherSysMenus = sysMenuProvider.querySysMenusByParentId(sysMenu.getId());
            if (this_fatherSysMenus.size() > 0) {
                packageSysUserMenus(sysUserMenus, userId, this_fatherSysMenus);
            }
        }
        return sysUserMenus;
    }


    public LinkedHashMap<String, String> packageUserPermissionMap(List<SysUserMenu> sysUserMenus) {
        LinkedHashMap<String, String> permissionMap = new LinkedHashMap<String, String>();
        for (SysUserMenu sysUserMenu : sysUserMenus) {
            SysMenu sysMenu = sysMenuProvider.queryById(sysUserMenu.getMenuId());
            if (permissionMap.get(sysMenu.getMenuName()) != null) {
                String permissionInfo = permissionMap.get(sysMenu.getMenuName());
                if (sysMenu.getParentId() == null || sysMenu.getParentId().trim().equals("0")) {
                    permissionInfo = permissionInfo + "," + sysUserMenu.getPermission();
                } else {
                    permissionInfo = permissionInfo + "," + sysUserMenu.getPermission() + "(submenu)";
                }
                permissionMap.put(sysMenu.getMenuName(), permissionInfo);
            } else {
                String permissionInfo = sysUserMenu.getPermission();
                if (sysMenu.getParentId() == null || sysMenu.getParentId().trim().equals("0")) {
                    permissionInfo = sysMenu.getId() + ": " + permissionInfo;
                } else {
                    permissionInfo = sysMenu.getId() + ": " + permissionInfo + "(submenu)";
                }
                permissionMap.put(sysMenu.getMenuName(), permissionInfo);
            }
        }

        return permissionMap;
    }

    // 当前角色为系统内置角色，赋予所有菜单的修改权限，没有权限的立即增加权限
    public void addPermissionToAdminUser(List<SysUserMenu> sysUserMenus, String userId) {
        List<SysMenu> sysMenus = sysMenuMapper.selectAll();
        for (int i = 0; i < sysMenus.size(); i++) {
            boolean ifAddSysUserMenu = true;
            for (int j = 0; j < sysUserMenus.size(); j++) {
                if (sysUserMenus.get(j).getMenuId().trim().equals(sysMenus.get(i).getId().trim())) {
                    ifAddSysUserMenu = false;
                }
            }
            if (ifAddSysUserMenu) {
                for (int k = 0; k < 4; k++) {
                    SysUserMenu record = new SysUserMenu();
                    record.setMenuId(sysMenus.get(i).getId());
                    if (k == 0) {
                        record.setPermission("read");
                    } else if (k == 1) {
                        record.setPermission("add");
                    } else if (k == 2) {
                        record.setPermission("update");
                    } else if (k == 3) {
                        record.setPermission("delete");
                    }
                    record.setUserId(userId);
                    record.setRemark("自动增加系统内置角色的角色权限");
                    record = sysUserMenuProvider.insert(record, userId);
                    sysUserMenus.add(record);
                }
            }
        }
    }

    /****************************** 用户权限查看辅助方法end ******************************/

    /** 查询用户所属角色 **/
    public Map<String, Object> userRoleDetail(String userId) {
        Map<String, Object> returnMap = InstanceUtil.newHashMap();
        List<SysRole> sysRoles = sysRoleMapper.selectAll();
        SysRole sysRole = sysAuthorizeMapper.querySysRoleByUserId(userId);
        if (sysRole == null) {
            sysRole = new SysRole();
            sysRole.setId(null);
            sysRole.setRoleName("--请选择角色--");
            sysRoles.add(sysRole);
        }
        returnMap.put("sysRoles", sysRoles);
        returnMap.put("sysRole_record", sysRole);
        return returnMap;
    }
}
