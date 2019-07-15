/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：QDXXService.java
 */
package com.centit.base.channcapital.service;


import java.util.List;
import java.util.Map;

import com.centit.base.channbill.model.ChannLedgerChrgModel;
import com.centit.core.po.PageDesc;

/**
 * 充值信息
 * @author liu_yg
 *
 */
public interface ChannCapitalService {
	
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
     */
    public List<ChannLedgerChrgModel> pageFreezQuery(Map<String, Object> params);
    /**
     * @param param the param
     * 
     * @return the map
     */
    public ChannLedgerChrgModel loadById(String param);
    
}
