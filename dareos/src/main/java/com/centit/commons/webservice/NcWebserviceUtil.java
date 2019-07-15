package com.centit.commons.webservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONObject;
import com.centit.base.product.mapper.ProductMapper;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.DateUtil;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.SpringContextUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.utils.LogicUtil;
import com.centit.core.utils.TimeUtil;
import com.centit.drp.main.sales.deliver.order.model.OrderModel;
import com.centit.drp.main.sales.deliver.order.model.SendDtlModel;
import com.centit.erp.sale.saleorder.mapper.SaleorderMapper;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.erp.sale.saleorder.model.SaleorderModelChld;

/**
 * 
 * @ClassName: NcWebserviceUtil
 * @Description:调用NC的webservice接口工具类
 * @author lv_f
 * @date 2019年5月10日 下午3:07:19
 *
 */
public class NcWebserviceUtil {
	
	
	/**
	 * 
	 * @Title: UpdateSaleOrder
	 * @Description: 销售订单对接（创建、变更、关闭）
	 * @author lv_f
	 * @date 2019年5月14日 下午8:02:32
	 * @param @param type (1，创建；2变更；3，关闭)
	 * @param @param content 创建成功后返回的content值
	 * @param @param saleorderModel
	 * @param @param saleorderModelChldList
	 * @param @return
	 * @return Map<String, String>
	 * @throws
	 */
	public static Map<String, String> djSaleOrder(String type,String content,SaleorderModel saleorderModel,List<SaleorderModelChld> saleorderModelChldList){
		String uuid = StringUtil.uuid32len();
		Map<String, String> resultMap = new HashMap<String, String>();
		String transport = "";
    	for (int l = 0; l < BusinessConsts.TRANSPORT_NAME.length; l++) {
			if (BusinessConsts.TRANSPORT_NAME[l].equals(saleorderModel.getTRANSPORT())) {
				transport = BusinessConsts.TRANSPORT_CODE[l];
			}
		}
		saleorderModel.setTRANSPORT(transport);//NC-运输方式/OS-运输方式 （2：默认汽运方式）
		saleorderModel.setSALEDEPT_ID(BusinessConsts.DEFAULT_SALEDEPT_ID); // NC-部门id/OS-业务员部门ID (0502默认部门)
		ProductMapper productMapper = (ProductMapper)SpringContextUtil.getBean("productMapper");
//		if (!StringUtils.isNotBlank(saleorderModel.getPRE_RECV_DATE())) {
			Date orderDeliveryDate = DateUtil.parseDate(saleorderModel.getORDER_DELIVERY_DATE());//订单交期
			Date preRecvDate = DateUtil.addDays(orderDeliveryDate, 7);  //  预计到货日期
//			String preRecvDate = DateUtil.formatDateToStr(preRecvDate,"yyyy-MM-dd HH:mm:ss");
			String preRecvDateStr = DateUtil.formatDateToStr(preRecvDate,"yyyy-MM-dd"); 
			saleorderModel.setPRE_RECV_DATE(preRecvDateStr);     
//		}
		for (SaleorderModelChld chld : saleorderModelChldList) {
//			if (!StringUtils.isNotBlank(chld.getADVC_SEND_DATE())) { 
//			Date advcSendDate = DateUtil.addDays(orderDeliveryDate, 7); //  预计发货日期（订单交期）
//			String advcSendDateStr = DateUtil.formatDateToStr(advcSendDate,"yyyy-MM-dd"); 
//			chld.setADVC_SEND_DATE(advcSendDateStr);
//			}
			String unitNo = productMapper.queryNoByName(chld.getSTD_UNIT());
			chld.setSTD_UNIT(unitNo);
		}
		String reqXML = createSaleOrderXML(type,content,saleorderModel,saleorderModelChldList); 
		InterUtil.actLog("NC接口", "销售订单对接", "调用", "成功", saleorderModel.getSALE_ORDER_ID(), "", uuid, "", "", reqXML);
		
//		ArrayList<String> paramList = new ArrayList<String>();
		//paramList.add(Consts.GDZC_URL);
//		paramList.add(reqXML);
//		CommWebServiceClient ws = new CommWebServiceClient();
//		String param_url = "http://10.10.1.119:7788/service/XChangeServlet?account=DareTest2&groupcode=DareGlobal";
		String flag = "";
		String result = "";
		ArrayList<String> billpkList = new ArrayList<String>();
		try {
			NcHttpUrlUtil ncutil = new NcHttpUrlUtil();
			result = ncutil.SendXML(reqXML, Consts.PARAM_URL, billpkList);
//			System.out.println(result);
			if (StringUtils.isNotBlank(result)) {
				int index = result.indexOf("导入失败");
				if (index > -1) {
					flag = "false";
				} else {
					flag = "true";
				}
			} else {
				flag = "false";
				result = "网络出错！";
			}
//			String result = ws.callWebService(methodName,paramList);
		} catch (Exception e) {
			flag = "false";
			result = "网络出错！";
			//e.printStackTrace();
		}
		resultMap.put("flag", flag);
		resultMap.put("message", result);
		System.out.println(resultMap.get("flag"));
		System.out.println(resultMap.get("message"));
		if (flag.equals("true")) {
			InterUtil.actLog("NC接口", "销售订单对接", "调用", "成功", saleorderModel.getSALE_ORDER_ID(), "", uuid, "", "", result);
		} else {
			InterUtil.actLog("NC接口", "销售订单对接", "调用", "失败", saleorderModel.getSALE_ORDER_ID(), "", uuid, "", "", result);
		}
		return resultMap;
	}
	
	/**
	 * 删除销售订单
	 * @param saleorderModel
	 * @return
	 */
	public static Map<String, String> delSaleOrder(SaleorderModel saleorderModel){
		String uuid = StringUtil.uuid32len();
		String reqXML = delSaleOrderXml(saleorderModel);
		InterUtil.actLog("NC接口", "销售订单删除对接", "调用", "成功", saleorderModel.getSALE_ORDER_ID(), "", uuid, "", "", reqXML);
		Map<String, String> resultMap = new HashMap<String, String>();
		String flag = "";
		String result = "";
		ArrayList<String> billpkList = new ArrayList<String>();
		try {
			NcHttpUrlUtil ncutil = new NcHttpUrlUtil();
			result = ncutil.SendXML(reqXML, Consts.PARAM_URL, billpkList);
			if (StringUtils.isNotBlank(result)) {
				int index = result.indexOf("导入失败");
				if (index > -1) {
					flag = "false";
				} else {
					flag = "true";
				}
			} else {
				flag = "false";
				result = "网络出错！";
			}
		} catch (Exception e) {
			flag = "false";
			result = "网络出错！";
		}
		resultMap.put("flag", flag);
		resultMap.put("message", result);
		System.out.println(resultMap.get("flag"));
		System.out.println(resultMap.get("message"));
		if (flag.equals("true")) {
			InterUtil.actLog("NC接口", "销售订单删除对接", "调用", "成功", saleorderModel.getSALE_ORDER_ID(), "", uuid, "", "", result);
		} else {
			InterUtil.actLog("NC接口", "销售订单删除对接", "调用", "失败", saleorderModel.getSALE_ORDER_ID(), "", uuid, "", "", result);
		}
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Title: createSendOrder
	 * @Description: 发货通知单发货指令创建、变更
	 * @author lv_f
	 * @date 2019年5月14日 下午8:04:54
	 * @param @param type  (1,创建；2,变更)
	 * @param @param content
	 * @param @param ordelModel
	 * @param @param sendDtlList
	 * @param @return
	 * @return Map<String, String>
	 * @throws
	 */
	public static Map<String, String> djSendOrder(String type,String content,OrderModel ordelModel,List<SendDtlModel> sendDtlList){
		String uuid = StringUtil.uuid32len();
		Map<String, String> resultMap = new HashMap<String, String>();
		String transport = "";
    	for (int l = 0; l < BusinessConsts.TRANSPORT_NAME.length; l++) {
			if (BusinessConsts.TRANSPORT_NAME[l].equals(ordelModel.getTransport())) {
				transport = BusinessConsts.TRANSPORT_CODE[l];
			}
		}
		ordelModel.setTransport(transport);//NC-运输方式/OS-运输方式 （2：默认汽运方式）
		ordelModel.setSaledept_id(BusinessConsts.DEFAULT_SALEDEPT_ID); // NC-部门id/OS-业务员部门ID (0502默认部门)
		ProductMapper productMapper = (ProductMapper)SpringContextUtil.getBean("productMapper");
		for (SendDtlModel chld : sendDtlList) {
			String unitNo = productMapper.queryNoByName(chld.getStd_unit());
			chld.setStd_unit(unitNo);
		}
		String reqXML = createSendOrderXML(type,content,ordelModel,sendDtlList);
		InterUtil.actLog("NC接口", "发货通知单对接", "调用", "成功", ordelModel.getSend_order_id(), "", uuid, "", "", reqXML);
		
//		ArrayList<String> paramList = new ArrayList<String>();
		//paramList.add(Consts.GDZC_URL);
//		paramList.add(reqXML);
//		CommWebServiceClient ws = new CommWebServiceClient();
		String flag = "";
		String result = "";
		ArrayList<String> billpkList = new ArrayList<String>();
		try {
			NcHttpUrlUtil ncutil = new NcHttpUrlUtil();
			result = ncutil.SendXML(reqXML, Consts.PARAM_URL, billpkList);
//			System.out.println(result);
			if (StringUtils.isNotBlank(result)) {
				int index = result.indexOf("导入失败");
				if (index > -1) {
					flag = "false";
				} else {
					flag = "true";
				}
			} else {
				flag = "false";
				result = "网络出错！";
			}
//			String result = ws.callWebService(methodName,paramList);
		} catch (Exception e) {
			flag = "false";
			result = "网络出错！";
			//e.printStackTrace();
		}
		resultMap.put("flag", flag);
		resultMap.put("message", result);
		System.out.println(resultMap.get("flag"));
		System.out.println(resultMap.get("message"));
		if (flag.equals("true")) {
			InterUtil.actLog("NC接口", "发货通知单对接", "调用", "成功", ordelModel.getSend_order_id(), "", uuid, "", "", result);
		} else {
			InterUtil.actLog("NC接口", "发货通知单对接", "调用", "失败", ordelModel.getSend_order_id(), "", uuid, "", "", result);
		}
		return resultMap;
	}
	
	/**
	 * 删除发货指令
	 * @param type
	 * @param content
	 * @param ordelModel
	 * @param sendDtlList
	 * @return
	 */
	public static Map<String, String> delSendOrder(OrderModel ordelModel){
		String uuid = StringUtil.uuid32len();
		Map<String, String> resultMap = new HashMap<String, String>();
		String reqXML = delSendOrderXML(ordelModel);
		InterUtil.actLog("NC接口", "发货通知单删除对接", "调用", "成功", ordelModel.getSend_order_id(), "", uuid, "", "", reqXML);
		
		String flag = "";
		String result = "";
		ArrayList<String> billpkList = new ArrayList<String>();
		try {
			NcHttpUrlUtil ncutil = new NcHttpUrlUtil();
//			String url = "http://218.3.85.144:7788/service/XChangeServlet?account=DareTest2&groupcode=DareGlobal";
//			result = ncutil.SendXML(reqXML, url, billpkList);
			result = ncutil.SendXML(reqXML, Consts.PARAM_URL, billpkList);
			if (StringUtils.isNotBlank(result)) {
				int index = result.indexOf("导入失败");
				if (index > -1) {
					flag = "false";
				} else {
					flag = "true";
				}
			} else {
				flag = "false";
				result = "网络出错！";
			}
		} catch (Exception e) {
			flag = "false";
			result = "网络出错！";
		}
		resultMap.put("flag", flag);
		resultMap.put("message", result);
		System.out.println(resultMap.get("flag"));
		System.out.println(resultMap.get("message"));
		if (flag.equals("true")) {
			InterUtil.actLog("NC接口", "发货通知单删除对接", "调用", "成功", ordelModel.getSend_order_id(), "", uuid, "", "", result);
		} else {
			InterUtil.actLog("NC接口", "发货通知单删除对接", "调用", "失败", ordelModel.getSend_order_id(), "", uuid, "", "", result);
		}
		return resultMap;
	}
	
	
	public static void testSaleOrder() {
		String content = "";
		String type = "1";   // 1，创建；2变更；3，关闭
		SaleorderModel model = new SaleorderModel();
		if (!"1".equals(type)) {
			content = "1001E210000000441Y6J";
		}
		model.setSALE_ORDER_ID("testID1008");//
		model.setLEDGER_ID("116");//NC-销售组织/OS-帐套编号
		model.setFACTORY_NO("M0812-G1907-0227");//NC-单据号/OS-厂编
		model.setCRE_TIME("2019-06-11 10:30:00");//NC-单据日期/OS-制单时间
		model.setTRANSPORT("2");//NC-运输方式/OS-运输方式 （2：默认汽运方式）
		model.setCHANN_NO("C00000034");//NC-客户/OS-经销商编号(CHANN_NO还是CHANN_NAME?)
		model.setSALEDEPT_ID("0502"); // NC-部门id/OS-业务员部门ID (0502默认部门)
		model.setREMARK("测试test614953");//NC-备注/OS-备注
		model.setTOTAL_AMOUNT("1800");//订货总金额
		model.setORDER_DELIVERY_DATE("2019-07-05 10:30:00");//订单交期
//		model.setPRE_RECV_DATE("2019-06-20 10:30:00"); // 预计交货日期
		model.setUPD_TIME("2019-07-05 10:47:54");
		model.setBILL_TYPE("常规订单"); // 单据类型
	        
		SaleorderModelChld chld = new SaleorderModelChld();
//		chld.setSALE_ORDER_DTL_ID("testDtl1");//明细id
		chld.setPRD_NO("0145000001");//NC-物料编码/OS-产品编号
//		chld.setPRD_NAME("测试物料名称1"); //物料/产品名称
		chld.setSTD_UNIT("扇");//NC-主单位/OS-标准单位
		chld.setORDER_NUM("1");//NC-主数量/OS-订单数量
		chld.setPRICE("1000"); // 含税单价/单价
//		chld.setADVC_SEND_DATE("2019-06-19 10:30:00");
		chld.setREMARK("test备注11111");// 行备注/备注
		//门洞尺寸,规格型号,推向,材质,颜色,玻璃,其他,系列,工程位置,是否开锁孔
		chld.setHOLE_SPEC("2200*900*50");
		chld.setPRD_SPEC("2000*800*40");
		chld.setPRD_TOWARD("左推");
		chld.setPRD_QUALITY("黄橡锯纹MFC");
		chld.setPRD_COLOR("本色");
		chld.setPRD_GLASS("5MM清玻");
		chld.setPRD_OTHER("麻面");
		chld.setPRD_SERIES("混水");
		chld.setPRD_PLACE("卧室防火门");
		chld.setIS_NO_LOCK_FLAG("1");
		
		SaleorderModelChld chld2 = new SaleorderModelChld();
//		chld2.setSALE_ORDER_DTL_ID("testDtl2");//明细id
		chld2.setPRD_NO("0145000001");//NC-物料编码/OS-产品编号
//		chld2.setPRD_NAME("测试物料名称2"); //物料/产品名称
		chld2.setSTD_UNIT("扇");//NC-主单位/OS-标准单位
		chld2.setORDER_NUM("1");//NC-主数量/OS-订单数量
		chld2.setPRICE("800"); // 含税单价/单价
//		chld2.setADVC_SEND_DATE("2019-06-19 10:30:00");
		chld2.setREMARK("test备注22222");// 行备注/备注
		//门洞尺寸,规格型号,推向,材质,颜色,玻璃,其他,系列,工程位置,是否开锁孔
		chld2.setHOLE_SPEC("2200*1000*50");
		chld2.setPRD_SPEC("2150*900*40");
		chld2.setPRD_TOWARD("左推");
		chld2.setPRD_QUALITY("黄橡锯纹MFC");
		chld2.setPRD_COLOR("红橡16年1#色");
		chld2.setPRD_GLASS("MM双层钢化清玻");
		chld2.setPRD_OTHER("门线45度斜拼");
		chld2.setPRD_SERIES("混水");
		chld2.setPRD_PLACE("卧室防火门");
		chld2.setIS_NO_LOCK_FLAG("1");
	
		
		List<SaleorderModelChld> chldlist = new ArrayList<SaleorderModelChld>();
		chldlist.add(chld);
		chldlist.add(chld2);
		djSaleOrder(type,content,model,chldlist);
	}
	
	public static void testSendOrder() {
		String content = "";
		String type = "1";   // 1，创建；2变更；
		OrderModel model = new OrderModel();
		if (!"1".equals(type)) {
			content = "1001E2100000004400NE";
		}
		model.setSend_order_id("testSO1011");//
		model.setLedger_id("116");//NC-物流组织/OS-帐套编号
		model.setSaledept_id("01");//部门
		model.setCre_time("2019-06-12 18:30:00");//NC-单据日期/OS-制单时间
		model.setTransport("1");//NC-运输方式/OS-运输方式
		model.setRemark("测试test6141608");//NC-备注/OS-备注
		model.setChann_no("C00000034");//客户编码/经销商编号
		model.setFactory_no("C1001");//源头单据号/厂编
	        
		SendDtlModel chld = new SendDtlModel();
		chld.setRow_no("10");// 行号
		chld.setPrd_no("0101000001");//NC-物料编码/OS-产品编号
		chld.setStd_unit("BOX");//NC-主单位/OS-标准单位
		chld.setOrder_num("100");//NC-主数量/OS-订单数量
		chld.setPrice("100"); // 含税单价/单价
		chld.setRemark("test备注11111");// 行备注/备注
		
		SendDtlModel chld2 = new SendDtlModel();
		chld2.setRow_no("20");// 行号
		chld2.setPrd_no("0101000002");//NC-物料编码/OS-产品编号
		chld2.setStd_unit("BOX");//NC-主单位/OS-标准单位
		chld2.setOrder_num("100");//NC-主数量/OS-订单数量
		chld2.setPrice("100"); // 含税单价/单价
		chld2.setRemark("test备注11111");// 行备注/备注
		
		List<SendDtlModel> chldlist = new ArrayList<SendDtlModel>();
		chldlist.add(chld);
		chldlist.add(chld2);
		djSendOrder(type,content,model,chldlist);
	}
	
	/**
	 * 发货单删除接口
	 */
	public static void testdelSendOrder() {
		OrderModel orderModel = new OrderModel();
		orderModel.setSend_order_id("f59a2b52202b48bbb87ed0e286639473");
		orderModel.setNc_return_id("1001E2100000004489PN");
		orderModel.setLedger_id("116");
		orderModel.setCre_time(" 2019-07-10 14:50:21");
		orderModel.setSend_order_no("CB201907101427");
		
		delSendOrder(orderModel);
	}
	
	public static void main(String[] args) {
//		testSaleOrder();
//		testSendOrder();
//		testdelSendOrder();
	}
	
	/**
	 * 删除NC的销售订单
	 * @param saleorderModel
	 * @return
	 */
	private static String delSaleOrderXml(SaleorderModel saleorderModel){
		// 1.document构建器
        Document doc = DocumentHelper.createDocument();
        
        // 2.添加元素(根)
        Element ufinterfaceEle = doc.addElement("ufinterface");
        ufinterfaceEle.addAttribute("account", "DareTest");//固定
        ufinterfaceEle.addAttribute("billtype", "30");//固定
        ufinterfaceEle.addAttribute("filename", StringUtil.uuid32len() + ".xml");
        ufinterfaceEle.addAttribute("groupcode", "DareGlobal");//固定
        ufinterfaceEle.addAttribute("isexchange", "Y");//固定
        ufinterfaceEle.addAttribute("replace", "Y");//固定
        ufinterfaceEle.addAttribute("roottag", "");//固定
        ufinterfaceEle.addAttribute("sender", "DY");//固定
        
        Element bill = ufinterfaceEle.addElement("bill");
        bill.addAttribute("id", saleorderModel.getSALE_ORDER_ID());// 销售订单id
        Element billhead = bill.addElement("billhead");
        billhead.addElement("csaleorderid").setText(saleorderModel.getNC_ID()==null?"":saleorderModel.getNC_ID()); // nc返回的id
        billhead.addElement("pk_group").setText("DareGlobal"); // 
        billhead.addElement("pk_org").setText(saleorderModel.getLEDGER_ID()); // NC-销售组织/OS-帐套编号
        billhead.addElement("pk_org_v").setText(saleorderModel.getLEDGER_ID());//NC-销售组织/OS-帐套编号
        billhead.addElement("vbillcode").setText(saleorderModel.getFACTORY_NO()); // 
        billhead.addElement("fstatusflag").setText("1"); // 
        billhead.addElement("vdef20").setText("1");//
        billhead.addElement("so_saleorder_b").setText("");//
        System.out.println(doc.asXML());
		return doc.asXML();
	}
	
	/**
	 * 删除发货指令
	 * @param ordelModel
	 * @return
	 */
	private static String delSendOrderXML(OrderModel ordelModel){
		// 1.document构建器
        Document doc = DocumentHelper.createDocument();
        // 2.添加元素(根)
        Element ufinterfaceEle = doc.addElement("ufinterface");
        ufinterfaceEle.addAttribute("account", "001");// 固定
        ufinterfaceEle.addAttribute("billtype", "4331");//固定
        ufinterfaceEle.addAttribute("filename", StringUtil.uuid32len() + ".xml");
        ufinterfaceEle.addAttribute("groupcode", "DareGlobal");//固定
        ufinterfaceEle.addAttribute("isexchange", "Y");//固定
        ufinterfaceEle.addAttribute("replace", "Y");//固定
        ufinterfaceEle.addAttribute("roottag", "");//固定
        ufinterfaceEle.addAttribute("sender", "DY");//固定
        
        Element bill = ufinterfaceEle.addElement("bill");//
        bill.addAttribute("id", ordelModel.getSend_order_id());//主表id
        
        Element billhead = bill.addElement("billhead");
        billhead.addElement("cdeliveryid").setText(LogicUtil.nullToSring(ordelModel.getNc_return_id()));
        billhead.addElement("pk_group").setText("DareGlobal"); // 
        billhead.addElement("pk_org").setText(ordelModel.getLedger_id()); // NC-销售组织/OS-帐套编号
        billhead.addElement("pk_org_v").setText(ordelModel.getLedger_id());//NC-销售组织/OS-帐套编号
        billhead.addElement("fstatusflag").setText("1");//
        billhead.addElement("creationtime").setText(ordelModel.getCre_time()==null?"":ordelModel.getCre_time());//
        billhead.addElement("vbillcode").setText(ordelModel.getSend_order_no());//发货单号
        billhead.addElement("vdef20").setText("1");//
        billhead.addElement("dr").setText("1");//删除标记
        System.out.println(doc.asXML());
		return doc.asXML();
		
	}
	
	/**
	 * 
	 * @Title: createSaleOrderXML
	 * @Description: 生成创建销售订单xml字符串，以便调用webservice传参数
	 * @author lv_f
	 * @date 2019年5月13日 下午2:05:14
	 * @param @param ordelModel
	 * @param @param saleorderModelChldList
	 * @param @return
	 * @return String
	 * @throws
	 */
	private static String createSaleOrderXML(String type,String content,SaleorderModel saleorderModel,List<SaleorderModelChld> saleorderModelChldList){
		if("1".equals(type)) {
			content = "";
		}
		BigDecimal allSum = new BigDecimal("0");
		for (int i = 0;i < saleorderModelChldList.size();i++){
        	SaleorderModelChld chld = saleorderModelChldList.get(i);
        	allSum = new BigDecimal(chld.getORDER_NUM()).add(allSum);
		}
		// 1.document构建器
        Document doc = DocumentHelper.createDocument();
        // 2.添加元素(根)
        Element ufinterfaceEle = doc.addElement("ufinterface");
        ufinterfaceEle.addAttribute("account", "DareTest");// 测试环境
        ufinterfaceEle.addAttribute("billtype", "30");//固定（常规订单、常规样品、工程订单、 工程样品、内部销售、网络销售、清库销售、改补单）
        ufinterfaceEle.addAttribute("filename", StringUtil.uuid32len() + ".xml"); // 可空
        ufinterfaceEle.addAttribute("groupcode", "DareGlobal");//固定
        ufinterfaceEle.addAttribute("isexchange", "Y");//待定
        ufinterfaceEle.addAttribute("replace", "Y");//待定
        ufinterfaceEle.addAttribute("roottag", "");//待定
        ufinterfaceEle.addAttribute("sender", "DY");//DY为固定
        
        Element bill = ufinterfaceEle.addElement("bill");
        bill.addAttribute("id", saleorderModel.getSALE_ORDER_ID());// 销售订单id
        
        Element billhead = bill.addElement("billhead");
        billhead.addElement("csaleorderid").setText(LogicUtil.nullToSring(content)); // 创建成果返回的content值
        billhead.addElement("pk_group").setText("DareGlobal"); // 集团(固定)
        billhead.addElement("pk_org").setText(saleorderModel.getLEDGER_ID());//NC-销售组织/OS-帐套编号
        billhead.addElement("pk_org_v").setText(saleorderModel.getLEDGER_ID());//同上
        billhead.addElement("cinvoicecustid").setText(saleorderModel.getCHANN_NO());// NC-客户id/OS-经销商编号
        billhead.addElement("ctrantypeid").setText("30-Cxx-JJ-01");//NC-订单类型
        billhead.addElement("vtrantypecode").setText("30-Cxx-JJ-01");//NC-订单类型编码
        billhead.addElement("cbiztypeid").setText("SO-JJ-01"); // 业务流程 (固定)
        billhead.addElement("vbillcode").setText(saleorderModel.getFACTORY_NO());//NC-单据号/OS-厂编
        billhead.addElement("dbilldate").setText(saleorderModel.getCRE_TIME());//NC-单据日期/OS-制单时间
        billhead.addElement("ccustomerid").setText(saleorderModel.getCHANN_NO()); // NC-客户id/OS-经销商编号
        billhead.addElement("cdeptvid").setText(saleorderModel.getSALEDEPT_ID()); // NC-部门id/OS-业务员部门ID
        billhead.addElement("cdeptid").setText(saleorderModel.getSALEDEPT_ID());  // NC-部门id/OS-业务员部门ID
        billhead.addElement("bfreecustflag").setText("N"); // 是否散户
        billhead.addElement("corigcurrencyid").setText("CNY"); // 币种
        billhead.addElement("ctransporttypeid").setText(saleorderModel.getTRANSPORT());//NC-运输方式/OS-运输方式
        billhead.addElement("ndiscountrate").setText("100"); // 整单折扣
        billhead.addElement("badvfeeflag").setText("N"); // 代垫运费
        billhead.addElement("bpreceiveflag").setText("N"); // 收款限额控制预收
        billhead.addElement("ntotalnum").setText(allSum.toString()); // 总数量 
        billhead.addElement("ntotalweight").setText("0"); // 合计重量
        billhead.addElement("ntotalvolume").setText("0"); // 合计体积
        billhead.addElement("ntotalpiece").setText("0"); // 总件数
        billhead.addElement("ntotalorigmny").setText(saleorderModel.getTOTAL_AMOUNT()); // 价税合计 /订货总金额
        billhead.addElement("boffsetflag").setText("N"); // 是否冲抵
        billhead.addElement("bpocooptomeflag").setText("N"); // 由采购订单协同生成
        billhead.addElement("fpfstatusflag").setText("1"); // 审批流状态
        billhead.addElement("vnote").setText(LogicUtil.nullToSring(saleorderModel.getREMARK2()==null?"":saleorderModel.getREMARK2())); // 备注
        billhead.addElement("vdef10").setText(saleorderModel.getURGENCY_TYPE()==null?"":saleorderModel.getURGENCY_TYPE()); // 紧急程度
        billhead.addElement("vdef11").setText(saleorderModel.getDESIGNER_NAME()==null?"":saleorderModel.getDESIGNER_NAME()); // 分派设计师（设计师名称）
        if ("1".equals(type)) { // 创建
        	 billhead.addElement("billmaker").setText(saleorderModel.getCREATOR()); // 制单人
             billhead.addElement("dmakedate").setText(saleorderModel.getCRE_TIME()); // 制单日期
             billhead.addElement("creator").setText(saleorderModel.getCREATOR()); // 创建人
             billhead.addElement("creationtime").setText(saleorderModel.getCRE_TIME()); // 创建时间
             billhead.addElement("bsendendflag").setText("N"); // 发货关闭
             billhead.addElement("boutendflag").setText("N"); // 出库关闭
             billhead.addElement("binvoicendflag").setText("N"); // 开票关闭
             billhead.addElement("bcostsettleflag").setText("N"); // 成本结算关闭
             billhead.addElement("barsettleflag").setText("N"); // 收入结算关闭
        } else if ("2".equals(type)) { // 变更
        	billhead.addElement("status").setText("1"); // 状态
        	billhead.addElement("modifier").setText(saleorderModel.getUPDATOR()); // 修改人
            billhead.addElement("modifiedtime").setText(saleorderModel.getUPD_TIME()); // 
        	billhead.addElement("bsendendflag").setText("N"); // 发货关闭
            billhead.addElement("boutendflag").setText("N"); // 出库关闭
            billhead.addElement("binvoicendflag").setText("N"); // 开票关闭
            billhead.addElement("bcostsettleflag").setText("N"); // 成本结算关闭
            billhead.addElement("barsettleflag").setText("N"); // 收入结算关闭
        } else if ("3".equals(type)) { // 关闭
        	billhead.addElement("status").setText("1"); // 状态
        	billhead.addElement("bsendendflag").setText("Y"); // 发货关闭
            billhead.addElement("boutendflag").setText("Y"); // 出库关闭
            billhead.addElement("binvoicendflag").setText("Y"); // 开票关闭
            billhead.addElement("bcostsettleflag").setText("Y"); // 成本结算关闭
            billhead.addElement("barsettleflag").setText("Y"); // 收入结算关闭
        }
        billhead.addElement("iprintcount").setText("0"); // 打印次数
        String vdef2 = "";
    	for (int j = 0; j < BusinessConsts.BILL_TYPE_NAME.length; j++) {
			if (BusinessConsts.BILL_TYPE_NAME[j].equals(saleorderModel.getBILL_TYPE())) {
				vdef2 = BusinessConsts.BILL_TYPE_CODE[j];
			}
		}
    	billhead.addElement("vdef2").setText(vdef2);  // 单据类型
    	
    	String vdef3 = "";
    	
    	for (int k = 0; k < BusinessConsts.HANDLE_TYPE_NAME.length; k++) {
			if (BusinessConsts.HANDLE_TYPE_NAME[k].equals(saleorderModel.getPROESS_TYPE())) {
				vdef3 = BusinessConsts.HANDLE_TYPE_CODE[k];
			}
		}
    	billhead.addElement("vdef3").setText(vdef3);  // 处理类型
    	
    	String fstatusflag = "";
    	if (BusinessConsts.HANDLE_TYPE_CODE_XHX.equals(vdef3)) {
    		fstatusflag = "2";
    	} else {
    		fstatusflag = "1";
    	}
    	billhead.addElement("fstatusflag").setText(fstatusflag); // 单据状态（审核通过）
    	
    	billhead.addElement("chreceivecustid").setText(saleorderModel.getRECV_CHANN_ID()); // 收货客户
    	String vdef7 = saleorderModel.getRECV_ADDR()+";"+saleorderModel.getTEL()+";"+saleorderModel.getPERSON_CON();
    	billhead.addElement("vdef7").setText(vdef7); // 收货客户地址，电话，联系人
        Element cdeliverybid = billhead.addElement("so_saleorder_b");
        
        BigDecimal oneHundred = new BigDecimal("100");
        ProductMapper productMapper = (ProductMapper)SpringContextUtil.getBean("productMapper");
        for (int i = 0;i < saleorderModelChldList.size();i++){
        	SaleorderModelChld saleorderModelChld = saleorderModelChldList.get(i);
//        	BigDecimal ntaxrate = new BigDecimal("13.00");  
//        	String ctaxcodeid = "CESS01"; // 税码
        	String ntaxrateStr = "";
        	String ctaxcodeid = "";
        	Map<String, Object> slMap = productMapper.querySlByNo(saleorderModelChld.getPRD_NO());
        	if (slMap!=null) {
        		ntaxrateStr = slMap.get("TAXRATE")==null?"13.00":slMap.get("TAXRATE").toString();
        		ctaxcodeid = slMap.get("TAXCODE")==null?"CESS01":slMap.get("TAXCODE").toString();// 税码
        	} else {
        		ntaxrateStr = "13.00";
        		ctaxcodeid = "CESS01";
        	}
        	BigDecimal ntaxrate = new BigDecimal(ntaxrateStr);// 税率
        	
        	Element item = cdeliverybid.addElement("item");
        	if (!"1".equals(type)) { // 变更/关闭
        		item.addElement("status").setText("1"); // 状态
        	}	
//        	item.addElement("csaleorderbid").setText("");//明细id
        	item.addElement("vbdef1").setText(saleorderModelChld.getSALE_ORDER_DTL_ID()); // 销售订单明细id
        	item.addElement("pk_group").setText("DareGlobal");//集团（固定）
        	item.addElement("pk_org").setText(saleorderModel.getLEDGER_ID());//NC-销售组织/OS-帐套编号
        	item.addElement("crececountryid").setText("CN");
        	item.addElement("csendcountryid").setText("CN");
        	item.addElement("ctaxcountryid").setText("CN");
        	item.addElement("fbuysellflag").setText("1");
        	item.addElement("csendstockorgid").setText(saleorderModel.getLEDGER_ID());
        	item.addElement("csendstockorgvid").setText(saleorderModel.getLEDGER_ID());
        	item.addElement("ctrafficorgvid").setText(saleorderModel.getLEDGER_ID());
        	item.addElement("ctrafficorgid").setText(saleorderModel.getLEDGER_ID());
        	item.addElement("csettleorgvid").setText(saleorderModel.getLEDGER_ID());
        	item.addElement("csettleorgid").setText(saleorderModel.getLEDGER_ID());
        	item.addElement("carorgvid").setText(saleorderModel.getLEDGER_ID());
        	item.addElement("carorgid").setText(saleorderModel.getLEDGER_ID());
        	item.addElement("dbilldate").setText(saleorderModel.getCRE_TIME());//NC-单据日期/OS-制单时间
        	item.addElement("cmaterialvid").setText(saleorderModelChld.getPRD_NO());// 物料编码/产品编号
        	item.addElement("cmaterialid").setText(saleorderModelChld.getPRD_NO());// 物料编码/产品编号
        	item.addElement("cunitid").setText(saleorderModelChld.getSTD_UNIT());// 主单位/标准单位
        	item.addElement("castunitid").setText(saleorderModelChld.getSTD_UNIT());// 单位/标准单位
        	String nqtunitnum = saleorderModelChld.getORDER_NUM()==null?"0":saleorderModelChld.getORDER_NUM(); // 报价数量
        	item.addElement("nnum").setText(nqtunitnum);// 主数量
        	item.addElement("nastnum").setText(nqtunitnum);// 数量
        	item.addElement("vchangerate").setText("1/1");// 换算率
        	item.addElement("cqtunitid").setText(saleorderModelChld.getSTD_UNIT());// 报价单位
        	item.addElement("nqtunitnum").setText(nqtunitnum);// 报价单位数量
        	item.addElement("vqtunitrate").setText("1/1"); // 报价换算率
        	item.addElement("ndiscountrate").setText("100");// 整单折扣
        	item.addElement("nitemdiscountrate").setText("100");// 单品折扣
        	item.addElement("ctaxcodeid").setText(ctaxcodeid);// 税码(提供视图)
        	item.addElement("ntaxrate").setText(ntaxrate.toString());// 税率(提供视图)
        	item.addElement("ftaxtypeflag").setText("1");// 扣税类别
        	item.addElement("ccurrencyid").setText("CNY");// 本位币
        	item.addElement("nexchangerate").setText("1");// 折本汇率
        	
        	String nqtorigtaxprice = saleorderModelChld.getDECT_PRICE()==null?"0":saleorderModelChld.getDECT_PRICE(); // 含税单价
        	if ("0".equals(nqtorigtaxprice)) {
        		item.addElement("blargessflag").setText("Y");// 含税单价
        	}
        	item.addElement("nqtorigtaxprice").setText(nqtorigtaxprice);// 含税单价
        	
        	BigDecimal nqtorigprice = new BigDecimal(nqtorigtaxprice).divide(new BigDecimal("1").add(ntaxrate.divide(oneHundred,2, BigDecimal.ROUND_HALF_UP)),2, BigDecimal.ROUND_HALF_UP);// 无税单价
        	nqtorigprice = nqtorigprice.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("nqtorigprice").setText(nqtorigprice.toString());// 无税单价 (含税单价  / （1 + 税率）)
        	
        	BigDecimal nqtorigtaxnetprc = new BigDecimal(nqtorigtaxprice).multiply(new BigDecimal("1").multiply(new BigDecimal("1")));// 含税净价
        	nqtorigtaxnetprc = nqtorigtaxnetprc.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("nqtorigtaxnetprc").setText(nqtorigtaxnetprc.toString());// 含税净价 (含税单价 * （单品扣率 * 整单扣率）)
        	
        	BigDecimal nqtorignetprice = nqtorigtaxnetprc.divide(new BigDecimal("1").add(ntaxrate.divide(oneHundred,2, BigDecimal.ROUND_HALF_UP)),2,BigDecimal.ROUND_HALF_UP);// 无税净价
        	nqtorignetprice = nqtorignetprice.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("nqtorignetprice").setText(nqtorignetprice.toString());// 无税净价 (含税净价 / （1 + 税率）)
        	
        	BigDecimal norigtaxmny = new BigDecimal(nqtunitnum).multiply(nqtorigtaxnetprc); // 价税合计 
        	norigtaxmny = norigtaxmny.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("norigtaxmny").setText(norigtaxmny.toString());// 价税合计 （报价数量 * 含税净价）
        	
        	BigDecimal norigtaxnetprice = norigtaxmny.divide(new BigDecimal(nqtunitnum),2,BigDecimal.ROUND_HALF_UP); // 主含税净价
        	norigtaxnetprice = norigtaxnetprice.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("norigtaxnetprice").setText(norigtaxnetprice.toString());// 主含税净价（价税合计 / 主数量）
        	
        	BigDecimal norigtaxprice = norigtaxnetprice.divide(new BigDecimal("1").multiply(new BigDecimal("1")),2,BigDecimal.ROUND_HALF_UP);     // 主含税单价
        	norigtaxprice = norigtaxprice.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("norigtaxprice").setText(norigtaxprice.toString());// 主含税单价 （主含税净价 / （单品扣率 * 整单扣率））
        	
        	BigDecimal norigprice = norigtaxprice.divide(new BigDecimal("1").add(ntaxrate.divide(oneHundred,2, BigDecimal.ROUND_HALF_UP)),2,BigDecimal.ROUND_HALF_UP); // 主无税单价
        	norigprice = norigprice.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("norigprice").setText(norigprice.toString());// 主无税单价（主含税单价   / （1 + 税率））
        	
        	BigDecimal norignetprice = norigtaxnetprice.divide(new BigDecimal("1").add(ntaxrate.divide(oneHundred,2, BigDecimal.ROUND_HALF_UP)),2,BigDecimal.ROUND_HALF_UP);  // 主无税净价
        	norignetprice = norignetprice.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("norignetprice").setText(norignetprice.toString());// 主无税净价（主含税净价  / （1 + 税率））
        	
        	BigDecimal ntax = norigtaxmny.multiply(ntaxrate.divide(oneHundred,2, BigDecimal.ROUND_HALF_UP));// 税额
        	ntax = ntax.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("ntax").setText(ntax.toString());// 税额（价税合计 * 税率）
        	
        	BigDecimal ncaltaxmny = norigtaxmny.subtract(ntax); // 计税金额 
        	ncaltaxmny = ncaltaxmny.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("ncaltaxmny").setText(ncaltaxmny.toString());// 计税金额 （价税合计 - 税额）
        	item.addElement("norigmny").setText(ncaltaxmny.toString());// 无税金额  = 计税金额 （价税合计 - 税额）
        	
        	BigDecimal norigdiscount = (new BigDecimal(nqtunitnum).multiply(new BigDecimal(nqtorigtaxprice))).subtract(norigtaxmny);
        	norigdiscount = norigdiscount.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("norigdiscount").setText(norigdiscount.toString());// 折扣额(有则填，无则不填)（报价数量 * 含税单价 - 价税合计）
        	
        	item.addElement("nqttaxprice").setText(nqtorigtaxprice);// 本币含税单价（含税单价）
        	item.addElement("nqtprice").setText(nqtorigprice.toString());// 本币无税单价（无税单价）
        	item.addElement("nqttaxnetprice").setText(nqtorigtaxnetprc.toString());// 本币含税净价（含税净价）
        	item.addElement("nqtnetprice").setText(nqtorignetprice.toString());// 本币无税净价（无税净价）
        	item.addElement("ntaxprice").setText(nqtorigtaxprice);// 主本币含税单价（含税单价）
        	item.addElement("nprice").setText(nqtorigprice.toString());// 主本币无税单价（无税单价）
        	item.addElement("nnetprice").setText(nqtorignetprice.toString());// 主本币无税净价（无税净价）
        	item.addElement("nmny").setText(ncaltaxmny.toString());// 本币无税金额 （无税金额）
        	item.addElement("ntaxmny").setText(norigtaxmny.toString());// 本币价税合计（价税合计）
        	item.addElement("ndiscount").setText(norigdiscount.toString());// 本币折扣额(有则填，无则不填)（折扣额）
        	
        	item.addElement("blaborflag").setText("N");// 服务类
        	item.addElement("bdiscountflag").setText("N");// 折扣类
        	item.addElement("blargessflag").setText("N");// 赠品
        	item.addElement("bbindflag").setText("N");// 捆绑存货
        	item.addElement("dsenddate").setText(saleorderModel.getORDER_DELIVERY_DATE());// 要求发货日期/订单交期
        	item.addElement("dreceivedate").setText(saleorderModel.getPRE_RECV_DATE());// 计划到货日期/预计交货日期
        	item.addElement("creceivecustid").setText(saleorderModel.getCHANN_NO());// 收货客户/经销商编号
        	item.addElement("btriatradeflag").setText("N");// 三角贸易
        	item.addElement("frowstatus").setText("2");// 行状态
//        	item.addElement("frowstatus").setText(saleorderModelChld.getREMARK());// 行备注 (有则填，无则不填)
        	item.addElement("bjczxsflag").setText("N");// 借出转销售
        	if (new BigDecimal(nqtunitnum).compareTo(new BigDecimal("0")) < 0) {
        		item.addElement("fretexchange").setText("1");// 退换货标记
        	} else {
        		item.addElement("fretexchange").setText("0");// 退换货标记
        	}
        	item.addElement("flargesstypeflag").setText("1");// 赠品价格分摊方式
        	item.addElement("bprerowcloseflag").setText("N");// 预订单行关闭
        	
        	if ("3".equals(type)) {
        		item.addElement("bbsendendflag").setText("Y");// 发货关闭
            	item.addElement("bbinvoicendflag").setText("Y");// 开票关闭
            	item.addElement("bboutendflag").setText("Y");// 出库关闭
            	item.addElement("bbsettleendflag").setText("Y");// 结算关闭
            	item.addElement("bbcostsettleflag").setText("Y");// 成本结算关闭
            	item.addElement("bbarsettleflag").setText("Y");// 收入结算关闭
        	} else {
        		item.addElement("bbsendendflag").setText("N");// 发货关闭
            	item.addElement("bbinvoicendflag").setText("N");// 开票关闭
            	item.addElement("bboutendflag").setText("N");// 出库关闭
            	item.addElement("bbsettleendflag").setText("N");// 结算关闭
            	item.addElement("bbcostsettleflag").setText("N");// 成本结算关闭
            	item.addElement("bbarsettleflag").setText("N");// 收入结算关闭
        	}
        	item.addElement("barrangedflag").setText("N");//  是否货源安排完毕
        	//规格型号,推向,材质,颜色,玻璃,其他,系列,是否开锁孔
        	String is_no_lock_flag = "";
        	if ("1".equals(saleorderModelChld.getIS_NO_LOCK_FLAG())) {
        		is_no_lock_flag = "开锁孔";
        	}
        	//saleorderModelChld.getHOLE_SPEC()+"},{"+   +"},{"+saleorderModelChld.getPRD_PLACE()   去掉门洞尺寸,工程位置
        	String vfree =  saleorderModelChld.getPRD_SPEC()+"},{"+saleorderModelChld.getPRD_TOWARD()
        			  +"},{"+saleorderModelChld.getPRD_QUALITY()+"},{"+saleorderModelChld.getPRD_COLOR()+"},{"+saleorderModelChld.getPRD_GLASS()
        			  +"},{"+saleorderModelChld.getPRD_OTHER()+"},{"+saleorderModelChld.getPRD_SERIES()
        			  +"},{"+is_no_lock_flag;
        	
        	item.addElement("vbdef17").setText(saleorderModelChld.getPRD_SPEC());//规格尺寸
        	item.addElement("vbdef13").setText(saleorderModelChld.getPRD_TOWARD());//推向
        	item.addElement("vbdef14").setText(saleorderModelChld.getPRD_QUALITY());//材质
        	item.addElement("vbdef18").setText(saleorderModelChld.getPRD_COLOR());//颜色
        	item.addElement("vbdef19").setText(saleorderModelChld.getPRD_GLASS());//玻璃
        	item.addElement("vbdef20").setText(saleorderModelChld.getPRD_OTHER());//其他
        	item.addElement("vbdef16").setText(is_no_lock_flag);//是否开锁孔

        	item.addElement("vfree9").setText(vfree); // 自由属性
//        	item.addElement("cprojectid").setText(saleorderModel.getFACTORY_NO()); // 项目/单据号/厂编
        	Integer crowno = Integer.valueOf(saleorderModelChld.getROW_NO()) * 10;
        	item.addElement("crowno").setText(""+crowno); // 行号
        	item.addElement("vbdef2").setText(saleorderModelChld.getGROUP_NO()); // 组号
        	
        	String cproductorid = "";
        	for (int l = 0; l < BusinessConsts.XILIE_NAME.length; l++) {
    			if (BusinessConsts.XILIE_NAME[l].equals(saleorderModelChld.getPRD_SERIES())) {
    				cproductorid = BusinessConsts.XILIE_CODE[l];
    			}
    		}
        	item.addElement("cproductorid").setText(cproductorid); // 系列
        	item.addElement("vrownote").setText(saleorderModelChld.getREMARK()==null?"":saleorderModelChld.getREMARK());//表体备注
        	item.addElement("vdef8").setText(saleorderModelChld.getIS_NO_REBATE_FLAG()==null?"":saleorderModelChld.getIS_NO_REBATE_FLAG());//是否返利
        }
       
        System.out.println(doc.asXML());
		return doc.asXML();
		
	}
	
	/**
	 * 
	 * @Title: createSendOrderXML
	 * @Description: 生成发货通知单xml字符串，以便调用webservice传参数
	 * @author lv_f
	 * @date 2019年5月13日 下午1:29:21
	 * @param @param ordelModel
	 * @param @param sendDtlList
	 * @param @return
	 * @return String
	 * @throws
	 */
	private static String createSendOrderXML(String type,String content,OrderModel ordelModel,List<SendDtlModel> sendDtlList){
		System.out.println(JSONObject.toJSON(sendDtlList));
		if("1".equals(type)) {
			content = "";
//			ordelModel.setCheck_advice("审核通过");
		}
		// 1.document构建器
        Document doc = DocumentHelper.createDocument();
        // 2.添加元素(根)
        Element ufinterfaceEle = doc.addElement("ufinterface");
        ufinterfaceEle.addAttribute("account", "001");// 固定
        ufinterfaceEle.addAttribute("billtype", "4331");//固定
        ufinterfaceEle.addAttribute("filename", StringUtil.uuid32len() + ".xml");//暂定
        ufinterfaceEle.addAttribute("groupcode", "DareGlobal");//固定
        ufinterfaceEle.addAttribute("isexchange", "Y");//暂定
        ufinterfaceEle.addAttribute("replace", "Y");//暂定
        ufinterfaceEle.addAttribute("roottag", "");//暂定
        ufinterfaceEle.addAttribute("sender", "DY");//暂定
        
        Element bill = ufinterfaceEle.addElement("bill");//
        bill.addAttribute("id", ordelModel.getSend_order_id());//主表id
        
        Element billhead = bill.addElement("billhead");
        billhead.addElement("cdeliveryid").setText(LogicUtil.nullToSring(content));
        billhead.addElement("pk_group").setText("DareGlobal");
        billhead.addElement("pk_org").setText(ordelModel.getLedger_id());//NC-物流组织/OS-帐套编号
        billhead.addElement("pk_org_v").setText(ordelModel.getLedger_id());//NC-物流组织版本/OS-帐套编号
        billhead.addElement("cbiztypeid").setText("SO-JJ-01"); // 业务流程
        billhead.addElement("ctrantypeid").setText("4331-01");  // 发货类型
        billhead.addElement("vtrantypecode").setText("4331-01");// 发货类型编码
        billhead.addElement("dbilldate").setText(ordelModel.getCre_time()); // 单据日期
        billhead.addElement("csenddeptid").setText(ordelModel.getSaledept_id()); // 发货部门最新版本
        billhead.addElement("csenddeptvid").setText(ordelModel.getSaledept_id()); // 发货部门
        billhead.addElement("ctransporttypeid").setText(ordelModel.getTransport()); // 运输方式
        billhead.addElement("fstatusflag").setText("1"); // 单据状态 
        billhead.addElement("vnote").setText(LogicUtil.nullToSring(ordelModel.getRemark())); // 备注
        billhead.addElement("creator").setText(ordelModel.getCreator()==null?"":ordelModel.getCreator()); // 创建人
        billhead.addElement("billmaker").setText(ordelModel.getCreator()==null?"":ordelModel.getCreator()); // 制单人
        billhead.addElement("creationtime").setText(ordelModel.getCre_time()); // 创建时间
        String vdef7 = ordelModel.getRecv_addr()+";"+ordelModel.getTel()+";"+ordelModel.getPerson_con();
        billhead.addElement("vdef7").setText(vdef7); // 收货地址，电话，联系人
        billhead.addElement("vbillcode").setText(ordelModel.getSend_order_no()); // 发货单编号
        
        String vdef12 = "";
    	for (int j = 0; j < BusinessConsts.BILL_TYPE_NAME.length; j++) {
			if (BusinessConsts.BILL_TYPE_NAME[j].equals(ordelModel.getBill_type())) {
				vdef12 = BusinessConsts.BILL_TYPE_CODE[j];
			}
		}
    	billhead.addElement("vdef12").setText(vdef12); // 单据类型
    	
    	String vdef13 = "";
    	for (int k = 0; k < BusinessConsts.HANDLE_TYPE_NAME.length; k++) {
			if (BusinessConsts.HANDLE_TYPE_NAME[k].equals(ordelModel.getProess_type())) {
				vdef13 = BusinessConsts.HANDLE_TYPE_CODE[k];
			}
		}
        
        billhead.addElement("vdef13").setText(vdef13); // 处理类型
        String advice=ordelModel.getCheck_advice();
        if(StringUtil.isEmpty(advice))advice="审批通过";
        String vdef10 = ordelModel.getCre_name()+","+ordelModel.getCre_time()
        		+","+ordelModel.getUpd_name()+","+TimeUtil.getDateTime()+","+advice;
        
        billhead.addElement("vdef10").setText(vdef10); // 提交人、提交时间、审批人、审批时间、审批意见
        billhead.addElement("vdef20").setText("订单系统"); // 标记
        
        Element cdeliverybid = billhead.addElement("cdeliverybid");
        
        BigDecimal oneHundred = new BigDecimal("100");
        SaleorderMapper saleorderMapper = (SaleorderMapper)SpringContextUtil.getBean("saleorderMapper");
        ProductMapper productMapper = (ProductMapper)SpringContextUtil.getBean("productMapper");
        for (int i = 0;i < sendDtlList.size();i++){
        	SendDtlModel sendDtlModel = sendDtlList.get(i);
        	System.out.println(JSONObject.toJSON(sendDtlModel));
//        	BigDecimal ntaxrate = new BigDecimal("13.00");  // 税率
//        	String ctaxcodeid = "CESS01"; // 税码
        	String ntaxrateStr = "";
        	String ctaxcodeid = "";
        	Map<String, Object> slMap = productMapper.querySlByNo(sendDtlModel.getPrd_no());
        	if (slMap!=null) {
        		ntaxrateStr = slMap.get("TAXRATE")==null?"13.00":slMap.get("TAXRATE").toString();
        		ctaxcodeid = slMap.get("TAXCODE")==null?"CESS01":slMap.get("TAXCODE").toString();// 税码
        	} else {
        		ntaxrateStr = "13.00";
        		ctaxcodeid = "CESS01";
        	}
        	BigDecimal ntaxrate = new BigDecimal(ntaxrateStr);// 税率
        	
        	Element item = cdeliverybid.addElement("item");
        	item.addElement("vbdef16").setText(sendDtlModel.getSale_order_dtl_id()); // 销售订单明细
        	item.addElement("pk_group").setText("DareGlobal"); //所属集团
        	item.addElement("pk_org").setText(ordelModel.getLedger_id()); //NC-物流组织/OS-帐套编号
        	Integer crowno = (i+1) * 10;
        	item.addElement("crowno").setText(""+crowno); // 行号
        	item.addElement("cordercustid").setText(ordelModel.getChann_no()); //客户编码/经销商编号
        	item.addElement("cmaterialid").setText(sendDtlModel.getPrd_no()); // 物料档案/产品编号
        	item.addElement("cmaterialvid").setText(sendDtlModel.getPrd_no()); // 物料编码/产品编号
        	item.addElement("castunitid").setText(sendDtlModel.getStd_unit()); // 单位
        	item.addElement("dsenddate").setText(ordelModel.getCre_time()); // 单据日期
        	item.addElement("csendstockorgid").setText(ordelModel.getLedger_id());
        	String nqtunitnum = sendDtlModel.getSend_num()==null?"0":sendDtlModel.getSend_num(); // 报价数量
        	item.addElement("nastnum").setText(nqtunitnum); // 数量
        	item.addElement("cunitid").setText(sendDtlModel.getStd_unit()); //主单位
        	item.addElement("nnum").setText(nqtunitnum); //主数量
        	item.addElement("vchangerate").setText("1/1"); //换算率
        	item.addElement("cqtunitid").setText(sendDtlModel.getStd_unit()); //报价单位
        	item.addElement("nqtunitnum").setText(nqtunitnum); //报价数量
        	item.addElement("vqtunitrate").setText("1/1"); // 报价换算率
        	item.addElement("bcheckflag").setText("N"); //是否已报检
        	item.addElement("busecheckflag").setText("N"); //是否根据质检结果入库
        	item.addElement("corigcurrencyid").setText("CNY"); //原币
        	item.addElement("nexchangerate").setText("1"); //折本汇率
        	item.addElement("ccurrencyid").setText("CNY"); //本位币
        	item.addElement("ctaxcodeid").setText(ctaxcodeid); //税码（视图提供）
        	item.addElement("ntaxrate").setText(ntaxrate.toString()); //税率（视图提供）
        	
        	item.addElement("ftaxtypeflag").setText("0"); //扣税类别<!--1=应税外加，0=应税内含-->
        	item.addElement("ndiscountrate").setText("100"); //整单折扣
        	item.addElement("nitemdiscountrate").setText("100"); //单品折扣
        	
        	String nqtorigtaxprice = sendDtlModel.getDect_price()==null?"0":sendDtlModel.getDect_price(); // 含税单价
        	if ("0".equals(nqtorigtaxprice)) {
        		item.addElement("blargessflag").setText("Y");// 含税单价
        	}
        	item.addElement("nqtorigtaxprice").setText(nqtorigtaxprice); //含税单价
        	
        	BigDecimal nqtorigprice = new BigDecimal(nqtorigtaxprice).divide(new BigDecimal("1").add(ntaxrate.divide(oneHundred,2, BigDecimal.ROUND_HALF_UP)),2,BigDecimal.ROUND_HALF_UP);// 无税单价
        	nqtorigprice = nqtorigprice.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("nqtorigprice").setText(nqtorigprice.toString()); //无税单价(含税单价  / （1 + 税率）)
        	
        	BigDecimal nqtorigtaxnetprc = new BigDecimal(nqtorigtaxprice).multiply(new BigDecimal("1").multiply(new BigDecimal("1")));// 含税净价
        	nqtorigtaxnetprc = nqtorigtaxnetprc.setScale(2,BigDecimal.ROUND_HALF_UP);
        	
        	item.addElement("nqtorigtaxnetprc").setText(nqtorigtaxnetprc.toString()); //含税净价(含税单价 * （单品扣率 * 整单扣率）)
        	
        	BigDecimal nqtorignetprice = nqtorigtaxnetprc.divide(new BigDecimal("1").add(ntaxrate.divide(oneHundred,2, BigDecimal.ROUND_HALF_UP)),2,BigDecimal.ROUND_HALF_UP);// 无税净价
        	nqtorignetprice = nqtorignetprice.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("nqtorignetprice").setText(nqtorignetprice.toString()); //无税净价(含税净价  / （1 + 税率）)
        	
        	BigDecimal norigtaxmny = new BigDecimal(nqtunitnum).multiply(nqtorigtaxnetprc); // 价税合计 
        	norigtaxmny = norigtaxmny.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("norigtaxmny").setText(norigtaxmny.toString()); //价税合计（报价数量 * 含税净价）
        	
        	BigDecimal ntax = norigtaxmny.multiply(ntaxrate.divide(oneHundred,2, BigDecimal.ROUND_HALF_UP));// 税额
        	ntax = ntax.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("ntax").setText(ntax.toString()); //税额（价税合计 * 税率）
        	
        	BigDecimal ncaltaxmny = norigtaxmny.subtract(ntax); // 计税金额 
        	ncaltaxmny = ncaltaxmny.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("ncaltaxmny").setText(ncaltaxmny.toString()); //计税金额（价税合计 - 税额）
        	item.addElement("norigmny").setText(ncaltaxmny.toString()); //无税金额（价税合计 - 税额）
        	
        	BigDecimal norigdiscount = (new BigDecimal(nqtunitnum).multiply(new BigDecimal(nqtorigtaxprice))).subtract(norigtaxmny);//折扣额
        	norigdiscount = norigdiscount.setScale(2,BigDecimal.ROUND_HALF_UP);
        	item.addElement("norigdiscount").setText(norigdiscount.toString()); //折扣额(有则填，无则不填)（报价数量 * 含税单价 - 价税合计）
        	
        	item.addElement("nmny").setText(ncaltaxmny.toString()); //本币无税金额
        	
        	item.addElement("ntaxmny").setText(norigtaxmny.toString()); //本币价税合计
        	item.addElement("ndiscount").setText(norigdiscount.toString()); //本币折扣额
        	
        	item.addElement("norigtaxprice").setText(nqtorigtaxprice); //主含税单价
        	item.addElement("norigprice").setText(nqtorigprice.toString()); //主无税单价
        	item.addElement("norigtaxnetprice").setText(nqtorigtaxnetprc.toString()); //主含税净价
        	item.addElement("norignetprice").setText(nqtorignetprice.toString()); //主无税净价
        	
        	item.addElement("ntaxprice").setText(nqtorigtaxprice); //本币主含税单价
        	item.addElement("nprice").setText(nqtorigprice.toString()); //本币主无税单价
        	item.addElement("ntaxnetprice").setText(nqtorigtaxnetprc.toString()); //本币主含税净价
        	item.addElement("nnetprice").setText(nqtorignetprice.toString()); //本币主无税净价
        	
        	item.addElement("nqttaxprice").setText(nqtorigtaxprice); //本币含税单价
        	item.addElement("nqtprice").setText(nqtorigprice.toString()); //本币无税单价
        	item.addElement("nqttaxnetprice").setText(nqtorigtaxnetprc.toString()); //本币含税净价
        	item.addElement("nqtnetprice").setText(nqtorignetprice.toString()); //本币无税净价
        	
        	item.addElement("vsrctype").setText("30");
        	item.addElement("vfirsttype").setText("30"); //源头单据类型
        	item.addElement("vfirstcode").setText(ordelModel.getFactory_no()); //源头单据号/厂编
        	item.addElement("vfirsttrantype").setText("30-Cxx-JJ-01"); //源头交易类型
        	item.addElement("vsrccode").setText(ordelModel.getFactory_no()); //来源单据号/厂编
        	item.addElement("vsrctrantype").setText("30-Cxx-JJ-01"); //来源交易类型
        	Map<String, Object> soder = saleorderMapper.queryDtlByid(sendDtlModel.getSale_order_dtl_id());
        	Integer vsrcrowno = soder.get("ROW_NO")==null?0:Integer.valueOf(soder.get("ROW_NO").toString()) * 10;
        	item.addElement("vsrcrowno").setText(""+vsrcrowno); //来源单据行号
        	item.addElement("csettleorgid").setText(ordelModel.getLedger_id());
        	item.addElement("csendstockorgvid").setText(ordelModel.getLedger_id()); //发货库存组织
        	item.addElement("btransendflag").setText("N"); //运输关闭
        	item.addElement("boutendflag").setText("N"); //出库关闭
        	item.addElement("frownote").setText(sendDtlModel.getRemark()==null?"":sendDtlModel.getRemark()); //备注
        	item.addElement("crececountryid").setText("CN"); //收货国家/地区
        	item.addElement("csendcountryid").setText("CN"); //发货国家/地区
        	item.addElement("ctaxcountryid").setText("CN"); //报税国家/地区
        	item.addElement("fbuysellflag").setText("1"); //购销类型<!--1=国内销售，2=国内采购，3=出口，4=进口，5=不区分-->
        	item.addElement("btriatradeflag").setText("N"); //三角贸易
        	item.addElement("vbdef4").setText(LogicUtil.nullToSring(sendDtlModel.getGroup_no()));//组号
        	item.addElement("vbdef13").setText(sendDtlModel.getSend_order_dtl_id()); // 发货单明细id
        	
        	String cproductorid = "";
        	for (int l = 0; l < BusinessConsts.XILIE_NAME.length; l++) {
    			if (BusinessConsts.XILIE_NAME[l].equals(sendDtlModel.getPrd_series())) {
    				cproductorid = BusinessConsts.XILIE_CODE[l];
    			}
    		}
        	item.addElement("cproductorid").setText(cproductorid); // 系列
        	item.addElement("vbdef2").setText(sendDtlModel.getPrd_spec()==null?"":sendDtlModel.getPrd_spec());//规格尺寸
        	item.addElement("vbdef11").setText(sendDtlModel.getPrd_toward()==null?"":sendDtlModel.getPrd_toward());//推向
        	item.addElement("vbdef14").setText(sendDtlModel.getPrd_quality()==null?"":sendDtlModel.getPrd_quality());//材质
        	item.addElement("vbdef18").setText(sendDtlModel.getPrd_color()==null?"":sendDtlModel.getPrd_color());//颜色
        	item.addElement("vbdef19").setText(sendDtlModel.getPrd_glass()==null?"":sendDtlModel.getPrd_glass());//玻璃
        	item.addElement("vbdef20").setText(sendDtlModel.getPrd_other()==null?"":sendDtlModel.getPrd_other());//其他
        	String is_no_lock_flag = "";
        	if ("1".equals(sendDtlModel.getIs_no_lock_flag())) {
        		is_no_lock_flag = "开锁孔";
        	}
        	item.addElement("vbdef9").setText(is_no_lock_flag);//是否开锁孔
        }
       
        System.out.println(doc.asXML());
		return doc.asXML();
		
	}
	
}
