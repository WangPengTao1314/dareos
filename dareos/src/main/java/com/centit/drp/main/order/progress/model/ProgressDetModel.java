package com.centit.drp.main.order.progress.model;

public class ProgressDetModel {

	String order_degree_dtl_id;// 订单明细ID
	String order_degree_id;// 订单ID
	String prd_id;// 货品ID
	String prd_no;// 货品编号
	String prd_name;// 货品名称
	String prd_spec;// 规格型号
	String hole_spec;// 门洞尺寸
	String prd_toward;// 推向
	String prd_quality;// 材质
	String prd_glass;// 玻璃
	String prd_other;// 其它
	String prd_series;// 系列
	Integer group_no;// 组号
	String std_unit;// 标准单位
	Integer order_num;// 订货数量
	Integer pro_num;// 生产数量
	Integer send_num;// 发货数量
	Integer del_flag;// 删除标记
	
	/***********新加字段start*********/
	String order_amount;//金额
	String prd_color;//颜色
	String brand;//品牌
	String price;//含税单价
	/***********新加字段end*********/
	
	public String getOrder_degree_dtl_id() {
		return order_degree_dtl_id;
	}
	public void setOrder_degree_dtl_id(String order_degree_dtl_id) {
		this.order_degree_dtl_id = order_degree_dtl_id;
	}
	public String getOrder_degree_id() {
		return order_degree_id;
	}
	public void setOrder_degree_id(String order_degree_id) {
		this.order_degree_id = order_degree_id;
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
	public Integer getGroup_no() {
		return group_no;
	}
	public void setGroup_no(Integer group_no) {
		this.group_no = group_no;
	}
	public String getStd_unit() {
		return std_unit;
	}
	public void setStd_unit(String std_unit) {
		this.std_unit = std_unit;
	}
	public Integer getOrder_num() {
		return order_num;
	}
	public void setOrder_num(Integer order_num) {
		this.order_num = order_num;
	}
	public Integer getPro_num() {
		return pro_num;
	}
	public void setPro_num(Integer pro_num) {
		this.pro_num = pro_num;
	}
	public Integer getSend_num() {
		return send_num;
	}
	public void setSend_num(Integer send_num) {
		this.send_num = send_num;
	}
	public Integer getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(Integer del_flag) {
		this.del_flag = del_flag;
	}
	public String getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
}
