package com.centit.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.commons.model.Consts;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.TimeComm;
import com.centit.sys.service.XTSQService;

// TODO: Auto-generated Javadoc
/**
 * The Class XTSQServiceImpl.
 */
@Service
public class XTSQServiceImpl  implements XTSQService {





    public String getQXTab(String keyName, String keyID, String adminXTYHID, String loginJGXXID, HttpServletRequest request) throws Exception {

        StringBuffer returnStr = new StringBuffer("");
        String sql = "select SYSTEMID,SYSTEMMC from T_system  ";

        try {
            List<Map<String,String>> resList = InterUtil.selcomList(sql);
            int maxlength = resList.size();
            for (int i = 0; i < maxlength; i++) {
                Map<String,String> temMap =  resList.get(i);
                returnStr.append("<td height=\"\" class=\"label_down label_line\" width=\"100\" id=\"BlueLabel" + i

                + "\" onclick=\"showit(this.id,'toMkxxTree?MKLB=" + temMap.get("SYSTEMID") + "&KeyName=" + keyName

                + "&KeyID=" + keyID + "&sid=" + new Date().getTime() + "'," + i + ")\">" + temMap.get("SYSTEMMC") + "</td>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnStr.toString();

    }


    
    public String[] getMKAry(String XTMC, int length, String condition) throws Exception {

        StringBuffer aSQL = new StringBuffer();
        aSQL.append("select count(*) NUM from T_SYS_XTMK where MKSM='");
        aSQL.append(XTMC);
        if (Consts.DBTYPE.equals("MSSQL")) {

            aSQL.append("' AND len(rtrim(MKBH))=");
        } else {
            aSQL.append("' AND length(rtrim(MKBH))=");
        }

        aSQL.append(length);
        aSQL.append("AND ");
        aSQL.append(condition);
        Map<String,String> temMap = InterUtil.selcom(aSQL.toString());

        int num = 0;
        if (temMap != null) {
            num = Integer.parseInt(String.valueOf(temMap.get("NUM")));
        }
        String MKAry[] = new String[num * 3];

        aSQL.delete(0, aSQL.length());

        if (Consts.DBTYPE.equals("MSSQL")) {

            aSQL.append("select MKMC,MKBH,XTMKDESC  from T_SYS_XTMK where MKSM='" + XTMC + "' AND len(rtrim(MKBH))=" + length + " AND " + condition
                    + " order by MKBH ");
        } else {
            aSQL.append("select MKMC,MKBH,XTMKDESC  from T_SYS_XTMK where MKSM='" + XTMC + "' AND length(rtrim(MKBH))=" + length + " AND " + condition
                    + " order by MKBH ");

        }
        List<Map<String,String>> reList = InterUtil.selcomList(aSQL.toString());
        int j = 0;
        for (int i = 0; i < reList.size(); i++) {
            Map<String,String> tempMap = reList.get(i);
            MKAry[j] = tranCodeN(tempMap.get("MKMC"));
            MKAry[j + 1] = tranCodeN(tempMap.get("MKBH"));
            MKAry[j + 2] = tranCodeN(tempMap.get("XTMKDESC"));
            if (MKAry[j + 2] == null) {
                MKAry[j + 2] = "";
            }

            j = j + 3;
        }

        return MKAry;
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


    public String returnQXJBRadioList(String MKQXJB) throws Exception {

        StringBuffer returnStrBuf = new StringBuffer("");

        List<Map<String,String>> reList = InterUtil.selcomList("select * from T_SYS_QXJB");
        int i = 0;
        if (MKQXJB.equals("Default")) {
            MKQXJB = "6";
        }
        for (; i < reList.size(); i++) {
            Map<String,String> tempMap = reList.get(i);
            returnStrBuf.append("<input type=\"radio\" style=\"\" name=\"defaultRadio\" ");

            if (tranCodeN(tempMap.get("QXJBID")).equals(MKQXJB)) {
                returnStrBuf.append(" checked ");
            }
            returnStrBuf.append("value='" + tranCodeN(tempMap.get("QXJBID")) + "' onclick=\"callDefaultRadio('" + tranCodeN(tempMap.get("QXJBID"))
                    + "')\">" + tranCodeN(tempMap.get("JBMC")));
        }
        return returnStrBuf.toString();

    }


    public String getStrMK2(String tableName, String condition) {

        StringBuffer strMK = new StringBuffer("");
        List<Map<String,String>> reList = InterUtil.selcomList("select XTMKID from " + tableName + condition);
        for (int i = 0; i < reList.size(); i++) {
            Map<String,String> tempMap =reList.get(i);
            strMK.append(tranCodeN(tempMap.get("XTMKID")) + ";");
        }

        return strMK.toString();

    }


    public String getStrMK(String tableName, String condition) throws Exception {

        try {

            List<Map<String,String>> reList = InterUtil.selcomList( "select XTSQID,XTMKID from " + tableName + condition);
            StringBuffer strMK = new StringBuffer("");
            for (int i = 0; i < reList.size(); i++) {
                Map<String,String> tempMap =reList.get(i);
                strMK.append(tranCodeN(tempMap.get("XTMKID")) + "_" + tranCodeN(tempMap.get("QXJBID")) + ";");
            }

            return strMK.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }


    /**
     * 更新用户权限.
     * 
     * @param XTYHID the xTYHID
     * @param MKSM the mKSM
     * @param ins_name2 the ins_name2
     * @param ins_value2 the ins_value2
     * @param selXTMKID the sel xtmkid
     * @param request the request
     * 
     * @throws Exception the exception
     */
    @Transactional
    public void insertXTSQ(String XTYHID, String MKSM, String[] ins_name2, String[] ins_value2, String[] selXTMKID, HttpServletRequest request)
            throws Exception {

        //更新用户自有权限信息
        try {

            String tempsql = "delete from T_SYS_XTSQ where XTYHID='" + XTYHID + "' AND XTMKID in (select XTMKID from T_SYS_XTMK where MKSM='" + MKSM
                    + "')";
            InterUtil.delete(tempsql);
            if (selXTMKID != null) {
                int i = 0;
                Vector<String> tempVector = new Vector<String>();
                for (i = 0; i < selXTMKID.length; i++) {
                    //if (selXTMKID[i].length() == FINALQXMKCodeLength) {
                    tempVector.add(selXTMKID[i]);
                    //}
                }
                String[] ins_YH_XTMKID = new String[tempVector.size()];
                //                String[] ins_YH_QXJBID = new String[tempVector.size()];
                String tempStr = "";
                for (i = 0; i < tempVector.size(); i++) {
                    tempStr = (String) tempVector.get(i);
                    ins_YH_XTMKID[i] = tempStr;
                    //                    ins_YH_QXJBID[i] = tranCodeP(request.getParameter("radioSel" + tempStr));
                }

                for (i = 0; i < ins_YH_XTMKID.length; i++) {
                    ins_value2[0] = TimeComm.getPreTimeStamp("XTSQ") + i;
                    ins_value2[1] = XTYHID;
                    ins_value2[2] = ins_YH_XTMKID[i];
                    //                    ins_value2[3] = ins_YH_QXJBID[i].substring(FINALQXMKCodeLength + 1, FINALQXMKCodeLength + 2);

                    tempsql = " insert into T_SYS_XTSQ(XTSQID, XTYHID, XTMKID) values('" + ins_value2[0] + "','"
                            + ins_value2[1] + "','" + ins_value2[2] + "')";
                    InterUtil.insert(tempsql);

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
