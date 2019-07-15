package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.sys.model.XswModel;


@Repository
public interface SysLoginMapper {
	
	void insertLoginLog(Map<String,String> map);
	
	String getIS_DRP_LEDGER(String ZTXXID);
	
	Map<String,String> getBaseChann();
	
	Map<String,String> getcurrChann(String CHANN_ID);
	
	Map<String,String> getCurrTrem(String DEPT_NO);
	
	Map<String,String> getYHXXByYHM(String YHM);
	
	List<String> getXTMKByXTYH(String XTYHID);
	
	List<String> getXTMKByXTJS(@Param("XTJSIDS") String XTJSIDS);
	
	List<String> getXTMKByGZZXX(@Param("GZZXXIDS") String GZZXXIDS);
	
	List<String> getAllXTMK();
	
	/**
	 * 
	 * @Title: selcomList 
	 * @Description: 根据拼接SQL查询结果集
	 * @author: liu_yg
	 * @date: 2019年2月28日 下午1:57:05 
	 * @param map
	 * @return
	 * @return: List<Map<String,String>>
	 */
	List<Map<String,String>> selcomList(Map<String,String> map);
	
	List<XswModel> getXSWModel(String STATE);
	
	/**
	 * 根据系统用户ID获取帐套分管
	 * @param xtyhId
	 * @return
	 */
	List<String> getLedgerChrgByXtyhId(String xtyhId);
	
	/**
	 * 根据经销商ID获取帐套分管
	 * @param jgxxid
	 * @return
	 */
	List<String> getLedgerChrgByJgxxid(String jgxxid);
	
}
