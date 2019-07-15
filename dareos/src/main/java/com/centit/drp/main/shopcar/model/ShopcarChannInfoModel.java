package com.centit.drp.main.shopcar.model;

import com.centit.commons.model.BaseModel;

public class ShopcarChannInfoModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4389554420402174757L;
	/**
	 * 收货渠道ID
	 */
	private String CHANN_ID;
	/**
	 * 收货渠道编号
	 */
	private String CHANN_NO;
	/**
	 * 收货渠道名称
	 */
	private String CHANN_NAME;
	/**
	 * 联系人
	 */
	private String PERSON_CON;
	/**
	 * 电话
	 */
	private String TEL;
	/**
	 * 详细地址
	 */
	private String RECV_ADDR;
	
	
	/**
	 * 运输方式
	 */
	private String TRANSPORT;
	
	/**
	 * 运输结算方式
	 */
	private String TRANSPORT_SETTLE;
	
	/**
	 * 备注
	 */
	private String REMARK;
	
	/**
	 * 客户姓名
	 */
	private String CUST_NAME;
	/**
	 * 客户电话
	 */
	private String CUST_TEL;
	/**
	 * 客户住址
	 */
	private String CUST_ADDR;
	
	/**
	 * 附件路径
	 */
	private String ATTR_PATH;
	
	
	/**
	 * @return the cHANN_ID
	 */
	public String getCHANN_ID() {
		return CHANN_ID;
	}
	/**
	 * @param cHANNID the cHANN_ID to set
	 */
	public void setCHANN_ID(String cHANNID) {
		CHANN_ID = cHANNID;
	}
	/**
	 * @return the cHANN_NO
	 */
	public String getCHANN_NO() {
		return CHANN_NO;
	}
	/**
	 * @param cHANNNO the cHANN_NO to set
	 */
	public void setCHANN_NO(String cHANNNO) {
		CHANN_NO = cHANNNO;
	}
	/**
	 * @return the cHANN_NAME
	 */
	public String getCHANN_NAME() {
		return CHANN_NAME;
	}
	/**
	 * @param cHANNNAME the cHANN_NAME to set
	 */
	public void setCHANN_NAME(String cHANNNAME) {
		CHANN_NAME = cHANNNAME;
	}
	/**
	 * @return the pERSON_CON
	 */
	public String getPERSON_CON() {
		return PERSON_CON;
	}
	/**
	 * @param pERSONCON the pERSON_CON to set
	 */
	public void setPERSON_CON(String pERSONCON) {
		PERSON_CON = pERSONCON;
	}
	/**
	 * @return the tEL
	 */
	public String getTEL() {
		return TEL;
	}
	/**
	 * @param tEL the tEL to set
	 */
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	/**
	 * @return the rECV_ADDR
	 */
	public String getRECV_ADDR() {
		return RECV_ADDR;
	}
	/**
	 * @param rECVADDR the rECV_ADDR to set
	 */
	public void setRECV_ADDR(String rECVADDR) {
		RECV_ADDR = rECVADDR;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
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
	public String getATTR_PATH() {
		return ATTR_PATH;
	}
	public void setATTR_PATH(String aTTR_PATH) {
		ATTR_PATH = aTTR_PATH;
	}
	
	
}
