package com.castiel.dao.sys;

import org.apache.ibatis.annotations.Param;

import com.castiel.core.base.BaseExpandMapper;

public interface SysPermissionExpandMapper extends BaseExpandMapper {

	Integer getPermissionByUserId(@Param("userId") Integer userId, @Param("url") String url);
}
