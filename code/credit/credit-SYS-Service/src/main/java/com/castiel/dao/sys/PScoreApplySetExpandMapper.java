package com.castiel.dao.sys;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.castiel.core.base.BaseExpandMapper;

public interface PScoreApplySetExpandMapper extends BaseExpandMapper {

	// 查询当前时间是否在设定的申请时间内
	String queryByScoreApplayTime(@Param("currentTime") Date currentTime);

}
