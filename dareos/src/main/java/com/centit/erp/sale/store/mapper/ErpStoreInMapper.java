package com.centit.erp.sale.store.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.erp.sale.store.model.ErpStoreIn;
import com.centit.erp.sale.store.model.ErpStoreInDtl;


@Repository
public interface ErpStoreInMapper {
	
	/**
	   *    列表分页查询
	 * @param params
	 */
	List<Map<String, Object>> pageQuery(Map<String, Object> params);
	/**
	 *   查询单条记录
	 * @param params
	 * @return
	 */
	Map<String, Object> toQuery(Map<String, Object> params);
	/**
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> toQuerySun(Map<String, Object> params);
	
	/**
	   * 编辑主表
	 * @param model
	 */
	void edit(ErpStoreIn model);
	/**
	  *  编辑附表
	 * @param models
	 */
	void editSun(ErpStoreInDtl models);
	
	/**
	 * 
	 * @Title: insertErpStoreIn
	 * @Description: 插入主表
	 * @param @param progressModel
	 * @return void
	 * @throws
	 */
	void insertErpStoreIn(ErpStoreIn model);
	
	/**
	 * 
	 * @Title: insertErpStoreInDtlList
	 * @Description: 插入子表
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void insertErpStoreInDtlList(List<ErpStoreInDtl> list);

}
