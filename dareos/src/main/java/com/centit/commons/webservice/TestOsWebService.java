package com.centit.commons.webservice;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import org.apache.axis.client.Service;

import org.apache.axis.client.Call;

public class TestOsWebService {
	
	public static void main(String[] args) {
        try {
			String endpoint = "http://192.168.3.121:8089/OsWebService?wsdl";   
			//直接引用远程的wsdl文件   
            //以下都是套路    
			Service service = new org.apache.axis.client.Service();   
			Call call = (Call) service.createCall();   
			call.setTargetEndpointAddress(endpoint);   
			//call.setOperationName(new QName("http://webservice.commons.centit.com/","deliverSend"));//WSDL里面描述的接口名称  
			call.setOperationName(new QName("http://webservice.commons.centit.com/","purOrderSend"));//WSDL里面描述的接口名称   
			call.addParameter("userName", org.apache.axis.encoding.XMLType.XSD_STRING,   
			              javax.xml.rpc.ParameterMode.IN);//接口的参数 
			call.addParameter("passWord", org.apache.axis.encoding.XMLType.XSD_STRING,   
		              javax.xml.rpc.ParameterMode.IN);//接口的参数   
//			call.addParameter("deliveryData", org.apache.axis.encoding.XMLType.XSD_STRING,   
//		              javax.xml.rpc.ParameterMode.IN);//接口的参数   
			call.addParameter("purOrderSendData", org.apache.axis.encoding.XMLType.XSD_STRING,   
		              javax.xml.rpc.ParameterMode.IN);//接口的参数   
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型     
			String userName = "测试人员";   
			String passWord = "passWord";
			String purOrderSendData = "{'delivery_date': '2019-05-27','ledger_id': 'NC测试','total_money': '2467','order_date': '2019-05-27','order_degree_id': 'NC测试',"
					+"'order_degree_no': '采购订单编号','order_num': '200','prvd_name': '供应商名称','prvd_no': '供应商编号','pur_dep': '采购部门','pur_name': '采购员','sale_order_no': '销售订单编号',"
					+"'detailList': [{'group_no': 1,'order_degree_dtl_id': '1','order_degree_id': 'NC测试','order_num': 65,'order_amount':'456','prd_glass': '玻璃','prd_id': '22','prd_name': '产品名称',"
					+"'prd_no': '22','prd_other': '其它','prd_quality': '材质','prd_series': '系列','prd_spec': '规格','prd_toward': '推向','std_unit': '主单位'}, {"
					+"'group_no': 1,'order_degree_dtl_id': '2','order_degree_id': 'NC测试','order_num': 65,'order_amount':'456','prd_glass': '玻璃','prd_id': '22','prd_name': '产品名称',"
					+"'prd_no': '22','prd_other': '其它','prd_quality': '材质','prd_series': '系列','prd_spec': '规格','prd_toward': '推向','std_unit': '主单位'}]}";
			String result = (String)call.invoke(new Object[]{userName,passWord,purOrderSendData});   
			//给方法传递参数，并且调用方法   
			System.out.println("result is "+result);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}   
	}

}
