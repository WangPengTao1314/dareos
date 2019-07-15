/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：QDXXService.java
 */
package com.centit.base.channamount.service;


import java.util.Map;

import com.centit.base.channamount.model.MonthAcctNoteModel;
import com.centit.core.po.PageDesc;

/**
 * 充值信息
 * @author liu_yg
 *
 */
public interface ChannAmountService {
	
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
    public MonthAcctNoteModel loadById(String monthAcctNoteId);
    
}
