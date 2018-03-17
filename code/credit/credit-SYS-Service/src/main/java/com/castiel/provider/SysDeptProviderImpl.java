package com.castiel.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.castiel.core.base.BaseProviderImpl;
import com.castiel.core.support.dubbo.spring.annotation.DubboService;
import com.castiel.core.util.FatherToChildCopyUtil;
import com.castiel.dao.generator.SysDeptMapper;
import com.castiel.dao.sys.SysDeptExpandMapper;
import com.castiel.model.generator.SysDept;
import com.castiel.model.sys.SysDeptBean;
import com.castiel.provider.SysDeptProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@CacheConfig(cacheNames = "sysDept")
@DubboService(interfaceClass = SysDeptProvider.class)
public class SysDeptProviderImpl extends BaseProviderImpl<SysDept> implements SysDeptProvider {
	@Autowired
	private SysDeptExpandMapper sysDeptExpandMapper;
	@Autowired
	private SysDeptMapper sysDeptMapper;

	public PageInfo<SysDept> query(Map<String, Object> params) {
		this.startPage(params);
		return getPage(sysDeptExpandMapper.query(params));
	}

	@Override
	public PageInfo<SysDeptBean> queryBean(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.startPage(params);
        Page<String> ids = sysDeptExpandMapper.query(params);
        List<SysDept> sysDepts = getList(ids);
        List<SysDeptBean> sysDeptBeans = new ArrayList<SysDeptBean>();
        for(SysDept sysDept:sysDepts){
        	SysDeptBean sysDeptBean = new SysDeptBean();
        	FatherToChildCopyUtil.Copy(sysDept, sysDeptBean);
        	if(sysDeptBean.getParentId()!=null&&!sysDeptBean.getParentId().trim().equals("0")){
        		sysDeptBean.setParentDept(queryById(sysDeptBean.getParentId()).getDeptName());
        	}else{
        		sysDeptBean.setParentDept("无上级部门");
        	}
        	sysDeptBeans.add(sysDeptBean);
        }
        return new PageInfo<SysDeptBean>(sysDeptBeans);
	}

	@Override
	public Map<String, Object> detail(String id) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		/* 获取所有部门信息 */
		List<SysDept> sysDepts = sysDeptMapper.selectAll();
		List<String> ids = new ArrayList<String>();
		for(int i=0;i<sysDepts.size();i++){
			if (!sysDepts.get(i).getId().trim().equals(id.trim())){
				ids.add(sysDepts.get(i).getId());
			}
		}
		sysDepts = getList(ids);
		List<SysDeptBean> sysDeptBeans = new ArrayList<SysDeptBean>();
		SysDeptBean sysDeptBean = new SysDeptBean();
		sysDeptBean.setParentDept("无上级部门");
		sysDeptBean.setDeptName("--无上级部门--");
		sysDeptBean.setId("0");
		sysDeptBeans.add(sysDeptBean);
		for(SysDept sysDept:sysDepts){
			sysDeptBean = new SysDeptBean();
			FatherToChildCopyUtil.Copy(sysDept, sysDeptBean);
			if(sysDeptBean.getParentId()!=null && !sysDeptBean.getParentId().trim().equals(("0"))){
				sysDeptBean.setParentDept(queryById(sysDeptBean.getParentId()).getDeptName());
			}else{
				sysDeptBean.setParentDept("顶级部门");
			}
			sysDeptBeans.add(sysDeptBean);
		}

		if(!id.trim().equals("0")){
			SysDept record = queryById(id);
			returnMap.put("record", record);
		}else{
			SysDept record = new SysDept();
			record.setParentId("0");
			returnMap.put("record", record);
		}
		returnMap.put("fatherSysDepts",sysDeptBeans);
		return returnMap;
	}
	
	//重名验证
	@Override
	public boolean checkDeptName(String deptId,String deptName){
		//数据库是否存在
    	boolean isExist = false;
    	List<SysDept>  sysDepts = sysDeptExpandMapper.getSysDeptsByDeptName(deptName);
    	if(sysDepts.size()>0){
    		//id==0则为添加操作
    		SysDept sysDept = sysDepts.get(0);
    		if(deptId!=null&&!deptId.trim().equals("0")){
				if(!sysDept.getId().trim().equals(deptId)){
					isExist = true;
				}
    		}else{
    			isExist = true;
    		}
    	}else{
    		isExist = false;
    	}
    	return isExist;
	}

}
