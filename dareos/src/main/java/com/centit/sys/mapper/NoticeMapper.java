package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import com.centit.sys.model.NotcDeptModel;
import org.springframework.stereotype.Repository;


@Repository
public interface NoticeMapper {
	
	Map<String,Object> loadById(String NOTICE_ID);
	
	/**
	 * 
	 * @Title: pageQuery 
	 * @Description: 分页查询
	 * @author: liu_yg
	 * @date: 2019年2月26日 下午3:05:02 
	 * @param pageQureyMap
	 * @return
	 * @return: List<Map<String,String>>
	 */
	List<Map<String,String>>  pageQuery(Map<String, Object> pageQureyMap);
	
	Integer pageCount(Map<String, Object> map);
	
	void insert(Map<String,String> params);
	
	void updateById(Map<String,String> params);
	
	void delete(Map<String,String> params);

	void insertDeptChld(Map<String,String> params);

	void deleteDeptChild(Map<String,String> params);

    List<Map<String,String>> loadDeptChldByMainId(Map<String,String> params);
	/**
	 * 查询分管账套信息
	 * @return
	 */
	List<Map<String,String>>  queryFGZTXXByUser(Map<String,String> params);
}
