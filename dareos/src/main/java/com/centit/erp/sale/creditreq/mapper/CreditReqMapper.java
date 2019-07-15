package com.centit.erp.sale.creditreq.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.erp.sale.creditreq.model.CreditReqModel;

@Repository
public interface CreditReqMapper {
	
	List<CreditReqModel> pageQuery(Map<String,Object> map);
	
	/**
	 * 根据主键id查询信用申请
	 * @param param
	 * @return
	 */
	CreditReqModel loadById(Map<String,String> map);
	
	/**
	 * 新增信用申请
	 */
	void insert(CreditReqModel model);
	
	/**
	 * 根据id修改信息
	 */
	void updateById(CreditReqModel model);
	
	/**
	 * （软删除）
	 */
	void delete(CreditReqModel model);
	
}
