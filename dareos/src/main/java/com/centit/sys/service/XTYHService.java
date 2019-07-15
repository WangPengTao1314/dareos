/**

* 项目名称：平台

* 系统名：基础数据

* 文件名：XTYHService.java
*/
package com.centit.sys.service;

import java.util.List;
import java.util.Map;

import com.centit.base.product.model.ProductUserModel;
import com.centit.core.po.PageDesc;
import com.centit.sys.model.UserBean;
import com.centit.sys.model.XTYHBean;
import com.centit.sys.model.XtyhLedgerChrgModel;

// TODO: Auto-generated Javadoc
/**
 * The Interface XTYHService.
 * 
 * @module 系统管理
 * @fuc 系统用户
 * @version 1.1
 * @author 唐赟
 */
public interface XTYHService{

    /**
     * 查询并分页.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
    public void pageQuery(Map <String, Object> params, PageDesc paeDesc);


    /**
     * 插入系统用户数据.
     * 
     * @param params the params
     * 
     * @return boolean
     */
    public boolean insert(Map <String, String> params);


    /**
     * 更新系统用户数据.
     * 
     * @param params the params
     * 
     * @return boolean
     */
    public boolean updateById(Map <String, String> params);


    /**
     * 删除系统用户数据.
     * 
     * @param param the param
     * 
     * @return boolean
     * public boolean delete(Map <String, String> params);
     */

    /**
     * 加载
     * 
     * @param params
     * @return
     */
    public Map <String, String> load(String param);




    /**
     * 更新用户状态.
     * 
     * @param params the params
     */
    public void updateUserStatus(Map <String, Object> params);


    /**
     * 更新用户密码.
     * 
     * @param params the params
     */
    public void updatePassword(Map <String, Object> params);


    /**
     * 获得所有用户编号列表.
     * 
     * @return the list< xtyh bean>
     */
    public List <XTYHBean> selectYhbh();


    /**
     * 系统授权.
     * 
     * @param xtyhid the xtyhid
     * 
     * @return the list< xtsq model>
     */
    public List <Map<String,String>> childQuery(String xtyhid);


    /**
     * 取用户的机构分管信息.
     * 
     * @param xtyhid the xtyhid
     * 
     * @return the list< map< string, string>>
     */
    public List <Map <String, String>> queryJgfgMxByXtyhid(String xtyhid);


    /**
     * 取用户的部门分管信息.
     * 
     * @param xtyhid the xtyhid
     * 
     * @return the list< map< string, string>>
     */
    public List <Map <String, String>> queryBmfgMxByXtyhid(String xtyhid);


    /**
     * 取用户的帐套分管信息.
     * 
     * @param xtyhid the xtyhid
     * 
     * @return the list< map< string, string>>
     */
    public List <Map <String, String>> queryZtfgMxByXtyhid(String xtyhid);


    /**
     * 插入机构分管明细表.
     * 
     * @param arrJgxxid the arr jgxxid
     * @param xtyhid the xtyhid
     * 
     * @return true, if tx insert jgfg mx
     */
    public boolean txInsertJgfgMx(String[] arrJgxxid, String xtyhid);


    /**
     * 插入部门分管明细表.
     * 
     * @param arrJgxxid the arr jgxxid
     * @param xtyhid the xtyhid
     * 
     * @return true, if tx insert bmfg mx
     */
    public boolean txInsertBmfgMx(String[] arrJgxxid, String xtyhid);


    /**
     * 插入帐套分管明细表.
     * 
     * @param arrJgxxid the arr jgxxid
     * @param xtyhid the xtyhid
     * 
     * @return true, if tx insert ztfg mx
     */
    public boolean txInsertZtfgMx(String[] arrJgxxid, String xtyhid);


    /**
     * 删除机构分管明细表.
     * 
     * @param ids the ids
     * 
     * @return true, if tx delete jgfg mx
     */
    public boolean txDeleteJgfgMx(String ids);


    /**
     * 删除部门分管明细表.
     * 
     * @param ids the ids
     * 
     * @return true, if tx delete bmfg mx
     */
    public boolean txDeleteBmfgMx(String ids);


    /**
     * 删除帐套分管明细表.
     * 
     * @param ids the ids
     * 
     * @return true, if tx delete ztfg mx
     */
    public boolean txDeleteZtfgMx(String ids);


    /**
     * 得到用户编号.
     * 
     * @return the yhbh
     */
    public String getYhbh();


    /**
     * Txdelete.
     * 
     * @param xtyhID the xtyh id
     * @param userBean the user bean
     * 
     * @return true, if successful
     */
    public boolean txdelete(Map<String,String> map);
    
    
    /**
     * 得到人员信息ID.
     * 
     * @return the ryxxid
     */
    public String getRyxxid(String XTYHID);
    
    /**
     * 删除明细.
     * 
     * @param xtyhid the xtyhid
     * 
     * @return boolean
     */
    public boolean deleteMX(Map <String, String> params);
    
    /**
     * 插入系统用户数据.
     * 
     * @param params the params
     * 
     * @return boolean
     */
    public boolean insertMX(Map <String, String> params);
    
    public List<ProductUserModel> loadUserChargByIDS(String USER_CHARG_PRD_IDS);
    public void txBatch4DeleteUser(String USER_CHARG_PRD_IDS);
    public int checkPRD(String PRD_IDS,String XTYHID);
    public void txChldEdit(List <ProductUserModel> modelList,String XTYHID);
    public List<ProductUserModel> userQueryById(String XTYHID);
    
    /**
     * 设置用户分管所有渠道
     * @param params
     */
    public void txUpdateUserChrgChann(Map<String, String> params);
    public void txDeleteUserChrgChann(Map<String, String> params);
    
    /**
     * 用户列表
     * @param params
     * @return
     */
    public List<Map<String, String>> queryUserList(Map<String,Object>params);
    /**
     * 编辑上下级关系
     * @param params
     */
    public void txEditStepUser(Map<String, String> params);
    /**
     * 删除上下级关系
     * @param params
     */
    public void txDeleteStepUser(Map<String, String> params);
    
    public void updateMxInfo(Map<String,String> params);
    
    List<Map<String,String>> getLedgerChrgList(String XTYHID);
    void delLedChrgByLedIds(String XTYH_LEDGER_IDS,UserBean userBean);
    void insertLegerChrg(String XTYHID,List<XtyhLedgerChrgModel> list,UserBean userBean);
    List<Map<String,String>> getLedgerChrgListByIds(String XTYH_LEDGER_IDS);
}
