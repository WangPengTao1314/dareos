package com.centit.drp.main.project.tendering.controller;

import java.io.PrintWriter;
import java.util.HashMap;
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
import com.centit.drp.main.project.tendering.model.ProjectInfoModel;
import com.centit.drp.main.project.tendering.service.ProjectInfoService;
import com.centit.sys.model.UserBean;

/**
 *  项目管理模块
 *  招投标管理
 * @author wang_pt
 *
 */
@Controller
@RequestMapping("/project/tendering")
public class ProjectInfoController extends BaseController {

	@Autowired
	private ProjectInfoService projectInfoService;

	private static final String webPath = "drp/main/project/tendering";
	
	/** 权限对象维护**/
    //增改删
    private static String PVG_BWS="BS0010101";
    private static String PVG_EDIT="BS0010102";
    private static String PVG_DELETE="BS0010103";
	
	/**
	 * 跳转页面
     * @param request
     * @param response
     * @return the action forward
     */
	 @RequestMapping(value = { "/toFrames"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toFrames(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
    	request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
    	return view(webPath, "Tendering_Frame");
    }
	 
	 /**
		 * To list
		 * @param request
		 * @param response
		 * @param pageDesc
		 * @return the action forward
		 */
		@RequestMapping(value = { "/toList" }, method = {RequestMethod.GET,RequestMethod.POST })
		public String toList(HttpServletRequest request, HttpServletResponse response, PageDesc pageDesc) {
			UserBean userBean =  ParamUtil.getUserBean(request);
			if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)))
	    	{
	    		throw new ServiceException("对不起，您没有权限 !");
	    	}
			Map<String, Object> params = new HashMap<String, Object>();
			ParamUtil.putStr2Map(request, "tendering_no", params);// 招标编号
			ParamUtil.putStr2Map(request, "project_name", params);// 投招标项目名称
			ParamUtil.putStr2Map(request, "tendering_unit", params);// 招标单位
			ParamUtil.putStr2Map(request, "project_type", params);// 项目类型
			ParamUtil.putStr2Map(request, "pageSize", params);//
			String search = ParamUtil.get(request, "search");
			String module = ParamUtil.get(request, "module");
			params.put("QXJBCON", ParamUtil.getPvgCon(search,module,PVG_BWS,PVG_EDIT,PVG_DELETE,"", "", "", userBean));
			
			//params.put("XTYHID", userBean.getXTYHID());
	 		projectInfoService.pageQuery(params, pageDesc);
			request.setAttribute("params", params);
			request.setAttribute("pvg",setPvg(userBean));
			request.setAttribute("page", pageDesc);
			System.out.println(request.getAttribute("page").toString());
			return view(webPath,"Tendering_List");//
			
		}

	 /**
     * to modify page.
     * 
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @RequestMapping(value = { "/toEdit"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toModi( HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean =  ParamUtil.getUserBean(request);
 		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)))
     	{
     		throw new ServiceException("对不起，您没有权限 !");
     	}
        Map<String, Object> params = new HashMap<String, Object>();
        int pageNo = ParamUtil.getInt(request, "pageNo", 1);
        ParamUtil.putStr2Map(request, "tendering_id", params);
        String tendering_id = ParamUtil.get(request, "tendering_id");
        if (StringUtils.isNotEmpty(tendering_id)) {
        	Map<String, Object> entry = projectInfoService.query(params);
        	request.setAttribute("entry", entry);
        	request.setAttribute("pageNo", pageNo);
		}
        return view(webPath, "Tendering_Edit");
    }
	/**
	 * edit
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
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String jsonData = ParamUtil.get(request, "jsonData");
		ProjectInfoModel model = LogicUtil.StrToObj(jsonData, ProjectInfoModel.class);
 		try {
 			projectInfoService.modify(model,request);
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
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/delete" }, method = {RequestMethod.POST })
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
 		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_DELETE)))
     	{
     		throw new ServiceException("对不起，您没有权限 !");
     	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String tendering_id = ParamUtil.get(request, "tendering_id");
			projectInfoService.delete(tendering_id, userBean);

		} catch (Exception e) {
			jsonResult = jsonResult(false, "删除失败");
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
	public void deleteFile(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String att_ids = ParamUtil.get(request, "att_ids");
			projectInfoService.deleteFile(att_ids, userBean);
		} catch (Exception e) {
			jsonResult = jsonResult(false, "删除失败");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}
	
	
	/**
	   *     投标单明细
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/toDetail" }, method = {RequestMethod.GET, RequestMethod.POST })
	public String toDetail(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "tendering_id", params);// 招标编号
		 Map<String, Object> entry = projectInfoService.query(params);
		request.setAttribute("entry", entry);
		//返回的位明细页面，
 		return view(webPath, "Tendering_Detail");
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
