/**
  *@module 系统模块   
  *@func 系统模块   
  *@version 1.1
  *@author zhuxw      
*/
package com.centit.commons.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.security.auth.login.LoginException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.centit.commons.model.Consts;
import com.centit.commons.model.ParamCover;
import com.centit.commons.model.Result;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;

// TODO: Auto-generated Javadoc
/**
 * The Class SessionFilter.
 * 
 * @module 系统模块
 * @func 系统基础类
 * @version 1.1
 * @author zhuxw
 */
public class SessionFilter implements Filter, Consts {

	/** The forward to. */
	private String forwardTo;
	
	/** The checked attrs. */
	private String[] checkedAttrs;
	
	/** The opened url. */
	private String[] openedURL;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		forwardTo = filterConfig.getInitParameter("forwardTo");
		String sessAttrs = filterConfig
				.getInitParameter("checkedSessionAttribute");
		checkedAttrs = StringUtil.toArr(sessAttrs);
		String urls = filterConfig.getInitParameter("openedURL");
		openedURL = StringUtil.toArr(urls);
	}

	/**
	 * 查看session是否過期， 如果是則導向指定頁面（web上配置的特定頁面除外）.
	 * 
	 * @param request the request
	 * @param response the response
	 * @param chain the chain
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (checkSessionValidate(request, response)) {
			chain.doFilter(request, response);
		}
	}

	/**
	 * 查看Session是否过期.
	 * 
	 * @param request the request
	 * @param response the response
	 * 
	 * @return true, if check session validate
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 * @throws LoginException 
	 */
	private boolean checkSessionValidate(ServletRequest request,
			ServletResponse response) throws  IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpSession session = httpRequest.getSession(false); 
			boolean flag = true;
			String servletPath = httpRequest.getServletPath().replaceAll("//", "/");
			// 配置拦截的请求
			String[] notFilter =new String[] { "/sys","/base"};
			System.out.println(servletPath);
			// 检查URL是否在监听范围内
			for (String s : notFilter) {
				if(servletPath.indexOf(s)!=-1){
					if(session == null){
						flag = false;
					} else {
							UserBean userBean = (UserBean) session.getAttribute("UserBean");
							if (null == userBean&&StringUtil.indexOf(openedURL, servletPath, true) < 0) {
								flag = false;
							}
					}
				}
			}
//			if (StringUtil.indexOf(openedURL, servletPath, true) < 0&&StringUtil.indexOf(notFilter, servletPath, true)< 0) {
//				if (session == null) {
//					flag = false;
//				} else {
//					UserBean userBean = (UserBean) session.getAttribute("UserBean");
//					if (null == userBean) {
//						flag = false;
//					}
//				}
//			}
			System.out.println(flag);
			if(!flag){
				if (httpRequest.getHeader("x-requested-with") != null  && httpRequest.getHeader("x-requested-with")  .equalsIgnoreCase("XMLHttpRequest")) { 
					response.setCharacterEncoding("utf-8");
					PrintWriter writer = response.getWriter();
					String jsonResult = new Gson().toJson(new Result(false, null, "用户已失效，请重新登录!"));
					if (null != writer) {
						writer.write(jsonResult);
						writer.close();
					}
				}else{ 
					request.setAttribute("errorMsg", "用户已失效，请重新登录!");
					((HttpServletRequest)request).getRequestDispatcher("/pages/index/loginError.jsp").forward(request, response);
//					throw new  RuntimeException("用户已失效，请重新登录!");
				} 
				return false;
			}
			
			// 日志監聽
			log(httpRequest);
			// 參數傳遞
			paramCover(httpRequest);
		}
		return true;
	}

	/**
	 * 系统日志,显示Request請求中的所有參數.
	 * 
	 * @param request the request
	 */
	protected void log(HttpServletRequest request) {
		if (DEBUG) {
			System.out.println("[系統日志] " + ParamUtil.favorit(request));
		}
	}

	/**
	 * 參數轉換及傳遞.
	 * 
	 * @param request the request
	 */
	protected void paramCover(HttpServletRequest request) {
		request.setAttribute(PARAM_COVER, new ParamCover(request));
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}
}
