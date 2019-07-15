package com.centit.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.centit.commons.model.Consts;
import com.centit.commons.model.Result;
import com.centit.commons.util.StringUtil;

/** 
 * @ClassName: BaseController 
 * @Description: 基类
 * @author: zhu_xw
 * @date: 2018年4月18日 下午2:11:16  
 */
public class BaseController {
	
	/** The forbids. */
	private static String[] forbids = { "action","paramUrl" };	

	/**
	 * Gets the writer.
	 * 
	 * @param response the response
	 * 
	 * @return the writer
	 */
	public PrintWriter getWriter(HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			//异常统一处理
			//e.printStackTrace();
		}
		return writer;
	}
	
	public String view(String viewPath, String pageName) {
		if(Consts.FAILURE.equals(pageName)){
			return "common/errpage";
		}else{
			return viewPath + "/" + pageName;
		}
		
	}
	
	public static String getString(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		return StringUtil.filterString(value);
	}
	
	
	public  Map<String,String> listParamsMap(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<?> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = getString(request, name);
			if (!StringUtil.isEmpty(name)) {
				if (!checkParams(name)) {
					continue;
				}
				params.put(name, value);
			}
		}
		return params;
	}
	
	private static boolean checkParams(String name) {
		for (int i = 0, j = forbids.length; i < j; i++) {
			if (forbids[i].equals(name))
				return false;
		}
		return true;
	}
	
	/**
	 * 设置字段排序带默认值 Description :.add by zhuxw
	 * 
	 * @param request
	 *            the request
	 * @param params
	 *            the params
	 */
	public void setOrderField(HttpServletRequest request,
			Map<String, String> params, String defOrderField, String defOrdertype) {
		// 字段排序
		String orderField = getString(request, "orderField");
		String orderType = getString(request, "orderType");
		if (!StringUtil.isEmpty(orderField)) {
			params.put("orderField", tranDbCol(orderField) + " " + orderType);
			request.setAttribute("orderType", orderType);
			request.setAttribute("orderField", orderField);
		} else {
			params.put("orderField", tranDbCol(defOrderField) + " " + defOrdertype);
			request.setAttribute("orderType", defOrdertype);
			request.setAttribute("orderField", defOrderField);

		}
	}
	
	public static String  tranDbCol(String str) {
		 StringBuffer  sb=new StringBuffer();
		 for(int i = 0; i < str.length(); i++)
		 {
			 if(i==0)sb.append(str.charAt(i));
			 else
				 if(Character.isUpperCase(str.charAt(i))&&!"_".equals(str.charAt(i-1))) sb.append("_").append(str.charAt(i));
		     else sb.append(str.charAt(i));
		 } 
		return sb.toString() ;
	}
	
	
	/** 
	 * @Title: getInt 
	 * @Description: 整型值
	 * @author: zhu_xw
	 * @date: 2017年3月8日 上午9:34:35 
	 * @param request
	 * @param name
	 * @param defValue
	 * @return
	 * @return: int
	 */
	public int getInt(HttpServletRequest request, String name, int defValue) {
		String value = getString(request, name);
		if (!StringUtil.isEmpty(value)) {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
			}
		}
		return defValue;
	}
	
	/**
	 * Json result.
	 * 
	 * @return the string
	 */
	public String jsonResult()
	{
		return jsonResult(true,"");
	}
	
	/**
	 * 用法:String jsonResult = jsonResult(true,"保存成功");
	 * writer.write(jsonResult);
	 * 
	 * @param isSuccess the is success
	 * @param messages the messages
	 * 
	 * @return the string
	 */
	public String jsonResult(boolean isSuccess,String messages)
	{
		String jsonResult = JSONObject.toJSONString(new Result(isSuccess, null, messages));
		return jsonResult;
	}
	
}
