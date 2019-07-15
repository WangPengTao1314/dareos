package com.centit.commons.webservice;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class NcHttpUrlUtil {
	
	public String SendXML(String xmlInfo,String param_url,ArrayList<String> billpkList) {
		StringBuffer msg = new StringBuffer();
		String succ = "-1";
		HttpURLConnection curconnection = null;
		String realUrl = "";

		try {
			// 只能通过参数来控制；
			// 接口调用,不登录NC,无法使用WorkbenchEnvironment;
//			realUrl = SysinitAccessor.getInstance().getParaString(
//					GLOBEORG, param_url); //DY_TRANSMATERIAL_URL
//			if ("".equals(realUrl) || realUrl.length() == 0)
//				return "导入失败:接口地址没有配置";

			curconnection = getConnection(param_url);
			Writer writer = new OutputStreamWriter(
					curconnection.getOutputStream(), "UTF-8");

			writer.write(xmlInfo);

			writer.flush();
			writer.close();
		} catch (Exception e) {
			msg.append("导入失败:调用平台出错," + e.getMessage());
//			Logger.error(e.getMessage(), e);
		}
		Document backdoc = receiveResponse(curconnection);
		// System.out.println(backdoc.asXML());
		/*
		 * <?xml version="1.0" encoding="UTF-8"?> <ufinterface billtype="F1"
		 * filename="F15eb715f12fWHOA.xml" isexchange="Y" replace="Y"
		 * roottag="sendresult" sender="WHOA" successful="Y"> <sendresult>
		 * <billpk> </billpk>
		 * <bdocid>0001A1100000000004RC15eb715f14d0000000F10</bdocid>
		 * <filename>F15eb715f12fWHOA.xml</filename> <resultcode>1</resultcode>
		 * <resultdescription>单据 0001A1100000000004RC15eb715f14d0000000F10
		 * 开始处理... 单据 0001A1100000000004RC15eb715f14d0000000F10
		 * 处理完毕!</resultdescription> <content>1001ZZ10000000001B2O</content>
		 * </sendresult> </ufinterface>
		 */
		if (null != backdoc) {
			billpkList = new ArrayList<String>();
			Element root = backdoc.getRootElement();
			// 单据类型
			String billType = root.attributeValue("billtype");
			Iterator it = root.elementIterator("sendresult");
			while (it.hasNext()) {
				Element sendresultElm = (Element) it.next();
				Element bdocid = sendresultElm.element("bdocid"); // xx_idcontrast.file_id
				// 处理结果代码
				Element resultcodeElm = sendresultElm.element("resultcode");
				// 处理结果描述
				Element descriptionElm = sendresultElm
						.element("resultdescription");
				if (resultcodeElm.getTextTrim().equals("1")) { // 1:处理成功
					succ = "1";
					//物料档案的主键
					billpkList.add(sendresultElm.element("content").getTextTrim());
//					msg.append(sendresultElm.asXML());
					msg.append(sendresultElm.element("content").getTextTrim());
				} else { // -1:处理失败
					succ = "-1";
					msg.append("导入失败:" + descriptionElm.getTextTrim());
					break;
				}

			}

		} else {
			succ = "-1";
			msg.append("导入失败");
		}
		if (curconnection != null)
			curconnection.disconnect();

		return msg.toString();

	}

	/**
	 * 根据URL地址获得 HttpURLConnection 对象
	 * 
	 * @param url地址
	 * @return HttpURLConnection
	 * @throws Exception 
	 * @throws BusinessException
	 */
	private HttpURLConnection getConnection(String url) throws Exception {
		try {
			System.out.println("数据发送地址：" + url);
			URL realURL = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) realURL
					.openConnection();
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-type", "text/xml");
			connection.setRequestMethod("POST");
			return connection;
		} catch (IOException e) {
//			Logger.error("获取发送地址连接出错！：", e);
			throw new Exception("获取发送地址连接出错！", e);
		}
	}

	/**
	 * 
	 * @param @param connection
	 * @param @return
	 * @description 获取回执信息Document
	 * 
	 */
	private Document receiveResponse(HttpURLConnection connection) {

		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(connection.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
//			Logger.error("获取回执文件出错：", e);
		}
		return doc;
	}
}
