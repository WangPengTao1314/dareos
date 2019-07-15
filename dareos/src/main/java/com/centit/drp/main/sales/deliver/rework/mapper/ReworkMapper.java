package com.centit.drp.main.sales.deliver.rework.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.drp.main.sales.deliver.order.model.OrderModel;
import com.centit.drp.main.sales.deliver.order.model.SendDtlModel;
/**
 * 
 * @author wang_pt
 *
 */
@Repository
public interface ReworkMapper {
	
	void updateByID(Map<String, Object> params);//
	void updateSunByID(Map<String, Object> params);
	List<Map<String, Object>> pageQuery(Map<String, Object> params);
	
	/**
	   *     编辑
	 * @param model
	 */
	void insert(OrderModel model);
	void modify(OrderModel model);
	/**
	   *      编辑回显查询
	 * @param params
	 * @return 
	 */
	Map<String, Object> toQuery(Map<String, Object> params);
	List<Map<String, Object>> toQuerySun(Map<String, Object> map);
	
	//回调
	Map<String, Object> toQueryBack(Map<String, Object> params);
	List<Map<String, Object>> toQuerySunBack(Map<String, Object> map);
	/**
	 * 编辑从表
	 * @param reworkModelDel
	 */
	void insertSun(SendDtlModel reworkModelDel);
	void modifySun(SendDtlModel reworkModelDel);
	
	/**
	   *  查询返修未完成，未通过的单据条数
	 */
	Integer counts(Map<String, Object> params);
	Integer checkFactoryNO(@Param("FACTORY_NO")String factory_no);
	
	
}
