/**
 * prjName:喜临门营销平台
 * ucName:订货订单维护
 * fileName:GoodsorderhdModelChld
*/
package com.centit.drp.sale.goodsorderhd.model;
import com.centit.commons.model.BaseModel;
/**
 * *@module
 * *@func 订货订单明细
 * *@version 1.1
 * *@author zzb
 * *@createdate 2013-09-15 10:35:10
 */
public class GoodsorderhdModelChld extends BaseModel{
   /**
	 *
	 */
	private static final long serialVersionUID = 1929584300934316770L;

/** 订货订单明细ID **/
   private String GOODS_ORDER_DTL_ID;
   /** 订货订单ID **/
   private String GOODS_ORDER_ID;
   /** 组号 **/
   private String GROUP_NO;
   /** 货品ID **/
   private String PRD_ID;
   /** 货品编号 **/
   private String PRD_NO;
   /** 货品名称 **/
   private String PRD_NAME;
   /** 规格型号 **/
   private String PRD_SPEC;
   /** 颜色 **/
   private String PRD_COLOR;
   /** 品牌 **/
   private String BRAND;
   /** 标准单位 **/
   private String STD_UNIT;
   /** 特殊工艺标记 **/
   private String SPCL_TECH_FLAG;
   /** 单价 **/
   private String PRICE;
   /** 折扣率 **/
   private String DECT_RATE;
   /** 折扣价 **/
   private String DECT_PRICE;
   /** 订货数量 **/
   private String ORDER_NUM;
   /** 订货金额 **/
   private String ORDER_AMOUNT;
   /** 备注 **/
   private String REMARK;
   /** 会话ID **/
   private String SESSION_ID;
   /** 特殊工艺加价倍数 **/
   private String TECH_PRICE_MULT;
   /** 删除标记 **/
   private String DEL_FLAG;
   /** 已实际发货数量 **/
   private String SENDED_NUM;
   /** 返利单价 **/
   private String REBATE_PRICE;
   /** 体积 **/
   private String VOLUME;
   /** 总体积 **/
   private String TOTAL_VOLUME;
   /** 原货品ID **/
   private String OLD_PRD_ID;
   /** 原货品编号  **/
   private String OLD_PRD_NO;
   /** 原货品名称  **/
   private String OLD_PRD_NAME;
   /** 原单价 **/
   private String OLD_PRICE;
   /** 特殊工艺id*/
   private String SPCL_TECH_ID;
   /** 原特殊工艺ID*/
   private String OLD_SPCL_TECH_ID;
   /** 是否非标  1->非标**/
   private String IS_NO_STAND_FLAG;
   /** 取消标记 **/
   private String CANCEL_FLAG;
   /** 要求到货日期 **/
   private String ORDER_RECV_DATE;
   /** 来源单据明细ID **/
   private String FROM_BILL_DTL_ID;
   /** 销售订单ID **/
   private String SALE_ORDER_ID;
   /** 原订货数量 **/
   private String OLD_ORDER_NUM;
   /** 返利金额 **/
   private String REBATE_AMOUNT;
   /** 特殊工艺加价倍数  **/
   private String TECH_MULT;
   /** 特殊工艺加价 金额  **/
   private String TECH_AMOUNT;
   private String SPCL_SPEC_REMARK;
   /** 是否备货，即是否抵库 **/
   private String IS_BACKUP_FLAG;
   /** 预计发货日期 **/
   private String ADVC_SEND_DATE;
   /** 冻结单价 **/
   private String CREDIT_FREEZE_PRICE;
   /** 行号 **/
   private String ROW_NO;
   /** 调价后的折扣率 **/
   private String ADJUST_DECT_RATE;
   /** 调价后的折单价 **/
   private String ADJUST_DECT_PRICE;
   /** 门洞尺寸 **/
   private String HOLE_SPEC;
   /** 推向 **/
   private String PRD_TOWARD;
   /** 材质 **/
   private String PRD_QUALITY;
   /** 玻璃 **/
   private String PRD_GLASS;
   /** 其它 **/
   private String PRD_OTHER;
   /** 系列 **/
   private String PRD_SERIES;
   /** 尺寸 **/
   private String PRD_SIZE;
   /** 投影面积 **/
   private String PROJECTED_AREA;
   /** 展开面积 **/
   private String EXPAND_AREA;
   /** 工程位置 **/
   private String PRD_PLACE;
	/** 是否返利 **/
	private String IS_NO_REBATE_FLAG;
	/** 是否开锁孔 **/
	private String IS_NO_LOCK_FLAG;

	/** 附件路径 */
	private String attPath;


     /**
     * get 订货订单明细ID value
     * @return the GOODS_ORDER_DTL_ID
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getGOODS_ORDER_DTL_ID(){
        return GOODS_ORDER_DTL_ID;
    }
	/**
     * set 订货订单明细ID value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setGOODS_ORDER_DTL_ID(String GOODS_ORDER_DTL_ID){
        this.GOODS_ORDER_DTL_ID=GOODS_ORDER_DTL_ID;
    }
     /**
     * get 订货订单ID value
     * @return the GOODS_ORDER_ID
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getGOODS_ORDER_ID(){
        return GOODS_ORDER_ID;
    }
	/**
     * set 订货订单ID value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setGOODS_ORDER_ID(String GOODS_ORDER_ID){
        this.GOODS_ORDER_ID=GOODS_ORDER_ID;
    }

    public String getGROUP_NO() {
		return GROUP_NO;
	}
	public void setGROUP_NO(String gROUP_NO) {
		GROUP_NO = gROUP_NO;
	}
	/**
     * get 货品ID value
     * @return the PRD_ID
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getPRD_ID(){
        return PRD_ID;
    }
	/**
     * set 货品ID value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setPRD_ID(String PRD_ID){
        this.PRD_ID=PRD_ID;
    }
     /**
     * get 货品编号 value
     * @return the PRD_NO
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getPRD_NO(){
        return PRD_NO;
    }
	/**
     * set 货品编号 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setPRD_NO(String PRD_NO){
        this.PRD_NO=PRD_NO;
    }
     /**
     * get 货品名称 value
     * @return the PRD_NAME
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getPRD_NAME(){
        return PRD_NAME;
    }
	/**
     * set 货品名称 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setPRD_NAME(String PRD_NAME){
        this.PRD_NAME=PRD_NAME;
    }
     /**
     * get 规格型号 value
     * @return the PRD_SPEC
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getPRD_SPEC(){
        return PRD_SPEC;
    }
	/**
     * set 规格型号 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setPRD_SPEC(String PRD_SPEC){
        this.PRD_SPEC=PRD_SPEC;
    }
     /**
     * get 颜色 value
     * @return the PRD_COLOR
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getPRD_COLOR(){
        return PRD_COLOR;
    }
	/**
     * set 颜色 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setPRD_COLOR(String PRD_COLOR){
        this.PRD_COLOR=PRD_COLOR;
    }
     /**
     * get 品牌 value
     * @return the BRAND
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getBRAND(){
        return BRAND;
    }
	/**
     * set 品牌 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setBRAND(String BRAND){
        this.BRAND=BRAND;
    }
     /**
     * get 标准单位 value
     * @return the STD_UNIT
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getSTD_UNIT(){
        return STD_UNIT;
    }
	/**
     * set 标准单位 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setSTD_UNIT(String STD_UNIT){
        this.STD_UNIT=STD_UNIT;
    }
     /**
     * get 特殊工艺标记 value
     * @return the SPCL_TECH_FLAG
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getSPCL_TECH_FLAG(){
        return SPCL_TECH_FLAG;
    }
	/**
     * set 特殊工艺标记 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setSPCL_TECH_FLAG(String SPCL_TECH_FLAG){
        this.SPCL_TECH_FLAG=SPCL_TECH_FLAG;
    }
     /**
     * get 单价 value
     * @return the PRICE
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getPRICE(){
        return PRICE;
    }
	/**
     * set 单价 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setPRICE(String PRICE){
        this.PRICE=PRICE;
    }
     /**
     * get 折扣率 value
     * @return the DECT_RATE
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getDECT_RATE(){
        return DECT_RATE;
    }
	/**
     * set 折扣率 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setDECT_RATE(String DECT_RATE){
        this.DECT_RATE=DECT_RATE;
    }
     /**
     * get 折扣价 value
     * @return the DECT_PRICE
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getDECT_PRICE(){
        return DECT_PRICE;
    }
	/**
     * set 折扣价 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setDECT_PRICE(String DECT_PRICE){
        this.DECT_PRICE=DECT_PRICE;
    }
     /**
     * get 订货数量 value
     * @return the ORDER_NUM
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getORDER_NUM(){
        return ORDER_NUM;
    }
	/**
     * set 订货数量 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setORDER_NUM(String ORDER_NUM){
        this.ORDER_NUM=ORDER_NUM;
    }
     /**
     * get 订货金额 value
     * @return the ORDER_AMOUNT
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getORDER_AMOUNT(){
        return ORDER_AMOUNT;
    }
	/**
     * set 订货金额 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setORDER_AMOUNT(String ORDER_AMOUNT){
        this.ORDER_AMOUNT=ORDER_AMOUNT;
    }
     /**
     * get 备注 value
     * @return the REMARK
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getREMARK(){
        return REMARK;
    }
	/**
     * set 备注 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setREMARK(String REMARK){
        this.REMARK=REMARK;
    }
     /**
     * get 会话ID value
     * @return the SESSION_ID
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getSESSION_ID(){
        return SESSION_ID;
    }
	/**
     * set 会话ID value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setSESSION_ID(String SESSION_ID){
        this.SESSION_ID=SESSION_ID;
    }
     /**
     * get 特殊工艺加价倍数 value
     * @return the TECH_PRICE_MULT
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getTECH_PRICE_MULT(){
        return TECH_PRICE_MULT;
    }
	/**
     * set 特殊工艺加价倍数 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setTECH_PRICE_MULT(String TECH_PRICE_MULT){
        this.TECH_PRICE_MULT=TECH_PRICE_MULT;
    }
     /**
     * get 删除标记 value
     * @return the DEL_FLAG
	 * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public String getDEL_FLAG(){
        return DEL_FLAG;
    }
	/**
     * set 删除标记 value
     * @createdate 2013-09-15 10:35:10
     * @author zzb
	 * @createdate 2013-09-15 10:35:10
     */
    public void setDEL_FLAG(String DEL_FLAG){
        this.DEL_FLAG=DEL_FLAG;
    }
	public String getSENDED_NUM() {
		return SENDED_NUM;
	}
	public void setSENDED_NUM(String sENDED_NUM) {
		SENDED_NUM = sENDED_NUM;
	}
	public String getREBATE_PRICE() {
		return REBATE_PRICE;
	}
	public void setREBATE_PRICE(String rEBATE_PRICE) {
		REBATE_PRICE = rEBATE_PRICE;
	}
	public String getREBATE_AMOUNT() {
		return REBATE_AMOUNT;
	}
	public void setREBATE_AMOUNT(String rEBATE_AMOUNT) {
		REBATE_AMOUNT = rEBATE_AMOUNT;
	}
	public String getVOLUME() {
		return VOLUME;
	}
	public void setVOLUME(String vOLUME) {
		VOLUME = vOLUME;
	}
	public String getTOTAL_VOLUME() {
		return TOTAL_VOLUME;
	}
	public void setTOTAL_VOLUME(String tOTALVOLUME) {
		TOTAL_VOLUME = tOTALVOLUME;
	}
	public String getOLD_PRD_ID() {
		return OLD_PRD_ID;
	}
	public void setOLD_PRD_ID(String oLDPRDID) {
		OLD_PRD_ID = oLDPRDID;
	}
	public String getOLD_PRD_NO() {
		return OLD_PRD_NO;
	}
	public void setOLD_PRD_NO(String oLDPRDNO) {
		OLD_PRD_NO = oLDPRDNO;
	}
	public String getOLD_PRD_NAME() {
		return OLD_PRD_NAME;
	}
	public void setOLD_PRD_NAME(String oLDPRDNAME) {
		OLD_PRD_NAME = oLDPRDNAME;
	}
	public String getOLD_PRICE() {
		return OLD_PRICE;
	}
	public void setOLD_PRICE(String oLDPRICE) {
		OLD_PRICE = oLDPRICE;
	}
	public String getSPCL_TECH_ID() {
		return SPCL_TECH_ID;
	}
	public void setSPCL_TECH_ID(String spcl_tech_id) {
		SPCL_TECH_ID = spcl_tech_id;
	}
	/**
	 * @return the oLD_SPCL_TECH_ID
	 */
	public String getOLD_SPCL_TECH_ID() {
		return OLD_SPCL_TECH_ID;
	}
	/**
	 * @param oLDSPCLTECHID the oLD_SPCL_TECH_ID to set
	 */
	public void setOLD_SPCL_TECH_ID(String oLDSPCLTECHID) {
		OLD_SPCL_TECH_ID = oLDSPCLTECHID;
	}
	public String getIS_NO_STAND_FLAG() {
		return IS_NO_STAND_FLAG;
	}
	public void setIS_NO_STAND_FLAG(String iSNOSTANDFLAG) {
		IS_NO_STAND_FLAG = iSNOSTANDFLAG;
	}
	public String getCANCEL_FLAG() {
		return CANCEL_FLAG;
	}
	public void setCANCEL_FLAG(String cANCEL_FLAG) {
		CANCEL_FLAG = cANCEL_FLAG;
	}
	public String getORDER_RECV_DATE() {
		return ORDER_RECV_DATE;
	}
	public void setORDER_RECV_DATE(String oRDERRECVDATE) {
		ORDER_RECV_DATE = oRDERRECVDATE;
	}
	public String getFROM_BILL_DTL_ID() {
		return FROM_BILL_DTL_ID;
	}
	public void setFROM_BILL_DTL_ID(String fROM_BILL_DTL_ID) {
		FROM_BILL_DTL_ID = fROM_BILL_DTL_ID;
	}
	public String getSALE_ORDER_ID() {
		return SALE_ORDER_ID;
	}
	public void setSALE_ORDER_ID(String sALE_ORDER_ID) {
		SALE_ORDER_ID = sALE_ORDER_ID;
	}
	public String getOLD_ORDER_NUM() {
		return OLD_ORDER_NUM;
	}
	public void setOLD_ORDER_NUM(String oLD_ORDER_NUM) {
		OLD_ORDER_NUM = oLD_ORDER_NUM;
	}
	public String getTECH_MULT() {
		return TECH_MULT;
	}
	public void setTECH_MULT(String tECHMULT) {
		TECH_MULT = tECHMULT;
	}
	public String getTECH_AMOUNT() {
		return TECH_AMOUNT;
	}
	public void setTECH_AMOUNT(String tECHAMOUNT) {
		TECH_AMOUNT = tECHAMOUNT;
	}
	/**
	 * @return the sPCL_SPEC_REMARK
	 */
	public String getSPCL_SPEC_REMARK() {
		return SPCL_SPEC_REMARK;
	}
	/**
	 * @param sPCLSPECREMARK the sPCL_SPEC_REMARK to set
	 */
	public void setSPCL_SPEC_REMARK(String sPCLSPECREMARK) {
		SPCL_SPEC_REMARK = sPCLSPECREMARK;
	}
	public String getIS_BACKUP_FLAG() {
		return IS_BACKUP_FLAG;
	}
	public void setIS_BACKUP_FLAG(String iSBACKUPFLAG) {
		IS_BACKUP_FLAG = iSBACKUPFLAG;
	}
	public String getADVC_SEND_DATE() {
		return ADVC_SEND_DATE;
	}
	public void setADVC_SEND_DATE(String aDVC_SEND_DATE) {
		ADVC_SEND_DATE = aDVC_SEND_DATE;
	}
	public String getCREDIT_FREEZE_PRICE() {
		return CREDIT_FREEZE_PRICE;
	}
	public void setCREDIT_FREEZE_PRICE(String cREDITFREEZEPRICE) {
		CREDIT_FREEZE_PRICE = cREDITFREEZEPRICE;
	}
	public String getROW_NO() {
		return ROW_NO;
	}
	public void setROW_NO(String rOWNO) {
		ROW_NO = rOWNO;
	}
	public String getADJUST_DECT_RATE() {
		return ADJUST_DECT_RATE;
	}
	public void setADJUST_DECT_RATE(String aDJUST_DECT_RATE) {
		ADJUST_DECT_RATE = aDJUST_DECT_RATE;
	}
	public String getADJUST_DECT_PRICE() {
		return ADJUST_DECT_PRICE;
	}
	public void setADJUST_DECT_PRICE(String aDJUST_DECT_PRICE) {
		ADJUST_DECT_PRICE = aDJUST_DECT_PRICE;
	}
	public String getHOLE_SPEC() {
		return HOLE_SPEC;
	}
	public void setHOLE_SPEC(String hOLE_SPEC) {
		HOLE_SPEC = hOLE_SPEC;
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
	public String getPRD_SIZE() {
		return PRD_SIZE;
	}
	public void setPRD_SIZE(String pRD_SIZE) {
		PRD_SIZE = pRD_SIZE;
	}

	public String getAttPath() {
		return attPath;
	}

	public void setAttPath(String attPath) {
		this.attPath = attPath;
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
	public String getPRD_PLACE() {
		return PRD_PLACE;
	}
	public void setPRD_PLACE(String pRD_PLACE) {
		PRD_PLACE = pRD_PLACE;
	}
	public String getIS_NO_REBATE_FLAG() {
		return IS_NO_REBATE_FLAG;
	}
	public void setIS_NO_REBATE_FLAG(String iS_NO_REBATE_FLAG) {
		IS_NO_REBATE_FLAG = iS_NO_REBATE_FLAG;
	}
	public String getIS_NO_LOCK_FLAG() {
		return IS_NO_LOCK_FLAG;
	}
	public void setIS_NO_LOCK_FLAG(String iS_NO_LOCK_FLAG) {
		IS_NO_LOCK_FLAG = iS_NO_LOCK_FLAG;
	}






}