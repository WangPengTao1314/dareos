package com.centit.drp.main.myorder.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.centit.base.product.model.ProductTree;
import com.centit.common.controller.BaseController;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.myorder.model.MyorderModel;
import com.centit.drp.main.myorder.service.MyorderService;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.DictService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 分销首页action
 * 
 * @author zhang_zhongbin
 * 
 */
@Controller
@RequestMapping("/drp/myorder")
public class MyorderController extends BaseController {

	/** 权限对象 **/
	/** 维护 */
	// 增删改查
	private static String PVG_BWS = "FX0020101";
	private static String PVG_EDIT = "FX0020102";
	private static String PVG_DELETE = "FX0020102";
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
	//是否显示赠品按钮
	private static String PVG_BWS_GIFT = "FX0020406";
	
	private static final String webPath="drp/main/myorder";
	
	@Autowired
	private MyorderService myorderService;
	/**
	 * 用于获取XML文件配置 引用于DicSelectAction类
	 */
	@Autowired
	private DictService dictService;

	

	/**
	 * 
	 * 下帧显示货品信息页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/toList",method= {RequestMethod.POST,RequestMethod.GET})
	public String toList(
			HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS) && !QXUtil.checkMKQX(
						userBean, PVG_BWS_AUDIT))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("STATE", BusinessConsts.JC_STATE_ENABLE);
		map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		// 数据库查找当前时间的所有促销信息
		map.put("STATE", BusinessConsts.ISSUANCE);// 已发布
		
		//获取接单组织
		List<Map<String,String>> ledgerList=myorderService.getLedgerByChannId(userBean.getCHANN_ID());
		
		
		
		map.put("CHANN_ID", userBean.getCHANN_ID());
		List<String> PRMT_PLANS = myorderService.findPRMT_PLANAll(map);

		request.setAttribute("PRMT_PLANS", PRMT_PLANS);
		request.setAttribute("LEDGER_ID", userBean.getLoginZTXXID());
		request.setAttribute("ledgerList", ledgerList);
		request.setAttribute("pvg", setPvg(userBean));
		return view(webPath,"Myorder_List");
	}
	
	@RequestMapping(value="/getPrdList",method= {RequestMethod.POST,RequestMethod.GET})
	public void getPrdList(HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
        UserBean userBean = ParamUtil.getUserBean(request);
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("prdInfo", ParamUtil.utf8Decoder(request, "prdInfo"));
		map.put("parPrdId", ParamUtil.utf8Decoder(request, "parPrdId"));
		map.put("ledger", ParamUtil.utf8Decoder(request, "ledger"));
		map.put("series", ParamUtil.utf8Decoder(request, "series"));
		map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		map.put("FINAL_NODE_FLAG", BusinessConsts.YJLBJ_FLAG_TRUE);
		map.put("STATE", BusinessConsts.JC_STATE_ENABLE);
		// 修改渠道折扣率获取方式
		String DECT_RATE = LogicUtil.getChannDsct(userBean.getCHANN_ID());
		if (StringUtil.isEmpty(DECT_RATE)) {
//			return view(webPath,"Myorder_error");
			DECT_RATE="1";
		}
		map.put("DECT_RATE", DECT_RATE);
		request.setAttribute("DECT_RATE", DECT_RATE);
		// 查询货品时增加渠道货品过滤
		StringBuffer sql = new StringBuffer();
//		sql.append(" and  a.COMM_FLAG=").append(BusinessConsts.YJLBJ_FLAG_TRUE);
		String lassQuery = ParamUtil.utf8Decoder(request, "lassQuery");
		if ("true".equals(lassQuery)) {
			sql.append(" and IS_CAN_FREE_FLAG=").append(BusinessConsts.YJLBJ_FLAG_TRUE);
		}
		map.put("sql", sql.toString());
		String rebateQuery = ParamUtil.utf8Decoder(request, "rebateQuery");//返利标记
		if ("true".equals(rebateQuery)) {
			map.put("REBATE_PRD_NO", " a.IS_REBATE_FLAG=1 ");
		}
		// 当前登录人所属渠道
		map.put("CHANN_ID", userBean.getCHANN_ID());
		ParamUtil.setOrderField(request, map, "a.PRD_NO", "ASC");
		myorderService.pageQuery(map, pageDesc);
		jsonResult = jsonResult(true, JSONObject.toJSONString(pageDesc));
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
	
	@RequestMapping(value="/getSeriesList",method= {RequestMethod.POST,RequestMethod.GET})
	public void getSeriesList(HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String LEDGER_ID=request.getParameter("LEDGER_ID");
		List<Map<String,String>> seriesList = myorderService.getSeriesd(LEDGER_ID);

		jsonResult = jsonResult(true, JSONObject.toJSONString(seriesList));
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }

	/**
	 * 上帧显示查询页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toSelPrd")
	public String toSelPrd(
			HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS) && !QXUtil.checkMKQX(
						userBean, PVG_BWS_AUDIT))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		return view(webPath,"Myorder_Sel");
	}
	@RequestMapping("/edit")
	public void edit(
			HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String jsonDate = ParamUtil.get(request, "jsonData");
			String LEDGER_ID = ParamUtil.get(request, "LEDGER_ID");
			String LEDGER_NAME = ParamUtil.get(request, "LEDGER_NAME");
			MyorderModel model = null;
			if (!StringUtil.isEmpty(jsonDate)) {
				model = new Gson().fromJson(jsonDate,
						new TypeToken<MyorderModel>() {
						}.getType());
			}
			model.setLEDGER_ID(LEDGER_ID);
			model.setLEDGER_NAME(LEDGER_NAME);
			myorderService.txEdit(model, userBean);
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
     * 显示树.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@RequestMapping(value="/showTree")
    public String showTree( HttpServletRequest request, HttpServletResponse response) {
		String LEDGER_ID=request.getParameter("LEDGER_ID");
        List <ProductTree> trees;
        try {
            trees = myorderService.getTree(LEDGER_ID);
            String treeData = new Gson().toJson(trees);
            request.setAttribute("tree", treeData);
            return view(webPath,"Myorder_Tree");
        } catch (Exception e) {
        	e.printStackTrace();
            request.setAttribute("msg", "获取货品信息失败！");
            return view(Consts.WEB_PATH,Consts.FAILURE);
        }
    }

	/**
	 * * 设置权限Map
	 * 
	 * @param UserBean
	 *            the userBean
	 * @return Map<String,String>
	 */
	@RequestMapping("/setPvg")
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
		pvgMap.put("PVG_BWS_GIFT", QXUtil.checkPvg(userBean, PVG_BWS_GIFT));
		pvgMap.put("AUD_TAB_NAME", AUD_TAB_NAME);
		pvgMap.put("AUD_TAB_KEY", AUD_TAB_KEY);
		pvgMap.put("AUD_BUSS_STATE", AUD_BUSS_STATE);
		pvgMap.put("AUD_BUSS_TYPE", AUD_BUSS_TYPE);
		pvgMap.put("AUD_FLOW_INS", AUD_FLOW_INS);
		return pvgMap;
	}

	// 点击图片弹出窗口显示大图片
	@RequestMapping("/toPicture")
	public String toPicture(
			HttpServletRequest request, HttpServletResponse response) {
		String fileName = request.getParameter("fileName");
		request.setAttribute("fileName", fileName);
		return view(webPath,"Myorder_Picture");
	}

}
