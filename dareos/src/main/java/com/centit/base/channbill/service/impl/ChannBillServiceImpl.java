/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ChannServiceImpl.java
 */
package com.centit.base.channbill.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.base.channbill.mapper.ChannBillMapper;
import com.centit.base.channbill.service.ChannBillService;
import com.centit.common.po.BookkeepingModel;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 充值信息
 * @author liu_yg
 *
 */
@Service
public class ChannBillServiceImpl  implements ChannBillService {
	
	@Autowired
	private ChannBillMapper mapper;
	

	/**
     * 查询并分页.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
	@Override
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc) {
		Page<BookkeepingModel> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}
	
	/**
	 * 查询冻结分页
	 */
	public void pageFreezQuery(Map<String, Object> params, PageDesc pageDesc) {
		Page<BookkeepingModel> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageFreezQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	public BookkeepingModel loadById(String bookkeepingId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("bookkeepingId", bookkeepingId);
		BookkeepingModel model = mapper.loadById(map);
		return model;
	}

	
}
