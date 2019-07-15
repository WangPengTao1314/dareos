package com.centit.drp.main.sales.deliver.after.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.common.mapper.FlowMapper;
import com.centit.common.po.FlowTrackModel;
import com.centit.common.service.FlowService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.sales.deliver.after.mapper.AfterMapper;
import com.centit.drp.main.sales.deliver.after.model.AfterModel;
import com.centit.drp.main.sales.deliver.after.service.AfterService;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class AfterServiceImpl implements AfterService {
	
	@Autowired
	private AfterMapper afterMapper;
	
	@Autowired
	FlowMapper mapper;
	
	@Autowired
	private FlowService service;
	
	/**
	 * 删除改变状态 del_flag (1：删除，0：新增)
	 */
	private static Integer PRO_del_flag = 1;

	/**
	 * 更新状态
	 */
	@Transactional
	public void delete(String problem_feedback_id, UserBean userBean) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("PROBLEM_FEEDBACK_ID", problem_feedback_id);
		params.put("updator", userBean.getXTYHID());
		params.put("upd_name", userBean.getXM());
		params.put("del_flag", PRO_del_flag);
		afterMapper.updateByID(params);
	}
	
	/**
	 * 列表分页查询
	 */
	@Override
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc) {
		Page<Object> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
		afterMapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}
	/**
	 * 单条记录信息查询，用于数据回显
	 */
	@Override
	public Map<String, Object> query(Map<String, Object> params) {
		
		Map<String, Object> map=afterMapper.toQuery(params);
		
		params.put("SPARE1","WPT20190610");
		//List<Map<String, Object>> listPath1=afterMapper.toQueryFile(map);
		
		String attPathP =getAttPath(params);
		map.put("ATT_PATH", attPathP);
		
		params.put("SPARE1","WPT20190611");
		//List<Map<String, Object>> listPath2 =afterMapper.toQueryFile(map); 
		attPathP =getAttPath(params);
		map.put("ATT_PATH2", attPathP);
		return map;
	}
	
	
	/**
	 * 记录数据的编辑
	 */
	@Transactional
	public void edit(AfterModel model, HttpServletRequest request) {
		// 获取用户信息
		UserBean userBean = ParamUtil.getUserBean(request);
		model.setDept_id(userBean.getBMXXID());// 所属部门id
		model.setDept_name(userBean.getBMMC());// 所属部门
		//节点操作跟踪
		//操作节点跟踪
		//String flowNo =model.getProblem_feedback_no();
		String remarks="问题反馈单新增";//
		
		//model.setOrder_trace_id(order_trace_id);
		
		if (StringUtils.isNotEmpty(model.getProblem_feedback_id())) {
			model.setUpdator(userBean.getXTYHID());
			model.setUpd_name(userBean.getXM());
			//model.setState("处理中");
			afterMapper.modify(model);
			if("处理中".equals(model.getState())) {
				remarks="问题反馈单已提交";
			}else  {
				remarks="问题反馈单编辑";
			}
 		} else {
			model.setProblem_feedback_id(StringUtil.uuid32len());// 项目ID
			model.setProblem_feedback_no(LogicUtil.getFeedbackNo(model.getOrder_org()));
			model.setCreator(userBean.getXTYHID());// 制单人ID
			model.setCre_name(userBean.getXM());
			//model.setChann_id(userBean.getCHANN_ID());
			//model.setChann_no(userBean.getCHANN_NO());
			model.setDept_id(userBean.getBMXXID());// 所属部门id org_id
			model.setDept_name(userBean.getBMMC());// 所属部门
			model.setOrg_id(userBean.getJGXXID());//机构id
			model.setOrg_name(userBean.getJGJC());//
			model.setState("草稿");
			afterMapper.insert(model);
		}
		String flowNo = service.getFlowNoByLedger(model.getOrder_org(), "0");
		service.insertFlowTrack(flowNo,model.getProblem_feedback_id(), model.getProblem_feedback_no(), userBean,"",remarks);
		//添加附件
		Map<String, Object> mapPath=null;
		if(!"处理中".equals(model.getState())) {
			mapPath=new  HashMap<String, Object>();
			mapPath.put("SPARE1","WPT20190610");
			mapPath.put("PROBLEM_FEEDBACK_ID",model.getProblem_feedback_id());
			//InterUtil.delByFromId(model.getProblem_feedback_id());
			afterMapper.updateFile(mapPath);
		}
		if (StringUtils.isNotEmpty(model.getUploadExcel())) {
			List<String>listPath=Arrays.asList(model.getUploadExcel().toString().split(","));
			for(String paths:listPath) {
				mapPath=new  HashMap<String, Object>();
				mapPath.put("ATT_PATH",paths);
				/**
				 * 标识：用来区分同一个问题单下的
				 */
				mapPath.put("SPARE1","WPT20190610");
				insertInfo(mapPath,userBean,model.getProblem_feedback_id());
			}
			//InterUtil.insertAttPath(listPath, userBean, model.getProblem_feedback_id());
		}
		if(afterMapper.checkProblemNO(model.getProblem_feedback_no().trim())>1) {
			throw new ServiceException("反馈单号："+model.getProblem_feedback_no()+"已存在！！！");
		}
		
	}
	/**
	 * 
	 */
	@Transactional
	public void editHandle(AfterModel model, HttpServletRequest request) {
		UserBean userBean = ParamUtil.getUserBean(request);
		//String selRowId = ParamUtil.get(request, "selRowId");
		//String order_org = ParamUtil.get(request, "ORDER_ORG");
		String text = ParamUtil.get(request, "RETURN_RSON");
		model.setReturn_reason(text);
		String type=request.getParameter("STATE");//
		//
		 if("已退回".equals(type)) {
			 model.setState("已退回");
		}else {
			model.setState("已处理");  
		}
		//
		String flowNo = service.getFlowNoByLedger(model.getOrder_org(), "0");
		service.insertFlowTrack(flowNo,model.getProblem_feedback_id(),model.getProblem_feedback_no(), userBean,text,model.getState()=="已处理"?"问题反馈单已处理":"问题反馈单已退回");
		//转化时间格式
		//model.setOrder_trace_id(order_trace_id);
		// 获取用户信息
		Map<String, Object> map=new HashMap<String, Object>();
		model.setDept_id(userBean.getBMXXID());// 所属部门id
		model.setDept_name(userBean.getBMMC());// 所属部门
		model.setUpdator(userBean.getXTYHID());
		model.setUpd_name(userBean.getXM());
		afterMapper.modify(model);
		//问题处理附件:
		//唯一标识字段，用于区分一个id绑定的2个文件
		//InterUtil.delByFromId(model.getSale_order_id());//
		map.put("ATT_PATH", model.getUploadExcel());
		map.put("SPARE1","WPT20190611");
		map.put("PROBLEM_FEEDBACK_ID",model.getProblem_feedback_id());
		//InterUtil.delByFromId(model.getProblem_feedback_id());
		if(StringUtils.isNotBlank(model.getProblem_feedback_id())) {
			afterMapper.updateFile(map);
			insertInfo(map,userBean,model.getProblem_feedback_id());
			
		}
		if(afterMapper.checkProblemNO(model.getProblem_feedback_no().trim())>1) {
			throw new ServiceException("反馈单号："+model.getProblem_feedback_no()+"已存在！！！");
		}
		
	}
	
	/**
	 * 传入附件信息批量新增附件信息
	 * 
	 * @param list
	 * @param userBean
	 * @param FROM_BILL_ID
	 */
	public  void insertInfo(Map<String, Object> list, UserBean userBean, String FROM_BILL_ID) {
		Map<String, Object> map = null;
		map = new HashMap<String, Object>();
		map.put("ATT_ID", StringUtil.uuid32len());
		map.put("ATT_PATH", list.get("ATT_PATH")==null?"":list.get("ATT_PATH").toString());
	    map.put("SPARE1",list.get("SPARE1")==null?"":list.get("SPARE1").toString());
		map.put("FROM_BILL_ID", FROM_BILL_ID);
		map.put("CREATOR", userBean.getRYXXID());
		map.put("CRE_NAME", userBean.getXM());
		map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		
		afterMapper.insertFile(map);

	}
	/**
	 * 获取问题反馈编码
	 * @return
	 */
//	public String getAfterID() {
//		String str = LogicUtil.getBmgz("PROBLEM_FEEDBACK_NOS");
//		return str;
//	}
	/**
	 * delete file
	 */
	@Override
	public void deleteFile(String att_id, UserBean userBean) {
		InterUtil.delByAttId(att_id);
	}
	
	/**
	 * 问题反馈单同一个单据不同类型附件的处理方法
	 * @param mapfile
	 * @return
	 */
	public  String getAttPath(Map<String, Object>mapfile){
		String attPath = "";
		//List<Map<String, String>> attList = InterUtil.findAttInfo(pkId);
		List<Map<String, Object>> attList=afterMapper.toQueryFile(mapfile);
		for (Iterator itAtt = attList.iterator(); itAtt.hasNext();) {
			Map<String, String> map = (Map<String, String>) itAtt.next();
			attPath += map.get("ATT_PATH");
			attPath += ",";
		}
		if (attPath.length() > 0) {
			attPath = attPath.substring(0, attPath.length()-1);
		}
		return attPath;
	}

}
