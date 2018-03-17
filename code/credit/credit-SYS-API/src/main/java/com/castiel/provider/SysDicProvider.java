package com.castiel.provider;

import java.util.Map;

import com.castiel.model.generator.SysDic;
import com.castiel.model.generator.SysDicIndex;

import com.github.pagehelper.PageInfo;

public interface SysDicProvider {
	public Map<String, Map<String, String>> getAllDic();

	public void updateDicIndex(SysDicIndex record);

	public void updateDic(SysDic record);

	public void deleteDic(String id);

	public SysDicIndex queryDicIndexById(String id);

	public SysDic queryDicById(String id);

	public PageInfo<SysDicIndex> queryDicIndex(Map<String, Object> params);

	public PageInfo<SysDic> queryDic(Map<String, Object> params);

	public void deleteDicIndex(String id);
	
	public Map<String, String> queryDicByDicIndexKey(String key);
	
	public boolean checkDicIndexName(String id,String dicIndexName);
	
	public boolean checkDicIndexKey(String id,String dicIndexKey);
}
