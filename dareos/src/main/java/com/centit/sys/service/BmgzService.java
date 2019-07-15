/**
 *@module 基础资料
 *@func 编码规则维护
 *@version 1.1
 *@author 孙炜
 */
package com.centit.sys.service;

import java.util.List;
import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.sys.model.BmgzModel;
import com.centit.sys.model.BmgzMxModel;
import com.centit.sys.model.UserBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface BmgzService.
 */
public interface BmgzService  {

    /**
     * 主表分页查询
     * Description :.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    public void  pageQuery(Map <String, Object> params, PageDesc pageDesc);


    /**
     * 查询主表单条记录详细信息
     * Description :.
     * 
     * @param bmgzid the bmgzid
     * 
     * @return the map< string, string>
     */
    public Map <String, String> load(String bmgzid);


    /**
     * 单条记录删除
     * Description :.
     * 
     * @param bmgzid the bmgzid
     * @param userBean the user bean
     * 
     * @return true, if tx delete
     */
    public boolean txDelete(String bmgzid, UserBean userBean);


    /**
     * 主表及子表编辑 新增/修改。
     * Description :.
     * 
     * @param bmgzid the bmgzid
     * @param userBean the user bean
     * @param bmgzModel the bmgz model
     * @param bmgzMxList the bmgz mx list
     */
    public void txEdit(String bmgzid, UserBean userBean, BmgzModel bmgzModel, List <BmgzMxModel> bmgzMxList);
    

    /**
     * 根据主表Id查询子表记录
     * Description :.
     * 
     * @param bmgzid the bmgzid
     * 
     * @return the list< bmgz mx model>
     */
    public List <BmgzMxModel> childQuery(String bmgzid);


    /**
     * 根据子表Id批量加载子表信息
     * Description :.
     * 
     * @param bmgzMxIds the bmgz mx ids
     * 
     * @return the list< bmgz mx model>
     */
    public List <BmgzMxModel> loadChilds(String bmgzMxIds);


    /**
     * 子表记录编辑：新增/修改
     * Description :.
     * 
     * @param bmgzid the bmgzid
     * @param modelList the model list
     * @param userBean the user bean
     * 
     * @return true, if tx child edit
     */
    public boolean txChildEdit(String bmgzid, List <BmgzMxModel> modelList, UserBean userBean);


    /**
     * 子表的批量删除
     * Description :.
     * 
     * @param bmgzMxIds the bmgz mx ids
     * @param bmgzId the bmgz id
     * @param userBean the user bean
     */
    public void txBatch4DeleteChild(String bmgzMxIds, String bmgzId, UserBean userBean);


    /**
     * 状态修改.
     * 
     * @param params the params
     */
    public void updateState(Map <String, String> params);


    /**
     * 新增判断，编号不能重复.
     * 
     * @param bmbh the bmbh
     * 
     * @return the count by bh
     */
    public int getCountByBH(String bmbh);


    /**
     * 删除主表之前,首先检查子表是否有数据.
     * 
     * @param bmgzid the bmgzid
     * 
     * @return the int
     */
    public int deleteCheck(String bmgzid);


    /**
     * 取得SEQUENCE.
     * 
     * @return the seq
     */
    public List getSeq();


    /**
     * 获取总长度.
     * 
     * @param bmgzid the bmgzid
     * 
     * @return int
     */
    public int getZcd(String bmgzid);
    
    public List<BmgzModel> getAll();
    /**
     * 编码规则
     * @param map
     * @return
     */
    List<Map<String,String>> queryMaxBillNo(Map<String,Object> map);
    /**
     * /**
	 * 查询编码规则明细.
	 * 
	 * @param bmdx the bmdx
	 * 
	 * @return List
	 */
    public List<Map<String, String>> selectBmgzMx(String bmdx);
    /**
	 * 查询Sequence.
	 * 
	 * @param sequencemc String
	 * 
	 * @return String
	 */
    public String selectSequence(String sequencemc);
    
    
    
}
