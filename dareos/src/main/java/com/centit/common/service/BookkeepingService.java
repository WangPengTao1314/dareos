package com.centit.common.service;

import java.util.List;

import com.centit.common.po.BookkeepingModel;

public interface BookkeepingService {

	/**
	 * 记录充值信息流水
	 * @param depositId 充值信息ID
	 * @param optionPerson 操作人用户ID  userBean.getXTYHID()
	 * @return
	 */
	public boolean rechargeAccount(String depositId,String optionPerson);
	
	/**
	 * 记录临时信用流水
	 * @param creditReqId
	 * @param optionPerson 操作人用户ID  userBean.getXTYHID()
	 * @return
	 */
	public boolean creditAccount(String creditReqId,String optionPerson);
	
	/**
	 * 更新失效临时信用，反填渠道总信用
	 * @param cModel
	 * @return
	 */
	public boolean updExpireCredit();
	
	/**
	 * 更新生效临时信用，反填渠道总信用
	 * @param cModel
	 * @return
	 */
	public boolean updEffectCredit();
	
	/**
	 * 校验可用金额、可用信用是否够下单
	 * @param saleOrderId 必传
	 * @return
	 */
	public boolean checkFreezSaleAmount(String saleOrderId);
	
	/**
	 * 校验实际金额是否够下单，如果不够的话 临时信用是否够下单
	 * 
	 * 校验金额时的几个返回值  (作废)
	 * 实际金额：可用的现钱   可用金额：可用的现钱+可用的信用
	 * 1：实际金额足够
	 * 2：实际金额不足，可用金额足够
	 * 3：实际金额不足，可用金额不足
	 * @param saleOrderId
	 */
//	public String checkSaleAmount(String saleOrderId);
	
	
	/**
	 * 冻结金额/返利/信用
	 * @param saleOrderId 销售订单ID
	 * @param optionPerson 操作人用户ID  userBean.getXTYHID()
	 */
	public void saleOrderFrozenAmount(String saleOrderId,String optionPerson,String type);
	
	/**
	 * 根据出库单扣减金额/返利/信用
	 * @param storeOutId
	 */
	public void storeOutAmount(String storeOutId);
	
	
	/**
	 * 根据销售订单ID 获取销售订单的流水记录，获取里面的冻结信息，释放冻结
	 * @param saleOrderId 销售订单ID
	 * @param optionPerson 操作人
	 * @return 返回施放额度的流水，如果是扣减额度时需要调用
	 */
	public BookkeepingModel releaseSaleFreezBookkeeping(String saleOrderId,String optionPerson);
	
	
	/**
	 * 删除销售订单时调用，施放额度并删除流水
	 * @param saleOrderId
	 */
	public void delBookkeeping(String saleOrderId);
	
	/**
	 * 返修单直接扣减额度
	 * @param saleOrderId
	 */
	public void reworkAmount(String saleOrderId);
	
	/**
	 * 校验发货通知单额度
	 * @param sendOrderId
	 */
	public String checkSendFreezAmount(String sendOrderId);
	
	/**
	 * 确认发货后，查看把单据的冻结信用挪到冻结金额内
	 * @param sendOrderId
	 */
	public void changeSaleFreezAmount(String sendOrderId);
	
	/**
	 * 要货单转销售订单时，释放要货单冻结金额，冻结销售订单金额
	 * @param saleOrderIds
	 * @param goodsOrderId
	 * @param optionPerson
	 */
	public void goodsToSaleOrderFrozenAmount(List<String> saleOrderIds,String goodsOrderId,String optionPerson);
	
	
	/**
	 * 根据要货单ID冻结信息
	 * @param goodsOrderId
	 * @param optionPerson
	 */
	public void goodsOrderFrozenAmount(String goodsOrderId, String optionPerson);
	
	/**
	 * 根据要货单ID释放冻结
	 * @param goodsOrderId
	 */
	public void releaseGoodsFreezBookkeeping(String goodsOrderId);
	
	/**
	 * NC出库单删除时，返回扣账、冻结、发货数量
	 * @param storeOutId
	 * @param optionPerson
	 */
	public void backStoreOut(String storeOutId,String optionPerson);
}
