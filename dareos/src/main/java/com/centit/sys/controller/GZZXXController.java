/**
 * 项目名称：平台
 * 系统名：权限管理
 * 文件名：GZZXXAction.java
 */
package com.centit.sys.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.common.controller.BaseController;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.ParamUtil;
import com.centit.core.po.PageDesc;
import com.centit.sys.model.GZZCYBean;
import com.centit.sys.model.GZZXXBean;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.GZZXXService;

// TODO: Auto-generated Javadoc
/**
 * The Class GZZXXAction.
 * 
 * @module 系统管理
 * @func 工作组信息
 * @version 1.1
 * @author 吴亚林
 */
@Controller
@RequestMapping("/sys/gzzxx")
public class GZZXXController extends BaseController {

    /** The gzzxx service. */
	@Autowired
    private GZZXXService gzzxxService;
	
	private static final String webPath = "/sys/gzzxx";
    /**
     * Sets the gzzxx service.
     * 
     * @param gzzxxService the new gzzxx service
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
	@RequestMapping(value="/toFrames",method= {RequestMethod.GET,RequestMethod.POST})
    public String toFrames( HttpServletRequest request, HttpServletResponse response) {
    	ParamUtil.setCommAttributeEx(request);
    	request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));    
        return view(webPath,"gzzxx_Frame");
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
    public void delete( HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        String GZZXXID = ParamUtil.get(request, "GZZXXID");
        UserBean userBean = ParamUtil.getUserBean(request);
        if (StringUtils.isNotEmpty(GZZXXID)) {
            try {
                gzzxxService.txDelete(GZZXXID, userBean);
            } catch (RuntimeException e) {
                e.printStackTrace();
                jsonResult = jsonResult(false, "删除失败");
            }
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
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
    public String toList( HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {

        Map params = new HashMap();
        ParamUtil.putStr2Map(request, "GZZBH", params);
        //ParamUtil.putStr2Map(request, "GZZMC", params);
        params.put("GZZMC", ParamUtil.utf8Decoder(request, "GZZMC"));
        //ParamUtil.putStr2Map(request, "GZZZT", params);
        params.put("GZZZT", ParamUtil.utf8Decoder(request, "GZZZT"));
        ParamUtil.putStr2Map(request, "YHBH", params);
        //ParamUtil.putStr2Map(request, "YHM", params);
        params.put("YHM", ParamUtil.utf8Decoder(request, "YHM"));
        params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
        //字段排序
        ParamUtil.setOrderField(request, params);
        int pageNo = ParamUtil.getInt(request, "pageNo", 1);
        ParamUtil.putStr2Map(request, "pageSize", params);
        gzzxxService.pageQuery(params, pageDesc);
        request.setAttribute("params", params);
        request.setAttribute("page", pageDesc);
        return view(webPath,"gzzxx_List");
    }


    /**
     * 工作组子表信息.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @RequestMapping("/childList")
    public String childList( HttpServletRequest request, HttpServletResponse response) {

        String GZZXXID = ParamUtil.get(request, "GZZXXID");
        if (StringUtils.isNotEmpty(GZZXXID)) {
            List <GZZCYBean> result = gzzxxService.childQuery(GZZXXID);

            request.setAttribute("rst", result);
        }

        return view(webPath,"gzzxx_Mx_List");
    }


    /**
     * 工作组明细.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/toDetail")
    public String toDetail( HttpServletRequest request, HttpServletResponse response) {

        String GZZXXID = ParamUtil.get(request, "GZZXXID");
        if (StringUtils.isNotEmpty(GZZXXID)) {

            Map <String, String> entry = gzzxxService.load(GZZXXID);
            request.setAttribute("rst", entry);
        }
        return view(webPath,"gzzxx_Detail");
    }


    /**
     * 编辑.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @RequestMapping("/toEditFrame")
    public String toEditFrame( HttpServletRequest request, HttpServletResponse response) {
    	
        // 设置跳转时需要的一些公用属性
        ParamUtil.setCommAttributeEx(request);
        return view(webPath,"gzzxx_Edit_Frame");
    }


    /**
     * 主表编辑.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/toParentEdit")
    public String toParentEdit( HttpServletRequest request, HttpServletResponse response) {

        String GZZXXID = ParamUtil.get(request, "selRowId");
        if (StringUtils.isNotEmpty(GZZXXID)) {
            Map <String, String> entry = gzzxxService.load(GZZXXID);
            request.setAttribute("rst", entry);
            request.setAttribute("isNew", true);
        } else {
            request.setAttribute("isNew", false);
        }
        return view(webPath,"gzzxx_Edit");
    }


    /**
     * 子表编辑.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @RequestMapping("/modifyToChildEdit")
    public String modifyToChildEdit( HttpServletRequest request, HttpServletResponse response) {

        String GZZXXID = ParamUtil.get(request, "GZZXXID");
        if (StringUtils.isNotEmpty(GZZXXID)) {
            List <GZZCYBean> result = gzzxxService.childQuery(GZZXXID);

            request.setAttribute("rst", result);
        }
        return view(webPath,"gzzxx_Mx_Edit");
    }


    /**
     * 工作组成员删除.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @RequestMapping("/childDelete")
    public String childDelete( HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        UserBean userBean = ParamUtil.getUserBean(request);
        String GZZCYID = request.getParameter("GZZCYID");
        try {
            gzzxxService.txBatch4DeleteChild(GZZCYID, userBean);
        } catch (RuntimeException e) {
            jsonResult = jsonResult(false, "删除失败");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
        return view(webPath,"gzzxx_Mx_Edit");
    }


    /**
     * 编辑.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @RequestMapping("/edit")
    public void edit( HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        try {
        	UserBean userBean = ParamUtil.getUserBean(request);
            // 主表数据
            String parentJsonData = ParamUtil.get(request, "parentJsonData");
            // 子表数据
            String jsonDate = ParamUtil.get(request, "childJsonData");

            GZZXXBean bean = null;
            if (StringUtils.isNotEmpty(parentJsonData)) {
                bean = new Gson().fromJson(parentJsonData, new TypeToken <GZZXXBean>() {
                }.getType());
            }

            String GZZXXID = ParamUtil.get(request, "GZZXXID");
            if (StringUtils.isEmpty(GZZXXID) && (gzzxxService.getGzzxxExits(bean.getGZZBH())) > 0) {
                jsonResult = jsonResult(false, "工作组编号已经存在");
            } else {
                List <GZZCYBean> list = null;

                if (StringUtils.isNotEmpty(jsonDate)) {
                    list = new Gson().fromJson(jsonDate, new TypeToken <List <GZZCYBean>>() {
                    }.getType());
                }
                HashMap retMap = this.checkXtyhid(list, GZZXXID);
                if (retMap.get("ret").toString() == "ok" || "ok".equals(retMap.get("ret").toString())) {
                    gzzxxService.txEdit(GZZXXID, bean, userBean, list);
                } else {
                    jsonResult = jsonResult(false, "工作组成员[" + retMap.get("xtyhid").toString() + "]重复");
                }
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
            jsonResult = jsonResult(false, "保存失败");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 启用/停用.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/changeState")
    public void changeState( HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        try {
            boolean isChanged = false;
            String GZZXXID = request.getParameter("GZZXXID");

            Map <String, String> entry = gzzxxService.load(GZZXXID);
            String state = entry.get("GZZZT");

            Map <String, String> params = new HashMap <String, String>();
            params.put("GZZXXID", GZZXXID);
            String changedState = "";
            if (BusinessConsts.JC_STATE_ENABLE.equals(state)) {
                changedState = BusinessConsts.JC_STATE_DISENABLE;
                params.put("GZZZT", BusinessConsts.JC_STATE_DISENABLE);
                gzzxxService.txUpdateById(params);
                isChanged = true;
            }
            //deleted by wang yehua 
            //else if (Consts.JC_STATE_DISENABLE.equals(state)) {
            // changedState = Consts.JC_STATE_ENABLE;
            //params.put("GZZZT", Consts.JC_STATE_ENABLE);
            //gzzxxService.txUpdateById(params);
            //isChanged = true;
            //} 
            else {
                changedState = BusinessConsts.JC_STATE_ENABLE;
                params.put("GZZZT", BusinessConsts.JC_STATE_ENABLE);
                gzzxxService.txUpdateById(params);
                isChanged = true;
            }
            if (isChanged) {
                jsonResult = jsonResult(true, changedState);
            } else {
                jsonResult = jsonResult(false, "状态不用修改");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            jsonResult = jsonResult(false, "状态修改失败");
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 明细编辑.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @RequestMapping("/toChildEdit")
    public String toChildEdit( HttpServletRequest request, HttpServletResponse response) {

        String GZZCYID = ParamUtil.get(request, "GZZCYID");
        List <GZZCYBean> list = null;
        if (StringUtils.isNotEmpty(GZZCYID)) {

            list = gzzxxService.loadChilds(GZZCYID);

            request.setAttribute("rst", list);
        }
        return view(webPath,"gzzxx_Mx_Edit");
    }


    /**
     * 子表编辑.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @RequestMapping("/childEdit")
    public void childEdit( HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
        	UserBean userBean = ParamUtil.getUserBean(request);

            String GZZXXID = ParamUtil.get(request, "GZZXXID");

            String jsonDate = request.getParameter("childJsonData");
            if (StringUtils.isNotEmpty(jsonDate)) {
                List <GZZCYBean> modelList = new Gson().fromJson(jsonDate, new TypeToken <List <GZZCYBean>>() {
                }.getType());

                HashMap retMap = this.checkXtyhid(modelList, GZZXXID);
                if (retMap.get("ret").toString() == "ok" || "ok".equals(retMap.get("ret").toString())) {
                    gzzxxService.txChildEdit(GZZXXID, modelList, userBean);
                } else {
                    jsonResult = jsonResult(false, "工作组成员[" + retMap.get("xtyhid").toString() + "]重复");
                }

            }
        } catch (Exception e) {
            jsonResult = jsonResult(false, "保存失败");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 明细中要做“用户编号” 唯一性校验.
     * 
     * @param modelList the model list
     * @param GZZXXID the gZZXXID
     * 
     * @return the hash map
     */
    @SuppressWarnings( {"unchecked", "unused"})
    @RequestMapping("/checkXtyhid")
    private HashMap checkXtyhid(List <GZZCYBean> modelList, String GZZXXID) {

        String ret = "ok";
        HashMap <String, String> retMap = new HashMap <String, String>();
        String xtyhid = "";

        try {
            String gzzcyid = null;

            StringBuffer gzzcyids = new StringBuffer();

            List <Map <String, String>> paramList = new ArrayList <Map <String, String>>();
            Map <String, String> paramMap = null;
            if (modelList != null && modelList.size() > 0) {
                for (GZZCYBean gzzCyBean : modelList) {
                    paramMap = new HashMap <String, String>();
                    gzzcyid = gzzCyBean.getGZZCYID();
                    if (gzzcyid != null && !"".equals(gzzcyid)) {
                        gzzcyids.append("','").append(gzzcyid);
                    }
                    xtyhid = gzzCyBean.getXTYHID();
                    paramMap.put("XTYHID", xtyhid);
                    paramList.add(paramMap);
                }
                gzzcyids.append("'");
                //把要修改的数据的明细数据过滤掉 ，用剩下的不修改数据与此次要保持的数据比较
                Map <String, String> param = new HashMap <String, String>();
                param.put("GZZXXID", GZZXXID);
                //保存时的数据中有修改的数据时
                if (!gzzcyids.toString().equals("'")) {
                    param.put("GZZCYIDS", gzzcyids.toString().substring(2));
                }
                //判断页面内与界面数据是否重复
                if (GZZXXID != null && !"".equals(GZZXXID)) {
                    List <GZZCYBean> result = gzzxxService.checkYHBH(param);
                    		//gzzxxService.findList("CZZCY.checkYHBH", param);
                    for (int i = 0; i < modelList.size(); i++) {
                        GZZCYBean webModel = modelList.get(i);
                        for (int j = 0; j < result.size(); j++) {
                            GZZCYBean tempModel = result.get(j);
                            if (tempModel.getXTYHID().equals(webModel.getXTYHID())) {
                                xtyhid = tempModel.getXTYHID();
                                ret = "error";
                                break;
                            }
                        }
                    }
                }
                if (ret != "error") {
                    //判断页面内明细表数据是否重复
                    for (int i = 0; i < modelList.size(); i++) {
                        GZZCYBean webModel = modelList.get(i);
                        for (int k = i + 1; k < modelList.size(); k++) {
                            GZZCYBean webModel2 = modelList.get(k);
                            if (webModel2.getXTYHID().equals(webModel.getXTYHID())) {
                                xtyhid = webModel.getXTYHID();
                                ret = "error";
                                break;
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            ret = "error";
            e.printStackTrace();
        }
        retMap.put("ret", ret);
        retMap.put("xtyhid", xtyhid);

        return retMap;
    }
}
