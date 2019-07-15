package com.centit.base.monthstatement.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.base.monthstatement.model.MonthAcctModel;

@Repository
public interface MonthStatementMapper {

	/**
	 * 查询月结列表
	 * @param param
	 * @return
	 */
	List<MonthAcctModel> pageQuery(Map<String,Object> param);
	
	/**
	 * 获取月结信息
	 * @param year
	 * @param month
	 * @return
	 */
	Map<String,String> getStatementInfo(@Param("year")String year,@Param("month")String month);
	
	/**
	 * 月结
	 * @param map
	 */
	void insertStatement(Map<String,String> map);
	
	/**
	 * 修改月结标记
	 * @param MONTH_ACCT_ID
	 */
	void updStatementFlag(String MONTH_ACCT_ID);
	
	void statement(String year);
}
