package com.centit.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.centit.common.mapper.BookkeepingMapper;
import com.centit.common.po.BookkeepingModel;
import com.centit.common.service.BookkeepingService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.sales.deliver.order.model.OrderModel;
import com.centit.drp.sale.goodsorderhd.model.GoodsorderhdModel;
import com.centit.erp.sale.creditreq.model.CreditReqModel;
import com.centit.erp.sale.depositinfo.model.DepositInfoModel;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.erp.sale.store.model.ErpStoreOut;

@Service
public class BookkeepingServiceImpl implements BookkeepingService{
	
	@Autowired
	BookkeepingMapper mapper;
	
	/**
	 * 记录充值信息流水
	 * @param depositId 充值信息ID
	 * @param optionPerson 操作人用户ID  userBean.getXTYHID()
	 * @return
	 */
	@Transactional
	public boolean rechargeAccount(String depositId,String optionPerson){
		//根据充值ID获取充值单据信息
		DepositInfoModel depositInfo = mapper.getDepoInfo(depositId);
		if(depositInfo==null){
			return true;
		}
		//获取充值单信息后把充值信息金额插入流水表中
		BookkeepingModel model = new BookkeepingModel();
		model.setBookkeepingId(StringUtil.uuid32len());
		model.setBillId(depositId);
		model.setBillNo(depositInfo.getDepositNo());
		model.setBillType(depositInfo.getDepositType());
		model.setBookkeepingType(BusinessConsts.BOOKKEEPING_TYPE_DEPOSIT);
		model.setDirection(BusinessConsts.YJLBJ_FLAG_FALSE);
		model.setOptionPerson(optionPerson);
		model.setLedgerId(depositInfo.getLedgerId());
		model.setLedgerName(depositInfo.getLedgerName());
		model.setChannId(depositInfo.getChannId());
		model.setChannNo(depositInfo.getChannNo());
		model.setChannName(depositInfo.getChannName());
		//查询渠道帐套分管表并锁表
		Map<String,Object> channInfo = getChannInfo(model);
		// 判断充值类型是金额还是返利,如果充值类型是返利，则金额存流水返利内，不然就存流水金额内
		if(depositInfo.getDepositType().equals(BusinessConsts.REBATE_TYPE)){
			//设置流水金额
			model.setRebateTotal(depositInfo.getDepositAmount());
			//汇总渠道帐套金额
			channInfo.put("amountRebate",LogicUtil.add(depositInfo.getDepositAmount(), channInfo.get("amountRebate")));
		}else{
			model.setAmountTotal(depositInfo.getDepositAmount());
			channInfo.put("amountMoney",add(depositInfo.getDepositAmount(), channInfo.get("amountMoney")));
		}
		optAmount(model, channInfo);
		
		deductionAccount(channInfo, optionPerson,depositInfo.getDepositType());
		
		//校验该充值单是否生成多条账，如果生成，抛出异常，回退
		int con =mapper.checkDepositAndCreditRepeat(depositId);
		if(con>1){
			throw new ServiceException("充值单："+depositInfo.getDepositNo()+"已入账，请刷新页面，不要重复审核！");
		}
		return true;
	}
	
	//相加
    public static double add(Object d1,Object d2){
        return LogicUtil.add(d1,d2);

    }
    //相减
    public static double sub(Object d1,Object d2){
    	return LogicUtil.sub(d1,d2);
    }
    
    //相乘
    public static double multiply(Object d1,Object d2){
    	return LogicUtil.multiply(d1,d2);
    }
	
	/**
	 * 记录临时信用流水
	 * @param creditReqId
	 * @param optionPerson
	 * @return
	 */
	@Transactional
	public boolean creditAccount(String creditReqId,String optionPerson){
		//根据临时信用ID获取临时信用信息
		CreditReqModel creditReq = mapper.getCreditReq(creditReqId);
		//获取临时信用信息后把临时信用信息信用插入流水表中
		BookkeepingModel model = new BookkeepingModel();
		model.setBookkeepingId(StringUtil.uuid32len());
		model.setBillId(creditReqId);
		model.setBillNo(creditReq.getCreditReqNo());
		model.setBillType(BusinessConsts.CREDIT_TYPE_APPLY);
		model.setDirection(BusinessConsts.YJLBJ_FLAG_FALSE);
		model.setBookkeepingType(BusinessConsts.BOOKKEEPING_TYPE_CREDIT);
		model.setOptionPerson(optionPerson);
		model.setLedgerId(creditReq.getLedgerId());
		model.setLedgerName(creditReq.getLedgerName());
		model.setChannId(creditReq.getChannId());
		model.setChannNo(creditReq.getChannNo());
		model.setChannName(creditReq.getChannName());
		model.setCreditTotal(creditReq.getCreditTotal());
		//查询渠道帐套分管表并锁表
		Map<String,Object> channInfo = getChannInfo(model);
		//汇总渠道帐套信用
		channInfo.put("amuntCredit",add(creditReq.getCreditTotal(), channInfo.get("amuntCredit")));
		optAmount(model, channInfo);
		//校验该充值单是否生成多条账，如果生成，抛出异常，回退
		int con =mapper.checkDepositAndCreditRepeat(creditReqId);
		if(con>1){
			throw new ServiceException("信用申请单："+creditReq.getCreditReqNo()+"已入账，请刷新页面，不要重复审核！");
		}
		return true;
	}
	
	/**
	 * 抵充到期的临时信用，更新渠道帐套总信用额度
	 */
	@Transactional
	public boolean updExpireCredit(){
		List<CreditReqModel> list =mapper.getYesterdayExpireCredit();
		if(list==null||list.isEmpty()){
			return true;
		}
		String creditReqId ="";
		for (int i = 0; i < list.size(); i++) {
			CreditReqModel cModel = list.get(i);
			creditReqId += "'"+cModel.getCreditReqId() +"',";
			BookkeepingModel model = new BookkeepingModel();
			model.setBookkeepingId(StringUtil.uuid32len());
			model.setBillId(cModel.getCreditReqId());
			model.setBillNo(cModel.getCreditReqNo());
			model.setBillType(BusinessConsts.CREDIT_TYPE_EXPIRE);
			model.setBookkeepingType(BusinessConsts.BOOKKEEPING_TYPE_CREDIT);
			model.setDirection(BusinessConsts.YJLBJ_FLAG_TRUE);
			model.setCreditTotal(cModel.getCreditTotal());
			model.setChannId(cModel.getChannId());
			model.setChannNo(cModel.getChannNo());
			model.setChannName(cModel.getChannName());
			model.setLedgerId(cModel.getLedgerId());
			model.setLedgerName(cModel.getLedgerName());
			Map<String,Object> channMap = getChannInfo(model);
			channMap.put("amuntCredit", sub(channMap.get("amuntCredit"), cModel.getCreditTotal()));
			optAmount(model, channMap);
		}
		creditReqId=creditReqId.substring(0,creditReqId.length()-1);
		Map<String,String> map = new HashMap<String, String>();
		map.put("creditReqId", creditReqId);
		map.put("processFlag", BusinessConsts.YJLBJ_FLAG_TRUE);
		mapper.updCreditFlag(map);
		return true;
	}
	/**
	 * 更新今日生效信用
	 */
	@Transactional
	public boolean  updEffectCredit(){
		List<String> list =mapper.getTodayEffectCredit();
		if(list==null||list.isEmpty()){
			return true;
		}
		String creditReqId ="";
		for (int i = 0; i < list.size(); i++) {
			creditReqId += "'"+ list.get(i) +"',";
			creditAccount(list.get(i), "");
		}
		creditReqId=creditReqId.substring(0,creditReqId.length()-1);
		Map<String,String> map = new HashMap<String, String>();
		map.put("creditReqId", creditReqId);
		map.put("processFlag", BusinessConsts.YJLBJ_FLAG_FALSE);
		mapper.updCreditFlag(map);
		return true;
	}
	
	/**
	 * 根据渠道ID、帐套ID获取的扣减信用信息，抵扣金额
	 * @param channInfo
	 * @param optionPerson
	 * @param depositType 充值类型
	 */
	@Transactional
	private void deductionAccount(Map<String,Object> channInfo,String optionPerson,String depositType){
		
		//根据总金额-冻结金额 获取可用金额 
		
		double money=sub(channInfo.get("amountMoney"), channInfo.get("freezMoney"));
		//获取欠扣信用
		double oweCredit = LogicUtil.getDouble(channInfo.get("oweCredit"));
		//判断欠扣信用信息是否大于0，如果大于0的话，根据渠道和帐套信息获取欠扣的流水信息
		if(oweCredit>0&&BusinessConsts.DEPOSITINFO_TYPE.equals(depositType)	){
			channInfo.put("oweType", BusinessConsts.AMOUNT_TYPE_OWE_CREDIT);//欠扣信用
			channInfo.put("repayType", BusinessConsts.AMOUNT_TYPE_REPAY_CREDIT);//金额抵欠扣信用
			List<Map<String,Object>> list = mapper.getOweSale(channInfo);
			for (int i = 0; i < list.size(); i++) {
				BookkeepingModel model = new BookkeepingModel();
				double credit = LogicUtil.getDouble(list.get(i).get("money"));//本条销售订单欠扣信用
				//判断欠扣信用是否大于可用金额，如果大于，可用金额清0，增加一条可用金额额度的抵欠扣信用记录
				if(money<credit){
					initBookkingBean(channInfo, model, optionPerson);
					model.setBillId(list.get(i).get("billId").toString());
					model.setBillNo(list.get(i).get("billNo").toString());
					model.setBookkeepingType(BusinessConsts.AMOUNT_TYPE_REPAY_CREDIT);
					model.setDirection(BusinessConsts.YJLBJ_FLAG_TRUE);
					model.setAmountTotal(money+"");
					
					channInfo.put("amountMoney", sub(channInfo.get("amountMoney"), money));
					channInfo.put("oweCredit", sub(channInfo.get("oweCredit"), money));
					optAmount(model, channInfo);
					break;
				}else{
					initBookkingBean(channInfo, model, optionPerson);
					model.setBillId(list.get(i).get("billId").toString());
					model.setBillNo(list.get(i).get("billNo").toString());
					model.setBookkeepingType(BusinessConsts.AMOUNT_TYPE_REPAY_CREDIT);
					model.setDirection(BusinessConsts.YJLBJ_FLAG_TRUE);
					model.setAmountTotal(credit+"");
					
					channInfo.put("amountMoney", sub(channInfo.get("amountMoney"), credit));
					channInfo.put("oweCredit", sub(channInfo.get("oweCredit"), credit));
					optAmount(model, channInfo);
					money = sub(money,credit);
				}
			}
		}
	}
	
	
	
	/**
	 * 校验可用金额、可用信用是否够下单
	 * @param saleOrderId 必传
	 * @param channInfo 传空，可返回渠道帐套对象
	 * @return
	 */
	public boolean checkFreezSaleAmount(String saleOrderId){
		SaleorderModel saleOrder = mapper.getSaleOrder(saleOrderId);
		//获取销售订单信息
		BookkeepingModel model = new BookkeepingModel();
		model.setChannId(saleOrder.getCHANN_ID());
		model.setLedgerId(saleOrder.getLEDGER_ID());
		//查询渠道帐套分管表并锁表
		Map<String,Object> channInfo = getChannInfo(model);
		initBookkingBean(channInfo, model, "");
		//获取可用额度  总金额-冻结金额+总信用-冻结信用-欠扣信用
		
		double money = sub(sub(add(sub(channInfo.get("amountMoney"), channInfo.get("freezMoney")),channInfo.get("amuntCredit")),channInfo.get("freezCredit")),channInfo.get("oweCredit"));
//				objToDouble()-//总金额
//				objToDouble()+//冻结金额
//				objToDouble()-//总信用
//				objToDouble()-//冻结信用
//				objToDouble();//欠扣信用
		//获取可用返利 总返利-冻结返利
		double rebate = sub(channInfo.get("amountRebate"), channInfo.get("freezRebate"));
		
		//判断订货金额是否大于可用金额
		if(money<LogicUtil.getDouble(saleOrder.getTOTAL_AMOUNT())){
			throw new ServiceException("经销商："+channInfo.get("channName")+"账户可用余额不足，不能下单");
		}
		//如果使用返利的话 判断返利是否足够
		if(rebate<LogicUtil.getDouble(saleOrder.getTOTAL_REBATE())){
			throw new ServiceException("经销商："+channInfo.get("channName")+"账户可用返利不足，不能下单");
		}
		return true;
	}
	
	
	/**
	 * 校验实际金额是否够下单，如果不够的话 临时信用是否够下单(作废)
	 * 
	 * 校验金额时的几个返回值  
	 * 实际金额：可用的现钱   可用金额：可用的现钱+可用的信用
	 * 1：实际金额足够
	 * 2：实际金额不足，可用金额足够
	 * 3：实际金额不足，可用金额不足
	 * @param saleOrderId
	 */
	/**
	public String checkSaleAmount(String saleOrderId){
		//获取销售订单信息
		SaleorderModel saleOrder = mapper.getSaleOrder(saleOrderId);
		BookkeepingModel model = new BookkeepingModel();
		model.setChannId(saleOrder.getCHANN_ID());
		model.setChannNo(saleOrder.getCHANN_NO());
		model.setChannName(saleOrder.getCHANN_NAME());
		model.setLedgerId(saleOrder.getLEDGER_ID());
		model.setLedgerName(saleOrder.getLEDGER_NAME());
		//查询渠道帐套分管表并锁表
		Map<String,Object> channInfo = getChannInfo(model);
		
		//获取实际金额  总金额-冻结金额
		double money = objToDouble(channInfo.get("amountMoney"))-//总金额
				objToDouble(channInfo.get("freezMoney"));//冻结金额
		
		//获取可用返利 总返利-冻结返利
		double rebate = objToDouble(channInfo.get("amountRebate"))-
				objToDouble(channInfo.get("freezRebate"));
		
		//如果使用返利的话 判断返利是否足够，如果返利不足直接返回异常
		if(rebate<LogicUtil.getDouble(saleOrder.getTOTAL_REBATE())){
			throw new ServiceException("账户可用返利不足，不能下单");
		}
		
		//判断订货金额是否大于实际金额，如果大于实际金额，再判断订货金额是否大于可用金额
		if(money<LogicUtil.getDouble(saleOrder.getTOTAL_AMOUNT())){
			//可用额度
			money = money+
					objToDouble(channInfo.get("amuntCredit"))-//总信用
					objToDouble(channInfo.get("freezCredit"));//冻结信用
			//判断可用金额是否足够
			if(money<LogicUtil.getDouble(saleOrder.getTOTAL_AMOUNT())){
				return BusinessConsts.CHECK_SALE_AMOUNT_2;
			}else{
				return BusinessConsts.CHECK_SALE_AMOUNT_3;
			}
		}else{
			return BusinessConsts.CHECK_SALE_AMOUNT_1;
		}
	}
	*/
	
	/**
	 * 根据要货单ID冻结信息
	 * @param goodsOrderId
	 * @param optionPerson
	 */
	@Transactional
	public void goodsOrderFrozenAmount(String goodsOrderId, String optionPerson){
		GoodsorderhdModel goodsOrder = mapper.getGoodsOrder(goodsOrderId);
		//根据要货单的经销商ID和帐套ID 获取渠道信息
		BookkeepingModel model = new BookkeepingModel();
		model.setChannId(goodsOrder.getCHANN_ID());
		model.setLedgerId(goodsOrder.getLEDGER_ID());
		Map<String,Object> channInfo = getChannInfo(model);
		initBookkingBean(channInfo, model, optionPerson);
		
		//获取可用额度  总金额-冻结金额+总信用-冻结信用-欠扣信用
		
		double money = sub(sub(add(sub(channInfo.get("amountMoney"), channInfo.get("freezMoney")),channInfo.get("amuntCredit")),channInfo.get("freezCredit")),channInfo.get("oweCredit"));
//				objToDouble()-//总金额
//				objToDouble()+//冻结金额
//				objToDouble()-//总信用
//				objToDouble()-//冻结信用
//				objToDouble();//欠扣信用
		//获取可用返利 总返利-冻结返利
		
		double rebate = sub(channInfo.get("amountRebate"), channInfo.get("freezRebate"));
		
		//判断订货金额是否大于可用金额
		if(money<LogicUtil.getDouble(goodsOrder.getTOTAL_AMOUNT())){
			throw new ServiceException("经销商："+channInfo.get("channName")+"账户可用余额不足，不能下单");
		}
		//如果使用返利的话 判断返利是否足够
		if(rebate<LogicUtil.getDouble(goodsOrder.getTOTAL_REBATE())){
			throw new ServiceException("经销商："+channInfo.get("channName")+"账户可用返利不足，不能下单");
		}
		model.setBookkeepingId(StringUtil.uuid32len());
		model.setBillId(goodsOrderId);
		model.setBillNo(goodsOrder.getGOODS_ORDER_NO());
		model.setBillType(BusinessConsts.AMOUNT_TYPE_FREEZ);
		model.setDirection(BusinessConsts.YJLBJ_FLAG_TRUE);
		model.setBookkeepingType(BusinessConsts.BOOKKEEPING_TYPE_GOODS);
		double freezAmount = LogicUtil.getDouble(goodsOrder.getTOTAL_AMOUNT());// 冻结金额 1500.00
		// 判断如果冻结金额大于可用金额时，冻结信用，不然直接冻结金额
		
		double residualAmount = sub(channInfo.get("amountMoney"), channInfo.get("freezMoney"));// 可用金额 0
		if(freezAmount>residualAmount){
			model.setAmountTotal(residualAmount+"");//冻结金额
			model.setCreditTotal(sub(freezAmount, residualAmount) +"");//冻结信用
			channInfo.put("freezMoney",add(channInfo.get("freezMoney"), residualAmount));
			channInfo.put("freezCredit", add(channInfo.get("freezCredit"), sub(freezAmount, residualAmount)));
		}else{
			model.setAmountTotal(goodsOrder.getTOTAL_AMOUNT());//冻结金额
			// 更新经销商冻结金额
			channInfo.put("freezMoney", add(channInfo.get("freezMoney"), goodsOrder.getTOTAL_AMOUNT()));
		}
		if(!"0".equals(goodsOrder.getTOTAL_REBATE())){
			model.setRebateTotal(goodsOrder.getTOTAL_REBATE());
			channInfo.put("freezRebate",add(channInfo.get("freezRebate"), goodsOrder.getTOTAL_REBATE()));
		}
		optAmount(model, channInfo);
	}
	
	/**
	 * 要货单转销售订单时，释放要货单冻结金额，冻结销售订单金额
	 * @param saleOrderIds
	 * @param goodsOrderId
	 * @param optionPerson
	 */
	@Transactional
	public void goodsToSaleOrderFrozenAmount(List<String> saleOrderIds,String goodsOrderId,String optionPerson){
		releaseGoodsFreezBookkeeping(goodsOrderId);
		//循环创建销售订单冻结信息
		for (int i = 0; i < saleOrderIds.size(); i++) {
			saleOrderFrozenAmount(saleOrderIds.get(i), optionPerson, "add");
		}
	}
	
	
	/**
	 * 根据要货单ID释放冻结
	 * @param goodsOrderId
	 */
	@Transactional
	public void releaseGoodsFreezBookkeeping(String goodsOrderId){
		GoodsorderhdModel goodsOrder = mapper.getGoodsOrder(goodsOrderId);
		//先释放要货单冻结金额
		Map<String,String> map = new HashMap<String, String>();
		map.put("billId", goodsOrderId);
		map.put("freezType", BusinessConsts.AMOUNT_TYPE_FREEZ);
		map.put("releaseType", BusinessConsts.AMOUNT_TYPE_RELEASE);
		BookkeepingModel model = mapper.getFreezInfo(map);//要货单冻结信息
		Map<String,Object> channInfo = getChannInfo(model);
		// 根据要货单冻结信息先释放渠道里的冻结额度
		channInfo.put("freezMoney", sub(channInfo.get("freezMoney"), model.getAmountTotal()));//冻结金额
		channInfo.put("freezRebate", sub(channInfo.get("freezRebate"), model.getRebateTotal()));// 冻结返利
		channInfo.put("freezCredit",  sub(channInfo.get("freezCredit"), model.getCreditTotal()));//冻结信用
		model.setBookkeepingId(StringUtil.uuid32len());
		model.setBillId(goodsOrderId);
		model.setBillNo(goodsOrder.getGOODS_ORDER_NO());
		model.setBillType(BusinessConsts.AMOUNT_TYPE_RELEASE);
		model.setDirection(BusinessConsts.YJLBJ_FLAG_FALSE);
		model.setBookkeepingType(BusinessConsts.BOOKKEEPING_TYPE_GOODS);
		optAmount(model, channInfo);
	}
	
	
	/**
	 * 销售订单冻结金额/返利/信用
	 * @param saleOrderId
	 * @param optionPerson
	 * @param type add:新增 upd:修改  判断新增还是修改，如果是新增则直接新增流水，如果是修改则删除原流水再增新流水
	 */
	@Transactional
	public void saleOrderFrozenAmount(String saleOrderId,String optionPerson,String type){
		if("upd".equals(type)){
			//释放冻结
			releaseSaleFreezBookkeeping(saleOrderId, optionPerson);
			//根据销售订单ID删除所有流水
//			mapper.delBookkeepingByBillId(saleOrderId);
		}
		//获取销售订单信息
		SaleorderModel saleOrder = mapper.getSaleOrder(saleOrderId);
		BookkeepingModel model = new BookkeepingModel();
		model.setChannId(saleOrder.getCHANN_ID());
		model.setLedgerId(saleOrder.getLEDGER_ID());
		//查询渠道帐套分管表并锁表
		Map<String,Object> channInfo = getChannInfo(model);
		// 如果可用信用够的话
		if(checkFreezSaleAmount(saleOrderId)){
			double totalAmount= LogicUtil.getDouble(saleOrder.getTOTAL_AMOUNT());//订货金额
			double totalRebate= LogicUtil.getDouble(saleOrder.getTOTAL_REBATE());//返利金额
			//优先冻结返利金额
			model = new BookkeepingModel();
			//如果有使用返利的话，记录返利使用流水
			if(totalRebate>0){
				model.setRebateTotal(totalRebate+"");
				channInfo.put("freezRebate", add(channInfo.get("freezRebate"), totalRebate));
				initBookkingBean(channInfo, model, optionPerson);
				saleFreezBookkeepingBean(model, saleOrder);
				optAmount(model, channInfo);
			}
			
			double money = sub(channInfo.get("amountMoney"), channInfo.get("freezMoney"));
//					objToDouble()-//总金额
//					objToDouble();//冻结金额
			//判断可用金额是否大于0，如果大于0的话，如果大于0的话，再判断订单金额是否大于可用金额，如果不大于0的话，直接冻结信用
			if(money>0){
				//判断订单金额是否大于可用金额
				if(totalAmount>money){//如果订单金额大于可用金额的话，冻结金额=总金额，冻结信用=总金额-原冻结金额
					model = new BookkeepingModel();
					double freezCredit = sub(totalAmount, money);
					initBookkingBean(channInfo, model, optionPerson);
					saleFreezBookkeepingBean(model, saleOrder);
					model.setAmountTotal(money+"");
					model.setCreditTotal(freezCredit+"");
					
					channInfo.put("freezMoney", add(channInfo.get("freezMoney"), money));
					channInfo.put("freezCredit",add(channInfo.get("freezCredit"), freezCredit));
					optAmount(model, channInfo);
				}else{ //如果订单金额小于可用金额，冻结金额=原冻结金额+订单金额
					model = new BookkeepingModel();
					initBookkingBean(channInfo, model, optionPerson);
					saleFreezBookkeepingBean(model, saleOrder);
					model.setAmountTotal(totalAmount+"");
					channInfo.put("freezMoney", add(channInfo.get("freezMoney"), totalAmount));
					optAmount(model, channInfo);
				}
			}else{//可用金额为0的时候直接冻结信用
				model = new BookkeepingModel();
				initBookkingBean(channInfo, model, optionPerson);
				saleFreezBookkeepingBean(model, saleOrder);
				model.setCreditTotal(totalAmount+"");
				channInfo.put("freezCredit", add(channInfo.get("freezCredit"), totalAmount));
				optAmount(model, channInfo);
			}
		}
	}
	
	
	/**
	 * 根据出库单扣减金额/返利/信用
	 * @param storeOutId
	 */
	@Transactional
	public void storeOutAmount(String storeOutId){
		String uuid = StringUtil.uuid32len();
		String optionPerson = "出库处理";
		ErpStoreOut storeOut = mapper.getStoreOut(storeOutId);
		InterUtil.actLog("出库处理扣账开始", optionPerson, "被调用", "成功", "storeOutId:"+storeOutId, "storeOutAmount", uuid, "", "", storeOut);
		//根据出库单获取发货指令信息
		OrderModel sendModel = mapper.getSendOrder(storeOut.getSend_order_id());
		// 施放冻结
		BookkeepingModel model = new BookkeepingModel();
		model.setBookkeepingId(StringUtil.uuid32len());
		model.setBillId(sendModel.getSale_order_id());
		model.setBillNo(sendModel.getFactory_no());
		model.setBillType(BusinessConsts.AMOUNT_TYPE_RELEASE);
		model.setDirection(BusinessConsts.YJLBJ_FLAG_FALSE);
		model.setBookkeepingType(BusinessConsts.BOOKKEEPING_TYPE_SALE);
		model.setOptionPerson(optionPerson);
		model.setLedgerId(sendModel.getLedger_id());
		model.setLedgerName(sendModel.getLedger_name());
		model.setChannId(sendModel.getChann_id());
		model.setChannNo(sendModel.getChann_no());
		model.setChannName(sendModel.getChann_name());
		
		Map<String,Object> channInfo = getChannInfo(model);
		model.setRebateTotal(storeOut.getTotal_rebate());
		channInfo.put("freezRebate", sub(channInfo.get("freezRebate"), storeOut.getTotal_rebate()));
		// 获取销售订单的冻结信息
		Map<String,String> map = new HashMap<String, String>();
		map.put("billId", sendModel.getSale_order_id());
		map.put("freezType", BusinessConsts.AMOUNT_TYPE_FREEZ);
		map.put("releaseType", BusinessConsts.AMOUNT_TYPE_RELEASE);
		BookkeepingModel saleBookkeepingModel =mapper.getFreezInfo(map);
		//判断销售订单是否有冻结信用，如果有冻结信用，优先释放冻结信用
		double saleFreezCredit  = LogicUtil.getDouble(saleBookkeepingModel.getCreditTotal());
		double outMoney = LogicUtil.getDouble(storeOut.getTotal_amount());// 出库金额
		// 默认金额0，信用0
		model.setAmountMoney("0");
		model.setCreditTotal("0");
		if(saleFreezCredit==0){// 如果没有冻结信用，直接释放金额
			model.setAmountTotal(outMoney+"");
			channInfo.put("freezMoney", sub(channInfo.get("freezMoney"), outMoney));
		}else if(saleFreezCredit>=outMoney){ //如果有冻结信用，并且冻结信用大于出库金额，直接释放信用
			model.setCreditTotal(outMoney+"");
			channInfo.put("freezCredit", sub(channInfo.get("freezCredit"), outMoney));
		}else if(saleFreezCredit<outMoney){// 如果冻结信用小于出库金额时，释放完冻结信用，余额释放冻结金额
			//释放信用
			model.setCreditTotal(saleFreezCredit+"");
			channInfo.put("freezCredit", sub(channInfo.get("freezCredit"), saleFreezCredit));
			//释放金额
			model.setAmountTotal(sub(outMoney, saleFreezCredit)+"");
			
			channInfo.put("freezMoney", sub(channInfo.get("freezMoney"), sub(outMoney, saleFreezCredit)));
		}
		optAmount(model, channInfo);
		
		// 新增的扣减金额流水
		BookkeepingModel insertModel = new BookkeepingModel();
		initBookkingBean(channInfo, insertModel, optionPerson);
		insertModel.setBillId(storeOut.getStore_out_id());
		insertModel.setBillNo(storeOut.getStore_out_no());
		insertModel.setBillType(BusinessConsts.AMOUNT_TYPE_OWE_CREDIT);
		insertModel.setDirection(BusinessConsts.YJLBJ_FLAG_TRUE);
		insertModel.setBookkeepingType(BusinessConsts.BOOKKEEPING_TYPE_OUT);
		// 根据释放流水额度 扣减总额度
		// 渠道帐套信息里的额度，总额度（总金额、总返利、总信用）= 原总额度 - 释放额度 
		// 扣减金额=释放金额+释放信用
		double money = add(model.getAmountTotal(), model.getCreditTotal());
		// 可用金额=总金额-冻结金额
		double totMoney = sub(channInfo.get("amountMoney"), channInfo.get("freezMoney")) ;
		// 先判断可用金额是否等于0，如果等于0 直接把钱扣减到 欠扣金额内
		if(totMoney==0){
			channInfo.put("oweCredit", money);
			insertModel.setCreditTotal(money+"");
		}else if(totMoney<money){//如果可用扣减额度大于可用金额，总金额=冻结金额， 欠扣信用=扣减额度-(原总金额-冻结金额）
			Object amountMoney = channInfo.get("amountMoney");//原总金额
			double oweCredit = sub(money, sub(amountMoney, channInfo.get("freezMoney")));
			insertModel.setAmountTotal(sub(amountMoney, channInfo.get("freezMoney"))+"");
			insertModel.setCreditTotal(oweCredit+"");
			channInfo.put("amountMoney", channInfo.get("freezMoney"));
			channInfo.put("oweCredit", (add(channInfo.get("oweCredit"), oweCredit))+"");
		}else{//如果扣减额度<=可用额度，直接扣钱
			insertModel.setAmountTotal(money+"");
			channInfo.put("amountMoney", sub(channInfo.get("amountMoney"), money));
		}
		channInfo.put("amountRebate",sub(channInfo.get("amountRebate"), model.getRebateTotal()));//总返利
		optAmount(insertModel, channInfo);
		
		InterUtil.actLog("出库处理扣账结束", optionPerson, "被调用", "成功", "storeOutId:"+storeOutId, "storeOutAmount", uuid, "", "", channInfo);
	}
	
	
	
	/**
	 * 根据返修单直接扣减金额/返利/信用
	 * @param saleOrderId
	 */
	@Transactional
	public void reworkAmount(String saleOrderId){
		String optionPerson = "返修操作";
		SaleorderModel orderModel = mapper.getSaleOrder(saleOrderId);
		BookkeepingModel model = new BookkeepingModel();
		model.setLedgerId(orderModel.getLEDGER_ID());
		model.setChannId(orderModel.getCHANN_ID());
		Map<String,Object> channInfo = getChannInfo(model);
		// 新增的扣减金额流水
		initBookkingBean(channInfo, model, optionPerson);
		model.setBillId(orderModel.getSALE_ORDER_ID());
		model.setBillNo(orderModel.getFACTORY_NO());
		model.setBillType(BusinessConsts.AMOUNT_TYPE_OWE_CREDIT);
		model.setDirection(BusinessConsts.YJLBJ_FLAG_TRUE);
		model.setBookkeepingType(BusinessConsts.BOOKKEEPING_TYPE_REWORK);
		// 可用金额=总金额-冻结金额
		double totMoney = sub(channInfo.get("amountMoney"), channInfo.get("freezMoney")) ;
		double money = Double.parseDouble(orderModel.getTOTAL_AMOUNT());
		// 先判断可用金额是否等于0，如果等于0 直接把钱扣减到 欠扣金额内
		if(totMoney==0){
			channInfo.put("oweCredit", money);
			model.setCreditTotal(money+"");
		}else if(totMoney<money){//如果可用扣减额度大于可用金额，总金额=冻结金额， 欠扣信用=扣减额度-(原总金额-冻结金额）
			Object amountMoney = channInfo.get("amountMoney");//原总金额
			double oweCredit = sub(money, sub(amountMoney, channInfo.get("freezMoney")));
			model.setAmountTotal((sub(amountMoney, channInfo.get("freezMoney")))+"");
			model.setCreditTotal(oweCredit+"");
			channInfo.put("amountMoney", channInfo.get("freezMoney"));
			channInfo.put("oweCredit", add(channInfo.get("oweCredit"), oweCredit));
		}else{//如果扣减额度<=可用额度，直接扣钱
			model.setAmountTotal(money+"");
			channInfo.put("amountMoney", sub(channInfo.get("amountMoney"), money));
		}
		optAmount(model, channInfo);
	}
	
	/**
	 * 根据销售订单ID 获取销售订单的流水记录，获取里面的冻结信息，释放冻结
	 * @param saleOrderId 销售订单ID
	 * @param optionPerson 操作人
	 * @return 返回施放额度的流水，如果是扣减额度时需要调用
	 */
	@Transactional
	public BookkeepingModel releaseSaleFreezBookkeeping(String saleOrderId,String optionPerson){
		SaleorderModel saleModel = mapper.getSaleOrder(saleOrderId);
		Map<String,String> map = new HashMap<String, String>();
		map.put("billId", saleOrderId);
		map.put("freezType", BusinessConsts.AMOUNT_TYPE_FREEZ);
		map.put("releaseType", BusinessConsts.AMOUNT_TYPE_RELEASE);
		BookkeepingModel model = mapper.getFreezInfo(map);
		if(model==null){//没有冻结直接返回
			return null;
		}
		// 判断是否有未释放的金额、返利、信用
		if(	!"0".equals(model.getAmountTotal())||
				!"0".equals(model.getRebateTotal())||
				!"0".equals(model.getCreditTotal())	){
			Map<String,Object> channInfo = getChannInfo(model);
			// 初始化流水里的信息
			initBookkingBean(channInfo, model, optionPerson);
			model.setBillId(saleOrderId);
			model.setBillNo(saleModel.getFACTORY_NO());
			model.setBookkeepingType(BusinessConsts.BOOKKEEPING_TYPE_SALE);
			//有多少冻结未释放的额度，就释放多少
			model.setBillType(BusinessConsts.AMOUNT_TYPE_RELEASE);
			model.setDirection(BusinessConsts.YJLBJ_FLAG_FALSE);
			//  冻结额度=原冻结额度-释放额度
			channInfo.put("freezMoney", sub(channInfo.get("freezMoney"), model.getAmountTotal()));//冻结金额
			
			channInfo.put("freezRebate", sub(channInfo.get("freezRebate"), model.getRebateTotal()));//冻结返利
			
			channInfo.put("freezCredit", sub(channInfo.get("freezCredit"), model.getCreditTotal()));//冻结信用金额
			
			optAmount(model, channInfo);
		}
		return model;
	}
	
	
//	/**
//	 * 释放发货单冻结金额
//	 * @param sendModel
//	 * @param optionPerson
//	 * @return
//	 */
//	@Transactional
//	public BookkeepingModel releaseSendFreezBookkeeping(OrderModel sendModel,String optionPerson){
//		BookkeepingModel model =new BookkeepingModel();
//		model.setBookkeepingId(StringUtil.uuid32len());
//		model.setBillId(sendModel.getSend_order_id());
//		model.setBillNo(sendModel.getSend_order_no());
//		model.setBillType(BusinessConsts.AMOUNT_TYPE_RELEASE);
//		model.setDirection(BusinessConsts.YJLBJ_FLAG_FALSE);
//		model.setBookkeepingType(BusinessConsts.BOOKKEEPING_TYPE_SEND);
//		model.setOptionPerson(optionPerson);
//		model.setLedgerId(sendModel.getLedger_id());
//		model.setLedgerName(sendModel.getLedger_name());
//		model.setChannId(sendModel.getChann_id());
//		model.setChannNo(sendModel.getChann_no());
//		model.setChannName(sendModel.getChann_name());
//		Map<String,Object> channInfo = getChannInfo(model);
//		//获取销售订单的总冻结信息
//		Map<String,String> map = new HashMap<String, String>();
//		map.put("billId", sendModel.getSale_order_id());
//		map.put("freezType", BusinessConsts.AMOUNT_TYPE_FREEZ);
//		map.put("releaseType", BusinessConsts.AMOUNT_TYPE_RELEASE);
//		BookkeepingModel freezModel = mapper.getFreezInfo(map);
//		// 如果使用返利，发货指令使用多少就施放多少
//		model.setRebateTotal(sendModel.getTotal_rebate());
//		channInfo.put("freezRebate", objToDouble(channInfo.get("freezRebate"))-LogicUtil.getDouble(model.getRebateTotal()));//冻结返利
//		//施放总金额
//		double money = LogicUtil.getDouble(sendModel.getTotal_amount());
//		// 冻结的信用
//		double freezCredit = LogicUtil.getDouble(freezModel.getCreditTotal());
//		//优先施放冻结信用
//		if(freezCredit>0){
//			if(freezCredit<money){
//				model.setCreditTotal(freezCredit+"");
//				channInfo.put("freezCredit", objToDouble(channInfo.get("freezCredit"))-LogicUtil.getDouble(model.getCreditTotal()));//释放冻结信用
//				model.setAmountTotal((money-freezCredit)+"");
//				channInfo.put("freezMoney", objToDouble(channInfo.get("freezMoney"))-(money-freezCredit));//释放冻结金额
//			}else{
//				model.setCreditTotal(money+"");
//				channInfo.put("freezCredit", objToDouble(channInfo.get("freezCredit"))-money);//释放冻结信用
//			}
//		}else{
//			model.setAmountTotal(money+"");
//			channInfo.put("freezMoney", objToDouble(channInfo.get("freezMoney"))-money);//释放冻结金额
//		}
//		optAmount(model, channInfo);
//		return model;
//	}
	
	/**
	 * 校验发货通知单额度
	 * @param sendOrderId
	 */
	@Transactional
	public String checkSendFreezAmount(String sendOrderId){
		// 获取发货指令信息
		OrderModel sendOrder = mapper.getSendOrder(sendOrderId);
		// 获取销售订单
//		SaleorderModel saleOrder = mapper.getSaleOrder(sendOrder.getSale_order_id());
		//根据销售订单ID获取销售订单的所有冻结流水
		Map<String,String> map = new HashMap<String, String>();
		map.put("billId", sendOrder.getSale_order_id());
		map.put("freezType", BusinessConsts.AMOUNT_TYPE_FREEZ);
		map.put("releaseType", BusinessConsts.AMOUNT_TYPE_RELEASE);
		BookkeepingModel model = mapper.getFreezInfo(map);
		Map<String,Object> channInfo = getChannInfo(model);
		// 如果欠扣信用大于
		if(LogicUtil.getDouble(channInfo.get("amuntCredit"))<LogicUtil.getDouble(channInfo.get("oweCredit"))){
			throw new ServiceException("欠款金额已大于临时额度，不能发货");
		}
		
		// 判断是否有冻结信用
		double saleFreezCredit  = LogicUtil.getDouble(model.getCreditTotal());
		double totalAmount = LogicUtil.getDouble(sendOrder.getTotal_amount());
		//如果有冻结信用的话，判断冻结信用是否大于当前发货金额
		double optAmount = saleFreezCredit>totalAmount ? totalAmount : saleFreezCredit;
		double amountMoney = LogicUtil.getDouble(channInfo.get("amountMoney"));//总金额
		double freezMoney = LogicUtil.getDouble(channInfo.get("freezMoney"));//冻结金额
		if(saleFreezCredit==0 && amountMoney>freezMoney ){
			return BusinessConsts.CHECK_SALE_AMOUNT_1;
		}
		//获取可用返利 总返利-冻结返利
//		double rebate = objToDouble(channInfo.get("amountRebate"))-
//				objToDouble(channInfo.get("freezRebate"));
		
		//如果使用返利的话 判断返利是否足够，如果返利不足直接返回异常
//		if(rebate<LogicUtil.getDouble(sendOrder.getTotal_rebate())){
//			throw new ServiceException("账户可用返利不足，不能发货");
//		}
		// 判断如果冻结信用转移到冻结金额后，冻结金额大于总金额，则证明可用金额不足，如果小于，可以继续执行
		if(amountMoney<add(freezMoney, optAmount)){
			return BusinessConsts.CHECK_SALE_AMOUNT_2;
		}else{
			return BusinessConsts.CHECK_SALE_AMOUNT_1;
		}
	}
	
	/**
	 * 确认发货后，查看把单据的冻结信用挪到冻结金额内
	 * @param sendOrderId
	 */
	@Transactional
	public void changeSaleFreezAmount(String sendOrderId){
		// 获取发货指令信息
		OrderModel sendOrder = mapper.getSendOrder(sendOrderId);
		//根据销售订单ID获取销售订单的所有冻结流水
		Map<String,String> map = new HashMap<String, String>();
		map.put("billId", sendOrder.getSale_order_id());
		map.put("freezType", BusinessConsts.AMOUNT_TYPE_FREEZ);
		map.put("releaseType", BusinessConsts.AMOUNT_TYPE_RELEASE);
		BookkeepingModel model = mapper.getFreezInfo(map);
		//如果该销售订单没有冻结信用，不做操作
		if("0".equals(model.getCreditTotal())){
			return;
		}
		//如果有冻结信用的话，判断冻结信用是否大于当前发货金额
		double saleFreezCredit  = LogicUtil.getDouble(model.getCreditTotal());
		double totalAmount = LogicUtil.getDouble(sendOrder.getTotal_amount());
		double optAmount = saleFreezCredit>totalAmount ? totalAmount : saleFreezCredit;
		
		
		BookkeepingModel saleFreezModel = new BookkeepingModel();
		saleFreezModel.setChannId(sendOrder.getChann_id());
		saleFreezModel.setLedgerId(sendOrder.getLedger_id());
		Map<String,Object> channInfo = getChannInfo(saleFreezModel);
		// 先释放冻结信用 释放销售订单的冻结信用
		
		initBookkingBean(channInfo, saleFreezModel, "发货单冻结信用改为冻结金额");
		saleFreezModel.setBookkeepingId(StringUtil.uuid32len());
		saleFreezModel.setBillId(sendOrder.getSale_order_id());
		saleFreezModel.setBillId(sendOrder.getFactory_no());
		saleFreezModel.setBillType(BusinessConsts.AMOUNT_TYPE_RELEASE);
		saleFreezModel.setBookkeepingType(BusinessConsts.BOOKKEEPING_TYPE_SALE);
		saleFreezModel.setCreditTotal(optAmount+"");
		saleFreezModel.setDirection(BusinessConsts.YJLBJ_FLAG_FALSE);
		channInfo.put("freezCredit", sub(channInfo.get("freezCredit"), optAmount));
		optAmount(saleFreezModel, channInfo);
		
		// 再冻结金额 
		saleFreezModel.setBookkeepingId(StringUtil.uuid32len());
		saleFreezModel.setBillType(BusinessConsts.AMOUNT_TYPE_FREEZ);
		saleFreezModel.setDirection(BusinessConsts.YJLBJ_FLAG_TRUE);
		saleFreezModel.setAmountMoney(optAmount+"");
		channInfo.put("freezMoney", add(channInfo.get("freezMoney"), optAmount));
		optAmount(saleFreezModel, channInfo);
	}
	
	
	
	/**
	 *  反填渠道帐套信息和新增流水
	 * @param model
	 * @param channInfo
	 */
	@Transactional
	private void optAmount(BookkeepingModel model,Map<String,Object> channInfo){
		model.setAmountMoney(String.valueOf(channInfo.get("amountMoney")));
		model.setFreezMoney(String.valueOf(channInfo.get("freezMoney")));
		model.setAmountRebate(String.valueOf(channInfo.get("amountRebate")));
		model.setFreezRebate(String.valueOf(channInfo.get("freezRebate")));
		model.setAmuntCredit(String.valueOf(channInfo.get("amuntCredit")));
		model.setFreezCredit(String.valueOf(channInfo.get("freezCredit")));
		model.setOweCredit(String.valueOf(channInfo.get("oweCredit")));
		mapper.insertBookkeeping(model);
		mapper.updChannInfo(channInfo);
	}
	
	/**
	 * 删除销售订单时调用，施放额度并删除流水
	 * @param saleOrderId
	 */
	@Transactional
	public void delBookkeeping(String saleOrderId){
		//释放冻结
		releaseSaleFreezBookkeeping(saleOrderId, "");
		//根据销售订单ID删除所有流水
//		mapper.delBookkeepingByBillId(saleOrderId);
	}
	
	/**
	 * 冻结订单时初始化流水信息(先通过initBookkingBean初始化bean，再调用此方法)
	 * @param model
	 * @param saleOrder
	 */
	private void saleFreezBookkeepingBean(BookkeepingModel model,SaleorderModel saleOrder){
		model.setBillId(saleOrder.getSALE_ORDER_ID());
		model.setBillNo(saleOrder.getFACTORY_NO());
		model.setBillType(BusinessConsts.AMOUNT_TYPE_FREEZ);
		model.setDirection(BusinessConsts.YJLBJ_FLAG_TRUE);
		model.setBookkeepingType(BusinessConsts.BOOKKEEPING_TYPE_SALE);
	}
	
	/**
	 * 根据渠道信息初始化流水bean
	 * @param channInfo 渠道帐套信息
	 * @param model 初始化流水信息
	 * @param optionPerson 操作人
	 */
	private void initBookkingBean(Map<String,Object> channInfo,BookkeepingModel model,String optionPerson){
		model.setBookkeepingId(StringUtil.uuid32len());
		model.setOptionPerson(optionPerson);
		model.setLedgerId(channInfo.get("ledgerId").toString());
		model.setLedgerName(channInfo.get("ledgerNameAbbr").toString());
		model.setChannId(channInfo.get("channId").toString());
		model.setChannNo(channInfo.get("channNo").toString());
		model.setChannName(channInfo.get("channName").toString());
	}
	
	/**
	 * 根据流水信息里的渠道信息和帐套信息获取渠道帐套内的额度
	 * @param model
	 * @return
	 */
	private Map<String,Object> getChannInfo(BookkeepingModel model){
		System.out.println(JSONObject.toJSON(model));
		Map<String,Object> channInfo = mapper.getChannInfo(model);
		if(channInfo==null||channInfo.isEmpty()){
			throw new ServiceException("该经销商未找到相关帐套分管信息！");
		}
		return channInfo;
	}
	
	/**
	 * NC出库单删除时，返回扣账、冻结、发货数量
	 * @param storeOutId
	 * @param optionPerson
	 */
	public void backStoreOut(String storeOutId,String optionPerson){
		//获取出库单对象
		ErpStoreOut outModel = mapper.getStoreOut(storeOutId);
		SaleorderModel saleModel = mapper.getSaleOrderByFactoryNo(outModel.getSale_order_no());
		//重新冻结销售订单
		BookkeepingModel model = new BookkeepingModel();
		model.setLedgerId(saleModel.getLEDGER_ID());
		model.setChannId(saleModel.getCHANN_ID());
		Map<String,Object> channInfo = getChannInfo(model);
		
		initBookkingBean(channInfo, model, optionPerson);
		saleFreezBookkeepingBean(model, saleModel);
		
		model.setAmountTotal(outModel.getTotal_amount());
		channInfo.put("freezMoney", add(outModel.getTotal_amount(), channInfo.get("freezMoney")));
		model.setRebateTotal(outModel.getTotal_rebate());
		channInfo.put("freezRebate", add(outModel.getTotal_rebate(), channInfo.get("freezRebate")));
		optAmount(model, channInfo);
		
		//重新增加额度
		// 新增的扣减金额流水
		BookkeepingModel insertModel = new BookkeepingModel();
		initBookkingBean(channInfo, insertModel, optionPerson);
		insertModel.setBillId(outModel.getStore_out_id());
		insertModel.setBillNo(outModel.getStore_out_no());
		insertModel.setBillType(BusinessConsts.AMOUNT_TYPE_OWE_CREDIT);
		insertModel.setDirection(BusinessConsts.YJLBJ_FLAG_TRUE);
		insertModel.setBookkeepingType(BusinessConsts.BOOKKEEPING_TYPE_OUT);
		// 账户金额反填
		insertModel.setAmountTotal(multiply(-1, outModel.getTotal_amount())+"");
		channInfo.put("amountMoney", add(channInfo.get("amountMoney"), outModel.getTotal_amount()));
		
		insertModel.setAmountRebate(multiply(-1, outModel.getTotal_rebate())+"");
		channInfo.put("amountRebate",add(channInfo.get("amountRebate"), model.getRebateTotal()));//总返利
		
		optAmount(insertModel, channInfo);
	}
}
