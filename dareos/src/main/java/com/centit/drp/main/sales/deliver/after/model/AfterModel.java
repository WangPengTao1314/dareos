package com.centit.drp.main.sales.deliver.after.model;

import java.util.Date;
	/**
	 *    售后反馈实体类
	 * @author wang_pt
	 *
	 */
public class AfterModel {
	
	  String  problem_feedback_id ;// '问题反馈单ID'  
	  String  problem_type;// '问题类型'  
	  String  problem_feedback_no ;// '问题反馈单编号'  
	  String  sale_order_id ;// '销售订单ID'  
	  String  sale_order_no ;// '销售订单NO'  
	  String  cust_name ;// '客户姓名'  
	  String  chann_no ;// '经销商编号'  
	  String  chann_id ;// '经销商ID'  
	  String  chann_name ;// '经销商名称'  
	  Date    delivery_time ;// '交货时间'  
	  String  person_con ;// '收货联系人'  
	  String  recv_addr ;// '收货地址'  
	  String  tel ;// '收货电话'  
	  String  problem_sketch ;// '问题简述'  
	  String  problem_detailed ;// '问题描述'  
	  String  dispose_type ;// '处理结果'  
	  String  blame ;// '责任所属'  
	  String  amendment_id ;// '改补单ID'  
	  String  amendment_no ;// '改补单编号'  
	  String  factory_no ;// '厂编'  
	  Double  supply_amount ;// '收费'  
	  Integer del_flag ;// '0=正常,1=删除'  
	  String  dept_name ;// '制单部门名称'  
	  String  dept_id ;// '制单部门ID'  
	  String  org_id ;// '制单机构ID'  
	  String  org_name ;// '制单机构名称'  
	  String  reason_analysis ;// '原因分析'  
	  String  creator ;// '制单人ID'  
	  String  cre_name ;// '制单人名称'  
	  Date  cre_time ;// '制单时间'  
	  String  updator ;// '更新人ID'  
	  String  upd_name ;// '更新人名称'  
	  Date  upd_time ;// '更新时间'  
	  String  uploadExcel;
	  String order_org;//
	  String problem_class;//
	  String state;
	  String order_trace_id;//流程跟踪id
	  String  return_reason;//退回原因
	  
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOrder_org() {
		return order_org;
	}
	public void setOrder_org(String order_org) {
		this.order_org = order_org;
	}
	public String getProblem_class() {
		return problem_class;
	}
	public void setProblem_class(String problem_class) {
		this.problem_class = problem_class;
	}
	public String getUploadExcel() {
		return uploadExcel;
	}
	public void setUploadExcel(String uploadExcel) {
		this.uploadExcel = uploadExcel;
	}
	public String getProblem_feedback_id() {
		return problem_feedback_id;
	}
	public void setProblem_feedback_id(String problem_feedback_id) {
		this.problem_feedback_id = problem_feedback_id;
	}
	public String getProblem_type() {
		return problem_type;
	}
	public void setProblem_type(String problem_type) {
		this.problem_type = problem_type;
	}
	public String getProblem_feedback_no() {
		return problem_feedback_no;
	}
	public void setProblem_feedback_no(String problem_feedback_no) {
		this.problem_feedback_no = problem_feedback_no;
	}
	public String getSale_order_id() {
		return sale_order_id;
	}
	public void setSale_order_id(String sale_order_id) {
		this.sale_order_id = sale_order_id;
	}
	public String getSale_order_no() {
		return sale_order_no;
	}
	public void setSale_order_no(String sale_order_no) {
		this.sale_order_no = sale_order_no;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getChann_no() {
		return chann_no;
	}
	public void setChann_no(String chann_no) {
		this.chann_no = chann_no;
	}
	public String getChann_id() {
		return chann_id;
	}
	public void setChann_id(String chann_id) {
		this.chann_id = chann_id;
	}
	public String getChann_name() {
		return chann_name;
	}
	public void setChann_name(String chann_name) {
		this.chann_name = chann_name;
	}
	public Date getDelivery_time() {
		return delivery_time;
	}
	public void setDelivery_time(Date delivery_time) {
		this.delivery_time = delivery_time;
	}
	public String getPerson_con() {
		return person_con;
	}
	public void setPerson_con(String person_con) {
		this.person_con = person_con;
	}
	public String getRecv_addr() {
		return recv_addr;
	}
	public void setRecv_addr(String recv_addr) {
		this.recv_addr = recv_addr;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getProblem_sketch() {
		return problem_sketch;
	}
	public void setProblem_sketch(String problem_sketch) {
		this.problem_sketch = problem_sketch;
	}
	public String getProblem_detailed() {
		return problem_detailed;
	}
	public void setProblem_detailed(String problem_detailed) {
		this.problem_detailed = problem_detailed;
	}
	public String getDispose_type() {
		return dispose_type;
	}
	public void setDispose_type(String dispose_type) {
		this.dispose_type = dispose_type;
	}
	public String getBlame() {
		return blame;
	}
	public void setBlame(String blame) {
		this.blame = blame;
	}
	public String getAmendment_id() {
		return amendment_id;
	}
	public void setAmendment_id(String amendment_id) {
		this.amendment_id = amendment_id;
	}
	public String getAmendment_no() {
		return amendment_no;
	}
	public void setAmendment_no(String amendment_no) {
		this.amendment_no = amendment_no;
	}
	public String getFactory_no() {
		return factory_no;
	}
	public void setFactory_no(String factory_no) {
		this.factory_no = factory_no;
	}
	public Double getSupply_amount() {
		return supply_amount;
	}
	public void setSupply_amount(Double supply_amount) {
		this.supply_amount = supply_amount;
	}
	public Integer getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(Integer del_flag) {
		this.del_flag = del_flag;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	 
	public String getReason_analysis() {
		return reason_analysis;
	}
	public void setReason_analysis(String reason_analysis) {
		this.reason_analysis = reason_analysis;
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
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
	}
	public String getUpd_name() {
		return upd_name;
	}
	public void setUpd_name(String upd_name) {
		this.upd_name = upd_name;
	}
	public Date getUpd_time() {
		return upd_time;
	}
	public void setUpd_time(Date upd_time) {
		this.upd_time = upd_time;
	}
	public String getOrder_trace_id() {
		return order_trace_id;
	}
	public void setOrder_trace_id(String order_trace_id) {
		this.order_trace_id = order_trace_id;
	}
	public String getReturn_reason() {
		return return_reason;
	}
	public void setReturn_reason(String return_reason) {
		this.return_reason = return_reason;
	}
	
	
	  

}
