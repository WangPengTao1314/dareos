/**
  *@module 系统管理
  *@fuc 数据字典页面处理
  *@version 1.1
  *@author 张羽
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.common.controller.BaseController;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.core.po.PageDesc;
import com.centit.sys.model.SJZDModel;
import com.centit.sys.model.SJZDMxModel;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.SJZDService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

// TODO: Auto-generated Javadoc
/**
 * 数据字典管理.
 * 
 * @author zhang_yu
 */
@Controller
@RequestMapping("/sys/sjzd")
public class SJZDController extends BaseController {

	@Autowired
    private SJZDService sjzdService;
	
	private static final String webPath = "sys/sjzd";

    /** The logger. */
    private Logger      logger = Logger.getLogger(SJZDController.class);




    /**
     * 数据字典管理首页.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @GetMapping("/toFrame")
    public String toFrame(HttpServletRequest request, HttpServletResponse response) {

        //设置跳转时需要的一些公用属性
        ParamUtil.setCommAttributeEx(request);
        request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
        return view(webPath,"sjzd_Frame");
    }


    /**
     * 数据字典编辑框架页面.
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

        //设置跳转时需要的一些公用属性
        String chistate = ParamUtil.utf8Decoder(request, "chistate");
        ParamUtil.setCommAttributeEx(request);
        request.setAttribute("chistate", chistate);
        return view(webPath,"sjzd_Edit_Frame");
    }


    /**
     * 数据字典列表
     * Description :.
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

        //查询条件
        Map <String, Object> params = new HashMap <String, Object>();
        ParamUtil.putStr2Map(request, "SJZDBH", params);
        params.put("SJZDMC", ParamUtil.utf8Decoder(request, "SJZDMC"));
        params.put("STATECON", ParamUtil.utf8Decoder(request, "STATECON"));
        System.err.println("STATECON===="+params.get("STATECON"));
        params.put("SJXMC", ParamUtil.utf8Decoder(request, "SJXMC"));
        ParamUtil.putStr2Map(request, "SJXZ", params);
        ParamUtil.putStr2Map(request, "U.REMARK", params);
        //只查询0的记录。1为删除。0为正常
        params.put("DELFLAG", "0");
        //字段排序
        ParamUtil.setOrderField(request, params);
        
        sjzdService.pageQuery(params, pageDesc);

        request.setAttribute("params", params);
        request.setAttribute("page", pageDesc);
        return view(webPath,"sjzd_List");
    }


    /**
     * 数据字典明细列表
     * Description :.
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

        String sjzdID = ParamUtil.get(request, "SJZDID");
        if (StringUtils.isNotEmpty(sjzdID)) {
            List <SJZDMxModel> result = sjzdService.childQuery(sjzdID);
            request.setAttribute("rst", result);
        }
        //为空直接跳转显示页面，而不是错误提示。
        return view(webPath,"sjzd_Mx_List");
    }


    /**
     * 数据字典修改页面跳转
     * Description :.
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

        String selRowId = ParamUtil.get(request, "selRowId");
        //为空则是新增，否则是修改
        if (StringUtils.isNotEmpty(selRowId)) {
            Map <String, String> entry = sjzdService.load(selRowId);
            request.setAttribute("rst", entry);
        }
        return view(webPath,"sjzd_Edit");
    }


    /**
     * 显示记录详细信息
     * Description :.
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

        String sjzdID = ParamUtil.get(request, "SJZDID");
        if (StringUtils.isNotEmpty(sjzdID)) {
            Map <String, String> entry = sjzdService.load(sjzdID);
            List<SJZDMxModel> list=sjzdService.childQuery(sjzdID);
            request.setAttribute("rst", entry);
            request.setAttribute("list", list);
        }
        return view(webPath,"sjzd_Detail");
    }


    /**
     * 数据字典明细编辑跳转页面
     * Description :.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @RequestMapping(value = { "/toChildEdit"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toChildEdit(HttpServletRequest request, HttpServletResponse response) {

        //多个Id批量查询，用逗号隔开
        String sjzdMxIds = request.getParameter("SJZDMXIDS");
        String chistate = ParamUtil.utf8Decoder(request, "chistate");
        //没有零星领料Id可以直接跳转。
        if (StringUtils.isNotEmpty(sjzdMxIds)) {
            List <SJZDMxModel> list = sjzdService.loadChilds(sjzdMxIds);
            request.setAttribute("rst", list);

        }
        request.setAttribute("chistate", chistate);
        return view(webPath,"sjzd_Mx_Edit");
    }


    /**
     * 数据字典编辑页面加载子页面
     * Description :.
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

        String sjzdID = ParamUtil.get(request, "SJZDID");
        String chistate = ParamUtil.utf8Decoder(request, "chistate");
        if (StringUtils.isNotEmpty(sjzdID)) {
            List <SJZDMxModel> result = sjzdService.childQuery(sjzdID);
            request.setAttribute("rst", result);
        }
        request.setAttribute("chistate", chistate);
        return view(webPath,"sjzd_Mx_Edit");
    }


    /**
     * 数据字典删除
     * Description :.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        UserBean userBean = ParamUtil.getUserBean(request);
        String jsonResult = jsonResult();
        try {
            String sjzdID = ParamUtil.get(request, "SJZDID");
            sjzdService.txDelete(sjzdID, userBean);
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
     * 数据字典编辑//新增或修改。
     * Description :.
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
        UserBean userBean = null;
        try {
            userBean = (UserBean) request.getSession(false).getAttribute("UserBean");
        } catch (Exception e) {
            jsonResult = jsonResult(false, "用户已失效");
            return;
        }
        try {
        	String sjzdid = ParamUtil.utf8Decoder(request, "SJZDID");//ParamUtil.get(request, "SJZDID");
            //String sjzdID = ParamUtil.get(request, "SJZDID");
        	String sjzdMxIds = request.getParameter("SJZDMXIDS");
        	if("''".equals(sjzdMxIds)||sjzdMxIds=="''"){
        		sjzdMxIds = "";
            }
            String parentJsonData = ParamUtil.get(request, "parentJsonData");
            SJZDModel sjzdModel = null;
            if (StringUtils.isNotEmpty(parentJsonData)) {
                sjzdModel = new Gson().fromJson(parentJsonData, new TypeToken <SJZDModel>() {
                }.getType());
            }
            if (null == sjzdModel) {
                view(Consts.WEB_PATH,Consts.FAILURE);
                return;
            }

            String jsonDate = ParamUtil.get(request, "childJsonData");
            List <SJZDMxModel> sjzdmxList = null;
            if (StringUtils.isNotEmpty(jsonDate)) {
                sjzdmxList = new Gson().fromJson(jsonDate, new TypeToken <List <SJZDMxModel>>() {
                }.getType());
            }
            int cou = 0;
            if (StringUtils.isEmpty(sjzdid)) {
                cou = sjzdService.getIdCount(sjzdModel.getSJZDBH());
            }
            
            if (cou == 0 ) {
            	int errorFlg = 0;
            	//明细去重复判断
            	if(sjzdmxList != null && sjzdmxList.size()>1){
                	for (int i = 1; i < sjzdmxList.size(); i++) {
                    	if(sjzdmxList.get(0).getSJXZ().equals(sjzdmxList.get(i).getSJXZ())){
                    		jsonResult = jsonResult(false, "数据项值重复");
                    		errorFlg = 1;
                        }
                    	if(sjzdmxList.get(0).getSJXMC().equals(sjzdmxList.get(i).getSJXMC())){
                    		jsonResult = jsonResult(false, "数据项名称重复");
                    		errorFlg = 1;
                        }
                    	if(sjzdmxList.get(0).getKEYCODE().equals(sjzdmxList.get(i).getKEYCODE())){
                    		jsonResult = jsonResult(false, "数据项代码重复");
                    		errorFlg = 1;
                        }
                    }
                }
            	
                
                if(sjzdmxList != null && StringUtils.isNotEmpty(sjzdid)){
                	Map <String, String> paramsMx = new HashMap <String, String>();
                	paramsMx.put("SJZDID", sjzdid);
                	paramsMx.put("SJZDMXIDS", sjzdMxIds);
                	List <SJZDMxModel> checkSjzdxList = sjzdService.getMXBySJZDID( paramsMx);
                	if (checkSjzdxList != null && checkSjzdxList.size() > 0) {
                	    for (int i = 0; i < sjzdmxList.size(); i++) {
                		    SJZDMxModel insertModel = sjzdmxList.get(i);
                			for (int j = 0; j < checkSjzdxList.size(); j++) {
                        		SJZDMxModel dbModel = checkSjzdxList.get(j);
                                if (dbModel.getSJXZ().equals(insertModel.getSJXZ())) {
                                	jsonResult = jsonResult(false, "数据项值重复");
                                	errorFlg = 2;
                                }
                                if (dbModel.getSJXMC().equals(insertModel.getSJXMC())) {
                                	jsonResult = jsonResult(false, "数据项名称重复");
                                	errorFlg = 2;
                                }
                                if (dbModel.getKEYCODE().equals(insertModel.getKEYCODE())) {
                                	jsonResult = jsonResult(false, "数据项代码重复");
                                	errorFlg = 2;
                                }
                			}
                		}
                	}
                }
                if (errorFlg == 0 ) {
                	sjzdService.txEdit(sjzdid, userBean, sjzdModel, sjzdmxList);
                }
            } else {
                jsonResult = jsonResult(false, "数据字典编号已存在，请重新输入");
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
     * 数据字典明细编辑
     * Description :.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/childEdit")
    public void childEdit(HttpServletRequest request, HttpServletResponse response) {

        SJZDModel sjzdModel = new SJZDModel();
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        int errorFlg = 0;
        try {
        	String sjzdid = ParamUtil.utf8Decoder(request, "SJZDID");//ParamUtil.get(request, "SJZDID");
            //String sjzdID = ParamUtil.get(request, "SJZDID");
        	String sjzdMxIds = request.getParameter("SJZDMXIDS");
        	if("''".equals(sjzdMxIds)||sjzdMxIds=="''"){
        		sjzdMxIds = "";
            }
            //sjzdModel.setSJZDID(sjzdid);
            //sjzdModel.setSJZDBH(sjzdid);
            String jsonDate = request.getParameter("childJsonData");
            if (StringUtils.isNotEmpty(jsonDate)) {
                List <SJZDMxModel> modelList = new Gson().fromJson(jsonDate, new TypeToken <List <SJZDMxModel>>() {
                }.getType());
                
                //明细去重复判断
                if(modelList != null && modelList.size()>1){
            		for (int i = 1; i < modelList.size(); i++) {
            			if(modelList.get(0).getSJXZ().equals(modelList.get(i).getSJXZ())){
                			jsonResult = jsonResult(false, "数据项值重复");
                			errorFlg = 1;
                        }
                		if(modelList.get(0).getSJXMC().equals(modelList.get(i).getSJXMC())){
                			jsonResult = jsonResult(false, "数据项名称重复");
                			errorFlg = 1;
                        }
                		if(modelList.get(0).getKEYCODE().equals(modelList.get(i).getKEYCODE())){
                			jsonResult = jsonResult(false, "数据项代码重复");
                			errorFlg = 1;
                        }
                	}
            	}
                if(modelList != null && StringUtils.isNotEmpty(sjzdid)){
                	Map <String, String> paramsMx = new HashMap <String, String>();
                	paramsMx.put("SJZDID", sjzdid);
                	paramsMx.put("SJZDMXIDS", sjzdMxIds);
                	List <SJZDMxModel> checkSjzdxList = sjzdService.getMXBySJZDID( paramsMx);
                	if (checkSjzdxList != null && checkSjzdxList.size() > 0) {
                	    for (int i = 0; i < modelList.size(); i++) {
                		    SJZDMxModel insertModel = modelList.get(i);
                			for (int j = 0; j < checkSjzdxList.size(); j++) {
                        		SJZDMxModel dbModel = checkSjzdxList.get(j);
                        		if (dbModel.getSJXZ().equals(insertModel.getSJXZ())) {
                                	jsonResult = jsonResult(false, "数据项值重复");
                                	errorFlg = 2;
                                }
                                if (dbModel.getSJXMC().equals(insertModel.getSJXMC())) {
                                	jsonResult = jsonResult(false, "数据项名称重复");
                                	errorFlg = 2;
                                }
                                if (dbModel.getKEYCODE().equals(insertModel.getKEYCODE())) {
                                	jsonResult = jsonResult(false, "数据项代码重复");
                                	errorFlg = 2;
                                }
                			}
                		}
                	}
                }
                
                if (errorFlg == 0 ) {
                	sjzdService.txChildEdit(sjzdid, modelList, sjzdModel);
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
     * 数据字典明细批量删除
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
            String sjzdMxIds = request.getParameter("SJZDMXIDS");
            //批量删除，多个Id之间用逗号隔开
            sjzdService.txBatch4DeleteChild(sjzdMxIds, userBean);
        } catch (Exception e) {
            jsonResult = jsonResult(false, "删除失败");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 状态的变更.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/stateChange")
    public void stateChange(HttpServletRequest request, HttpServletResponse response) {

        logger.info("stateChange 开始");
        String sjzdId = ParamUtil.get(request, "SJZDID");
        String jsonResult = jsonResult();
        try {
            if (StringUtils.isNotEmpty(sjzdId)) {
                Map <String, String> entry = sjzdService.load(sjzdId);
                if(entry==null)
                {
                    jsonResult = jsonResult(false, "保存失败,传入的ID不合法!");	
                }else
                {
                String state = entry.get("STATE");
                Map <String, String> params = new HashMap <String, String>();
                params.put("SJZDID", sjzdId);
                String button = ParamUtil.get(request, "BUTTON");
                //"启用"状态,可修改为停用状态
                if (button.equals("stop")) {
                    params.put("STATE", BusinessConsts.JC_STATE_DISENABLE);
                    sjzdService.txUpdateById(params);

                    // 利用json传递修改后的状态,方便修改list画面上的状态值
                    jsonResult = jsonResult(true, BusinessConsts.JC_STATE_DISENABLE);
                } else if (button.equals("start")) {

                    // "制定","停用"状态,可修改为启用状态
                    params.put("STATE", BusinessConsts.JC_STATE_ENABLE);
                    sjzdService.txUpdateById(params);

                    // 利用json传递修改后的状态,方便修改list画面上的状态值
                    jsonResult = jsonResult(true, BusinessConsts.JC_STATE_ENABLE);
                } else {
                    jsonResult = jsonResult(true, state);
                }
               }
            }
        } catch (Exception e) {
            jsonResult = jsonResult(false, "保存失败");
            e.printStackTrace();
        }
        PrintWriter writer = getWriter(response);
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
        logger.info("stateChange 结束");
    }

}
