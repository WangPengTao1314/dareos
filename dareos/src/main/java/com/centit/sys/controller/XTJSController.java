/**

 * 项目名称：平台

 * 系统名：基础数据

 * 文件名：XTJSAction.java

 */
package com.centit.sys.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.common.controller.BaseController;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.ParamUtil;
import com.centit.core.po.PageDesc;
import com.centit.sys.model.UserBean;
import com.centit.sys.model.XTJSBean;
import com.centit.sys.model.XTYHJSBean;
import com.centit.sys.service.XTJSService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/sys/xtjs")
public class XTJSController extends BaseController {

    //系统角色Service变量
    /** The xtjs service. */
	@Autowired
    private XTJSService xtjsService;
	
	private static final String webPath = "sys/xtjs";





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

        ParamUtil.setCommAttributeEx(request);
        request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
        return view(webPath, "xtjs_Frame");
    }


    /**
     * 主从表一起保存.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
	@PostMapping("/edit")
    public void edit(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        String status = ParamUtil.get(request, "XTJSID");
        UserBean userBean = null;
        try {
            userBean = (UserBean) request.getSession(false).getAttribute("UserBean");
        } catch (Exception e) {
            jsonResult = jsonResult(false, "用户已失效");
            return;
        }
        int flag = 0;
        String jsonDateP = ParamUtil.get(request, "parentJsonData");
        XTJSBean xtjs = null;
        if (StringUtils.isNotEmpty(jsonDateP)) {
            xtjs = new Gson().fromJson(jsonDateP, new TypeToken <XTJSBean>() {
            }.getType());
                        if (StringUtils.isEmpty(status)) {
                            String jsbh = xtjs.getJSBH();
                            //判断是否有重复角色编号
                            List <XTJSBean> bhList = xtjsService.getBHList();
                            for (int i = 0; i < bhList.size(); i++) {
                                if (jsbh.equals(bhList.get(i).getJSBH())) {
                                    flag = 1;
                                }
                            }
                        }
        }

        try {
            String jsonDateC = ParamUtil.get(request, "childJsonData");
            List <XTYHJSBean> xtyhjs = null;
            if (StringUtils.isNotEmpty(jsonDateC)) {
                xtyhjs = new Gson().fromJson(jsonDateC, new TypeToken <List <XTYHJSBean>>() {
                }.getType());
            }
            if (flag == 1) {
                jsonResult = jsonResult(false, "角色编号重复，请重新输入");
            } else {
            	boolean chkFlag=true;
                //判断用户编号是否有重复值
                if(xtyhjs!=null)
                {
                for (int i = 0; i < xtyhjs.size() - 1; i++) {
                    String temp = xtyhjs.get(i).getYHBH();
                    if (temp.equals(xtyhjs.get(i + 1).getYHBH())) {
                    	jsonResult = jsonResult(false, "明细用户编号重复！");
                    	chkFlag=false;
                    	break;
                    }
                  }
                }
                //判断数据库是否有重复的用户编号
                //List <XTYHJSBean> bhList = getYHBHList(xtjs.getJSBH());
                if(chkFlag)
                {
                List <XTYHJSBean> bhList = xtjsService.getYHBHList(status);
                
                if(xtyhjs!=null)
                {
                for (int q = 0; q < xtyhjs.size(); q++) {
                    if ("".equals(xtyhjs.get(q).getXTYHJSID())) {
                        for (int j = 0; j < bhList.size(); j++) {
                            if (xtyhjs.get(q).getYHBH().equals(bhList.get(j).getXTYHID())) {
                            	jsonResult = jsonResult(false, " 用户编号重复!");
                            	chkFlag=false;
                            	break;
                            }
                        }
                    }
                }
                }
                
                
                }
                if(chkFlag)
                {
            	if (!(xtjsService.txEdit(status, userBean, xtjs, xtyhjs))) {
                    jsonResult = jsonResult(false, "保存失败");
                }
                }
            	
            }

        } catch (Exception e) {
            jsonResult = jsonResult(false, "保存失败");
            e.printStackTrace();
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
	@GetMapping("/toDetail")
    public String toDetail(HttpServletRequest request, HttpServletResponse response) {

        String xtjsID = ParamUtil.get(request, "XTJSID");

        Map <String, String> entry = xtjsService.load(xtjsID);
        request.setAttribute("rst", entry);

        return view(webPath, "xtjs_Detail");
    }


    /**
     * 删除.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
	@PostMapping("/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        UserBean userBean = ParamUtil.getUserBean(request);
        try {
            String xtjsID = ParamUtil.get(request, "JSBH");
            xtjsService.txDelete(xtjsID, userBean);
        } catch (Exception e) {
            jsonResult = jsonResult(false, "删除失败");
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
    public String toList(HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {

        Map <String, Object> params = new HashMap <String, Object>();
        ParamUtil.putStr2Map(request, "JSBH", params);
        params.put("JSMC", ParamUtil.utf8Decoder(request, "JSMC"));
        params.put("YHZT", ParamUtil.utf8Decoder(request, "YHZT"));
        params.put("XM", ParamUtil.utf8Decoder(request, "XM"));
        ParamUtil.putStr2Map(request, "YHBH", params);
        params.put("YHM", ParamUtil.utf8Decoder(request, "YHM"));
        //未删除的
        params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);

        //字段排序
        ParamUtil.setOrderField(request, params);
        ParamUtil.putStr2Map(request, "pageSize", params);
        xtjsService.pageQuery(params, pageDesc);

        request.setAttribute("params", params);
        request.setAttribute("page", pageDesc);
        return view(webPath, "xtjs_List");
    }


    /**
     * Child list.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @RequestMapping(value = { "/childList"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String childList(HttpServletRequest request, HttpServletResponse response) {

        String XTJSID = ParamUtil.get(request, "XTJSID");
        if (StringUtils.isNotEmpty(XTJSID)) {
            List <XTYHJSBean> result = xtjsService.childQuery(XTJSID);
            request.setAttribute("rst", result);
        }
        return view(webPath, "xtjs_Mx_List");
    }


    /**
     * To edit frame.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @GetMapping("/toEditFrame")
    public String toEditFrame(HttpServletRequest request, HttpServletResponse response) {

        ParamUtil.setCommAttributeEx(request);
        return view(webPath, "xtjs_Edit_Frame");
    }


    /**
     * To parent edit.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @GetMapping("/toParentEdit")
    public String toParentEdit(HttpServletRequest request, HttpServletResponse response) {

        String status = "add";
        String selRowId = ParamUtil.get(request, "selRowId");
        //为空则是新增，否则是修改
        if (StringUtils.isNotEmpty(selRowId)) {
            Map <String, String> entry = xtjsService.load(selRowId);
            status = "modify";
            request.setAttribute("rst", entry);
        }
        request.setAttribute("status", status);
        return view(webPath, "xtjs_Edit");

    }


    /**
     * 主从表编缉.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @GetMapping("/modifyToChildEdit")
    public String modifyToChildEdit(HttpServletRequest request, HttpServletResponse response) {

        String xtjsId = ParamUtil.get(request, "XTJSID");
        if (StringUtils.isNotEmpty(xtjsId)) {

            List <XTYHJSBean> result = xtjsService.childQuery(xtjsId);
            request.setAttribute("rst", result);
        }

        return view(webPath, "xtjs_Mx_Edit");
    }


    /**
     * 子表保存.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/childEdit")
    public void childEdit(HttpServletRequest request, HttpServletResponse response) {

        XTJSBean xtjs = new XTJSBean();
        List <XTYHJSBean> subList = null;
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        String xtjsid = ParamUtil.get(request, "XTJSID");
        String jsonData = request.getParameter("childJsonData");
        if (StringUtils.isNotEmpty(jsonData)) {
            subList = new Gson().fromJson(jsonData, new TypeToken <List <XTYHJSBean>>() {
            }.getType());
            if (xtjsService.txChildEdit(xtjsid, xtjs, subList)) {
                request.setAttribute("msg", "保存成功");
            } else {
                jsonResult = jsonResult(false, "保存失败,用户编号有重复值");
            }
        } else {
            jsonResult = jsonResult(false, "保存失败");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 跳转到从表编缉页面前需要设置的值.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @PostMapping("/toChildEdit")
    public String toChildEdit(HttpServletRequest request, HttpServletResponse response) {

        String status = "";
        //多个Id批量查询，用逗号隔开
        String xtjsids = request.getParameter("XTYHJSIDS");

        if (StringUtils.isNotEmpty(xtjsids)) {
            List <XTYHJSBean> list = xtjsService.loadChilds(xtjsids);
            status = "modify";
            request.setAttribute("rst", list);
        }
        request.setAttribute("status", status);
        return view(webPath, "xtjs_Mx_Edit");
    }


    /**
     * 明细表批量删除
     * Description :.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/childDelete")
    public void childDelete(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        UserBean userBean = ParamUtil.getUserBean(request);
        String jsonResult = jsonResult();
        try {
            String XTJSYHID = request.getParameter("XTJSYHID");
            //批量删除，多个Id之间用逗号隔开
            xtjsService.txBatch4DeleteChild(XTJSYHID, userBean);
        } catch (Exception e) {
            jsonResult = jsonResult(false, "删除失败");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
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
    @PostMapping("/updateJSStatus")
    public void updateJSStatus(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        String flag = ParamUtil.get(request, "flag");
        Map <String, String> params = new HashMap <String, String>();
        ParamUtil.putStr2Map(request, "xtjsid", params);
        if ("1".equals(flag)) {
            params.put("STATE", BusinessConsts.JC_STATE_ENABLE);
        } else {
            params.put("STATE", BusinessConsts.JC_STATE_DISENABLE);
        }
        try {
            xtjsService.updateJSStatus(params);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = jsonResult(false, "更新失败");
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }

}
