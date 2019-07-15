package com.centit.base.brand.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface BrandMapper {
	
	List<Map<String,Object>> pageQuery(Map<String,Object> map);
	
	/**
	 * 根据主键id查询品牌信息
	 * @param param
	 * @return
	 */
	Map<String,Object> loadById(String param);
	
	/**
	 * 新增品牌信息
	 */
	void insert(Map<String,Object> map);
	
	/**
	 * 根据id修改信息
	 */
	void updateById(Map<String, Object> params);
	
	/**
	 * （软删除）
	 */
	void delete(Map<String, Object> params);
}
