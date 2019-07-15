package com.centit.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.base.chann.mapper.ChannMapper;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.InterUtil;
import com.centit.drp.main.firpage.mapper.DrpFirpageMapper;
import com.centit.erp.sale.prmtplan.model.PrmtplanModel;
import com.centit.sys.mapper.FirstPageMapper;
import com.centit.sys.mapper.MessageMapper;
import com.centit.sys.mapper.XTYHMapper;
import com.centit.sys.model.MessageModel;
import com.centit.sys.model.NoticeModel;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.FirstPageService;


@Service
public class FirstPageServiceImpl implements FirstPageService {
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private DrpFirpageMapper drpFirpageMapper;
	
	@Autowired
	private FirstPageMapper firstPageMapper;
	
	@Autowired
	private XTYHMapper xTYHMapper;
	
	@Autowired
	private ChannMapper channMapper;
    
    /**
     * 查询所有短消息.
     * 
     * @param map the map
     * 
     * @return the all message
     */
	public List<MessageModel>getAllMessage(Map<String,String> map){
		return messageMapper.queryAllMessage(map);
	}
    
    /**
     * 根据短消息ID修改查看状态.
     * 
     * @param map the map
     * 
     * @return the string
     */
	public String txInsertCkztByDxxid(Map<String,String> map){
		List<Map<String,String>> list = messageMapper.queryOldMessById(map.get("MESSAGEID"));
		if(list==null||list.size()==0){
			messageMapper.insertCkztBydxxid(map);
		}
		return "1";
	}
	
	/**
	 * 将短消息插入表中.
	 * 
	 * @param map the map
	 * 
	 * @return true, if tx insert message
	 */
	public boolean txInsertMessage(Map<String,String> map){
		messageMapper.insertMessage( map);
		return true;
	}
	
	/**
	 * 将审批时生成的短消息插入表中.
	 * 
	 * @param map the map
	 * 
	 * @return true, if tx insert message by audit
	 */
	public boolean txInsertMessageByAudit(Map<String,String> map){
		messageMapper.insertMessageByAudit(map);
		return true;
	}
	
	/**
	 * 代办事宜.
	 * @param sql the sql
	 * @return the list< map< string, string>>
	 */
	public List<Map<String,String>> queryDbsyByUser(String sql){
		return messageMapper.queryDbsyByUser(sql);
	}
	 
 	/**
 	 * 数据过滤.
 	 * @param sql the sql
 	 * @return the filter data
 	 */
	public Map<String,String>getFilterData(String sql){
		return messageMapper.queryFilterData(sql);
	}
	
	

	/**
	 * 查询推广促销
	 * @param userBean userBean
	 * @param size 记录数
	 * @return
	 */
	public List<PrmtplanModel> queryPrmtpList(UserBean userBean,int size){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("XTYHID", userBean.getXTYHID());
		paramMap.put("STATE", BusinessConsts.ISSUANCE);
		paramMap.put("size", size);
		return drpFirpageMapper.queryPrmt(paramMap);
	}
	
	/**
	 * 查询公告
	 * @param userBean 用户
	 * @param size 记录数
	 * @return
	 */
	public List<NoticeModel> queryNoticeList(UserBean userBean,int size){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER();//1.经销商   0.总部
		if(BusinessConsts.INTEGER_1.equals(IS_DRP_LEDGER)){
			paramMap.put("NOTICE_OBJ", "3");
		}else{
			paramMap.put("NOTICE_OBJ", "2");
		}
		paramMap.put("size",size);
		paramMap.put("USERID", userBean.getXTYHID());
		return drpFirpageMapper.queryNotice(paramMap);
	}
	
	public int queryBmForInt(UserBean userBean){
		return 0;
	}
	@Override
	public Map<String, Object> getAuth(UserBean userBean) {
		List<Map<String, String>> authList=null;
		/**
 		 * 分销账套0：总部，1:经销商
 		 */
		//渠道分管账套 
		if("1".equals(userBean.getIS_DRP_LEDGER())) {
			authList=channMapper.getLedgerChrgList(userBean.getCHANN_ID());
		}else {
			authList=xTYHMapper.getLedgerChrgList(userBean.getXTYHID());
		}
		Map<String, Object>maps=new HashMap<String, Object>();
		maps.put("authList",authList);
		return maps;
	}
	/**
	   * 近一年销售单据，全部，已完成，未完成   月总金额汇总  
	 */
	@Override
	public Map<String, Object> queryFlash(Map<String, Object> params,UserBean userBean) {
		List<Map<String, String>> authList=null;
		/**
 		 * 分销账套0：总部，1:经销商
 		 */
		//渠道分管账套 
		if("1".equals(userBean.getIS_DRP_LEDGER())) {
			params.put("CHANN_ID",userBean.getCHANN_ID());//渠道ID
		}
		Map<String, Object>maps=new HashMap<String, Object>();
		maps.put("allInfo",firstPageMapper.querySum(params));
		maps.put("orderRank", firstPageMapper.orderRanking(params));
		return maps;
	}
	
	/**
	   *  获取各首页待办事项记录条数：
	 */
	@Override
	public Map<String, Object> queryCounts(Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		//返修订单
		//params.put("STATE","");
		map.put("reCount",firstPageMapper.ReworkCounts(params));
		//销售订单 
		map.put("SaleCount",firstPageMapper.SaleCounts(params));
		//账户管理充值信息 
		map.put("DepositCount",firstPageMapper.DepositCounts(params));
		//额度信息 
		map.put("CreditCount",firstPageMapper.CreditReqCounts(params));
		//
		map.put("GoodsCount",firstPageMapper.GoodsorderCounts(params));
		return map;
	}
	
	/**
	 * 加载系统所有菜单.
	 * 
	 * @return the list
	 */
	@Override
	public List<Map <String,Object>> findAllMenus() {
		return firstPageMapper.findAll();
	} 

	/**
	 * 加载待办事项.
	 * 
	 * @param XTYHID the xTYHID
	 * 
	 * @return the list
	 */
	@Override
	public List<Map <String,Object>> findMenuByUserId(String XTYHID,String IS_DRP_LEDGER) {
		Map <String,String> params = new HashMap<String, String>();
		params.put("userId", XTYHID);
		if("1".equals(IS_DRP_LEDGER))
		{
			return firstPageMapper.findByUserIdDrp(params);
		}else
		{
			return firstPageMapper.findByUserIdErp(params);
		}
		
		
	}
	/**
	 * 加载待办事项.
	 * 
	 * @param XTYHID the xTYHID
	 * 
	 * @return the list
	 */
	@Override
	public List<Map <String,Object>> findMenuByUserId2(String XTYHID,String IS_DRP_LEDGER) {
		Map <String,String> params = new HashMap<String, String>();
		params.put("userId", XTYHID);
		if("1".equals(IS_DRP_LEDGER))
		{
			return firstPageMapper.findByUserIdDrp2(params);
		}else
		{
			return firstPageMapper.findByUserIdErp2(params);
		}
		
		
	}

	@Override
	public int getToDoCon(Map<String, String> map,UserBean userBean) {
		// 查询表名
		String tableName =map.get("tableName");
		//查询状态
		String state=map.get("state");
		// 权限类型 1代表经销商  2代表用户分管经销商&&帐套分管 // 3 拼接查询SQL查询 4:用户分管经销商
		String qxType = map.get("qxType");
		String sql = "select count(1) cnt from " + tableName +" u ";
		if("1".equals(qxType)){
			sql += " where u.CHANN_ID = '"+userBean.getCHANN_ID()+"' ";
		}else if("2".equals(qxType)){
			sql+=" left join BASE_USER_CHANN_CHRG c on u.CHANN_ID = c.CHANN_ID "+
					" left join BASE_XTYH_LEDGER_CHRG l on u.LEDGER_ID = l.LEDGER_ID "+
					" where c.USER_ID = '"+userBean.getXTYHID() +"' and l.XTYHID = '"+userBean.getXTYHID() +"' ";
		}else if("3".equals(qxType)){
			String conSql = map.get("sql");
			conSql = conSql.replaceAll("'XTYHID'", "'"+userBean.getXTYHID()+"'");
			sql+=" where " + conSql;
		}else if("4".equals(qxType)){
			sql+=" left join BASE_USER_CHANN_CHRG c on u.CHANN_ID = c.CHANN_ID "+
					" where c.USER_ID = '"+userBean.getXTYHID() +"' ";
		}
		if(state.indexOf(",")>0){
			sql += " and u.state in ("+state+") ";
		}else{
			sql += " and u.state = '"+state+"' ";
		}
		sql += " and u.DEL_FLAG='0' ";
		return InterUtil.getIntBySql(sql);
	}




	
}
