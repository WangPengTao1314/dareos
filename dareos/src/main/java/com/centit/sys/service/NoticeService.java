/** * 项目名称：平台 * 系统名： * 文件名：Notice.java*/package com.centit.sys.service;import java.util.List;import java.util.Map;import com.centit.core.po.PageDesc;import com.centit.sys.model.NotcDeptModel;import com.centit.sys.model.NoticeModel;import com.centit.sys.model.UserBean;// TODO: Auto-generated Javadoc/** * *@module * *@func * *@version 1.1 * *@author zhuxw * *@createdate 2011-11-16 */public interface NoticeService  {	/**	 * * 查询并分页	 * * @param params map对象	 * * @param pageNo 页码.	 * 	 * @param params the params	 * @param pageNo the page no	 * 	 * @return the i list page	 */	public void pageQuery(Map<String,Object> params, PageDesc pageDesc);	/**	 * * 详细信息	 * * @param param 主键字段值.	 * 	 * @param param the param	 * 	 * @return the map< string, string>	 */	public Map<String,Object> load(String param);		/**	 * 插入数据	 * @return	 */	public boolean txInsert(Map<String,String> params);		/**	 * 更新数据	 * @param params	 * @return	 */	public boolean txUpdateById(Map<String,String> params);		/**	 * * 删除数据	 * * @param NOTICEID.	 * 	 * @param NOTICEID the nOTICEID	 * 	 * @return true, if tx delete	 */	public boolean txDelete(String NOTICEID,UserBean userBean);		public String txEdit(String NOTICEID, NoticeModel noticeModel, UserBean userBean);        /**     * 根据主表id查询子表明细     *      * @param pId  主表ID     *      * @return       */    public List<Map<String,String>> childDeptQuery(String pId);	/**	 * * 根据子表Id批量加载子表信息	 * @param params 子表IDS	 *	 * @return the list	 */    public List <NotcDeptModel> loadDeptChilds(Map <String, String> params) ;//    public void	/**	 * 查询分管账号列表	 */	List<Map<String,String>> queryFGZTXXByUser(Map<String,String> params);}