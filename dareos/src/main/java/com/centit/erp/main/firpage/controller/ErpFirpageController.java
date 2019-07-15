package com.centit.erp.main.firpage.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centit.common.controller.BaseController;
import com.centit.commons.model.Result;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.erp.main.firpage.service.ErpFirpageService;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;

/**
 * 分销首页action
 * 
 * @author zhang_zhongbin
 * 
 */
@Controller
@RequestMapping("/sys/erpFirst")
public class ErpFirpageController extends BaseController {

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
	private ErpFirpageService erpFirpageService;
	
	private static final String webPath = "index";

	public String toFrame(HttpServletRequest request, HttpServletResponse response) {
//		UserBean userBean = ParamUtil.getUserBean(request);
		// if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_BWS))
		// {
		// throw new ServiceException("对不起，您没有权限 !");
		// }

//		List<PrmtplanModel> prmtList = this.drpFirpageService.queryPrmtpList(
//				userBean, 5);
//
//		request.setAttribute("prmtList", prmtList);
//		request.setAttribute("noticeList", noticeList);
		return view(webPath, "index");
	}

  
	/**
	 * 查询 总部代办事宜
	 */
	@PostMapping("/queryErpCount")
	public void queryErpCount(HttpServletRequest request, HttpServletResponse response) {

		UserBean userBean = (UserBean) request.getSession(false).getAttribute(
				"UserBean");

		PrintWriter writer = getWriter(response);

		//未处理的订货订单
		int Goods = erpFirpageService.queryGoodsCount(userBean.getCHANNS_CHARG());
		//制定交付计划
		int turnoverplan=erpFirpageService.queryTurnoverplanCount(userBean);
		//交付计划维护
		int turnoverhd=erpFirpageService.queryTurnoverhdCount(userBean);
		
		//货品发运
		int pdtdeliver=erpFirpageService.queryPdtdeliverCount(userBean);
		
		//发运确认
		int deliverconfm=erpFirpageService.querydeliverconfmCount(userBean);
        //待审核工艺单
		int techAudit = erpFirpageService.queryTechorderAudit(userBean);
		//待核价工艺单
		int techPrice = erpFirpageService.queryTechorderPrice(userBean);
		
		Map<String,Object> map = new HashMap<String,Object> ();
		map.put("Goods", Goods);
		map.put("turnoverplan", turnoverplan);
		map.put("turnoverhd", turnoverhd);
		map.put("pdtdeliver", pdtdeliver);
		map.put("deliverconfm", deliverconfm);
		map.put("techAudit", techAudit);
		map.put("techPrice", techPrice);
		String jsonResult = new Gson().toJson(new Result(true, map, ""));

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}
	
	/**
	 * 老板首页 查看公告
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("toNotice")
	public String toNotice(HttpServletRequest request, HttpServletResponse response) {
//		UserBean userBean = ParamUtil.getUserBean(request);
		String NOTICE_ID = ParamUtil.get(request,"NOTICE_ID");
		if(!StringUtils.isEmpty(NOTICE_ID)){
			Map<String,Object> entry = erpFirpageService.loadNoticeModel(NOTICE_ID);
			request.setAttribute("notice", entry);
		}
        
		return view(webPath, "notice");
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
