package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.sys.model.MenuInfo;


@Repository
public interface MenuInfoMapper {
	
	Map<String, Object> query(Map<String, Object> params);
	
	void delete(Map<String,Object> map);
	
	void updateById(MenuInfo model);
	
	int insert(MenuInfo model);
	
	List<MenuInfo> pageQuery(Map<String, Object> pageQureyMap);
	
	List<MenuInfo> findByUserIdDrp(Map<String,String> map);
	
	List<MenuInfo> findByUserIdErp(Map<String,String> map);
	
	List<MenuInfo> findAll();
	 
}
