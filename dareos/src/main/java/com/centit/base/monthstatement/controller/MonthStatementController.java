package com.centit.base.monthstatement.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.base.channchrg.controller.ChannChrgController;
import com.centit.base.monthstatement.model.MonthAcctModel;
import com.centit.base.monthstatement.service.MonthStatementService;
import com.centit.common.controller.BaseController;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.UserBean;

@Controller
@RequestMapping("/base/monthstatement")
public class MonthStatementController extends BaseController {
	// 日志记录
		/** The logger. */
		private Logger logger = Logger.getLogger(ChannChrgController.class);

		// 维护界面
		// 增删改查
		private static String PVG_BWS = "BS0012101";
		private static String PVG_EDIT = "BS0012102";
		private static String PVG_COMMIT = "BS0012103";
		
		private static final String webPath = "base/monthstatement";
		
		@Autowired
		private MonthStatementService service;
		
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
			return view(webPath,"MonthStatement_Frame");

		}
		
		/**
	     * 查询结果列表.
	     * @param mapping the mapping
	     * @param form the form
	     * @param request the request
	     * @param response the response
	     * 
	     * @return the action forward
	     */
		@RequestMapping(value = { "/toList"}, method = { RequestMethod.GET, RequestMethod.POST })
	    public String toList( HttpServletRequest request, HttpServletResponse response) {
			UserBean userBean = ParamUtil.getUserBean(request);
			if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_BWS)){
	    		throw new ServiceException("对不起，您没有权限 !");
	    	}
	    	Map<String, Object> params = LogicUtil.getParameterMap(request);
	    	if(StringUtil.isEmpty(String.valueOf(params.get("year")))){
	    		params.put("year",99999);
	    	}
			// 字段排序
			ParamUtil.setOrderField(request, params, "u.MONTH", "DESC");
			List<MonthAcctModel> list = service.pageQuery(params);
	    	request.setAttribute("params", params);
	    	request.setAttribute("list", list);
	    	request.setAttribute("pvg",setPvg(userBean));
	        return view(webPath,"MonthStatement_List");
	    }
		
		
		 /**
	     * 编辑.
	     * 
	     * @param mapping the mapping
	     * @param form the form
	     * @param request the request
	     * @param response the response
	     */
		@PostMapping("/edit")
	    public void edit( HttpServletRequest request, HttpServletResponse response) {
	    	UserBean userBean =  ParamUtil.getUserBean(request);
	    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean,PVG_EDIT))
	    	{
	    		throw new ServiceException("对不起，您没有权限 !");
	    	}
	    	PrintWriter writer = getWriter(response);
	        String jsonResult = jsonResult();
	        String year = ParamUtil.get(request, "year");
	    	try {
	    		service.edit(year);
	    		jsonResult = jsonResult(true, "操作成功");
			}catch(ServiceException s){
				jsonResult = jsonResult(false, s.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
	        if (null != writer) {
	            writer.write(jsonResult);
	            writer.close();
	        }
	    }
		
		/**
		 * 结算
		 * @param request
		 * @param response
		 */
		@PostMapping("/statement")
	    public void statement( HttpServletRequest request, HttpServletResponse response) {
			UserBean userBean =  ParamUtil.getUserBean(request);
			
			
			if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean,PVG_COMMIT))
	    	{
	    		throw new ServiceException("对不起，您没有权限 !");
	    	}
	        PrintWriter writer = getWriter(response);
	        String jsonResult = jsonResult();
	        try {
	        	String year = request.getParameter("year");
				String month = request.getParameter("month");
				service.statement(year,month,userBean);
				jsonResult = jsonResult(true, "操作成功");
			} catch (Exception e) {
				jsonResult = jsonResult(false, "操作成功");
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
			pvgMap.put("PVG_COMMIT", QXUtil.checkPvg(userBean, PVG_COMMIT));
			return pvgMap;
		}
}
