/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ChannServiceImpl.java
 */
package com.centit.erp.sale.creditreq.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.common.service.BookkeepingService;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.erp.sale.creditreq.mapper.CreditReqMapper;
import com.centit.erp.sale.creditreq.model.CreditReqModel;
import com.centit.erp.sale.creditreq.service.CreditReqService;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 充值信息
 * @author liu_yg
 *
 */
@Service
public class CreditReqServiceImpl  implements CreditReqService {
	
	@Autowired
	private CreditReqMapper mapper;
	
	@Autowired
	private BookkeepingService service;

	/**
     * 查询并分页.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
	@Override
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc) {
		Page<CreditReqModel> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	public CreditReqModel loadById(String creditReqId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("creditReqId", creditReqId);
		CreditReqModel model = mapper.loadById(map);
		model.setAttPath(InterUtil.findAttStr(creditReqId));
		return model;
	}

    /**
     * 软删除.
     * 
     * @param userBean the user bean
     * 
     */
    @Transactional
	public void delete(String creditReqId, UserBean userBean) {
    	CreditReqModel model =new CreditReqModel();
    	model.setCreditReqId(creditReqId);
    	model.setUpdator(userBean.getXTYHID());		//更新人ID
    	model.setUpdName(userBean.getXM());			//更新人名称
    	model.setUpdTime("sysdate");
    	model.setDelFlag(BusinessConsts.DEL_FLAG_DROP);
        mapper.delete(model);
	}
    
	@Override
	@Transactional
	public void txEdit(String creditReqId, CreditReqModel model, UserBean userBean) {
		
		if (StringUtils.isEmpty(creditReqId)) {
			creditReqId = StringUtil.uuid32len();
			model.setCreditReqId(creditReqId);
			model.setCreditReqNo(LogicUtil.getBmgz("CREDIT_REQ_NO_SEQ"));
			model.setState(BusinessConsts.UNCOMMIT);
			model.setDelFlag(BusinessConsts.DEL_FLAG_COMMON);
			model.setCreator(userBean.getXTYHID());			//制单人ID
			model.setCreName(userBean.getXM());				//制单人名称
			model.setDeptId(userBean.getBMXXID());			//制单部门ID
			model.setDeptName(userBean.getBMMC());			//制单部门名称
			model.setOrgId(userBean.getJGXXID());				//制单机构ID
			model.setOrgName(userBean.getJGMC());				//制单机构名称
			mapper.insert(model);
		}else{
			model.setCreditReqId(creditReqId);
        	model.setUpdator(userBean.getXTYHID());		//更新人ID
        	model.setUpdName(userBean.getXM());			//更新人名称
        	model.setUpdTime("sysdate");
            txUpdateById(model);
        }
        
      //保存附件
      		String path = model.getAttPath();
      		InterUtil.delByFromId(creditReqId);//先删除 后添加
      		if(!StringUtil.isEmpty(path)){
      			List<String> pathList = new ArrayList<String>(Arrays.asList(path.split(",")));
      			InterUtil.insertAttPath(pathList, userBean, creditReqId);
      		}
	}
	
    /**
     * @param params the params
     */
	@Transactional
	public void txUpdateById(CreditReqModel model) {
		mapper.updateById(model);        
    }
	
	@Transactional
	public void audit(CreditReqModel model) throws ParseException{
		// 获取当前信用信息
		CreditReqModel newModel = loadById(model.getCreditReqId());
		if(BusinessConsts.PASS.equals(model.getState())){//判断是否是审核通过
			SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = f.parse(newModel.getEffectiveDate());
			Date date2 = f.parse(f.format(new Date()));
			// 如果生效日期小于或者等于今天，直接增加信用并增加流水,如果生效日期大于今天，则通过定时器任务增加
			if(date1.getTime()<=date2.getTime()){
				service.creditAccount(model.getCreditReqId(), model.getAuditId());
				model.setProcessFlag(BusinessConsts.YJLBJ_FLAG_FALSE);//设置操作标记为0，说明该条信用已生效
			}
		}
		mapper.updateById(model);
	}
	
	
}
