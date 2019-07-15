/**
 * prjName:喜临门营销平台
 * ucName:标准订单
 * fileName:ChangeApplyService
*/
package com.centit.erp.sale.saleorder.service;
import java.util.List;
import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.erp.sale.saleorder.model.ChangeApplyModel;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.erp.sale.saleorder.model.SaleorderModelChld;
import com.centit.sys.model.UserBean;

/**
 *
 * @ClassName: ChangeApplyService
 * @Description: 销售订单变更申请service接口
 * @author wang_dw
 * @date 2019年3月14日 下午4:47:29
 *
 */
public interface ChangeApplyService {
	/**
	 * @查询
	 * @param params map
	 * @param pageNo
	 *
	 * @return the i list page
	 */
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc);

	/**
	 * @主表详细页面
	 * @param CHANGE_APPLY_ID
	 *
	 * @return the map< string, Object>
	 */
	public Map<String, Object> load(String CHANGE_APPLY_ID);

	/**
	 * 编辑：新增/删除
	 * Description :.
	 *
	 * @param CHANGE_APPLY_ID the CHANGE_APPLY id
	 * @param obj {@link ChangeApplyModel}
	 * @param userBean the user bean
	 *
	 * @return the string
	 */
	public String txEdit(String CHANGE_APPLY_ID, ChangeApplyModel obj, UserBean userBean);

	/**
	 * 提交
	 * Description :.
	 *
	 * @param CHANGE_APPLY_ID the CHANGE_APPLY id
	 * @param model {@link ChangeApplyModel}
	 * @param userBean the user bean
	 *
	 * @return the string
	 */
	public String txCommit(String CHANGE_APPLY_ID, ChangeApplyModel model, UserBean userBean) throws Exception;

	/**
	 * 变更申请审核
	 * @param CHANGE_APPLY_ID
	 * @param SALE_ORDER_ID
	 * @param changeApplyModel {@link ChangeApplyModel}
	 * @param obj {@link SaleorderModel}
	 * @param chldList {@link List<SaleorderModelChld>}
	 * @param userBean {@link UserBean}
	 *
	 * @return
	 */
	public void txChange(String CHANGE_APPLY_ID, String SALE_ORDER_ID, ChangeApplyModel changeApplyModel, SaleorderModel obj,List<SaleorderModelChld> chldList, UserBean userBean)throws Exception;

	/**
	 * 销售订单变更-审图报价
	 * @param selRowId 订货订单ID
	 * @param model {@link SaleorderModel}
	 * @param chldList {@link List<SaleorderModelChld>}
	 * @param userBean {@link UserBean}
	 * @param option {审图-audit,报价-quote}
	 * @throws Exception
	 */
	public void txAudit(String selRowId, SaleorderModel model,
			List<SaleorderModelChld> chldList, UserBean userBean,
			String option) throws Exception;

	/**
	 * 销售订单变更-确认报价
	 * @param selRowId 订货订单ID
	 * @param model {@link SaleorderModel}
	 * @param option [confirmquote:经销商确认报价，更新订单交期为确认日期往后推40天]
	 * @param userBean {@link UserBean}
	 * @throws Exception
	 */
	public void txConfirm(String selRowId, SaleorderModel model, String option, UserBean userBean) throws Exception;

	/**
	 * 销售订单变更-退回指定节点
	 * @param selRowId 订货订单ID
	 * @param no 销售订单NO
	 * @param RETURN_RSON 退回原因
	 * @param flowServiceId 流程ID
	 * @param userBean {@link UserBean}
	 */
	public void txBack(String selRowId, String no, String RETURN_RSON, String flowServiceId, UserBean userBean);

}