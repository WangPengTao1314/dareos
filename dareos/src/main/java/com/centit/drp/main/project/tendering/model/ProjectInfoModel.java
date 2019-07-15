package com.centit.drp.main.project.tendering.model;

import java.util.Date;

/**
 * 工程项目信息_招投标实体类
 * 
 * @author wang_pt
 *
 */
public class ProjectInfoModel {

	String tendering_id;//招投标id
	String tendering_no;//招投标编号
	String project_no;//项目编号
	String project_name;//项目名称
	String project_type;//项目类型
	String tendering_unit;//招投标单位
	Date open_time;//开工时间
	Double budget_amount;//
	String project_address;//工程地址
	Integer construc_period;//
	String procure_content;//
	String remark;//备注
	String creator;//
	String cre_name;//
	Date cre_time;//
	String updator;//
	String upd_name;//
	Date upd_time;//
	String dept_id;//部门id
	String dept_name;//部门名称
	Integer del_flag;//删除标志
	String charge_person;//负责人
	Double deposit;//投标保证金
	String contact_a_person;//甲方联系人
	Date deposit_expire_date;//保证金回收时间
	String tel;//电话
	String uploadExcel;//附件
	
	
	
	public String getUploadExcel() {
		return uploadExcel;
	}
	public void setUploadExcel(String uploadExcel) {
		this.uploadExcel = uploadExcel;
	}
	public String getTendering_id() {
		return tendering_id;
	}
	public void setTendering_id(String tendering_id) {
		this.tendering_id = tendering_id;
	}
	public String getTendering_no() {
		return tendering_no;
	}
	public void setTendering_no(String tendering_no) {
		this.tendering_no = tendering_no;
	}
	public String getProject_no() {
		return project_no;
	}
	public void setProject_no(String project_no) {
		this.project_no = project_no;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getProject_type() {
		return project_type;
	}
	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}
	public String getTendering_unit() {
		return tendering_unit;
	}
	public void setTendering_unit(String tendering_unit) {
		this.tendering_unit = tendering_unit;
	}
	public Date getOpen_time() {
		return open_time;
	}
	public void setOpen_time(Date open_time) {
		this.open_time = open_time;
	}
	public Double getBudget_amount() {
		return budget_amount;
	}
	public void setBudget_amount(Double budget_amount) {
		this.budget_amount = budget_amount;
	}
	public String getProject_address() {
		return project_address;
	}
	public void setProject_address(String project_address) {
		this.project_address = project_address;
	}
	public Integer getConstruc_period() {
		return construc_period;
	}
	public void setConstruc_period(Integer construc_period) {
		this.construc_period = construc_period;
	}
	public String getProcure_content() {
		return procure_content;
	}
	public void setProcure_content(String procure_content) {
		this.procure_content = procure_content;
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
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public Integer getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(Integer del_flag) {
		this.del_flag = del_flag;
	}
	public String getCharge_person() {
		return charge_person;
	}
	public void setCharge_person(String charge_person) {
		this.charge_person = charge_person;
	}
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public String getContact_a_person() {
		return contact_a_person;
	}
	public void setContact_a_person(String contact_a_person) {
		this.contact_a_person = contact_a_person;
	}
	public Date getDeposit_expire_date() {
		return deposit_expire_date;
	}
	public void setDeposit_expire_date(Date deposit_expire_date) {
		this.deposit_expire_date = deposit_expire_date;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	
	
}
