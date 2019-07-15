/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ChannServiceImpl.java
 */
package com.centit.base.channcapital.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.base.channbill.model.ChannLedgerChrgModel;
import com.centit.base.channcapital.mapper.ChannCapitalMapper;
import com.centit.base.channcapital.service.ChannCapitalService;
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
public class ChannCapitalServiceImpl  implements ChannCapitalService {
	
	@Autowired
	private ChannCapitalMapper mapper;
	

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
		Page<ChannLedgerChrgModel> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}
	
	/**
	 * 查询冻结分页
	 */
	public List<ChannLedgerChrgModel> pageFreezQuery(Map<String, Object> params) {
    	return mapper.pageFreezQuery(params);
	}

	public ChannLedgerChrgModel loadById(String channLedgerChrgId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("channLedgerChrgId", channLedgerChrgId);
		ChannLedgerChrgModel model = mapper.loadById(map);
		return model;
	}

	
}
