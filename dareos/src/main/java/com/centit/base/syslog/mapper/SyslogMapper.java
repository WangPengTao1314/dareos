package com.centit.base.syslog.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.base.chann.model.DeliveraddrModelChld;


@Repository
public interface SyslogMapper {
	
	List<Map<String,String>> pageQuery(Map<String,String> map);
	
	Map<String,String> loadById(String SYSLOG_ID);
	
	/**
	 * 基础数据删除时 ，把删除的数据记录到表里保存
	 * @param map
	 */
	void insertDataRecycle(Map<String,String> map);
	
	/**
	 * 新增日志
	 */
	void insertActLog(Map<String,Object> map);
	
	
	
}
