package com.centit.base.areachrg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import com.centit.base.area.mapper.AreaMapper;
import com.centit.base.areachrg.mapper.AreaChrgMapper;
import com.centit.base.areachrg.model.AreaChrgModel;
import com.centit.base.areachrg.service.AreaChrgService;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class AreaChrgServiceImpl implements AreaChrgService {
	@Autowired
	AreaChrgMapper mapper;
	@Autowired
	AreaMapper areaMapper;

	/**
     * 查询并分页.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    public void pageQuery(Map <String, Object> params,PageDesc pageDesc) {
        Page<Map<String,String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
    }
    
    
    /**
     * 根据ID加载详细信息.
     * 
     * @param param the param
     * 
     * @return the map< string, string>
     */
	public Map <String, String> loadById(String param){
    	return mapper.loadById(param);
    }
    
    
    /**
     * 根据区域ID加载 区域分管明细.
     * 
     * @param areaID  区域ID
     * 
     * @return  区域分管明细
     */
    
	public List<AreaChrgModel> childQuery(String areaID){
    	 Map<String,String> params = new HashMap<String,String>();
         params.put("AREA_ID", areaID);
         return mapper.query( params);
    }
    
 
    
    /**
     * 根据子表Id批量加载子表信息
     * Description :.
     * 
     * @param IDs  字表IDS
     * 
     * @return  
     */
	public List<AreaChrgModel> loadChilds(String IDs){
    	   return mapper.loadByIds( IDs);
    }
	
    
    /**
     * 子表记录编辑：新增/修改
     * Description :.
     * 
     * @param areaId 区域信息ID
     * @param  modelList 区域分管明细
     * 
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
        	Map <String, String> params = new HashMap <String, String>();
        	params.put("AREA_ID", areaId);
        	params.put("CHRG_TYPE", model.getCHRG_TYPE());
        	params.put("CHRG_NO", model.getCHRG_NO());
        	params.put("CHRG_NAME", model.getCHRG_NAME());
        	params.put("JOB", model.getJOB());
        	
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
        	mapper.insert(addList);
        }
        if(!updateList.isEmpty()){
        	mapper.updateById(updateList);
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
		areaMapper.deleteareaflat(CHRGIDS);
		areaMapper.insertareaflat(datas);
    }
    
    
    /**
     * 子表的批量删除 软删除
     * Description :.
     * 
     * @param  areaChrgIDs 明细IDS
     * @param  
     */
    public void txBatch4DeleteChild(String AREA_ID,String areaChrgIDs,String CHRG_IDS, UserBean userBean){
    	Map<String,String> param = new HashMap<String,String>();
    	param.put("AREA_ID", AREA_ID);
    	param.put("CHRG_IDS", CHRG_IDS);
    	//删除区域扁平表的相关信息
    	areaMapper.deleteAreaflatByAreaID(param);
    	mapper.updateDELByIds(areaChrgIDs);
    }
    
    
	public List<AreaChrgModel> findList(Map<String,String> paramMap){
		return mapper.findRepeat(paramMap);
    }
    

}
