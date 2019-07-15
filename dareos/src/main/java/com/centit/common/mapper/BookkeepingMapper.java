package com.centit.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.common.po.BookkeepingModel;
import com.centit.drp.main.sales.deliver.order.model.OrderModel;
import com.centit.drp.sale.goodsorderhd.model.GoodsorderhdModel;
import com.centit.erp.sale.creditreq.model.CreditReqModel;
import com.centit.erp.sale.depositinfo.model.DepositInfoModel;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.erp.sale.store.model.ErpStoreOut;
@Repository
public interface BookkeepingMapper {

	/**
	 * 根据充值单ID获取充值单信息
	 * @param depositId
	 * @return
	 */
	DepositInfoModel getDepoInfo(String depositId);
	
	/**
	 * 新增流水信息
	 * @param model
	 */
	void insertBookkeeping(BookkeepingModel model);
	
	/**
	 * 批量新增流水信息
	 * @param list
	 */
	void batchInsertBookkeeping(List<BookkeepingModel> list);
	
	/**
	 * 获取渠道帐套金额信息
	 * @param model
	 * @return
	 */
	Map<String,Object> getChannInfo(BookkeepingModel model);
	
	/**
	 * 更新渠道总账
	 * @param channInfo
	 */
	void updChannInfo(Map<String,Object> channInfo);
	
	/**
	 * 根据信用ID获取信用信息
	 * @param creditReqId
	 * @return
	 */
	CreditReqModel getCreditReq(String creditReqId);
	
	/**
	 * 根据渠道、帐套信息获取当前欠扣信用信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getOweSale(Map<String,Object> map);
	
	/**
	 * 根据销售订单ID获取销售订单信息
	 * @param saleOrderId
	 * @return
	 */
	SaleorderModel getSaleOrder(String saleOrderId);
	
	/**
	 * 根据销售订单厂编获取销售订单信息
	 * @param factoryNo
	 * @return
	 */
	SaleorderModel getSaleOrderByFactoryNo(String factoryNo);
	
	/**
	 * 根据销售订单ID获取还未释放的额度信息
	 * @param BookkeepingModel
	 * @return
	 */
	BookkeepingModel getFreezInfo(Map<String,String> map);
	
	/**
	 * 根据业务ID删除流水
	 * @param saleOrderId
	 */
	void delBookkeepingByBillId(String billId);
	
	/**
	 * 根据发货指令ID获取发货指令信息
	 * @param sendOrderId
	 * @return
	 */
	OrderModel getSendOrder(String sendOrderId);
	
	/**
	 * 根据要货单ID获取要货单信息
	 * @param goodsOrderId
	 * @return
	 */
	GoodsorderhdModel getGoodsOrder(String goodsOrderId);
	/**
	 * 获取出库单信息
	 * @param storeOutId
	 * @return
	 */
	ErpStoreOut getStoreOut(String storeOutId);
	
	/**
	 * 获取昨日到期临时信用
	 * @return
	 */
	List<CreditReqModel> getYesterdayExpireCredit();
	
	/**
	 * 获取今日生效的临时信用
	 * @return
	 */
	List<String> getTodayEffectCredit();
	
	/**
	 * 修改信用标记
	 * @param map
	 */
	void updCreditFlag(Map<String,String> map);
	
	List<Map<String,String>> getCheckBookkeeping();
	
	//初始化经销商
	/**
	 * 获取初始化渠道金额
	 * @return
	 */
	List<Map<String,String>> getInitChann();
	
	void insertDepositInfo(DepositInfoModel d);
	
	/**
	 * 根据业务ID校验是否重复添加
	 * @param billId
	 * @return
	 */
	int checkDepositAndCreditRepeat(String billId);
	
	//==================刷新数据用===========================
	/**
	 * 获取所有有流水的经销商分管信息
	 * @return
	 */
	public List<BookkeepingModel> getAllChann();
	
	/**
	 * 根据渠道ID和帐套ID获取流水信息
	 * @param channId
	 * @param ledgerId
	 * @return
	 */
	public List<BookkeepingModel> getBKGByChannIdAndLedgerId(@Param("channId")String channId,@Param("ledgerId")String ledgerId);
	
	public void updAllBookkeeping(List<BookkeepingModel> list);
}
