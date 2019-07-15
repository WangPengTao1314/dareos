/**
  *@module 系统管理
  *@fuc 数据字典数据处理接口
  *@version 1.1
  *@author 张羽
*/

package com.centit.sys.service;

import java.util.List;
import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.sys.model.SJZDModel;
import com.centit.sys.model.SJZDMxModel;
import com.centit.sys.model.UserBean;

// TODO: Auto-generated Javadoc
/**
 * 数据字典接口.
 * 
 * @author zhang_yu
 */
public interface SJZDService  {

    /**
     * 主表分页查询
     * Description :.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    public void pageQuery(Map <String, Object> params,PageDesc pageDesc);


    /**
     * 查询主表单条记录详细信息
     * Description :.
     * 
     * @param sjzdId the sjzd id
     * 
     * @return the map< string, string>
     */
    public Map <String, String> load(String sjzdId);


    /**
     * 单条记录删除
     * Description :.
     * 
     * @param sjzdId the sjzd id
     * @param userBean the user bean
     * 
     * @return true, if tx delete
     */
    public boolean txDelete(String sjzdId, UserBean userBean);


    /**
     * 主表及子表编辑 新增/修改。
     * Description :.
     * 
     * @param sjzdId the sjzd id
     * @param sjzdModel the sjzd model
     * @param sjzdmxList the sjzdmx list
     * @param userBean the user bean
     */
    public void txEdit(String sjzdId, UserBean userBean, SJZDModel sjzdModel, List <SJZDMxModel> sjzdmxList);


    /**
     * 根据主表Id查询子表记录
     * Description :.
     * 
     * @param sjzdID the sjzd id
     * 
     * @return the list< sjzd mx model>
     */
    public List <SJZDMxModel> childQuery(String sjzdID);


    /**
     * 根据子表Id批量加载子表信息
     * Description :.
     * 
     * @param sjzdMxIds the sjzd mx ids
     * 
     * @return the list< sjzd mx model>
     */
    public List <SJZDMxModel> loadChilds(String sjzdMxIds);


    /**
     * 子表记录编辑：新增/修改
     * Description :.
     * 
     * @param sjzdid the sjzdid
     * @param modelList the model list
     * @param sjzdModel the sjzd model
     * 
     * @return true, if tx child edit
     */
    public boolean txChildEdit(String sjzdid, List <SJZDMxModel> modelList, SJZDModel sjzdModel);


    /**
     * 子表的批量删除
     * Description :.
     * 
     * @param sjzdmxids the sjzdmxids
     * @param userBean the user bean
     */
    public void txBatch4DeleteChild(String sjzdmxids, UserBean userBean);


    /**
     * Tx update by id.
     * 
     * @param params the params
     * 
     * @return true, if successful
     */
    public boolean txUpdateById(Map <String, String> params);


    /**
     * Gets the id count.
     * 
     * @param sjzdID the sjzd id
     * 
     * @return the id count
     */
    public int getIdCount(String sjzdID);
    
    public List <SJZDMxModel> getMXBySJZDID(Map <String, String> paramsMx);
    
    List<Map<String,String>> checkSJZDMXBySJXZ(Map<String,String> params);
    
    List<Map<String,String>> checkSJZDMXById(Map<String,String> params);
    
    void insertSJZDMXByService(Map<String,String> params);
    
    void updateSJZDMXByService(Map<String,String> params);
    
}
