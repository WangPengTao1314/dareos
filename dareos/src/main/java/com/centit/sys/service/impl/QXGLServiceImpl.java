package com.centit.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.centit.commons.model.Consts;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.TimeComm;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.QXGLService;
// TODO: Auto-generated Javadoc

/**
 * The Class QXGLBeanService.
 * 
 * @module 系统模块
 * @fuc 系统模块
 * @version 1.1
 * @author 朱晓文
 */
@Service
public class QXGLServiceImpl  implements QXGLService{

	
	/**
	 * Gets the row num.
	 * 
	 * @param tab the tab
	 * @param con the con
	 * 
	 * @return the row num
	 */
	public boolean getRowNum(String tab,String con) {
		int num = InterUtil.getRowNum(tab, con);
		if(num==0)
		return false;
		else
		return true;	
	}
	
	
	/**
	 * Gets the qX tab.
	 * 
	 * @param keyName the key name
	 * @param keyID the key id
	 * @param adminXTYHID the admin xtyhid
	 * @param loginJGXXID the login jgxxid
	 * @param request the request
	 * 
	 * @return the qX tab
	 * 
	 * @throws Exception the exception
	 */
	public String getQXTab(String keyName, String keyID, String adminXTYHID, String loginJGXXID,HttpServletRequest request) throws Exception
	{
		StringBuffer returnStr = new StringBuffer("");
	    String sql = "select SYSTEMID,SYSTEMMC from T_SYSTEM  ";
	    try
		{
	    	List<Map<String,String>> resList =InterUtil.selcomList(sql);
			int maxlength=resList.size();
			for (int i = 0; i < maxlength; i++) {
				Map<String,String> temMap = resList.get(i);								
					returnStr.append(
						"<td height=\"\" class=\"label_down label_line\" width=\"100\" id=\"BlueLabel"
							+ i
							+ "\" onclick=\"showit(this.id,'toMkxxTree?MKLB="
							+ temMap.get("SYSTEMID")
							+ "&KeyName="
							+ keyName
							+ "&KeyID="
							+ keyID
							+ "&sid="
							+ new Date().getTime()
							+ "',"
							+ i
							+ ")\">"
							+ temMap.get("SYSTEMMC")
							+ "</td>");
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return returnStr.toString();
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
	
	StringBuffer aSQL = new StringBuffer();
	aSQL.append("select count(*) NUM from T_SYS_XTMK where MKSM='");
	aSQL.append(XTMC);
	if(Consts.DBTYPE.equals("MSSQL"))
	{
		
		aSQL.append("' AND len(rtrim(MKBH))=");
	}else
	{
		aSQL.append("' AND length(rtrim(MKBH))=");
	}
	
	aSQL.append(length);
	aSQL.append(" AND ");
	aSQL.append(condition);
	Map<String,String> temMap =InterUtil.selcom( aSQL.toString());

    int num = 0;
    if(temMap!=null)
    {
        num = Integer.parseInt(String.valueOf(temMap.get("NUM")));
    }
    String MKAry[] = new String[num * 3];
    
    
    aSQL.delete(0, aSQL.length());
    
    if(Consts.DBTYPE.equals("MSSQL"))
	{
    	
    	aSQL.append("select MKMC,MKBH,XTMKDESC  from T_SYS_XTMK where MKSM='" + XTMC + "' AND len(rtrim(MKBH))=" + length + " AND " + condition + " order by MKBH ");
	}else
	{
		aSQL.append("select MKMC,MKBH,XTMKDESC  from T_SYS_XTMK where MKSM='" + XTMC + "' AND length(rtrim(MKBH))=" + length + " AND " + condition + " order by MKBH ");
		
	}
    List<Map<String,String>> reList =InterUtil.selcomList(aSQL.toString());
    int j=0;
    for(int i=0;i<reList.size();i++)
	{
    	Map<String,String> tempMap=reList.get(i);
		    MKAry[j] = tranCodeN(tempMap.get("MKMC"));
            MKAry[j + 1] = tranCodeN(tempMap.get("MKBH"));
            MKAry[j + 2] = tranCodeN(tempMap.get("XTMKDESC"));
            if(MKAry[j + 2] == null)
            {
                MKAry[j + 2] = "";
            }
		
		j=j+3;
	}
  return MKAry;
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
	 StringBuffer returnStrBuf = new StringBuffer("");

		 List<Map<String,String>> reList =InterUtil.selcomList("select * from T_SYS_QXJB");
	     for(int i=0;i<reList.size();i++)
		 {
	    	 Map<String,String> tempMap=reList.get(i);
			 returnStrBuf.append("<input type=\"radio\" style=\"\" name=\"defaultRadio\" ");
			 if(i == 6)
         {
             returnStrBuf.append(" checked ");
         }
         returnStrBuf.append("value='" + tranCodeN(tempMap.get("QXJBID"))  + "' onclick=\"callDefaultRadio('" + tranCodeN(tempMap.get("QXJBID"))  + "')\">" + tranCodeN(tempMap.get("JBMC")));
		  }
		
	  return returnStrBuf.toString();
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
	StringBuffer returnStrBuf = new StringBuffer("");
	
		 List<Map<String,String>> reList =InterUtil.selcomList("select * from T_SYS_QXJB order by QXJBID");
		int i = 0;
    if(MKQXJB.equals("Default"))
    {
        MKQXJB = "6";
    }
	     for(;i<reList.size();i++)
		 {
	    	 Map<String,String> tempMap=reList.get(i);
			 returnStrBuf.append("<input type=\"radio\" style=\"\" name=\"defaultRadio\" ");
         
         if(tranCodeN(tempMap.get("QXJBID")).equals(MKQXJB))
         {
             returnStrBuf.append(" checked ");
         }
         returnStrBuf.append("value='" + tranCodeN(tempMap.get("QXJBID")) + "' onclick=\"callDefaultRadio('" + tranCodeN(tempMap.get("QXJBID")) + "')\">" + tranCodeN(tempMap.get("JBMC")));
		 }
	 return returnStrBuf.toString();
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
 * 
 * @method returnQXJBRadioList
 * @author Administrator
 * @create time 2009-8-4 14:31:30
 */
public String returnQXJBRadioList(String MKQXJB,UserBean aUserBean)
	throws Exception
{
	StringBuffer returnStrBuf = new StringBuffer("");
	boolean isDisabled = false ;
	if ("administrator".equals(aUserBean.getYHM())){
		isDisabled = true; 
	}else {
		isDisabled = this.checkDisabled(aUserBean.getXTYHID());
	}
	
	
	
		 List<Map<String,String>> reList =InterUtil.selcomList("select * from T_SYS_QXJB");
		 
	
	int i = 0;
	if(MKQXJB.equals("Default"))
	{
		MKQXJB = "6";
	}
	for(;i<reList.size();i++)
	 {
		Map<String,String> tempMap=reList.get(i);
		
		returnStrBuf.append("<input type=\"radio\" style=\"\" name=\"defaultRadio\" ");
		 String tempstr  = tranCodeN(tempMap.get("QXJBID"));
		if(tempstr.equals(MKQXJB))
		{
			returnStrBuf.append(" checked ");
		}
		if (tempstr.equals("1")&&!isDisabled){
			returnStrBuf.append(" disabled ");
		}
		//End
		 
		returnStrBuf.append("value='" + tranCodeN(tempMap.get("QXJBID")) + "' onclick=\"callDefaultRadio('" + tranCodeN(tempMap.get("QXJBID"))  + "')\">" + tranCodeN(tempMap.get("JBMC")) );
		i++;
	}
	return returnStrBuf.toString();
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
	boolean  result = false ;
	String sql  = "select *    from T_SYS_XTYHJG WHERE XTYHID IN (SELECT XTYHID FROM T_SYS_XTYH WHERE YHM='"+xtyhid+"' )";
	
	Map<String,String> temMap = InterUtil.selcom(sql);
	if (temMap!=null){
		result = true;
	}
	return result;
}

/**
 * Return qxjb radio tree.
 * 
 * @return the string
 * 
 * @throws Exception the exception
 */
public String returnQXJBRadioTree()
    throws Exception
{
    
	 List<Map<String,String>> reList = InterUtil.selcomList("select * from T_SYS_QXJB");
    
   StringBuffer returnStrBuf = new StringBuffer("");
   for(int i=0;i<reList.size();i++)
	 {
	   Map<String,String> tempMap=reList.get(i);
        returnStrBuf.append("<input  style=\"\" type=\"radio\" name=\"radiobutton#00#\" ");
        if(i == 0)
        {
            returnStrBuf.append(" checked ");
        }
        returnStrBuf.append("value=\"#00#_" + tranCodeN(tempMap.get("QXJBID")) + "\">" + tranCodeN(tempMap.get("JBMC")));
    }

    return returnStrBuf.toString();
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
 try  {

	 List<Map<String,String>> reList = InterUtil.selcomList("select QXJBID,XTMKID from " + tableName + condition);
    StringBuffer strMK = new StringBuffer("");
    for(int i=0;i<reList.size();i++)
	 {
    	Map<String,String> tempMap=reList.get(i);
		strMK.append(tranCodeN(tempMap.get("XTMKID"))  + "_" + tranCodeN(tempMap.get("QXJBID")) + ";");
    }
   
    return strMK.toString();
 }catch(Exception ex){
 	ex.printStackTrace();
 	throw ex;
 }
}

/**
 * Gets the str m k2.
 * 
 * @param tableName the table name
 * @param condition the condition
 * 
 * @return the str m k2
 * 
 * @throws Exception  */
public String getStrMK2(String tableName, String condition) 
{

	StringBuffer strMK = new StringBuffer("");
	 List<Map<String,String>> reList = InterUtil.selcomList("select XTMKID from " + tableName + condition);
	 for(int i=0;i<reList.size();i++)
	 {
		 Map<String,String> tempMap=reList.get(i);
		strMK.append(tranCodeN(tempMap.get("XTMKID"))+ ";");
	}

	return strMK.toString();
}



 /**
  * Insert xtyhqx self.
  * 
  * @param XTYHID the xTYHID
  * @param MKSM the mKSM
  * @param ins_name2 the ins_name2
  * @param ins_value2 the ins_value2
  * @param selXTMKID the sel xtmkid
  * @param FINALQXMKCodeLength the fINALQXMK code length
  * @param request the request
  * 
  * @throws Exception the exception
  */
 public void insertXTYHQXSelf(String XTYHID, String MKSM,String [] ins_name2,String [] ins_value2,String[] selXTMKID,int FINALQXMKCodeLength,HttpServletRequest request)
 throws Exception
{
			//更新用户自有权限信息
			try {

			String	tempsql = "delete from T_SYS_YHQX where XTYHID='"
					+ XTYHID
					+ "' AND XTMKID in (select XTMKID from T_SYS_XTMK where MKSM='"
					+ MKSM
					+ "')";
		    InterUtil.delete(tempsql);
            if (selXTMKID != null) {
					int i = 0;
					Vector<String> tempVector = new Vector<String>();
					for (i = 0; i < selXTMKID.length; i++) {
						if (selXTMKID[i].length() == FINALQXMKCodeLength) {
							tempVector.add(selXTMKID[i]);
						}
					}
					String[] ins_YH_XTMKID = new String[tempVector.size()];
					String[] ins_YH_QXJBID = new String[tempVector.size()];
					String tempStr = "";
					for (i = 0; i < tempVector.size(); i++) {
						tempStr = (String) tempVector.get(i);
						//sevQXGLBean.insert(ins_YH_XTMKID, tempStr, i);
						ins_YH_XTMKID[i]=tempStr;
						ins_YH_QXJBID[i]=tranCodeP(request.getParameter("radioSel" + tempStr));
					}

					for (i = 0; i < ins_YH_XTMKID.length; i++) {
						ins_value2[0] =
							TimeComm.getPreTimeStamp("YHQX") + i;
						ins_value2[1] = XTYHID;
						ins_value2[2] = ins_YH_XTMKID[i];
						ins_value2[3] =
							ins_YH_QXJBID[i].substring(
								FINALQXMKCodeLength + 1,
								FINALQXMKCodeLength + 2);
						
						
							tempsql =" insert into T_SYS_YHQX(YHQXID, XTYHID, XTMKID, QXJBID) values('"
							 +ins_value2[0]+"','"
							 +ins_value2[1]+"','"
							 +ins_value2[2]+"','"
							 +ins_value2[3]+"')";
							InterUtil.insert(tempsql);
						

				}
				//Start: 修改初始系统用户权限状态 0: 等待组建
				//sevQXGLBean.executeStaticSql(
				//	"update T_SYS_XTYH set XTYHQXZT='0',refreshqys='1' where XTYHID ='"
				//		+ XTYHID
				//		+ "'");
				//End.
            }
			} catch (Exception ex) {
				ex.printStackTrace();
			}

}
 
 
 /**
  * Insertxtjs qx.
  * 
  * @param XTJSID the xTJSID
  * @param MKSM the mKSM
  * @param ins_name2 the ins_name2
  * @param ins_value2 the ins_value2
  * @param selXTMKID the sel xtmkid
  * @param FINALQXMKCodeLength the fINALQXMK code length
  * @param request the request
  * 
  * @throws Exception the exception
  */
 public void insertxtjsQX(String XTJSID, String MKSM,String [] ins_name2,String [] ins_value2,String[] selXTMKID,int FINALQXMKCodeLength,HttpServletRequest request)
 throws Exception
{
			//更新系统权限信息
			try {

			String	tempsql = "delete from T_SYS_JSQX where XTJSID='"
					+ XTJSID
					+ "' AND XTMKID in (select XTMKID from T_SYS_XTMK where MKSM='"
					+ MKSM
					+ "')";
		    InterUtil.delete(tempsql);
		    
            if (selXTMKID != null) {
					int i = 0;
					Vector<String> tempVector = new Vector<String>();
					for (i = 0; i < selXTMKID.length; i++) {
						if (selXTMKID[i].length() == FINALQXMKCodeLength) {
							tempVector.add(selXTMKID[i]);
						}
					}
					String[] ins_YH_XTMKID = new String[tempVector.size()];
					String[] ins_YH_QXJBID = new String[tempVector.size()];
					String tempStr = "";
					for (i = 0; i < tempVector.size(); i++) {
						tempStr = (String) tempVector.get(i);
						//sevQXGLBean.insert(ins_YH_XTMKID, tempStr, i);
						ins_YH_XTMKID[i]=tempStr;
						ins_YH_QXJBID[i]=tranCodeP(request.getParameter("radioSel" + tempStr));
					}

					for (i = 0; i < ins_YH_XTMKID.length; i++) {
						ins_value2[0] =
							TimeComm.getPreTimeStamp("JSQX") + i;
						ins_value2[1] = XTJSID;
						ins_value2[2] = ins_YH_XTMKID[i];
						ins_value2[3] =
							ins_YH_QXJBID[i].substring(
								FINALQXMKCodeLength + 1,
								FINALQXMKCodeLength + 2);
						
						
							tempsql =" insert into T_SYS_JSQX(JSQXID, XTJSID, XTMKID, QXJBID) values('"
							 +ins_value2[0]+"','"
							 +ins_value2[1]+"','"
							 +ins_value2[2]+"','"
							 +ins_value2[3]+"')";
						InterUtil.insert(tempsql);

				}
				//Start: 修改初始系统用户权限状态 0: 等待组建
				//sevQXGLBean.executeStaticSql(
				//	"update T_SYS_XTYH set XTYHQXZT='0',refreshqys='1' where XTYHID ='"
				//		+ XTYHID
				//		+ "'");
				//End.
            }
			} catch (Exception ex) {
				ex.printStackTrace();
			}

}
 
 //更新工作组权限
 /**
  * Insertgzz qx.
  * 
  * @param GZZXXID the gZZXXID
  * @param MKSM the mKSM
  * @param ins_name2 the ins_name2
  * @param ins_value2 the ins_value2
  * @param selXTMKID the sel xtmkid
  * @param FINALQXMKCodeLength the fINALQXMK code length
  * @param request the request
  * 
  * @throws Exception the exception
  */
 public void insertgzzQX(String GZZXXID, String MKSM,String [] ins_name2,String [] ins_value2,String[] selXTMKID,int FINALQXMKCodeLength,HttpServletRequest request)
 throws Exception
{
			//更新系统权限信息
			try {

			String	tempsql = "delete from T_SYS_GZZQX where GZZXXID='"
					+ GZZXXID
					+ "' AND XTMKID in (select XTMKID from T_SYS_XTMK where MKSM='"
					+ MKSM
					+ "')";
		    InterUtil.delete(tempsql);
		    
            if (selXTMKID != null) {
					int i = 0;
					Vector<String> tempVector = new Vector<String>();
					for (i = 0; i < selXTMKID.length; i++) {
						if (selXTMKID[i].length() == FINALQXMKCodeLength) {
							tempVector.add(selXTMKID[i]);
						}
					}
					String[] ins_YH_XTMKID = new String[tempVector.size()];
					String[] ins_YH_QXJBID = new String[tempVector.size()];
					String tempStr = "";
					for (i = 0; i < tempVector.size(); i++) {
						tempStr = (String) tempVector.get(i);
						//sevQXGLBean.insert(ins_YH_XTMKID, tempStr, i);
						ins_YH_XTMKID[i]=tempStr;
						ins_YH_QXJBID[i]=tranCodeP(request.getParameter("radioSel" + tempStr));
					}

					for (i = 0; i < ins_YH_XTMKID.length; i++) {
						ins_value2[0] =
							TimeComm.getPreTimeStamp("GZZQX") + i;
						ins_value2[1] = GZZXXID;
						ins_value2[2] = ins_YH_XTMKID[i];
						ins_value2[3] =
							ins_YH_QXJBID[i].substring(
								FINALQXMKCodeLength + 1,
								FINALQXMKCodeLength + 2);
						
						
							tempsql =" insert into T_SYS_GZZQX(GZZQXID, GZZXXID, XTMKID, QXJBID) values('"
							 +ins_value2[0]+"','"
							 +ins_value2[1]+"','"
							 +ins_value2[2]+"','"
							 +ins_value2[3]+"')";
							InterUtil.insert(tempsql);
						

				}
				//Start: 修改初始系统用户权限状态 0: 等待组建
				//sevQXGLBean.executeStaticSql(
				//	"update T_SYS_XTYH set XTYHQXZT='0',refreshqys='1' where XTYHID ='"
				//		+ XTYHID
				//		+ "'");
				//End.
            }
			} catch (Exception ex) {
				ex.printStackTrace();
			}

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
