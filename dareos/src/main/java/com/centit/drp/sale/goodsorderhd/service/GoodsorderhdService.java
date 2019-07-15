/**
 * prjName:喜临门营销平台
 * ucName:订货订单维护
 * fileName:GoodsorderhdService
 */
package com.centit.drp.sale.goodsorderhd.service;
import java.util.List;
import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.drp.sale.goodsorderhd.model.GoodsorderhdModel;
import com.centit.drp.sale.goodsorderhd.model.GoodsorderhdModelChld;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.sys.model.UserBean;
/**
 * *@service
 * *@func
 * *@version 1.1
 * *@author zzb
 * *@createdate 2013-09-15 10:35:10
 */
public interface GoodsorderhdService  {
	/**
	 * @查询
	 * @param params map
	 * @param pageNo
	 *
	 * @return the i list page
	 */
	public void pageQuery(Map<String,Object> params,PageDesc pageDesc);

	/**
	 * @主从保存编辑
	 * @Description :
	 * @param GOODS_ORDER_ID
	 * @param GoodsorderhdModel
	 * @param userBean.
	 *
	 * @return
	 */
	public String txEdit(String GOODS_ORDER_ID, GoodsorderhdModel obj,List<GoodsorderhdModelChld> chldList,
			UserBean userBean,String REBATEDSCT,String erjiOrderId, String order);

	/**
	 * 保存并提交
	 * @param GOODS_ORDER_ID
	 * @param obj
	 * @param chldList
	 * @param userBean
	 * @param REBATEDSCT
	 * @param erjiOrderId
	 * @param order
	 * @return
	 */
	public String txEditToCommit(String GOODS_ORDER_ID, GoodsorderhdModel obj,List<GoodsorderhdModelChld> chldList,
			UserBean userBean,String REBATEDSCT,String erjiOrderId, String order);

	/**
	 * @主表删除
	 * @param Map
	 * @param userBean
	 * @return true, if tx delete
	 */
	public boolean txDelete(Map <String, String> params, UserBean userBean);

	/**
	 * @主表详细页面
	 * @param param GOODS_ORDER_ID
	 *
	 * @return the map< string, string>
	 */
	public Map<String,Object> load(String GOODS_ORDER_ID);

	/**
	 * * 根据主表Id查询子表记录
	 * @param GOODS_ORDER_ID the GOODS_ORDER_ID
	 * @return the list
	 */
	public List <Map<String, String>> childQuery(String GOODS_ORDER_ID);

	/**
	 * * 根据子表Id批量加载子表信息
	 * @param GOODS_ORDER_DTL_IDs the GOODS_ORDER_DTL_IDs
	 *
	 * @return the list
	 */
	public List <GoodsorderhdModelChld> loadChilds(Map <String, String> params) ;

	/**
	 * 子 保存
	 * @param GOODS_ORDER_ID 订单ID
	 * @param modelList 明细list
	 * @param ORDER_RECV_DATE 交期
	 * @param params 参数
	 * @return
	 */
	public boolean txChildEdit(String GOODS_ORDER_ID, List <GoodsorderhdModelChld> modelList,
			UserBean userBean,String ORDER_RECV_DATE,Map<String,String> params,String action);
	/**
	 * * 子表的批量删除
	 * @param GOODS_ORDER_DTL_IDs the GOODS_ORDER_DTL_IDs
	 */
	public void txBatch4DeleteChild(String GOODS_ORDER_DTL_IDs);

	/**
	 * 加载渠道
	 * @param param CHANN_ID
	 * @return the map< string, string>
	 */
	public Map<String, String> getChann(String CHANN_ID);

	/**
	 * 查询货品的 折扣率
	 * @param AREA_ID 区域ID
	 * @param PRD_ID 货品ID
	 * @return
	 */
	public Map<String,String> getRateByAreaIdPId(String AREA_ID,String PRD_ID);

	/**
	 * 根据货品id查询明细的总金额和总价格
	 * @param GOODS_ORDER_ID
	 */
	public Map<String,String> queryTotal(String GOODS_ORDER_ID);
	/**
	 * 提交
	 * @param selRowId 订货订单ID
	 * @param BILL_TYPE 单据类型
	 * @param GOODS_ORDER_NO 订货订单NO
	 * @param flowServiceId 流程ID
	 * @param userBean
	 */
	@Deprecated
	public void txCommit(String selRowId,String BILL_TYPE,String GOODS_ORDER_NO,String flowServiceId, UserBean userBean)throws Exception;
	/**
	 * 提交
	 * @param selRowId 订货订单ID
	 * @param model {@link GoodsorderhdModel}
	 * @param userBean {@link UserBean}
	 */
	public void txCommit(String selRowId,GoodsorderhdModel model, UserBean userBean)throws Exception;


	/**
	 * 撤销
	 * @param selRowId 订货订单ID
	 * @param GOODS_ORDER_NO 订货订单NO
	 * @param flowServiceId 流程ID
	 * @param userBean
	 */
	public void revoke(String selRowId, String GOODS_ORDER_NO, String flowServiceId, UserBean userBean);

	/**
	 * 下级要货处理-退回
	 * @param selRowId 订货订单ID
	 * @param GOODS_ORDER_NO 订货订单NO
	 * @param RETURN_RSON 退回原因
	 * @param flowServiceId 流程ID
	 * @param userBean {@link UserBean}
	 */
	public void back(String selRowId, String GOODS_ORDER_NO, String RETURN_RSON, String flowServiceId, UserBean userBean);

	/**
	 * 下级订货处理-转总部订货订单
	 * @param selRowId 订货订单ID
	 * @param GOODS_ORDER_NO 订货订单NO
	 * @param flowServiceId 流程ID
	 * @param userBean {@link UserBean}
	 */
	public void turnToHQ(String selRowId, String GOODS_ORDER_NO, String flowServiceId, UserBean userBean);



	/**
	 * 根据单据主键获取该单据附件地址
	 * @param pkId
	 * @return
	 */
	public String getAttPath(String pkId);
	/**
	 *
	 * 订货处理-审图报价
	 * @param selRowId 订货订单ID
	 * @param model {@link GoodsorderhdModel}
	 * @param chldList {@link List<GoodsorderhdModelChld>}
	 * @param userBean {@link UserBean}
	 * @param option {审图-audit,报价-quote}
	 * @throws Exception
	 */
	public void txAudit(String selRowId, GoodsorderhdModel model,
			List<GoodsorderhdModelChld> chldList, UserBean userBean,
			String option);

	/**
	 * 订货处理-确认报价
	 * @param selRowId 订货订单ID
	 * @param model {@link GoodsorderhdModel}
	 * @param option [confirmquote:经销商确认报价，更新订单交期为确认日期往后推40天]
	 * @param userBean {@link UserBean}
	 * @throws Exception
	 */
	public void txConfirm(String selRowId, GoodsorderhdModel model, String option, UserBean userBean);

	/**
	 * 订货处理-退回
	 * @param selRowId 订货订单ID
	 * @param GOODS_ORDER_NO 订货订单NO
	 * @param RETURN_RSON 退回原因
	 * @param flowServiceId 流程ID
	 * @param userBean {@link UserBean}
	 */
	@Deprecated
	public void txBack(String selRowId, String GOODS_ORDER_NO, String RETURN_RSON, String flowServiceId, UserBean userBean);
	/**
	 * 订货处理-退回
	 * @param selRowId 订货订单ID
	 * @param model {@link GoodsorderhdModel}
	 * @param userBean {@link UserBean}
	 */
	public void txBack(String selRowId, GoodsorderhdModel model, UserBean userBean);

	/**
	 * 订货处理-生成销售订单
	 * @param GOODS_ORDER_ID 订货订单ID
	 * @param model {@link GoodsorderhdModel}
	 * @param xsddModel {@link SaleorderModel}
	 * @param parentModelList {@link List<SaleorderModel>}
	 * @param userBean {@link UserBean}
	 * @throws Exception
	 */
	public void txCrexsdd(String GOODS_ORDER_ID, GoodsorderhdModel model, SaleorderModel xsddModel, List<SaleorderModel> parentModelList, UserBean userBean);

	/**
	 * 根据当前登录人所属渠道获取渠道折扣率
	 */
	public String getChannDiscount(String CHANN_ID);
	/**
	 * 查询预订单明细
	 * @return
	 */
	public List<Map<String,Object>>toQuertAvdcDtl(Map<String,String>params);

	/**
	 * 查询 锁住此行
	 * @param GOODS_ORDER_ID
	 * @return
	 */
	public Map<String,Object> txLoadForUpdate(String GOODS_ORDER_ID);

	void txUpdateById(Map<String,String> params);

	/**
	 * 销售订单退回到预订单
	 * @param saleOrderId
	 * @param userBean
	 */
	public void saleBackGoods(String goodsOrderId,UserBean userBean);

}