/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：BMXXService.java
 */
package com.centit.sys.service;

import java.util.List;
import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.sys.model.BMXXModel;
import com.centit.sys.model.BMXXTree;
import com.centit.sys.model.UserBean;

/**
 * The Interface BMXXService.
 * 
 * @module 系统管理
 * @func 部门信息
 * @version 1.1
 * @author 吴亚林
 */
public interface BMXXService {

    /**
     * 查询并分页.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    @SuppressWarnings("unchecked")
    public void pageQuery(Map<String, Object> map, PageDesc pageDesc);


    /**
     * 新增.
     * 
     * @param params the params
     * 
     * @return true, if insert
     */
    @SuppressWarnings("unchecked")
    public void insert(Map params);


    /**
     * 修改.
     * 
     * @param params the params
     * 
     * @return true, if update by id
     */
    @SuppressWarnings("unchecked")
    public void updateById(Map params);


    /**
     * 删除.
     * 
     * @param BMXXID the bMXXID
     * @param userBean the user bean
     * 
     * @return true, if tx delete
     */
    public void txDelete(String BMXXID, UserBean userBean);

    
    /**
     * 软删除.
     * 
     * @param BMXXID the BMXX id
     * @param userBean the user bean
     * @return true, if tx delete
     */
    public void txNewDelete(String BMXXID, UserBean userBean);

    /**
     * 加载.
     * 
     * @param param the param
     * 
     * @return the map
     */
    @SuppressWarnings("unchecked")
    public Map load(String param);


    /**
     * 编辑.
     * 
     * @param BMXXID the bMXXID
     * @param model the model
     * @param userBean the user bean
     */
    public void txEdit(String BMXXID, BMXXModel model, UserBean userBean);


    /**
     * 部门树.
     * 
     * @param ctx the ctx
     * @param theme the theme
     * 
     * @return the tree
     * 
     * @throws Exception the exception
     */
    public List <BMXXTree> getTree(String ctx, String theme) throws Exception;


    /**
     * 判断部门编号是否存在.
     * 
     * @param BMXXBH the bMXXBH
     * 
     * @return the bmxx exits
     */
    public int getBmxxExits(String BMXXBH);


    /**
     * 修改.
     * 
     * @param params the params
     * 
     * @return true, if tx update by id
     */
    public void txUpdateById(Map <String, String> params);


    /**
     * Tx update all by id.
     * 
     * @param params the params
     * 
     * @return true, if successful
     */
    public void txUpdateAllById(Map <String, String> params);


    /**
     * 校验是否有未生效的上级部门.
     * 
     * @param params the params
     * 
     * @return the int
     */
    public int cksjbm(Map <String, String> params);


    /**
     * 校验是否有未停用的下级部门.
     * 
     * @param params the params
     * 
     * @return the int
     */
    public int ckxjbm(Map <String, String> params);
    
    
    /**
     * 加载.
     * 
     * @param SJBM the sJBM
     * 
     * @return true, if load jgzt
     */
    public boolean loadBMZT(String SJBM);
    
    /**
     * 加载.
     * 
     * @param SJJG the sJJG
     * 
     * @return true, if load jgzt
     */
    public boolean loadJGZT(String SJJG);
}
