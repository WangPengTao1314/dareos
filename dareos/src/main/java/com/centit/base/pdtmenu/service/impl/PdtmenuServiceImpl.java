package com.centit.base.pdtmenu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.base.pdtmenu.mapper.PdtmenuMapper;
import com.centit.base.pdtmenu.model.PdtmenuModel;
import com.centit.base.pdtmenu.model.PdtmenuTree;
import com.centit.base.pdtmenu.service.PdtmenuService;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.DateUtil;
import com.centit.commons.util.ResourceUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class PdtmenuServiceImpl implements PdtmenuService{
	
	@Autowired
	private PdtmenuMapper mapper;
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
     * 修改状态为停用
     * Description :.
     * 
     * @param params the params
     * 
     * @return true, if tx stop by id
     */
    @Transactional
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
    @Transactional
    public boolean txStartById(Map <String, String> params) {
        mapper.updateStateById(params);
        return true;
    }
    /**
     * 删除.
     * 
     * @param PRD_ID the Pdtmenu id
     * @param userBean the user bean
     * 
     * @return true, if tx delete
     */
    @Transactional
    public boolean txDelete(Map <String, String> params) {
    	mapper.delete(params);
    	return true;
    }
    /**
     * 增加记录 Description :.
     * 
     * @param params the params
     * 
     * @return true, if tx insert
     */
    @Transactional
    public boolean txInsert(Map <String, Object> params) {
       
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
    @Transactional
    public boolean txUpdateById(Map <String, Object> params) {
        mapper.updateById(params);
        return true;
    }
    /**
     * 加载.
     * 
     * @param PRD_ID the Pdtmenu id
     * 
     * @return the map
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> load(String PRD_ID) {
        return mapper.loadById(PRD_ID);
    }
	 /**
     * 树.
     * 
     * @return the tree
     * 
     * @throws Exception the exception
     */
    @SuppressWarnings("unchecked")
	public List <PdtmenuTree> getTree() throws Exception {
        List<PdtmenuTree> menus =  mapper.queryTree("");
        return ResourceUtil.getZTreeFromList(menus, PdtmenuTree.class);
    }
    /**
     * 编辑.
     * 
     * @param PRD_ID the Pdtmenu id
     * @param PdtmenuModel the Pdtmenu model
     * @param userBean the user bean
     * 
     * @return the string
     */
    public String txEdit(String PRD_ID, PdtmenuModel PdtmenuModel, UserBean userBean) {
        Map <String, Object> params = new HashMap <String, Object>();
        params.put("PRD_NAME", PdtmenuModel.getPRD_NAME());//货品名称
        params.put("BRAND", PdtmenuModel.getBRAND());//品牌
        params.put("PAR_PRD_ID", PdtmenuModel.getPAR_PRD_ID());//上级货品ID
        params.put("PAR_PRD_NO", PdtmenuModel.getPAR_PRD_NO());//上级货品编号
        params.put("PAR_PRD_NAME", PdtmenuModel.getPAR_PRD_NAME());//上级货品名称
        params.put("FINAL_NODE_FLAG", BusinessConsts.YJLBJ_FLAG_FALSE);//终结点标记
        params.put("PRD_CLASS_TYPE", BusinessConsts.YJLBJ_FLAG_FALSE);//货品分类类型
        params.put("REMARK", PdtmenuModel.getREMARK());//备注
        params.put("DEAFULT_ADVC_SEND_DATE", PdtmenuModel.getDEAFULT_ADVC_SEND_DATE());//默认交期
        if (StringUtils.isEmpty(PRD_ID)) {
        	PRD_ID=StringUtil.uuid32len();
        	params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);//删除标记
        	params.put("CREATOR", userBean.getXTYHID());//制单人ID
            params.put("CRE_NAME", userBean.getXM());//制单人名称
            params.put("CRE_TIME", "sysdate");//制单时间
            params.put("DEPT_ID", userBean.getBMXXID());//制单部门ID
            params.put("DEPT_NAME", userBean.getBMMC());//制单部门名称
            params.put("ORG_ID", userBean.getJGXXID());//制单机构id
            params.put("ORG_NAME", userBean.getJGMC());//制单机构名称
        	params.put("PRD_ID", PRD_ID);//货品id
        	params.put("PRD_NO", PdtmenuModel.getPRD_NO());//货品编号
        	params.put("STATE", BusinessConsts.JC_STATE_DEFAULT);//状态
            txInsert(params);
        }else{
        	 params.put("PRD_ID", PRD_ID);
             params.put("UPDATOR", userBean.getXTYHID());//更新人id
             params.put("UPD_NAME", userBean.getXM());//更新人名称
             params.put("UPD_TIME", DateUtil.now());//更新时间
             txUpdateById(params);
			/* clearCaches(PRD_ID); */
        }
        return PRD_ID;
    }
    /**
     * 获取所有品牌名称
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> getBrand(){
    	Map<String,String> map=new HashMap<String,String>();
    	map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
    	map.put("STATE", BusinessConsts.JC_STATE_ENABLE);
    	return mapper.getBrand(map);
    }
    /**
     * 查询主表是否有相同编号
     */
    public int getCountPRD_NO(String PRD_NO) {
		return mapper.getCountPRD_NO(PRD_NO);
	}
}
