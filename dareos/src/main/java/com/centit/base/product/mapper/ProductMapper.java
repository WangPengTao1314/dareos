package com.centit.base.product.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.base.product.model.PrdSpclTechModel;
import com.centit.base.product.model.PrdSuitModel;
import com.centit.base.product.model.PrdUnitModel;
import com.centit.base.product.model.ProductModel;
import com.centit.base.product.model.ProductTree;
@Repository
public interface ProductMapper {
	/**
	 *      分页查询
	 * @param pageQureyMap
	 * @return
	 */
	public List<Map<String,String>> pageQuery(Map<String, Object> pageQureyMap);
	/**
	 * 分页计数
	 * @param pageCountMap
	 * @return
	 */
	public Integer pageCount(Map<String, Object> pageCountMap);
	/**
	 * 查询
	 * @param queryMap
	 * @return
	 */
	public List<Map<String,String>> query(Map<String, Object> queryMap);
	/**
	 * 根据ID查找
	 * @param loadById
	 * @return
	 */
	public Map<String,String> loadById(String pro_id);
	/**
	 * 查询主表是否存在重复编号
	 * @return
	 */
	public Integer getCountPRD_NO(Map<String, String> countPRD_NOmaMap);
	/**
	 * 查询子表货品计量单位是否存在重复编号
	 * @param nuitMap
	 * @return
	 */
	public Integer getCountNuitMEAS_UNIT_NO(Map<String, String> nuitMap);
	/**
	 * 查询子表货品套是否存在重复编号
	 * @param suitMap
	 * @return
	 */
	public Integer getCountSuitPRD_NO(Map<String, String> suitMap);
	/**
	 * 未找到
	 */
	public String loadSTATE(String pro_id);
	/**
	 * 插入数据
	 * @param map
	 * @return
	 */
	Integer insert(Map<String, Object> map);
	Integer insertPrdSpclTech(List<Map<String, Object>> listmap);
	/**
	 * update
	 * @param map
	 * @return
	 */
	Integer updatePrdSpclTech(List<Map<String, Object>> listMap);
	Integer updateById(Map<String, ? extends Object> map);
	Integer updateByNo(Map<String, Object> map);
	/**
	 * 软删除
	 * @param map
	 * @return
	 */
	Integer delete(Map<String, String> map);
	/**
	 * 修改状态
	 * @param map
	 * @return
	 */
	Integer updateStateById(Map<String, String> map);
	/**
	 * 查询树
	 * @return
	 */
	List<ProductTree> queryTree();
	/**
	 * 查询树
	 * @param LEDGER_ID
	 * @return
	 */
	List<ProductTree> queryTree4Ledger(String LEDGER_ID);
	/**
	 * 根据主表ID查询货品计量单位明细
	 * @param map
	 * @return
	 */
	List<PrdUnitModel> queryUnitByFkId(Map<String, Object> map);
	/**
	 * 根据主表ID查询货品套明细
	 */
	List<PrdSuitModel> querySuitByFkId(Map<String, Object> map);
	/**
	 * 根据明细表IDS查询货品计量单位明细
	 * @param map
	 * @return
	 */
	List<PrdUnitModel> loadUnitByIds(Map<String, String> map);
	/**
	 * 根据明细表IDS查询货品套明细
	 * @param map
	 * @return
	 */
	List<PrdSuitModel> loadSuitByIds(Map<String, String> map);
	/**
	 * 货品计量单位明细表更新
	 * @param map
	 * @return
	 */
	Integer updateUnitById(List<Map<String, Object>> listmap);
	/**
	 * 货品套明细表更新
	 * @param map
	 * @return
	 */
	Integer updateSuitById(List<Map<String, Object>> listmap);
	/**
	 * 货品计量单位明细表插入
	 * @param map
	 * @return
	 */
	Integer insertUnit(List<Map<String, Object>> listmap);
	/**
	 * 货品套明细表插入
	 * @param map
	 * @return
	 */
	Integer insertSuit(List<Map<String, Object>> listmap);
	/**
	 * 根据货品计量单位明细主键ID删除明细
	 * @param map
	 * @return
	 */
	Integer delUnitByFkId(Map<String, String> map);
	/**
	 * 根据货品套明细主键ID删除明细
	 * @param map
	 * @return
	 */
	Integer delSuitByFkId(Map<String, String> map);
	/**
	 * 根据主键ID删除明细的子表货品计量单位
	 * @param map
	 * @return
	 */
	Integer delGUnitByProId(Map<String, String> map);
	/**
	 * 根据主键ID删除明细的子表货品套
	 * @param map
	 * @return
	 */
	Integer delGSuitByProId(Map<String, String> map);

	List<String> getBrand(Map<String, String> map);
	List<PrdSpclTechModel> queryPrdId(Map<String, Object> map);
	List<Map<String,String>> queryPrdSpcTechId(String pro_spcl);
	void delPrdSpcTechById(Map<String, String> map);
	List<Map<String,String>> queryPrdExp(Map<String, Object> map);
	void updateByPrdNo(Map<String, Object> map);
	Integer insertTemp(List<Map<String, String>> listmap);
	List<Map<String,String>> checkMustFld(String sessionId);
	List<Map<String,String>> checkDRPMustFld(String sessionId);
	/**
	 * 检查是是否有重记录
	 * @param sessionId
	 * @return
	 */
	List<Map<String,String>> checkPdtRepeat(String sessionId);
	List<String> checkPrdLedger(Map<String, String> map);
	List<String> checkPrdBrand(String sessionId);
	List<String> checkPrdStdUnit(String sessionId);
	List<String> checkPrdMeasUnit(String sessionId);
	
	/**
	 * 根据单位名称查询单位编码
	 * @param unitName
	 * @return
	 */
	String queryNoByName(String unitName);
	
	/**
	 * 根据物料编码查询税率、税码
	 * @param code
	 * @return
	 */
	Map<String,Object> querySlByNo(String code);
	
	/**
	 * 验证有上级货品编号无上级货品名称的
	 * @param sessionId
	 * @return
	 */
	List<String> checkNoParPrdInfo(String sessionId);
	/**
	 * 验证无上级货品编号有上级货品名称的
	 * @param sessionId
	 * @return
	 */
	List<String> checkNameParPrdInfo(String sessionId);
	/**
	 *  验证有上级货品编号有上级货品名称的
	 * @param sessionId
	 * @return
	 */
	List<Map<String, String>> checkParPrdInfo(String sessionId);
	String checkPrdHead(Map<String, String> map);
	List<String> checkPrdNum(String sessionId);
	void upParPrdInfo(Map<String, String> map);
	void upDrpPrdInfo(String sessionId);
	void upErpPrdInfo(String sessionId);
	void insertDrpPrdInfo(String sessionId);
	void insertErpPrdInfo(String sessionId);
	void delPrdTemp(String sessionId);
	Map<String, String> getPrdSuitInfo(String pro_Id);
	String getPrdIdByUnitIds(String prd_unit_Ids);
	Map<String, String> getPrdSuitPriceInfo(String pro_Id);
	Integer checkPrdNo(Map<String, String> map);
	String getParPrdId(Map<String, String> map);
	void delLedChrgByLedIds(Map<String,String> map);
	void insertLegerChrg(List<Map<String,String>> list);
	List<Map<String,String>> getLedgerChrgListByIds(@Param("PRD_LEDGER_IDS")String PRD_LEDGER_IDS);
	List<String> checkLadgerDup(String productId);
	void delLedChrgByLedChannId(Map<String,String> map);
	List<Map<String,String>> getLedgerChrgList(String productId);
	
	/**
	 * 获取物料分类中最大的同步时间
	 * @return
	 */
	public String queryTypeMaxTbTime();
	
	/**
	 * 获取物料中最大的同步时间
	 * @return
	 */
	public String queryMaxTbTime();


	/**
	 *
	 * @Title: queryNcMarbasclass
	 * @Description: 查询从NC过来需要新插入的w物料分类数据
	 * @author lv_f
	 * @date 2019年5月23日 上午9:16:36
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>>  queryInsertNcMarbasclass(Map<String, Object> params);

	/**
	 *
	 * @Title: queryUpdateNcMarbasclass
	 * @Description: 查询从NC过来需要更新的物料分类数据
	 * @author lv_f
	 * @date 2019年5月23日 下午2:50:00
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> queryUpdateNcMarbasclass(Map<String, Object> params);

	/**
	 *
	 * @Title: insertPrdList
	 * @Description: 批量新增
	 * @author lv_f
	 * @date 2019年5月23日 下午3:08:22
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void insertPrdList(List<Map<String,Object>> list);

	/**
	 *
	 * @Title: updatePrdList
	 * @Description: 批量更新
	 * @author lv_f
	 * @date 2019年5月23日 下午3:08:31
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void updatePrdList(List<Map<String,Object>> list);

	/**
	 *
	 * @Title: getPrdLedgerByPrdAndLeder
	 * @Description: 根据物料id和账套id查询记录
	 * @author lv_f
	 * @date 2019年5月23日 下午1:22:42
	 * @param @param params
	 * @param @return
	 * @return Map<String,String>
	 * @throws
	 */
	Map<String,String> getPrdLedgerByPrdAndLeder(Map<String, String> params);

	/**
	 *
	 * @Title: updatePrdLedgerId
	 * @Description: 批量更新物料账套关系表
	 * @author lv_f
	 * @date 2019年5月23日 下午1:44:23
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void updatePrdLedgerId(List<Map<String,String>> list);

	/**
	 *
	 * @Title: queryInsertNcPrd
	 * @Description: 查询从NC过来需要新增的物料数据
	 * @author lv_f
	 * @date 2019年5月23日 下午4:41:45
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>>  queryInsertNcPrd(Map<String, Object> params);

	/**
	 *
	 * @Title: queryUpdateNcPrd
	 * @Description: 查询从NC过来需要更新的物料数据
	 * @author lv_f
	 * @date 2019年5月23日 下午4:41:45
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>>  queryUpdateNcPrd(Map<String, Object> params);

	/**
	 * 根据货品帐套分管ID获取帐套分管信息(无论是否删除)
	 * @param PRD_LEDGER_IDS
	 * @return
	 */
	public List<Map<String,String>> getLedgerChrgByIds(@Param("PRD_LEDGER_IDS")String PRD_LEDGER_IDS);
	
	
	/**
	 * //根据递归获取所有上级货品ID
	 * @param PRD_ID
	 * @return
	 */
	public List<String> getSubPrdInfo(String PRD_ID);
	
	/**
	 * 根据上级货品和帐套信息获取数量
	 * @param map
	 * @return
	 */
	public int getLedgerChrgBySubId(Map<String,String> map);
	
	
	/**
	 * 根据货品和帐套信息获取数量
	 * @param map
	 * @return
	 */
	public int getLedgerChrgById(Map<String,String> map);
	
	/**
	 * 根据货品ID和帐套ID删除货品帐套分管
	 * @param LEDGER_ID
	 * @param PRD_ID
	 */
	void delLedgerChrgByPrdId(Map<String,String> map);
	
	/**
	 * 更新简称到货品账套分管表中
	 */
	void updateBpLedgerNameAbbr();
	
	/**
	 * 更新货品的二级父节点id和名称
	 */
	void updateTwoIDAndName();
	
	void insterLedgerChrgByPrdId(Map<String,String> map);
}
