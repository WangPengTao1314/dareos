package com.centit.drp.main.sales.deliver.after.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.drp.main.sales.deliver.after.model.AfterModel;

@Repository
public interface AfterMapper {
	/**
	 * 显示列表
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> pageQuery(Map<String, Object> params);
	/**
	 * 查询单挑记录
	 * @param params
	 * @return
	 */
	Map<String, Object> toQuery(Map<String, Object> params);
	
	/**
	 * 编辑
	 * @param model
	 */
	void modify(AfterModel model);
	void insert(AfterModel model);
	
	/**
	 * 状态删除
	 * @param params
	 */
	void updateByID(Map<String, Object> params);
	 void updateFile(Map<String, Object> params);
	/**
	 * 自定义附件保存
	 * @param 
	 */
	void insertFile(Map<String, Object>params);

	/**
	  * 自定义附件查询
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> toQueryFile(Map<String, Object> params);
	/**
	 * checkProblemNOEdit
	 */
	int checkProblemNO(@Param("PROBLEM_FEEDBACK_NO")String PROBLEM_FEEDBACK_NO);
}
