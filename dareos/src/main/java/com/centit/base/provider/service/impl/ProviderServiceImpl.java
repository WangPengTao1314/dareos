/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ChannServiceImpl.java
 */
package com.centit.base.provider.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.base.provider.mapper.ProviderMapper;
import com.centit.base.provider.model.ProviderModel;
import com.centit.base.provider.service.ProviderService;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @module 系统管理
 * @func 供应商信息 
 * @version 1.0
 */
@Service
public class ProviderServiceImpl  implements ProviderService {
	
	@Autowired
	private ProviderMapper mapper;

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
		Page<Map<String,String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	public Map<String, Object> loadById(String param) {
		return mapper.loadById(param);
	}

    /**
     * 软删除.
     * 
     * @param userBean the user bean
     * 
     */
    @Transactional
	public void delete(String prvd_id, UserBean userBean) {
    	Map <String, Object> params = new HashMap <String, Object>();
    	params.put("PRVD_ID", prvd_id);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_DROP);
        mapper.delete(params);
	}

	@Override
	public void txEdit(String prvd_id, ProviderModel providerModel, UserBean userBean) {
		Map <String, Object> params = new HashMap <String, Object>();
        params.put("PRVD_NO", providerModel.getPRVD_NO());
        params.put("PRVD_NAME", providerModel.getPRVD_NAME());
        params.put("PRVD_TYPE", providerModel.getPRVD_TYPE());
        params.put("PRVD_LVL", providerModel.getPRVD_LVL());
        params.put("PRVD_NATRUE", providerModel.getPRVD_NATRUE());
        params.put("NATION", providerModel.getNATION());
        params.put("PROV", providerModel.getPROV());
        params.put("CITY", providerModel.getCITY());
        params.put("COUNTY", providerModel.getCOUNTY());
        params.put("PRVD_CY", providerModel.getPRVD_CY());
        params.put("PRVD_CAP", providerModel.getPRVD_CAP());
        params.put("PERSON_BUSS", providerModel.getPERSON_BUSS());
        params.put("PERSON_CON", providerModel.getPERSON_CON());
        params.put("TEL", providerModel.getTEL());
        params.put("MOBILE_PHONE", providerModel.getMOBILE_PHONE());
        params.put("TAX", providerModel.getTAX());
        params.put("POST", providerModel.getPOST());
        params.put("EMAIL", providerModel.getEMAIL());
        params.put("WEB", providerModel.getWEB());
        params.put("LEGAL_REPRE", providerModel.getLEGAL_REPRE());
        params.put("BUSS_LIC", providerModel.getBUSS_LIC());
        params.put("INVOICE_TI", providerModel.getINVOICE_TI());
        params.put("INVOICE_ADDR", providerModel.getINVOICE_ADDR());
        params.put("OPENING_BANK", providerModel.getOPENING_BANK());
        params.put("BANK_ACCT", providerModel.getBANK_ACCT());
        params.put("FI_CMP_NO", providerModel.getFI_CMP_NO());
        params.put("ADDRESS", providerModel.getADDRESS());
        params.put("REMARK", providerModel.getREMARK());
        params.put("DEFAULT_FLAG", providerModel.getDEFAULT_FLAG());
        params.put("UPDATOR", userBean.getXTYHID());
	    params.put("UPD_NAME", userBean.getXM()); 
        if (StringUtils.isEmpty(prvd_id)) {
        	prvd_id = StringUtil.uuid32len();
            params.put("PRVD_ID", prvd_id);
            params.put("STATE", BusinessConsts.JC_STATE_DEFAULT);
            params.put("CREATOR", userBean.getXTYHID());
            params.put("CRE_NAME", userBean.getXM());
            params.put("UPDATOR", userBean.getXTYHID());
            params.put("UPD_NAME", userBean.getXM());
            params.put("DEPT_ID", userBean.getBMBH());        
            params.put("DEPT_NAME", userBean.getBMMC());
            params.put("ORG_ID", userBean.getJGBH());
            params.put("ORG_NAME", userBean.getJGMC());
            params.put("LEDGER_ID", userBean.getLoginZTXXID());
            params.put("LEDGER_NAME", userBean.getLoginZTMC());
            txInsert(params);
        } else {
        	params.put("PRVD_ID", prvd_id);
            txUpdateById(params);
        }
        /*if(checkDEFAULT_FLAG()>1){
        	throw new ServiceException("对不起，已有选择过的默认供应商，不能多选!");
        }*/
	}
	
	/**
     * 增加记录 
     * 
     */
	@Transactional(readOnly=false)
    public void txInsert(Map <String, Object> params) {
    	mapper.insert(params);
    }
	
    /**
     * @param params the params
     */
	@Transactional
    public void txUpdateById(Map <String, Object> params) {
		mapper.updateById(params);        
    }
	
}
