/**

 * 项目名称：平台 

 * 系统名：基础数据 

 * 文件名：XTYHAction.java 

 */

package com.centit.sys.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.base.product.model.ProductUserModel;
import com.centit.common.controller.BaseController;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.model.Properties;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.commons.util.security.MD5Encrypt;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.UserBean;
import com.centit.sys.model.XTYHBean;
import com.centit.sys.model.XtyhLedgerChrgModel;
import com.centit.sys.service.XTSQService;
import com.centit.sys.service.XTYHService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * The Class XTYHAction.
 * 
 * @module 系统管理
 * @fuc 系统用户
 * @version 1.1
 * @author 唐赟
 */
@Controller
@RequestMapping("/sys/xtyh")
public class XTYHController extends BaseController {

    //系统用户Service变量
    /** The xtyh service. */
	@Autowired
    private XTYHService xtyhService;

    /** The xtsq service. */
	@Autowired
    private XTSQService xtsqService;
	
	private static final String webPath = "sys/xtyh";




    /**
     * 机构信息框架页面.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@GetMapping("/toFrames")
    public String toFrames(HttpServletRequest request, HttpServletResponse response) {

        //如果帐套不显示，则帐套分管不显示 范春锋 20120323
        String account = Properties.getString("ACCOUNT_DISPLAY");
        if (!StringUtil.isEmpty(account) && "false".equals(account.toUpperCase())) {
            request.setAttribute("account", "noDisplay");
        }
        request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
        return view(webPath, "xtyh_Frame");
    }


    /**
     * 增加机构信息初始化.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@PostMapping("/toAdd")
    public String toAdd(HttpServletRequest request, HttpServletResponse response) {

        String status = ParamUtil.get(request, "status");
        System.out.println("status:" + status);
        request.setAttribute("status", status);
        return view(webPath, "save");
    }


    /**
     * 修改系统用户信息初始化页面.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@GetMapping("/toEdit")
    public String toEdit(HttpServletRequest request, HttpServletResponse response) {

        String xtyhID = ParamUtil.get(request, "XTYHID");
        String status = "add";
        if (StringUtils.isNotEmpty(xtyhID)) {
            Map <String, String> entry = xtyhService.load(xtyhID);
            request.setAttribute("rst", entry);
            status = "modify";
        }
        request.setAttribute("status", status);
        return view(webPath, "xtyh_Edit");
    }


    /**
     * 保存系统用户信息.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @PostMapping("/toSave")
    public void toSave(HttpServletRequest request, HttpServletResponse response) {

    	UserBean userBean =  ParamUtil.getUserBean(request);
        String jsonData = ParamUtil.get(request, "jsonData");
        XTYHBean xtyhModel = new XTYHBean();
        if (StringUtils.isNotEmpty(jsonData)) {
            xtyhModel = new Gson().fromJson(jsonData, new TypeToken <XTYHBean>() {
            }.getType());
        }
        Map <String, String> params = new HashMap <String, String>();
        params.put("YHBH", xtyhModel.getYHBH());
        params.put("YHM", xtyhModel.getYHM());
        params.put("YHKL", xtyhModel.getYHKL());
        params.put("RYXXID", xtyhModel.getRYXXID());
        params.put("BMXXID", xtyhModel.getBMXXID());
        params.put("JGXXID", xtyhModel.getJGXXID());
        params.put("ZTXXID", xtyhModel.getZTXXID());
        params.put("YHLB", xtyhModel.getYHLB());
        params.put("YHYWLX", xtyhModel.getYHYWLX());
        params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
        params.put("PORTAL_URL", xtyhModel.getPORTAL_URL());
        params.put("CREATOR", userBean.getXTYHID());//制单人ID
        params.put("CRE_NAME", userBean.getXM());//制单人名称

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        String yhbh = xtyhModel.getYHBH();
        String yhm = xtyhModel.getYHM();

        String status = xtyhModel.getCtrType();

        int flag = 1;
        if ("add".equals(status)) {
            //查询用户编号,用户名是否有重复
            List <XTYHBean> list = xtyhService.selectYhbh();
            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getYHBH() != null && yhbh != null) {
                                    if (yhbh.equals(list.get(i).getYHBH())) {
                                        flag = 0;
                                        break;
                                    }
                                }

                if (list.get(i).getYHM() != null && yhm != null) {
                    if (yhm.equals(list.get(i).getYHM())) {
                        flag = 2;
                        break;
                    }
                }
            }
        }

        if (flag == 0) {
            jsonResult = jsonResult(false, "用户编号重复！");
        } else if (flag == 2) {
            jsonResult = jsonResult(false, "用户名重复！");
        } else {
            if ("add".equals(status)) {
                params.put("YHZT", BusinessConsts.JC_STATE_DEFAULT); //用户状态默认为“制定”
                try {
                    String xtyhid = StringUtil.uuid32len();
                    params.put("YHBH", yhbh);
                    params.put("XTYHID", yhbh);
                    //params.put("XTYHID", params.get("YHBH"));
                    params.put("XTYHJGFGID", StringUtil.uuid32len());
                    params.put("XTYHBMFGID", StringUtil.uuid32len());
                    params.put("XTYHZTDZID", StringUtil.uuid32len());
                    xtyhService.insert(params);

                    //如果没有帐套，则插入默认帐套 范春锋 20120223
                    String account = Properties.getString("ACCOUNT_DISPLAY");
                    if (!StringUtil.isEmpty(account) && "FALSE".equals(account.toUpperCase())) {
                        //插入数据
                        String[] ztssids = new String[] {Properties.getString("ACCOUNT_CODE")};
                        //插入帐套
                        xtyhService.txInsertZtfgMx(ztssids, xtyhid);
                    }
                    request.setAttribute("msg", "保存成功！");
                } catch (Exception e) {
                    e.printStackTrace();
                    jsonResult = jsonResult(false, "保存失败！");
                }

            } else {
                try {
                    params.put("XTYHID", request.getParameter("XTYHID"));
                    //判断人员编号是否更改
                    String OldRYXXID = xtyhService.getRyxxid(request.getParameter("XTYHID"));
                    if(OldRYXXID.equals(xtyhModel.getRYXXID())){//没更改则直接更新
                    	xtyhService.updateById(params);
                    }else{//更改后则重新生成机构，部门，帐套明细信息
                    	//String xtyhid = StringUtil.uuid32len();
                        params.put("XTYHID", yhbh);
                        params.put("XTYHJGFGID", StringUtil.uuid32len());
                        params.put("XTYHBMFGID", StringUtil.uuid32len());
                        params.put("XTYHZTDZID", StringUtil.uuid32len());
                        xtyhService.updateMxInfo(params);
                    }
                    request.setAttribute("msg", "修改成功！");
                } catch (Exception e) {
                    jsonResult = jsonResult(false, "保存失败！");
                }
            }
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }

    }


    /**
     * 查看系统用户详细信息.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @GetMapping("/toDetail")
    public String toDetail(HttpServletRequest request, HttpServletResponse response) {

        String xtyhID = ParamUtil.get(request, "XTYHID");
        Map <String, String> entry = xtyhService.load(xtyhID);
        request.setAttribute("rst", entry);
        return view(webPath, "xtyh_Detail");
    }


    /**
     * 删除.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @PostMapping("/delete")
    public String delete(HttpServletRequest request, HttpServletResponse response) {

        String xtyhID = ParamUtil.get(request, "XTYHID");
        UserBean userBean = ParamUtil.getUserBean(request);
        if (StringUtil.isEmpty(xtyhID)) {
            request.setAttribute("msg", "系统用户编号不存在！");
        }
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        Map <String, String> entry =  xtyhService.load( xtyhID);
        Map <String, String> params = new HashMap <String, String>();
        params.put("ZTXXID", entry.get("ZTXXID"));
        params.put("BMXXID", entry.get("BMXXID"));
        params.put("JGXXID", entry.get("JGXXID"));
        params.put("XTYHID", xtyhID);
        params.put("DATARECYCLEID", StringUtil.uuid32len());
        params.put("DELETOR", userBean.getYHM());
        try {
            xtyhService.txdelete(params);
            request.setAttribute("msg", "删除成功！");
        } catch (Exception ex) {
            jsonResult = jsonResult(false, "删除失败，状态必须为‘制定’才能删除！");
            ex.printStackTrace();
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
        return view(webPath, "xtyh_List");
    }


    /**
     * 查询结果列表.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @RequestMapping(value = { "/toList"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toList(HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {

        Map <String, Object> params =LogicUtil.getParameterMap(request);
        //字段排序
        ParamUtil.setOrderField(request, params, "a.CRE_TIME DESC,a.YHBH", "ASC");
        xtyhService.pageQuery(params, pageDesc);
        request.setAttribute("page", pageDesc);

        request.setAttribute("params", params);
//        UserBean userBean = ParamUtil.getUserBean(request);
        //String QXJBCON = QXUtil.getQXTJ(userBean, PrivateConsts.YHXXWH);
        //params.put("QXJBCON", QXJBCON);
        return view(webPath, "xtyh_List");
    }




    /**
     * 启用，停用按钮.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @author tang_yun
     */
    @PostMapping("/updateUserStatus")
    public void updateUserStatus(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        Map <String, Object> params =LogicUtil.getParameterMap(request);
        if ("1".equals(params.get("flag"))) {
            params.put("yhzt", BusinessConsts.JC_STATE_ENABLE);
        } else {
            params.put("yhzt", BusinessConsts.JC_STATE_DISENABLE);
        }
        try {
        	xtyhService.updateUserStatus(params);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = jsonResult(false, "更新失败");
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 设为默认密码按钮.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     * 
     * @author tang_yun
     */
    @PostMapping("/updatePassword")
    public void updatePassword(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
        	Map <String, Object> params =LogicUtil.getParameterMap(request);
	        params.put("password", MD5Encrypt.MD5(String.valueOf(params.get("DEF_PWD"))));
            xtyhService.updatePassword(params);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = jsonResult(false, "更新失败");
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 系统授权详细.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @GetMapping("/childList")
    public String childList(HttpServletRequest request, HttpServletResponse response) {

        String xtyhid = ParamUtil.get(request, "XTYHID");
        if (StringUtils.isNotEmpty(xtyhid)) {
        	List <Map<String,String>> result = xtyhService.childQuery(xtyhid);

            request.setAttribute("rst", result);
        }

        return view(webPath, "xtyh_Mx_List");
    }


    /**
     * 跳转到用户权限页面.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     * 
     * @throws Exception the exception
     */
    @GetMapping("/toUserQXList")
    public String toUserQXList(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String XTYHID = ParamUtil.get(request, "XTYHID");
        HttpSession session = request.getSession(false);
        UserBean aUserBean = (UserBean) session.getAttribute("UserBean");
        String qxtab = xtsqService.getQXTab("XTYHID", XTYHID, aUserBean.getXTYHID(), aUserBean.getJGXXID(), request);
        request.setAttribute("qxtab", qxtab);
        request.setAttribute("XTYHID", XTYHID);
        System.err.println("qxtab===" + qxtab);
        return view(webPath, "userpower");
    }


    /**
     * 跳转到用户权限树页面.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @GetMapping("/toMkxxTree")
    public String toMkxxTree(HttpServletRequest request, HttpServletResponse response) {

        try {
            String KeyName = request.getParameter("KeyName") == null ? "" : request.getParameter("KeyName");
            String KeyID = request.getParameter("KeyID") == null ? "" : request.getParameter("KeyID");
            String MKLB = request.getParameter("MKLB") == null ? "" : request.getParameter("MKLB");

            String[] jspMkary = xtsqService.getMKAry(MKLB, 5, "1=1 AND (MKZT<>'停用' or MKZT is null or MKZT='')");
            request.setAttribute("jspMkary", jspMkary);
            String condition = " where " + KeyName + "='" + KeyID + "'";
            String jspYesOrNo = "";
            String tableName = "";
            boolean AllFlag = false;

            if (KeyName.equalsIgnoreCase("XTYHID")) {
                tableName = "T_SYS_XTSQ";
                //jspYesOrNo = xtsqService.getStrMK2("T_SYS_TEMPSYSTEMADM", " where XTYHID='" + xtyhid + "' AND MKLB='" + MKLB + "'");
                if (jspYesOrNo.equals("")) {
                    AllFlag = true;
                }
            }

            String jspStrMK = "";
            if (KeyName.equals("SYSTEMADM")) {

                AllFlag = true;

                //jspStrMK = xtsqService.getStrMK2("T_SYS_TEMPSYSTEMADM", " where XTYHID='" + KeyID + "'");
            } else {
                jspStrMK = xtsqService.getStrMK(tableName, condition);
            }
            request.setAttribute("jspYesOrNo", jspYesOrNo);
            request.setAttribute("jspStrMK", jspStrMK);
            Map<String,Object> jspSubMkaryMap = new HashMap<String,Object>();
            for (int i = 0; i < jspMkary.length; i = i + 3) {
                int tempMKAryI = i + 1;

                if (jspYesOrNo.indexOf(jspMkary[tempMKAryI]) != -1 || AllFlag) {
                    String[] SubMKAry = xtsqService.getMKAry(MKLB, 7, " SJMK='" + jspMkary[tempMKAryI]
                            + "' AND (MKZT<>'停用' or MKZT is null or MKZT='')");
                    jspSubMkaryMap.put(tempMKAryI + "", SubMKAry);

                }

            }
            request.setAttribute("jspSubMkaryMap", jspSubMkaryMap);
            request.setAttribute("KeyName", KeyName);
            request.setAttribute("KeyID", KeyID);
            request.setAttribute("MKLB", MKLB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view(webPath, "MKXXTree");
    }
        
        /**
         * Tran code p.
         * 
         * @param Str the str
         * 
         * @return the string
         */
        public String tranCodeP(String Str) {

        return Str == null ? "" : Str;
    }


    /**
     * 更新用户权限.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
        @PostMapping("/insertXTYHQXSelf")
    public String insertXTYHQXSelf(HttpServletRequest request, HttpServletResponse response) {

        String[] ins_name2 = {"YHQXID", "XTYHID", "XTMKID"};
        String[] ins_value2 = new String[ins_name2.length];
        String MKSM = request.getParameter("MKSM");
        String XTYHID = tranCodeP(request.getParameter("XTYHID"));
        String[] selXTMKID = request.getParameterValues("checkSel");

        try {
            xtsqService.insertXTSQ(XTYHID, MKSM, ins_name2, ins_value2, selXTMKID, request);

        } catch (Exception e) {

            request.setAttribute("msg", "授权失败!");
            e.printStackTrace();
            return view(Consts.WEB_PATH, Consts.SUCCESS);
        }
        request.setAttribute("msg", "授权成功!");
        return view(Consts.WEB_PATH, Consts.SUCCESS);
    }


    /**
     * 取用户的机构分管信息.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
     @GetMapping("/toJgfgMx")
    public String toJgfgMx(HttpServletRequest request, HttpServletResponse response) {

        String xtyhID = ParamUtil.get(request, "XTYHID");
        List <Map <String, String>> entry = xtyhService.queryJgfgMxByXtyhid(xtyhID);
        request.setAttribute("rst", entry);
        request.setAttribute("xtyhzt", ParamUtil.get(request, "xtyhzt"));
        return view(webPath, "xtyh_jgfg");
    }


    /**
     * 取用户的部门分管信息.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
        @GetMapping("/toBmfgMx")
    public String toBmfgMx(HttpServletRequest request, HttpServletResponse response) {

        String xtyhID = ParamUtil.get(request, "XTYHID");
        List <Map <String, String>> entry = xtyhService.queryBmfgMxByXtyhid(xtyhID);
        request.setAttribute("rst", entry);
        request.setAttribute("xtyhzt", ParamUtil.get(request, "xtyhzt"));
        return view(webPath, "xtyh_bmfg");
    }


    /**
     * 取用户的帐套分管信息.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
     @GetMapping("/toZtfgMx")
    public String toZtfgMx(HttpServletRequest request, HttpServletResponse response) {

        String xtyhID = ParamUtil.get(request, "XTYHID");
        List <Map <String, String>> entry = xtyhService.queryZtfgMxByXtyhid(xtyhID);
        request.setAttribute("rst", entry);
        request.setAttribute("xtyhzt", ParamUtil.get(request, "xtyhzt"));
        return view(webPath, "xtyh_ztfg");
    }


    /**
     * 插入机构分管明细表.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/insertJgfgMx")
    public void insertJgfgMx(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        String xtyhid = ParamUtil.get(request, "XTYHID");
        String jgxxid = ParamUtil.get(request, "JGXXIDS");
        String rownum = ParamUtil.get(request, "ROWNUM");

        if (rownum != null && !"0".equals(rownum)) {
            String[] jgssids = jgxxid.split(",");
            if (xtyhService.txInsertJgfgMx(jgssids, xtyhid)) {
                jsonResult = jsonResult(false, "更新成功!");
            } else {
                jsonResult = jsonResult(false, "更新失败!");
            }
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 插入部门分管明细表.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/insertBmfgMx")
    public void insertBmfgMx(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        String xtyhid = ParamUtil.get(request, "XTYHID");
        String bmxxid = ParamUtil.get(request, "BMXXIDS");
        String rownum = ParamUtil.get(request, "ROWNUM");

        if (rownum != null && !"0".equals(rownum)) {
            String[] bmssids = bmxxid.split(",");
            if (xtyhService.txInsertBmfgMx(bmssids, xtyhid)) {
                jsonResult = jsonResult(false, "更新成功!");
            } else {
                jsonResult = jsonResult(false, "更新失败!");
            }
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 插入帐套分管明细表.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/insertZtfgMx")
    public void insertZtfgMx(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        String xtyhid = ParamUtil.get(request, "XTYHID");
        String ztxxid = ParamUtil.get(request, "ZTXXIDS");
        String rownum = ParamUtil.get(request, "ROWNUM");

        if (rownum != null && !"0".equals(rownum)) {
            String[] ztssids = ztxxid.split(",");
            if (xtyhService.txInsertZtfgMx(ztssids, xtyhid)) {
                jsonResult = jsonResult(false, "更新成功!");
            } else {
                jsonResult = jsonResult(false, "更新失败!");
            }
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 删除机构分管明细表数据.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/deleteJgfgMx")
    public void deleteJgfgMx(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        String xtyhjgfgid = ParamUtil.get(request, "XTYHJGFGID");

        if (xtyhService.txDeleteJgfgMx(xtyhjgfgid)) {
            jsonResult = jsonResult(false, "删除成功!");
        } else {
            jsonResult = jsonResult(false, "删除失败!");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 删除机构分管明细表数据.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/deleteBmfgMx")
    public void deleteBmfgMx(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        String xtyhbmfgid = ParamUtil.get(request, "XTYHBMFGID");

        if (xtyhService.txDeleteBmfgMx(xtyhbmfgid)) {
            jsonResult = jsonResult(false, "删除成功!");
        } else {
            jsonResult = jsonResult(false, "删除失败!");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 删除机构分管明细表数据.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/deleteZtfgMx")
    public void deleteZtfgMx(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        String xtyhztdzid = ParamUtil.get(request, "XTYHZTDZID");

        if (xtyhService.txDeleteZtfgMx(xtyhztdzid)) {
            jsonResult = jsonResult(false, "删除成功!");
        } else {
            jsonResult = jsonResult(false, "删除失败!");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
    
    
    /**
     * * to 用户分管货品明细页面
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @PostMapping("/toUserEdit")
    public String toUserEdit(HttpServletRequest request, HttpServletResponse response) {
    	//多个Id批量查询，用逗号隔开
        String USER_CHARG_PRD_IDS = request.getParameter("USER_CHARG_PRD_ID");
        //没有零星领料Id可以直接跳转。
        if (!StringUtil.isEmpty(USER_CHARG_PRD_IDS)) {
          List <ProductUserModel> list = xtyhService.loadUserChargByIDS(USER_CHARG_PRD_IDS);
          request.setAttribute("rst", list);
        }
        return view(webPath, "xtyh_chld__Edit");
    }
    
    
    /**
     * * 用户货品分管批量删除
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/userDelete")
    public void userDelete(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
        	String USER_CHARG_PRD_IDS = request.getParameter("USER_CHARG_PRD_ID");
            //批量删除，多个Id之间用逗号隔开
        	xtyhService.txBatch4DeleteUser(USER_CHARG_PRD_IDS);
		} catch (Exception e) {
            jsonResult = jsonResult(false, "删除失败");
            e.printStackTrace();
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
    /**
     * * 用户货品分管 新增/修改数据
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/userEdit")
    public void userEdit(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
            String XTYHID = request.getParameter("XTYHID");
            String jsonDate = request.getParameter("childJsonData");
            if (!StringUtil.isEmpty(jsonDate)) {
                List <ProductUserModel> modelList = new Gson().fromJson(jsonDate, new TypeToken <List <ProductUserModel>>() {
                }.getType());
                StringBuffer str=new StringBuffer();
                for (int i = 0; i < modelList.size(); i++) {
                	ProductUserModel model=modelList.get(i);
                	if(StringUtil.isEmpty(model.getUSER_CHARG_PRD_ID())){
                		str.append("'").append(model.getPRD_ID()).append("',");
                	}
				}
                String PRD_IDS=str.toString();
                if (!StringUtil.isEmpty(PRD_IDS)) {
                	PRD_IDS = PRD_IDS.substring(0,PRD_IDS.length() - 1);
                	int count=xtyhService.checkPRD(PRD_IDS,XTYHID);
                    if(count>0){
                    	throw new ServiceException("存在重复货品记录！");
                    }
        		}
                xtyhService.txChldEdit(modelList,XTYHID);
            }
        }catch(ServiceException s){
        	jsonResult = jsonResult(false, s.getMessage());
        } catch (Exception e) {
        	e.printStackTrace();
            jsonResult = jsonResult(false, "保存失败");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
    
    /**
     * * 用户分管货品套列表
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @GetMapping("/toUserList")
    public String toUserList(HttpServletRequest request, HttpServletResponse response) {
        String XTYHID =ParamUtil.get(request, "XTYHID");
        if(!StringUtil.isEmpty(XTYHID))
        {
        	 List<ProductUserModel> result = xtyhService.userQueryById(XTYHID);
             request.setAttribute("rst", result);
        }
        return view(webPath, "xtyh_chld__list");
    }
    
    
    /**
     * 设置用户分管所以渠道
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/updateUserChrgChann")
    public void updateUserChrgChann(
    		HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
        	String CHRG = ParamUtil.get(request, "CHRG");
        	String xtyhid = ParamUtil.get(request, "xtyhid");
            Map<String, String> params = new HashMap <String, String>();
            params.put("XTYHID", xtyhid);
            if(BusinessConsts.INTEGER_0.equals(CHRG)){
            	xtyhService.txDeleteUserChrgChann(params);
            }
            if(BusinessConsts.INTEGER_1.equals(CHRG)){
            	xtyhService.txUpdateUserChrgChann(params);
            }
        	
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = jsonResult(false, "操作失败");
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
    @GetMapping("/toStepFrame")
	public String toStepFrame(
			HttpServletRequest request, HttpServletResponse response) {
		
		String XTYHID = ParamUtil.get(request, "XTYHID");
		request.setAttribute("XTYHID", XTYHID);
		return view(webPath, "userstep_Frame");

	}
	
    @GetMapping("/toTopPage")
	public String toTopPage(
			HttpServletRequest request, HttpServletResponse response) {
//		UserBean userBean = ParamUtil.getUserBean(request);
//		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS))) {
//			throw new ServiceException("对不起，您没有权限 !");
//		}
//		
//		request.setAttribute("pvg", setPvg(userBean));
		String XTYHID = ParamUtil.get(request, "XTYHID");
		Map<String,String> entry = this.xtyhService.load(XTYHID);
		request.setAttribute("user", entry);
		return view(webPath, "userstep_top_List");

	}
	
	
	 /**
	    * 用户列表
	    * @param mapping
	    * @param form
	    * @param request
	    * @param response
	    * @return
	    */
	@RequestMapping(value = { "/userList"}, method = { RequestMethod.GET, RequestMethod.POST })
		public String userList(
				HttpServletRequest request, HttpServletResponse response) {
			Map<String,Object> params=LogicUtil.getParameterMap(request);
			String search = ParamUtil.get(request, "search");
			String notcharg = ParamUtil.get(request, "notcharg");
			String PAR_USER_ID = ParamUtil.get(request, "PAR_USER_ID");//分管人ID
			StringBuffer qx = new StringBuffer();
			if("1".equals(notcharg)){//未分管
				qx.append(" and temp.PAR_USER_ID is null ");
			}

			if("2".equals(notcharg)){//已分管
				qx.append(" and temp.PAR_USER_ID='");
				qx.append(PAR_USER_ID).append("' ");
			}
			params.put("QX", qx.toString());
			params.put("search", search);
			params.put("STATE", "启用");
			params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
			List<Map<String,String>> list = this.xtyhService.queryUserList(params);
			request.setAttribute("result", list);
			request.setAttribute("params", params);
		 
			return view(webPath, "userstep_List");
		}
		
	/**
	 * 编辑上下级关系
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@PostMapping("/editStepUser")
	public void editStepUser(
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String PAR_USER_ID = ParamUtil.get(request, "PAR_USER_ID");
			String XTYHIDS = ParamUtil.get(request, "XTYHIDS");
			Map<String, String> params = new HashMap<String, String>();
			params.put("PAR_USER_ID", PAR_USER_ID);
			params.put("XTYHIDS", XTYHIDS);
			this.xtyhService.txEditStepUser(params);
			jsonResult = jsonResult(true, "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "保存失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * 删除上下级关系
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@PostMapping("/deleteStepUser")
	public void deleteStepUser(
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String PAR_USER_ID = ParamUtil.get(request, "PAR_USER_ID");
			String XTYHIDS = ParamUtil.get(request, "XTYHIDS");
			Map<String, String> params = new HashMap<String, String>();
			params.put("PAR_USER_ID", PAR_USER_ID);
			params.put("XTYHIDS", XTYHIDS);
			this.xtyhService.txDeleteStepUser(params);
			jsonResult = jsonResult(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "删除失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}
	
	
	

	/**
	 * 帐套分管列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "ledgerChrgList"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String ledgerChrgList( HttpServletRequest request, HttpServletResponse response) {
        String XTYHID = ParamUtil.get(request, "XTYHID");
        if(!StringUtil.isEmpty(XTYHID))
        {
        	 List<Map<String,String>> result = xtyhService.getLedgerChrgList(XTYHID);
             request.setAttribute("rst", result);
        }
        return view(webPath,"xtyh_List_Ledger");
    }
	
	/**
	 * 跳转帐套分管编辑页面
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/toLedgerEdit")
	   public String toLedgerEdit( HttpServletRequest request, HttpServletResponse response) {
	     	//多个Id批量查询，用逗号隔开
	       String XTYH_LEDGER_IDS = request.getParameter("XTYH_LEDGER_IDS");
	    
	       if (!StringUtil.isEmpty(XTYH_LEDGER_IDS)) {
	          List <Map<String, String>> list = xtyhService.getLedgerChrgListByIds(XTYH_LEDGER_IDS);
	          request.setAttribute("rst", list);
	       }
	       return view(webPath,"xtyh_Edit_Ledger");
	   }
	
	/**
	 * 根据明细ID删除渠道帐套分管
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "delLedChrgByLedIds"}, method = { RequestMethod.GET, RequestMethod.POST })
    public void delLedChrgByLedIds( HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
        	String XTYH_LEDGER_IDS = request.getParameter("XTYH_LEDGER_IDS");
            //批量删除，多个Id之间用逗号隔开
            xtyhService.delLedChrgByLedIds(XTYH_LEDGER_IDS,userBean);
		} catch (Exception e) {
            jsonResult = jsonResult(false, "删除失败");
            e.printStackTrace();
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
	/**
	 * 新增帐套分管
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "insertLegerChrg"}, method = { RequestMethod.GET, RequestMethod.POST })
    public void insertLegerChrg( HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
        	String XTYHID = request.getParameter("XTYHID");
            String jsonDate = request.getParameter("childJsonData");
        	 if (!StringUtil.isEmpty(jsonDate)) {
                 List <XtyhLedgerChrgModel> modelList = LogicUtil.StrToArray(jsonDate, XtyhLedgerChrgModel.class);
                 xtyhService.insertLegerChrg(XTYHID, modelList,userBean);
                 jsonResult = jsonResult(true, "保存成功");
             }
		}catch(RuntimeException s){
			 jsonResult = jsonResult(false, s.getMessage());
		} catch (Exception e) {
            jsonResult = jsonResult(false, "保存失败");
            e.printStackTrace();
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
}
