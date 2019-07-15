package com.centit.system.user.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.system.user.entity.RYXXModel;
import com.centit.system.user.entity.RYXXTree;

/**
 * 
 * @ClassName: RYXXMapper 
 * @Description: 人员信息
 * @author: liu_yg
 * @date: 2019年2月22日 下午3:28:07
 */
@Repository
public interface RYXXMapper {
	
	/**
	 * 
	 * @Title: pageQuery 
	 * @Description: 分页查询
	 * @author: liu_yg
	 * @date: 2019年2月26日 下午3:05:02 
	 * @param pageQureyMap
	 * @return
	 * @return: List<Map<String,String>>
	 */
	public List<Map<String,String>>  pageQuery(Map<String, Object> pageQureyMap);
	
	
	/**
	 * 
	 * @Title: insert 
	 * @Description: 新增人员信息
	 * @author: liu_yg
	 * @date: 2019年2月22日 下午3:26:34 
	 * @param ryxxModel
	 * @return
	 * @return: int
	 */
	int insert(RYXXModel ryxxModel);
	
	/**
	 * 
	 * @Title: findTreeList 
	 * @Description: 查询人员树
	 * @author: liu_yg
	 * @date: 2019年2月22日 下午3:26:06 
	 * @param map
	 * @return
	 * @return: List<RYXXTree>
	 */
	List<RYXXTree> findTreeList(Map <String, String> map);
	
	/**
	 * 
	 * @Title: loadById 
	 * @Description: 根据对象ID获取对象信息
	 * @author: liu_yg
	 * @date: 2019年2月22日 下午3:37:17 
	 * @param objId
	 * @return
	 * @return: RYXXModel
	 */
	Map<String,String> loadById(String objId);
	
	/**
	 * 
	 * @Title: delete 
	 * @Description: 根据ID逻辑删除
	 * @author: liu_yg
	 * @date: 2019年2月22日 下午3:46:40 
	 * @param map
	 * @return: void
	 */
	void delete(Map<String,String> map);
	
	/**
	 * 
	 * @Title: changeState 
	 * @Description: 根据人员ID和当前状态启用/停用
	 * @author: liu_yg
	 * @date: 2019年2月22日 下午3:49:56 
	 * @param map
	 * @return: void
	 */
	void changeState(Map<String,String> map);
	
	/**
	 * 
	 * @Title: update 
	 * @Description: 修改人员信息
	 * @author: liu_yg
	 * @date: 2019年2月22日 下午5:19:37 
	 * @param obj
	 * @return: void
	 */
	void updateById(RYXXModel obj);
	
	/**
	 * 
	 * @Title: updateUserById 
	 * @Description: 修改用户信息
	 * @author: liu_yg
	 * @date: 2019年2月22日 下午5:19:49 
	 * @param objId
	 * @return: void
	 */
	void updateUserById(String objId);
	
	/**
	 * 
	 * @Title: queryForInt 
	 * @Description: 判断编号是否重复
	 * @author: liu_yg
	 * @date: 2019年2月22日 下午5:21:05 
	 * @param objId
	 * @return
	 * @return: int
	 */
	int queryForInt(String objId);
	
	int getCountBH(String bh);
	
	String getMaxBh(String jgxxId);
	
	String loadBMZT(String bmbh);
	
	String loadJGZT(String jgxxId);
	
	/**
	 * 获取人员中最大的同步时间
	 * @return
	 */
	public String queryMaxTbTime();
	
	/**
	 * 
	 * @Title: queryInsertNcRyxx
	 * @Description: 查询从NC过来需要插入的人员
	 * @author lv_f
	 * @date 2019年5月24日 下午5:16:00
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>>  queryInsertNcRyxx(Map<String, Object> params);
	
	/**
	 * 
	 * @Title: queryUpdateNcRyxx
	 * @Description: 查询从NC过来需要更新的人员
	 * @author lv_f
	 * @date 2019年5月24日 下午5:16:20
	 * @param @param params
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>>  queryUpdateNcRyxx(Map<String, Object> params);
	
	/**
	 * 
	 * @Title: insertRyxxList
	 * @Description: 批量插入人员信息
	 * @author lv_f
	 * @date 2019年5月24日 下午5:16:43
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void insertRyxxList(List<Map<String,Object>> list);
	
	/**
	 * 
	 * @Title: updateRyxxList
	 * @Description: 批量更新人员信息
	 * @author lv_f
	 * @date 2019年5月24日 下午5:17:00
	 * @param @param list
	 * @return void
	 * @throws
	 */
	void updateRyxxList(List<Map<String,Object>> list);
	
	/**
	 * 同步人员信息
	 */
	void MergeDateRyxx();
	
}
