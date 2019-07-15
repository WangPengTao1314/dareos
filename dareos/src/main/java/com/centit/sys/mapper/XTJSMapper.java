package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.sys.model.XTJSBean;


@Repository
public interface XTJSMapper {
	
	List<Map<String,String>> pageQuery(Map<String,Object> map);
	
	void insert(Map<String,String> map);
	
	void insertChild(Map<String,String> map);
	
	void updateById(Map<String,String> map);
	
	void delete(String xtjsID);
	
	void deleteJSMX(String xtjsID);
	
	Map<String,String> loadById(String XTJSID);
	
	Map<String,String> query(Map<String,String> map);
	
	List <XTJSBean> getBHList();
	
	void insertJS(Map<String,String> map);
	
	void insertJSMX(Map<String,String> map);
	
	void updateJSStatus(Map<String,String> map);
	
	
}
