/**

* 项目名称：平台

* 系统名：财务基础数据

* 文件名：ZTWHService.java
*/
package com.centit.sys.service;

import java.util.List;
import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.sys.model.ZTWHModel;
import com.centit.sys.model.ZTWHTree;

/**
 * The Interface ZTWHService.
 * 
 * @module 财务管理
 * @fuc 帐套信息维护
 */
public interface ZTWHService {

    /**
     * 帐套列表信息.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    public void pageQuery(Map <String, Object> params, PageDesc pageDesc);


    /**
     * 点击树时获得的列表.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    public void pageQuery1(Map <String, Object> params, PageDesc pageDesc);


    /**
     * 获得一条数据，根据ID.
     * 
     * @param ztxxID the ztxx id
     * 
     */
    public Map <String, Object> getOneRecord(String ztxxID);

    /**
     * 获得一条数据，根据ID.
     * 
     * @param ztxxID the ztxx id
     * 
     * @return the map< string, string>
     */
  public Map <String, Object> getOne(String ztxxID);

    /**
     * 更新.
     * 
     * @param params the params
     * 
     * @return true, if update by id
     */
    public void updateById(Map <String, Object> params);


    /**
     * 插入.
     * 
     * @param params the params
     * 
     * @return true, if insert record
     */
    public void insertRecord(Map <String, Object> params);


    /**
     * 得到所有帐套编号.
     * 
     * @return the BH list
     */
    public List <ZTWHModel> getAllBH();


    /**
     * 更新状态.
     * 
     * @param params the params
     */
    public void updateZTStatus(Map <String, Object> params);


    /**
     * 删除.
     * 
     * @param ztxxid the ztxxid
     */
    public void deleteById(Map <String, Object> ztxxid);


    /**
     * 得到树信息.
     * 
     * @return the tree
     * 
     * @throws Exception the exception
     */
    public List <ZTWHTree> queryTree() throws Exception;


    /**
     * 获得上级帐套有几条.
     * 
     * @param ztxxid the ztxxid
     * 
     * @return the count record
     */
    public int getCountRecord(String ztxxid);
    
    /**
     * Query sj for int.
     * 
     * @param params the params
     * 
     * @return true, if successful
     */
    public boolean querySjForInt(Map<String,Object> params);
    
    /**
     * 加载.
     * 
     * @param SJZT the sJZT
     * 
     * @return true, if load jgzt
     */
    public boolean loadZTZT(String SJZT);
}
