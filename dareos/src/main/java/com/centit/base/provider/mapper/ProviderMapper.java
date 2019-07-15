package com.centit.base.provider.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface ProviderMapper {
	
	List<Map<String,Object>> pageQuery(Map<String,Object> map);
	
	/**
	 * 根据主键id查询供应商信息
	 * @param param
	 * @return
	 */
	Map<String,Object> loadById(String param);
	
	/**
	 * 新增供应商信息
	 */
	void insert(Map<String,Object> map);
	
	/**
	 * 根据id修改供应商信息
	 */
	void updateById(Map<String, Object> params);
	
	/**
	 * （软删除）
	 */
	void delete(Map<String, Object> params);
	
	/**
	 * 获取供应商中最大的同步时间
	 * @return
	 */
	public String queryMaxTbTime();
	
	/**
	 * 
	 * @Title: queryInsertNcAddr
	 * @Description: 查询从NC过来需要新插入的供应商
	 * @author lv_f
	 * @date 2019年5月24日 上午9:43:42
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>>  queryInsertNcPrvd(Map<String, Object> params);
	
	/**
	 * 
	 * @Title: queryUpdateNcAddr
	 * @Description: 查询从NC过来需要更新的供应商
	 * @author lv_f
	 * @date 2019年5月24日 上午9:52:33
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>>  queryUpdateNcPrvd(Map<String, Object> params);
	
	/**
	 * 
	 * @Title: insertPrvdList
	 * @Description: 批量插入供应商表
	 * @author lv_f
	 * @date 2019年5月24日 上午8:27:17
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void insertPrvdList(List<Map<String,Object>> list);
	
	/**
	 * 
	 * @Title: updatePrvdList
	 * @Description: 批量更新供应商表
	 * @author lv_f
	 * @date 2019年5月24日 上午8:29:14
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void updatePrvdList(List<Map<String,Object>> list);
}
