/**
 * 
 */
package com.castiel.provider;

import java.util.List;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.SysSession;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author ShenHuaJie
 * @version 2016年5月15日 上午11:21:21
 */
public interface SysSessionProvider extends BaseProvider<SysSession> {
	@Transactional
	public void deleteBySessionId(final String sessionId);

	public List<String> querySessionIdByAccount(String account);

	public void delete(String id);
}
