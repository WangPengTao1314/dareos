/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ProductModel.java
 */

package com.centit.drp.main.myorder.model;

import com.centit.commons.model.BaseModel;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductModel.
 * 
 * @module 系统管理
 * @func 货品信息
 * @version 1.1
 * @author 刘曰刚
 */
public class MyorderModel extends BaseModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4943417535815136771L;
	
	/**
	 * 购物车ID
	 */
	private String SHOP_CART_ID;
	
	/**
	 * 货品ID
	 */
	private String PRD_ID;
	
	/**
	 * 货品编号
	 */
	private String PRD_NO;
	
	/**
	 * 货品名称
	 */
	private String PRD_NAME;
	
	/**
	 * 规格型号
	 */
	private String PRD_SPEC;

	/**
	 * 颜色
	 */
	private String PRD_COLOR;

	/**
	 * 品牌
	 */
	private String BRAND;

	/**
	 * 标准单位
	 */
	private String STD_UNIT;
	
	
	/**
	 * 单价
	 */
	private String PRICE;
	
	/**
	 * 折扣率
	 */
	private String DECT_RATE;
	
	/**
	 * 折扣价
	 */
	private String DECT_PRICE;
	
	/**
	 * 订货数量
	 */
	private String ORDER_NUM;
	
	/**
	 * 订货金额
	 */
	private String ORDER_AMOUNT;
	
	/**
	 * 会话id
	 */
	private String SESSION_ID;
	
	/**
	 * 处理标记
	 */
	private String DEAL_FLAG;
	/**
	 * 行号
	 */
	private String ROW_NO;
	/**
	 * 门洞尺寸、规格
	 */
	private String HOLE_SPEC;
	/**
	 * 材质
	 */
	private String PRD_QUALITY;
	/**
	 * 推向
	 */
	private String PRD_TOWARD;
	/**
	 * 玻璃
	 */
	private String PRD_GLASS;
	/**
	 * 其他
	 */
	private String PRD_OTHER;
	
	/**
	 * 系列
	 */
	private String PRD_SERIES;
	
	/** 订单特殊工艺ID*/
	private String SPCL_TECH_ID;
	
	/**
	 * 备注
	 */
	private String REMARK;
	
	/**
	 * 尺寸
	 */
	private String PRD_SIZE;
	
	/**
	 * 附件路径
	 */
	private String ATT_PATH;
	/**
	 * 帐套ID
	 */
	private String LEDGER_ID;
	/**
	 * 帐套名称
	 */
	private String LEDGER_NAME;
	
	/**
	 * 投影面积
	 */
	private String PROJECTED_AREA;
	
	/**
	 * 展开面积
	 */
	private String EXPAND_AREA;
	
	/**
	 * 货品图片
	 */
	private String PIC_PATH;
	
	

	public String getPIC_PATH() {
		return PIC_PATH;
	}

	public void setPIC_PATH(String pIC_PATH) {
		PIC_PATH = pIC_PATH;
	}

	/**
	 * @return the sHOP_CART_ID
	 */
	public String getSHOP_CART_ID() {
		return SHOP_CART_ID;
	}

	/**
	 * @param sHOPCARTID the sHOP_CART_ID to set
	 */
	public void setSHOP_CART_ID(String sHOPCARTID) {
		SHOP_CART_ID = sHOPCARTID;
	}

	/**
	 * @return the pRD_ID
	 */
	public String getPRD_ID() {
		return PRD_ID;
	}

	/**
	 * @param pRDID the pRD_ID to set
	 */
	public void setPRD_ID(String pRDID) {
		PRD_ID = pRDID;
	}

	/**
	 * @return the pRD_NO
	 */
	public String getPRD_NO() {
		return PRD_NO;
	}

	/**
	 * @param pRDNO the pRD_NO to set
	 */
	public void setPRD_NO(String pRDNO) {
		PRD_NO = pRDNO;
	}

	/**
	 * @return the pRD_NAME
	 */
	public String getPRD_NAME() {
		return PRD_NAME;
	}

	/**
	 * @param pRDNAME the pRD_NAME to set
	 */
	public void setPRD_NAME(String pRDNAME) {
		PRD_NAME = pRDNAME;
	}

	/**
	 * @return the pRD_SPEC
	 */
	public String getPRD_SPEC() {
		return PRD_SPEC;
	}

	/**
	 * @param pRDSPEC the pRD_SPEC to set
	 */
	public void setPRD_SPEC(String pRDSPEC) {
		PRD_SPEC = pRDSPEC;
	}

	/**
	 * @return the pRD_COLOR
	 */
	public String getPRD_COLOR() {
		return PRD_COLOR;
	}

	/**
	 * @param pRDCOLOR the pRD_COLOR to set
	 */
	public void setPRD_COLOR(String pRDCOLOR) {
		PRD_COLOR = pRDCOLOR;
	}

	/**
	 * @return the bRAND
	 */
	public String getBRAND() {
		return BRAND;
	}

	/**
	 * @param bRAND the bRAND to set
	 */
	public void setBRAND(String bRAND) {
		BRAND = bRAND;
	}

	/**
	 * @return the sTD_UNIT
	 */
	public String getSTD_UNIT() {
		return STD_UNIT;
	}

	/**
	 * @param sTDUNIT the sTD_UNIT to set
	 */
	public void setSTD_UNIT(String sTDUNIT) {
		STD_UNIT = sTDUNIT;
	}


	/**
	 * @return the pRICE
	 */
	public String getPRICE() {
		return PRICE;
	}

	/**
	 * @param pRICE the pRICE to set
	 */
	public void setPRICE(String pRICE) {
		PRICE = pRICE;
	}

	/**
	 * @return the dECT_RATE
	 */
	public String getDECT_RATE() {
		return DECT_RATE;
	}

	/**
	 * @param dECTRATE the dECT_RATE to set
	 */
	public void setDECT_RATE(String dECTRATE) {
		DECT_RATE = dECTRATE;
	}

	/**
	 * @return the dECT_PRICE
	 */
	public String getDECT_PRICE() {
		return DECT_PRICE;
	}

	/**
	 * @param dECTPRICE the dECT_PRICE to set
	 */
	public void setDECT_PRICE(String dECTPRICE) {
		DECT_PRICE = dECTPRICE;
	}

	/**
	 * @return the oRDER_NUM
	 */
	public String getORDER_NUM() {
		return ORDER_NUM;
	}

	/**
	 * @param oRDERNUM the oRDER_NUM to set
	 */
	public void setORDER_NUM(String oRDERNUM) {
		ORDER_NUM = oRDERNUM;
	}

	/**
	 * @return the oRDER_AMOUNT
	 */
	public String getORDER_AMOUNT() {
		return ORDER_AMOUNT;
	}

	/**
	 * @param oRDERAMOUNT the oRDER_AMOUNT to set
	 */
	public void setORDER_AMOUNT(String oRDERAMOUNT) {
		ORDER_AMOUNT = oRDERAMOUNT;
	}

	/**
	 * @return the sESSION_ID
	 */
	public String getSESSION_ID() {
		return SESSION_ID;
	}

	/**
	 * @param sESSIONID the sESSION_ID to set
	 */
	public void setSESSION_ID(String sESSIONID) {
		SESSION_ID = sESSIONID;
	}

	/**
	 * @return the dEAL_FLAG
	 */
	public String getDEAL_FLAG() {
		return DEAL_FLAG;
	}

	/**
	 * @param dEALFLAG the dEAL_FLAG to set
	 */
	public void setDEAL_FLAG(String dEALFLAG) {
		DEAL_FLAG = dEALFLAG;
	}

	/**
	 * @return the sPCL_TECH_ID
	 */
	public String getSPCL_TECH_ID() {
		return SPCL_TECH_ID;
	}

	/**
	 * @param sPCLTECHID the sPCL_TECH_ID to set
	 */
	public void setSPCL_TECH_ID(String sPCLTECHID) {
		SPCL_TECH_ID = sPCLTECHID;
	}

	public String getROW_NO() {
		return ROW_NO;
	}

	public void setROW_NO(String rOW_NO) {
		ROW_NO = rOW_NO;
	}


	public String getPRD_GLASS() {
		return PRD_GLASS;
	}

	public void setPRD_GLASS(String pRD_GLASS) {
		PRD_GLASS = pRD_GLASS;
	}

	public String getHOLE_SPEC() {
		return HOLE_SPEC;
	}

	public void setHOLE_SPEC(String hOLE_SPEC) {
		HOLE_SPEC = hOLE_SPEC;
	}

	public String getPRD_QUALITY() {
		return PRD_QUALITY;
	}

	public void setPRD_QUALITY(String pRD_QUALITY) {
		PRD_QUALITY = pRD_QUALITY;
	}

	public String getPRD_TOWARD() {
		return PRD_TOWARD;
	}

	public void setPRD_TOWARD(String pRD_TOWARD) {
		PRD_TOWARD = pRD_TOWARD;
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

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	public String getPRD_SIZE() {
		return PRD_SIZE;
	}

	public void setPRD_SIZE(String pRD_SIZE) {
		PRD_SIZE = pRD_SIZE;
	}

	public String getATT_PATH() {
		return ATT_PATH;
	}

	public void setATT_PATH(String aTT_PATH) {
		ATT_PATH = aTT_PATH;
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

	public String getPROJECTED_AREA() {
		return PROJECTED_AREA;
	}

	public void setPROJECTED_AREA(String pROJECTED_AREA) {
		PROJECTED_AREA = pROJECTED_AREA;
	}

	public String getEXPAND_AREA() {
		return EXPAND_AREA;
	}

	public void setEXPAND_AREA(String eXPAND_AREA) {
		EXPAND_AREA = eXPAND_AREA;
	}

	
	
}
