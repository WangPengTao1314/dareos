/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ChannServiceImpl.java
 */
package com.centit.base.channamount.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.base.channamount.mapper.ChannAmountMapper;
import com.centit.base.channamount.model.MonthAcctNoteModel;
import com.centit.base.channamount.service.ChannAmountService;
import com.centit.common.service.BookkeepingService;
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
public class ChannAmountServiceImpl  implements ChannAmountService {
	
	@Autowired
	private ChannAmountMapper mapper;
	
	@Autowired
	private BookkeepingService service;

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
		Page<MonthAcctNoteModel> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	public MonthAcctNoteModel loadById(String monthAcctNoteId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("monthAcctNoteId", monthAcctNoteId);
		MonthAcctNoteModel model = mapper.loadById(map);
		return model;
	}

	
}
