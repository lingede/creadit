package com.castiel.service.sys;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.Assert;
import com.castiel.core.support.login.LoginHelper;
import com.castiel.core.support.login.ThirdPartyUser;
import com.castiel.core.util.WebUtil;
import com.castiel.model.generator.SysUser;
import com.castiel.model.sys.SysUserBean;
import com.castiel.provider.SysUserProvider;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:47:21
 */
@Service
public class SysUserService extends BaseService<SysUserProvider, SysUser> {
    @Autowired
    public void setProvider(SysUserProvider provider) {
        this.provider = provider;
    }

    /** 修改用户信息 */
    @CachePut
    public void updateUserInfo(SysUser sysUser) {
        Assert.isNotBlank(sysUser.getId(), "USER_ID");
        Assert.isNotBlank(sysUser.getAccount(), "ACCOUNT");
        Assert.length(sysUser.getAccount(), 3, 15, "ACCOUNT");
        SysUser user = this.queryById(sysUser.getId());
        Assert.notNull(user, "USER", sysUser.getId());
        if (StringUtils.isBlank(sysUser.getPassword())) {
            sysUser.setPassword(user.getPassword());
        }
        if (StringUtil.isEmpty(sysUser.getAvatar())) {
            sysUser.setAvatar(user.getAvatar());
        }
        sysUser.setUpdateBy(WebUtil.getCurrentUser());
        provider.update(sysUser);
    }

    public PageInfo<SysUserBean> queryBean(Map<String, Object> params) {
        return provider.queryBean(params);
    }

    public PageInfo<SysUserBean> queryAdmin(Map<String, Object> params) {
        return provider.queryAdmin(params);
    }

    public void updatePassword(String id, String password) {
        Assert.isNotBlank(id, "USER_ID");
        Assert.isNotBlank(password, "PASSWORD");
        SysUser sysUser = provider.queryById(id);
        Assert.notNull(sysUser, "USER", id);
        String userId = WebUtil.getCurrentUser();
        if (!id.equals(userId)) {
            SysUser user = provider.queryById(userId);
            if (user.getUserType() == 1) {
                throw new UnauthorizedException();
            }
        }
        sysUser.setPassword(encryptPassword(password));
        sysUser.setUpdateBy(WebUtil.getCurrentUser());
        provider.update(sysUser);
    }

    public String encryptPassword(String password) {
        return provider.encryptPassword(password);
    }

    // 账号重名验证
    public boolean checkUserName(String userId, String account) {
        return provider.checkUserName(userId, account);
    }


    public void thirdPartyLogin(ThirdPartyUser thirdUser) {
        SysUser sysUser = null;
        // 查询是否已经绑定过
        String userId = provider.queryUserIdByThirdParty(thirdUser.getOpenid(), thirdUser.getProvider());
        if (userId == null) {
            sysUser = insertThirdPartyUser(thirdUser);
        } else {
            sysUser = queryById(userId);
        }
        LoginHelper.login(sysUser.getAccount(), sysUser.getPassword());
    }

    public SysUser insertThirdPartyUser(ThirdPartyUser thirdUser) {
        return provider.insertThirdPartyUser(thirdUser);
    }

    public void updateLocked(String id, boolean locked) {
        provider.updateLocked(id, locked);
    }

    // 查询用户详情
    public Map<String, Object> detail(String id) {
        return provider.detail(id);
    }
}
