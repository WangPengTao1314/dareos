package com.centit.sys.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.sys.mapper.MenuInfoMapper;
import com.centit.sys.model.MenuInfo;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.MenuService;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * The Class MenuService.
 * 
 * @module 系统模块
 * @fuc 系统模块
 * @version 1.1
 * @author 朱晓文
 */
@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuInfoMapper mapper;

    /**
     * 查询并分页.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    public void pageQuery(Map<String, Object> map, PageDesc pageDesc) {
    	Page<Map<String,String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(map);
		LogicUtil.transPageHelper(pageDesc, p);
    }

	
	
	@Override
	public void modify(MenuInfo model) {
			 mapper.updateById(model);
	}
	@Override
	public void insert(MenuInfo model) {
		 mapper.insert(model);
	}
	

	@Override
	public void delete(String paramsID) {
		Map <String, Object> params = new HashMap <String, Object>();
		params.put("menuId", paramsID);
		mapper.delete(params);
	}

	@Override
	public Map<String, Object> load(Map<String, Object> params) {
		return mapper.query(params);
	}

	

}
