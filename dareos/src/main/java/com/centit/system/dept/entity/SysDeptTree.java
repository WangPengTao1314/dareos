package com.centit.system.dept.entity;

import com.centit.common.po.BaseTreeNode;

/**
 * @ClassName: SysDeptTree 
 * @Description: 组织部门树形结构
 * @author: zhu_hj
 * @date: 2018年5月23日 上午10:43:57
 */
public class SysDeptTree extends BaseTreeNode{
	private static final long serialVersionUID = 1L;
	
	private String no; // 编码
	
	private String oid; // 组织ID
	
	private String ono; // 组织编号
	
	private String oname; // 组织名称
	
	private String state; // 状态
	
	private Integer type; // 类型：0机构 1部门


	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOno() {
		return ono;
	}

	public void setOno(String ono) {
		this.ono = ono;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
