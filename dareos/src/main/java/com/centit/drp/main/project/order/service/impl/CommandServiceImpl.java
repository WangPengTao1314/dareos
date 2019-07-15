package com.centit.drp.main.project.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.commons.util.InterUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.project.order.mapper.CommandMapper;
import com.centit.drp.main.project.order.model.CommandModel;
import com.centit.drp.main.project.order.service.CommandService;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class CommandServiceImpl implements CommandService {
	
	@Autowired
	private CommandMapper commandMapper;
	
	@Override
	public Map<String, Object> query(Map<String, Object> params) {
		
		return commandMapper.toQuery(params);
	}

	@Transactional
	public void edit(CommandModel model, HttpServletRequest request) {
		commandMapper.insert(model);
		commandMapper.modify(model);
		
	}
	
	@Override
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc) {
		Page<Object> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
		List<Map<String, Object>>listPath=commandMapper.pageQuery(params);
		if(listPath!=null){
			for(Map<String, Object>mapPath:listPath) {
				String fileName=mapPath.get("ATT_PATH").toString().substring(1);
				mapPath.put("FILENAME",fileName.substring(fileName.indexOf("/")+1));
			}
		}
		LogicUtil.transPageHelper(pageDesc, p);
		
	}
	@Override
	public List<Map<String, Object>> getFileInfo(Map<String, Object> params) {
		List<Map<String, Object>> listPath=commandMapper.pageQuery(params);
		if(listPath!=null){
			for(Map<String, Object>mapPath:listPath) {
				String fileName=mapPath.get("ATT_PATH").toString().substring(1);
				mapPath.put("FILENAME",fileName.substring(fileName.indexOf("/")+1));
			}
		}
		return listPath;
	}
	
	@Override
	public void delete(String project_directive_id, UserBean userBean) {
		InterUtil.delByAttId(project_directive_id);
		//updateByID
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("project_directive_id",project_directive_id);
		commandMapper.updateByID(params);
	}

	@Transactional
	public void updateSate(UserBean userBean, Map<String, Object> params) {
		params.put("updator", userBean.getXTYHID());
		params.put("upd_name", userBean.getXM());
		commandMapper.updateStateByID(params);
		
	}

	

	 

	
}
