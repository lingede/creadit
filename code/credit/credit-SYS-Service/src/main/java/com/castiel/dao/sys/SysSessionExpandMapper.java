package com.castiel.dao.sys;

import java.util.List;

import com.castiel.core.base.BaseExpandMapper;

public interface SysSessionExpandMapper extends BaseExpandMapper {

	void deleteBySessionId(String sessionId);

	String queryBySessionId(String sessionId);

	List<String> querySessionIdByAccount(String account);

}
