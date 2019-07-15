
package com.centit.base.provider.model;

/**
 * 
 * @module 系统管理
 * @func 供应商信息
 * @version 1.0
 */
public class ProviderModel {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4943417535815136771L;

	/** PRVD_ID .*/
	private String PRVD_ID; //供应商ID
	/** PRVD_NO .*/
	private String PRVD_NO; //供应商编号
	/** PRVD_NAME .*/
	private String PRVD_NAME; //供应商名称
	/** PRVD_TYPE .*/
	private String PRVD_TYPE; //供应商类别
	/** PRVD_LVL .*/
	private String PRVD_LVL; //供应商级别
	/** PRVD_NATRUE .*/
	private String PRVD_NATRUE; //供应商性质
	/** ZONE_ID .*/
	private String ZONE_ID; //行政区划ID
	/** NATION .*/
	private String NATION; //国家
	/** PROV .*/
	private String PROV; //省份
	/** CITY .*/
	private String CITY; //城市
	/** COUNTY .*/
	private String COUNTY; //区县
	/** PRVD_CY .*/
	private String PRVD_CY; //供货周期
	/** PRVD_CAP .*/
	private String PRVD_CAP; //供货能力
	/** PERSON_BUSS .*/
	private String PERSON_BUSS; //业务员
	/** PERSON_CON .*/
	private String PERSON_CON; //联系人
	/** TEL .*/
	private String TEL; //电话
	/** MOBILE_PHONE .*/
	private String MOBILE_PHONE; //手机
	/** TAX .*/
	private String TAX; //传真
	/** POST .*/
	private String POST; //邮政编号
	/** ADDRESS .*/
	private String ADDRESS; //详细地址
	/** EMAIL .*/
	private String EMAIL; //电子邮件
	/** WEB .*/
	private String WEB; //网站
	/** LEGAL_REPRE .*/
	private String LEGAL_REPRE; //法人代表
	/** BUSS_LIC .*/
	private String BUSS_LIC; //营业执照号
	/** INVOICE_TI .*/
	private String INVOICE_TI; //发票抬头
	/** INVOICE_ADDR .*/
	private String INVOICE_ADDR; //发票地址
	/** OPENING_BANK .*/
	private String OPENING_BANK; //开户行
	/** BANK_ACCT .*/
	private String BANK_ACCT; //银行账号
	/** FI_CMP_NO .*/
	private String FI_CMP_NO; //财务对照码
	/** STATE .*/
	private String STATE; //状态
	/** REMARK .*/
	private String REMARK; //备注
	/** CREATOR .*/
	private String CREATOR; //制单人ID
	/** CRE_NAME .*/
	private String CRE_NAME; //制单人名称
	/** CRE_TIME .*/
	private String CRE_TIME; //制单时间
	/** UPDATOR .*/
	private String UPDATOR; //更新人ID
	/** UPD_NAME .*/
	private String UPD_NAME; //更新人名称
	/** UPD_TIME .*/
	private String UPD_TIME; //更新时间
	/** DEPT_ID .*/
	private String DEPT_ID; //制单部门ID
	/** DEPT_NAME .*/
	private String DEPT_NAME; //制单部门名称
	/** ORG_ID .*/
	private String ORG_ID; //制单机构ID
	/** ORG_NAME .*/
	private String ORG_NAME; //制单机构名称
	/** LEDGER_ID .*/
	private String LEDGER_ID; //帐套ID
	/** LEDGER_NAME .*/
	private String LEDGER_NAME; //帐套名称
	/** DEL_FLAG .*/
	private String DEL_FLAG; //删除标记
	
	private String DEFAULT_FLAG;//默认标记

	public String getPRVD_ID() {
		return PRVD_ID;
	}

	public void setPRVD_ID(String pRVD_ID) {
		PRVD_ID = pRVD_ID;
	}

	public String getPRVD_NO() {
		return PRVD_NO;
	}

	public void setPRVD_NO(String pRVD_NO) {
		PRVD_NO = pRVD_NO;
	}

	public String getPRVD_NAME() {
		return PRVD_NAME;
	}

	public void setPRVD_NAME(String pRVD_NAME) {
		PRVD_NAME = pRVD_NAME;
	}

	public String getPRVD_TYPE() {
		return PRVD_TYPE;
	}

	public void setPRVD_TYPE(String pRVD_TYPE) {
		PRVD_TYPE = pRVD_TYPE;
	}

	public String getPRVD_LVL() {
		return PRVD_LVL;
	}

	public void setPRVD_LVL(String pRVD_LVL) {
		PRVD_LVL = pRVD_LVL;
	}

	public String getPRVD_NATRUE() {
		return PRVD_NATRUE;
	}

	public void setPRVD_NATRUE(String pRVD_NATRUE) {
		PRVD_NATRUE = pRVD_NATRUE;
	}

	public String getZONE_ID() {
		return ZONE_ID;
	}

	public void setZONE_ID(String zONE_ID) {
		ZONE_ID = zONE_ID;
	}

	public String getNATION() {
		return NATION;
	}

	public void setNATION(String nATION) {
		NATION = nATION;
	}

	public String getPROV() {
		return PROV;
	}

	public void setPROV(String pROV) {
		PROV = pROV;
	}

	public String getCITY() {
		return CITY;
	}

	public void setCITY(String cITY) {
		CITY = cITY;
	}

	public String getCOUNTY() {
		return COUNTY;
	}

	public void setCOUNTY(String cOUNTY) {
		COUNTY = cOUNTY;
	}

	public String getPRVD_CY() {
		return PRVD_CY;
	}

	public void setPRVD_CY(String pRVD_CY) {
		PRVD_CY = pRVD_CY;
	}

	public String getPRVD_CAP() {
		return PRVD_CAP;
	}

	public void setPRVD_CAP(String pRVD_CAP) {
		PRVD_CAP = pRVD_CAP;
	}

	public String getPERSON_BUSS() {
		return PERSON_BUSS;
	}

	public void setPERSON_BUSS(String pERSON_BUSS) {
		PERSON_BUSS = pERSON_BUSS;
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

	public String getMOBILE_PHONE() {
		return MOBILE_PHONE;
	}

	public void setMOBILE_PHONE(String mOBILE_PHONE) {
		MOBILE_PHONE = mOBILE_PHONE;
	}

	public String getTAX() {
		return TAX;
	}

	public void setTAX(String tAX) {
		TAX = tAX;
	}

	public String getPOST() {
		return POST;
	}

	public void setPOST(String pOST) {
		POST = pOST;
	}

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getWEB() {
		return WEB;
	}

	public void setWEB(String wEB) {
		WEB = wEB;
	}

	public String getLEGAL_REPRE() {
		return LEGAL_REPRE;
	}

	public void setLEGAL_REPRE(String lEGAL_REPRE) {
		LEGAL_REPRE = lEGAL_REPRE;
	}

	public String getBUSS_LIC() {
		return BUSS_LIC;
	}

	public void setBUSS_LIC(String bUSS_LIC) {
		BUSS_LIC = bUSS_LIC;
	}

	public String getINVOICE_TI() {
		return INVOICE_TI;
	}

	public void setINVOICE_TI(String iNVOICE_TI) {
		INVOICE_TI = iNVOICE_TI;
	}

	public String getINVOICE_ADDR() {
		return INVOICE_ADDR;
	}

	public void setINVOICE_ADDR(String iNVOICE_ADDR) {
		INVOICE_ADDR = iNVOICE_ADDR;
	}

	public String getOPENING_BANK() {
		return OPENING_BANK;
	}

	public void setOPENING_BANK(String oPENING_BANK) {
		OPENING_BANK = oPENING_BANK;
	}

	public String getBANK_ACCT() {
		return BANK_ACCT;
	}

	public void setBANK_ACCT(String bANK_ACCT) {
		BANK_ACCT = bANK_ACCT;
	}

	public String getFI_CMP_NO() {
		return FI_CMP_NO;
	}

	public void setFI_CMP_NO(String fI_CMP_NO) {
		FI_CMP_NO = fI_CMP_NO;
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

	public String getDEFAULT_FLAG() {
		return DEFAULT_FLAG;
	}

	public void setDEFAULT_FLAG(String dEFAULT_FLAG) {
		DEFAULT_FLAG = dEFAULT_FLAG;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}