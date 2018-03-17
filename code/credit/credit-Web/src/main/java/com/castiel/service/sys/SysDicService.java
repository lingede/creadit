package com.castiel.service.sys;

import java.util.Map;

import com.castiel.core.config.Resources;
import com.castiel.core.support.Assert;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.core.util.WebUtil;
import com.castiel.model.generator.SysDic;
import com.castiel.model.generator.SysDicIndex;
import com.castiel.provider.SysDicProvider;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

@Service
public class SysDicService {
    @DubboReference
	private SysDicProvider provider;

	public PageInfo<SysDicIndex> queryDicIndex(Map<String, Object> params) {
		return provider.queryDicIndex(params);
	}

	public PageInfo<SysDic> queryDic(Map<String, Object> params) {
		return provider.queryDic(params);
	}

	public void addDicIndex(SysDicIndex record) {
		record.setCreateBy(WebUtil.getCurrentUser());
		provider.updateDicIndex(record);
	}

	public void updateDicIndex(SysDicIndex record) {
		Assert.isNotBlank(record.getId(), Resources.getMessage("ID_IS_NULL"));
		record.setUpdateBy(WebUtil.getCurrentUser());
		provider.updateDicIndex(record);
	}

	public void deleteDicIndex(String id) {
		Assert.isNotBlank(id, Resources.getMessage("ID_IS_NULL"));
		provider.deleteDicIndex(id);
	}

	public void addDic(SysDic record) {
		record.setCreateBy(WebUtil.getCurrentUser());
		provider.updateDic(record);
	}

	public void updateDic(SysDic record) {
		Assert.isNotBlank(record.getId(), Resources.getMessage("ID_IS_NULL"));
		record.setUpdateBy(WebUtil.getCurrentUser());
		provider.updateDic(record);
	}

	public void deleteDic(String id) {
		Assert.isNotBlank(id, Resources.getMessage("ID_IS_NULL"));
		provider.deleteDic(id);
	}

	public SysDicIndex queryDicIndexById(String id) {
		return provider.queryDicIndexById(id);
	}

	public SysDic queryDicById(String id) {
		return provider.queryDicById(id);
	}

	public Map<String, String> queryDicByDicIndexKey(String key) {
		Assert.notNull(key, Resources.getMessage("KEY_IS_NULL"));
		return provider.queryDicByDicIndexKey(key);
	}
	
	public boolean checkDicIndexName(String id,String dicIndexName){
		return provider.checkDicIndexName(id,dicIndexName);
	}
	
	public boolean checkDicIndexKey(String id,String dicIndexKey){
		return provider.checkDicIndexKey(id,dicIndexKey);
	}
}
