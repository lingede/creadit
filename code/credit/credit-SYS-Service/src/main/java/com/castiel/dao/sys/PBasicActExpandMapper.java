package com.castiel.dao.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.castiel.core.base.BaseExpandMapper;
import com.castiel.model.generator.PBasicAct;
import com.castiel.model.sys.PBasicActBean;

public interface PBasicActExpandMapper extends BaseExpandMapper {
	String queryActNoByActName(@Param(value = "actName") String actName);

	List<PBasicActBean> searchAct(Map<String, Object> params);

	List<PBasicAct> queryActs(int offset, int limit);

	int queryTotal();

	Integer isEnrolTime(@Param(value = "actid") String actid);

	void deletePBasicActById(@Param(value = "actId") String actId);

	Date actStorageTime();

	PBasicAct getActByActId(@Param(value = "actId") String actId);

	String getFullCount(@Param(value = "type") int type, @Param(value = "actid") String actid);

	List<PBasicAct> queryEnrollActs(int offset, int limit);
}
