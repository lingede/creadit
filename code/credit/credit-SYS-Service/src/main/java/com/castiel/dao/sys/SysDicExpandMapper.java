package com.castiel.dao.sys;

import java.util.List;
import java.util.Map;

import com.castiel.model.generator.SysDicIndex;
import com.github.pagehelper.Page;

public interface SysDicExpandMapper {

	Page<String> queryDicIndex(Map<String, Object> params);

	Page<String> queryDic(Map<String, Object> params);
	
	List<SysDicIndex> getSysDicIndexsByDicIndexName(String dicIndexName);
	
	List<SysDicIndex> getSysDicIndexsByDicIndexKey(String dicIndexKey);

}
