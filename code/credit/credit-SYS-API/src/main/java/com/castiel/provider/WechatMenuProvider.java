package com.castiel.provider;

import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.WechatMenu;
import com.castiel.model.wechat.WechatMenuBean;
import com.github.pagehelper.PageInfo;

public interface WechatMenuProvider extends BaseProvider<WechatMenu>  {
	
	public PageInfo<WechatMenuBean> queryBean(Map<String, Object> params);
	
	public Map<String, Object> detail(String id);
	
	public JSONObject getSyncData();
	
	/**得到最顶级微信菜单**/
	public List<WechatMenu> queryTopLevelWechatMenus();
	/**根据父级菜单获取下级菜单*/
	public List<WechatMenu> queryWechatMenusByParentId(String parentId);

}
