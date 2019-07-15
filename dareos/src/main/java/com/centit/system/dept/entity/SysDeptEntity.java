package com.centit.system.dept.entity;

import java.io.Serializable;

/**
 * @ClassName: SysDeptEntity 
 * @Description: 部门信息-Entity
 * @author: zhu_hj
 * @date: 2018年5月17日 下午4:26:26
 */
public class SysDeptEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private String deptId; // 部门ID

    private String deptNo; // 部门编号

    private String deptName; // 部门名称

    private String deptNameAbbr; // 部门简称 

    private String tel; // 电话

    private String tax; // 传真

    private String address; // 地址 

    private String parDeptId; // 上级部门ID

    private String parDeptName; // 上级部门名称

    private String orgId; // 组织ID

    private String orgNo; // 组织编号

    private String orgName; // 组织名称

    private String leaderId; // 负责人ID

    private String leaderName; // 负责人名称

    private String memo; // 备注

    private String creId; //制定人ID

    private String creName; //制定人名称

    private String creTime; //制定时间

    private String updId; //修改人ID

    private String updName; //修改人名称

    private String updTime; //修改时间

    private String state; //状态

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptNameAbbr() {
		return deptNameAbbr;
	}

	public void setDeptNameAbbr(String deptNameAbbr) {
		this.deptNameAbbr = deptNameAbbr;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getParDeptId() {
		return parDeptId;
	}

	public void setParDeptId(String parDeptId) {
		this.parDeptId = parDeptId;
	}

	public String getParDeptName() {
		return parDeptName;
	}

	public void setParDeptName(String parDeptName) {
		this.parDeptName = parDeptName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCreId() {
		return creId;
	}

	public void setCreId(String creId) {
		this.creId = creId;
	}

	public String getCreName() {
		return creName;
	}

	public void setCreName(String creName) {
		this.creName = creName;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getUpdId() {
		return updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public String getUpdName() {
		return updName;
	}

	public void setUpdName(String updName) {
		this.updName = updName;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}