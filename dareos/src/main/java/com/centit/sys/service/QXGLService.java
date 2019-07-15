package com.centit.sys.service;

import javax.servlet.http.HttpServletRequest;

import com.centit.sys.model.UserBean;

public interface QXGLService {

	String getQXTab(String keyName, String keyID, String adminXTYHID, String loginJGXXID,HttpServletRequest request) throws Exception;
	
	String returnQXJBRadioList()  throws Exception;
	
	String returnQXJBRadioList(String MKQXJB)  throws Exception;
	
	String[] getMKAry(String XTMC, int length, String condition) throws Exception;
	
	String getStrMK(String tableName, String condition)  throws Exception;
	
	void insertXTYHQXSelf(String XTYHID, String MKSM,String [] ins_name2,String [] ins_value2,String[] selXTMKID,int FINALQXMKCodeLength,HttpServletRequest request) throws Exception;
	
	void insertxtjsQX(String XTJSID, String MKSM,String [] ins_name2,String [] ins_value2,String[] selXTMKID,int FINALQXMKCodeLength,HttpServletRequest request) throws Exception;
	
	void insertgzzQX(String GZZXXID, String MKSM,String [] ins_name2,String [] ins_value2,String[] selXTMKID,int FINALQXMKCodeLength,HttpServletRequest request) throws Exception;
	
	boolean getRowNum(String tab,String con);
	
	String returnQXJBRadioTree()  throws Exception;
	
	String returnQXJBRadioList(String MKQXJB,UserBean aUserBean) throws Exception;
	
	boolean checkDisabled(String xtyhid)throws Exception;
	
	String getStrMK2(String tableName, String condition);
}
