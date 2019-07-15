package com.centit.erp.sale.store.model;

import java.util.Date;
import java.util.List;

import com.centit.commons.model.BaseModel;

public class ErpStoreIn extends BaseModel {
	private static final long serialVersionUID = 1L;

	private String store_in_id;// 入库单ID
	
	private String store_in_no;// 入库单NO
	
	private String sale_order_no; // 销售订单编号
	
	private String in_date; // 入库日期
	
	private String state; // 状态
	
	private String remark; // 备注
	
	private String creator; // 制单人ID
	
	private String cre_name; // 制单人名称
	
	private Date cre_time; // 制单时间
	
	/** 0=正常,1=删除 **/
	private String del_flag; // 删除标记
	
	private String ledger_id; //帐套编号
	
	private Date upd_time;// 同步时间
	
	private List<ErpStoreInDtl> detailList;

	public List<ErpStoreInDtl> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ErpStoreInDtl> detailList) {
		this.detailList = detailList;
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

	public String getSale_order_no() {
		return sale_order_no;
	}

	public void setSale_order_no(String sale_order_no) {
		this.sale_order_no = sale_order_no;
	}

	public String getIn_date() {
		return in_date;
	}

	public void setIn_date(String in_date) {
		this.in_date = in_date;
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

	public String getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}

	public String getLedger_id() {
		return ledger_id;
	}

	public void setLedger_id(String ledger_id) {
		this.ledger_id = ledger_id;
	}

	public Date getUpd_time() {
		return upd_time;
	}

	public void setUpd_time(Date upd_time) {
		this.upd_time = upd_time;
	}
	
}
