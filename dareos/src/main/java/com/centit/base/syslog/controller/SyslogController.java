/**
 * 
 */
package com.centit.base.syslog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.base.syslog.service.SyslogService;
import com.centit.common.controller.BaseController;
import com.centit.commons.util.ParamUtil;
import com.centit.core.po.PageDesc;

/**
 * 系统日志
 * 
 * @author gu_hongxiu
 *
 */
@Controller
@RequestMapping("/base/syslog")
public class SyslogController extends BaseController {
	
	/** 
	 * 日志记录
	 * */
	private Logger logger = Logger.getLogger(SyslogController.class);
	@Autowired
	private SyslogService syslogService;
	
	private static final String webPath = "base/syslog";

	@GetMapping("/toFrames")
	public String toFrame(
			HttpServletRequest request, HttpServletResponse response) {
		// 设置跳转时需要的一些公用属性
		ParamUtil.setCommAttributeEx(request);
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request,
				"paramUrl"));
		return view(webPath,"Syslog_Frame");

	}
	
	/**
     * 系统日志列表.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * s
     * @return the action forward
     */
	@RequestMapping(value = { "/toList"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toList( HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("SYSLOG_ID", ParamUtil.utf8Decoder(request, "SYSLOG_ID"));		//系统日志id
    	params.put("UC_NAME", ParamUtil.utf8Decoder(request, "UC_NAME"));			//模块名称
        params.put("ACT_NAME", ParamUtil.utf8Decoder(request, "ACT_NAME"));			//操作名称
        params.put("OPRATOR", ParamUtil.utf8Decoder(request, "OPRATOR"));			//调用/被调用
        params.put("ACT_TIME_FROM", ParamUtil.utf8Decoder(request, "ACT_TIME_FROM"));//操作时间从
        params.put("ACT_TIME_TO", ParamUtil.utf8Decoder(request, "ACT_TIME_TO"));	 //操作时间到
        params.put("STATE", ParamUtil.utf8Decoder(request, "STATE"));				 //状态
        params.put("APPCODE", ParamUtil.utf8Decoder(request, "APPCODE"));			//调用方appcode
        params.put("APPID", ParamUtil.utf8Decoder(request, "APPID"));			//uid
        params.put("OPRCODE", ParamUtil.utf8Decoder(request, "OPRCODE"));	//服务码+操作码       
		
		syslogService.pageQuery(params, pageDesc);
		request.setAttribute("params", params);
		request.setAttribute("page", pageDesc);
        return view(webPath,"Syslog_List");
    }
    
    /**
     * 查看系统日志详细信息.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@GetMapping("/toDetail")
    public String toDetail( HttpServletRequest request, HttpServletResponse response) {
    	    	
        String SYSLOG_ID = ParamUtil.get(request, "SYSLOG_ID");
        Map<String,String> entry = syslogService.load(SYSLOG_ID);
        request.setAttribute("rst", entry);

        return view(webPath,"Syslog_Detail");
    }
	

}
