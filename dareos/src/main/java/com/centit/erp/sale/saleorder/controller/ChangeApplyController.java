/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：BMXXAction.java
 */
package com.centit.erp.sale.saleorder.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.centit.core.utils.LogicUtil;
import com.centit.erp.sale.saleorder.model.ChangeApplyModel;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.erp.sale.saleorder.model.SaleorderModelChld;
import com.centit.erp.sale.saleorder.service.ChangeApplyService;
import com.centit.erp.sale.saleorder.service.SaleorderService;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @ClassName: ChangeApplyController
 * @Description: 销售订单变更申请控制器
 * @author wang_dw
 * @date 2019年3月1日 下午3:15:10
 *
 */
@Controller
@RequestMapping("/erp/changeapply")
public class ChangeApplyController extends BaseController {

	@Autowired
	private SaleorderService saleorderService;

	@Autowired
	private ChangeApplyService changeApplyService;

	private static final String webPath = "erp/sale/saleorder";

	/**日志**/
	private Logger logger = LoggerFactory.getLogger(ChangeApplyController.class);
	/** 权限对象**/
	/** 维护*/
	//增删改查
	private static String PVG_BWS="FX0020601";
	private static String PVG_EDIT="";
	private static String PVG_DELETE="";
	// 报价确认
	private static String PVG_QUOTE_CONFIRM="FX0020602";
	//启用,停用
	private static String PVG_START_STOP="";
	//确认，取消
	private static String PVG_FINISH_CANCLE="";
	/** end*/
	/**审批维护必须维护字段**/
	//提交撤销
	private static String PVG_COMMIT_CANCLE="?";
	private static String PVG_TRACE="?";
	//审核模块
	private static String PVG_BWS_AUDIT="?";
	private static String PVG_AUDIT_AUDIT="?";
	private static String PVG_TRACE_AUDIT="?";
	//审批流参数
	private static String AUD_TAB_NAME="ERP_SALE_ORDER";
	private static String AUD_TAB_KEY="SALE_ORDER_ID";
	private static String AUD_BUSS_STATE="";
	private static String AUD_BUSS_TYPE="ERP_SALE_ORDER_AUDIT";
	private static String AUD_FLOW_INS="com.hoperun.sys.service.PublicFlowInterface";

	private static String PVG_BWS_CHANGE="BS0010501";
	private static String PVG_EDIT_CHANGE="BS0010502";
	private static String PVG_DELETE_CHANGE="BS0010503";
	// 审图
	private static String PVG_AUDIT_CHANGE="BS0010504";
	// 报价
	private static String PVG_QUOTE_CHANGE="BS0010505";
	// 领导确认
	private static String PVG_CONFIRM_CHANGE="BS0010506";
	// 生成销售订单
	private static String PVG_CREXSDD_CHANGE="BS0010507";
	/**审批 end**/

	/**
	 * * to 框架页面
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping(value = "/toFrame", method = { RequestMethod.GET, RequestMethod.POST })
	public String toFrame(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS)
				&& !QXUtil.checkMKQX(userBean, PVG_BWS_CHANGE)))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		ParamUtil.setCommAttributeEx(request);
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
		// 除去设计师权限还有其他权限则>0
		request.setAttribute("isShowPrice", LogicUtil.isShowPrice(userBean.getXTJSIDS()));
		return view(webPath, "Saleorder_Frame");
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
	@RequestMapping(value = "/toList", method = { RequestMethod.GET, RequestMethod.POST })
	public String toList(HttpServletRequest request, HttpServletResponse response, PageDesc pageDesc) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS)
				&& !QXUtil.checkMKQX(userBean, PVG_BWS_CHANGE)))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		Map<String,Object> params = new HashMap<String,Object>();

		ParamUtil.putStr2Map(request, "FACTORY_NO", params);
		ParamUtil.putStr2Map(request, "PROESS_TYPE", params);
		ParamUtil.putStr2Map(request, "CHANN_NAME", params);
		ParamUtil.putStr2Map(request, "CUST_NAME", params);
		ParamUtil.putStr2Map(request, "DDSTATE", params);
		ParamUtil.putStr2Map(request, "STATE", params);
		ParamUtil.putStr2Map(request, "ORDER_DATE_BEGIN", params);
		ParamUtil.putStr2Map(request, "ORDER_DATE_END", params);
		ParamUtil.putStr2Map(request, "ORDER_DELIVERY_DATE_BEGIN", params);
		ParamUtil.putStr2Map(request, "ORDER_DELIVERY_DATE_END", params);
		ParamUtil.putStr2Map(request, "CUST_ADDR", params);
		ParamUtil.putStr2Map(request, "GOODS_ORDER_NO", params);
		ParamUtil.putStr2Map(request, "BILL_TYPE", params);
		params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);

		@SuppressWarnings("unused")
		String module = ParamUtil.get(request,"module");
		String search = ParamUtil.get(request,"search");
		String STATE = ParamUtil.get(request,"STATE");

		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商，   0：总部
		if(IS_DRP_LEDGER.equals("0")){ // 订单中心
			params.put("NOTSTATE", BusinessConsts.GOODSORDER_STATE_CG);
			if(StringUtil.isEmpty(search)){
				// 默认查询提交和变更中的申请单
				params.put("STATES", "'"+BusinessConsts.COMMIT+"','"+BusinessConsts.PASS+"'");
				if (!QXUtil.checkMKQX(userBean, PVG_AUDIT_CHANGE)) {
					// 没有审图权限
					params.put("DDNOTSTATE", BusinessConsts.GOODSORDER_STATE_STZ);
				} else if (!QXUtil.checkMKQX(userBean, PVG_QUOTE_CHANGE)) {
					// 没有报价权限
					params.put("DDNOTSTATE", BusinessConsts.GOODSORDER_STATE_BJZ);
				}
				//初始化查询状态默认显示
				String STATES = LogicUtil.getOrderQxByUserBean(userBean, PVG_BWS_CHANGE);
				//System.err.println(STATES);
				params.put("DDSTATES", STATES);
			} else {
				if(!StringUtil.isEmpty(STATE)){
					params.put("STATE",STATE);
				}
			}
		} else if(IS_DRP_LEDGER.equals("1")){ // 经销商
			params.put("CHANN_ID",userBean.getCHANN_ID());
			if (StringUtil.isEmpty(search)) {
				// 默认查询报价确认状态
				//初始化查询状态默认显示
				String STATES = LogicUtil.getOrderQxByUserBean(userBean, PVG_BWS);
				//System.err.println(STATES);
				params.put("DDSTATES", STATES);
				// 草稿和退回
				params.put("STATES", "'"+BusinessConsts.GOODSORDER_STATE_CG+"','"+BusinessConsts._BACK+"'");
			} else {
				if(!StringUtil.isEmpty(STATE)){
					params.put("STATE",STATE);
				}
			}

		}
		request.setAttribute("IS_DRP_LEDGER",IS_DRP_LEDGER);

		//字段排序
		ParamUtil.setOrderField(request, params, "u.CHANGE_APPLY_NO", "DESC");
		ParamUtil.putStr2Map(request, "pageSize", params);
		changeApplyService.pageQuery(params, pageDesc);
		request.setAttribute("params", params);
		request.setAttribute("pvg",setPvg(userBean));
		request.setAttribute("page", pageDesc);
		return view(webPath, "Saleorder_ChangeList");
	}

	/**
     * *
     * * to 编辑框架页面
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     *
     * @return the action forward
     */
	@RequestMapping(value = "/toEditFrame", method = { RequestMethod.GET, RequestMethod.POST })
    public String toEditFrame(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        //设置跳转时需要的一些公用属性
        ParamUtil.setCommAttributeEx(request);
        request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
//        return mapping.findForward("editFrame");
        return view(webPath, "Saleorder_Edit_Frame");
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
	@RequestMapping(value = "/toEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public String toParentEdit(HttpServletRequest request, HttpServletResponse response){
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
    	/**
         * 系统的当前时间
         */
//        SimpleDateFormat currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat currentTime = new SimpleDateFormat("yyyy-MM-dd");
        request.setAttribute("currentTime", currentTime.format(new Date()));
        /**
         *设置订单交期时间(+4天)
         */
        SimpleDateFormat order_delivery_date = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, 40);
        request.setAttribute("order_delivery_date", order_delivery_date.format(calendar.getTime()));
		String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
		Map<String,Object> entry = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(SALE_ORDER_ID)){
			entry = saleorderService.load(SALE_ORDER_ID);
			// 主表附件
			String attPathP = LogicUtil.getAttPath(SALE_ORDER_ID);
			entry.put("attPath", attPathP); //附件图纸 可能有多个
			request.setAttribute("rst", entry);

			List <Map <String, String>> childList = saleorderService.childQuery(SALE_ORDER_ID);
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
			request.setAttribute("userBean", userBean);
		}
//		return mapping.findForward("toedit");
		return view(webPath, "Saleorder_Edit");
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
	@RequestMapping(value = "/toDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public String toDetail(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS)
				&& !QXUtil.checkMKQX(userBean, PVG_BWS_CHANGE)))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
		if(!StringUtil.isEmpty(SALE_ORDER_ID)){
			Map<String,Object> entry = saleorderService.load(SALE_ORDER_ID);
			request.setAttribute("rst", entry);

			List <Map<String,String>> result = saleorderService.childQuery(SALE_ORDER_ID);
			for (Map<String,String> child : result) {
				String attPath = LogicUtil.getAttPath(child.get("SALE_ORDER_DTL_ID"));
				child.put("attPath", attPath);
			}
			request.setAttribute("rstChild", result);
		}
//		return mapping.findForward("todetail");
		return view(webPath, "Saleorder_Detail");
	}

	/**
	 * * 提交时，校验是否有明细.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping(value = "/toCommit", method = { RequestMethod.GET, RequestMethod.POST })
	public void toCommit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_COMMIT_CANCLE))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String errorId = "";
		try {
			String CHANGE_APPLY_ID = request.getParameter("CHANGE_APPLY_ID");
			Map<String, Object> entry = changeApplyService.load(CHANGE_APPLY_ID);
			if (!BusinessConsts.GOODSORDER_STATE_CG.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}
			//changeApplyService.txCommit(CHANGE_APPLY_ID, entry, userBean);
			jsonResult = jsonResult(true, "提交成功");
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			jsonResult = jsonResult(false, e.getMessage());
		} catch (Exception e) {
			if ("0".equals(errorId)) {
				jsonResult = jsonResult(false, "没有明细，不能提交!");
			} else {
				jsonResult = jsonResult(false, "提交失败");
			}
			e.printStackTrace();
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}

	/**
	 * to 变更订单
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@RequestMapping("/tochange")
	public String toChange(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT_CHANGE)))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		String CHANGE_APPLY_ID = ParamUtil.get(request, "selRowId");
		String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
		String option = ParamUtil.get(request, "option");
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商，   0：总部
		if(!StringUtil.isEmpty(CHANGE_APPLY_ID)){
			Map<String,Object> entry = changeApplyService.load(CHANGE_APPLY_ID);
			// 主表附件
			String attPathP = LogicUtil.getAttPath(SALE_ORDER_ID);
			entry.put("attPath", attPathP); //附件图纸 可能有多个
			request.setAttribute("rst", entry);

			// 查询明细数据
			List <Map<String,String>> result = saleorderService.childQuery(SALE_ORDER_ID);
			for (Map<String,String> child : result) {
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
	 * 销售订单处理-订单变更
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping("/change")
	public void change(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT_CHANGE) )
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String CHANGE_APPLY_ID = ParamUtil.get(request, "CHANGE_APPLY_ID");
			String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
			Map<String, Object> entry = changeApplyService.load(CHANGE_APPLY_ID);
			if (!BusinessConsts.COMMIT.equals(entry.get("APPLYSTATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}

			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			SaleorderModel model = new Gson().fromJson(parentJsonData, new TypeToken <SaleorderModel>() {}.getType());
			String jsonDate = ParamUtil.get(request, "childJsonData");
			List <SaleorderModelChld> chldModelList = null;
			if (!StringUtil.isEmpty(jsonDate)) {
				chldModelList = new Gson().fromJson(jsonDate, new TypeToken <List <SaleorderModelChld>>() {
				}.getType());
			}

			ChangeApplyModel changeApplyModel = new Gson().fromJson(parentJsonData, new TypeToken <ChangeApplyModel>() {}.getType());
			changeApplyService.txChange(CHANGE_APPLY_ID, SALE_ORDER_ID, changeApplyModel, model, chldModelList, userBean);
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
	 * 销售订单变更-to审图报价页面
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 *
	 * @return the action forward
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/toAudit")
	public String toAudit(
			HttpServletRequest request, HttpServletResponse response){
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_AUDIT_CHANGE)
				&& !QXUtil.checkMKQX(userBean, PVG_QUOTE_CHANGE)
				&& !QXUtil.checkMKQX(userBean, PVG_QUOTE_CONFIRM)
				&& !QXUtil.checkMKQX(userBean, PVG_CONFIRM_CHANGE))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		String CHANGE_APPLY_ID = ParamUtil.get(request, "selRowId");
		String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
		String option = ParamUtil.get(request, "option");
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商，   0：总部
		Map<String,Object> entry = changeApplyService.load(CHANGE_APPLY_ID);
		// 主表附件
		String attPathP = LogicUtil.getAttPath(SALE_ORDER_ID);
		entry.put("attPath", attPathP); //附件图纸 可能有多个
		request.setAttribute("rst", entry);
		// 查询明细数据
		List <Map <String, String>> childList = saleorderService.childQuery(SALE_ORDER_ID);
		for (Iterator it = childList.iterator(); it.hasNext();) {
			Map<String, String> child = (Map<String, String>) it.next();
			String attPath = LogicUtil.getAttPath(child.get("SALE_ORDER_DTL_ID"));
			child.put("attPath", attPath);
		}
		request.setAttribute("rstChild", childList);
		request.setAttribute("option",option);
		request.setAttribute("IS_DRP_LEDGER",IS_DRP_LEDGER);
		return view(webPath,"Saleorder_Edit");
	}

	/**
	 * 销售订单变更-审图、报价
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping("/audit")
	public void audit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_AUDIT_CHANGE)
				&& !QXUtil.checkMKQX(userBean, PVG_QUOTE_CHANGE))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
			String option = ParamUtil.get(request, "option"); // 总部审图、报价
			//String REBATEDSCT = ParamUtil.get(request, "REBATEDSCT");//返利折扣

			Map<String, Object> entry = saleorderService.load(SALE_ORDER_ID);
			if ("audit".equals(option) && !BusinessConsts.GOODSORDER_STATE_STZ.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}
			if ("quote".equals(option) && !BusinessConsts.GOODSORDER_STATE_BJZ.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}

			String parentJsonData = ParamUtil.get(request, "parentJsonData");

			SaleorderModel model = new Gson().fromJson(parentJsonData, new TypeToken <SaleorderModel>() {}.getType());
			String childJsonData = ParamUtil.get(request, "childJsonData");
			List <SaleorderModelChld> chldModelList = null;
			if (!StringUtil.isEmpty(childJsonData)) {
				chldModelList = new Gson().fromJson(childJsonData, new TypeToken <List <SaleorderModelChld>>() {
				}.getType());
			}
			changeApplyService.txAudit(SALE_ORDER_ID, model, chldModelList, userBean, option);
			jsonResult = jsonResult(true, "操作成功");

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
	 * 销售订单变更-确认报价
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping("/confirm")
	public void confirm(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_QUOTE_CONFIRM)
				&& !QXUtil.checkMKQX(userBean, PVG_CONFIRM_CHANGE))
		{
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String SALE_ORDER_ID = ParamUtil.get(request, "SALE_ORDER_ID");
			String option = ParamUtil.get(request, "option"); // 经销商确认报价、总部确认
			//String REBATEDSCT = ParamUtil.get(request, "REBATEDSCT");//返利折扣

			Map<String, Object> entry = saleorderService.load(SALE_ORDER_ID);
			if ("confirmquote".equals(option) && !BusinessConsts.GOODSORDER_STATE_BJDQR.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}
			if ("confirm".equals(option) && !BusinessConsts.GOODSORDER_STATE_LDDQR.equals(entry.get("STATE"))) {
				throw new ServiceException("当前单据状态已更改，请刷新后重试！");
			}

			String parentJsonData = ParamUtil.get(request, "parentJsonData");
			SaleorderModel model = new Gson().fromJson(parentJsonData, new TypeToken <SaleorderModel>() {}.getType());

			changeApplyService.txConfirm(SALE_ORDER_ID, model, option, userBean);
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
	 * 设置权限Map
	 * @param UserBean the userBean
	 * @return Map<String,String>
	 */
	private Map<String, String> setPvg(UserBean userBean) {
		Map<String, String> pvgMap = new HashMap<String, String>();
		pvgMap.put("PVG_BWS", QXUtil.checkPvg(userBean, PVG_BWS));
		pvgMap.put("PVG_EDIT", QXUtil.checkPvg(userBean, PVG_EDIT));
		pvgMap.put("PVG_DELETE", QXUtil.checkPvg(userBean, PVG_DELETE));
		pvgMap.put("PVG_QUOTE_CONFIRM", QXUtil.checkPvg(userBean, PVG_QUOTE_CONFIRM));

		pvgMap.put("PVG_START_STOP", QXUtil.checkPvg(userBean, PVG_START_STOP));
		pvgMap.put("PVG_FINISH_CANCLE",
				QXUtil.checkPvg(userBean, PVG_FINISH_CANCLE));

		pvgMap.put("PVG_COMMIT_CANCLE",
				QXUtil.checkPvg(userBean, PVG_COMMIT_CANCLE));
		pvgMap.put("PVG_TRACE", QXUtil.checkPvg(userBean, PVG_TRACE));
		pvgMap.put("PVG_BWS_AUDIT", QXUtil.checkPvg(userBean, PVG_BWS_AUDIT));
		pvgMap.put("PVG_AUDIT_AUDIT",
				QXUtil.checkPvg(userBean, PVG_AUDIT_AUDIT));
		pvgMap.put("PVG_TRACE_AUDIT",
				QXUtil.checkPvg(userBean, PVG_TRACE_AUDIT));

		pvgMap.put("AUD_TAB_NAME", AUD_TAB_NAME);
		pvgMap.put("AUD_TAB_KEY", AUD_TAB_KEY);
		pvgMap.put("AUD_BUSS_STATE", AUD_BUSS_STATE);
		pvgMap.put("AUD_BUSS_TYPE", AUD_BUSS_TYPE);
		pvgMap.put("AUD_FLOW_INS", AUD_FLOW_INS);

		pvgMap.put("PVG_BWS_CHANGE", QXUtil.checkPvg(userBean, PVG_BWS_CHANGE));
		pvgMap.put("PVG_EDIT_CHANGE", QXUtil.checkPvg(userBean, PVG_EDIT_CHANGE));
		pvgMap.put("PVG_DELETE_CHANGE", QXUtil.checkPvg(userBean, PVG_DELETE_CHANGE));
		pvgMap.put("PVG_AUDIT_CHANGE", QXUtil.checkPvg(userBean, PVG_AUDIT_CHANGE));
		pvgMap.put("PVG_QUOTE_CHANGE", QXUtil.checkPvg(userBean, PVG_QUOTE_CHANGE));
		pvgMap.put("PVG_CONFIRM_CHANGE", QXUtil.checkPvg(userBean, PVG_CONFIRM_CHANGE));
		pvgMap.put("PVG_CREXSDD_CHANGE", QXUtil.checkPvg(userBean, PVG_CREXSDD_CHANGE));

		return pvgMap;
	}

}
