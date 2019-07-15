package com.centit.drp.main.shopcar.service.impl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.common.service.FlowService;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.shopcar.mapper.ShopCarMapper;
import com.centit.drp.main.shopcar.model.ShopcarChannInfoModel;
import com.centit.drp.main.shopcar.model.ShopcarModel;
import com.centit.drp.main.shopcar.service.ShopcarService;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * *@serviceImpl
 * *@func
 * *@version 1.1
 * *@author lyg
 * *@createdate 2013-08-19 14:35:52
 */
@Service
public class ShopcarServiceImpl implements ShopcarService {
	
	@Autowired
	private ShopCarMapper mapper;
	@Autowired
	private FlowService service;
	
	
	/**
	 * 查询
	 * @param params
	 * @param pageNo
	 * @return
	 */
	public List<Map<String,Object>> pageQuery(Map<String, Object> params) {
    	return mapper.pageQuery(params);
	}
	/**
	 * @删除
	 * @param ADVC_ORDER_ID
	 * 
	 * @return true, if tx delete
	 */
	public void txDelete(Map<String, Object> params) {
		 mapper.delete(params);
	}
	/**
	 * 增加
	 */
	public void txInsert(Map<String, Object> orderMap) {
		mapper.insert(orderMap);
	} 
	 
	/**
	 * 查询当前渠道
	 * @param CHANN_ID 渠道ID
	 * @return
	 */
	public Map<String, Object> getChannInfo(String CHANN_ID){
		return mapper.getChannCreDit(CHANN_ID);
		
	}
	
	/**
	 * 更新购物车
	 */
	public void update(List<ShopcarModel> modelList,UserBean userBean) throws ParseException{
		List<Map<String, Object>> updateList = new ArrayList<Map<String,Object>>();
		for (ShopcarModel model : modelList) {
			Map<String, Object> params = new HashMap<String,Object>();
			String SHOP_CART_ID=model.getSHOP_CART_ID();
			params.put("SHOP_CART_ID", SHOP_CART_ID);
			params.put("ORDER_NUM", model.getORDER_NUM());
			params.put("ORDER_AMOUNT", model.getORDER_AMOUNT());
			params.put("UPD_NAME",userBean.getXM());//更新人名称
		    params.put("UPDATOR",userBean.getXTYHID());//更新人ID
		    params.put("UPD_TIME","数据库时间");//更新时间
		    params.put("REMARK", model.getREMARK());//备注
		    String ORDER_RECV_DATE = model.getORDER_RECV_DATE();
		    params.put("ORDER_RECV_DATE", ORDER_RECV_DATE);//交货日期
		    updateList.add(params);
		}
		mapper.update(updateList);
	}
	
	
	public void upPrice(Map<String, Object> map) {
		mapper.upPrice(map);
	}
	// 获取数据库当前时间
	/*public String getDate() {
		return load("Shopcar.getDate", "").toString();
	}*/
	public long getCount(Map<String,Object> map){
		Object obj = mapper.pageCount(map);
		return Long.parseLong(obj.toString());
	}
	/**
     * 根据当前登录人所属渠道获取渠道类型
     * @param CHANN_ID
     * @return
     */
    public Map<String,Object> getCHANN_TYPE(String CHANN_ID){
    	return mapper.getCHANN_TYPE(CHANN_ID);
    }
    public String getLargessDect(String CHANN_ID){
    	Map<String,Object> map=new HashMap<String, Object>();
    	map.put("CHANN_ID", CHANN_ID);
    	map.put("DECT_TYPE", "赠品折扣");
    	Object obj = mapper.getLargessDect(map);
    	if(null==obj){
    		return null;
    	}else{
    		return obj.toString();
    	}
    }
    
    /**
     * 保存并下单
     */
    @Transactional
	public boolean txSaveCommit(Map<String,String> shopParams,List<ShopcarModel> ModelList,UserBean userBean,ShopcarChannInfoModel channModel) throws Exception {
		this.update(ModelList, userBean);
		//生成要货单信息
		Map<String,Object> orderMap=new HashMap<String, Object>();
		//收货方就是要货放
		orderMap.put("RECV_CHANN_ID", channModel.getCHANN_ID());
		orderMap.put("RECV_CHANN_NO", channModel.getCHANN_NO());
		orderMap.put("RECV_CHANN_NAME", channModel.getCHANN_NAME());
		orderMap.put("ORDER_CHANN_ID", channModel.getCHANN_ID());
		orderMap.put("ORDER_CHANN_NO", channModel.getCHANN_NO());
		orderMap.put("ORDER_CHANN_NAME", channModel.getCHANN_NAME());
		orderMap.put("PERSON_CON", channModel.getPERSON_CON());
		orderMap.put("TEL", channModel.getTEL());
		orderMap.put("RECV_ADDR", channModel.getRECV_ADDR());
		orderMap.put("STATE", BusinessConsts.GOODSORDER_STATE_CG);
		orderMap.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		orderMap.put("REMARK", channModel.getREMARK());
		orderMap.put("CRE_NAME",userBean.getXM());//制单人名称
		orderMap.put("CREATOR",userBean.getXTYHID());//制单人ID
		orderMap.put("DEPT_ID",userBean.getBMXXID());//制单部门ID
		orderMap.put("DEPT_NAME",userBean.getBMMC());//制单部门名称
		orderMap.put("ORG_ID",userBean.getJGXXID());//制单机构ID
		orderMap.put("ORG_NAME",userBean.getJGMC());//制单机构名称
		orderMap.put("CRE_TIME","数据库时间");//制单时间
		orderMap.put("IS_USE_REBATE", shopParams.get("rebate"));
		orderMap.put("BILL_TYPE", "常规订单");
		orderMap.put("TRANSPORT_SETTLE", channModel.getTRANSPORT_SETTLE());
		orderMap.put("TRANSPORT", channModel.getTRANSPORT());
		orderMap.put("SALE_ID", userBean.getRYXXID());
		orderMap.put("SALE_NAME", userBean.getXM());
		orderMap.put("SALEDEPT_NAME", userBean.getBMMC());
		orderMap.put("SALEDEPT_ID", userBean.getBMBH());
		Map<String, Object> channInfo=getChannInfo(channModel.getCHANN_ID());
		orderMap.put("AREA_ID", channInfo.get("AREA_ID"));
		orderMap.put("AREA_NO", channInfo.get("AREA_NO"));
		orderMap.put("AREA_NAME", channInfo.get("AREA_NAME"));
		orderMap.put("PRINT_NAME", channInfo.get("CHANN_ABBR"));
		orderMap.put("CUST_NAME", channModel.getCUST_NAME());
		orderMap.put("CUST_TEL", channModel.getCUST_TEL());
		orderMap.put("CUST_ADDR", channModel.getCUST_ADDR());
		orderMap.put("CHANN_ID", channInfo.get("CHANN_ID"));
		orderMap.put("CHANN_NO", channInfo.get("CHANN_NO"));
		orderMap.put("CHANN_NAME", channInfo.get("CHANN_NAME"));
		orderMap.put("CHANN_ID_P", channInfo.get("CHANN_ID_P"));
		orderMap.put("CHANN_NO_P", channInfo.get("CHANN_NO_P"));
		orderMap.put("CHANN_NAME_P", channInfo.get("CHANN_NAME_P"));
		String GOODS_ORDER_NOs = "";
		//根据购物车ID和货品类别分组生成订货订单
		//先查询有多少组
		List<Map<String,String>> ledgerList=mapper.getLedgerGroup(shopParams.get("SHOP_CART_IDS"));
		for (int i = 0; i < ledgerList.size(); i++) {
			//订单ID
			String GOODS_ORDER_ID=StringUtil.uuid32len();
			//订单编号
			String GOODS_ORDER_NO=LogicUtil.getBmgz("GOODS_ORDER_NO_SEQ");
			GOODS_ORDER_NOs += GOODS_ORDER_NO+",";
			orderMap.put("GOODS_ORDER_ID", GOODS_ORDER_ID);
			orderMap.put("GOODS_ORDER_NO", GOODS_ORDER_NO);
			orderMap.put("LEDGER_ID", ledgerList.get(i).get("LEDGER_ID"));
			orderMap.put("LEDGER_NAME", ledgerList.get(i).get("LEDGER_NAME"));
			this.txInsert(orderMap);//新增订单表
			orderMap.put("ids", shopParams.get("SHOP_CART_IDS"));
			mapper.insertOrderDtl(orderMap);
		}
		String[] noArry = GOODS_ORDER_NOs.substring(0,GOODS_ORDER_NOs.length()-1).split(",");
		String flowType = LogicUtil.getFlowType(userBean);
		// 创建流程接点
		for (int i = 0; i < noArry.length; i++) {
			String flowNo = service.getFlowNoByLedger(ledgerList.get(i).get("LEDGER_ID"), flowType);
			service.insertFirstFlow(flowNo, userBean, noArry[i]);
		}
		//更新购物车处理标记
	    Map<String,Object> map=new HashMap<String,Object>();
	    map.put("SHOP_CART_IDS", shopParams.get("SHOP_CART_IDS"));
	    map.put("DEAL_FLAG", BusinessConsts.YJLBJ_FLAG_TRUE);
	    map.put("SESSION_ID", StringUtil.uuid32len());
	    mapper.upDeal(map);
	return true;
	}
	/**
	 * 插入生命周期表
	 * @param GOODS_ORDER_ID 订货订单ID
	 * @param BILL_TYPE 单据类型
	 * @param GOODS_ORDER_NO 订货订单NO
	 * @param userBean
	 */
	public void saveOrderTruck(String GOODS_ORDER_ID,String BILL_TYPE,String GOODS_ORDER_NO, UserBean userBean){
		Map<String,Object>params = new HashMap<String,Object>();
		String GOODS_ORDER_TRACE_ID = StringUtil.uuid32len();
		params.put("GOODS_ORDER_TRACE_ID", GOODS_ORDER_TRACE_ID);
		params.put("GOODS_ORDER_ID", GOODS_ORDER_ID);
		params.put("BILL_NO", GOODS_ORDER_NO);//订货订单的NO
		params.put("BILL_TYPE", BILL_TYPE);//订货订单的type
		
		params.put("ACTION_NAME", BusinessConsts.ORDER_BILL_ACTION_MAKE);//订货订单制作
		params.put("TRACE_URL", BusinessConsts.ORDER_BILL_ACTION_TRACE_URL+GOODS_ORDER_ID);
		params.put("DEAL_PSON_NAME", userBean.getXM());
		params.put("STATE", BusinessConsts.STATE_MAKE);
		
		
	}
	/**
	 * 下单，保存，下单并提交时验证预订单转订货是否退回
	 * @param SHOP_CAR_IDS
	 * @return
	 */
	public boolean checkAdvcReturn(String SHOP_CART_IDS){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("SHOP_CART_IDS", SHOP_CART_IDS);
		map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_DROP);
		int count=StringUtil.getInteger(mapper.checkAdvcReturn(map));
		if(count>0){
			return false;
		}
		return true;
	}

}