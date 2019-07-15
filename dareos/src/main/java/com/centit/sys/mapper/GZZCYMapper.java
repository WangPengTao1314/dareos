package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.sys.model.GZZCYBean;


@Repository
public interface GZZCYMapper {
	
	List<Map<String,String>> queryFORLOGIN(String str);
	
	List<Map<String,String>> pageQuery(Map<String,String> map);
	Integer pageCount(Map<String,String> map);
	List<Map<String,String>> query(Map<String,String> map);
	List<GZZCYBean> loadById(Map<String,String> map);
	void delete(String str);
	void deleteById(String str);
	void insertMX(Map<String,String> map);
	void insert(List<Map<String,String>> listmap);
	void updateById(List<Map<String,String>> listmap);
	List<GZZCYBean>loadByIds(String str);
	List<GZZCYBean> checkYHBH(Map<String,String> map);
	
}
