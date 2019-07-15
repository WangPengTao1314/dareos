package com.centit.base.distributor.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.base.distributor.model.DistributorModel;

@Repository
public interface DistributorMapper {
	List<Map<String, String>> pageQuery(Map<String, Object> params);
	Integer pageCount(Map<String, String> map);
	List<Map<String, String>> query(Map<String, String> map);
	Map<String, String> loadById(Map<String, String> params);
	void insert(Map<String, String> params);
	void updateById(Map<String, String> params);
	void delete(Map<String, String> map);
	void updateStateById(Map<String, String> params);
	List<Map> qryExp(Map<String, String> params);
	String queryMaxNo(Map<String, String> map);
	List<DistributorModel> queryMaxNoT(String str);
}
