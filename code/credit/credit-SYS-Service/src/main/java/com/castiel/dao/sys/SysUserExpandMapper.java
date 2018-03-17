/**
 * 
 */
package com.castiel.dao.sys;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.castiel.core.base.BaseExpandMapper;
import com.castiel.model.generator.SysUser;
import com.github.pagehelper.Page;

/**
 * @author ShenHuaJie
 */
public interface SysUserExpandMapper extends BaseExpandMapper {

    String queryUserIdByThirdParty(@Param("provider") String provider, @Param("openId") String openId);

    List<SysUser> getSysUsersByUserName(String account);

    Page<String> queryAdmin(Map<String, Object> params);
}
