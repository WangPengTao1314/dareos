package com.centit.commons.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.StringUtil;
import com.centit.commons.model.Consts;
import com.centit.core.utils.LogicUtil;
import com.centit.erp.sale.saleorder.model.SaleorderModel;

/**
 * 
 * @ClassName: NcWebserviceUtil
 * @Description:调用NC的webservice接口工具类
 */
public class NcFoWebserviceUtil {
	
	
	/**
	 * 
	 * @Title: UpdateSaleOrder
	 * @Description: 销售订单对接（创建、变更、关闭）
	 * @param @param saleorderModel
	 * @param @param saleorderModelChldList
	 * @param @return
	 * @return Map<String, String>
	 * @throws
	 */
	public static Map<String, String> djSaleFactoryNO(String type,String content,SaleorderModel saleorderModel){
		String uuid = StringUtil.uuid32len();
		Map<String, String> resultMap = new HashMap<String, String>();
		String reqXML = createSaleFactoryNOXML(type,content,saleorderModel); 
		InterUtil.actLog("NC接口", "销售订单厂编对接", "调用", "成功", saleorderModel.getSALE_ORDER_ID(), "", uuid, "", "", reqXML);
		
//		ArrayList<String> paramList = new ArrayList<String>();
		//paramList.add(Consts.GDZC_URL);
//		paramList.add(reqXML);
//		CommWebServiceClient ws = new CommWebServiceClient();
		//测试地址
//		String param_url = "http://218.3.85.144:7788/service/XChangeServlet?account=DareTest2&groupcode=DareGlobal";
//		String param_url = "http://218.3.85.187:80/service/XChangeServlet?account=0001&groupcode=DareGlobal";
		String flag = "";
		String result = "";
		ArrayList<String> billpkList = new ArrayList<String>();
		try {
			NcHttpUrlUtil ncutil = new NcHttpUrlUtil();
			//测试
//			result = ncutil.SendXML(reqXML, param_url, billpkList);
			//正式
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
			InterUtil.actLog("NC接口", "销售订单厂编对接", "调用", "成功", saleorderModel.getSALE_ORDER_ID(), "", uuid, "", "", result);
		} else {
			InterUtil.actLog("NC接口", "销售订单厂编对接", "调用", "失败", saleorderModel.getSALE_ORDER_ID(), "", uuid, "", "", result);
		}
		return resultMap;
	}
	
	
	public static void testSaleOrder() {
		String content = "";
		String type = "1";   // 1，创建；2变更；3，关闭
		SaleorderModel model = new SaleorderModel();
		model.setSALE_ORDER_ID("testID1008");//
		model.setLEDGER_ID("116");//NC-销售组织/OS-帐套编号
		model.setFACTORY_NO("M0812-G1907-0226");//NC-单据号/OS-厂编
		model.setCRE_TIME("2019-07-4 22:30:00");//NC-单据日期/OS-制单时间
		model.setCREATOR("20091020001");//NC-单据日期/OS-制单时间
		model.setCHANN_NAME("南通长轮房屋开发有限公司（南通雍华府）");//NC-客户/OS-经销商编号(CHANN_NO还是CHANN_NAME?)

		djSaleFactoryNO(type,content,model);
	}
	

	public static void main(String[] args) {
		testSaleOrder();
	}
	
	
	/**
	 * 
	 * @Title: createSaleFactoryNOXML
	 * @Description: 生成创建销售订单厂编xml字符串，以便调用webservice传参数
	 * @param @param ordelModel
	 * @param @param saleorderModelChldList
	 * @param @return
	 * @return String
	 * @throws
	 */
	private static String createSaleFactoryNOXML(String type,String content,SaleorderModel saleorderModel){
		if("1".equals(type)) {
			content = "";
		}
		// 1.document构建器
        Document doc = DocumentHelper.createDocument();
        // 2.添加元素(根)
        Element ufinterfaceEle = doc.addElement("ufinterface");
        ufinterfaceEle.addAttribute("account", "DareTest2");// 测试环境
        ufinterfaceEle.addAttribute("billtype", "project");//固定
        ufinterfaceEle.addAttribute("filename", ""); // 可空
        ufinterfaceEle.addAttribute("groupcode", "");//固定
        ufinterfaceEle.addAttribute("isexchange", "Y");//待定
        ufinterfaceEle.addAttribute("replace", "Y");//待定
        ufinterfaceEle.addAttribute("roottag", "");//待定
        ufinterfaceEle.addAttribute("sender", "DY");//DY为固定
        
        Element bill = ufinterfaceEle.addElement("bill");
        bill.addAttribute("id", saleorderModel.getSALE_ORDER_ID());// 销售订单id
        
        Element billhead = bill.addElement("billhead");
        billhead.addElement("pk_project").setText(LogicUtil.nullToSring(content)); // 创建成果返回的content值
        billhead.addElement("pk_group").setText("DareGlobal"); // 集团(固定)
        billhead.addElement("pk_org").setText(saleorderModel.getLEDGER_ID());//NC-销售组织/OS-帐套编号
        billhead.addElement("pk_org_v").setText(saleorderModel.getLEDGER_ID());//项目组织多版本,最大长度为20,类型为:String
        billhead.addElement("project_code").setText(saleorderModel.getFACTORY_NO());// 传厂编,最大长度为40,类型为:String
        billhead.addElement("project_name").setText(saleorderModel.getCHANN_NAME());//传厂编对应的客户名称,最大长度为200,类型为:String
        billhead.addElement("pk_projectclass").setText("07");//项目类型,最大长度为20,类型为:String
        billhead.addElement("pk_eps").setText("07"); // EPS,最大长度为20,类型为:String
	    billhead.addElement("enablestate").setText("2"); // 启用状态,最大长度为0,类型为:Integer
	    billhead.addElement("creator").setText(saleorderModel.getCREATOR()); // 创建人
	    billhead.addElement("creationtime").setText(saleorderModel.getCRE_TIME()); // 创建时间
        billhead.addElement("pk_duty_org").setText(saleorderModel.getLEDGER_ID()); // 责任组织,最大长度为20,类型为:String
        billhead.addElement("pk_duty_org_v").setText(saleorderModel.getLEDGER_ID());  // 责任组织,最大长度为20,类型为:String
    	billhead.addElement("bill_type").setText("4D10"); // 单据类型,最大长度为4,类型为:String

       
        System.out.println(doc.asXML());
		return doc.asXML();
		
	}	

}
