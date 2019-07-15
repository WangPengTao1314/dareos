package com.centit.drp.main.sales.deliver.order.controller;

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
import com.centit.common.service.BookkeepingService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.project.management.service.ProjectConsts;
import com.centit.drp.main.sales.deliver.order.model.OrderModel;
import com.centit.drp.main.sales.deliver.order.model.SendDtlModel;
import com.centit.drp.main.sales.deliver.order.service.OrderService;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 发货管理模块 发货指令维护
 * 
 * @author wang_pt
 *
 */
@Controller
@RequestMapping("/deliver/order")
public class OrderController extends BaseController {

	private static final String webPath = "drp/main/deliver/order";

	@Autowired
	private OrderService orderService;
	@Autowired
	BookkeepingService bookkeepingService;
	/** 权限对象维护 **/
	// 增改删
	private static String PVG_BWS = "BS0010601";// 查询
	private static String PVG_EDIT = "BS0010602";// 增改
	private static String PVG_DELETE = "BS0010603";// 删
	// 审核
	private static String PVG_BWS_QUERY = "BS0010701";// 查询
	private static String PVG_BWS_AUDIT = "BS0010702";// 增改
	private static String PVG_BWS_DELETE = "BS0010703";// 删

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
		if (Consts.FUN_CHEK_PVG
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS) && !QXUtil.checkMKQX(userBean, PVG_BWS_QUERY))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
		request.setAttribute("module", ParamUtil.utf8Decoder(request, "module"));
		return view(webPath, "Order_Frame");
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
		if (Consts.FUN_CHEK_PVG
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS) && !QXUtil.checkMKQX(userBean, PVG_BWS_QUERY))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "SEND_ORDER_NO", params);// 发货编号
		ParamUtil.putStr2Map(request, "SALE_ORDER_NO", params);// 订单编号
		ParamUtil.putStr2Map(request, "CHANN_NAME", params);// 经销商 
		ParamUtil.putStr2Map(request, "STATE", params);// 单据状态
		ParamUtil.putStr2Map(request, "SALE_NAME", params);//业务员
		ParamUtil.putStr2Map(request, "pagesize", params);//

		String module = ParamUtil.get(request, "module");
		String search = ParamUtil.get(request, "search");
		//params.put("QXJBCON", ParamUtil.getPvgCon(search, module, PVG_BWS, PVG_EDIT, PVG_DELETE, PVG_BWS_QUERY,
		//		PVG_BWS_AUDIT, PVG_BWS_DELETE, userBean));
		//params.put("QXJBCON", QXUtil.getQXTJ(userBean, PVG_BWS));
		params.put("billTypeQuery", LogicUtil.getProjectQx(userBean, null));
		params.put("XTYHID", userBean.getXTYHID());
		if ("wh".equals(module)) {
			params.put("STATES",
					"state in ('" + ProjectConsts.SENDORDER_WAITCHECK + "','" + ProjectConsts.SENDORDER_REJECT + "','"
							+ ProjectConsts.SENDORDER_DRAFT + "','" + ProjectConsts.SENDORDER_CHECKPASS + "','"
							+ ProjectConsts.SENDORDER_OVWER + "')");
		} else if ("sh".equals(module)) {
			params.put("STATES", "state in ('" + ProjectConsts.SENDORDER_CHECKPASS + "','"
					+ ProjectConsts.SENDORDER_WAITCHECK + "','" + ProjectConsts.SENDORDER_OVWER + "')");
		}
		if (StringUtils.isEmpty(search)) {
			if ("wh".equals(module)) {
				params.put("queryState", "'草稿','已拒绝'");
			} else if ("sh".equals(module)) {
				params.put("queryState", "'待审核'");
			}
		}
		// 字段排序
		ParamUtil.setOrderField(request, params, "cre_time2", "DESC");
		
		orderService.pageQuery2(params, pageDesc);
		
		request.setAttribute("params", params);
		request.setAttribute("pvg", setPvg(userBean));
		request.setAttribute("page", pageDesc);
		return view(webPath, "Order_List");
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

		ParamUtil.putStr2Map(request, "SEND_ORDER_ID", params);// 发货单id
		ParamUtil.putStr2Map(request, "SALE_ORDER_ID", params);// 销售订单id
		String send_order_id = ParamUtil.get(request, "SEND_ORDER_ID");
		String sale_order_id = ParamUtil.get(request, "SALE_ORDER_ID");
		Map<String, Object> entry = null;
		if (StringUtils.isNotEmpty(send_order_id)) {
			entry = orderService.querySun(params);
		} else {
			if (StringUtils.isNotEmpty(sale_order_id)) {
				entry = orderService.getSaleData(params);
			}
		}
		// ZTMC 账套
		request.setAttribute("ADMIN", userBean.getXTYHID());
		request.setAttribute("entry", entry);
		request.setAttribute("pageNo", pageNo);

		return view(webPath, "Order_Edit");
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
		try {
			// 多条记录
			String jsonDataMulti = request.getParameter("jsonDataMulti");
			// String sale_order_id = request.getParameter("sale_order_id");
			String jsonData = ParamUtil.get(request, "parentJsonData");
			OrderModel model = LogicUtil.StrToObj(jsonData, OrderModel.class);
			String BILL_TYPE = "";
			List<SendDtlModel> modelList = null;
			Double num = 0.0;
			if (!StringUtil.isEmpty(jsonDataMulti)) {
				modelList = new Gson().fromJson(jsonDataMulti, new TypeToken<List<SendDtlModel>>() {
				}.getType());
				for (SendDtlModel moldes : modelList) {

					num += Double.parseDouble(StringUtils.isBlank(moldes.getSend_num()) ? "0" : moldes.getSend_num());
				}
			}
			if (num < 1) {
				jsonResult = jsonResult(false, "请填写发货数量，或货物已发完");
			} else {
				orderService.modify(model, modelList, request, BILL_TYPE);
			}
		} catch (ServiceException e) {
			jsonResult = jsonResult(false, e.getMessage());
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
	 * 删除.
	 * 
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG
				&& (!QXUtil.checkMKQX(userBean, PVG_DELETE) && !QXUtil.checkMKQX(userBean, PVG_BWS_DELETE))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String send_order_id = ParamUtil.get(request, "SEND_ORDER_ID");
		try {
			if (StringUtils.isNotEmpty(send_order_id)) {
				orderService.delete(send_order_id, userBean);
			}
		} catch (ServiceException e) {
			jsonResult = jsonResult(false, e.getMessage());
		} catch (Exception e) {
			jsonResult = jsonResult(false, "删除失败");
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
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS) && !QXUtil.checkMKQX(userBean, PVG_BWS_QUERY))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "SEND_ORDER_ID", params);
		Map<String, Object> entry = orderService.querySun(params);
		// ZTMC 账套
		request.setAttribute("ZTMC", userBean.getLoginZTMC());
		request.setAttribute("entry", entry);
		return view(webPath, "Order_Detail");
	}

	/**
	 * 跳转审核界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/toCheck" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (!QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "SEND_ORDER_ID", params);
		Map<String, Object> entry = orderService.querySun(params);
		// ZTMC 账套
		request.setAttribute("ZTMC", userBean.getLoginZTMC());
		request.setAttribute("msgSatate", null);
		request.setAttribute("entry", entry);
		return view(webPath, "Order_Check");
	}

	/**
	 * 审核
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/check" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void check(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (!QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String send_order_id = request.getParameter("SEND_ORDER_ID");
			//
			if (StringUtils.isBlank(send_order_id)) {
				jsonResult = jsonResult(false, "操作失败，未找到发货的信息，请联系管理员！");
			}
			String status = request.getParameter("STATUS");
			String jsonData = ParamUtil.get(request, "jsonData");
			OrderModel model = LogicUtil.StrToObj(jsonData, OrderModel.class);
			model.setState(status);
			model.setSend_order_id(send_order_id);
			model.setUpdator(userBean.getXTYHID());
			model.setUpd_name(userBean.getXM());
			orderService.sendAudit(model, send_order_id,userBean);
			jsonResult = jsonResult(true, "操作成功");
		} catch (ServiceException s) {
			jsonResult = jsonResult(false, s.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "操作失败!");
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
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		OrderModel model = new OrderModel();
		Map<String, Object> map = new HashMap<String, Object>();
		String send_order_id = ParamUtil.get(request, "SEND_ORDER_ID");
		String LEDGER_ID = ParamUtil.get(request, "LEDGER_ID");
		String FACTORY_NO = ParamUtil.get(request, "FACTORY_NO");
		// 校验
		map.put("SEND_ORDER_ID", send_order_id);
		map = orderService.checkSendNum(map);
		try {
			if (Double.valueOf(map.get("ALLSEND_NUM").toString()) <= 0) {
				jsonResult = jsonResult(false, "提交失败,请至少填写一条单据的明细数量或货物已发完！！！");
			} else {
				if (StringUtils.isEmpty(send_order_id)) {
					jsonResult = jsonResult(false, "操作失败，未找到发货的信息，请联系管理员！");
				}
				model.setSend_order_id(send_order_id);
				model.setState("待审核");
				model.setFactory_no(FACTORY_NO);
				model.setLedger_id(LEDGER_ID);
				orderService.submit(model,userBean);
			}
		}catch(ServiceException e) {
			jsonResult = jsonResult(false, e.getMessage());
		}catch (Exception e) {
			jsonResult = jsonResult(false, "提交失败");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * 校验订单是否具有发货的资格
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/checkOrder" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void checkOrder(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String send_order_id = request.getParameter("SEND_ORDER_ID");
			String stateFlag = bookkeepingService.checkSendFreezAmount(send_order_id);
			if (BusinessConsts.CHECK_SALE_AMOUNT_2.equals(stateFlag)) {
				jsonResult = jsonResult(true, "金额不足");
			}
			jsonResult = jsonResult(true, "");
		} catch(ServiceException e) {
			jsonResult = jsonResult(false, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "单据校验失败");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}

	/**
	 * 校验订单是否具有发货的资格
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/checkSendNum" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void checkSendNum(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String SALE_ORDER_ID = request.getParameter("SALE_ORDER_DTL_ID");
			String SALE_ORDER_DTL_ID = request.getParameter("SEND_ORDER_ID");
			String SEND_NUM = request.getParameter("SEND_NUM");// 发货数量
			String ORDER_NUM = request.getParameter("ORDER_NUM");// 订货数量
			Double num = 0.0;
			num = StringUtils.isBlank(SEND_NUM) ? 0 : Double.parseDouble(SEND_NUM);
			map.put("SALE_ORDER_ID", SALE_ORDER_ID);
			map.put("SALE_ORDER_DTL_ID", SALE_ORDER_DTL_ID);
			map = orderService.checkSendNum(map);
			Boolean flag = Double.parseDouble(ORDER_NUM) - Double.parseDouble(map.get("ALLSEND_NUM").toString()) > num;
			if (!flag) {
				jsonResult = jsonResult(false, "发货数量校验失败");
			}
		} catch(ServiceException e) {
			jsonResult = jsonResult(false, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "发货数量校验失败");
		}
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
		// 维护
		pvgMap.put("PVG_BWS", QXUtil.checkPvg(userBean, PVG_BWS));
		pvgMap.put("PVG_EDIT", QXUtil.checkPvg(userBean, PVG_EDIT));
		pvgMap.put("PVG_DELETE", QXUtil.checkPvg(userBean, PVG_DELETE));
		// 审核
		pvgMap.put("PVG_BWS_AUDIT", QXUtil.checkPvg(userBean, PVG_BWS_AUDIT));
		pvgMap.put("PVG_BWS_QUERY", QXUtil.checkPvg(userBean, PVG_BWS_QUERY));
		pvgMap.put("PVG_BWS_DELETE", QXUtil.checkPvg(userBean, PVG_BWS_DELETE));
		return pvgMap;
	}

}
