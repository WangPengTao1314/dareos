package com.centit.drp.main.project.tendering.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.centit.core.po.PageDesc;
import com.centit.drp.main.project.tendering.model.ProjectInfoModel;
import com.centit.sys.model.UserBean;


/**
  * 招投标业务层实现类
 * @author wang_pt
 *
 */
public interface ProjectInfoService {
	 
	 /**
	  * 列表分页查询
	  * @param params
	  * @param pageDesc
	  */
	void pageQuery(Map<String, Object> params, PageDesc pageDesc);
	/**
	 * 根据id 查询单条记录
	 * @param params
	 * @return
	 */
	Map<String, Object>  query(Map<String, Object> params);
	/**
	 * 编辑信息
	 * @param model
	 * @param request
	 */
	void modify(ProjectInfoModel model, HttpServletRequest request);
	/**
	 * 删除项目记录
	 * @param tendering_id
	 * @param userBean
	 */
	void delete(String tendering_id, UserBean userBean);
	/**
	 * 删除附件
	 * @param att_id
	 * @param userBean
	 */
	void deleteFile(String att_id, UserBean userBean);
	

}
