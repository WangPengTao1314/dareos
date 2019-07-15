/**
 * prjName:喜临门营销平台
 * ucName:标准订单
 * fileName:ChangeApplyModel
 */
package com.centit.erp.sale.saleorder.model;

import com.centit.commons.model.BaseModel;

public class ChangeApplyModel extends BaseModel {
	private static final long serialVersionUID = 1L;

	/** 变更申请ID **/
	private String CHANGE_APPLY_ID;
	/** 变更申请编号 **/
	private String CHANGE_APPLY_NO;
	/** 销售订单ID **/
	private String SALE_ORDER_ID;
	/** 厂编 **/
	private String FACTORY_NO;
	/** 经销商变更内容 **/
	private String CHAN_REMARK;
	/** 订单员变更内容 **/
	private String ORDER_REMARK;
	/** 通知人 **/
	private String TELL_PERSON;
	/** 订单主表历史 **/
	private String ORDER_HIS;
	/** 订单明细历史 **/
	private String ORDER_DTL_HIS;
	/** 状态 **/
	private String STATE;
	/** 制单人ID **/
	private String CREATOR;
	/** 制单人名称 **/
	private String CRE_NAME;
	/** 制单时间 **/
	private String CRE_TIME;
	/** 制单部门名称 **/
	private String DEPT_NAME;
	/** 制单部门ID **/
	private String DEPT_ID;
	/** 制单机构ID **/
	private String ORG_ID;
	/** 制单机构名称 **/
	private String ORG_NAME;
	/** 更新人ID **/
	private String UPDATOR;
	/** 更新人名称 **/
	private String UPD_NAME;
	/** 更新时间 **/
	private String UPD_TIME;
	/** 帐套名称 **/
	private String LEDGER_NAME;
	/** 帐套ID **/
	private String LEDGER_ID;
	/** 0=正常,1=删除 **/
	private String DEL_FLAG;
	/** 审核时间 **/
	private String AUDIT_TIME;
	/** 审核人姓名 **/
	private String AUDIT_NAME;
	/** 审核人ID **/
	private String AUDIT_ID;

	/** 流程跟踪ID **/
	private String ORDER_TRACE_ID;
	/** 审核状态 */
	private String auditStatus;
	/** 审核意见 */
	private String auditContents;


	public String getCHANGE_APPLY_ID() {
		return CHANGE_APPLY_ID;
	}
	public void setCHANGE_APPLY_ID(String cHANGE_APPLY_ID) {
		CHANGE_APPLY_ID = cHANGE_APPLY_ID;
	}
	public String getCHANGE_APPLY_NO() {
		return CHANGE_APPLY_NO;
	}
	public void setCHANGE_APPLY_NO(String cHANGE_APPLY_NO) {
		CHANGE_APPLY_NO = cHANGE_APPLY_NO;
	}
	public String getSALE_ORDER_ID() {
		return SALE_ORDER_ID;
	}
	public void setSALE_ORDER_ID(String sALE_ORDER_ID) {
		SALE_ORDER_ID = sALE_ORDER_ID;
	}
	public String getFACTORY_NO() {
		return FACTORY_NO;
	}
	public void setFACTORY_NO(String fACTORY_NO) {
		FACTORY_NO = fACTORY_NO;
	}
	public String getCHAN_REMARK() {
		return CHAN_REMARK;
	}
	public void setCHAN_REMARK(String cHAN_REMARK) {
		CHAN_REMARK = cHAN_REMARK;
	}
	public String getORDER_REMARK() {
		return ORDER_REMARK;
	}
	public void setORDER_REMARK(String oRDER_REMARK) {
		ORDER_REMARK = oRDER_REMARK;
	}
	public String getTELL_PERSON() {
		return TELL_PERSON;
	}
	public void setTELL_PERSON(String tELL_PERSON) {
		TELL_PERSON = tELL_PERSON;
	}
	public String getORDER_HIS() {
		return ORDER_HIS;
	}
	public void setORDER_HIS(String oRDER_HIS) {
		ORDER_HIS = oRDER_HIS;
	}
	public String getORDER_DTL_HIS() {
		return ORDER_DTL_HIS;
	}
	public void setORDER_DTL_HIS(String oRDER_DTL_HIS) {
		ORDER_DTL_HIS = oRDER_DTL_HIS;
	}
	public String getSTATE() {
		return STATE;
	}
	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}
	public String getCREATOR() {
		return CREATOR;
	}
	public void setCREATOR(String cREATOR) {
		CREATOR = cREATOR;
	}
	public String getCRE_NAME() {
		return CRE_NAME;
	}
	public void setCRE_NAME(String cRE_NAME) {
		CRE_NAME = cRE_NAME;
	}
	public String getCRE_TIME() {
		return CRE_TIME;
	}
	public void setCRE_TIME(String cRE_TIME) {
		CRE_TIME = cRE_TIME;
	}
	public String getDEPT_NAME() {
		return DEPT_NAME;
	}
	public void setDEPT_NAME(String dEPT_NAME) {
		DEPT_NAME = dEPT_NAME;
	}
	public String getDEPT_ID() {
		return DEPT_ID;
	}
	public void setDEPT_ID(String dEPT_ID) {
		DEPT_ID = dEPT_ID;
	}
	public String getORG_ID() {
		return ORG_ID;
	}
	public void setORG_ID(String oRG_ID) {
		ORG_ID = oRG_ID;
	}
	public String getORG_NAME() {
		return ORG_NAME;
	}
	public void setORG_NAME(String oRG_NAME) {
		ORG_NAME = oRG_NAME;
	}
	public String getUPDATOR() {
		return UPDATOR;
	}
	public void setUPDATOR(String uPDATOR) {
		UPDATOR = uPDATOR;
	}
	public String getUPD_NAME() {
		return UPD_NAME;
	}
	public void setUPD_NAME(String uPD_NAME) {
		UPD_NAME = uPD_NAME;
	}
	public String getUPD_TIME() {
		return UPD_TIME;
	}
	public void setUPD_TIME(String uPD_TIME) {
		UPD_TIME = uPD_TIME;
	}
	public String getLEDGER_NAME() {
		return LEDGER_NAME;
	}
	public void setLEDGER_NAME(String lEDGER_NAME) {
		LEDGER_NAME = lEDGER_NAME;
	}
	public String getLEDGER_ID() {
		return LEDGER_ID;
	}
	public void setLEDGER_ID(String lEDGER_ID) {
		LEDGER_ID = lEDGER_ID;
	}
	public String getDEL_FLAG() {
		return DEL_FLAG;
	}
	public void setDEL_FLAG(String dEL_FLAG) {
		DEL_FLAG = dEL_FLAG;
	}
	public String getAUDIT_TIME() {
		return AUDIT_TIME;
	}
	public void setAUDIT_TIME(String aUDIT_TIME) {
		AUDIT_TIME = aUDIT_TIME;
	}
	public String getAUDIT_NAME() {
		return AUDIT_NAME;
	}
	public void setAUDIT_NAME(String aUDIT_NAME) {
		AUDIT_NAME = aUDIT_NAME;
	}
	public String getAUDIT_ID() {
		return AUDIT_ID;
	}
	public void setAUDIT_ID(String aUDIT_ID) {
		AUDIT_ID = aUDIT_ID;
	}
	public String getORDER_TRACE_ID() {
		return ORDER_TRACE_ID;
	}
	public void setORDER_TRACE_ID(String oRDER_TRACE_ID) {
		ORDER_TRACE_ID = oRDER_TRACE_ID;
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
