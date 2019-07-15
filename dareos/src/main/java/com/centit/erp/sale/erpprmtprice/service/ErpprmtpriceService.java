/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：QDXXService.java
 */
package com.centit.erp.sale.erpprmtprice.service;

import java.util.List;
import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.erp.sale.erpprmtprice.model.ErpprmtpriceModel;
import com.centit.sys.model.UserBean;
/**
 * The Interface channService.
 * 
 * @module 系统管理
 * @func 渠道信息
 * @version 1.0
 * @author 刘曰刚
 */
public interface ErpprmtpriceService  {
	
	/**
     * 查询并分页.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    public void pageQuery(Map<String, Object> params, PageDesc pageDesc);
    public void txInsertPrice(List<ErpprmtpriceModel> ModelList,String PRMT_PLAN_ID,UserBean userBean);
    public void txUpdateState(String PRMT_PRICE_IDS,String STATE,UserBean userBean);
}
