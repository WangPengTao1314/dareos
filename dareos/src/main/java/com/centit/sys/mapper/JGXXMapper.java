package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.sys.model.JGXXTree;
@Repository
public interface JGXXMapper {
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	List<Map<String, String>> pageQuery(Map<String, Object> map);
	Integer pageCount(Map<String, Object> map);
	List<Map<String, String>> query(Map<String, Object> map);
	void insert(Map<String, String> map);
	void updateById(Map<String, String> map);
	void delete(String str);
	void updateStateById(Map<String, String> map);
	void insertDelDate(Map<String, String> map);
	Map<String, String> loadById(String str);
	String loadJGZT(String str);
	List<JGXXTree> queryTree(String str);
	Integer queryBhForInt(Map<String, String> map);
	Integer querySjForInt(Map<String, String> map);
	void stopById(Map<String, String> map);
	void startById(Map<String, String> map);
	void stopBmById(Map<String, Object> map);
	void stopRyById(Map<String, Object> map);
	
}
