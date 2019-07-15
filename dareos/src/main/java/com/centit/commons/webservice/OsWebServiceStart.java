package com.centit.commons.webservice;

import javax.servlet.http.HttpServlet;
import javax.xml.ws.Endpoint;

import com.centit.commons.model.Consts;

public class OsWebServiceStart extends HttpServlet{
	
	private static final long serialVersionUID = 1093479513345347389L;
	
	public OsWebServiceStart(){
		try {
//			Endpoint.publish("http://192.168.6.212:8081/OsWebService", new OsWebService());
//			Endpoint.publish("http://192.168.3.121:8090/OsWebService", new OsWebService());
			Endpoint.publish(Consts.WEBSERVICE_URL, new OsWebService());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
