package com.castiel.dao.wechat;


import java.util.List;
import com.castiel.core.base.BaseExpandMapper;
import com.castiel.model.generator.WechatMenu;

public interface WechatMenuExpandMapper extends BaseExpandMapper {
	/**得到最顶级微信菜单**/
	public List<WechatMenu> queryTopLevelWechatMenus();
	/**根据父级菜单获取下级菜单*/
	public List<WechatMenu> queryWechatMenusByParentId(String parentId);
}
