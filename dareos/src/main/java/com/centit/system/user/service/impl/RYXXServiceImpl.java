
package com.centit.system.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.TreeModelLoader;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.ResourceUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.UserBean;
import com.centit.system.user.entity.RYXXModel;
import com.centit.system.user.entity.RYXXTree;
import com.centit.system.user.mapper.RYXXMapper;
import com.centit.system.user.service.RYXXService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @ClassName: RYXXServiceImpl 
 * @Description: 人员信息
 * @author: liu_yg
 * @date: 2019年2月22日 下午3:28:31
 */
@Service
public class RYXXServiceImpl implements RYXXService {
	
	@Autowired
	private RYXXMapper mapper;
	

    @Override
	public void pageQuery(Map<String, Object> map, PageDesc pageDesc) {
    	Page<Map<String,String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(map);
		LogicUtil.transPageHelper(pageDesc, p);
	}


    @Transactional
	@Override
    public void txInsert(RYXXModel obj) {
    	mapper.insert(obj);
    }
    
    public void changeState(Map<String,String> map){
    	mapper.changeState(map);
    }

    @Transactional
    public void delete(String objId, UserBean userBean) {
    	Map<String,String> map=mapper.loadById(objId);
        Map <String, String> params = new HashMap <String, String>();
        params.put("RYXXID", objId);
        params.put("DELFLAG", BusinessConsts.DEL_FLAG_DROP);
        mapper.delete(params);
        
        InterUtil.insertDataRecycle(userBean.getRYXXID(), "人员信息维护", map);
    }


    
    public Map<String,String> loadById(String objId) {

        return mapper.loadById(objId);
    }


    @Override
    public void edit(RYXXModel obj, UserBean userBean) {

        if (StringUtils.isEmpty(obj.getRYXXID())) {
        	obj.setRYZT(BusinessConsts.JC_STATE_DEFAULT);//状态
        	obj.setRYXXID(obj.getRYBH());
            obj.setIS_DRP_LEDGER(BusinessConsts.YJLBJ_FLAG_FALSE);
            obj.setCREATER(userBean.getXTYHID());//制单人ID
            obj.setCRENAME(userBean.getXM());//制单人名称 
            mapper.insert(obj);
        } else {
        	obj.setUPDATER(userBean.getXTYHID());//更新人id    
        	mapper.updateById(obj);
        	mapper.updateUserById(obj.getRYXXID());
        }
    }


    public int getRyxxExits(String RYXXID) {

        return mapper.queryForInt(RYXXID);
    }


    /**
     * 部门机构树.
     * 
     * @param ctx the ctx
     * @param theme the theme
     * 
     * @return the tree
     * 
     * @throws Exception the exception
     */
    public List <RYXXTree> getTree(String ctx, String theme) throws Exception {

        Map <String, String> params = new HashMap <String, String>();
        params.put("ctx", "'" + ctx + "'");
        params.put("theme", "'" + theme + "'");
        List <RYXXTree> tree = mapper.findTreeList(params);
        Map <String, String> mapFlds = new HashMap <String, String>();
        mapFlds.put(TreeModelLoader.TREE_MODEL_FLD_ID, "id");
        mapFlds.put(TreeModelLoader.TREE_MODEL_FLD_PID, "pid");
        mapFlds.put(TreeModelLoader.TREE_MODEL_FLD_NAME, "name");
        mapFlds.put("def1", "flag");
        mapFlds.put("icon", "icon");
        return ResourceUtil.getZTreeFromList(tree, RYXXTree.class, mapFlds);

    }


    /**
     * 得到当前机构的当前最大编号.
     * 
     * @param jgxxid the jgxxid
     * 
     * @return the max bh
     */
    public String getMaxBh(String jgxxId) {

        return mapper.getMaxBh(jgxxId);
    }
    
    public int getCountBH(String bh){
    	return mapper.getCountBH(bh);
    }
    
    /**
     * 加载.
     * 
     * @param SJBM the sJBM
     * 
     * @return true, if load jgzt
     */
    public boolean loadBMZT(String SJBM) {

        if ("停用".equals(mapper.loadBMZT(SJBM))) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * 加载.
     * 
     * @param SJJG the sJJG
     * 
     * @return true, if load jgzt
     */
    public boolean loadJGZT(String SJJG) {

        if ("停用".equals(mapper.loadJGZT(SJJG))) {
            return false;
        } else {
            return true;
        }
    }



}
