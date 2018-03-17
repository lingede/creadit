package com.castiel.provider;

import java.util.List;
import java.util.Map;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.AdminFrontPage2;
import com.castiel.model.generator.AdministerFrontPage;
import com.castiel.model.generator.PActRound;
import com.github.pagehelper.PageInfo;

public interface AdministerFrontPageProvider extends BaseProvider<PActRound> {
	public PageInfo<AdministerFrontPage> queryStuInfo(Map<String, Object> params);

	public PageInfo<AdminFrontPage2> queryDeptInfo(Map<String, Object> params);

	public List<AdministerFrontPage> getAllStuInfo(String dept);

	public List<AdminFrontPage2> getAllDeptInfo(String dept);
}
