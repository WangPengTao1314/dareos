/**

 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ChannAction.java
 */
package com.centit.base.brand.controller;

import java.io.PrintWriter;
import java.util.HashMap;
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

import com.centit.base.brand.model.BrandModel;
import com.centit.base.brand.service.BrandService;
import com.centit.common.controller.BaseController;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.core.po.PageDesc;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



/**
 * The Class ChannAction.
 * 
 * @module 系统管理
 * @func 品牌信息
 * @version 1.0
 */
@Controller
@RequestMapping("/base/brand")
public class BrandController extends BaseController {
	@Autowired
	private BrandService brandService;
	
	private Logger logger = Logger.getLogger(BrandController.class);
	/** 权限对象维护**/
    //增删改查
    private static String PVG_BWS="XT0012201";
    private static String PVG_EDIT="XT0012202";
    private static String PVG_DELETE="XT0012203";
    //启用,停用
    private static String PVG_START_STOP="XT0012204";
    //确认，取消
    private static String PVG_FINISH_CANCLE="";
    private static String AUD_BUSS_STATE="STATE";
    /**权限 end*/
    
    /**审批维护必须维护字段**/
    //提交撤销
    private static String PVG_COMMIT_CANCLE="";
    private static String PVG_TRACE="";
    //审核模块
    private static String PVG_BWS_AUDIT="";
    private static String PVG_AUDIT_AUDIT="";
    private static String PVG_TRACE_AUDIT="";
    //审批流参数
    private static String AUD_TAB_NAME="";
    private static String AUD_TAB_KEY="";
    private static String AUD_BUSS_TYPE="";
    private static String AUD_FLOW_INS="";
    
    private static final String webPath = "base/brand";
	/**
	 * 品牌信息框架页面
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
    @GetMapping("/toFrame")
	public String toFrame(HttpServletRequest request, HttpServletResponse response){
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_BWS))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		//设置跳转时需要的一些公用属性
    	ParamUtil.setCommAttributeEx(request);
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
		logger.info("toFrame in");
		return view(webPath,"Brand_Frame");
	}
	
	/**
     * 查询结果列表.
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@RequestMapping(value = { "/toList"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toList( HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_BWS)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
    	Map<String, Object> params = new HashMap<String, Object>();
    	//品牌ID
        params.put("BRAND_ID", ParamUtil.utf8Decoder(request, "BRAND_ID"));	
        //品牌
        params.put("BRAND", ParamUtil.utf8Decoder(request, "BRAND"));
        //英文名称
        params.put("BRAND_EN", ParamUtil.utf8Decoder(request, "BRAND_EN"));	
        //制单时间从
        params.put("CRE_TIME_FROM", ParamUtil.utf8Decoder(request, "CRE_TIME_FROM"));
        //制单时间到
        params.put("CRE_TIME_TO", ParamUtil.utf8Decoder(request, "CRE_TIME_TO"));
    	//状态
        params.put("STATE", ParamUtil.utf8Decoder(request, "STATE"));				
        //只查询0的记录。1为删除。0为正常
		params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
        /*String search = ParamUtil.get(request,"search");
		String module = ParamUtil.get(request,"module");*/
		// 字段排序
		ParamUtil.setOrderField(request, params, "u.CRE_TIME", "DESC");
		//权限级别和审批流的封装以及状态的封装
		//params.put("QXJBCON", ParamUtil.getPvgCon(search,module,PVG_BWS,PVG_BWS_AUDIT,AUD_TAB_KEY, AUD_TAB_NAME,AUD_BUSS_TYPE,AUD_BUSS_STATE,userBean));
		brandService.pageQuery(params, pageDesc);
    	request.setAttribute("params", params);
    	request.setAttribute("pvg",setPvg(userBean));
        request.setAttribute("page", pageDesc);
        return view(webPath,"Brand_List");
    }
    
    /**
     * 查看人员详细信息.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     */
	@GetMapping("/toDetail")
    public String toDetail( HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean =  ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_BWS)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
    	String BRAND_ID = ParamUtil.get(request, "BRAND_ID");
        Map<String, Object> entry = brandService.loadById(BRAND_ID);
        request.setAttribute("rst", entry);
        return view(webPath,"Brand_Detail");
    }
    /**
     * 点击新增或修改按钮跳转到编辑页面.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@GetMapping("/toEdit")
    public String toEdit( HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean =  ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        String BRAND_ID = ParamUtil.get(request, "BRAND_ID");
        if (StringUtils.isNotEmpty(BRAND_ID)) {
            Map<String,Object> entry = brandService.loadById(BRAND_ID);
            request.setAttribute("rst", entry);
        }
        return view(webPath,"Brand_Edit");
    }
    /**
     * 编辑.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
	@PostMapping("/edit")
    public void edit( HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean,PVG_EDIT)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
    	try {
    		String BRAND_ID = ParamUtil.get(request, "BRAND_ID");
            String jsonData = ParamUtil.get(request, "jsonData");
            BrandModel model = null;
            if (StringUtils.isNotEmpty(jsonData)) {
                model = new Gson().fromJson(jsonData, new TypeToken <BrandModel>() {
                }.getType());
                
                //新增的时候
                if(StringUtils.isEmpty(BRAND_ID)){
                	Map<String,Object> Map = brandService.loadById(model.getBRAND_ID().trim());
                    if(null != Map && !Map.isEmpty()){
                    	throw new ServiceException("品牌ID重复，请重新输入");
                    }
                }
                
    			brandService.txEdit(BRAND_ID, model, userBean); 
            }
        }catch(ServiceException e){
        	jsonResult = jsonResult(false, e.getMessage());
        }catch (RuntimeException e) {
            jsonResult = jsonResult(false, "保存失败");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
    /**
     * 删除
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
	@PostMapping("/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response){
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_DELETE)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        String BRAND_ID = ParamUtil.get(request, "BRAND_ID");

        if (StringUtils.isNotEmpty(BRAND_ID)) {
            try {
                brandService.delete(BRAND_ID, userBean);
            } catch (RuntimeException e) {
                jsonResult = jsonResult(false, "删除失败");
            }
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
    /**
     * 修改状态为停用.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
	@PostMapping("/changeStateStop")
    public void changeStateStop(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, PVG_START_STOP)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
        	String BRAND_ID = request.getParameter("BRAND_ID");
            Map <String, Object> params = new HashMap <String, Object>();
            params.put("BRAND_ID", BRAND_ID);
            params.put("UPDATOR", userBean.getXTYHID());
            params.put("UPD_NAME", userBean.getXM());
            params.put("STATE", BusinessConsts.JC_STATE_DISENABLE);
            brandService.txUpdateById(params);
            jsonResult = jsonResult(true, "状态修改成功!");
        } catch (Exception e) {
            jsonResult = jsonResult(false, "状态修改失败!");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
	
	/**
     * 修改状态为启用.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
	@PostMapping("/changeStateStart")
    public void changeStateStart(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, PVG_START_STOP)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
        	String BRAND_ID = request.getParameter("BRAND_ID");
            Map <String, Object> params = new HashMap <String, Object>();
            params.put("BRAND_ID", BRAND_ID);
            params.put("UPDATOR", userBean.getXTYHID());
            params.put("UPD_NAME", userBean.getXM());
            params.put("STATE", BusinessConsts.JC_STATE_ENABLE);
            brandService.txUpdateById(params);
            jsonResult = jsonResult(true, "状态修改成功!");
        } catch (Exception e) {
            jsonResult = jsonResult(false, "状态修改失败!");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
	
	/**
	 * 设置权限Map
	 * @param UserBean the userBean
	 * @return Map<String,String>
	 */
	private Map<String, String> setPvg(UserBean userBean) {
		Map<String, String> pvgMap = new HashMap<String, String>();
		pvgMap.put("PVG_BWS", QXUtil.checkPvg(userBean, PVG_BWS));
		pvgMap.put("PVG_EDIT", QXUtil.checkPvg(userBean, PVG_EDIT));
		pvgMap.put("PVG_DELETE", QXUtil.checkPvg(userBean, PVG_DELETE));
		pvgMap.put("PVG_START_STOP", QXUtil.checkPvg(userBean, PVG_START_STOP));
		pvgMap.put("PVG_COMMIT_CANCLE",QXUtil.checkPvg(userBean, PVG_COMMIT_CANCLE));
		pvgMap.put("PVG_TRACE", QXUtil.checkPvg(userBean, PVG_TRACE));
		pvgMap.put("PVG_BWS_AUDIT", QXUtil.checkPvg(userBean, PVG_BWS_AUDIT));
		pvgMap.put("PVG_AUDIT_AUDIT",QXUtil.checkPvg(userBean, PVG_AUDIT_AUDIT));
		pvgMap.put("PVG_FINISH_CANCLE",QXUtil.checkPvg(userBean, PVG_FINISH_CANCLE));
		pvgMap.put("PVG_TRACE_AUDIT",QXUtil.checkPvg(userBean, PVG_TRACE_AUDIT));
		pvgMap.put("AUD_TAB_NAME", AUD_TAB_NAME);
		pvgMap.put("AUD_TAB_KEY", AUD_TAB_KEY);
		pvgMap.put("AUD_BUSS_STATE", AUD_BUSS_STATE);
		pvgMap.put("AUD_BUSS_TYPE", AUD_BUSS_TYPE);
		pvgMap.put("AUD_FLOW_INS", AUD_FLOW_INS);
		return pvgMap;
	}

}
