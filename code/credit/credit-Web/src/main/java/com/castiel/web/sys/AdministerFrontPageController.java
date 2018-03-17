package com.castiel.web.sys;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.castiel.core.base.BaseController;
import com.castiel.core.util.WebUtil;
import com.castiel.model.generator.AdminFrontPage2;
import com.castiel.model.generator.AdministerFrontPage;
import com.castiel.model.generator.AdministerPageInfo;
import com.castiel.service.sys.AdministerFrontPageWebService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value = "管理员前台展示")
@RequestMapping(value = "ARound", method = RequestMethod.POST)
public class AdministerFrontPageController extends BaseController {
    @Autowired
    private AdministerFrontPageWebService administerFrontPageWebService;

    // 分页查询信息
    @RequiresPermissions("platform.actround.read")
    @ApiOperation(value = "查询前台展示页面学生信息")
    @RequestMapping("/list")
    public Object getAll(ModelMap map, HttpServletRequest request) {
        try {
            Map<String, Object> params = WebUtil.getParameterMap(request);
            params.put("orderBy", "STU_ID asc");
            PageInfo<AdministerFrontPage> list = administerFrontPageWebService.queryStuInfo(params);
            AdministerPageInfo info = new AdministerPageInfo();

            Map<String, Object> params2 = WebUtil.getParameterMap(request);
            params.put("orderBy", "ACT_START_TIME desc");
            PageInfo<AdminFrontPage2> list2 = administerFrontPageWebService.queryDetInfo(params2);
            info.setInfo1(list);
            info.setInfo2(list2);
            return setSuccessModelMap(map, info);
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    // 导出excel表格
    @RequiresPermissions("platform.actround.read")
    @ApiOperation(value = "存储学生信息excel")
    @RequestMapping("/excelStu")
    public void exportStudentExcel(ModelMap map, HttpServletRequest request, HttpServletResponse response) throws java.text.ParseException, IOException {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        String dept = "";
        if (params.containsKey("dept")) {
            dept = (String) params.get("dept");
        }
        List<AdministerFrontPage> list = administerFrontPageWebService.getAllStuInfo(dept);
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-disposition", "attachment; filename=student.xls");
        XSSFWorkbook workbook = CreateStuExcel("学生表", list);
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 导出另一个excel
    @RequiresPermissions("platform.actround.read")
    @ApiOperation(value = "存储学院信息excel")
    @RequestMapping("/excelDept")
    public void exportDept(ModelMap map, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> params = WebUtil.getParameterMap(request);
        String dept2 = "";
        if (params.containsKey("dept2")) {
            dept2 = (String) params.get("dept2");
        }
        List<AdminFrontPage2> list = administerFrontPageWebService.getAllDeptInfo(dept2);
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-disposition", "attachment; filename=dept.xls");
        XSSFWorkbook workbook = CreateDeptExcel("学院表", list);
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private XSSFWorkbook CreateStuExcel(String sheetName, List<AdministerFrontPage> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow header = sheet.createRow(0);
        sheet.setColumnWidth(9, 255 * 50);
        header.createCell(0).setCellValue("序号");
        header.createCell(1).setCellValue("学号");
        header.createCell(2).setCellValue("姓名");
        header.createCell(3).setCellValue("学院");
        header.createCell(4).setCellValue("专业");
        header.createCell(5).setCellValue("参加讲座次数");
        header.createCell(6).setCellValue("参加演出次数");
        header.createCell(7).setCellValue("讲座获得学分");
        header.createCell(8).setCellValue("演出获得学分");

        CreationHelper helper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(helper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));

        int i = 1; // i行
        for (AdministerFrontPage bean : list) {
            XSSFRow newRow = sheet.createRow(i);
            newRow.createCell(0).setCellValue(i);
            newRow.createCell(1).setCellValue(bean.getId_());
            newRow.createCell(2).setCellValue(bean.getSTU_NAME());
            newRow.createCell(3).setCellValue(bean.getDEPT_NAME());
            newRow.createCell(4).setCellValue(bean.getSTU_MAJOR());
            newRow.createCell(5).setCellValue(bean.getTOTAL());
            newRow.createCell(6).setCellValue(bean.getTOTAL2());
            newRow.createCell(7).setCellValue(bean.getSCORE_VALUE());
            newRow.createCell(7).setCellValue(bean.getSCORE_VALUE2());
            i++;
        }
        return workbook;
    }

    private XSSFWorkbook CreateDeptExcel(String sheetName, List<AdminFrontPage2> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow header = sheet.createRow(0);
        sheet.setColumnWidth(9, 255 * 50);
        header.createCell(0).setCellValue("序号");
        header.createCell(1).setCellValue("学院名称");
        header.createCell(2).setCellValue("学院编号");
        header.createCell(3).setCellValue("活动名称");
        header.createCell(4).setCellValue("活动开始时间");
        header.createCell(5).setCellValue("活动结束时间");
        header.createCell(6).setCellValue("活动编号");
        header.createCell(7).setCellValue("申报人数");
        header.createCell(8).setCellValue("实到人数");
        header.createCell(9).setCellValue("缺席人数");

        CreationHelper helper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(helper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));

        int i = 1; // i行
        for (AdminFrontPage2 bean : list) {
            XSSFRow newRow = sheet.createRow(i);
            newRow.createCell(0).setCellValue(i);
            newRow.createCell(1).setCellValue(bean.getDEPT_NAME());
            newRow.createCell(2).setCellValue(bean.getDEPT_NO());
            newRow.createCell(3).setCellValue(bean.getACT_NAME());
            newRow.createCell(4).setCellValue(bean.getACT_START_TIME());
            newRow.createCell(5).setCellValue(bean.getACT_END_TIME());
            newRow.createCell(6).setCellValue(bean.getACT_NO());
            newRow.createCell(7).setCellValue(bean.getAPPLY_NUMBER());
            newRow.createCell(8).setCellValue(bean.getREALITY_NUMBER());
            newRow.createCell(9).setCellValue(bean.getABSENT_NUMBER());
            i++;
        }
        return workbook;
    }


}
