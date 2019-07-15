package com.centit.sys.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.centit.common.controller.BaseController;
import com.centit.commons.model.Consts;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.QXGLService;

// TODO: Auto-generated Javadoc
/**
 * The Class QXGLAction.
 * 
 * @module 系统模块
 * @fuc 系统模块
 * @version 1.1
 * @author 朱晓文
 */
@Controller
@RequestMapping("/sys/qxgl")
public class QXGLController extends BaseController {

	/** The qxgl bean service. */
	@Autowired
	private QXGLService qxglBeanService;
	
	
	private static final String webPath = "sys/qxcomm";


	/**
	 * Toread xml list.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	@GetMapping("/toreadXMLList")
	public String toreadXMLList(
			HttpServletRequest request, HttpServletResponse response) {

		return view(webPath, "readQXxml");
	}

	/**
	 * 对用户进行授权.
	 * 
	 * @param keyName the key name
	 * @param keyID the key id
	 * @param adminXTYHID the admin xtyhid
	 * @param loginJGXXID the login jgxxid
	 * @param request the request
	 * 
	 * @return the QX tab
	 */
	@PostMapping("/getQXTab")
	public String getQXTab(String keyName, String keyID, String adminXTYHID,
			String loginJGXXID,HttpServletRequest request) {
		try {
			return qxglBeanService.getQXTab(keyName, keyID, adminXTYHID,loginJGXXID, request);
		} catch (Exception e) {
			return "";
		}
	}

	
	//跳转到用户授权页面
	/**
	 * To user qx list.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	@RequestMapping(value = { "/toUserQXList"}, method = { RequestMethod.GET, RequestMethod.POST })
	public String toUserQXList(
			HttpServletRequest request, HttpServletResponse response) {

		    String XTYHID =tranCodeP(request.getParameter("XTYHID"));
		    HttpSession session = request.getSession(false);
		    UserBean aUserBean = (UserBean)session.getAttribute("UserBean");
		    String qxtab=getQXTab("XTYHID",XTYHID,aUserBean.getXTYHID(),aUserBean.getJGXXID(), request);
		    request.setAttribute("qxtab", qxtab);
		    request.setAttribute("XTYHID", XTYHID);
		    System.err.println("qxtab==="+qxtab);
		    return view(webPath, "userQX");
	}
	
	//跳转到系统角色授权页面
	/**
	 * To xtjsqx list.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	@GetMapping("/toXTJSQXList")
	public String toXTJSQXList(
			HttpServletRequest request, HttpServletResponse response) {

		    String XTJSID =tranCodeP(request.getParameter("XTJSID"));
		    HttpSession session = request.getSession(false);
		    UserBean aUserBean = (UserBean)session.getAttribute("UserBean");
		    String qxtab=getQXTab("XTJSID",XTJSID,aUserBean.getXTYHID(),aUserBean.getJGXXID(), request);		 
		    request.setAttribute("qxtab", qxtab);
		    request.setAttribute("XTJSID", XTJSID);
		    System.err.println("qxtab==="+qxtab);
		    return view(webPath, "xtjsQX");
	}
	
	//跳转到工作组授权页面
	/**
	 * To gzzqx list.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	@RequestMapping(value = { "/toGZZQXList"}, method = { RequestMethod.GET, RequestMethod.POST })
	public String toGZZQXList(
			HttpServletRequest request, HttpServletResponse response) {

		    String GZZXXID =tranCodeP(request.getParameter("GZZXXID"));
		    HttpSession session = request.getSession(false);
		    UserBean aUserBean = (UserBean)session.getAttribute("UserBean");
		    String qxtab=getQXTab("GZZXXID",GZZXXID,aUserBean.getXTYHID(),aUserBean.getJGXXID(),request);		 
		    request.setAttribute("qxtab", qxtab);
		    request.setAttribute("GZZXXID", GZZXXID);
		    System.err.println("qxtab==="+qxtab);
		    return view(webPath, "gzzQX");
	}
	
	//跳转到用户权限树页面
	/**
	 * To mkxx tree.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	@GetMapping("/toMkxxTree")
	public String toMkxxTree(
			HttpServletRequest request, HttpServletResponse response) {

		try{
		String KeyName = request.getParameter("KeyName")==null?"":request.getParameter("KeyName");
		String KeyID = request.getParameter("KeyID")==null?"":request.getParameter("KeyID");
		String MKLB = request.getParameter("MKLB")==null?"":request.getParameter("MKLB");
		UserBean userBean = ParamUtil.getUserBean(request);
		
		//这个方法也可以优化
		String[] jspMkary = qxglBeanService.getMKAry(MKLB,5,"1=1 AND (MKZT<>'停用' or MKZT is null or MKZT='') AND XTMKID in (select XTMKID  from T_SYS_XTSQ where  XTYHID='"+userBean.getXTYHID()+"')");
		request.setAttribute("jspMkary", jspMkary);
		//end
		String MKQXJB = request.getParameter("MKQXJB")==null?"":request.getParameter("MKQXJB");
		   if (MKQXJB.equals("")) MKQXJB = "Default";
		String jspReturnQXJBRadioList = qxglBeanService.returnQXJBRadioList(MKQXJB);
		request.setAttribute("jspReturnQXJBRadioList", jspReturnQXJBRadioList);		
	    String condition = " where "+KeyName + "='"+ KeyID+"'";
	    String jspYesOrNo = "";
	    String tableName = "";
	    boolean  AllFlag = false;
	    if (KeyName.equalsIgnoreCase("XTJSID")) {			     
	      tableName = "T_SYS_JSQX";
	      //jspYesOrNo =  qxglBeanService.getStrMK2("T_SYS_TEMPSYSTEMADM"," where XTYHID='"+xtyhid+"' AND MKLB='"+MKLB+"'");
	        if (jspYesOrNo.equals("")) {
	          AllFlag = true;
	        }
	    }
	    if (KeyName.equalsIgnoreCase("XTYHID")) {
	        tableName = "T_SYS_YHQX";
	       // jspYesOrNo =  qxglBeanService.getStrMK2("T_SYS_TEMPSYSTEMADM"," where XTYHID='"+xtyhid+"' AND MKLB='"+MKLB+"'");
	         if (jspYesOrNo.equals("")) {
	          AllFlag = true;
	        }
	    }
	    if (KeyName.equalsIgnoreCase("GZZXXID")) {
	        //jspYesOrNo =   qxglBeanService.getStrMK2("T_SYS_TEMPSYSTEMADM"," where XTYHID='"+xtyhid+"' AND MKLB='"+MKLB+"'");
	        tableName = "T_SYS_GZZQX";
	         if (jspYesOrNo.equals("")) {
	          AllFlag = true;
	        }
	    }
	    
	    String jspStrMK = "";
	     if (KeyName.equals("SYSTEMADM")){
	         AllFlag = true;
	         // jspStrMK = qxglBeanService.getStrMK2("T_SYS_TEMPSYSTEMADM"," where XTYHID='"+KeyID+"'");
	   } else {
	      
		   jspStrMK =  qxglBeanService.getStrMK(tableName, condition);
	   }
	   
       request.setAttribute("jspYesOrNo", jspYesOrNo);
       request.setAttribute("jspStrMK", jspStrMK);	     	     
       Map<String,Object> jspSubMkaryMap = new HashMap<String,Object>();
       Map<String,Object> jspFinalMkaryMap = new HashMap<String,Object>();
     
       // start 这个循环的方法可以优化
	     for (int i=0; i< jspMkary.length; i=i+3) {
	    		int tempMKAryI = i+1;
	    		if(Consts.DBTYPE.equals("MSSQL"))
	    		{
		    	    if ( jspYesOrNo.indexOf(jspMkary[tempMKAryI])!=-1 || AllFlag) {
		    	    	 String[] SubMKAry = qxglBeanService.getMKAry(MKLB, 7, " SJMK='"+jspMkary[tempMKAryI]+"' AND (MKZT<>'停用' or MKZT is null or MKZT='') AND substring(XTMKID,1,7) in (select XTMKID  from T_SYS_XTSQ where  XTYHID='"+userBean.getXTYHID()+"') ");
		    	    	 jspSubMkaryMap.put(tempMKAryI+"", SubMKAry);
		    	    	 
		    	    	 for (int j=0; j<SubMKAry.length; j=j+3) {
		    	    			int tempMKAryJ = j+1;
		    	    			
		    	    		if ( jspYesOrNo.indexOf(SubMKAry[tempMKAryJ])!=-1 || AllFlag) {
		    	    		   String[] FinalMKAry = qxglBeanService.getMKAry(MKLB, 9, "SJMK='"+SubMKAry[tempMKAryJ]+"' AND (MKZT<>'停用' or MKZT is null or MKZT='') AND substring(XTMKID,1,7)	 in (select XTMKID  from T_SYS_XTSQ where  XTYHID='"+userBean.getXTYHID()+"') ");
		    	    		   jspFinalMkaryMap.put(SubMKAry[tempMKAryJ]+tempMKAryI+tempMKAryJ, FinalMKAry);
		    	    	   }
		    	       }
		    	    }
	    		}else{
	    			 if ( jspYesOrNo.indexOf(jspMkary[tempMKAryI])!=-1 || AllFlag) {
		    	    	 String[] SubMKAry = qxglBeanService.getMKAry(MKLB, 7, " SJMK='"+jspMkary[tempMKAryI]+"' AND (MKZT<>'停用' or MKZT is null or MKZT='') AND substr(XTMKID,1,7) in (select XTMKID  from T_SYS_XTSQ where  XTYHID='"+userBean.getXTYHID()+"') ");
		    	    	 jspSubMkaryMap.put(tempMKAryI+"", SubMKAry);
		    	    	 
		    	    	 for (int j=0; j<SubMKAry.length; j=j+3) {
		    	    			int tempMKAryJ = j+1;
		    	    			
		    	    		if ( jspYesOrNo.indexOf(SubMKAry[tempMKAryJ])!=-1 || AllFlag) {
		    	    		   String[] FinalMKAry = qxglBeanService.getMKAry(MKLB, 9, "SJMK='"+SubMKAry[tempMKAryJ]+"' AND (MKZT<>'停用' or MKZT is null or MKZT='') AND substr(XTMKID,1,7)	 in (select XTMKID  from T_SYS_XTSQ where  XTYHID='"+userBean.getXTYHID()+"') ");
		    	    		   jspFinalMkaryMap.put(SubMKAry[tempMKAryJ]+tempMKAryI+tempMKAryJ, FinalMKAry);
		    	    	   }
		    	       }
		    	    }
	    		}
 
	  		}
	     //end 
     request.setAttribute("jspSubMkaryMap", jspSubMkaryMap);
     request.setAttribute("jspFinalMkaryMap", jspFinalMkaryMap);	
     request.setAttribute("KeyName", KeyName);
     request.setAttribute("KeyID", KeyID);
     request.setAttribute("MKLB", MKLB);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	return view(webPath, "MKXXTree");
	}
	
   /** The Constant FINALQXMKCodeLength. */
   private final static int FINALQXMKCodeLength = 9;
	
	//更新用户权限
	/**
	 * Insert xtyhqx self.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
   @PostMapping("/insertXTYHQXSelf")
	public String insertXTYHQXSelf(
			HttpServletRequest request, HttpServletResponse response) {	 
	    String[] ins_name2 = { "YHQXID", "XTYHID", "XTMKID", "QXJBID" };
		String[] ins_value2 = new String[ins_name2.length];
		String MKSM = request.getParameter("MKSM");
		String XTYHID = tranCodeP(request.getParameter("XTYHID"));
		String[] selXTMKID = request.getParameterValues("checkSel");
		try {
			qxglBeanService.insertXTYHQXSelf(XTYHID, MKSM, ins_name2, ins_value2, selXTMKID, FINALQXMKCodeLength, request);
		} catch (Exception e) {
			request.setAttribute("msg", "系统用户自有权限更新失败!");
			e.printStackTrace();
			return view(Consts.WEB_PATH, Consts.SUCCESS);
		}
		request.setAttribute("msg", "系统用户自有权限已经成功更新!");
		return view(Consts.WEB_PATH, Consts.SUCCESS);
	    
	}
	// 更新系统角色权限		
	/**
	 * Insertxtjs qx.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
   @PostMapping("/insertxtjsQX")
	public String insertxtjsQX(
			HttpServletRequest request, HttpServletResponse response) {	
		
		  String[] ins_name2 = {"JSQXID", "XTJSID", "XTMKID","QXJBID"};
	      String[] ins_value2 = new String[ins_name2.length];
		  String MKSM = request.getParameter("MKSM");
		  String XTJSID = tranCodeP(request.getParameter("XTJSID"));
		  String[] selXTMKID = request.getParameterValues("checkSel");
		
      try {
      	  //String MKSM = request.getParameter("MKSM");
    	  qxglBeanService.insertxtjsQX(XTJSID, MKSM, ins_name2, ins_value2, selXTMKID, FINALQXMKCodeLength, request);
		//sevQXGLBean.executeStaticSql("delete from T_SYS_JSQX where XTJSID='"+XTJSID+"' AND XTMKID in (select XTMKID from T_SYS_XTMK where MKSM='"+MKSM+"')");
	} catch (Exception e) {
		request.setAttribute("msg", "系统角色权限更新失败!");
		e.printStackTrace();
		return view(Consts.WEB_PATH, Consts.SUCCESS);
	}
      //String[] selXTMKID = request.getParameterValues("checkSel");
/*      if (selXTMKID == null) {

         return gotoInfoPage("系统角色已经成功更新!" , "系统角色" , XTJSID , "1" ) ;
      }*/
      /*int i = 0;
      int j = 0;
      Vector tempVector = new Vector();
      for (i = 0 ; i < selXTMKID.length; i++ ) {
        if (selXTMKID[i].length() == FINALQXMKCodeLength) {
          j++;
          tempVector.add(selXTMKID[i]);
        }
      }
      String[] ins_JS_XTMKID = new String[tempVector.size()];
      String[] ins_JS_QXJBID = new String[tempVector.size()];
      String tempStr = "";
      for (i = 0; i < tempVector.size(); i++ ) {
          tempStr = (String)tempVector.get(i);
          sevQXGLBean.insert(ins_JS_XTMKID, tempStr, i);
          sevQXGLBean.insert(ins_JS_QXJBID,tranCodeP(request.getParameter("radioSel"+tempStr)), i);
      }
      
    
      for (i = 0; i < ins_JS_XTMKID.length; i++) {
        ins_value2[0] = TimeComm.getPreTimeStamp("JSQX")+i;
        //// ("JSQXID="+ins_value2[0]);
        ins_value2[1] = XTJSID;
        //// ("XTJSID="+ins_value2[1]);
        ins_value2[2] = ins_JS_XTMKID[i];
        //// ("XTMKID="+ins_value2[2]);
        ins_value2[3] = ins_JS_QXJBID[i].substring(FINALQXMKCodeLength+1,FINALQXMKCodeLength+2);
        //// ("QXJBID="+ins_value2[3]);
        try {
			sevQXGLBean.insertDB("T_SYS_JSQX", ins_name2, ins_value2);
		} catch (Exception e) {
		}*/      

      //Start: 修改初始系统用户权限状态 0: 等待组建
     //  try {
	//		sevQXGLBean.executeStaticSql("update T_SYS_XTYH set XTYHQXZT='0',refreshqys='1' where XTYHID in (select XTYHID from T_SYS_XTYHJS where XTJSID='"+XTJSID+"')");
	//	} catch (SQLException e) {
	//	}     	
       
       request.setAttribute("msg", "系统角色权限已经成功更新!");
       return view(Consts.WEB_PATH, Consts.SUCCESS);
       
	
	}
	
	
	
// 更新工作组权限	
	
	/**
 * Insert gzzqx.
 * 
 * @param mapping the mapping
 * @param form the form
 * @param request the request
 * @param response the response
 * 
 * @return the action forward
 */
   @PostMapping("/insertGZZQX")
public String insertGZZQX(
			HttpServletRequest request, HttpServletResponse response) {	
		
		   String MKSM = request.getParameter("MKSM");
	  	   String[] ins_name2 = {"GZZQXID", "GZZXXID", "XTMKID","QXJBID"};
	       String[] ins_value2 = new String[ins_name2.length];
	       String GZZXXID = tranCodeP(request.getParameter("GZZXXID"));
	       String[] selXTMKID = request.getParameterValues("checkSel");
	       try {
	       	  //String MKSM = request.getParameter("MKSM");
	     	  qxglBeanService.insertgzzQX(GZZXXID, MKSM, ins_name2, ins_value2, selXTMKID, FINALQXMKCodeLength, request);
	 		//sevQXGLBean.executeStaticSql("delete from T_SYS_JSQX where XTJSID='"+XTJSID+"' AND XTMKID in (select XTMKID from T_SYS_XTMK where MKSM='"+MKSM+"')");
	 	} catch (Exception e) {
	 		request.setAttribute("msg", "工作组权限更新失败!");
	 		e.printStackTrace();
	 		return view(Consts.WEB_PATH, Consts.SUCCESS);
	 	}
		
    /*  try {
 	try {
        String MKSM = request.getParameter("MKSM");
       sevQXGLBean.executeStaticSql("delete from T_SYS_GZZQX where GZZXXID='"+GZZXXID+"' AND XTMKID in (select XTMKID from T_SYS_XTMK where MKSM='"+MKSM+"')");
    	 String[] ins_name2 = {"GZZQXID", "GZZXXID", "XTMKID","QXJBID"};
       String[] ins_value2 = new String[ins_name2.length];	
       String[] selXTMKID = request.getParameterValues("checkSel");
       int i = 0;
       int j = 0;
       Vector tempVector = new Vector();
       if (selXTMKID != null) {
        for (i = 0 ; i < selXTMKID.length; i++ ) {
          if (selXTMKID[i].length() == FINALQXMKCodeLength) {
            j++;
            tempVector.add(selXTMKID[i]);
          }
        }
        String[] ins_GZZ_XTMKID = new String[tempVector.size()];
        String[] ins_GZZ_QXJBID = new String[tempVector.size()];
        String tempStr = "";
        for (i = 0; i < tempVector.size(); i++ ) {
            tempStr = (String)tempVector.get(i);
            sevQXGLBean.insert(ins_GZZ_XTMKID, tempStr, i);
            sevQXGLBean.insert(ins_GZZ_QXJBID, tranCodeP(request.getParameter("radioSel"+tempStr)), i);
        }

        for (i = 0; i < ins_GZZ_XTMKID.length; i++) {
          ins_value2[0] = TimeComm.getPreTimeStamp("GZZQX")+i;
         // // ("GZZQXID="+ins_value2[0]);
          ins_value2[1] = GZZXXID;
         // // ("GZZXXID="+ins_value2[1]);
          ins_value2[2] = ins_GZZ_XTMKID[i];
         // // ("XTMKID="+ins_value2[2]);
          ins_value2[3] = ins_GZZ_QXJBID[i].substring(FINALQXMKCodeLength+1,FINALQXMKCodeLength+2);
         // // ("QXJBID="+ins_value2[3]);
          sevQXGLBean.insertDB("T_SYS_GZZQX", ins_name2, ins_value2);
          }
        }
       //Start: 修改初始系统用户权限状态 0: 等待组建
       // sevQXGLBean.executeStaticSql("update T_SYS_XTYH set XTYHQXZT='0',refreshqys='1' where XTYHID in (select XTYHID from T_SYS_GZZCY where GZZXXID='"+GZZXXID+"')");
      //End.

      }	catch (Exception ex) {
    		ex.printStackTrace();
    	}*/
      request.setAttribute("msg", "工作组权限已经成功更新!");
      return view(Consts.WEB_PATH, Consts.SUCCESS);
   
      }
    //End.
	
	
	//系统用户权限
	/**
     * Insert mkqx.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
   @PostMapping("/insertMKQX")
    public String insertMKQX(
			HttpServletRequest request, HttpServletResponse response) {	

/*	String XTYHID = tranCodeP(request.getParameter("XTYHID"));
	String MKLB = tranCodeP(request.getParameter("MKSM"));
	try {
		sevQXGLBean.executeStaticSql("delete from T_SYS_TEMPSYSTEMADM where XTYHID='" + XTYHID + "' AND MKLB='"+MKLB+"'");
		Vector tempVector = new Vector();
		int i = 0;
		int j = 0;
		if (selXTMKID != null) {
			for (i = 0; i < selXTMKID.length; i++) {
				if (selXTMKID[i].length() == FINALQXMKCodeLength) {
					j++;
					tempVector.add(selXTMKID[i]);
				}
			}
			for (i = 0; i < tempVector.size(); i++) {
				sevQXGLBean.executeStaticSql("insert into T_SYS_TEMPSYSTEMADM(XTMKID,XTYHID,MKLB) values('"+(String)tempVector.get(i)+"','"+XTYHID+"','"+MKLB+"')");
			}
		}
		return gotoInfoPage("系统用户自有权限已经成功更新!" , "系统用户" , XTYHID , "6" ) ;
	} catch (SQLException e) {
		
		e.printStackTrace();
		return gotoErrPage(e.toString());
	}*/
		request.setAttribute("msg", "工作组权限已经成功更新!");
		return view(Consts.WEB_PATH, Consts.SUCCESS);
	}
	
	
	
	//把权限文件 读取到数据库中  by zhuxw
	/**
	 * Read xtmk.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 * 
	 * @throws SQLException the SQL exception
	 */
   @PostMapping("/readXTMK")
	public String readXTMK(
			HttpServletRequest request, HttpServletResponse response)
			throws SQLException {
		Document document = null;
		String SYSTYPE = ParamUtil.get(request, "SYSTYPE");
		String FileName = "xmlQXComm_" + SYSTYPE + ".xml";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			StringBuffer requestURL = request.getRequestURL();
			String ContextPath = request.getContextPath();
			String filePath = requestURL.toString().substring(0,
					requestURL.toString().indexOf(ContextPath))
					+ request.getContextPath() + "/pages/sys/configfiles/privilege/";
			System.err.println("paht==" + filePath);
//            URL jspURL = new URL(filePath);
//			URL url = new URL(jspURL, FileName);
			InputSource is = new InputSource(new InputStreamReader(new FileInputStream(request.getSession().getServletContext().getRealPath("/")+"/pages/sys/configfiles/privilege/"+FileName), "UTF-8"));
//			InputStreamReader isr = new InputStreamReader(new FileInputStream(request.getSession().getServletContext().getRealPath("/")+"/pages/sys/configfiles/privilege/"+FileName), "UTF-8");
			document = builder.parse(is);
		} catch (Exception ex) {
			System.err
					.println("\u8BFB\u53D6XML\u6587\u4EF6\u6CA1\u6709\u627E\u5230"
							+ ex.toString());
			request.setAttribute("msg", "系统权限文件不存在！");
			return view(Consts.WEB_PATH, Consts.SUCCESS);
		}

		NodeList MKList = document.getElementsByTagName("FMK");
		String MKType = FileName.substring(FileName.length() - 6, FileName.length() - 4);
		System.err.println("MKType====" + MKType);
		// 把所有用户置为不可登陆
		try {
			StringBuffer aSQL = new StringBuffer();
			aSQL.append("update ");
			aSQL.append("T_SYS_XTYH set XTYHQXZT='R' where XTYHID in (select XTYHID from  ");
			aSQL.append("T_SYS_USERQX where MKBHJB like ");
			aSQL.append("'");
			aSQL.append(MKType);
			aSQL.append("%')");
			InterUtil.update(aSQL.toString());

		} catch (Exception ex) {
			request.setAttribute("msg", "程序出错！");
			return view(Consts.WEB_PATH, Consts.SUCCESS);
		}

		try {

			StringBuffer aSQL = new StringBuffer();
			aSQL.append("delete from  ");
			aSQL.append("T_SYS_XTMK where MKSM = ");
			aSQL.append("'");
			aSQL.append(MKType);
			aSQL.append("'");
			InterUtil.delete(aSQL.toString());

		} catch (Exception ex) {
			System.err
					.println("\uFF2D\uFF2B\uFF34\uFF59\uFF50\uFF45\uFF1D"
							+ MKType
							+ "\u7684\u6A21\u5757\u6570\u636E\u7531\u4E8E\u5B58\u5728\u5173\u8054\u4E0D\u80FD\u5220"
							+ "\u9664");
			request.setAttribute("msg", "程序出错！");
			return view(Consts.WEB_PATH, Consts.SUCCESS);
		}
		String MKSM = "";
		for (int iF = 0; iF < MKList.getLength(); iF++) {
			Element FMK = (Element) MKList.item(iF);
			String FMKName = FMK.getAttribute("FMKName");
			String FMKCode = FMK.getAttribute("FMKCode");
			String FMKXTMKDesc = FMK.getAttribute("XTMKDesc");
			MKSM = FMK.getAttribute("MKSM");
			if (!qxglBeanService.getRowNum("T_SYS_XTMK", " MKBH='"
					+ FMKCode + "'")) {

				StringBuffer aSQL = new StringBuffer();
				aSQL.append(" insert into ");
				aSQL.append("T_SYS_XTMK (XTMKID, MKBH, MKMC, SJMK,MKSM,XTMKDesc) values(");
				aSQL.append("'");
				aSQL.append(FMKCode);
				aSQL.append("','");
				aSQL.append(FMKCode);
				aSQL.append("','");
				aSQL.append(FMKName);
				aSQL.append("','");
				aSQL.append("','");
				aSQL.append(MKSM);
				aSQL.append("','");
				aSQL.append(FMKXTMKDesc);
				aSQL.append("')");
				InterUtil.insert(aSQL.toString());
				System.err.println("info=="
						+ "\u63D2\u5165\u7CFB\u7EDF\u6A21\u5757\u8BB0\u5F55");

			} else {
				StringBuffer aSQL = new StringBuffer();
				aSQL.append("update ");
				aSQL.append("T_SYS_XTMK set MKMC=");
				aSQL.append("'");
				aSQL.append(FMKName);
				aSQL.append("',MKSM= ");
				aSQL.append("'");
				aSQL.append(MKSM);
				aSQL.append("',XTMKDesc= ");
				aSQL.append("'");
				aSQL.append(FMKXTMKDesc);
				aSQL.append("',MKBH= ");
				aSQL.append("'");
				aSQL.append(FMKCode);
				aSQL.append("'");
				aSQL.append(" where MKBH= ");
				aSQL.append("'");
				aSQL.append(FMKCode);
				aSQL.append("'");
				InterUtil.update(aSQL.toString());

			}
			NodeList ZMKList = FMK.getElementsByTagName("ZMK");
			for (int iZ = 0; iZ < ZMKList.getLength(); iZ++) {
				Element ZMK = (Element) ZMKList.item(iZ);
				NodeList ZMKQXList = ZMK.getElementsByTagName("ZMKQX");
				NodeList QXJBList = ZMK.getElementsByTagName("QXJB");
				
				String ZMKCode = ZMK.getAttribute("ZMKCode");
				if (!qxglBeanService.getRowNum("T_SYS_XTMK",
						"  MKBH='" + ZMK.getAttribute("ZMKCode") + "'")) {
					StringBuffer aSQL = new StringBuffer();
					aSQL.append(" insert into ");
					aSQL.append("T_SYS_XTMK (XTMKID, MKBH, MKMC, SJMK,MKSM,XTMKDesc) values(");
					aSQL.append("'");
					aSQL.append(ZMK.getAttribute("ZMKCode"));
					aSQL.append("','");
					aSQL.append(ZMK.getAttribute("ZMKCode"));
					aSQL.append("','");
					aSQL.append(ZMK.getAttribute("ZMKName"));
					aSQL.append("','");
					aSQL.append(FMKCode);
					aSQL.append("','");
					aSQL.append(MKSM);
					aSQL.append("','");
					aSQL.append(ZMK.getAttribute("XTMKDesc"));
					aSQL.append("')");
					InterUtil.insert(aSQL.toString());

				} else {
					StringBuffer aSQL = new StringBuffer();
					aSQL.append("update ");
					aSQL.append("T_SYS_XTMK set MKMC=");
					aSQL.append("'");
					aSQL.append(ZMK.getAttribute("ZMKName"));
					aSQL.append("',SJMK= ");
					aSQL.append("'");
					aSQL.append(FMKCode);
					aSQL.append("',MKSM= ");
					aSQL.append("'");
					aSQL.append(MKSM);
					aSQL.append("',XTMKDesc= ");
					aSQL.append("'");
					aSQL.append(ZMK.getAttribute("XTMKDesc"));
					aSQL.append("' where MKBH= ");
					aSQL.append("'");
					aSQL.append(ZMK.getAttribute("ZMKCode"));
					aSQL.append("'");
					InterUtil.update(aSQL.toString());

				}
				for (int iQ = 0; iQ < ZMKQXList.getLength(); iQ++) {
					Element ZMKQX = (Element) ZMKQXList.item(iQ);
					String ZMKQXCode = ZMKQX.getAttribute("MKCode");
					if (!qxglBeanService.getRowNum("T_SYS_XTMK",
							"  MKBH='" + ZMKQXCode + "'")) {

						StringBuffer aSQL = new StringBuffer();
						aSQL.append(" insert into ");
						aSQL.append("T_SYS_XTMK (XTMKID, MKBH, MKMC, SJMK,MKSM,XTMKDESC) values(");
						aSQL.append("'");
						aSQL.append(ZMKQXCode);
						aSQL.append("','");
						aSQL.append(ZMKQXCode);
						aSQL.append("','");
						aSQL.append(ZMK.getAttribute("ZMKName"));
						aSQL.append("_");
						aSQL.append(ZMKQX.getAttribute("QXName"));
						aSQL.append("','");
						aSQL.append(ZMKCode);
						aSQL.append("','");
						aSQL.append(MKSM);
						aSQL.append("','");
						aSQL.append(ZMKQX.getAttribute("XTMKDesc"));
						aSQL.append("')");
						InterUtil.insert(aSQL.toString());

					} else {
						StringBuffer aSQL = new StringBuffer();
						aSQL.append("update ");
						aSQL.append("T_SYS_XTMK set MKMC=");
						aSQL.append("'");
						aSQL.append(ZMK.getAttribute("ZMKName"));
						aSQL.append("_");
						aSQL.append(ZMKQX.getAttribute("QXName"));
						aSQL.append("',SJMK= ");
						aSQL.append("'");
						aSQL.append(ZMKCode);
						aSQL.append("',MKSM= ");
						aSQL.append("'");
						aSQL.append(MKSM);
						aSQL.append("',XTMKDESC= ");
						aSQL.append("'");
						aSQL.append(ZMKQX.getAttribute("XTMKDesc"));
						aSQL.append("'");
						aSQL.append(" where MKBH= ");
						aSQL.append("'");
						aSQL.append(ZMKQXCode);
						aSQL.append("'");
						InterUtil.update(aSQL.toString());

					}
				}

				
				
				//先删除权限级别
				StringBuffer aSQLQXJB = new StringBuffer();
				aSQLQXJB.append(" delete from ");
				aSQLQXJB.append("T_SYS_XTMKQXJB  where XTMKID='");
				aSQLQXJB.append(ZMK.getAttribute("ZMKCode"));
				aSQLQXJB.append("'");
				InterUtil.delete(aSQLQXJB.toString());
				//插入权限级别
				for (int iQ = 0; iQ < QXJBList.getLength(); iQ++) {
					Element QXJB = (Element) QXJBList.item(iQ);
					String tempFkId = StringUtil.uuid32len();
					StringBuffer aSQL = new StringBuffer();
					aSQL.append(" insert into ");
					aSQL.append("T_SYS_XTMKQXJB (XTMKQXJBID,XTMKID,MKBH,MKMC,QXJBMC,QXJBID,QXCODE) values(");
					aSQL.append("'");
					aSQL.append(tempFkId);
					aSQL.append("','");
					aSQL.append(ZMK.getAttribute("ZMKCode"));
					aSQL.append("','");
					aSQL.append(ZMK.getAttribute("ZMKCode"));
					aSQL.append("','");
					aSQL.append(ZMK.getAttribute("ZMKName"));
					aSQL.append("','");
					aSQL.append(QXJB.getAttribute("JBName"));
					aSQL.append("','");
					aSQL.append(QXJB.getAttribute("JBValue"));
					aSQL.append("','");
					aSQL.append(QXJB.getFirstChild().getNodeValue().replaceAll("'",""));
					aSQL.append("')");
					InterUtil.insert(aSQL.toString());
				}
				
				
			}

		}
		// 把所有用户置可登陆

		StringBuffer aSQL = new StringBuffer();
		aSQL.append("update ");
		aSQL.append("T_SYS_XTYH set XTYHQXZT='0' where XTYHID in (select XTYHID from  ");
		aSQL.append("T_SYS_USERQX where MKBHJB like ");
		aSQL.append("'");
		aSQL.append(MKType);
		aSQL.append("%')");
		InterUtil.update(aSQL.toString());
		request.setAttribute("msg", "系统权限重构成功！");
		return view(Consts.WEB_PATH, Consts.SUCCESS);
	}

/**
 * Gets the mK ary.
 * 
 * @param XTMC the xTMC
 * @param length the length
 * @param condition the condition
 * 
 * @return the mK ary
 * 
 * @throws Exception the exception
 */
public String[] getMKAry(String XTMC, int length, String condition)
    throws Exception
{
	System.err.println("qxglBeanService==="+qxglBeanService);
	return qxglBeanService.getMKAry(XTMC, length, condition);
}

/**
 * Return qxjb radio list.
 * 
 * @return the string
 * 
 * @throws Exception the exception
 */
public String returnQXJBRadioList()
    throws Exception        
{
	return qxglBeanService.returnQXJBRadioTree();
}

/**
 * Return qxjb radio list.
 * 
 * @param MKQXJB the mKQXJB
 * 
 * @return the string
 * 
 * @throws Exception the exception
 */
public String returnQXJBRadioList(String MKQXJB)
    throws Exception
{
	return qxglBeanService.returnQXJBRadioList(MKQXJB);
}

/**
 * Return qxjb radio list.
 * 
 * @param MKQXJB the mKQXJB
 * @param aUserBean the a user bean
 * 
 * @return the string
 * 
 * @throws Exception the exception
 */
public String returnQXJBRadioList(String MKQXJB,UserBean aUserBean)
	throws Exception
{
	return qxglBeanService.returnQXJBRadioList(MKQXJB, aUserBean);
}

/**
 * Check disabled.
 * 
 * @param xtyhid the xtyhid
 * 
 * @return true, if successful
 * 
 * @throws Exception the exception
 */
public  boolean checkDisabled(String xtyhid)throws Exception{

	return qxglBeanService.checkDisabled(xtyhid);
}

/**
 * Gets the str mk.
 * 
 * @param tableName the table name
 * @param condition the condition
 * 
 * @return the str mk
 * 
 * @throws Exception the exception
 */
public String getStrMK(String tableName, String condition)
    throws Exception
{
	return qxglBeanService.getStrMK(tableName, condition);
}

/**
 * Gets the str m k2.
 * 
 * @param tableName the table name
 * @param condition the condition
 * 
 * @return the str m k2
 * 
 * @throws Exception  * @throws SQLException the SQL exception
 */
public String getStrMK2(String tableName, String condition) throws SQLException
{
	return qxglBeanService.getStrMK2(tableName, condition);
}
	

	
	/**
	 * Tran code p.
	 * 
	 * @param Str the str
	 * 
	 * @return the string
	 */
	public String tranCodeP(String Str) {

		return Str == null ? "" : Str;
	}

	/**
	 * Tran code n.
	 * 
	 * @param Str the str
	 * 
	 * @return the string
	 */
	public String tranCodeN(Object Str) {

		return Str == null ? "" : Str.toString();
	}
}
