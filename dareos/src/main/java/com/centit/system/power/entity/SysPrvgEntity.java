package com.centit.system.power.entity;

import java.io.Serializable;

/**
 * @ClassName: SysPrvgEntity 
 * @Description: 操作权限-Entity
 * @author: zhu_hj
 * @date: 2018年5月18日 下午2:23:28
 */
public class SysPrvgEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private String prvgId; // 权限ID

    private String menuId; // 菜单ID

    private String prvgNo; // 权限编号

    private String prvgName; // 权限名称

    private String prvgType; // 权限类型

    private String prvgLvl; // 权限级别

    private String prvgSql; // 权限SQL

	public String getPrvgId() {
		return prvgId;
	}

	public void setPrvgId(String prvgId) {
		this.prvgId = prvgId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getPrvgNo() {
		return prvgNo;
	}

	public void setPrvgNo(String prvgNo) {
		this.prvgNo = prvgNo;
	}

	public String getPrvgName() {
		return prvgName;
	}

	public void setPrvgName(String prvgName) {
		this.prvgName = prvgName;
	}

	public String getPrvgType() {
		return prvgType;
	}

	public void setPrvgType(String prvgType) {
		this.prvgType = prvgType;
	}

	public String getPrvgLvl() {
		return prvgLvl;
	}

	public void setPrvgLvl(String prvgLvl) {
		this.prvgLvl = prvgLvl;
	}

	public String getPrvgSql() {
		return prvgSql;
	}

	public void setPrvgSql(String prvgSql) {
		this.prvgSql = prvgSql;
	}

}