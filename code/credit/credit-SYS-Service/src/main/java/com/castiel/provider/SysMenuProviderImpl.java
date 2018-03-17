package com.castiel.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.core.util.FatherToChildCopyUtil;
import com.castiel.core.util.PageInfoCopyUtil;
import com.castiel.dao.generator.SysMenuMapper;
import com.castiel.dao.sys.SysMenuExpandMapper;
import com.castiel.model.generator.SysMenu;
import com.castiel.model.sys.SysMenuBean;
import com.github.pagehelper.PageInfo;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@CacheConfig(cacheNames = "sysMenu")
@DubboService(interfaceClass = SysMenuProvider.class)
public class SysMenuProviderImpl extends BaseProviderImpl<SysMenu> implements SysMenuProvider {
	@Autowired
	private SysMenuExpandMapper sysMenuExpandMapper;
	@Autowired
	private SysDicProvider sysDicProvider;
	@Autowired
	private SysMenuMapper sysMenuMapper;

	public PageInfo<SysMenu> query(Map<String, Object> params) {
		this.startPage(params);
		PageInfo<SysMenu> pageInfo = getPage(sysMenuExpandMapper.query(params));
		Map<String, String> menuTypeMap = sysDicProvider.queryDicByDicIndexKey("MENUTYPE");
		for (SysMenu sysMenu : pageInfo.getList()) {
			if (sysMenu.getMenuType() != null) {
				sysMenu.setRemark(menuTypeMap.get(sysMenu.getMenuType().toString()));
			}
		}
		return pageInfo;
	}

	@Override
	public PageInfo<SysMenuBean> queryBean(Map<String, Object> params) {
		this.startPage(params);
		PageInfo<SysMenu> pageInfo = getPage(sysMenuExpandMapper.query(params));
		Map<String, String> menuTypeMap = sysDicProvider.queryDicByDicIndexKey("MENUTYPE");
		List<SysMenuBean> sysMenuBeans = new ArrayList<SysMenuBean>();
		for (SysMenu sysMenu : pageInfo.getList()) {
			SysMenuBean sysMenuBean = new SysMenuBean();
			FatherToChildCopyUtil.Copy(sysMenu, sysMenuBean);
			if (sysMenuBean.getMenuType() != null) {
				sysMenuBean.setRemark(menuTypeMap.get(sysMenuBean.getMenuType()));
			}
			if (sysMenuBean.getParentId() != null && !sysMenuBean.getParentId().trim().equals("0")) {
				sysMenuBean.setParentMenu(queryById(sysMenuBean.getParentId()).getMenuName());
			} else {
				sysMenuBean.setParentMenu("无上级菜单");
			}
			sysMenuBeans.add(sysMenuBean);
		}
		@SuppressWarnings("unchecked")
		PageInfo<SysMenuBean> return_pageInfo = PageInfoCopyUtil.Copy(pageInfo, sysMenuBeans);
		return return_pageInfo;
	}

	@Override
	public Map<String, Object> detail(String id) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		/** 上级菜单 **/
		List<SysMenu> sysMenus = sysMenuMapper.selectAll();
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < sysMenus.size(); i++) {
			if (sysMenus.get(i).getIsShow() && !sysMenus.get(i).getId().trim().equals(id.trim())) {
				ids.add(sysMenus.get(i).getId());
			}
		}
		sysMenus = getList(ids);
		List<SysMenuBean> sysMenuBeans = new ArrayList<SysMenuBean>();
		SysMenuBean sysMenuBean = new SysMenuBean();
		sysMenuBean.setParentMenu("无上级菜单");
		sysMenuBean.setMenuName("--无上级菜单--");
		sysMenuBean.setId("0");
		sysMenuBeans.add(sysMenuBean);
		for (SysMenu sysMenu : sysMenus) {
			sysMenuBean = new SysMenuBean();
			FatherToChildCopyUtil.Copy(sysMenu, sysMenuBean);
			if (sysMenuBean.getParentId() != null && !sysMenuBean.getParentId().trim().equals("0")) {
				sysMenuBean.setParentMenu(queryById(sysMenuBean.getParentId()).getMenuName());
			} else {
				sysMenuBean.setParentMenu("顶级菜单");
			}
			sysMenuBeans.add(sysMenuBean);
		}
		/** 查询的元素 **/
		if (!id.trim().equals("0")) {
			SysMenu record = queryById(id);
			returnMap.put("record", record);
		} else {
			SysMenu record = new SysMenu();
			record.setParentId("0");
			record.setIsShow(true);
			record.setExpand(false);
			returnMap.put("record", record);
		}
		returnMap.put("fatherSysMenus", sysMenuBeans);
		return returnMap;
	}

	public List<SysMenu> querySysMenusByParentId(String parentId) {
		return sysMenuExpandMapper.querySysMenusByParentId(parentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.castiel.provider.SysMenuProvider#getPermissions()
	 */
	@Override
	public List<Map<String, String>> getPermissions() {
		return sysMenuExpandMapper.getPermissions();
	}

	@Override
	public boolean checkMenuName(String id, String menuName) {
		// 数据库是否存在
		boolean isExist = false;
		List<SysMenu> sysMenus = sysMenuExpandMapper.getSysMenusByMenuName(menuName);
		if (sysMenus.size() > 0) {
			// id==0则为添加操作
			SysMenu sysMenu = sysMenus.get(0);
			if (id != null && !id.trim().equals("0")) {
				if (!sysMenu.getId().trim().equals(id)) {
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
