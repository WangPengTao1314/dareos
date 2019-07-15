package com.centit.drp.main.myorder.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.base.product.model.ProductTree;

@Repository
public interface MyorderMapper {
	List<Map<String, Object>> pageQuery(Map<String, Object> map);
	Integer pageCount(Map<String, Object> map);
	List<String> findBRANDAll(Map<String, String> map);
	List<Map<String, Object>> getByPrdInfo(Map<String, Object> map);
	void insert(List<Map<String, String>> listmap);
	void update(List<Map<String, String>> listmap);
	List<String> findPRMT_PLANAll(Map<String, String> map);
	String getChannDiscount(String str);
	List<String> findType(Map<String, String> map);
	/**
	 * 查询树
	 * @return
	 */
	List<ProductTree> queryTree(String LEDGER_ID);
	
	List<Map<String,String>> getLedgerByChannId(String channId);
	
	List<Map<String,String>> getSeriesd(String LEDGER_ID);
}
