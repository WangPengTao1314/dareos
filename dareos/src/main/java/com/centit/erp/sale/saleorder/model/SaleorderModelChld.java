/**
 * prjName:喜临门营销平台
 * ucName:标准订单
 * fileName:SaleorderModelChld
*/
package com.centit.erp.sale.saleorder.model;
import com.centit.commons.model.BaseModel;
/**
 *
 * @ClassName: SaleorderModelChld
 * @Description: 销售订单明细实体
 * @author wang_dw
 * @date 2019年3月14日 下午2:02:39
 *
 */
public class SaleorderModelChld extends BaseModel {
	private static final long serialVersionUID = 7645035784513016218L;
	/** 销售订单明细ID **/
	private String SALE_ORDER_DTL_ID;
	/** 销售订单ID **/
	private String SALE_ORDER_ID;
	/** 组号 **/
	private String GROUP_NO;
	/** 产品ID **/
	private String PRD_ID;
	/** 产品编号 **/
	private String PRD_NO;
	/** 产品名称 **/
	private String PRD_NAME;
	/** 门洞尺寸 **/
	private String HOLE_SPEC;
	/** 规格型号 **/
	private String PRD_SPEC;
	/** 推向 **/
	private String PRD_TOWARD;
	/** 材质 **/
	private String PRD_QUALITY;
	/** 颜色 **/
	private String PRD_COLOR;
	/** 玻璃 **/
	private String PRD_GLASS;
	/** 其他 **/
	private String PRD_OTHER;
	/** 系列 **/
	private String PRD_SERIES;

	/** 品牌 **/
	private String BRAND;
	/** 标准单位 **/
	private String STD_UNIT;
	/** 是否非标 */
	private String IS_NO_STAND_FLAG;
	/** 单价 **/
	private String PRICE;
	/** 折扣率 **/
	private String DECT_RATE;
	/** 折后单价 **/
	private String DECT_PRICE;
	/** 返利单价 **/
	private String REBATE_PRICE;
	/** 返利金额 **/
	private String REBATE_AMOUNT;
	/** 订货数量 **/
	private String ORDER_NUM;
	/** 订货金额 **/
	private String ORDER_AMOUNT;
	/** 预计发货日期 **/
	private String ADVC_SEND_DATE;
	/** 备注 **/
	private String REMARK;
	/** 删除标记 **/
	private String DEL_FLAG;
	/** 来源单据明细ID **/
	private String FROM_BILL_DTL_ID;
	/** 已实际发货数量 **/
	private String SENDED_NUM;
	/** 行号 **/
	private String ROW_NO;
//	/** 是否是备货 **/
//	private String IS_BACKUP_FLAG;
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
	/** 附图号 **/
	private String FIGURE_NO;

	/** 附件路径 */
	private String attPath;

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

	public String getHOLE_SPEC() {
		return HOLE_SPEC;
	}

	public void setHOLE_SPEC(String hOLE_SPEC) {
		HOLE_SPEC = hOLE_SPEC;
	}

	public String getPRD_NO() {
		return PRD_NO;
	}

	public void setPRD_NO(String pRD_NO) {
		PRD_NO = pRD_NO;
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

	public String getPRD_NAME() {
		return PRD_NAME;
	}

	public void setPRD_NAME(String pRD_NAME) {
		PRD_NAME = pRD_NAME;
	}

	public String getPRD_SPEC() {
		return PRD_SPEC;
	}

	public void setPRD_SPEC(String pRD_SPEC) {
		PRD_SPEC = pRD_SPEC;
	}

	public String getPRD_COLOR() {
		return PRD_COLOR;
	}

	public void setPRD_COLOR(String pRD_COLOR) {
		PRD_COLOR = pRD_COLOR;
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

	public String getIS_NO_STAND_FLAG() {
		return IS_NO_STAND_FLAG;
	}

	public void setIS_NO_STAND_FLAG(String iS_NO_STAND_FLAG) {
		IS_NO_STAND_FLAG = iS_NO_STAND_FLAG;
	}

	public String getPRICE() {
		return PRICE;
	}

	public void setPRICE(String pRICE) {
		PRICE = pRICE;
	}

	public String getDECT_RATE() {
		return DECT_RATE;
	}

	public void setDECT_RATE(String dECT_RATE) {
		DECT_RATE = dECT_RATE;
	}

	public String getORDER_AMOUNT() {
		return ORDER_AMOUNT;
	}

	public void setORDER_AMOUNT(String oRDER_AMOUNT) {
		ORDER_AMOUNT = oRDER_AMOUNT;
	}

	public String getDECT_PRICE() {
		return DECT_PRICE;
	}

	public void setDECT_PRICE(String dECT_PRICE) {
		DECT_PRICE = dECT_PRICE;
	}

	public String getORDER_NUM() {
		return ORDER_NUM;
	}

	public void setORDER_NUM(String oRDER_NUM) {
		ORDER_NUM = oRDER_NUM;
	}

	public String getADVC_SEND_DATE() {
		return ADVC_SEND_DATE;
	}

	public void setADVC_SEND_DATE(String aDVC_SEND_DATE) {
		ADVC_SEND_DATE = aDVC_SEND_DATE;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	public String getDEL_FLAG() {
		return DEL_FLAG;
	}

	public void setDEL_FLAG(String dEL_FLAG) {
		DEL_FLAG = dEL_FLAG;
	}

	public String getFROM_BILL_DTL_ID() {
		return FROM_BILL_DTL_ID;
	}

	public void setFROM_BILL_DTL_ID(String fROM_BILL_DTL_ID) {
		FROM_BILL_DTL_ID = fROM_BILL_DTL_ID;
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

	public String getROW_NO() {
		return ROW_NO;
	}

	public void setROW_NO(String rOW_NO) {
		ROW_NO = rOW_NO;
	}

	public String getAttPath() {
		return attPath;
	}

	public void setAttPath(String attPath) {
		this.attPath = attPath;
	}

	public String getPRD_SIZE() {
		return PRD_SIZE;
	}

	public void setPRD_SIZE(String pRD_SIZE) {
		PRD_SIZE = pRD_SIZE;
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
	public String getFIGURE_NO() {
		return FIGURE_NO;
	}
	public void setFIGURE_NO(String fIGURE_NO) {
		FIGURE_NO = fIGURE_NO;
	}
}