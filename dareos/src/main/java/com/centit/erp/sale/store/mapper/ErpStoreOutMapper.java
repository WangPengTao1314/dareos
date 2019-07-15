package com.centit.erp.sale.store.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.drp.main.order.progress.model.ProgressDetModel;
import com.centit.drp.main.order.progress.model.ProgressModel;
import com.centit.erp.sale.store.model.ErpStoreOut;
import com.centit.erp.sale.store.model.ErpStoreOutDtl;


@Repository
public interface ErpStoreOutMapper {
	
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
	void edit(ErpStoreOut model);
	/**
	  *  编辑子表
	 * @param models
	 */
	void editSun(ErpStoreOutDtl models);
	
	/**
	 * 
	 * @Title: insertErpStoreOut
	 * @Description: 插入主表
	 * @param @param progressModel
	 * @return void
	 * @throws
	 */
	void insertErpStoreOut(ErpStoreOut model);
	
	/**
	 * 
	 * @Title: insertErpStoreOutDtlList
	 * @Description: 插入子表
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void insertErpStoreOutDtlList(List<ErpStoreOutDtl> list);

}
