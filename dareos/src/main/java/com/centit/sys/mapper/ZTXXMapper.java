package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.sys.model.ZTWHModel;
import com.centit.sys.model.ZTWHTree;

 
@Repository
public interface ZTXXMapper {
	
	Map<String, Object> query(Map<String, Object> params);
	/**
	 * 根据主键id删除（硬删除）
	 * @param map
	 */
	void delete(Map<String,Object> map);
	
	void updateById(Map<String, Object> params);
	//getOne
	Map<String, Object> getOne(String params);
	//getOneRecord
	Map<String, Object> getOneRecord(String params);
	
	int insertRecord(Map<String, Object> params);
	//pageCount1
	int pageCount1(Map<String, Object> params);
	
	List<Map<String, Object>> pageQuery(Map<String, Object> pageQureyMap);

	int pageCount(Map<String, Object> pageQureyMap);

	/**
	 * 
	 * @param pageQureyMap
	 * @return
	 */
	List<Map<String, Object>> pageQuery1(Map<String, Object> pageQureyMap);
	
	/**
	 * 编号列表
	 * @return
	 */
	List<ZTWHModel> getAllBH();
	
	/**
	 * 根据主键id修改状态
	 */
	void updateZTStatus(Map<String, Object> params);
	
	/**
	 * 隐藏数据（软删除）
	 */
	void deleteById(Map<String, Object> params);
	
	/**
	 * 查询编号 名称 父节点
	 */
	List<ZTWHTree> queryTree();
	
	/**
	 * 根据主键id 计数帐套编号
	 */
	int getCountRecord(String params);
	
	/**
	 * 
	 */
	int querySjForInt(Map<String, Object> params);
	
	/**
	 * 根据上级账套查询状态
	 */
	String loadZTZT(String params);
}
