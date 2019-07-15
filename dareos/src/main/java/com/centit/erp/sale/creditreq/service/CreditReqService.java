/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：QDXXService.java
 */
package com.centit.erp.sale.creditreq.service;


import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.erp.sale.creditreq.model.CreditReqModel;
import com.centit.sys.model.UserBean;

/**
 * 充值信息
 * @author liu_yg
 *
 */
public interface CreditReqService {
	
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
    public CreditReqModel loadById(String param);
    
    /**
     * 更新记录
     * 
     * @param params the params
     * 
     * @return true, if tx update by id
     */
    public void txUpdateById(CreditReqModel model);

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
    public void txEdit(String brandId, CreditReqModel model, UserBean userBean);

	/**
     * 删除记录
     * 
     * @return true, if tx delete
     */
    public void delete(String depositId, UserBean userBean);
    
    /**
     * 审核
     * @param map
     * @throws ParseException 
     */
    public void audit(CreditReqModel model) throws ParseException;
    
}
