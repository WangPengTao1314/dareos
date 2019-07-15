package com.centit.drp.main.sales.deliver.rework.model;

import java.util.Date;
/**
 * 返修单维护实体类
 * @author wang_pt
 *
 */
public class ReworkModel {
	
	String  sale_order_id;//销售订单ID 
    String  sale_order_no;//销售订单编号 
    String  bill_type;//单据类型 
    String  from_bill_id;//来源单据ID 
    String  from_bill_no;//来源单据NO 
    String  bill_type2;//来源单据类型 
    String  order_chann_id;//要货组织ID 
    String  order_chann_no;//要货组织编号 
    String  order_chann_name;//要货组织名称 
    Date  order_date;//下单日期 
    String  chann_id;//经销商ID 
    String  chann_no;//经销商编号 
    String  chann_name;//经销商名称 
    String  print_name;//标签打印名称 
    String  transport;//运输方式 
    String  transport_settle;//运输结算方式 
    String  factory_no;//厂编 
    String  proess_type;//处理类型（定制、现货、OEM） 
    String  sale_id;//业务员ID 
    String  sale_name;//业务员名称 
    String  saledept_name;//业务员部门名称 
    String  saledept_id;//业务员部门ID 
    Date  pre_recv_date;//预计交货日期 
    String  cust_name;//发货方名称 
    Integer  is_use_rebate;//0=否,1=是 
    String  recv_chann_id;//收货组织ID 
    String  recv_chann_no;//收货组织编号 
    String  recv_chann_name;//收货组织名称 
    String  person_con;//收货联系人 
    String  recv_addr;//收货地址 
    String  recv_addr_no;//收货地址编号 
    String  tel;//收货电话 
    String  state;//状态 
    String  remark;//经销商备注 
    String  remark2;//订单员备注 
    String  creator;//制单人ID 
    String  cre_name;//制单人名称 
    Date  cre_time;//制单时间 
    String  updator;//更新人ID 
    String  upd_name;//更新人名称 
    Date  upd_time;//更新时间 
    String  ledger_name;//帐套名称 
    String  ledger_id;//帐套ID 
    Integer  del_flag;//0=正常,1=删除 
    Date  audit_time;//审核时间 
    String  audit_name;//审核人姓名 
    String  audit_id;//审核人ID 
    String  dept_name;//制单部门名称 
    String  dept_id;//制单部门ID 
    String  org_id;//制单机构ID 
    String  org_name;//制单机构名称 
    String  area_id;//区域ID 
    String  area_no;//区域编号 
    String  area_name;//区域名称 
    Integer  base_add_flag;//0=否,1=是 
    Date  order_delivery_date;//订单交期 
    String  cust_tel;//客户电话 
    String  cust_addr;//客户住址 
    Double rebate_rate;
    Double  order_amount;//订单金额 
    String  order_trace_id;//流程跟踪ID 
    String  return_show_flag;//退回高亮显示标记
    String  problem_feedback_id;//问题反馈单ID 
    String  problem_feedback_no; //问题反馈单NO
    String  ATT_PATH;//附件路径
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
	public String getBill_type() {
		return bill_type;
	}
	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}
	public String getFrom_bill_id() {
		return from_bill_id;
	}
	public void setFrom_bill_id(String from_bill_id) {
		this.from_bill_id = from_bill_id;
	}
	public String getFrom_bill_no() {
		return from_bill_no;
	}
	public void setFrom_bill_no(String from_bill_no) {
		this.from_bill_no = from_bill_no;
	}
	public String getBill_type2() {
		return bill_type2;
	}
	public void setBill_type2(String bill_type2) {
		this.bill_type2 = bill_type2;
	}
	public String getOrder_chann_id() {
		return order_chann_id;
	}
	public void setOrder_chann_id(String order_chann_id) {
		this.order_chann_id = order_chann_id;
	}
	public String getOrder_chann_no() {
		return order_chann_no;
	}
	public void setOrder_chann_no(String order_chann_no) {
		this.order_chann_no = order_chann_no;
	}
	public String getOrder_chann_name() {
		return order_chann_name;
	}
	public void setOrder_chann_name(String order_chann_name) {
		this.order_chann_name = order_chann_name;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public String getChann_id() {
		return chann_id;
	}
	public void setChann_id(String chann_id) {
		this.chann_id = chann_id;
	}
	public String getChann_no() {
		return chann_no;
	}
	public void setChann_no(String chann_no) {
		this.chann_no = chann_no;
	}
	public String getChann_name() {
		return chann_name;
	}
	public void setChann_name(String chann_name) {
		this.chann_name = chann_name;
	}
	public String getPrint_name() {
		return print_name;
	}
	public void setPrint_name(String print_name) {
		this.print_name = print_name;
	}
	public String getTransport() {
		return transport;
	}
	public void setTransport(String transport) {
		this.transport = transport;
	}
	public String getTransport_settle() {
		return transport_settle;
	}
	public void setTransport_settle(String transport_settle) {
		this.transport_settle = transport_settle;
	}
	public String getFactory_no() {
		return factory_no;
	}
	public void setFactory_no(String factory_no) {
		this.factory_no = factory_no;
	}
	public String getProess_type() {
		return proess_type;
	}
	public void setProess_type(String proess_type) {
		this.proess_type = proess_type;
	}
	public String getSale_id() {
		return sale_id;
	}
	public void setSale_id(String sale_id) {
		this.sale_id = sale_id;
	}
	public String getSale_name() {
		return sale_name;
	}
	public void setSale_name(String sale_name) {
		this.sale_name = sale_name;
	}
	public String getSaledept_name() {
		return saledept_name;
	}
	public void setSaledept_name(String saledept_name) {
		this.saledept_name = saledept_name;
	}
	public String getSaledept_id() {
		return saledept_id;
	}
	public void setSaledept_id(String saledept_id) {
		this.saledept_id = saledept_id;
	}
	public Date getPre_recv_date() {
		return pre_recv_date;
	}
	public void setPre_recv_date(Date pre_recv_date) {
		this.pre_recv_date = pre_recv_date;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public Integer getIs_use_rebate() {
		return is_use_rebate;
	}
	public void setIs_use_rebate(Integer is_use_rebate) {
		this.is_use_rebate = is_use_rebate;
	}
	public String getRecv_chann_id() {
		return recv_chann_id;
	}
	public void setRecv_chann_id(String recv_chann_id) {
		this.recv_chann_id = recv_chann_id;
	}
	public String getRecv_chann_no() {
		return recv_chann_no;
	}
	public void setRecv_chann_no(String recv_chann_no) {
		this.recv_chann_no = recv_chann_no;
	}
	public String getRecv_chann_name() {
		return recv_chann_name;
	}
	public void setRecv_chann_name(String recv_chann_name) {
		this.recv_chann_name = recv_chann_name;
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
	public String getRecv_addr_no() {
		return recv_addr_no;
	}
	public void setRecv_addr_no(String recv_addr_no) {
		this.recv_addr_no = recv_addr_no;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
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
	public String getLedger_name() {
		return ledger_name;
	}
	public void setLedger_name(String ledger_name) {
		this.ledger_name = ledger_name;
	}
	public String getLedger_id() {
		return ledger_id;
	}
	public void setLedger_id(String ledger_id) {
		this.ledger_id = ledger_id;
	}
	public Integer getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(Integer del_flag) {
		this.del_flag = del_flag;
	}
	public Date getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(Date audit_time) {
		this.audit_time = audit_time;
	}
	public String getAudit_name() {
		return audit_name;
	}
	public void setAudit_name(String audit_name) {
		this.audit_name = audit_name;
	}
	public String getAudit_id() {
		return audit_id;
	}
	public void setAudit_id(String audit_id) {
		this.audit_id = audit_id;
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
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public String getArea_no() {
		return area_no;
	}
	public void setArea_no(String area_no) {
		this.area_no = area_no;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public Integer getBase_add_flag() {
		return base_add_flag;
	}
	public void setBase_add_flag(Integer base_add_flag) {
		this.base_add_flag = base_add_flag;
	}
	public Date getOrder_delivery_date() {
		return order_delivery_date;
	}
	public void setOrder_delivery_date(Date order_delivery_date) {
		this.order_delivery_date = order_delivery_date;
	}
	public String getCust_tel() {
		return cust_tel;
	}
	public void setCust_tel(String cust_tel) {
		this.cust_tel = cust_tel;
	}
	public String getCust_addr() {
		return cust_addr;
	}
	public void setCust_addr(String cust_addr) {
		this.cust_addr = cust_addr;
	}
	public Double getRebate_rate() {
		return rebate_rate;
	}
	public void setRebate_rate(Double rebate_rate) {
		this.rebate_rate = rebate_rate;
	}
	public Double getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(Double order_amount) {
		this.order_amount = order_amount;
	}
	public String getOrder_trace_id() {
		return order_trace_id;
	}
	public void setOrder_trace_id(String order_trace_id) {
		this.order_trace_id = order_trace_id;
	}
	public String getReturn_show_flag() {
		return return_show_flag;
	}
	public void setReturn_show_flag(String return_show_flag) {
		this.return_show_flag = return_show_flag;
	}
	public String getProblem_feedback_id() {
		return problem_feedback_id;
	}
	public void setProblem_feedback_id(String problem_feedback_id) {
		this.problem_feedback_id = problem_feedback_id;
	}
	public String getProblem_feedback_no() {
		return problem_feedback_no;
	}
	public void setProblem_feedback_no(String problem_feedback_no) {
		this.problem_feedback_no = problem_feedback_no;
	}
	public String getATT_PATH() {
		return ATT_PATH;
	}
	public void setATT_PATH(String aTT_PATH) {
		ATT_PATH = aTT_PATH;
	}
    
    
   
}
