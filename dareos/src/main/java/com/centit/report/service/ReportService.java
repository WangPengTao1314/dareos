package com.centit.report.service;

import java.util.List;
import java.util.Map;

import com.centit.report.entity.OrderBean;

public interface ReportService {


	public List<OrderBean> qryOrder();

	public List<Map<String, Object>> getOrderDtl(Map<String, String> queryParams);

	
}
