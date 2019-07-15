/**
 * prjName:喜临门营销平台
 * ucName:销售订单设计师分派表
 * fileName:SaleDesignerModel
 */
package com.centit.erp.sale.saleorder.model;

import com.centit.commons.model.BaseModel;

public class SaleDesignerModel extends BaseModel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** 设计师分派ID **/
	private String DESIGNER_ID;
	/** 销售订单ID **/
	private String SALE_ORDER_ID;
	/** 销售订单编号 **/
	private String SALE_ORDER_NO;
	/** 设计师ID **/
	private String DESIGNER;
	/** 设计师名称 **/
	private String DESIGNER_NAME;
	/** 分派时间 **/
	private String ASSIGN_TIME;
	/** 分派人ID **/
	private String ASSIGNER;
	/** 0=未完成,1=完成 **/
	private String FINISH_FLAG;
	/** 完成时间 **/
	private String FINISH_TIME;
	/** 备注 **/
	private String REMARK;
	/** 流程跟踪ID **/
	private String ORDER_TRACE_ID;
	/** 帐套ID **/
	private String LEDGER_ID;

	/** 审核状态 */
	private String auditStatus;
	/** 审核意见 */
	private String auditContents;

	public String getDESIGNER_ID() {
		return DESIGNER_ID;
	}
	public void setDESIGNER_ID(String dESIGNER_ID) {
		DESIGNER_ID = dESIGNER_ID;
	}
	public String getSALE_ORDER_ID() {
		return SALE_ORDER_ID;
	}
	public void setSALE_ORDER_ID(String sALE_ORDER_ID) {
		SALE_ORDER_ID = sALE_ORDER_ID;
	}
	public String getSALE_ORDER_NO() {
		return SALE_ORDER_NO;
	}
	public void setSALE_ORDER_NO(String sALE_ORDER_NO) {
		SALE_ORDER_NO = sALE_ORDER_NO;
	}
	public String getDESIGNER() {
		return DESIGNER;
	}
	public void setDESIGNER(String dESIGNER) {
		DESIGNER = dESIGNER;
	}
	public String getDESIGNER_NAME() {
		return DESIGNER_NAME;
	}
	public void setDESIGNER_NAME(String dESIGNER_NAME) {
		DESIGNER_NAME = dESIGNER_NAME;
	}
	public String getASSIGN_TIME() {
		return ASSIGN_TIME;
	}
	public void setASSIGN_TIME(String aSSIGN_TIME) {
		ASSIGN_TIME = aSSIGN_TIME;
	}
	public String getASSIGNER() {
		return ASSIGNER;
	}
	public void setASSIGNER(String aSSIGNER) {
		ASSIGNER = aSSIGNER;
	}
	public String getFINISH_FLAG() {
		return FINISH_FLAG;
	}
	public void setFINISH_FLAG(String fINISH_FLAG) {
		FINISH_FLAG = fINISH_FLAG;
	}
	public String getFINISH_TIME() {
		return FINISH_TIME;
	}
	public void setFINISH_TIME(String fINISH_TIME) {
		FINISH_TIME = fINISH_TIME;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getORDER_TRACE_ID() {
		return ORDER_TRACE_ID;
	}
	public void setORDER_TRACE_ID(String oRDER_TRACE_ID) {
		ORDER_TRACE_ID = oRDER_TRACE_ID;
	}
	public String getLEDGER_ID() {
		return LEDGER_ID;
	}
	public void setLEDGER_ID(String lEDGER_ID) {
		LEDGER_ID = lEDGER_ID;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAuditContents() {
		return auditContents;
	}
	public void setAuditContents(String auditContents) {
		this.auditContents = auditContents;
	}

}
