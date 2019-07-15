package com.centit.drp.main.project.management.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.centit.drp.main.project.management.model.ManageModel;
import com.centit.drp.main.project.management.model.PayableModel;
import com.centit.drp.main.project.management.model.ProOrderModel;

@Repository
public interface ManageMapper {
	
	
	
	/**
	   *   分页查询
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> pageQuery(Map<String, Object> map);
	/**
	   *   查询付款记录
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> listQuery(Map<String, Object> params);
	/**
	 *      编辑
	 * @param model
	 */
	void modify(ManageModel model);
	void insert(ManageModel model);
	
	/**
	   *    删除项目，更改状态
	 * @param params
	 */
	void updateByID(Map<String, Object> params);
	/**
	  *     更新附件信息
	 * @param params
	 */
	void updateFile(Map<String, Object> params);
	/**
	   *      查询单条记录 
	 * @param params
	 * @return
	 */
	Map<String, Object> toQuery(Map<String, Object> params);
	/**
	   *     项目节点维护_回款信息维护
	 * @param map
	 */
	void insertMX(PayableModel models );
	void editMX(PayableModel models );
	/**
	   *     删除汇款信息记录
	 * @param PROJECT_PAYABLE_ID
	 */
	void deleteById(@Param("project_payable_id")String PROJECT_PAYABLE_ID);
	/**
	   *     查询附件记录
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> toQueryFile(Map<String, Object> params);
	/**
	   *   项目节点附件维护
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> toQueryFile2(Map<String, Object> params);
	/**
	 * @param list
	 */
	void insertFile(List<Map<String, Object>> list);
	/**
	 * 添加项目指令
	 * @param model
	 */
	void insertOrderFile(ProOrderModel model);
	/**
	 * 实际出货总金额
	 * @param params
	 * @return
	 */
	Map<String, Object> sumMonery(Map<String, Object> params);
}
