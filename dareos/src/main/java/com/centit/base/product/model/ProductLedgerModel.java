package com.centit.base.product.model;

import com.centit.commons.model.BaseModel;

public class ProductLedgerModel extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 货品帐套分管ID
	 */
	private String PRD_LEDGER_ID;
	/**
	 * 货品ID
	 */
	private String PRD_ID;
	/**
	 * 帐套ID
	 */
	private String LEDGER_ID;
	/**
	 * 帐套名称
	 */
	private String LEDGER_NAME;
	
	/**
	 * 简称
	 */
	private String LEDGER_NAME_ABBR;
	
	public String getPRD_LEDGER_ID() {
		return PRD_LEDGER_ID;
	}
	public void setPRD_LEDGER_ID(String pRD_LEDGER_ID) {
		PRD_LEDGER_ID = pRD_LEDGER_ID;
	}
	public String getPRD_ID() {
		return PRD_ID;
	}
	public void setPRD_ID(String pRD_ID) {
		PRD_ID = pRD_ID;
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
	public String getLEDGER_NAME_ABBR() {
		return LEDGER_NAME_ABBR;
	}
	public void setLEDGER_NAME_ABBR(String lEDGER_NAME_ABBR) {
		LEDGER_NAME_ABBR = lEDGER_NAME_ABBR;
	}
	
}
