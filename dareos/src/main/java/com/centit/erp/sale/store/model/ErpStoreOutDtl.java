package com.centit.erp.sale.store.model;

import com.centit.commons.model.BaseModel;

public class ErpStoreOutDtl extends BaseModel {
	private static final long serialVersionUID = 1L;

	private String store_out_dtl_id; // 出库单明细ID
	
	private String store_out_id; // 出库单ID
	
	private String store_out_no; // 出库单NO
	
	private String prd_id; // 货品ID
	
	private String prd_no; // 货品编号
	
	private String prd_name; // 货品名称
	
	private String prd_spec; // 规格型号
	
	private String hole_spec; // 门洞尺寸
	
	private String prd_toward; // 推向
	
	private String prd_quality; //材质
	
	private String prd_glass; // 玻璃
	
	private String prd_other; // 其它
	
	private String prd_series; // 系列
	
	private String group_no; // 组号
	
	private String std_unit; // 标准单位
	
	private String prd_color; // 颜色
	
	private String brand; // 品牌
	
	private String prd_place; // 工程位置
	
	private String is_no_lock_flag; // 是否开锁孔
	
	private String out_num; // 出库数量
	
	private String send_order_dtl_id; // 发货单明细id
	
	private String dect_price; // 折扣单价
	
	/** 0=正常,1=删除 **/
	private String del_flag;
	
	public String getPrd_place() {
		return prd_place;
	}

	public void setPrd_place(String prd_place) {
		this.prd_place = prd_place;
	}

	public String getIs_no_lock_flag() {
		return is_no_lock_flag;
	}

	public void setIs_no_lock_flag(String is_no_lock_flag) {
		this.is_no_lock_flag = is_no_lock_flag;
	}

	public String getDect_price() {
		return dect_price;
	}

	public void setDect_price(String dect_price) {
		this.dect_price = dect_price;
	}

	public String getSend_order_dtl_id() {
		return send_order_dtl_id;
	}

	public void setSend_order_dtl_id(String send_order_dtl_id) {
		this.send_order_dtl_id = send_order_dtl_id;
	}

	public String getStore_out_dtl_id() {
		return store_out_dtl_id;
	}

	public void setStore_out_dtl_id(String store_out_dtl_id) {
		this.store_out_dtl_id = store_out_dtl_id;
	}

	public String getStore_out_id() {
		return store_out_id;
	}

	public void setStore_out_id(String store_out_id) {
		this.store_out_id = store_out_id;
	}

	public String getStore_out_no() {
		return store_out_no;
	}

	public void setStore_out_no(String store_out_no) {
		this.store_out_no = store_out_no;
	}

	public String getPrd_id() {
		return prd_id;
	}

	public void setPrd_id(String prd_id) {
		this.prd_id = prd_id;
	}

	public String getPrd_no() {
		return prd_no;
	}

	public void setPrd_no(String prd_no) {
		this.prd_no = prd_no;
	}

	public String getPrd_name() {
		return prd_name;
	}

	public void setPrd_name(String prd_name) {
		this.prd_name = prd_name;
	}

	public String getPrd_spec() {
		return prd_spec;
	}

	public void setPrd_spec(String prd_spec) {
		this.prd_spec = prd_spec;
	}

	public String getHole_spec() {
		return hole_spec;
	}

	public void setHole_spec(String hole_spec) {
		this.hole_spec = hole_spec;
	}

	public String getPrd_toward() {
		return prd_toward;
	}

	public void setPrd_toward(String prd_toward) {
		this.prd_toward = prd_toward;
	}

	public String getPrd_quality() {
		return prd_quality;
	}

	public void setPrd_quality(String prd_quality) {
		this.prd_quality = prd_quality;
	}

	public String getPrd_glass() {
		return prd_glass;
	}

	public void setPrd_glass(String prd_glass) {
		this.prd_glass = prd_glass;
	}

	public String getPrd_other() {
		return prd_other;
	}

	public void setPrd_other(String prd_other) {
		this.prd_other = prd_other;
	}

	public String getPrd_series() {
		return prd_series;
	}

	public void setPrd_series(String prd_series) {
		this.prd_series = prd_series;
	}

	public String getGroup_no() {
		return group_no;
	}

	public void setGroup_no(String group_no) {
		this.group_no = group_no;
	}

	public String getStd_unit() {
		return std_unit;
	}

	public void setStd_unit(String std_unit) {
		this.std_unit = std_unit;
	}

	public String getPrd_color() {
		return prd_color;
	}

	public void setPrd_color(String prd_color) {
		this.prd_color = prd_color;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getOut_num() {
		return out_num;
	}

	public void setOut_num(String out_num) {
		this.out_num = out_num;
	}

	public String getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}

}
