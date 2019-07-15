package com.centit.drp.main.sales.deliver.order.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.centit.core.po.PageDesc;
import com.centit.drp.main.sales.deliver.order.model.OrderModel;
import com.centit.drp.main.sales.deliver.order.model.SendDtlModel;
import com.centit.erp.sale.store.model.ErpStoreOutDtl;
import com.centit.sys.model.UserBean;
/**
 * 发货管理模块
 * 发货指令维护Service实现类
 * @author wang_pt
 *
 */
public interface OrderService {
	
	/**
	 * 查询单条记录
	 * @param params
	 * @return
	 */
	Map<String, Object> query(Map<String, Object> params);
	/**
	 * 获取子表单条记录
	 * @param params
	 * @return
	 */
	Map<String, Object> querySun(Map<String, Object> params);
	/**
	 * 编辑
	 * @param modelList
	 * @param request 
	 */
	Map<String, Object> modify(OrderModel model,List<SendDtlModel> modelList, HttpServletRequest request,String BILL_TYPE);
	
	/**
	 * 删除
	 * @param sale_order_id
	 * @param userBean
	 */
	void delete(String sale_order_id, UserBean userBean);

	void submit(OrderModel model,UserBean userBean);
	/**
	 * 发货指令列表，分页查询
	 * @param params
	 * @param pageDesc
	 */
	void pageQuery2(Map<String, Object> params, PageDesc pageDesc);
	/**
	 * 推送NC数据
	 * @param sendOrderId
	 * @return
	 */
	OrderModel getSendOrder(String sendOrderId);
	List<SendDtlModel> getSendDtlOrder(String sendOrderId);
	
	/**
	 * 校验发货数量
	 * @param params
	 * @return
	 */
	Map<String, Object> checkSendNum(Map<String, Object> params);
	/**
	 * 发货单数据回写
	 */
	void FinishGoodNum(List<ErpStoreOutDtl> detailList,String factoryNo);
	/**
	 * 通用选取数据数据回显
	 */
	Map<String, Object> getSaleData(Map<String, Object> params);
	
	/**
	 * 审核通过
	 */
	void sendAudit(OrderModel model,String send_order_id,UserBean userBean);
	

}
