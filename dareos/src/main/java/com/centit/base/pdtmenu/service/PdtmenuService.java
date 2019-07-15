package com.centit.base.pdtmenu.service;

import java.util.List;
import java.util.Map;

import com.centit.base.pdtmenu.model.PdtmenuModel;
import com.centit.base.pdtmenu.model.PdtmenuTree;
import com.centit.core.po.PageDesc;
import com.centit.sys.model.UserBean;
/**
 * The Interface PdtmenuService.
 * 
 * @module 系统管理
 * @func 货品分类
 * @version 1.1
 * @author 刘曰刚
 */
public interface PdtmenuService{
	 /**
     * 查询并分页.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    public void pageQuery(Map <String, Object> params, PageDesc pageDesc);
    /**
     * 编辑：新增
     * Description :.
     * 
     * @param Zone_Id the XZQH id
     * @param XZQHModel the XZQH model
     * @param userBean the user bean
     * 
     * @return the string
     */
    public String txEdit(String PRD_ID, PdtmenuModel PdtmenuModel, UserBean userBean);
    /**
     * 修改状态为停用
     * Description :.
     * 
     * @param params the params
     * 
     * @return true, if tx stop by id
     */
    public boolean txStopById(Map <String, String> params);


    /**
     * 修改状态为启用
     * Description :.
     * 
     * @param params the params
     * 
     * @return true, if tx start by id
     */
    public boolean txStartById(Map <String, String> params);
    /**
     * 删除记录
     * Description :.
     * 
     * @param Zone_Id the XZQH id
     * @param userBean the user bean
     * 
     * @return true, if tx delete
     */
    public boolean txDelete(Map <String, String> params);
    
    /**
     * 详细信息.
     * 
     * @param param the param
     * 
     * @return the map< string, string>
     */
    public Map <String, String> load(String param);
	 /**
     * 显示树.
     * 
     * @return the tree
     * 
     * @throws Exception the exception
     */
    public List <PdtmenuTree> getTree() throws Exception;
    /**
     * 获取所有品牌名称
     * @return
     */
    public List<String> getBrand();
    /**
     * 判断编号是否重复
     * Gets the count PRD_NO.
     * 
     * @param PRD_NO the PRD_NO
     * 
     * @return the count PRD_NO
     */
    public int getCountPRD_NO(String PRD_NO);
}
