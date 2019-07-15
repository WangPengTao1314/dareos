package com.centit.system.dept.service;

import java.util.List;
import java.util.Map;

import com.centit.core.exception.BizException;
import com.centit.core.po.PageDesc;
import com.centit.system.dept.entity.SysDeptEntity;
import com.centit.system.dept.entity.SysDeptTree;

/**
 * @ClassName: SysDeptService 
 * @Description: 部门信息-Service
 * @author: zhu_hj
 * @date: 2018年5月17日 下午4:44:45
 */
public interface SysDeptService {
	
	void query(Map<String, Object> map, PageDesc pageDesc);
	
	List<SysDeptTree> queryDeptTree(Map<String, Object> map) throws Exception;
	
	SysDeptEntity getByDeptId(String deptId);
	
	void saveDept(SysDeptEntity dept) throws BizException;
	
	void enableDept(String deptId) throws BizException;
	
	void disableDept(String deptId) throws BizException;
	
	void deleteByDeptId(String deptId) throws BizException;
	
	List<SysDeptTree> queryDeptListByParent(Map<String, Object> map) throws Exception;
	
}
