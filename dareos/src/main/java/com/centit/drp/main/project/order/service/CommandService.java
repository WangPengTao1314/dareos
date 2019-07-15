package com.centit.drp.main.project.order.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.centit.core.po.PageDesc;
import com.centit.drp.main.project.order.model.CommandModel;
import com.centit.sys.model.UserBean;

public interface CommandService {
	/**
	 * 查询指令单条记录
	 * @param params
	 * @return
	 */
	Map<String, Object> query(Map<String, Object> params);
	/**
	 * 编辑指令
	 * @param model
	 * @param request
	 */
	void edit(CommandModel model, HttpServletRequest request);
	/**
	 * 显示指令到列表中
	 * @param params
	 * @param pageDesc
	 */
	void pageQuery(Map<String, Object> params, PageDesc pageDesc);

	/**
	 * 删除指令
	 * @param project_directive_id
	 * @param userBean
	 */
	void delete(String project_directive_id, UserBean userBean);
	/**
	 * 
	 * @param userBean
	 * @param params
	 */
	void updateSate(UserBean userBean,Map<String, Object> params);
	/**
	 * 列表显示
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getFileInfo(Map<String, Object> params);

}
