package com.centit.commons.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.centit.base.chann.mapper.ChannMapper;
import com.centit.base.chann.service.ChannService;
import com.centit.base.product.mapper.ProductMapper;
import com.centit.base.provider.mapper.ProviderMapper;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.StringUtil;
import com.centit.sys.mapper.BMXXMapper;
import com.centit.system.user.mapper.RYXXMapper;

/**
 * 
 * @ClassName: NcQueryToOsUtil
 * @Description: 请求是NC，服务方式OS，
 * 方案是从NC方那边数据库查询，然后同步更新到OS系统中
    采用dblink的方式(@nc) 方法如下：
  create database link link名称 
  connect to 对方数据库用户名 identified by 对方数据库用户密码
   using '对方数据库ip:端口/实例名'; 
   
  create database link nc
  connect to ats IDENTIFIED BY "ats"
  using '(
  DESCRIPTION = 
    (ADDRESS_LIST = 
    (ADDRESS = (PROTOCOL = TCP)(HOST = 10.75.43.2 )(PORT = 1521)))  
    (CONNECT_DATA = 
      (SERVICE_NAME = zbdbtest)
     )
    )';

   
   
 * @author lv_f
 * @date 2019年5月20日 上午9:10:47
 *
 */
@Service
public class NcQueryToOsUtil {
	
	/**
	 * 批量保存数据库记录的时最大条数
	 */
	public static final int num_max = 50;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ChannMapper channMapper;
	
	@Autowired
	private ProviderMapper providerMapper;
	
	@Autowired
	private BMXXMapper bmxxmapper;
	
	@Autowired
	private RYXXMapper ryxxmapper;
	
	@Autowired
	private ChannService channService;
	
	/**
	 * 
	 * @Title: syncPrdc
	 * @Description: 同步物料分类
	 * @author lv_f
	 * @date 2019年5月20日 上午10:34:58
	 * @param syncTime 同步数据的开始时间
	 * @return boolean
	 * @throws
	 */
	@Transactional
	public boolean syncPrdc(String syncTime){
		boolean result = true;
		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if ("1".equals(syncTime)) {
				syncTime = productMapper.queryTypeMaxTbTime();
				params.put("syncTime", syncTime);
			}
			
			params.put("code", BusinessConsts.TIMEBER_LEDGER_ID);
			List<Map<String, Object>> insertPrdList = productMapper.queryInsertNcMarbasclass(params);
			List<Map<String, Object>> updatePrdList = productMapper.queryUpdateNcMarbasclass(params);
			
			List<Map<String, Object>> dataMapList = new ArrayList<Map<String,Object>>();
			dataMapList.addAll(insertPrdList);
			dataMapList.addAll(updatePrdList);
			
			List<Map<String, String>> insertLegerMapList = new ArrayList<Map<String,String>>();
			List<Map<String, String>> updateLegerMapList = new ArrayList<Map<String,String>>();
			for (Map<String, Object> dataMap : dataMapList) {
//				dataMap.put("LEDGER_ID", "116");
//				dataMap.put("LEDGER_NAME", "江苏合雅木门有限公司");
				String prdId = (String)dataMap.get("PRD_ID");
				if (BusinessConsts.TIMEBER_LEDGER_ID.equals(prdId)) {
					dataMap.put("PAR_PRD_ID","");
					dataMap.put("PAR_PRD_NO","");
					dataMap.put("PAR_PRD_NAME","");
				}
				String ledgerIdStr = (String)dataMap.get("LEDGER_ID");
				String ledgerNameStr = (String)dataMap.get("LEDGER_NAME");
				String[] ledgerIdArr = ledgerIdStr.split(",");
				String[] ledgerNameArr = ledgerNameStr.split(",");
				for(int i = 0;i < ledgerIdArr.length; i++){
					String ledgerId = ledgerIdArr[i];
					String ledgerName = ledgerNameArr[i];
					Map<String, String> param = new HashMap<String, String>();
					param.put("LEDGER_ID", ledgerId);
					param.put("PRD_ID", prdId);
					Map<String, String> existMap = productMapper.getPrdLedgerByPrdAndLeder(param); 				
					if (existMap == null){
						Map<String,String> map = new HashMap<String, String>();
						map.put("PRD_LEDGER_ID", StringUtil.uuid32len());
						map.put("PRD_ID", prdId);
						map.put("LEDGER_ID",ledgerId);
						map.put("LEDGER_NAME", ledgerName);
						//map.put("LEDGER_NAME_ABBR","");
						map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
						insertLegerMapList.add(map);
					}else {
						Map<String,String> map = new HashMap<String, String>();
						String prdLedgerId = (String)existMap.get("PRD_LEDGER_ID");
						map.put("PRD_LEDGER_ID", prdLedgerId);
						map.put("PRD_ID", prdId);
						map.put("LEDGER_ID",ledgerId);
						map.put("LEDGER_NAME", ledgerName);
						updateLegerMapList.add(map);
					}				
				}			
			}
			if (insertPrdList.size() > 0){
				if(insertPrdList.size() > num_max){
					int count = (int) Math.ceil(insertPrdList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							productMapper.insertPrdList(insertPrdList.subList(i*num_max, insertPrdList.size()));
						}else{
							productMapper.insertPrdList(insertPrdList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					productMapper.insertPrdList(insertPrdList);
				}							
			}
			if (updatePrdList.size() > 0){
				if(updatePrdList.size() > num_max){
					int count = (int) Math.ceil(updatePrdList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							productMapper.updatePrdList(updatePrdList.subList(i*num_max, updatePrdList.size()));
						}else{
							productMapper.updatePrdList(updatePrdList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					productMapper.updatePrdList(updatePrdList);
				}								
			}
			if (insertLegerMapList.size() > 0){
				if(insertLegerMapList.size() > num_max){
					int count = (int) Math.ceil(insertLegerMapList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							productMapper.insertLegerChrg(insertLegerMapList.subList(i*num_max, insertLegerMapList.size()));
						}else{
							productMapper.insertLegerChrg(insertLegerMapList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					productMapper.insertLegerChrg(insertLegerMapList);
				}						
			}
			if (updateLegerMapList.size() > 0){
				if(updateLegerMapList.size() > num_max){
					int count = (int) Math.ceil(updateLegerMapList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							productMapper.updatePrdLedgerId(updateLegerMapList.subList(i*num_max, updateLegerMapList.size()));
						}else{
							productMapper.updatePrdLedgerId(updateLegerMapList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					productMapper.updatePrdLedgerId(updateLegerMapList);
				}						
			}		
			
			//
			productMapper.updateBpLedgerNameAbbr();
		} catch (Exception e) {
			result = false;
			//手动回滚事务
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 
	 * @Title: syncPrd
	 * @Description: 同步物料
	 * @author lv_f
	 * @date 2019年5月23日 下午6:51:59
	 * @param syncTime 同步数据的开始时间
	 * @return boolean
	 * @throws
	 */
	@Transactional
	public boolean syncPrd(String syncTime){
		boolean result = true;
		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if ("1".equals(syncTime)) {
				syncTime = productMapper.queryMaxTbTime();
				params.put("syncTime", syncTime);
			}
			
			List<Map<String, Object>> insertPrdList = productMapper.queryInsertNcPrd(params);
			List<Map<String, Object>> updatePrdList = productMapper.queryUpdateNcPrd(params);
			
			List<Map<String, Object>> dataMapList = new ArrayList<Map<String,Object>>();
			dataMapList.addAll(insertPrdList);
			dataMapList.addAll(updatePrdList);
			
			List<Map<String, String>> insertLegerMapList = new ArrayList<Map<String,String>>();
			List<Map<String, String>> updateLegerMapList = new ArrayList<Map<String,String>>();
			for (Map<String, Object> dataMap : dataMapList) {
				String prdId = (String)dataMap.get("PRD_ID");
				String ledgerInfoStr = dataMap.get("LEDGERINFO")==null?"":(String)dataMap.get("LEDGERINFO");
//				String ledgerIdStr = (String)dataMap.get("LEDGER_ID");
//				String ledgerNameStr = (String)dataMap.get("LEDGER_NAME");
				String[] ledgerInfoArr = ledgerInfoStr.split(",");
//				String[] ledgerIdArr = ledgerIdStr.split(",");
//				String[] ledgerNameArr = ledgerNameStr.split(",");
				for(int i = 0;i < ledgerInfoArr.length; i++){
					String[] ledgerStr = ledgerInfoArr[i].split("\\|");
					String ledgerId = ledgerStr[1];
					String ledgerName = ledgerStr[2];
//					String ledgerId = ledgerIdArr[i];
//					String ledgerName = ledgerNameArr[i];
					Map<String, String> param = new HashMap<String, String>();
					param.put("LEDGER_ID", ledgerId);
					param.put("PRD_ID", prdId);
					Map<String, String> existMap = productMapper.getPrdLedgerByPrdAndLeder(param); 				
					if (existMap == null){
						Map<String,String> map = new HashMap<String, String>();
						map.put("PRD_LEDGER_ID", StringUtil.uuid32len());
						map.put("PRD_ID", prdId);
						map.put("LEDGER_ID",ledgerId);
						map.put("LEDGER_NAME", ledgerName);
						//map.put("LEDGER_NAME_ABBR","");
						map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
						insertLegerMapList.add(map);
					}else {
						Map<String,String> map = new HashMap<String, String>();
						String prdLedgerId = (String)existMap.get("PRD_LEDGER_ID");
						map.put("PRD_LEDGER_ID", prdLedgerId);
						map.put("PRD_ID", prdId);
						map.put("LEDGER_ID",ledgerId);
						map.put("LEDGER_NAME", ledgerName);
						updateLegerMapList.add(map);
					}				
				}
				
				//物料表LEDGER_ID不插入数据
				dataMap.put("LEDGER_ID", "");
			}
			if (insertPrdList.size() > 0){
				if(insertPrdList.size() > num_max){
					int count = (int) Math.ceil(insertPrdList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							productMapper.insertPrdList(insertPrdList.subList(i*num_max, insertPrdList.size()));
						}else{
							productMapper.insertPrdList(insertPrdList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					productMapper.insertPrdList(insertPrdList);
				}							
			}
			if (updatePrdList.size() > 0){
				if(updatePrdList.size() > num_max){
					int count = (int) Math.ceil(updatePrdList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							productMapper.updatePrdList(updatePrdList.subList(i*num_max, updatePrdList.size()));
						}else{
							productMapper.updatePrdList(updatePrdList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					productMapper.updatePrdList(updatePrdList);
				}
			}
			if (insertLegerMapList.size() > 0){
				if(insertLegerMapList.size() > num_max){
					int count = (int) Math.ceil(insertLegerMapList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							productMapper.insertLegerChrg(insertLegerMapList.subList(i*num_max, insertLegerMapList.size()));
						}else{
							productMapper.insertLegerChrg(insertLegerMapList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					productMapper.insertLegerChrg(insertLegerMapList);
				}			
			}
			if (updateLegerMapList.size() > 0){
				if(updateLegerMapList.size() > num_max){
					int count = (int) Math.ceil(updateLegerMapList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							productMapper.updatePrdLedgerId(updateLegerMapList.subList(i*num_max, updateLegerMapList.size()));
						}else{
							productMapper.updatePrdLedgerId(updateLegerMapList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					productMapper.updatePrdLedgerId(updateLegerMapList);
				}							
			}		
			//
			productMapper.updateBpLedgerNameAbbr();
		} catch (Exception e) {
			result = false;
			//手动回滚事务
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}
		
		return result;
	}
		
	/**
	 * 
	 * @Title: syncChann
	 * @Description: 同步客户信息/渠道信息
	 * @author lv_f
	 * @date 2019年5月23日 下午6:52:35
	 * @param syncTime 同步数据的开始时间
	 * @return boolean
	 * @throws
	 */
	@Transactional
	public boolean syncChann(String syncTime){
		boolean result = true;
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			if ("1".equals(syncTime)) {
				syncTime = channMapper.queryMaxTbTime();
				params.put("syncTime", syncTime);
			}
			/*****同步客户渠道信息开始********/
			List<Map<String, Object>> insertChannList = channMapper.queryInsertNcChann(params);
			List<Map<String, Object>> updateChannList = channMapper.queryUpdateNcChann(params);
			
			List<Map<String, Object>> dataMapList = new ArrayList<Map<String,Object>>();
			dataMapList.addAll(insertChannList);
			dataMapList.addAll(updateChannList);
			
			List<Map<String, String>> insertLegerMapList = new ArrayList<Map<String,String>>();
			List<Map<String, String>> updateLegerMapList = new ArrayList<Map<String,String>>();
			for (Map<String, Object> dataMap : dataMapList) {
				String custprop = dataMap.get("CUSTPROP")==null?"":String.valueOf(dataMap.get("CUSTPROP"));
				if ("内部客户".equals(custprop)) {
					dataMap.put("STATE", "制定");
				}
				dataMap.put("IS_BASE_FLAG", "0");
				String channId = dataMap.get("CHANN_ID")==null?"":(String)dataMap.get("CHANN_ID");
				String ledgerInfoStr = dataMap.get("LEDGERINFO")==null?"":(String)dataMap.get("LEDGERINFO");
//				String ledgerIdStr = (String)dataMap.get("LEDGER_ID");
//				String ledgerNameStr = (String)dataMap.get("LEDGER_NAME");
				String[] ledgerInfoArr = ledgerInfoStr.split(",");
//				String[] ledgerIdArr = ledgerIdStr.split(",");
//				String[] ledgerNameArr = ledgerNameStr.split(",");
				for(int i = 0;i < ledgerInfoArr.length; i++){
					String[] ledgerStr = ledgerInfoArr[i].split("\\|");
					String ledgerId = ledgerStr[1];
					String ledgerName = ledgerStr[2];
//					String ledgerId = ledgerIdArr[i];
//					String ledgerName = ledgerNameArr[i];
					Map<String, String> param = new HashMap<String, String>();
					param.put("LEDGER_ID", ledgerId);
					param.put("CHANN_ID", channId);
					Map<String, String> existMap = channMapper.getChannLedgerByChannAndLeder(param); 				
					if (existMap == null){
						Map<String,String> map = new HashMap<String, String>();
						map.put("CHANN_LEDGER_CHRG_ID", StringUtil.uuid32len());
						map.put("CHANN_ID", channId);
						map.put("LEDGER_ID",ledgerId);
						map.put("LEDGER_NAME", ledgerName);
						//map.put("LEDGER_NAME_ABBR","");
						map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
						insertLegerMapList.add(map);
					}else {
						Map<String,String> map = new HashMap<String, String>();
						String channLedgerId = (String)existMap.get("CHANN_LEDGER_CHRG_ID");
						map.put("CHANN_LEDGER_CHRG_ID", channLedgerId);
						map.put("CHANN_ID", channId);
						map.put("LEDGER_ID",ledgerId);
						map.put("LEDGER_NAME", ledgerName);
						updateLegerMapList.add(map);
					}				
				}
				
				//渠道表LEDGER_ID不插入数据
				dataMap.put("LEDGER_ID", "");
				dataMap.put("LEDGER_NAME", "");
			}
			if (insertChannList.size() > 0){
				if(insertChannList.size() > num_max){
					int count = (int) Math.ceil(insertChannList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							channMapper.insertChannList(insertChannList.subList(i*num_max, insertChannList.size()));
						}else{
							channMapper.insertChannList(insertChannList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					channMapper.insertChannList(insertChannList);
				}
				for (Map<String, Object> insertmap : insertChannList) {
					String channId = insertmap.get("CHANN_ID")==null?"":String.valueOf(insertmap.get("CHANN_ID"));
					String custprop = insertmap.get("CUSTPROP")==null?"":String.valueOf(insertmap.get("CUSTPROP"));
					String channName = insertmap.get("CHANN_NAME")==null?"":String.valueOf(insertmap.get("CHANN_NAME"));
					if (StringUtils.isNotBlank(channId) && "外部客户".equals(custprop)) {
						if (!"116".equals(channId) && !"10801".equals(channId) && 
							!"107".equals(channId) && !"113".equals(channId) && !"121".equals(channId)) {
							channService.insertChannConf(channId,channName);
						}
					}
				}
			}
			if (updateChannList.size() > 0){
				if(updateChannList.size() > num_max){
					int count = (int) Math.ceil(updateChannList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							channMapper.updateChannList(updateChannList.subList(i*num_max, updateChannList.size()));
						}else{
							channMapper.updateChannList(updateChannList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					channMapper.updateChannList(updateChannList);
				}		
				for (Map<String, Object> updatetmap : updateChannList) {
					String channId = updatetmap.get("CHANN_ID")==null?"":String.valueOf(updatetmap.get("CHANN_ID"));
					String channName = updatetmap.get("CHANN_NAME")==null?"":String.valueOf(updatetmap.get("CHANN_NAME"));
					if (StringUtils.isNotBlank(channId)) {
//						if (!"116".equals(channId) && !"10801".equals(channId) && 
//								!"107".equals(channId) && !"113".equals(channId) && !"121".equals(channId)) {
//							channService(channId);
						    channService.txUpdate(channId,channName);
//						}
					}
				}
			}
			if (insertLegerMapList.size() > 0){
				if(insertLegerMapList.size() > num_max){
					int count = (int) Math.ceil(insertLegerMapList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							channMapper.insertLegerChrg(insertLegerMapList.subList(i*num_max, insertLegerMapList.size()));
						}else{
							channMapper.insertLegerChrg(insertLegerMapList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					channMapper.insertLegerChrg(insertLegerMapList);
				}		
			}
			if (updateLegerMapList.size() > 0){
				if(updateLegerMapList.size() > num_max){
					int count = (int) Math.ceil(updateLegerMapList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							channMapper.updateChannLedgerId(updateLegerMapList.subList(i*num_max, updateLegerMapList.size()));
						}else{
							channMapper.updateChannLedgerId(updateLegerMapList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					channMapper.updateChannLedgerId(updateLegerMapList);
				}		
			}
			/*****同步客户渠道信息结束********/
			
			// 更新简称到客户/经销商账套分管表中
			channMapper.updateLedgerNameAbbr();
			
			if ("1".equals(syncTime)) {
				syncTime = channMapper.queryBdaMaxTbTime();
				params.put("syncTime", syncTime);
			}
			/*****同步收货地址信息开始********/
			List<Map<String, String>> insertAddrList = channMapper.queryInsertNcAddr(params);
			List<Map<String, String>> updateAddrList = channMapper.queryUpdateNcAddr(params);
			if (insertAddrList.size() > 0){
				if(insertAddrList.size() > num_max){
					int count = (int) Math.ceil(insertAddrList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							channMapper.insertChld(insertAddrList.subList(i*num_max, insertAddrList.size()));
						}else{
							channMapper.insertChld(insertAddrList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					channMapper.insertChld(insertAddrList);
				}		
			}
			if (updateAddrList.size() > 0){
				if(updateAddrList.size() > num_max){
					int count = (int) Math.ceil(updateAddrList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							channMapper.updateChldById(updateAddrList.subList(i*num_max, updateAddrList.size()));
						}else{
							channMapper.updateChldById(updateAddrList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					channMapper.updateChldById(updateAddrList);
				}		
			}
			/*****同步收货地址信息结束********/
			
		} catch (Exception e) {
			result = false;
			//手动回滚事务
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * @Title: syncPrvd
	 * @Description: 同步供应商信息
	 * @author lv_f
	 * @date 2019年5月24日 下午1:40:05
     * @param syncTime 同步数据的开始时间
	 * @return boolean
	 * @throws
	 */
	@Transactional
	public boolean syncPrvd(String syncTime){
		boolean result = true;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if ("1".equals(syncTime)) {
				syncTime = providerMapper.queryMaxTbTime();
				params.put("syncTime", syncTime);
			}
			
			List<Map<String, Object>> insertPrvdList = providerMapper.queryInsertNcPrvd(params);
			List<Map<String, Object>> updatePrvdList = providerMapper.queryUpdateNcPrvd(params);
			
			List<Map<String, Object>> dataMapList = new ArrayList<Map<String,Object>>();
			dataMapList.addAll(insertPrvdList);
			dataMapList.addAll(updatePrvdList);
			
			List<Map<String, String>> insertLegerMapList = new ArrayList<Map<String,String>>();
			List<Map<String, String>> updateLegerMapList = new ArrayList<Map<String,String>>();
			for (Map<String, Object> dataMap : dataMapList) {
				//渠道和供应商共用同一张表
				String channId = (String)dataMap.get("PRVD_ID");
				String ledgerInfoStr = dataMap.get("LEDGERINFO")==null?"":(String)dataMap.get("LEDGERINFO");
				String[] ledgerInfoArr = ledgerInfoStr.split(",");
//				String ledgerIdStr = (String)dataMap.get("LEDGER_ID");
//				String ledgerNameStr = (String)dataMap.get("LEDGER_NAME");
//				String[] ledgerIdArr = ledgerIdStr.split(",");
//				String[] ledgerNameArr = ledgerNameStr.split(",");
				for(int i = 0;i < ledgerInfoArr.length; i++){
					String[] ledgerStr = ledgerInfoArr[i].split("\\|");
					String ledgerId = ledgerStr[1];
					String ledgerName = ledgerStr[2];
//					String ledgerId = ledgerIdArr[i];
//					String ledgerName = ledgerNameArr[i];
					Map<String, String> param = new HashMap<String, String>();
					param.put("LEDGER_ID", ledgerId);
					param.put("CHANN_ID", channId);
					Map<String, String> existMap = channMapper.getChannLedgerByChannAndLeder(param); 				
					if (existMap == null){
						Map<String,String> map = new HashMap<String, String>();
						map.put("CHANN_LEDGER_CHRG_ID", StringUtil.uuid32len());
						map.put("CHANN_ID", channId);
						map.put("LEDGER_ID",ledgerId);
						map.put("LEDGER_NAME", ledgerName);
						//map.put("LEDGER_NAME_ABBR","");
						map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
						insertLegerMapList.add(map);
					}else {
						Map<String,String> map = new HashMap<String, String>();
						String channLedgerId = (String)existMap.get("CHANN_LEDGER_CHRG_ID");
						map.put("CHANN_LEDGER_CHRG_ID", channLedgerId);
						map.put("CHANN_ID", channId);
						map.put("LEDGER_ID",ledgerId);
						map.put("LEDGER_NAME", ledgerName);
						updateLegerMapList.add(map);
					}				
				}
				//LEDGER_ID不插入数据
				dataMap.put("LEDGER_ID", "");
				dataMap.put("LEDGER_NAME", "");
			}
			if (insertPrvdList.size() > 0){
				if(insertPrvdList.size() > num_max){
					int count = (int) Math.ceil(insertPrvdList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							providerMapper.insertPrvdList(insertPrvdList.subList(i*num_max, insertPrvdList.size()));
						}else{
							providerMapper.insertPrvdList(insertPrvdList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					providerMapper.insertPrvdList(insertPrvdList);
				}		
			}
			if (updatePrvdList.size() > 0){
				if(updatePrvdList.size() > num_max){
					int count = (int) Math.ceil(updatePrvdList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							providerMapper.updatePrvdList(updatePrvdList.subList(i*num_max, updatePrvdList.size()));
						}else{
							providerMapper.updatePrvdList(updatePrvdList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					providerMapper.updatePrvdList(updatePrvdList);
				}		
			}
			if (insertLegerMapList.size() > 0){
				if(insertLegerMapList.size() > num_max){
					int count = (int) Math.ceil(insertLegerMapList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							channMapper.insertLegerChrg(insertLegerMapList.subList(i*num_max, insertLegerMapList.size()));
						}else{
							channMapper.insertLegerChrg(insertLegerMapList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					channMapper.insertLegerChrg(insertLegerMapList);
				}		
			}
			if (updateLegerMapList.size() > 0){
				if(updateLegerMapList.size() > num_max){
					int count = (int) Math.ceil(updateLegerMapList.size()/(double)num_max);
					for (int i = 0; i < count; i++) {
						if(i == count-1){
							channMapper.updateChannLedgerId(updateLegerMapList.subList(i*num_max, updateLegerMapList.size()));
						}else{
							channMapper.updateChannLedgerId(updateLegerMapList.subList(i*num_max, (i+1)*num_max));
						}
					}
				}else{
					channMapper.updateChannLedgerId(updateLegerMapList);
				}		
			}
		} catch (Exception e) {
			result = false;
			//手动回滚事务
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}
		return result;
	}
	

	
	/**
	 * 
	 * @Title: syncDep
	 * @Description: 同步部门信息
	 * @author lv_f
	 * @date 2019年5月27日 上午10:17:43
     * @param syncTime 同步数据的开始时间
	 * @return boolean
	 * @throws
	 */
	@Transactional
	public boolean syncDep(String syncTime){
		boolean result = true;
		try {
			bmxxmapper.MergeDateBmxx();
//			Map<String, Object> params = new HashMap<String, Object>();
//			if ("1".equals(syncTime)) {
//				syncTime = bmxxmapper.queryMaxTbTime();
//				params.put("syncTime", syncTime);
//			}
//			
//			List<Map<String, Object>> insertBmxxList = bmxxmapper.queryInsertNcBmxx(params);
//			List<Map<String, Object>> updateBmxxList = bmxxmapper.queryUpdateNcBmxx(params);
//			
//			if (insertBmxxList.size() > 0){
//				if(insertBmxxList.size() > num_max){
//					int count = (int) Math.ceil(insertBmxxList.size()/(double)num_max);
//					for (int i = 0; i < count; i++) {
//						if(i == count-1){
//							bmxxmapper.insertBmxxList(insertBmxxList.subList(i*num_max, insertBmxxList.size()));
//						}else{
//							bmxxmapper.insertBmxxList(insertBmxxList.subList(i*num_max, (i+1)*num_max));
//						}
//					}
//				}else{
//					bmxxmapper.insertBmxxList(insertBmxxList);
//				}		
//			}
//			if (updateBmxxList.size() > 0){
//				if(updateBmxxList.size() > num_max){
//					int count = (int) Math.ceil(updateBmxxList.size()/(double)num_max);
//					for (int i = 0; i < count; i++) {
//						if(i == count-1){
//							bmxxmapper.updateBmxxList(updateBmxxList.subList(i*num_max, updateBmxxList.size()));
//						}else{
//							bmxxmapper.updateBmxxList(updateBmxxList.subList(i*num_max, (i+1)*num_max));
//						}
//					}
//				}else{
//					bmxxmapper.updateBmxxList(updateBmxxList);
//				}		
//			}
			
		} catch (Exception e) {
			result = false;
			//手动回滚事务
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 
	 * @Title: syncEmp
	 * @Description: syncEmp
	 * @author lv_f
	 * @date 2019年5月24日 下午5:18:42
     * @param syncTime 同步人员数据的开始时间
	 * @return boolean
	 * @throws
	 */
	@Transactional
	public boolean syncEmp(String syncTime){
		boolean result = true;
		try {
			ryxxmapper.MergeDateRyxx();
//			Map<String, Object> params = new HashMap<String, Object>();
//			if ("1".equals(syncTime)) {
//				syncTime = ryxxmapper.queryMaxTbTime();
//				params.put("syncTime", syncTime);
//			}
//			
//			List<Map<String, Object>> insertRyxxList = ryxxmapper.queryInsertNcRyxx(params);
//			List<Map<String, Object>> updateRyxxList = ryxxmapper.queryUpdateNcRyxx(params);
//			
//			if (insertRyxxList.size() > 0){
//				if(insertRyxxList.size() > num_max){
//					int count=(int) Math.ceil(insertRyxxList.size()/(double)num_max);
//					for (int i = 0; i < count; i++) {
//						if(i == count-1){
//							ryxxmapper.insertRyxxList(insertRyxxList.subList(i*num_max, insertRyxxList.size()));
//						}else{
//							ryxxmapper.insertRyxxList(insertRyxxList.subList(i*num_max, (i+1)*num_max));
//						}
//					}
//				}else{
//					ryxxmapper.insertRyxxList(insertRyxxList);
//				}			
//			}
//			if (updateRyxxList.size() > 0){
//				if(updateRyxxList.size() > num_max){
//					int count=(int) Math.ceil(updateRyxxList.size()/(double)num_max);
//					for (int i = 0; i < count; i++) {
//						if(i == count-1){
//							ryxxmapper.updateRyxxList(updateRyxxList.subList(i*num_max, updateRyxxList.size()));
//						}else{
//							ryxxmapper.updateRyxxList(updateRyxxList.subList(i*num_max, (i+1)*num_max));
//						}
//					}
//				}else{
//					ryxxmapper.updateRyxxList(updateRyxxList);
//				}			
//			}
			
		} catch (Exception e) {
			result = false;
			//手动回滚事务
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}
		return result;
	}
}
