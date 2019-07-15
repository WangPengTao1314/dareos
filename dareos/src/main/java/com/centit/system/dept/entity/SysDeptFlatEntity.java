package com.centit.system.dept.entity;

import java.io.Serializable;

/**
 * @ClassName: SysDeptFlatEntity 
 * @Description: 部门关联关系-Entity
 * @author: zhu_hj
 * @date: 2018年5月21日 下午4:30:21
 */
public class SysDeptFlatEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String deptFlatId; // 主键ID

    private String deptId; // 部门ID

    private String parDeptId; // 上级部门ID

	public String getDeptFlatId() {
		return deptFlatId;
	}

	public void setDeptFlatId(String deptFlatId) {
		this.deptFlatId = deptFlatId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getParDeptId() {
		return parDeptId;
	}

	public void setParDeptId(String parDeptId) {
		this.parDeptId = parDeptId;
	}
    
}