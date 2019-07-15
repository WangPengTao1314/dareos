/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：BMXXAction.java
 */
package com.centit.erp.sale.saleorder.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.centit.common.controller.BaseController;
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
import com.centit.drp.main.sales.deliver.order.model.OrderModel;
import com.centit.drp.main.sales.deliver.order.model.SendDtlModel;
import com.centit.drp.main.sales.deliver.order.service.OrderService;
import com.centit.erp.sale.saleorder.model.ChangeApplyModel;
import com.centit.erp.sale.saleorder.model.SaleDesignerModel;
import com.centit.erp.sale.saleorder.model.SaleorderConsts;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.erp.sale.saleorder.model.SaleorderModelChld;
import com.centit.erp.sale.saleorder.service.ChangeApplyService;
import com.centit.erp.sale.saleorder.service.SaleorderService;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.XTYHService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @ClassName: SaleorderController
 * @Description: 销售订单控制器
 * @author wang_dw
 * @date 2019年3月1日 下午3:15:10
 *
 */
@Controller
@RequestMapping("/erp/saleorder")
public class SaleorderController extends BaseController {

	@Autowired
	private SaleorderService saleorderService;
	@Autowired
	private ChangeApplyService changeApplyService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private XTYHService xtyhService;

	private static final String webPath = "erp/sale/saleorder";
	private static final String webPath_good = "drp/sale/saleorderview";

	/** 日志 **/
	private Logger logger = LoggerFactory.getLogger(SaleorderController.class);
	/** 权限对象 **/
	/** 维护 */
	// 增删改查
	private static String PVG_BWS = "BS0010401";
	private static String PVG_EDIT = "BS0010402";
	private static String PVG_DELETE = "BS0010403";
	// 启用,停用
	private static String PVG_START_STOP = "";
	// 确认，取消
	private static String PVG_FINISH_CANCLE = "";
	// 转设计师
	private static String PVG_TURNDESIGNER = "BS0010404";
	// 转ERP
	private static String PVG_TURNERP = "BS0010405";
	// 导入
	private static String PVG_IMPORT = "BS0010406";
	// 设计师分派
	private static String PVG_ASSIGN = "BS0010407";
	// 设计师拆件
	private static String PVG_DESIGN = "BS0010408";
	// 退回
	private static String PVG_BACK = "BS0010409";
	// 生成发货指令
	private static String PVG_CREFHZL = "BS0010410";
	// 申请变更
	private static String PVG_APPLY = "BS0010411";
	/** end */
	/** 审批维护必须维护字段 **/
	// 提交撤销
	private static String PVG_COMMIT_CANCLE = "BS0010402";
	private static String PVG_TRACE = "BS0010703";
	// 审核模块
	private static String PVG_BWS_AUDIT = "BS0010501";
	private static String PVG_AUDIT_AUDIT = "?";
	private static String PVG_TRACE_AUDIT = "?";
	// 审批流参数
	private static String AUD_TAB_NAME = "ERP_SALE_ORDER";
	private static String AUD_TAB_KEY = "SALE_ORDER_ID";
	private static String AUD_BUSS_STATE = "";
	private static String AUD_BUSS_TYPE = "ERP_SALE_ORDER_AUDIT";
	private static String AUD_FLOW_INS = "com.hoperun.sys.service.PublicFlowInterface";
	/** 审批 end **/
	/** 经销商查看 **/
	private static String PVG_BWS_FX = "FX0020501";
	// 申请变更
	private static String PVG_APPLY_FX = "FX0020502";
	// 流程跟踪
	private static String PVG_TRACE_FX = "FX0020503";
	// 收货确认
	private static String PVG_CONFIRM_FX = "FX0020504";
	// 问题反馈
	private static String PVG_FEEDBACK_FX = "FX0020505";

	/** end */

	/**
	 * * to 框架页面
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping(value = "/toFrame", method = { RequestMethod.GET, RequestMethod.POST })
	public String toFrame(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS)
				&& !QXUtil.checkMKQX(userBean, PVG_BWS_FX))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		ParamUtil.setCommAttributeEx(request);
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
		request.setAttribute("HEAD", ParamUtil.utf8Decoder(request, "HEAD"));
		// 除去设计师权限还有其他权限则>0
		request.setAttribute("isShowPrice", LogicUtil.isShowPrice(userBean.getXTJSIDS()));
		return view(webPath, "Saleorder_Frame");
	}

	/**
	 * * query List data
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping(value = "/toList", method = { RequestMethod.GET, RequestMethod.POST })
	public String toList(HttpServletRequest request, HttpServletResponse response, PageDesc pageDesc) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS)
				&& !QXUtil.checkMKQX(userBean, PVG_BWS_FX))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		Map<String, Object> params = new HashMap<String, Object>();

		ParamUtil.putStr2Map(request, "FACTORY_NO", params);
		ParamUtil.putStr2Map(request, "PROESS_TYPE", params);
		ParamUtil.putStr2Map(request, "URGENCY_TYPE", params);
		ParamUtil.putStr2Map(request, "CHANN_NAME", params);
		ParamUtil.putStr2Map(request, "CUST_NAME", params);
		ParamUtil.putStr2Map(request, "DDSTATE", params);
		ParamUtil.putStr2Map(request, "STATE", params);
		ParamUtil.putStr2Map(request, "STATE2", params);
		ParamUtil.putStr2Map(request, "ORDER_DATE_BEGIN", params);
		ParamUtil.putStr2Map(request, "ORDER_DATE_END", params);
		ParamUtil.putStr2Map(request, "ORDER_DELIVERY_DATE_BEGIN", params);
		ParamUtil.putStr2Map(request, "ORDER_DELIVERY_DATE_END", params);
		ParamUtil.putStr2Map(request, "CUST_ADDR", params);
		ParamUtil.putStr2Map(request, "GOODS_ORDER_NO", params);
		ParamUtil.putStr2Map(request, "BILL_TYPE", params);
		params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		// params.put("LEDGER_IDS", userBean.getFGZTXXIDS());

		// 不查询'返修订单'
		params.put("NOT_BILL_TYPE", "返修订单");

		@SuppressWarnings("unused")
		String module = ParamUtil.get(request, "module");
		String search = ParamUtil.get(request, "search");
		String STATE = ParamUtil.get(request, "STATE");

		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商， 0：总部
		if (IS_DRP_LEDGER.equals("0")) { // 订单中心 订货处理
			String xtjsids = userBean.getXTJSIDS();
			if (xtjsids.contains(BusinessConsts.DESIGNER_XTJSID)) {
				params.put("DESIGNER", userBean.getXTYHID());// 总部-拆件师
				if (StringUtil.isEmpty(search)) {
					params.put("STATE", BusinessConsts.GOODSORDER_STATE_SJSCJ);
				} else {
					params.put("STATES", "'" + BusinessConsts.GOODSORDER_STATE_SJSCJ + "','"
							+ BusinessConsts.GOODSORDER_STATE_DSC + "','"
							+ BusinessConsts.INPRODUCTTION + "','"
							+ BusinessConsts.DELIVERYED + "','"
							+ BusinessConsts.COMPLETED + "'");
					if(!StringUtil.isEmpty(STATE)){
						params.put("STATE",STATE);
					}
				}
			} else {
				params.put("billTypeQuery", LogicUtil.getProjectQx(userBean, "u"));
				params.put("CHANN_IDS", userBean.getCHANNS_CHARG());
				// 默认查询未处理状态
				/*
				params.put("STATES", "'" + BusinessConsts.GOODSORDER_STATE_CG
						+ "','" + BusinessConsts.WAIT_DEAL + "','"
						+ BusinessConsts.GOODSORDER_STATE_FPSJS + "','"
						+ BusinessConsts.GOODSORDER_STATE_SJSCJ + "','"
						+ BusinessConsts.GOODSORDER_STATE_DSC + "','"
						+ BusinessConsts.INPRODUCTTION + "','"
						+ BusinessConsts.DELIVERYED + "','"
						+ BusinessConsts.COMPLETED + "'");*/
				if(StringUtil.isEmpty(search)){/*
					params.put("STATES", "'" + BusinessConsts.GOODSORDER_STATE_CG
							+ "','" + BusinessConsts.WAIT_DEAL + "','"
							+ BusinessConsts.GOODSORDER_STATE_FPSJS + "','"
							+ BusinessConsts.GOODSORDER_STATE_SJSCJ + "','"
							+ BusinessConsts.GOODSORDER_STATE_DSC + "'");*/
					// 初始化查询状态默认显示
					String STATES = LogicUtil.getOrderQxByUserBean(userBean, PVG_BWS);
					// System.err.println(STATES);
					params.put("STATES", STATES);
				} else {
					if (!StringUtil.isEmpty(STATE)) {
						params.put("STATE", STATE);
					}
				}
			}
		} else if (IS_DRP_LEDGER.equals("1")) { // 经销商订货管理
			params.put("CHANN_ID", userBean.getCHANN_ID());
			if (StringUtil.isEmpty(search)) {
				// 初始化查询状态默认显示
				String STATES = LogicUtil.getOrderQxByUserBean(userBean, PVG_BWS_FX);
				// System.err.println(STATES);
				params.put("STATES", STATES);
			} else {
				if (!StringUtil.isEmpty(STATE)) {
					params.put("STATE", STATE);
				}
			}
		}
		request.setAttribute("IS_DRP_LEDGER", IS_DRP_LEDGER);

		// 字段排序
		ParamUtil.setOrderField(request, params, "u.RETURN_SHOW_FLAG DESC, u.UPD_TIME", "DESC");
		ParamUtil.putStr2Map(request, "pageSize", params);
		saleorderService.pageQuery(params, pageDesc);
		request.setAttribute("params", params);
		request.setAttribute("pvg", setPvg(userBean));
		request.setAttribute("page", pageDesc);
		return view(webPath, "Saleorder_List");
	}

	/**
	 * * 明细列表
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping(value = "/childList", method = { RequestMethod.GET, RequestMethod.POST })
	public String childList(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS)
				&& !QXUtil.checkMKQX(userBean, PVG_BWS_FX))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		String SALE_ORDER_ID = ParamUtil.get(request, "selRowId");
		String module = ParamUtil.get(request, "module");
		if (!StringUtil.isEmpty(SALE_ORDER_ID)) {
//        	 List <SaleorderModelChld> result = saleorderService.childQuery(SALE_ORDER_ID);
			List<Map<String, String>> result = saleorderService.childQuery(SALE_ORDER_ID);
			if (null != result) {
				request.setAttribute("resultSize", result.size());
			}
			request.setAttribute("rst", result);

		}
		request.setAttribute("module", module);
		request.setAttribute("pvg", setPvg(userBean));
//        return mapping.findForward("childlist");
		return view(webPath, "Saleorder_List_Chld");
	}


	/**
	 * * to 主表编辑页面初始化
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/toEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public String toParentEdit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		/**
		 * 系统的当前时间
		 */
//        SimpleDateFormat currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat currentTime = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("currentTime", currentTime.format(new Date()));
		/**
		 * 设置订单交期时间(+25天)
		 */
		SimpleDateFormat order_delivery_date = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, 25);
		request.setAttribute("order_delivery_date", order_delivery_date.format(calendar.getTime()));
		String SALE_ORDER_ID = ParamUtil.get(request, "selRowId");
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商， 0：总部
		String xtbs = "";
		Map<String, Object> entry = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(SALE_ORDER_ID)) {
			entry = saleorderService.load(SALE_ORDER_ID);
			// 主表附件
			String attPathP = LogicUtil.getAttPath(SALE_ORDER_ID);
			entry.put("attPath", attPathP); // 附件图纸 可能有多个

			List<Map<String, String>> childList = saleorderService.childQuery(SALE_ORDER_ID);
			for (Iterator it = childList.iterator(); it.hasNext();) {
				Map<String, String> child = (Map<String, String>) it.next();
				String attPath = LogicUtil.getAttPath(child.get("SALE_ORDER_DTL_ID"));
				child.put("attPath", attPath);
			}
			request.setAttribute("rstChild", childList);

		} else {
			entry.put("SALE_ID", userBean.getXTYHID());
			entry.put("SALE_NAME", userBean.getXM());
			entry.put("SALEDEPT_NAME", userBean.getBMMC());
			entry.put("SALEDEPT_ID", userBean.getBMXXID());

			//设置订单交期时间(+25天)
			Date date = DateUtil.addDays(DateUtil.getDate(), 25);
			String ORDER_DELIVERY_DATE = DateUtil.formatDateToStr(date);
			entry.put("ORDER_DELIVERY_DATE", ORDER_DELIVERY_DATE);
			date = DateUtil.addDays(date, 7);
			String PRE_RECV_DATE = DateUtil.formatDateToStr(date);
			entry.put("PRE_RECV_DATE", PRE_RECV_DATE);

			entry.put("TRANSPORT", "汽运");
			if (IS_DRP_LEDGER.equals("0")) {
				// 分管账套唯一则默认带出
				List<Map<String, String>> result = xtyhService.getLedgerChrgList(userBean.getXTYHID());
				if (null != result && result.size() == 1) {
					String LEDGER_ID = result.get(0).get("LEDGER_ID");
					entry.put("LEDGER_ID", LEDGER_ID);
				}
			}

			// String no = LogicUtil.getBmgz("SALE_ORDER_NO_SEQ");
			// entry.put("SALE_ORDER_NO", no);
			// entry.put("FACTORY_NO", no);

			request.setAttribute("userBean", userBean);
		}

		String LEDGER_ID = StringUtil.nullToSring(entry.get("LEDGER_ID"));
		xtbs = Consts.FACTORY_NO_CONF.getString(LEDGER_ID);// 系统标识
		request.setAttribute("xtbs", xtbs);

		// 获取返利折扣
		String CHANN_ID = StringUtil.nullToSring(entry.get("CHANN_ID"));
		Map<String,String> result = LogicUtil.getLedgerChrg(CHANN_ID, LEDGER_ID);
		if(null != result){
			entry.put("REBATEDSCT", result.get("DECT_RATE"));//返利折扣
			entry.put("REBATE_TOTAL", result.get("REBATE_TOTAL"));//返利总金额
			entry.put("REBATE_CURRT", result.get("REBATE_CURRT"));//当前返利
			//entry.put("REBATE_CURRT", "20000");
			entry.put("REBATE_FREEZE", result.get("REBATE_FREEZE"));//冻结的返利
		}

		request.setAttribute("rst", entry);
		request.setAttribute("IS_DRP_LEDGER", IS_DRP_LEDGER);
		request.setAttribute("XTYHID", userBean.getXTYHID());
		return view(webPath, "Saleorder_Edit");
	}

	@RequestMapping("/getFactoryNo")
	public void getFactoryNo(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		Map<String, Object> entry = new HashMap<String, Object>();
		try {
			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			SaleorderModel model = new Gson().fromJson(parentJsonData, new TypeToken <SaleorderModel>() {}.getType());

			String BILL_TYPE = model.getBILL_TYPE();
			if (StringUtil.isEmpty(BILL_TYPE)) {
				throw new ServiceException("请输入销售类型!");
			}

			String ORDER_DELIVERY_DATE = model.getORDER_DELIVERY_DATE();
			if (StringUtil.isEmpty(ORDER_DELIVERY_DATE)) {
				Date date = DateUtil.addDays(DateUtil.getDate(), 40);
				ORDER_DELIVERY_DATE = DateUtil.formatDateToStr(date);
				entry.put("ORDER_DELIVERY_DATE", ORDER_DELIVERY_DATE);
				date = DateUtil.addDays(date, 7);
				String PRE_RECV_DATE = DateUtil.formatDateToStr(date);
				entry.put("PRE_RECV_DATE", PRE_RECV_DATE);
			}
			String xtbs = Consts.FACTORY_NO_CONF.getString(model.getLEDGER_ID());// 系统标识
			entry.put("xtbs", xtbs);

			String orderNo = LogicUtil.getFactoryNo(model.getLEDGER_ID(), model.getORDER_DELIVERY_DATE(), model.getBILL_TYPE());
			entry.put("FACTORY_NO", orderNo);
			// entry.put("FACTORY_NO", "H0810-N1906-0319");
			jsonResult = JSONObject.toJSONString(new Result(entry));
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

	@RequestMapping("/changeFactoryNo")
	public void changeFactoryNo(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			SaleorderModel model = new Gson().fromJson(parentJsonData, new TypeToken <SaleorderModel>() {}.getType());

			String ORDER_DELIVERY_DATE = getString(request, "ORDER_DELIVERY_DATE");
			// String FACTORY_NO = getString(request, "FACTORY_NO");
			Date date = DateUtil.addDays(DateUtil.parseDate(ORDER_DELIVERY_DATE), 7);
			String PRE_RECV_DATE = DateUtil.formatDateToStr(date);
			model.setPRE_RECV_DATE(PRE_RECV_DATE);

			String orderNo = model.getFACTORY_NO();
			model.setFACTORY_NO(orderNo);
			jsonResult = JSONObject.toJSONString(new Result(model));
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
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		// 经销商整单复制
		String SALE_ORDER_ID = ParamUtil.get(request, "selRowId");
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商， 0：总部
		String xtbs = "";
		Map<String, Object> entry = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(SALE_ORDER_ID)) {
			entry = saleorderService.load(SALE_ORDER_ID);
			// 主表附件
			String attPathP = LogicUtil.getAttPath(SALE_ORDER_ID);
			entry.put("attPath", attPathP); // 附件图纸 可能有多个
			entry.put("SALE_ORDER_ID", "");
			entry.put("SALE_ORDER_NO", "");

			List<Map<String, String>> childList = saleorderService.childQuery(SALE_ORDER_ID);
			for (Iterator it = childList.iterator(); it.hasNext();) {
				Map<String, String> child = (Map<String, String>) it.next();
				String attPath = LogicUtil.getAttPath(child.get("SALE_ORDER_DTL_ID"));
				child.put("attPath", attPath);
				child.put("SALE_ORDER_DTL_ID", "");
				child.put("SALE_ORDER_ID", "");/*
				child.put("PRICE", BusinessConsts.INTEGER_0);
				child.put("DECT_RATE", "100");
				child.put("DECT_PRICE", BusinessConsts.INTEGER_0);
				child.put("REBATE_PRICE", BusinessConsts.INTEGER_0);
				child.put("REBATE_AMOUNT", BusinessConsts.INTEGER_0);
				child.put("ORDER_NUM", BusinessConsts.INTEGER_0);
				child.put("ORDER_AMOUNT", BusinessConsts.INTEGER_0);
				child.put("SENDED_NUM", BusinessConsts.INTEGER_0);*/
			}
			request.setAttribute("rstChild", childList);
		}
		String LEDGER_ID = StringUtil.nullToSring(entry.get("LEDGER_ID"));
		xtbs = Consts.FACTORY_NO_CONF.getString(LEDGER_ID);// 系统标识
		request.setAttribute("xtbs", xtbs);

		request.setAttribute("rst", entry);
		request.setAttribute("IS_DRP_LEDGER", IS_DRP_LEDGER);
		request.setAttribute("XTYHID", userBean.getXTYHID());
		return view(webPath, "Saleorder_Edit");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/copyToSave")
	public void copyToSave(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
			Map<String, Object> entry = saleorderService.load(SALE_ORDER_ID);
			// 主表附件
			String attPathP = LogicUtil.getAttPath(SALE_ORDER_ID);
			entry.put("attPath", attPathP); // 附件图纸 可能有多个
			entry.put("SALE_ORDER_ID", "");
			SaleorderModel model = (SaleorderModel) LogicUtil.tranMap2Bean(entry, SaleorderModel.class);
			String orderNo = LogicUtil.getFactoryNo(model.getLEDGER_ID(), model.getORDER_DELIVERY_DATE(), model.getBILL_TYPE());
			model.setFACTORY_NO(orderNo);

			List childs = saleorderService.childQuery(SALE_ORDER_ID);
			List<SaleorderModelChld> result = new LinkedList<SaleorderModelChld>();
			for (Iterator it = childs.iterator(); it.hasNext();) {
				Map<String, Object> child = (Map<String, Object>) it.next();
				String attPath = LogicUtil.getAttPath(child.get("SALE_ORDER_DTL_ID").toString());
				child.put("attPath", attPath);
				child.put("SALE_ORDER_DTL_ID", "");
				child.put("SALE_ORDER_ID", "");
				result.add((SaleorderModelChld) LogicUtil.tranMap2Bean(child, SaleorderModelChld.class));
			}
			saleorderService.txEdit(SALE_ORDER_ID, model, result, userBean);
			jsonResult = jsonResult(true, "复制成功");
		} catch (ServiceException s) {
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
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping(value = "/modifyToChildEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyToChildEdit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		String SALE_ORDER_ID = ParamUtil.get(request, "selRowId");
		if (!StringUtil.isEmpty(SALE_ORDER_ID)) {
			List<Map<String, String>> result = saleorderService.childQuery(SALE_ORDER_ID);
			request.setAttribute("rst", result);
		}
//        return mapping.findForward("childedit");
		return view(webPath, "Saleorder_Edit_Chld");
	}

	/**
	 * * to 直接编辑明细页面
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping(value = "/toChildEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public String toChildEdit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		// 多个Id批量查询，用逗号隔开
		String SALE_ORDER_DTL_IDs = request.getParameter("SALE_ORDER_DTL_IDS");
		// 没有零星领料Id可以直接跳转。
		if (!StringUtil.isEmpty(SALE_ORDER_DTL_IDs)) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("SALE_ORDER_DTL_IDS", SALE_ORDER_DTL_IDs);
			List<Map<String, String>> list = saleorderService.loadChilds(params);
			request.setAttribute("rst", list);
		}
//        return mapping.findForward("childedit");
		return view(webPath, "Saleorder_Edit_Chld");
	}

	/**
	 * * 主表 新增/修改数据
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
	public void edit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			SaleorderModel model = new Gson().fromJson(parentJsonData, new TypeToken<SaleorderModel>() {
			}.getType());
			String jsonDate = ParamUtil.get(request, "childJsonData");
			List<SaleorderModelChld> chldModelList = null;
			if (!StringUtil.isEmpty(jsonDate)) {
				chldModelList = new Gson().fromJson(jsonDate, new TypeToken<List<SaleorderModelChld>>() {
				}.getType());
			}
			saleorderService.txEdit(SALE_ORDER_ID, model, chldModelList, userBean);
			jsonResult = jsonResult(true, "保存成功");
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

	@RequestMapping("/edittocommit")
	public void editToCcommit(HttpServletRequest request, HttpServletResponse response,String state) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			SaleorderModel model = new Gson().fromJson(parentJsonData, new TypeToken<SaleorderModel>() {
			}.getType());
			String jsonDate = ParamUtil.get(request, "childJsonData");
			List<SaleorderModelChld> chldModelList = null;
			if (!StringUtil.isEmpty(jsonDate)) {
				chldModelList = new Gson().fromJson(jsonDate, new TypeToken<List<SaleorderModelChld>>() {
				}.getType());
			}

			saleorderService.txEditToCommit(SALE_ORDER_ID, model, chldModelList, userBean);
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
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping(value = "/childEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public void childEdit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String SALE_ORDER_ID = request.getParameter("SALE_ORDER_ID");
			String BILL_TYPE = request.getParameter("BILL_TYPE");
			String jsonDate = request.getParameter("childJsonData");
			if (!StringUtil.isEmpty(jsonDate)) {
				List<SaleorderModelChld> modelList = new Gson().fromJson(jsonDate,
						new TypeToken<List<SaleorderModelChld>>() {
						}.getType());
				saleorderService.txChildEdit(SALE_ORDER_ID, modelList, userBean, "list", BILL_TYPE);
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
	 * * 主表 删除
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
//		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_DELETE))
//    	{
//    		throw new ServiceException("对不起，您没有权限 !");
//    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
			Map<String, String> params = new HashMap<String, String>();
			params.put("SALE_ORDER_ID", SALE_ORDER_ID);
			params.put("UPD_NAME", userBean.getXM());
			params.put("UPDATOR", userBean.getXTYHID());
			params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_DROP);
			saleorderService.txDelete(params);
//			saleorderService.clearCaches(params);
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
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping(value = "/childDelete", method = { RequestMethod.GET, RequestMethod.POST })
	public void childDelete(HttpServletRequest request, HttpServletResponse response) {
//    	UserBean userBean = ParamUtil.getUserBean(request);
//    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_DELETE))
//    	{
//    		throw new ServiceException("对不起，您没有权限 !");
//    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String SALE_ORDER_DTL_IDs = request.getParameter("SALE_ORDER_DTL_IDS");
			// 批量删除，多个Id之间用逗号隔开
			saleorderService.txBatch4DeleteChild(SALE_ORDER_DTL_IDs);
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
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/toDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public String toDetail(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS) && !QXUtil.checkMKQX(userBean, PVG_BWS_FX))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商， 0：总部
		String SALE_ORDER_ID = ParamUtil.get(request, "selRowId");
		if (!StringUtil.isEmpty(SALE_ORDER_ID)) {
			Map<String, Object> entry = saleorderService.load(SALE_ORDER_ID);
			// 主表附件
			String attPathP = LogicUtil.getAttPath(SALE_ORDER_ID);
			entry.put("attPath", attPathP); // 附件图纸 可能有多个
			// 拆件计料表附件
			String no = StringUtil.nullToSring(entry.get("FACTORY_NO"));
			String attPath_cj = LogicUtil.getAttPath(no);
			entry.put("attPath_cj", attPath_cj); // 附件图纸 可能有多个
			request.setAttribute("rst", entry);

			List<Map<String, String>> result = saleorderService.childQuery(SALE_ORDER_ID);
			for (Iterator it = result.iterator(); it.hasNext();) {
				Map<String, String> child = (Map<String, String>) it.next();
				String attPath = LogicUtil.getAttPath(child.get("SALE_ORDER_DTL_ID"));
				child.put("attPath", attPath);
			}
			request.setAttribute("rstChild", result);
		}
		request.setAttribute("IS_DRP_LEDGER", IS_DRP_LEDGER);
//		return mapping.findForward("todetail");
		return view(webPath, "Saleorder_Detail");
	}

	/**
	 * * 提交时，校验是否有明细.
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping(value = "/toCommit", method = { RequestMethod.GET, RequestMethod.POST })
	public void toCommit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_COMMIT_CANCLE)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String errorId = "";
		try {
			String SALE_ORDER_ID = request.getParameter("SALE_ORDER_ID");
			Map<String, Object> entry = saleorderService.load(SALE_ORDER_ID);
			if (!BusinessConsts.GOODSORDER_STATE_CG.equals(entry.get("STATE"))
					&& !BusinessConsts._BACK.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}
			// String SALE_ORDER_NO = StringUtil.nullToSring(entry.get("SALE_ORDER_NO"));
			List<Map<String, String>> list = saleorderService.childQuery(SALE_ORDER_ID);
			if (list.size() == 0) {
				errorId = "0";
				throw new Exception();
			} else {
				// saleorderService.txToCommit(SALE_ORDER_ID, SALE_ORDER_NO, userBean);

				SaleorderModel model = (SaleorderModel) LogicUtil.tranMap2Bean(entry, SaleorderModel.class);
				saleorderService.txCommit(SALE_ORDER_ID, model, userBean);
				jsonResult = jsonResult(true, "提交成功");
			}
		} catch (ServiceException e) {
			jsonResult = jsonResult(false, e.getMessage());
		} catch (Exception e) {
			if ("0".equals(errorId)) {
				jsonResult = jsonResult(false, "没有明细，不能提交!");
			} else {
				jsonResult = jsonResult(false, "提交失败");
			}
			logger.error(e.getMessage());
			// e.printStackTrace();
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * to 变更申请
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping(value = "/toapply", method = { RequestMethod.GET, RequestMethod.POST })
	public String toApply(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_APPLY_FX)
				&& !QXUtil.checkMKQX(userBean, PVG_APPLY)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商， 0：总部
		String SALE_ORDER_ID = ParamUtil.get(request, "selRowId");
		if (!StringUtil.isEmpty(SALE_ORDER_ID)) {
			// 判断是否已存在未处理完成的变更申请
			Map<String, Object> entry = saleorderService.load(SALE_ORDER_ID);
			request.setAttribute("rst", entry);
		}
		request.setAttribute("IS_DRP_LEDGER", IS_DRP_LEDGER);
		return view(webPath, "Saleorder_Apply");
	}

	/**
	 * 销售订单变更申请-保存
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/apply")
	public void apply(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_APPLY_FX)
				&& !QXUtil.checkMKQX(userBean, PVG_APPLY)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String CHANGE_APPLY_ID = ParamUtil.get(request, "CHANGE_APPLY_ID");
			if (!StringUtil.isEmpty(CHANGE_APPLY_ID)) {
				Map<String, Object> entry = changeApplyService.load(CHANGE_APPLY_ID);
				if (!BusinessConsts.GOODSORDER_STATE_CG.equals(entry.get("APPLYSTATE"))) {
					throw new ServiceException("当前单据状态已更改，请刷新后重试！");
				}
			}

			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			ChangeApplyModel model = new Gson().fromJson(parentJsonData, new TypeToken <ChangeApplyModel>() {}.getType());
			changeApplyService.txEdit(CHANGE_APPLY_ID, model, userBean);

			jsonResult = jsonResult(true, "保存成功");
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
	 * 销售订单变更申请-提交
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/applytocommit")
	public void applyToCommit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_APPLY_FX)
				&& !QXUtil.checkMKQX(userBean, PVG_APPLY)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String CHANGE_APPLY_ID = ParamUtil.get(request, "CHANGE_APPLY_ID");
			if (!StringUtil.isEmpty(CHANGE_APPLY_ID)) {
				Map<String, Object> entry = changeApplyService.load(CHANGE_APPLY_ID);
				if (!BusinessConsts.GOODSORDER_STATE_CG.equals(entry.get("APPLYSTATE"))) {
					throw new ServiceException("当前单据状态已更改，请刷新后重试！");
				}
			}

			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			ChangeApplyModel model = new Gson().fromJson(parentJsonData, new TypeToken <ChangeApplyModel>() {}.getType());
			changeApplyService.txCommit(CHANGE_APPLY_ID, model, userBean);

			jsonResult = jsonResult(true, "提交成功");
		} catch (ServiceException s) {
			jsonResult = jsonResult(false, s.getMessage());
		} catch (Exception e) {
			jsonResult = jsonResult(false, "提交失败");
			e.printStackTrace();
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * 销售订单处理-退回到草稿
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/back")
	public void back4gc(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_TURNDESIGNER)
				&& !QXUtil.checkMKQX(userBean, PVG_TURNERP)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String selRowId = ParamUtil.get(request, "SALE_ORDER_ID");
			if (!StringUtil.isEmpty(selRowId)) {
				Map<String, Object> entry = saleorderService.load(selRowId);
				if (!"待处理".equals(StringUtil.nullToSring(entry.get("STATE")))
						&& !"待生产".equals(StringUtil.nullToSring(entry.get("STATE")))) {
					throw new ServiceException("当前单据状态已更改，请刷新后重试！");
				}
				String auditContents = ParamUtil.get(request, "auditContents");
				SaleorderModel model = (SaleorderModel) LogicUtil.tranMap2Bean(entry, SaleorderModel.class);
				model.setAuditContents(auditContents);
				saleorderService.back4gc(selRowId, model, userBean);
			}

			jsonResult = jsonResult(true, "操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonResult = jsonResult(false, "操作失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * 销售订单处理-转设计师
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/turnDesigner")
	public void turnDesigner(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_TURNDESIGNER)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
			Map<String, Object> entry = saleorderService.load(SALE_ORDER_ID);
			if (!BusinessConsts.WAIT_DEAL.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}
			// String no = StringUtil.nullToSring(entry.get("SALE_ORDER_NO"));
			// String flowServiceId = StringUtil.nullToSring(entry.get("ORDER_TRACE_ID"));
			// String state = StringUtil.nullToSring(entry.get("STATE"));
			SaleorderModel model = (SaleorderModel) LogicUtil.tranMap2Bean(entry, SaleorderModel.class);
			saleorderService.turnDesigner(SALE_ORDER_ID, model, userBean);
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
	 * 销售订单处理-转ERP
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/turnERP")
	public void turnERP(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_TURNERP)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
			Map<String, Object> entry = saleorderService.load(SALE_ORDER_ID);
			if (!BusinessConsts.WAIT_DEAL.equals(entry.get("STATE"))
					&& !BusinessConsts.GOODSORDER_STATE_DSC.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}
			// String no = StringUtil.nullToSring(entry.get("SALE_ORDER_NO"));
			// String flowServiceId = StringUtil.nullToSring(entry.get("ORDER_TRACE_ID"));
			SaleorderModel model = (SaleorderModel) LogicUtil.tranMap2Bean(entry, SaleorderModel.class);
			saleorderService.turnERP(SALE_ORDER_ID, model, userBean);
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

	@RequestMapping("/ERPback")
	public void ERPback(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG
				&& !QXUtil.checkMKQX(userBean, PVG_TURNERP)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String selRowId = ParamUtil.get(request, "SALE_ORDER_ID");
			String reason = ParamUtil.get(request, "reason");
			if(!StringUtil.isEmpty(selRowId)){
				// TODO 判断是否存在发货指令
				boolean isExists = false;
				if (isExists) {
					throw new ServiceException("请先删除发货指令！");
				}

				Map<String, Object> entry = saleorderService.load(selRowId);
				/*if (!"草稿".equals(StringUtil.nullToSring(model.get("STATE")))
						&& !"退回".equals(StringUtil.nullToSring(model.get("STATE")))) {
					throw new ServiceException("该单据已经提交！");
				}*/
				SaleorderModel model = (SaleorderModel) LogicUtil.tranMap2Bean(entry, SaleorderModel.class);
				model.setAuditContents(reason);
				saleorderService.txERPBack(selRowId, model, userBean);
			}

			jsonResult = jsonResult(true, "操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonResult = jsonResult(false, "操作失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * 销售订单处理-to分派设计师
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping("/toAssign")
	public String toAssign(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_ASSIGN)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		String SALE_ORDER_ID = ParamUtil.get(request, "selRowId");
		String SALE_ORDER_NO = ParamUtil.get(request, "FACTORY_NO");
		String LEDGER_ID = ParamUtil.get(request, "LEDGER_ID");
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商， 0：总部
		Map<String, Object> entry = saleorderService.loadAssignByFkid(SALE_ORDER_ID);
		if (null == entry) {
			entry = new HashMap<String, Object>();
			entry.put("SALE_ORDER_ID", SALE_ORDER_ID);
			entry.put("SALE_ORDER_NO", SALE_ORDER_NO);
		}

		// 设计师分派情况
		Map<String, String> params = new HashMap<>();
		params.put("LEDGER_ID", LEDGER_ID);
		params.put("DESIGNER_XTJSID", BusinessConsts.DESIGNER_XTJSID);// 总部-拆件师

		List<Map<String, Object>> assignList = saleorderService.assignQueryTotal(params);
		List<Map<String, Object>> assignDtlList = saleorderService.assignQuery(params);
		entry = convertRow2Col(entry, assignList, assignDtlList);

		request.setAttribute("entry", entry);
		request.setAttribute("IS_DRP_LEDGER", IS_DRP_LEDGER);
		return view(webPath, "Saleorder_Assign");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Object> convertRow2Col(Map<String, Object> entry, List<Map<String, Object>> assignList,
			List<Map<String, Object>> assignDtlList) {
		// 最近3天
		List<String> dateList = new ArrayList<String>();
		for (int i = 1; i <= 3; i++) {
			Date date = DateUtil.beforeDate(DateUtil.getDate(), i);
			String dateStr = DateUtil.formatDateToStr(date);
			dateList.add(dateStr);
		}
		entry.put("dateList", dateList);

		for (Iterator it = assignList.iterator(); it.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) it.next();
			String ryxxid = StringUtil.nullToSring(map.get("RYXXID"));

			// 最近3天
			for (int i = 1; i <= 3; i++) {
				String dateStr = dateList.get(i - 1);
				String key = "DAY" + (i - 1);
				for (Iterator itDtl = assignDtlList.iterator(); itDtl.hasNext();) {
					Map<String, Object> mapDtl = (Map<String, Object>) itDtl.next();
					String DESIGNER = StringUtil.nullToSring(mapDtl.get("RYXXID"));
					String ASSIGN_TIME = StringUtil.nullToSring(mapDtl.get("ASSIGN_TIME"));
					if (ryxxid.equals(DESIGNER) && ASSIGN_TIME.length() == 0) {
						map.put(key + "TTL", "0");
						map.put(key + "UNFIN", "0");
					}
					if (ryxxid.equals(DESIGNER) && dateStr.equals(ASSIGN_TIME)) {
						map.put(key + "TTL", mapDtl.get("CNT_TTL"));
						map.put(key + "UNFIN", mapDtl.get("CNT_UNFIN"));
					}
				}

			}
		}

		entry.put("assignList", assignList);
		return entry;
	}

	/**
	 * 销售订单处理-分派设计师
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/assign")
	public void assign(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_ASSIGN)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
			Map<String, Object> entry = saleorderService.load(SALE_ORDER_ID);
			if (!BusinessConsts.GOODSORDER_STATE_FPSJS.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}

			String DESIGNER_ID = ParamUtil.get(request, "DESIGNER_ID");
			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			SaleDesignerModel model = new Gson().fromJson(parentJsonData, new TypeToken <SaleDesignerModel>() {}.getType());
			SaleorderModel ddModel = (SaleorderModel) LogicUtil.tranMap2Bean(entry, SaleorderModel.class);
			String ORDER_TRACE_ID = StringUtil.nullToSring(entry.get("ORDER_TRACE_ID"));
			model.setORDER_TRACE_ID(ORDER_TRACE_ID);
			String msg = saleorderService.assignEdit(DESIGNER_ID, model, ddModel, userBean);
			jsonResult = jsonResult(true, msg);

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
	 * 销售订单处理-to设计师拆件
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping("/toDesign")
	public String toDesign(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_DESIGN)
				&& !QXUtil.checkMKQX(userBean, PVG_TURNERP)) {
			throw new ServiceException("对不起，您没有权限 !");
		}

		String SALE_ORDER_ID = ParamUtil.get(request, "selRowId");
		String option = ParamUtil.get(request, "option"); // 总部审图、报价
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商， 0：总部
		if (!StringUtil.isEmpty(SALE_ORDER_ID)) {
			Map<String, Object> entry = saleorderService.load(SALE_ORDER_ID);
			// 主表附件
			String attPathP = LogicUtil.getAttPath(SALE_ORDER_ID);
			entry.put("attPath", attPathP); // 附件图纸 可能有多个
			// 拆件计料表附件
			String no = StringUtil.nullToSring(entry.get("FACTORY_NO"));
			String attPath_cj = LogicUtil.getAttPath(no);
			entry.put("attPath_cj", attPath_cj); // 附件图纸 可能有多个
			request.setAttribute("rst", entry);

			List<Map<String, String>> result = saleorderService.childQuery(SALE_ORDER_ID);
			for (Map<String, String> child : result) {
				String attPath = LogicUtil.getAttPath(child.get("SALE_ORDER_DTL_ID"));
				child.put("attPath", attPath);
			}
			request.setAttribute("rstChild", result);
		}
		request.setAttribute("option", option);
		request.setAttribute("IS_DRP_LEDGER", IS_DRP_LEDGER);
		request.setAttribute("XTYHID", userBean.getXTYHID());
		return view(webPath, "Saleorder_Edit");
	}

	/**
	 * 销售订单处理-设计师拆件
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping("/design")
	public void design(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_DESIGN)) {
			throw new ServiceException("对不起，您没有权限!");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
			Map<String, Object> entry = saleorderService.load(SALE_ORDER_ID);
			if (!BusinessConsts.GOODSORDER_STATE_SJSCJ.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}

			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			SaleorderModel xsddModel = new Gson().fromJson(parentJsonData, new TypeToken <SaleorderModel>() {}.getType());
			SaleDesignerModel model = new Gson().fromJson(parentJsonData, new TypeToken <SaleDesignerModel>() {}.getType());
			String msg = saleorderService.txDesign(SALE_ORDER_ID, model, xsddModel, userBean);
			jsonResult = jsonResult(true, msg);

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
	 * 销售订单处理-退回到上一节点
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping("/back2pre")
	public void back(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);/*
		if (Consts.FUN_CHEK_PVG
				&& !QXUtil.checkMKQX(userBean, PVG_BACK)) {
			throw new ServiceException("对不起，您没有权限 !");
		}*/
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String selRowId = ParamUtil.get(request, "selRowId");
			String reason = ParamUtil.get(request, "reason");
			if(!StringUtil.isEmpty(selRowId)){
				Map<String, Object> entry = saleorderService.load(selRowId);
				/*if (!"草稿".equals(StringUtil.nullToSring(model.get("STATE")))
						&& !"退回".equals(StringUtil.nullToSring(model.get("STATE")))) {
					throw new ServiceException("该单据已经提交！");
				}*/
				SaleorderModel model = (SaleorderModel) LogicUtil.tranMap2Bean(entry, SaleorderModel.class);
				if (!StringUtil.isEmpty(reason)) {
					model.setAuditContents(reason);
				}
				saleorderService.txBack(selRowId, model, userBean);
			}

			jsonResult = jsonResult(true, "退回成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonResult = jsonResult(false, "操作失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * * to 订货订单框架页面
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping(value = "/toGoodsFrame", method = { RequestMethod.GET, RequestMethod.POST })
	public String toGoodsFrame(HttpServletRequest request, HttpServletResponse response) {
		ParamUtil.setCommAttributeEx(request);
		String paramUrl = ParamUtil.utf8Decoder(request, "paramUrl");
		String search = ParamUtil.get(request, "search");
		String SALE_ORDER_ID = ParamUtil.get(request, "selRowId");
		request.setAttribute("paramUrl", paramUrl);
		request.setAttribute("search", search);
		request.setAttribute("SALE_ORDER_ID", SALE_ORDER_ID);
//		return mapping.findForward("goodsFrames");
		return view(webPath_good, "SaleorderviewGoods_Frame");
	}

	/**
	 * * query List data
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping(value = "/toGoodsList", method = { RequestMethod.GET, RequestMethod.POST })
	public String toGoodsList(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		Map<String, String> params = new HashMap<String, String>();
		params.put("SALE_ORDER_ID", ParamUtil.get(request, "selRowId"));
		// 只查询0的记录。1为删除。0为正常
		params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		// 字段排序
//		ParamUtil.setOrderField(request, params, "u.ORDER_RECV_DATE", "ASC");
//		int pageNo = ParamUtil.getInt(request, "pageNo", 1);
		ParamUtil.putStr2Map(request, "pageSize", params);
//		IListPage page = saleorderService.goodsPageQuery(params, pageNo);
		request.setAttribute("params", params);
		request.setAttribute("pvg", setPvg(userBean));
//		request.setAttribute("page", page);
//		return mapping.findForward("goodsList");
		return "";
	}

	/**
	 * * 明细列表
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping(value = "/goodsChildList", method = { RequestMethod.GET, RequestMethod.POST })
	public String goodsChildList(HttpServletRequest request, HttpServletResponse response) {
//		String GOODS_ORDER_ID = ParamUtil.get(request, "GOODS_ORDER_ID");
//		if (!StringUtil.isEmpty(GOODS_ORDER_ID)) {
//			List<SaleorderviewChldModel> result = saleorderService.goodsChildQuery(GOODS_ORDER_ID);
//			request.setAttribute("rst", result);
//		}
//		return mapping.findForward("goodsChildlist");
		return "";
	}

	/**
	 * 查询收货方赠品折扣
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getChannDECT_RATE", method = { RequestMethod.GET, RequestMethod.POST })
	public void getChannDECT_RATE(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String CHANN_ID = request.getParameter("CHANN_ID");
			String BILL_TYPE = request.getParameter("BILL_TYPE");
			String DECT_RATE = "";
			if (BILL_TYPE.equals("赠品订货")) {
				DECT_RATE = saleorderService.getDECT_RATE(CHANN_ID);
			} else {
//        		DECT_RATE=LogicUtil.getChannDsct(CHANN_ID);
			}
			if (StringUtil.isEmpty(DECT_RATE)) {
				throw new ServiceException("总部未设置所选订货方的折扣，请设置后进行操作！");
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("DECT_RATE", DECT_RATE);
			jsonResult = new Gson().toJson(new Result(true, map, ""));
		} catch (ServiceException s) {
			jsonResult = jsonResult(false, s.getMessage());
		} catch (Exception e) {
			jsonResult = jsonResult(false, "获取渠道赠品折扣失败");
			e.printStackTrace();
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}

	@RequestMapping(value = "/toExpData", method = { RequestMethod.GET, RequestMethod.POST })
	public void toExpData(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS) && !QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		Map<String, String> params = new HashMap<String, String>();
		ParamUtil.putStr2Map(request, "ORDER_CHANN_NO", params);
		ParamUtil.putStr2Map(request, "ORDER_CHANN_NAME", params);
		ParamUtil.putStr2Map(request, "SALE_ORDER_NO", params);
		ParamUtil.putStr2Map(request, "STATE", params);
		ParamUtil.putStr2Map(request, "AREA_NO", params);
		ParamUtil.putStr2Map(request, "AREA_NAME", params);
		ParamUtil.putStr2Map(request, "CRE_TIME_BEGIN", params);
		ParamUtil.putStr2Map(request, "CRE_TIME_END", params);
		ParamUtil.putStr2Map(request, "GOODS_ORDER_NO", params);// 订货订单编号

		ParamUtil.putStr2Map(request, "PRD_NO", params);
		ParamUtil.putStr2Map(request, "PRD_NAME", params);
		ParamUtil.putStr2Map(request, "PRD_SPEC", params);
		params.put("BASE_ADD_FLAG", BusinessConsts.YJLBJ_FLAG_TRUE);
		// 只查询0的记录。1为删除。0为正常
		params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		String search = ParamUtil.get(request, "search");
		String module = ParamUtil.get(request, "module");
		String BILL_TYPE = ParamUtil.get(request, "BILL_TYPE");
		StringBuffer strQx = new StringBuffer("");
		strQx.append(ParamUtil.getPvgCon(search, module, PVG_BWS, PVG_BWS_AUDIT, AUD_TAB_KEY, AUD_TAB_NAME,
				AUD_BUSS_TYPE, AUD_BUSS_STATE, userBean));
		// 只查询审核通过状态的数据
		if (StringUtil.isEmpty(params.get("STATE")) && params.isEmpty()) {
			strQx.append(" and u.STATE='" + BusinessConsts.UNCOMMIT + "' ");
		} else {
			strQx.append(" and u.STATE in ('未提交','已取消','审核通过','待发货','已发货','已完成','退回') ");
		}

		params.put("XTYHID", userBean.getXTYHID());
		if (StringUtil.isEmpty(BILL_TYPE)) {
			strQx.append(" and u.BILL_TYPE in ('标准','赠品订货','返利订货') ");
		} else {
			params.put("BILL_TYPE", BILL_TYPE);
		}

		// 权限级别和审批流的封装
		StringBuffer sb = new StringBuffer();
		sb.append(StringUtil.getStrQX("u", strQx.toString()));
		params.put("QXJBCON", sb.toString());

		// 渠道分管sql by zzb 2014-6-17
		String CHANNS_CHARG = userBean.getCHANNS_CHARG();
		params.put("CHANNS_CHARG", CHANNS_CHARG);

		// 字段排序
//		ParamUtil.setOrderField(request, params, "u.CRE_TIME", "DESC");

		List<?> list = saleorderService.qrySaleOrderExp(params);

		// excel数据上列显示的列明
		String tmpContentCn = "销售订单编号,单据类型,订货方编号,发货方名称,是否使用返利,收货方编号,收货方名称,联系人,电话,生产基地名称,收货地址编号,"
				+ "产品编号,产品名称,规格型号,品牌,标准单位,是否是备货,是否非标,特殊规格说明,是否赠品标记,供货价,折扣率,折后单价,订货数量,订货金额,体积,"
				+ "明细备注,区域名称,制单人名称,组织名称,收货地址,区域经理名称,区域经理电话,区域服务中心编号,区域服务中心名称,状态,主表备注";
		//
		String tmpContent = "SALE_ORDER_NO,BILL_TYPE,ORDER_CHANN_NO,ORDER_CHANN_NAME,IS_USE_REBATE,RECV_CHANN_NO,RECV_CHANN_NAME,"
				+ "PERSON_CON,TEL,SHIP_POINT_NAME,RECV_ADDR_NO,PRD_NO,PRD_NAME,PRD_SPEC,BRAND,STD_UNIT,IS_BACKUP_FLAG,"
				+ "IS_NO_STAND_FLAG,SPCL_SPEC_REMARK,IS_FREE_FLAG,PRICE,DECT_RATE,DECT_PRICE,ORDER_NUM,ORDER_AMOUNT,VOLUME,REMARKDTL,"
				+ "AREA_NAME,CRE_NAME,LEDGER_NAME,RECV_ADDR,AREA_MANAGE_NAME,AREA_MANAGE_TEL,AREA_SER_NO,AREA_SER_NAME,STATE,REMARK";
		try {
			ExcelUtil.doExport(response, list, "标准订单", tmpContent, tmpContentCn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成发货指令
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/toSendOrder" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toSendOrder(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_CREFHZL)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		int pageNo = ParamUtil.getInt(request, "pageNo", 1);
		String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
		Map<String, Object> entry = new HashMap<String, Object>();
		String errorMessage ="";
		if (StringUtils.isNotEmpty(SALE_ORDER_ID)) {
			entry = saleorderService.load(SALE_ORDER_ID);
			List<Map<String, String>> list = InterUtil.findAttInfo(SALE_ORDER_ID);
			List<Map<String, String>> listMap = null;
			String fileName = "";
			String Path = "";
			if (list.size() != 0) {
				Path = list.get(0).get("ATT_PATH");
				entry.put("ATT_PATH", Path);
				fileName = Path.substring(1);
				entry.put("FILENAME", fileName.substring(fileName.indexOf("/") + 1));
			}
			List<Map<String, String>> entrySun = saleorderService.toSendFindSale(SALE_ORDER_ID);
			if (entrySun.size() == 0) {
				entrySun = null;
				errorMessage= "该单据已转过发货单！";
			} else {
				for (int i = 0; i < entrySun.size(); i++) {
					listMap = InterUtil.findAttInfo(entrySun.get(i).get("SALE_ORDER_DTL_ID").toString());
					if (listMap.size() != 0) {
						Path = listMap.get(0).get("ATT_PATH");
						entrySun.get(i).put("ATT_PATHS", Path);
						fileName = Path.substring(1);
						entrySun.get(i).put("FILENAME", fileName.substring(fileName.indexOf("/") + 1));
					}
				}
			}
			entry.put("entrySun", entrySun);
		}
		request.setAttribute("errorMessage", errorMessage);
		request.setAttribute("entry", entry);
		request.setAttribute("pageNo", pageNo);
		return view(webPath, "Saleorder_sendOrder");
	}

	/**
	 * 保存发货指令
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/saveSendOrder" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void saveSendOrder(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_CREFHZL)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			// 多条记录
			String jsonDataMulti = request.getParameter("jsonDataMulti");
			String jsonData = ParamUtil.get(request, "parentJsonData");
			OrderModel model = LogicUtil.StrToObj(jsonData, OrderModel.class);
			String BILL_TYPE = "";
			// 单挑记录
			List<SendDtlModel> modelList = null;
			Integer num = 0;
			if (!StringUtil.isEmpty(jsonDataMulti)) {
				modelList = new Gson().fromJson(jsonDataMulti, new TypeToken<List<SendDtlModel>>() {
				}.getType());
				for (SendDtlModel moldes : modelList) {
					num +=Integer.parseInt(
							moldes.getSend_num() == null || moldes.getSend_num() == "" ? "0" : moldes.getSend_num());
				}
			}
			if (num < 1) {
				jsonResult = jsonResult(false, "请填写发货数量，或货物已发完");
			} else {
				orderService.modify(model, modelList, request, BILL_TYPE);
				// 更改订单状态
//				params.put("SALE_ORDER_ID", sale_order_id);
//				params.put("STATE", "生产中");
//				saleorderService.txUpdateById(params);
			}
		}catch(ServiceException s){
			jsonResult = jsonResult(false, s.getMessage());
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
	 * 收货确认
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping(value = "/toConfirm", method = { RequestMethod.GET, RequestMethod.POST })
	public void toConfirm(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_CONFIRM_FX)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String SALE_ORDER_ID = request.getParameter("SALE_ORDER_ID");
			Map<String, Object> entry = saleorderService.load(SALE_ORDER_ID);
			if (!BusinessConsts.DELIVERYED.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}
			SaleorderModel model = (SaleorderModel) LogicUtil.tranMap2Bean(entry, SaleorderModel.class);
			saleorderService.txConfirm(SALE_ORDER_ID, model, userBean);
			jsonResult = jsonResult(true, "确认成功");
		} catch (ServiceException e) {
			jsonResult = jsonResult(false, e.getMessage());
		} catch (Exception e) {
			jsonResult = jsonResult(false, "提交失败");
			logger.error(e.getMessage());
			// e.printStackTrace();
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping(value = "/parseExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public void parseExcel(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}

		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String info = "";
		try {
			List<Map<String, Map<String, String>>> sheetList = new LinkedList<Map<String, Map<String, String>>>();
			Map<String, Map<String, String>> sheetMap = new HashMap<String, Map<String, String>>();
			Map<String, String> entryFields = new LinkedHashMap<String, String>();
			// 主表信息字段
			entryFields.put("PROJECT_NAME", "1,1");
			entryFields.put("FACTORY_NO", "1,6");
			entryFields.put("ORDER_DATE", "1,11");
			entryFields.put("PRE_RECV_DATE", "1,13");
			entryFields.put("BILL_TYPE", "1,15");

			entryFields.put("CHANN_NAME", "2,1");
			entryFields.put("PERSON_CON", "2,6");
			entryFields.put("TEL", "2,8");
			entryFields.put("RECV_ADDR", "2,11");
			entryFields.put("PROESS_TYPE", "2,15");
			entryFields.put("REMARK2", "n,0");
			sheetMap.put(SaleorderConsts.ENTRY_FIELDS, entryFields);

			// 明细信息字段
			Map<String, String> childFields = new LinkedHashMap<String, String>();
			childFields.put("GROUP_NO", "0");
			childFields.put("PRD_NAME", "1");// 产品名称
			childFields.put("PRD_SPEC", "2");// 规格型号
			childFields.put("PRD_QUALITY", "3");// 材质
			childFields.put("PRD_COLOR", "4");// 颜色
			childFields.put("PRD_GLASS", "5");// 玻璃
			childFields.put("PRD_TOWARD", "6");// 推向
			childFields.put("PRD_SERIES", "7");// 系列
			childFields.put("PRD_OTHER", "8");// 其他
			childFields.put("STD_UNIT", "9");// 单位
			childFields.put("ORDER_NUM", "10");// 数量
			childFields.put("PRICE", "11");// 单价
			childFields.put("ORDER_AMOUNT", "12");// 金额
			childFields.put("PRD_PLACE", "13");// 空间位置
			childFields.put("FIGURE_NO", "14");// 附图号
			childFields.put("REMARK", "15");// 备注
			sheetMap.put(SaleorderConsts.CHILD_FIELDS, childFields);

			// 主表信息非空字段名称
			Map<String, String> entryFieldLabels = new LinkedHashMap<String, String>();
			entryFieldLabels.put("PROJECT_NAME", "项目名称");
			entryFieldLabels.put("FACTORY_NO", "订单编号");
			entryFieldLabels.put("ORDER_DATE", "订单日期");
			entryFieldLabels.put("PRE_RECV_DATE", "交货日期");
			entryFieldLabels.put("BILL_TYPE", "订单类型");

			entryFieldLabels.put("CHANN_NAME", "挂账单位");
			entryFieldLabels.put("PERSON_CON", "收货人");
			entryFieldLabels.put("TEL", "收货电话");
			entryFieldLabels.put("RECV_ADDR", "收货地址");
			entryFieldLabels.put("PROESS_TYPE", "处理类型");
			// entryFieldLabels.put("REMARK2", "订单备注");
			sheetMap.put(SaleorderConsts.ENTRY_FIELD_REQUIED_LABELS, entryFieldLabels);

			// 子表信息非空字段名称
			Map<String, String> childFieldLabels = new LinkedHashMap<String, String>();
			// childFieldLabels.put("GROUP_NO", "0");
			childFieldLabels.put("PRD_NAME", "产品名称");// 产品名称
			// childFieldLabels.put("PRD_SPEC", "2");//规格型号
			// childFieldLabels.put("PRD_QUALITY", "3");//材质
			// childFieldLabels.put("PRD_COLOR", "4");//颜色
			// childFieldLabels.put("PRD_GLASS", "5");//玻璃
			// childFieldLabels.put("PRD_TOWARD", "6");//推向
			// childFieldLabels.put("PRD_SERIES", "7");//系列
			// childFieldLabels.put("PRD_OTHER", "8");//其他
			// childFieldLabels.put("STD_UNIT", "9");//单位
			childFieldLabels.put("ORDER_NUM", "数量");// 数量
			childFieldLabels.put("PRICE", "单价");// 单价
			// childFieldLabels.put("ORDER_AMOUNT", "12");//金额
			// childFieldLabels.put("PRD_PLACE", "13");//空间位置
			// childFieldLabels.put("FIGURE_NO", "14");//附图号
			// childFieldLabels.put("REMARK", "15");//备注
			sheetMap.put(SaleorderConsts.CHILD_FIELD_REQUIED_LABELS, childFieldLabels);

			// 子表信息数据字典字段名称
			Map<String, String> childDictLabels = new LinkedHashMap<String, String>();
			//childDictLabels.put("GROUP_NO", "0");
			//childDictLabels.put("PRD_NAME", "产品名称");// 产品名称
			//childDictLabels.put("PRD_SPEC", "规格型号");//规格型号
			childDictLabels.put("PRD_QUALITY", "材质");//材质
			childDictLabels.put("PRD_COLOR", "颜色");//颜色
			childDictLabels.put("PRD_GLASS", "玻璃");//玻璃
			childDictLabels.put("PRD_TOWARD", "推向");//推向
			childDictLabels.put("PRD_SERIES", "系列");//系列
			childDictLabels.put("PRD_OTHER", "其他");//其他
			//childDictLabels.put("STD_UNIT", "单位");//单位
			//childDictLabels.put("ORDER_NUM", "数量");// 数量
			//childDictLabels.put("PRICE", "单价");// 单价
			//childDictLabels.put("ORDER_AMOUNT", "金额");//金额
			childDictLabels.put("PRD_PLACE", "空间位置");//空间位置
			//childDictLabels.put("FIGURE_NO", "附图号");//附图号
			//childDictLabels.put("REMARK", "备注");//备注
			sheetMap.put(SaleorderConsts.CHILD_DICT_LABELS, childDictLabels);
			// 子表信息数据字典字段使用数据字典ID
			Map<String, String> childDicts = new LinkedHashMap<String, String>();
			//childDicts.put("GROUP_NO", "0");
			//childDicts.put("PRD_NAME", "产品名称");// 产品名称
			//childDicts.put("PRD_SPEC", "规格型号");//规格型号
			childDicts.put("PRD_QUALITY", "PROD_MATER");//材质
			childDicts.put("PRD_COLOR", "PROD_COLOR");//颜色
			childDicts.put("PRD_GLASS", "PROD_GLASS");//玻璃
			childDicts.put("PRD_TOWARD", "PROD_TOWARD");//推向
			childDicts.put("PRD_SERIES", "PRO_SERIES");//系列
			childDicts.put("PRD_OTHER", "PROD_OTHER");//其他
			//childDicts.put("STD_UNIT", "单位");//单位
			//childDicts.put("ORDER_NUM", "数量");// 数量
			//childDicts.put("PRICE", "单价");// 单价
			//childDicts.put("ORDER_AMOUNT", "金额");//金额
			childDicts.put("PRD_PLACE", "PRO_PLACE");//空间位置
			//childDicts.put("FIGURE_NO", "附图号");//附图号
			//childDicts.put("REMARK", "备注");//备注
			sheetMap.put(SaleorderConsts.CHILD_DICTS, childDicts);

			// String[] a = new String[]{"1"};
			sheetList.add(sheetMap);
			// List list = ExcelUtil.readExcelbyModel(path + secondPath + filePath, 1, alist, a);

			// HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook(request);
			/*
			String uploadPath = CustomizedProperties.getContextProperty(Consts.PROPERTIES_KEY_UPLOADPATH);
			String path = request.getRealPath(uploadPath);*/
			String path=Consts.UPLOADPATH_ROOT;
			String filePath = ParamUtil.utf8Decoder(request, "filePath");
			Workbook workbook = ExcelUtil.getWorkbookByPath(path + filePath);
			info = saleorderService.txParseExcelToDb(workbook, sheetList, userBean, request);
			System.out.println(info);
		} catch (ServiceException e) {
			jsonResult = jsonResult(false, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "Execl解析失败." + e.getMessage());
		}

		if (!"".equals(info)) {
			jsonResult(false, info);
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	@RequestMapping(value = "/exportExcelDtl", method = { RequestMethod.GET, RequestMethod.POST })
	public void exportDtl(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<String, String>();
		try {
			String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
			Map<String, Object> entry = saleorderService.load(SALE_ORDER_ID);
			String SALE_ORDER_DTL_IDs = request.getParameter("SALE_ORDER_DTL_IDS");
			// 没有零星领料Id可以直接跳转。
			if (!StringUtil.isEmpty(SALE_ORDER_DTL_IDs)) {
				params.put("SALE_ORDER_DTL_IDS", SALE_ORDER_DTL_IDs);
			}
			List<Map<String, String>> list = saleorderService.childQuery(SALE_ORDER_ID);

			// excel数据上列显示的列明
			String tmpContentCn = "组号,产品编码,产品名称,单位,门洞尺寸,规格尺寸,"
					+ "材质,颜色,推向,玻璃,其他,系列,工程位置,是否开锁孔,"
					+ "附图号,图纸,是否返利,可用库存,数量,已实发数量,"
					+ "计算报价,折扣率(%),返利,最终报价,金额,备注"
					;
			//
			String tmpContent = "GROUP_NO,PRD_NO,PRD_NAME,STD_UNIT,HOLE_SPEC,PRD_SPEC,"
					+ "PRD_QUALITY,PRD_COLOR,PRD_TOWARD,PRD_GLASS,PRD_OTHER,PRD_SERIES,PRD_PLACE,IS_NO_LOCK_FLAG_TEXT,"
					+ "FIGURE_NO,attPath,IS_NO_REBATE_FLAG_TEXT,IS_NO_STAND_FLAG,ORDER_NUM,SENDED_NUM,"
					+ "PRICE,DECT_RATE,REBATE_AMOUNT,DECT_PRICE,ORDER_AMOUNT,REMARK";
			ExcelUtil.doExport(response, list, StringUtil.nullToSring(entry.get("BILL_TYPE")+"_"+entry.get("FACTORY_NO")), tmpContent, tmpContentCn);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * 设置权限Map
	 *
	 * @param UserBean the userBean
	 * @return Map<String,String>
	 */
	private Map<String, String> setPvg(UserBean userBean) {
		Map<String, String> pvgMap = new HashMap<String, String>();
		pvgMap.put("PVG_BWS", QXUtil.checkPvg(userBean, PVG_BWS));
		pvgMap.put("PVG_EDIT", QXUtil.checkPvg(userBean, PVG_EDIT));
		pvgMap.put("PVG_DELETE", QXUtil.checkPvg(userBean, PVG_DELETE));
		pvgMap.put("PVG_START_STOP", QXUtil.checkPvg(userBean, PVG_START_STOP));
		pvgMap.put("PVG_COMMIT_CANCLE", QXUtil.checkPvg(userBean, PVG_COMMIT_CANCLE));
		pvgMap.put("PVG_TRACE", QXUtil.checkPvg(userBean, PVG_TRACE));
		pvgMap.put("PVG_BWS_AUDIT", QXUtil.checkPvg(userBean, PVG_BWS_AUDIT));
		pvgMap.put("PVG_AUDIT_AUDIT", QXUtil.checkPvg(userBean, PVG_AUDIT_AUDIT));
		pvgMap.put("PVG_FINISH_CANCLE", QXUtil.checkPvg(userBean, PVG_FINISH_CANCLE));
		pvgMap.put("PVG_TRACE_AUDIT", QXUtil.checkPvg(userBean, PVG_TRACE_AUDIT));

		pvgMap.put("AUD_TAB_NAME", AUD_TAB_NAME);
		pvgMap.put("AUD_TAB_KEY", AUD_TAB_KEY);
		pvgMap.put("AUD_BUSS_STATE", AUD_BUSS_STATE);
		pvgMap.put("AUD_BUSS_TYPE", AUD_BUSS_TYPE);
		pvgMap.put("AUD_FLOW_INS", AUD_FLOW_INS);

		pvgMap.put("PVG_TURNDESIGNER", QXUtil.checkPvg(userBean, PVG_TURNDESIGNER));
		pvgMap.put("PVG_TURNERP", QXUtil.checkPvg(userBean, PVG_TURNERP));
		pvgMap.put("PVG_IMPORT", QXUtil.checkPvg(userBean, PVG_IMPORT));
		pvgMap.put("PVG_ASSIGN", QXUtil.checkPvg(userBean, PVG_ASSIGN));
		pvgMap.put("PVG_DESIGN", QXUtil.checkPvg(userBean, PVG_DESIGN));
		pvgMap.put("PVG_BACK", QXUtil.checkPvg(userBean, PVG_BACK));
		pvgMap.put("PVG_CREFHZL", QXUtil.checkPvg(userBean, PVG_CREFHZL));
		pvgMap.put("PVG_APPLY", QXUtil.checkPvg(userBean, PVG_APPLY));

		pvgMap.put("PVG_BWS_FX", QXUtil.checkPvg(userBean, PVG_BWS_FX));
		pvgMap.put("PVG_APPLY_FX", QXUtil.checkPvg(userBean, PVG_APPLY_FX));
		pvgMap.put("PVG_TRACE_FX", QXUtil.checkPvg(userBean, PVG_TRACE_FX));
		pvgMap.put("PVG_CONFIRM_FX", QXUtil.checkPvg(userBean, PVG_CONFIRM_FX));
		pvgMap.put("PVG_FEEDBACK_FX", QXUtil.checkPvg(userBean, PVG_FEEDBACK_FX));

		pvgMap.put("xtyhid", userBean.getXTYHID());
		return pvgMap;
	}

}