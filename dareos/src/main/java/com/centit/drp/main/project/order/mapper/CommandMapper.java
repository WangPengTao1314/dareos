package com.centit.drp.main.project.order.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.drp.main.project.order.model.CommandModel;

@Repository
public interface CommandMapper {
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	Map<String, Object> toQuery(Map<String, Object> params);
	
	/**
	 * 编辑
	 * @param model
	 */
	void insert(CommandModel model);
	void modify(CommandModel model);
	
	/**
	 * 分页查询
	 * @param params
	 */
	List<Map<String, Object>> pageQuery(Map<String, Object> params);
	/**
	 * 状态删除
	 * @param params
	 */
	void updateByID(Map<String, Object> params);
	/**
	 * 修改状态
	 * @param params
	 */
	void updateStateByID(Map<String, Object> params);

}
