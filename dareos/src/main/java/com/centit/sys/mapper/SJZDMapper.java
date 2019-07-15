package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface SJZDMapper {
	
	/**
	 * 
	 * @Title: pageQuery 
	 * @Description: 分页查询
	 * @author: liu_yg
	 * @date: 2019年2月26日 下午3:05:02 
	 * @param pageQureyMap
	 * @return
	 * @return: List<Map<String,String>>
	 */
	public List<Map<String,String>>  pageQuery(Map<String, Object> pageQureyMap);
	
	Map<String,String> loadById(String sjzdId);
	
	void insertSJZD(Map<String,String> map);
	
	void insertSJZDMX(Map<String,String> map);
	
	void deletemx(String sjzdId);
	
	void delete(String sjzdId);
	
	void insert(Map<String,String> map);
	
	void updateById(Map<String,String> map);
	
	void updateState(Map <String, String> params);
	
	int getIdCount(String SJZDBH);
	
	List<Map<String,String>> checkSJZDMXBySJXZ(Map<String,String> params);
	
	List<Map<String,String>> checkSJZDMXById(Map<String,String> params);
	
	void insertSJZDMXByService(Map<String,String> params);
	
	void updateSJZDMXByService(Map<String,String> params);
	
	
	
	
	
	
}
