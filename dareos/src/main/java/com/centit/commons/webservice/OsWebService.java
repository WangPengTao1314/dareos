package com.centit.commons.webservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.centit.common.service.BookkeepingService;
import com.centit.common.service.impl.OsNcServiceImpl;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.SpringContextUtil;
import com.centit.commons.util.StringUtil;
import com.centit.drp.main.order.progress.mapper.ProgressMapper;
import com.centit.drp.main.order.progress.model.ProgressDetModel;
import com.centit.drp.main.order.progress.model.ProgressModel;
import com.centit.erp.sale.store.mapper.ErpStoreInMapper;
import com.centit.erp.sale.store.model.ErpStoreIn;
import com.centit.erp.sale.store.model.ErpStoreInDtl;
import com.centit.erp.sale.store.model.ErpStoreOut;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class OsWebService {
	
	private Logger logger = Logger.getLogger(OsWebService.class);
	
//	@Autowired
//	private OrderService orderService;
	
	@Autowired
	private BookkeepingService bookkeepingService;

	public OsWebService(){
	   System.out.println("OsWebServiceStart*******!");
	}
	
	/**
	 * 
	 * @Title: deliverySend
	 * @Description:发货出库(出库数量回写) 
	 * @author lv_f
	 * @date 2019年5月27日 下午3:08:15
	 * @param @param userName
	 * @param @param passWord
	 * @param @param deliveryData
	 * @param @return
	 * @return String
	 * @throws
	 */
//	@WebMethod(action="deliverySend",operationName="deliverySend",exclude=false)
//	@Transactional
//	public String deliverySend(@WebParam(name = "userName")String userName,@WebParam(name = "passWord")String passWord,
//			@WebParam(name = "deliveryData")String deliveryData){
//		//deliveryData = "";//怎么传数据格式待定
//		try {
//			OrderMapper orderMapper = (OrderMapper)SpringContextUtil.getBean("orderMapper");
//			SaleorderMapper saleorderMapper = (SaleorderMapper)SpringContextUtil.getBean("saleorderMapper");
//			//先默认获取到发货订单id和明细id
//			String sendOrderId = "b5c7a6ad497c4e6ebaef1bbcdaa56d36";//发货订单id
//			String sendOrderDtlIdStr = "13adee11a99c427598549f9c91a803a1,fe317321b7b8449cb65da9003442e6dc";//发货订单明细id，多个，用逗号隔开
//			String orderDtlNumStr = "100,100";//发货数量，多个，用逗号隔开，顺序需要和发货订单明细id一一对应
//			
//			String[] sendOrderDtlIdArr = sendOrderDtlIdStr.split(",");
//			String[] orderDtlNumArr = orderDtlNumStr.split(",");
//			
//			Map<String, Object> params1 = new HashMap<String, Object>();
//			Map<String, String> params2 = new HashMap<String, String>();
//			String saleOrderId = "5526bd6e60f148d39e4716fa8feb31c0";//看NC那边能否提供，否则通过发货订单ID查询
//			for (int i = 0;i < sendOrderDtlIdArr.length;i++){
//				String sendOrderDtlId = sendOrderDtlIdArr[i];
//				String orderDtlNum = orderDtlNumArr[i];
//				
//				params1.put("SEND_ORDER_DTL_ID", sendOrderDtlId);
//				Map<String, Object> sendOrderDtlMap = orderMapper.queryDtlById(params1);
//				//销售明细表明细ID
//				String saleOrderDtlId = (String)sendOrderDtlMap.get("SALE_ORDER_DTL_ID");
//				System.out.println(saleOrderDtlId);
//				//更新已实际发货数量
//				params2.put("SALE_ORDER_DTL_ID", saleOrderDtlId);
//				params2.put("SENDED_NUM", orderDtlNum);
//				saleorderMapper.updateChldByIdNew(params2);
//			}
//			Map<String, String> params3 = new HashMap<String, String>();
//			params3.put("SALE_ORDER_ID", saleOrderId);
//			String difValue = saleorderMapper.getSaleOrderSendDif(params3);
//			if (Integer.parseInt(difValue) == 0){//相等，则更新发货表和订单表状态都为已完成
//				Map<String, Object> params4 = new HashMap<String, Object>();
//				params4.put("SEND_ORDER_ID", sendOrderId);
//				params4.put("STATE", "已完成");
//				orderMapper.modify(params4);
//				Map<String, String> params5 = new HashMap<String, String>();
//				params5.put("SALE_ORDER_ID", saleOrderId);
//				params5.put("STATE", "已完成");
//				saleorderMapper.updateById(params5);					
//			}
//			return "true";
//		} catch (Exception e) {
//			//手动回滚事务
//			e.printStackTrace();
//			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			return "fasle";
//		}
//	}
	
	
	/**
	 * 入库单回写
	 * @param userName
	 * @param passWord
	 * @param storeInData
	 * @return
	 */
	@WebMethod(action="storeIn",operationName="storeIn",exclude=false)
	@Transactional
	public String storeIn(@WebParam(name = "userName")String userName,@WebParam(name = "passWord")String passWord,
			@WebParam(name = "storeInData")String storeInData){
		String uuid = StringUtil.uuid32len();
		InterUtil.actLog("web接口", "入库单回写", "被调用", "success", "", "", uuid, "", "", storeInData);
		try {
			ErpStoreIn erpStoreIn = JSON.parseObject(storeInData, ErpStoreIn.class);
			
			ErpStoreInMapper erpStoreInMapper = (ErpStoreInMapper)SpringContextUtil.getBean("erpStoreInMapper");
			//新增主表
			erpStoreInMapper.insertErpStoreIn(erpStoreIn);
			//新增明细表
			List<ErpStoreInDtl> detailList = erpStoreIn.getDetailList();
			if (detailList.size() > 0){
				erpStoreInMapper.insertErpStoreInDtlList(detailList);
			}
			InterUtil.actLog("web接口", "入库单回写", "被调用", "success", "", "", uuid, "", "", "成功");
			return "{code:'true',message:'成功'}";
		} catch (Exception e) {
			//手动回滚事务
			e.printStackTrace();
			String mess = e.getMessage();
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			InterUtil.actLog("web接口", "入库单回写", "被调用", "error", "", "", uuid, "", "", mess);
			return "{code:'false',message:'"+mess+"'}";
		}
	}
	
	/**
	 * 出库单回写
	 * @param userName
	 * @param passWord
	 * @param storeOutData
	 * @return
	 */
	@WebMethod(action="storeOut",operationName="storeOut",exclude=false)
	public String storeOut(@WebParam(name = "userName")String userName,@WebParam(name = "passWord")String passWord,
			@WebParam(name = "storeOutData")String storeOutData){
		String storeOutid = "";
		String uuid = StringUtil.uuid32len();
		InterUtil.actLog("web接口", "出库单回写", "被调用", "成功", storeOutid, "", uuid, "", "", storeOutData);
		try {
			ErpStoreOut erpStoreOut = JSON.parseObject(storeOutData, ErpStoreOut.class);
			storeOutid = erpStoreOut.getStore_out_id();
			
			OsNcServiceImpl osNcService = (OsNcServiceImpl)SpringContextUtil.getBean("osNcServiceImpl");
			String result = osNcService.mainStoreOut(erpStoreOut);
			if ("1".equals(result)) {
				InterUtil.actLog("web接口", "出库单回写", "被调用", "成功", storeOutid, "", uuid, "", "", "成功");
				return "{code:'true',message:'成功'}";
			} else if("2".equals(result)) {
				InterUtil.actLog("web接口", "出库单回写", "被调用", "失败", storeOutid, "", uuid, "", "", "出库数量不能小于0");
				return "{code:'false',message:'出库数量不能小于0'}";
			} else {
				InterUtil.actLog("web接口", "出库单回写", "被调用", "失败", storeOutid, "", uuid, "", "", "明细数据不能空");
				return "{code:'false',message:'明细数据不能为空'}";
			}
		}catch(ServiceException s){
			String mess = s.getMessage();
			InterUtil.actLog("web接口", "出库单回写", "被调用", "失败", storeOutid, "", uuid, "", "", mess);
			return "{code:'false',message:'"+mess+"'}";
		} catch (Exception e) {
			//手动回滚事务
			e.printStackTrace();
	//		TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
			String mess = e.getMessage();
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			InterUtil.actLog("web接口", "出库单回写", "被调用", "失败", storeOutid, "", uuid, "", "", mess);
			return "{code:'false',message:'"+mess+"'}";
		}
	}
	
	
	/**
	 * 
	 * @Title: purOrderSend
	 * @Description: OEM采购订单(采购订单回写)
	 * @author lv_f
	 * @date 2019年5月27日 下午3:09:27
	 * @param @param userName
	 * @param @param passWord
	 * @param @param purOrderSendData
	 * @param @return
	 * @return String
	 * @throws
	 */
	@WebMethod(action="purOrderSend",operationName="purOrderSend",exclude=false)
	@Transactional
	public String purOrderSend(@WebParam(name = "userName")String userName,@WebParam(name = "passWord")String passWord,
			@WebParam(name = "purOrderSendData")String purOrderSendData){
		String purid = "";
		String uuid = StringUtil.uuid32len();
		InterUtil.actLog("web接口", "采购订单回写", "被调用", "成功", "", "", uuid, "", "", purOrderSendData);
		//json格式
		try {
/*			purOrderSendData = "{'delivery_date': '2019-05-27','ledger_id': 'NC测试','total_money': '2467','order_date': '2019-05-27','order_degree_id': 'NC测试',"
					+"'order_degree_no': '采购订单编号','order_num': 200,'prvd_name': '供应商名称','prvd_no': '供应商编号','pur_dep': '采购部门','pur_name': '采购员','sale_order_no': '销售订单编号',"
					+"'detailList': [{'group_no': 1,'order_degree_dtl_id': '1','order_degree_id': 'NC测试','order_num': 65,'order_amount':'456','prd_glass': '玻璃','prd_id': '22','prd_name': '产品名称',"
					+"'prd_no': '22','prd_other': '其它','prd_quality': '材质','prd_series': '系列','prd_spec': '规格','prd_toward': '推向','std_unit': '主单位'}, {"
					+"'group_no': 1,'order_degree_dtl_id': '2','order_degree_id': 'NC测试','order_num': 65,'order_amount':'456','prd_glass': '玻璃','prd_id': '22','prd_name': '产品名称',"
					+"'prd_no': '22','prd_other': '其它','prd_quality': '材质','prd_series': '系列','prd_spec': '规格','prd_toward': '推向','std_unit': '主单位'}]}";*/
			
			ProgressModel mainProgressModel = JSON.parseObject(purOrderSendData, ProgressModel.class);
			purid = mainProgressModel.getOrder_degree_id();
			if (StringUtils.isNotEmpty(mainProgressModel.getOrder_num())) {
				Double orderNum = Double.valueOf(mainProgressModel.getOrder_num());
				orderNum = Double.parseDouble(String.format("%.2f",orderNum));
				mainProgressModel.setOrder_num(orderNum.toString());
			}
			
			ProgressMapper progressMapper = (ProgressMapper)SpringContextUtil.getBean("progressMapper");
			//新增主表
			progressMapper.insertProgressModel(mainProgressModel);
			//新增明细表
			List<ProgressDetModel> detailList = mainProgressModel.getDetailList();
			if (detailList.size() > 0){
				progressMapper.insertProgressDetModelList(detailList);
			}
			InterUtil.actLog("web接口", "采购订单回写", "被调用", "成功", purid, "", uuid, "", "", "成功");
			return "{code:'true',message:'成功'}";
		} catch (Exception e) {
			//手动回滚事务
			e.printStackTrace();
			String mess = e.getMessage();
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			InterUtil.actLog("web接口", "采购订单回写", "被调用", "失败", purid, "", uuid, "", "", mess);
			return "{code:'false',message:'"+mess+"'}";
		}
		
	}
		

}
