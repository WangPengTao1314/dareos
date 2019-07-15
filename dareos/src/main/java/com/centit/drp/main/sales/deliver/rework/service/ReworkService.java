package com.centit.drp.main.sales.deliver.rework.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.centit.core.po.PageDesc;
import com.centit.drp.main.sales.deliver.order.model.OrderModel;
import com.centit.drp.main.sales.deliver.order.model.SendDtlModel;
import com.centit.sys.model.UserBean;
/**
 * 售后管理模块
 * 返修单
 * @author wang_pt
 */
public interface ReworkService {
	/**
	 * 列表分页查询
	 * @param params
	 * @param pageDesc
	 */
	void pageQuery(Map<String, Object> params, PageDesc pageDesc);
	/**
	 * 查询单条记录
	 * @param params
	 * @return
	 */
	Map<String, Object> query(Map<String, Object> params);
	Map<String, Object> queryBack(Map<String, Object> params);
	/**
	 * 编辑
	 * @param model
	 * @param chldModelList
	 * @param request
	 */
	void edit(OrderModel model, List<SendDtlModel> chldModelList, HttpServletRequest request);
	/**
	 * 删除
	 * @param sale_order_id
	 * @param userBean
	 */
	void delete(String sale_order_id, UserBean userBean);
	
	void deleteDtl(String sale_order_dtl_id, UserBean userBean);
	/**
	 * 
	 * @param params
	 * @return
	 */
    Integer counts(Map<String, Object> params);
    /**
     * 更改状态
     * @param params
     */
    void submit(OrderModel model,UserBean userBean);
   
    
	
}
