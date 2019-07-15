package com.centit.drp.main.project.management.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.project.management.mapper.ManageMapper;
import com.centit.drp.main.project.management.model.ManageModel;
import com.centit.drp.main.project.management.model.PayableModel;
import com.centit.drp.main.project.management.model.ProOrderModel;
import com.centit.drp.main.project.management.service.ManageService;
import com.centit.drp.main.project.management.service.ProjectConsts;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ManageServiceImpl implements ManageService {

	@Autowired
	private ManageMapper manageMapper;
	/**
	 * 删除改变状态 del_flag (1：删除，0：新增)
	 */
	private static Integer PRO_del_flag = 1;

	/**
	 * 获取项目编码
	 * @return
	 */
	public String getManager() {
		String str = LogicUtil.getBmgz("PROJECT_NO_SEQ");
		return str;
	}

	/**
	 * 查询并分页.
	 * @param params 
	 * @param pageNo 
	 */
	public void pageQuery(Map<String, Object> map, PageDesc pageDesc) {
		Page<Object> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
		manageMapper.pageQuery(map);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	/**
	 * 查询项目单条记录回显
	 */
	@Override
	public Map<String, Object> query(Map<String, Object> params) {
		return manageMapper.toQuery(params);
	}
	
	/**
	 * 编辑
	 */
	@Transactional
	public void modify(ManageModel model, HttpServletRequest request) {
		// 获取用户信息
		UserBean userBean = ParamUtil.getUserBean(request);
		model.setDept_id(userBean.getBMXXID());// 所属部门id
		model.setDept_name(userBean.getBMMC());// 所属部门
		model.setTendering_id(model.getTendering_id());
		// 转化时间格式
		if (StringUtils.isNotEmpty(model.getProject_id())) {
			model.setUpdator(userBean.getXTYHID());
			model.setUpd_name(userBean.getXM());
			manageMapper.modify(model);
		} else {
			model.setProject_id(StringUtil.uuid32len());// 项目ID
			model.setProject_no(getManager());
			model.setCreator(userBean.getXTYHID());// 制单人ID
			model.setCre_name(userBean.getXM());
			model.setState("立项");
			manageMapper.insert(model);
		}

	}
	
	

	/**
	 * 删除项目:更新状态
	 */
	@Transactional
	public void delete(String project_id, UserBean userBean) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project_id", project_id);
		params.put("updator", userBean.getXTYHID());
		params.put("upd_name", userBean.getXM());
		params.put("del_flag", PRO_del_flag);
		manageMapper.updateByID(params);
	}
	
	/**
	 * 项目明细
	 */
	@Override
	public Map<String, Object> querySun(Map<String, Object> params) {
		Map<String, Object> map2 = new HashMap<String, Object>();
		/**
		 * 查询单个项目记录
		 */
		Map<String, Object> map = manageMapper.toQuery(params);
		//统计实际出货总金额 sumMonery
		Map<String, Object> sumMonerymap = manageMapper.sumMonery(map);
		if(null!=sumMonerymap){
			map.put("OUT_PRD_AMOUNT", sumMonerymap.get("OUT_PRD_AMOUNT")==null?0:sumMonerymap.get("OUT_PRD_AMOUNT"));
		}	
		/**
		 * 查询付款记录多条
		 */
		List<Map<String, Object>> entrySun = manageMapper.listQuery(map);

		map.put("entrySun", entrySun);
		map2.put("project_id", map.get("PROJECT_ID"));
		/**
		 * 查询阶段附件信息：多条记录
		 * 
		 */
		// 附件
		map2.put("directive_type",ProjectConsts.PROJECT_SAMPLE_INFOR);
		List<Map<String, Object>> listPath1 = manageMapper.toQueryFile(map2);
		map.put("listPath1", listPath1);
		//样板房
		map2.put("directive_type", ProjectConsts.PROJECT_MODEL_HOUSES);
		List<Map<String, Object>> listPath2 = manageMapper.toQueryFile(map2);
		map.put("listPath2", listPath2);
		//
		map2.put("directive_type", ProjectConsts.PROJECT_CONT_SIG);
		List<Map<String, Object>> listPath3 = manageMapper.toQueryFile(map2);
		map.put("listPath3", listPath3);
		//
		map2.put("directive_type", ProjectConsts.PROJECT_HANDOVER);
		List<Map<String, Object>> listPath4 = manageMapper.toQueryFile(map2);
		map.put("listPath4", listPath4);
		//
		map2.put("directive_type", ProjectConsts.PROJECT_MATER_PURC);
		List<Map<String, Object>> listPath5 = manageMapper.toQueryFile(map2);
		map.put("listPath5", listPath5);
		//
		map2.put("directive_type", ProjectConsts.PROJECT_SIG_CONT);
		List<Map<String, Object>> listPath6 = manageMapper.toQueryFile(map2);
		map.put("listPath6", listPath6);
		//
		map2.put("directive_type", ProjectConsts.PROJECT_BULK_ORDER);
		List<Map<String, Object>> listPath7 = manageMapper.toQueryFile(map2);
		map.put("listPath7", listPath7);
		//
		map2.put("directive_type", ProjectConsts.PROJECT_DELIVER_GOODS);
		List<Map<String, Object>> listPath8 = manageMapper.toQueryFile(map2);
		map.put("listPath8", listPath8);
		//
		map2.put("directive_type", ProjectConsts.PROJECT_VISA);
		List<Map<String, Object>> listPath9 = manageMapper.toQueryFile(map2);
		map.put("listPath9", listPath9);
		//
		map2.put("directive_type", ProjectConsts.PROJECT_REPLEN);
		List<Map<String, Object>> listPath10 = manageMapper.toQueryFile(map2);
		map.put("listPath10", listPath10);
		//
		map2.put("directive_type", ProjectConsts.PROJECT_REPAIR);
		List<Map<String, Object>> listPath11 = manageMapper.toQueryFile(map2);
		map.put("listPath11", listPath11);
		return map;
	}
	
	/**
	 * 项目节点数据维护
	 * 
	 */
	@Transactional
	public void txChldEdit(ProOrderModel model, List<PayableModel> modelList, UserBean userBean, String project_id) {
		List<PayableModel> addList = new ArrayList<PayableModel>();
		List<PayableModel> upList = new ArrayList<PayableModel>();
		Map<String, Object> maps = new HashMap<String, Object>();
		if (modelList != null) {
			for (int i = 0,length=modelList.size(); i < length; i++) {
				PayableModel models = modelList.get(i);
				if (StringUtils.isEmpty(models.getProject_payable_id())) {
					models.setProject_payable_id(StringUtil.uuid32len());
					addList.add(models);
				} else {
					upList.add(models);
				}
			}
			if (!upList.isEmpty()) {
				for (int i = 0; i <upList.size() ; i++) {
					manageMapper.editMX(upList.get(i));

				}
			}
			if (!addList.isEmpty()) {
				for (int i = 0; i < addList.size(); i++) {
					manageMapper.insertMX(addList.get(i));

				}
			}
		}
		// 上传附件 
		if (model != null) {
			String sample_att_id = model.getSample_att_id(); // request.getParameter("sample_att_id");
			String sample_houses_att_id = model.getSample_houses_att_id();// request.getParameter("sample_houses_att_id");
			String contract_sing_att_id = model.getContract_sing_att_id(); // request.getParameter("contract_sing_att_id");
			String project_handover_att_id = model.getProject_handover_att_id();// request.getParameter("project_handover_att_id");
			String material_apply_att_id = model.getMaterial_apply_att_id();// request.getParameter("material_apply_att_id");
			String install_contract_sing_att_id = model.getInstall_contract_sing_att_id();// request.getParameter("install_contract_sing_att_id");
			String goods_order_att_id = model.getGoods_order_att_id();// request.getParameter("goods_order_att_id");
			String send_pro_att_id = model.getSend_pro_att_id();// request.getParameter("send_pro_att_id");
			String visa_att_id = model.getVisa_att_id();// request.getParameter("visa_att_id");
			String replenishment_att_id = model.getReplenishment_att_id();// request.getParameter("replenishment_att_id");
			String service_att_id = model.getService_att_id();// request.getParameter("service_att_id");
			// XMGL2019041701:自定义唯一标识编号
			List<String> pathList =null;
			model.setProject_id(project_id);
			getBaseInfo(model,userBean);
			//样品信息
			if (StringUtils.isNotEmpty(sample_att_id)) {
				pathList = new ArrayList<String>(Arrays.asList(sample_att_id.split(",")));
				model.setDirective_type(ProjectConsts.PROJECT_SAMPLE_INFOR);
				model.setProject_directive_id(StringUtil.uuid32len());
				manageMapper.insertOrderFile(model);
				InterUtil.insertAttPath(pathList, userBean, model.getProject_directive_id());
			}
			// 样板房
			if (StringUtils.isNotEmpty(sample_houses_att_id)) {
				pathList = new ArrayList<String>(Arrays.asList(sample_houses_att_id.split(",")));
				model.setDirective_type(ProjectConsts.PROJECT_MODEL_HOUSES);
				model.setProject_directive_id(StringUtil.uuid32len());
				manageMapper.insertOrderFile(model);
				InterUtil.insertAttPath(pathList, userBean, model.getProject_directive_id());

			}
			// 合同签订
			if (StringUtils.isNotEmpty(contract_sing_att_id)) {
				pathList = new ArrayList<String>(Arrays.asList(contract_sing_att_id.split(",")));
				model.setDirective_type(ProjectConsts.PROJECT_CONT_SIG);
				model.setProject_directive_id(StringUtil.uuid32len());
				manageMapper.insertOrderFile(model);
				InterUtil.insertAttPath(pathList, userBean, model.getProject_directive_id());
			}
			// 项目交接
			if (StringUtils.isNotEmpty(project_handover_att_id)) {
				pathList = new ArrayList<String>(Arrays.asList(project_handover_att_id.split(",")));
				model.setDirective_type(ProjectConsts.PROJECT_HANDOVER);
				model.setProject_directive_id(StringUtil.uuid32len());
				manageMapper.insertOrderFile(model);
				InterUtil.insertAttPath(pathList, userBean, model.getProject_directive_id());
			}
			// 材料申购
			if (StringUtils.isNotEmpty(material_apply_att_id)) {
				pathList = new ArrayList<String>(Arrays.asList(material_apply_att_id.split(",")));
				model.setDirective_type(ProjectConsts.PROJECT_MATER_PURC);
				model.setProject_directive_id(StringUtil.uuid32len());
				manageMapper.insertOrderFile(model);
				InterUtil.insertAttPath(pathList, userBean, model.getProject_directive_id());
			}
			// 安装合同签订
			if (StringUtils.isNotEmpty(install_contract_sing_att_id)) {
				pathList = new ArrayList<String>(Arrays.asList(install_contract_sing_att_id.split(",")));
				model.setDirective_type(ProjectConsts.PROJECT_SIG_CONT);
				model.setProject_directive_id(StringUtil.uuid32len());
				manageMapper.insertOrderFile(model);
				InterUtil.insertAttPath(pathList, userBean, model.getProject_directive_id());
			}
			// 大货下单
			if (StringUtils.isNotEmpty(goods_order_att_id)) {
				pathList = new ArrayList<String>(Arrays.asList(goods_order_att_id.split(",")));
				model.setDirective_type(ProjectConsts.PROJECT_BULK_ORDER);
				model.setProject_directive_id(StringUtil.uuid32len());
				manageMapper.insertOrderFile(model);
				InterUtil.insertAttPath(pathList, userBean, model.getProject_directive_id());
			}
			// 发货
			if (StringUtils.isNotEmpty(send_pro_att_id)) {
				pathList = new ArrayList<String>(Arrays.asList(goods_order_att_id.split(",")));
				model.setDirective_type(ProjectConsts.PROJECT_DELIVER_GOODS);
				model.setProject_directive_id(StringUtil.uuid32len());
				manageMapper.insertOrderFile(model);
				InterUtil.insertAttPath(pathList, userBean, model.getProject_directive_id());

			}
			// 签证
			if (StringUtils.isNotEmpty(visa_att_id)) {
				pathList = new ArrayList<String>(Arrays.asList(visa_att_id.split(",")));
				model.setDirective_type(ProjectConsts.PROJECT_VISA);
				model.setProject_directive_id(StringUtil.uuid32len());
				manageMapper.insertOrderFile(model);
				InterUtil.insertAttPath(pathList, userBean, model.getProject_directive_id());

			}
			// 补货
			if (StringUtils.isNotEmpty(replenishment_att_id)) {
				pathList = new ArrayList<String>(Arrays.asList(replenishment_att_id.split(",")));
				model.setDirective_type(ProjectConsts.PROJECT_REPLEN);
				model.setProject_directive_id(StringUtil.uuid32len());
				manageMapper.insertOrderFile(model);
				InterUtil.insertAttPath(pathList, userBean, model.getProject_directive_id());

			}
			// 维修
			if (StringUtils.isNotEmpty(service_att_id)) {
				pathList = new ArrayList<String>(Arrays.asList(service_att_id.split(",")));
				model.setDirective_type(ProjectConsts.PROJECT_REPAIR);
				model.setProject_directive_id(StringUtil.uuid32len());
				manageMapper.insertOrderFile(model);
				InterUtil.insertAttPath(pathList, userBean, model.getProject_directive_id());
			}
		}

	}

	/**
	 * 项目付款阶段数据删除
	 */
	@Transactional
	public void sunDelete(String pROJECT_PAYABLE_ID) {
		// 批量删除时，id是用‘,’分开的字符串
		String[] ids = pROJECT_PAYABLE_ID.replace("'", "").trim().split(",");
		for (String p_id : ids) {
			manageMapper.deleteById(p_id);
		}
	}
	
	/**
	 * 删除附件
	 */
	@Override
	public void deleteFile(String att_id) {
		InterUtil.delByAttId(att_id);
	}
	/**
	 * 传入附件信息批量新增附件信息
	 * 
	 * @param list
	 * @param userBean
	 * @param FROM_BILL_ID
	 */
	public  void insertInfo(List<Map<String, Object>> list, UserBean userBean, String FROM_BILL_ID) {
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		
		for (Map<String, Object> listMap : list) {
			map = new HashMap<String, Object>();
			map.put("ATT_ID", StringUtil.uuid32len());
			map.put("ATT_PATH", listMap.get("ATT_PATH").toString());
			if (!StringUtil.isEmpty((String) listMap.get("SPARE1"))) {
				map.put("SPARE1", listMap.get("SPARE1").toString());
			} else {
				map.put("SPARE1", "");
			}
			map.put("FROM_BILL_ID", FROM_BILL_ID);
			map.put("CREATOR", userBean.getRYXXID());
			map.put("CRE_NAME", userBean.getXM());
			map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
			addList.add(map);
		}
		manageMapper.insertFile(addList);

	}

	@Transactional
	public void saveOrderFile(ProOrderModel model, UserBean userBean) {
			// 添加附件
			getBaseInfo(model,userBean);
			model.setProject_directive_id(StringUtil.uuid32len());
			manageMapper.insertOrderFile(model);
			
			// 编辑指令
			List<String> list = new ArrayList<String>(Arrays.asList(model.getUploadfile().split(",")));
			InterUtil.insertAttPath(list, userBean, model.getProject_directive_id());
	}

	/**
	 * 查询上传单条指令附件
	 */
	@Override
	public List<Map<String, Object>> toQueryFile(Map<String, Object> params) {
		List<Map<String, Object>> listPath=manageMapper.toQueryFile(params);
		if(listPath!=null){
			for(Map<String, Object>mapPath:listPath) {
				String fileName=mapPath.get("ATT_PATH").toString().substring(1);
				mapPath.put("FILENAME",fileName.substring(fileName.indexOf("/")+1));
			}
		}
		return listPath;
	}
	
	//获取基本信息
	private ProOrderModel getBaseInfo(ProOrderModel model,UserBean userBean) {
		model.setCreator(userBean.getXTYHID());// 制单人ID
		model.setCre_name(userBean.getXM());// 制单人名称
		model.setDept_id(userBean.getBMXXID());// 所属部门id
		model.setDept_name(userBean.getBMMC());// 所属部门
		model.setState(ProjectConsts.PROJECT_WAIT_DOWNSEND);//待下发
		return model;
	}
	

}
