package com.castiel.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.castiel.core.base.BaseProvider;
import com.castiel.model.generator.AdminFrontPage2;
import com.castiel.model.generator.AdminPageInfo;
import com.castiel.model.generator.AdministerFrontPage;
import com.castiel.model.generator.PActRound;

public interface AdministerFrontPageExpandMapper extends BaseProvider<PActRound> {
	public List<AdministerFrontPage> selectStuInfo(@Param(value = "dept") String dept);

	public int getDeptInfoTotal(@Param(value = "dept") String dept);

	public List<AdminFrontPage2> selectDeptInfo(AdminPageInfo info);

	public List<AdminFrontPage2> selectAllDeptInfo(@Param(value = "dept") String dept);

}
