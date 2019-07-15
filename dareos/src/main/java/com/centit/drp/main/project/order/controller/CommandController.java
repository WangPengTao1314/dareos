package com.centit.drp.main.project.order.controller;

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
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.core.utils.SpringContextHolder;
import com.centit.drp.main.project.management.model.ProOrderModel;
import com.centit.drp.main.project.management.service.ManageService;
import com.centit.drp.main.project.order.service.CommandService;
import com.centit.sys.model.UserBean;
/**
 * 项目管理模块
 * 项目指令
 * @author wang_pt
 *
 */
@Controller
@RequestMapping("/project/order")
public class CommandController extends BaseController {
	
	@Autowired
	private CommandService commandService;

	private static final String webPath = "drp/main/project/order";
	
	/** 权限对象维护**/
    //query
    private static String PVG_BWS="BS0012001";
    //edit
    private static String PVG_EDIT="BS0012002";
    //delete
    private static String PVG_DELETE="BS0012003";
    
	/**
	 * 跳转页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/toFrames" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toFrames(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
		return view(webPath, "Order_Frame");
	}

	/**
	 * To list.
	 * 
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	@RequestMapping(value = { "/toList" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toList(HttpServletRequest request, HttpServletResponse response, PageDesc pageDesc) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "PROJECT_NO", params);// 项目编号
		ParamUtil.putStr2Map(request, "PROJECT_NAME", params);// 项目名称
		ParamUtil.putStr2Map(request, "DIRECTIVE_TYPE", params);// 合同签约单位
		ParamUtil.putStr2Map(request, "STATE", params);// 状态
		ParamUtil.putStr2Map(request, "pageSize", params);
		String search = ParamUtil.get(request, "search");
		String module = ParamUtil.get(request, "module");
		params.put("QXJBCON", ParamUtil.getPvgCon(search,module,PVG_BWS,PVG_EDIT,PVG_DELETE,"", "", "", userBean));
		params.put("XTYHID", userBean.getXTYHID());
		commandService.pageQuery(params, pageDesc);
		request.setAttribute("params", params);
		request.setAttribute("pvg",setPvg(userBean));
		request.setAttribute("page", pageDesc);
		return view(webPath, "Order_List");
	}
	
	/**
	 * to modify page.
	 * 
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	@RequestMapping(value = { "/toEdit" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toEdit(HttpServletRequest request, HttpServletResponse response, PageDesc pageDesc) {
		UserBean userBean =  ParamUtil.getUserBean(request);
 		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)))
     	{
     		throw new ServiceException("对不起，您没有权限 !");
     	}
		Map<String, Object> params = new HashMap<String, Object>();
		int pageNo = ParamUtil.getInt(request, "pageNo", 1);
		ParamUtil.putStr2Map(request, "project_directive_id", params);
		String project_directive_id = ParamUtil.get(request, "project_directive_id");
		if (StringUtils.isNotEmpty(project_directive_id)) {
			List<Map<String, Object>> list = commandService.getFileInfo(params);
			params.put("project_name", list.get(0).get("PROJECT_NAME"));
			params.put("project_id", list.get(0).get("PROJECT_ID"));
			params.put("project_no", list.get(0).get("PROJECT_NO"));
			request.setAttribute("page", list);
			request.setAttribute("params", params);
			request.setAttribute("pageNo", pageNo);
		}
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
		UserBean userBean =  ParamUtil.getUserBean(request);
 		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)))
     	{
     		throw new ServiceException("对不起，您没有权限 !");
     	}
		ManageService manageService = SpringContextHolder.getBean(ManageService.class);
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String jsonData = ParamUtil.get(request, "jsonData");
		ProOrderModel model = LogicUtil.StrToObj(jsonData, ProOrderModel.class);
		try {
			manageService.saveOrderFile(model, userBean);
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
	 * 指令下达
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/release" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void release(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
 		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)))
     	{
     		throw new ServiceException("对不起，您没有权限 !");
     	}
		Map<String, Object> params = new HashMap<String, Object>();
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String project_directive_id = ParamUtil.get(request, "project_directive_id");
		try {
			if (StringUtils.isNotEmpty(project_directive_id)) {
				params.put("project_directive_id", project_directive_id);
				params.put("state", "待接收");
				commandService.updateSate(userBean, params);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "指令下达失败!");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}

	/**
	 * 指令接受
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/accept" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void accept(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
 		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)))
     	{
     		throw new ServiceException("对不起，您没有权限 !");
     	}
		Map<String, Object> params = new HashMap<String, Object>();
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String project_directive_id = ParamUtil.get(request, "project_directive_id");
		try {
			if (StringUtils.isNotEmpty(project_directive_id)) {
				params.put("project_directive_id", project_directive_id);
				params.put("state", "已接受");
				commandService.updateSate(userBean, params);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "指令接受失败!");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}

	/**
	 * 删除.
	 * 
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
 		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_DELETE)))
     	{
     		throw new ServiceException("对不起，您没有权限 !");
     	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String project_directive_id = ParamUtil.get(request, "att_id");
		try {
			if (StringUtils.isNotEmpty(project_directive_id)) {
				commandService.delete(project_directive_id, userBean);
			}
		} catch (Exception e) {
			jsonResult = jsonResult(false, "删除失败");
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
		return pvgMap;
	}
	

}
