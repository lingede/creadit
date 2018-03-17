package com.castiel.service.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.core.util.DateUtil;
import com.castiel.model.generator.PBasicAct;
import com.castiel.model.sys.BasicActBean;
import com.castiel.model.sys.PActEnrolBean;
import com.castiel.model.sys.PBasicActBean;
import com.castiel.model.sys.PBasicActUnion;
import com.castiel.provider.PBasicActProvider;
import com.github.pagehelper.PageInfo;

@Service
public class PBasicActService extends BaseService<PBasicActProvider, PBasicAct> {

	@DubboReference
	public void setProvider(PBasicActProvider provider) {
		this.provider = provider;
	}

	public List<PBasicAct> getActs(int offset, int limit) {
		try {
			List<PBasicAct> acts = provider.queryActs(offset, limit);
			return acts;
		} catch (Exception ex) {
			return null;
		}
	}

	public int getTotal() {
		return provider.queryTotal();
	}

	public boolean isEnrolTime(String actid) {
		return provider.isEnrolTime(actid) == 1 ? true : false;
	}

	public PageInfo<BasicActBean> queryBean(Map<String, Object> params) {
		return provider.queryBean(params);
	}

	public Map<String, Object> detail(String id) {
		return provider.datail(id);
	}

	public void deleteAct(String id) {
		provider.deleteById(id);
	}

	public PBasicAct transDate(PBasicActUnion record) {
		PBasicAct pBasicAct = new PBasicAct();
		if (record.getId() != null)
			pBasicAct.setId(record.getId());
		pBasicAct.setActEnrolStartTime(record.getActEnrolStartTime());
		pBasicAct.setActEnrolEndTime(record.getActEnrolEndTime());
		pBasicAct.setActStartTime(record.getActStartTime());
		pBasicAct.setActEndTime(record.getActEndTime());
		pBasicAct.setActName(record.getActName());
		pBasicAct.setDeptId(record.getDeptId());
		pBasicAct.setActNo(record.getActNo());
		pBasicAct.setActContent(record.getActContent());
		pBasicAct.setActPlace(record.getActPlace());
		pBasicAct.setActCancleTime(record.getActCancleTime());
		pBasicAct.setActPeopleNumber(record.getActPeopleNumber());
		pBasicAct.setActTeacherNumber(record.getActTeacherNumber());
		pBasicAct.setActStudentNumber(record.getActStudentNumber());
		return pBasicAct;
	}

	public PBasicActBean getDateString(PBasicAct pBasicAct) {
		PBasicActBean pBasicActBean = new PBasicActBean();
		pBasicActBean.setActContent(pBasicAct.getActContent());
		pBasicActBean.setActEndTime(DateUtil.format(pBasicAct.getActEndTime()));
		pBasicActBean.setActEnrolEndTime(DateUtil.format(pBasicAct.getActEnrolEndTime()));
		pBasicActBean.setActEnrolStartTime(DateUtil.format(pBasicAct.getActEnrolStartTime()));
		pBasicActBean.setActid(pBasicAct.getId());
		pBasicActBean.setActName(pBasicAct.getActName());
		pBasicActBean.setActNo(pBasicAct.getActNo());
		pBasicActBean.setActPlace(pBasicAct.getActPlace());
		pBasicActBean.setActStartTime(DateUtil.format(pBasicAct.getActStartTime()));
		pBasicActBean.setDeptId(pBasicAct.getDeptId());
		return pBasicActBean;
	}

	public PageInfo<PActEnrolBean> searchActEnrol(Map<String, Object> params) {
		return provider.searchActEnrol(params);
	}

	public PBasicAct getActByActId(String actId) {
		// TODO Auto-generated method stub
		return provider.getActByActId(actId);
	}

	public int getFullCount(int type, String actid) {
		// TODO Auto-generated method stub
		return provider.getFullCount(type, actid);
	}

	public List<PBasicAct> getEnrollActs(int offset, int limit) {
		try {
			List<PBasicAct> acts = provider.queryEnrollActs(offset, limit);
			return acts;
		} catch (Exception ex) {
			return null;
		}
	}
}
