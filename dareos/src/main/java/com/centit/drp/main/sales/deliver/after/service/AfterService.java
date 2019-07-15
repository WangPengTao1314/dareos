package com.centit.drp.main.sales.deliver.after.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.centit.core.po.PageDesc;
import com.centit.drp.main.sales.deliver.after.model.AfterModel;
import com.centit.sys.model.UserBean;

public interface AfterService {

	void pageQuery(Map<String, Object> params, PageDesc pageDesc);

	Map<String, Object> query(Map<String, Object> params);

	void edit(AfterModel model, HttpServletRequest request);

	void delete(String send_order_id, UserBean userBean);

	void editHandle(AfterModel model, HttpServletRequest request);

	void deleteFile(String att_id, UserBean userBean);
	
}
