/**
 * 
 */
package com.centit.base.syslog.service;

import java.util.Map;

import com.centit.core.po.PageDesc;

/**
 * 
 * @module 系统日志
 * @author gu_hongxiu
 *
 */
public interface SyslogService {
	
	/**
     * 主表分页查询
     * Description :.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    public void pageQuery(Map <String, String> params, PageDesc pageDesc);
    
    
    /**
     * 详细信息.
     * 
     * @param param the param
     * 
     * @return the map< string, string>
     */
    public Map<String,String> load(String param);
    
    public boolean insertDataRecycle(Map<String,String> map);
    
    public boolean doActLog(String UC_NAME,String ACT_NAME,String OPRATOR,String STATE,String REMARK,String APPCODE,String APPID,String OPRCODE,String KEY,String optContent);

}
