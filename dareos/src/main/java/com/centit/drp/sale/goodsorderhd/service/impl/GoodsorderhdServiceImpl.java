/**
 * prjName:营销平台
 * ucName:订货订单维护
 * fileName:GoodsorderhdServiceImpl
 */
package com.centit.drp.sale.goodsorderhd.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.base.chann.mapper.ChannMapper;
import com.centit.common.service.BookkeepingService;
import com.centit.common.service.FlowService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.shopcar.mapper.ShopCarMapper;
import com.centit.drp.sale.advcorder.mapper.AdvcorderMapper;
import com.centit.drp.sale.goodsorderhd.mapper.GoodsorderhdMapper;
import com.centit.drp.sale.goodsorderhd.model.GoodsorderhdModel;
import com.centit.drp.sale.goodsorderhd.model.GoodsorderhdModelChld;
import com.centit.drp.sale.goodsorderhd.service.GoodsorderhdService;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.erp.sale.saleorder.service.SaleorderService;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
/**
 * *@订货订单维护
 * *@func
 */
@Service
public class GoodsorderhdServiceImpl  implements GoodsorderhdService {

	@Autowired
	private GoodsorderhdMapper mapper;
	@Autowired
	private ShopCarMapper shopcarMapper;
	@Autowired
	private AdvcorderMapper advcorderMapper;
	@Autowired
	private ChannMapper channMapper;

	@Autowired
	private FlowService flowService;
	@Autowired
	private SaleorderService saleorderService;
	@Autowired
	BookkeepingService bookkeepService;
	/**
	 * @查询
	 * @param params map
	 * @param pageNo
	 *
	 * @return the ilistpage
	 */
	@Override
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc) {
		Page<Map<String, String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
		mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	/**
	 * * 增加
	 * * @param params
	 *
	 * @return true, if tx insert
	 */
	@Transactional
	public boolean txInsert(Map<String,String> params) {

		mapper.insert(params);
		return true;
	}
	/**
	 * @删除
	 * @param GOODS_ORDER_ID
	 * @param userBean
	 * @return true, if tx delete
	 */
	@Override
	@Transactional
	public boolean txDelete(Map <String, String> params, UserBean userBean) {
		//删除父
		mapper.delete(params);
		//删除子表
		mapper.delChldByFkId(params);
		String erjiOrderId = params.get("FROM_BILL_ID");
		if (!StringUtil.isEmpty(erjiOrderId)) {
			// 原单据-退回到待转总部
			Map<String, Object> entry = this.txLoadForUpdate(erjiOrderId);
			String erjiOrderNo = StringUtil.nullToSring(entry.get("GOODS_ORDER_NO"));
			String flowServiceId = StringUtil.nullToSring(entry.get("ORDER_TRACE_ID"));
			flowService.backFlow(erjiOrderId, erjiOrderNo, flowServiceId, BusinessConsts.GOOD_FLOW_NO_SUB, userBean, "");
		}
		return true;
	}

	/**
	 * * update data
	 * * @param params
	 *
	 *
	 * @return true, if tx update by id
	 */
	@Override
	@Transactional
	public void txUpdateById(Map<String,String> params) {
		mapper.updateById(params);
	}

	/**
	 * @编辑
	 * @Description :
	 * @param GOODS_ORDER_ID
	 * @param GoodsorderhdModel
	 * @param userBean.
	 * @return true, if tx txEdit
	 */
	@Override
	@Transactional
	public String txEdit(String GOODS_ORDER_ID, GoodsorderhdModel model,List<GoodsorderhdModelChld> chldList,
			UserBean userBean,String REBATEDSCT,String erjiOrderId, String order) {
		Map<String,String> params = new HashMap<String,String>();
		String IS_USE_REBATE = "";
		//String BILL_TYPE = "";
		if(null != model){
			IS_USE_REBATE = model.getIS_USE_REBATE(); // 是否使用返利
			/*Map<String,String> wareaMap=shopcarMapper.getWareaInfo(model.getORDER_CHANN_ID());
			params.putAll(wareaMap);*/
			params.put("ORDER_CHANN_ID", model.getORDER_CHANN_ID());
			params.put("ORDER_CHANN_NO", model.getORDER_CHANN_NO());
			params.put("ORDER_CHANN_NAME", model.getORDER_CHANN_NAME());
			params.put("RECV_CHANN_ID", model.getRECV_CHANN_ID());
			params.put("RECV_CHANN_NO", model.getRECV_CHANN_NO());
			params.put("RECV_CHANN_NAME", model.getRECV_CHANN_NAME());
			params.put("CHANN_ID", model.getCHANN_ID());
			params.put("CHANN_NO", model.getCHANN_NO());
			params.put("CHANN_NAME", model.getCHANN_NAME());
			params.put("PERSON_CON", model.getPERSON_CON());
			params.put("TEL", model.getTEL());
			params.put("RECV_ADDR", model.getRECV_ADDR());
			params.put("REMARK", model.getREMARK());//经销商备注
			params.put("REMARK2", model.getREMARK2());//订单员备注
			params.put("CHANN_NAME", model.getCHANN_NAME());
			params.put("BILL_TYPE", model.getBILL_TYPE());
			params.put("ORDER_DATE", model.getORDER_DATE());
			params.put("PRINT_NAME", model.getPRINT_NAME());
			params.put("CUST_NAME", model.getCUST_NAME());
			params.put("CUST_TEL", model.getCUST_TEL());
			params.put("CUST_ADDR", model.getCUST_ADDR());
			params.put("ORDER_DELIVERY_DATE", model.getORDER_DELIVERY_DATE());//订单交期
			params.put("PRE_RECV_DATE", model.getPRE_RECV_DATE());
			params.put("TRANSPORT_SETTLE", model.getTRANSPORT_SETTLE());
			params.put("TRANSPORT", model.getTRANSPORT());
			params.put("LEDGER_ID",model.getLEDGER_ID());
			params.put("LEDGER_NAME",model.getLEDGER_NAME());
			params.put("SALE_ID", model.getSALE_ID());
			params.put("SALE_NAME", model.getSALE_NAME());
			params.put("SALEDEPT_NAME", model.getSALEDEPT_NAME());
			params.put("SALEDEPT_ID", model.getSALEDEPT_ID());
			params.put("FROM_BILL_ID", model.getFROM_BILL_ID());
			params.put("TOTAL_REBATE", StringUtil.nullToZero(model.getTOTAL_REBATE()));
			params.put("TOTAL_AMOUNT", StringUtil.nullToZero(model.getTOTAL_AMOUNT()));
			if(StringUtil.isEmpty(IS_USE_REBATE)){
				params.put("IS_USE_REBATE", "0");
			}else{
				params.put("IS_USE_REBATE", model.getIS_USE_REBATE());
			}
			params.put("ORDER_RECV_DATE", model.getORDER_RECV_DATE());//要求到货日期
			params.put("AREA_ID", model.getAREA_ID());
			params.put("AREA_NO", model.getAREA_NO());
			params.put("AREA_NAME", model.getAREA_NAME());
			params.put("RECV_ADDR_NO", model.getRECV_ADDR_NO());//收货地址编号
			if(StringUtil.isEmpty(GOODS_ORDER_ID)){ // 新增
				GOODS_ORDER_ID = StringUtil.uuid32len();
				params.put("GOODS_ORDER_ID", GOODS_ORDER_ID);
				String GOODS_ORDER_NO = LogicUtil.getBmgz("GOODS_ORDER_NO_SEQ");
				//String GOODS_ORDER_NO = model.getGOODS_ORDER_NO();
				// 判断单号是否重复
				int res = shopcarMapper.judgeGoodNo(GOODS_ORDER_NO);
				if(res >= 1){
					throw new ServiceException("单号重复了，请联系管理员");
				}
				params.put("GOODS_ORDER_NO", GOODS_ORDER_NO);
				params.put("STATE",BusinessConsts.GOODSORDER_STATE_CG);//草稿
				params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);
				params.put("CRE_NAME", userBean.getXM());
				params.put("CREATOR", userBean.getXTYHID());
				params.put("UPD_NAME", userBean.getXM());
				params.put("UPDATOR", userBean.getXTYHID());
				params.put("DEPT_ID", userBean.getBMXXID());
				params.put("DEPT_NAME", userBean.getBMMC());

				if (StringUtil.nullToSring(order).equals("order")) {
					params.put("ORG_ID", model.getCHANN_ID());
					params.put("ORG_NAME", model.getCHANN_NAME());
				} else {
					params.put("ORG_ID", userBean.getJGXXID());
					params.put("ORG_NAME", userBean.getJGMC());
					params.put("CHANN_ID", userBean.getCHANN_ID());
					params.put("CHANN_NO", userBean.getCHANN_NO());
					params.put("CHANN_NAME", userBean.getCHANN_NAME());
					params.put("CHANN_ID_P", userBean.getCHANN_ID_P());
					params.put("CHANN_NO_P", userBean.getCHANN_NO_P());
					params.put("CHANN_NAME_P", userBean.getCHANN_NAME_P());
				}
				txInsert(params);

				// 流程跟踪
				//String flowNo = LogicUtil.getFlowNo(userBean);
				String flowType = LogicUtil.getFlowType(userBean);
				String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), flowType);
				flowService.insertFirstFlow(flowNo, userBean, params.get("GOODS_ORDER_NO"));

				// 下级订货处理 转总部订单
				if (!StringUtil.isEmpty(erjiOrderId)) {
					Map<String, Object> entry = this.txLoadForUpdate(GOODS_ORDER_ID);
					// 新单据提交-审图中
					String flowServiceId = StringUtil.nullToSring(entry.get("ORDER_TRACE_ID"));
					//flowService.insertNextFlow(GOODS_ORDER_ID, GOODS_ORDER_NO, flowServiceId, BusinessConsts.GOOD_FLOW_NO, userBean);

					GoodsorderhdModel model_new = (GoodsorderhdModel) LogicUtil.tranMap2Bean(entry, GoodsorderhdModel.class);
					txCommit(GOODS_ORDER_ID, model_new, userBean);

					// 原单据-已转总部
					entry = this.txLoadForUpdate(erjiOrderId);
					String erjiOrderNo = StringUtil.nullToSring(entry.get("GOODS_ORDER_NO"));
					flowServiceId = StringUtil.nullToSring(entry.get("ORDER_TRACE_ID"));
					flowService.insertNextFlow(erjiOrderId, erjiOrderNo, flowServiceId, BusinessConsts.GOOD_FLOW_NO_SUB, userBean, "");
				}
			} else{
				params.put("UPD_NAME",userBean.getXM());
				params.put("UPDATOR",userBean.getXTYHID());
				params.put("UPD_TIME","sysdate");
				params.put("GOODS_ORDER_ID", GOODS_ORDER_ID);

				if (StringUtil.nullToSring(order).equals("order")) {
					params.put("ORG_ID", model.getCHANN_ID());
					params.put("ORG_NAME", model.getCHANN_NAME());
				}
				txUpdateById(params);
				/* clearCaches(GOODS_ORDER_ID); */
			}

			// 保存上传的文件
			String attPath = model.getAttPath();
			if (!StringUtil.isEmpty(attPath)) {
				InterUtil.delByFromId(GOODS_ORDER_ID);
				String[] arr = attPath.split(",");
				InterUtil.insertAttPath(Arrays.asList(arr), userBean, GOODS_ORDER_ID);
			}
		}

		//子表信息保存
		if (null != chldList && !chldList.isEmpty()) {
			Map<String,String> paramsMap = new HashMap<String,String>();
			paramsMap.put("IS_USE_REBATE", IS_USE_REBATE);
			paramsMap.put("REBATEDSCT", REBATEDSCT);
			paramsMap.put("erjiOrderId", erjiOrderId);
			txChildEdit(GOODS_ORDER_ID, chldList,userBean,null,paramsMap,"edit");
		}
		return GOODS_ORDER_ID;
	}

	@Override
	@Transactional
	public String txEditToCommit(String GOODS_ORDER_ID, GoodsorderhdModel model,List<GoodsorderhdModelChld> chldList,
			UserBean userBean,String REBATEDSCT,String erjiOrderId, String order) {
		GOODS_ORDER_ID = txEdit(GOODS_ORDER_ID, model, chldList, userBean, REBATEDSCT, erjiOrderId, order);
		Map<String, Object> entry = txLoadForUpdate(GOODS_ORDER_ID);
		model = (GoodsorderhdModel) LogicUtil.tranMap2Bean(entry, GoodsorderhdModel.class);
		txCommit(GOODS_ORDER_ID, model, userBean);
		return GOODS_ORDER_ID;
	}

	/**
	 * @详细
	 * @param param GOODS_ORDER_ID
	 * @param param the param
	 *
	 * @return the map< string, string>
	 */
	@Override
	public Map<String,Object> load(String param) {
		return mapper.loadById(param);
	}

	public GoodsorderhdModel loadById(String param) throws Exception{
		GoodsorderhdModel model = (GoodsorderhdModel)LogicUtil.tranMap2Bean(mapper.loadById(param), GoodsorderhdModel.class);
		return model;
	}


	/**
	 * 查询 锁住此行
	 * @param GOODS_ORDER_ID
	 * @return
	 */
	@Override
	@Transactional
	public Map<String,Object> txLoadForUpdate(String GOODS_ORDER_ID){
		return mapper.loadByIdForUpdate(GOODS_ORDER_ID);
	}

	/**
	 * 子 保存
	 * @param GOODS_ORDER_ID 订单ID
	 * @param modelList 明细list
	 * @param ORDER_RECV_DATE 交期
	 * @param IS_USE_REBATE 是否使用返利 1->使用
	 * @return
	 */
	@Override
	@Transactional
	public boolean txChildEdit(String GOODS_ORDER_ID,List<GoodsorderhdModelChld> chldList,
			UserBean userBean,String ORDER_RECV_DATE,Map<String,String> params,String action) {
		@SuppressWarnings("unused")
		String SPCL_TECH_IDS="";
		Map<String,String>paramMap = new HashMap<String,String>();
		paramMap.put("GOODS_ORDER_ID", GOODS_ORDER_ID);
		//int ROW_NO = mapper.queryMaxRowNo(paramMap);
		//使用返利
		//String IS_USE_REBATE = params.get("IS_USE_REBATE");
		//新增列表
		List <Map <String, Object>> addList = new ArrayList <Map <String, Object>>();
		//修改列表
		List <Map <String, Object>> updateList = new ArrayList <Map <String, Object>>();
		for (GoodsorderhdModelChld model : chldList) {
			Map <String, Object> child = new HashMap <String, Object>();
			child.put("GROUP_NO",model.getGROUP_NO());
			child.put("PRD_ID",model.getPRD_ID());
			child.put("PRD_NO",model.getPRD_NO());
			child.put("PRD_NAME",model.getPRD_NAME());
			child.put("PRD_SPEC",model.getPRD_SPEC());
			child.put("PRD_COLOR",model.getPRD_COLOR());
			child.put("BRAND",model.getBRAND());
			child.put("STD_UNIT",model.getSTD_UNIT());
			child.put("HOLE_SPEC",model.getHOLE_SPEC());
			child.put("PRD_SIZE",model.getPRD_SIZE());
			child.put("PRD_QUALITY",model.getPRD_QUALITY());
			child.put("PRD_COLOR",model.getPRD_COLOR());
			child.put("PRD_TOWARD",model.getPRD_TOWARD());
			child.put("PRD_GLASS",model.getPRD_GLASS());
			child.put("PRD_OTHER",model.getPRD_OTHER());
			child.put("PRD_SERIES",model.getPRD_SERIES());
			child.put("PROJECTED_AREA",model.getPROJECTED_AREA());
			child.put("EXPAND_AREA",model.getEXPAND_AREA());
			child.put("PRD_PLACE",model.getPRD_PLACE());
			child.put("IS_NO_REBATE_FLAG",model.getIS_NO_REBATE_FLAG());//是否返利
			child.put("IS_NO_LOCK_FLAG",model.getIS_NO_LOCK_FLAG());//是否开锁孔
			child.put("PRICE",model.getPRICE());
			child.put("DECT_RATE",model.getDECT_RATE());
			child.put("DECT_PRICE",model.getDECT_PRICE());
			child.put("ORDER_NUM",model.getORDER_NUM());
			child.put("ORDER_AMOUNT",model.getORDER_AMOUNT());
			child.put("REBATE_AMOUNT",model.getREBATE_AMOUNT());
			child.put("REBATE_PRICE",model.getREBATE_PRICE());
			child.put("ORDER_RECV_DATE",model.getORDER_RECV_DATE());//要求到货日期
			child.put("ROW_NO", model.getROW_NO());//行号
			child.put("REMARK", model.getREMARK());//备注
			child.put("GOODS_ORDER_ID",GOODS_ORDER_ID);

			String REBATE_AMOUNT = StringUtil.nullToZero(model.getREBATE_AMOUNT());
			if(!BusinessConsts.INTEGER_0.equals(REBATE_AMOUNT)){
				//返利金额
				BigDecimal amount = new BigDecimal(StringUtil.nullToZero(model.getREBATE_AMOUNT()));
				//订货数量
				BigDecimal num = new BigDecimal(StringUtil.nullToZero(model.getORDER_NUM()));
				if(BigDecimal.ZERO.equals(num)){
					break;
				}
				// 返利单价=返利金额÷订货数量
				BigDecimal REBATE_PRICE = amount.divide(num, BigDecimal.ROUND_HALF_UP);
				child.put("REBATE_PRICE",REBATE_PRICE.toString());//返利单价
			} else {
				child.put("DECT_RATE", model.getDECT_RATE());
				child.put("DECT_PRICE",model.getDECT_PRICE());
				child.put("ORDER_AMOUNT", model.getORDER_AMOUNT());
			}

			String SPCL_TECH_ID = model.getSPCL_TECH_ID();
			if (!StringUtil.isEmpty(SPCL_TECH_ID)) {
				SPCL_TECH_IDS += "'" + SPCL_TECH_ID + "',";
			}
			child.put("SPCL_TECH_ID", SPCL_TECH_ID);
			child.put("OLD_SPCL_TECH_ID", model.getOLD_SPCL_TECH_ID());
			// 如果没有明细ID的则是新增，有的是修改
			if (StringUtil.isEmpty(model.getGOODS_ORDER_DTL_ID()) ) {
				child.put("GOODS_ORDER_DTL_ID", StringUtil.uuid32len());
				child.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
				child.put("IS_BACKUP_FLAG",BusinessConsts.INTEGER_0);//是否抵库标记,默认设置未0
				child.put("OLD_ORDER_NUM", model.getORDER_NUM());
				addList.add(child);
			} else {
				child.put("GOODS_ORDER_DTL_ID", model.getGOODS_ORDER_DTL_ID());
				updateList.add(child);
			}

			// 保存上传的文件
			String attPath = model.getAttPath();
			if (!StringUtil.isEmpty(attPath)) {
				String dtlId = StringUtil.nullToSring(child.get("GOODS_ORDER_DTL_ID"));
				InterUtil.delByFromId(dtlId);
				String[] arr = attPath.split(",");
				InterUtil.insertAttPath(Arrays.asList(arr), userBean, StringUtil.nullToSring(dtlId));
			}
		}


		if (!updateList.isEmpty()) {

			mapper.updateChldById(updateList);
		}
		if (!addList.isEmpty()) {

			mapper.insertChld(addList);
		}

		if(!StringUtil.isEmpty(ORDER_RECV_DATE)){
			//更新主表的 交期
			//明细最近的交期  如：2014-7-8和 2014-7-9，取2014-7-8的插到表里
			Map<String,String> paramsMap = new HashMap<String,String>();
			paramsMap.put("ORDER_RECV_DATE", ORDER_RECV_DATE);
			paramsMap.put("GOODS_ORDER_ID", GOODS_ORDER_ID);
			txUpdateById(paramsMap);
		}

		return true;
	}

	/**
	 * * 根据主表Id查询子表记录
	 * @param GOODS_ORDER_ID the GOODS_ORDER_ID
	 *
	 * @return the list< new master slavemx model>
	 */
	@Override
	public List <Map<String, String>> childQuery(String GOODS_ORDER_ID) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("GOODS_ORDER_ID", GOODS_ORDER_ID);
		//只查询0的记录。1为删除。0为正常
		params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);
		return mapper.queryChldByFkId(params);
	}

	/**
	 * * 根据子表Id批量加载子表信息
	 * @param GOODS_ORDER_DTL_IDs the GOODS_ORDER_DTL_IDs
	 *
	 * @return the list< new master slavemx model>
	 */
	@Override
	public List <GoodsorderhdModelChld> loadChilds(Map <String, String> params) {
		return mapper.loadChldByIds(params);
	}

	/**
	 * * 子表批量删除:软删除
	 *
	 * @param GOODS_ORDER_DTL_IDs the GOODS_ORDER_DTL_IDs
	 */
	@Override
	@Transactional
	public void txBatch4DeleteChild(String GOODS_ORDER_DTL_IDs) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("GOODS_ORDER_DTL_IDS", GOODS_ORDER_DTL_IDs);
		params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_DROP);

		mapper.deleteChldByIds(params);
	}

	/**
	 * 加载渠道
	 * @param param CHANN_ID
	 * @return the map< string, string>
	 */
	@Override
	public Map<String, String> getChann(String CHANN_ID){

		return channMapper.loadById(CHANN_ID);
	}

	/**
	 * 查询货品的 折扣率
	 * @param AREA_ID 区域ID
	 * @param PRD_ID 货品ID
	 * @return
	 */
	@Override
	public Map<String,String> getRateByAreaIdPId(String AREA_ID,String PRD_ID){
		Map<String, String> params = new HashMap<String, String>();
		params.put("AREA_ID", AREA_ID);
		params.put("PRD_ID", PRD_ID);
		return mapper.getRate(params);
	}

	@Override
	public Map<String,String> queryTotal(String GOODS_ORDER_ID) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("GOODS_ORDER_ID", GOODS_ORDER_ID);
		map.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);
		return mapper.queryTotal(map);
	}

	/**
	 * 提交
	 * @param selRowId 订货订单ID
	 * @param BILL_TYPE 单据类型
	 * @param GOODS_ORDER_NO 订货订单NO
	 * @param flowServiceId 流程ID
	 * @param userBean
	 */
	@Override
	@Transactional
	public void txCommit(String selRowId,String BILL_TYPE,String GOODS_ORDER_NO,String flowServiceId, UserBean userBean) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put("GOODS_ORDER_ID", selRowId);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		params.put("ORDER_DATE", BusinessConsts.UPDATE_TIME);

		// 流程流转
		@SuppressWarnings("deprecation")
		String flowNo = LogicUtil.getFlowNo(userBean);
		flowService.insertNextFlow(selRowId, GOODS_ORDER_NO, flowServiceId, flowNo, userBean, "");

		// 更新下单时间
		mapper.updateById(params);

		//修改订货订单交期
		//		upAdvcOrder(GOODS_ORDER_ID);
	}
	@Override
	@Transactional
	public void txCommit(String selRowId,GoodsorderhdModel model, UserBean userBean) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("GOODS_ORDER_ID", selRowId);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		params.put("ORDER_DATE", BusinessConsts.UPDATE_TIME);

		// 流程流转
		String flowType = LogicUtil.getFlowType(userBean);
		String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), flowType);
		flowService.insertNextFlow(selRowId, model.getGOODS_ORDER_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, "");

		// 更新下单时间
		mapper.updateById(params);
	}




	/**
	 * 撤销
	 * @param selRowId 订货订单ID
	 * @param GOODS_ORDER_NO 订货订单NO
	 * @param flowServiceId 流程ID
	 * @param userBean
	 */
	@Override
	@Transactional
	public void revoke(String selRowId, String GOODS_ORDER_NO, String flowServiceId, UserBean userBean){
		Map<String, String> params = new HashMap<String, String>();
		params.put("GOODS_ORDER_ID", selRowId);
		params.put("UPDATOR",userBean.getXTYHID());
		params.put("UPD_NAME",userBean.getXM());
		params.put("UPD_TIME",BusinessConsts.UPDATE_TIME);
		params.put("STATE",BusinessConsts.REVOKE);//撤销后 状态为‘撤销’

		mapper.updateById(params);
	}

	/**
	 * 根据单据主键获取该单据附件地址
	 * @param pkId
	 * @return
	 */
	@Override
	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getAttPath(String pkId){
		String attPath = "";
		List<Map<String, String>> attList = InterUtil.findAttInfo(pkId);
		for (Iterator itAtt = attList.iterator(); itAtt.hasNext();) {
			Map<String, String> map = (Map<String, String>) itAtt.next();
			attPath += map.get("ATT_PATH");
			attPath += ",";
		}
		if (attPath.length() > 0) {
			attPath = attPath.substring(0, attPath.length()-1);
		}
		return attPath;
	}

	/**
	 * 下级订货处理-转总部订货订单
	 * @param selRowId 订货订单ID
	 * @param GOODS_ORDER_NO 订货订单NO
	 * @param flowServiceId 流程ID
	 * @param userBean
	 */
	@Override
	@Transactional
	public void turnToHQ(String selRowId,String GOODS_ORDER_NO,String flowServiceId, UserBean userBean){
		// 流程流转
		flowService.insertNextFlow(selRowId, GOODS_ORDER_NO, flowServiceId, BusinessConsts.GOOD_FLOW_NO_SUB, userBean, "");

		Map<String, String> params = new HashMap<String, String>();
		params.put("GOODS_ORDER_ID", selRowId);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		mapper.updateById(params);
	}

	/**
	 * 订货处理-审图
	 * @param selRowId 订货订单ID
	 * @param obj {@link GoodsorderhdModel}
	 * @param chldList {@link List<GoodsorderhdModelChld>}
	 * @param userBean {@link UserBean}
	 * @param option {审图-audit,报价-quote}
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void txAudit(String selRowId, GoodsorderhdModel model,
			List<GoodsorderhdModelChld> chldList, UserBean userBean,
			String option) {
		if ("audit".equals(option) ) {
			// 保存主表上传的文件
			String attPath = model.getAttPath();
			if (!StringUtil.isEmpty(attPath)) {
				InterUtil.delByFromId(selRowId);
				String[] arr = attPath.split(",");
				InterUtil.insertAttPath(Arrays.asList(arr), userBean, selRowId);
			}
		}
		if ("quote".equals(option)) {
			// 保存报价
			Map<String,String> params = new HashMap<String, String>();
			params.put("TOTAL_AMOUNT", model.getTOTAL_AMOUNT());
			params.put("TOTAL_REBATE", model.getTOTAL_REBATE());
			params.put("GOODS_ORDER_ID", selRowId);
			params.put("BILL_TYPE", model.getBILL_TYPE());
			params.put("REMARK2", model.getREMARK2());
			params.put("RETURN_RSON", model.getAuditContents());
			txUpdateById(params);

		}
		txChildAudit(selRowId, chldList, userBean, option);

		if (!"Z".equals(model.getAuditStatus())) {
			// 流程跟踪
			String flowType = "0";//LogicUtil.getFlowType(userBean);
			String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), flowType);
			if ("T".equals(model.getAuditStatus())) {
				flowService.insertNextFlow(selRowId, model.getGOODS_ORDER_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, model.getAuditContents());
			} else {
				flowService.backFlow(selRowId, model.getGOODS_ORDER_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, model.getAuditContents());
			}
		}
	}

	/**
	 * 订货处理-审图(明细)
	 * @param GOODS_ORDER_ID 订单ID
	 * @param chldList 明细list
	 * @return
	 */
	@Transactional
	public boolean txChildAudit(String GOODS_ORDER_ID,
			List<GoodsorderhdModelChld> chldList, UserBean userBean, String option) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("GOODS_ORDER_ID", GOODS_ORDER_ID);
		// 新增列表
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		// 修改列表
		List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
		for (GoodsorderhdModelChld model : chldList) {
			Map<String, Object> child = new HashMap<String, Object>();
			child.put("GROUP_NO", model.getGROUP_NO());
			child.put("PRD_ID", model.getPRD_ID());
			child.put("PRD_NO", model.getPRD_NO());
			child.put("PRD_NAME", model.getPRD_NAME());
			child.put("PRD_SPEC", model.getPRD_SPEC());
			child.put("PRD_COLOR", model.getPRD_COLOR());
			child.put("BRAND", model.getBRAND());
			child.put("STD_UNIT", model.getSTD_UNIT());
			child.put("HOLE_SPEC", model.getHOLE_SPEC());
			child.put("PRD_SIZE", model.getPRD_SIZE());
			child.put("PRD_QUALITY", model.getPRD_QUALITY());
			child.put("PRD_COLOR", model.getPRD_COLOR());
			child.put("PRD_TOWARD", model.getPRD_TOWARD());
			child.put("PRD_GLASS", model.getPRD_GLASS());
			child.put("PRD_OTHER", model.getPRD_OTHER());
			child.put("PRD_SERIES", model.getPRD_SERIES());
			child.put("PROJECTED_AREA",model.getPROJECTED_AREA());
			child.put("EXPAND_AREA",model.getEXPAND_AREA());
			child.put("PRD_PLACE",model.getPRD_PLACE());
			child.put("IS_NO_REBATE_FLAG",model.getIS_NO_REBATE_FLAG());//是否返利
			child.put("IS_NO_LOCK_FLAG",model.getIS_NO_LOCK_FLAG());//是否开锁孔
			child.put("PRICE", model.getPRICE());
			child.put("DECT_RATE", model.getDECT_RATE());
			child.put("DECT_PRICE", model.getDECT_PRICE());
			child.put("ORDER_NUM", model.getORDER_NUM());
			child.put("ORDER_AMOUNT", model.getORDER_AMOUNT());
			child.put("REBATE_AMOUNT", model.getREBATE_AMOUNT());
			child.put("REBATE_PRICE",model.getREBATE_PRICE());
			child.put("ORDER_RECV_DATE", model.getORDER_RECV_DATE());// 要求到货日期
			child.put("ROW_NO", model.getROW_NO());//行号
			child.put("REMARK", model.getREMARK());// 备注
			child.put("GOODS_ORDER_ID", GOODS_ORDER_ID);

			String REBATE_AMOUNT = StringUtil.nullToZero(model.getREBATE_AMOUNT());
			if(!BusinessConsts.INTEGER_0.equals(REBATE_AMOUNT)){
				//返利金额
				BigDecimal amount = new BigDecimal(StringUtil.nullToZero(model.getREBATE_AMOUNT()));
				//订货数量
				BigDecimal num = new BigDecimal(StringUtil.nullToZero(model.getORDER_NUM()));

				// 返利单价=返利金额÷订货数量
				BigDecimal REBATE_PRICE = amount.divide(num, BigDecimal.ROUND_HALF_UP);
				child.put("REBATE_PRICE",REBATE_PRICE.toString());//返利单价
			}

			// 如果没有明细ID的则是新增，有的是修改
			if (StringUtil.isEmpty(model.getGOODS_ORDER_DTL_ID())) {
				child.put("GOODS_ORDER_DTL_ID", StringUtil.uuid32len());
				child.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
				child.put("IS_BACKUP_FLAG", BusinessConsts.INTEGER_0);// 是否抵库标记,默认设置未0
				child.put("OLD_ORDER_NUM", model.getORDER_NUM());
				addList.add(child);
			} else {
				child.put("GOODS_ORDER_DTL_ID", model.getGOODS_ORDER_DTL_ID());
				updateList.add(child);
			}

			if ("audit".equals(option)) {
				// 保存上传的文件
				String attPath = model.getAttPath();
				if (!StringUtil.isEmpty(attPath)) {
					String dtlId = StringUtil.nullToSring(child.get("GOODS_ORDER_DTL_ID"));
					InterUtil.delByFromId(dtlId);
					String[] arr = attPath.split(",");
					InterUtil.insertAttPath(Arrays.asList(arr), userBean, dtlId);
				}
			}
		}

		if ("quote".equals(option)) {
			if (!updateList.isEmpty()) {
				mapper.updateChldById(updateList);
			}
			if (!addList.isEmpty()) {
				mapper.insertChld(addList);
			}
		}
		return true;
	}

	/**
	 * 订货处理-确认报价
	 * @param selRowId 订货订单ID
	 * @param model {@link GoodsorderhdModel}
	 * @param option [confirmquote:经销商确认报价，更新订单交期为确认日期往后推40天]
	 * @param userBean {@link UserBean}
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void txConfirm(String selRowId, GoodsorderhdModel model, String option, UserBean userBean) {
		Map<String, String> params = new HashMap<String, String>();
		// 流程跟踪
		String flowType = "0";//LogicUtil.getFlowType(userBean);
		String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), flowType);
		if ("T".equals(model.getAuditStatus())) {
			flowService.insertNextFlow(selRowId, model.getGOODS_ORDER_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, model.getAuditContents());
			//params.put("confirmstatus", option);
		} else {
			// 退回到报价中
			flowService.backFlow(selRowId, model.getGOODS_ORDER_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, BusinessConsts.GOODSORDER_STATE_BJZ, model.getAuditContents());
		}

		params.put("GOODS_ORDER_ID", selRowId);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		params.put("RETURN_RSON", model.getAuditContents());
		mapper.updateById(params);

		// 经销商确认订单后 冻结要货单金额
		if("confirmquote".equals(option)&&"T".equals(model.getAuditStatus())){
			bookkeepService.goodsOrderFrozenAmount(selRowId, userBean.getXM());
		}
		// 领导审核退回到经销商确认时 删除冻结
		if("confirm".equals(option)&&"F".equals(model.getAuditStatus())){
			bookkeepService.releaseGoodsFreezBookkeeping(selRowId);
		}
	}

	/**
	 * 下级订货处理-退回
	 * @param selRowId 订货订单ID
	 * @param GOODS_ORDER_NO 订货订单NO
	 * @param RETURN_RSON 退回原因
	 * @param flowServiceId 流程ID
	 * @param userBean
	 */
	@Override
	@Transactional
	public void back(String selRowId,String GOODS_ORDER_NO, String RETURN_RSON, String flowServiceId, UserBean userBean){
		// 流程流转
		flowService.backFlow(selRowId, GOODS_ORDER_NO, flowServiceId, BusinessConsts.GOOD_FLOW_NO_SUB, userBean, RETURN_RSON);

		Map<String, String> params = new HashMap<String, String>();
		params.put("GOODS_ORDER_ID", selRowId);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		params.put("RETURN_RSON", RETURN_RSON);
		mapper.updateById(params);
	}

	/**
	 * 订货处理-退回
	 * @param selRowId 订货订单ID
	 * @param GOODS_ORDER_NO 订货订单NO
	 * @param RETURN_RSON 退回原因
	 * @param flowServiceId 流程ID
	 * @param userBean
	 */
	@Override
	@Transactional
	public void txBack(String selRowId,String GOODS_ORDER_NO, String RETURN_RSON, String flowServiceId, UserBean userBean){
		// 流程流转
		flowService.backFlow(selRowId, GOODS_ORDER_NO, flowServiceId, BusinessConsts.GOOD_FLOW_NO, userBean, RETURN_RSON);

		Map<String, String> params = new HashMap<String, String>();
		params.put("GOODS_ORDER_ID", selRowId);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		params.put("RETURN_RSON", RETURN_RSON);
		mapper.updateById(params);
	}

	@Override
	@Transactional
	public void txBack(String selRowId, GoodsorderhdModel model, UserBean userBean) {
		String stateCurr = model.getSTATE();
		String RETURN_RSON = model.getRETURN_RSON();
		// 流程流转
		String flowType = "0";//LogicUtil.getFlowType(userBean);
		String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), flowType);
		flowService.backFlow(selRowId, model.getGOODS_ORDER_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, RETURN_RSON);

		Map<String, String> params = new HashMap<String, String>();
		params.put("GOODS_ORDER_ID", selRowId);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		params.put("RETURN_RSON", RETURN_RSON);
		mapper.updateById(params);

		if (BusinessConsts.GOODSORDER_STATE_LDDQR.equals(stateCurr)) {
			// 从订单审核退回经销商确认报价，释放冻结
			bookkeepService.releaseGoodsFreezBookkeeping(selRowId);
		} else {

		}
	};

	/**
	 * 订货处理-生成销售订单
	 * @param GOODS_ORDER_ID 订货订单ID
	 * @param model {@link GoodsorderhdModel}
	 * @param xsddModel {@link SaleorderModel}
	 * @param parentModelList {@link List<SaleorderModel>}
	 * @param userBean {@link UserBean}
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void txCrexsdd(String GOODS_ORDER_ID, GoodsorderhdModel model, SaleorderModel xsddModel, List<SaleorderModel> parentModelList, UserBean userBean) {
		// 修改订货订单信息
		Map<String, String> params = new HashMap<String, String>();
		params.put("GOODS_ORDER_ID", GOODS_ORDER_ID);
		params.put("ORDER_DELIVERY_DATE", model.getORDER_DELIVERY_DATE());
		params.put("PRE_RECV_DATE", model.getPRE_RECV_DATE());
		this.txUpdateById(params);
		bookkeepService.releaseGoodsFreezBookkeeping(GOODS_ORDER_ID);
		for (SaleorderModel saleorderModel : parentModelList) {
			if (saleorderModel.getChildList() != null && !saleorderModel.getChildList().isEmpty()) {
				saleorderService.txEdit(saleorderModel.getSALE_ORDER_ID(), saleorderModel, saleorderModel.getChildList(), userBean);
			}
		}


		// 流程跟踪
		String flowType = "0";//LogicUtil.getFlowType(userBean);
		String flowNo = flowService.getFlowNoByLedger(xsddModel.getLEDGER_ID(), flowType);
		flowService.insertNextMultipleFlow(GOODS_ORDER_ID,  xsddModel.getFROM_BILL_NO(), xsddModel.getORDER_TRACE_ID(), flowNo, userBean, "");

	}

	//-- 0156143--Start--刘曰刚--2014-06-16//
	/**
	 * 根据当前登录人所属渠道获取渠道折扣率
	 */
	@Override
	public String getChannDiscount(String CHANN_ID){
		Object obj=mapper.getChannDiscount(CHANN_ID);
		if(null!=obj){
			return obj.toString();
		}
		return null;
	}

	/**
	 * 查询预订单明细
	 * @return
	 */
	@Override
	public List<Map<String,Object>> toQuertAvdcDtl(Map<String,String>params){
		return mapper.toQuertAvdcDtl(params);
	}
	//修改订货订单交期
	@Transactional
	public void upAdvcOrder(String GOODS_ORDER_ID){

		mapper.upAdvcOrder(GOODS_ORDER_ID);
	}


	/**
	 * 销售订单退回到预订单
	 * @param saleOrderId
	 * @param userBean
	 */
	@Override
	@Transactional
	public void saleBackGoods(String goodsOrderId,UserBean userBean){
		// 根据销售订单获取要来源要货单生成的销售订单集
		List<Map<String,String>> saleList = mapper.getSalesById(goodsOrderId);
		String goodsOrderNo = "";
		String orderTraceId = "";
		String ledgerId = "";
		for (int i = 0; i < saleList.size(); i++) {
			if(i==0){
				goodsOrderNo = saleList.get(i).get("FROM_BILL_NO");
				orderTraceId = saleList.get(i).get("ORDER_TRACE_ID");
				ledgerId = saleList.get(i).get("LEDGER_ID");
			}
			// 校验是否有销售订单状态不是待处理
			if(!BusinessConsts.WAIT_DEAL.equals(saleList.get(i).get("STATE"))){
				throw new ServiceException("厂编："+saleList.get(i).get("FACTORY_NO")+"已流转到后续操作，请撤回后重新退回！");
			}
			//删除销售订单
			Map<String,String> params= new HashMap<String, String>();
			params.put("SALE_ORDER_ID", saleList.get(i).get("SALE_ORDER_ID"));
			params.put("UPD_NAME", userBean.getXM());
			params.put("UPDATOR", userBean.getXTYHID());
			params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_DROP);
			saleorderService.txDelete(params);
		}
		//修改要货单状态
		// 流程流转
		String flowType = "0";//LogicUtil.getFlowType(userBean);
		String flowNo = flowService.getFlowNoByLedger(ledgerId, flowType);
		flowService.backFlow(goodsOrderId, goodsOrderNo, orderTraceId, flowNo, userBean, BusinessConsts.GOODSORDER_STATE_BJZ, "强制退回并删除销售订单");
	}
}
