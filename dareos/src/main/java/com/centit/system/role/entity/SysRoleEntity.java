package com.centit.system.role.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: SysRoleEntity 
 * @Description: 角色信息-Entity
 * @author: zhu_hj
 * @date: 2018年5月18日 上午10:13:57
 */
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private String roleId; // 角色ID

    private String roleNo; // 角色编号

    private String roleName; // 角色名称

    private String orgId; // 组织ID

    private String orgNo; // 组织编号

    private String orgName; // 组织名称

    private String memo; // 备注

    private String creId; //制定人ID

    private String creName; //制定人名称

    private String creTime; //制定时间

    private String updId; //修改人ID

    private String updName; //修改人名称

    private String updTime; //修改时间

    private String state; //状态
    
    private List<SysRolePrvgEntity> prvgList; // 角色权限

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public List<SysRolePrvgEntity> getPrvgList() {
		return prvgList;
	}

	public void setPrvgList(List<SysRolePrvgEntity> prvgList) {
		this.prvgList = prvgList;
	}
}