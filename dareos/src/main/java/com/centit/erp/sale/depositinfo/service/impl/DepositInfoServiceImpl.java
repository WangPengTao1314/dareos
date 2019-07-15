/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ChannServiceImpl.java
 */
package com.centit.erp.sale.depositinfo.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.centit.erp.sale.depositinfo.mapper.DepositInfoMapper;
import com.centit.erp.sale.depositinfo.model.DepositInfoModel;
import com.centit.erp.sale.depositinfo.service.DepositInfoService;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 充值信息
 * @author liu_yg
 *
 */
@Service
public class DepositInfoServiceImpl  implements DepositInfoService {
	
	@Autowired
	private DepositInfoMapper mapper;
	
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
		Page<DepositInfoModel> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	public DepositInfoModel loadById(String depositId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("depositId", depositId);
		DepositInfoModel model = mapper.loadById(map);
		model.setAttPath(InterUtil.findAttStr(depositId));
		return model;
	}

    /**
     * 软删除.
     * 
     * @param userBean the user bean
     * 
     */
    @Transactional
	public void delete(String depositId, UserBean userBean) {
    	Map <String, String> params = new HashMap <String, String>();
        params.put("depositId", depositId);
        params.put("updator", userBean.getXTYHID());
        params.put("updName", userBean.getXM());
        params.put("updTime", "sysdate");
        params.put("delFlag", BusinessConsts.DEL_FLAG_DROP);
        mapper.delete(params);
	}
    
	@Override
	@Transactional
	public void txEdit(String depositId, DepositInfoModel model, UserBean userBean) {
		Map <String, String> params = new HashMap <String, String>();
        params.put("depositType", model.getDepositType());			
        params.put("channId", model.getChannId());
        params.put("channNo", model.getChannNo());
        params.put("channName", model.getChannName());
        params.put("depositAmount", model.getDepositAmount());
        params.put("ledgerId", model.getLedgerId());
        params.put("ledgerName", model.getLedgerName());
        params.put("remark", model.getRemark());			//备注
        if (StringUtils.isEmpty(depositId)) {
        	depositId = StringUtil.uuid32len();
        	params.put("depositNo", LogicUtil.getBmgz("DEPOSIT_NO_SEQ"));
        	params.put("depositId", depositId);		    //充值信息编号
            params.put("state", BusinessConsts.UNCOMMIT);	//状态
            params.put("delFlag", BusinessConsts.DEL_FLAG_COMMON);
            params.put("creator", userBean.getXTYHID());			//制单人ID
            params.put("creName", userBean.getXM());				//制单人名称
            params.put("deptId", userBean.getBMXXID());			//制单部门ID
            params.put("deptName", userBean.getBMMC());			//制单部门名称
            params.put("orgId", userBean.getJGXXID());				//制单机构ID
            params.put("orgName", userBean.getJGMC());				//制单机构名称
            mapper.insert(params);
        } else {
        	params.put("depositId", depositId);
        	params.put("updator", userBean.getXTYHID());		//更新人ID
            params.put("updName", userBean.getXM());			//更新人名称
            params.put("updTime", "sysdate");
            txUpdateById(params);
        }
        
      //保存附件
      		String path = model.getAttPath();
      		InterUtil.delByFromId(depositId);//先删除 后添加
      		if(!StringUtil.isEmpty(path)){
      			List<String> pathList = new ArrayList<String>(Arrays.asList(path.split(",")));
      			InterUtil.insertAttPath(pathList, userBean, depositId);
      		}
	}
	
    /**
     * @param params the params
     */
	@Transactional
    public void txUpdateById(Map <String, String> params) {
		mapper.updateById(params); 
    }
	
	@Transactional
	public void audit(Map<String,String> params){
//		mapper.updateById(params);
		if(BusinessConsts.PASS.equals(params.get("state"))){
			params.put("processFlag", BusinessConsts.YJLBJ_FLAG_TRUE);
			service.rechargeAccount(params.get("depositId"), params.get("auditId"));
		}
		mapper.updateById(params);
	}
	
}
