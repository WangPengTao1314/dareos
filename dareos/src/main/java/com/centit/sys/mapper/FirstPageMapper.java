package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.sys.model.MenuInfo;
@Repository
public interface FirstPageMapper {
	
	/**
	 * 计算，已完成的销售订单,近一年月总金额  
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> querySum(Map<String, Object> params);
	/**
	   *  查询返修未完成，未通过的单据条数
	 */
	Integer ReworkCounts(Map<String, Object> params);
	/**
	   *  销售订单未完成，未通过的单据条数
	 */
	Integer SaleCounts(Map<String, Object> params);
	/**
	 * 账户管理充值信息 
	 */
	Integer DepositCounts(Map<String, Object> params);
	/**
	 * 额度信息
	 */
	Integer CreditReqCounts(Map<String, Object> params);
	/**
	   *  要货单
	 */
	Integer GoodsorderCounts(Map<String, Object> params);
	
	/**
	 * 款式下单排名
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> orderRanking(Map<String, Object> params);
	/**
	 * 用户对单据的操作权限
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> OperationAuthority(Map<String, Object> params);
	
	/**
	 * 加载菜单
	 * @param map
	 * @return
	 */
	 List<Map<String, Object>> findByUserIdDrp(Map<String,String> map);
	
	 List<Map<String, Object>> findByUserIdErp(Map<String,String> map);
	
	 List<Map<String, Object>> findAll();
	 /**
	  * 
	  * @param map
	  * @return
	  */
	 List<Map<String, Object>> findByUserIdDrp2(Map<String,String> map);
		
	 List<Map<String, Object>> findByUserIdErp2(Map<String,String> map);
	

}
