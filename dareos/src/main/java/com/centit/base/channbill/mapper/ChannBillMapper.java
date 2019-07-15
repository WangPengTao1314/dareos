package com.centit.base.channbill.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.common.po.BookkeepingModel;

@Repository
public interface ChannBillMapper {
	
	List<BookkeepingModel> pageQuery(Map<String,Object> map);
	
	
	List<BookkeepingModel> pageFreezQuery(Map<String,Object> map);
	
	/**
	 * 根据主键id查询信用申请
	 * @param param
	 * @return
	 */
	BookkeepingModel loadById(Map<String,String> map);
	
}
