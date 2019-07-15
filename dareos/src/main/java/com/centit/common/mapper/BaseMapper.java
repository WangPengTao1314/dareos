package com.centit.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BaseMapper {	
	
	
	/**
	 * 根据SQL查询结果集
	 * @param sql
	 * @return
	 */
	List<Map<String,String>> selcomList(@Param("sql") String sql);
	
	/**
	 * 根据SQL新增数据
	 * @param sql
	 */
	void insert(@Param("sql") String sql);
	
	/**
	 * 根据SQL修改/删除数据
	 * @param sql
	 */
	void update(@Param("sql") String sql);
	
	/**
	 * 根据表名和条件查询行数
	 * @param tab
	 * @param con
	 * @return
	 */
	int getRowNum(@Param("tabName") String tab,@Param("conDition") String conDition);
	
	int getSeqByNo(@Param("no")String no);
	
	/**
	 * 查询SQL返回int类型
	 * @param sql
	 * @return
	 */
	int getIntBySql(@Param("sql") String sql);
	
	void delSeq(@Param("no")String no);
	void insertSeq(@Param("no")String no);
	
}
