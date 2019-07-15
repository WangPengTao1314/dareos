package com.centit.drp.main.firpage.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.centit.common.controller.BaseController;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.model.Result;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.core.utils.SpringContextHolder;
import com.centit.drp.main.firpage.service.DrpFirpageService;
import com.centit.erp.sale.prmtplan.model.PrmtplanModel;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.NoticeService;

/**
 * 
 * @ClassName: DrpFirpageController 
 * @Description: 分销首页action
 * @author: liu_yg
 * @date: 2019年2月28日 下午2:57:45
 */
@Controller
@RequestMapping("/sys/drpFirst")
public class DrpFirpageController extends BaseController {

	/** 权限对象 **/
	/** 维护 */
	// 增删改查
	private static String PVG_BWS = "";
	private static String PVG_EDIT = "";
	private static String PVG_DELETE = "";
	// 启用,停用
	private static String PVG_START_STOP = "";
	// 确认，取消
	private static String PVG_FINISH_CANCLE = "";
	/** end */
	/** 审批维护必须维护字段 **/
	// 提交撤销
	private static String PVG_COMMIT_CANCLE = "";
	private static String PVG_TRACE = "";
	// 审核模块
	private static String PVG_BWS_AUDIT = "";
	private static String PVG_AUDIT_AUDIT = "";
	private static String PVG_TRACE_AUDIT = "";
	// 审批流参数
	private static String AUD_TAB_NAME = "";
	private static String AUD_TAB_KEY = "";
	private static String AUD_BUSS_STATE = "";
	private static String AUD_BUSS_TYPE = "";
	private static String AUD_FLOW_INS = "";

	@Autowired
	private DrpFirpageService drpFirpageService;
	
	private static final String webPath = "drp/main/firpage";

	public String toFrame(
			HttpServletRequest request, HttpServletResponse response) {
		return view("pages/index", "index");
	}
	

	/**
	 * 首页 滚动 促销信息
	 * 
	 * @param userBean
	 * @param request
	 */
	@PostMapping("/queryPrmt")
	public void queryPrmt(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = (UserBean) request.getSession(false).getAttribute(
				"UserBean");
		PrintWriter writer = getWriter(response);

//		List<PrmtplanModel> prmtList = drpFirpageService.queryPrmtpList(userBean, 5);
		String jsonResult = JSONObject.toJSONString(new Result(true, new ArrayList<PrmtplanModel>(), ""));

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}

	/**
	 * 根据 NOTICE_ID 加载公告
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@PostMapping("loadNotice")
	public void loadNotice(
			HttpServletRequest request, HttpServletResponse response) {
		String NOTICE_ID = ParamUtil.get(request, "NOTICE_ID");
		Map<String, Object> model = this.drpFirpageService.loadNoticeModel(NOTICE_ID);
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		if(null != model){
			if(model.get("NOTICE")!=null) {
				jsonResult = jsonResult(true, model.get("NOTICE").toString());
			}
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
		
	}

	/**
	 * 点击 公告 更多信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/toMoreNotice")
	public String toMoreNotice(
			HttpServletRequest request, HttpServletResponse response) {
		// 设置跳转时需要的一些公用属性
		ParamUtil.setCommAttributeEx(request);
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request,
				"paramUrl"));
		return view(webPath, "Notice_Frame");
	}

	/**
	 * 公告detail
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String toNoticeDetail(
			HttpServletRequest request, HttpServletResponse response) {

		String NOTICE_ID = ParamUtil.get(request, "NOTICE_ID");

		if (StringUtils.isNotEmpty(NOTICE_ID)) {
			Map<String, Object> model = this.drpFirpageService
					.loadNoticeModel(NOTICE_ID);
			request.setAttribute("rst", model);
		}

		return view(webPath, "Notice_Detail");
	}

	/**
	 * 公告 更多信息的 列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/listNotice")
	public String listNotice(HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {
			NoticeService noticeService = SpringContextHolder.getBean(NoticeService.class);
			UserBean userBean = (UserBean) request.getSession(false).getAttribute(
				"UserBean");
			Map<String, Object> params = LogicUtil.getParameterMap(request);
			params.put("DELFLAG",BusinessConsts.DEL_FLAG_COMMON);
			String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER();//1.经销商   0.总部
			if(BusinessConsts.INTEGER_1.equals(IS_DRP_LEDGER)){
				params.put("NOTICE_OBJ", "3");
			}else{
				params.put("NOTICE_OBJ", "2");
			}
			params.put("USERID",userBean.getXTYHID());
			//字段排序
			//drpFirpageService.pageQueryNotice(params,pageDesc);
			request.setAttribute("params", params);
			noticeService.pageQuery(params, pageDesc);
			request.setAttribute("page",pageDesc);

		return view(webPath, "Notice_List");
	}

	/**
	 * 促销 更多
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String toMorePrmt(
			HttpServletRequest request, HttpServletResponse response) {
		// 设置跳转时需要的一些公用属性
		ParamUtil.setCommAttributeEx(request);
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request,
				"paramUrl"));
		return view(webPath, "toMorePrmt");
	}

	/**
	 * 促销detail
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
//	public String toPrmtDetail(
//			HttpServletRequest request, HttpServletResponse response) {
//		
//		String PRMT_PLAN_ID = ParamUtil.get(request, "PRMT_PLAN_ID");
//		if (StringUtils.isNotEmpty(PRMT_PLAN_ID)) {
//			Map<String, String> model = this.drpFirpageService
//					.loadPrmtModel(PRMT_PLAN_ID);
//			request.setAttribute("rst", model);
//		}
//
//		return view(webPath, "toPrmtDetail");
//	}

	/**
	 * 促销信息 列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String listPrmt(
			HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {

		UserBean userBean = (UserBean) request.getSession(false).getAttribute(
				"UserBean");
		Map<String,Object> params=LogicUtil.getParameterMap(request);
		params.put("STATE", "'" + BusinessConsts.ISSUANCE + "','"
				+ BusinessConsts.OVER + "'");
		params.put("XTYHID", userBean.getXTYHID());
		params.put("CHANN_ID", userBean.getCHANN_ID());
		
		 drpFirpageService.pageQueryPrmt(params, pageDesc);

		request.setAttribute("params", params);
		request.setAttribute("page", pageDesc);

		return view(webPath, "listPrmt");
	}

	/**
	 * 查询 待发货预订单 待入库 待退货
	 */
	@PostMapping("/queryCount")
	public void queryCount(
			HttpServletRequest request, HttpServletResponse response) {

		UserBean userBean = (UserBean) request.getSession(false).getAttribute(
				"UserBean");

		PrintWriter writer = getWriter(response);

//		List list = this.drpFirpageService.queryCount();
		int prdReturnReq = queryPrdRetuenReqCount(userBean);
		int storeIn = queryStoreInCount(userBean);
		//待发货预订单
		int order = queryOrderCount(userBean);
		Map<String,Object> map = new HashMap<String,Object> ();
		map.put("DRP_ADVC_ORDER", order);
		map.put("DRP_STOREIN_NOTICE", storeIn);
		map.put("DRP_PRD_RETURN_REQ", prdReturnReq);
		
		String jsonResult =JSONObject.toJSONString(new Result(true, map, ""));

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}
	
	
	

	/**
	 * 退货申请单  提交 状态
	 * @param userBean
	 * @return
	 */
	private int queryPrdRetuenReqCount(UserBean userBean){
		Map<String, String> params = new HashMap<String, String>();
		// 审核模块
		String PVG_BWS_AUDIT = "BS0012101";
		String PVG_BWS = "FX0020701";
		// 审批流参数
		String AUD_TAB_NAME = "DRP_PRD_RETURN_REQ";
		String AUD_TAB_KEY = "PRD_RETURN_REQ_ID";
		String AUD_BUSS_STATE = "";
		String AUD_BUSS_TYPE = "DRP_PRD_RETURN_REQ_AUDIT";

		String search = "";
		String module = "wh";
		StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(search, module, PVG_BWS,
				PVG_BWS_AUDIT, AUD_TAB_KEY, AUD_TAB_NAME, AUD_BUSS_TYPE,
				AUD_BUSS_STATE, userBean));
		qx.append(" and STATE='提交' and DEL_FLAG=0 ");
		params.put("QXJBCON", qx.toString());
		int count = this.drpFirpageService.queryPrdRetuenReqCount(params);
		
		return count;
	}
	
	
	/**
	 * 入库通知单  提交 状态
	 * @param userBean
	 * @return
	 */
	private int queryStoreInCount(UserBean userBean){
		Map<String, String> params = new HashMap<String, String>();
		// 审核模块
		String PVG_BWS_AUDIT = "FX0031201";
		String PVG_BWS = "FX0030301";
		// 审批流参数
		String AUD_TAB_NAME = "DRP_STOREIN_NOTICE";
		String AUD_TAB_KEY = "STOREIN_NOTICE_ID";
		String AUD_BUSS_STATE = "";
		String AUD_BUSS_TYPE = "DRP_STOREIN_NOTICE_AUDIT";

		String search = "";
		String module = "wh";

		StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(search, module, PVG_BWS,
				PVG_BWS_AUDIT, AUD_TAB_KEY, AUD_TAB_NAME, AUD_BUSS_TYPE,
				AUD_BUSS_STATE, userBean));
		
		qx.append(" and STATE='提交' and DEL_FLAG=0 and LEDGER_ID='"+userBean.getLoginZTXXID()+"' ");
		params.put("QXJBCON", qx.toString());
		int count = this.drpFirpageService.queryStoreIn(params);
		
		return count;
	}
	
	/**
	 * 查询 待发货预订单   状态 待发货
	 * @return
	 */
	private int queryOrderCount(UserBean userBean){
		Map<String, String> params = new HashMap<String, String>();
		params.put("STATE",BusinessConsts.STATE_WAIT_SEND);//待发货
		params.put("BILL_TYPE",BusinessConsts.TYPE_END_SALE);//终端销售
		params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);//0
		params.put("LEDGER_ID",userBean.getLoginZTXXID());
		
		String PVG_BWS="FX0020901";
	    // 审批流参数
	    String PVG_BWS_AUDIT="";
		String AUD_TAB_NAME = "";
		String AUD_TAB_KEY = "";
		String AUD_BUSS_STATE = "";
		String AUD_BUSS_TYPE = "";
	    
	   //权限级别和审批流的封装
		params.put("QXJBCON", ParamUtil.getPvgCon(null, null, PVG_BWS,
				PVG_BWS_AUDIT, AUD_TAB_KEY, AUD_TAB_NAME, AUD_BUSS_TYPE,
				AUD_BUSS_STATE, userBean));
	    
		
		return this.drpFirpageService.queryOrderCount(params);
	}
	
	/**
	 * 首页 【待办事宜】先判断是否有权限
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@PostMapping("/checkHaveQx")
	public void checkHaveQx(
			HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		//改为页面传递权限
		String QX = ParamUtil.get(request, "QX");
		//入库通知单
		//String STOREIN_PVG_BWS = "FX0030301";
		//预订单
		//String ADVC_PVG_BWS = "FX0020901";
		//退货申请单
		//String PRDRETURNREQ_PVG_BWS = "FX0020701";
		//投诉与建议
		//String PVG_ADVISEINIT_BWS = "FX0040101";
		//订货订单
		//String PVG_GOODSORHD_BWS = "FX0020401";
		 
		int error = 0;
		if (Consts.FUN_CHEK_PVG) {
			if(StringUtil.isEmpty(QX)){
				error = 0;
			}else if(!QXUtil.checkMKQX(userBean, QX)  ){
				error = 1;
			}
		}
		
		if(error == 1){
			jsonResult = jsonResult(true, "对不起，您没有权限");
		}else{
			jsonResult = jsonResult(false, "");
		}
		
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
		
		
	}
	/**
	 * 首页 柱状图
	 */
	@PostMapping("/queryBar")
	public void queryBar(
			HttpServletRequest request, HttpServletResponse response) {

		UserBean userBean = ParamUtil.getUserBean(request);
		PrintWriter writer = getWriter(response);
		List<Map<String,String>> list = drpFirpageService.queryBar(userBean);
		String jsonResult = JSONObject.toJSONString(new Result(true, list, ""));
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}
	
	
	/**
	 * 查找附件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
//	public void downFille(
//			HttpServletRequest request, HttpServletResponse response) {
//		PrintWriter writer = getWriter(response);
//		String jsonResult = jsonResult();
//		try {
//			String noticeId = request.getParameter("NOTICEID");
//			Map<String,String> result = drpFirpageService.queryFilePath(noticeId);
////			jsonResult = new Gson().toJson(new Result(true, result, ""));	 
//			 
//		} catch (Exception e) {
//			jsonResult = jsonResult(false, "");
//			e.printStackTrace();
//		}
//		if (null != writer) {
////			writer.write(jsonResult);
//			writer.close();
//		}
//	}

	/**
	 * 查询发运单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@PostMapping("queryDeliver")
	public void queryDeliver(
			HttpServletRequest request, HttpServletResponse response){
		PrintWriter writer = getWriter(response);
		UserBean userBean = ParamUtil.getUserBean(request);
		String jsonResult = jsonResult();
		try {
			List<Map<String,String>> result = drpFirpageService.queryDeliver(userBean);
			jsonResult = JSONObject.toJSONString(new Result(true, result, ""));	 
			 
		} catch (Exception e) {
			jsonResult = jsonResult(false, "");
			e.printStackTrace();
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * * 设置权限Map
	 * 
	 * @param UserBean
	 *            the userBean
	 * @return Map<String,String>
	 */
	private Map<String, String> setPvg(UserBean userBean) {
		Map<String, String> pvgMap = new HashMap<String, String>();
		pvgMap.put("PVG_BWS", QXUtil.checkPvg(userBean, PVG_BWS));
		pvgMap.put("PVG_EDIT", QXUtil.checkPvg(userBean, PVG_EDIT));
		pvgMap.put("PVG_DELETE", QXUtil.checkPvg(userBean, PVG_DELETE));
		pvgMap.put("PVG_START_STOP", QXUtil.checkPvg(userBean, PVG_START_STOP));
		pvgMap.put("PVG_COMMIT_CANCLE", QXUtil.checkPvg(userBean,
				PVG_COMMIT_CANCLE));
		pvgMap.put("PVG_TRACE", QXUtil.checkPvg(userBean, PVG_TRACE));
		pvgMap.put("PVG_BWS_AUDIT", QXUtil.checkPvg(userBean, PVG_BWS_AUDIT));
		pvgMap.put("PVG_AUDIT_AUDIT", QXUtil
				.checkPvg(userBean, PVG_AUDIT_AUDIT));
		pvgMap.put("PVG_FINISH_CANCLE", QXUtil.checkPvg(userBean,
				PVG_FINISH_CANCLE));
		pvgMap.put("PVG_TRACE_AUDIT", QXUtil
				.checkPvg(userBean, PVG_TRACE_AUDIT));
		pvgMap.put("AUD_TAB_NAME", AUD_TAB_NAME);
		pvgMap.put("AUD_TAB_KEY", AUD_TAB_KEY);
		pvgMap.put("AUD_BUSS_STATE", AUD_BUSS_STATE);
		pvgMap.put("AUD_BUSS_TYPE", AUD_BUSS_TYPE);
		pvgMap.put("AUD_FLOW_INS", AUD_FLOW_INS);
		return pvgMap;
	}


}
