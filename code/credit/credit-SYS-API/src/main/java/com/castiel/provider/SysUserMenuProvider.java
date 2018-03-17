package com.castiel.provider;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.SysUserMenu;

public interface SysUserMenuProvider extends BaseProvider<SysUserMenu> {
	
	public SysUserMenu insert(SysUserMenu record,String userId);

}
