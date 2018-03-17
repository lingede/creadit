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
import com.castiel.dao.generator.WechatMenuMapper;
import com.castiel.dao.wechat.WechatMenuExpandMapper;
import com.castiel.model.generator.WechatMenu;
import com.castiel.model.wechat.WechatMenuBean;
import com.castiel.wechat.vo.WeChatMenuPackageVo;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * @author YangBin
 * @version 2017年3月8日 下午3:19:19
 */
@CacheConfig(cacheNames = "wechatMenu")
@DubboService(interfaceClass = WechatMenuProvider.class)
public class WechatMenuProviderImpl extends BaseProviderImpl<WechatMenu> implements WechatMenuProvider {

	@Autowired
	private WechatMenuMapper wechatMenuMapper;
	@Autowired
	private WechatMenuExpandMapper weChatMenuExpandMapper;

	@Override
	public PageInfo<WechatMenuBean> queryBean(Map<String, Object> params) {
		List<WechatMenu> topLevelWechatMenu = weChatMenuExpandMapper.queryTopLevelWechatMenus();
		// 需要传递到前台的菜单List
		List<WechatMenu> WeChatMenusPackaged = new ArrayList<WechatMenu>();
		for (int i = 0; i < topLevelWechatMenu.size(); i++) {
			WeChatMenusPackaged.add(topLevelWechatMenu.get(i));
			List<WechatMenu> sub_button = weChatMenuExpandMapper.queryWechatMenusByParentId(topLevelWechatMenu.get(i).getId());
			for (int j = 0; j < sub_button.size(); j++) {
				sub_button.get(j).setName(sub_button.get(j).getName());
				WeChatMenusPackaged.add(sub_button.get(j));
			}
		}
		List<WechatMenuBean> wechatMenuBeans = new ArrayList<WechatMenuBean>();
		for (int i = 0; i < WeChatMenusPackaged.size(); i++) {
			WechatMenuBean wechatMenuBean = new WechatMenuBean();
			wechatMenuBean.setName(WeChatMenusPackaged.get(i).getName());
			wechatMenuBean.setCreateBy(WeChatMenusPackaged.get(i).getCreateBy());
			wechatMenuBean.setCreateTime(WeChatMenusPackaged.get(i).getCreateTime());
			wechatMenuBean.setEnable(WeChatMenusPackaged.get(i).getEnable());
			wechatMenuBean.setId(WeChatMenusPackaged.get(i).getId());
			wechatMenuBean.setKey(WeChatMenusPackaged.get(i).getKey());
			wechatMenuBean.setLevel(WeChatMenusPackaged.get(i).getLevel());
			wechatMenuBean.setLocked(WeChatMenusPackaged.get(i).getLocked());
			wechatMenuBean.setMediaId(WeChatMenusPackaged.get(i).getMediaId());
			wechatMenuBean.setParentId(WeChatMenusPackaged.get(i).getParentId());
			if (WeChatMenusPackaged.get(i).getParentId() != null && !WeChatMenusPackaged.get(i).getParentId().equals("")) {
				wechatMenuBean.setParentMenu(queryById(WeChatMenusPackaged.get(i).getParentId()).getName());
			} else {
				wechatMenuBean.setParentMenu("");
			}
			wechatMenuBean.setRemark(WeChatMenusPackaged.get(i).getRemark());
			wechatMenuBean.setSort(WeChatMenusPackaged.get(i).getSort());
			wechatMenuBean.setType(WeChatMenusPackaged.get(i).getType());
			wechatMenuBean.setUpdateBy(WeChatMenusPackaged.get(i).getUpdateBy());
			wechatMenuBean.setUpdateTime(WeChatMenusPackaged.get(i).getUpdateTime());
			if (WeChatMenusPackaged.get(i).getUrl() != null && WeChatMenusPackaged.get(i).getUrl().length() > 40) {
				wechatMenuBean.setUrl(WeChatMenusPackaged.get(i).getUrl().substring(0, 40) + "...");
			} else {
				wechatMenuBean.setUrl(WeChatMenusPackaged.get(i).getUrl());
			}
			wechatMenuBeans.add(wechatMenuBean);
		}
		// 设置返回对象
		PageInfo<WechatMenuBean> pageInfo = new PageInfo<WechatMenuBean>();
		pageInfo.setList(wechatMenuBeans);
		pageInfo.setSize(wechatMenuBeans.size());
		pageInfo.setPages(1);
		pageInfo.setHasNextPage(false);
		pageInfo.setFirstPage(1);
		pageInfo.setIsLastPage(true);
		pageInfo.setPages(1);
		pageInfo.setTotal(wechatMenuBeans.size());
		pageInfo.setPageSize(wechatMenuBeans.size());
		// 先查询所有的一级菜单
		return pageInfo;
	}

	@Override
	public Map<String, Object> detail(String id) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		/** 上级菜单 **/
		List<WechatMenu> wechatMenus = wechatMenuMapper.selectAll();
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < wechatMenus.size(); i++) {
			if (!wechatMenus.get(i).getId().trim().equals(id.trim()) && wechatMenus.get(i).getLevel() == 1) {
				ids.add(wechatMenus.get(i).getId());
			}
		}
		wechatMenus = getList(ids);
		List<WechatMenuBean> wechatMenuBeans = new ArrayList<WechatMenuBean>();
		WechatMenuBean wechatMenuBean = new WechatMenuBean();
		wechatMenuBean.setParentMenu("无上级菜单");
		wechatMenuBean.setName("--无上级菜单--");
		wechatMenuBean.setId("0");
		wechatMenuBeans.add(wechatMenuBean);
		for (WechatMenu wechatMenu : wechatMenus) {
			wechatMenuBean = new WechatMenuBean();
			FatherToChildCopyUtil.Copy(wechatMenu, wechatMenuBean);
			if (wechatMenuBean.getParentId() != null && !wechatMenuBean.getParentId().trim().equals("0")) {
				System.err.println(wechatMenuBean.getParentId());
				wechatMenuBean.setParentMenu(queryById(wechatMenuBean.getParentId()).getName());
			} else {
				wechatMenuBean.setParentMenu("顶级菜单");
			}
			wechatMenuBeans.add(wechatMenuBean);
		}
		/** 查询的元素 **/
		if (!id.trim().equals("0")) {
			WechatMenu record = queryById(id);
			returnMap.put("record", record);
		} else {
			WechatMenu record = new WechatMenu();
			record.setParentId("0");
			returnMap.put("record", record);
		}
		returnMap.put("fatherWechatMenus", wechatMenuBeans);
		return returnMap;
	}

	@Override
	public JSONObject getSyncData() {
		// 得到所有一级菜单
		List<WechatMenu> weChatMenus = weChatMenuExpandMapper.queryTopLevelWechatMenus();
		// 封装发送到微信的格式数据
		List<WeChatMenuPackageVo> packagedWeChatMenus = new ArrayList<WeChatMenuPackageVo>();
		for (int i = 0; i < weChatMenus.size(); i++) {
			WeChatMenuPackageVo weChatMenuPackageVo = new WeChatMenuPackageVo();
			weChatMenuPackageVo.setName(weChatMenus.get(i).getName());
			weChatMenuPackageVo.setType(weChatMenus.get(i).getType());
			weChatMenuPackageVo.setKey(weChatMenus.get(i).getKey());
			weChatMenuPackageVo.setMedia_id(weChatMenus.get(i).getMediaId());
			weChatMenuPackageVo.setUrl(weChatMenus.get(i).getUrl());
			// 子菜单
			List<WechatMenu> sub_button = weChatMenuExpandMapper.queryWechatMenusByParentId(weChatMenus.get(i).getId());
			List<WeChatMenuPackageVo> sub_packagedWeChatMenus = null;
			if (sub_button != null && sub_button.size() > 0) {
				sub_packagedWeChatMenus = new ArrayList<WeChatMenuPackageVo>();
				for (int j = 0; j < sub_button.size(); j++) {
					WechatMenu sub_weChatMenu = sub_button.get(j);
					WeChatMenuPackageVo sub_weChatMenuPackageVo = new WeChatMenuPackageVo();
					sub_weChatMenuPackageVo.setName(sub_weChatMenu.getName());
					sub_weChatMenuPackageVo.setType(sub_weChatMenu.getType());
					sub_weChatMenuPackageVo.setKey(sub_weChatMenu.getKey());
					sub_weChatMenuPackageVo.setMedia_id(sub_weChatMenu.getMediaId());
					sub_weChatMenuPackageVo.setUrl(sub_weChatMenu.getUrl());
					sub_packagedWeChatMenus.add(sub_weChatMenuPackageVo);
				}
			}
			weChatMenuPackageVo.setSub_button(sub_packagedWeChatMenus);
			packagedWeChatMenus.add(weChatMenuPackageVo);
		}
		JSONObject jsonObject = new JSONObject();
		try {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			JSONArray jsonArray = JSONArray.fromObject(packagedWeChatMenus, jsonConfig);
			jsonObject.put("button", jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;

	}

	/** 得到最顶级微信菜单 **/
	@Override
	public List<WechatMenu> queryTopLevelWechatMenus() {
		return weChatMenuExpandMapper.queryTopLevelWechatMenus();
	}

	/** 根据父级菜单获取下级菜单 */
	@Override
	public List<WechatMenu> queryWechatMenusByParentId(String parentId) {
		return weChatMenuExpandMapper.queryWechatMenusByParentId(parentId);
	}
}
