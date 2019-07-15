package com.centit.sys.service;

import java.util.List;
import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.erp.sale.prmtplan.model.PrmtplanModel;
import com.centit.sys.model.MessageModel;
import com.centit.sys.model.NoticeModel;
import com.centit.sys.model.UserBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface FirstPageService.
 */
public interface FirstPageService {

	/**
	 * 查询所有短消息.
	 * 
	 * @param map the map
	 * 
	 * @return the all message
	 */
	public List<MessageModel>getAllMessage(Map<String,String> map);
	
	/**
	 * 根据短消息ID修改查看状态.
	 * 
	 * @param map the map
	 * 
	 * @return the string
	 */
	public String txInsertCkztByDxxid(Map<String,String> map);
	
	/**
	 * 将短消息插入表中.
	 * 
	 * @param map the map
	 * 
	 * @return true, if tx insert message
	 */
	public boolean txInsertMessage(Map<String,String> map);
	
	/**
	 * 将审批时生成的短消息插入表中.
	 * 
	 * @param map the map
	 * 
	 * @return true, if tx insert message by audit
	 */
	public boolean txInsertMessageByAudit(Map<String,String> map);
	
	/**
	 * 代办事宜.
	 * 
	 * @param sql the sql
	 * 
	 * @return the list< map< string, string>>
	 */
	public List<Map<String,String>> queryDbsyByUser(String sql);
	
	/**
	 * Gets the filter data.
	 * 
	 * @param sql the sql
	 * 
	 * @return the filter data
	 */
	public Map<String,String>getFilterData(String sql);
	
	
	/**
	 * 查询推广促销
	 * @param size 记录数
	 * @return
	 */
	public List<PrmtplanModel> queryPrmtpList(UserBean userBean,int size);
	
	/**
	 * 查询公告
	 * @param userBean 用户
	 * @param size 记录数
	 * @return
	 */
	public List<NoticeModel> queryNoticeList(UserBean userBean,int size);
	
	/**
	   *     查询首页待办事项信息记录条数
	 * @param params
	 * @return
	 */
	Map<String, Object> queryCounts(Map<String, Object> params);
	/**
	 * 计算，已完成的销售订单,近一年月总金额  
	 */
	Map<String, Object> queryFlash(Map<String, Object> params,UserBean userBean);
	Map<String, Object> getAuth(UserBean userBean) ;
	/**
	 * 加载菜单
	 * @return
	 */
    List<Map <String,Object>> findAllMenus();
    
    List<Map <String,Object>> findMenuByUserId(String XTYHID,String IS_DRP_LEDGER);
    List<Map <String,Object>> findMenuByUserId2(String XTYHID,String IS_DRP_LEDGER);
    /**
     * 根据菜单配置获取待办数量
     * @param map
     * @return
     */
    int getToDoCon(Map<String,String> map,UserBean userBean);
	
}
