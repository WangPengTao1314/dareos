package com.centit.drp.main.sales.deliver.rework.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.centit.common.service.BookkeepingService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.DateUtil;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.sales.deliver.order.model.OrderModel;
import com.centit.drp.main.sales.deliver.order.model.SendDtlModel;
import com.centit.drp.main.sales.deliver.rework.service.ReworkService;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.erp.sale.saleorder.service.SaleorderService;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 售后管理模块 返修单维护
 * 
 * @author wang_pt
 *
 */
@Controller
@RequestMapping("deliver/rework")
public class ReworkController extends BaseController {

	private static final String webPath = "drp/main/deliver/rework";
	private static final String REPAIR_ORDER = "返修订单";//

	@Autowired
	private ReworkService reworkService;

	/** 权限对象维护 **/
	// 返修单  查 增改 删 提交
	private static String PVG_BWS = "BS0011101";
	private static String PVG_EDIT = "BS0011102";
	private static String PVG_DELETE = "BS0011103";
	private static String PVG_SUBMIT = "BS0011104";
	// 返修单 处理
	private static String PVG_BWS_HANDLE = "BS0012201";
	private static String PVG_AUDIT_HANDLE = "BS0012202";
	
	

	/**
	 * 跳转页面 销售订单选取
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/toFrames" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toFrames(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS)&&!QXUtil.checkMKQX(userBean, PVG_BWS_HANDLE))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		request.setAttribute("HEAD", ParamUtil.utf8Decoder(request, "HEAD"));
		request.setAttribute("module", ParamUtil.utf8Decoder(request, "module"));
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
		return view(webPath, "Rework_Frame");
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
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS) && !QXUtil.checkMKQX(userBean, PVG_BWS_HANDLE))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		//
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "SALE_ORDER_NO", params);// 返修单号
		ParamUtil.putStr2Map(request, "FROM_BILL_NO", params);// 原订单编号
		ParamUtil.putStr2Map(request, "STATE", params);// 订单状态
		ParamUtil.putStr2Map(request, "STATE2", params);// 订单状态
		ParamUtil.putStr2Map(request, "CRE_TIME1", params);// 提交日期
		ParamUtil.putStr2Map(request, "CRE_TIME2", params);
		ParamUtil.putStr2Map(request, "PRE_RECV_DATE1", params);// 交货日期
		ParamUtil.putStr2Map(request, "PRE_RECV_DATE2", params);
		ParamUtil.putStr2Map(request, "CHANN_NAME", params);// 经销商
		params.put("BILL_TYPE", REPAIR_ORDER);// 返修订单
		//
		ParamUtil.putStr2Map(request, "pagesize", params);
		String module = ParamUtil.get(request, "module");
		String search = ParamUtil.get(request, "search");
		if(!"0".equals(userBean.getIS_DRP_LEDGER())) {
			params.put("QXJBCON", QXUtil.getQXTJ(userBean, PVG_BWS));
		}
		if ("wh".equals(module)) {
			// params.put("XTYHID", userBean.getXTYHID());
			params.put("STATES", "STATE in ('草稿','待审核','生产中','已退回')");
		} else if ("sh".equals(module)) {
			params.put("STATES", "STATE in ('生产中','待审核','已退回')");
		}
		
		// 字段排序
		ParamUtil.setOrderField(request, params, "cre_time2", "DESC");
				
		reworkService.pageQuery(params, pageDesc);
		request.setAttribute("params", params);
		request.setAttribute("pvg", setPvg(userBean));
		request.setAttribute("page", pageDesc);
		// 返修单据未通过，未完成记录条数
		request.setAttribute("counts", reworkService.counts(null));
		return view(webPath, "Rework_List");
	}

	/**
	 * to modify page.
	 * 
	 * @param request  the request
	 * @param response the response
	 * @return the action forward
	 */
	@RequestMapping(value = { "/toEdit" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toEdit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		int pageNo = ParamUtil.getInt(request, "pageNo", 1);
		ParamUtil.putStr2Map(request, "SALE_ORDER_ID", params);// 发货单id
		ParamUtil.putStr2Map(request, "SALE_ORDER_IDS", params);// 发货单id
		String sale_order_id = ParamUtil.get(request, "SALE_ORDER_ID");
		String sale_order_ids = ParamUtil.get(request, "SALE_ORDER_IDS");
		Map<String, Object> entry =  new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(sale_order_id)) {
			// ZTMC 账套
			entry = reworkService.query(params);
		} else {
			if (StringUtils.isNotEmpty(sale_order_ids)) {
				entry = reworkService.queryBack(params);
			}else {
				entry.put("SALE_ID", userBean.getXTYHID());
				entry.put("SALE_NAME", userBean.getXM());
				entry.put("SALEDEPT_NAME", userBean.getBMMC());//
				entry.put("SALEDEPT_ID", userBean.getBMXXID());
				entry.put("TRANSPORT","汽运");
				//设置订单交期时间(+25天)
				Date date = DateUtil.addDays(DateUtil.getDate(), 25);
				String ORDER_DELIVERY_DATE = DateUtil.formatDateToStr(date);
				entry.put("ORDER_DELIVERY_DATE", ORDER_DELIVERY_DATE);
				date = DateUtil.addDays(date, 7);
				String PRE_RECV_DATE = DateUtil.formatDateToStr(date);
				entry.put("PRE_RECV_DATE", PRE_RECV_DATE);
				/**
				 * 设置订单交期时间(+25天)
				 */
				SimpleDateFormat order_delivery_date = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.DAY_OF_YEAR, 25);
				entry.put("ORDER_DELIVERY_DATE", order_delivery_date.format(calendar.getTime()));
			}
		}
		
		// queryBack
		
		request.setAttribute("entry", entry);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("ZTMC", userBean.getLoginZTMC());
		request.setAttribute("ADMIN", userBean.getXTYHID());
		request.setAttribute("IS_DRP_LEDGER", userBean.getIS_DRP_LEDGER());
		return view(webPath, "Rework_Edit");
	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/edit" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void edit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		// 主表
		String parentJsonData = ParamUtil.get(request, "parentJsonData");
		OrderModel model = LogicUtil.StrToObj(parentJsonData, OrderModel.class);

		// 从表
		String childJsonData = ParamUtil.get(request, "childJsonData");
		try {
			List<SendDtlModel> chldModelList = null;
			if (!StringUtil.isEmpty(childJsonData)) {
				chldModelList = new Gson().fromJson(childJsonData, new TypeToken<List<SendDtlModel>>() {
				}.getType());
			}
			// 校验返修单号是否重复
			reworkService.edit(model, chldModelList, request);
		} catch(ServiceException e) {
			jsonResult=jsonResult(false,e.getMessage());
		}catch (RuntimeException e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "保存失败");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}

	/**
	 * 删除.
	 * 
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_DELETE)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String sale_order_id = ParamUtil.get(request, "SALE_ORDER_ID");
		try {
			if (StringUtils.isEmpty(sale_order_id)) {
				jsonResult = jsonResult(false, "未找到该单据,请重新刷新或联系管理员！！！");
			}
			reworkService.delete(sale_order_id, userBean);
		} catch (Exception e) {
			jsonResult = jsonResult(false, "删除失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}
	
	@RequestMapping("/deleteDtl")
	public void deleteDtl(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String PROJECT_PAYABLE_ID = ParamUtil.get(request, "SALE_ORDER_DTL_IDS");
		if (StringUtils.isNotEmpty(PROJECT_PAYABLE_ID)) {
			try {
				reworkService.deleteDtl(PROJECT_PAYABLE_ID,userBean);
			} catch (Exception e) {
				jsonResult = jsonResult(false, "删除失败");
			}
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
		UserBean userBean = ParamUtil.getUserBean(request);//
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS)&&!QXUtil.checkMKQX(userBean, PVG_BWS_HANDLE))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "SALE_ORDER_ID", params);
		Map<String, Object> entry = reworkService.query(params);
		request.setAttribute("entry", entry);
		// ZTMC 账套
		request.setAttribute("ZTMC", userBean.getLoginZTMC());
		return view(webPath, "Rework_Detail");
	}

	/**
	 * 审核
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/toCheck" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toCheck(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (!QXUtil.checkMKQX(userBean, PVG_AUDIT_HANDLE)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "SALE_ORDER_ID", params);
		Map<String, Object> entry = reworkService.query(params);
		request.setAttribute("entry", entry);
		// ZTMC 账套
		request.setAttribute("ZTMC", userBean.getLoginZTMC());
		return view(webPath, "Rework_Check");
	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/check" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void check(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (!QXUtil.checkMKQX(userBean, PVG_AUDIT_HANDLE)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		// 主表 STATUS
		String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
		String jsonData = ParamUtil.get(request, "jsonData");
		OrderModel model = LogicUtil.StrToObj(jsonData, OrderModel.class);
		model.setSale_order_id(SALE_ORDER_ID);
		model.setState(request.getParameter("STATUS"));
		try {
			reworkService.submit(model,userBean);
			
		}catch(ServiceException e) {
			jsonResult=jsonResult(false,e.getMessage()); 
		}catch (RuntimeException e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "保存失败");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}

	/**
	 * 提交
	 * 
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/submit")
	public void submit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_SUBMIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		OrderModel model = new OrderModel();
		Map<String, Object> map = new HashMap<String, Object>();
		String sale_order_id = ParamUtil.get(request, "SALE_ORDER_ID");
		// 校验
		// map.put("SALE_ORDER_ID", sale_order_id);
		// map=orderService.checkSendNum(map);
		// if(Double.valueOf(map.get("ALLSEND_NUM").toString())<=0) {
		// jsonResult = jsonResult(false, "提交失败,请至少填写一条单据的明细数量或货物已发完！！！");
		// }else {
		try {
			if (StringUtils.isEmpty(sale_order_id)) {
				jsonResult = jsonResult(false, "提交失败");
			}
			model.setSale_order_id(sale_order_id);
			model.setState("待审核");
			reworkService.submit(model,userBean);
		} catch (Exception e) {
			jsonResult = jsonResult(false, "提交失败");
		}
		// }
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
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
		pvgMap.put("PVG_SUBMIT", QXUtil.checkPvg(userBean, PVG_SUBMIT));
		//
		pvgMap.put("PVG_BWS_HANDLE", QXUtil.checkPvg(userBean, PVG_BWS_HANDLE));
		pvgMap.put("PVG_AUDIT_HANDLE", QXUtil.checkPvg(userBean, PVG_AUDIT_HANDLE));
		return pvgMap;
	}

}
