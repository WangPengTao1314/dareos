package com.centit.system.user.service;

import java.util.List;
import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.sys.model.UserBean;
import com.centit.system.user.entity.RYXXModel;
import com.centit.system.user.entity.RYXXTree;

/**
 * 
 * @ClassName: RYXXService 
 * @Description: 人员信息
 * @author: liu_yg
 * @date: 2019年2月22日 下午3:29:24
 */
public interface RYXXService {

	/**
	 * 
	 * @Title: pageQuery 
	 * @Description: 分页查询
	 * @author: liu_yg
	 * @date: 2019年2月26日 下午3:25:26 
	 * @param pageQureyMap
	 * @param pageDesc
	 * @return: void
	 */
	public void  pageQuery(Map<String, Object> pageQureyMap,PageDesc pageDesc);
    
    /**
     * 
     * @Title: getObjById 
     * @Description: 根据对象ID获取对象信息
     * @author: liu_yg
     * @date: 2019年2月22日 下午3:36:06 
     * @param objId
     * @return
     * @return: RYXXModel
     */
    public Map<String,String> loadById(String objId);
    
    /**
     * 
     * @Title: changeState 
     * @Description: 根据用户ID和当前状态启用/停用
     * @author: liu_yg
     * @date: 2019年2月22日 下午3:51:33 
     * @param map
     * @return: void
     */
    public void changeState(Map<String,String> map);
    
    
    
    /**
     * 
     * @Title: txInsert 
     * @Description: 新增
     * @author: liu_yg
     * @date: 2019年2月22日 下午3:29:40 
     * @param obj
     * @return: void
     */
    public void txInsert(RYXXModel obj);


    /**
     * 
     * @Title: delete 
     * @Description: 逻辑删除
     * @author: liu_yg
     * @date: 2019年2月22日 下午3:39:45 
     * @param objId
     * @param userBean
     * @return
     */
    public void delete(String objId, UserBean userBean);




    /**
     * 
     * @Title: txEdit 
     * @Description: 修改
     * @author: liu_yg
     * @date: 2019年2月22日 下午3:30:18 
     * @param model
     * @param userBean
     * @return: void
     */
    public void edit(RYXXModel model, UserBean userBean);


    /**
     * 
     * @Title: getRyxxExits 
     * @Description: 判断编号是否已经存在
     * @author: liu_yg
     * @date: 2019年2月22日 下午3:30:37 
     * @param RYXXID
     * @return
     * @return: int
     */
    public int getRyxxExits(String RYXXID);


    /**
     * 
     * @Title: getTree 
     * @Description: 查询树
     * @author: liu_yg
     * @date: 2019年2月22日 下午3:30:45 
     * @param ctx
     * @param theme
     * @return
     * @throws Exception
     * @return: List<RYXXTree>
     */
    public List <RYXXTree> getTree(String ctx, String theme) throws Exception;




    /**
     * 得到当前机构的当前最大编号.
     * 
     * @param jgxxid the jgxxid
     * 
     * @return the max bh
     */
    public String getMaxBh(String jgxxid);
    
    /**
     * Gets the count bh.
     * 
     * @param BH the bH
     * 
     * @return the count bh
     */
    public int getCountBH(String BH);
    
    /**
     * 加载.
     * 
     * @param SJBM the sJBM
     * 
     * @return true, if load jgzt
     */
    public boolean loadBMZT(String SJBM);
    
    /**
     * 加载.
     * 
     * @param SJJG the sJJG
     * 
     * @return true, if load jgzt
     */
    public boolean loadJGZT(String SJJG);
}
