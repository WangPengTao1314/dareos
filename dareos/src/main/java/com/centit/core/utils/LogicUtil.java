package com.centit.core.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.centit.base.chann.service.impl.ChannServiceImpl;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.model.Properties;
import com.centit.commons.util.BmgzHelper;
import com.centit.commons.util.DateUtil;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.JesonImplUtil;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.BmgzService;
import com.github.pagehelper.Page;
import com.google.gson.Gson;

/**
 * @ClassName: LogicUtil
 * @Description: 通用方法函数
 * @author: zhu_hj
 * @date: 2018年5月3日 上午10:40:50
 */
public class LogicUtil {
	protected static Log logger = LogFactory.getLog(LogicUtil.class);

	@SuppressWarnings("unused")
	private static String callESBSynMethod = "doSyncTask"; //调用ESB同步方法

	/**
	 * @Title: transSort2String
	 * @Description: 将SpringDataJpa的分页对象中的排序字段解释成sql能直接执行的格式
	 * @author: zhu_hj
	 * @date: 2018年5月3日 上午10:43:50
	 * @param pageable
	 * @return
	 * @return: String
	 */
	public static String transSort2String(Pageable pageable) {
		String sort = null;
		if (pageable.getSort() != null) {
			sort = pageable.getSort().toString().replace(":", "");
		}
		return sort;
	}


	/**
	 * @Title: covertListTransBean2Map
	 * @Description: 将List<Object>转换成List<Map>
	 * @author: zhu_hj
	 * @date: 2018年5月3日 上午10:47:16
	 * @param objList
	 * @return: List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> covertListTransBean2Map( List<Object> objList) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Object obj : objList) {
			list.add(transBean2Map(obj));
		}
		return list;
	}

	/**
	 * @Title: transBean2Map
	 * @Description: 将Object转换成Map
	 * @author: zhu_hj
	 * @date: 2018年5月3日 上午10:46:57
	 * @param obj
	 * @return
	 * @return: Map<String,Object>
	 */
	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			logger.error("transBean2Map Error {}", e);
		}
		return map;
	}

	/**
	 * // Map --> Bean 1: 利用Introspector和PropertyDescriptor 将Map --> Bean
	 * @param map
	 * @param beanClass
	 * @return
	 */
	public static Object tranMap2Bean(Map<String, Object> map, Class<?> beanClass) {
		if (map == null) {
			return null;
		}
		Object obj = null;
		try {
			obj = beanClass.newInstance();
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				Method setter = property.getWriteMethod();
				if (setter != null) {
					String value = StringUtil.nullToSring(map.get(property.getName()));
					setter.invoke(obj, value);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		return obj;
	}

	public static Object tranMapValue2BeanSetter(String field, String value, Object obj) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String propertyName = property.getName();
				if (field.equals(propertyName)) {
					Method setter = property.getWriteMethod();
					if (setter != null) {
						setter.invoke(obj, value);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		return obj;
	}

	/**
	 * @Title: Array2String
	 * @Description: String数组按指定字符拼接成字符串
	 * @author: zhu_hj
	 * @date: 2018年5月3日 上午10:48:05
	 * @param arr
	 * @param pxChar
	 * @return
	 * @return: String
	 */
	public static String Array2String(String[] arr, String pxChar) {
		String str = "";
		if (arr != null && arr.length > 0) {
			for (int i = 0; i < arr.length; i++) {
				if (!str.equals(""))
					str += ",";
				if (StringUtils.isNotEmpty(pxChar))
					str += pxChar + arr[i] + pxChar;
				else
					str += arr[i];
			}
		}
		return str;
	}

	/**
	 * @Title: getFields
	 * @Description: 通过反射获取对象中的所有字段
	 * @author: zhu_hj
	 * @date: 2018年5月8日 下午2:12:08
	 * @param object
	 * @return
	 * @return: Field[]
	 */
	public static Field[] getFields(Object object) {
		return object.getClass().getDeclaredFields();
	}

	/**
	 * @Title: getFieldValue
	 * @Description: 通过反射获取对象中指定字段的值
	 * @author: zhu_hj
	 * @date: 2018年5月22日 下午2:39:07
	 * @param obj
	 * @param fieldName
	 * @return
	 * @return: Object
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field fd : fields) {
			if (fd.getName().equals(fieldName)) {
				return getFieldValue(obj, fd);
			}
		}
		return null;
	}

	/**
	 * @Title: getFieldValue
	 * @Description: 通过反射获取对象中指定字段的值
	 * @author: zhu_hj
	 * @date: 2018年5月8日 下午2:12:40
	 * @param obj
	 * @param field
	 * @return
	 * @return: Object
	 */
	public static Object getFieldValue(Object obj, Field field) {
		try {
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * @Title: createRandomUUID
	 * @Description: 生成32位UUID
	 * @author: zhu_hj
	 * @date: 2018年5月14日 下午2:39:07
	 * @return
	 * @return: String
	 */
	public static String createRandomUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 将空串转换成空字符串.
	 *
	 * @param obj
	 *            the obj
	 *
	 * @return the string
	 */
	public static String nullToSring(Object obj) {
		if (null == obj || "".equals(obj) || "null".equals(obj)) {
			return "";
		}
		return String.valueOf(obj);
	}

	/**
	 * 将string 转换为double
	 *
	 * @param str
	 * @return
	 */
	public static Double getDouble(Object obj) {
		Double d = 0d;
		String str = nullToSring(obj);
		try {
			if (StringUtils.isEmpty(str)) {
				d = 0d;
			}
			d = Double.valueOf(str);
		} catch (Exception e) {
			d = 0d;
		}
		return d;

	}

	/**
	 * 正则表达式验证
	 *
	 * @param str
	 *            字符串
	 * @param checkStr
	 *            正则表达式
	 * @return
	 */
	// 验证8位正整数和2位小数 String checkStr="[0-9]+\\.{0,1}[0-9]{0,2}";
	// 验证是否是数字 ^[-\\+]?[\\d]*$
	// 验证日期格式YYYY-MM-DD String checkDateStr="[0-9]{4}-[0-9]{2}-[0-9]{2}";
	public static boolean regexCheck(String str, String checkStr) {
		Pattern pattern = Pattern.compile(checkStr);
		Matcher flag = pattern.matcher(str);
		if (!flag.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 通过json传递数据的post请求 resourceParam 根据参数去system.properties内查询配置
	 */
	public static String sendPost(String param, String resourceParam) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = "";
		JSONObject jb = JSONObject.parseObject(CustomizedProperties
				.getContextProperty(resourceParam));
		try {
			URL realUrl = new URL(jb.getString("url"));
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("ClientId", jb.getString("ClientId"));
			conn.setRequestProperty("OperationCode",
					jb.getString("OperationCode"));
			conn.setRequestProperty("LoginCode", jb.getString("LoginCode"));
			conn.setRequestProperty("LoginPassword",
					jb.getString("LoginPassword"));
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			// 发送请求参数
			out.write(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 将请求参数转换成Map
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		Map properties = request.getParameterMap();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Iterator entries = properties.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String key = entry.getKey().toString();
			Object value = entry.getValue();
			if (value != null) {
				if (value instanceof String[] && ((String[]) value).length == 1)
					returnMap.put(key, request.getParameter(key));
				else
					returnMap.put(key, entry.getValue());
			}
		}
		return returnMap;
	}

	/**
	 *
	 * @Title: xmlToBean
	 * @Description: XML转换为javabean
	 * @author: liu_yg
	 * @date: 2019年2月25日 下午12:41:13
	 * @param xml
	 * @param load
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 * @return: Object
	 */
	public static Object xmlToBean(String xml, Class<?> load)
			throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(load);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		StringReader sr = new StringReader(xml);
		Object object = unmarshaller.unmarshal(sr);
		return object;
	}

	/**
	 *
	 * @Title: beanToXml
	 * @Description: javabean转换为XML
	 * @author: liu_yg
	 * @date: 2019年2月25日 上午10:49:39
	 * @param t
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 * @return: StringWriter
	 */
	public static <T> StringWriter beanToXml(T t) throws JAXBException,
			FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(t.getClass());
		Marshaller m = context.createMarshaller();
		StringWriter sw = new StringWriter();
		m.marshal(t, sw);
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化
		// m.marshal(t, new FileOutputStream("src/cn/mr/javabeanxml/test.xml"));
		return sw;
	}

	/**
	 *
	 * @Title: beanToXml
	 * @Description: javabean转换为XML
	 * @author: liu_yg
	 * @date: 2019年2月25日 下午12:36:48
	 * @param obj
	 * @param load
	 * @return
	 * @throws JAXBException
	 * @return: String
	 */
	public static String beanToXml(Object obj, Class<?> load)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(load);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString();
	}

	/**
	 *
	 * @Title: transPageHelper
	 * @Description: 根据mybatis分页插件返回的分页对象，转换成自定义的分页对象
	 * @author: liu_yg
	 * @date: 2019年2月26日 下午3:27:25
	 * @param pageDesc
	 * @param page
	 * @return: void
	 */
	public static void transPageHelper(PageDesc pageDesc, Page<?> page) {
    	//pageDesc.setPage(page.getPageNum());
    	//pageDesc.setPageSize(page.getPageSize());
		if(page.isCount()) {
			pageDesc.setTotal((int)page.getTotal());
	    	pageDesc.setPages(page.getPages());
		} else {
			double pages = (double)pageDesc.getTotal()/pageDesc.getPageSize();
			pageDesc.setPages((int)Math.ceil(pages));
		}
    	pageDesc.setStartRow(page.getStartRow());
    	pageDesc.setEndRow(page.getEndRow());
    	//pageDesc.setOrderBy(page.getOrderBy());
    	pageDesc.setResult(page.getResult());
    }

public static List<Map<String, String>> getAgencyWorkSql(UserBean userBean) {

		List<Map<String, String>> resList = new ArrayList<Map<String,String>>();
		String[] list = {"装修申请单审核","装修报销申请单审核","专卖店转分销店申请单审核","专卖店撤店及终止申请单审核","门店精致化检查结果审核",
		        "拓展拜访表审核","门店拜访表审核","加盟商拜访表审核","月度拜访计划审核","新开/翻新门店评估单审核","门店变更申请单审核 ",
		        "工作计划审核","个人工作计划审核","推广费用申请单审核","推广费用报销单审核","渠道销售指标设定审核","区域销售指标设定审核",
		        "销售数据上报审核","退货申请单审核","加盟商终止合作申请审核","分销商购货月报审核","分销渠道信息登记审核","营销经理日报表审核"," 加盟商营销经理评价表审核"};
		for(int i=0;i<list.length;i++){
			if("装修申请单审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select 'BS2A01' MENU_ID, '装修申请单审核' MENU_NAME,'decorationapp.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from  DRP_CHANN_RNVTN u where 1=1 and u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0031701","BS0030101","CHANN_RNVTN_ID", "DRP_CHANN_RNVTN","DRP_CHANN_RNVTN_AUDIT","STATE",userBean));
                sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
                sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("装修报销申请单审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select 'BS2A07' MENU_ID, '装修报销申请单审核' MENU_NAME,'/decorationreit.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from  DRP_RNVTN_REIT_REQ u where 1=1 AND u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0031801","BS0030301","RNVTN_REIT_REQ_ID", "DRP_RNVTN_REIT_REQ","DRP_RNVTN_REIT_REQ_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("专卖店转分销店申请单审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select 'BS2B02' MENU_ID, '专卖店转分销店申请单审核' MENU_NAME,'storetoretail.shtml?action=toFrame&module=sh' MENU_URL, count(0) NUM from  DRP_SPCL_STORE_TO_RETAIL_REQ u where 1=1 AND u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0031101","BS0030201","SPCL_STORE_TO_RETAIL_REQ_ID", "DRP_SPCL_STORE_TO_RETAIL_REQ","DRP_SPCL_STORE_TO_RETAIL_REQ_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}
			if("专卖店撤店及终止申请单审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select 'BS2B04' MENU_ID, '专卖店撤店及终止申请单审核' MENU_NAME,'storeclose.shtml?action=toFrame&module=sh' MENU_URL, count(0) NUM from  DRP_SPCL_STORE_CC_REQ u  where 1=1 AND u.DEL_FLAG=0 ");
                StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0030401","BS0030501","SPCL_STORE_CC_REQ_ID", "DRP_SPCL_STORE_CC_REQ","DRP_SPCL_STORE_CC_REQ_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("门店精致化检查结果审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select  'BS2D01' MENU_ID,'门店精致化检查结果审核' MENU_NAME,'termrefinecheck.shtml?action=toFrame&module=sh' MENU_URL, count(0) NUM from  TERM_REFINE_CHECK u where 1=1 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0032001","BS0031201","TERM_REFINE_CHECK_ID", "TERM_REFINE_CHECK","DRP_TERM_REFINE_CHECK_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("拓展拜访表审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select  'BS2F02' MENU_ID,'拓展拜访表审核' MENU_NAME,'expandvisit.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from  ERP_EXPAND_VISIT u where 1=1 AND u.DEL_FLAG=0");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0032801","BS0033101","EXPAND_VISIT_ID", "ERP_EXPAND_VISIT","ERP_EXPAND_VISIT_AUDIT","STATE",userBean));
				//sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("门店拜访表审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select  'BS2F04' MENU_ID,'门店拜访表审核' MENU_NAME,'storevisit.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from  ERP_STORE_VISIT u where 1=1 AND u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0033001","BS0033301","STORE_VISIT_ID", "ERP_STORE_VISIT","ERP_STORE_VISIT_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("加盟商拜访表审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select 'BS2F06' MENU_ID, '加盟商拜访表审核' MENU_NAME,'channvisit.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from  ERP_CHANN_VISIT u where 1=1 AND u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0032901","BS0033201","CHANN_VISIT_ID", "ERP_CHANN_VISIT","ERP_CHANN_VISIT_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
                //sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("月度拜访计划审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select  'BS2F08' MENU_ID,'月度拜访计划审核' MENU_NAME,'monthVisit.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from  ERP_MONTH_VISIT_PLAN u where 1=1 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0033401","BS0033501","MONTH_VISIT_PLAN_ID", "ERP_MONTH_VISIT_PLAN","ERP_MONTH_VISIT_PLAN_AUDIT","STATE",userBean));
				//sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
                Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("新开/翻新门店评估单审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select  'BS2G02' MENU_ID,'新开/翻新门店评估单审核' MENU_NAME,'openterminal.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from ERP_OPEN_TERMINAL_REQ u where 1=1 AND u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0032101","BS0032201","OPEN_TERMINAL_REQ_ID", "ERP_OPEN_TERMINAL_REQ","ERP_OPEN_TERMINAL_REQ_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID_P IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("门店变更申请单审核 ".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select  'BS2G04' MENU_ID,'门店变更申请单审核' MENU_NAME,'terminalchange.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from BASE_TERMINAL_CHANGE u where 1=1 AND u.DEL_FLAG =0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0032301","BS0032401","TERMINAL_CHANGE_ID", "BASE_TERMINAL_CHANGE","BASE_TERMINAL_CHANGE_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID_P IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("工作计划审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select 'BS2H02' MENU_ID, '工作计划审核' MENU_NAME,'workplanmage.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from ERP_WORK_PLAN u where 1=1 AND u.DEL_FLAG=0");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0032501","BS0032601","WORK_PLAN_ID", "ERP_WORK_PLAN","ERP_WORK_PLAN_AUDIT","STATE",userBean));
				//sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("个人工作计划审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select  'BS2H04' MENU_ID,'个人计划审核' MENU_NAME,'grgzjh.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from ERP_PER_WORK_PLAN  u  where 1=1 AND u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0033601","BS0033701","PER_WORK_PLAN_ID", "ERP_PER_WORK_PLAN","ERP_PER_WORK_PLAN_AUDIT","STATE",userBean));
				//sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("推广费用申请单审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select  'BS3A01' MENU_ID,'推广费用申请单审核' MENU_NAME,'promoexpen.shtml?action=toFrame&module=sh' MENU_URL, count(0) NUM from ERP_PRMT_COST_REQ u where 1=1 AND u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0020901","BS0020601","PRMT_COST_REQ_ID", "ERP_PRMT_COST_REQ","ERP_PRMT_COST_REQ_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}
			if("推广费用报销单审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select  'BS1I04' MENU_ID,'推广费用报销单审核' MENU_NAME,'expenseorder.shtml?action=toFrame&module=sh' MENU_URL, count(0) NUM from ERP_EXPENSE_ORDER u where 1=1 AND u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0022801","BS0023001","EXPENSE_ORDER_ID", "ERP_EXPENSE_ORDER","ERP_EXPENSE_ORDER_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("渠道销售指标设定审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select  'BS1F02' MENU_ID,'渠道销售指标设定审核' MENU_NAME,'saleplan.shtml?action=toFrame&module=sh' MENU_URL, count(0) NUM from ERP_SALE_PLAN u where 1=1 AND u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0021301","BS0021401","SALE_PLAN_ID", "ERP_SALE_PLAN","ERP_SALE_PLAN_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("区域销售指标设定审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select  'BS1F04' MENU_ID,'区域销售指标设定审核' MENU_NAME,'areasaleplan.shtml?action=toFrame&module=sh' MENU_URL, count(0) NUM from ERP_AREA_SALE_PLAN  u where 1=1 AND u.DEL_FLAG=0");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0021801","BS0021901","AREA_SALE_PLAN_ID", "ERP_AREA_SALE_PLAN","ERP_AREA_SALE_PLAN_AUDIT","STATE",userBean));
				//sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("销售数据上报审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select  'BS1G02' MENU_ID,'销售数据上报审核' MENU_NAME,'saledateup.shtml?action=toFrame&module=sh' MENU_URL, count(0) NUM from ERP_SALE_DATE_UP u where 1=1 AND u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0022701","BS0021701","SALE_DATE_UP_ID", "ERP_SALE_DATE_UP","DRP_SALE_DATE_UP_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}
			if("退货申请单审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select 'BS0H02' MENU_ID,'退货申请单审核' MENU_NAME, 'prdreturnreq.shtml?action=toFrame&module=sh' MENU_URL, count(0) NUM from DRP_PRD_RETURN_REQ u where 1=1 AND u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","FX0020701","BS0012101","PRD_RETURN_REQ_ID", "DRP_PRD_RETURN_REQ","DRP_PRD_RETURN_REQ_AUDIT","STATE",userBean));
				sql.append(" and RETURN_CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("加盟商终止合作申请审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select 'BS1M02' MENU_ID,'加盟商终止合作申请审核' MENU_NAME, 'distributerEndReq.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from ERP_DISTRIBUTOR_END_REQ u where 1=1 and u.DEL_FLAG=0");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0034801","BS0034901","CHANN_END_REQ_ID", "ERP_DISTRIBUTOR_END_REQ","ERP_CHANN_END_REQ_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("分销商购货月报审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select 'BS1K04' MENU_ID,'分销商购货月报审核' MENU_NAME, 'distributerSalerpt.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from ERP_DISTRIBUTOR_SALE_RPT u where 1=1 AND u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0034401","BS0035101","DISTRIBUTOR_SALE_RPT_ID", "ERP_DISTRIBUTOR_SALE_RPT","ERP_DISTRIBUTOR_SALE_RPT_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("分销渠道信息登记审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select 'BS1K02' MENU_ID,'分销渠道信息登记审核' MENU_NAME, 'distributerReq.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from ERP_DISTRIBUTOR_REQ u where 1=1 AND u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0034201","BS0034301","DISTRIBUTOR_REQ_ID", "ERP_DISTRIBUTOR_REQ","ERP_DISTRIBUTOR_REQ_AUDIT","STATE",userBean));
				sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("营销经理日报表审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select 'BS1J02' MENU_ID,'营销经理日报表审核' MENU_NAME, 'mkmdayreport.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from DRP_MKM_DAY_REPORT u where 1=1 and u.DEL_FLAG=0 ");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","BS0034601","BS0034701","MKM_DAY_RPT_ID", "DRP_MKM_DAY_REPORT","DRP_MKM_DAY_REPORT_AUDIT","STATE",userBean));
				//sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}

			if("加盟商营销经理评价表审核".equals(list[i].toString())){
				StringBuffer sql = new StringBuffer();
				sql.append("select 'BS1J04' MENU_ID,'加盟商营销经理评价表审核' MENU_NAME, 'channscoremkm.shtml?action=toFrames&module=sh' MENU_URL, count(0) NUM from DRP_CHANN_SCORE_MKM u where 1=1 AND u.DEL_FLAG=0");
				StringBuffer qx = new StringBuffer(ParamUtil.getPvgCon(null,"sh","FX0060301","BS0034501","CHANN_SCORE_MKM_ID", "DRP_CHANN_SCORE_MKM","DRP_CHANN_SCORE_MKM_AUDIT","STATE",userBean));
				//sql.append(" and CHANN_ID IN "+userBean.getCHANNS_CHARG());
				sql.append(" and "+qx.toString());
				Map<String,String> rstMap=InterUtil.selcom(sql.toString());
			    if(!"0".equals(StringUtil.nullToSring(rstMap.get("NUM"))))
				{
					 resList.add(rstMap);
				}
			}
		}
		return resList;
	}

	@SuppressWarnings("unchecked")
	public static <T> T StrToObj(String str,Class<?> clazz){
		return (T) JSON.parseObject(str,clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> T StrToArray(String str,Class<?>clazz){
		return (T) JSON.parseArray(str,clazz);
	}

	/**
	 * 获得图片服务器路径
	 * add by zhuxw 2013-1-23
	 * @param storeList
	 * @param storeDtlList
	 */
	public static String getPicPath(String fileName)  {
		String picServer = Properties.getString("PIC_SERVER_URL");
		String secondPath = Properties.getString("PICTURE_DIR");
		String picPath=picServer+secondPath+"/"+fileName;
		return picPath;
	}


	/**
	 * 获得图片服务器路径
	 * add by zzb 2014-8-30
	 * @param picServerURL 服务器地址
	 * @param fileName 文件名
	 */
	public static String getPicServerURL(String picServerURL,String fileName)  {
		String secondPath = Properties.getString("PICTURE_DIR");
		String picPath = picServerURL+secondPath+"/"+fileName;
		return picPath;
	}

	/**
	 * 该方法传入UserBean后把制单信息和帐套信息和删除标记封装成map返回
	 * @param userBean
	 * @return
	 */
	public static Map<String,String> sysFild(UserBean userBean){
		Map<String,String> map=new HashMap<String,String>();
		map.put("CRE_NAME",userBean.getXM());//制单人名称
		map.put("CREATOR",userBean.getXTYHID());//制单人ID
		map.put("DEPT_ID",userBean.getBMXXID());//制单部门ID
		map.put("DEPT_NAME",userBean.getBMMC());//制单部门名称
		map.put("ORG_ID",userBean.getJGXXID());//制单机构ID
		map.put("ORG_NAME",userBean.getJGMC());//制单机构名称
		map.put("CRE_TIME","sysdate");//制单时间
		map.put("LEDGER_ID",userBean.getLoginZTXXID());//帐套ID
		map.put("LEDGER_NAME",userBean.getLoginZTMC());//帐套名称
		map.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);//删除标记
		return map;
	}
	/**
	 *
	 * @param cellname 列名 字段编码
	 * @param tablename 表名
	 * @param Prefix 编码前缀
	 * @param length 段长度
	 * @param LAST_BILL_NO 上一个编号
	 * @param userBean
	 * @return
	 */
	public static String getBillSequenceNo(String cellname,String tablename,String Prefix,
			int length,String LAST_BILL_NO,UserBean userBean){
		BmgzService bmgzService = SpringContextHolder.getBean(BmgzService.class);
		String billNo = "";
		List<Map<String,String>> result = null;
		int lng = Prefix.length();
		String date = DateUtil.format(new Date(), "yyyyMMdd");
		String ORDER_NO = "";
		if(StringUtil.isEmpty(LAST_BILL_NO)){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("cellname", cellname);//列名 字段编码
			params.put("tablename", tablename);//表名
			params.put("date", date);
			params.put("index", lng+1);
			params.put("endIndex", date.length());
			if(null != userBean){
				params.put("LEDGER_ID", userBean.getLoginZTXXID());//帐套
			}
			result = bmgzService.queryMaxBillNo(params);
			if(null == result || result.isEmpty()){
				billNo = Prefix+date+getRoundStr(length,1);
				return  billNo;
			}else{
			    ORDER_NO = result.get(0).get(cellname);
			}

		}

		if(StringUtil.isEmpty(ORDER_NO)){
			ORDER_NO = LAST_BILL_NO;
		}
		String suffix = ORDER_NO.substring(lng+8,ORDER_NO.length());
		int num = StringUtil.getInteger(suffix);
		num = num+1;
		billNo =  Prefix+date+getRoundStr(length,num);
	    return billNo;
	}
	/**
	 * 获取编码规则.
	 * @param bmdx 编码对象
	 * @return 编码
	 * @throws ServiceException
	 */
	public static String getBmgz(String bmdx) throws ServiceException {
		logger.info("Enter LogicUtil.getBmgz().bmdx is " + bmdx);
		BmgzHelper bh = BmgzHelper.getInstance();
		if (null == bh) {
			logger.error("Init BmgzHelper.class failed.");
			throw new ServiceException();
		}
		return bh.createCode(bmdx);
	}

	/**
	 *
	 * @param cellname 列名 字段编码
	 * @param tablename 表名
	 * @param Prefix 编码前缀
	 * @param length 段长度
	 * @param userBean
	 * @return
	 */
	public static String getBillNo(String cellname,String tablename,String Prefix,int length,UserBean userBean){

		BmgzService bmgzService = SpringContextHolder.getBean(BmgzService.class);
		String billNo = "";
		//String Prefix = BusinessConsts.ADVC_ORDER_NO_PREFIX;//预订单 单头
		int lng = Prefix.length();
		String date = DateUtil.format(new Date(), "yyyyMMdd");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cellname", cellname);//列名 字段编码
		params.put("tablename", tablename);//表名
		params.put("date", date);
		params.put("index", lng+1);
		params.put("endIndex", date.length());
		if(null != userBean){
			params.put("LEDGER_ID", userBean.getLoginZTXXID());//帐套
		}
		List<Map<String,String>> result = bmgzService.queryMaxBillNo(params);
		if(null == result || result.isEmpty()){
			billNo = Prefix+date+getRoundStr(length,0);
		}else{
			String ORDER_NO = result.get(0).get(cellname);
			String suffix = ORDER_NO.substring(lng+8,ORDER_NO.length());
			int num = StringUtil.getInteger(suffix);
			num = num+1;
			billNo =  Prefix+date+getRoundStr(length,num);
		}

	    return billNo;
	}

	public static String getRoundStr(int leng,int num){
		String str  = Integer.toString(num);
		while(str.length()<leng){
			str = "0"+str;
		}
		return str;
	}

	/**
	 *
	 * @param cellname 列名 字段编码
	 * @param tablename 表名
	 * @param Prefix 编码前缀
	 * @param length 段长度
	 * @param LAST_BILL_NO 上一个编号
	 * @param userBean
	 * @return
	 */



	public static String getRYXXBillNo(String cellname,String tablename,String Prefix,int length,String subString,UserBean userBean){
		BmgzService bmgzService = SpringContextHolder.getBean(BmgzService.class);
		String billNo = "";
		int lng = Prefix.length();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cellname", cellname);//列名 字段编码
		params.put("tablename", tablename);//表名
		params.put("date", subString);
		params.put("index", 0);
		params.put("endIndex", subString.length());
		List<Map<String,String>> result =  bmgzService.queryMaxBillNo(params);
		if(null == result || result.isEmpty()){
			billNo = Prefix+getRoundStr(length,0);
		}else{
			String ORDER_NO = result.get(0).get(cellname);
			String suffix = ORDER_NO.substring(lng,ORDER_NO.length());
			int num = StringUtil.getInteger(suffix);
			num = num+1;
			billNo =  Prefix+getRoundStr(length,num);
		}

	    return billNo;
	}


	@SuppressWarnings(value = { "rawtypes", "unused", "unchecked" })
	public static String reutrnEsbInfo(String strJsonData){
		JesonImplUtil jsUtil = new JesonImplUtil(true,strJsonData);
		HashMap headMap =  jsUtil.getMbHead();
		HashMap resMap = jsUtil.getResponse();
		String Status = (String)resMap.get("Status");
		String Code = (String)resMap.get("Code");
		if(BusinessConsts.WS_SUCESS.equals(Code)){
			ArrayList bodyList = jsUtil.getMbBody();
        	String impJsonData = new Gson().toJson(bodyList);
        	return impJsonData;
		}else{
			ArrayList list  = new ArrayList();
			list.add(resMap);
			String resuData = new Gson().toJson(list);
        	return resuData;

		}
	}



	/**
	 * 根据所传渠道id查询返利折扣
	 * @param CHANN_ID  渠道ID
	 * @return
	 */
	public static Map<String,Object> getChannRebateDsct(String CHANN_ID){
		ChannServiceImpl channService = SpringContextHolder.getBean(ChannServiceImpl.class);
		Map<String,String> map = new HashMap<String, String>();
		map.put("CHANN_ID", CHANN_ID);
		map.put("DSCTTYPE", "返利折扣");
		Map<String,Object> result = channService.getChannRebateDsct( map);
		return result;
	}


	/**
	 * 根据所传渠道id查询返利折扣
	 * @param CHANN_ID  渠道ID
	 * @return
	 */
	public static List<Map<String,String>> getLedgerChrgList(String CHANN_ID){
		ChannServiceImpl channService = SpringContextHolder.getBean(ChannServiceImpl.class);
		List<Map<String,String>> result = channService.getLedgerChrgList( CHANN_ID);
		return result;
	}

	public static Map<String,String> getLedgerChrg(String CHANN_ID, String LEDGER_ID){
		if (StringUtil.isEmpty(LEDGER_ID)) {
			return null;
		}
		List<Map<String,String>> resultList = getLedgerChrgList(CHANN_ID);
		for (Map<String, String> result : resultList) {
			if (LEDGER_ID.equals(result.get("LEDGER_ID"))) {
				return result;
			}
		}
		return null;
	}

	/**
	 * 根据所传渠道id查询渠道折扣率，如果该渠道是惩罚渠道则取惩罚折扣，不然则取正常折扣
	 * @param CHANN_ID 当前登录人所属渠道
	 * @return 如果有折扣率，则返回折扣率字符串，如果没有，则返回null
	 */
	public static String getChannDsct(String CHANN_ID){
		ChannServiceImpl channService = SpringContextHolder.getBean(ChannServiceImpl.class);
		Map<String,String> map=new HashMap<String, String>();
		map.put("CHANN_ID", CHANN_ID);
		map.put("nrlDsct", BusinessConsts.DECT_TYPE);
		map.put("punDsct", "惩罚折扣");
		Map<String,Object> result = channService.getChannDsct( map);
		if(null == result){
			return null;
		}else{
			return StringUtil.nullToSring(result.get("DECT_RATE"));
		}
	}


	/**
	 * 查询flowNo[一级经销商-ORDER,二级经销商-SUB_ORDER]
	 * @param userBean
	 * @return flowNo
	 */
	@Deprecated
	public static String getFlowNo(UserBean userBean) {
		String flowNo = "";
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商，0：总部
		if ("0".equals(IS_DRP_LEDGER)) { // 订单中心 订货处理
			flowNo = BusinessConsts.GOOD_FLOW_NO;
		} else if ("1".equals(IS_DRP_LEDGER)) { // 经销商订货管理
			String CHANN_ID_P = userBean.getCHANN_ID_P();
			if (!StringUtil.isEmpty(CHANN_ID_P)) {
				// 下级经销商
				flowNo = BusinessConsts.GOOD_FLOW_NO_SUB;
			} else {
				// 一级经销商
				flowNo = BusinessConsts.GOOD_FLOW_NO;
			}
		}
		return flowNo;
	}

	/**
	 * 查询flowType[一级经销商-ORDER-0,二级经销商-SUB_ORDER-2]
	 * @param userBean
	 * @return flowNo
	 */
	public static String getFlowType(UserBean userBean) {
		String flowType = "";
		String IS_DRP_LEDGER = userBean.getIS_DRP_LEDGER(); // 1:经销商，0：总部
		if ("0".equals(IS_DRP_LEDGER)) { // 订单中心 订货处理
			flowType = "0";//BusinessConsts.GOOD_FLOW_NO;
		} else if ("1".equals(IS_DRP_LEDGER)) { // 经销商订货管理
			String CHANN_ID_P = userBean.getCHANN_ID_P();
			if (!StringUtil.isEmpty(CHANN_ID_P)) {
				// 下级经销商
				flowType = "2";//BusinessConsts.GOOD_FLOW_NO_SUB;
			} else {
				// 一级经销商
				flowType = "0";//BusinessConsts.GOOD_FLOW_NO;
			}
		}
		return flowType;
	}

	/**
	 * 根据单据主键获取该单据附件地址
	 * @param pkId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getAttPath(String pkId){
		String attPath = "";
		List<Map<String, String>> attList = InterUtil.findAttInfo(pkId);
		for (Iterator itAtt = attList.iterator(); itAtt.hasNext();) {
			Map<String, String> map = (Map<String, String>) itAtt.next();
			attPath += map.get("ATT_PATH");
			attPath += ",";
		}
		if (attPath.length() > 0) {
			attPath = attPath.substring(0, attPath.length()-1);
		}
		return attPath;
	}

	/**
	 *
	 * @param ledgerId 帐套ID
	 * @param preRecvDate 交期
	 * @param billType 订单类型
	 * @return 系统标识+交期-类型+年月-4位流水号
	 */
	public static String getFactoryNo(String ledgerId,String preRecvDate,String billType){
		String xtbs = Consts.FACTORY_NO_CONF.getString(ledgerId);//系统标识

		String date = TimeUtil.getDateTime();//当前时间
		if(StringUtil.isEmpty(preRecvDate)){
			preRecvDate = date;
		}

		String jq = patchZero(TimeUtil.getMonth(preRecvDate),2,"0") + "" + patchZero(TimeUtil.getDay(preRecvDate),2,"0");//交期

		//根据订单类型获取数据字典里对应的数据项代码
		String sql = "select KEYCODE from T_SYS_SJZDMX where SJZDID = 'SALE_TYPE' and SJXMC = '"+billType+"'";

		String lx = InterUtil.selcom(sql).get("KEYCODE");//类型

		String ny =  patchZero(String.valueOf(TimeUtil.getYear(date)).substring(2,4),2,"0") + "" + patchZero(TimeUtil.getMonth(date),2,"0");//年月

		int seq = InterUtil.getSeqByNo("FACTORY_NO_SEQ");
		return xtbs + jq + "-" + lx + ny + "-" + patchZero(seq,4,"0");
		/**
		//获取流水号 获取 系统标识+%-%+年月- 的流水号
		sql = "select FACTORY_NO from ERP_SALE_ORDER where FACTORY_NO like '"+xtbs+"%-%"+ny+"' order by FACTORY_NO desc ";
		Map<String,String> map = InterUtil.selcom(sql);
		if(map==null||map.isEmpty()){
			return xtbs + jq + "-" + lx + ny + "-" + "001";
		}else{
			String ls = map.get("FACTORY_NO");
			 ls = patchZero((Integer.parseInt(ls) + 1),3,"0");
			return xtbs + jq + "-" + lx + ny + "-" + ls;
		}
		*/
	}
	
	/**
	 * 获取返修单单号
	 * @param ledgerId
	 * @return 组织标识+年月-2位流水号-SF
	 */
	public static String getReworkNo(String ledgerId){
		String xtbs = Consts.FACTORY_NO_CONF.getString(ledgerId);//系统标识
		String date = TimeUtil.getDateTime();//当前时间
		String ny =  patchZero(String.valueOf(TimeUtil.getYear(date)).substring(2,4),2,"0") + "" + patchZero(TimeUtil.getMonth(date),2,"0");//年月
		String no=xtbs+ny+"-";
		//获取反馈单最大流水
		String sql = "select max(FACTORY_NO) FACTORY_NO from ERP_SALE_ORDER where BILL_TYPE='返修订单' and FACTORY_NO like '"+no+"%' ";
		Map<String,String> map = InterUtil.selcom(sql);
		String con="";
		if(map==null||map.isEmpty()){
			con = "01";
		}else{
			con = patchZero(Integer.parseInt(String.valueOf(map.get("FACTORY_NO").substring(6,8)))+1,2,"0");
		}
		return no + con + "-" + "SF";
	}
	
	/**
	 * 生成反馈单单号
	 * @param ledgerId
	 * @return 组织标识+年月-3位流水号-FK
	 */
	public static String getFeedbackNo(String ledgerId){
		String xtbs = Consts.FACTORY_NO_CONF.getString(ledgerId);//系统标识
		String date = TimeUtil.getDateTime();//当前时间
		String ny =  patchZero(String.valueOf(TimeUtil.getYear(date)).substring(2,4),2,"0") + "" + patchZero(TimeUtil.getMonth(date),2,"0");//年月
		String no=xtbs+ny+"-";
		//获取反馈单最大流水
		String sql = "select max(PROBLEM_FEEDBACK_NO) PROBLEM_FEEDBACK_NO from DRP_PROBLEM_FEEDBACK where  PROBLEM_FEEDBACK_NO like '"+no+"%' ";
		Map<String,String> map = InterUtil.selcom(sql);
		String con="";
		if(map==null||map.isEmpty()){
			con = "001";
		}else{
			con = patchZero(Integer.parseInt(String.valueOf(map.get("PROBLEM_FEEDBACK_NO")).substring(6,9))+1,3,"0");
		}
		return no + con + "-" + "FK";
	}

	/**
	 * 补位
	 * @param str 要补的字符串
	 * @param num 需要的位数
	 * @param patchStr 补的字符串
	 * @return
	 */
	public static String patchZero(Object obj,int num,String patchStr){
		String str = String.valueOf(obj);
		int con = num - str.length();
		for (int i = 0; i < con; i++) {
			str = patchStr+str;
		}
		return str;
	}

	/**
	 * 根据用户权限获取销售订单/要货单的状态过滤
	 * @param userBean
	 * @param qx 菜单查询权限
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getOrderQxByUserBean(UserBean userBean,String qx){
		String sql = "";
		List<String> list=new ArrayList<String>(userBean.getQXMap().keySet());
		qx = qx.substring(0,qx.length()-2);
		JSONObject json=Consts.FIRST_TO_DO_MENUINFO;
		for (int i = 0; i < list.size(); i++) {
			if(qx.equals(list.get(i).substring(0,list.get(i).length()-2))){
				JSONObject js = json.getJSONObject(list.get(i));
				if(js!=null){
					Map<String,String> map=LogicUtil.StrToObj(js.toString(), Map.class);
					if(map.get("state").indexOf(",")>0){
						sql += map.get("state")+",";
					}else{
						sql += "'"+ map.get("state") + "',";
					}
				}
			}
		}
		if(!StringUtil.isEmpty(sql)){
			sql = sql.substring(0,sql.length()-1);
		}
		return sql;
	}

	/**
	 * 只有设计师(无价格)权限，不可以查看价格，除此之外还有其他权限的则可以查看价格
	 * @param XTJSIDs
	 * @return 角色IDs字符串去除设计师(无价格)的ID之后的字符串长度
	 */
	public static int isShowPrice(String XTJSIDs) {
		String xtjsids = XTJSIDs;
		xtjsids = xtjsids.replace(BusinessConsts.NOPRICE_XT, "")
						.replace(BusinessConsts.NOPRICE_FSTDR, "")
						.replace(BusinessConsts.NOPRICE_SECDR, "")
						.replace(BusinessConsts.DESIGNER_XTJSID, "")
						.replace(",''","")
						.replace("'',","")
						.replace("''","");
		return xtjsids.length();
	}
	
	/**
	 * 根据用户字段判断是显示工程订单还是查询其余订单
	 * @param userBean
	 * @param alias 表别名
	 * @return
	 */
	public static String getProjectQx(UserBean userBean,String alias){
		if(!StringUtil.isEmpty(alias)){
			alias+=".";
		}else{
			alias="";
		}
		String sql =" and "+alias+"BILL_TYPE ";
		if(!StringUtil.isEmpty(userBean.getYHYWLX())){
			String[] yhywlxArray = userBean.getYHYWLX().split(",");
			sql += " in (";
			for (int i = 0; i < yhywlxArray.length; i++) {
				sql+="'"+yhywlxArray[i]+"',";
			}
			sql = sql.substring(0, sql.length()-1);
			sql+=")";
			return sql;
		}
		return "";
	}
	
	
	//相加
    public static double add(Object d1,Object d2){
        BigDecimal b1=new BigDecimal(String.valueOf(d1));
        BigDecimal b2=new BigDecimal(String.valueOf(d2));
        return b1.add(b2).doubleValue();

    }
    //相减
    public static double sub(Object d1,Object d2){
        BigDecimal b1=new BigDecimal(String.valueOf(d1));
        BigDecimal b2=new BigDecimal(String.valueOf(d2));
        return b1.subtract(b2).doubleValue();
    }
    
    //相乘
    public static double multiply(Object d1,Object d2){
        BigDecimal b1=new BigDecimal(String.valueOf(d1));
        BigDecimal b2=new BigDecimal(String.valueOf(d2));
        return b1.multiply(b2).doubleValue();
    }

}
