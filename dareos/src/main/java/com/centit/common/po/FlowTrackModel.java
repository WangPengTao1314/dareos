package com.centit.common.po;

import java.io.Serializable;

public class FlowTrackModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 流程接点跟踪ID
	 */
	private String flowTrackId;
	/**
	 * 流程编号
	 */
	private String flowNo;
	/**
	 * 订单ID
	 */
	private String flowServiceId;
	/**
	 * 业务单据编号
	 */
	private String billNo;
	/**
	 * 操作
	 */
	private String action;
	/**
	 * 操作人ID
	 */
	private String actorId;
	/**
	 * 操作人姓名
	 */
	private String actorName;
	/**
	 * 操作时间
	 */
	private String actorTime;
	/**
	 * 操作备注
	 */
	private String actorRemarks;
	/**
	 * 下一接点
	 */
	private String nextFlowId;
	/**
	 * 接点序号
	 */
	private Integer indexNo;
	public String getFlowTrackId() {
		return flowTrackId;
	}
	public void setFlowTrackId(String flowTrackId) {
		this.flowTrackId = flowTrackId;
	}
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	public String getFlowServiceId() {
		return flowServiceId;
	}
	public void setFlowServiceId(String flowServiceId) {
		this.flowServiceId = flowServiceId;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	public String getActorName() {
		return actorName;
	}
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}
	public String getActorTime() {
		return actorTime;
	}
	public void setActorTime(String actorTime) {
		this.actorTime = actorTime;
	}
	public String getActorRemarks() {
		return actorRemarks;
	}
	public void setActorRemarks(String actorRemarks) {
		this.actorRemarks = actorRemarks;
	}
	public String getNextFlowId() {
		return nextFlowId;
	}
	public void setNextFlowId(String nextFlowId) {
		this.nextFlowId = nextFlowId;
	}
	public Integer getIndexNo() {
		return indexNo;
	}
	public void setIndexNo(Integer indexNo) {
		this.indexNo = indexNo;
	}
	
	
}
