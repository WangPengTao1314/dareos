/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：QDXXService.java
 */
package com.centit.base.brand.service;


import java.util.Map;

import com.centit.base.brand.model.BrandModel;

import com.centit.core.po.PageDesc;
import com.centit.sys.model.UserBean;

/**
 * @module 系统管理
 * @func 品牌信息
 * @version 1.0
 */
public interface BrandService {
	
	/**
     * 查询并分页.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    public void pageQuery(Map<String, Object> params, PageDesc pageDesc);
    /**
     * @param param the param
     * 
     * @return the map
     */
    public Map<String,Object> loadById(String param);
    
    /**
     * 更新记录
     * 
     * @param params the params
     * 
     * @return true, if tx update by id
     */
    public void txUpdateById(Map <String, Object> params);

    /**
     * 编辑：新增/修改
     * Description :.
     * 
     * @param chann_id the chan id
     * @param ChannpunishModel the chann model
     * @param userBean the user bean
     * 
     * @return the string
     */
    public void txEdit(String brandId, BrandModel model, UserBean userBean);

	/**
     * 删除记录
     * 
     * @return true, if tx delete
     */
    public void delete(String brandId, UserBean userBean);
    

}
