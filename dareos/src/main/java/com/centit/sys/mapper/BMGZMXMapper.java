package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.sys.model.BmgzMxModel;
@Repository
public interface BMGZMXMapper {
	List<BmgzMxModel> pageQuery(Map<String, String> map);
	Integer pageCount(Map<String, String> map);
	List<BmgzMxModel> query(Map<String, String> map);
	void insert(List<Map<String, String>> listmap);
	void updateById(List<Map<String, String>> listmap);
	void deleteByFkId(String str);
	void deleteByIds(String str);
	void insertBMGZMX(Map<String, String> map);
	List<Map<String, String>> loadById(Map<String, String> map);
	List<BmgzMxModel> loadByIds(String str);
	Integer getZcd(String str);
}
