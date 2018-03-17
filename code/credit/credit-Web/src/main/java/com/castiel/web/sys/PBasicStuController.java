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
import com.castiel.model.generator.PBasicStu;
import com.castiel.service.sys.PBasicStuService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "学生管理", description = "学生管理")
@RequestMapping(value = "/stu", method = RequestMethod.POST)
public class PBasicStuController extends BaseController {
    @Autowired
    private PBasicStuService pBasicStuService;

    @ApiOperation(value = "查询学生")
    @RequiresPermissions("platform.stu.read")
    @RequestMapping(value = "read/list")
    public Object get(HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        params.put("orderBy", "id_ asc");
        PageInfo<?> list = pBasicStuService.queryBean(params);
        return setSuccessModelMap(modelMap, list);
    }

    @ApiOperation(value = "编辑学生")
    @RequiresPermissions(value = {"platform.stu.add", "platform.stu.update"})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(HttpServletRequest request, ModelMap modelMap) {
        PBasicStu record = Request2ModelUtil.covert(PBasicStu.class, request);
        if (!pBasicStuService.IsExistThisStu(record.getId())) {
            pBasicStuService.insertStu(record);
        } else {
            pBasicStuService.update(record);
        }
        return setSuccessModelMap(modelMap);
    }

    // 详细信息
    @ApiOperation(value = "学生详情")
    @RequiresPermissions("platform.stu.read")
    @RequestMapping(value = "/read/detail")
    public Object detail(ModelMap modelMap, @RequestParam(value = "id", required = false) String id) {
        Map<String, Object> returnMap = pBasicStuService.detail(id);
        return setSuccessModelMap(modelMap, returnMap);
    }

    // 删除学生
    @ApiOperation(value = "删除学生")
    @RequiresPermissions("platform.stu.delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(HttpServletRequest request, ModelMap modelMap, String id) {
        pBasicStuService.deleteStu(id);
        return setSuccessModelMap(modelMap);
    }
}
