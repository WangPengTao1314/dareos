package com.centit.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.centit.report.entity.OrderBean;
import com.centit.report.mapper.ReportMapper;
import com.centit.report.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
    private ReportMapper reportDao;
	
	public List<OrderBean> qryOrder()
	{
		return reportDao.qryOrder();
	}

	@Override
	public List<Map<String, Object>> getOrderDtl(Map<String, String> queryParams) {
		
		return reportDao.getOrderDtl(queryParams);
	}
	
	
}
