package com.centit.report.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.report.entity.OrderBean;

@Repository
public interface ReportMapper {
	
	public List<OrderBean> qryOrder();

	public List<Map<String, Object>> getOrderDtl(Map<String, String> queryParams);

}
