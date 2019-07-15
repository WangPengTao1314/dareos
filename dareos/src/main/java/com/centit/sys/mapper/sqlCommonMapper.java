package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.commons.inter.dyncolumn.model.DynColumnModel;
@Repository
public interface sqlCommonMapper {
	List<Map<String, String>> select001_T_SYS_BMGZ(Map<String, String> map);
	String select002_T_SYS_BMGZ(@Param("sequencemc")String str);
	List<DynColumnModel> queryDynColumn(Map<String, String> map);
	void delCustomizedColumn(Map<String, String> map);
	void insertCustomizedColumn(DynColumnModel dynColumnModel);
	void insert(String str);
	void update(String str);
	Integer queryForInt(@Param("queryForIntSql")String str);
	List<Map<String, String>> query(@Param("querySql")String str);
	Map<String, String> queryForMap(@Param("querySql") String str);
}
