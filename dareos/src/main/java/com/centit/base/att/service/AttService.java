package com.centit.base.att.service;

import java.util.List;
import java.util.Map;

import com.centit.sys.model.UserBean;

public interface AttService {

	/**
	 * 根据关联ID查找附件信息
	 * @param FROM_BILL_ID
	 * @return
	 */
	List<Map<String,String>> findList(String FROM_BILL_ID);
	
	/**
	 * 传入附件路径批量新增附件信息
	 * @param list
	 */
	void insert(List<String> list,UserBean userBean,String FROM_BILL_ID);
	
	/**
	 * 传入附件信息批量新增附件信息
	 * @param list
	 * @param userBean
	 * @param FROM_BILL_ID
	 */
	void insertInfo(List<Map<String,String>> list,UserBean userBean,String FROM_BILL_ID);
	
	/**
	 * 根据来源单删除所有附件
	 * @param FROM_BILL_ID
	 */
	void delByFromId(String FROM_BILL_ID);
	
	/**
	 * 根据附件ID删除附件
	 * @param attId
	 */
	void delByAttId(String ATT_ID);
	
	/**
	 * 
	 * @param FROM_BILL_ID
	 * @return
	 */
	String findStr(String FROM_BILL_ID);

	/**
	 * 根据文件名获取重复数据
	 * @param names
	 * @return
	 */
	List<String> checkAttrName(String names);
}
