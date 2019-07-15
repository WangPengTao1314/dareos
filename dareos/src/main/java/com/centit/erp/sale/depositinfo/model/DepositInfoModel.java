package com.centit.erp.sale.depositinfo.model;

import com.centit.commons.model.BaseModel;

/**
 * 充值信息
 * 
 * @author liu_yg
 *
 */
public class DepositInfoModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 充值信息ID
	 */
	private String depositId;
	/**
	 * 经销商ID
	 */
	private String channId;
	/**
	 * 充值类型
	 */
	private String depositType;
	/**
	 * 经销商编号
	 */
	private String channNo;
	/**
	 * 经销商名称
	 */
	private String channName;
	/**
	 * 充值信息NO
	 */
	private String depositNo;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 制单人ID
	 */
	private String creator;
	/**
	 * 制单人名称
	 */
	private String creName;
	/**
	 * 制单时间
	 */
	private String creTime;
	/**
	 * 更新人ID
	 */
	private String updator;
	/**
	 * 更新人名称
	 */
	private String updName;
	/**
	 * 更新时间
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
	 * 制单部门ID
	 */
	private String deptId;
	/**
	 * 制单部门名称
	 */
	private String deptName;
	/**
	 * 制单机构ID
	 */
	private String orgId;
	/**
	 * 制单机构名称
	 */
	private String orgName;
	/**
	 * 账套
	 */
	private String ledgerId;
	/**
	 * 账套名称
	 */
	private String ledgerName;
	/**
	 * 删除标记
	 */
	private String delFlag;
	/**
	 * 充值金额
	 */
	private String depositAmount;
	/**
	 * 附件路径
	 */
	private String attPath;
	
	private String auditOpinion;
	public String getDepositId() {
		return depositId;
	}
	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}
	public String getChannId() {
		return channId;
	}
	public void setChannId(String channId) {
		this.channId = channId;
	}
	public String getDepositType() {
		return depositType;
	}
	public void setDepositType(String depositType) {
		this.depositType = depositType;
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
	public String getDepositNo() {
		return depositNo;
	}
	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
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
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}
	public String getAttPath() {
		return attPath;
	}
	public void setAttPath(String attPath) {
		this.attPath = attPath;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
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

}