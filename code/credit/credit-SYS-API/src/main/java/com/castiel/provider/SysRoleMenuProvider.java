package com.castiel.provider;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.SysRoleMenu;

public interface SysRoleMenuProvider extends BaseProvider<SysRoleMenu> {
	
	public SysRoleMenu insert(SysRoleMenu record,String userId);

}
