package com.centit.drp.main.shopcar.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCarMapper {
	
	List<Map<String,Object>> pageQuery(Map<String,Object> map);
	
	int pageCount(Map<String,Object> map);
	
	void update(List<Map<String,Object>> list);
	
	void insertSpcl(Map<String,Object> map);
	
	/**
	 * 新增 订货订单
	 * @param orderMap
	 */
	void insert(Map<String, Object> orderMap);
	
	void insertDtl(List<Map <String, Object>> list);
	
	/**
	 * 渠道信息信用获取方式
	 * @param map
	 */
	Map<String, Object> getChannCreDit(String CHANN_ID);
	
	void delete(Map<String,Object> map);
	
	/**
	 * 根据货品id获取货品体积
	 * @param PRD_ID
	 * @return
	 */
	int getPrdVOLUME(String PRD_ID);
	
	/**
	 * 修改订货订单ID
	 */
	void upShopCar(Map<String,Object> map);
	
	/**
	 *修改价格、折扣率
	 */
	void upPrice(Map<String,Object> map);
	
	/**
	 * 根据传进来的工艺单id查询合并后的数据
	 */
	List<Map <String, Object>> getPrdInfo(String SHOP_CART_ID);
	
	/**
	 * 更新处理标记
	 */
	void upDeal(Map<String,Object> map);
	
	/**
	 * 根据渠道id获取渠道类型
	 */
	Map<String,Object> getCHANN_TYPE(String CHANN_ID);
	
	/**
	 * 获取发货信息
	 */
	Map<String,Object> getShipPointInfo(String CHANN_ID);
	
	/**
	 * 根据渠道id和折扣类型 查询折扣率
	 */
	String getLargessDect(Map<String,Object> map);
	
	/**
	 * 根据渠道id查询区域信息
	 */
	Map<String,String> getWareaInfo(String CHANN_ID);
	
	/**
	 * 查询订货订单号是否重复
	 */
	int judgeGoodNo(String GOODS_ORDER_NO);
	
	/**
	 * 根据渠道id和删除标识查询数量
	 */
	int checkAdvcReturn(Map<String,Object> map);
	
	List<Map<String,String>> getLedgerGroup(@Param("ids")String ids);
	
	void insertOrderDtl(Map<String, Object> orderMap);
	
}
