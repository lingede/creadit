package com.castiel.web.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.castiel.core.base.BaseController;
import com.castiel.core.util.Request2ModelUtil;
import com.castiel.core.util.WebUtil;
import com.castiel.model.sys.PBasicActUnion;
import com.castiel.service.sys.PBasicActService;
import com.castiel.service.sys.PBasicDeptService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "活动管理", description = "活动管理")
@RequestMapping(value = "/pact", method = RequestMethod.POST)
public class PBasicActController extends BaseController {
    @Autowired
    private PBasicActService pBasicActService;
    @Autowired
    private PBasicDeptService pBasicDeptService;

    @ApiOperation(value = "查询活动")
    @RequiresPermissions("platform.act.read")
    @RequestMapping(value = "/read/list")
    public Object get(HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        params.put("orderBy", "ACT_START_TIME desc");
        PageInfo<?> list = pBasicActService.queryBean(params);
        // list = pBasicDeptService.getDeptName(list);
        return setSuccessModelMap(modelMap, list);
    }

    @ApiOperation(value = "添加活动")
    @RequiresPermissions("platform.act.add")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(HttpServletRequest request, ModelMap modelMap) {
        PBasicActUnion record = Request2ModelUtil.covert(PBasicActUnion.class, request);
        pBasicActService.add(pBasicActService.transDate(record));
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "修改活动")
    @RequiresPermissions("platform.act.update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(HttpServletRequest request, ModelMap modelMap) {
        PBasicActUnion record = Request2ModelUtil.covert(PBasicActUnion.class, request);
        pBasicActService.update(pBasicActService.transDate(record));
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "删除活动")
    @RequiresPermissions("platform.act.delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(HttpServletRequest request, ModelMap modelMap, String id) {
        pBasicActService.deleteAct(id);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "活动详情")
    @RequiresPermissions("platform.act.read")
    @RequestMapping(value = "/read/detail")
    public Object detail(ModelMap modelMap, @RequestParam(value = "id", required = false) String id) {
        Map<String, Object> returnMap = pBasicActService.detail(id);
        return setSuccessModelMap(modelMap, returnMap);
    }

    @RequestMapping(value = "/actEnrol/list")
    public Object SearchActEnrol(HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        PageInfo<?> list = pBasicActService.searchActEnrol(params);
        return setSuccessModelMap(modelMap, list);
    }

}
