package com.centit.base.channcapital.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.base.channbill.model.ChannLedgerChrgModel;

@Repository
public interface ChannCapitalMapper {
	
	List<ChannLedgerChrgModel> pageQuery(Map<String,Object> map);
	
	
	List<ChannLedgerChrgModel> pageFreezQuery(Map<String,Object> map);
	
	/**
	 * 根据主键id查询信用申请
	 * @param param
	 * @return
	 */
	ChannLedgerChrgModel loadById(Map<String,String> map);
	
}
