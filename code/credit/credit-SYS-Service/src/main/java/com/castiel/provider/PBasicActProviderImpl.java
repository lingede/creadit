package com.castiel.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.dao.sys.PBasicActExpandMapper;
import com.castiel.dao.sys.PBasicDeptExpandMapper;
import com.castiel.model.generator.PBasicAct;
import com.castiel.model.sys.BasicActBean;
import com.castiel.model.sys.PActEnrolBean;
import com.castiel.model.sys.PBasicActBean;
import com.castiel.model.sys.PBasicDeptBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@CacheConfig(cacheNames = "pBasicAct")
@DubboService(interfaceClass = PBasicActProvider.class)
public class PBasicActProviderImpl extends BaseProviderImpl<PBasicAct> implements PBasicActProvider {
	@Autowired
	private PBasicActExpandMapper pBasicActExpandMapper;
	@Autowired
	private PBasicDeptExpandMapper pBasicDeptExpandMapper;
	@Autowired
	private PActEnrolProvider pActEnrolPovider;

	@Override
	public String queryActNoByActName(String actName) {
		return pBasicActExpandMapper.queryActNoByActName(actName);
	}

	@Override
	public List<PBasicAct> queryActs(int offset, int limit) {
		return pBasicActExpandMapper.queryActs(offset, limit);
	}

	@Override
	public List<PBasicActBean> searchAct(Map<String, Object> params) {
		return pBasicActExpandMapper.searchAct(params);
	}

	@Override
	public int queryTotal() {
		return pBasicActExpandMapper.queryTotal();
	}

	@Override
	public Integer isEnrolTime(String actid) {
		return pBasicActExpandMapper.isEnrolTime(actid);
	}


	@Override
	public PageInfo<BasicActBean> queryBean(Map<String, Object> params) {
		this.startPage(params);
		Page<String> ids = pBasicActExpandMapper.query(params);
		return getPage(ids, BasicActBean.class);
	}

	@Override
	public void deleteById(String id) {
		pBasicActExpandMapper.deletePBasicActById(id);
	}

	@Override
	public Map<String, Object> datail(String id) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<PBasicDeptBean> pBasicDeptBeans = pBasicDeptExpandMapper.qCombobox();
		if (!id.trim().equals("0")) {
			PBasicAct record = queryById(id);
			returnMap.put("record", record);
		} else {
			PBasicAct record = new PBasicAct();
			returnMap.put("record", record);
		}
		returnMap.put("pBasicDeptBeans", pBasicDeptBeans);
		return returnMap;
	}

	@Override
	public PageInfo<PActEnrolBean> searchActEnrol(Map<String, Object> params) {
		this.startPage(params);

		PageInfo<PActEnrolBean> pageInfo = pActEnrolPovider.search(params);

		return pageInfo;
	}

	@Override
	public PBasicAct getActByActId(String actId) {
		// TODO Auto-generated method stub
		return pBasicActExpandMapper.getActByActId(actId);
	}

	@Override
	public int getFullCount(int type, String actid) {
		// TODO Auto-generated method stub
		String count = pBasicActExpandMapper.getFullCount(type, actid);
		return (count == null) ? 0 : Integer.parseInt(count);
	}

	@Override
	public List<PBasicAct> queryEnrollActs(int offset, int limit) {
		// TODO Auto-generated method stub
		return pBasicActExpandMapper.queryEnrollActs(offset, limit);
	}
}
