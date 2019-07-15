package com.centit.erp.sale.creditreq.model;

import com.centit.commons.model.BaseModel;

/**
 * 信用申请
 * 
 * @author liu_yg
 *
 */
public class CreditReqModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 
     *信用申请ID
     */
	private String creditReqId;
	/** 
     *信用申请NO
     */
	private String creditReqNo;
	/** 
     *渠道ID
     */
	private String channId;
	/** 
     *渠道编号
     */
	private String channNo;
	/** 
     *渠道名称
     */
	private String channName;
	/** 
     *申请额度
     */
	private String creditTotal;
	/** 
     *帐套ID
     */
	private String ledgerId;
	/** 
     *帐套名称
     */
	private String ledgerName;
	/** 
     *状态
     */
	private String state;
	/** 
     *备注
     */
	private String remark;
	/** 
     *制单人ID
     */
	private String creator;
	/** 
     *制单人名称
     */
	private String creName;
	/** 
     *制单时间
     */
	private String creTime;
	/** 
     *更新人ID
     */
	private String updator;
	/** 
     *更新人名称
     */
	private String updName;
	/** 
     *更新时间
     */
	private String updTime;
	/** 
     *审核人ID
     */
	private String auditId;
	/** 
     *审核人姓名
     */
	private String auditName;
	/** 
     *审核时间
     */
	private String auditTime;
	/** 
     *制单部门ID
     */
	private String deptId;
	/** 
     *制单部门名称
     */
	private String deptName;
	/** 
     *制单机构ID
     */
	private String orgId;
	/** 
     *制单机构名称
     */
	private String orgName;
	/** 
     *删除标记
     */
	private String delFlag;
	/** 
     *生效日期
     */
	private String effectiveDate;
	/** 
     *失效日期
     */
	private String expirationDate;
	/** 
     *审核意见
     */
	private String auditOpinion;
	/**
	 * 附件
	 */
	private String attPath;
	/**
	 * 操作标记 
	 */
	private String processFlag;
	public String getCreditReqId() {
		return creditReqId;
	}
	public void setCreditReqId(String creditReqId) {
		this.creditReqId = creditReqId;
	}
	public String getCreditReqNo() {
		return creditReqNo;
	}
	public void setCreditReqNo(String creditReqNo) {
		this.creditReqNo = creditReqNo;
	}
	public String getChannId() {
		return channId;
	}
	public void setChannId(String channId) {
		this.channId = channId;
	}
	public String getChannNo() {
		return channNo;
	}
	public void setChannNo(String channNo) {
		this.channNo = channNo;
	}
	public String getChannName() {
		return channName;
	}
	public void setChannName(String channName) {
		this.channName = channName;
	}
	public String getCreditTotal() {
		return creditTotal;
	}
	public void setCreditTotal(String creditTotal) {
		this.creditTotal = creditTotal;
	}
	public String getLedgerId() {
		return ledgerId;
	}
	public void setLedgerId(String ledgerId) {
		this.ledgerId = ledgerId;
	}
	public String getLedgerName() {
		return ledgerName;
	}
	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreName() {
		return creName;
	}
	public void setCreName(String creName) {
		this.creName = creName;
	}
	public String getCreTime() {
		return creTime;
	}
	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
	}
	public String getUpdName() {
		return updName;
	}
	public void setUpdName(String updName) {
		this.updName = updName;
	}
	public String getUpdTime() {
		return updTime;
	}
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public String getAttPath() {
		return attPath;
	}
	public void setAttPath(String attPath) {
		this.attPath = attPath;
	}
	public String getProcessFlag() {
		return processFlag;
	}
	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}
	
}