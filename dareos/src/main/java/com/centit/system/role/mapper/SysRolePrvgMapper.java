package com.centit.system.role.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.system.role.entity.SysRolePrvgEntity;

@Repository
public interface SysRolePrvgMapper {
	
	/**
	 * 根据角色ID查询角色权限
	 */
	List<SysRolePrvgEntity> queryPrvgsByRoleId(String roleId);
	
	/**
	 * 根据用户ID查询关联的操作权限
	 */
	List<SysRolePrvgEntity> queryPrvgsByUserId(String userId);
	
	/**
	 * 根据用户ID、菜单编码、权限编码，查询权限SQL
	 */
	String queryPrvgSql(Map<String, Object> map);
	
	/**
	 * 根据主键ID查找关联记录
	 */
	SysRolePrvgEntity selectByRolePrvgId(String rolePrvgId);
	
	/**
	 * 根据角色ID和权限ID查找关联记录
	 */
	SysRolePrvgEntity selectByRoleIdAndPrvgId(@Param("roleId")String roleId, @Param("prvgId")String prvgId);
	
	/**
	 * 保存角色权限关联记录
	 */
	int insert(SysRolePrvgEntity record);
    
	/**
	 * 按主键ID删除角色权限关联记录
	 */
    int deleteByRolePrvgId(String rolePrvgId);

    /**
	 * 按角色ID删除角色权限关联记录
	 */
    int deleteByRoleId(String roleId);
    
    /**
	 * 按权限ID删除角色权限关联记录
	 */
    int deleteByPrvgId(String prvgId);
    
    /**
	 * 按菜单ID删除角色权限关联记录
	 */
    int deleteByMenuId(String menuId);
    
    /**
	 * 按角色ID和权限ID删除角色权限关联记录
	 */
    int deleteByRoleIdAndPrvgId(@Param("roleId")String roleId, @Param("prvgId")String prvgId);
    
}