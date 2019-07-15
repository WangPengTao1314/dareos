/**
 * prjName:喜临门营销平台
 * ucName:标准订单
 * fileName:SaleorderModel
 */
package com.centit.erp.sale.saleorder.model;

import java.util.List;

import com.centit.commons.model.BaseModel;

	/**
	 *
 * @ClassName: SaleorderModel
 * @Description: 销售订单实体
 * @author wang_dw
 * @date 2019年3月14日 下午1:46:15
 *
	 */
public class SaleorderModel extends BaseModel {
	private static final long serialVersionUID = 8852579488417684037L;
	/** 销售订单ID **/
	private String SALE_ORDER_ID;
	/** 销售订单编号 **/
	private String SALE_ORDER_NO;
	/** 单据类型 **/
	private String BILL_TYPE;
	/** 来源单据ID **/
	private String FROM_BILL_ID;
	/** 来源单据NO **/
	private String FROM_BILL_NO;
	/** 来源单据类型 **/
	private String BILL_TYPE2;
	/** 要货组织ID **/
	private String ORDER_CHANN_ID;
	/** 要货组织编号 **/
	private String ORDER_CHANN_NO;
	/** 要货组织名称 **/
	private String ORDER_CHANN_NAME;
	/** 下单日期 **/
	private String ORDER_DATE;
	/** 经销商ID **/
	private String CHANN_ID;
	/** 经销商编号 **/
	private String CHANN_NO;
	/** 经销商名称 **/
	private String CHANN_NAME;
	/** 标签打印名称 **/
	private String PRINT_NAME;
	/** 运输方式 **/
	private String TRANSPORT;
	/** 运输结算方式 **/
	private String TRANSPORT_SETTLE;
	/** 厂编 **/
	private String FACTORY_NO;
	/** 处理类型（定制、现货、OEM） **/
	private String PROESS_TYPE;
	/** 业务员ID **/
	private String SALE_ID;
	/** 业务员名称 **/
	private String SALE_NAME;
	/** 业务员部门名称 **/
	private String SALEDEPT_NAME;
	/** 业务员部门ID **/
	private String SALEDEPT_ID;
	/** 预计交货日期 **/
	private String PRE_RECV_DATE;
//	/** 发货方ID **/
//	private String SEND_CHANN_ID;
//	/** 发货方编号 **/
//	private String SEND_CHANN_NO;
//	/** 发货方名称 **/
//	private String CUST_NAME;
	/** 客户姓名  **/
	private String CUST_NAME;
	/** 是否使用返利:0=否,1=是 **/
	private String IS_USE_REBATE;
	/** 收货组织ID **/
	private String RECV_CHANN_ID;
	/** 收货组织编号 **/
	private String RECV_CHANN_NO;
	/** 收货组织名称 **/
	private String RECV_CHANN_NAME;
	/** 收货联系人 **/
	private String PERSON_CON;
	/** 收货地址 **/
	private String RECV_ADDR;
	/** 收货地址编号 **/
	private String RECV_ADDR_NO;
	/** 收货电话 **/
	private String TEL;
	/** 状态 **/
	private String STATE;
	/** 经销商备注 **/
	private String REMARK;
	/** 订单员备注 **/
	private String REMARK2;
	/** 制单人ID **/
	private String CREATOR;
	/** 制单人名称 **/
	private String CRE_NAME;
	/** 制单时间 **/
	private String CRE_TIME;
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
	/** 删除标记:0=正常,1=删除 **/
	private String DEL_FLAG;
	/** 审核时间 **/
	private String AUDIT_TIME;
	/** 审核人姓名 **/
	private String AUDIT_NAME;
	/** 审核人ID **/
	private String AUDIT_ID;
	/** 制单部门名称 **/
	private String DEPT_NAME;
	/** 制单部门ID **/
	private String DEPT_ID;
	/** 制单机构ID **/
	private String ORG_ID;
	/** 制单机构名称 **/
	private String ORG_NAME;
	/** 区域ID **/
	private String AREA_ID;
	/** 区域编号 **/
	private String AREA_NO;
	/** 区域名称 **/
	private String AREA_NAME;
	/** 总部新增标记:0=否,1=是 **/
	private String BASE_ADD_FLAG;
	/** 订单交期 **/
	private String ORDER_DELIVERY_DATE;
	/** 客户电话 **/
	private String CUST_TEL;
	/** 客户住址 **/
	private String CUST_ADDR;

	/** 返利使用比例 **/
	private String REBATE_RATE;
	/** 流程跟踪ID **/
	private String ORDER_TRACE_ID;
	/** 退回高亮显示标记 **/
	private String RETURN_SHOW_FLAG;
	/** 问题反馈单ID **/
	private String PROBLEM_FEEDBACK_ID;
	/** 问题反馈单NO **/
	private String PROBLEM_FEEDBACK_NO;
	/** 订货总金额 **/
	private String TOTAL_AMOUNT;
	/** 订货总返利 **/
	private String TOTAL_REBATE;

	String PROJECT_ID;//工程项目id
	String PROJECT_NO;//工程项目编号
	String PROJECT_NAME;//工程项目名称

	String URGENCY_TYPE;//紧急程度

	/** 附件路径 */
	private String attPath;
	/** 明细JSON */
	private List<SaleorderModelChld> childList;

	/** 拆件记料表附件路径 */
	private String attPath_cj;

	/** 变更状态 0：正常；1：变更中 */
	private String CHANGE_FLAG;

	/** 审核状态 */
	private String auditStatus;
	/** 审核意见 */
	private String auditContents;
	/** 记录正在变更中的当前变更申请单ID */
	private String CHANGE_APPLY_ID;

	/** 推送NC系统返回单据ID **/
	private String NC_ID;
	/** 设计师名称 **/
	private String DESIGNER_NAME;

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
	public String getBILL_TYPE() {
		return BILL_TYPE;
	}
	public void setBILL_TYPE(String bILL_TYPE) {
		BILL_TYPE = bILL_TYPE;
	}
	public String getFROM_BILL_ID() {
		return FROM_BILL_ID;
	}
	public void setFROM_BILL_ID(String fROM_BILL_ID) {
		FROM_BILL_ID = fROM_BILL_ID;
	}
	public String getFROM_BILL_NO() {
		return FROM_BILL_NO;
	}
	public void setFROM_BILL_NO(String fROM_BILL_NO) {
		FROM_BILL_NO = fROM_BILL_NO;
	}
	public String getBILL_TYPE2() {
		return BILL_TYPE2;
	}
	public void setBILL_TYPE2(String bILL_TYPE2) {
		BILL_TYPE2 = bILL_TYPE2;
	}
	public String getORDER_CHANN_ID() {
		return ORDER_CHANN_ID;
	}
	public void setORDER_CHANN_ID(String oRDER_CHANN_ID) {
		ORDER_CHANN_ID = oRDER_CHANN_ID;
	}
	public String getORDER_CHANN_NO() {
		return ORDER_CHANN_NO;
	}
	public void setORDER_CHANN_NO(String oRDER_CHANN_NO) {
		ORDER_CHANN_NO = oRDER_CHANN_NO;
	}
	public String getORDER_CHANN_NAME() {
		return ORDER_CHANN_NAME;
	}
	public void setORDER_CHANN_NAME(String oRDER_CHANN_NAME) {
		ORDER_CHANN_NAME = oRDER_CHANN_NAME;
	}
	public String getORDER_DATE() {
		return ORDER_DATE;
	}
	public void setORDER_DATE(String oRDER_DATE) {
		ORDER_DATE = oRDER_DATE;
	}
	public String getCHANN_ID() {
		return CHANN_ID;
	}
	public void setCHANN_ID(String cHANN_ID) {
		CHANN_ID = cHANN_ID;
	}
	public String getCHANN_NO() {
		return CHANN_NO;
	}
	public void setCHANN_NO(String cHANN_NO) {
		CHANN_NO = cHANN_NO;
	}
	public String getCHANN_NAME() {
		return CHANN_NAME;
	}
	public void setCHANN_NAME(String cHANN_NAME) {
		CHANN_NAME = cHANN_NAME;
	}
	public String getPRINT_NAME() {
		return PRINT_NAME;
	}
	public void setPRINT_NAME(String pRINT_NAME) {
		PRINT_NAME = pRINT_NAME;
	}
	public String getTRANSPORT() {
		return TRANSPORT;
	}
	public void setTRANSPORT(String tRANSPORT) {
		TRANSPORT = tRANSPORT;
	}
	public String getTRANSPORT_SETTLE() {
		return TRANSPORT_SETTLE;
	}
	public void setTRANSPORT_SETTLE(String tRANSPORT_SETTLE) {
		TRANSPORT_SETTLE = tRANSPORT_SETTLE;
	}
	public String getFACTORY_NO() {
		return FACTORY_NO;
	}
	public void setFACTORY_NO(String fACTORY_NO) {
		FACTORY_NO = fACTORY_NO;
	}
	public String getPROESS_TYPE() {
		return PROESS_TYPE;
	}
	public void setPROESS_TYPE(String pROESS_TYPE) {
		PROESS_TYPE = pROESS_TYPE;
	}
	public String getSALE_ID() {
		return SALE_ID;
	}
	public void setSALE_ID(String sALE_ID) {
		SALE_ID = sALE_ID;
	}
	public String getSALE_NAME() {
		return SALE_NAME;
	}
	public void setSALE_NAME(String sALE_NAME) {
		SALE_NAME = sALE_NAME;
	}
	public String getSALEDEPT_NAME() {
		return SALEDEPT_NAME;
	}
	public void setSALEDEPT_NAME(String sALEDEPT_NAME) {
		SALEDEPT_NAME = sALEDEPT_NAME;
	}
	public String getSALEDEPT_ID() {
		return SALEDEPT_ID;
	}
	public void setSALEDEPT_ID(String sALEDEPT_ID) {
		SALEDEPT_ID = sALEDEPT_ID;
	}
	public String getPRE_RECV_DATE() {
		return PRE_RECV_DATE;
	}
	public void setPRE_RECV_DATE(String pRE_RECV_DATE) {
		PRE_RECV_DATE = pRE_RECV_DATE;
	}
	public String getCUST_NAME() {
		return CUST_NAME;
	}
	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}
	public String getIS_USE_REBATE() {
		return IS_USE_REBATE;
	}
	public void setIS_USE_REBATE(String iS_USE_REBATE) {
		IS_USE_REBATE = iS_USE_REBATE;
	}
	public String getRECV_CHANN_ID() {
		return RECV_CHANN_ID;
	}
	public void setRECV_CHANN_ID(String rECV_CHANN_ID) {
		RECV_CHANN_ID = rECV_CHANN_ID;
	}
	public String getRECV_CHANN_NO() {
		return RECV_CHANN_NO;
	}
	public void setRECV_CHANN_NO(String rECV_CHANN_NO) {
		RECV_CHANN_NO = rECV_CHANN_NO;
	}
	public String getRECV_CHANN_NAME() {
		return RECV_CHANN_NAME;
	}
	public void setRECV_CHANN_NAME(String rECV_CHANN_NAME) {
		RECV_CHANN_NAME = rECV_CHANN_NAME;
	}
	public String getPERSON_CON() {
		return PERSON_CON;
	}
	public void setPERSON_CON(String pERSON_CON) {
		PERSON_CON = pERSON_CON;
	}
	public String getRECV_ADDR() {
		return RECV_ADDR;
	}
	public void setRECV_ADDR(String rECV_ADDR) {
		RECV_ADDR = rECV_ADDR;
	}
	public String getRECV_ADDR_NO() {
		return RECV_ADDR_NO;
	}
	public void setRECV_ADDR_NO(String rECV_ADDR_NO) {
		RECV_ADDR_NO = rECV_ADDR_NO;
	}
	public String getTEL() {
		return TEL;
	}
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	public String getSTATE() {
		return STATE;
	}
	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getREMARK2() {
		return REMARK2;
	}
	public void setREMARK2(String rEMARK2) {
		REMARK2 = rEMARK2;
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
	public String getAREA_ID() {
		return AREA_ID;
	}
	public void setAREA_ID(String aREA_ID) {
		AREA_ID = aREA_ID;
	}
	public String getAREA_NO() {
		return AREA_NO;
	}
	public void setAREA_NO(String aREA_NO) {
		AREA_NO = aREA_NO;
	}
	public String getAREA_NAME() {
		return AREA_NAME;
	}
	public void setAREA_NAME(String aREA_NAME) {
		AREA_NAME = aREA_NAME;
	}
	public String getBASE_ADD_FLAG() {
		return BASE_ADD_FLAG;
	}
	public void setBASE_ADD_FLAG(String bASE_ADD_FLAG) {
		BASE_ADD_FLAG = bASE_ADD_FLAG;
	}
	public String getORDER_DELIVERY_DATE() {
		return ORDER_DELIVERY_DATE;
	}
	public void setORDER_DELIVERY_DATE(String oRDER_DELIVERY_DATE) {
		ORDER_DELIVERY_DATE = oRDER_DELIVERY_DATE;
	}
	public String getCUST_TEL() {
		return CUST_TEL;
	}
	public void setCUST_TEL(String cUST_TEL) {
		CUST_TEL = cUST_TEL;
	}
	public String getCUST_ADDR() {
		return CUST_ADDR;
	}
	public void setCUST_ADDR(String cUST_ADDR) {
		CUST_ADDR = cUST_ADDR;
	}
	public String getREBATE_RATE() {
		return REBATE_RATE;
	}
	public void setREBATE_RATE(String rEBATE_RATE) {
		REBATE_RATE = rEBATE_RATE;
	}
	public String getORDER_TRACE_ID() {
		return ORDER_TRACE_ID;
	}
	public void setORDER_TRACE_ID(String oRDER_TRACE_ID) {
		ORDER_TRACE_ID = oRDER_TRACE_ID;
	}
	public String getRETURN_SHOW_FLAG() {
		return RETURN_SHOW_FLAG;
	}
	public void setRETURN_SHOW_FLAG(String rETURN_SHOW_FLAG) {
		RETURN_SHOW_FLAG = rETURN_SHOW_FLAG;
	}
	public String getPROBLEM_FEEDBACK_ID() {
		return PROBLEM_FEEDBACK_ID;
	}
	public void setPROBLEM_FEEDBACK_ID(String pROBLEM_FEEDBACK_ID) {
		PROBLEM_FEEDBACK_ID = pROBLEM_FEEDBACK_ID;
	}
	public String getPROBLEM_FEEDBACK_NO() {
		return PROBLEM_FEEDBACK_NO;
	}
	public void setPROBLEM_FEEDBACK_NO(String pROBLEM_FEEDBACK_NO) {
		PROBLEM_FEEDBACK_NO = pROBLEM_FEEDBACK_NO;
	}
	public String getTOTAL_AMOUNT() {
		return TOTAL_AMOUNT;
	}
	public void setTOTAL_AMOUNT(String tOTAL_AMOUNT) {
		TOTAL_AMOUNT = tOTAL_AMOUNT;
	}
	public String getTOTAL_REBATE() {
		return TOTAL_REBATE;
	}
	public void setTOTAL_REBATE(String tOTAL_REBATE) {
		TOTAL_REBATE = tOTAL_REBATE;
	}
	public String getAttPath() {
		return attPath;
	}
	public void setAttPath(String attPath) {
		this.attPath = attPath;
	}
	public List<SaleorderModelChld> getChildList() {
		return childList;
	}
	public void setChildList(List<SaleorderModelChld> childList) {
		this.childList = childList;
	}
	public String getAttPath_cj() {
		return attPath_cj;
	}
	public void setAttPath_cj(String attPath_cj) {
		this.attPath_cj = attPath_cj;
	}
	public String getCHANGE_FLAG() {
		return CHANGE_FLAG;
	}
	public void setCHANGE_FLAG(String cHANGE_FLAG) {
		CHANGE_FLAG = cHANGE_FLAG;
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
	public String getCHANGE_APPLY_ID() {
		return CHANGE_APPLY_ID;
	}
	public void setCHANGE_APPLY_ID(String cHANGE_APPLY_ID) {
		CHANGE_APPLY_ID = cHANGE_APPLY_ID;
	}
	public String getNC_ID() {
		return NC_ID;
	}
	public void setNC_ID(String nC_ID) {
		NC_ID = nC_ID;
	}
	public String getPROJECT_ID() {
		return PROJECT_ID;
	}
	public void setPROJECT_ID(String pROJECT_ID) {
		PROJECT_ID = pROJECT_ID;
	}
	public String getPROJECT_NO() {
		return PROJECT_NO;
	}
	public void setPROJECT_NO(String pROJECT_NO) {
		PROJECT_NO = pROJECT_NO;
	}
	public String getPROJECT_NAME() {
		return PROJECT_NAME;
	}
	public void setPROJECT_NAME(String pROJECT_NAME) {
		PROJECT_NAME = pROJECT_NAME;
	}
	public String getURGENCY_TYPE() {
		return URGENCY_TYPE;
	}
	public void setURGENCY_TYPE(String uRGENCY_TYPE) {
		URGENCY_TYPE = uRGENCY_TYPE;
	}

	public SaleorderModel copyNotNullProperty(SaleorderModel other) {

		if (other.getSALE_ORDER_ID() != null)
			this.setSALE_ORDER_ID(other.getSALE_ORDER_ID());

		if (other.getSALE_ORDER_NO() != null)
			this.SALE_ORDER_NO = other.getSALE_ORDER_NO();
		if (other.getBILL_TYPE() != null)
			this.BILL_TYPE = other.getBILL_TYPE();
		if (other.getFROM_BILL_ID() != null)
			this.FROM_BILL_ID = other.getFROM_BILL_ID();
		if (other.getFROM_BILL_NO() != null)
			this.FROM_BILL_NO = other.getFROM_BILL_NO();
		if (other.getBILL_TYPE2() != null)
			this.BILL_TYPE2 = other.getBILL_TYPE2();
		if (other.getORDER_CHANN_ID() != null)
			this.ORDER_CHANN_ID = other.getORDER_CHANN_ID();
		if (other.getORDER_CHANN_NO() != null)
			this.ORDER_CHANN_NO = other.getORDER_CHANN_NO();
		if (other.getORDER_CHANN_NAME() != null)
			this.ORDER_CHANN_NAME = other.getORDER_CHANN_NAME();
		if (other.getORDER_DATE() != null)
			this.ORDER_DATE = other.getORDER_DATE();
		if (other.getCHANN_ID() != null)
			this.CHANN_ID = other.getCHANN_ID();
		if (other.getCHANN_NO() != null)
			this.CHANN_NO = other.getCHANN_NO();
		if (other.getCHANN_NAME() != null)
			this.CHANN_NAME = other.getCHANN_NAME();
		if (other.getPRINT_NAME() != null)
			this.PRINT_NAME = other.getPRINT_NAME();
		if (other.getTRANSPORT() != null)
			this.TRANSPORT = other.getTRANSPORT();
		if (other.getTRANSPORT_SETTLE() != null)
			this.TRANSPORT_SETTLE = other.getTRANSPORT_SETTLE();
		if (other.getFACTORY_NO() != null)
			this.FACTORY_NO = other.getFACTORY_NO();
		if (other.getPROESS_TYPE() != null)
			this.PROESS_TYPE = other.getPROESS_TYPE();
		if (other.getSALE_ID() != null)
			this.SALE_ID = other.getSALE_ID();
		if (other.getSALE_NAME() != null)
			this.SALE_NAME = other.getSALE_NAME();
		if (other.getSALEDEPT_NAME() != null)
			this.SALEDEPT_NAME = other.getSALEDEPT_NAME();
		if (other.getSALEDEPT_ID() != null)
			this.SALEDEPT_ID = other.getSALEDEPT_ID();
		if (other.getPRE_RECV_DATE() != null)
			this.PRE_RECV_DATE = other.getPRE_RECV_DATE();
		if (other.getCUST_NAME() != null)
			this.CUST_NAME = other.getCUST_NAME();
		if (other.getIS_USE_REBATE() != null)
			this.IS_USE_REBATE = other.getIS_USE_REBATE();
		if (other.getRECV_CHANN_ID() != null)
			this.RECV_CHANN_ID = other.getRECV_CHANN_ID();
		if (other.getRECV_CHANN_NO() != null)
			this.RECV_CHANN_NO = other.getRECV_CHANN_NO();
		if (other.getRECV_CHANN_NAME() != null)
			this.RECV_CHANN_NAME = other.getRECV_CHANN_NAME();
		if (other.getPERSON_CON() != null)
			this.PERSON_CON = other.getPERSON_CON();
		if (other.getRECV_ADDR() != null)
			this.RECV_ADDR = other.getRECV_ADDR();
		if (other.getRECV_ADDR_NO() != null)
			this.RECV_ADDR_NO = other.getRECV_ADDR_NO();
		if (other.getTEL() != null)
			this.TEL = other.getTEL();
		if (other.getREMARK() != null)
			this.REMARK = other.getREMARK();
		if (other.getREMARK2() != null)
			this.REMARK2 = other.getREMARK2();
		if (other.getORDER_DELIVERY_DATE() != null)
			this.ORDER_DELIVERY_DATE = other.getORDER_DELIVERY_DATE();
		if (other.getCUST_TEL() != null)
			this.CUST_TEL = other.getCUST_TEL();
		if (other.getCUST_ADDR() != null)
			this.CUST_ADDR = other.getCUST_ADDR();
		if (other.getTOTAL_AMOUNT() != null)
			this.TOTAL_AMOUNT = other.getTOTAL_AMOUNT();
		if (other.getTOTAL_REBATE() != null)
			this.TOTAL_REBATE = other.getTOTAL_REBATE();
		if (other.getPROJECT_ID() != null)
			this.PROJECT_ID = other.getPROJECT_ID();
		if (other.getPROJECT_NO() != null)
			this.PROJECT_NO = other.getPROJECT_NO();
		if (other.getPROJECT_NAME() != null)
			this.PROJECT_NAME = other.getPROJECT_NAME();
		if (other.getURGENCY_TYPE() != null)
			this.URGENCY_TYPE = other.getURGENCY_TYPE();
		if (other.getAttPath() != null)
			this.attPath = other.getAttPath();
		if (other.getAttPath_cj() != null)
			this.attPath_cj = other.getAttPath_cj();

		return this;
	}
	public String getDESIGNER_NAME() {
		return DESIGNER_NAME;
	}
	public void setDESIGNER_NAME(String dESIGNER_NAME) {
		DESIGNER_NAME = dESIGNER_NAME;
	}
}
