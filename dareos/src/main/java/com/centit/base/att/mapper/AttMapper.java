package com.centit.base.att.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AttMapper {
	/**
	 * 根据关联ID查找附件信息
	 * @param FROM_BILL_ID
	 * @return
	 */
	List<Map<String,String>> findList(String FROM_BILL_ID);
	
	/**
	 * 批量新增附件信息
	 * @param list
	 */
	void insert(List<Map<String,String>> list);
	
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
	 * 根据附件ID返回字符串
	 * @param FROM_BILL_ID
	 * @return
	 */
	String findStr(String FROM_BILL_ID);
	
	/**
	 * 根据文件名校验是否重复
	 * @param names
	 * @return
	 */
	public List<String> checkAttrName(@Param("names") String names);
}
