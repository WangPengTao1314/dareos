package com.centit.erp.sale.erpprmtprice.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.core.po.PageDesc;
import com.centit.erp.sale.prmtplan.model.PrmtplanModel;
import com.centit.sys.model.NoticeModel;


@Repository
public interface ErpprmtpriceMapper {
	
	List<Map<String,String>> pageQuery(Map<String,Object> map);
	
	void insert(List<Map<String,Object>> list);
	
	void update(List<Map<String,Object>> update);
	
	void updateState(Map<String,String> map);
	
	
	
	
	
	
	
	
	
	
	
}
