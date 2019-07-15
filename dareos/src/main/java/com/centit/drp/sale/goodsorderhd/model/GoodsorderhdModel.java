/**
 * prjName:喜临门营销平台
 * ucName:订货订单维护
 * fileName:GoodsorderhdModel
 */
package com.centit.drp.sale.goodsorderhd.model;

import com.centit.commons.model.BaseModel;

public class GoodsorderhdModel extends BaseModel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** 订货订单ID **/
	private String GOODS_ORDER_ID;
	/** 订货订单编号 **/
	private String GOODS_ORDER_NO;
	/** 要货组织ID **/
	private String ORDER_CHANN_ID;
	/** 要货组织编号 **/
	private String ORDER_CHANN_NO;
	/** 要货组织名称 **/
	private String ORDER_CHANN_NAME;
	/** 订单类型 **/
	private String BILL_TYPE;
	/** 是否使用返利 **/
	private String IS_USE_REBATE;
	/** 收货方ID **/
	private String RECV_CHANN_ID;
	/** 收货方编号 **/
	private String RECV_CHANN_NO;
	/** 收货方名称 **/
	private String RECV_CHANN_NAME;
	/** 收货联系人 **/
	private String PERSON_CON;
	/** 收货电话 **/
	private String TEL;
	/** 收货地址 **/
	private String RECV_ADDR;
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
	/** 审核人ID **/
	private String AUDIT_ID;
	/** 审核人姓名 **/
	private String AUDIT_NAME;
	/** 审核时间 **/
	private String AUDIT_TIME;
	/** 制单部门ID **/
	private String DEPT_ID;
	/** 制单部门名称 **/
	private String DEPT_NAME;
	/** 制单机构ID **/
	private String ORG_ID;
	/** 制单机构名称 **/
	private String ORG_NAME;
	/** 帐套ID **/
	private String LEDGER_ID;
	/** 帐套名称 **/
	private String LEDGER_NAME;
	/** 删除标记 **/
	private String DEL_FLAG;
	/** 区域ID **/
	private String AREA_ID;
	/** 区域编号 **/
	private String AREA_NO;
	/** 区域名称 **/
	private String AREA_NAME;
	/** 收货地址编号 **/
	private String RECV_ADDR_NO;
	/** 要求到货日期 **/
	private String ORDER_RECV_DATE;
	/** 下单日期 **/
	private String ORDER_DATE;
	/** 退回原因 **/
	private String RETURN_RSON;
	/** 标签打印名称 **/
	private String PRINT_NAME;
	/** 客户姓名 **/
	private String CUST_NAME;
	/** 客户电话 **/
	private String CUST_TEL;
	/** 客户住址 **/
	private String CUST_ADDR;
	/** 订单交期 **/
	private String ORDER_DELIVERY_DATE;
	/** 预计交货日期 **/
	private String PRE_RECV_DATE;
	/** 运输方式 **/
	private String TRANSPORT;
	/** 运输结算方式 **/
	private String TRANSPORT_SETTLE;
	/** 业务员ID **/
	private String SALE_ID;
	/** 业务员名称 **/
	private String SALE_NAME;
	/** 业务员部门名称 **/
	private String SALEDEPT_NAME;
	/** 业务员部门ID **/
	private String SALEDEPT_ID;
	/** 经销商ID **/
	private String CHANN_ID;
	/** 经销商编号 **/
	private String CHANN_NO;
	/** 经销商名称 **/
	private String CHANN_NAME;
	/** 流程跟踪ID **/
	private String ORDER_TRACE_ID;
	/** 上级经销商ID **/
	private String CHANN_ID_P;
	/** 上级经销商编号 **/
	private String CHANN_NO_P;
	/** 上级经销商名称 **/
	private String CHANN_NAME_P;
	/** 来源单据ID **/
	private String FROM_BILL_ID;
	/** 退回高亮显示标记 **/
	private String RETURN_SHOW_FLAG;
	/** 订货总金额 **/
	private String TOTAL_AMOUNT;
	/** 订货总返利 **/
	private String TOTAL_REBATE;

	/** 审核状态 */
	private String auditStatus;
	/** 审核意见 */
	private String auditContents;

	/** 附件路径 */
	private String attPath;

	public String getGOODS_ORDER_ID() {
		return GOODS_ORDER_ID;
	}

	public void setGOODS_ORDER_ID(String gOODS_ORDER_ID) {
		GOODS_ORDER_ID = gOODS_ORDER_ID;
	}

	public String getGOODS_ORDER_NO() {
		return GOODS_ORDER_NO;
	}

	public void setGOODS_ORDER_NO(String gOODS_ORDER_NO) {
		GOODS_ORDER_NO = gOODS_ORDER_NO;
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

	public String getBILL_TYPE() {
		return BILL_TYPE;
	}

	public void setBILL_TYPE(String bILL_TYPE) {
		BILL_TYPE = bILL_TYPE;
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

	public String getTEL() {
		return TEL;
	}

	public void setTEL(String tEL) {
		TEL = tEL;
	}

	public String getRECV_ADDR() {
		return RECV_ADDR;
	}

	public void setRECV_ADDR(String rECV_ADDR) {
		RECV_ADDR = rECV_ADDR;
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
		REMARK = rEMARK2;
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

	public String getAUDIT_ID() {
		return AUDIT_ID;
	}

	public void setAUDIT_ID(String aUDIT_ID) {
		AUDIT_ID = aUDIT_ID;
	}

	public String getAUDIT_NAME() {
		return AUDIT_NAME;
	}

	public void setAUDIT_NAME(String aUDIT_NAME) {
		AUDIT_NAME = aUDIT_NAME;
	}

	public String getAUDIT_TIME() {
		return AUDIT_TIME;
	}

	public void setAUDIT_TIME(String aUDIT_TIME) {
		AUDIT_TIME = aUDIT_TIME;
	}

	public String getDEPT_ID() {
		return DEPT_ID;
	}

	public void setDEPT_ID(String dEPT_ID) {
		DEPT_ID = dEPT_ID;
	}

	public String getDEPT_NAME() {
		return DEPT_NAME;
	}

	public void setDEPT_NAME(String dEPT_NAME) {
		DEPT_NAME = dEPT_NAME;
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

	public String getLEDGER_ID() {
		return LEDGER_ID;
	}

	public void setLEDGER_ID(String lEDGER_ID) {
		LEDGER_ID = lEDGER_ID;
	}

	public String getLEDGER_NAME() {
		return LEDGER_NAME;
	}

	public void setLEDGER_NAME(String lEDGER_NAME) {
		LEDGER_NAME = lEDGER_NAME;
	}

	public String getDEL_FLAG() {
		return DEL_FLAG;
	}

	public void setDEL_FLAG(String dEL_FLAG) {
		DEL_FLAG = dEL_FLAG;
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

	public String getRECV_ADDR_NO() {
		return RECV_ADDR_NO;
	}

	public void setRECV_ADDR_NO(String rECV_ADDR_NO) {
		RECV_ADDR_NO = rECV_ADDR_NO;
	}

	public String getORDER_RECV_DATE() {
		return ORDER_RECV_DATE;
	}

	public void setORDER_RECV_DATE(String oRDER_RECV_DATE) {
		ORDER_RECV_DATE = oRDER_RECV_DATE;
	}

	public String getORDER_DATE() {
		return ORDER_DATE;
	}

	public void setORDER_DATE(String oRDER_DATE) {
		ORDER_DATE = oRDER_DATE;
	}

	public String getRETURN_RSON() {
		return RETURN_RSON;
	}

	public void setRETURN_RSON(String rETURN_RSON) {
		RETURN_RSON = rETURN_RSON;
	}

	public String getPRINT_NAME() {
		return PRINT_NAME;
	}

	public void setPRINT_NAME(String pRINT_NAME) {
		PRINT_NAME = pRINT_NAME;
	}

	public String getCUST_NAME() {
		return CUST_NAME;
	}

	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
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

	public String getORDER_DELIVERY_DATE() {
		return ORDER_DELIVERY_DATE;
	}

	public void setORDER_DELIVERY_DATE(String oRDER_DELIVERY_DATE) {
		ORDER_DELIVERY_DATE = oRDER_DELIVERY_DATE;
	}

	public String getPRE_RECV_DATE() {
		return PRE_RECV_DATE;
	}

	public void setPRE_RECV_DATE(String pRE_RECV_DATE) {
		PRE_RECV_DATE = pRE_RECV_DATE;
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

	public String getORDER_TRACE_ID() {
		return ORDER_TRACE_ID;
	}

	public void setORDER_TRACE_ID(String oRDER_TRACE_ID) {
		ORDER_TRACE_ID = oRDER_TRACE_ID;
	}

	public String getCHANN_ID_P() {
		return CHANN_ID_P;
	}

	public void setCHANN_ID_P(String cHANN_ID_P) {
		CHANN_ID_P = cHANN_ID_P;
	}

	public String getCHANN_NO_P() {
		return CHANN_NO_P;
	}

	public void setCHANN_NO_P(String cHANN_NO_P) {
		CHANN_NO_P = cHANN_NO_P;
	}

	public String getCHANN_NAME_P() {
		return CHANN_NAME_P;
	}

	public void setCHANN_NAME_P(String cHANN_NAME_P) {
		CHANN_NAME_P = cHANN_NAME_P;
	}

	public String getFROM_BILL_ID() {
		return FROM_BILL_ID;
	}

	public void setFROM_BILL_ID(String fROM_BILL_ID) {
		FROM_BILL_ID = fROM_BILL_ID;
	}

	public String getRETURN_SHOW_FLAG() {
		return RETURN_SHOW_FLAG;
	}

	public void setRETURN_SHOW_FLAG(String rETURN_SHOW_FLAG) {
		RETURN_SHOW_FLAG = rETURN_SHOW_FLAG;
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

	public String getAttPath() {
		return attPath;
	}

	public void setAttPath(String attPath) {
		this.attPath = attPath;
	}

}
