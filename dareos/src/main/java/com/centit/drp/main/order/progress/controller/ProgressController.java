package com.centit.drp.main.order.progress.controller;

import java.io.PrintWriter;
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
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.order.progress.model.ProgressDetModel;
import com.centit.drp.main.order.progress.model.ProgressModel;
import com.centit.drp.main.order.progress.service.ProgressService;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *    经销商订单进度确认
 * @author WangPengTao
 *
 */
@Controller
@RequestMapping("/order/progress")
public class ProgressController extends BaseController {
	
	
	private static final String webPath = "drp/main/order/progress";
	
	@Autowired
	private ProgressService progressService ;
	
	/** 权限对象维护**/
    private static String PVG_QUERY="FX0010101";//查询
    private static String PVG_CONFIRM="FX0010102";//交期确认
    private static String PVG_FEEDBACK="FX0010103";//反馈
    
   
	
	/**
	 * 跳转页面 确认订单进度
	 * 
	 * @param request
	 * @param response 
	 * @return
	 */
	@RequestMapping(value = { "/toFrames" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toFrames(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_QUERY)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
		return view(webPath, "Progress_Frame");
	}

	/**
	 * To list.
	 * 
	 * @param request  the request
	 * @param response the response
	 * @return the action forward
	 */
	@RequestMapping(value = { "/toList" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toList(HttpServletRequest request, HttpServletResponse response, PageDesc pageDesc) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_QUERY)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "SALE_ORDER_NO", params);//发货编号
		ParamUtil.putStr2Map(request, "DELIVERY1", params);//交货日期从
		ParamUtil.putStr2Map(request, "DELIVERY2", params);//交货日期到
		ParamUtil.putStr2Map(request, "STATE", params);//单据状态
		ParamUtil.putStr2Map(request, "pagesize", params);
		
		// 字段排序
		ParamUtil.setOrderField(request, params, "cre_time", "DESC");
		progressService.pageQuery(params, pageDesc);
		request.setAttribute("params", params);
		request.setAttribute("pvg",setPvg(userBean));
		request.setAttribute("page", pageDesc);
		return view(webPath, "Progress_List");
	}

	/**
	 * to modify page.
	 * 
	 * @param request  the request
	 * @param response the response
	 * @return the action forward
	 */
	@RequestMapping(value = { "/toConfirm" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toEdit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_CONFIRM)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		int pageNo = ParamUtil.getInt(request, "pageNo", 1);
		ParamUtil.putStr2Map(request, "order_degree_id", params);// 发货单id
		String order_degree_id = ParamUtil.get(request, "order_degree_id");
		if (StringUtils.isNotEmpty(order_degree_id)) {
			Map<String, Object> entry =progressService.query(params);
			request.setAttribute("entry", entry);
			request.setAttribute("pageNo", pageNo);
		}


		return view(webPath, "Progress_confirm_delivery");
	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/confirm" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void edit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_CONFIRM)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String jsonData = ParamUtil.get(request, "parentJsonData");
		ProgressModel model = LogicUtil.StrToObj(jsonData, ProgressModel.class);
		
		try {
			if(StringUtil.isEmpty(model.getEstimate_delivery_date())) {
				jsonResult = jsonResult(false, "请输入预计交货日期！！！");
			}else {
				progressService.confirm(model,request);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "保存失败");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}
	
	/**
	 * to modify page.
	 * 
	 * @param request  the request
	 * @param response the response
	 * @return the action forward
	 */
	@RequestMapping(value = { "/toFeedback" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toFeedback(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_FEEDBACK)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		int pageNo = ParamUtil.getInt(request, "pageNo", 1);
		ParamUtil.putStr2Map(request, "order_degree_id", params);// 发货单id
		String order_degree_id = ParamUtil.get(request, "order_degree_id");
		if (StringUtils.isNotEmpty(order_degree_id)) {
			Map<String, Object> entry =progressService.query(params);
			request.setAttribute("entry", entry);
			request.setAttribute("pageNo", pageNo);
		}


		return view(webPath, "Progress_feedback");
	}
	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/feedback" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void feedback(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_FEEDBACK)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String jsonData = ParamUtil.get(request, "parentJsonData");
		ProgressModel model =LogicUtil.StrToObj(jsonData, ProgressModel.class);
		//
		String childJsonData = ParamUtil.get(request, "childJsonData");
		
		try {
			List <ProgressDetModel> chldModelList = null;
	        if (!StringUtil.isEmpty(childJsonData)) {
	            chldModelList = new Gson().fromJson(childJsonData, new TypeToken <List <ProgressDetModel>>() {
	            }.getType());
	        }
			progressService.edit(model,chldModelList,request);
		} catch (RuntimeException e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "保存失败");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}
	
	
	
	/**
	 * 发货单明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/toDetail" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String showDetail(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_QUERY)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "order_degree_id", params);
		Map<String, Object> entry = progressService.query(params);
		request.setAttribute("entry", entry);
		return view(webPath, "Progress_Detail");
	}

	   /**
		  * 设置权限Map
		 * @param UserBean the userBean
		 * @return Map<String,String>
		 */
		private Map<String, String> setPvg(UserBean userBean) {
			Map<String, String> pvgMap = new HashMap<String, String>();
			pvgMap.put("PVG_QUERY", QXUtil.checkPvg(userBean, PVG_QUERY));
			pvgMap.put("PVG_CONFIRM", QXUtil.checkPvg(userBean, PVG_CONFIRM));
			pvgMap.put("PVG_FEEDBACK", QXUtil.checkPvg(userBean, PVG_FEEDBACK));
			return pvgMap;
		}
	
	
}
