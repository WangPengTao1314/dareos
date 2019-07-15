package com.centit.drp.main.order.progress.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.drp.main.order.progress.model.ProgressDetModel;
import com.centit.drp.main.order.progress.model.ProgressModel;


@Repository
public interface ProgressMapper {
	
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
	 * 状态删除
	 * @param params
	 */
	void updateByID(Map<String, Object> params);
	/**
	   * 编辑主表
	 * @param model
	 */
	void edit(ProgressModel model);
	/**
	  *  编辑附表
	 * @param models
	 */
	void editSun(ProgressDetModel models);
	
	/**
	 * 
	 * @Title: insertProgressModel
	 * @Description: 插入进度确认主表
	 * @author lv_f
	 * @date 2019年5月29日 下午1:37:05
	 * @param @param progressModel
	 * @return void
	 * @throws
	 */
	void insertProgressModel(ProgressModel progressModel);
	
	/**
	 * 
	 * @Title: insertProgressDetModelList
	 * @Description: 插入进度确认子表
	 * @author lv_f
	 * @date 2019年5月29日 下午1:37:10
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void insertProgressDetModelList(List<ProgressDetModel> list);

}
