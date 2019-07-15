package com.centit.drp.main.sales.deliver.order.model;

import java.util.Date;
/**
    *    发货指令实体类
 * @author wang_pt
 *
 */
public class OrderModel {

	String sale_order_id;//销售订单id
	String sale_order_no;//销售订单编号
	String bill_type;//订单类型
	String from_bill_id;//订单来源id
	String from_bill_no;//来源编号
	String bill_type2;//来源订单类型
	String order_chann_id;//要货组织ID
	String order_chann_no;//要货组织编号
	String order_chann_name;//要货组织名称
	Date order_date;//要货日期
	String chann_id;//经销商id
	String chann_no;//经销商编号
	String chann_name;//经销商名称
	String print_name;//打印名称
	String transport;//运输方式
	String transport_settle;//运输结算方式
	String factory_no;//厂编
	String proess_type;//处理类型（定制、现货、OEM）
	String sale_id;//业务员id
	String sale_name;//业务员名称
	String saledept_name;//业务部门名称
	String saledept_id;//业务部门id
	Date pre_recv_date;//预计收货日期
	String cust_name;//发货方名称
	String is_use_rebate;
	String recv_chann_id;//收货组织ID
	String recv_chann_no;//收货组织编号
	String recv_chann_name;//收货组织名称
	String person_con;//收货联系人
	String recv_addr;//收货地址
	String recv_addr_no;//收货编号
	String tel;//收货电话
	String state;//状态
	String remark;//备注1
	String remark2;//备注2
	String creator;//
	String cre_name;
	String cre_time;
	String updator;
	String upd_name;
	String upd_time;
	
	String ledger_name;//
	String ledger_id;//
	String del_flag;//删除标识
	String dept_name;//部门名称
	String dept_id;//部门id
	String org_id;//组织id
	String org_name;//组织名称
	String area_id;//区域id
	String area_no;//区域编号
	String area_name;//区域名称
	//Integer base_add_flag;//
	Date order_delivery_date;//预计发货时间
	String cust_tel;//客户联系方式
	String cust_addr;//客户地址
	String rebate_rate;//收款比例
	String order_amount;//订单金额
	String uploadExcel;//上传附件
	//Integer order_num;//发货数量
	//String sale_order_dtl_id;//发货指令明细表id
	String send_order_id;//发货指令id
	String send_order_no;//发货指令编号
	String total_amount;//订货总金额
	String total_rebate;//订货总返利       
	String  ATT_PATH;//附件路径
	
	String  problem_feedback_id;//问题反馈单ID 
	String  problem_feedback_no; //问题反馈单NO
	/**
	 * NC接口返回的id,当单据需要变更，时传入该值
	 */
	String nc_return_id;
	
	String check_advice;//审核意见
	
	  
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
	public String getIs_use_rebate() {
		return is_use_rebate;
	}
	public void setIs_use_rebate(String is_use_rebate) {
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
	public String getCre_time() {
		return cre_time;
	}
	public void setCre_time(String cre_time) {
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
	public String getUpd_time() {
		return upd_time;
	}
	public void setUpd_time(String upd_time) {
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
	public String getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(String del_flag) {
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
	
	public String getUploadExcel() {
		return uploadExcel;
	}
	public void setUploadExcel(String uploadExcel) {
		this.uploadExcel = uploadExcel;
	}
	
	public String getSend_order_id() {
		return send_order_id;
	}
	public void setSend_order_id(String send_order_id) {
		this.send_order_id = send_order_id;
	}
	public String getSend_order_no() {
		return send_order_no;
	}
	public void setSend_order_no(String send_order_no) {
		this.send_order_no = send_order_no;
	}
	public String getNc_return_id() {
		return nc_return_id;
	}
	public void setNc_return_id(String nc_return_id) {
		this.nc_return_id = nc_return_id;
	}
	public String getATT_PATH() {
		return ATT_PATH;
	}
	public void setATT_PATH(String aTT_PATH) {
		ATT_PATH = aTT_PATH;
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
	public String getRebate_rate() {
		return rebate_rate;
	}
	public void setRebate_rate(String rebate_rate) {
		this.rebate_rate = rebate_rate;
	}
	public String getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
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
	public String getCheck_advice() {
		return check_advice;
	}
	public void setCheck_advice(String check_advice) {
		this.check_advice = check_advice;
	}
	
	
	
	

}
