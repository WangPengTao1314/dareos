package com.centit.base.areachrg.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.base.area.model.AreaTree;
import com.centit.base.areachrg.model.AreaChrgModel;


@Repository
public interface AreaChrgMapper {
	List<Map<String,String>> pageQuery(Map<String, Object> map);
	Integer pageCount(Map<String, String> map);
	List<AreaTree> queryTree(String str);
	Map<String,String> loadById(String str);
	List<AreaChrgModel> query(Map<String, String> map);
	List<AreaChrgModel> loadByIds(@Param("IDs")String str);
	void insert(List<Map<String, String>> list);
	void updateById(List<Map<String, String>> list);
	void deleteByIds(String str);
	void updateDELByIds(String str);
	void  updateDELByAreaId(String areaID);
	Integer queryBhForInt(Map<String , String> map);
	String loadQUXXZT(String str);
	List<AreaChrgModel> findRepeat(Map<String, String> map);
	void delete(String str);
	void startById(Map<String, String> map);
}
