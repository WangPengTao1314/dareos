package com.centit.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.centit.base.att.service.AttService;
import com.centit.base.syslog.service.SyslogService;
import com.centit.common.service.BaseService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.model.InterReturnMsg;
import com.centit.core.utils.SpringContextHolder;
import com.centit.sys.mapper.SJZDMapper;
import com.centit.sys.model.UserBean;

public class InterUtil {
	/**
	 * 将null转化为""防止数据库插入 null
	 * 
	 * @param str
	 * @return
	 */
	public static String paseNullString(String str) {
		if (null == str || "null".equals(str)) {
			return "";
		}
		return str;
	}
	
	/**
	 * 替换sql的最后一个 逗号
	 * 
	 * @param sqlBuf
	 * @return
	 */
	public static StringBuffer replaceUpSql(StringBuffer sqlBuf) {
		String sql = sqlBuf.toString().trim();
		int length = sql.length() - 1;
		int index = sql.lastIndexOf(",");
		if (index == length) {
			sql = sql.substring(0, length);
		}
		return new StringBuffer(sql);
	}
	/**
	 * 检查必填字段是否有空
	 * @param strFld
	 * @param bodyMap
	 * @param msg
	 * @return
	 */
	public static boolean checkMustFld(String[] strFld, Map<String,Object> bodyMap,
			InterReturnMsg msg) {
		StringBuffer warin = new StringBuffer();
		for (int i = 0; i < strFld.length; i++) {
			String fldName = strFld[i];
			String fldValue = StringUtil.nullToSring(bodyMap.get(fldName));
			if (StringUtil.isEmpty(fldValue)) {
				warin.append("[" + fldName + "]");
			}
		}
		if (warin.toString().length() > 0) {
			warin.append("必填字段为空!");
			msg.setFLAG(BusinessConsts.FLASE);
			msg.setMESSAGE(warin.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * 检查必填字段是否有空(list)
	 * @param strFld
	 * @param bodyMap
	 * @param msg
	 * @return
	 */
	public static boolean checkMustFld(String[] strFld, Object[] object) throws Exception{
		try {
			for (int i = 0; i < object.length; i++) {
				Object obj=object[i];
				for (int j = 0; j < strFld.length; j++) {
					Method method=obj.getClass().getMethod("get"+strFld[j]);
					String value=String.valueOf(method.invoke(obj));
					if(StringUtil.isEmpty(value)){
						return false;
					}
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 检查必填字段是否有空(model)
	 * @param strFld
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static boolean checkMustFMd(String[] strFld, Object object) throws Exception{
		try {
			for (int j = 0; j < strFld.length; j++) {
				Method method=object.getClass().getMethod("get"+strFld[j]);
				String value=String.valueOf(method.invoke(object));
				if(StringUtil.isEmpty(value)){
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	/**
	 * 单条数据查询
	 */
	public static Map<String,String> selcom(String sql) {
		BaseService baseService = SpringContextHolder.getBean(BaseService.class);
		return baseService.selcom(sql);
	}
	
	/**
	 * 根据查询SQL返回int类型
	 * @param sql
	 * @return
	 */
	public static int getIntBySql(String sql){
		BaseService baseService = SpringContextHolder.getBean(BaseService.class);
		return baseService.getIntBySql(sql);
	}

	/**
	 * 多条数据查询
	 * 
	 * @param params
	 * @return
	 */
	public static List<Map<String,String>> selcomList(String sql) {
		BaseService baseService = SpringContextHolder.getBean(BaseService.class);
		return baseService.selcomList(sql);
	}
	
	/**
	 * 修改
	 * @param params
	 * @return
	 */
	public static boolean update(String sql) {
		BaseService baseService = SpringContextHolder.getBean(BaseService.class);
		baseService.update(sql);
		return true;
	}
	
	public static int getSeqByNo(String no){
		BaseService baseService = SpringContextHolder.getBean(BaseService.class);
		return baseService.getSeqByNo(no);
	}
	
	/**
	 * 新增
	 * @param params
	 * @return
	 */
	public static boolean insert(String sql) {
		BaseService baseService = SpringContextHolder.getBean(BaseService.class);
		baseService.insert(sql);
		return true;
	}
	
	/**
	 * 删除
	 * @param params
	 * @return
	 */
	public static boolean delete(String sql) {
		BaseService baseService = SpringContextHolder.getBean(BaseService.class);
		baseService.delete(sql);
		return true;
	}
	
	/**
	 * 根据表名和查询条件查询行数
	 * @param tab 表名
	 * @param con 查询条件字符串
	 * @return
	 */
	public static int getRowNum(String tab,String con){
		BaseService baseService = SpringContextHolder.getBean(BaseService.class);
		return baseService.getRowNum(tab,con);
	}
	
	/**
	 * U9状态信息转换
	 * @param strState
	 * @return
	 */
	public static String coveState(String strState) throws Exception {
		if (null == strState || "".equals(strState)) {
			strState = "-1";
		}
		int state = Integer.parseInt(strState);
		switch (state) {
		case 0:
			return "制定";
		case 1:
			return "启用";
		case 2:
			return "停用";
		default:
			return "启用";
		}
	}
	
	/**
	 * 检查是否有重复记录
	 * @param keyName 检查字段名
	 * @param keyValue 检查字段值
	 * @param tableName 检查表名
	 * @return
	 * @throws Exception
	 */
	public static boolean checkPrimarykey(String keyName, String keyValue,String tableName) throws Exception {
		StringBuffer payMentBuf = new StringBuffer();
		payMentBuf.append("SELECT ").append(keyName).append(" FROM ").append(
				tableName).append(" WHERE DEL_FLAG = 0 and ").append(keyName).append(" = '")
				.append(keyValue).append("'");
		System.out.println("checkPrimarykey==============="+ payMentBuf.toString());
		Map<String,String> map = selcom(payMentBuf.toString());
		if (map != null && map.size() > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 检查是否有重复记录(老数据 删除标记为DELFLAG)
	 * 
	 * @param mainKey
	 * @return
	 * @throws Exception
	 */
	public static boolean checkPrimarykeyOld(String keyName, String keyValue,String tableName) throws Exception {
		StringBuffer payMentBuf = new StringBuffer();
		payMentBuf.append("SELECT ").append(keyName).append(" FROM ").append(
				tableName).append(" WHERE DELFLAG=0 and ").append(keyName).append(" = '").append(keyValue).append("'");
		Map<String,String> map = selcom(payMentBuf.toString());
		if (map != null && map.size() > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 密码错误提示信息
	 * @return
	 */
	public static InterReturnMsg returnCheckUserFail(){
		InterReturnMsg msg = new InterReturnMsg();
		msg.setFLAG(BusinessConsts.FLASE);
		msg.setMESSAGE("用户名密码不正确!");
		return msg;
	}
	
	/**
	 * 提取错误信息
	 * @param ex
	 * @return
	 */
	public static String getErrorInfo(Exception ex){ 
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String errorInfo = ex.toString();
		if(errorInfo.length()>2900){
			errorInfo = errorInfo.substring(0, 2900);
		}
		return sw.toString();
	}
	
	/**
	 * 传参查询所需要的值
	 * @param param 条件  键为条件，值为条件查询值
	 * @param result 所需要查询的字段
	 * @param tableName  表名
	 * @return
	 */
	public static List<Map<String,String>> getSqlResult(Map<String,String> params,List<String> result,String tableName){
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		if(!result.isEmpty()){
			for (int i = 0; i < result.size(); i++) {
				sql.append(result.get(i)).append(",");
			}
			sql=replaceUpSql(sql);
		}
		sql.append(" from ").append(tableName).append(" where 1=1 ");
		if(!params.isEmpty()){
			 for (Map.Entry<String, String> param : params.entrySet()) {
				 sql.append(" and ").append(param.getKey()).append(" = '").append(param.getValue()).append("'");
			  }
		}
		System.err.println("checkSql=============================="+sql.toString());
		return selcomList(sql.toString());
	}
	/**
	 * 传参查询所需要的值
	 * @param param 条件  键为条件，值为条件查询值
	 * @param result 所需要查询的字段
	 * @param tableName  表名
	 * @param sqlStr 追加sql
	 * @return
	 */
	public static List<Map<String,String>> getSqlResult(Map<String,String> params,List<String> result,String tableName,String sqlStr){
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		if(!result.isEmpty()){
			for (int i = 0; i < result.size(); i++) {
				sql.append(result.get(i)).append(",");
			}
			sql=replaceUpSql(sql);
		}
		sql.append(" from ").append(tableName).append(" where 1=1 ");
		if(!params.isEmpty()){
			 for (Map.Entry<String, String> param : params.entrySet()) {
				 sql.append(" and ").append(param.getKey()).append(" = '").append(param.getValue()).append("'");
			  }
		}
		if(!StringUtil.isEmpty(sqlStr)){
			sql.append(sqlStr);
		}
		System.err.println("checkSql=============================="+sql.toString());
		return selcomList(sql.toString());
	}
	/**
	 * 接口传输，所有数据来自MDM
	 * @param type  add 新增，upd 修改
	 * @return
	 */
	public static Map<String, String> getCreInfo(String type){
		Map<String, String> map=new HashMap<String, String>();
		if("add".equals(type)){
			map.put("CREATOR", "MDM");//制单人
			map.put("CRE_NAME", "MDM");//制单人名称
			map.put("CRE_TIME", "sysdate");//制单时间
			map.put("DEPT_ID", "MDM");//制单部门
			map.put("DEPT_NAME", "MDM");//制单部门名称
			map.put("ORG_ID", "MDM");//制单机构
			map.put("ORG_NAME", "MDM");//制单机构名称
			map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);//删除标记
		}else if ("upd".equals(type)) {
			map.put("UPDATOR", "MDM");//修改人
			map.put("UPD_NAME", "MDM");//修改人名称
			map.put("UPD_TIME", "sysdate");//修改时间
		}
		return map;
	}
	
	/**
	 * 清空查询需要的对象
	 * @param params
	 * @param result
	 * @param resultList
	 */
	public static void clearObj(Map<String,String> params,List<String> result,List<Map<String,String>> resultList){
		params.clear();
		result.clear();
		resultList.clear();
	}
	/**
	 * 验证数据字典是否存在,如果不存在，则新增一条
	 * @param value
	 * @return
	 */
	public static void checkSJZDInfo(String SJXZ,String BASE_CODE,String SJZDBH){
		SJZDMapper mapper = SpringContextHolder.getBean(SJZDMapper.class);
		Map<String,String> params=new HashMap<String, String>();
		params.put("SJZDBH", SJZDBH);
		params.put("SJXZ", SJXZ);
		List<Map<String,String>> list=mapper.checkSJZDMXBySJXZ( params);
		//根据数据字典编号，值，查询是否有值，如果有值，则不操作
		if(list.isEmpty()){
			//如果没有值，则根据编码ID查询是否有值
			if(!StringUtil.isEmpty(BASE_CODE)){
				params.put("BASE_CODE", BASE_CODE);
			}
			list=mapper.checkSJZDMXById( params);
			//如果有值，说明修改，如果没有值，则是新增（值存在，code不存在，则是原数据，值不存在，code存在，则是修改)
			if(!list.isEmpty()){
				updateSJZDMXByService(SJZDBH, SJXZ, BASE_CODE);
			}else{
				insertSJZDMXByService(SJZDBH, SJXZ, BASE_CODE);
			}
		}
	}
	/**
	 * 新增数据字典明细
	 * @param SJZDBH 数据字典编号
	 * @param SJXMC 数据字典值
	 * @return
	 */
	public static boolean insertSJZDMXByService(String SJZDBH,String SJXZ,String BASE_CODE){
		Map<String,String> map=new HashMap<String, String>();
		map.put("SJZDBH", SJZDBH);
		map.put("SJXZ", SJXZ);
		map.put("BASE_CODE", BASE_CODE);
		map.put("SJZDMXID", StringUtil.uuid32len());
		map.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
		map.put("STATE", BusinessConsts.JC_STATE_ENABLE);
		map.put("CREATER", "MDM");
		SJZDMapper mapper = SpringContextHolder.getBean(SJZDMapper.class);
		mapper.insertSJZDMXByService( map);
		
		return true;
	}
	/**
	 * 修改数据字典明细
	 * @param SJZDBH 数据字典编号
	 * @param SJXMC 数据字典值
	 * @return
	 */
	public static boolean updateSJZDMXByService(String SJZDBH,String SJXZ,String BASE_CODE){
		Map<String,String> map=new HashMap<String, String>();
		map.put("SJZDBH", SJZDBH);
		map.put("SJXZ", SJXZ);
		map.put("BASE_CODE", BASE_CODE);
		map.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
		map.put("STATE", BusinessConsts.JC_STATE_ENABLE);
		SJZDMapper mapper = SpringContextHolder.getBean(SJZDMapper.class);
		mapper.updateSJZDMXByService(map);
		return true;
	}
	
	/**
	 * 删除基础数据时保留数据信息
	 * @param deleteTor 删除人ID
	 * @param businessType 删除模块类型
	 * @param map 删除对象
	 * @return
	 */
	public static boolean insertDataRecycle(String deleteTor,String businessType,Map<String,String> map){
		String fileName="DF";
		int con = 1;
		Map<String,String> param=new HashMap<String, String>();
		param.put("DATARECYCLEID", StringUtil.uuid32len());
		param.put("DELETOR", deleteTor);
		for (String key : map.keySet()) {
			param.put(fileName + con, key+":"+String.valueOf(map.get(key)));
			con++;
		}
		SyslogService mapper = SpringContextHolder.getBean(SyslogService.class);
		return mapper.insertDataRecycle(param);
	}
	
	
	/**
	 * 插入日志
	 * @param UC_NAME 模块名称
	 * @param ACT_NAME 操作名称
	 * @param OPRATOR 调用/被调用
	 * @param STATE 状态
	 * @param REMARK 备注
	 * @param APPCODE 调用方APPCODE
	 * @param APPID UID
	 * @param OPRCODE 服务码+操作码
	 * @param KEY 业务单据主键(没有就传空字符串)
	 * @param optContent 参数对象
	 */
	public static void actLog(String UC_NAME, String ACT_NAME, String OPRATOR,
			String STATE, String REMARK,String APPCODE,String APPID,String OPRCODE,String KEY,Object optContent) {
		System.err.println("Consts.ACT_LOG===" + Consts.ACT_LOG);
		System.err.println("Consts.REMARK===" +REMARK);
		if (Consts.ACT_LOG) {
			if (!"成功".equals(STATE))
				STATE = "失败";
			REMARK = StringUtil.splitStringAtBeginByte(REMARK,3000);
			SyslogService mapper = SpringContextHolder.getBean(SyslogService.class);
			if (null == mapper) {
				throw new ServiceException();
			}
			mapper.doActLog(UC_NAME, ACT_NAME, OPRATOR, STATE, REMARK, APPCODE, APPID, OPRCODE,KEY,JSONObject.toJSONString(optContent));
		}
	}
	

	
	/**
	 * 根据关联来源单据ID查询所有附件信息
	 * @param FROM_BILL_ID 关联来源单据ID
	 * @return
	 */
	public static List<Map<String,String>> findAttInfo(String FROM_BILL_ID){
		AttService mapper = SpringContextHolder.getBean(AttService.class);
		return mapper.findList(FROM_BILL_ID);
	}
	
	/**
	 * 根据关联来源单据ID查询所有附件信息 
	 * @param FROM_BILL_ID
	 * @return 多数据用逗号隔开显示
	 */
	public static String findAttStr(String FROM_BILL_ID){
		AttService mapper = SpringContextHolder.getBean(AttService.class);
		return mapper.findStr(FROM_BILL_ID);
	}
	
	/**
	 * 根据结果集校验文件名是否重复
	 * @param list
	 * @return
	 */
	public static void checkAttrName(List<String> list){
		if(list!=null){
			AttService mapper = SpringContextHolder.getBean(AttService.class);
			String names="";
			for (int i = 0; i < list.size(); i++) {
				//截取文件名称
				names += "'"+list.get(i).substring(list.get(i).lastIndexOf("/")+1,list.get(i).lastIndexOf("_"))+list.get(i).substring(list.get(i).lastIndexOf("."), list.get(i).length())+"',";
			}
			names = names.substring(0,names.length()-1);
			List<String> nameList = mapper.checkAttrName(names);
			if(!nameList.isEmpty()){
				
				throw new ServiceException("附件："+StringUtils.join(nameList.toArray(), ",")+"文件名重复");
			}
		}
	}
	
	/**
	 * 批量新增附件信息
	 * @param list 附件路径集合
	 * @param userBean 登录用户信息
	 * @param FROM_BILL_ID 关联来源ID
	 * @return
	 */
	public static boolean insertAttPath(List<String> list,UserBean userBean,String FROM_BILL_ID){
		AttService mapper = SpringContextHolder.getBean(AttService.class);
		mapper.insert(list, userBean, FROM_BILL_ID);
		return true;
	}
	
	/**
	 * 批量新增附件信息
	 * @param list 附件集合
	 * @param ATT_PATH 路径 ，SPARE1 备用字段1，SPARE2 备用字段2，SPARE3 备用字段3
	 * @param userBean 登录用户信息
	 * @param FROM_BILL_ID 关联来源ID
	 * @return
	 */
	public static boolean insertAttInfo(List<Map<String,String>> list,UserBean userBean,String FROM_BILL_ID){
		AttService mapper = SpringContextHolder.getBean(AttService.class);
		mapper.insertInfo(list, userBean, FROM_BILL_ID);
		return true;
	}
	
	/**
	 * 根据来源单删除所有附件
	 * @param FROM_BILL_ID
	 */
	public static boolean  delByFromId(String FROM_BILL_ID){
		AttService mapper = SpringContextHolder.getBean(AttService.class);
		mapper.delByFromId(FROM_BILL_ID);
		return true;
	}
	
	/**
	 * 根据附件ID删除附件
	 * @param attId 附件ID
	 */
	public static boolean delByAttId(String ATT_ID){
		AttService mapper = SpringContextHolder.getBean(AttService.class);
		mapper.delByAttId(ATT_ID);
		return true;
	}
	
	
}
