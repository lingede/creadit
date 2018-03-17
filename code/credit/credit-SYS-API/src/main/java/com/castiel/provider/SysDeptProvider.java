package com.castiel.provider;

import java.util.Map;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.SysDept;
import com.castiel.model.sys.SysDeptBean;
import com.github.pagehelper.PageInfo;

public interface SysDeptProvider extends BaseProvider<SysDept> {
	
	public PageInfo<SysDeptBean> queryBean(Map<String, Object> params);

	public Map<String, Object> detail(String id);
	
	public boolean checkDeptName(String deptId,String deptName);
}
