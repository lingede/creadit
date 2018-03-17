package com.castiel.service.sys;

import java.util.List;
import java.util.Map;

import com.castiel.core.support.Assert;
import com.castiel.model.generator.SysSession;
import com.castiel.provider.SysSessionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

/**
 * 会话管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:08:32
 */
@Service
public class SysSessionService {
	@Autowired
	private SysSessionProvider sysSessionProvider;
	@Autowired
	private RedisOperationsSessionRepository sessionRepository;

	public PageInfo<?> query(Map<String, Object> params) {
		return sysSessionProvider.query(params);
	}

	/** 删除会话 */
	public void deleteByAccount(String account) {
		Assert.isNotBlank(account, "ACCOUNT");
		List<String> sessionIds = sysSessionProvider.querySessionIdByAccount(account);
		if (sessionIds != null) {
			for (String sessionId : sessionIds) {
				sessionRepository.delete(sessionId);
				sessionRepository.cleanupExpiredSessions();
				sysSessionProvider.deleteBySessionId(sessionId);
			}
		}
	}

	/** 删除会话 */
	public void delete(String id) {
		Assert.isNotBlank(id, "ID");
		SysSession sysSession = sysSessionProvider.queryById(id);
		if (sysSession != null) {
			sessionRepository.delete(sysSession.getSessionId());
			sessionRepository.cleanupExpiredSessions();
			sysSessionProvider.delete(id);
		}
	}

	/** 更新会话 */
	public void update(SysSession record) {
		sysSessionProvider.update(record);
	}

	/** 删除会话 */
	public void deleteBySessionId(String sessionId) {
		sysSessionProvider.deleteBySessionId(sessionId);
	}

}
