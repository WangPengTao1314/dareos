package com.centit.base.employee.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.base.employee.model.EmployeeModelChld;
@Repository
public interface EmployeeMapper {
	List<Map<String,String>> pageQuery(Map<String,Object> map);
	
	void insert(Map<String, String> params);
	
	void insertXTYH(Map<String, String> params);
	
	void insertXTYHZTDZ(Map<String, String> params);
	
	void updateById(Map<String, String> params);
	
	void updateXTYH(Map<String, String> params);
	
	void updatePassword(Map<String, String> params);
	
	Map<String,String> loadById(@Param("RYXXID")String RYXXID);
	
	void updateChldById(List<Map<String,String>> list);
	
	void insertChld(List<Map<String,String>> list);
	
	List<EmployeeModelChld> queryChldByFkId(Map<String,String> param);
	
	List<EmployeeModelChld> loadChldByIds(Map<String,String> param);
	
	void deleteChldByIds(Map<String, String> params);
	
	int getExits(String RYBH);
	
	String getTERM_DECT_CTR_FLAG(String CHANN_ID);
	
	int getRHMExits(String YHM);
	
	
}
