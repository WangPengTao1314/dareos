package com.centit.common.service;

import java.rmi.ServerException;
import java.util.List;

import com.centit.common.po.FlowTrackModel;
import com.centit.sys.model.UserBean;

public interface FlowService {

	/**
	 * 新增单据时，创建流程
	 * @param flowNo 流程编号（要货单流程：ORDER；下级要货单流程：SUB_ORDER）
	 * @param userBean 当前登录人信息
	 * @param billNo 业务单据编号
	 * @return 返回查询流程跟踪的唯一ID，自行记录到业务表中
	 */
	public void insertFirstFlow(String flowNo,UserBean userBean,String billNo);

	/**
	 * 扭转下个节点
	 * @param billId 业务单据ID
	 * @param billNo 业务单据NO
	 * @param flowNo 流程编号
	 * @param flowServiceId 主表记录关联流程的ID
	 * @param userBean 当前登录人信息
	 * @param actorRemarks 审核/退回意见
	 */
	public void insertNextFlow(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String actorRemarks);

	/**
	 * 根据接点序号流转到指定接点
	 * @param billId
	 * @param billNo
	 * @param flowServiceId
	 * @param flowNo
	 * @param userBean
	 * @param state 接点状态
	 * @param actorRemarks 审核/退回意见
	 */
	public void insertNextFlowByIndexNo(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String state,String actorRemarks);

	/**
	 * 根据服务ID获取流程跟踪
	 * @param flowServiceId
	 * @return
	 */
	public List<FlowTrackModel> getFlowTrackById(String flowServiceId);
	
	/**
	 * 根据服务ID获取流程跟踪(增加业务单号排序)
	 * @param flowServiceId
	 * @return
	 */
	public List<FlowTrackModel> getFlowTrackByIdOrder(String flowServiceId);
	

	/**
	 * 退回到指定接点
	 * @param billId
	 * @param billNo
	 * @param flowServiceId
	 * @param flowNo
	 * @param userBean
	 * @param state 退回接点
	 * @param actorRemarks 审核/退回意见
	 */
	public void backFlow(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String state,String actorRemarks);

	/**
	 * 退回到上一层接点
	 * @param billId
	 * @param billNo
	 * @param flowServiceId
	 * @param flowNo
	 * @param userBean
	 * @param actorRemarks 审核/退回意见
	 */
	public void backFlow(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String actorRemarks);
	
	
	/**
	 * 退回到指定接点(只操作当前表)
	 * @param billId
	 * @param billNo
	 * @param flowServiceId
	 * @param flowNo
	 * @param userBean
	 * @param state 退回接点
	 * @param actorRemarks 审核/退回意见
	 */
	public void backFlowByTableName(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String state,String actorRemarks,String tableName);

	/**
	 * 退回到上一层接点(只操作当前表)
	 * @param billId
	 * @param billNo
	 * @param flowServiceId
	 * @param flowNo
	 * @param userBean
	 * @param actorRemarks 审核/退回意见
	 */
	public void backFlowByTableName(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String actorRemarks,String tableName);
	
	
	/**
	 * 流传到下个接点(一对多时使用，修改所有单据的状态)
	 * @param billId 业务单据ID
	 * @param billNo 业务单据编号
	 * @param flowServiceId 流程服务ID
	 * @param flowNo 流程编号
	 * @param userBean
	 * @param actorRemarks
	 */
	public void insertNextMultipleFlow(String billId,String billNo,String flowServiceId,String flowNo,UserBean userBean,String actorRemarks);

	/**
	 * 手动新增流程跟踪
	 * @param flowNo 流程编号
	 * @param flowServiceId 服务ID
	 * @param billNo 业务编号
	 * @param userBean 登录信息
	 * @param actorRemarks 操作意见
	 * @param actionDescribe 操作描述
	 */
	public void insertFlowTrack(String flowNo,String flowServiceId,String billNo,UserBean userBean,String actorRemarks,String actionDescribe);

	/**
	 * 根据帐套ID获取流程编号
	 * @param LedgerId 帐套ID
	 * @param type 流程类型，0是订单流程，1是变更流程,2是下级要货单流程，其余值默认是普通流程
	 * @return
	 * @throws ServerException
	 */
	public String getFlowNoByLedger(String LedgerId,String type);
	
	public void initSale(UserBean userBean);
}
