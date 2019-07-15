package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.sys.model.BMXXModel;
import com.centit.sys.model.BMXXTree;

/**
 * 
 * @ClassName: RYXXMapper 
 * @Description: 人员信息
 * @author: liu_yg
 * @date: 2019年2月22日 下午3:28:07
 */
@Repository
public interface BMXXMapper {
	
	/**
	 * 
	 * @Title: pageQuery
	 * @Description: 部门查询
	 * @author wang_dw
	 * @date 2019年3月1日 下午1:46:43
	 * @param @param pageQureyMap
	 * @param @return
	 * @return List<BMXXModel>
	 * @throws
	 */
	public List<BMXXModel>  pageQuery(Map<String, Object> pageQureyMap);
	
	public List<Map<String, Object>>  pageQuery1(Map<String, Object> pageQureyMap);
	
	/**
	 * 
	 * @Title: insert
	 * @Description: 新增部门
	 * @author wang_dw
	 * @date 2019年3月1日 下午1:47:39
	 * @param @param params
	 * @param @return
	 * @return int
	 * @throws
	 */
	int insert(Map params);
	
	/**
	 * 
	 * @Title: update
	 * @Description: 修改部门
	 * @author wang_dw
	 * @date 2019年3月1日 下午2:03:37
	 * @param @param params
	 * @param @return
	 * @return int
	 * @throws
	 */
	int updateById(Map params);
	
	/**
	 * 
	 * @Title: delete
	 * @Description: 删除部门
	 * @author wang_dw
	 * @date 2019年3月1日 下午2:19:56
	 * @param @param BMXXID
	 * @param @return
	 * @return int
	 * @throws
	 */
	int delete(String BMXXID);
	
	/**
	 * 
	 * @Title: updateStateById
	 * @Description: 逻辑删除部门
	 * @author wang_dw
	 * @date 2019年3月1日 下午2:26:40
	 * @param @param params
	 * @param @return
	 * @return int
	 * @throws
	 */
	int updateStateById(Map params);
	
	/**
	 * 
	 * @Title: loadById
	 * @Description: 加载
	 * @author wang_dw
	 * @date 2019年3月1日 下午2:34:08
	 * @param @param params
	 * @param @return
	 * @return Map
	 * @throws
	 */
	Map loadById(Map params);
	
	/**
	 * 
	 * @Title: queryTree
	 * @Description: 查询树
	 * @author wang_dw
	 * @date 2019年3月1日 下午2:45:45
	 * @param @param params
	 * @param @return
	 * @return List<BMXXTree>
	 * @throws
	 */
	List<BMXXTree> queryTree(Map params);
	
	/**
	 * 
	 * @Title: getExits
	 * @Description: 判断部门编号是否存在
	 * @author wang_dw
	 * @date 2019年3月1日 下午2:51:46
	 * @param @param BMXXBH
	 * @param @return
	 * @return int
	 * @throws
	 */
	int getExits(String BMXXBH);
	
	/**
	 * 
	 * @Title: updateAllById
	 * @Description: 
	 * @author wang_dw
	 * @date 2019年3月1日 下午2:53:34
	 * @param @param params
	 * @param @return
	 * @return int
	 * @throws
	 */
	int updateAllById(Map params);
	
	/**
	 * 
	 * @Title: updateRById
	 * @Description: 
	 * @author wang_dw
	 * @date 2019年3月1日 下午2:55:05
	 * @param @param params
	 * @param @return
	 * @return int
	 * @throws
	 */
	int updateRById(Map params);
	
	/**
	 * 
	 * @Title: getWsxSJ
	 * @Description: 
	 * @author wang_dw
	 * @date 2019年3月1日 下午2:56:48
	 * @param @param params
	 * @param @return
	 * @return int
	 * @throws
	 */
	int getWsxSJ(Map params);
	
	/**
	 * 
	 * @Title: getWtyXJ
	 * @Description: 
	 * @author wang_dw
	 * @date 2019年3月1日 下午2:58:33
	 * @param @param params
	 * @param @return
	 * @return int
	 * @throws
	 */
	int getWtyXJ(Map params);
	
	/**
	 * 
	 * @Title: loadBMZT
	 * @Description: 
	 * @author wang_dw
	 * @date 2019年3月1日 下午3:00:50
	 * @param @param SJBM
	 * @param @return
	 * @return String
	 * @throws
	 */
	String loadBMZT(String SJBM);
	
	/**
	 * 
	 * @Title: loadJGZT
	 * @Description: 
	 * @author wang_dw
	 * @date 2019年3月1日 下午3:07:45
	 * @param @param SJJG
	 * @param @return
	 * @return String
	 * @throws
	 */
	String loadJGZT(String SJJG);
	
	/**
	 * 获取部门中最大的同步时间
	 * @return
	 */
	public String queryMaxTbTime();
	
	/**
	 * 
	 * @Title: queryInsertNcBmxx
	 * @Description: 查询从NC过来需要插入的部门
	 * @author lv_f
	 * @date 2019年5月24日 下午4:03:01
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>>  queryInsertNcBmxx(Map<String, Object> params);
	
	/**
	 * 
	 * @Title: queryUpdateNcBmxx
	 * @Description: 查询从NC过来需要更新的部门
	 * @author lv_f
	 * @date 2019年5月24日 下午4:03:05
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>>  queryUpdateNcBmxx(Map<String, Object> params);
	
	/**
	 * 
	 * @Title: insertBmxxList
	 * @Description: 批量插入部门信息
	 * @author lv_f
	 * @date 2019年5月24日 下午4:03:09
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void insertBmxxList(List<Map<String,Object>> list);
	
	/**
	 * 
	 * @Title: updateBmxxList
	 * @Description: 批量更新部门信息
	 * @author lv_f
	 * @date 2019年5月24日 下午4:03:13
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void updateBmxxList(List<Map<String,Object>> list);
	
	/**
	 * 同步部门信息
	 */
	void MergeDateBmxx();
}
