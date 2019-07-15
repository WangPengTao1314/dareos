package com.centit.erp.sale.store.model;

import java.util.Date;
import java.util.List;

import com.centit.commons.model.BaseModel;

public class ErpStoreOut extends BaseModel {
	private static final long serialVersionUID = 1L;

	private String store_out_id;// 出库单ID
	
	private String store_out_no;// 出库单NO
	
	private String sale_order_no; // 销售订单编号
	
	private String out_date; // 出库日期
	
	private String logistic_no; // 物流单号
	
	private String state; // 状态
	
	private String remark; // 备注
	
	private String creator; // 制单人ID
	
	private String cre_name; // 制单人名称
	
	private Date cre_time; // 制单时间
	
	/** 0=正常,1=删除 **/
	private String del_flag; // 删除标记
	
	private String ledger_id; //帐套编号
	
	private Date upd_time;// 同步时间
	
	private String send_order_id; // 发货单id（存的是nc返回id）
	
	private String total_amount; // 出库总金额
	
	private String total_rebate; // 返利总金额
	
	List<ErpStoreOutDtl> detailList;
	
	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getTotal_rebate() {
		return total_rebate;
	}

	public void setTotal_rebate(String total_rebate) {
		this.total_rebate = total_rebate;
	}

	public String getSend_order_id() {
		return send_order_id;
	}

	public void setSend_order_id(String send_order_id) {
		this.send_order_id = send_order_id;
	}

	public List<ErpStoreOutDtl> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ErpStoreOutDtl> detailList) {
		this.detailList = detailList;
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

	public String getSale_order_no() {
		return sale_order_no;
	}

	public void setSale_order_no(String sale_order_no) {
		this.sale_order_no = sale_order_no;
	}

	public String getOut_date() {
		return out_date;
	}

	public void setOut_date(String out_date) {
		this.out_date = out_date;
	}

	public String getLogistic_no() {
		return logistic_no;
	}

	public void setLogistic_no(String logistic_no) {
		this.logistic_no = logistic_no;
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
