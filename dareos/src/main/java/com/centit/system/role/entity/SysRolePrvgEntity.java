package com.centit.system.role.entity;

import java.io.Serializable;

/**
 * @ClassName: SysRolePrvgEntity 
 * @Description: 角色权限-Entity
 * @author: zhu_hj
 * @date: 2018年5月18日 上午10:18:40
 */
public class SysRolePrvgEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    private String rolePrvgId; // 主键ID

    private String roleId; // 角色ID

    private String prvgId; // 权限ID

    private String menuId; // 菜单ID
    
    private String roleNo; // 角色编号
    
    private String menuNo; // 菜单编号
    
    private String prvgNo; // 权限编号

	public String getRolePrvgId() {
		return rolePrvgId;
	}

	public void setRolePrvgId(String rolePrvgId) {
		this.rolePrvgId = rolePrvgId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

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

	public String getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public String getPrvgNo() {
		return prvgNo;
	}

	public void setPrvgNo(String prvgNo) {
		this.prvgNo = prvgNo;
	}
}