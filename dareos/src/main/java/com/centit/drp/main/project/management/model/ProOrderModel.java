package com.centit.drp.main.project.management.model;

import java.util.Date;
/**
 * 项目指令
 * @author wang_pt
 *
 */
public class ProOrderModel {
	
	String  project_directive_id;//项目指令ID  
	String  project_id;//项目ID  
	String  project_no;//项目编号  
	String  project_name;//项目名称  
	String  directive_type;//指令类型  
	String  state;//状态  
	String  creator;//制单人ID  
	String  cre_name;//制单人名称  
	Date  	cre_time;//制单时间  
	String  updator;//更新人ID  
	String  upd_name;//更新人名称  
	Date  	upd_time;//更新时间  
	String  dept_id;//制单部门ID  
	String  dept_name;//制单部门名称  
	String  org_id;//制单机构ID  
	String  org_name;//制单机构名称  
	String  del_flag;//删除标记  
	String 	uploadfile;//
	
	//项目信息阶段附件id
	String sample_att_id;
	String sample_houses_att_id;
	String contract_sing_att_id;
	String project_handover_att_id;
	String material_apply_att_id;
	String install_contract_sing_att_id;
	String goods_order_att_id;
	String send_pro_att_id;
	String visa_att_id;
	String replenishment_att_id;
	String service_att_id;
	
	public String getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile;
	}
	public String getProject_directive_id() {
		return project_directive_id;
	}
	public void setProject_directive_id(String project_directive_id) {
		this.project_directive_id = project_directive_id;
	}
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
	public String getDirective_type() {
		return directive_type;
	}
	public void setDirective_type(String directive_type) {
		this.directive_type = directive_type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}
	public String getSample_att_id() {
		return sample_att_id;
	}
	public void setSample_att_id(String sample_att_id) {
		this.sample_att_id = sample_att_id;
	}
	public String getSample_houses_att_id() {
		return sample_houses_att_id;
	}
	public void setSample_houses_att_id(String sample_houses_att_id) {
		this.sample_houses_att_id = sample_houses_att_id;
	}
	public String getContract_sing_att_id() {
		return contract_sing_att_id;
	}
	public void setContract_sing_att_id(String contract_sing_att_id) {
		this.contract_sing_att_id = contract_sing_att_id;
	}
	public String getProject_handover_att_id() {
		return project_handover_att_id;
	}
	public void setProject_handover_att_id(String project_handover_att_id) {
		this.project_handover_att_id = project_handover_att_id;
	}
	public String getMaterial_apply_att_id() {
		return material_apply_att_id;
	}
	public void setMaterial_apply_att_id(String material_apply_att_id) {
		this.material_apply_att_id = material_apply_att_id;
	}
	public String getInstall_contract_sing_att_id() {
		return install_contract_sing_att_id;
	}
	public void setInstall_contract_sing_att_id(String install_contract_sing_att_id) {
		this.install_contract_sing_att_id = install_contract_sing_att_id;
	}
	public String getGoods_order_att_id() {
		return goods_order_att_id;
	}
	public void setGoods_order_att_id(String goods_order_att_id) {
		this.goods_order_att_id = goods_order_att_id;
	}
	public String getSend_pro_att_id() {
		return send_pro_att_id;
	}
	public void setSend_pro_att_id(String send_pro_att_id) {
		this.send_pro_att_id = send_pro_att_id;
	}
	public String getVisa_att_id() {
		return visa_att_id;
	}
	public void setVisa_att_id(String visa_att_id) {
		this.visa_att_id = visa_att_id;
	}
	public String getReplenishment_att_id() {
		return replenishment_att_id;
	}
	public void setReplenishment_att_id(String replenishment_att_id) {
		this.replenishment_att_id = replenishment_att_id;
	}
	public String getService_att_id() {
		return service_att_id;
	}
	public void setService_att_id(String service_att_id) {
		this.service_att_id = service_att_id;
	}
	
	

}
