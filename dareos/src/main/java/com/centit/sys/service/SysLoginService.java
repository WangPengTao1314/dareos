package com.centit.sys.service;

import java.util.List;
import java.util.Map;

import com.centit.sys.model.MenuInfo;
import com.centit.sys.model.UserBean;
import com.centit.sys.model.XswModel;
import com.centit.sys.model.ZTWHModel;

public interface SysLoginService {
	
	
	public void doLog(UserBean aUserBean,String fromIP);
	
	public String getIS_DRP_LEDGER(String ZTXXID);
	
	public Map<String,String> getBaseChann();
	
	public Map<String,String> getcurrChann(String chann_Id);
	
	public Map<String,String> getCurrTrem(String dept_No);
	
	public Map<String,Object> getUserQXINFO(UserBean aUserBean);
	
	public Map<String,String> getUserQXJB(UserBean aUserBean);
	
	public List<XswModel> getXSWModel();
	
	public List<MenuInfo> findAllMenus();
	
	public List<MenuInfo> findMenuByUserId(String XTYHID,String IS_DRP_LEDGER);
	
	public  Map<String,String> getUserInfoByUserIdAndPd(String userId,String pwd);
	
	public boolean updatePwd(Map user);
	
	
	/**
	 * 
	 * @Title: getZtxx 
	 * @Description: 获取帐套信息
	 * @author: liu_yg
	 * @date: 2019年2月27日 下午2:21:59 
	 * @param userId
	 * @return
	 * @return: List<ZTWHModel>
	 */
	public List<Map<String,String>> getZtxx(String userId);
	
	public List<Map<String,String>> getNotices(String loginZTXXID);
	
	public UserBean initQXComm(String YHM, String KL, String ZTXXID, boolean loginType) throws Exception;
	
	/**
	 * 根据系统用户ID获取帐套分管
	 * @param xtyhId
	 * @return
	 */
	public String getAllZTXXIDSByXTYHID(String xtyhId);
	
	/**
	 * 根据经销商ID获取帐套分管
	 * @param jgxxid
	 * @return
	 */
	public String getAllZTXXIDSByJGXXID(String jgxxid);

}
