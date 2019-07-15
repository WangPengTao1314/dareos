package com.centit.system.dept.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.system.dept.entity.SysDeptEntity;
import com.centit.system.dept.entity.SysDeptTree;

/**
 * @ClassName: SysDeptMapper 
 * @Description: 部门信息-DAO
 * @author: zhu_hj
 * @date: 2018年5月17日 下午4:28:57
 */
@Repository
public interface SysDeptMapper {
    
	/**
     * 查询部门列表
     */
	List<SysDeptEntity> query(Map<String, Object> map);
	
	/**
     * 查询部门树
     */
	List<SysDeptTree> queryDeptTree(Map<String, Object> map);
	
	/**
	 * 按ID查找部门信息
	 */
	SysDeptEntity selectByDeptId(String deptId);

    /**
	 * 插入时判断编码是否重复
	 */
	int checkNoIsExists(String deptNo);
	
	/**
	 * 插入部门信息
	 */
    int insert(SysDeptEntity record);
    
    /**
	 * 修改部门信息
	 */
    int updateByDeptId(SysDeptEntity record);
    
    /**
	 * 判断部门是否有下级
	 */
	int checkDeptHasChild(String deptId);
	
	/**
	 * 判断部门下是否有人员
	 */
	int checkDeptHasUser(String deptId);
	
	/**
	 * 删除部门
	 */
    int deleteByDeptId(String deptId);
    
    /**
     * 启用/停用部门及其下级部门
     */
    int updateDeptState(SysDeptEntity record);
    
    /**
     * 根据父节点查询下级部门
     */
    List<SysDeptTree> queryDeptListByParent(Map<String, Object> map);
    
    /**
     * 按ID查询直接子节点的数据 
     */
    int getCountByParId(@Param("deptId") String deptId);
}