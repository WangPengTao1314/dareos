package com.centit.base.channchrg.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.common.controller.BaseController;
import com.centit.base.channchrg.service.ChannChrgService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.commons.util.StringUtil;
import com.centit.sys.model.UserBean;

/**
 * 渠道分管设置
 *
 * @author zhang_zhongbin
 *
 */
@Controller
@RequestMapping("/base/channchrg")
public class ChannChrgController extends BaseController {

	// 日志记录
	/** The logger. */
	private Logger logger = Logger.getLogger(ChannChrgController.class);

	// 维护界面
	// 增删改查
	private static String PVG_BWS = "XT0013201";
	private static String PVG_EDIT = "XT0013202";
	private static String PVG_DELETE = "XT0013203";
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

	private static final String webPath = "base/channchrg";
	@Autowired
	private ChannChrgService channChrgService;

	@RequestMapping(value="/toFrame",method= {RequestMethod.POST,RequestMethod.GET})
	public String toFrame(
			HttpServletRequest request, HttpServletResponse response) {

		logger.info("toFrame方法调用开始");

		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		// 设置跳转时需要的一些公用属性
		ParamUtil.setCommAttributeEx(request);
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request,
				"paramUrl"));
		logger.info("toFrame方法调用结束");
		return view(webPath,"Channchrg_Frame");

	}

	/**
	 * 查询结果列表.
	 *
	 */
	@RequestMapping("/toList")
	public String toList(
			HttpServletRequest request, HttpServletResponse response) {
		// chk权限
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS))) {
			throw new ServiceException("对不起，您没有权限 !");
		}

//		Map<String, String> params = new HashMap<String, String>();
//
//		String search = ParamUtil.get(request, "search");
//		//权限级别和审批流的封装
//	   //params.put("QXJBCON", ParamUtil.getPvgCon(search,module,PVG_BWS,PVG_BWS_AUDIT,AUD_TAB_KEY, AUD_TAB_NAME,AUD_BUSS_TYPE,AUD_BUSS_STATE,userBean));
//
//		//只查询0的记录。1为删除。0为正常
//        params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);
//        List<Map<String,String>> list = this.channChrgService.childQuery(params);
//        request.setAttribute("result", list);
//		request.setAttribute("params", params);
//		request.setAttribute("pvg", setPvg(userBean));
		return view(webPath,"Chann_List");
	}


	@RequestMapping("/toTopPage")
	public String toTopPage(
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("toFrame方法调用开始");
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && (!QXUtil.checkMKQX(userBean, PVG_BWS))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		request.setAttribute("XTYHID", userBean.getXTYHID());
		request.setAttribute("pvg", setPvg(userBean));
		return view(webPath,"Channchrg_List");

	}


   /**
    * 渠道列表
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    */
	@RequestMapping("/childList")
	public String childList(
			HttpServletRequest request, HttpServletResponse response) {

		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_BWS)) {
			throw new ServiceException("对不起，您没有权限 !");
		}

		Map<String, String> params = new HashMap<String, String>();
		ParamUtil.putStr2Map(request, "LEDGER_ID", params);
		ParamUtil.putStr2Map(request, "AREA_ID", params);
		ParamUtil.putStr2Map(request, "AREA_NO", params);
		ParamUtil.putStr2Map(request, "AREA_NAME", params);
		ParamUtil.putStr2Map(request, "CHANN_NO", params);
		ParamUtil.putStr2Map(request, "CHANN_NAME", params);
		ParamUtil.putStr2Map(request, "CHANN_TYPE", params);
		ParamUtil.putStr2Map(request, "CHAA_LVL", params);
		ParamUtil.putStr2Map(request, "PROV", params);
		ParamUtil.putStr2Map(request, "CITY", params);

		String search = ParamUtil.get(request, "search");
		String notcharg = ParamUtil.get(request, "notcharg");//只显示未分管
		String XTYHID = ParamUtil.get(request, "XTYHID");
		if(StringUtil.isEmpty(XTYHID)){
			ParamUtil.putStr2Map(request, "YHBH", params);
			ParamUtil.putStr2Map(request, "YHM", params);
		}else{
			params.put("XTYHID", XTYHID);
		}

		//查询  未分管的
		if(BusinessConsts.INTEGER_1.equals(notcharg)){
			params.put("notcharg", notcharg);
		}

		//查询  已分管的
		if(BusinessConsts.INTEGER_2.equals(notcharg)){
			params.put("havecharg", notcharg);
		}
		if(StringUtil.isEmpty(search)){
			params.put("searchFlag", "1<>1");
		}
		params.put("search", search);
		params.put("STATE_PARAMS", " c.STATE in('启用','停用') ");
		params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		List<Map<String,String>> list = this.channChrgService.childQuery(params);
		request.setAttribute("result", list);
		request.setAttribute("params", params);
		request.setAttribute("pvg", setPvg(userBean));
		return view(webPath,"Chann_List");
	}


	/**
	 * 编辑 保存.
	 *
	 */
	@RequestMapping("/edit")
	public void edit(
			HttpServletRequest request, HttpServletResponse response) {

		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}

		String XTYHID = ParamUtil.get(request, "XTYHID");
		String CHANN_IDS = ParamUtil.get(request, "CHANN_IDS");
        String deleteIds = ParamUtil.get(request, "deleteIds");
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			if(StringUtil.isEmpty(XTYHID)){
				throw new ServiceException("找不到用户ID");
			}
			channChrgService.txEdit(XTYHID, CHANN_IDS,deleteIds);
			jsonResult = jsonResult(true, "保存成功");
		} catch (Exception e) {
			jsonResult = jsonResult(false, "保存失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}




	/**
	 * 删除
	 *
	 */
	@RequestMapping("/delete")
	public void delete(
			HttpServletRequest request, HttpServletResponse response) {

		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_DELETE)) {
			throw new ServiceException("对不起，您没有权限 !");
		}

		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String XTYHID = ParamUtil.get(request, "XTYHID");
			String CHANN_IDS = request.getParameter("CHANN_IDS");

			channChrgService.txDelete(XTYHID, CHANN_IDS);
			jsonResult = jsonResult(true, "操作成功");

		} catch (Exception e) {
			jsonResult = jsonResult(false, "操作失败");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}




	/**
	 * 用户列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/childYhxxList")
	public String childYhxxList(
			HttpServletRequest request, HttpServletResponse response) {

		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_BWS)) {
			throw new ServiceException("对不起，您没有权限 !");
		}

		Map<String, String> params = new HashMap<String, String>();
		ParamUtil.putStr2Map(request, "LEDGER_ID", params);
		ParamUtil.putStr2Map(request, "CHANN_ID", params);
//		ParamUtil.putStr2Map(request, "CHANN_NO", params);
//		ParamUtil.putStr2Map(request, "CHANN_NAM", params);
		ParamUtil.putStr2Map(request, "YHBH", params);
		ParamUtil.putStr2Map(request, "YHM", params);
		ParamUtil.putStr2Map(request, "BMXXID", params);
		ParamUtil.putStr2Map(request, "BMMC", params);

		String search = ParamUtil.get(request, "search");
		String notcharg = ParamUtil.get(request, "notcharg");//只显示未分管

		//查询  未分管的
		if(BusinessConsts.INTEGER_1.equals(notcharg)){
			params.put("notcharg", notcharg);
		}

		//查询  已分管的
		if(BusinessConsts.INTEGER_2.equals(notcharg)){
			params.put("havecharg", notcharg);
		}
		if(StringUtil.isEmpty(search)){
			params.put("searchFlag", "1<>1");
		}
		params.put("search", search);
		params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		List<Map<String,String>> list = this.channChrgService.childYhxxQuery(params);
		request.setAttribute("result", list);
		request.setAttribute("params", params);
		request.setAttribute("pvg", setPvg(userBean));
		return view(webPath,"Sysuser_List");
	}
	/**
	 * 编辑 保存.按渠道维护 保存用户信息
	 *
	 */
	@RequestMapping("/editRyxx")
	public void editRyxx(
			HttpServletRequest request, HttpServletResponse response) {

		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}

		String XTYHIDS = ParamUtil.get(request, "XTYHIDS");
		String CHANN_ID = ParamUtil.get(request, "CHANN_ID");
        String deleteIds = ParamUtil.get(request, "deleteIds");
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			if(StringUtil.isEmpty(CHANN_ID)){
				throw new ServiceException("找不到渠道");
			}
			channChrgService.txEditRyxx(XTYHIDS, CHANN_ID,deleteIds);
			jsonResult = jsonResult(true, "保存成功");
		} catch (Exception e) {
			jsonResult = jsonResult(false, "保存失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}


	/**
	 * 以渠道维护 取消分管
	 *
	 */
	@RequestMapping("/deleteYhxx")
	public void deleteYhxx(
			HttpServletRequest request, HttpServletResponse response) {

		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_DELETE)) {
			throw new ServiceException("对不起，您没有权限 !");
		}

		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String XTYHIDS = ParamUtil.get(request, "XTYHIDS");
			String CHANN_ID = request.getParameter("CHANN_ID");
			channChrgService.txDeleteYhxx(CHANN_ID, XTYHIDS);
			jsonResult = jsonResult(true, "操作成功");

		} catch (Exception e) {
			jsonResult = jsonResult(false, "操作失败");
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
		pvgMap.put("PVG_COMMIT_CANCLE", QXUtil.checkPvg(userBean, PVG_COMMIT_CANCLE));
		pvgMap.put("PVG_TRACE", QXUtil.checkPvg(userBean, PVG_TRACE));
		pvgMap.put("PVG_BWS_AUDIT", QXUtil.checkPvg(userBean, PVG_BWS_AUDIT));
		pvgMap.put("PVG_AUDIT_AUDIT", QXUtil.checkPvg(userBean, PVG_AUDIT_AUDIT));
		pvgMap.put("PVG_TRACE_AUDIT", QXUtil.checkPvg(userBean, PVG_TRACE_AUDIT));
		pvgMap.put("AUD_TAB_NAME", AUD_TAB_NAME);
		pvgMap.put("AUD_TAB_KEY", AUD_TAB_KEY);
		pvgMap.put("AUD_BUSS_STATE", AUD_BUSS_STATE);
		pvgMap.put("AUD_BUSS_TYPE", AUD_BUSS_TYPE);
		pvgMap.put("AUD_FLOW_INS", AUD_FLOW_INS);
		return pvgMap;
	}

	public ChannChrgService getChannChrgService() {
		return channChrgService;
	}

	public void setChannChrgService(ChannChrgService channChrgService) {
		this.channChrgService = channChrgService;
	}









}
