package com.centit.sys.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.centit.core.po.PageDesc;
import com.centit.sys.model.MenuInfo;



/**
 * The Class MenuService.
 * 
 * @module 系统模块
 * @fuc 系统模块
 * @version 1.1
 */
public interface MenuService {

    /**
     * 查询并分页.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
	public void  pageQuery(Map<String, Object> pageQureyMap,PageDesc pageDesc);



    /**
     * Delete.
     * 
     * @param paramsID the params id
     * @param userBean the user bean
     * 
     * @return true, if successful
     */
    public void delete(String paramsID);


    /**
     * Load.
     * 
     * @param params the params
     * 
     * @return the map
     */
    public Map<String, Object> load(Map<String, Object> params);

	public void modify(MenuInfo model);


	public void insert(MenuInfo model);



}
