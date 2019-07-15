package com.centit.drp.main.project.management.model;

import java.util.Date;
/**
 *    项目管理实体类
 * @author wang_pt
 *
 */
public class ManageModel {

	String project_id;//项目id
	String project_no;//项目编号
	String project_name;//项目名称
	String tendering_id;//招投标id
	String tendering_no;//招投标no
	String project_type;//项目类型
	String contract_unit;//合同签订单位
	String address;//地址
	String charge_person;//负责人
	String warranty_period;//质保期
	Date start_date;//开工时间
	Date settle_date;//结算时间
	Double settle_amount;//结算金额
	String unit_full_name;//单位全称
	String unit_address;//单位地址
	String bank_acct;//账户
	String bank_name;//开户银行
	String ax_burden;//税务登记号
	String remark;//备注
	Date check_accept;//竣工验收时间
	Date warranty_amount_expire_date;//质保金到期时间
	String state;//状态
	String contact_a_person;//甲方联系人
	String supply_unit;//供货单位
	String salesman;//业务员
	String project_manager;//项目经理
	String contracts_number;//合同数量
	String colour_and_material;//颜色及材质
	String appearance_requirement;//涂装或表面效果要求
	String material_requirement;//用材要求
	String hardware_config;//五金配置
	String packaging_requirements;//产品包装及标识要求
	String process_drawing;//工艺图纸
	Double out_prd_amount;//实际出货总金额
	
	//表公用字段
	String creator;
	String cre_name;
	Date cre_time;
	String updator;
	String upd_name;
	Date upd_time;
	String dept_id;
	String dept_name;
	Double del_flag;//删除标志
	String uploadFile;// 上传附件
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
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
	public String getProject_type() {
		return project_type;
	}
	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}
	public String getContract_unit() {
		return contract_unit;
	}
	public void setContract_unit(String contract_unit) {
		this.contract_unit = contract_unit;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCharge_person() {
		return charge_person;
	}
	public void setCharge_person(String charge_person) {
		this.charge_person = charge_person;
	}
	public String getWarranty_period() {
		return warranty_period;
	}
	public void setWarranty_period(String warranty_period) {
		this.warranty_period = warranty_period;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getSettle_date() {
		return settle_date;
	}
	public void setSettle_date(Date settle_date) {
		this.settle_date = settle_date;
	}
	public Double getSettle_amount() {
		return settle_amount;
	}
	public void setSettle_amount(Double settle_amount) {
		this.settle_amount = settle_amount;
	}
	public String getUnit_full_name() {
		return unit_full_name;
	}
	public void setUnit_full_name(String unit_full_name) {
		this.unit_full_name = unit_full_name;
	}
	public String getUnit_address() {
		return unit_address;
	}
	public void setUnit_address(String unit_address) {
		this.unit_address = unit_address;
	}
	public String getBank_acct() {
		return bank_acct;
	}
	public void setBank_acct(String bank_acct) {
		this.bank_acct = bank_acct;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getAx_burden() {
		return ax_burden;
	}
	public void setAx_burden(String ax_burden) {
		this.ax_burden = ax_burden;
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
	public Double getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(Double del_flag) {
		this.del_flag = del_flag;
	}
	public Date getCheck_accept() {
		return check_accept;
	}
	public void setCheck_accept(Date check_accept) {
		this.check_accept = check_accept;
	}
	public Date getWarranty_amount_expire_date() {
		return warranty_amount_expire_date;
	}
	public void setWarranty_amount_expire_date(Date warranty_amount_expire_date) {
		this.warranty_amount_expire_date = warranty_amount_expire_date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getContact_a_person() {
		return contact_a_person;
	}
	public void setContact_a_person(String contact_a_person) {
		this.contact_a_person = contact_a_person;
	}
	public String getSupply_unit() {
		return supply_unit;
	}
	public void setSupply_unit(String supply_unit) {
		this.supply_unit = supply_unit;
	}
	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public String getProject_manager() {
		return project_manager;
	}
	public void setProject_manager(String project_manager) {
		this.project_manager = project_manager;
	}
	public String getContracts_number() {
		return contracts_number;
	}
	public void setContracts_number(String contracts_number) {
		this.contracts_number = contracts_number;
	}
	public String getColour_and_material() {
		return colour_and_material;
	}
	public void setColour_and_material(String colour_and_material) {
		this.colour_and_material = colour_and_material;
	}
	public String getAppearance_requirement() {
		return appearance_requirement;
	}
	public void setAppearance_requirement(String appearance_requirement) {
		this.appearance_requirement = appearance_requirement;
	}
	public String getMaterial_requirement() {
		return material_requirement;
	}
	public void setMaterial_requirement(String material_requirement) {
		this.material_requirement = material_requirement;
	}
	public String getHardware_config() {
		return hardware_config;
	}
	public void setHardware_config(String hardware_config) {
		this.hardware_config = hardware_config;
	}
	public String getPackaging_requirements() {
		return packaging_requirements;
	}
	public void setPackaging_requirements(String packaging_requirements) {
		this.packaging_requirements = packaging_requirements;
	}
	public String getProcess_drawing() {
		return process_drawing;
	}
	public void setProcess_drawing(String process_drawing) {
		this.process_drawing = process_drawing;
	}
	public Double getOut_prd_amount() {
		return out_prd_amount;
	}
	public void setOut_prd_amount(Double out_prd_amount) {
		this.out_prd_amount = out_prd_amount;
	}
	public String getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	
	
	

}
