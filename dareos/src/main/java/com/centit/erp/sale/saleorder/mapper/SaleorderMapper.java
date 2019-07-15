package com.centit.erp.sale.saleorder.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.erp.sale.saleorder.model.SaleorderModelChld;
import com.centit.sys.model.UserBean;

/**
 *
 * @ClassName: SaleorderMapper
 * @Description: 销售订单Mapper
 * @author wang_dw
 * @date 2019年3月14日 下午2:15:46
 *
 */
@Repository
public interface SaleorderMapper {
	/**
	 *
	 * @Title: pageQuery
	 * @Description: 销售订单查询
	 * @author wang_dw
	 * @date 2019年3月14日 下午2:17:08
	 * @param @param pageQureyMap
	 * @param @return
	 * @return List<SaleorderModel>
	 * @throws
	 */
	public List<SaleorderModel>  pageQuery(Map<String, Object> pageQureyMap);

	public int pageCount(Map<String, Object> pageQureyMap);

	public void insert(Map<String, String> params);

	/**
	 * @主从保存编辑
	 * @Description :
	 * @param SALE_ORDER_ID
	 * @param SaleorderModel
	 * @param userBean.
	 *
	 * @return
	 */
	public void txEdit(String SALE_ORDER_ID, SaleorderModel obj,List<SaleorderModelChld> chldList, UserBean userBean);

	/**
	 * @主表删除
	 * @param Map
	 *
	 * @return true, if tx delete
	 */
	public void delete(Map <String, String> params);

	public void delChldByFkId(Map <String, String> params);

	public void updateById(Map<String,String> params);

	public Map<String,String> queryChannWarId(Map<String,String> param);

	public void updateChldById(List <Map <String, String>> list);

	public void insertChld(List <Map <String, String>> list);

	public List <Map <String, String>> queryChldByFkId(Map<String,String> param);

	public List <Map <String, String>> loadChldByIds(Map<String,String> param);

	/**
	 * 根据id查询子表信息
	 * @param id
	 * @return
	 */
	public Map<String,Object> queryDtlByid(String id);

	public void deleteChldByIds(Map<String,String> params);

	public void updateChldByIds(Map<String,String> params);

	public int queryMaxRowNo(Map<String,String> params);

	public String getDECT_RATE(Map<String,String> map);

	public void updateGoodsOrderDtl_SaleId(Map<String,String> params);

	/**
	 * @主表详细页面
	 * @param param SALE_ORDER_ID
	 *
	 * @return the map< string, Object>
	 */
	public Map<String,Object> loadById(Map<String,String> param);

	 /**
     * * 根据主表Id查询子表记录
     * @param SALE_ORDER_ID the SALE_ORDER_ID
     * @return the list
     */
    public List <SaleorderModelChld> childQuery(String SALE_ORDER_ID);

    /**
     * * 根据子表Id批量加载子表信息
     * @param SALE_ORDER_DTL_IDs the SALE_ORDER_DTL_IDs
     *
     * @return the list
     */
    public List <SaleorderModelChld> loadChilds(Map <String, String> params) ;

	 /**
     * * 子表记录编辑：新增/修改
     * @param SALE_ORDER_ID the SALE_ORDER_ID
     * @param modelList the model list
     *
     * @return true, if tx child edit
     */
    public boolean txChildEdit(String SALE_ORDER_ID, List <SaleorderModelChld> modelList,String actionType,String BILL_TYPE);

    /**
     * * 子表的批量删除
     * @param SALE_ORDER_DTL_IDs the SALE_ORDER_DTL_IDs
     */
    public void txBatch4DeleteChild(String SALE_ORDER_DTL_IDs);

    /**
     * 转非标订单
     * @param SALE_ORDER_ID 销售订单ID
     * @param SALE_ORDER_DTL_IDS  明细IDS
     * @param isAll true->全部
     */
    public void txConvertTechOrder(String SALE_ORDER_ID,String SALE_ORDER_DTL_IDS,boolean isAll,UserBean userBean);

    /**
     * 取消预定
     * @param SALE_ORDER_ID 订单ID
     * @param SALE_ORDER_DTL_IDS 明细IDs
     * @param FROM_BILL_DTL_IDS 来源单据IDS
     */
    public void txCalcelSaleOrder(String SALE_ORDER_ID, String SALE_ORDER_DTL_IDS,String FROM_BILL_DTL_IDS,UserBean userBean)throws Exception;

    /**
     * 恢复预定
     * @param SALE_ORDER_ID 销售订单ID
     * @param SALE_ORDER_DTL_IDS  明细IDS
     * @param FROM_BILL_DTL_IDS 来源单据IDS
     */
    public void txRecoverOrder(String SALE_ORDER_ID,String SALE_ORDER_DTL_IDS,String FROM_BILL_DTL_IDS,UserBean userBean)throws Exception;

    /**
     * 提交
     * @param SALE_ORDER_ID 销售订单ID
     */
    public void txToCommit(String SALE_ORDER_ID ,String SALE_ORDER_NO,UserBean userBean);


    /**
     * * 根据主表Id查询生命周期子表记录
     * @param SALE_ORDER_ID the SALE_ORDER_ID
     * @return the list
     */
	public List <Map<String,String>> queryTrace(String SALE_ORDER_ID);

	/**
	 * @订货订单查询
	 * @param params
	 *            map
	 * @param pageNo
	 *
	 * @return the i list page
	 */
//	public IListPage goodsPageQuery(Map<String, String> params, int pageNo);

	/**
	 * * 根据订货订单主表Id查询子表记录
	 *
	 * @param GOODS_ORDER_ID
	 *            the GOODS_ORDER_ID
	 * @return the list
	 */
//	public List<SaleorderviewChldModel> goodsChildQuery(String GOODS_ORDER_ID);

	public String getDECT_RATE(String CHANN_ID);

	public List<Map <String, Object>> qrySaleOrderExp(Map<String,String> map);


	/**
	 * 根id据查询分派设计师
	 * @param DESIGNER_ID或者SALE_ORDER_ID
	 * @return
	 */
	public Map<String,Object> loadAssignById(Map<String,String> param);
	/**
	 * 查询设计师分派情况
	 * @return the list
	 */
	public List<Map <String, Object>> assignQueryTotal(Map<String,String> param);
	public List<Map <String, Object>> assignQuery(Map<String,String> param);
	public void insertDesigner(Map<String, String> params);
	public void updateDesignerById(Map<String,String> params);
	public void deleteDesignerById(String DESIGNER_ID);


	/**
	 * 查询销售订单及变更申请
	 * @return
	 */
	public Map<String,Object> loadApplyByFkid(Map<String,String> param);

	/**
	 *
	 * @Title: getOrderSendDif
	 * @Description: 获取一个订单下订货和已发货数量差异值
	 * @author lv_f
	 * @date 2019年5月22日 下午2:29:42
	 * @param @param param
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getSaleOrderSendDif(Map<String,String> param);

	/**
	 * 根据销售订单获取可发数量的发货指令
	 * @param saleOrderId
	 * @return
	 */
	public List<Map<String,String>> toSendFindSale(String saleOrderId);
	
}
