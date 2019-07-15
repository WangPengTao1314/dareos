package com.centit.sys.controller;

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
import com.centit.commons.model.ParamCover;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.MenuInfo;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.MenuService;

/**
 * The Class MenuAction.
 * 
 * @module 系统模块
 * @fuc 系统模块
 * @version 1.1
 * @author 朱晓文
 */
@Controller
@RequestMapping("/sys/menu")
public class MenuController extends BaseController {

    /** The menu service. */
	@Autowired
    private MenuService menuService;

    private static final String webPath = "sys/menu";

    /**
    /**
	 * 跳转页面
     * @param request
     * @param response
     * @return the action forward
     */
	 @RequestMapping(value = { "/toFrames"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toFrames(HttpServletRequest request, HttpServletResponse response) {
    	request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
    	return view(webPath, "Menu_Frame");
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
			
			Map<String, Object> params = new HashMap<String, Object>();
			ParamUtil.putStr2Map(request, "menuId", params);
		    ParamUtil.putStr2Map(request, "menuName", params);
		    ParamUtil.putStr2Map(request, "menuParId", params);
		    ParamUtil.putStr2Map(request, "pageSize", params);
	 		menuService.pageQuery(params, pageDesc);
			request.setAttribute("params", params);
			request.setAttribute("page", pageDesc);
			System.out.println(request.getAttribute("page").toString());
			return view(webPath,"Menu_List");//
			
		}
		
		
		/**
	     * to modify page.
	     * 
	     * @param request the request
	     * @param response the response
	     * 
	     * @return the action forward
	     */
	   @RequestMapping(value = { "/toAdd"}, method = { RequestMethod.GET, RequestMethod.POST })
	    public String toAdd( HttpServletRequest request, HttpServletResponse response) {
	        return view(webPath, "Menu_Add");
	    }

	    /**
		 * edit
		 * 
		 * @param request
		 * @param response
		 * @return 
		 */
		@RequestMapping(value = { "/add" }, method = { RequestMethod.GET, RequestMethod.POST })
		public void add(HttpServletRequest request, HttpServletResponse response) {
			
			PrintWriter writer = getWriter(response);
			String jsonResult = jsonResult();
			String jsonData = ParamUtil.get(request, "jsonData");
			Map <String,Object> params=new HashMap<String,Object>();
			MenuInfo model = LogicUtil.StrToObj(jsonData, MenuInfo.class);
	 		try {
	 			boolean flag=model.getMenuId().equals(model.getMenuParId());
	 			if(flag) {
	 				jsonResult = jsonResult(false, "菜单编号与父级菜单编号重复");
	 			}else {
	 				params.put("menuId",model.getMenuId());
	 				//params.put("menuParId",model.getMenuParId());
	 				Map<String, Object> entry = menuService.load(params);
	 				if (entry != null) {
	 					jsonResult = jsonResult(false, "该菜单已存在！！！");
	 				}else {
	 					menuService.insert(model);
	 				}
	 				
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
     * to modify page.
     * 
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @RequestMapping(value = { "/toEdit"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toModi( HttpServletRequest request, HttpServletResponse response) {
    	
        Map<String, Object> params = new HashMap<String, Object>();
        int pageNo = ParamUtil.getInt(request, "pageNo", 1);
        ParamUtil.putStr2Map(request, "menuId", params);
        String menuId = ParamUtil.get(request, "menuId");
        if (StringUtils.isNotEmpty(menuId)) {
        	 Map<String, Object> entry = menuService.load(params);
        	request.setAttribute("entry", entry);
        	request.setAttribute("pageNo", pageNo);
		}
        return view(webPath, "Menu_Edit");
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
		
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String jsonData = ParamUtil.get(request, "jsonData");
		//Map <String,Object> params=new HashMap<String,Object>();
		MenuInfo model = LogicUtil.StrToObj(jsonData, MenuInfo.class);
		boolean flag=model.getMenuId().equals(model.getMenuParId());
 		try {
 			if(flag) {
 				jsonResult = jsonResult(false, "菜单编号与父级菜单编号重复");
 			}else {
	 			//params.put("menuId",model.getMenuId());
	 			//params.put("menuParId",model.getMenuParId());
	 			//Map<String, Object> entry = menuService.load(params);
	 	        //if (entry == null) {
	 	        //	menuService.insert(model);
	 	       // }else {
	 	        	menuService.modify(model);
	 	        //}
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
	 * delete
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/delete" }, method = {RequestMethod.POST })
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String menuId = request.getParameter("menuId");
			menuService.delete(menuId);

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
		
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "menuId", params);// 招标编号
		 Map<String, Object> entry = menuService.load(params);
		request.setAttribute("entry", entry);
		//返回的位明细页面，
 		return view(webPath, "Menu_Detail");
	}
	
    

}
