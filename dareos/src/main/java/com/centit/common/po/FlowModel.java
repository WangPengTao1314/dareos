package com.centit.common.po;

import java.io.Serializable;

public class FlowModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 流程接点ID
	 */
	private String flowId;
	/**
	 * 流程编号
	 */
	private String flowNo;
	/**
	 * 本级接点状态
	 */
	private String stateVal;
	/**
	 * 操作表
	 */
	private String tableName;
	/**
	 * 状态字段
	 */
	private String stateFieldName;
	/**
	 * 序号
	 */
	private Integer indexNo;
	/**
	 * 操作描述
	 */
	private String actionDescribe ;
	/**
	 * 主键编号字段
	 */
	private String primaryFieldName;
	/**
	 * 流程服务ID字段名
	 */
	private String flowServiceFieldName;
	/**
	 * 退回时高亮显示字段
	 */
	private String flagFieldName;
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	public String getStateVal() {
		return stateVal;
	}
	public void setStateVal(String stateVal) {
		this.stateVal = stateVal;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getStateFieldName() {
		return stateFieldName;
	}
	public void setStateFieldName(String stateFieldName) {
		this.stateFieldName = stateFieldName;
	}
	public Integer getIndexNo() {
		return indexNo;
	}
	public void setIndexNo(Integer indexNo) {
		this.indexNo = indexNo;
	}
	public String getActionDescribe() {
		return actionDescribe;
	}
	public void setActionDescribe(String actionDescribe) {
		this.actionDescribe = actionDescribe;
	}
	public String getPrimaryFieldName() {
		return primaryFieldName;
	}
	public void setPrimaryFieldName(String primaryFieldName) {
		this.primaryFieldName = primaryFieldName;
	}
	public String getFlowServiceFieldName() {
		return flowServiceFieldName;
	}
	public void setFlowServiceFieldName(String flowServiceFieldName) {
		this.flowServiceFieldName = flowServiceFieldName;
	}
	public String getFlagFieldName() {
		return flagFieldName;
	}
	public void setFlagFieldName(String flagFieldName) {
		this.flagFieldName = flagFieldName;
	}
	
}
