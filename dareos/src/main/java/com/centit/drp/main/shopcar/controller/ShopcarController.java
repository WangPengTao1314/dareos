package com.centit.drp.main.shopcar.controller;

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

import com.centit.base.chann.service.ChannService;
import com.centit.common.controller.BaseController;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.shopcar.model.ShopcarChannInfoModel;
import com.centit.drp.main.shopcar.model.ShopcarModel;
import com.centit.drp.main.shopcar.service.ShopcarService;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * 分销首页Controller
 * 
 */
@Controller
@RequestMapping("/drp/shopcar")
public class ShopcarController extends BaseController {

	/** 权限对象 **/
	/** 维护 */
	// 增删改查
	private static String PVG_BWS="FX0020101";
    private static String PVG_EDIT="FX0020102";
    private static String PVG_DELETE="FX0020102";
	// 启用,停用
	private static String PVG_START_STOP = "";
	// 确认，取消
	private static String PVG_FINISH_CANCLE = "";
	/** end */
	/** 审批维护必须维护字段 **/
	// 提交撤销
	private static String PVG_COMMIT_CANCLE = "";
	private static String PVG_TRACE = "";
	
	//是否显示赠品按钮
	private static String PVG_BWS_GIFT = "";
	
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
	private ShopcarService shopcarService;
	
	@Autowired
	private ChannService channService;
	
	private static final String webPath = "drp/main/shopcar";

	private Logger logger = Logger.getLogger(ShopcarController.class);

	/**
	 * 删除购物车
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_DELETE))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		String SHOP_CART_IDS = request.getParameter("SHOP_CART_IDS");
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("SHOP_CART_IDS", SHOP_CART_IDS);
			params.put("UPD_NAME", userBean.getXM());
			params.put("UPDATOR", userBean.getXTYHID());
			params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_DROP);
			shopcarService.txDelete(params);
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
	 * 上帧显示查询页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toInfo")
	public String toInfo(
			HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)&&!QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		String CHANN_ID = userBean.getCHANN_ID();
		Map<String, Object> chann = shopcarService.getChannInfo(CHANN_ID);
		
		String sta;
		String AREA_SER_ID =  userBean.getAREA_SER_ID(); //区域服务中心ID
		if(StringUtil.isEmpty(AREA_SER_ID)&& !"true".equals(Consts.IS_OLD_FLOW)){
			sta = "show";
		}else{
			sta = "hidden";
		}
		//查询可用信用  add by zzb 2014-09-23
		if("1".equals(Consts.IS_NO_DELIVER_PLAN_FLAG)){
			/*String userCredit = LogicUtil.getU9CurrCredit(userBean.getCHANN_NO());
			request.setAttribute("userCredit", userCredit);*/
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		map.put("DEAL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		map.put("CHANN_ID", userBean.getCHANN_ID());
		ParamUtil.putStr2Map(request, "SHOP_CART_TYPE", map);//购物行类型
		String search = ParamUtil.get(request,"search");
		String module = ParamUtil.get(request,"module");
		String REBATEFLAG=request.getParameter("REBATEFLAG");//是否使用返利标记
		
		String LARGESSFLAG=request.getParameter("LARGESSFLAG");//是否使用赠品标记
		if("1".equals(REBATEFLAG)){
			map.put("PRDNOS", " b.IS_REBATE_FLAG=1 and c.SPCL_TECH_ID is null ");
		}
		StringBuffer sql=new StringBuffer();
		if("1".equals(LARGESSFLAG)){
			sql.append(" b.IS_CAN_FREE_FLAG=").append(BusinessConsts.YJLBJ_FLAG_TRUE);
			sql.append(" and c.SPCL_TECH_ID is null");
		}else{
			sql.append(" 1=1");
		}
		map.put("sql", sql.toString());
		//图片服务器地址
//		String picture_url = LogicUtil.getPicServerURL(userBean.getCURRT_PIC_URL(),"");
		map.put("QXJBCON", ParamUtil.getPvgCon(search,module,PVG_BWS,PVG_BWS_AUDIT,AUD_TAB_KEY, AUD_TAB_NAME,AUD_BUSS_TYPE,AUD_BUSS_STATE,userBean));
		// 字段排序
		ParamUtil.setOrderField(request, map, "u.PRD_NO", "DESC");
		List<Map<String,Object>> list = shopcarService.pageQuery(map);
		
		// 获取用户额度信息
		List<Map<String,String>> moneyList = channService.getLedgerChrgList(userBean.getCHANN_ID());
		request.setAttribute("sta", sta);
//		request.setAttribute("picture_url", picture_url);
		request.setAttribute("params", map);
		request.setAttribute("pvg", setPvg(userBean));
		request.setAttribute("list", list);
		request.setAttribute("moneyList", moneyList);
		request.setAttribute("CHANN_ID", userBean.getCHANN_ID());
		request.setAttribute("REBATEFLAG", REBATEFLAG);
		request.setAttribute("LARGESSFLAG", LARGESSFLAG);
		request.setAttribute("params", chann);
		request.setAttribute("sta", sta);
		request.setAttribute("pvg", setPvg(userBean));
		return view(webPath,"Shopcar_OrderInfo");
	}

	// 点击图片弹出窗口显示大图片
	@RequestMapping("/toPicture")
	public String toPicture(HttpServletRequest request, HttpServletResponse response) {
		String fileName = request.getParameter("fileName");
		request.setAttribute("fileName", fileName);
		return view(webPath,"Shopcar_Picture");
	}

	// 转订货订单
	@RequestMapping("/edit")
	public void edit(
			HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			Map<String,String> shopParams=new HashMap<String, String>();
			String jsonDate = ParamUtil.get(request, "jsonData");
			String tabData = ParamUtil.get(request, "tabData");
			shopParams.put("rebate", request.getParameter("rebate"));
			shopParams.put("LARGESSDSCT", request.getParameter("LARGESSDSCT"));
			shopParams.put("LARGESSFLAG", request.getParameter("LARGESSFLAG"));
			if(StringUtil.isEmpty(shopParams.get("rebate"))){
				shopParams.put("rebate", "0");
			}
			if(StringUtil.isEmpty(shopParams.get("LARGESSFLAG"))){
				shopParams.put("LARGESSFLAG", "0");
			}
			shopParams.put("type", request.getParameter("type"));
			shopParams.put("SHOP_CART_IDS", request.getParameter("SHOP_CART_IDS"));
			shopParams.put("REBATEDSCT", request.getParameter("REBATEDSCT"));
			List<ShopcarModel> ModelList = null;
			ShopcarChannInfoModel channModel = null;
			if (!StringUtil.isEmpty(jsonDate)) {
				ModelList = new Gson().fromJson(jsonDate,
						new TypeToken<List<ShopcarModel>>() {
						}.getType());
			}
			if (!StringUtil.isEmpty(tabData)) {
				channModel = new Gson().fromJson(tabData,
						new TypeToken<ShopcarChannInfoModel>() {
						}.getType());
			}
			boolean flag=shopcarService.txSaveCommit(shopParams,ModelList,userBean,channModel);
			if(flag){
				jsonResult = jsonResult(true, "操作成功");
			}else{
				jsonResult = jsonResult(false, "操作失败");
			}
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
	
	@RequestMapping("/toDiv")
	public String toDiv(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if (Consts.FUN_CHEK_PVG
				&& (!QXUtil.checkMKQX(userBean, PVG_BWS) && !QXUtil.checkMKQX(
						userBean, PVG_BWS_AUDIT))) {
			throw new ServiceException("对不起，您没有权限 !");
		}
		String json = request.getParameter("param");
		
		request.setAttribute("rst", LogicUtil.StrToObj(json, Map.class));
		return view(webPath,"Shopcar_openDiv");
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
		pvgMap.put("PVG_TRACE_AUDIT", QXUtil.checkPvg(userBean, PVG_TRACE_AUDIT));
		pvgMap.put("PVG_BWS_GIFT", QXUtil.checkPvg(userBean, PVG_BWS_GIFT));
		
		pvgMap.put("AUD_TAB_NAME", AUD_TAB_NAME);
		pvgMap.put("AUD_TAB_KEY", AUD_TAB_KEY);
		pvgMap.put("AUD_BUSS_STATE", AUD_BUSS_STATE);
		pvgMap.put("AUD_BUSS_TYPE", AUD_BUSS_TYPE);
		pvgMap.put("AUD_FLOW_INS", AUD_FLOW_INS);
		pvgMap.put("AUD_FLOW_INS", AUD_FLOW_INS);
		
		return pvgMap;
	}

}
