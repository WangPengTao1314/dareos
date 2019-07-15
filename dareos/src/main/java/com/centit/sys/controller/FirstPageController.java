package com.centit.sys.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.centit.common.controller.BaseController;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.model.Result;
import com.centit.commons.util.MessTimerTask;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.MessageModel;
import com.centit.sys.model.NoticeModel;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.FirstPageService;
import com.centit.sys.service.NoticeService;
import com.centit.sys.service.SysLoginService;

/**
 * 
 * @ClassName: FirstPageController
 * @Description: 首页
 * @author: liu_yg
 * @date: 2019年2月28日 下午2:52:50
 */
@Controller
@RequestMapping("sys/first")
public class FirstPageController extends BaseController {

	/** The logger. */
	private Logger logger = Logger.getLogger(FirstPageController.class);

	private static final String webPath = "sys/message";

	@Autowired
	private FirstPageService firstPageService;

	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private SysLoginService sysLoginService;

	@PostMapping("/getNewShortInfo")
	public String getNewShortInfo(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = (UserBean) request.getSession(false).getAttribute("UserBean");
		if (userBean != null) {
			List<MessageModel> oldModelList = MessTimerTask.getReturnList();
			List<MessageModel> rntModel = new ArrayList<MessageModel>();

			if (null != oldModelList && oldModelList.size() > 0) {
				int size = oldModelList.size();
				String yhid = userBean.getXTYHID();
				for (int i = 0; i < size; i++) {
					MessageModel tmpModel = new MessageModel();
					MessageModel oldModel = oldModelList.get(i);
					String RECEIVEID = oldModel.getRECEIVEID();
					if (yhid.equals(RECEIVEID)) {
						tmpModel.setMESSAGEID(oldModel.getMESSAGEID());
						tmpModel.setCKZT(oldModel.getCKZT());
						tmpModel.setMSGINFO(oldModel.getMSGINFO());
						tmpModel.setSENDID(oldModel.getSENDID());
						tmpModel.setSENDER(oldModel.getSENDER());
						tmpModel.setSENDTIME(oldModel.getSENDTIME());
						tmpModel.setRECEIVEID(RECEIVEID);
						tmpModel.setRECEIVER(oldModel.getRECEIVER());
						rntModel.add(tmpModel);
					}
					if (rntModel.size() == 500) {// 每个人只能看前500条消息
						break;
					}
				}
				request.setAttribute("modelList", rntModel);
			} else {
				request.setAttribute("modelList", null);
			}
			request.setAttribute("XTYHZTXXID", userBean.getLoginZTXXID());
		}
		// 1分销
		request.setAttribute("IS_DRP_LEDGER", userBean.getIS_DRP_LEDGER());
		return view(webPath, "message");
	}

	/**
	 * 根据短消息ID把消息插入已查看表中.
	 * 
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@PostMapping("/txInsertCkztByDxxid")
	public void txInsertCkztByDxxid(HttpServletRequest request, HttpServletResponse response) {
		logger.info("txUpdateCkztByDxxid方法调用开始");
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();

		String dxxid = request.getParameter("dxxid");
		Map<String, String> map = new HashMap<String, String>();
		map.put("MSGTRACEID", StringUtil.uuid32len());
		map.put("MESSAGEID", dxxid);
		if ("1".equals(firstPageService.txInsertCkztByDxxid(map))) {
			jsonResult = jsonResult(true, "SUCCESS");
		} else {
			jsonResult = jsonResult(false, "查看数据出错！");
		}
		// 修改对象中消息的查看状态
		for (int i = 0; i < MessTimerTask.getReturnList().size(); i++) {
			if (dxxid.equals(MessTimerTask.getReturnList().get(i).getMESSAGEID())) {
				MessTimerTask.getReturnList().get(i).setCKZT("1");
			}
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
		logger.info("txUpdateCkztByDxxid方法调用结束");
	}

	/**
	 * 将短消息插入表中.
	 * 
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@PostMapping("/txInsertMessage")
	public void txInsertMessage(HttpServletRequest request, HttpServletResponse response) {
		logger.info("txInsertMessage方法调用开始");
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();

		String xtyhids = request.getParameter("xtyhids");
		String bmxxids = request.getParameter("bmxxids");
		String jgxxids = request.getParameter("jgxxids");
		String fsnr = request.getParameter("fsnr");
		xtyhids = "'" + xtyhids.replaceAll(",", "','") + "'";
		bmxxids = "'" + bmxxids.replaceAll(",", "','") + "'";
		jgxxids = "'" + jgxxids.replaceAll(",", "','") + "'";
		Map<String, String> map = new TreeMap<String, String>();
		UserBean userBean = (UserBean) request.getSession(false).getAttribute("UserBean");

		map.put("YHBH", userBean.getYHBH());
		map.put("SENDID", userBean.getXTYHID());
		map.put("SENDER", userBean.getYHM());
		map.put("MSGINFO", fsnr);
		map.put("XTYHID", xtyhids);
		map.put("BMXXID", bmxxids);
		map.put("JGXXID", jgxxids);

		if (firstPageService.txInsertMessage(map)) {
			jsonResult = jsonResult(true, "消息发送成功");
		} else {
			jsonResult = jsonResult(false, "消息发送失败！");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
		logger.info("txInsertMessage方法调用结束");
	}

	/**
	 * 将短消息插入表中 调用 var _xtyhids=""; var _bmxxids="000001"; var _jgxxids=""; var
	 * _jsxxids=""; var _fsnr="方法调用开始，测试审核发送短消息是否成功！"; $.ajax({ url:
	 * ctxPath+"/firstPage.shtml?action=txInsertMessageByAudit", type:"POST",
	 * dataType:"text", data:{xtyhids:_xtyhids,bmxxids:_bmxxids,jgxxids:_jgxxids,
	 * fsnr:_fsnr,jsxxids:_jsxxids}, complete: function(xhr){ eval("json =
	 * "+xhr.responseText); if(json.success==true){
	 * parent.showErrorMsg(utf8Decode(json.messages)); }else{
	 * parent.showErrorMsg(utf8Decode(json.messages)); } } });
	 * 
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@PostMapping("/txInsertMessageByAudit")
	public void txInsertMessageByAudit(HttpServletRequest request, HttpServletResponse response) {
		logger.info("txInsertMessageByAudit方法调用开始");
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();

		String xtyhids = request.getParameter("xtyhids");
		String bmxxids = request.getParameter("bmxxids");
		String jgxxids = request.getParameter("jgxxids");
		String jsxxids = request.getParameter("jsxxids");
		String fsnr = request.getParameter("fsnr");
		xtyhids = "'" + xtyhids.replaceAll(",", "','") + "'";
		bmxxids = "'" + bmxxids.replaceAll(",", "','") + "'";
		jgxxids = "'" + jgxxids.replaceAll(",", "','") + "'";
		jsxxids = "'" + jsxxids.replaceAll(",", "','") + "'";
		Map<String, String> map = new TreeMap<String, String>();
		UserBean userBean = (UserBean) request.getSession(false).getAttribute("UserBean");

		map.put("YHBH", userBean.getYHBH());
		map.put("SENDID", userBean.getXTYHID());
		map.put("SENDER", userBean.getYHM());
		map.put("MSGINFO", fsnr);
		map.put("XTYHID", xtyhids);
		map.put("BMXXID", bmxxids);
		map.put("JGXXID", jgxxids);
		map.put("XTJSID", jsxxids);

		if (firstPageService.txInsertMessageByAudit(map)) {
			jsonResult = jsonResult(true, "消息发送成功");
		} else {
			jsonResult = jsonResult(false, "消息发送失败！");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
		logger.info("txInsertMessageByAudit方法调用结束");
	}

	/**
	 * 首页
	 * 
	 * @return the action forward
	 */
	@RequestMapping(value = { "/toFirst" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toFirst(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = (UserBean) request.getSession(false).getAttribute("UserBean");
		Map<String, Object> params = new HashMap<String, Object>();
		// 公告消息列表
		this.queryNotice(userBean, request);
		// 首页报表汇总
//		String LEDGER_ID = request.getParameter("LEDGER_ID");
//		params.put("LEDGER_ID", LEDGER_ID);
		Map<String, Object> sumList = firstPageService.getAuth(userBean);
		request.setAttribute("sumlist", sumList);// 首页：portal.jsp
		request.setAttribute("ztxxs", userBean.getFGZTXXIDS());
		
		//待办事项
		List<Map<String, Object>> aUsrMenus = new ArrayList<Map<String, Object>>();
			aUsrMenus = firstPageService.findMenuByUserId2(userBean.getXTYHID(),userBean.getIS_DRP_LEDGER());
		
		
		JSONObject json=Consts.FIRST_TO_DO_MENUINFO;
		Map<String,Map<String,String>> menuList = new HashMap<String, Map<String,String>>();
		List<String> list=new ArrayList<String>(userBean.getQXMap().keySet());
		for (int i = 0; i < list.size(); i++) {
			JSONObject js = json.getJSONObject(list.get(i));
			if(js!=null){
				Map<String,String> map=LogicUtil.StrToObj(js.toString(), Map.class);
				// 根据查询条件拼接数量
				int con = firstPageService.getToDoCon(map,userBean);
				if(con>0){
					String key = list.get(i).substring(0,list.get(i).length()-2);
					Map<String,String> mapMenu=menuList.get(key);
					if(mapMenu==null){
						map.put("cont", con+"");
						menuList.put(key, map);
					}else{
						mapMenu.put("cont", (Integer.parseInt(mapMenu.get("cont"))+con)+"");
					}
				}
			}
		}
		List<Map<String,String>> listMenu = new ArrayList<Map<String,String>>();
		for (String key : menuList.keySet()) {
			listMenu.add(menuList.get(key));
		}
		request.setAttribute("toDoList", listMenu);
		//常用功能
		if (aUsrMenus == null || aUsrMenus.isEmpty()) {
			request.setAttribute("msg", "您未被授权，请检查角色菜单配置！");
			return view(webPath, Consts.FAILURE);
		}
		request.setAttribute("CommonFunc", aUsrMenus);
		
//		request.setAttribute("mapCount", firstPageService.queryCounts(null));
		return view("index", "portal");
	}

	/**
	 * 根据账套筛选
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/goFirst" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String goFirst(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);

		// 公告消息列表
		this.queryNotice(userBean, request);

		Map<String, Object> params = new HashMap<String, Object>();
		String LEDGER_ID = request.getParameter("LEDGER_ID");

		params.put("LEDGER_ID", LEDGER_ID);
		Map<String, Object> sumList = firstPageService.queryFlash(params, userBean);
		request.setAttribute("sumlist", sumList);
//		request.setAttribute("mapCount", firstPageService.queryCounts(null));
		return view("index", "portal");

	}

	/**
	 * 首页查询公告
	 * 
	 * @param userBean
	 * @param request
	 */
	public void queryNotice(UserBean userBean, HttpServletRequest request) {
		List<NoticeModel> noticeList = null;
		noticeList = firstPageService.queryNoticeList(userBean, 4);
		request.setAttribute("noticeList", noticeList);
	}

	

	@RequestMapping("/queryNoticeList")
	public String toList(HttpServletRequest request, HttpServletResponse response, PageDesc pageDesc) {

		UserBean userBean = ParamUtil.getUserBean(request);

		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "STATE", params);
		ParamUtil.putStr2Map(request, "STATIME", params);
		ParamUtil.putStr2Map(request, "ENDTIME", params);
		ParamUtil.putStr2Map(request, "CRETIME_START", params);
		ParamUtil.putStr2Map(request, "CRETIME_END", params);
		ParamUtil.putStr2Map(request, "NOTICE_TYPE", params);
		ParamUtil.putStr2Map(request, "STATE", params);
		/* params.put("ZTXXID", userBean.getLoginZTXXID()); */
		// 只查询0的记录。1为删除。0为正常
		params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);

		if (BusinessConsts.INTEGER_1.equals(userBean.getIS_DRP_LEDGER())) {
			params.put("NOTICE_OBJ", "3");
		} else {
			params.put("NOTICE_OBJ", "2");
		}
		// 字段排序
		ParamUtil.setOrderField(request, params);
		noticeService.pageQuery(params, pageDesc);

		request.setAttribute("page", pageDesc);
		return view("index", "NoticeViewList");
	}

	/**
	 * 获得当前年份，月份
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void currentYearAndMonth(HttpServletRequest request, HttpServletResponse response) {
		int currentYear = 0;
		Calendar c = Calendar.getInstance();// 获得系统当前日期
		currentYear = c.get(Calendar.YEAR);
		// currentMonth = c.get(Calendar.MONTH) + 1;// 系统日期从0开始算起
		String currentMonth = "";
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		java.util.Date dd = Calendar.getInstance().getTime();
		currentMonth = sdf.format(dd);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("currentYear", currentYear);// 当前年
		param.put("theYearBeforeLast", currentYear - 1);// 前一年
		param.put("threeYearsAgo", currentYear - 2);
		param.put("currentMonth", currentMonth);
		String jsonResult = jsonResult();

		jsonResult = JSONObject.toJSONString(new Result(true, param, "保存成功"));
		PrintWriter writer = getWriter(response);
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}

	/**
	 * 总部订货金额前30 区域维度 按季度
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void queryAreaTopByQuarter(HttpServletRequest request, HttpServletResponse response) {
		logger.info("queryAreaTopByQuarter方法调用开始");
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String rptYear = StringUtil.nullToSring(request.getParameter("RPT_YEAR"));
			String rptQuarter = StringUtil.nullToSring(request.getParameter("RPT_QUARTER"));// 季度
			String RPT_RAKING = StringUtil.nullToSring(request.getParameter("RPT_RAKING"));// 排名
			String RPT_MONTH = StringUtil.nullToSring(request.getParameter("RPT_MONTH"));// 月份
			String RPT_FLAG = StringUtil.nullToSring(request.getParameter("RPT_FLAG"));// 月份标志
			if (StringUtil.isEmpty(RPT_RAKING)) {
				RPT_RAKING = "10";
			}
			if (StringUtil.isEmpty(rptYear)) {
				throw new ServiceException("对不起！请选择年份！");
			}

			Map<String, String> map = new HashMap<String, String>();
			if ("0".equals(RPT_FLAG)) {
				if (StringUtil.isEmpty(RPT_MONTH)) {
					throw new ServiceException("对不起！请选择月份！");
				}
				RPT_MONTH = StringUtil.joinConIn(RPT_MONTH, ",");
				map.put("COLUMN", "temp.S_MM");
				map.put("RPT_QUARTER", RPT_MONTH);
				map.put("G_CLUMN", "S_MM");
			} else {
				if (StringUtil.isEmpty(rptQuarter)) {
					throw new ServiceException("对不起！请选择季节！");
				}
				rptQuarter = StringUtil.joinConIn(rptQuarter, ",");
				map.put("RPT_QUARTER", rptQuarter);
				map.put("COLUMN", "temp.S_QUARTER");
				map.put("G_CLUMN", "S_QUARTER");
			}
			map.put("YEAR", rptYear);
			map.put("RAKING", RPT_RAKING);
			List<Map<String, String>> rst = null;// firstPageService.queryAreaTopByQuarter(map);
			if (rst == null) {
				rst = new ArrayList<Map<String, String>>();
			}
			jsonResult = jsonResult(true, JSONObject.toJSONString(rst));
			System.out.println(jsonResult);
		} catch (ServiceException e) {
			jsonResult = jsonResult(false, e.getMessage());
		} catch (Exception e) {
			jsonResult = jsonResult(false, "出错!" + e.getMessage());
			e.printStackTrace();
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
		logger.info("queryAreaTopByQuarter方法调用结束");
	}

}
