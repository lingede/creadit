package com.castiel.core.util;

import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * Created by YangBin on 2016/6/29.
 * pageInfo复制
 */
public final class PageInfoCopyUtil {

	public static final <T> PageInfo Copy(PageInfo pageInfo,List<T> list){
		PageInfo return_pageInfo = new PageInfo();
		return_pageInfo.setEndRow(pageInfo.getEndRow());
		return_pageInfo.setFirstPage(pageInfo.getFirstPage());
		return_pageInfo.setHasNextPage(pageInfo.isHasNextPage());
		return_pageInfo.setHasPreviousPage(pageInfo.isHasPreviousPage());
		return_pageInfo.setIsFirstPage(pageInfo.isIsFirstPage());
		return_pageInfo.setIsLastPage(pageInfo.isIsLastPage());
		return_pageInfo.setLastPage(pageInfo.getLastPage());
		return_pageInfo.setNavigatepageNums(pageInfo.getNavigatepageNums());
		return_pageInfo.setNavigatePages(pageInfo.getNavigatePages());
		return_pageInfo.setNextPage(pageInfo.getNextPage());
		return_pageInfo.setOrderBy(pageInfo.getOrderBy());
		return_pageInfo.setPageNum(pageInfo.getPageNum());
		return_pageInfo.setPages(pageInfo.getPages());
		return_pageInfo.setPageSize(pageInfo.getPageSize());
		return_pageInfo.setPrePage(pageInfo.getPrePage());
		return_pageInfo.setSize(pageInfo.getSize());
		return_pageInfo.setStartRow(pageInfo.getStartRow());
		return_pageInfo.setTotal(pageInfo.getTotal());
		return_pageInfo.setList(list);
		return return_pageInfo;
	}
}