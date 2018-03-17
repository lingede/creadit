package com.castiel.provider;

import java.util.List;
import java.util.Map;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.PBasicAct;
import com.castiel.model.sys.BasicActBean;
import com.castiel.model.sys.PActEnrolBean;
import com.castiel.model.sys.PBasicActBean;
import com.github.pagehelper.PageInfo;

public interface PBasicActProvider extends BaseProvider<PBasicAct> {
	String queryActNoByActName(String actName);

	List<PBasicActBean> searchAct(Map<String, Object> params);

	List<PBasicAct> queryActs(int offset, int limit);

	public PageInfo<PActEnrolBean> searchActEnrol(Map<String, Object> params);

	int queryTotal();

	Integer isEnrolTime(String actid);

	PageInfo<BasicActBean> queryBean(Map<String, Object> params);

	void deleteById(String id);

	Map<String, Object> datail(String id);

	PBasicAct getActByActId(String actId);

	int getFullCount(int type, String actid);

	List<PBasicAct> queryEnrollActs(int offset, int limit);
}
