/**

 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ChannAction.java
 */
package com.centit.erp.sale.erpprmtprice.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.common.controller.BaseController;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.erp.sale.erpprmtprice.model.ErpprmtpriceModel;
import com.centit.erp.sale.erpprmtprice.service.ErpprmtpriceService;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * The Class ChannAction.
 * 
 * @module 系统管理
 * @func 渠道惩罚设定
 * @version 1.0
 * @author 刘曰刚
 */
@Controller
@RequestMapping("/erp/erpprmtprice")
public class ErpprmtpriceController extends BaseController {
	/** The chann Service*/
	@Autowired
	private ErpprmtpriceService erpprmtpriceService;
	
	private static final String webPath = "erp/sale/erpprmtprice";
	

	/** 权限对象**/
	/** 维护*/
    //增删改查
    private static String PVG_BWS="BS0011901";
    private static String PVG_EDIT="BS0011902";
    private static String PVG_DELETE="BS0011903";
    //启用,停用
    private static String PVG_START_STOP="BS0011904";
    
	/**
	 * 渠道信息框架
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
    @GetMapping("/toFrames")
	public String toFrames(HttpServletRequest request, HttpServletResponse response){
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_BWS))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
		return view(webPath, "Erpprmtprice_Frame");
	}
    
    @GetMapping("/toSel")
   	public String toSel(HttpServletRequest request, HttpServletResponse response){
   		UserBean userBean =  ParamUtil.getUserBean(request);
   		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_BWS))
       	{
       		throw new ServiceException("对不起，您没有权限 !");
       	}
   		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
   		return view(webPath, "Erpprmtprice_Sel");
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
    	UserBean userBean =  ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_BWS))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("PRMT_PLAN_NO", ParamUtil.utf8Decoder(request, "PRMT_PLAN_NO"));
    	params.put("PRMT_PLAN_NAME", ParamUtil.utf8Decoder(request, "PRMT_PLAN_NAME"));
    	params.put("STATE", ParamUtil.utf8Decoder(request, "STATE"));
    	params.put("PAR_PRD_NAME", StringUtil.creCon("PAR_PRD_NAME", ParamUtil.utf8Decoder(request, "PAR_PRD_NAME"), ","));
    	params.put("PRD_NO", StringUtil.creCon("PRD_NO", ParamUtil.utf8Decoder(request, "PRD_NO"), ","));
    	params.put("PRD_NAME", StringUtil.creCon("PRD_NAME", ParamUtil.utf8Decoder(request, "PRD_NAME"), ","));
    	params.put("PRD_GROUP_NAME", StringUtil.creCon("a.PRD_GROUP_NAME", ParamUtil.utf8Decoder(request, "PRD_GROUP_NAME"), ","));
    	String PRMT_PLAN_ID=ParamUtil.utf8Decoder(request, "PRMT_PLAN_ID");
    	String set=ParamUtil.utf8Decoder(request, "set");
    	String STATE=ParamUtil.utf8Decoder(request, "STATE");
    	
    	if(StringUtil.isEmpty(PRMT_PLAN_ID)){
    		params.put("seachFlag", "1<>1");
    	}
    	if("已设置".equals(set)){
    		params.put("priceFlag", " 1=1 ");
    		params.put("prdFlag", " 1<>1 ");
		}else if("未设置".equals(set)){
			params.put("priceFlag", " 1<>1 ");
    		params.put("prdFlag", " 1=1 ");
		}
    	if("启用".equals(STATE)){
    		params.put("prdFlag", " 1<>1 ");
    	}
    	//只查询0的记录。1为删除。0为正常
		params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		params.put("COMM_FLAG", BusinessConsts.YJLBJ_FLAG_TRUE);
//		String search = ParamUtil.get(request,"search");
//		String module = ParamUtil.get(request,"module");
		//权限级别和审批流的封装以及状态的封装
//		params.put("QXJBCON", ParamUtil.getPvgCon(search,module,PVG_BWS,userBean));
    	// 字段排序
		ParamUtil.setOrderField(request, params, "PRD_NO", "DESC");
    	ParamUtil.putStr2Map(request, "pageSize", params);
    	erpprmtpriceService.pageQuery(params, pageDesc);
    	request.setAttribute("params", params);
    	request.setAttribute("pvg",setPvg(userBean));
        request.setAttribute("page", pageDesc);
        request.setAttribute("ZTXXID", userBean.getLoginZTXXID());
        return view(webPath, "Erpprmtprice_List");
    }
    /**
     * 保存促销价格
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
    @PostMapping("/edit")
	public void edit(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		try {
			String jsonDate = ParamUtil.get(request, "jsonData");
			String PRMT_PLAN_ID=ParamUtil.get(request, "PRMT_PLAN_ID");
			List<ErpprmtpriceModel> ModelList = null;
			if (!StringUtil.isEmpty(jsonDate)) {
				ModelList = new Gson().fromJson(jsonDate,
						new TypeToken<List<ErpprmtpriceModel>>() {
						}.getType());
				erpprmtpriceService.txInsertPrice(ModelList,PRMT_PLAN_ID,userBean);
			}
			
		} catch (ServiceException s) {
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
	/**
	 * 启用/停用
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
    @PostMapping("/updateState")
	public void updateState(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_START_STOP))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		try {
			String PRMT_PRICE_IDS = ParamUtil.get(request, "PRMT_PRICE_IDS");
			String STATE=ParamUtil.get(request, "STATE");
			erpprmtpriceService.txUpdateState(PRMT_PRICE_IDS,STATE,userBean);
		} catch (ServiceException s) {
			jsonResult = jsonResult(false, s.getMessage());
		} catch (Exception e) {
			jsonResult = jsonResult(false, "操作失败");
			e.printStackTrace();
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

    /**
	 * * 设置权限Map
	 * @param UserBean the userBean
	 * @return Map<String,String>
	 */
	 private Map<String,String> setPvg(UserBean userBean)
	    {
	    	Map<String,String>pvgMap=new HashMap<String,String>();
	    	pvgMap.put("PVG_BWS",QXUtil.checkPvg(userBean, PVG_BWS));
	    	pvgMap.put("PVG_EDIT",QXUtil.checkPvg(userBean, PVG_EDIT));
	    	pvgMap.put("PVG_DELETE",QXUtil.checkPvg(userBean, PVG_DELETE)  );
	    	pvgMap.put("PVG_START_STOP",QXUtil.checkPvg(userBean, PVG_START_STOP) );
	    	return  pvgMap;
	   }
}
