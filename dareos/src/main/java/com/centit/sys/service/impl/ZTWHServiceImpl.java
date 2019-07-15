/**

* 项目名称：平台

* 系统名：财务基础数据

* 文件名：ZTWHServiceImpl.java
*/
package com.centit.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.commons.util.ResourceUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.mapper.ZTXXMapper;
import com.centit.sys.model.ZTWHModel;
import com.centit.sys.model.ZTWHTree;
import com.centit.sys.service.ZTWHService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * The Class ZTWHServiceImpl.
 * 
 * @module 财务管理
 * @fuc 帐套信息维护
 */
@Service
public class ZTWHServiceImpl implements ZTWHService {
	
	@Autowired
	private ZTXXMapper mapper;

    /**
     * 帐套维护列表信息.
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
     * 
     */
    public void pageQuery1(Map<String, Object> params, PageDesc pageDesc) {
    	Page<Map<String,String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery1(params);
    	LogicUtil.transPageHelper(pageDesc, p);
    }


    /**
     * 
     */
    public Map<String, Object> getOneRecord(String ztxxID) {
        return mapper.getOneRecord(ztxxID);
    }


    public Map<String, Object> getOne(String ztxxID) {
        return mapper.getOne(ztxxID);
    }


    /**
     * 更新.
     * 
     * @param params the params
     * 
     * @return true, if update by id
     */
    public void updateById(Map<String, Object> params) {

    	mapper.updateById(params);
    }


    /**
     * 插入.
     * 
     * @param params the params
     * 
     */
    public void insertRecord(Map <String, Object> params) {

        mapper.insertRecord(params);
    }


    /**
     * 得到所有帐套编号.
     * 
     * @return the BH list
     */
    public List <ZTWHModel> getAllBH() {
        return mapper.getAllBH();
    }


    public void updateZTStatus(Map <String, Object> params) {

    	mapper.updateZTStatus(params);
    }


    public void deleteById(Map <String, Object> params) {

    	mapper.delete(params);
    }


    /**
     * 树.
     * 
     * @return the tree
     * 
     * @throws Exception the exception
     */
    public List <ZTWHTree> queryTree() throws Exception {
        List <ZTWHTree> menus = mapper.queryTree();
        return ResourceUtil.getZTreeFromList(menus, ZTWHTree.class);
    }


    public int getCountRecord(String ztxxid) {

        return mapper.getCountRecord( ztxxid);
    }
    
    /**
     * 
     */
    public boolean querySjForInt(Map<String,Object> params){
    	if (0 == mapper.querySjForInt(params)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     */
    public boolean loadZTZT(String SJZT) {
        if ("停用".equals(mapper.loadZTZT(SJZT))) {
            return false;
        } else {
            return true;
        }
    }
    
}
