package com.castiel.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castiel.dao.sys.AdministerFrontPageExpandMapper;
import com.castiel.model.generator.AdminFrontPage2;
import com.castiel.model.generator.AdminPageInfo;
import com.github.pagehelper.PageInfo;

@Service
public class AdminPageProviderImpl implements AdminPageProvider {
	@Autowired
	private AdministerFrontPageExpandMapper dao;
	private AdminPageInfo page;

	public AdminPageProviderImpl() {
		page = new AdminPageInfo();
	}

	@Override
	public PageInfo<AdminFrontPage2> paging(int pageNum, String dept) {
		try {
			clearPage();
			page.setDept(dept);
			caculateTotal();
			caculatePages();
			if (pageNum > page.getPages()) {
				pageNum = 1;
			}
			page.setPageNum(pageNum);
			caculateStartRow();
			caculateSize();

			List<AdminFrontPage2> list = dao.selectDeptInfo(page);
			PageInfo<AdminFrontPage2> info2 = new PageInfo<>();
			info2.setList(list);
			info2.setPageNum(page.getPageNum());
			info2.setSize(page.getSize());
			info2.setTotal(page.getTotal());
			info2.setPages(page.getPages());
			info2.setPageSize(page.getPageSize());
			info2.setOrderBy(page.getOrderBy());
			return info2;
		} catch (Exception ex) {
			return null;
		}
	}

	private void clearPage() {
		page = new AdminPageInfo();
	}

	private void caculateTotal() {
		page.setTotal(dao.getDeptInfoTotal(page.getDept()));
	}


	private void caculatePages() {
		page.setPages((int) Math.ceil(((double) page.getTotal() / (double) page.getPageSize())));
	}

	private void caculateSize() {
		if (page.getPageNum() >= 1 && page.getPageNum() <= page.getPages()) {
			if (page.getPageNum() < page.getPages()) {
				page.setSize(page.getPageSize());
			} else {
				page.setSize(page.getTotal() - (page.getPageNum() - 1) * page.getPageSize());
			}
		}
	}

	private void caculateStartRow() {
		page.setStartRow((page.getPageNum() - 1) * page.getPageSize());
	}
}
