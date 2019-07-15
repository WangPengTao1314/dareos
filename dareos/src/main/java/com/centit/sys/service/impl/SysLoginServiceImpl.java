package com.centit.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.DateUtil;
import com.centit.commons.util.StringUtil;
import com.centit.commons.util.TimeComm;
import com.centit.sys.mapper.GZZCYMapper;
import com.centit.sys.mapper.MenuInfoMapper;
import com.centit.sys.mapper.SysLoginMapper;
import com.centit.sys.mapper.XTYHJSMapper;
import com.centit.sys.mapper.XTYHMapper;
import com.centit.sys.mapper.sqlCommonMapper;
import com.centit.sys.model.MenuInfo;
import com.centit.sys.model.UserBean;
import com.centit.sys.model.XswModel;
import com.centit.sys.model.ZTWHModel;
import com.centit.sys.service.SysLoginService;

@Service
public class SysLoginServiceImpl implements SysLoginService{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(SysLoginService.class);
	
	@Autowired
	private XTYHMapper mapper;
	
	@Autowired
	private SysLoginMapper sysLoginMapper;
	
	@Autowired
	private XTYHJSMapper xtyhjsMapper;
	
	@Autowired
	private GZZCYMapper gzzcyMapper;
	
	@Autowired
	private MenuInfoMapper menuInfoMapper;
	@Autowired
	private sqlCommonMapper commonMapper;
	
	public UserBean initQXComm(String YHM, String KL, String ZTXXID, boolean loginType) throws Exception {
 
		Map<String,String> resMap = sysLoginMapper.getYHXXByYHM(YHM);
		if(resMap==null||resMap.get("XTYHID")==null||resMap.get("XTYHID").equals(""))
		{
			UserBean userbean = null;
			return userbean;
		}
		String XTYHID = resMap.get("XTYHID").toString();
		String YHKL =  resMap.get("YHKL")==null?"":resMap.get("YHKL").toString();
		if (!YHKL.equals(KL)) {
			UserBean userbean = null;
			return userbean;
		}
		Map<String,String> map =new HashMap<String, String>();
		map.put("XTYHID", XTYHID);
		Map<String,String> XTYHMap =  mapper.query(map);
		List<Map<String,String>> XTJSMap = xtyhjsMapper.queryFORLOGIN(XTYHID);
		List<Map<String,String>> GZZXXBean = gzzcyMapper.queryFORLOGIN(XTYHID);
		
  	    UserBean aUserBean = new UserBean(XTYHMap, XTJSMap, GZZXXBean);
  	    //首页URL
  	    aUserBean.setPORTAL_URL(StringUtil.nullToSring(resMap.get("PORTAL_URL")));
  	    //终端店长或者导购员
  	    if("门店_导购员".equals(resMap.get("RYJB")))
  	    {
  	    	aUserBean.setEMPY_FLAG("0");
  	    }else if("门店_店长".equals(resMap.get("RYJB")))
  	    {
  	    	aUserBean.setEMPY_FLAG("1");
  	    }
  	   aUserBean.setCHANNS_CHARG(StringUtil.nullToSring(resMap.get("IS_FG_ALL_CHANN")));
  	    aUserBean.setYHLB(StringUtil.nullToSring(resMap.get("IS_DRP_LEDGER"))); 
		return aUserBean;
	}
	
	/**
	 * 根据ip地址查询用户信息
	 * @param ipAddr
	 * @return
	 */
//	public Map queryUserInfoForAD(String ipAddr){
//		
//		if(StringUtil.isEmpty(ipAddr)){
//			return null;
//		}
//		
//		String sql = " select YHM,YHKL from T_SYS_XTYH where RMT_USER_IP = '" + ipAddr + "'";
//		
//		Map params = new HashMap();
//		params.put("SelSQL", sql);
//		return selcom(params);
//	}
	

	
	/**
	 * Gets the all ztxxids.
	 * 
	 * @param YHM the yHM
	 * @param sperFlag the sper flag
	 * 
	 * @return the all ztxxids
	 */
	public String getAllZTXXIDSByXTYHID(String XTYHID) {
		//根据用户信息ID获取分管帐套
		List<String> list = sysLoginMapper.getLedgerChrgByXtyhId(XTYHID);
		String ledgerIds = "";
		for (int i = 0; i < list.size(); i++) {
			ledgerIds+= "'" + list.get(i) + "',";
		}
		if(StringUtil.isEmpty(ledgerIds)){
			return "''";
		}else{
			return ledgerIds.substring(0,ledgerIds.length()-1);
		}
	}
	
	public String getAllZTXXIDSByJGXXID(String JGXXID){
		//根据用户信息ID获取分管帐套
		List<String> list = sysLoginMapper.getLedgerChrgByJgxxid(JGXXID);
		String ledgerIds = "";
		for (int i = 0; i < list.size(); i++) {
			ledgerIds+= "'" + list.get(i) + "',";
		}
		if(StringUtil.isEmpty(ledgerIds)){
			return "''";
		}else{
			return ledgerIds.substring(0,ledgerIds.length()-1);
		}
	}
	
	/**
	 * 加载系统所有菜单.
	 * 
	 * @return the list
	 */
	public List<MenuInfo> findAllMenus() {
		return menuInfoMapper.findAll();
	} 

	/**
	 * 加载用户菜单.
	 * 
	 * @param XTYHID the xTYHID
	 * 
	 * @return the list
	 */
	public List<MenuInfo> findMenuByUserId(String XTYHID,String IS_DRP_LEDGER) {
		Map <String,String> params = new HashMap<String, String>();
		params.put("userId", XTYHID);
		if("1".equals(IS_DRP_LEDGER))
		{
			return menuInfoMapper.findByUserIdDrp(params);
		}else
		{
			return menuInfoMapper.findByUserIdErp(params);
		}
		
		
	}

	/**
	 * 修改密码.
	 * 
	 * @param user the user
	 * 
	 * @return true, if update pwd
	 */
	public boolean updatePwd(Map user) {
		 mapper.updatePassword(user);
		 return true;
	}
	
	
	/**
	 * Do log.
	 * 
	 * @param aUserBean the a user bean
	 * @param fromIP the from ip
	 */
	public void doLog(UserBean aUserBean,String fromIP){
		Map<String,String> map=new HashMap<String, String>();
		map.put("DLRZID", TimeComm.getTimeStamp("DLRZ"));
		map.put("XTYHID", aUserBean.getXTYHID());
		map.put("DLSJ", TimeComm.getNYRDate() + " "+ TimeComm.getSFMDate());
		map.put("YHIP", fromIP);
		sysLoginMapper.insertLoginLog(map);
	}
	
	/**
	 * Do Action log.
	 * 
	 * @param aUserBean the a user bean
	 * @param fromIP the from ip
	 */
	public void doActLog(String UC_NAME,String ACT_NAME,String OPRATOR,String STATE,String REMARK,String APPCODE,String APPID,String OPRCODE,String KEY) 
	{
	StringBuffer aSQL = new StringBuffer();
	try {
//		Map params = new HashMap();
        aSQL.append("insert into ");
		aSQL.append("T_SYS_SYSLOG(SYSLOG_ID,UC_NAME,ACT_NAME,OPRATOR,ACT_TIME,STATE,REMARK,APPCODE,APPID,OPRCODE,KEY) values(");
		aSQL.append("'");
		aSQL.append(StringUtil.uuid32len());
		aSQL.append("','");
		aSQL.append(StringUtil.nullToSring(UC_NAME));
		aSQL.append("','");
		aSQL.append(StringUtil.nullToSring(ACT_NAME));
		aSQL.append("','");
		aSQL.append(StringUtil.nullToSring(OPRATOR));
		aSQL.append("','");
		aSQL.append(DateUtil.newDataTime());
		aSQL.append("','");
		aSQL.append(StringUtil.nullToSring(STATE));
		aSQL.append("','");
		aSQL.append(StringUtil.nullToSring(REMARK).replaceAll("'", ""));
		aSQL.append("','");
		aSQL.append(StringUtil.nullToSring(APPCODE));
		aSQL.append("','");
		aSQL.append(StringUtil.nullToSring(APPID));
		aSQL.append("','");
		aSQL.append(StringUtil.nullToSring(OPRCODE));
		aSQL.append("','");
		aSQL.append(StringUtil.nullToSring(KEY));
		aSQL.append("')");
//		params.put("InsSQL", aSQL.toString());
		System.err.println("insertSQL==="+aSQL.toString());
		commonMapper.insert(aSQL.toString());
	} catch (Exception ex) {
		System.err
				.println("\u5199\u5165\u767B\u5F55\u65E5\u5FD7\u51FA\u9519:"
						+ ex.toString());
	}
	
	}	
	/**
	 * Gets the user qxinfo.
	 * 
	 * @param aUserBean the a user bean
	 * 
	 * @return the user qxinfo
	 */
	public Map<String,Object> getUserQXINFO(UserBean aUserBean) 
	{
		Map<String,Object> resMap= new HashMap<String, Object>();
		List<String> resList = sysLoginMapper.getXTMKByXTYH(aUserBean.getXTYHID());
		int maxnum = resList.size();
		for (int i = 0; i < maxnum; i++) {
			resMap.put(resList.get(i),1);
		}
		
		resList = sysLoginMapper.getXTMKByXTJS(aUserBean.getXTJSIDS());
		
		maxnum = resList.size();
		for (int i = 0; i < maxnum; i++) {
			resMap.put(resList.get(i).trim(),1);
		}
		
		resList = sysLoginMapper.getXTMKByGZZXX(aUserBean.getGZZXXIDS());
		maxnum = resList.size();
		for (int i = 0; i < maxnum; i++) {
			resMap.put(resList.get(i).trim(),1);
		}
		
		if("administrator".equals(aUserBean.getYHM())){
			resList = sysLoginMapper.getAllXTMK();
			maxnum = resList.size();
			for (int i = 0; i < maxnum; i++) {
				resMap.put(resList.get(i).trim(),1);
			}
		  }
		return  resMap; 
	}
	
	
	
	//组织权限级别
	/**
	 * Gets the user qxjb.
	 * 
	 * @param aUserBean the a user bean
	 * 
	 * @return the user qxjb
	 */
	public Map<String,String> getUserQXJB(UserBean aUserBean) 
	{
		Map<String,String> resMap= new HashMap<String,String>();
		Map<String,String> params= new HashMap<String,String>();
		StringBuffer aSQL = new StringBuffer();
		if (Consts.DBTYPE.equals("DB2")) {
			aSQL.append(" select  XTMKID,QXCODE  ");
			aSQL.append(" from (  ");
			aSQL.append(" select a.XTMKID,QXCODE,a.QXJBID from  ");
			aSQL.append("T_SYS_YHQX a left join ");
			aSQL.append("T_SYS_XTMKQXJB b on substr(a.XTMKID, 1, length(b.XTMKID))=b.XTMKID and a.QXJBID=b.QXJBID where a.XTYHID=");
			aSQL.append("'");
			aSQL.append(aUserBean.getXTYHID());
			aSQL.append("'");
			aSQL.append(" union all");
			aSQL.append(" select a.XTMKID,QXCODE,a.QXJBID from  T_SYS_XTJS js,");
			aSQL.append("T_SYS_JSQX a left join ");
			aSQL.append("T_SYS_XTMKQXJB b on substr(a.XTMKID, 1,length(b.XTMKID))=b.XTMKID and a.QXJBID=b.QXJBID  left join  ");
			aSQL.append("T_SYS_XTYHJS c on a.XTJSID=c.XTJSID  and c.DELFLAG=0  where js.XTJSID=a.XTJSID and js.DELFLAG=0 and js.STATE='启用' and c.XTYHID= ");
			aSQL.append("'");
			aSQL.append(aUserBean.getXTYHID());
			aSQL.append("'");
			aSQL.append(" union all");
			aSQL.append(" select a.XTMKID,QXCODE,a.QXJBID from T_SYS_GZZXX gzz ,");
			aSQL.append("T_SYS_GZZQX a left join ");
			aSQL.append("T_SYS_XTMKQXJB b on substr(a.XTMKID, 1, length(b.XTMKID))=b.XTMKID and a.QXJBID=b.QXJBID  left join  ");
			aSQL.append("T_SYS_GZZCY c on a.GZZXXID=c.GZZXXID and c.DELFLAG=0 where gzz.GZZXXID=a.GZZXXID  and gzz.DELFLAg=0 and gzz.GZZZT='启用' and c.XTYHID= ");
			aSQL.append("'");
			aSQL.append(aUserBean.getXTYHID());
			aSQL.append("'");
			aSQL.append(" ) temp  order by XTMKID ASC,QXJBID DESC ");
			
		} else if (Consts.DBTYPE.equals("ORACLE")) {
			aSQL.append(" select  XTMKID,QXCODE  ");
			aSQL.append(" from (  ");
			aSQL.append(" select a.XTMKID,QXCODE,a.QXJBID from  ");
			aSQL.append("T_SYS_YHQX a left join ");
			aSQL.append("T_SYS_XTMKQXJB b on substr(a.XTMKID, 1, length(b.XTMKID))=b.XTMKID and a.QXJBID=b.QXJBID where a.XTYHID=");
			aSQL.append("'");
			aSQL.append(aUserBean.getXTYHID());
			aSQL.append("'");
			aSQL.append(" union all");
			aSQL.append(" select a.XTMKID,QXCODE,a.QXJBID from  T_SYS_XTJS js,");
			aSQL.append("T_SYS_JSQX a left join ");
			aSQL.append("T_SYS_XTMKQXJB b on substr(a.XTMKID, 1,length(b.XTMKID))=b.XTMKID and a.QXJBID=b.QXJBID  left join  ");
			aSQL.append("T_SYS_XTYHJS c on a.XTJSID=c.XTJSID  and c.DELFLAG=0  where js.XTJSID=a.XTJSID and js.DELFLAG=0 and js.STATE='启用' and c.XTYHID= ");
			aSQL.append("'");
			aSQL.append(aUserBean.getXTYHID());
			aSQL.append("'");
			aSQL.append(" union all");
			aSQL.append(" select a.XTMKID,QXCODE,a.QXJBID from T_SYS_GZZXX gzz ,");
			aSQL.append("T_SYS_GZZQX a left join ");
			aSQL.append("T_SYS_XTMKQXJB b on substr(a.XTMKID, 1, length(b.XTMKID))=b.XTMKID and a.QXJBID=b.QXJBID  left join  ");
			aSQL.append("T_SYS_GZZCY c on a.GZZXXID=c.GZZXXID and c.DELFLAG=0 where gzz.GZZXXID=a.GZZXXID  and gzz.DELFLAg=0 and gzz.GZZZT='启用' and c.XTYHID= ");
			aSQL.append("'");
			aSQL.append(aUserBean.getXTYHID());
			aSQL.append("'");
			aSQL.append(" ) temp  order by XTMKID ASC,QXJBID DESC ");
			
		}else if (Consts.DBTYPE.equals("MSSQL")){
			aSQL.append(" select  XTMKID,QXCODE  ");
			aSQL.append(" from (  ");
			aSQL.append(" select a.XTMKID,QXCODE,a.QXJBID from  ");
			aSQL.append("T_SYS_YHQX a left join ");
			aSQL.append("T_SYS_XTMKQXJB b on substring(a.XTMKID, 1, datalength(b.XTMKID))=b.XTMKID and a.QXJBID=b.QXJBID where a.XTYHID=");
			aSQL.append("'");
			aSQL.append(aUserBean.getXTYHID());
			aSQL.append("'");
			aSQL.append(" union all");
			aSQL.append(" select a.XTMKID,QXCODE,a.QXJBID from  T_SYS_XTJS js,");
			
			aSQL.append("T_SYS_JSQX a left join ");
				 
			aSQL.append("T_SYS_XTMKQXJB b on substring(a.XTMKID, 1, datalength(b.XTMKID))=b.XTMKID and a.QXJBID=b.QXJBID  left join  ");
			
			aSQL.append("T_SYS_XTYHJS c on a.XTJSID=c.XTJSID  and c.DELFLAG=0  where js.XTJSID=a.XTJSID and js.DELFLAG=0 and js.STATE='启用' and c.XTYHID= ");
			aSQL.append("'");
			aSQL.append(aUserBean.getXTYHID());
			aSQL.append("'");
			aSQL.append(" union all");
			aSQL.append(" select a.XTMKID,QXCODE,a.QXJBID from T_SYS_GZZXX gzz ,");
			
			aSQL.append("T_SYS_GZZQX a left join ");
			
			aSQL.append("T_SYS_XTMKQXJB b on substring(a.XTMKID, 1, datalength(b.XTMKID))=b.XTMKID and a.QXJBID=b.QXJBID  left join  ");
			
			aSQL.append("T_SYS_GZZCY c on a.GZZXXID=c.GZZXXID and c.DELFLAG=0 where gzz.GZZXXID=a.GZZXXID  and gzz.DELFLAg=0 and gzz.GZZZT='启用' and c.XTYHID= ");
			aSQL.append("'");
			aSQL.append(aUserBean.getXTYHID());
			aSQL.append("'");
			aSQL.append(" ) temp  order by XTMKID ASC,QXJBID DESC "); 
		}
		
		params.put("SelSQL", aSQL.toString());
		 if("administrator".equals(aUserBean.getYHM()))
		  {
			  aSQL.delete(0, aSQL.length());
			  aSQL.append(" select XTMKID,QXCODE from T_SYS_XTMKQXJB b where QXJBID='2' ");
		  }
		List<Map<String,String>> resList = sysLoginMapper.selcomList(params);
		int maxnum = resList.size();
		for (int i = 0; i < maxnum; i++) {
			Map<String,String> tempMap = (Map<String,String>) resList.get(i);
			String QXCODE=tranCodeN(tempMap.get("QXCODE"));
			//写出翻译代码
			if(QXCODE.indexOf("#XTYHID#")!=-1)
			{
				QXCODE=QXCODE.replaceAll("#XTYHID#","'"+aUserBean.getXTYHID()+"'");
			}
			if(QXCODE.indexOf("#JGXXID#")!=-1)
			{
				QXCODE=QXCODE.replaceAll("#JGXXID#","'"+aUserBean.getJGXXID()+"'");
			}
			if(QXCODE.indexOf("#BMXXID#")!=-1)
			{
				QXCODE=QXCODE.replaceAll("#BMXXID#","'"+aUserBean.getBMXXID()+"'");
			}
			//机构集中
			if(QXCODE.indexOf("#JGXXIDS#")!=-1)
			{
				QXCODE=QXCODE.replaceAll("#JGXXIDS#"," select JGXXID from T_SYS_JGXX start with JGXXID = '"+aUserBean.getJGXXID()+"' connect by prior JGXXID = SJJG ");
			}
			//多机构
			if(QXCODE.indexOf("#DJGXXIDS#")!=-1)
			{
				//这里要做人员分管机构模块才行
				//QXCODE=QXCODE.replaceAll("#DJGXXIDS#"," select JGXXID from T_SYS_JGXX start with  JGXXID in (select distinct  JGXXID  from T_SYS_XTYHJGFG where XTYHID='"+aUserBean.getXTYHID()+"')  connect by prior JGXXID = SJJG ");
				QXCODE=QXCODE.replaceAll("#DJGXXIDS#"," select   JGXXID  from T_SYS_XTYHJGFG where XTYHID='"+aUserBean.getXTYHID()+"' ");
			}
			//部门集中
			if(QXCODE.indexOf("#BMXXIDS#")!=-1)
			{
				QXCODE=QXCODE.replaceAll("#BMXXIDS#"," select BMXXID from T_SYS_BMXX start with BMXXID = '"+aUserBean.getBMXXID()+"' connect by prior BMXXID = SJBM ");
			}
			//多部门
			if(QXCODE.indexOf("#DBMXXIDS#")!=-1)
			{
				//这里要做人员分管部门模块才行
				//QXCODE=QXCODE.replaceAll("#DBMXXIDS#"," select BMXXID from T_SYS_BMXX start with  BMXXID in (select distinct  BMXXID  from T_SYS_XTYHBMFG where XTYHID='"+aUserBean.getXTYHID()+"')  connect by prior BMXXID = SJBM ");
				QXCODE=QXCODE.replaceAll("#DBMXXIDS#"," select distinct  BMXXID  from T_SYS_XTYHBMFG where XTYHID='"+aUserBean.getXTYHID()+"' ");
			}
			if("".equals("QXCODE"))
			{
				QXCODE="1<>1";
			}
			resMap.put(tranCodeN(tempMap.get("XTMKID")),QXCODE);
		}
		
		return  resMap;
	}
	
	/**
	 * Tran code p.
	 * 
	 * @param Str the str
	 * 
	 * @return the string
	 */
	public String tranCodeP(String Str) {

		return Str == null ? "" : Str;
	}

	/**
	 * Tran code n.
	 * 
	 * @param Str the str
	 * 
	 * @return the string
	 */
	public String tranCodeN(Object Str) {

		return Str == null ? "" : Str.toString();
	}
	
	//获得采购组对象
	/**
	 * Gets the xSW model.
	 * 
	 * @return the xSW model
	 */
	public List<XswModel> getXSWModel() 
	{
	  List<XswModel> resList = sysLoginMapper.getXSWModel(BusinessConsts.JC_STATE_ENABLE);
	  return resList;
	}
	public String getIS_DRP_LEDGER(String ZTXXID){
		  return sysLoginMapper.getIS_DRP_LEDGER(ZTXXID);
		
	}
	public Map<String,String> getBaseChann()
	{
		  return sysLoginMapper.getBaseChann();
		
	}
	public Map<String,String> getcurrChann(String chann_Id)
	{
		  return sysLoginMapper.getcurrChann(chann_Id);
		
	}
	
	public Map<String,String> getCurrTrem(String dept_No)
	{
		  return sysLoginMapper.getCurrTrem(dept_No);
		
	}
	
	
	//根据用户名和密码获得用户相关信息
	/**
	 * Gets the xSW model.
	 * 
	 * @return the xSW model
	 */
	public  Map<String,String> getUserInfoByUserIdAndPd(String userId,String pwd) 
	{
		  Map<String,String> resMap= new HashMap<String,String>();
//		  Map<String,String> params = new HashMap<String,String>();
		  StringBuffer aSQL = new StringBuffer();
		  
		  aSQL.append(" select c.ZTXXID,c.ZTMC ");
		  aSQL.append(" from T_SYS_XTYH a ");
		  aSQL.append(" left join T_SYS_BMXX b  on a.BMXXID=b.BMXXID ");
		  aSQL.append(" left join T_SYS_ZTXX c on b.ZTXXID=c.ZTXXID ");
		  aSQL.append(" where ");
		  aSQL.append("      a.YHM='"+userId+"' ");
		  aSQL.append(" and  a.YHKL='"+pwd+"' ");
//		  params.put("querySql", aSQL.toString());
		  resMap = commonMapper.queryForMap(aSQL.toString());
		  return resMap;
	}
	
	
	/**
	 * Delcom.
	 * 
	 * @param params the params
	 * 
	 * @return true, if successful
	 */
//	public boolean delcom(Map params) {
//		return delete("sqlcom.delete", params);
//	}
	
	/**
	 * Updcom.
	 * 
	 * @param params the params
	 * 
	 * @return true, if successful
	 */
//	public boolean updcom(Map params) {
//		return update("sqlcom.update", params);
//	}
	
	
	/**
	 * Selxtyh.
	 * 
	 * @param params the params
	 * 
	 * @return the map
	 */
//	public Map selxtyh(Map params) {
//		return (Map)load("XTYH.query",params);
//	}
	
	
	
	
	
	public List<Map<String,String>> getZtxx(String userId) {
		return mapper.getZtxx( userId);
	}
	
	/**
	 * Gets the notices.
	 * 
	 * @param loginZTXXID the login ztxxid
	 * 
	 * @return the notices
	 */
	public List<Map<String,String>> getNotices(String loginZTXXID) {
		return mapper.getNotices(loginZTXXID);
	}

}
