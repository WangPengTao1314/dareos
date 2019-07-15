package com.centit.erp.sale.depositinfo.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.erp.sale.depositinfo.model.DepositInfoModel;

@Repository
public interface DepositInfoMapper {
	
	List<DepositInfoModel> pageQuery(Map<String,Object> map);
	
	/**
	 * 根据主键id查询充值信息信息
	 * @param param
	 * @return
	 */
	DepositInfoModel loadById(Map<String,String> map);
	
	/**
	 * 新增充值信息信息
	 */
	void insert(Map<String,String> map);
	
	/**
	 * 根据id修改信息
	 */
	void updateById(Map<String, String> params);
	
	/**
	 * （软删除）
	 */
	void delete(Map<String, String> params);
}
