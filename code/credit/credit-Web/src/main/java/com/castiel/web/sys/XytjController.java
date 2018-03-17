package com.castiel.web.sys;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.castiel.core.base.BaseController;
import com.castiel.core.util.WebUtil;
import com.castiel.model.sys.PXytjActStatisticsBean;
import com.castiel.model.sys.PXytjCreditStatisticsBean;
import com.castiel.service.sys.PXytjService;
import com.github.pagehelper.PageInfo;

// 学院用户前台展示

@RestController
@RequestMapping(value = "/xytj", method = RequestMethod.POST)
public class XytjController extends BaseController {
    @Autowired
    private PXytjService pXytjService;

    @RequestMapping(value = "/cardFlow/list")
    public Object SearchCardFlow(HttpServletRequest request, ModelMap modelMap) throws ParseException {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        params.put("orderBy", "ACT_NAME desc,STU_ID asc");
        PageInfo<?> list = pXytjService.queryCardFlow(params);
        return setSuccessModelMap(modelMap, list);
    }

    @RequestMapping(value = "/cardFlow/report")
    public void cardFlowReport(HttpServletResponse response, @RequestParam(value = "deptId", required = false) String deptId, @RequestParam(value = "actStartDate", required = false) String actStartDate, @RequestParam(value = "actEndDate", required = false) String actEndDate) throws IOException, ParseException {
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-disposition", "attachment; filename=cardFlow.xls");
        XSSFWorkbook workbook = pXytjService.queryCardFlowAllToExcel(deptId, actStartDate, actEndDate);


        ServletOutputStream outputStream = response.getOutputStream();
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/credit/list")
    public Object SearchCredit(HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        params.put("orderBy", "STU_ID asc");
        PageInfo<?> list = pXytjService.queryCredit(params);
        return setSuccessModelMap(modelMap, list);
    }

    @RequestMapping(value = "/credit/report/1")
    public void creditReport1(HttpServletResponse response, @RequestParam(value = "deptId", required = false) String deptId, @RequestParam(value = "reviewDateStart", required = false) String reviewDateStart, @RequestParam(value = "reviewDateEnd", required = false) String reviewDateEnd) throws IOException, ParseException {
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-disposition", "attachment; filename=credit1.xls");
        XSSFWorkbook workbook = pXytjService.query1creditToExcel(deptId, reviewDateStart, reviewDateEnd);


        ServletOutputStream outputStream = response.getOutputStream();
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/credit/report/2")
    public void creditReport2(HttpServletResponse response, @RequestParam(value = "deptId", required = false) String deptId, @RequestParam(value = "reviewDateStart", required = false) String reviewDateStart, @RequestParam(value = "reviewDateEnd", required = false) String reviewDateEnd) throws IOException, ParseException {
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-disposition", "attachment; filename=credit2.xls");
        XSSFWorkbook workbook = pXytjService.query2creditToExcel(deptId, reviewDateStart, reviewDateEnd);


        ServletOutputStream outputStream = response.getOutputStream();
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RequestMapping(value = "/cardFlow/Statistics")
    public Object SearchCardFlowStatistics(HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        PageInfo<PXytjActStatisticsBean> list = pXytjService.actStatistics(params);
        return setSuccessModelMap(modelMap, list);
    }

    @RequestMapping(value = "/credit/Statistics")
    public Object SearchCreditStatistics(ModelMap modelMap, @RequestParam(value = "deptId", required = false) String deptId, @RequestParam(value = "scoreCycleStartDate", required = false) String scoreCycleStartDate, @RequestParam(value = "scoreCycleEndDate", required = false) String scoreCycleEndDate) {
        PXytjCreditStatisticsBean bean = pXytjService.creditStatistics(deptId, scoreCycleStartDate, scoreCycleEndDate);
        return setSuccessModelMap(modelMap, bean);
    }

    @RequestMapping(value = "/act/list")
    public Object SearchAct(HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        PageInfo<?> list = pXytjService.searchAct(params);
        return setSuccessModelMap(modelMap, list);
    }

    @RequestMapping(value = "/actEnrol/list")
    public Object SearchActEnrol(HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        params.put("orderBy", "STU_ID asc");
        PageInfo<?> list = pXytjService.searchActEnrol(params);
        return setSuccessModelMap(modelMap, list);
    }

    @RequestMapping(value = "/enrollist/report")
    public void enrollistReport(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-disposition", "attachment; filename=enrol.xls");
        XSSFWorkbook workbook = pXytjService.querynrollistToExcel(params);


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
