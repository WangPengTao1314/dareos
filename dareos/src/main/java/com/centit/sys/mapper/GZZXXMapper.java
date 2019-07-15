package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface GZZXXMapper {
	
	List<Map<String,String>> pageQuery(Map<String,String> map);
	Integer pageCount(Map<String,String> map);
	List<Map<String,String>> query(Map<String,String> map);
	String getXXID();
	void insert(Map<String,String> map);
	void updateById(Map<String,String> map);
	void delete(String str);
	void deletemx(String str);
	void insertGZZ(Map<String,String> map);
	void insertGZZMX(Map<String,String> map);
	Map<String,String> loadById(Map<String,String> map);
	Integer getExits(String str);
}
