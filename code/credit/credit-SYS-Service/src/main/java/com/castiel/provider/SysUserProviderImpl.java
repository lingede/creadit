/**
 * 
 */
package com.castiel.provider;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.core.support.login.ThirdPartyUser;
import com.castiel.core.util.SecurityUtil;
import com.castiel.dao.generator.SysUserThirdpartyMapper;
import com.castiel.dao.sys.PBasicDeptExpandMapper;
import com.castiel.dao.sys.SysUserExpandMapper;
import com.castiel.model.generator.PBasicDept;
import com.castiel.model.generator.SysUser;
import com.castiel.model.generator.SysUserThirdparty;
import com.castiel.model.sys.PBasicDeptBean;
import com.castiel.model.sys.SysUserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@DubboService(interfaceClass = SysUserProvider.class)
@CacheConfig(cacheNames = "sysUser")
public class SysUserProviderImpl extends BaseProviderImpl<SysUser> implements SysUserProvider {
    @Autowired
    private SysUserExpandMapper sysUserExpandMapper;
    @Autowired
    private SysUserThirdpartyMapper thirdpartyMapper;
    @Autowired
    private SysDicProvider sysDicProvider;
    @Autowired
    private PBasicDeptProvider pbasicDeptProvider;

    @Autowired
    private PBasicDeptExpandMapper pBasicDeptExpandMapper;

    public PageInfo<SysUser> query(Map<String, Object> params) {
        this.startPage(params);
        return getPage(sysUserExpandMapper.query(params));
    }

    public PageInfo<SysUserBean> queryBean(Map<String, Object> params) {
        this.startPage(params);
        Page<String> userIds = sysUserExpandMapper.query(params);
        Map<String, String> userTypeMap = sysDicProvider.queryDicByDicIndexKey("USERTYPE");
        PageInfo<SysUserBean> pageInfo = getPage(userIds, SysUserBean.class);
        for (SysUserBean userBean : pageInfo.getList()) {
            if (userBean.getUserType() != null) {
                userBean.setUserTypeText(userTypeMap.get(userBean.getUserType().toString()));
            }
            if (userBean.getDeptId() != null) {
                PBasicDept pBasicDept = pbasicDeptProvider.queryById(userBean.getDeptId());
                if (pBasicDept != null) {
                    userBean.setDeptName(pBasicDept.getDeptName());
                } else {
                    userBean.setDeptName("");
                }

            }
        }
        return pageInfo;
    }

    /** 查询第三方帐号用户Id */
    @Cacheable
    public String queryUserIdByThirdParty(String openId, String provider) {
        return sysUserExpandMapper.queryUserIdByThirdParty(provider, openId);
    }

    // 加密
    public String encryptPassword(String password) {
        return SecurityUtil.encryptMd5(SecurityUtil.encryptSHA(password));
    }

    // 重名验证
    public boolean checkUserName(String userId, String account) {
        // 数据库是否存在
        boolean isExist = false;
        List<SysUser> sysUsers = sysUserExpandMapper.getSysUsersByUserName(account);
        if (sysUsers.size() > 0) {
            // id==0则为添加操作
            SysUser sysUser = sysUsers.get(0);
            if (userId != null && !userId.trim().equals("0")) {
                if (!sysUser.getId().trim().equals(userId)) {
                    isExist = true;
                }
            } else {
                isExist = true;
            }
        } else {
            isExist = false;
        }
        return isExist;
    }

    /** 保存第三方帐号 */
    @Transactional
    public SysUser insertThirdPartyUser(ThirdPartyUser thirdPartyUser) {
        SysUser sysUser = new SysUser();
        sysUser.setSex(0);
        sysUser.setUserType(1);
        sysUser.setPassword(this.encryptPassword("123456"));
        sysUser.setUserName(thirdPartyUser.getUserName());
        sysUser.setAvatar(thirdPartyUser.getAvatarUrl());
        // 初始化第三方信息
        SysUserThirdparty thirdparty = new SysUserThirdparty();
        thirdparty.setId(createId("SysUserThirdparty"));
        thirdparty.setProvider(thirdPartyUser.getProvider());
        thirdparty.setOpenId(thirdPartyUser.getOpenid());
        thirdparty.setCreateTime(new Date());

        this.update(sysUser);
        sysUser.setAccount(thirdparty.getProvider() + sysUser.getId());
        this.update(sysUser);
        thirdparty.setUserId(sysUser.getId());
        thirdpartyMapper.insert(thirdparty);
        return sysUser;
    }

    public void updateLocked(String id, boolean locked) {
        SysUser record = queryById(id);
        record.setLocked(locked);
        update(record);
    }

    @Override
    public Map<String, Object> detail(String id) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        /* 获取所有部门信息 */
        List<PBasicDeptBean> pBasicDeptBeans = pBasicDeptExpandMapper.qCombobox();


        // List<SysDept> sysDepts = sysDeptMapper.selectAll();
        // List<SysDeptBean> sysDeptBeans = new ArrayList<SysDeptBean>();
        // SysDeptBean sysDeptBean = new SysDeptBean();
        // sysDeptBean.setParentDept("请选择部门");
        // sysDeptBean.setDeptName("--请选择部门--");
        // sysDeptBean.setId(null);
        // sysDeptBeans.add(sysDeptBean);
        // for(SysDept sysDept:sysDepts){
        // sysDeptBean = new SysDeptBean();
        // FatherToChildCopyUtil.Copy(sysDept, sysDeptBean);
        // if(sysDeptBean.getParentId()!=null && !sysDeptBean.getParentId().trim().equals(("0"))){
        // sysDeptBean.setParentDept(sysDeptProvider.queryById(sysDeptBean.getParentId()).getDeptName());
        // }else{
        // sysDeptBean.setParentDept("顶级部门");
        // }
        // sysDeptBeans.add(sysDeptBean);
        // }
        if (id.trim().equals("0")) {
            /** 查询的元素 **/
            returnMap.put("record", null);
            returnMap.put("pBasicDeptBeans", pBasicDeptBeans);
        } else {
            /** 查询的元素 **/
            SysUser record = queryById(id);
            returnMap.put("record", record);
            returnMap.put("pBasicDeptBeans", pBasicDeptBeans);
        }

        return returnMap;
    }

    @Override
    public PageInfo<SysUserBean> queryAdmin(Map<String, Object> params) {
        this.startPage(params);
        Page<String> userIds = sysUserExpandMapper.queryAdmin(params);
        Map<String, String> userTypeMap = sysDicProvider.queryDicByDicIndexKey("USERTYPE");
        PageInfo<SysUserBean> pageInfo = getPage(userIds, SysUserBean.class);
        for (SysUserBean userBean : pageInfo.getList()) {
            if (userBean.getUserType() != null) {
                userBean.setUserTypeText(userTypeMap.get(userBean.getUserType().toString()));
            }
            if (userBean.getDeptId() != null) {
                PBasicDept pBasicDept = pbasicDeptProvider.queryById(userBean.getDeptId());
                if (pBasicDept != null) {
                    userBean.setDeptName(pBasicDept.getDeptName());
                } else {
                    userBean.setDeptName("");
                }

            }
        }
        return pageInfo;
    }
}
