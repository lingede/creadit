package com.castiel.service.sys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.model.generator.PCardFlow;
import com.castiel.model.sys.PActEnrolBean;
import com.castiel.model.sys.PActRoundBean;
import com.castiel.model.sys.PBasicActBean;
import com.castiel.model.sys.PXytjActStatisticsBean;
import com.castiel.model.sys.PXytjCardFlowBean;
import com.castiel.model.sys.PXytjCreditStatisticsBean;
import com.castiel.provider.PXytjProvider;
import com.github.pagehelper.PageInfo;

@Service
public class PXytjService extends BaseService<PXytjProvider, PCardFlow> {

    @DubboReference
    public void setProvider(PXytjProvider provider) {
        this.provider = provider;
    }

    public PageInfo<PXytjCardFlowBean> queryCardFlow(Map<String, Object> params) {
        return provider.queryCardFlow(params);
    }

    public PageInfo<PActRoundBean> queryCredit(Map<String, Object> params) {
        return provider.queryCredit(params);
    }

    public PageInfo<PBasicActBean> searchAct(Map<String, Object> params) {
        return provider.searchAct(params);
    }

    public PageInfo<PActEnrolBean> searchActEnrol(Map<String, Object> params) {
        return provider.searchActEnrol(params);
    }

    public XSSFWorkbook queryCardFlowAllToExcel(String deptId, String actStartDate, String actEndDate) throws ParseException {
        List<PXytjCardFlowBean> list = provider.queryCardFlowAll(deptId, actStartDate, actEndDate);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow header = sheet.createRow(0);
        sheet.setColumnWidth(9, 255 * 50);
        header.createCell(0).setCellValue("序号");
        header.createCell(1).setCellValue("学号");
        header.createCell(2).setCellValue("姓名");
        header.createCell(3).setCellValue("学院");
        header.createCell(4).setCellValue("专业");
        header.createCell(5).setCellValue("参加活动名称");
        header.createCell(6).setCellValue("活动编号");
        header.createCell(7).setCellValue("签到时间");
        header.createCell(8).setCellValue("活动是否有效");
        header.createCell(9).setCellValue("无效原因");

        CreationHelper helper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(helper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));

        int i = 1; // i行
        for (PXytjCardFlowBean bean : list) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String strSignTime = sdf.format(bean.getSignTime());
            XSSFRow newRow = sheet.createRow(i);
            newRow.createCell(0).setCellValue(i);
            newRow.createCell(1).setCellValue(bean.getStuId());
            newRow.createCell(2).setCellValue(bean.getStuName());
            newRow.createCell(3).setCellValue(bean.getDeptName());
            newRow.createCell(4).setCellValue(bean.getStuMajor());
            newRow.createCell(5).setCellValue(bean.getActName());
            newRow.createCell(6).setCellValue(bean.getActNo());
            newRow.createCell(7).setCellValue(strSignTime);
            newRow.createCell(8).setCellValue(bean.getIsvalid() == 1 ? "是" : "否");
            newRow.createCell(9).setCellValue(bean.getInvalidReason());
            i++;
        }
        return workbook;
    }

    public XSSFWorkbook querynrollistToExcel(Map<String, Object> params) throws ParseException {
        List<PActEnrolBean> list = provider.searchEnrolList(params);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow header = sheet.createRow(0);
        sheet.setColumnWidth(9, 255 * 50);
        header.createCell(0).setCellValue("序号");
        header.createCell(1).setCellValue("学号");
        header.createCell(2).setCellValue("姓名");
        header.createCell(3).setCellValue("学院");

        CreationHelper helper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(helper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));

        int i = 1; // i行
        for (PActEnrolBean bean : list) {
            XSSFRow newRow = sheet.createRow(i);
            newRow.createCell(0).setCellValue(i);
            newRow.createCell(1).setCellValue(bean.getStuId());
            newRow.createCell(2).setCellValue(bean.getStuName());
            newRow.createCell(3).setCellValue(bean.getDeptName());
            i++;
        }
        return workbook;
    }

    public XSSFWorkbook query1creditToExcel(String deptId, String reviewDateStart, String reviewDateEnd) throws ParseException {
        List<String> list = provider.query1ScoreStu(deptId, reviewDateStart, reviewDateEnd);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow header = sheet.createRow(0);
        sheet.setColumnWidth(9, 255 * 50);
        header.createCell(0).setCellValue("学号");
        CreationHelper helper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(helper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));
        int i = 1; // i行
        for (String stuId : list) {
            XSSFRow newRow = sheet.createRow(i);
            newRow.createCell(0).setCellValue(stuId);
            i++;
        }
        return workbook;

    }

    public XSSFWorkbook query2creditToExcel(String deptId, String reviewDateStart, String reviewDateEnd) throws ParseException {
        List<String> list = provider.query2ScoreStu(deptId, reviewDateStart, reviewDateEnd);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow header = sheet.createRow(0);
        sheet.setColumnWidth(9, 255 * 50);
        header.createCell(0).setCellValue("学号");
        CreationHelper helper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(helper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));
        int i = 1; // i行
        for (String stuId : list) {
            XSSFRow newRow = sheet.createRow(i);
            newRow.createCell(0).setCellValue(stuId);
            i++;
        }

        return workbook;

    }

    public PageInfo<PXytjActStatisticsBean> actStatistics(Map<String, Object> params) {
        return provider.queryActStatistics(params);
    }

    public PXytjCreditStatisticsBean creditStatistics(String deptId, String scoreCycleStartDate, String scoreCycleEndDate) {
        return provider.queryCreditStatistics(deptId, scoreCycleStartDate, scoreCycleEndDate);
    }
}
