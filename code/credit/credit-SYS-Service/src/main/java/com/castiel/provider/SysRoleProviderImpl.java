package com.castiel.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.core.util.FatherToChildCopyUtil;
import com.castiel.dao.generator.SysDeptMapper;
import com.castiel.dao.sys.SysRoleExpandMapper;
import com.castiel.model.generator.SysDept;
import com.castiel.model.generator.SysRole;
import com.castiel.model.sys.SysDeptBean;
import com.castiel.model.sys.SysRoleBean;
import com.github.pagehelper.PageInfo;

@CacheConfig(cacheNames = "sysRole")
@DubboService(interfaceClass = SysRoleProvider.class)
public class SysRoleProviderImpl extends BaseProviderImpl<SysRole> implements SysRoleProvider {
	@Autowired
	private SysRoleExpandMapper sysRoleExpandMapper;
	@Autowired
	private SysDeptProvider sysDeptProvider;
	@Autowired
	private SysDeptMapper sysDeptMapper;

	public PageInfo<SysRole> query(Map<String, Object> params) {
		startPage(params);
		return getPage(sysRoleExpandMapper.query(params));
	}

	public PageInfo<SysRoleBean> queryBean(Map<String, Object> params) {
		startPage(params);
		PageInfo<SysRoleBean> pageInfo = getPage(sysRoleExpandMapper.query(params), SysRoleBean.class);
		// 权限信息
		for (SysRoleBean bean : pageInfo.getList()) {
			List<String> permissions = sysRoleExpandMapper.queryPermission(bean.getId());
			if (bean.getDeptId() != null && !bean.getDeptId().trim().equals("") && !bean.getDeptId().trim().equals("0")) {
				bean.setDept(sysDeptProvider.queryById(bean.getDeptId()).getDeptName());
			}
			for (String permission : permissions) {
				if (StringUtils.isBlank(bean.getPermission())) {
					bean.setPermission(permission);
				} else {
					bean.setPermission(bean.getPermission() + ";" + permission);
				}
			}
		}
		return pageInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.castiel.provider.SysRoleProvider#getPermissions(java.lang.String)
	 */
	@Override
	public List<String> getPermissions(String id) {
		return sysRoleExpandMapper.getPermissions(id);
	}

	@Override
	public Map<String, Object> detail(String id) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		/* 获取所有部门信息 */
		List<SysDept> sysDepts = sysDeptMapper.selectAll();
		List<SysDeptBean> sysDeptBeans = new ArrayList<SysDeptBean>();
		SysDeptBean sysDeptBean = new SysDeptBean();
		sysDeptBean.setParentDept("请选择部门");
		sysDeptBean.setDeptName("--请选择部门--");
		sysDeptBean.setId(null);
		sysDeptBeans.add(sysDeptBean);
		for (SysDept sysDept : sysDepts) {
			sysDeptBean = new SysDeptBean();
			FatherToChildCopyUtil.Copy(sysDept, sysDeptBean);
			if (sysDeptBean.getParentId() != null && !sysDeptBean.getParentId().trim().equals(("0"))) {
				sysDeptBean.setParentDept(sysDeptProvider.queryById(sysDeptBean.getParentId()).getDeptName());
			} else {
				sysDeptBean.setParentDept("顶级部门");
			}
			sysDeptBeans.add(sysDeptBean);
		}
		if (id.trim().equals("0")) {
			/** 查询的元素 **/
			SysRole record = new SysRole();
			record.setRoleType(null);
			record.setDeptId(null);
			returnMap.put("record", record);
			returnMap.put("sysDeptBeans", sysDeptBeans);
		} else {
			/** 查询的元素 **/
			SysRole record = queryById(id);
			returnMap.put("record", record);
			returnMap.put("sysDeptBeans", sysDeptBeans);
		}

		return returnMap;
	}

	// 角色名重名验证
	public boolean checkRoleName(String roleId, String roleName) {
		// 数据库是否存在
		boolean isExist = false;
		List<SysRole> sysRoles = sysRoleExpandMapper.getSysRolesByRoleName(roleName);
		if (sysRoles.size() > 0) {
			// id==0则为添加操作
			SysRole sysRole = sysRoles.get(0);
			if (roleId != null && !roleId.trim().equals("0")) {
				if (!sysRole.getId().trim().equals(roleId)) {
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
}
