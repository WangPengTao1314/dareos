package com.centit.drp.main.order.progress.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.centit.core.po.PageDesc;
import com.centit.drp.main.order.progress.model.ProgressDetModel;
import com.centit.drp.main.order.progress.model.ProgressModel;

public interface ProgressService {

	void pageQuery(Map<String, Object> params, PageDesc pageDesc);

	Map<String, Object> query(Map<String, Object> params);

	void edit(ProgressModel model, List<ProgressDetModel> chldModelList, HttpServletRequest request);

	void confirm(ProgressModel model, HttpServletRequest request);



}
