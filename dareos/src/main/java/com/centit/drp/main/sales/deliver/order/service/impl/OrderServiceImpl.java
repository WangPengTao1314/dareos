package com.centit.drp.main.sales.deliver.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.common.service.BookkeepingService;
import com.centit.common.service.FlowService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.commons.webservice.NcWebserviceUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.project.management.service.ProjectConsts;
import com.centit.drp.main.sales.deliver.order.mapper.OrderMapper;
import com.centit.drp.main.sales.deliver.order.model.OrderModel;
import com.centit.drp.main.sales.deliver.order.model.SendDtlModel;
import com.centit.drp.main.sales.deliver.order.service.OrderService;
import com.centit.erp.sale.store.model.ErpStoreOutDtl;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 发货管理模块 发货指令维护
 * 
 * @author wang_pt
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private FlowService service;
	@Autowired
	private BookkeepingService bService;
	/**
	 * 删除改变状态 del_flag (1：删除，0：新增)
	 */
	private static Integer PRO_del_flag = 1;
	
	/**
	 * 分页查询
	 */
	@Override
	public void pageQuery2(Map<String, Object> params, PageDesc pageDesc) {
//		backStoreOut("1001V6100000004G3WF5");
		Page<Object> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
		orderMapper.pageQuery2(params);
		LogicUtil.transPageHelper(pageDesc, p);

	}
	
	/**
	 * 主表单记录查询
	 */
	@Override
	public Map<String, Object> query(Map<String, Object> params) {
		Map<String, Object> map = orderMapper.toQuery(params);
		return map;
	}

	/**
	 * 明细编辑数据回显
	 */
	@Override
	public Map<String, Object> querySun(Map<String, Object> params) {
		Map<String, Object> map = orderMapper.toQuery(params);
		String attPathP = LogicUtil.getAttPath(map.get("SALE_ORDER_ID").toString());
		map.put("ATT_PATH", attPathP);
		if(map!=null) {
//			Integer num = 0;
			List<Map<String, Object>> entrySun = orderMapper.toQuerySendSun(map);
			if (entrySun!= null) {
				for(Map<String, Object>listMap:entrySun) {
					String attPathS = LogicUtil.getAttPath(listMap.get("SALE_ORDER_DTL_ID").toString());
					listMap.put("ATT_PATHS", attPathS);
				}
//				for (int i = 0; i < entrySun.size(); i++) {
//					/**
//					 * 校验发货数量
//					 */
//					params = new HashMap<String, Object>();
//					params.put("SALE_ORDER_ID", map.get("SALE_ORDER_ID"));
//					params.put("SALE_ORDER_DTL_ID", entrySun.get(i).get("SALE_ORDER_DTL_ID"));
//					params.put("stateNUM","待审核");
//					
//					params = orderMapper.allSendNum(params);
//					num = Integer.valueOf(entrySun.get(i).get("ORDER_NUM")==null?"0":entrySun.get(i).get("ORDER_NUM").toString())
//							- Integer.valueOf(params.get("ALLSEND_NUM")==null?"0":params.get("ALLSEND_NUM").toString());
//					entrySun.get(i).put("WAITNUM", num);
//					entrySun.get(i).put("SEND_NUM", num);
//				}
				map.put("entrySun", entrySun);
			}
			
		}

		return map;
	}
	
	@Override
	public Map<String, Object> getSaleData(Map<String, Object> params) {
		Map<String, Object> entry = orderMapper.saleQuery(params);
		String attPathP = LogicUtil.getAttPath(params.get("SALE_ORDER_ID").toString());
		entry.put("ATT_PATH", attPathP);
		List<Map<String, Object>> entrySun = orderMapper.toQuerySaleSun(params);
		if(entrySun!=null) {
			for(Map<String, Object>listMap:entrySun) {
				String attPathS = LogicUtil.getAttPath(listMap.get("SALE_ORDER_DTL_ID").toString());
				listMap.put("ATT_PATHS", attPathS);
			}
			
		}
		/**
		 * 校验发货数量
		 */
//		Integer num=0;
//		for(Map<String, Object> mapList:entrySun) {
//			params=new HashMap<String, Object>();
//			params.put("SALE_ORDER_DTL_ID",mapList.get("SALE_ORDER_DTL_ID"));
//			params=orderMapper.allSendNum(params);
//			num=Integer.valueOf(String.valueOf(mapList.get("ORDER_NUM")))-Integer.valueOf(String.valueOf(params.get("ALLSEND_NUM")));
//			mapList.put("WAITNUM",String.valueOf(num));
//			mapList.put("SEND_NUM",String.valueOf(num));
//			mapList.put("REBATE_PRICE",mapList.get("REBATE_PRICE")==null?"0":mapList.get("REBATE_PRICE"));//
//			
//		}
		entry.put("entrySun", entrySun);
		return entry;
	}
	
	@Transactional
	public Map<String, Object> modify(OrderModel model,List<SendDtlModel> modelList, HttpServletRequest request, String BILL_TYPE) {
		// 获取用户信息
		UserBean userBean = ParamUtil.getUserBean(request);
		List<SendDtlModel > upList = new ArrayList<SendDtlModel>();
		List<SendDtlModel > addList = new ArrayList<SendDtlModel>();
		//
		String sale_order_id = request.getParameter("SALE_ORDER_ID");
		Map<String, Object> mapNum = new HashMap<String, Object>();
		//String send_order_id = request.getParameter("SEND_ORDER_ID");
		//编辑主表
		String msg="发货单新增";
		if (StringUtil.isEmpty(model.getSend_order_id())) {
			model.setSend_order_id(StringUtil.uuid32len());
			model.setSend_order_no(getManager());
			model.setCreator(userBean.getXTYHID());
			model.setCre_name(userBean.getXM());
			model.setState(ProjectConsts.SENDORDER_DRAFT);
			if ("返修订单".equals(BILL_TYPE)) {
				model.setBill_type(BILL_TYPE);
			}
			orderMapper.insert(model);
		} else {
			orderMapper.modify(model);
			//发货指令进度跟踪
			msg="发货单编辑";
		}
		String flowNo = service.getFlowNoByLedger(model.getLedger_id(), "0");
		service.insertFlowTrack(flowNo,model.getSend_order_id(), model.getFactory_no(), userBean, "", msg);
		// 编辑子表
		Double total_amount = 0.00,total_rebate = 0.00;
		Integer ROW_NO = 1;
		if (modelList!=null) {
			for (int i = 0; i < modelList.size(); i++) {
				SendDtlModel modelMul = modelList.get(i);
				modelMul.setSale_order_id(sale_order_id);
				modelMul.setSend_order_id(model.getSend_order_id());
				if(StringUtil.isEmpty(modelMul.getSend_order_dtl_id())) {
					modelMul.setSend_order_dtl_id(StringUtil.uuid32len());
					modelMul.setRow_no(String.valueOf(ROW_NO));
					ROW_NO++;
					addList.add(modelMul);
				}else {
					upList.add(modelMul);
				}
				total_amount +=Double.parseDouble(StringUtils.isBlank(modelMul.getSend_amount())?"0.0":modelMul.getSend_amount());
				total_rebate +=Double.parseDouble(StringUtils.isBlank(modelMul.getRebate_amount())?"0.0":modelMul.getRebate_amount());
			}
			// 更新总金额
			model.setTotal_amount(String.valueOf(total_amount));
			model.setTotal_rebate(String.valueOf(total_rebate));
			model.setUpdator(userBean.getXTYHID());
			model.setUpd_name(userBean.getXM());
			orderMapper.modify(model);
		}
		if (!upList.isEmpty()) {
			for (int i = 0; i < upList.size(); i++) {
				orderMapper.updateSunById(upList.get(i));
			}
		}
		if (!addList.isEmpty()) {
			for (int i = 0; i < addList.size(); i++) {
				orderMapper.insertSun(addList.get(i));
			}
		}
		// 根据销售订单的ID 校验明细的发货数量
		if(!"返修订单".equals(BILL_TYPE)){
			checkSendNum(model.getSale_order_id());
		}
		//校验返修单号唯一
//		if("返修订单".equals(BILL_TYPE)){
//			//校验原订单编号
//			String SALE_ORDER_NO ="sale_order_no='"+model.getSale_order_no().trim()+"'";
//			if (orderMapper.checkFactoryNO(SALE_ORDER_NO) > 1) {
//				throw new ServiceException("返修单编号："+model.getFrom_bill_no()+" 已存在！！！");
//			}
//		}
		return mapNum;
	}

	/**
	 * 发货指令编号
	 * @return
	 */
	public String getManager() {
		String str = LogicUtil.getBmgz("SEND_ORDER_NO_SEQ");
		return str;
	}

	@Override
	public void delete(String send_order_id, UserBean userBean) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("SEND_ORDER_ID", send_order_id);
		params.put("updator", userBean.getXTYHID());
		params.put("upd_name", userBean.getXM());
		params.put("del_flag", PRO_del_flag);
		orderMapper.updateByID(params);
		orderMapper.delSendDtlById(send_order_id);
	}

	/**
	 * 审核
	 */
	@Transactional
	public void submit(OrderModel model,UserBean userBean) {
		// 审批意见 check_advice
		orderMapper.modify(model);
		//发货指令进度跟踪
		String state=model.getState();
		String msg="";
		if("待审核".equals(state)) {
			msg="发货单已提交";
		}else if("已拒绝".equals(state)) {
			msg="发货单审核已拒绝";
		}else if("已通过".equals(state)) {
			msg="发货单审核已通过";
		}
		String flowNo = service.getFlowNoByLedger(model.getLedger_id(), "0");
		service.insertFlowTrack(flowNo,model.getSend_order_id(), model.getFactory_no(), userBean,model.getCheck_advice(),msg);
		
	}

	@Override
	public OrderModel getSendOrder(String sendOrderId) {
		return orderMapper.getSendOrder(sendOrderId);
	}
	
	@Override
	public List<SendDtlModel> getSendDtlOrder(String sendOrderId) {
		return orderMapper.getSendDtlOrder(sendOrderId);
	}
	@Override
	public Map<String, Object> checkSendNum(Map<String, Object> params) {
		return orderMapper.allSendNum(params);
	}
	
	/**
	 * (store_out_id send_order_id )出库单数据回填请求参数 
	 * sand_order_id：出库单回填存储的为销发货单return_nc_id数据
	 */
	@Transactional
	public void FinishGoodNum(List<ErpStoreOutDtl> detailList,String factoryNo) {
		List<Map<String,String>> updList=new ArrayList<Map<String,String>>();
		String sendOrderDtlIds = "";
		//循环反填出库单明细
		for (int i = 0; i < detailList.size(); i++) {
			Map<String,String> map=new HashMap<String, String>();
			map.put("sendOrderDtlId", detailList.get(i).getSend_order_dtl_id());
			map.put("sendedNum", detailList.get(i).getOut_num());
			updList.add(map);
			sendOrderDtlIds+="'"+detailList.get(i).getSend_order_dtl_id()+"',";
		}
		// 更新发货指令明细
		orderMapper.updSendSendedNum(updList);
		
		//更新销售订单明细发货数量
		orderMapper.updSaleSendedNum(updList);
		
		//根据发货指令明细ID获取发货指令主表ID
		if(!StringUtil.isEmpty(sendOrderDtlIds)){
			sendOrderDtlIds=sendOrderDtlIds.substring(0,sendOrderDtlIds.length()-1);
		}
		List<String> sendIdList = orderMapper.getSendIdByDtlIds(sendOrderDtlIds);
		// 根据发货指令主表ID判断是否修改状态
		String sendOrderIds = "";
		for (int i = 0; i < sendIdList.size(); i++) {
			sendOrderIds+="'"+sendIdList.get(i)+"',";
		}
		if(!StringUtil.isEmpty(sendOrderIds)){
			sendOrderIds=sendOrderIds.substring(0,sendOrderIds.length()-1);
		}
		Map<String,String> map =new HashMap<String, String>();
		map.put("state", BusinessConsts.COMPLETED);
		map.put("sendOrderIds", sendOrderIds);
		// 更新发货指令主表状态
		orderMapper.updSendState(map);
		//更新销售订单主表状态
		String saleOrderId = orderMapper.getSendedSaleId(factoryNo);
		if(!StringUtil.isEmpty(saleOrderId)){
			Map<String,String> saleMap=orderMapper.getSaleOrderById(saleOrderId);
			Map<String,String> userMap = new HashMap<String, String>();// 模拟UserBean
			userMap.put("RYXXID", "出库处理");
			userMap.put("XM", "出库处理");
			UserBean userBean = new UserBean(userMap, null, null);
			service.insertNextFlow(saleMap.get("SALE_ORDER_ID"), saleMap.get("FACTORY_NO"), saleMap.get("ORDER_TRACE_ID"),  service.getFlowNoByLedger(saleMap.get("LEDGER_ID"), "0"), userBean, null);
		}
		Map<String, String> numIstrue = orderMapper.queryNumIsTrue(factoryNo);
		if (numIstrue!=null) {
			String prdNostr = numIstrue.get("prd_name")==null?"":numIstrue.get("prd_name").toString();
			if (!StringUtil.isEmpty(prdNostr)) {
				throw new ServiceException(prdNostr+"：出库数量不正确！");
			}
		}
		
	}

	/**
	 * 审核通过
	 */
	@Transactional
	public void sendAudit(OrderModel model,String send_order_id,UserBean userBean){
		if(!"已拒绝".equals(model.getState())){
			/**
			 * 发货单推送数据到NC的接口
			 */
			OrderModel tempMpdel = getSendOrder(send_order_id);
			tempMpdel.setUpdator(userBean.getXTYHID());
			tempMpdel.setUpd_name(userBean.getXM());
			Map<String, String> resultMap = NcWebserviceUtil.djSendOrder("1",tempMpdel.getNc_return_id(), tempMpdel, getSendDtlOrder(send_order_id));
			if("false".equals(resultMap.get("flag"))){
				throw new ServiceException(resultMap.get("message"));
			}
			model.setNc_return_id(resultMap.get("message"));
		}
		model.setSend_order_id(send_order_id);
		submit(model,userBean);
	}
	
	
	public void checkSendNum(String saleOrderId){
		List<String> list=orderMapper.checkSendNum(saleOrderId);
		if(list!=null&&!list.isEmpty()){
			String prdName ="";
			for (int i = 0; i < list.size(); i++) {
				prdName+=list.get(i)+",";
			}
			prdName.substring(0,prdName.length()-1);
			throw new ServiceException("物料："+prdName+"发货过多，请检查后重新保存");
		}
	}
	
	/**
	 * 出库单删除
	 * @param storeOutId
	 */
	@Transactional
	public void backStoreOut(String storeOutId){
		bService.backStoreOut(storeOutId, "出库单删除回退");
		//根据出库单获取出库单明细发货数量
		List<Map<String,String>> outDtlList = orderMapper.getOutDtlById(storeOutId);
		String factoryNo = orderMapper.getFactoryNoByOutId(storeOutId);
		List<Map<String,String>> updList = new ArrayList<Map<String,String>>();
		String sendOrderDtlIds="";
		for (int i = 0; i < outDtlList.size(); i++) {
			Map<String,String> map=new HashMap<String, String>();
			map.put("sendOrderDtlId", outDtlList.get(i).get("SEND_ORDER_DTL_ID"));
			map.put("sendedNum", LogicUtil.multiply(outDtlList.get(i).get("OUT_NUM"),-1)+"");
			updList.add(map);
			sendOrderDtlIds+="'"+outDtlList.get(i).get("SEND_ORDER_DTL_ID")+"',";
		}
		// 更新发货指令明细
		orderMapper.updSendSendedNum(updList);
		
		//更新销售订单明细发货数量
		orderMapper.updSaleSendedNum(updList);
		
		//根据发货指令明细ID获取发货指令主表ID
		if(!StringUtil.isEmpty(sendOrderDtlIds)){
			sendOrderDtlIds=sendOrderDtlIds.substring(0,sendOrderDtlIds.length()-1);
		}
		List<String> sendIdList = orderMapper.getSendIdByDtlIds(sendOrderDtlIds);
		// 根据发货指令主表ID判断是否修改状态
		String sendOrderIds = "";
		for (int i = 0; i < sendIdList.size(); i++) {
			sendOrderIds+="'"+sendIdList.get(i)+"',";
		}
		if(!StringUtil.isEmpty(sendOrderIds)){
			sendOrderIds=sendOrderIds.substring(0,sendOrderIds.length()-1);
		}
		Map<String,String> map =new HashMap<String, String>();
		map.put("state", BusinessConsts.COMPLETED);
		map.put("sendOrderIds", sendOrderIds);
		// 更新发货指令主表状态
		orderMapper.updSendState(map);
		//更新销售订单主表状态
		String saleOrderId = orderMapper.getSendedSaleId(factoryNo);
		if(!StringUtil.isEmpty(saleOrderId)){
			Map<String,String> saleMap=orderMapper.getSaleOrderById(saleOrderId);
			Map<String,String> userMap = new HashMap<String, String>();// 模拟UserBean
			userMap.put("RYXXID", "出库处理");
			userMap.put("XM", "出库处理");
			UserBean userBean = new UserBean(userMap, null, null);
			service.insertNextFlow(saleMap.get("SALE_ORDER_ID"), saleMap.get("FACTORY_NO"), saleMap.get("ORDER_TRACE_ID"),  service.getFlowNoByLedger(saleMap.get("LEDGER_ID"), "0"), userBean, null);
		}
	}

}
