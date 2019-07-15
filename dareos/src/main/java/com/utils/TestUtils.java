package com.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.centit.core.utils.LogicUtil;
import com.centit.system.user.entity.RYXXModel;
import com.utils.model.ifModel;
import com.utils.model.isNotEmptyModel;
import com.utils.model.xmlModel;

public class TestUtils {

	public static void main(String[] args) {
		TestUtils tu = new TestUtils();
		tu.isNotEmptyToIf();
		
	}
	
	/**
	 * 
	 * @Title: isNotEmptyToIf 
	 * @Description: sql里ibatis的isNotFmnty转换成myBatis的if(查询修改)
	 * 注：编辑SQL内用的 isNotNull标签可自己手动修改 xmlModel.java 里的 @XmlElement的注释  并修改方法内的拼接字符串注释
	 * @author: liu_yg
	 * @date: 2019年2月25日 上午9:52:26 
	 * @return
	 * @return: String
	 */
	public void isNotEmptyToIf(){
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream("D:\\json\\test.txt"), "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String s = null, ws = null;
			StringBuffer sd =new StringBuffer();
			while ((s = br.readLine()) != null) {
				sd.append(s);
			}
			String txt = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><list>" + sd.toString() + "</list>";
	        xmlModel xml = (xmlModel) LogicUtil.xmlToBean(txt, xmlModel.class);
	        List<isNotEmptyModel> list=xml.getIsNotEmptyList();
	        List<ifModel> xmlList=new ArrayList<ifModel>();
	        for (int i = 0; i < list.size(); i++) {
				ifModel im = new ifModel();
				// isNotEmpty
				im.setTest(list.get(i).getProperty() + " !=null" + " and " + list.get(i).getProperty() + " !='' ");
				im.setTxt(" "+list.get(i).getPrepend() + " " + list.get(i).getTxt());
				// isNotNull
//				im.setTest(list.get(i).getProperty() + " !=null");
//				im.setTxt(list.get(i).getTxt() + list.get(i).getPrepend());
				xmlList.add(im);
			}
	        xml =new xmlModel();
	        xml.setIfList(xmlList);
	        StringWriter sw = LogicUtil.beanToXml(xml);
	        String str = sw.toString();
	        str =str.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><list>", "").replace("</list>", "");
	        System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	

}
