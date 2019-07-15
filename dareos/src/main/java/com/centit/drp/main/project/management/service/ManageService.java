package com.centit.drp.main.project.management.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.centit.core.po.PageDesc;
import com.centit.drp.main.project.management.model.ManageModel;
import com.centit.drp.main.project.management.model.PayableModel;
import com.centit.drp.main.project.management.model.ProOrderModel;
import com.centit.sys.model.UserBean;
/**
 * 项目单维护业务层实体类
 * @author wang_pt
 *
 */
public interface ManageService {
	
	void delete(String project_id,UserBean userBean);
	
	/**
	 * 项目工程单节点维护
	 * @param params
	 * @param pageDesc
	 */
	void pageQuery(Map<String, Object> params, PageDesc pageDesc);
	void modify(ManageModel model, HttpServletRequest request);

	
	/**
	 * 查询项目单单条记录
	 * @param params
	 * @return
	 */
	Map<String, Object> query(Map<String, Object> params);
	Map<String, Object> querySun(Map<String, Object> params);

	/**
	 * 项目节点维护
	 * @param model
	 * @param modelList
	 * @param userBean
	 * @param project_id
	 */
	void txChldEdit(ProOrderModel model, List<PayableModel> modelList, UserBean userBean, String project_id);
	void sunDelete(String pROJECT_PAYABLE_ID);

	/**
	 * 项目上传指令
	 * @param model
	 * @param userBean
	 */
	void saveOrderFile(ProOrderModel model, UserBean userBean);
	void deleteFile(String att_id);
	List<Map<String,Object>> toQueryFile(Map<String, Object> params);
	
	

}
