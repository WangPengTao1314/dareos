package com.centit.core.po;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ResponseBean.java
 * @Description: ajax请求统一返回对象，所有请求均以该对象封装并返回
 * 		返回状态码：
 * 		1 	成功 
 * 		0	主动捕获的业务异常，如保存时数据重复等异常
 * 		500	程序异常，没有显式捕获的异常，由异常拦截器处理
 * 		401	未登录异常，由ShiroUserFilter处理
 * 		403	资源访问权限异常，由Shiro处理
 * @author: zhu_hj
 * @date: 2018年5月2日 下午3:02:47
 */
public class ResponseBean implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final Integer MSG_CODE_SUCCESS = 1;
	
	public static final Integer MSG_CODE_SYSERROR = 500;
	
	public static final Integer MSG_CODE_EXCEPTION = 0;
	
	public static final Integer MSG_CODE_NOTLOGIN = 401;
	
	public static final Integer MSG_CODE_UNAUTHORIZED = 403;
	
	private Integer code;
	
	private String message;
	
	private Object data;
	
	public ResponseBean(){}
	
	public ResponseBean(Integer code, String message){
		this.code=code;
		this.message=message;
	}
	
	public ResponseBean(Integer code, String message, Object data){
		this.code=code;
		this.message=message;
		this.data=data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public static ResponseBean Success(String message){
		return new ResponseBean(MSG_CODE_SUCCESS, message);
	}
	
	public static ResponseBean Success(){
		return new ResponseBean(MSG_CODE_SUCCESS, "");
	}
	
	public static ResponseBean Success(Object data){
		return new ResponseBean(MSG_CODE_SUCCESS, "", data);
	}
	
	public static ResponseBean Success(String message,Object data){
		return new ResponseBean(MSG_CODE_SUCCESS, message, data);
	}
	
	public static ResponseBean sysError(String message){
		return new ResponseBean(MSG_CODE_SYSERROR, StringUtils.isEmpty(message)?"程序异常":message);
	}
	
	public static ResponseBean Exception(String message){
		return new ResponseBean(MSG_CODE_EXCEPTION, message);
	}
	
	public static ResponseBean NotLogin(String message){
		return new ResponseBean(MSG_CODE_NOTLOGIN, StringUtils.isEmpty(message)?"没有登录":message);
	}
	
	public static ResponseBean AccessForbidden(String message){
		return new ResponseBean(MSG_CODE_UNAUTHORIZED, StringUtils.isEmpty(message)?"没有资源访问权限":message);
	}
	
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
