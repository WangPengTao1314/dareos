package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.sys.model.BmgzModel;

@Repository
public interface BMGZMapper {
	List<Map<String, String>> pageQuery(Map<String, Object> map);
	Integer pageCount(Map<String, String> map);
	List<Map<String, String>> query(Map<String, String> map);
	void insert(Map<String, String> map);
	void updateById(Map<String, String> map);
	void delete(String str);
	void deletemx(String str);
	void insertBMGZ(Map<String, String> map);
	void insertBMGZMX(Map<String, String> map);
	Map<String, String> loadById(String str);
	Integer getCountByBH(String str);
	Integer getCountFromChildTable(String str);
	List<Map> getSeq();
	Map<String, String> getChildId(String str);
	List<BmgzModel> getAll();
	String getBMBH();
	List<Map<String, String>> queryMaxBillNo(Map<String, Object> map);
	
}
