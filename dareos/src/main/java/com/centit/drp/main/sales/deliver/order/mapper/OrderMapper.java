package com.centit.drp.main.sales.deliver.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.drp.main.sales.deliver.order.model.OrderModel;
import com.centit.drp.main.sales.deliver.order.model.SendDtlModel;

@Repository
public interface OrderMapper {

	/**
	   *   分页查询
	 * @param map
	 * @return
	 */
	Map<String, Object> saleQuery(Map<String, Object> map);
	List<Map<String, Object>> pageQuery2(Map<String, Object> params);
	//主表单条记录查询
	Map<String, Object> toQuery(Map<String, Object> params);
	//销售订单明细记录查询
	List<Map<String, Object>> toQuerySaleSun(Map<String, Object> params);
	//发货指令明细记录查询 
	List<Map<String, Object>> toQuerySendSun(Map<String, Object> params);
	//获取单条销售订单明细 allSendNum2
	Map<String, Object> allSendNum(Map<String, Object> params);
	
	List<Map<String, Object>> allSendNum2(Map<String, Object> params);
	
	Map<String, Object> querySendIdByNcId(String nc_return_id);
	
	//根据发货单明细id查询单条记录
	Map<String, Object> queryDtlById(Map<String, Object> params);
	
	//编辑数据
	void insert(OrderModel model);
	void modify(OrderModel model);
	//
	void modifySaleDel(Map<String, Object> map);
	//
	void modifySale(Map<String, Object> map);
	/**
	   *     删除发货指令
	 * @param send_order_id
	 */
	void deleteFH(@Param("send_order_id") String send_order_id);
	//
	void updateByID(Map<String, Object> params);
	//编辑子表
	void updateSunById(SendDtlModel model);
	void insertSun(SendDtlModel model);
	/**
	 * 发货单推数据推送NC
	 * @param sendOrderId
	 * @return
	 */
	OrderModel getSendOrder(String sendOrderId);
	List<SendDtlModel> getSendDtlOrder(String sendOrderId);
	/**
	 *发货单数据回写
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> dataBackNC(Map<String, Object> params);
	
	/**
	 * 根据发货指令主表ID删除明细ID
	 * @param sendOrderId
	 */
	void delSendDtlById(String sendOrderId);
	
	/**
	 * 更新发货指令明细发货数量
	 * @param list
	 */
	void updSendSendedNum(List<Map<String,String>> list);
	
	/**
	 * 更新销售订单明细发货数量
	 * @param list
	 */
	void updSaleSendedNum(List<Map<String,String>> list);
	
	/**
	 * 根据发货指令明细ID获取发货指令ID
	 * @param sendOrderDtlIds
	 * @return
	 */
	List<String> getSendIdByDtlIds(@Param("sendOrderDtlIds") String sendOrderDtlIds);
	
	/**
	 * 根据发货指令ID判断已发数量修改状态
	 * @param map
	 */
	void updSendState(Map<String,String> map);
	
	/**
	 * 根据厂编获取销售订单剩余发货数量
	 * @param factoryNo
	 * @return
	 */
	String getSendedSaleId(String factoryNo);
	
	/**
	 * 根据销售订单id查询所有明细中是否存在订单数量小于已发货数量的数据
	 * @param saleOrderId
	 * @return
	 */
	Map<String,String> queryNumIsTrue(String factory_no);
	
	/**
	 * 根据销售订单主表ID获取主表信息
	 * @param saleOrderId
	 * @return
	 */
	Map<String,String> getSaleOrderById(String saleOrderId);
	
	/**
	 * 根据销售订单ID校验下面物料发货有没有超
	 * @param saleOrderId
	 * @return
	 */
	List<String> checkSendNum(String saleOrderId);
	
	/**
	 * 根据出库单ID获取出库单明细出库数量
	 * @param getOutDtlById
	 * @return
	 */
	List<Map<String,String>>  getOutDtlById(String storeOutId);
	
	/**
	 * 根据出库单ID获取厂编
	 * @param storeOutId
	 * @return
	 */
	String getFactoryNoByOutId(String storeOutId);
	/**
	 * 校验返修发货单号
	 * @param factory_no
	 * @return
	 */
	Integer checkFactoryNO(@Param("FACTORY_NO")String factory_no);
	
}
