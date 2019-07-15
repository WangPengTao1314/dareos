package com.centit.base.chann.model;

public class DeliveraddrModelChld {
	
	/** 送货地址信息ID **/
	private String DELIVER_ADDR_ID;
	/** 渠道ID **/
	private String CHANN_ID;
	/** 送货地址信息编号**/
	private String DELIVER_ADDR_NO;
	/** 送货详细地址**/
	private String DELIVER_DTL_ADDR;
	/**状态*/
	private String STATE;
	/**删除标记*/
	private String DEL_FLAG;
	/**收货联系人*/
	private String PERSON_CON;
	/**收货电话*/
	private String TEL;
	/**运输方式*/
	private String TRANSPORT;
	
	public String getDELIVER_ADDR_ID() {
		return DELIVER_ADDR_ID;
	}
	public void setDELIVER_ADDR_ID(String dELIVERADDRID) {
		DELIVER_ADDR_ID = dELIVERADDRID;
	}
	public String getCHANN_ID() {
		return CHANN_ID;
	}
	public void setCHANN_ID(String cHANNID) {
		CHANN_ID = cHANNID;
	}
	public String getDELIVER_ADDR_NO() {
		return DELIVER_ADDR_NO;
	}
	public void setDELIVER_ADDR_NO(String dELIVERADDRNO) {
		DELIVER_ADDR_NO = dELIVERADDRNO;
	}
	public String getDELIVER_DTL_ADDR() {
		return DELIVER_DTL_ADDR;
	}
	public void setDELIVER_DTL_ADDR(String dELIVERDTLADDR) {
		DELIVER_DTL_ADDR = dELIVERDTLADDR;
	}
	public String getSTATE() {
		return STATE;
	}
	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}
	public String getDEL_FLAG() {
		return DEL_FLAG;
	}
	public void setDEL_FLAG(String dELFLAG) {
		DEL_FLAG = dELFLAG;
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
	public String getTRANSPORT() {
		return TRANSPORT;
	}
	public void setTRANSPORT(String tRANSPORT) {
		TRANSPORT = tRANSPORT;
	}
	
	
	

}
