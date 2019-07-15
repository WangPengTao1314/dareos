/**

 * 项目名称：平台

 * 系统名：财务基础数据

 * 文件名：XTJSServiceImpl.java

 */
package com.centit.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.mapper.XTJSMapper;
import com.centit.sys.mapper.XTYHJSMapper;
import com.centit.sys.model.UserBean;
import com.centit.sys.model.XTJSBean;
import com.centit.sys.model.XTYHJSBean;
import com.centit.sys.service.XTJSService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class XTJSServiceImpl.
 * 
 * @module 系统管理
 * @fuc 系统角色信息维护
 * @version 1.1
 * @author 唐赟
 */
@Service
public class XTJSServiceImpl  implements XTJSService {
	@Autowired
	private XTJSMapper mapper;
	@Autowired
	private XTYHJSMapper xtyhjsMapper;

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


    /**
     * 主表插入.
     * 
     * @param params the params
     * 
     * @return true, if insert
     */
    public boolean insert(Map <String, String> params) {
    	mapper.insert(params);
        return true;
    }


    /**
     * 子表插入.
     * 
     * @param params the params
     * 
     * @return true, if insert child
     */
    public boolean insertChild(Map <String, String> params) {
    	List<Map<String,String>> list=new ArrayList<Map<String,String>>();
    	list.add(params);
    	xtyhjsMapper.insertChild(list);
        return true;
    }


    public boolean updateById(Map <String, String> params) {
    	mapper.updateById(params);
        return true;
    }


    /**
     * 加载.
     * 
     * @param param the param
     * 
     * @return the map< string, string>
     */
    public Map <String, String> load(String xtjsID) {

        return mapper.loadById( xtjsID);
    }


    public Map <String, String> query(Map <String, String> params) {

        return mapper.query(params);
    }


    public List <XTYHJSBean> childQuery(String id) {
    	List<XTYHJSBean> list=new ArrayList<XTYHJSBean>();
    	try {
    		Map <String, String> params = new HashMap <String, String>();
            params.put("XTYHID", id);
            //只查询0的记录。1为删除。0为正常
            params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
            list =xtyhjsMapper.pageQuery(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return list;
    }


    /**
     * 得到所有角色编号.
     * 
     * @return the BH list
     */
    public List <XTJSBean> getBHList() {
    	return mapper.getBHList();
    }


    public List <XTYHJSBean> getYHBHList(String XTJSID) {
    	return xtyhjsMapper.getYHBHList(XTJSID);
    }


    public List <XTYHJSBean> loadChilds(String xtjsids) {
    	return xtyhjsMapper.loadByIds(xtjsids);
    }


    public boolean txEdit(String status, UserBean userBean, XTJSBean xtjs, List <XTYHJSBean> xtyhjs) {

        Map <String, String> pList = new HashMap <String, String>();

        pList.put("JSMC", xtjs.getJSMC());
        pList.put("JSSM", xtjs.getJSSM());
        if (StringUtils.isEmpty(status)) {
           // status = (String) this.load("XTJS.getXTJSBH", "");
            pList.put("XTJSID", xtjs.getJSBH());
            pList.put("JSBH",  xtjs.getJSBH());
            pList.put("STATE", BusinessConsts.JC_STATE_DEFAULT);
            pList.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
            insert(pList);//主表插入

        } else {
            pList.put("XTJSID", status);
            try {
                updateById(pList);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.err.println("444444444444444444444");
        //子表信息编辑
        if (StringUtils.isEmpty(status)) {
        	status=xtjs.getJSBH();
        }
        //System.err.println("5555555555");
        if (null != xtyhjs && !xtyhjs.isEmpty()) {
            if (!(txChildEdit(status, xtjs, xtyhjs))) {
                return false;
            }
        }
       //System.err.println("66666666666666");
        return true;
    }


    /**
     * 数据字典明细页面编辑。.
     * 
     * @param status the status
     * @param xtjs the xtjs
     * @param xtyhjs the xtyhjs
     * 
     * @return true, if tx child edit
     */
    @Override
    public boolean txChildEdit(String status, XTJSBean xtjs, List <XTYHJSBean> xtyhjs) {

        //新增列表
        List <Map <String, String>> addList = new ArrayList <Map <String, String>>();
        //修改列表
        List <Map <String, String>> updateList = new ArrayList <Map <String, String>>();
        //判断用户编号是否有重复值
        for (int i = 0; i < xtyhjs.size() - 1; i++) {
            String temp = xtyhjs.get(i).getYHBH();
            if (temp.equals(xtyhjs.get(i + 1).getYHBH())) {
                return false;
            }
        }
        //判断数据库是否有重复的用户编号
        //List <XTYHJSBean> bhList = getYHBHList(xtjs.getJSBH());
        List <XTYHJSBean> bhList = getYHBHList(status);
        for (int q = 0; q < xtyhjs.size(); q++) {
            if ("".equals(xtyhjs.get(q).getXTYHJSID())) {
                for (int j = 0; j < bhList.size(); j++) {
                    if (xtyhjs.get(q).getYHBH().equals(bhList.get(j).getXTYHID())) {
                        return false;
                    }
                }
            }
        }

        for (XTYHJSBean model : xtyhjs) {
            Map <String, String> params = new HashMap <String, String>();            
            params.put("XTYHID", model.getXTYHID());
            //如果没有领料明细ID的则是新增，有的是修改
            //if (StringUtils.isEmpty(status)) {
            if (StringUtils.isEmpty(model.getXTYHJSID())) {
            	params.put("XTJSID", status);
                String xtyhjsID = StringUtil.uuid32len();
                params.put("XTYHJSID", xtyhjsID);
                params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
                addList.add(params);
            } else {
            	params.put("XTJSID", status);
                params.put("XTYHJSID", model.getXTYHJSID());
                updateList.add(params);
            }
        }
        if (!updateList.isEmpty()) {
        	xtyhjsMapper.updateChild(updateList);
        }
        if (!addList.isEmpty()) {
            try {
            	xtyhjsMapper.insertChild(addList);
            } catch (Exception e) {
                e.printStackTrace();
                return false;

            }
        }
        return true;
    }


    public void txBatch4DeleteChild(String xtyhjsids, UserBean userBean) {

        Map <String, String> params = new HashMap <String, String>();
        params.put("xtyhjsids", xtyhjsids);
        params.put("DATARECYCLEID", StringUtil.uuid32len());
        params.put("DELETOR", userBean.getYHM());
        xtyhjsMapper.insertXTYHJS(params);
        xtyhjsMapper.deleteChildByIds(xtyhjsids);
    }


    public boolean txDelete(String xtjsID, UserBean userBean) {

        Map <String, String> params = new HashMap <String, String>();
        params.put("xtjsID", xtjsID);
        params.put("DATARECYCLEID", StringUtil.uuid32len());
        params.put("DELETOR", userBean.getYHM());
        mapper.insertJS(params);

        //明细数据有多条
        //        int count = (Integer) load("XTJS.getmxts", xtjsID);
        //        List <Map <String, String>> ids = new ArrayList <Map <String, String>>();
        //        for (int i = 0; i < count; i++) {
        //            Map <String, String> paramsq = new HashMap <String, String>();
        //            String id = StringUtil.uuid32len();
        //            paramsq.put("DATARECYCLEID", id);
        //            paramsq.put("DELETOR", userBean.getYHM());
        //            paramsq.put("xtjsID", xtjsID);
        //            ids.add(paramsq);
        //        }
        params.put("DATARECYCLEID", StringUtil.uuid32len());
        mapper.insertJSMX(params);

        mapper.delete(xtjsID);
        mapper.deleteJSMX(xtjsID);
        return true;
    }


    public void updateJSStatus(Map <String, String> params) {
    	mapper.updateJSStatus(params);
    }
}
