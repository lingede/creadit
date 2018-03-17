package com.castiel.dao.sys;

import java.util.List;

import com.castiel.core.base.BaseExpandMapper;
import com.castiel.model.generator.SysDept;

public interface SysDeptExpandMapper extends BaseExpandMapper {
	
	List<SysDept> getSysDeptsByDeptName(String deptName);

}