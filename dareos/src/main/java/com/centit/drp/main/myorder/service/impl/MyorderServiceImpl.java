
package com.centit.drp.main.myorder.service.impl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.base.product.model.ProductTree;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.ResourceUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.myorder.mapper.MyorderMapper;
import com.centit.drp.main.myorder.model.MyorderModel;
import com.centit.drp.main.myorder.service.MyorderService;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
/**
 * *@serviceImpl
 * *@func
 * *@version 1.1
 * *@author lyg
 * *@createdate 2013-08-19 14:35:52
 */
@Service
public class MyorderServiceImpl implements MyorderService {
	
	@Autowired
	private MyorderMapper mapper;
	/**
	 * 查询
	 * @param map
	 * @param pageNo
	 * @return
	 */
	public void pageQuery(Map<String, Object> map, PageDesc pageDesc) {
		Page<Map<String, String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(map);
		LogicUtil.transPageHelper(pageDesc, p);
	}
	//获取货品状态为启用状态的所有品牌
	public List<String> findBRANDAll(Map<String,String> map) {
		return mapper.findBRANDAll(map);
	}
	//获取货品状态为启用状态的所有品牌
	public List<String> findPRMT_PLANAll(Map<String,String> map) {
		return mapper.findPRMT_PLANAll(map);
	}
	//添加到购物车表
	@Transactional
	public void txEdit(MyorderModel model,UserBean userBean) {
		Map<String,String> map = new HashMap<String,String>();
		String SHOP_CART_ID=StringUtil.uuid32len();
		String PRD_ID=model.getPRD_ID();
		String DEL_FLAG=BusinessConsts.DEL_FLAG_COMMON;
		String DEAL_FLAG=BusinessConsts.YJLBJ_FLAG_FALSE;
		map.put("PRD_ID", PRD_ID);
		map.put("DEL_FLAG", DEL_FLAG);
		map.put("DEAL_FLAG", DEAL_FLAG);
		map.put("PRD_SIZE", model.getPRD_SIZE());
		map.put("LEDGER_ID", userBean.getLoginZTXXID());
		map.put("SHOP_CART_TYPE", "手工新增");
		map.put("SHOP_CART_ID",SHOP_CART_ID);
		map.put("ORDER_NUM", model.getORDER_NUM());
		map.put("PRICE", model.getPRICE());
		map.put("BRAND", model.getBRAND());
		map.put("PRD_COLOR", model.getPRD_COLOR());
		map.put("ORDER_AMOUNT", model.getORDER_AMOUNT());
		map.put("HOLE_SPEC", model.getHOLE_SPEC());
		map.put("PRD_QUALITY", model.getPRD_QUALITY());
		map.put("PRD_TOWARD", model.getPRD_TOWARD());
		map.put("PRD_GLASS", model.getPRD_GLASS());
		map.put("PRD_OTHER", model.getPRD_OTHER());
		map.put("PRD_SERIES", model.getPRD_SERIES());
		map.put("PROJECTED_AREA", model.getPROJECTED_AREA());
		map.put("EXPAND_AREA", model.getEXPAND_AREA());
		map.put("DEL_FLAG", DEL_FLAG);
		map.put("DEAL_FLAG", DEAL_FLAG);
		map.put("PRD_ID", PRD_ID);
		map.put("SHOP_CART_TYPE", "手工新增");
		map.put("CREATOR",userBean.getXTYHID());//制单人ID
		map.put("CRE_NAME", userBean.getXM());//制单人名称
		map.put("UPDATOR",userBean.getXTYHID());//更新人ID
		map.put("UPD_NAME", userBean.getXM());//更新人名称
		map.put("UPD_TIME", "数据库时间");//更新时间
	    map.put("DEPT_ID",userBean.getBMXXID());//制单部门ID
	    map.put("DEPT_NAME",userBean.getBMMC());//制单部门名称
	    map.put("ORG_ID",userBean.getJGXXID());//制单机构ID
	    map.put("ORG_NAME",userBean.getJGMC());//制单机构名称
	    map.put("CRE_TIME","数据库时间");//制单时间
	    map.put("LEDGER_ID",model.getLEDGER_ID());//帐套ID
	    map.put("LEDGER_NAME",model.getLEDGER_NAME());//帐套名称
	    List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	    list.add(map);
		mapper.insert(list);
		
		//保存附件
		String path = model.getATT_PATH();
		InterUtil.delByFromId(SHOP_CART_ID);//先删除 后添加
		if(!StringUtil.isEmpty(path)){
			List<String> pathList = new ArrayList<String>(Arrays.asList(path.split(",")));
			InterUtil.insertAttPath(pathList, userBean, SHOP_CART_ID);
		}
		
		
	}
	public long getCount(Map<String,Object> map){
		
		return mapper.pageCount(map);
		
	}
	//-- 0156143--Start--刘曰刚--2014-06-16//
	/**
	 * 根据当前登录人所属渠道获取渠道折扣率
	 */
	public String getChannDiscount(String CHANN_ID){
		String obj=mapper.getChannDiscount(CHANN_ID);
		if(null!=obj){
			return obj;
		}
		return null;
	}
	//-- 0156143--End--刘曰刚--2014-06-16//
	/**
	 * 查询货品分类
	 */
	public List<String> findType(){
		Map<String,String> map=new HashMap<String, String>();
		map.put("FINAL_NODE_FLAG", BusinessConsts.YJLBJ_FLAG_TRUE);
		map.put("STATE", "'启用','停用'");
		map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		return mapper.findType(map);
	}
	
	/**
	 * 树.
	 * 
	 * @return the tree
	 * 
	 * @throws Exception the exception
	 */
	public List<ProductTree> getTree(String LEDGER_ID) throws Exception {
		List<ProductTree> menus = mapper.queryTree(LEDGER_ID);//this.findList("product.queryTree", "");
		return ResourceUtil.getZTreeFromList(menus, ProductTree.class);
	}
	
	public List<Map<String,String>> getLedgerByChannId(String channId){
		return mapper.getLedgerByChannId(channId);
	}
	
	 public List<Map<String,String>> getSeriesd(String LEDGER_ID){
		 return mapper.getSeriesd(LEDGER_ID);
	 }
}