/**
 * prjName:喜临门营销平台
 * ucName:订货订单维护
 * fileName:GoodsorderhdAction
 */
package com.centit.drp.sale.goodsorderhd.controller;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centit.base.chann.model.DeliveraddrModelChld;
import com.centit.base.chann.service.ChannService;
import com.centit.common.controller.BaseController;
import com.centit.common.service.FlowService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.model.Result;
import com.centit.commons.util.DateUtil;
import com.centit.commons.util.ExcelUtil;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.sale.goodsorderhd.model.GoodsorderhdModel;
import com.centit.drp.sale.goodsorderhd.model.GoodsorderhdModelChld;
import com.centit.drp.sale.goodsorderhd.service.GoodsorderhdService;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.XTYHService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * *@serviceImpl
 * *@func 分销-订货单管理
 */
@Controller
@RequestMapping("/drp/goodsorderhd")
public class GoodsorderhdController extends BaseController {
	/**日志**/
	private Logger logger = Logger.getLogger(GoodsorderhdController.class);

	private static final String webPath="drp/sale/goodsorderhd";
	/** 权限对象**/
	/** 维护*/
	//增删改查
	private static String PVG_BWS="FX0020301";
	private static String PVG_EDIT="FX0020302";
	private static String PVG_DELETE="FX0020303";
	// 申请修改
	private static String PVG_APPLY="FX0020304";
	// 报价确认
	private static String PVG_QUOTE_CONFIRM="FX0020305";
	// 售后反馈
	private static String PVG_FEEDBACK="FX0020306";
	//启用,停用
	private static String PVG_START_STOP="";
	//确认，取消
	private static String PVG_FINISH_CANCLE="";
	/** end*/

	/**下级订货单处理**/
	private static String PVG_BWS_SUB="FX0020401";
	// 报价
	private static String PVG_QUOTE_SUB="FX0020402";
	// 转总部订单
	private static String PVG_EDIT_SUB="FX0020403";
	// 退回
	private static String PVG_BACK_SUB="FX0020404";
	/** end*/

	/**审批维护必须维护字段**/
	//提交撤销
	private static String PVG_COMMIT_CANCLE="FX0020307";
	private static String PVG_TRACE="?";
	//审核模块
	private static String PVG_BWS_AUDIT="BS0010301";
	private static String PVG_TRACE_AUDIT="?";
	// 审图
	private static String PVG_AUDIT_AUDIT="BS0010304";
	// 报价
	private static String PVG_QUOTE_AUDIT="BS0010305";
	// 领导确认
	private static String PVG_CONFIRM_AUDIT="BS0010306";
	// 生成销售订单
	private static String PVG_CREXSDD_AUDIT="BS0010307";
	// 退回
	private static String PVG_BACK_AUDIT="BS0010308";

	//预订单处理新增/修改
	private static String PVG_EDIT_AUDIT="BS0010302";
	//预订单处理删除
	private static String PVG_DELETE_AUDIT="BS0010303";
	//预订单处理提交
	private static String PVG_COMMIT_AUDIT="BS0010308";


	/** 业务service*/
	@Autowired
	private GoodsorderhdService goodsorderhdService;

	@Autowired
	private FlowService flowService;
	@Autowired
	private ChannService channService;
	@Autowired
	private XTYHService xtyhService;

	/**
	 * 订货管理框架页面
	 */
	@RequestMapping(value="/toFrame",method= {RequestMethod.POST,RequestMethod.GET})
	public String toFrame(
			HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS))
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS_SUB))
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT)))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		ParamUtil.setCommAttributeEx(request);
		String CHANN_ID = userBean.getCHANN_ID(); // 经销商id
		String paramUrl = ParamUtil.utf8Decoder(request, "paramUrl");
		String doCommitSave = ParamUtil.get(request,"doCommitSave");
		String flag = ParamUtil.get(request,"flag");
		String channel = ParamUtil.get(request,"channel");
		if(!StringUtil.isEmpty(channel)){
			request.setAttribute("channel",channel );
		}
		request.setAttribute("CHANN_ID",CHANN_ID );
		request.setAttribute("paramUrl",paramUrl );
		request.setAttribute("doCommitSave",doCommitSave);
		request.setAttribute("flag",flag);
		request.setAttribute("HEAD", ParamUtil.utf8Decoder(request, "HEAD"));
		// 除去设计师权限还有其他权限则>0
		request.setAttribute("isShowPrice", LogicUtil.isShowPrice(userBean.getXTJSIDS()));
		return view(webPath,"Goodsorderhd_Frame");
	}

	/**
	 * * query List data
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping("/toList")
	public String toList(HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS))
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT)))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		Map<String,Object> params = new HashMap<String,Object>();
		String module = ParamUtil.get(request,"module");
		String GOODS_ORDER_ID = ParamUtil.get(request,"selRowId");
		String CHANN_ID = userBean.getCHANN_ID(); // 登录的经销商id
		String channel = ParamUtil.get(request,"channel"); // 标识
		if(channel.contains("?")) {
			channel = channel.substring(0, channel.indexOf("?"));//截取?之前的字符串
		}
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商，   0：总部
		ParamUtil.putStr2Map(request, "GOODS_ORDER_NO", params);
		ParamUtil.putStr2Map(request, "ORDER_CHANN_NO", params);
		ParamUtil.putStr2Map(request, "ORDER_CHANN_NAME", params);
		ParamUtil.putStr2Map(request, "RECV_CHANN_NO", params);
		ParamUtil.putStr2Map(request, "RECV_CHANN_NAME", params);
		ParamUtil.putStr2Map(request, "CHANN_NAME", params);
		ParamUtil.putStr2Map(request, "CUST_NAME", params);
		ParamUtil.putStr2Map(request, "CUST_ADDR", params);
		ParamUtil.putStr2Map(request, "CRE_TIME_BEGIN", params);
		ParamUtil.putStr2Map(request, "CRE_TIME_END", params);
		ParamUtil.putStr2Map(request, "BILL_TYPE", params);
		ParamUtil.putStr2Map(request, "ADVC_ORDER_NO", params);
		ParamUtil.putStr2Map(request, "PRD_NO", params);
		ParamUtil.putStr2Map(request, "PRD_NAME", params);
		ParamUtil.putStr2Map(request, "ORDER_DATE_beg", params);
		ParamUtil.putStr2Map(request, "ORDER_DATE_end", params);
		ParamUtil.putStr2Map(request, "ORDER_RECV_DATE_beg", params);
		ParamUtil.putStr2Map(request, "ORDER_RECV_DATE_end", params);

		String ADVC_ORDER_NO=ParamUtil.get(request,"ADVC_ORDER_NO");
		params.put("ADVC_ORDER_NOQuery", StringUtil.creCon("c.ADVC_ORDER_NO", ADVC_ORDER_NO, ","));
		String PRD_NOS=ParamUtil.get(request,"PRD_NO");
		params.put("PRD_NOQuery", StringUtil.creCon("PRD_NO", PRD_NOS, ","));
		String PRD_NAMES=ParamUtil.get(request,"PRD_NAME");
		params.put("PRD_NAMEQuery", StringUtil.creCon("PRD_NAME", PRD_NAMES, ","));
		//只查询0的记录。1为删除。0为正常
		params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);

		String search = ParamUtil.get(request,"search");
		String STATE = ParamUtil.get(request,"STATE");
		String flag = ParamUtil.get(request,"flag");

		/*if("reback".equals(flag)){
			params.put("reback","u.STATE in ('总部退回','区域服务中心退回') ");
		}

		if("isSend".equals(flag)){
			params.put("isSend","isSend");
		}*/

		if(StringUtil.isEmpty(search) && !StringUtil.isEmpty(GOODS_ORDER_ID) && -1 !=GOODS_ORDER_ID.indexOf("_")){
			params.put("GOODS_ORDER_ID",GOODS_ORDER_ID);//订货订单生命跟踪 跳转
		}
		if(IS_DRP_LEDGER.equals("0")){ // 订单中心 订货处理
			params.put("CHANN_IDS", userBean.getCHANNS_CHARG());
			params.put("CHANN_ID_P","is null");
			// 待处理单据
			//params.put("STATES", "'"+BusinessConsts.GOODSORDER_STATE_STZ+"','"+BusinessConsts.GOODSORDER_STATE_STZ_BJZ+"','"+BusinessConsts.GOODSORDER_STATE_LDDQR+"','"+BusinessConsts.GOODSORDER_STATE_YSH+"'");
			if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_EDIT_AUDIT))) {
				params.put("NOTSTATE", BusinessConsts.GOODSORDER_STATE_CG);
			}
			if(StringUtil.isEmpty(search)){
				// 默认查询审图中、报价中状态
				//params.put("STATE",BusinessConsts.GOODSORDER_STATE_STZ);
				if (!QXUtil.checkMKQX(userBean, PVG_AUDIT_AUDIT)) {
					// 没有审图权限
					params.put("NOTSTATE", BusinessConsts.GOODSORDER_STATE_STZ);
				} else if (!QXUtil.checkMKQX(userBean, PVG_QUOTE_AUDIT)) {
					// 没有报价权限
					params.put("NOTSTATE", BusinessConsts.GOODSORDER_STATE_BJZ);
				}
				//初始化查询状态默认显示
				String STATES = LogicUtil.getOrderQxByUserBean(userBean, PVG_BWS_AUDIT);
				//System.err.println(STATES);
				params.put("STATES", STATES);
			} else {
				if(StringUtil.isEmpty(STATE)){
					// params.put("STATES", "'"+BusinessConsts.GOODSORDER_STATE_STZ+"','"+BusinessConsts.GOODSORDER_STATE_STZ_BJZ+"','"+BusinessConsts.GOODSORDER_STATE_LDDQR+"','"+BusinessConsts.GOODSORDER_STATE_YSH+"'");
				} else {
					params.put("STATE",STATE);
				}
			}/*
			String QXJBCON = QXUtil.getQXTJ(userBean, PVG_BWS_AUDIT);
			params.put("QXJBCON", QXJBCON);*/
		} else if(IS_DRP_LEDGER.equals("1")){ // 经销商订货管理
			params.put("CHANN_ID",CHANN_ID);
			if (StringUtil.isEmpty(search)) {
				// 默认查询草稿、退回状态
				//params.put("STATE",BusinessConsts.GOODSORDER_STATE_CG);
				// 草稿和退回
				params.put("STATES", "'"+BusinessConsts.GOODSORDER_STATE_CG+"','"
										+BusinessConsts._BACK+"','"
										+BusinessConsts.GOODSORDER_STATE_DZZB+"','"
										+BusinessConsts.GOODSORDER_STATE_YZZB+"','"
										+BusinessConsts.COMPLETED+"',"
										+BusinessConsts.GOODSORDER_STATE_BJDQR+"'");

				//初始化查询状态默认显示
				String STATES = LogicUtil.getOrderQxByUserBean(userBean, PVG_BWS);
				//System.err.println(STATES);
				params.put("STATES", STATES);
			} else {
				if(!StringUtil.isEmpty(STATE)){
					params.put("STATE",STATE);
				}
			}
			// 下级经销商IS_DRP_LEDGER改为2
			if (!StringUtil.isEmpty(userBean.getCHANN_ID_P())) {
				IS_DRP_LEDGER=BusinessConsts.INTEGER_2;
			}
			// 门店设计师只能查看自己的单据
			String QXJBCON = QXUtil.getQXTJ(userBean, PVG_BWS);
			params.put("QXJBCON", QXJBCON);
		}

		//权限级别和审批流的封装
		/*
		StringBuffer sb = new StringBuffer(ParamUtil.getPvgCon(search,module,PVG_BWS,"","", "","",null,userBean));
		params.put("QXJBCON", sb.toString());
		sb.append(" and u.LEDGER_ID ='").append(userBean.getLoginZTXXID()).append("'");
		params.put("STATE", STATE);*/
		//字段排序
		ParamUtil.setOrderField(request, params, "u.RETURN_SHOW_FLAG desc, u.UPD_TIME desc", "");
		ParamUtil.putStr2Map(request, "pageSize", params);
		goodsorderhdService.pageQuery(params, pageDesc);

		request.setAttribute("module", module);
		request.setAttribute("params", params);
		request.setAttribute("pvg",setPvg(userBean));
		request.setAttribute("page", pageDesc);
		/*
		Map<String,Object> entry = new HashMap<String,Object>();
		//用户所在渠道信息
		Map<String,Object> chann = goodsorderhdService.loadChann(userBean.getCHANN_ID());
		entry.put("INI_CREDIT", chann.get("INI_CREDIT"));//初始信用
		entry.put("REBATE_TOTAL", chann.get("REBATE_TOTAL"));//返利总额
		entry.put("REBATE_CURRT", chann.get("REBATE_CURRT"));//当前返利
//		entry.put("CREDIT_TOTAL", chann.get("CREDIT_TOTAL"));//信用总额
		entry.put("TEMP_CREDIT_REQ", chann.get("TEMP_CREDIT_REQ"));//临时信用
		entry.put("CREDIT_CURRT", chann.get("CREDIT_CURRT"));//当前信用
		entry.put("FREEZE_CREDIT", chann.get("FREEZE_CREDIT"));//冻结信用
		entry.put("PAYMENT_CREDIT", chann.get("PAYMENT_CREDIT"));//付款凭证信用
		entry.put("CHANN_NO", chann.get("CHANN_NO"));
		entry.put("CHANN_NAME", chann.get("CHANN_NAME"));

		//获取返利折扣
		Map<String,Object> result = LogicUtil.getChannRebateDsct(userBean.getCHANN_ID());
		if(null != result){
			request.setAttribute("REBATEDSCT", result.get("DECT_RATE"));//返利折扣
			request.setAttribute("REBATE_TOTAL", result.get("REBATE_TOTAL"));//返利总金额
			request.setAttribute("REBATE_CURRT", result.get("REBATE_CURRT"));//当前返利
			request.setAttribute("REBATE_FREEZE", result.get("REBATE_FREEZE"));//冻结的返利
		}*/
		//查询可用信用  add by zzb 2014-09-23
		//		if("1".equals(Consts.IS_NO_DELIVER_PLAN_FLAG)){
		//			String userCredit = LogicUtil.getU9CurrCredit(userBean.getCHANN_NO());
		//			request.setAttribute("userCredit", userCredit);
		//		}
		request.setAttribute("channel", channel);
		//request.setAttribute("rst", entry);
		request.setAttribute("flag", flag);
		request.setAttribute("ZTXXID", userBean.getLoginZTXXID());
		request.setAttribute("BASE_CHANN_ID", userBean.getBASE_CHANN_ID());
		request.setAttribute("IS_DRP_LEDGER", IS_DRP_LEDGER);

		return view(webPath,"Goodsorderhd_List");

	}
	/**
	 * 下级订货处理
	 * @param request
	 * @param response
	 * @param pageDesc
	 * @return
	 */
	@RequestMapping("/toListJunior")
	public String toListJunior(HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS_SUB)))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		String GOODS_ORDER_ID = ParamUtil.get(request, "selRowId");
		String CHANN_ID = userBean.getCHANN_ID(); // 登录的经销商id
		String channel = ParamUtil.get(request, "channel"); // 标识
		ParamUtil.putStr2Map(request, "GOODS_ORDER_NO", params);
		ParamUtil.putStr2Map(request, "ORDER_CHANN_NO", params);
		ParamUtil.putStr2Map(request, "ORDER_CHANN_NAME", params);
		ParamUtil.putStr2Map(request, "RECV_CHANN_NO", params);
		ParamUtil.putStr2Map(request, "RECV_CHANN_NAME", params);
		ParamUtil.putStr2Map(request, "CRE_TIME_BEGIN", params);
		ParamUtil.putStr2Map(request, "CRE_TIME_END", params);
		ParamUtil.putStr2Map(request, "BILL_TYPE", params);
		ParamUtil.putStr2Map(request, "ADVC_ORDER_NO", params);
		ParamUtil.putStr2Map(request, "PRD_NO", params);
		ParamUtil.putStr2Map(request, "PRD_NAME", params);
		ParamUtil.putStr2Map(request, "ORDER_DATE_beg", params);
		ParamUtil.putStr2Map(request, "ORDER_DATE_end", params);
		ParamUtil.putStr2Map(request, "ORDER_RECV_DATE_beg", params);
		ParamUtil.putStr2Map(request, "ORDER_RECV_DATE_end", params);

		String ADVC_ORDER_NO=ParamUtil.get(request,"ADVC_ORDER_NO");
		params.put("ADVC_ORDER_NOQuery", StringUtil.creCon("c.ADVC_ORDER_NO", ADVC_ORDER_NO, ","));
		String PRD_NOS=ParamUtil.get(request,"PRD_NO");
		params.put("PRD_NOQuery", StringUtil.creCon("PRD_NO", PRD_NOS, ","));
		String PRD_NAMES=ParamUtil.get(request,"PRD_NAME");
		params.put("PRD_NAMEQuery", StringUtil.creCon("PRD_NAME", PRD_NAMES, ","));
		//只查询0的记录。1为删除。0为正常
		params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);

		String STATE = ParamUtil.get(request,"STATE");
		String search = ParamUtil.get(request,"search");
		String flag = ParamUtil.get(request,"flag");

		if(StringUtil.isEmpty(search) && !StringUtil.isEmpty(GOODS_ORDER_ID) && -1 !=GOODS_ORDER_ID.indexOf("_")){
			params.put("GOODS_ORDER_ID",GOODS_ORDER_ID);//订货订单生命跟踪 跳转
		}
		// 下级订货处理  上级经销商是当前登录人的经销商id
		params.put("CHANN_ID_P"," = '"+CHANN_ID+"'");
		params.put("STATES", "'"+BusinessConsts._BACK+"','"
				+BusinessConsts.GOODSORDER_STATE_DZZB+"','"
				+BusinessConsts.GOODSORDER_STATE_YZZB+"','"
				+BusinessConsts.COMPLETED+"'");
		if(StringUtil.isEmpty(search)){
			params.put("STATE",BusinessConsts.GOODSORDER_STATE_DZZB);
			//初始化查询状态默认显示
			String STATES = LogicUtil.getOrderQxByUserBean(userBean, PVG_EDIT_SUB);
			//System.err.println(STATES);
			params.put("STATES", STATES);
		} else {
			if(StringUtil.isEmpty(STATE)){
			} else {
				params.put("STATE",STATE);
			}
		}

		//字段排序
		ParamUtil.setOrderField(request, params, "u.RETURN_SHOW_FLAG desc, u.UPD_TIME desc", "");
		ParamUtil.putStr2Map(request, "pageSize", params);
		goodsorderhdService.pageQuery(params, pageDesc);

		request.setAttribute("params", params);
		request.setAttribute("pvg",setPvg(userBean));
		request.setAttribute("page", pageDesc);

		Map<String,Object> entry = new HashMap<String,Object>();
		//用户所在渠道信息
		/*
		Map<String,Object> chann = goodsorderhdService.loadChann(userBean.getCHANN_ID());
		entry.put("INI_CREDIT", chann.get("INI_CREDIT"));//初始信用
		entry.put("REBATE_TOTAL", chann.get("REBATE_TOTAL"));//返利总额
		entry.put("REBATE_CURRT", chann.get("REBATE_CURRT"));//当前返利
//		entry.put("CREDIT_TOTAL", chann.get("CREDIT_TOTAL"));//信用总额
		entry.put("TEMP_CREDIT_REQ", chann.get("TEMP_CREDIT_REQ"));//临时信用
		entry.put("CREDIT_CURRT", chann.get("CREDIT_CURRT"));//当前信用
		entry.put("FREEZE_CREDIT", chann.get("FREEZE_CREDIT"));//冻结信用
		entry.put("PAYMENT_CREDIT", chann.get("PAYMENT_CREDIT"));//付款凭证信用
		entry.put("CHANN_NO", chann.get("CHANN_NO"));
		entry.put("CHANN_NAME", chann.get("CHANN_NAME"));*/

		//获取返利折扣
		Map<String,Object> result = LogicUtil.getChannRebateDsct(userBean.getCHANN_ID());
		if(null != result){
			request.setAttribute("REBATEDSCT", result.get("DECT_RATE"));//返利折扣
			request.setAttribute("REBATE_TOTAL", result.get("REBATE_TOTAL"));//返利总金额
			request.setAttribute("REBATE_CURRT", result.get("REBATE_CURRT"));//当前返利
			request.setAttribute("REBATE_FREEZE", result.get("REBATE_FREEZE"));//冻结的返利
		}
		//查询可用信用  add by zzb 2014-09-23
		//		if("1".equals(Consts.IS_NO_DELIVER_PLAN_FLAG)){
		//			String userCredit = LogicUtil.getU9CurrCredit(userBean.getCHANN_NO());
		//			request.setAttribute("userCredit", userCredit);
		//		}
		request.setAttribute("channel", channel);
		request.setAttribute("rst", entry);
		request.setAttribute("flag", flag);
		request.setAttribute("ZTXXID", userBean.getLoginZTXXID());
		request.setAttribute("BASE_CHANN_ID", userBean.getBASE_CHANN_ID());
		return view(webPath,"Goodsorderhd_Dealwithjunior");
	}

	/**
	 * * 明细列表
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping("/childList")
	public String childList(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS))
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS_SUB))
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT)))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		String GOODS_ORDER_ID =ParamUtil.get(request, "GOODS_ORDER_ID");
		Map<String,String> param=goodsorderhdService.queryTotal(GOODS_ORDER_ID);
		if(!StringUtil.isEmpty(GOODS_ORDER_ID)) {
			List <Map<String, String>> result = goodsorderhdService.childQuery(GOODS_ORDER_ID);
			request.setAttribute("rst", result);
		}
		request.setAttribute("pvg",setPvg(userBean));
		request.setAttribute("CHANN_ID", userBean.getCHANN_ID());
		request.setAttribute("totalInfo", param);
		return view(webPath,"Goodsorderhd_List_Chld");
	}


	/**
	 * * to 主表编辑页面初始化
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping("/toEdit")
	public String toParentEdit(HttpServletRequest request, HttpServletResponse response){
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)
				&& !QXUtil.checkMKQX(userBean, PVG_EDIT_SUB))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		String selRowId = ParamUtil.get(request, "selRowId");
		String erjiOrderId = ParamUtil.get(request, "erjiOrderId"); // 下级订货处理 转总部订单
		String option = ParamUtil.get(request, "option"); // 总部审图、报价
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商，   0：总部
		//用户所在渠道信息
		Map<String,String> chann = goodsorderhdService.getChann(userBean.getCHANN_ID());
		Map<String,Object> entry = new HashMap<String,Object>();

		if(!StringUtil.isEmpty(selRowId)){
			entry = goodsorderhdService.load(selRowId);
			// 主表附件
			String attPath = getAttPath(selRowId);
			entry.put("attPath", attPath); //附件图纸 可能有多个
			// 查询明细数据
			modifyToChildEdit(selRowId, option, request);

			// 下级订货处理 转总部订单
			if (!StringUtil.isEmpty(erjiOrderId)) {
				selRowId = "";
				entry.put("GOODS_ORDER_ID", "");
				entry.put("GOODS_ORDER_NO", "");
				entry.put("FROM_BILL_ID", erjiOrderId);

				entry.put("SALE_ID", userBean.getXTYHID());
				entry.put("SALE_NAME", userBean.getXM());
				entry.put("SALEDEPT_NAME", userBean.getBMMC());
				entry.put("SALEDEPT_ID", userBean.getBMXXID());

				entry.put("ORDER_CHANN_ID", userBean.getCHANN_ID());
				entry.put("ORDER_CHANN_NO", userBean.getCHANN_NO());
				entry.put("ORDER_CHANN_NAME", chann.get("CHANN_ABBR"));
				entry.put("CHANN_ID", userBean.getCHANN_ID());
				entry.put("CHANN_NO", userBean.getCHANN_NO());
				entry.put("CHANN_NAME", userBean.getCHANN_NAME());
				entry.put("CHANN_ID_P", "");
				entry.put("CHANN_NO_P", "");
				entry.put("CHANN_NAME_P", "");
			}
		}else{

			entry.put("ORDER_CHANN_ID", userBean.getCHANN_ID());
			entry.put("ORDER_CHANN_NO", userBean.getCHANN_NO());
			entry.put("ORDER_CHANN_NAME", chann.get("CHANN_ABBR"));
			entry.put("RECV_CHANN_ID", userBean.getCHANN_ID());
			entry.put("RECV_CHANN_NO", userBean.getCHANN_NO());
			entry.put("RECV_CHANN_NAME", chann.get("CHANN_ABBR"));
			//entry.put("PERSON_CON", chann.get("PERSON_CON"));
			//entry.put("TEL", chann.get("TEL"));
			//entry.put("RECV_ADDR", chann.get("ADDRESS"));
			entry.put("PRINT_NAME", chann.get("CHANN_ABBR"));
			entry.put("SALE_ID", userBean.getXTYHID());
			entry.put("SALE_NAME", userBean.getXM());
			entry.put("SALEDEPT_NAME", userBean.getBMMC());
			entry.put("SALEDEPT_ID", userBean.getBMXXID());
			entry.put("CHANN_ID", userBean.getCHANN_ID());
			entry.put("CHANN_NO", userBean.getCHANN_NO());
			entry.put("CHANN_NAME", userBean.getCHANN_NAME());

			entry.put("BILL_TYPE", "常规订单");
			entry.put("TRANSPORT", "汽运");
			if(IS_DRP_LEDGER.equals("0")){ // 订单中心 订货处理

			} else if(IS_DRP_LEDGER.equals("1")){ // 经销商订货管理
				String CHANN_ID = userBean.getCHANN_ID();
				// 分管账套唯一则默认带出
				List<Map<String,String>> result = channService.getLedgerChrgList(CHANN_ID);
				if (null != result && result.size() == 1) {
					String LEDGER_ID = result.get(0).get("LEDGER_ID");
					entry.put("LEDGER_ID", LEDGER_ID);
				}
				//收货地址唯一则默认带出
				Map<String,String> params = new HashMap<String,String>();
				params.put("CHANN_ID", CHANN_ID);
				params.put("STATE", BusinessConsts.JC_STATE_ENABLE);
				List <DeliveraddrModelChld> addrList = channService.childsQuery(params);
				if (null != addrList && addrList.size() == 1) {
					DeliveraddrModelChld addr = addrList.get(0);
					entry.put("PERSON_CON", addr.getPERSON_CON());
					entry.put("TEL", addr.getTEL());
					entry.put("RECV_ADDR", addr.getDELIVER_DTL_ADDR());
					//entry.put("TRANSPORT", addr.getTRANSPORT());
				}

			}
		}

		if(IS_DRP_LEDGER.equals("0")){ // 订单中心 订货处理

		} else if(IS_DRP_LEDGER.equals("1")){ // 经销商订货管理

			// 获取返利折扣
			Map<String,String> result = LogicUtil.getLedgerChrg(userBean.getCHANN_ID(), StringUtil.nullToSring(entry.get("LEDGER_ID")));
			if(null != result){
				entry.put("REBATEDSCT", result.get("DECT_RATE"));//返利折扣
				entry.put("REBATE_TOTAL", result.get("REBATE_TOTAL"));//返利总金额
				entry.put("REBATE_CURRT", result.get("REBATE_CURRT"));//当前返利
				entry.put("REBATE_FREEZE", result.get("REBATE_FREEZE"));//冻结的返利

			}

			// 获取折扣率
			String DECT_RATE = LogicUtil.getChannDsct(userBean.getCHANN_ID());
			request.setAttribute("DECT_RATE", DECT_RATE);//折扣率

			// 下级经销商IS_DRP_LEDGER改为2
			if (!StringUtil.isEmpty(userBean.getCHANN_ID_P())) {
				IS_DRP_LEDGER=BusinessConsts.INTEGER_2;
			}
		}

		Map<String,String> param=goodsorderhdService.queryTotal(selRowId);
		request.setAttribute("totalInfo", param);
		request.setAttribute("rst", entry);
		request.setAttribute("BASE_CHANN_ID", userBean.getBASE_CHANN_ID());
		request.setAttribute("ZTXXID", userBean.getLoginZTXXID());
		request.setAttribute("pvg",setPvg(userBean));
		request.setAttribute("IS_DRP_LEDGER",IS_DRP_LEDGER);
		request.setAttribute("erjiOrderId",erjiOrderId);
		request.setAttribute("option",option);
		return view(webPath,"Goodsorderhd_Edit");
	}



	/**
	 * 预订单处理页面新增/编辑页面
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/toHandleList")
	public String toHandleList(HttpServletRequest request,
			HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT_AUDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商，   0：总部
		if(IS_DRP_LEDGER.equals("0")){ // 订单中心 订货处理
		} else if(IS_DRP_LEDGER.equals("1")){ // 经销商订货管理
		}
		request.setAttribute("xtyhId", userBean.getXTYHID());
		return view(webPath, "Goodsorderhd_HandleEdit");
	}

	/**
	 * 预订单处理页面新增/编辑页面
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/toHandleEdit")
	public String toHandleEdit(HttpServletRequest request,
			HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT_AUDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		Map<String, Object> entry = new HashMap<String, Object>();
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商，   0：总部
		String CHANN_IDS = userBean.getCHANNS_CHARG();
		String selRowId = ParamUtil.get(request, "selRowId");
		if (StringUtils.isNotEmpty(selRowId)) {
			entry = goodsorderhdService.load(selRowId);
			// 主表附件
			String attPath = getAttPath(selRowId);
			entry.put("attPath", attPath); //附件图纸 可能有多个
			// 查询明细数据
			modifyToChildEdit(selRowId, "handle", request);
		} else {
			entry.put("SALE_ID", userBean.getXTYHID());
			entry.put("SALE_NAME", userBean.getXM());
			entry.put("SALEDEPT_NAME", userBean.getBMMC());
			entry.put("SALEDEPT_ID", userBean.getBMXXID());

			entry.put("BILL_TYPE", "常规订单");
			entry.put("TRANSPORT", "汽运");

			String XTYHID = userBean.getXTYHID();
			// 分管账套唯一则默认带出
			List<Map<String,String>> result = xtyhService.getLedgerChrgList(XTYHID);
			if (null != result && result.size() == 1) {
				String LEDGER_ID = result.get(0).get("LEDGER_ID");
				entry.put("LEDGER_ID", LEDGER_ID);
			}
		}
		try {
			String LEDGER_ID = StringUtil.nullToSring(entry.get("LEDGER_ID"));
			// 流程编号
			String flowType = "0";//LogicUtil.getFlowType(userBean);
			String flowNo = flowService.getFlowNoByLedger(LEDGER_ID, flowType);
			request.setAttribute("flowNo",flowNo);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error(e.getMessage());
		}
		request.setAttribute("rst", entry);
		request.setAttribute("IS_DRP_LEDGER", IS_DRP_LEDGER);
		request.setAttribute("XTYHID", userBean.getXTYHID());
		request.setAttribute("CHANN_IDS", CHANN_IDS);
		return view(webPath, "Goodsorderhd_HandleEdit");
	}


	/**
	 * * to 主表编辑页面初始化
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/copyToEdit")
	public String copyToEdit(HttpServletRequest request, HttpServletResponse response){
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)
				&& !QXUtil.checkMKQX(userBean, PVG_EDIT_SUB))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		// 经销商整单复制
		String selRowId = ParamUtil.get(request, "selRowId");

		Map<String,Object> entry = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(selRowId)){
			entry = goodsorderhdService.load(selRowId);
			// 主表附件
			String attPathP = getAttPath(selRowId);
			entry.put("attPath", attPathP); //附件图纸 可能有多个
			entry.put("GOODS_ORDER_ID", "");
			entry.put("GOODS_ORDER_NO", "");
			entry.put("ORDER_DATE", "");
			// 查询明细数据
			List <Map<String, String>> result = goodsorderhdService.childQuery(selRowId);
			for (Iterator it = result.iterator(); it.hasNext();) {
				Map<String, String> child = (Map<String, String>) it.next();
				String attPath = getAttPath(child.get("GOODS_ORDER_DTL_ID"));
				child.put("attPath", attPath);
				child.put("GOODS_ORDER_DTL_ID", "");
				child.put("GOODS_ORDER_ID", "");/*
				child.put("PRICE", BusinessConsts.INTEGER_0);
				child.put("DECT_RATE", "100");
				child.put("DECT_PRICE", BusinessConsts.INTEGER_0);
				child.put("REBATE_PRICE", BusinessConsts.INTEGER_0);
				child.put("REBATE_AMOUNT", BusinessConsts.INTEGER_0);
				child.put("ORDER_NUM", BusinessConsts.INTEGER_0);
				child.put("ORDER_AMOUNT", BusinessConsts.INTEGER_0);
				child.put("SENDED_NUM", BusinessConsts.INTEGER_0);*/
			}
			request.setAttribute("rstChild", result);
		}

		request.setAttribute("rst", entry);
		request.setAttribute("pvg",setPvg(userBean));
		return view(webPath,"Goodsorderhd_Edit");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/copyToSave")
	public void copyToSave(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean,PVG_EDIT))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String selRowId = ParamUtil.get(request, "selRowId");

			Map<String, Object> entry = goodsorderhdService.load(selRowId);
			// 主表附件
			String attPathP = getAttPath(selRowId);
			entry.put("attPath", attPathP); //附件图纸 可能有多个
			entry.put("GOODS_ORDER_ID", "");
			entry.put("GOODS_ORDER_NO", "");
			GoodsorderhdModel model = (GoodsorderhdModel) LogicUtil.tranMap2Bean(entry, GoodsorderhdModel.class);

			// 查询明细数据
			List<GoodsorderhdModelChld> result = new ArrayList<GoodsorderhdModelChld>();
			List childs = goodsorderhdService.childQuery(selRowId);
			for (Iterator it = childs.iterator(); it.hasNext();) {
				Map<String, Object> child = (Map<String, Object>) it.next();
				String attPath = getAttPath(child.get("GOODS_ORDER_DTL_ID").toString());
				child.put("attPath", attPath);
				child.put("GOODS_ORDER_DTL_ID", "");

				result.add((GoodsorderhdModelChld) LogicUtil.tranMap2Bean(child, GoodsorderhdModelChld.class));
			}
			String optper = ParamUtil.get(request, "optper");
			goodsorderhdService.txEdit(selRowId, model, result, userBean, "", "", optper);
			jsonResult = jsonResult(true, "复制成功");
		}catch(ServiceException s){
			jsonResult = jsonResult(false, s.getMessage());
		} catch (Exception e) {
			jsonResult = jsonResult(false, "复制失败");
			e.printStackTrace();
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * * 编辑框架页面加载子页面
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping("/modifyToChildEdit")
	public String modifyToChildEdit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		String GOODS_ORDER_ID = ParamUtil.get(request, "GOODS_ORDER_ID");
		if(!StringUtil.isEmpty(GOODS_ORDER_ID))
		{
			List <Map<String, String>> result = goodsorderhdService.childQuery(GOODS_ORDER_ID);
			request.setAttribute("rst", result);
		}
		//获取返利货品分类
		String[] REBATE_PRD_NUMBER=Consts.REBATE_PRD_NUMBERS.split(",");
		StringBuffer PRD_NOS=new StringBuffer();
		for (int i = 0; i < REBATE_PRD_NUMBER.length; i++) {
			PRD_NOS.append("'").append(REBATE_PRD_NUMBER[i]).append("',");
		}
		String PRDNOS=PRD_NOS.toString();
		if(PRDNOS.length()>0){
			PRDNOS = PRDNOS.substring(0,PRDNOS.length() - 1);
		}
		//-- 0156143--Start--刘曰刚--2014-06-16//
		//获取渠道折扣率
		String DECT_RATE=LogicUtil.getChannDsct(userBean.getCHANN_ID());
		request.setAttribute("DECT_RATE", DECT_RATE);
		//-- 0156143--End--刘曰刚--2014-06-16//
		request.setAttribute("CHANN_ID", userBean.getCHANN_ID());
		request.setAttribute("PRDNOS", PRDNOS);
		return view(webPath,"Goodsorderhd_Edit_Chld");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void modifyToChildEdit(String GOODS_ORDER_ID, String option, HttpServletRequest request){

		if(!StringUtil.isEmpty(GOODS_ORDER_ID))
		{
			List <Map<String, String>> result = goodsorderhdService.childQuery(GOODS_ORDER_ID);
			for (Iterator it = result.iterator(); it.hasNext();) {
				Map<String, String> child = (Map<String, String>) it.next();
				String attPath = getAttPath(child.get("GOODS_ORDER_DTL_ID"));
				child.put("attPath", attPath);
				if ("turnToHQ".equals(option)) {
					child.put("PRICE", "0");
					child.put("DECT_RATE", "100");
				}
			}
			request.setAttribute("rstChild", result);
		}

		//获取返利货品分类
		/*String[] REBATE_PRD_NUMBER=Consts.REBATE_PRD_NUMBERS.split(",");
		StringBuffer PRD_NOS=new StringBuffer();
		for (int i = 0; i < REBATE_PRD_NUMBER.length; i++) {
			PRD_NOS.append("'").append(REBATE_PRD_NUMBER[i]).append("',");
		}
		String PRDNOS=PRD_NOS.toString();
		if(PRDNOS.length()>0){
			PRDNOS = PRDNOS.substring(0,PRDNOS.length() - 1);
		}*/
		//-- 0156143--Start--刘曰刚--2014-06-16//
		//获取渠道折扣率
		/*String DECT_RATE=LogicUtil.getChannDsct(userBean.getCHANN_ID());
        request.setAttribute("DECT_RATE", DECT_RATE);*/
		//-- 0156143--End--刘曰刚--2014-06-16//
		//request.setAttribute("CHANN_ID", userBean.getCHANN_ID());
		//request.setAttribute("PRDNOS", PRDNOS);


	}

	/**
	 * * to 直接编辑明细页面
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping("/toChildEdit")
	public String toChildEdit(
			HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		//多个Id批量查询，用逗号隔开
		String GOODS_ORDER_DTL_IDs = request.getParameter("GOODS_ORDER_DTL_IDS");
		//没有零星领料Id可以直接跳转。
		if (!StringUtil.isEmpty(GOODS_ORDER_DTL_IDs)) {
			Map<String,String> params = new HashMap<String,String>();
			params.put("GOODS_ORDER_DTL_IDS",GOODS_ORDER_DTL_IDs);
			List <GoodsorderhdModelChld> list = goodsorderhdService.loadChilds(params);
			request.setAttribute("rst", list);
		}
		//获取返利货品分类
		String[] REBATE_PRD_NUMBER=Consts.REBATE_PRD_NUMBERS.split(",");
		StringBuffer PRD_NOS=new StringBuffer();
		for (int i = 0; i < REBATE_PRD_NUMBER.length; i++) {
			PRD_NOS.append("'").append(REBATE_PRD_NUMBER[i]).append("',");
		}
		String PRDNOS=PRD_NOS.toString();
		if(PRDNOS.length()>0){
			PRDNOS = PRDNOS.substring(0,PRDNOS.length() - 1);
		}
		//-- 0156143--Start--刘曰刚--2014-06-16//
		//获取渠道折扣率
		String DECT_RATE=LogicUtil.getChannDsct(userBean.getCHANN_ID());
		request.setAttribute("DECT_RATE", DECT_RATE);
		//-- 0156143--End--刘曰刚--2014-06-16//

		request.setAttribute("CHANN_ID", userBean.getCHANN_ID());
		request.setAttribute("PRDNOS", PRDNOS);
		return view(webPath,"Goodsorderhd_Edit_Chld");
	}
	/**
	 * 根据id修改状态
	 * @param request
	 * @param response
	 * @param state
	 */
	@RequestMapping("/upStateById")
	@ResponseBody
	public void upStateById(HttpServletRequest request, HttpServletResponse response,String state) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean,PVG_EDIT))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			Map<String,String> params = new HashMap<String,String>();
			String selRowId = ParamUtil.get(request, "selRowId");
			params.put("GOODS_ORDER_ID", selRowId);
			params.put("STATE", state);
			goodsorderhdService.txUpdateById(params);
			Map<String,String> p = new HashMap<String,String>();
			p.put("GOODS_ORDER_ID", selRowId);
			jsonResult = new Gson().toJson(new Result(true, p, "保存成功"));

		}catch(ServiceException s){
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
	 * * 主表 新增/修改数据
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping("/edit")
	public void edit(HttpServletRequest request, HttpServletResponse response,String state) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean,PVG_EDIT)
				&&!QXUtil.checkMKQX(userBean,PVG_EDIT_AUDIT))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String GOODS_ORDER_ID = ParamUtil.get(request, "GOODS_ORDER_ID");
			String erjiOrderId = ParamUtil.get(request, "erjiOrderId"); // 下级订货处理 转总部订单
			if(!StringUtil.isEmpty(erjiOrderId)){
				GOODS_ORDER_ID = "";
				Map<String, Object> entry = goodsorderhdService.txLoadForUpdate(erjiOrderId);
				if (!BusinessConsts.GOODSORDER_STATE_DZZB.equals(entry.get("STATE"))) {
					throw new ServiceException("当前单据状态已更改，请刷新后重试！");
				}
			} else if(!StringUtil.isEmpty(GOODS_ORDER_ID)){
				Map<String, Object> entry = goodsorderhdService.txLoadForUpdate(GOODS_ORDER_ID);
				if (!BusinessConsts.GOODSORDER_STATE_CG.equals(entry.get("STATE"))
						&& !BusinessConsts._BACK.equals(entry.get("STATE"))
						&& !BusinessConsts.GOODSORDER_STATE_DZZB.equals(entry.get("STATE"))
						&& !BusinessConsts.GOODSORDER_STATE_YZZB.equals(entry.get("STATE"))) {
					throw new ServiceException("当前单据状态已更改，请刷新后重试！");
				}
			}
			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			String REBATEDSCT = ParamUtil.get(request, "REBATEDSCT");//返利折扣

			GoodsorderhdModel model = new Gson().fromJson(parentJsonData, new TypeToken <GoodsorderhdModel>() {}.getType());
			String childJsonData = ParamUtil.get(request, "childJsonData");
			List <GoodsorderhdModelChld> chldModelList = null;
			if (!StringUtil.isEmpty(childJsonData)) {
				chldModelList = new Gson().fromJson(childJsonData, new TypeToken <List <GoodsorderhdModelChld>>() {
				}.getType());
			}

			String optper = ParamUtil.get(request, "optper");
			GOODS_ORDER_ID =  goodsorderhdService.txEdit(GOODS_ORDER_ID, model, chldModelList, userBean,REBATEDSCT,erjiOrderId, optper);
			//            jsonResult = jsonResult(true, GOODS_ORDER_ID);
			Map<String,String> p = new HashMap<String,String>();
			p.put("GOODS_ORDER_ID", GOODS_ORDER_ID);
			jsonResult = new Gson().toJson(new Result(true, p, "保存成功"));

		}catch(ServiceException s){
			jsonResult = jsonResult(false, s.getMessage());
			s.printStackTrace();
		} catch (Exception e) {
			jsonResult = jsonResult(false, "保存失败");
			e.printStackTrace();
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	@RequestMapping("/edittocommit")
	public void editToCcommit(HttpServletRequest request, HttpServletResponse response,String state) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean,PVG_EDIT)
				&&!QXUtil.checkMKQX(userBean,PVG_EDIT_AUDIT)
				&&!QXUtil.checkMKQX(userBean,PVG_COMMIT_CANCLE))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String GOODS_ORDER_ID = ParamUtil.get(request, "GOODS_ORDER_ID");
			String erjiOrderId = ParamUtil.get(request, "erjiOrderId"); // 下级订货处理 转总部订单
			if(!StringUtil.isEmpty(erjiOrderId)){
				GOODS_ORDER_ID = "";
				Map<String, Object> entry = goodsorderhdService.txLoadForUpdate(erjiOrderId);
				if (!BusinessConsts.GOODSORDER_STATE_DZZB.equals(entry.get("STATE"))) {
					throw new ServiceException("当前单据状态已更改，请刷新后重试！");
				}
			} else if(!StringUtil.isEmpty(GOODS_ORDER_ID)){
				Map<String, Object> entry = goodsorderhdService.txLoadForUpdate(GOODS_ORDER_ID);
				if (!BusinessConsts.GOODSORDER_STATE_CG.equals(entry.get("STATE"))
						&& !BusinessConsts._BACK.equals(entry.get("STATE"))) {
					throw new ServiceException("当前单据状态已更改，请刷新后重试！");
				}
			}
			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			String REBATEDSCT = ParamUtil.get(request, "REBATEDSCT");//返利折扣

			GoodsorderhdModel model = new Gson().fromJson(parentJsonData, new TypeToken <GoodsorderhdModel>() {}.getType());
			String childJsonData = ParamUtil.get(request, "childJsonData");
			List <GoodsorderhdModelChld> chldModelList = null;
			if (!StringUtil.isEmpty(childJsonData)) {
				chldModelList = new Gson().fromJson(childJsonData, new TypeToken <List <GoodsorderhdModelChld>>() {
				}.getType());
			} else {
				throw new ServiceException("没有明细，不能提交!");
			}

			String optper = ParamUtil.get(request, "optper");
			GOODS_ORDER_ID =  goodsorderhdService.txEditToCommit(GOODS_ORDER_ID, model, chldModelList, userBean,REBATEDSCT,erjiOrderId, optper);
			jsonResult = jsonResult(true, "提交成功");

		}catch(ServiceException s){
			jsonResult = jsonResult(false, s.getMessage());
			s.printStackTrace();
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
	 * * 子表 新增/修改数据
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping("/childEdit")
	public void childEdit(
			HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String GOODS_ORDER_ID = request.getParameter("GOODS_ORDER_ID");
			String ORDER_RECV_DATE = request.getParameter("ORDER_RECV_DATE");//交期
			String jsonDate = request.getParameter("childJsonData");

			String IS_USE_REBATE = ParamUtil.get(request, "IS_USE_REBATE");//是否使用返利
			String REBATEDSCT = ParamUtil.get(request, "REBATEDSCT");//返利折扣
			Map<String,String> params = new HashMap<String,String>();
			params.put("IS_USE_REBATE", IS_USE_REBATE);
			params.put("REBATEDSCT", REBATEDSCT);
			if (!StringUtil.isEmpty(jsonDate)) {
				List <GoodsorderhdModelChld> modelList = new Gson().fromJson(jsonDate, new TypeToken <List <GoodsorderhdModelChld>>() {
				}.getType());
				goodsorderhdService.txChildEdit(GOODS_ORDER_ID, modelList,userBean,ORDER_RECV_DATE,params,"list");
			}
		}catch(ServiceException s){
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
	 * * 查询 折扣率
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping("/getRate")
	public void getRate(
			HttpServletRequest request, HttpServletResponse response) {
		@SuppressWarnings("unused")
		UserBean userBean = ParamUtil.getUserBean(request);
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String PRD_ID = ParamUtil.get(request, "PRD_ID");
			String AREA_ID = ParamUtil.get(request, "AREA_ID");
			Map<String,String> reault = goodsorderhdService.getRateByAreaIdPId(AREA_ID,PRD_ID);
			//             if(StringUtil.isEmpty(rate)){
			//            	 Map<String,Object> chann = goodsorderhdService.loadChann(userBean.getCHANN_ID());
			//             	 jsonResult = new Gson().toJson(new Result(true, chann, ""));
			//             }else{
			//            	 jsonResult = jsonResult(true, rate);
			//             }
			jsonResult = new Gson().toJson(new Result(true, reault, ""));
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
	 * * 主表 删除
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping("/delete")
	public void delete(
			HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_DELETE))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String GOODS_ORDER_ID = ParamUtil.get(request, "GOODS_ORDER_ID");
			Map<String,Object> entry = goodsorderhdService.load(GOODS_ORDER_ID);
			Map <String, String> params = new HashMap <String, String>();
			params.put("GOODS_ORDER_ID", GOODS_ORDER_ID);
			params.put("FROM_BILL_ID", StringUtil.nullToSring(entry.get("FROM_BILL_ID")));
			params.put("UPD_NAME", userBean.getXM());
			params.put("UPDATOR", userBean.getXTYHID());
			params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_DROP);
			goodsorderhdService.txDelete(params, userBean);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "删除失败!");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * * 明细批量删除
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping("/childDelete")
	public void childDelete(
			HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_DELETE)
				&&!QXUtil.checkMKQX(userBean, PVG_QUOTE_AUDIT))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String GOODS_ORDER_DTL_IDs = request.getParameter("GOODS_ORDER_DTL_IDS");
			//批量删除，多个Id之间用逗号隔开
			goodsorderhdService.txBatch4DeleteChild(GOODS_ORDER_DTL_IDs);
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
	 * * to 详细信息
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/toDetail")
	public String toDetail(
			HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS))
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS_SUB))
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT)))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		String GOODS_ORDER_ID = ParamUtil.get(request, "selRowId");
		if(!StringUtil.isEmpty(GOODS_ORDER_ID)){
			Map<String,Object> entry = goodsorderhdService.load(GOODS_ORDER_ID);
			// 主表附件
			String attPathP = getAttPath(GOODS_ORDER_ID);
			entry.put("attPath", attPathP); //附件图纸 可能有多个
			request.setAttribute("rst", entry);

			List <Map<String, String>> result = goodsorderhdService.childQuery(GOODS_ORDER_ID);
			for (Iterator it = result.iterator(); it.hasNext();) {
				Map<String, String> child = (Map<String, String>) it.next();
				String attPath = getAttPath(child.get("GOODS_ORDER_DTL_ID"));
				child.put("attPath", attPath);
			}
			request.setAttribute("rstChild", result);
		}
		String AREA_SER_ID = userBean.getAREA_SER_ID();
		if(!StringUtil.isEmpty(AREA_SER_ID) && !"true".equals(Consts.IS_OLD_FLOW)){
			//走区域服务中心
			request.setAttribute("havaAreaSerId", 0);
		}
		return view(webPath,"Goodsorderhd_Detail");
	}

	/**
	 * * 提交时，校验是否有明细.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping("/toCommit")
	public void toCommit(
			HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_COMMIT_CANCLE))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String errorId = "";
		try {
			String selRowId = ParamUtil.get(request,"selRowId");
			if(!StringUtil.isEmpty(selRowId)){
				Map<String, Object> entry = goodsorderhdService.txLoadForUpdate(selRowId);
				if (!BusinessConsts.GOODSORDER_STATE_CG.equals(StringUtil.nullToSring(entry.get("STATE")))
						&& !BusinessConsts._BACK.equals(StringUtil.nullToSring(entry.get("STATE")))) {
					throw new ServiceException("当前单据状态已更改，请刷新后重试！");
				}
				List<Map<String, String>> list = goodsorderhdService.childQuery(selRowId);
				if (list.size() == 0) {
					errorId = "0";
					throw new ServiceException("没有明细，不能提交!");
				} else {
					GoodsorderhdModel model = (GoodsorderhdModel) LogicUtil.tranMap2Bean(entry, GoodsorderhdModel.class);
					goodsorderhdService.txCommit(selRowId, model, userBean);
					jsonResult = jsonResult(true, "提交成功");
				}
			}

		}catch(ServiceException s){
			jsonResult = jsonResult(false, s.getMessage());
		} catch (Exception e) {
			if ("0".equals(errorId)) {
				jsonResult = jsonResult(false, "没有明细，不能提交!");
			}else if ("1".equals(errorId)) {
				jsonResult = jsonResult(false, e.getMessage());
			}else {
				jsonResult = jsonResult(false, "提交失败");
			}
			logger.error(e);
			e.printStackTrace();
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * 撤销
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping("/revoke")
	public void revoke(
			HttpServletRequest request,HttpServletResponse response){
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_COMMIT_CANCLE)){
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try{
			String selRowId = ParamUtil.get(request,"selRowId");
			String GOODS_ORDER_NO = ParamUtil.get(request,"GOODS_ORDER_NO");
			String flowServiceId = ParamUtil.get(request,"flowServiceId");

			goodsorderhdService.revoke(selRowId, GOODS_ORDER_NO, flowServiceId, userBean);
			jsonResult = jsonResult(true, "撤销成功");
		}catch(Exception e){
			logger.info(e);
			jsonResult = jsonResult(false, "撤销失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}

	/**
	 * 下级要货处理-退回
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping("/back2c")
	public void back2c(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG
				&& !QXUtil.checkMKQX(userBean, PVG_BACK_SUB)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String selRowId = ParamUtil.get(request, "selRowId");
			String RETURN_RSON = ParamUtil.get(request, "RETURN_RSON");
			if(!StringUtil.isEmpty(selRowId)){
				Map<String, Object> entry = goodsorderhdService.txLoadForUpdate(selRowId);
				/*if (!"草稿".equals(StringUtil.nullToSring(model.get("STATE")))
						&& !"退回".equals(StringUtil.nullToSring(model.get("STATE")))) {
					throw new ServiceException("该单据已经提交！");
				}*/
				String GOODS_ORDER_NO = StringUtil.nullToSring(entry.get("GOODS_ORDER_NO"));
				String flowServiceId = StringUtil.nullToSring(entry.get("ORDER_TRACE_ID"));
				goodsorderhdService.back(selRowId, GOODS_ORDER_NO, RETURN_RSON, flowServiceId, userBean);
			}

			jsonResult = jsonResult(true, "操作成功");
		} catch (Exception e) {
			logger.info(e);
			jsonResult = jsonResult(false, "操作失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * 下级订货处理-转总部订货订单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping("/turnToHQ")
	public void turnToHQ(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG
				&& !QXUtil.checkMKQX(userBean, PVG_COMMIT_CANCLE)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String selRowId = ParamUtil.get(request, "selRowId");
			String GOODS_ORDER_NO = ParamUtil.get(request, "GOODS_ORDER_NO");
			if(!StringUtil.isEmpty(selRowId)){
				Map<String, Object> model = goodsorderhdService.txLoadForUpdate(selRowId);
				/*if (!"草稿".equals(StringUtil.nullToSring(model.get("STATE")))
						&& !"退回".equals(StringUtil.nullToSring(model.get("STATE")))) {
					throw new ServiceException("该单据已经提交！");
				}*/
				String flowServiceId = StringUtil.nullToSring(model.get("ORDER_TRACE_ID"));
				goodsorderhdService.turnToHQ(selRowId, GOODS_ORDER_NO, flowServiceId, userBean);
			}

			jsonResult = jsonResult(true, "操作成功");
		} catch (Exception e) {
			logger.info(e);
			jsonResult = jsonResult(false, "操作失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * 订货处理-审图页面
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping("/toAudit")
	public String toAudit(
			HttpServletRequest request, HttpServletResponse response){
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_AUDIT_AUDIT)
				&& !QXUtil.checkMKQX(userBean, PVG_QUOTE_AUDIT)
				&& !QXUtil.checkMKQX(userBean, PVG_QUOTE_CONFIRM)
				&& !QXUtil.checkMKQX(userBean, PVG_CONFIRM_AUDIT))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		String selRowId = ParamUtil.get(request, "selRowId");
		String option = ParamUtil.get(request, "option"); // 总部审图、报价
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商，   0：总部
		Map<String,Object> entry = goodsorderhdService.load(selRowId);
		// 主表附件
		String attPath = getAttPath(selRowId);
		entry.put("attPath", attPath); //附件图纸 可能有多个

		// 获取返利折扣
		String CHANN_ID = StringUtil.nullToSring(entry.get("CHANN_ID"));
		String LEDGER_ID = StringUtil.nullToSring(entry.get("LEDGER_ID"));
		Map<String,String> result = LogicUtil.getLedgerChrg(CHANN_ID, LEDGER_ID);
		if(null != result){
			entry.put("REBATEDSCT", result.get("DECT_RATE"));//返利折扣
			entry.put("REBATE_TOTAL", result.get("REBATE_TOTAL"));//返利总金额
			entry.put("REBATE_CURRT", result.get("REBATE_CURRT"));//当前返利
			//entry.put("REBATE_CURRT", "20000");
			entry.put("REBATE_FREEZE", result.get("REBATE_FREEZE"));//冻结的返利
		}


		try {
			// 查询明细数据
			modifyToChildEdit(selRowId, option, request);
			// 流程编号
			String flowType = "0";//LogicUtil.getFlowType(userBean);
			String flowNo = flowService.getFlowNoByLedger(LEDGER_ID, flowType);
			request.setAttribute("flowNo",flowNo);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error(e.getMessage());
		}

		request.setAttribute("rst", entry);
		request.setAttribute("selRowId",selRowId);
		request.setAttribute("option",option);
		request.setAttribute("IS_DRP_LEDGER",IS_DRP_LEDGER);
		return view(webPath,"Goodsorderhd_Edit");
	}

	/**
	 * 订货处理-审图、报价
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping("/audit")
	public void audit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_AUDIT_AUDIT)
				&& !QXUtil.checkMKQX(userBean, PVG_QUOTE_AUDIT))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String GOODS_ORDER_ID = ParamUtil.get(request, "GOODS_ORDER_ID");
			String option = ParamUtil.get(request, "option"); // 总部审图、报价
			//String REBATEDSCT = ParamUtil.get(request, "REBATEDSCT");//返利折扣

			Map<String, Object> entry = goodsorderhdService.txLoadForUpdate(GOODS_ORDER_ID);
			if ("audit".equals(option) && !BusinessConsts.GOODSORDER_STATE_STZ.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}
			if ("quote".equals(option) && !BusinessConsts.GOODSORDER_STATE_BJZ.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}

			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			GoodsorderhdModel model = new Gson().fromJson(parentJsonData, new TypeToken <GoodsorderhdModel>() {}.getType());
			String childJsonData = ParamUtil.get(request, "childJsonData");
			List <GoodsorderhdModelChld> chldModelList = null;
			if (!StringUtil.isEmpty(childJsonData)) {
				chldModelList = new Gson().fromJson(childJsonData, new TypeToken <List <GoodsorderhdModelChld>>() {
				}.getType());
			}

			goodsorderhdService.txAudit(GOODS_ORDER_ID, model, chldModelList, userBean, option);
			jsonResult = jsonResult(true, "操作成功");

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
	 * 订货处理-确认报价
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping("/confirm")
	public void confirm(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_QUOTE_CONFIRM)
				&& !QXUtil.checkMKQX(userBean, PVG_CONFIRM_AUDIT))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String GOODS_ORDER_ID = ParamUtil.get(request, "GOODS_ORDER_ID");
			String option = ParamUtil.get(request, "option"); // 经销商确认报价、总部确认
			//String REBATEDSCT = ParamUtil.get(request, "REBATEDSCT");//返利折扣

			Map<String, Object> entry = goodsorderhdService.txLoadForUpdate(GOODS_ORDER_ID);
			if ("confirmquote".equals(option) && !BusinessConsts.GOODSORDER_STATE_BJDQR.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}
			if ("confirm".equals(option) && !BusinessConsts.GOODSORDER_STATE_LDDQR.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}

			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			GoodsorderhdModel model = new Gson().fromJson(parentJsonData, new TypeToken <GoodsorderhdModel>() {}.getType());

			goodsorderhdService.txConfirm(GOODS_ORDER_ID, model, option, userBean);
			jsonResult = jsonResult(true, "操作成功");

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
	 * 订货处理-退回
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping("/back")
	public void back(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG
				&& !QXUtil.checkMKQX(userBean, PVG_BACK_AUDIT)
				&& !QXUtil.checkMKQX(userBean, PVG_CREXSDD_AUDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String selRowId = ParamUtil.get(request, "selRowId");
			//String GOODS_ORDER_NO = ParamUtil.get(request, "GOODS_ORDER_NO");
			String RETURN_RSON = ParamUtil.get(request, "RETURN_RSON");
			if(!StringUtil.isEmpty(selRowId)){
				Map<String, Object> entry = goodsorderhdService.load(selRowId);
				/*if (!"草稿".equals(StringUtil.nullToSring(model.get("STATE")))
						&& !"退回".equals(StringUtil.nullToSring(model.get("STATE")))) {
					throw new ServiceException("该单据已经提交！");
				}*/
				//String flowServiceId = StringUtil.nullToSring(entry.get("ORDER_TRACE_ID"));
				//goodsorderhdService.txBack(selRowId, GOODS_ORDER_NO, RETURN_RSON, flowServiceId, userBean);
				GoodsorderhdModel model = (GoodsorderhdModel) LogicUtil.tranMap2Bean(entry, GoodsorderhdModel.class);
				model.setRETURN_RSON(RETURN_RSON);
				goodsorderhdService.txBack(selRowId, model, userBean);
			}

			jsonResult = jsonResult(true, "操作成功");
		} catch (Exception e) {
			logger.error(e);
			jsonResult = jsonResult(false, "操作失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * 订货处理-强制退回并删除销售订单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping("/saleBack")
	public void saleBack(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG
				&& !QXUtil.checkMKQX(userBean, PVG_BACK_AUDIT)
				&& !QXUtil.checkMKQX(userBean, PVG_CREXSDD_AUDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String selRowId = ParamUtil.get(request, "SALE_ORDER_ID");
			String reason = ParamUtil.get(request, "reason");
			if(!StringUtil.isEmpty(selRowId)){
				// TODO 判断销售订单状态是否为'待处理'
				boolean isExists = false;
				if (isExists) {
					throw new ServiceException("请先退回销售订单！");
				}

				Map<String, Object> entry = goodsorderhdService.load(selRowId);
				/*if (!"草稿".equals(StringUtil.nullToSring(model.get("STATE")))
						&& !"退回".equals(StringUtil.nullToSring(model.get("STATE")))) {
					throw new ServiceException("该单据已经提交！");
				}*/
				GoodsorderhdModel model = (GoodsorderhdModel) LogicUtil.tranMap2Bean(entry, GoodsorderhdModel.class);
				model.setAuditContents(reason);
				goodsorderhdService.saleBackGoods(selRowId, userBean);
			}

			jsonResult = jsonResult(true, "操作成功");
		} catch (Exception e) {
			logger.error(e);
			jsonResult = jsonResult(false, "操作失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * 订货处理-生成销售订单
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping("/tocrexsdd")
	public String toCrexsdd(
			HttpServletRequest request, HttpServletResponse response){
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_CREXSDD_AUDIT))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		String selRowId = ParamUtil.get(request, "selRowId");
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商，   0：总部
		Map<String,Object> entry = goodsorderhdService.load(selRowId);
		// 主表附件
		String attPath = getAttPath(selRowId);
		entry.put("attPath", attPath); //附件图纸 可能有多个
		// 查询明细数据
		try {
			// 预计到货日期
			GoodsorderhdModel model = (GoodsorderhdModel) LogicUtil.tranMap2Bean(entry, GoodsorderhdModel.class);/*
			*/
			String ORDER_DELIVERY_DATE = model.getORDER_DELIVERY_DATE();
			if (StringUtil.isEmpty(ORDER_DELIVERY_DATE)) {
			}
			Date date = DateUtil.addDays(DateUtil.getDate(), 25);
			ORDER_DELIVERY_DATE = DateUtil.formatDateToStr(date);
			model.setORDER_DELIVERY_DATE(ORDER_DELIVERY_DATE);
			date = DateUtil.addDays(date, 7);
			String PRE_RECV_DATE = DateUtil.formatDateToStr(date);
			model.setPRE_RECV_DATE(PRE_RECV_DATE);

			entry.put("ORDER_DELIVERY_DATE", ORDER_DELIVERY_DATE);
			entry.put("PRE_RECV_DATE", PRE_RECV_DATE);

			String xtbs = Consts.FACTORY_NO_CONF.getString(model.getLEDGER_ID());//系统标识
			request.setAttribute("xtbs",xtbs);

			modifyToChildCrexsdd(selRowId, model, request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		request.setAttribute("rst", entry);
		request.setAttribute("selRowId",selRowId);
		request.setAttribute("IS_DRP_LEDGER",IS_DRP_LEDGER);
		return view(webPath,"Goodsorderhd_Crexsdd");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void modifyToChildCrexsdd(String GOODS_ORDER_ID, GoodsorderhdModel model, HttpServletRequest request){
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_CREXSDD_AUDIT))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		if(!StringUtil.isEmpty(GOODS_ORDER_ID))
		{
			String[] PROESS_TYPEs = new String[]{"定制","现货","OEM定制"};
			// 订货订单明细
			List <Map<String, String>> childList = goodsorderhdService.childQuery(GOODS_ORDER_ID);
			// 销售订单
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			String orderNo = LogicUtil.getFactoryNo(model.getLEDGER_ID(), model.getORDER_DELIVERY_DATE(), model.getBILL_TYPE());
			for (int i = 0; i < PROESS_TYPEs.length; i++) {
				String no = orderNo + "-" + (i+1);
				String type = PROESS_TYPEs[i];

				Map<String, Object> entry = new HashMap<String, Object>();
				//String no = LogicUtil.getBmgz("ERP_SALE_ORDER_NO");
				entry.put("SALE_ORDER_NO", no);
				entry.put("FACTORY_NO", no);
				entry.put("PROESS_TYPE", type);
				entry.put("attPath", model.getAttPath()); //要货单的附件图纸 可能有多个
				entry.put("URGENCY_TYPE", BusinessConsts.NORMAL);

				// 封装销售订单明细
				List <Map<String, String>> childListNew = new ArrayList<Map<String, String>>();
				for (Iterator it = childList.iterator(); it.hasNext();) {
					Map<String, String> child = (Map<String, String>) it.next();
					String attPath = getAttPath(child.get("GOODS_ORDER_DTL_ID"));
					child.put("attPath", attPath);

					// 查询库存数量

					if (i == 2) {// TODO 判断是否OEM

						//childListNew.add(child);
					} else if (i == 1) {// TODO 判断是否现货

						//childListNew.add(child);
					} else {
						childListNew.add(child);
					}
				}
				entry.put("childList", childListNew);
				result.add(entry);
			}
			request.setAttribute("rstChild", result);
		}
	}

	@RequestMapping(value = "/crexsdd", method = { RequestMethod.GET, RequestMethod.POST })
	public void crexsdd(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean,PVG_CREXSDD_AUDIT))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String GOODS_ORDER_ID = ParamUtil.get(request, "GOODS_ORDER_ID");
			/*Map<String, Object> entry = goodsorderhdService.txLoadForUpdate(GOODS_ORDER_ID);
			if (!BusinessConsts.GOODSORDER_STATE_DZZB.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}*/
			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			GoodsorderhdModel model = new Gson().fromJson(parentJsonData, new TypeToken <GoodsorderhdModel>() {}.getType());
			SaleorderModel xxddModel = new Gson().fromJson(parentJsonData, new TypeToken <SaleorderModel>() {}.getType());

			String childJsonData = ParamUtil.get(request, "childJsonData");
			List <SaleorderModel> xsddModelList = null;
			if (!StringUtil.isEmpty(childJsonData)) {
				xsddModelList = new Gson().fromJson(childJsonData, new TypeToken <List <SaleorderModel>>() {
				}.getType());
			}
			goodsorderhdService.txCrexsdd(GOODS_ORDER_ID, model, xxddModel, xsddModelList, userBean);
			//jsonResult = jsonResult(true, "操作成功");
			Map<String,String> p = new HashMap<String,String>();
			p.put("GOODS_ORDER_ID", GOODS_ORDER_ID);
			jsonResult = new Gson().toJson(new Result(true, p, "操作成功"));
		}catch(ServiceException s){
			jsonResult = jsonResult(false, s.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonResult = jsonResult(false, "操作失败");
			e.printStackTrace();
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * 根据单据主键获取该单据附件地址
	 * @param pkId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getAttPath(String pkId){
		String attPath = "";
		List<Map<String, String>> attList = InterUtil.findAttInfo(pkId);
		for (Iterator itAtt = attList.iterator(); itAtt.hasNext();) {
			Map<String, String> map = (Map<String, String>) itAtt.next();
			attPath += map.get("ATT_PATH");
			attPath += ",";
		}
		if (attPath.length() > 0) {
			attPath = attPath.substring(0, attPath.length()-1);
		}
		return attPath;
	}

	/**
	 * 查看预订单明细
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toLookAdvc")
	public String toLookAdvc(
			HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		@SuppressWarnings("unused")
		String GOODS_ORDER_ID =ParamUtil.get(request, "GOODS_ORDER_ID");
		String FROM_BILL_DTL_ID =ParamUtil.get(request, "FROM_BILL_DTL_ID");
		String GOODS_ORDER_DTL_ID =ParamUtil.get(request, "GOODS_ORDER_DTL_ID");

		if(!StringUtil.isEmpty(GOODS_ORDER_DTL_ID)){
			Map<String,String>params = new HashMap<String,String>();
			params.put("FROM_BILL_DTL_ID", FROM_BILL_DTL_ID);
			params.put("GOODS_ORDER_DTL_ID", GOODS_ORDER_DTL_ID);
			List <Map<String,Object>> result = goodsorderhdService.toQuertAvdcDtl(params);
			request.setAttribute("rst", result);
		}
		request.setAttribute("pvg",setPvg(userBean));
		return view(webPath,"Goodsorderhd_Look_AdvcDtl");
	}

	@RequestMapping(value = "/exportExcelDtl", method = { RequestMethod.GET, RequestMethod.POST })
	public void exportDtl(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<String, String>();
		try {
			String GOODS_ORDER_ID = ParamUtil.get(request, "GOODS_ORDER_ID");
			Map<String, Object> entry = goodsorderhdService.load(GOODS_ORDER_ID);
			String GOODS_ORDER_DTL_IDs = request.getParameter("GOODS_ORDER_DTL_IDS");
			// 没有零星领料Id可以直接跳转。
			if (!StringUtil.isEmpty(GOODS_ORDER_DTL_IDs)) {
				params.put("GOODS_ORDER_DTL_IDS", GOODS_ORDER_DTL_IDs);
			}
			List<Map<String, String>> list = goodsorderhdService.childQuery(GOODS_ORDER_ID);

			// excel数据上列显示的列明
			String tmpContentCn = "组号,产品编码,产品名称,单位,门洞尺寸,规格尺寸,"
					+ "材质,颜色,推向,玻璃,其他,系列,是否开锁孔,"
					+ "图纸,是否返利,数量,"
					+ "计算报价,折扣率(%),返利,最终报价,金额,备注"
					;
			//
			String tmpContent = "GROUP_NO,PRD_NO,PRD_NAME,STD_UNIT,HOLE_SPEC,PRD_SPEC,"
					+ "PRD_QUALITY,PRD_COLOR,PRD_TOWARD,PRD_GLASS,PRD_OTHER,PRD_SERIES,IS_NO_LOCK_FLAG_TEXT,"
					+ "attPath,IS_NO_REBATE_FLAG_TEXT,ORDER_NUM,"
					+ "PRICE,DECT_RATE,REBATE_AMOUNT,DECT_PRICE,ORDER_AMOUNT,REMARK";
			ExcelUtil.doExport(response, list, StringUtil.nullToSring(entry.get("BILL_TYPE")+"_"+entry.get("GOODS_ORDER_NO")), tmpContent, tmpContentCn);
		} catch (Exception e) {

			e.printStackTrace();
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
		pvgMap.put("PVG_APPLY", QXUtil.checkPvg(userBean, PVG_APPLY));
		pvgMap.put("PVG_QUOTE_CONFIRM",
				QXUtil.checkPvg(userBean, PVG_QUOTE_CONFIRM));
		pvgMap.put("PVG_FEEDBACK", QXUtil.checkPvg(userBean, PVG_FEEDBACK));
		pvgMap.put("PVG_START_STOP", QXUtil.checkPvg(userBean, PVG_START_STOP));
		pvgMap.put("PVG_COMMIT_CANCLE",
				QXUtil.checkPvg(userBean, PVG_COMMIT_CANCLE));
		pvgMap.put("PVG_TRACE", QXUtil.checkPvg(userBean, PVG_TRACE));
		pvgMap.put("PVG_FINISH_CANCLE",
				QXUtil.checkPvg(userBean, PVG_FINISH_CANCLE));

		pvgMap.put("PVG_BWS_SUB", QXUtil.checkPvg(userBean, PVG_BWS_SUB));
		pvgMap.put("PVG_QUOTE_SUB", QXUtil.checkPvg(userBean, PVG_QUOTE_SUB));
		pvgMap.put("PVG_EDIT_SUB", QXUtil.checkPvg(userBean, PVG_EDIT_SUB));
		pvgMap.put("PVG_BACK_SUB", QXUtil.checkPvg(userBean, PVG_BACK_SUB));

		pvgMap.put("PVG_BWS_AUDIT", QXUtil.checkPvg(userBean, PVG_BWS_AUDIT));
		pvgMap.put("PVG_TRACE_AUDIT", QXUtil.checkPvg(userBean, PVG_TRACE_AUDIT));
		pvgMap.put("PVG_AUDIT_AUDIT", QXUtil.checkPvg(userBean, PVG_AUDIT_AUDIT));
		pvgMap.put("PVG_QUOTE_AUDIT", QXUtil.checkPvg(userBean, PVG_QUOTE_AUDIT));
		pvgMap.put("PVG_CONFIRM_AUDIT", QXUtil.checkPvg(userBean, PVG_CONFIRM_AUDIT));
		pvgMap.put("PVG_CREXSDD_AUDIT", QXUtil.checkPvg(userBean, PVG_CREXSDD_AUDIT));
		pvgMap.put("PVG_BACK_AUDIT", QXUtil.checkPvg(userBean, PVG_BACK_AUDIT));
		pvgMap.put("PVG_EDIT_AUDIT", QXUtil.checkPvg(userBean, PVG_EDIT_AUDIT));

		pvgMap.put("PVG_DELETE_AUDIT", QXUtil.checkPvg(userBean, PVG_DELETE_AUDIT));
		pvgMap.put("PVG_COMMIT_AUDIT", QXUtil.checkPvg(userBean, PVG_COMMIT_AUDIT));

		pvgMap.put("xtyhid", userBean.getXTYHID());
		return pvgMap;
	}

}