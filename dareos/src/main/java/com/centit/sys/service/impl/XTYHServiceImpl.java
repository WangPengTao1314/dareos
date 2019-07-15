/**

 * 项目名称：平台

 * 系统名：基础数据

 * 文件名：XTYHServiceImpl.java
 */
package com.centit.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.base.product.model.ProductUserModel;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.mapper.XTSQMapper;
import com.centit.sys.mapper.XTYHMapper;
import com.centit.sys.model.UserBean;
import com.centit.sys.model.XTYHBean;
import com.centit.sys.model.XtyhLedgerChrgModel;
import com.centit.sys.service.XTYHService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class XTYHServiceImpl.
 * 
 * @module 系统管理
 * @fuc 系统用户
 * @version 1.1
 * @author 唐赟
 */
@Service
public class XTYHServiceImpl  implements XTYHService {
	@Autowired
	private XTYHMapper mapper;
	
	@Autowired
	private XTSQMapper xtsqMapper;

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

    @Transactional
    public boolean insert(Map <String, String> params) {
    	mapper.insert(params);
        //插入机构分管明细表
    	mapper.insertJgfgMx(params);
        //插入部门分管明细表
    	mapper.insertBmfgMx(params);
        //插入帐套分管明细表
    	mapper.insertZtfgMx(params);
        return true;
    }


    public boolean updateById(Map <String, String> params) {
    	mapper.updateById(params);
        return true;
    }

    @Transactional
    public boolean txdelete(Map<String,String> map) {
    	xtsqMapper.delete(map.get("XTYHID"));
        mapper.insertXTYH(map);
        mapper.updateOne(map.get("XTYHID"));
        this.deleteMX(map);
        return true;
    }


    /**
     * 加载.
     * 
     * @param param the param
     * 
     * @return the map< string, string>
     */
    public Map <String, String> load(String param) {
    	return mapper.loadById(param);
    }


    public Map <String, String> query(Map <String, String> params) {

        return mapper.queryy(params);
    }


    public void updateUserStatus(Map <String, Object> params) {
    	mapper.updateUserStatusById(params);
    }


    public void updatePassword(Map <String, Object> params) {
    	mapper.updatePassword(params);
    }


    public List <XTYHBean> selectYhbh() {
    	return mapper.selectAllYhbh();
    }


    public List <Map<String,String>> childQuery(String xtyhid) {
    	
    	return xtsqMapper.loadById(xtyhid);
    }


    /**
     * 取用户的机构分管信息.
     * 
     * @param xtyhid the xtyhid
     * 
     * @return the list< map< string, string>>
     */
    public List <Map <String, String>> queryJgfgMxByXtyhid(String xtyhid) {
    	return mapper.queryJgfgMxByXtyhid(xtyhid);
    }


    /**
     * 取用户的机构分管信息.
     * 
     * @param xtyhid the xtyhid
     * 
     * @return the list< map< string, string>>
     */
    public List <Map <String, String>> queryBmfgMxByXtyhid(String xtyhid) {
    	return mapper.queryBmfgMxByXtyhid(xtyhid);
    }


    /**
     * 取用户的机构分管信息.
     * 
     * @param xtyhid the xtyhid
     * 
     * @return the list< map< string, string>>
     */
    public List <Map <String, String>> queryZtfgMxByXtyhid(String xtyhid) {

    	return mapper.queryZtfgMxByXtyhid(xtyhid);
    }


    /**
     * 插入机构分管明细表.
     * 
     * @param arrJgxxid the arr jgxxid
     * @param xtyhid the xtyhid
     * 
     * @return true, if tx insert jgfg mx
     */
    public boolean txInsertJgfgMx(String[] arrJgxxid, String xtyhid) {

        Map <String, String> map = new HashMap<String, String>();
        for (int i = 0; i < arrJgxxid.length; i++) {
            map.put("XTYHJGFGID", StringUtil.uuid32len());
            map.put("XTYHID", xtyhid);
            map.put("JGXXID", arrJgxxid[i]);
            mapper.insertJgfgMx(map);
        }
        return true;
    }


    /**
     * 插入部门分管明细表.
     * 
     * @param arrBmxxid the arr bmxxid
     * @param xtyhid the xtyhid
     * 
     * @return true, if tx insert bmfg mx
     */
    public boolean txInsertBmfgMx(String[] arrBmxxid, String xtyhid) {

        Map <String, String> map = new HashMap();
        for (int i = 0; i < arrBmxxid.length; i++) {
            map.put("XTYHBMFGID", StringUtil.uuid32len());
            map.put("XTYHID", xtyhid);
            map.put("BMXXID", arrBmxxid[i]);
            mapper.insertBmfgMx(map);
        }
        return true;
    }


    /**
     * 插入帐套分管明细表.
     * 
     * @param arrZtxxid the arr ztxxid
     * @param xtyhid the xtyhid
     * 
     * @return true, if tx insert ztfg mx
     */
    public boolean txInsertZtfgMx(String[] arrZtxxid, String xtyhid) {

        Map <String, String> map = new HashMap();
        for (int i = 0; i < arrZtxxid.length; i++) {
            map.put("XTYHZTDZID", StringUtil.uuid32len());
            map.put("XTYHID", xtyhid);
            map.put("ZTXXID", arrZtxxid[i]);
            mapper.insertZtfgMx(map);
        }
        return true;
    }


    /**
     * 删除机构分管明细表.
     * 
     * @param ids the ids
     * 
     * @return true, if tx delete jgfg mx
     */
    public boolean txDeleteJgfgMx(String ids) {
    	mapper.deleteJgfgMx(ids);
        return true;
    }


    /**
     * 删除部门分管明细表.
     * 
     * @param ids the ids
     * 
     * @return true, if tx delete bmfg mx
     */
    public boolean txDeleteBmfgMx(String ids) {
    	mapper.deleteBmfgMx(ids);
        return true;
    }


    /**
     * 删除帐套分管明细表.
     * 
     * @param ids the ids
     * 
     * @return true, if tx delete ztfg mx
     */
    public boolean txDeleteZtfgMx(String ids) {
    	mapper.deleteZtfgMx(ids);
        return true;
    }


    /**
     * 得到用户编号.
     * 
     * @return the yhbh
     */
    public String getYhbh() {

        return mapper.getXTYHBH();
    }
    
    
     /**
      * 得到人员信息ID.
      * 
      * @return the ryxxid
      */
     public String getRyxxid(String XTYHID) {
    	 return mapper.getRyxxidByXtyhid(XTYHID);
     }
     @Transactional
     public void updateMxInfo(Map<String,String> params){
    	 this.updateById(params);
    	 this.deleteMX(params);
    	 this.insertMX(params);
     }
     
     @Transactional
     public boolean deleteMX(Map <String, String> params) {
    	 
         //删除机构分管明细表
         mapper.deleteJgfg(params);
         //删除部门分管明细表
         mapper.deleteBmfg(params);
         //删除帐套分管明细表
         mapper.deleteZtfg(params);
         return true;
     }
     
     @Transactional
     public boolean insertMX(Map <String, String> params) {

       //插入机构分管明细表
     	mapper.insertJgfgMx(params);
         //插入部门分管明细表
     	mapper.insertBmfgMx(params);
         //插入帐套分管明细表
     	mapper.insertZtfgMx(params);
         return true;
     }
     
     
     public List <ProductUserModel> loadUserChargByIDS(String USER_CHARG_PRD_IDS){
    	 return mapper.loadUserByIDS(USER_CHARG_PRD_IDS);
     }
     public void txBatch4DeleteUser(String USER_CHARG_PRD_IDS){
    	 mapper.delUserByIDS(USER_CHARG_PRD_IDS);
     }
     public int checkPRD(String PRD_IDS,String XTYHID){
     	Map<String,String> map=new HashMap<String, String>();
     	map.put("PRD_IDS", PRD_IDS);
     	map.put("XTYHID", XTYHID);
     	Integer count=mapper.checkPRD(map);
     	if(null==count){
     		count=0;
     	}
     	return StringUtil.getInteger(count);
     }
     
     @Transactional
     public void txChldEdit(List <ProductUserModel> modelList,String XTYHID){
     	List<Map<String,String>> addList=new ArrayList<Map<String,String>>();
     	List<Map<String,String>> upList=new ArrayList<Map<String,String>>();
     	for (int i = 0; i < modelList.size(); i++) {
     		ProductUserModel model=modelList.get(i);
     		Map<String,String> map=new HashMap<String, String>();
     		map.put("XTYHID", XTYHID);
     		map.put("PRD_ID", model.getPRD_ID());
     		if(StringUtil.isEmpty(model.getUSER_CHARG_PRD_ID())){
     			map.put("USER_CHARG_PRD_ID", StringUtil.uuid32len());
     			addList.add(map);
     		}else{
     			map.put("USER_CHARG_PRD_ID", model.getUSER_CHARG_PRD_ID());
     			upList.add(map);
     		}
 		}
     	if(!addList.isEmpty()){
     		mapper.addCharg(addList);
     	}
     	if(!upList.isEmpty()){
     		mapper.upCharg(upList);
     	}
     }
     public List<ProductUserModel> userQueryById(String XTYHID){
    	 return mapper.queryChld(XTYHID);
     }
     
     /**
      * 设置用户分管所有渠道
      * @param params
      */
     @Transactional
     public void txUpdateUserChrgChann(Map<String, String> params){
    	 mapper.upUserChannCharg(params);
    	 params.put("IS_FG_ALL_CHANN", BusinessConsts.INTEGER_1);
    	 mapper.upXtyhChargFlag(params);
     }
     /**
      * 设置用户分管所有渠道
      * @param params
      */
     @Transactional
     public void txDeleteUserChrgChann(Map<String, String> params){
    	 mapper.delUserChannCharg(params);
    	 params.put("IS_FG_ALL_CHANN", BusinessConsts.INTEGER_0);
    	 mapper.upXtyhChargFlag(params);
     }
     
     /**
      * 用户列表
      * @param params
      * @return
      */
     public List<Map<String, String>> queryUserList(Map<String,Object>params){
    	 return mapper.queryUserList(params);
     }
     
     /**
      * 编辑上下级关系
      * @param params
      */
     @Transactional
     public void txEditStepUser(Map<String, String> params){
    	 String XTYHIDS = params.get("XTYHIDS");
    	 String[] arry = XTYHIDS.split(",");
    	 List<Map<String,String>> addList = new ArrayList<Map<String,String>>();
    	 //先删除之前分配的上下级关系
    	 XTYHIDS = "'"+XTYHIDS.replace(",", "','")+"'";
    	 params.put("USER_IDS", XTYHIDS);
    	 mapper.deleteStepUser(XTYHIDS);
    	 
    	 for(int i=0;i<arry.length;i++){
    		 Map<String, String> model = new HashMap<String,String>(); 
    		 model.put("USER_REL_ID", StringUtil.uuid32len());
    		 model.putAll(params);
    		 model.put("USER_ID", arry[i]);
    		 addList.add(model);
    	 }
    	 
    	 //在新增
    	 if(!addList.isEmpty()){
     		mapper.insertStepUser(addList);
     	 }
    	
     }
     
     
     /**
      * 删除上下级关系
      * @param params
      */
     public void txDeleteStepUser(Map<String, String> params){
    	 //先删除之前分配的上下级关系
    	 String XTYHIDS = params.get("XTYHIDS");
    	 XTYHIDS = "'"+XTYHIDS.replace(",", "','")+"'";
    	 params.put("USER_IDS", XTYHIDS);
    	 mapper.deleteStepUser(XTYHIDS);
     }

     
     public List<Map<String,String>> getLedgerChrgList(String XTYHID){
 		return mapper.getLedgerChrgList(XTYHID);
 	}
 	
 	public void delLedChrgByLedIds(String XTYH_LEDGER_IDS,UserBean userBean){
 		Map<String,String> map=new HashMap<String, String>();
 		map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_DROP);
 		map.put("XTYH_LEDGER_IDS", XTYH_LEDGER_IDS);
 		map.put("UPDATOR", userBean.getXTYHID());//更新人id
 		map.put("UPD_NAME", userBean.getXM());//更新人名称
 		mapper.delLedChrgByLedIds(map);
 	}
 	
 	@Transactional
 	public void insertLegerChrg(String XTYHID,List<XtyhLedgerChrgModel> list,UserBean userBean){
 		//只新增，不修改，修改金额在充值或信用额度申请页面上进行
 		List<Map<String,String>> addList=new ArrayList<Map<String,String>>();
 		for (int i = 0; i < list.size(); i++) {
 			if(!StringUtil.isEmpty(list.get(i).getXTYH_LEDGER_ID())){
 				continue;
 			}
 			Map<String,String> map=new HashMap<String, String>();
 			map.put("XTYH_LEDGER_ID", StringUtil.uuid32len());
 			map.put("XTYHID", XTYHID);
 			map.put("LEDGER_ID", list.get(i).getLEDGER_ID());
 			map.put("LEDGER_NAME", list.get(i).getLEDGER_NAME());
 			map.put("LEDGER_NAME_ABBR", list.get(i).getLEDGER_NAME_ABBR());
 			map.put("CREATOR", userBean.getXTYHID());
 			map.put("CRE_NAME", userBean.getXM());
 			map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
 			addList.add(map);
 		}
 		if(!addList.isEmpty()){
 			mapper.insertLegerChrg(addList);
 		}
 		//查重
 		List<String> dupList=mapper.checkLadgerDup(XTYHID);
 		if(!dupList.isEmpty()){
 			String mess="帐套：";
 			for (int i = 0; i < dupList.size(); i++) {
 				mess+=dupList.get(i)+",";
 			}
 			mess=mess.substring(0,mess.length()-1);
 			mess+="重复新增，请检查后重新保存";
 			throw new RuntimeException(mess);
 		}
 		
 	}
 	public List<Map<String,String>> getLedgerChrgListByIds(String XTYH_LEDGER_IDS){
 		return mapper.getLedgerChrgListByIds(XTYH_LEDGER_IDS);
 	}
}
