/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：QDXXService.java
 */
package com.centit.erp.sale.depositinfo.service;


import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.erp.sale.depositinfo.model.DepositInfoModel;
import com.centit.sys.model.UserBean;

/**
 * 充值信息
 * @author liu_yg
 *
 */
public interface DepositInfoService {
	
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
    public DepositInfoModel loadById(String param);
    
    /**
     * 更新记录
     * 
     * @param params the params
     * 
     * @return true, if tx update by id
     */
    public void txUpdateById(Map <String, String> params);

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
    public void txEdit(String brandId, DepositInfoModel model, UserBean userBean);

	/**
     * 删除记录
     * 
     * @return true, if tx delete
     */
    public void delete(String depositId, UserBean userBean);
    
    /**
     * 审核
     * @param map
     */
    public void audit(Map<String,String> map);

}
