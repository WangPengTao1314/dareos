package com.centit.common.service;

import java.util.List;
import java.util.Map;

public interface BaseService {

	/**
	 * 根据SQL查询单条记录
	 * @param sql
	 * @return
	 */
	Map<String,String> selcom(String sql);
	
	/**
	 * 根据SQL查询结果集
	 * @param sql
	 * @return
	 */
	List<Map<String,String>> selcomList(String sql);
	
	/**
	 * 根据SQL新增数据
	 * @param sql
	 */
	void insert(String sql);
	
	/**
	 * 根据SQL修改数据
	 * @param sql
	 */
	void update(String sql);
	
	/**
	 * 根据SQL删除数据
	 * @param sql
	 */
	void delete(String sql);
	
	/**
	 * 根据表名和条件查询行数
	 * @param tab
	 * @param con
	 * @return
	 */
	int getRowNum(String tab,String con);
	
	/**
	 * 根据sequence编号获取序号
	 * @param no
	 * @return
	 */
	int getSeqByNo(String no);
	
	/**
	 * 根据sequence编号初始化
	 * @param no
	 */
	void clearSeq(String no);
	
	
	int getIntBySql(String sql);
}
