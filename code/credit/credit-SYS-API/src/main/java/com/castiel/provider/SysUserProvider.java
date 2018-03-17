/**
 * 
 */
package com.castiel.provider;

import java.util.Map;

import com.castiel.core.base.BaseProvider;
import com.castiel.core.support.login.ThirdPartyUser;
import com.castiel.model.generator.SysUser;
import com.castiel.model.sys.SysUserBean;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author ShenHuaJie
 * @version 2016年5月15日 上午11:21:47
 */
public interface SysUserProvider extends BaseProvider<SysUser> {

    public PageInfo<SysUserBean> queryBean(Map<String, Object> params);

    public PageInfo<SysUserBean> queryAdmin(Map<String, Object> params);

    public String encryptPassword(String password);

    /** 查询第三方帐号用户Id */
    public String queryUserIdByThirdParty(String openId, String provider);

    /** 保存第三方帐号 */
    public SysUser insertThirdPartyUser(ThirdPartyUser thirdPartyUser);

    public void updateLocked(String id, boolean locked);

    public boolean checkUserName(String userId, String account);

    public Map<String, Object> detail(String id);
}
