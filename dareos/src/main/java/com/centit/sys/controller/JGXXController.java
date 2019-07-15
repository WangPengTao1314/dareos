/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：JGXXAction.java
 */
package com.centit.sys.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centit.common.controller.BaseController;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.DateUtil;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.core.po.PageDesc;
import com.centit.sys.model.JGXXModel;
import com.centit.sys.model.JGXXTree;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.JGXXService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * The Class JGXXAction.
 * 
 * @module 系统管理
 * @func 机构信息
 * @version 1.1
 * @author 蔡瑾钊
 */
@Controller
@RequestMapping("/sys/jgxx")
public class JGXXController extends BaseController {

    // 日志记录
    /** The logger. */
    private Logger      logger = Logger.getLogger(JGXXController.class);
    
    private static final String webPath = "/sys/jgxx";
    /** The jgxx service. */
    @Autowired
    private JGXXService jgxxService;


    /**
     * Sets the jgxx service.
     * 
     * @param jgxxService the new jgxx service
     */
	/*
	 * public void setjgxxService(JGXXService jgxxService) {
	 * 
	 * this.jgxxService = jgxxService; }
	 */


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
    @RequestMapping(value="/toFrame")
    public String toFrame(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
    	return view(webPath,"jgxx_Frame");
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
    @RequestMapping("/toList")
    public String toList(HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {
        //chk权限
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, "XT0010301"))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
    	 
        Map <String, Object> params = new HashMap <String, Object>();
        ParamUtil.putStr2Map(request, "JGXXID", params);
        //ParamUtil.putStr2Map(request, "JGMC", params);
        params.put("JGMC", ParamUtil.utf8Decoder(request, "JGMC"));
        //ParamUtil.putStr2Map(request, "JGJC", params);
        params.put("JGJC", ParamUtil.utf8Decoder(request, "JGJC"));
        ParamUtil.putStr2Map(request, "JGBH", params);
        //ParamUtil.putStr2Map(request, "SJJG", params);
        params.put("SJJG", ParamUtil.utf8Decoder(request, "SJJG"));
        String search = request.getParameter("search");
        String JGZT = request.getParameter("JGZT");
        if (search == null || "".equals(search)) {
            params.put("JGZT", "('" + BusinessConsts.JC_STATE_DEFAULT + "','" + BusinessConsts.JC_STATE_ENABLE + "','" + BusinessConsts.JC_STATE_DISENABLE + "')");
        } else {
            if (null == JGZT || "".equals(JGZT)) {
                params.put("JGZT", "('" + BusinessConsts.JC_STATE_DEFAULT + "','" + BusinessConsts.JC_STATE_ENABLE + "','" + BusinessConsts.JC_STATE_DISENABLE + "')");
            } else {
                params.put("JGZT", "('" + JGZT + "')");
            }
        }
        ParamUtil.putStr2Map(request, "ZTXXID", params);
        ParamUtil.putStr2Map(request, "XH", params);
        // 只查询0的记录。1为删除。0为正常
        params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
        params.put("IS_DRP_LEDGER", "0"); //过滤掉分销商的机构
        // 字段排序
        ParamUtil.setOrderField(request, params, "u.XH", "asc");
        ParamUtil.putStr2Map(request, "pageSize", params);
        jgxxService.pageQuery(params, pageDesc);
        request.setAttribute("params", params);
        request.setAttribute("page", pageDesc);
        return view(webPath,"jgxx_List");
    }


    /**
     * 机构信息编辑初始化页面.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @RequestMapping("/toEdit")
    public String toEdit(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
        String jgxxId = ParamUtil.get(request, "JGXXID"); 
        Map<String,String> entry = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(jgxxId)) {
            entry = jgxxService.load(jgxxId);
        }else{
        	entry.put("ZTMC", userBean.getLoginZTMC());
        	entry.put("ZTXXID", userBean.getLoginZTXXID());
        }
        request.setAttribute("rst", entry);
        return view(webPath,"jgxx_Edit");
    }


    /**
     * 机构信息编辑 新增/修改.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @RequestMapping(value="/edit")
    public void edit(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, "XT0010302"))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        logger.info("Enter edit()");
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        String jsonData = ParamUtil.get(request, "jsonData");
        JGXXModel jgxxModel = new JGXXModel();
        if (StringUtils.isNotEmpty(jsonData)) {
            jgxxModel = new Gson().fromJson(jsonData, new TypeToken <JGXXModel>() {
            }.getType());
        }
        String jgxxId = ParamUtil.get(request, "JGXXID");
        String SJJG = jgxxModel.getSJJG();
        Map <String, String> params = new HashMap <String, String>();
        params.put("JGXXID", jgxxId);
        params.put("JGBH", jgxxModel.getJGBH());
        if (jgxxService.queryForInt(params)) {
        	params.put("DELFLAG", "0");
            if (jgxxService.loadJGZT(SJJG)) {
                try {
                    jgxxId = jgxxService.txEdit(jgxxId, jgxxModel, userBean);
                    jsonResult = jsonResult(true, jgxxId);
                } catch (Exception e) {
                	e.printStackTrace();
                    jsonResult = jsonResult(false, "保存失败");
                }
            } else {
                jsonResult = jsonResult(false, "不可选择停用的机构作为上级机构");
            }
        } else {
            jsonResult = jsonResult(false, "该编号已存在");
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 查看机构详细信息.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @RequestMapping(value="/toDetail")
    public String toDetail(HttpServletRequest request, HttpServletResponse response) {

        String jgxxId = ParamUtil.get(request, "JGXXID");
        if (StringUtils.isNotEmpty(jgxxId)) {
            Map <String, String> entry = jgxxService.load(jgxxId);
            request.setAttribute("rst", entry);
        }
        return view(webPath,"jgxx_Detail");
    }


    /**
     * 删除.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @RequestMapping("/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, "XT0010303"))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
            String jgxxId = ParamUtil.get(request, "JGXXID");
//            jgxxService.txDelete(jgxxId, userBean);
//            jgxxService.clearCaches(jgxxId);
            jgxxService.txNewDelete(jgxxId, userBean);
        } catch (Exception e) {
            jsonResult = jsonResult(false, "删除失败");
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 按钮修改状态为启用.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @RequestMapping("/changeStateQy")
    public void changeStateQy(HttpServletRequest request, HttpServletResponse response) {

    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, "XT0010304"))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        logger.info("按钮修改为启用单条记录开始");
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        boolean isChanged = false;
        try {
            logger.warn("取得对应记录的状态");
            String JGXXID = request.getParameter("JGXXID");
            // String state = entry.get("STATE");
            Map <String, String> params = new HashMap <String, String>();
            String changedState = "";
            params.put("JGXXID", JGXXID);
            params.put("UPDATER", userBean.getXTYHID());
            params.put("UPDTIME", DateUtil.now());
            changedState = BusinessConsts.JC_STATE_ENABLE;
            params.put("JGZT", BusinessConsts.JC_STATE_ENABLE);
            if (jgxxService.querySjForInt(params)) {

                jgxxService.txStartById(params);
                isChanged = true;

                if (isChanged) {
                    jsonResult = jsonResult(true, changedState);
                } else {
                    jsonResult = jsonResult(false, "状态不用修改");
                }
            } else {
                jsonResult = jsonResult(false, "请先启用上级");
            }
        } catch (Exception e) {
            jsonResult = jsonResult(false, "状态修改失败");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 按钮修改状态为停用.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @RequestMapping("/changeStateTy")
    public void changeStateTy(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, "XT0010304"))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        logger.info("按钮修改为停用单条记录开始");
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        boolean isChanged = false;
        try {
            logger.warn("取得对应记录的状态");
            String JGXXID = request.getParameter("JGXXID");
            // Map <String, String> entry = cljyxmService.load(CLWZZJXMID);
            // String state = entry.get("STATE");
            Map <String, String> params = new HashMap <String, String>();
            String changedState = "";
            params.put("JGXXID", JGXXID);
            params.put("UPDATER", userBean.getXTYHID());
            params.put("UPDTIME", DateUtil.now());
            changedState = BusinessConsts.JC_STATE_DISENABLE;
            params.put("JGZT", BusinessConsts.JC_STATE_DISENABLE);
            jgxxService.txStopById(params);
            isChanged = true;

            if (isChanged) {
                jsonResult = jsonResult(true, changedState);
            } else {
                jsonResult = jsonResult(false, "状态不用修改");
            }
        } catch (Exception e) {
            jsonResult = jsonResult(false, "状态修改失败");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 停用启用按钮修改单条记录状态.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @RequestMapping("/changeState")
    public void changeState(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, "XT0010304"))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        logger.info("停用启用按钮修改单条记录状态");
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        boolean isChanged = false;
        try {
            String JGXXID = request.getParameter("JGXXID");
            Map <String, String> entry = jgxxService.load(JGXXID);
            String state = entry.get("STATE");
            Map <String, String> params = new HashMap <String, String>();
            String changedState = state;
            params.put("JGXXID", JGXXID);
            if (BusinessConsts.JC_STATE_ENABLE.equals(state)) {
                changedState = BusinessConsts.JC_STATE_DISENABLE;
                params.put("JGZT", BusinessConsts.JC_STATE_DISENABLE);
                jgxxService.txUpdateById(params);
                isChanged = true;
            } else if (BusinessConsts.JC_STATE_DISENABLE.equals(state)) {
                changedState = BusinessConsts.JC_STATE_ENABLE;
                params.put("JGZT", BusinessConsts.JC_STATE_ENABLE);
                jgxxService.txUpdateById(params);
                isChanged = true;
            }
            if (isChanged) {
                jsonResult = jsonResult(true, changedState);
            } else {
                jsonResult = jsonResult(false, "状态不用修改");
            }
        } catch (Exception e) {
            jsonResult = jsonResult(false, "状态修改失败");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 显示树.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @GetMapping("/showTree")
    public String showTree(HttpServletRequest request, HttpServletResponse response) {

        List <JGXXTree> trees;
        try {
            trees = jgxxService.getTree();
            String treeData = new Gson().toJson(trees);
            request.setAttribute("tree", treeData);
            return view(webPath,"jgxx_Tree");
        } catch (Exception e) {
            request.setAttribute("msg", "获取组织机构失败！");
            return view(Consts.WEB_PATH,Consts.FAILURE);
        }
    }

}
