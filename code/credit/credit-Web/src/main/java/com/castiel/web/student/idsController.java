package com.castiel.web.student;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.castiel.core.base.BaseController;
import com.castiel.core.config.Resources;
import com.castiel.core.exception.LoginException;
import com.castiel.core.util.SecurityUtil;
import com.castiel.model.generator.PActEnrol;
import com.castiel.model.generator.PActRound;
import com.castiel.model.generator.PBasicAct;
import com.castiel.model.generator.PBasicStu;
import com.castiel.model.generator.SysUser;
import com.castiel.model.sys.PActRoundBean;
import com.castiel.model.sys.PCardFlowBean;
import com.castiel.service.sys.PActEnrolService;
import com.castiel.service.sys.PActRoundService;
import com.castiel.service.sys.PBasicActService;
import com.castiel.service.sys.PBasicDeptService;
import com.castiel.service.sys.PBasicStuService;
import com.castiel.service.sys.PCardFlowService;
import com.castiel.service.sys.PScoreApplyWebService;

@Controller
@RequestMapping("/student")
public class idsController extends BaseController {

    @Autowired
    private PBasicStuService pbasicStuService;
    @Autowired
    private PActRoundService pActRoundService;
    @Autowired
    private PScoreApplyWebService pScoreApplyService;
    @Autowired
    private PBasicActService pbasicActService;
    @Autowired
    private PActEnrolService pactEnrolService;
    @Autowired
    private PCardFlowService pCardFlowService;
    @Autowired
    private PBasicDeptService pbasicDeptService;


    @RequestMapping(value = "/ids/login", method = RequestMethod.GET)
    public String login(ModelMap modelMap, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getRemoteUser();

        System.err.println("uid=" + uid + "进入系统");

        Principal principal = request.getUserPrincipal();
        AttributePrincipal aPrincipal = (AttributePrincipal) principal;
        Map<String, Object> map = aPrincipal.getAttributes();
        String cn = (String) map.get("cn");
        SysUser sysUser = new SysUser();
        sysUser.setAccount(uid);
        sysUser.setPassword(SecurityUtil.encryptMd5(SecurityUtil.encryptSHA(uid + "ffff")));
        sysUser.setUserName(cn);
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getAccount(), sysUser.getPassword());
        token.setRememberMe(true);
        try {
            currentUser.login(token);
            currentUser.isAuthenticated();
            return "redirect:/#/sys/blank/list";
        } catch (UnknownAccountException e) {
            throw new LoginException(Resources.getMessage("ACCOUNT_UNKNOWN", token.getPrincipal()));
        } catch (LockedAccountException e) {
            throw new LoginException(Resources.getMessage("ACCOUNT_LOCKED", token.getPrincipal()));
        } catch (DisabledAccountException e) {
            throw new LoginException(Resources.getMessage("ACCOUNT_DISABLED", token.getPrincipal()));
        } catch (ExpiredCredentialsException e) {
            throw new LoginException(Resources.getMessage("ACCOUNT_EXPIRED", token.getPrincipal()));
        } catch (Exception e) {
            throw new LoginException(Resources.getMessage("LOGIN_FAIL"), e);
        }
    }

    @RequestMapping(value = "/ids/stuinfo", method = RequestMethod.GET)
    public String main(Integer type, String actid, String status, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getRemoteUser();// 获取登录用户id
        if (!pbasicStuService.IsExistThisStu(uid)) {
            PBasicStu stu = new PBasicStu();
            stu.setId(uid);
            Principal principal = request.getUserPrincipal();
            if (principal != null && principal instanceof AttributePrincipal) {
                AttributePrincipal aPrincipal = (AttributePrincipal) principal;
                Map<String, Object> map = aPrincipal.getAttributes();
                stu.setStuName((String) map.get("cn"));
                stu.setDeptId(pbasicDeptService.getDeptIdByName((String) map.get("eduPersonOrgDN")));
            }
            pbasicStuService.insertStu(stu);
        }
        model.addAttribute("id", uid);
        if (type == 1) {// 学生信息
            return "redirect:/student/stuinfo.html";
        } else if (type == 2) {// 活动详情
            model.addAttribute("actid", actid);
            return "redirect:/student/actinfo.html";
        } else if (type == 3) {// 活动场次查询
            return "redirect:/student/actround.html";
        } else if (type == 4) {// 学分认定申请
            return "redirect:/student/scoreApply.html";
        } else if (type == 5) {// 报名活动列表
            return "redirect:/student/enrollacts.html";
        } else if (type == 6) {// 报名活动列表
            model.addAttribute("actid", actid);
            model.addAttribute("status", status);
            return "redirect:/student/enrollactinfo.html";
        } else if (type == 7) {// 可报名活动列表
            return "redirect:/student/isenrollacts.html";
        }
        return null;
    }

    @RequestMapping(value = "/stuinfo", method = RequestMethod.GET)
    public @ResponseBody Object get(String id, HttpServletRequest request, ModelMap modelMap) {
        return setSuccessModelMap(modelMap, pbasicStuService.getStuById(id));
    }

    @RequestMapping(value = "/DataSave", method = RequestMethod.POST)
    @ResponseBody
    public Object DataSave(PBasicStu stu, ModelMap model) {
        try {
            pbasicStuService.update(stu);
            return "success";
        } catch (Exception e) {
            return "fail";
        }

    }

    @RequestMapping(value = "/scoreApply/read", method = RequestMethod.POST)
    public Object get(@RequestParam(value = "stuId", required = false) String stuId, ModelMap modelMap) {
        PActRoundBean bean = pActRoundService.searchByStuId(stuId);
        return setSuccessModelMap(modelMap, bean);
    }

    // 查询申请时间是否在给定的申请时间内和是否已申请
    @RequestMapping(value = "/scoreApply/applyPermission", method = RequestMethod.POST)
    public Object getApplyPermission(ModelMap modelMap, @RequestParam(value = "stuId", required = false) String stuId) {
        String id = pScoreApplyService.searchByCurrentTime();
        PActRound bean = pActRoundService.searchByStuId(stuId);
        Integer affirm = bean.getIsaffirm();
        String isAffirm = "0";
        if (affirm == null) {
            isAffirm = "0";
        } else if (affirm == 1) {
            isAffirm = "1";
        } else {
            isAffirm = "0";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("isAffirm", isAffirm);
        return setSuccessModelMap(modelMap, map);
    }

    // 查询申请时间是否在给定的申请时间内和是否已申请
    @RequestMapping(value = "/scoreApply/applyPermission2", method = RequestMethod.POST)
    public Object getApplyPermission2(ModelMap modelMap, @RequestParam(value = "stuId", required = false) String stuId) {
        String id = pScoreApplyService.searchByCurrentTime();
        PActRound bean = pActRoundService.searchByStuId(stuId);
        Integer affirm = bean.getIsaffirm2();
        String isAffirm = "0";
        if (affirm == null) {
            isAffirm = "0";
        } else if (affirm == 1) {
            isAffirm = "1";
        } else {
            isAffirm = "0";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("isAffirm2", isAffirm);
        return setSuccessModelMap(modelMap, map);
    }

    @RequestMapping(value = "/scoreApply/applyScore", method = RequestMethod.POST)
    public Object applyOneScore(ModelMap modelMap, @RequestParam(value = "stuId", required = false) String stuId, @RequestParam(value = "score", required = false) int score) {
        pActRoundService.applyScore(score, stuId);
        return setSuccessModelMap(modelMap);
    }

    @RequestMapping(value = "/scoreApply/applyScore2", method = RequestMethod.POST)
    public Object applyOneScore2(ModelMap modelMap, @RequestParam(value = "stuId", required = false) String stuId, @RequestParam(value = "score", required = false) int score) {
        pActRoundService.applyScore2(score, stuId);
        return setSuccessModelMap(modelMap);
    }

    @RequestMapping(value = "/actround", method = RequestMethod.POST)
    public @ResponseBody Object getActRound(String id, String start, String end, HttpServletRequest request, ModelMap modelMap) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(start);
        Date endDate = sdf.parse(end);
        logger.info("start=" + start + ":" + startDate.toString());
        Map<String, Object> map = pActRoundService.searchByStuIdAndTime(id, startDate, endDate);
        return setSuccessModelMap(modelMap, map);
    }

    @RequestMapping(value = "/actround/getTime", method = RequestMethod.GET)
    public @ResponseBody Object getActRound(HttpServletRequest request, ModelMap modelMap) {
        Map<String, String> map = new HashMap<String, String>();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int nextYear = year + 1;
        map.put("start", year + "-05-01");
        map.put("end", nextYear + "-04-30");
        return setSuccessModelMap(modelMap, map);
    }

    /**
     * 活动详情
     * 
     * @param actid
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/act/actinfo", method = RequestMethod.GET)
    public @ResponseBody Object getActInfo(String actid, HttpServletRequest request, ModelMap modelMap) {
        PBasicAct pBasicAct = pbasicActService.queryById(actid);
		return setSuccessModelMap(modelMap, pbasicActService.getDateString(pBasicAct));
    }

    @RequestMapping(value = "/act/enroll", method = RequestMethod.POST)
    public @ResponseBody Object enroll(String actid, String stuid, HttpServletRequest request, ModelMap modelMap) {
        // 查询是否在报名时间内
        if (!pbasicActService.isEnrolTime(actid)) {
            return setSuccessModelMap(modelMap, 202);
        }
        PActEnrol pActEnrol = pactEnrolService.isenrol(stuid, actid);
        // 查询是否已报名
        if (pActEnrol != null && pActEnrol.getIsenrol() == 1) {
            return setSuccessModelMap(modelMap, 201);
        }
        int type = pbasicStuService.getStuTypeById(stuid);
        int enrollCount = pactEnrolService.enrollCount(type, actid);
        int fullCount = pbasicActService.getFullCount(type, actid);
        if (enrollCount >= fullCount) {
            return setSuccessModelMap(modelMap, 203);
        }
        if (pActEnrol == null) {
            pActEnrol = new PActEnrol();
        }
        pActEnrol.setActId(actid);
        pActEnrol.setStuId(stuid);
        pActEnrol.setDeptId(pbasicStuService.getDeptNameByStuId(stuid));
        pActEnrol.setIsenrol(1);// 0未报名，1已报名
        pActEnrol.setEnrolTime(new Date());
        pactEnrolService.add(pActEnrol);
        return setSuccessModelMap(modelMap, 200);
    }

    @RequestMapping("/actList")
    @ResponseBody
    public PagingData queryActs(int limit, int offset, String _) {
        List<PBasicAct> acts = pbasicActService.getActs(offset, limit);
        PagingData data = new PagingData();
        data.setRows(acts);
        data.setTotal(acts.size());
        return data;
    }

    @RequestMapping(value = "/enrollacts", method = RequestMethod.POST)
    @ResponseBody
    public Object getEnrollActs(String id, int offset, int limit, HttpServletRequest request, ModelMap modelMap) throws ParseException {
        Map<String, Object> map = pactEnrolService.getActsByStuId(id, offset, limit);
        return setSuccessModelMap(modelMap, map);
    }

    @RequestMapping(value = "/enrollacts/info", method = RequestMethod.POST)
    @ResponseBody
    public Object getEnrollActInfo(String actId, String status, String stuId, HttpServletRequest request, ModelMap modelMap) throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        PBasicAct pBasicAct = pbasicActService.getActByActId(actId);
        if (pBasicAct == null) {
            pBasicAct = new PBasicAct();
            pBasicAct.setActName("发生错误，信息查询不到！");
        }
		map.put("pBasicAct", pbasicActService.getDateString(pBasicAct));
        if (!status.equals("12")) {
            map.put("title", "活动详情");
        } else {
            map.put("title", "参加活动结果");
            String message;
            PCardFlowBean pCardFlowBean = pCardFlowService.getIsvalidAndReason(pBasicAct.getActName(), stuId);
            if (pCardFlowBean.getIsValid() == 0) {
                message = "本次活动参加无效</br>原因是：" + pCardFlowBean.getInvalidReason();
            } else {
                message = "本次活动参加有效";
            }
            map.put("message", message);
        }
        map.put("status", status);
        return setSuccessModelMap(modelMap, map);
    }

    @RequestMapping(value = "/enrollacts/enrollChange", method = RequestMethod.POST)
    @ResponseBody
    public Object enrollChange(String actId, String stuId, Integer isenrol, HttpServletRequest request, ModelMap modelMap) throws ParseException {
        if (isenrol == 1) {
            int type = pbasicStuService.getStuTypeById(stuId);
            int enrollCount = pactEnrolService.enrollCount(type, actId);
            int fullCount = pbasicActService.getFullCount(type, actId);
            if (enrollCount >= fullCount) {
                return setSuccessModelMap(modelMap, 203);
            }
        }
        pactEnrolService.updateById(actId, stuId, isenrol);
        return setSuccessModelMap(modelMap, 200);
    }

    @RequestMapping(value = "/isenrollacts", method = RequestMethod.POST)
    @ResponseBody
    public Object getIsEnrollActs(String id, int offset, int limit, HttpServletRequest request, ModelMap modelMap) throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<PBasicAct> pBasicActs = pbasicActService.getEnrollActs(offset, limit);
        List<PBasicAct> res = new ArrayList<PBasicAct>();
        for (PBasicAct pBasicAct : pBasicActs) {
            String actId = pBasicAct.getId();
            PActEnrol pActEnrol = pactEnrolService.isenrol(id, actId);
            if (pActEnrol != null && pActEnrol.getIsenrol() == 1) {
                continue;
            }
            int type = pbasicStuService.getStuTypeById(id);
            int enrollCount = pactEnrolService.enrollCount(type, actId);
            int fullCount = pbasicActService.getFullCount(type, actId);
            if (enrollCount >= fullCount) {
                continue;
            }
            res.add(pBasicAct);
        }
        int total = res.size();
        map.put("total", total);
        map.put("pBasicActs", res);
        return setSuccessModelMap(modelMap, map);
    }

}
