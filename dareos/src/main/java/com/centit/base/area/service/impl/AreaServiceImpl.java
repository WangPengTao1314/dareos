package com.centit.base.area.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import com.centit.base.area.mapper.AreaMapper;
import com.centit.base.area.model.AreaModel;
import com.centit.base.area.model.AreaTree;
import com.centit.base.area.service.AreaService;
import com.centit.base.areachrg.mapper.AreaChrgMapper;
import com.centit.base.areachrg.model.AreaChrgModel;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.ResourceUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
/**
 * 区域信息
 * @author zhang_zhongbin
 *
 */
/**
 * 区域信息
 * @author zhang_zhongbin
 *
 */
@Service
public class AreaServiceImpl  implements AreaService {
	@Autowired
	AreaMapper mapper;
	@Autowired
	AreaChrgMapper areaChrgMapper;
	
	   /**
     * 查询并分页.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    public void pageQuery(Map <String, Object> params, PageDesc pageDesc) {
        Page<Map<String,String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
    }

    /**
     * 树.
     * 
     * @return the tree
     * 
     * @throws Exception the exception
     */
	public List <AreaTree> getTree() throws Exception {
        List <AreaTree> menus = mapper.queryTree();
        return ResourceUtil.getZTreeFromList(menus, AreaTree.class);
    }
    
    
    /**
     * 加载.
     * 
     * @param areaId the 区域Id
     * 
     * @return the map
     */
    public Map<String,String> load(String areaId) {

        return mapper.loadById(areaId);
    }
    
    
    /**
     * 判断是否存在重复机构.
     * 
     * @param params the params
     * 
     * @return true, 无重复
     */
    public boolean queryForInt(Map <String, String> params) {

        if (0 == mapper.queryBhForInt(params)) {
            return true;
        } else
            return false;
    }
    
    /**
     * 状态
     * 启用->true
     * 
     * @param SJJG the sJJG
     * 
     * @return true, if load jgzt
     */
    public boolean loadAreaState(String AREA_ID_P){
    	 if (BusinessConsts.JC_STATE_DISENABLE.equals(mapper.loadAreaState(AREA_ID_P))) {
             return false;
         } else {
             return true;
         }
    }
    
    /**
     * 编辑.
     * 
     * @param areaId the 区域id
     * @param model the 区域实体 model
     * @param userBean the user bean
     * @param modelList 字表
     * @return the string
     */
    @Transient
    public String txEdit(AreaModel model, UserBean userBean,List <AreaChrgModel> modelList) {
        Map <String, String> params = new HashMap <String, String>();
        params.put("AREA_NO", model.getAREA_NO());
        params.put("AREA_NAME", model.getAREA_NAME());
        params.put("AREA_ID_P", model.getAREA_ID_P());
        params.put("AREA_NO_P", model.getAREA_NO_P());
        params.put("AREA_NAME_P", model.getAREA_NAME_P());
        params.put("LEDGER_ID", userBean.getLoginZTXXID());
        params.put("LEDGER_NAME", userBean.getLoginZTMC());
        
        String areaId = model.getAREA_ID();
        if (StringUtils.isEmpty(areaId)) {
        	areaId = model.getAREA_NO();
        	params.put("AREA_ID", areaId);
        	params.put("CREATOR", userBean.getXTYHID());
            params.put("CRE_NAME", userBean.getXM());
            params.put("DEPT_ID", userBean.getBMXXID());
            params.put("DEPT_NAME", userBean.getBMMC());
            params.put("ORG_ID", userBean.getJGXXID());
            params.put("ORG_NAME", userBean.getJGMC());
            params.put("STATE", BusinessConsts.JC_STATE_DEFAULT);// 新增的时候状态是制定
            params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);// 新增的时候删除标记是0
            txInsert(params);
        } else {
            params.put("AREA_ID", areaId);
            params.put("UPDATOR", userBean.getXTYHID());
            params.put("UPD_NAME", userBean.getYHM());
            
            txUpdateById(params);
        }
        
        if(null != modelList && !modelList.isEmpty()){
        	 txChildEdit(areaId,modelList);
        }
       
        return areaId;
    }

    
    /**
     * 子表记录编辑：新增/修改
     * Description :.
     * @param areaId 区域信息ID
     * @param  modelList 区域分管明细
     * @return true, if tx child edit
     */
    @Transient
    public boolean txChildEdit(String areaId, List <AreaChrgModel> modelList){
    	//新增列表
        List <Map <String, String>> addList = new ArrayList <Map <String, String>>();
        //修改列表
        List <Map <String, String>> updateList = new ArrayList <Map <String, String>>();
        //根据分管编号 查询
        List <Map <String, String>> updateAreaFlatList = new ArrayList <Map <String, String>>();
        int size = modelList.size();
        int i=0;
        StringBuffer bf = new StringBuffer();
        for(AreaChrgModel model : modelList){
        	i++;
        	Map <String, String> params = new HashMap <String, String>();
        	params.put("AREA_ID", areaId);
        	params.put("CHRG_TYPE", model.getCHRG_TYPE());
        	params.put("CHRG_NO", model.getCHRG_NO());
        	params.put("CHRG_NAME", model.getCHRG_NAME());
        	params.put("JOB", model.getJOB());
        	params.put("CHRG_ID", model.getCHRG_ID());
        	
        	//如果没有区域分管ID的则是新增，有的是修改
            if (StringUtils.isEmpty(model.getAREA_CHRG_ID())) {
            	params.put("AREA_CHRG_ID", StringUtil.uuid32len());
            	params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
            	addList.add(params);
            }else{
            	params.put("AREA_CHRG_ID", model.getAREA_CHRG_ID());
            	updateList.add(params);
            }
            
            if(!StringUtil.isEmpty(model.getCHRG_ID())){
            	Map <String, String> p = new HashMap <String, String>();
            	p.put("CHRG_NO", model.getCHRG_NO());
            	p.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
            	updateAreaFlatList.add(p);
            	
            	bf.append("'" + model.getCHRG_ID() + "'");
                 if(i != size){
                 	bf.append(",");
                 }
            }
           
        }
        
        if(!addList.isEmpty()){
        	areaChrgMapper.insert(addList);
        }
        if(!updateList.isEmpty()){
        	areaChrgMapper.updateById(updateList);
        }
        
        //区域分管 更新的时候 更新   区域分管扁平表
        updateAreaFlat(updateAreaFlatList,bf.toString());
        
        
        	
        return true;
            
    }
    
    /**
     * 区域分管扁平表
     * 先根据“分管对象ID” 删除所有的信息
     * 在根据“分管对象ID” 查询与之相关的所有信息再插入
     * @param datas 区域
     * @param CHRGIDS 分管对象ID 存用户ID
     */
	private void updateAreaFlat(List<Map<String,String>> datas,String CHRGIDS){
        mapper.deleteareaflat(CHRGIDS);
        mapper.insertareaflat(datas);
    }
    /**
     * 增加记录 Description :.
     * 
     * @param params the params
     * 
     * @return true, if tx insert
     */
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
    public boolean txUpdateById(Map <String, String> params) {
    	mapper.updateById(params);
        return true;
    }
    
    /**
     * 软删除.
     * 同时更新 区域扁平表
     * @param areaId the 区域 id
     * @param userBean the user bean
     * 
     * @return true, if tx delete
     */
    public boolean txDelete(String areaId, UserBean userBean) {

        Map <String, String> params = new HashMap <String, String>();
        params.put("AREA_ID", areaId);
        params.put("DEL_FLAG", "1");
        params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getYHM());
        try{
        	mapper.updateStateById(params);
        	areaChrgMapper.updateDELByAreaId(areaId);
        	//删除区域扁平表的相关信息
        	mapper.deleteAreaflatByAreaID(params);
        }catch(Exception e){
        	return false;
        }
        return true;
    }
    
    
//    /**
//     * 子表的批量删除 软删除
//     * Description :.
//     * 
//     * @param  areaChrgIDs 明细IDS
//     * @param  
//     */
//    public void txBatch4DeleteChild(String areaChrgIDs, UserBean userBean){
//        update("AreaChrg.updateDELByIds", areaChrgIDs);
//    }
    
    
    
    /**
     * 修改状态为停用
     * Description :.
     * 
     * @param params the params
     * 
     * @return true, if tx stop by id
     */
    public boolean txStopById(Map <String, String> params) {
    	mapper.updateStateById(params);
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
    public boolean txStartById(Map <String, String> params) {
    	mapper.updateStateById(params);
        return true;
    }

}
