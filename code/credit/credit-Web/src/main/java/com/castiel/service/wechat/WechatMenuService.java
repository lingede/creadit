package com.castiel.service.wechat;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.model.generator.WechatMenu;
import com.castiel.model.wechat.WechatMenuBean;
import com.castiel.provider.WechatMenuProvider;
import com.castiel.wechat.WxAccessTokenUtil;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;


@Service
public class WechatMenuService extends BaseService<WechatMenuProvider, WechatMenu> {

	@DubboReference
	public void setProvider(WechatMenuProvider provider) {
		this.provider = provider;
	}

	public PageInfo<WechatMenuBean> queryBean(Map<String, Object> params) {
		PageInfo<WechatMenuBean> pageInfo = provider.queryBean(params);
		return pageInfo;
	};

	public Map<String, Object> detail(String id) {
		return provider.detail(id);
	}

	public List<WechatMenu> queryTopLevelWechatMenus() {
		return provider.queryTopLevelWechatMenus();
	}

	public List<WechatMenu> queryWechatMenusByParentId(String parentId) {
		return provider.queryWechatMenusByParentId(parentId);
	}

	public void sync(HttpServletRequest request) {
		JSONObject jsonObject = provider.getSyncData();
		// 同步操作
		String access_token = WxAccessTokenUtil.getBaseAccessToken(request).getString("access_token");
		// 覆盖操作
		JSONObject weChatMenu_jsonObject = JSONObject.fromObject(WxAccessTokenUtil.loadJson("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token, jsonObject.toString()));
		System.err.println(jsonObject.toString());
		System.err.println(weChatMenu_jsonObject);
	}



}
