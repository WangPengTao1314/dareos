package com.centit.drp.main.wap.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.commons.model.Consts;
import com.centit.commons.util.StringUtil;
import com.centit.drp.main.wap.mapper.SysWapLoginMapper;
import com.centit.drp.main.wap.service.SysWapLoginService;
import com.centit.erp.sale.marketcardsale.model.MarketcardSaleChldModel;
import com.centit.sys.model.UserBean;
import com.centit.sys.model.ZTWHModel;
@Service
public class SysWapLoginServiceImpl implements SysWapLoginService{
	
	@Autowired
	private SysWapLoginMapper mapper;

	public UserBean initQXComm(String YHM, String KL, String ZTXXID)
			throws Exception {

		StringBuffer aSQL = new StringBuffer("");
		Map<String, String> params = new HashMap<String, String>();
		aSQL.append("select YHKL,XTYHID,ZTXXID,IS_DRP_LEDGER,RYJB from ");
		aSQL.append("V_XTYH_JGXX_BMXX_RYXX where YHM='");
		aSQL.append(YHM);
		aSQL.append("'");
		// 系统用户是否跨帐套
		// aSQL.append(" and ZTXXID='");
		// aSQL.append(ZTXXID);
		// aSQL.append("'");
		aSQL.append("AND YHZT='\u542f\u7528' ");
		params.put("SelSQL", aSQL.toString());
		Map resMap = new HashMap();//selcom(params);
//		if (resMap == null || resMap.get("XTYHID") == null
//				|| resMap.get("XTYHID").equals("")) {
//			UserBean userbean = null;
//			return userbean;
//		}
		String XTYHID = resMap.get("XTYHID").toString();
		String YHKL = resMap.get("YHKL") == null ? "" : resMap.get("YHKL")
				.toString();
		System.err.println("YHKL=" + YHKL);
		System.err.println("KL=" + KL);
		if (!YHKL.equals(KL)) {
			UserBean userbean = null;
			return userbean;
		}
		params.put("XTYHID", XTYHID);
		Map XTYHMap =  new HashMap();//selxtyh(params);
		List XTJSMap = new ArrayList();//selxtyhjs(params);
		List GZZXXBean = new ArrayList();//selczzcy(params);
		
  	    UserBean aUserBean = new UserBean(XTYHMap, XTJSMap, GZZXXBean);
  	    //是否加盟商标记
  	    aUserBean.setIS_DRP_LEDGER(StringUtil.nullToSring(resMap.get("IS_DRP_LEDGER")));
  	    //终端店长或者导购员
  	    if("门店_导购员".equals(resMap.get("RYJB"))) {
  	    	aUserBean.setEMPY_FLAG("0");
  	    }else if("门店_店长".equals(resMap.get("RYJB"))) {
  	    	aUserBean.setEMPY_FLAG("1");
  	    }

		params.clear();
		params.put("XTYHID", XTYHID);

		return aUserBean;
	}

	
	
	public List<MarketcardSaleChldModel> queryCradList(UserBean userBean){
		return mapper.queryCradList(userBean.getRYXXID());
	}
	/**
	 * 根据卡券编号加载卡券
	 * @param card_no
	 * @return
	 */
//	public Map<String,String> loadCardByNo(String card_no){
//		Map<String,String> paramMap = new HashMap<String,String>();
//		paramMap.put("MARKETING_CARD_NO", card_no);
//		return (Map<String, String>) mapper.load("MarketcardSale.loadByCardNo", paramMap);
//	}
//	
//	public List selxtyhjs(Map params) {
//		return mapper.findList("XTYHJS.queryFORLOGIN", params);
//	}
//	
//	public List selczzcy(Map params) {
//		return mapper.findList("CZZCY.queryFORLOGIN", params);
//	}
//	public List<ZTWHModel> getZtxx(String userId) {
//		return mapper.findList("XTYH.getZtxx", userId);
//	}

	// 通用增删改查
	/**
	 * Selcom.
	 * 
	 * @param params
	 *            the params
	 * 
	 * @return the map
	 */
//	public Map selcom(Map params) {
//		return (Map) load("sqlcom.query", params);
//	}

	/**
	 * Selcom list.
	 * 
	 * @param params
	 *            the params
	 * 
	 * @return the list
	 */
//	public List selcomList(Map params) {
//		return mapper.findList("sqlcom.query", params);
//	}

	/**
	 * Delcom.
	 * 
	 * @param params
	 *            the params
	 * 
	 * @return true, if successful
	 */
//	public boolean delcom(Map params) {
//		return delete("sqlcom.delete", params);
//	}

	/**
	 * Updcom.
	 * 
	 * @param params
	 *            the params
	 * 
	 * @return true, if successful
	 */
//	public boolean updcom(Map params) {
//		return update("sqlcom.update", params);
//	}

	/**
	 * Inscom.
	 * 
	 * @param params
	 *            the params
	 * 
	 * @return true, if successful
	 */
	public boolean inscom(Map params) {
//		insert("sqlcom.insert", params);
		return true;
	}

	/**
	 * Selxtyh.
	 * 
	 * @param params
	 *            the params
	 * 
	 * @return the map
	 */
//	public Map selxtyh(Map params) {
//		return (Map) load("XTYH.query", params);
//	}

}
