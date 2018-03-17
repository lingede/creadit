package com.castiel.service.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.model.generator.AdminFrontPage2;
import com.castiel.model.generator.AdministerFrontPage;
import com.castiel.model.generator.PActRound;
import com.castiel.provider.AdministerFrontPageProvider;
import com.github.pagehelper.PageInfo;

@Service
public class AdministerFrontPageWebService extends BaseService<AdministerFrontPageProvider, PActRound> {

	@DubboReference
	public void setProvider(AdministerFrontPageProvider provider) {
		this.provider = provider;
	}


	public PageInfo<AdministerFrontPage> queryStuInfo(Map<String, Object> params) {
		return provider.queryStuInfo(params);
	}

	public PageInfo<AdminFrontPage2> queryDetInfo(Map<String, Object> params) {
		return provider.queryDeptInfo(params);
	}

	public List<AdministerFrontPage> getAllStuInfo(String dept) {
		return provider.getAllStuInfo(dept);
	}

	public List<AdminFrontPage2> getAllDeptInfo(String dept) {
		return provider.getAllDeptInfo(dept);
	}

}
