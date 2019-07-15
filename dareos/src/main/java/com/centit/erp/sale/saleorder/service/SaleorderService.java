/**
 * prjName:喜临门营销平台
 * ucName:标准订单
 * fileName:SaleorderService
*/
package com.centit.erp.sale.saleorder.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Workbook;

import com.centit.core.po.PageDesc;
import com.centit.erp.sale.saleorder.model.SaleDesignerModel;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.erp.sale.saleorder.model.SaleorderModelChld;
import com.centit.sys.model.UserBean;

/**
 *
 * @ClassName: SaleorderService
 * @Description: 销售订单service接口
 * @author wang_dw
 * @date 2019年3月14日 下午4:47:29
 *
 */
public interface SaleorderService {
	/**
	 * @查询
	 * @param params map
	 * @param pageNo
	 *
	 * @return the i list page
	 */
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc);

	/**
	 * @主从保存编辑
	 * @Description :
	 * @param SALE_ORDER_ID
	 * @param SaleorderModel
	 * @param userBean.
	 *
	 * @return
	 */
	public String txEdit(String SALE_ORDER_ID, SaleorderModel obj,List<SaleorderModelChld> chldList, UserBean userBean);

	/**
	 * 保存并提交
	 * @Description :
	 * @param SALE_ORDER_ID
	 * @param SaleorderModel
	 * @param userBean.
	 *
	 * @return
	 */
	public String txEditToCommit(String SALE_ORDER_ID, SaleorderModel obj,List<SaleorderModelChld> chldList, UserBean userBean);
	/**
	 * @主表删除
	 * @param Map
	 *
	 * @return true, if tx delete
	 */
	public void txDelete(Map<String, String> params);

	/**
	 * @主表详细页面
	 * @param param SALE_ORDER_ID
	 *
	 * @return the map< string, Object>
	 */
	public Map<String, Object> load(String SALE_ORDER_ID);

	 /**
     * * 根据主表Id查询子表记录
     * @param SALE_ORDER_ID the SALE_ORDER_ID
     * @return the list
     */
    public List <Map <String, String>> childQuery(String SALE_ORDER_ID);

    /**
     * * 根据子表Id批量加载子表信息
     * @param SALE_ORDER_DTL_IDs the SALE_ORDER_DTL_IDs
     *
     * @return the list
     */
    public List <Map <String, String>> loadChilds(Map <String, String> params) ;

	 /**
     * * 子表记录编辑：新增/修改
     * @param SALE_ORDER_ID the SALE_ORDER_ID
     * @param modelList the model list
     * @param userBean {@link UserBean}
     *
     * @return true, if tx child edit
     */
    public boolean txChildEdit(String SALE_ORDER_ID, List <SaleorderModelChld> modelList, UserBean userBean, String actionType,String BILL_TYPE);
	 /**
     * * 子表的批量删除
     * @param SALE_ORDER_DTL_IDs the SALE_ORDER_DTL_IDs
     */
    public void txBatch4DeleteChild(String SALE_ORDER_DTL_IDs);

    /**
     * 提交
     * @param SALE_ORDER_ID 销售订单ID
     */
	public void txCommit(String SALE_ORDER_ID, SaleorderModel model, UserBean userBean);

	public String getDECT_RATE(String CHANN_ID);

	public List<?> qrySaleOrderExp(Map<String,String> map);

	/**
	 * 销售订单处理-退回到草稿
	 * @param selRowId 销售订单ID
	 * @param model {@link SaleorderModel}
	 * @param userBean {@link UserBean}
	 */
	public void back4gc(String SALE_ORDER_ID, SaleorderModel model, UserBean userBean);

	/**
	 * 销售订单处理-退回到上一节点
	 * @param selRowId 销售订单ID
	 * @param model {@link SaleorderModel}
	 * @param userBean {@link UserBean}
	 * @param TableName
	 */
	public void txBack(String SALE_ORDER_ID, SaleorderModel model, UserBean userBean);

	/**
	 * 销售订单处理-退回到上一节点
	 * @param selRowId 销售订单ID
	 * @param model {@link SaleorderModel}
	 * @param userBean {@link UserBean}
	 * @param TableName
	 */
	public void txBack(String SALE_ORDER_ID, SaleorderModel model, UserBean userBean, String TableName);

	/**
	 * 销售订单处理-生产退回到待处理
	 * @param selRowId 销售订单ID
	 * @param model {@link SaleorderModel}
	 * @param userBean {@link UserBean}
	 */
	public void txERPBack(String SALE_ORDER_ID, SaleorderModel model, UserBean userBean);

	/**
	 *
	 * 销售订单处理-转设计师
	 * @param selRowId 销售订单ID
	 * @param no 销售订单NO
	 * @param flowServiceId 流程ID
	 * @param state 流程状态
	 * @param userBean {@link UserBean}
	 */
	@Deprecated
	public void turnDesigner(String selRowId, String no, String flowServiceId, String state, UserBean userBean);
	/**
	 * 销售订单处理-转设计师
	 * @param selRowId 销售订单ID
	 * @param model {@link SaleorderModel}
	 * @param userBean {@link UserBean}
	 */
	public void turnDesigner(String SALE_ORDER_ID, SaleorderModel model, UserBean userBean);
	/**
	 *
	 * 销售订单处理-转ERP
	 * @param selRowId 销售订单ID
	 * @param no 销售订单NO
	 * @param flowServiceId 流程ID
	 * @param userBean {@link UserBean}
	 */
	@Deprecated
	public void turnERP(String selRowId, String no, String flowServiceId, UserBean userBean);
	/**
	 * 销售订单处理-转ERP
	 * @param selRowId 销售订单ID
	 * @param model {@link SaleorderModel}
	 */
	public void turnERP(String SALE_ORDER_ID, SaleorderModel model, UserBean userBean);


	/**
	 * 根据销售订单id查询分派设计师
	 * @param SALE_ORDER_ID
	 * @return
	 */
	public Map<String, Object> loadAssignByFkid(String SALE_ORDER_ID);
	/**
	 * 根据主键查询分派设计师
	 * @param DESIGNER_ID
	 * @return
	 */
	public Map<String, Object> loadAssignById(String DESIGNER_ID);
	/**
	 * 查询设计师分派情况
	 * @return the list
	 */
	public List<Map<String, Object>> assignQueryTotal(Map<String, String> params);
	public List<Map<String, Object>> assignQuery(Map<String, String> params);

	/**
	 * 保存设计师分派
	 * @param DESIGNER_ID
	 * @param SaleDesignerModel
	 * @param userBean.
	 *
	 * @return
	 */
	public String assignEdit(String DESIGNER_ID, SaleDesignerModel model, SaleorderModel obj, UserBean userBean);

	/**
	 * 拆件计料表保存
	 * @Description :
	 * @param SALE_ORDER_ID
	 * @param SaleDesignerModel
	 * @param SaleorderModel
	 * @param userBean.
	 * @return
	 */
	public String txDesign(String SALE_ORDER_ID, SaleDesignerModel model, SaleorderModel obj, UserBean userBean);

	/**
	 * 销售订单处理-收货确认
	 * @param selRowId 销售订单ID
	 * @param model {@link SaleorderModel}
	 * @param userBean {@link UserBean}
	 */
	public void txConfirm(String SALE_ORDER_ID, SaleorderModel model, UserBean userBean);

	/**
	 * 根据销售订单id查询变更申请
	 * @param SALE_ORDER_ID
	 * @return
	 */
	public Map<String, Object> loadApplyByFkid(String SALE_ORDER_ID);
	/**
	 * 根据主键查询变更申请
	 * @param CHANGE_APPLY_ID
	 * @return
	 */
	public Map<String, Object> loadApplyById(String CHANGE_APPLY_ID);

	public void txUpdateById(Map<String, String> params);

	/**
	 * 根据模板导入Excel
	 * @param workBook
	 * @param sheetModel
	 * @param userBean
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String txParseExcelToDb(Workbook workBook, List sheetModel, UserBean userBean, HttpServletRequest request) ;

	public List<Map<String,String>> toSendFindSale(String saleOrderId);
}