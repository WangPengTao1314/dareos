package com.centit.common.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.common.service.BookkeepingService;
import com.centit.common.service.OsNcService;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.StringUtil;
import com.centit.drp.main.sales.deliver.order.mapper.OrderMapper;
import com.centit.drp.main.sales.deliver.order.service.OrderService;
import com.centit.erp.sale.store.mapper.ErpStoreOutMapper;
import com.centit.erp.sale.store.model.ErpStoreOut;
import com.centit.erp.sale.store.model.ErpStoreOutDtl;

@Service
public class OsNcServiceImpl implements OsNcService {
	
	@Autowired
	private ErpStoreOutMapper erpStoreOutMapper;
	
	@Autowired
	private BookkeepingService bookkeepingService;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderService orderService;

	/**
	 * 出库回写接口主体
	 */
	@Override
	@Transactional
	public String mainStoreOut(ErpStoreOut erpStoreOut) {
		String result = "0";
		
		List<ErpStoreOutDtl> detailList = erpStoreOut.getDetailList();
		if (detailList.size() > 0){
			BigDecimal totalAmount = new BigDecimal("0"); //出库总金额
			BigDecimal totalRebate = new BigDecimal("0"); //返利总金额
			for (ErpStoreOutDtl esoDtl : detailList) {
				String outNumStr = esoDtl.getOut_num()==null?"0":esoDtl.getOut_num().toString();
				BigDecimal outNum = new BigDecimal(outNumStr);
				if (outNum.compareTo(new BigDecimal("0")) <= 0) {
					result = "2"; // 出库数量不能小于0
				}
				if (StringUtils.isNotBlank(esoDtl.getIs_no_lock_flag())) {
					esoDtl.setIs_no_lock_flag("1");
				} else {
					esoDtl.setIs_no_lock_flag("0");
				}
				if (StringUtils.isNotBlank(esoDtl.getOut_num())) {
					if (StringUtils.isNotBlank(esoDtl.getDect_price())) {
						totalAmount = totalAmount.add(new BigDecimal(esoDtl.getOut_num()).multiply(new BigDecimal(esoDtl.getDect_price())));
					}
					Map<String, Object> sendParams = new HashMap<String, Object>();
					sendParams.put("SEND_ORDER_DTL_ID", esoDtl.getSend_order_dtl_id());
					Map<String, Object> sendDtlMap = orderMapper.queryDtlById(sendParams);
					String rebatePriceStr = sendDtlMap.get("rebate_price")==null?"":sendDtlMap.get("rebate_price").toString();// 返利单价
					if (StringUtils.isNotBlank(rebatePriceStr)) {
						totalRebate = totalRebate.add(new BigDecimal(esoDtl.getOut_num()).multiply(new BigDecimal(rebatePriceStr)));
					}
				}
			}
			
			if (result.equals("0")) {
				erpStoreOut.setTotal_amount(totalAmount.toString());
				erpStoreOut.setTotal_rebate(totalRebate.toString());
				Map<String, Object> sdiMap = orderMapper.querySendIdByNcId(erpStoreOut.getSend_order_id());
				String send_order_id = sdiMap.get("SEND_ORDER_ID")==null?"":sdiMap.get("SEND_ORDER_ID").toString();
				erpStoreOut.setSend_order_id(send_order_id);
				
//					
				//新增主表
				erpStoreOutMapper.insertErpStoreOut(erpStoreOut);
				//新增明细表
				erpStoreOutMapper.insertErpStoreOutDtlList(detailList);
				
				orderService.FinishGoodNum(detailList,erpStoreOut.getSale_order_no());
				
				//扣减金额/返利/信用
				bookkeepingService.storeOutAmount(erpStoreOut.getStore_out_id());
				result = "1"; //成功
			}
		}
		return result;
	}
	
}
