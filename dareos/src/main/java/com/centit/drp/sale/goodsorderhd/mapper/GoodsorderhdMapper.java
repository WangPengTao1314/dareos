package com.centit.drp.sale.goodsorderhd.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.drp.sale.goodsorderhd.model.GoodsorderhdModelChld;

@Repository
public interface GoodsorderhdMapper {
	List<Map<String, String>> pageQuery(Map<String, Object> map);
	Integer pageCount(Map<String, Object> map);
	List<Map<String, Object>> query(Map<String, Object> map);

	Map<String, String> getRate(Map<String, String> map);
	void insert(Map<String, String> map);
	void updateById(Map<String, String> params);
	void delete(Map<String, String> params);
	Map<String, Object> loadById(String str);
	List <Map<String, String>> queryChldByFkId(Map<String, Object> map);
	List <GoodsorderhdModelChld> loadChldByIds(Map<String, String> map);
	void insertChld(List<Map<String, Object>> listmap);
	void updateChldById(List<Map<String, Object>> listmap);
	void deleteChldByIds(Map<String, Object> map);
	void delChldByFkId(Map<String, String> params);
	void afterAuditing(Map<String, Object> map);
	void updateCREDIT_FREEZE_PRICE(Map<String, String> map);
	Map<String, String> queryTotal(Map<String, String> map);
	List<Map<String, String>> queryAdvcOrderByGoodsOrder(Map<String, String> map);
	List<String> getChannDiscount(String str);
	void delDtlById(Map<String, Object> map);
	Integer queryMaxRowNo(Map<String, String> map);
	List<Map<String,Object>> toQuertAvdcDtl(Map<String, String> map);
	Map<String, Object> loadByIdForUpdate(String str);
	void upAdvcOrder(String str);
	
	/**
	 * 根据销售订单ID查询来源预订单生成的所有销售订单
	 * @param saleOrderId
	 * @return
	 */
	public List<Map<String,String>> getSalesById(String goodsOrderId);

}
