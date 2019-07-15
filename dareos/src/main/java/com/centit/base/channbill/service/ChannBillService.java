/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：QDXXService.java
 */
package com.centit.base.channbill.service;


import java.util.List;
import java.util.Map;

import com.centit.common.po.BookkeepingModel;
import com.centit.core.po.PageDesc;
import com.centit.erp.sale.creditreq.model.CreditReqModel;
import com.centit.sys.model.UserBean;

/**
 * 充值信息
 * @author liu_yg
 *
 */
public interface ChannBillService {
	
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
     * 查询冻结分页
     * @param params
     * @param pageDesc
     */
    public void pageFreezQuery(Map<String, Object> params, PageDesc pageDesc);
    /**
     * @param param the param
     * 
     * @return the map
     */
    public BookkeepingModel loadById(String param);
    
}
