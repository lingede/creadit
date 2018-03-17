package com.castiel.web.sys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.castiel.core.base.BaseController;
import com.castiel.core.util.Request2ModelUtil;
import com.castiel.model.generator.PScoreApplySet;
import com.castiel.model.generator.PScoreSet;
import com.castiel.model.generator.PScoreSetApllyUnion;
import com.castiel.service.sys.PScoreApplyWebService;
import com.castiel.service.sys.PScoreSetWebService;

import io.swagger.annotations.Api;

@Controller
@Api(value = "学分设置管理")
@RequestMapping("Score")
public class PScoreSetController extends BaseController {

	@Autowired
	private PScoreSetWebService scoreService;
	@Autowired
	private PScoreApplyWebService applyService;


	@RequestMapping(value = "/set", method = RequestMethod.POST)
	@RequiresPermissions("platform.score.add")
	public Object setScore(HttpServletRequest request, ModelMap modelMap) {
		try {
			PScoreSetApllyUnion Union = Request2ModelUtil.covert(PScoreSetApllyUnion.class, request);
			PScoreApplySet Aplly = new PScoreApplySet();
			PScoreSet set = new PScoreSet();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Aplly.setScoreApplyStartTime(sdf.parse(Union.getScoreApplyStartTime()));
			Aplly.setScoreApplyEndTime(sdf.parse(Union.getScoreApplyEndTime()));
			set.setScoreAffirmStartTime(sdf.parse(Union.getScoreAffirmStartTime()));
			set.setScoreAffirmEndTime(sdf.parse(Union.getScoreAffirmEndTime()));
			set.setScoreApplySetValue1(Integer.parseInt(Union.getScoreApplySetValue1()));
			set.setScoreApplySetValue3(Integer.parseInt(Union.getScoreApplySetValue3()));
			set.setScoreApplySetValue2(Integer.parseInt(Union.getScoreApplySetValue2()));
			set.setScoreApplySetValue4(Integer.parseInt(Union.getScoreApplySetValue4()));
			set.setScoreCycleStartTime(Union.getScoreCycleStartTime());
			set.setScoreCycleEndTime(Union.getScoreCycleEndTime());
			String apllyId = applyService.CreateId();
			Aplly.setScoreApplyId(apllyId);
			set.setScoreApplyId(apllyId);
			applyService.add(Aplly);
			scoreService.add(set);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return setSuccessModelMap(modelMap);
	}

	@RequestMapping(value = "/read")
	@RequiresPermissions("platform.score.read")
	@ResponseBody
	public Object queryNewestData(ModelMap modelMap) {
		PScoreSetApllyUnion data = scoreService.queryNewestData();
		Date now = new Date();
		data.setCreateBy("12");
		data.setCreateTime(now);
		data.setEnable(true);
		data.setId("12");
		data.setRemark("12");
		return setSuccessModelMap(modelMap, data);
	}
}
