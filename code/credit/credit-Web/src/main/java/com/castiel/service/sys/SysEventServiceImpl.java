package com.castiel.service.sys;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.SysEventService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.core.util.DateUtil;
import com.castiel.core.util.ExceptionUtil;
import com.castiel.core.util.InstanceUtil;
import com.castiel.core.util.WebUtil;
import com.castiel.model.generator.SysEvent;
import com.castiel.provider.SysEventProvider;

import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;

@Service
public class SysEventServiceImpl extends BaseService<SysEventProvider, SysEvent>
		implements SysEventService {
	@DubboReference
	public void setProvider(SysEventProvider provider) {
		this.provider = provider;
	}
	private ExecutorService executorService = Executors.newCachedThreadPool();

	public void saveEvent(final HttpServletRequest request, final HttpServletResponse response,
			final Exception ex, final Long startTime, final Long endTime) {
		final SysEvent record = new SysEvent();
        String uid = WebUtil.getCurrentUser();
		record.setMethod(request.getMethod());
		record.setRequestUri(request.getServletPath());
		record.setClientHost(WebUtil.getHost(request));
		record.setUserAgent(request.getHeader("user-agent"));
		//由于文件或图片数据过长，不存入数据库start
		if(request.getParameterMap().get("fileData")!=null){
			Map<String,String[]> parameterMap = InstanceUtil.newHashMap();
			for(String key:request.getParameterMap().keySet()){
				if(!key.trim().equals("fileData")){
					parameterMap.put(key, request.getParameterMap().get(key));
				}else{
					String[] fileDateValue = new String[1];
					fileDateValue[0] = "文件或图像数据";
					parameterMap.put(key,fileDateValue);
				}
			}
			record.setParameters(JSON.toJSONString(parameterMap));
		//由于文件或图片数据过长，不存入数据库end
		}else{
			record.setParameters(JSON.toJSONString(request.getParameterMap()));
		}
		record.setStatus(response.getStatus());
        record.setCreateBy(uid);
        record.setUpdateBy(uid);
		final String msg = (String) request.getAttribute("msg");

		executorService.submit(new Runnable() {
			public void run() {
				try { // 保存操作
					if (StringUtils.isNotBlank(msg)) {
						record.setRemark(msg);
					} else {
						record.setRemark(ExceptionUtil.getStackTraceAsString(ex));
					}
					Map<String, Object> params = InstanceUtil.newHashMap();
					params.put("permission_url", record.getRequestUri());
					
					add(record);
					// 内存信息
					if (logger.isDebugEnabled()) {
						String message = "开始时间: {}; 结束时间: {}; 耗时: {}s; URI: {}; ";
						// 最大内存: {}M; 已分配内存: {}M; 已分配内存中的剩余空间: {}M; 最大可用内存: {}M.
						// long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
						// long max = Runtime.getRuntime().maxMemory() / 1024 / 1024;
						// long free = Runtime.getRuntime().freeMemory() / 1024 / 1024;
						// , max, total, free, max - total + free
						logger.debug(message, DateUtil.format(startTime, "HH:mm:ss.SSS"),
								DateUtil.format(endTime, "HH:mm:ss.SSS"),
								(endTime - startTime) / 1000.00, record.getRequestUri());
					}
				} catch (Exception e) {
					logger.error("Save event log cause error :", e);
				}
			}
		});
	}
}