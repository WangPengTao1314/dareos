package com.centit.base.area.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.base.area.model.AreaTree;


@Repository
public interface AreaMapper {
	
	List<Map<String,String>> pageQuery(Map<String,Object> map);
	
	List <AreaTree> queryTree();
	
	Map<String,String> loadById(String areaId);
	
	int queryBhForInt(Map<String,String> map);
	
	String loadAreaState(String AREA_ID_P);
	
	void deleteareaflat(@Param("CHRGIDS") String CHRGIDS);
	
	void insertareaflat(List<Map<String,String>> list);
	
	void insert(Map<String,String> map);
	
	void updateById(Map<String,String> map);
	
	void updateStateById(Map<String,String> map);
	
	void deleteAreaflatByAreaID(Map<String,String> map);
}
