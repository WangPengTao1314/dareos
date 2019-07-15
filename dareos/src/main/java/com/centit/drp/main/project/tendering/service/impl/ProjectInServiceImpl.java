package com.centit.drp.main.project.tendering.service.impl;

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

import com.centit.commons.util.InterUtil;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.project.tendering.mapper.ProjectInfoMapper;
import com.centit.drp.main.project.tendering.model.ProjectInfoModel;
import com.centit.drp.main.project.tendering.service.ProjectInfoService;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 工程管理招投标 _业务处理层
 * 
 * @author wang_pt
 *
 */
@Service
public class ProjectInServiceImpl implements ProjectInfoService {

	@Autowired
	private ProjectInfoMapper projectInfoMapper;
	/**
	 * 删除改变状态 del_flag (1：删除，0：新增)
	 */
	private static Integer PRO_del_flag = 1;

	/**
	 * 招投标信息编辑
	 */
	@Transactional
	public void modify(ProjectInfoModel model, HttpServletRequest request) {
		// 获取用户信息
		UserBean userBean = ParamUtil.getUserBean(request);
		// 获取用户信息
		model.setDept_id(userBean.getBMXXID());// 所属部门id
		model.setDept_name(userBean.getBMMC());// 所属部门
		if (StringUtils.isNotEmpty(model.getTendering_id())) {
			model.setUpdator(userBean.getXTYHID());
			model.setUpd_name(userBean.getXM());
			projectInfoMapper.modify(model);
			//
		} else {
			model.setTendering_id(StringUtil.uuid32len());// 项目ID
			model.setTendering_no(getTender());
			model.setCreator(userBean.getXTYHID());// 制单人ID
			model.setCre_name(userBean.getXM());
			projectInfoMapper.insert(model);
		}
		// 添加附件
		if (StringUtils.isNotEmpty(model.getUploadExcel())) {
			List<String> pathList = new ArrayList<String>(Arrays.asList(model.getUploadExcel().split(",")));
			InterUtil.insertAttPath(pathList, userBean, model.getTendering_id());
		}

	}

	/**
	 * 列表分页查询
	 */
	public void pageQuery(Map<String, Object> map, PageDesc pageDesc) {
		Page<Object> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
		projectInfoMapper.pageQuery(map);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	/**
	 * 获取单条招投标记录信息
	 */
	@Override
	public Map<String, Object> query(Map<String, Object> params) {
		Map<String, Object> proQuery = projectInfoMapper.toQuery(params);
		List<Map<String, String>> listPath = InterUtil.findAttInfo(proQuery.get("TENDERING_ID").toString());
		proQuery.put("paths", listPath);
		return proQuery;

	}

	/**
	 * delete
	 */
	@Transactional
	public void delete(String tendering_id, UserBean userBean) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tendering_id", tendering_id);
		params.put("updator", userBean.getXTYHID());
		params.put("upd_name", userBean.getXM());
		params.put("del_flag", PRO_del_flag);
		projectInfoMapper.updateByID(params);

	}

	/**
	 * 附件上传删除
	 */
	@Override
	public void deleteFile(String att_ids, UserBean userBean) {
		// List<String>lists =Arrays.asList(att_ids.split(","));
		// for(int i=0,length=lists.size();i<length;i++) {
		// att_ids=lists.get(i);//substring(1,lists.get(i).length()-1);
		InterUtil.delByAttId(att_ids);
		// }
	}

	/**
	 * 获取招投编码
	 * 
	 * @return
	 */
	public String getTender() {
		String str = LogicUtil.getBmgz("TENDERING_NO_SEQ");
		return str;
	}

}
