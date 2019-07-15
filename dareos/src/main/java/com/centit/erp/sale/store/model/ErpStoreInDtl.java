package com.centit.erp.sale.store.model;

import com.centit.commons.model.BaseModel;

public class ErpStoreInDtl extends BaseModel {
	private static final long serialVersionUID = 1L;

	private String store_in_dtl_id; // 入库单明细ID
	
	private String store_in_id; // 入库单ID
	
	private String store_in_no; // 入库单NO
	
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
	
	private String pkg_num; // 包数
	
	private String in_num; // 入库数量
	
	/** 0=正常,1=删除 **/
	private String del_flag;

	public String getStore_in_dtl_id() {
		return store_in_dtl_id;
	}

	public void setStore_in_dtl_id(String store_in_dtl_id) {
		this.store_in_dtl_id = store_in_dtl_id;
	}

	public String getStore_in_id() {
		return store_in_id;
	}

	public void setStore_in_id(String store_in_id) {
		this.store_in_id = store_in_id;
	}

	public String getStore_in_no() {
		return store_in_no;
	}

	public void setStore_in_no(String store_in_no) {
		this.store_in_no = store_in_no;
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

	public String getPkg_num() {
		return pkg_num;
	}

	public void setPkg_num(String pkg_num) {
		this.pkg_num = pkg_num;
	}

	public String getIn_num() {
		return in_num;
	}

	public void setIn_num(String in_num) {
		this.in_num = in_num;
	}

	public String getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}

}
