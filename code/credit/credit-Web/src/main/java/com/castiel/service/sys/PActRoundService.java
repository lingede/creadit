package com.castiel.service.sys;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.castiel.core.base.BaseService;
import com.castiel.core.support.dubbo.spring.annotation.DubboReference;
import com.castiel.core.util.RedisUtil;
import com.castiel.model.generator.PActRound;
import com.castiel.model.sys.PActRoundBean;
import com.castiel.provider.PActRoundProvider;
import com.github.pagehelper.PageInfo;

@Service
public class PActRoundService extends BaseService<PActRoundProvider, PActRound> {
    @DubboReference
    public void setProvider(PActRoundProvider provider) {
        this.provider = provider;
    }

    @Autowired
    private PScoreApplyWebService pScoreApplyService;

    // 分页查询(讲座)
    public PageInfo<PActRoundBean> queryBean(Map<String, Object> params) {
        params.put("orderBy", "STU_ID asc");
        return provider.queryBean(params);
    }

    // 分页查询(演出)
    public PageInfo<PActRoundBean> queryBean2(Map<String, Object> params) {
        params.put("orderBy", "STU_ID asc");
        return provider.queryBean2(params);
    }

    // 审核(讲座)
    public void review(int isPass, String id, String reviewReason, String deptId, String reviewName) {
        Date date = new Date();
        if (isPass == 1) {
            provider.review(id, isPass, date, null, deptId, reviewName);
        } else {
            provider.review(id, isPass, date, reviewReason, deptId, reviewName);
        }
        RedisUtil.batchDel("iStark:pActRound:");
    }

    // 审核(演出)
    public void review2(int isPass, String id, String reviewReason, String deptId, String reviewName) {
        Date date = new Date();
        if (isPass == 1) {
            provider.review2(id, isPass, date, null, deptId, reviewName);
        } else {
            provider.review2(id, isPass, date, reviewReason, deptId, reviewName);
        }
        RedisUtil.batchDel("iStark:pActRound:");
    }


    // 批量审核(讲座)
    public void batchReview(int isPass, String[] selected, String reviewReason, String deptId, String reviewName) {
        Date date = new Date();
        if (isPass == 1) {
            for (String select : selected) {
                provider.review(select, isPass, date, null, deptId, reviewName);
            }
        } else {
            for (String select : selected) {
                provider.review(select, isPass, date, reviewReason, deptId, reviewName);
            }
        }
        RedisUtil.batchDel("iStark:pActRound:");
    }

    // 批量审核(演出)
    public void batchReview2(int isPass, String[] selected, String reviewReason, String deptId, String reviewName) {
        Date date = new Date();
        if (isPass == 1) {
            for (String select : selected) {
                provider.review2(select, isPass, date, null, deptId, reviewName);
            }
        } else {
            for (String select : selected) {
                provider.review2(select, isPass, date, reviewReason, deptId, reviewName);
            }
        }
        RedisUtil.batchDel("iStark:pActRound:");
    }


    // 全部审核(讲座)
    public void reviewAll(int isPass, String reviewReason, String deptId, String reviewName) {
        Date date = new Date();
        if (isPass == 1) {
            provider.reviewAll(isPass, date, null, deptId, reviewName);
        } else {
            provider.reviewAll(isPass, date, reviewReason, deptId, reviewName);
        }
        RedisUtil.batchDel("iStark:pActRound:");
    }

    // 全部审核(演出)
    public void reviewAll2(int isPass, String reviewReason, String deptId, String reviewName) {
        Date date = new Date();
        if (isPass == 1) {
            provider.reviewAll2(isPass, date, null, deptId, reviewName);
        } else {
            provider.reviewAll2(isPass, date, reviewReason, deptId, reviewName);
        }
        RedisUtil.batchDel("iStark:pActRound:");
    }

    // 导出未审核学生的报表(讲座)
    public XSSFWorkbook queryNonReviewStudentToExcel(String deptId) {
        List<PActRoundBean> list = provider.queryNonReviewStudent(deptId);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow header = sheet.createRow(0);
        sheet.setColumnWidth(9, 255 * 50);

        header.createCell(0).setCellValue("序号");
        header.createCell(1).setCellValue("学号");
        header.createCell(2).setCellValue("姓名");
        header.createCell(3).setCellValue("学院");
        header.createCell(4).setCellValue("参加讲座次数");
        header.createCell(5).setCellValue("是否申请学分");
        header.createCell(6).setCellValue("申请时间");
        header.createCell(7).setCellValue("申请认定学分");
        header.createCell(8).setCellValue("审核");
        header.createCell(9).setCellValue("未通过原因");

        CreationHelper helper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(helper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));

        int i = 1; // i行
        for (PActRoundBean bean : list) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String strAffirmTime = "";
            if (bean.getAffirmTime() != null) {
                strAffirmTime = sdf.format(bean.getAffirmTime());
            }
            XSSFRow newRow = sheet.createRow(i);
            newRow.createCell(0).setCellValue(i);
            newRow.createCell(1).setCellValue(bean.getStuId());
            newRow.createCell(2).setCellValue(bean.getStuName());
            newRow.createCell(3).setCellValue(bean.getDeptId());
            newRow.createCell(4).setCellValue(bean.getTotal());
            newRow.createCell(5).setCellValue(bean.getIsaffirm() == 1 ? "是" : "否");
            newRow.createCell(6).setCellValue(strAffirmTime);
            if (bean.getAffirmScore() != null) {
                newRow.createCell(7).setCellValue(bean.getAffirmScore());
            }
            i++;
        }
        return workbook;
    }

    // 导出未审核学生的报表(演出)
    public XSSFWorkbook queryNonReviewStudentToExcel2(String deptId) {
        List<PActRoundBean> list = provider.queryNonReviewStudent2(deptId);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow header = sheet.createRow(0);
        sheet.setColumnWidth(9, 255 * 50);

        header.createCell(0).setCellValue("序号");
        header.createCell(1).setCellValue("学号");
        header.createCell(2).setCellValue("姓名");
        header.createCell(3).setCellValue("学院");
        header.createCell(4).setCellValue("参加演出次数");
        header.createCell(5).setCellValue("是否申请学分");
        header.createCell(6).setCellValue("申请时间");
        header.createCell(7).setCellValue("申请认定学分");
        header.createCell(8).setCellValue("审核");
        header.createCell(9).setCellValue("未通过原因");

        CreationHelper helper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(helper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));

        int i = 1; // i行
        for (PActRoundBean bean : list) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String strAffirmTime = "";
            if (bean.getAffirmTime2() != null) {
                strAffirmTime = sdf.format(bean.getAffirmTime2());
            }
            XSSFRow newRow = sheet.createRow(i);
            System.err.println(bean.toString() + "----------------------------------------" + bean.getTotal2());
            newRow.createCell(0).setCellValue(i);
            newRow.createCell(1).setCellValue(bean.getStuId());
            newRow.createCell(2).setCellValue(bean.getStuName());
            newRow.createCell(3).setCellValue(bean.getDeptId());
            newRow.createCell(4).setCellValue(bean.getTotal2());
            newRow.createCell(5).setCellValue(bean.getIsaffirm2() == 1 ? "是" : "否");
            newRow.createCell(6).setCellValue(strAffirmTime);
            if (bean.getAffirmScore() != null) {
                newRow.createCell(7).setCellValue(bean.getAffirmScore2());
            }
            i++;
        }
        return workbook;
    }

    // excel审核(讲座)
    public void reviewExcel(MultipartFile file, String deptId, String reviewName) throws IOException {
        Date date = new Date();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<PActRoundBean> list = new ArrayList<PActRoundBean>();
        for (int row = 1; row <= sheet.getLastRowNum(); row++) {
            XSSFRow xssfRow = sheet.getRow(row);
            PActRoundBean bean = new PActRoundBean();
            if (xssfRow.getCell(1) != null) {
                bean.setStuId(xssfRow.getCell(1).getStringCellValue());
            }
            if (xssfRow.getCell(8) != null) {
                if (xssfRow.getCell(8).getNumericCellValue() == 1 || xssfRow.getCell(8).getNumericCellValue() == 0) {
                    bean.setReviewStatus((int) xssfRow.getCell(8).getNumericCellValue());
                } else
                    continue;
            }

            if (xssfRow.getCell(9) != null) {
                bean.setReviewReason(xssfRow.getCell(9).getStringCellValue());
            }
            list.add(bean);
        }
        for (PActRoundBean bean : list) {
            if (bean.getReviewStatus() != null) {
                if (bean.getReviewStatus() == 1) {
                    System.out.println(bean.getStuId());
                    provider.reviewByStuId(bean.getStuId(), 1, date, null, deptId, reviewName);
                } else if (bean.getReviewStatus() == 0) {
                    System.out.println(bean.getStuId());
                    if (bean.getReviewReason() != null) {
                        provider.reviewByStuId(bean.getStuId(), 0, date, bean.getReviewReason(), deptId, reviewName);
                    } else {
                        provider.reviewByStuId(bean.getStuId(), 0, date, null, deptId, reviewName);
                    }
                }
            }
        }
        RedisUtil.batchDel("iStark:pActRound:");
    }

    // excel审核(演出)
    public void reviewExcel2(MultipartFile file, String deptId, String reviewName) throws IOException {
        Date date = new Date();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<PActRoundBean> list = new ArrayList<PActRoundBean>();
        for (int row = 1; row <= sheet.getLastRowNum(); row++) {
            XSSFRow xssfRow = sheet.getRow(row);
            PActRoundBean bean = new PActRoundBean();
            if (xssfRow.getCell(1) != null) {
                bean.setStuId(xssfRow.getCell(1).getStringCellValue());
            }
            if (xssfRow.getCell(8) != null) {
                if (xssfRow.getCell(8).getNumericCellValue() == 1 || xssfRow.getCell(8).getNumericCellValue() == 0) {
                    bean.setReviewStatus((int) xssfRow.getCell(8).getNumericCellValue());
                } else
                    continue;
            }

            if (xssfRow.getCell(9) != null) {
                bean.setReviewReason(xssfRow.getCell(9).getStringCellValue());
            }
            list.add(bean);
        }
        for (PActRoundBean bean : list) {
            if (bean.getReviewStatus() != null) {
                if (bean.getReviewStatus() == 1) {
                    System.out.println(bean.getStuId());
                    provider.reviewByStuId2(bean.getStuId(), 1, date, null, deptId, reviewName);
                } else if (bean.getReviewStatus() == 0) {
                    System.out.println(bean.getStuId());
                    if (bean.getReviewReason() != null) {
                        provider.reviewByStuId2(bean.getStuId(), 0, date, bean.getReviewReason(), deptId, reviewName);
                    } else {
                        provider.reviewByStuId2(bean.getStuId(), 0, date, null, deptId, reviewName);
                    }
                }
            }
        }
        RedisUtil.batchDel("iStark:pActRound:");
    }

    public PActRoundBean searchByStuId(String stuId) {
        return provider.searchByStuId(stuId);
    }

    // 申请学分(讲座)
    public void applyScore(int score, String stuId) {
        Date date = new Date();
        String id = pScoreApplyService.searchByCurrentTime();
        Integer isAffirm = searchByStuId(stuId).getIsaffirm();
        if (isAffirm == null) {
            isAffirm = 0;
        }
        if (id != null && isAffirm == 0) {
            if (score == 1) {
                provider.applyScore(score, date, stuId);
            } else if (score == 2) {
                provider.applyScore(score, date, stuId);
            }
        }
        RedisUtil.batchDel("iStark:pActRound:");
    }

    // 申请学分(演出)
    public void applyScore2(int score, String stuId) {
        Date date = new Date();
        String id = pScoreApplyService.searchByCurrentTime();
        Integer isAffirm2 = searchByStuId(stuId).getIsaffirm2();
        if (isAffirm2 == null) {
            isAffirm2 = 0;
        }
        if (id != null && isAffirm2 == 0) {
            if (score == 1) {
                provider.applyScore2(score, date, stuId);
            } else if (score == 2) {
                provider.applyScore2(score, date, stuId);
            }
        }
        RedisUtil.batchDel("iStark:pActRound:");
    }

    public Map<String, Object> searchByStuIdAndTime(String stuId, Date start, Date end) {
        return provider.searchByStuIdAndTime(stuId, start, end);
    }
}
