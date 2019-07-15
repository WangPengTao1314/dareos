package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.sys.model.ZTWHModel;


@Repository
public interface XTSQMapper {
	
	void insert(Map<String,String> map);
	
	void delete(String XTYHID);
	
	List<Map<String,String>> loadById(String XTYHID);
	
	
}
