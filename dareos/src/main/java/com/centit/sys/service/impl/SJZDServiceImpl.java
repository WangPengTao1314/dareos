/**
  *@module 系统管理
  *@fuc 数据字典数据处理实现
  *@version 1.1
  *@author 张羽
*/

package com.centit.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.DateUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.mapper.SJZDMXMapper;
import com.centit.sys.mapper.SJZDMapper;
import com.centit.sys.model.SJZDModel;
import com.centit.sys.model.SJZDMxModel;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.SJZDService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

// TODO: Auto-generated Javadoc
/**
 * 数据字典实现类.
 * 
 * @author zhang_yu
 */
@Service
public class SJZDServiceImpl  implements SJZDService {
	@Autowired
	private SJZDMapper mapper;
	@Autowired
	private SJZDMXMapper sjzdmxMapper;

    /**
     * 分页查询
     * Description :.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    @Override
    public void pageQuery(Map <String, Object> params, PageDesc pageDesc) {
        Page<Map<String,String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
    }


    /**
     * 查询详细信息
     * Description :.
     * 
     * @param sjzdId the sjzd id
     * 
     * @return the map< string, string>
     */
    @Override
    public Map <String, String> load(String sjzdId) {

        return mapper.loadById(sjzdId);
    }


    /**
     * 单条记录删除
     * Description :.
     * 
     * @param sjzdId the sjzd id
     * @param userBean the user bean
     * 
     * @return true, if tx delete
     */
    @Override
    @Transactional
    public boolean txDelete(String sjzdId, UserBean userBean) {

        Map <String, String> paramsq = new HashMap <String, String>();
        paramsq.put("SJZDID", sjzdId);
        paramsq.put("DATARECYCLEID", StringUtil.uuid32len());
        paramsq.put("DELETOR", userBean.getYHM());
        mapper.insertSJZD(paramsq);
        paramsq.put("DATARECYCLEID", StringUtil.uuid32len());
        mapper.insertSJZDMX(paramsq);
        mapper.deletemx(sjzdId);
        mapper.delete(sjzdId);
        return true;
    }


    /**
     * 主表及子表编辑 新增/修改。
     * Description :.
     * 
     * @param sjzdId the sjzd id
     * @param sjzdmxList the sjzdmx list
     * @param userBean the user bean
     * @param sjzdModel the sjzd model
     */
    @Override
    @Transactional
    public void txEdit(String sjzdId, UserBean userBean, SJZDModel sjzdModel, List <SJZDMxModel> sjzdmxList) {

        Map <String, String> params = new HashMap <String, String>();
        params.put("SJZDBH", sjzdModel.getSJZDBH());
        params.put("SJZDMC", sjzdModel.getSJZDMC());
        params.put("REMARK", sjzdModel.getREMARK());
        params.put("UPDATER", userBean.getXTYHID());
        params.put("UPDTIME", DateUtil.now());

        //如果lxllId为空，说明是新增记录
        if (StringUtils.isEmpty(sjzdId)) {
            sjzdId = sjzdModel.getSJZDBH();
            params.put("SJZDID", sjzdId);
            params.put("CREATER", userBean.getXTYHID());
            params.put("CRENAME", userBean.getXM());
            params.put("STATE", BusinessConsts.JC_STATE_DEFAULT);
            insert(params);
        } else {
            params.put("SJZDID", sjzdId);
            sjzdModel.setSJZDID(sjzdId);
            updateById(params);
        }
        //子表信息编辑
        if (null != sjzdmxList && !sjzdmxList.isEmpty()) {
            txChildEdit(sjzdId, sjzdmxList, sjzdModel);
        }
    }


    /**
     * 根据主表Id查询子表记录
     * Description :.
     * 
     * @param sjzdId the sjzd id
     * 
     * @return the list< sjzd mx model>
     */
    @Override
    public List <SJZDMxModel> childQuery(String sjzdId) {

        Map<String,String> params = new HashMap<String,String>();
        params.put("SJZDID", sjzdId);
        params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
        return sjzdmxMapper.query(params);
    }


    /**
     * 根据子表Id批量加载子表信息
     * Description :.
     * 
     * @param sjzdMxIds the sjzd mx ids
     * 
     * @return the list< sjzd mx model>
     */
    @Override
    public List <SJZDMxModel> loadChilds(String sjzdMxIds) {
        return sjzdmxMapper.loadByIds( sjzdMxIds);
    }


    /**
     * 数据字典明细页面编辑。.
     * 
     * @param sjzdId the sjzd id
     * @param modelList the model list
     * @param sjzdModel the sjzd model
     * 
     * @return true, if tx child edit
     */
    @Override
    @Transactional
    public boolean txChildEdit(String sjzdId, List <SJZDMxModel> modelList, SJZDModel sjzdModel) {

        //新增列表
        List <Map <String, String>> addList = new ArrayList <Map <String, String>>();
        //修改列表
        List <Map <String, String>> updateList = new ArrayList <Map <String, String>>();
        for (SJZDMxModel model : modelList) {
            Map <String, String> params = new HashMap <String, String>();
            params.put("SJZDID", sjzdId);
            params.put("SJXMC", model.getSJXMC());
            params.put("SJSJZDMXID", "");
            params.put("SJXZ", model.getSJXZ());
            params.put("SJGL", model.getSJGL());
            params.put("KEYCODE", model.getKEYCODE());
            params.put("REMARK", model.getREMARK());//KEYCODE,SJZDMXID,SJZDID,SJXMC,SJXZ,SJSJZDMXID,SJGL,REMARK,CRETIME,UPDTIME,DELFLAG
            //如果没有领料明细ID的则是新增，有的是修改
            if (StringUtils.isEmpty(model.getSJZDMXID())) {
                params.put("SJZDMXID", StringUtil.uuid32len());
                addList.add(params);
            } else {

                params.put("SJZDMXID", model.getSJZDMXID());
                updateList.add(params);
            }
        }

        if (!updateList.isEmpty()) {
        	sjzdmxMapper.updateById(updateList);
        }
        if (!addList.isEmpty()) {
        	sjzdmxMapper.insert(addList);

        }
        return true;
    }


    /**
     * 批量删除.
     * 
     * @param sjzdmxids the sjzdmxids
     * @param userBean the user bean
     */
    @Override
    @Transactional
    public void txBatch4DeleteChild(String sjzdmxids, UserBean userBean) {

        Map <String, String> params = new HashMap <String, String>();
        params.put("sjzdmxids", sjzdmxids);
        params.put("DATARECYCLEID", StringUtil.uuid32len());
        params.put("DELETOR", userBean.getYHM());
        sjzdmxMapper.insertSJZDMX(params);
        sjzdmxMapper.deleteByIds(sjzdmxids);
        
    }


    /**
     * Insert.
     * 
     * @param params the params
     * 
     * @return true, if successful
     */
    public boolean insert(Map <String, String> params) {
    	mapper.insert(params);
        return true;
    }


    /**
     * Update by id.
     * 
     * @param params the params
     * 
     * @return true, if successful
     */
    public boolean updateById(Map <String, String> params) {
    	mapper.updateById(params);
        return true;
    }


    public boolean txUpdateById(Map <String, String> params) {
    	mapper.updateState(params);
        return true;
    }


    /**
     * 校验是否有重复编号.
     * 
     * @param sjzdID the sjzd id
     * 
     * @return the id count
     */
    public int getIdCount(String SJZDBH) {
    	return mapper.getIdCount(SJZDBH);
    }
    
    public List<SJZDMxModel> getMXBySJZDID(Map <String, String> paramsMx){
    	return sjzdmxMapper.getMXBySJZDID(paramsMx);
    }
    
    public List<Map<String,String>> checkSJZDMXBySJXZ(Map<String,String> params){
    	return mapper.checkSJZDMXBySJXZ(params);
    }
    public List<Map<String,String>> checkSJZDMXById(Map<String,String> params){
    	return mapper.checkSJZDMXById(params);
    }
    public void insertSJZDMXByService(Map<String,String> params){
    	mapper.insertSJZDMXByService(params);
    }
    
    public void updateSJZDMXByService(Map<String,String> params){
    	mapper.updateSJZDMXByService(params);
    }
}
