package com.centit.drp.main.project.tendering.mapper;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.centit.drp.main.project.tendering.model.ProjectInfoModel;

@Repository
public interface ProjectInfoMapper {
	/**
	 * 编辑
	 * @param model
	 */
	void insert(ProjectInfoModel model);
	void modify(ProjectInfoModel model);
	
	/**
	   *   分页查询
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> pageQuery(Map<String, Object> map);
	 
	/**
	 * 查询项目单挑记录
	 * @param params
	 * @return
	 */
	Map<String, Object> toQuery(Map<String, Object> params);
	/**
	 * 更改状态 删除记录信息
	 * @param params
	 */
	void updateByID(Map<String, Object> params);

}
