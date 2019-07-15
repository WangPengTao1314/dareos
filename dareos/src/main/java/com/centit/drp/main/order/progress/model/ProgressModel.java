package com.centit.drp.main.order.progress.model;

import java.util.Date;
import java.util.List;

public class ProgressModel {

	String order_degree_id;// 订单ID  
	String order_degree_no;// 订单NO  
	String order_from;// 订单来源  
	String delivery_date;// 交货日期  
	String order_num;// 订单数量  
	Integer complete_num;// 完成数量  
	Integer send_num;// 发货数量  
	String confirm_peo;// 确认人  
	Date confirm_date;// 确认时间  
	String state;// 状态  
	String remark;// 备注  
	String creator;// 制单人ID  
	String cre_name;// 制单人名称  
	Date cre_time;// 制单时间  
	Integer del_flag;// 删除标记  
	String uploadExcel;//附件
	String estimate_delivery_date;//
	
	/***********新加字段start*********/
	String order_degree_type;//采购订单类型
	String sale_order_no;//销售订单编号
	String pur_dep;//采购部门
	String pur_name;//采购员
	String ledger_id;//采购组织
	String order_date;//订单日期
	
	String prvd_no;//供应商编号   
	String prvd_name;//供应商名称  
	String total_money;//总金额  
	
	/***********新加字段end*********/
	
	List<ProgressDetModel> detailList;//订单确认明细表
	
	
	/***********新加字段end*********/
	public String getOrder_degree_id() {
		return order_degree_id;
	}
	public void setOrder_degree_id(String order_degree_id) {
		this.order_degree_id = order_degree_id;
	}
	public String getOrder_degree_no() {
		return order_degree_no;
	}
	public void setOrder_degree_no(String order_degree_no) {
		this.order_degree_no = order_degree_no;
	}
	public String getOrder_from() {
		return order_from;
	}
	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}
	public String getDelivery_date() {
		return delivery_date;
	}
	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	public Integer getComplete_num() {
		return complete_num;
	}
	public void setComplete_num(Integer complete_num) {
		this.complete_num = complete_num;
	}
	public Integer getSend_num() {
		return send_num;
	}
	public void setSend_num(Integer send_num) {
		this.send_num = send_num;
	}
	public String getConfirm_peo() {
		return confirm_peo;
	}
	public void setConfirm_peo(String confirm_peo) {
		this.confirm_peo = confirm_peo;
	}
	public Date getConfirm_date() {
		return confirm_date;
	}
	public void setConfirm_date(Date confirm_date) {
		this.confirm_date = confirm_date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCre_name() {
		return cre_name;
	}
	public void setCre_name(String cre_name) {
		this.cre_name = cre_name;
	}
	public Date getCre_time() {
		return cre_time;
	}
	public void setCre_time(Date cre_time) {
		this.cre_time = cre_time;
	}
	public Integer getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(Integer del_flag) {
		this.del_flag = del_flag;
	}
	public String getUploadExcel() {
		return uploadExcel;
	}
	public void setUploadExcel(String uploadExcel) {
		this.uploadExcel = uploadExcel;
	}
	public String getEstimate_delivery_date() {
		return estimate_delivery_date;
	}
	public void setEstimate_delivery_date(String estimate_delivery_date) {
		this.estimate_delivery_date = estimate_delivery_date;
	}
	public String getOrder_degree_type() {
		return order_degree_type;
	}
	public void setOrder_degree_type(String order_degree_type) {
		this.order_degree_type = order_degree_type;
	}
	public String getSale_order_no() {
		return sale_order_no;
	}
	public void setSale_order_no(String sale_order_no) {
		this.sale_order_no = sale_order_no;
	}
	public String getPur_dep() {
		return pur_dep;
	}
	public void setPur_dep(String pur_dep) {
		this.pur_dep = pur_dep;
	}
	public String getPur_name() {
		return pur_name;
	}
	public void setPur_name(String pur_name) {
		this.pur_name = pur_name;
	}
	public String getLedger_id() {
		return ledger_id;
	}
	public void setLedger_id(String ledger_id) {
		this.ledger_id = ledger_id;
	}
	public String getPrvd_no() {
		return prvd_no;
	}
	public void setPrvd_no(String prvd_no) {
		this.prvd_no = prvd_no;
	}
	public String getPrvd_name() {
		return prvd_name;
	}
	public void setPrvd_name(String prvd_name) {
		this.prvd_name = prvd_name;
	}
	public String getTotal_money() {
		return total_money;
	}
	public void setTotal_money(String total_money) {
		this.total_money = total_money;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public List<ProgressDetModel> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<ProgressDetModel> detailList) {
		this.detailList = detailList;
	}
	
}
