﻿/**
 * prjName:喜临门营销平台
 * ucName:推广促销方案维护
 * fileName:PrmtplanServiceImpl
*/
package com.centit.erp.sale.prmtplan.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.erp.sale.prmtplan.mapper.PrmtplanMapper;
import com.centit.erp.sale.prmtplan.model.PrmtplaAreaModel;
import com.centit.erp.sale.prmtplan.model.PrmtplanModel;
import com.centit.erp.sale.prmtplan.model.PrmtplanModelChld;
import com.centit.erp.sale.prmtplan.service.PrmtplanService;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
/**
 * *@serviceImpl
 * *@func
 * *@version 1.1
 * *@author zzb
 * *@createdate 2013-08-23 16:04:28
 */
@Service
public class PrmtplanServiceImpl implements PrmtplanService {
	@Autowired
	private PrmtplanMapper mapper;
    /**
	 * @查询
	 * @param params map
	 * @param pageNo 
	 *
	 * @return the ilistpage
	 */
	public void pageQuery(Map<String,Object> params, PageDesc pageDesc) {
		Page<Map<String,String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}
	/**
	 * * 增加
	 * * @param params 
	 * 
	 * @return true, if tx insert
	 */
	public boolean txInsert(Map<String,String> params) {
		mapper.insert(params);
		return true;
	}
	/**
	 * @删除
	 * @param PRMT_PLAN_ID
	 * 
	 * @return true, if tx delete
	 */
	@Transactional
	public boolean txDelete(Map <String, String> params) {
	     //删除父
		mapper.delete(params);
         mapper.delChldByFkId(params);
		 //删除子表 
		 return true;
	}
	
	/**
	 * * update data
	 * * @param params
	 * 
	 * 
	 * @return true, if tx update by id
	 */
	public boolean txUpdateById(Map<String,String> params) {
		mapper.updateById(params);
		return true;
	}
	
	/**
	 * @编辑
	 * @Description :
	 * @param PRMT_PLAN_ID
	 * @param PrmtplanModel
	 * @param userBean.
	 *
	 * @return true, if tx txEdit 
	 */
	public void txEdit(String PRMT_PLAN_ID, PrmtplanModel model,List<PrmtplanModelChld> chldList, UserBean userBean) {
		Map<String,String> params = new HashMap<String,String>();
		    params.put("PRMT_PLAN_NAME",model.getPRMT_PLAN_NAME());
		    params.put("EFFCT_DATE_END",model.getEFFCT_DATE_END());
		   
		    params.put("EFFCT_DATE_BEG",model.getEFFCT_DATE_BEG());
		    params.put("PRMT_TYPE",model.getPRMT_TYPE());
		    params.put("REMARK",model.getREMARK());
		if(StringUtil.isEmpty(PRMT_PLAN_ID)){
			PRMT_PLAN_ID= StringUtil.uuid32len();
			String no = LogicUtil.getBmgz("PRMT_PLAN_NO_SEQ");
			params.put("PRMT_PLAN_ID", PRMT_PLAN_ID);
			params.put("PRMT_PLAN_NO",no);
		    params.put("CRE_NAME",userBean.getXM());
		    params.put("CREATOR",userBean.getXTYHID());
		    params.put("UPD_NAME",userBean.getXM());
		    params.put("UPDATOR",userBean.getXTYHID());
		    params.put("DEPT_ID",userBean.getBMXXID());
		    params.put("DEPT_NAME",userBean.getBMMC());
		    params.put("ORG_ID",userBean.getJGXXID());
		    params.put("ORG_NAME",userBean.getJGMC());
		    params.put("LEDGER_ID",userBean.getLoginZTXXID());
		    params.put("LEDGER_NAME",userBean.getLoginZTMC());
		    params.put("STATE",BusinessConsts.JC_STATE_DEFAULT);
		    params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);
		txInsert(params);
		} else{
		    params.put("UPD_NAME",userBean.getXM());
		    params.put("UPDATOR",userBean.getXTYHID());
		    params.put("UPD_TIME","sysdate");
			params.put("PRMT_PLAN_ID", PRMT_PLAN_ID);
			txUpdateById(params);
		}
		//子表信息保存
	    if (null != chldList && !chldList.isEmpty()) {
			    txChildEdit(PRMT_PLAN_ID, chldList);
		}
	}
	
	/**
	 * @详细
	 * @param param PRMT_PLAN_ID
	 * @param param the param
	 * 
	 * @return the map< string, string>
	 */
	public Map<String,String> load(String param) {
		return mapper.loadById(param);
	}
	
	/**
     * * 明细数据编辑
     * 
     * @param PRMT_PLAN_ID the PRMT_PLAN_ID
     * @param modelList the model list
     * 
     * @return true, if tx child edit
     */
    @Override
    public boolean txChildEdit(String PRMT_PLAN_ID, List<PrmtplanModelChld> chldList) {

        //新增列表
        List <Map <String, String>> addList = new ArrayList <Map <String, String>>();
        //修改列表
        List <Map <String, String>> updateList = new ArrayList <Map <String, String>>();
        for (PrmtplanModelChld model : chldList) {
            Map <String, String> params = new HashMap <String, String>();
            params.put("PRMT_PLAN_ID",PRMT_PLAN_ID); 
            params.put("PRD_GROUP_ID",model.getPRD_GROUP_ID());
            params.put("PRD_GROUP_NO",model.getPRD_GROUP_NO());
            params.put("PRD_GROUP_NAME",model.getPRD_GROUP_NAME());
            
            //如果没有明细ID的则是新增，有的是修改
            if (StringUtil.isEmpty(model.getPRMT_PRD_GROUP_ID())) {
                params.put("PRMT_PRD_GROUP_ID", StringUtil.uuid32len());
		        params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);
                addList.add(params);
            } else {
                params.put("PRMT_PRD_GROUP_ID", model.getPRMT_PRD_GROUP_ID());
                updateList.add(params);
            }
        }

        if (!updateList.isEmpty()) {
        	mapper.updateChldById(updateList);
        }
        if (!addList.isEmpty()) {
        	mapper.insertChld(addList);
        }
        return true;
    }
	
	/**
     * * 根据主表Id查询子表记录
     * @param PRMT_PLAN_ID the PRMT_PLAN_ID
     * 
     * @return the list< new master slavemx model>
     */
    @Override
    @SuppressWarnings("unchecked")
    public List <PrmtplanModelChld> childQuery(String PRMT_PLAN_ID) {
        Map<String,String> params = new HashMap<String,String>();
        params.put("PRMT_PLAN_ID", PRMT_PLAN_ID);
        //只查询0的记录。1为删除。0为正常
		params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);
        return mapper.queryChldByFkId(params);
    }

    /**
     * * 根据子表Id批量加载子表信息
     * @param PRMT_PRD_GROUP_IDs the PRMT_PRD_GROUP_IDs 
     * 
     * @return the list< new master slavemx model>
     */
    @Override
    public List <PrmtplanModelChld> loadChilds(Map <String, String> params) {
       return mapper.loadChldByIds(params);
    }
	
    /**
     * * 子表批量删除:软删除
     * 
     * @param PRMT_PRD_GROUP_IDs the PRMT_PRD_GROUP_IDs
     */
    @Override
    public void txBatch4DeleteChild(String PRMT_PRD_GROUP_IDs) {
	   Map<String,String> params = new HashMap<String,String>();
	   params.put("PRMT_PRD_GROUP_IDS", PRMT_PRD_GROUP_IDs);
	   params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_DROP);
       mapper.deleteChldByIds(params);
    }
    
    /**
     * 发布
     * 更新状态  以及相关信息
     */
    public void updateIssuance(String id,String state,UserBean userBean){
    	 Map<String,String> params = new HashMap<String,String>();
  	     params.put("PRMT_PLAN_ID", id);
  	     params.put("STATE", state);
  	     params.put("UPDATOR", userBean.getXM());
  	     params.put("UPD_NAME", userBean.getXTYHID());
  	     params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
  	   
    	 mapper.updateById(params);
    }
    
    
    
	 /**
    * * 根据主表Id查询子表记录  生效区域
    * @param PRMT_PLAN_ID the PRMT_PLAN_ID
    * @return the list 生效区域
    */
   public List <PrmtplaAreaModel> areaQuery(String PRMT_PLAN_ID){
	   Map<String,String> params = new HashMap<String,String>();
       params.put("PRMT_PLAN_ID", PRMT_PLAN_ID);
       //只查询0的记录。1为删除。0为正常
		params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);
       return mapper.queryAreaByFkId(params);
   }
   
   /**
    * * 子表的批量删除 生效区域
    * @param PRMT_EFFCT_AREA_IDS the PRMT_EFFCT_AREA_IDS
    */
   public void txBatch4DeleteArea(String PRMT_EFFCT_AREA_IDS){
	   Map<String,String> params = new HashMap<String,String>();
	   params.put("PRMT_EFFCT_AREA_IDS", PRMT_EFFCT_AREA_IDS);
	   params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_DROP);
       mapper.deleteAreaByIds(params);
   }
   
   /**
    * * 根据子表Id批量加载子表信息 生效区域
    * @param PRMT_PRD_GROUP_IDs the PRMT_PRD_GROUP_IDs
    * 
    * @return the list
    */
   public List <PrmtplaAreaModel> loadAreas(Map <String, String> params){
	   return mapper.loadAreaByIds(params);
   }
	
	 /**
    * * 子表记录编辑：新增/修改 生效区域
    * @param PRMT_PLAN_ID the PRMT_PLAN_ID
    * @param modelList the model list
    * 
    * @return true, if tx child edit
    */
   public boolean txAreaEdit(String PRMT_PLAN_ID, List <PrmtplaAreaModel> modelList){
	   //新增列表
       List <Map <String, String>> addList = new ArrayList <Map <String, String>>();
       //修改列表
       List <Map <String, String>> updateList = new ArrayList <Map <String, String>>();
       for (PrmtplaAreaModel model : modelList) {
           Map <String, String> params = new HashMap <String, String>();
           params.put("PRMT_PLAN_ID",PRMT_PLAN_ID); 
           params.put("AREA_ID",model.getAREA_ID());
           params.put("AREA_NO",model.getAREA_NO());
           params.put("AREA_NAME",model.getAREA_NAME());
           
           //如果没有明细ID的则是新增，有的是修改
           if (StringUtil.isEmpty(model.getPRMT_EFFCT_AREA_ID())) {
               params.put("PRMT_EFFCT_AREA_ID", StringUtil.uuid32len());
		       params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);
               addList.add(params);
           } else {
               params.put("PRMT_EFFCT_AREA_ID", model.getPRMT_EFFCT_AREA_ID());
               updateList.add(params);
           }
       }

       if (!updateList.isEmpty()) {
          mapper.updateAreaById(updateList);
       }
       if (!addList.isEmpty()) {
           mapper.insertArea(addList);
       }
       return true;
   }
   
   /**
    * 
    * @param params
    * @return
    */
   public List<Map<String,String>> findAreaList(Map <String, String> params){
	   return mapper.findAreaByPid(params);
   }
   
}