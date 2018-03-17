package com.castiel.provider;

import com.castiel.model.generator.AdminFrontPage2;
import com.github.pagehelper.PageInfo;

public interface AdminPageProvider {

	public PageInfo<AdminFrontPage2> paging(int pageNum, String dept);

}
