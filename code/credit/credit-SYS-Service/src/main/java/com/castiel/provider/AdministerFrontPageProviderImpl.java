package com.castiel.provider;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.dao.sys.AdministerFrontPageExpandMapper;
import com.castiel.model.generator.AdminFrontPage2;
import com.castiel.model.generator.AdministerFrontPage;
import com.castiel.model.generator.PActRound;
import com.github.pagehelper.PageInfo;

@CacheConfig(cacheNames = "AdministerFrontPage")
@DubboService(interfaceClass = AdministerFrontPageProvider.class)
public class AdministerFrontPageProviderImpl extends BaseProviderImpl<PActRound> implements AdministerFrontPageProvider {

	@Autowired
	private AdministerFrontPageExpandMapper AdministerFrontPageDao;
	@Autowired
	private AdminPageProvider pageDao;

	public PageInfo<AdministerFrontPage> queryStuInfo(Map<String, Object> params) {
		try {
			this.startPage(params);
			String dept = "";
			if (params.containsKey("dept")) {
				dept = (String) params.get("dept");
			}
			List<AdministerFrontPage> list1 = AdministerFrontPageDao.selectStuInfo(dept);
			return new PageInfo<AdministerFrontPage>(list1);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public PageInfo<AdminFrontPage2> queryDeptInfo(Map<String, Object> params) {
		try {
			int pageNum = 1;
			String dept = "";
			if (params.containsKey("pageNum2")) {
				String pageStr = (String) params.get("pageNum2");
				pageNum = Integer.parseInt(pageStr);
			}
			if (params.containsKey("dept2")) {
				dept = (String) params.get("dept2");
			}
			PageInfo<AdminFrontPage2> info = pageDao.paging(pageNum, dept);
			for (AdminFrontPage2 adminFrontPage2 : info.getList()) {
				adminFrontPage2.caculateAbsentPeople();
			}
			return info;
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public List<AdministerFrontPage> getAllStuInfo(String dept) {
		return AdministerFrontPageDao.selectStuInfo(dept);
	}

	@Override
	public List<AdminFrontPage2> getAllDeptInfo(String dept) {
		List<AdminFrontPage2> list = AdministerFrontPageDao.selectAllDeptInfo(dept);
		for (AdminFrontPage2 adminFrontPage2 : list) {
			adminFrontPage2.caculateAbsentPeople();
		}
		return list;
	}


}
