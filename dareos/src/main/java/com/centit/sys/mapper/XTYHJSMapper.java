package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.sys.model.XTYHJSBean;


@Repository
public interface XTYHJSMapper {
	
	List<Map<String,String>> queryFORLOGIN(String XTYHID);
	
	List<XTYHJSBean> pageQuery(Map <String, String> params);
	
	List <XTYHJSBean> getYHBHList(String XTJSID);
	
	List <XTYHJSBean> loadByIds(@Param("xtjsids")String xtjsids);
	
	void updateChild(List<Map<String,String>> list);
	
	void insertChild(List<Map<String,String>> list);
	
	void insertXTYHJS(Map<String,String> map);
	
	void deleteChildByIds(@Param("xtyhjsids")String xtyhjsids);
	
}
