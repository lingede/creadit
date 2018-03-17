package com.castiel.web.sys;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.castiel.core.base.BaseController;
import com.castiel.core.util.WebUtil;
import com.castiel.service.sys.PActRoundService;
import com.github.pagehelper.PageInfo;

/*
 * 学院用户后台管理控制器
 */
@RestController
@RequestMapping(value = "/actround2", method = RequestMethod.POST)
public class ActRoundController2 extends BaseController {
    @Autowired
    private PActRoundService pActRoundService;


    @RequestMapping(value = "/read/list")
    public Object get(HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        PageInfo<?> list = pActRoundService.queryBean2(params);
        return setSuccessModelMap(modelMap, list);
    }

    @RequiresPermissions("platform.actround2.update")
    @RequestMapping(value = "/review")
    public Object review(ModelMap modelMap, @RequestParam(value = "id", required = false) String id, @RequestParam(value = "isPass", required = false) int isPass, @RequestParam(value = "reviewReason", required = false) String reviewReason, @RequestParam(value = "deptId", required = false) String deptId, @RequestParam(value = "reviewName", required = false) String reviewName) {
        pActRoundService.review2(isPass, id, reviewReason, deptId, reviewName);
        return setSuccessModelMap(modelMap);
    }

    @RequiresPermissions("platform.actround2.update")
    @RequestMapping(value = "/batchReview")
    public Object review(ModelMap modelMap, @RequestParam(value = "selected[]", required = false) String[] selected, @RequestParam(value = "isPass", required = false) int isPass, @RequestParam(value = "reviewReason", required = false) String reviewReason, @RequestParam(value = "deptId", required = false) String deptId, @RequestParam(value = "reviewName", required = false) String reviewName) {
        pActRoundService.batchReview2(isPass, selected, reviewReason, deptId, reviewName);
        return setSuccessModelMap(modelMap);
    }

    @RequiresPermissions("platform.actround2.update")
    @RequestMapping(value = "/reviewAll")
    public Object reviewAll(ModelMap modelMap, @RequestParam(value = "isPass", required = false) int isPass, @RequestParam(value = "reviewReason", required = false) String reviewReason, @RequestParam(value = "deptId", required = false) String deptId, @RequestParam(value = "reviewName", required = false) String reviewName) {
        pActRoundService.reviewAll2(isPass, reviewReason, deptId, reviewName);
        return setSuccessModelMap(modelMap);
    }

    @RequiresPermissions("platform.actround2.update")
    @RequestMapping(value = "/reviewExcel")
    public String reviewExcel(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "deptId") String deptId, @RequestParam(value = "reviewName") String reviewName, HttpServletResponse response) throws IOException {
        if (file == null) {
            return null;
        } else {
            pActRoundService.reviewExcel2(file, deptId, reviewName);
            return "Success";
        }

    }

    @RequestMapping(value = "/nonReviewReport")
    public void nonReviewReport(HttpServletResponse response, @RequestParam(value = "deptId", required = false) String deptId) throws IOException, ParseException {
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-disposition", "attachment; filename=nonReviewReport.xls");
        XSSFWorkbook workbook = pActRoundService.queryNonReviewStudentToExcel2(deptId);

        ServletOutputStream outputStream = response.getOutputStream();
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
