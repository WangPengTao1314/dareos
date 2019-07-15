package com.centit.base.chann.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.base.chann.model.DeliveraddrModelChld;


@Repository
public interface ChannMapper {
	
	List<Map<String,String>> pageQuery(Map<String,Object> map);
	
	Map<String,String> loadById(String CHANN_ID);
	
	int getCountCHANN_NO(String CHANN_NO);
	
	String loadSTATE(String CHANN_ID);
	
	Map<String,String> getSHIP_POINTInfo(Map<String,String> maps);
	
	void insert(Map <String, String> params);
	
	void upZTXX(Map <String, String> params);
	
	void upJGXX(Map <String, String> params);
	
	void upBMXX(Map <String, String> params);
	
	void upTerminal(Map <String, String> params);
	
	void updateById(Map <String, String> params);
	
	int getWtyXJ(Map <String, String> params);
	
	void delete(Map <String, String> params);
	
	void delChldByFkId(Map <String, String> params);
	
	void updateStateById(Map <String, String> params);
	
	String getSTATE(String CHANN_ID);
	
	void insertT_SYS_ZTXX(Map <String, String> params);
	
	void insertT_SYS_JGXX(Map <String, String> params);
	
	void insertT_SYS_BMXX(Map <String, String> params);
	
	void insertT_SYS_RYXX(Map <String, String> params);
	
	void insertT_SYS_XTYH(Map <String, String> params);
	
	void insertT_SYS_XTYHZTDZ(Map <String, String> params);
	
	void insertT_SYS_XTYHJS(Map <String, String> params);
	
	void updateChldById(List<Map<String,String>> list);
	
	void insertChld(List<Map<String,String>> list);
	
	List<String> checkAddrCount();
	
	List<DeliveraddrModelChld> queryChldByFkId(Map<String,String> params);
	
	List<DeliveraddrModelChld> loadChldByIds(Map<String,String> params);
	
	void deleteChldByIds(Map<String,String> params);
	
	void stopChild(Map<String,String> params);
	
	int countAddrNo(Map<String,String> params);
	
	List<Map<String,String>> chargQuery(String CHANN_ID);
	
	List<Map<String,String>> qryChannExp(Map<String,String> params);
	
	void batchUpdateChann_1(Map<String,String> params);
	
	void batchUpdateChann_2(Map<String,String> params);
	
	void batchUpdateChann_3(Map<String,String> params);
	
	void batchUpdateChann_4(Map<String,String> params);
	
	void upTerminalByChannIds(Map<String,String> params);
	
	void batchUpdateStoreIn(Map<String,String> params);
	
	List<Map<String,String>>  queryChannByparams(Map<String,String> params);
	
	int checkChannInfoIntact(String CHANN_ID);
	
	String getChannId(String CHANN_NO);
	
	Map<String,Object> getChannRebateDsct(Map<String,String> map);
	
	Map<String,Object> getChannDsct(Map<String,String> map);
	
	List<Map<String,String>> getLedgerChrgList(String channId);
	
	void delLedChrgByLedIds(Map<String,String> map);
	void insertLegerChrg(List<Map<String,String>> list);
	List<Map<String,String>> getLedgerChrgListByIds(@Param("CHANN_LEDGER_CHRG_IDS")String CHANN_LEDGER_CHRG_IDS);
	List<String> checkLadgerDup(String channId);
	void delLedChrgByLedChannId(Map<String,String> map);
	
	/**
	 * 更新简称到客户/经销商账套分管表中
	 */
	void updateLedgerNameAbbr();
	
	/**
	 * 获取经销商中最大的同步时间
	 * @return
	 */
	public String queryMaxTbTime();
	
	/**
	 * 获取经销商收货地址中最大的同步时间
	 * @return
	 */
	public String queryBdaMaxTbTime();
	
	/**
	 * 
	 * @Title: queryInsertNcChann
	 * @Description: 查询从NC过来需要新插入的客户/渠道数据
	 * @author lv_f
	 * @date 2019年5月23日 下午7:45:16
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>>  queryInsertNcChann(Map<String, Object> params);
	
	/**
	 * 
	 * @Title: queryUpdateNcChann
	 * @Description: 查询从NC过来需要更新的客户/渠道数据
	 * @author lv_f
	 * @date 2019年5月23日 下午7:45:21
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>>  queryUpdateNcChann(Map<String, Object> params);
	
	/**
	 * 
	 * @Title: insertChannList
	 * @Description: 批量插入渠道表
	 * @author lv_f
	 * @date 2019年5月24日 上午8:27:17
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void insertChannList(List<Map<String,Object>> list);
	
	/**
	 * 
	 * @Title: updateChannList
	 * @Description: 批量更新渠道表
	 * @author lv_f
	 * @date 2019年5月24日 上午8:29:14
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void updateChannList(List<Map<String,Object>> list);
	
	/**
	 * 
	 * @Title: getChannLedgerByChannAndLeder
	 * @Description: 根据渠道id和账套id查询记录
	 * @author lv_f
	 * @date 2019年5月23日 下午1:22:42
	 * @param @param params
	 * @param @return
	 * @return Map<String,String>
	 * @throws
	 */
	Map<String,String> getChannLedgerByChannAndLeder(Map<String, String> params);
	
	/**
	 * 
	 * @Title: updateChannLedgerId
	 * @Description: 批量更新渠道账套关系表
	 * @author lv_f
	 * @date 2019年5月23日 下午1:44:23
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void updateChannLedgerId(List<Map<String,String>> list);
	
	/**
	 * 
	 * @Title: queryInsertNcAddr
	 * @Description: 查询从NC过来需要新插入的地址
	 * @author lv_f
	 * @date 2019年5月24日 上午9:43:42
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,String>>
	 * @throws
	 */
	public List<Map<String, String>>  queryInsertNcAddr(Map<String, Object> params);
	
	/**
	 * 
	 * @Title: queryUpdateNcAddr
	 * @Description: 查询从NC过来需要更新的地址
	 * @author lv_f
	 * @date 2019年5月24日 上午9:52:33
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, String>>  queryUpdateNcAddr(Map<String, Object> params);
	
	
}
