package com.centit.drp.main.project.management.controller;

import java.io.PrintWriter;
import java.util.Arrays;
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
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.core.utils.SpringContextHolder;
import com.centit.drp.main.project.management.model.ManageModel;
import com.centit.drp.main.project.management.model.PayableModel;
import com.centit.drp.main.project.management.model.ProOrderModel;
import com.centit.drp.main.project.management.service.ManageService;
import com.centit.drp.main.project.order.service.CommandService;
import com.centit.drp.main.project.tendering.service.ProjectInfoService;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 *  项目管理模块
 *  工程单维护
 * @author wang_pt
 *
 */
@Controller
@RequestMapping("/project/manage")
public class ManageController extends BaseController {

	@Autowired
	private ManageService manageService;

	private static final String webPath = "drp/main/project/manage";
	
	/** 权限对象维护**/
    //查询
    private static String PVG_BWS="BS0010201";
    //编辑
    private static String PVG_EDIT="BS0010202";
    //删除
    private static String PVG_DELETE="BS0010203";

	/**
	 * toFrames
     * @param request
     * @param response
     * @return Manage_Frame
     */
	 @RequestMapping(value = { "/toFrames"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toFrames(HttpServletRequest request, HttpServletResponse response) {
		 UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
    	request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
    	return view(webPath, "Manage_Frame");
    }
	 
	/**
	 *  toList
	 * @param request
	 * @param response
	 * @param pageDesc
	 * @return Manage_List
	 */
	@RequestMapping(value = { "/toList"}, method = { RequestMethod.GET, RequestMethod.POST })
	public String toList(HttpServletRequest request, HttpServletResponse response, PageDesc pageDesc) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "PROJECT_NO", params);// 项目编号
		ParamUtil.putStr2Map(request, "PROJECT_NAME", params);// 项目名称
		ParamUtil.putStr2Map(request, "CONTRACT_UNIT", params);// 合同签约单位
		ParamUtil.putStr2Map(request, "PROJECT_MANAGER", params);// 负责人
		ParamUtil.putStr2Map(request, "STATE", params);// 状态
		ParamUtil.putStr2Map(request, "pageSize", params);
		String search = ParamUtil.get(request, "search");
		String module = ParamUtil.get(request, "module");
		params.put("QXJBCON", ParamUtil.getPvgCon(search,module,PVG_BWS,PVG_EDIT,PVG_DELETE,"", "", "", userBean));
		//params.put("XTYHID", userBean.getXTYHID());
		
		// 字段排序
	    ParamUtil.setOrderField(request, params, "cre_time", "DESC");
		manageService.pageQuery(params, pageDesc);
		request.setAttribute("params", params);
		request.setAttribute("pvg",setPvg(userBean));
		request.setAttribute("page", pageDesc);
		return view(webPath, "Manage_List");
	}

	/**
	 * toEdit
	 * @param request
	 * @param response
	 * @return Manage_Edit
	 */
	@RequestMapping(value = { "/toEdit" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toEdit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		int pageNo = ParamUtil.getInt(request, "pageNo", 1);
		ParamUtil.putStr2Map(request, "project_id", params);
		String project_id = ParamUtil.get(request, "project_id");
		if (StringUtils.isNotEmpty(project_id)) {
			Map<String, Object> entry = manageService.query(params);
			request.setAttribute("entry", entry);
			request.setAttribute("pageNo", pageNo);

		}
		return view(webPath, "Manage_Edit");
	}

	/**
	 * edit
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/edit" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void edit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String jsonData = ParamUtil.get(request, "jsonData");
		ManageModel model = LogicUtil.StrToObj(jsonData, ManageModel.class);
		
 		try {
 			manageService.modify(model,request);
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
	 * delete
	 * @param request
	 * @param response
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
		String project_id = ParamUtil.get(request, "project_id");
		if (StringUtils.isNotEmpty(project_id)) {
			try {
				manageService.delete(project_id, userBean);
				
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
	 * showDetail
	 * @param request
	 * @param response
	 * @return Manage_Detail
	 */
	@RequestMapping(value = { "/toDetail" }, method = {RequestMethod.GET, RequestMethod.POST })
	public String showDetail(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "project_id", params);
		Map<String, Object> entry = manageService.querySun(params);
		request.setAttribute("entry", entry);
		return view(webPath, "Manage_Detail");
	}
	
	
	/**
	 * 项目节点维护
	 * @param request
	 * @param response
	 * @return Manage_Detail_Servicing
	 */
	@RequestMapping(value = { "/toMxEdit" }, method = {RequestMethod.GET, RequestMethod.POST })
	public String toMxEdit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		// 编辑
		ParamUtil.putStr2Map(request, "project_id", params);
		Map<String, Object> entry = manageService.querySun(params);
		request.setAttribute("entry", entry);
		// 返回的位明细页面
		return view(webPath,"Manage_Detail_Servicing");
	}


	/**
	 * 项目节点维护
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/mxEdit" }, method = {RequestMethod.GET, RequestMethod.POST })
	public void MxEdit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        String project_id = request.getParameter("project_id");
        String jsonDate = request.getParameter("childJsonData");    
        String parentJsonData = ParamUtil.get(request, "parentJsonData");
    	try {
    		List <PayableModel> modelList=null;
            if (!StringUtil.isEmpty(jsonDate)) {
            	modelList = new Gson().fromJson(jsonDate, new TypeToken <List <PayableModel>>() {
                }.getType());
            }
            ProOrderModel model=null;
            if (!StringUtil.isEmpty(parentJsonData)) {
            	model = LogicUtil.StrToObj(parentJsonData, ProOrderModel.class);
            }
            manageService.txChldEdit(model,modelList,userBean,project_id);
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
	 *  删除 付款阶段数据
	 * @param request  
	 * @param response 
	 */
	@RequestMapping("/mxDelete")
	public void mxDelete(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String PROJECT_PAYABLE_ID = ParamUtil.get(request, "PROJECT_PAYABLE_ID");
		if (StringUtils.isNotEmpty(PROJECT_PAYABLE_ID)) {
			try {
				manageService.sunDelete(PROJECT_PAYABLE_ID);
			} catch (Exception e) {
				jsonResult = jsonResult(false, "删除失败");
			}
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
    }
	
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~项目指令上传~~~~~~~~~~~~~~~~~~~~~~~~ */
	
	/**
	 * 项目指令上传 
	 * @param request
	 * @param response
	 * @return Manage_Com
	 */
	@RequestMapping(value = { "/toComEdit" }, method = {RequestMethod.GET, RequestMethod.POST })
	public String toComEdit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		//ParamUtil.putStr2Map(request, "state", params);// 状态
		ParamUtil.putStr2Map(request, "project_id", params);
		ParamUtil.putStr2Map(request, "pageSize", params);
		List<Map<String, Object>>list=manageService.toQueryFile(params);
		Map<String, Object> entry = manageService.query(params);
		//params.put("STATE", entry.get("STATE"));
		params.put("PROJECT_ID", entry.get("PROJECT_ID"));
		params.put("PROJECT_NO", entry.get("PROJECT_NO"));
		params.put("PROJECT_NAME", entry.get("PROJECT_NAME"));
		request.setAttribute("params", params);
		request.setAttribute("page", list); 
		// 返回的位明细页面
		return view(webPath,"Manage_Com");
	}
	
	 
	/** 
	 *   项目指令上传维护
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/comEdit" }, method = {RequestMethod.GET, RequestMethod.POST })
	public void comEdit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
 		try {
 			String jsonData = ParamUtil.get(request, "jsonData");
 			if (!StringUtil.isEmpty(jsonData)) {
 				ProOrderModel model = LogicUtil.StrToObj(jsonData, ProOrderModel.class);
 				manageService.saveOrderFile(model,userBean);
 			}
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
	 * 
	 * 上传指令删除
	 * @param request
	 * @param response
	 */
	@RequestMapping("/deleteOrder")
	public void deleteFile(HttpServletRequest request, HttpServletResponse response) {
		ProjectInfoService projectinfoservice = SpringContextHolder.getBean(ProjectInfoService.class);
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String project_directive_ids = ParamUtil.get(request,"project_directive_ids");
		if (StringUtils.isNotEmpty(project_directive_ids)) {
			try {
				projectinfoservice.deleteFile(project_directive_ids, userBean);
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
	 * 下发指令
	 * @param request
	 * @param response
	 */
	@RequestMapping("/sendOrder")
	public void sendOrder(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		CommandService commandservice = SpringContextHolder.getBean(CommandService.class);
		Map<String, Object> params = new HashMap<String, Object>();
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String project_directive_ids = ParamUtil.get(request,"project_directive_ids");
			try {
				
				if (StringUtils.isNotEmpty(project_directive_ids)) {
					List<String>lists =Arrays.asList(project_directive_ids.split(","));
					for(int i=0;i<lists.size();i++) {
						project_directive_ids=lists.get(i).substring(1,lists.get(i).length()-1);
						params.put("project_directive_id", project_directive_ids);
						params.put("state", "待接收");
						commandservice.updateSate(userBean,params);
					}
				}
			} catch (Exception e) {
				jsonResult = jsonResult(false, "指令下发失败!");
			}
			

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}
	
	
	/**
	 * deleteFile
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/deleteFile" }, method = {RequestMethod.POST })
	public void deleteFiles(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			//
			
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
