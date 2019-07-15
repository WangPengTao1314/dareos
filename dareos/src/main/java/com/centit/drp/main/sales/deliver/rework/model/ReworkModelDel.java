package com.centit.drp.main.sales.deliver.rework.model;

import java.util.Date;

public class ReworkModelDel {
	
	String  SALE_ORDER_DTL_ID;//销售订单明细ID  
    String  SALE_ORDER_ID;//销售订单ID  
    String  GROUP_NO;//组号  
    String  PRD_ID;//货品ID  
    String  PRD_NO;//货品编号  
    String  PRD_NAME;//货品名称  
    String  HOLE_SPEC;//门洞尺寸  
    String  PRD_SPEC;//规格尺寸  
    String  PRD_TOWARD;//推向  
    String  PRD_QUALITY;//材质  
    String  PRD_COLOR;//颜色  
    String  PRD_GLASS;//玻璃  
    String  PRD_OTHER;//其它  
    String  PRD_SERIES;//系列  
    String  BRAND;//品牌  
    String  STD_UNIT;//标准单位  
    Integer  IS_NO_STAND_FLAG;//0=否,1=是  
    Double  PRICE;//单价  
    Double  DECT_RATE;//折扣率  
    Double  DECT_PRICE;//折后单价  
    Double  REBATE_PRICE;//返利单价  
    Double  REBATE_AMOUNT;//返利金额  
    Double  ORDER_NUM;//订货数量  
    Double  ORDER_AMOUNT;//订货金额  
    Date  ADVC_SEND_DATE;//预计发货日期  
    String  REMARK;//备注  
    Integer  DEL_FLAG;//0=否,1=是  
    String  FROM_BILL_DTL_ID;//来源单据明细ID  
    Integer  SENDED_NUM;//已实际发货数量  
    Integer  ROW_NO;
    String ATT_PATH;
	public String getSALE_ORDER_DTL_ID() {
		return SALE_ORDER_DTL_ID;
	}
	public void setSALE_ORDER_DTL_ID(String sALE_ORDER_DTL_ID) {
		SALE_ORDER_DTL_ID = sALE_ORDER_DTL_ID;
	}
	public String getSALE_ORDER_ID() {
		return SALE_ORDER_ID;
	}
	public void setSALE_ORDER_ID(String sALE_ORDER_ID) {
		SALE_ORDER_ID = sALE_ORDER_ID;
	}
	public String getGROUP_NO() {
		return GROUP_NO;
	}
	public void setGROUP_NO(String gROUP_NO) {
		GROUP_NO = gROUP_NO;
	}
	public String getPRD_ID() {
		return PRD_ID;
	}
	public void setPRD_ID(String pRD_ID) {
		PRD_ID = pRD_ID;
	}
	public String getPRD_NO() {
		return PRD_NO;
	}
	public void setPRD_NO(String pRD_NO) {
		PRD_NO = pRD_NO;
	}
	public String getPRD_NAME() {
		return PRD_NAME;
	}
	public void setPRD_NAME(String pRD_NAME) {
		PRD_NAME = pRD_NAME;
	}
	public String getHOLE_SPEC() {
		return HOLE_SPEC;
	}
	public void setHOLE_SPEC(String hOLE_SPEC) {
		HOLE_SPEC = hOLE_SPEC;
	}
	public String getPRD_SPEC() {
		return PRD_SPEC;
	}
	public void setPRD_SPEC(String pRD_SPEC) {
		PRD_SPEC = pRD_SPEC;
	}
	public String getPRD_TOWARD() {
		return PRD_TOWARD;
	}
	public void setPRD_TOWARD(String pRD_TOWARD) {
		PRD_TOWARD = pRD_TOWARD;
	}
	public String getPRD_QUALITY() {
		return PRD_QUALITY;
	}
	public void setPRD_QUALITY(String pRD_QUALITY) {
		PRD_QUALITY = pRD_QUALITY;
	}
	public String getPRD_COLOR() {
		return PRD_COLOR;
	}
	public void setPRD_COLOR(String pRD_COLOR) {
		PRD_COLOR = pRD_COLOR;
	}
	public String getPRD_GLASS() {
		return PRD_GLASS;
	}
	public void setPRD_GLASS(String pRD_GLASS) {
		PRD_GLASS = pRD_GLASS;
	}
	public String getPRD_OTHER() {
		return PRD_OTHER;
	}
	public void setPRD_OTHER(String pRD_OTHER) {
		PRD_OTHER = pRD_OTHER;
	}
	public String getPRD_SERIES() {
		return PRD_SERIES;
	}
	public void setPRD_SERIES(String pRD_SERIES) {
		PRD_SERIES = pRD_SERIES;
	}
	public String getBRAND() {
		return BRAND;
	}
	public void setBRAND(String bRAND) {
		BRAND = bRAND;
	}
	public String getSTD_UNIT() {
		return STD_UNIT;
	}
	public void setSTD_UNIT(String sTD_UNIT) {
		STD_UNIT = sTD_UNIT;
	}
	public Integer getIS_NO_STAND_FLAG() {
		return IS_NO_STAND_FLAG;
	}
	public void setIS_NO_STAND_FLAG(Integer iS_NO_STAND_FLAG) {
		IS_NO_STAND_FLAG = iS_NO_STAND_FLAG;
	}
	public Double getPRICE() {
		return PRICE;
	}
	public void setPRICE(Double pRICE) {
		PRICE = pRICE;
	}
	public Double getDECT_RATE() {
		return DECT_RATE;
	}
	public void setDECT_RATE(Double dECT_RATE) {
		DECT_RATE = dECT_RATE;
	}
	public Double getDECT_PRICE() {
		return DECT_PRICE;
	}
	public void setDECT_PRICE(Double dECT_PRICE) {
		DECT_PRICE = dECT_PRICE;
	}
	public Double getREBATE_PRICE() {
		return REBATE_PRICE;
	}
	public void setREBATE_PRICE(Double rEBATE_PRICE) {
		REBATE_PRICE = rEBATE_PRICE;
	}
	public Double getREBATE_AMOUNT() {
		return REBATE_AMOUNT;
	}
	public void setREBATE_AMOUNT(Double rEBATE_AMOUNT) {
		REBATE_AMOUNT = rEBATE_AMOUNT;
	}
	public Double getORDER_NUM() {
		return ORDER_NUM;
	}
	public void setORDER_NUM(Double oRDER_NUM) {
		ORDER_NUM = oRDER_NUM;
	}
	public Double getORDER_AMOUNT() {
		return ORDER_AMOUNT;
	}
	public void setORDER_AMOUNT(Double oRDER_AMOUNT) {
		ORDER_AMOUNT = oRDER_AMOUNT;
	}
	public Date getADVC_SEND_DATE() {
		return ADVC_SEND_DATE;
	}
	public void setADVC_SEND_DATE(Date aDVC_SEND_DATE) {
		ADVC_SEND_DATE = aDVC_SEND_DATE;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public Integer getDEL_FLAG() {
		return DEL_FLAG;
	}
	public void setDEL_FLAG(Integer dEL_FLAG) {
		DEL_FLAG = dEL_FLAG;
	}
	public String getFROM_BILL_DTL_ID() {
		return FROM_BILL_DTL_ID;
	}
	public void setFROM_BILL_DTL_ID(String fROM_BILL_DTL_ID) {
		FROM_BILL_DTL_ID = fROM_BILL_DTL_ID;
	}
	public Integer getSENDED_NUM() {
		return SENDED_NUM;
	}
	public void setSENDED_NUM(Integer sENDED_NUM) {
		SENDED_NUM = sENDED_NUM;
	}
	public Integer getROW_NO() {
		return ROW_NO;
	}
	public void setROW_NO(Integer rOW_NO) {
		ROW_NO = rOW_NO;
	}
	public String getATT_PATH() {
		return ATT_PATH;
	}
	public void setATT_PATH(String aTT_PATH) {
		ATT_PATH = aTT_PATH;
	}
    
	

}
