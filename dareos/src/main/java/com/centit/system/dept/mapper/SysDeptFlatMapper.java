package com.centit.system.dept.mapper;

import org.springframework.stereotype.Repository;

import com.centit.system.dept.entity.SysDeptFlatEntity;

/**
 * @ClassName: SysDeptFlatMapper 
 * @Description: 部门关联关系-DAO
 * @author: zhu_hj
 * @date: 2018年5月21日 下午4:30:06
 */
@Repository
public interface SysDeptFlatMapper {
    
	/**
	 * 根据主键ID查找关联记录
	 */
	SysDeptFlatEntity selectByDeptFlatId(String deptFlatId);
	
	/**
	 * 插入部门关联记录
	 */
    int insert(SysDeptFlatEntity record);

    /**
	 * 按部门ID删除关联记录
	 */
    int deleteByDeptId(String deptId);
    
    /**
	 * 按上级部门ID删除关联记录
	 */
    int deleteByParDeptId(String ParDeptId);
    
    /**
	 * 按主键ID删除关联记录
	 */
    int deleteByDeptFlatId(String deptFlatId);
}