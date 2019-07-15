/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：JGXXServiceImpl.java
 */
package com.centit.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.DateUtil;
import com.centit.commons.util.ResourceUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.mapper.JGXXMapper;
import com.centit.sys.model.JGXXModel;
import com.centit.sys.model.JGXXTree;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.JGXXService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class JGXXServiceImpl.
 * 
 * @module 系统管理
 * @func 机构信息
 * @version 1.1
 * @author 蔡瑾钊
 */
@Service
public class JGXXServiceImpl implements JGXXService {
	@Autowired
	private JGXXMapper mapper;
    /**
     * 查询并分页.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    public void pageQuery(Map <String, Object> params, PageDesc pageDesc) {
    	Page<Map<String, String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
        
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
        //insert("JGXX.insert", params);
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
     * @param jgxxId the jgxx id
     * @param userBean the user bean
     * 
     * @return true, if tx delete
     */
    @Transactional
    public boolean txDelete(String jgxxId, UserBean userBean) {

        Map <String, String> params = new HashMap <String, String>();
        params.put("JGXXID", jgxxId);
        params.put("DATARECYCLEID", StringUtil.uuid32len());
        params.put("DELETOR", userBean.getYHM());
        //insert("JGXX.insertDelDate", params);
        mapper.insertDelDate(params);
        //return delete("JGXX.delete", jgxxId);
        
        mapper.delete(jgxxId);
        return true;
    }

    /**
     * 软删除.
     * 
     * @param jgxxId the jgxx id
     * @param userBean the user bean
     * @return true, if tx delete
     */
    @Transactional
    public void txNewDelete(String jgxxId, UserBean userBean) {
        Map <String, String> params = new HashMap <String, String>();
        params.put("JGXXID", jgxxId);
        params.put("UPDATER", userBean.getXTYHID());
        params.put("DELFLAG", BusinessConsts.DEL_FLAG_DROP);
        //update("JGXX.updateStateById", params);
        mapper.updateStateById(params);
        //this.clearCaches(jgxxId);
    }

    /**
     * 修改状态为停用
     * Description :.
     * 
     * @param params the params
     * 
     * @return true, if tx stop by id
     */
    public boolean txStopById(Map <String, String> params) {

        //update("JGXX.stopRyById", params);
        //update("JGXX.stopBmById", params);
        //return update("JGXX.stopById", params);
        mapper.stopById(params);
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
    	
        //update("JGXX.stopRyById", params);
        //update("JGXX.stopBmById", params);
        //return update("JGXX.startById", params);
        mapper.startById(params);
        return true;
    }


    /**
     * 加载.
     * 
     * @param jgxxId the jgxx id
     * 
     * @return the map
     */
    @SuppressWarnings("unchecked")
    public Map load(String jgxxId) {
    	
		return (Map) mapper.loadById(jgxxId);
    }


    /**
     * 加载.
     * 
     * @param SJJG the sJJG
     * 
     * @return true, if load jgzt
     */
    @SuppressWarnings("unchecked")
    public boolean loadJGZT(String SJJG) {
    	
        if ("停用".equals(mapper.loadJGZT(SJJG))) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 判断是否存在重复机构.
     * 
     * @param params the params
     * 
     * @return true, if query for int
     */
    public boolean queryForInt(Map <String, String> params) {
        if (0 == mapper.queryBhForInt(params)) {
            return true;
        } else
            return false;
    }


    /**
     * 判断是否存在下级机构.
     * 
     * @param params the params
     * 
     * @return true, if query sj for int
     */
    public boolean querySjForInt(Map <String, String> params) {

        if (0 == mapper.querySjForInt(params)) {
        	
            return true;
        } else {
            return false;
        }
    }


    /**
     * 编辑.
     * 
     * @param jgxxId the jgxx id
     * @param jgxxModel the jgxx model
     * @param userBean the user bean
     * 
     * @return the string
     */
    @Override
    public String txEdit(String jgxxId, JGXXModel jgxxModel, UserBean userBean) {

        Map <String, String> params = new HashMap <String, String>();
        params.put("DZXXID", jgxxModel.getDZXXID());
        params.put("ZTXXID", jgxxModel.getZTXXID());
        params.put("JGBH", jgxxModel.getJGBH());
        params.put("JGMC", jgxxModel.getJGMC());
        params.put("JGJC", jgxxModel.getJGJC());
        params.put("FRDB", jgxxModel.getFRDB());
        params.put("DH", jgxxModel.getDH());
        params.put("CZ", jgxxModel.getCZ());
        params.put("DZYJ", jgxxModel.getDZYJ());
        params.put("ZYDZ", jgxxModel.getZYDZ());
        params.put("SJJG", jgxxModel.getSJJG());
//        modify by zzb 2014-07-03
//        if("".equals(jgxxModel.getSJJG()))
//        {
//        	params.put("SJJG", null);
//        }	
        params.put("QTSM", jgxxModel.getQTSM());
        params.put("XXDZ", jgxxModel.getXXDZ());
        params.put("YZBM", jgxxModel.getYZBM());
        params.put("JGYWMC", jgxxModel.getJGYWMC());
        params.put("FGCWRYID", jgxxModel.getFGCWRYID());
        params.put("FGCWRYXM", jgxxModel.getFGCWRYXM());
        params.put("JGYWJC", jgxxModel.getJGYWJC());
        params.put("JGYWXXDZ", jgxxModel.getJGYWXXDZ());
        params.put("XH", String.valueOf(jgxxModel.getXH()));
        params.put("UPDATER", userBean.getXTYHID());
        params.put("UPDTIME", DateUtil.now());

        if (StringUtils.isEmpty(jgxxId)) {
            //jgxxId = StringUtil.uuid32len();
            params.put("JGXXID", jgxxModel.getJGBH());
            jgxxId=jgxxModel.getJGBH();
            params.put("JGZT", "制定");
            params.put("CREATER", userBean.getXTYHID());
            params.put("CRENAME", userBean.getXM());
            params.put("NFYF", DateUtil.nfyf());
            txInsert(params);
        } else {
            params.put("JGXXID", jgxxId);
            txUpdateById(params);
            
        }
        return jgxxId;
    }


    /**
     * 树.
     * 
     * @return the tree
     * 
     * @throws Exception the exception
     */
    public List <JGXXTree> getTree() throws Exception {

        List <JGXXTree> menus = mapper.queryTree("");
        return ResourceUtil.getZTreeFromList(menus, JGXXTree.class);
    }

}
