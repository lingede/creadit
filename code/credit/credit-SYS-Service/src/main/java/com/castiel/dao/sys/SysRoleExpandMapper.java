package com.castiel.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.castiel.core.base.BaseExpandMapper;
import com.castiel.model.generator.SysRole;

public interface SysRoleExpandMapper extends BaseExpandMapper {

    List<String> queryPermission(@Param("roleId") String id);

    List<String> getPermissions(@Param("roleId") String id);
    
    List<SysRole> getSysRolesByRoleName(String roleName);
}
