package com.centit.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.common.mapper.FlowMapper;
import com.centit.common.po.FlowModel;
import com.centit.common.po.FlowTrackModel;
import com.centit.common.service.FlowService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.StringUtil;
import com.centit.sys.model.UserBean;

@Service
public class FlowServiceImpl implements FlowService {
	@Autowired
	FlowMapper mapper;
	/** 普通流程-"0" */
	public static final String TYPE_COMMON = BusinessConsts.TYPE_COMMON;
	/** 变更流程-"1" */
	public static final String TYPE_CHANGE = BusinessConsts.TYPE_CHANGE;
	/** 下级要货单流程-"2" */
	public static final String TYPE_SUB = BusinessConsts.TYPE_SUB;

	/**
	 * 创建单据时，调用的新增流程接口
	 */
	@Override
	@Transactional
	public void insertFirstFlow(String flowNo, UserBean userBean, String billNo) {
		// 根据流程编号获取流程接点
		List<FlowModel> flowList = this.getFlowByNo(flowNo);
		//生成整个流程的查询ID
		String flowServiceId = StringUtil.uuid32len();
		// 创建流程跟踪
		this.insertFlowTrack(flowNo, flowServiceId, billNo, flowList.get(0), userBean,null,null);
		this.upBillInfo(flowList.get(0), billNo, flowServiceId, null,true);
	}
	
	/**
	 * 根据服务ID获取流程跟踪(增加业务单号排序)
	 * @param flowServiceId
	 * @return
	 */
	public List<FlowTrackModel> getFlowTrackByIdOrder(String flowServiceId){
		return mapper.getFlowTrackByIdOrder(flowServiceId);
	}
	/**
	 * 流传到下个接点
	 */
	@Override
	@Transactional
	public void insertNextFlow(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String actorRemarks){
		//获取流程节点信息
		List<FlowModel> flowList = this.getFlowByNo(flowNo);
		//获取流程跟踪信息
		List<FlowTrackModel> flowTrackList=this.getFlowTrackById(flowServiceId);
		Integer indexNo = 0;
		//获取该单据最后一个序号值+1=当前该走的流程序号
		for (int i = 0; i < flowTrackList.size(); i++) {
			if(flowTrackList.get(i).getBillNo().equals(billNo)&&flowTrackList.get(i).getFlowNo().equals(flowNo)){
				indexNo = flowTrackList.get(i).getIndexNo() + 1;
				break;
			}
		}
		//循环接点查找序号
		for (int i = 0; i < flowList.size(); i++) {
			if(flowList.get(i).getIndexNo() == indexNo){
				//提交时，判断是否高亮显示
				this.upBillInfo(flowList.get(i), billNo, flowServiceId, checkFlagFieldVal(flowTrackList, indexNo), false);
				this.insertFlowTrack(flowNo, flowServiceId, billNo, flowList.get(i), userBean,null,actorRemarks);
			}
		}
	}
	/**
	 * 根据接点序号流转到指定接点
	 * @param billId
	 * @param billNo
	 * @param flowServiceId
	 * @param flowNo
	 * @param userBean
	 * @param state 接点状态
	 * @param actorRemarks 审核意见
	 */
	@Override
	@Transactional
	public void insertNextFlowByIndexNo(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String state,String actorRemarks){
		//获取流程节点信息
		List<FlowModel> flowList = this.getFlowByNo(flowNo);
		//获取流程跟踪信息
		List<FlowTrackModel> flowTrackList=this.getFlowTrackById(flowServiceId);

		//循环接点查找序号
		for (int i = 0; i < flowList.size(); i++) {
			if(flowList.get(i).getStateVal().equals(state) ){
				//提交时，判断是否高亮显示
				this.upBillInfo(flowList.get(i), billNo, flowServiceId, checkFlagFieldVal(flowTrackList, flowList.get(i).getIndexNo()),false);
				this.insertFlowTrack(flowNo, flowServiceId, billNo, flowList.get(i), userBean,null,actorRemarks);
			}
		}
	}

	/**
	 * 根据流程编号获取流程接点信息
	 * @param flowNo
	 * @return
	 */
	public List<FlowModel> getFlowByNo(String flowNo){
		return mapper.getFlowByNo(flowNo);
	}

	/**
	 * 根据服务ID获取流程跟踪
	 * @param flowServiceId
	 * @return
	 */
	@Override
	public List<FlowTrackModel> getFlowTrackById(String flowServiceId){
		return mapper.getFlowTrackById(flowServiceId);
	}

	/**
	 * 修改业务单据信息
	 * @param model
	 * @param billNo
	 * @param flowServiceId 新增接点时才传递flowServiceId值，后续操作接点无需传值
	 * @param flagFieldVal 为是否高亮显示值，如果不传就不操作
	 * @param interFlag 新增标记，创建新流程接点时为true，操作流程接点时为false，用于判断流程退回到初始接点时和新增初始接点的区别
	 */
	public void upBillInfo(FlowModel model,String billNo,String flowServiceId,String flagFieldVal,boolean interFlag){
		// 判断退回修改，如果indexNo等于0的话，退回时状态改为退回，而不是初始状态
		if(!interFlag&&model.getIndexNo()==0){
			model.setStateVal(BusinessConsts._BACK);
		}
		StringBuffer sql=new StringBuffer();
		sql.append(" update ")
		    .append(model.getTableName())
		    .append(" set ");
		if(!StringUtil.isEmpty(flowServiceId)){
			sql.append(model.getFlowServiceFieldName()).append(" = '").append(flowServiceId).append("', ");
		}
		if(StringUtil.isEmpty(flagFieldVal)){
			flagFieldVal="0";
		}
		sql.append(model.getFlagFieldName()).append(" = '").append(flagFieldVal).append("', ")
			.append(model.getStateFieldName()).append(" = '").append(model.getStateVal()).append("'")
		    .append(" where ").append(model.getPrimaryFieldName()).append(" = '").append(billNo).append("'");
//		    .append(" or ").append(model.getFlowServiceFieldName()).append(" = '").append(flowServiceId).append("' ");
		InterUtil.update(sql.toString());
	}

	/**
	 * 新增流程跟踪
	 * @param flowNo
	 * @param flowServiceId
	 * @param billNo
	 * @param model
	 * @param userBean
	 * @param actorRemarks 审核意见
	 */
	public void insertFlowTrack(String flowNo,String flowServiceId,String billNo,FlowModel model,UserBean userBean,String returnFlag,String actorRemarks){
		FlowTrackModel flowTrackModel = new FlowTrackModel();
		flowTrackModel.setFlowTrackId(StringUtil.uuid32len());
		flowTrackModel.setFlowNo(flowNo);
		flowTrackModel.setFlowServiceId(flowServiceId);
		flowTrackModel.setBillNo(billNo);
		flowTrackModel.setActorRemarks(actorRemarks);
		if(StringUtil.isEmpty(returnFlag)){
			flowTrackModel.setAction(model.getActionDescribe());
		}else{
			if(BusinessConsts._BACK.equals(model.getStateVal())){
				flowTrackModel.setAction("退回到初始状态");
			}else{
				flowTrackModel.setAction("退回到" + model.getStateVal());
			}
		}
		if(StringUtil.isEmpty(flowTrackModel.getAction())){
			return;
		}
		flowTrackModel.setActorId(userBean.getRYXXID());
		flowTrackModel.setActorName(userBean.getXM());
		flowTrackModel.setIndexNo(model.getIndexNo());
		mapper.insertFlowTrack(flowTrackModel);
	}

	/**
	 * 手动新增流程跟踪
	 * @param flowNo 流程编号
	 * @param flowServiceId 服务ID
	 * @param billNo 业务编号
	 * @param userBean 登录信息
	 * @param actorRemarks 操作意见
	 * @param actionDescribe 操作描述
	 */
	@Override
	public void insertFlowTrack(String flowNo,String flowServiceId,String billNo,UserBean userBean,String actorRemarks,String actionDescribe){
		FlowTrackModel flowTrackModel = new FlowTrackModel();
		flowTrackModel.setFlowTrackId(StringUtil.uuid32len());
		flowTrackModel.setFlowNo(flowNo);
		flowTrackModel.setFlowServiceId(flowServiceId);
		flowTrackModel.setBillNo(billNo);
		flowTrackModel.setActorRemarks(actorRemarks);
		flowTrackModel.setAction(actionDescribe);
		flowTrackModel.setActorId(userBean.getRYXXID());
		flowTrackModel.setActorName(userBean.getXM());
		flowTrackModel.setIndexNo(-1);// 手动新增流程没有流程序号
		mapper.insertFlowTrack(flowTrackModel);
	}

	/**
	 * 退回到上一层接点
	 * @param billId
	 * @param billNo
	 * @param flowServiceId
	 * @param flowNo
	 * @param userBean
	 * @param actorRemarks
	 */
	@Override
	@Transactional
	public void backFlow(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String actorRemarks){
		//获取流程节点信息
		List<FlowModel> flowList = this.getFlowByNo(flowNo);
		//获取流程跟踪信息
		List<FlowTrackModel> flowTrackList=this.getFlowTrackById(flowServiceId);
		//获取流程跟踪上个接点序号
		Integer indexNo = flowTrackList.get(0).getIndexNo()-1;
		// 判断上个接点是否和当前接点序号是否相同 如果相同，取上上个接点序号。。。
//		for (int i = 0; i < flowTrackList.size(); i++) {
//			if(indexNo != flowTrackList.get(i).getIndexNo()&&flowTrackList.get(i).getFlowNo().equals(flowNo)){
//				indexNo = flowTrackList.get(i).getIndexNo();
//				break;
//			}
//		}
		//循环接点查找序号
		for (int i = 0; i < flowList.size(); i++) {
			if(flowList.get(i).getIndexNo() == indexNo){
				//提交时，判断是否高亮显示
				this.upBillInfo(flowList.get(i), billNo, flowServiceId, null,false);
				this.insertFlowTrack(flowNo, flowServiceId, billNo, flowList.get(i), userBean,"1",actorRemarks);
			}
		}
	}
	
	/**
	 * 退回到指定接点
	 * @param billId
	 * @param billNo
	 * @param flowServiceId
	 * @param flowNo
	 * @param userBean
	 * @param state
	 * @param actorRemarks
	 */
	@Override
	@Transactional
	public void backFlow(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String state,String actorRemarks){
		//获取流程节点信息
		List<FlowModel> flowList = this.getFlowByNo(flowNo);
		//循环接点查找序号
		for (int i = 0; i < flowList.size(); i++) {
			if(flowList.get(i).getStateVal() .equals(state) ){
				//提交时，判断是否高亮显示
				this.upBillInfo(flowList.get(i), billNo, flowServiceId, null,false);
				this.insertFlowTrack(flowNo, flowServiceId, billNo, flowList.get(i), userBean,"1",actorRemarks);
			}
		}
	}
	
	
	/**
	 * 退回到上一层接点(只操作当前表的退回)
	 * @param billId
	 * @param billNo
	 * @param flowServiceId
	 * @param flowNo
	 * @param userBean
	 * @param actorRemarks
	 */
	@Override
	@Transactional
	public void backFlowByTableName(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String actorRemarks,String tableName){
		//获取流程节点信息
		List<FlowModel> flowList = this.getFlowByNo(flowNo);
		//获取流程跟踪信息
		List<FlowTrackModel> flowTrackList=this.getFlowTrackById(flowServiceId);
		//获取流程跟踪上个接点序号
		Integer indexNo = flowTrackList.get(0).getIndexNo()-1;
		// 判断上个接点是否和当前接点序号是否相同 如果相同，取上上个接点序号。。。
//		for (int i = 0; i < flowTrackList.size(); i++) {
//			if(indexNo != flowTrackList.get(i).getIndexNo()&&flowTrackList.get(i).getFlowNo().equals(flowNo)){
//				indexNo = flowTrackList.get(i).getIndexNo();
//				break;
//			}
//		}
		//循环接点查找序号
		for (int i = 0; i < flowList.size(); i++) {
			if(flowList.get(i).getIndexNo() == indexNo){
				if(!flowList.get(i).getTableName().equals(tableName)){
					continue;
				}
				//提交时，判断是否高亮显示
				this.upBillInfo(flowList.get(i), billNo, flowServiceId, null,false);
				this.insertFlowTrack(flowNo, flowServiceId, billNo, flowList.get(i), userBean,"1",actorRemarks);
			}
		}
	}
	
	/**
	 * 退回到指定接点(只操作当前表的退回)
	 * @param billId
	 * @param billNo
	 * @param flowServiceId
	 * @param flowNo
	 * @param userBean
	 * @param state
	 * @param actorRemarks
	 */
	@Override
	@Transactional
	public void backFlowByTableName(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String state,String actorRemarks,String tableName){
		//获取流程节点信息
		List<FlowModel> flowList = this.getFlowByNo(flowNo);
		//循环接点查找序号
		for (int i = 0; i < flowList.size(); i++) {
			if(flowList.get(i).getStateVal() .equals(state) ){
				if(!flowList.get(i).getTableName().equals(tableName)){
					continue;
				}
				//提交时，判断是否高亮显示
				this.upBillInfo(flowList.get(i), billNo, flowServiceId, null,false);
				this.insertFlowTrack(flowNo, flowServiceId, billNo, flowList.get(i), userBean,"1",actorRemarks);
			}
		}
	}

	/**
	 * 判断是否需要高亮显示
	 * @return
	 */
	public String checkFlagFieldVal(List<FlowTrackModel> flowTrackList,Integer indexNo){
		String flagFieldVal = "0";
		// 循环流程跟踪，判断里面是否有退回接点
		for (int i = 0; i < flowTrackList.size(); i++) {
			// 找到第一个退回接点，获取接点序号，如果接点序号小余或等于下个接点时，高亮显示
			if(!StringUtil.isEmpty(flowTrackList.get(i).getAction())){
				if(flowTrackList.get(i).getAction().indexOf(BusinessConsts._BACK)>=0){
					if(indexNo-1<=flowTrackList.get(i).getIndexNo()){
						flagFieldVal = "1";
						break;
					}
				}
			}
		}
		return flagFieldVal;
	}

	/**
	 * 流传到下个接点(一对多时使用，修改所有单据的状态)
	 * @param billId 业务单据ID
	 * @param billNo 业务单据编号
	 * @param flowServiceId 流程服务ID
	 * @param flowNo 流程编号
	 * @param userBean
	 * @param actorRemarks
	 */
	@Override
	@Transactional
	public void insertNextMultipleFlow(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String actorRemarks){
		//获取流程节点信息
		List<FlowModel> flowList = this.getFlowByNo(flowNo);
		//获取流程跟踪信息
		List<FlowTrackModel> flowTrackList=this.getFlowTrackById(flowServiceId);
		//获取最后一个序号值+1=当前该走的流程序号
		Integer indexNo = 0;
		//获取该单据最后一个序号值+1=当前该走的流程序号
		for (int i = 0; i < flowTrackList.size(); i++) {
			if(flowTrackList.get(i).getBillNo().equals(billNo)&&flowTrackList.get(i).getFlowNo().equals(flowNo)){
				indexNo = flowTrackList.get(i).getIndexNo() + 1;
				break;
			}
		}
		//循环接点查找序号
		for (int i = 0; i < flowList.size(); i++) {
			if(flowList.get(i).getIndexNo() == indexNo){
				StringBuffer sql=new StringBuffer();
				sql.append(" update ")
				    .append(flowList.get(i).getTableName())
				    .append(" set ");
				String flagFieldVal = checkFlagFieldVal(flowTrackList, indexNo);
				if(StringUtil.isEmpty(flagFieldVal)){
					flagFieldVal="0";
				}
				//修改单据状态
				sql.append(flowList.get(i).getFlagFieldName()).append(" = '").append(flagFieldVal).append("', ")
					.append(flowList.get(i).getStateFieldName()).append(" = '").append(flowList.get(i).getStateVal()).append("'")
				    .append(" where ").append(flowList.get(i).getFlowServiceFieldName()).append(" = '").append(flowServiceId).append("'");
				InterUtil.update(sql.toString());
				// 创建流程跟踪

				sql = new StringBuffer();
				sql.append(" insert into T_SYS_FLOW_TRACK(")
				    .append("FLOW_TRACK_ID,")
				    .append("FLOW_NO,")
				    .append("FLOW_SERVICE_ID,")
				    .append("BILL_NO,")
				    .append("ACTION,")
				    .append("ACTOR_ID,")
				    .append("ACTOR_NAME,")
				    .append("ACTOR_TIME,")
				    .append("ACTOR_REMARKS,")
				    .append("INDEX_NO) ")
				    .append(" select ")
				    .append("sys_guid(),")
				    .append("'").append(flowNo).append("',")
				    .append("'").append(flowServiceId).append("',")
				    .append(flowList.get(i).getPrimaryFieldName()).append(",")
				    .append("'").append(flowList.get(i).getActionDescribe()).append("',")
				    .append("'").append(userBean.getRYXXID()).append("',")
				    .append("'").append(userBean.getXM()).append("',")
				    .append("sysdate,")
				    .append("'").append(actorRemarks).append("',")
				    .append("'").append(flowList.get(i).getIndexNo()).append("' ")
				    .append(" from ").append(flowList.get(i).getTableName())
				    .append(" where ").append(flowList.get(i).getFlowServiceFieldName()).append("='").append(flowServiceId).append("'");
				InterUtil.update(sql.toString());
				//提交时，判断是否高亮显示
//				this.upBillInfo(flowList.get(i), billNo, flowServiceId, checkFlagFieldVal(flowTrackList, indexNo),false);
//				this.insertFlowTrack(flowNo, flowServiceId, billNo, flowList.get(i), userBean,null,actorRemarks);
			}
		}
	}


	@Override
	public String getFlowNoByLedger(String LedgerId,String type) {
		try {
			String flowNo = Consts.FLOW_CONF.get(LedgerId).toString();
			switch (type) {
			case TYPE_COMMON://普通流程
				return flowNo;
			case TYPE_CHANGE: //变更流程
				return "BG"+"_"+flowNo;
			case TYPE_SUB: //下级要货单流程
				return "SUB_ORDER";
			default:
				return flowNo;// 默认普通流程
			}
		} catch (NullPointerException n) {
			n.printStackTrace();
			throw new ServiceException("未找到指定帐套流程");
		}catch(Exception e){
			throw new ServiceException("帐套流程获取错误，请联系管理员！");
		}
	}
	
	@Transactional
	public void updInitSale(UserBean userBean){
		String flowNo = getFlowNoByLedger("116", "0");
		String sql="select FACTORY_NO from erp_sale_order where order_trace_id is null for update";
		List<Map<String,String>> list= InterUtil.selcomList(sql);
		for (int i = 0; i < list.size(); i++) {
			String ORDER_TRACE_ID =StringUtil.uuid32len();
//			insertFlowTrack(flowNo, ORDER_TRACE_ID, list.get(i).get("FACTORY_NO"), userBean, "", "新增销售订单");
			String sql1="update erp_sale_order set ORDER_TRACE_ID ='"+ORDER_TRACE_ID+"' where FACTORY_NO='"+list.get(i).get("FACTORY_NO")+"'";
			InterUtil.update(sql1);
			String sql2="update t_sys_flow_track set FLOW_SERVICE_ID='"+ORDER_TRACE_ID+"' where BILL_NO='"+list.get(i).get("FACTORY_NO")+"'";
			InterUtil.update(sql2);
		}
		
	}
	
	@Transactional
	public void initSale(UserBean userBean){
		String flowNo = getFlowNoByLedger("116", "0");
		String sql="select FACTORY_NO from erp_sale_order where order_trace_id is null for update";
		List<Map<String,String>> list= InterUtil.selcomList(sql);
		for (int i = 0; i < list.size(); i++) {
			String ORDER_TRACE_ID =StringUtil.uuid32len();
			insertFlowTrack(flowNo, ORDER_TRACE_ID, list.get(i).get("FACTORY_NO"), userBean, "", "新增销售订单");
			String sql1="update erp_sale_order set ORDER_TRACE_ID ='"+ORDER_TRACE_ID+"' where FACTORY_NO='"+list.get(i).get("FACTORY_NO")+"'";
			InterUtil.update(sql1);
		}
		
	}
}
