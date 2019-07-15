package com.centit.drp.main.order.progress.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.commons.util.InterUtil;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.order.progress.mapper.ProgressMapper;
import com.centit.drp.main.order.progress.model.ProgressDetModel;
import com.centit.drp.main.order.progress.model.ProgressModel;
import com.centit.drp.main.order.progress.service.ProgressService;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ProgressServiceImpl implements ProgressService {
	
	@Autowired
	private ProgressMapper progressMapper;
	
	/**
	 * 列表分页查询
	 */
	@Override
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc) {
		Page<Object> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
		progressMapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}
	@Override
	public Map<String, Object> query(Map<String, Object> params) {
		Map<String, Object>map=progressMapper.toQuery(params);
		List<Map<String, String>>list=InterUtil.findAttInfo(map.get("ORDER_DEGREE_ID").toString());
		if(list.size()!=0) {
			map.put("ATT_PATH",list.get(0).get("ATT_PATH"));
		}
		map.put("entrySun", progressMapper.toQuerySun(map));
		return map;
	}
	
	@Override
	public void edit(ProgressModel model, List<ProgressDetModel> chldModelList, HttpServletRequest request) {
		// 获取用户信息
		UserBean userBean = ParamUtil.getUserBean(request);
		//编辑子表明细
		if(chldModelList!=null) {
			for(ProgressDetModel models:chldModelList) {
				progressMapper.editSun(models);
			}
		}
		//编辑主表
		//if(model.getOrder_num()==0) {
			//model.setState("已完成");
		//}
		progressMapper.edit(model);
		//编辑附件
		String filePath=model.getUploadExcel();
		if(!StringUtil.isEmpty(filePath)) {
			List<String>list=Arrays.asList(filePath.split(","));
			InterUtil.insertAttPath(list, userBean, model.getOrder_degree_id());
		}
		
		
	}
	
	@Override
	public void confirm(ProgressModel model, HttpServletRequest request) {
		// 获取用户信息
		UserBean userBean = ParamUtil.getUserBean(request);
		if(model!=null) {
			//编辑主表
			model.setState("已确认");
			progressMapper.edit(model);
			//编辑附件
			String filePath=model.getUploadExcel();
			if(!StringUtil.isEmpty(filePath)) {
				List<String>list=Arrays.asList(filePath.split(","));
				InterUtil.insertAttPath(list, userBean, model.getOrder_degree_id());
			}
		}
		
	}

}
