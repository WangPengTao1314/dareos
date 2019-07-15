package com.centit.base.pdtmenu.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.base.pdtmenu.model.PdtmenuTree;

@Repository
public interface PdtmenuMapper {
	
	List<Map<String, String>> pageQuery(Map<String, Object> map);
	Integer pageCount(Map<String, String> map);
	void updateStateById(Map<String, String> map);
	void delete(Map<String, String> map);
	void insert(Map<String, Object> map);
	void updateById(Map<String, Object> map);
	Map<String, String> loadById(String str);
	List<PdtmenuTree>queryTree(String str);
	List<String> getBrand(Map<String, String> map);
	Integer getCountPRD_NO(String str);
	
}
