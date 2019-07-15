/**  
 * @module 系统管理
 * @func 分销渠道信息
 * @version 1.0
 * @author gu_hongxiu
 */
package com.centit.base.distributor.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.base.chann.mapper.ChannMapper;
import com.centit.base.distributor.mapper.DistributorMapper;
import com.centit.base.distributor.model.DistributorModel;
import com.centit.base.distributor.service.DistributorService;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.DateUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class DistributorServiceImpl  implements DistributorService {

	@Autowired
	private DistributorMapper mapper;
	@Autowired
	private ChannMapper cmapper;
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
		Page<Map<String, String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
		mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> load(String param) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
        params.put("DISTRIBUTOR_ID", param);
        return mapper.loadById(params);
	}

	@Override
	public boolean loadSTATE(String CHANN_ID) {
		// TODO Auto-generated method stub
		if ("停用".equals(cmapper.loadSTATE(CHANN_ID))) {
            return false;
        } else {
            return true;
        }
	}
	 /**
     * 编辑.
     * 
     * @param CHANN_ID the chann id
     * @param ChannpunishModel the chann model
     * @param userBean the user bean
     * 
     * @return the string
     */
	@SuppressWarnings("unchecked")
	@Override
	public String txEdit(String DISTRIBUTOR_ID, DistributorModel model, UserBean userBean) {
		// TODO Auto-generated method stub
		Map <String, String> params = new HashMap <String, String>();
		
		params.put("DISTRIBUTOR_NAME", model.getDISTRIBUTOR_NAME());//分销商名称
		params.put("DISTRIBUTOR_TYPE", model.getDISTRIBUTOR_TYPE());//分销商类型
		params.put("TEL", model.getTEL());//公司电话
		
		params.put("CHANN_ID", model.getCHANN_ID());//渠道ID
        params.put("CHANN_NO", model.getCHANN_NO());//渠道编号
        params.put("CHANN_NAME", model.getCHANN_NAME());//渠道名称       
        params.put("AREA_MANAGE_ID", model.getAREA_MANAGE_ID());//区域经理ID
        params.put("AREA_MANAGE_NAME", model.getAREA_MANAGE_NAME());//区域经理名称
        params.put("AREA_MANAGE_TEL", model.getAREA_MANAGE_TEL());//区域经理电话      
        params.put("AREA_ID", model.getAREA_ID());//区域id
        params.put("AREA_NO", model.getAREA_NO());//区域编号
        params.put("AREA_NAME", model.getAREA_NAME());//区域名称
        params.put("ZONE_ID", model.getZONE_ID());//行政区划id
        params.put("NATION", model.getNATION());//国家
        params.put("PROV", model.getPROV());//省份
        params.put("CITY", model.getCITY());//城市
        params.put("COUNTY", model.getCOUNTY());//区县
        
        params.put("ADDRESS", model.getADDRESS());//详细地址
        params.put("TAX", model.getTAX());//传真
        params.put("EMAIL", model.getEMAIL());//电子邮件
        params.put("PERSON_CON", model.getPERSON_CON());//联系人
        params.put("MOBILE", model.getMOBILE());//手机
        params.put("SALE_STORE_NAME", model.getSALE_STORE_NAME());//商场名称
        params.put("SALE_STORE_LOCAL", model.getSALE_STORE_LOCAL());//商场位置
        params.put("BUSS_BRAND", model.getBUSS_BRAND());//经营品牌
        params.put("BUSS_CLASS", model.getBUSS_CLASS());//主营分类        
        params.put("COOPER_DATE", model.getCOOPER_DATE());//合作日期
      //params.put("REMARK", model.getREMARK());//备注
        
        if (StringUtils.isEmpty(DISTRIBUTOR_ID)) {
        	DISTRIBUTOR_ID = StringUtil.uuid32len();
        	params.put("DISTRIBUTOR_ID", DISTRIBUTOR_ID);
        	//params.put("DISTRIBUTOR_NO",LogicUtil.getBmgz("BAS;E_DISTRIBUTOR_NO"));
        	String DISTRIBUTOR_NO = queryMaxNo();
        	if(DISTRIBUTOR_NO == null){
        		DISTRIBUTOR_NO = "1300001";
        	}else{
        		DISTRIBUTOR_NO = String.valueOf(Integer.parseInt(DISTRIBUTOR_NO)+1);
        	}
        	params.put("DISTRIBUTOR_NO",DISTRIBUTOR_NO);
        	params.put("CREATOR", userBean.getXTYHID());//制单人ID
            params.put("CRE_NAME", userBean.getXM());//制单人名称
            params.put("CRE_TIME", DateUtil.now());//制单时间
            params.put("DEPT_ID", userBean.getBMXXID());//制单部门ID
            params.put("DEPT_NAME", userBean.getBMMC());//制单部门名称
            params.put("ORG_ID", userBean.getJGXXID());//制单机构id
            params.put("ORG_NAME", userBean.getJGMC());//制单机构名称
            params.put("LEDGER_ID", userBean.getLoginZTXXID());//帐套ID
            params.put("LEDGER_NAME", userBean.getLoginZTMC());//帐套名称
        	params.put("STATE", BusinessConsts.JC_STATE_DEFAULT);//状态
        	params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);//删除标记        	
            txInsert(params);
        } else { 
        	params.put("DISTRIBUTOR_ID", DISTRIBUTOR_ID);
            params.put("UPDATOR", userBean.getXTYHID());//更新人id
            params.put("UPD_NAME", userBean.getXM());//更新人名称
            params.put("UPD_TIME", DateUtil.now());//更新时间
            txUpdateById(params);
			/* clearCaches(DISTRIBUTOR_ID); */
        }
        return DISTRIBUTOR_ID;
	}
	   /**
     * 增加记录 Description :.
     * 
     * @param params the params
     * 
     * @return true, if tx insert
     */
	@Transactional
    public boolean txInsert(Map <String, String> params) {
        
        mapper.insert(params);
        return true;
    }
    /**
     * 更新记录 Description :.
     * 
     * @param params the params
     * 
     * @return true, if tx update by id
     */
	@Transactional
    public boolean txUpdateById(Map <String, String> params) {    	
        mapper.updateById(params);
        return true;
    }
    
    /**
     * 软删除.
     * 
     * @param CHANN_ID the chann id
     * @param userBean the user bean
     * 
     * @return true, if tx delete
     */
	@Transactional
	public boolean txDelete(Map<String,String> map) {
		 mapper.delete(map);
		 return true;
	}
	
	/**
     * 修改状态为启用
     * Description :.
     * 
     * @param params the params
     * 
     * @return true, if tx start by id
     */
	@Transactional
    public boolean txStartById(Map <String, String> params) {    	        
        mapper.updateStateById(params);
        return true;
    }

	/**
     * 修改状态为停用
     * Description :.
     * 
     * @param params the params
     * 
     * @return true, if tx stop by id
     */
	@Transactional
    public boolean txStopById(Map <String, String> params) {        
        mapper.updateStateById(params);
        return true;
    }
    
    //按渠道id查找渠道状态
    public String getSTATE(String CHANN_ID){
    	return cmapper.getSTATE(CHANN_ID).toString();
    }
    
    /**
     * 查询导出数据
     */
    public  List <Map> qryExp(Map params){
        return mapper.qryExp(params);
    }
    
	public  String  queryMaxNo(){
		 Map<String, String> params = new HashMap<String, String>();
		 String str =  mapper.queryMaxNo(params);
				/* (String)load("distributor.queryMaxNo", params); */
		 return str;
	}
}
