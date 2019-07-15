package com.centit.commons.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import com.centit.core.utils.SpringContextHolder;
import com.centit.sys.model.MessageModel;
import com.centit.sys.service.FirstPageService;

// TODO: Auto-generated Javadoc
/**
 * The Class MessTimerTask.
 */
public class MessTimerTask extends TimerTask {
	
	/** The first page service. */
	@Autowired
	private FirstPageService firstPageService;
	
	/** The instance. */
	@Autowired
	private static MessTimerTask instance;


	
	/**
	 * 获取MessTimerTask实例
	 * Description :.
	 * 
	 * @return the instance
	 */
	public static MessTimerTask getInstance() {
		if (null == instance) {
			instance = SpringContextHolder.getBean(MessTimerTask.class);
		}
		return instance;
	}
	
	/** The return list. */
	private static List<MessageModel> returnList;
	
	/**
	 * Gets the return list.
	 * 
	 * @return the return list
	 */
	public static List<MessageModel> getReturnList() {
		return returnList;
	}
	
	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		Map<String,String> params = new HashMap();
		returnList = firstPageService.getAllMessage(params);
		
		/**
		try{
			String addr = "";
			try{
				addr =  getIp() ;
				//InetAddress.getLocalHost().getHostAddress().toString();
			}catch(Exception e){
				e.printStackTrace();
			}
			 
			//if(addr.indexOf("192.168.10.232")>=0&&cutTime>=23){
		    //}
          }catch(Exception ex){
			ex.printStackTrace();
		}
		**/
		
	}

	/**
	 * Sets the return list.
	 * 
	 * @param returnList the new return list
	 */
	public static void setReturnList(List<MessageModel> returnList) {
		MessTimerTask.returnList = returnList;
	}
	//add by zhuxw
	/**
	 * Gets the ip.
	 * 
	 * @return the ip
	 */
	public String  getIp() {
	Enumeration<NetworkInterface> netInterfaces = null;  
	StringBuffer ipsStr=new StringBuffer() ;
	try {   
	    netInterfaces = NetworkInterface.getNetworkInterfaces();   
	    while (netInterfaces.hasMoreElements()) {   
	        NetworkInterface ni = netInterfaces.nextElement();   
	        Enumeration<InetAddress> ips = ni.getInetAddresses();   
	        while (ips.hasMoreElements()) {   
	        	ipsStr.append(ips.nextElement().getHostAddress());   
	        }   
	    }   
	} catch (Exception e) {   
	    e.printStackTrace();   
	}
	return ipsStr.toString();
	}
  
}

