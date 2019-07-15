/**
  *@module 系统模块
  *@fuc 系统模块
  *@version 1.1
  *@author 朱晓文
  */
package com.centit.sys.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centit.common.controller.BaseController;
import com.centit.commons.servlet.ContextServlet;
import com.centit.commons.servlet.DictInfoBean;
import com.centit.commons.util.ParamUtil;
import com.centit.sys.service.DictService;

// TODO: Auto-generated Javadoc
/**
 * The Class DicSelectAction.
 *
 * @module 系统模块
 * @fuc 系统模块
 * @version 1.1
 * @author 朱晓文
 */
@Controller
@RequestMapping("/sys/dicselect")
public class DicSelectController extends BaseController {

	@Autowired
    private DictService dictService;


    /**
     * 获取字典数据.
     *
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/dictSel")
    public void dictSel(HttpServletRequest request, HttpServletResponse response) {

        //String jsonArg = ParamUtil.get(request, "jsonData");
        //Id:"test1",dictId:"sel_90",action:"dictSel"

        //DicSelectModel dicSelect = new Gson().fromJson(jsonArg, new TypeToken <DicSelectModel>() {}.getType());
        String dictId = ParamUtil.get(request, "dictId");//dicSelect.getDictId();

        String selCommHead = "System"; // 默认为物流的配置文件
        if (dictId.indexOf("_") != -1) {
            selCommHead = dictId.substring(0, dictId.indexOf("_"));
        } else {
            dictId = selCommHead + "_" + dictId;
        }

        String fileName = request.getSession().getServletContext().getRealPath("")  + File.separator + "pages" + File.separator + "sys" + File.separator + "configfiles"
                + File.separator + "dictionary" + File.separator  + "dictConfig_" + selCommHead + ".xml";
        String jsonResult = jsonResult();
        // List dicList = ContextServlet.getDicListHm(this.key);
        PrintWriter writer = getWriter(response);
        StringBuilder sbdict = new StringBuilder();
        try {
        	 List <?> dicList = ContextServlet.getDicList(fileName + dictId.substring(dictId.indexOf("_")));
             if (null == dicList) {
                 dicList = dictService.getDictList(fileName, dictId.substring(selCommHead.length() + 1),ParamUtil.get(request, "condition"));
             }
        	String defVal = ParamUtil.get(request, "defaultValue");
            int listCount = dicList.size();
            sbdict.append("<option value=''>-请选择-</option>");
            for (int i = 0; i < listCount; i++) {
                DictInfoBean dictBean = (DictInfoBean) dicList.get(i);
//                if (defVal.equals(dictBean.getDiccnname())) { modify by  zbb 2014-3-13
                if (defVal.equals(dictBean.getDicname())) {
                    sbdict.append("<option selected value='");
                    sbdict.append(dictBean.getDicname()).append("'>");
                    sbdict.append(dictBean.getDiccnname()).append("</option>");
                } else {
                    sbdict.append("<option value='");
                    sbdict.append(dictBean.getDicname()).append("'>");
                    sbdict.append(dictBean.getDiccnname()).append("</option>");
                }
            }
            if (listCount > 0) {
            	jsonResult = jsonResult(true, sbdict.toString());
			} else {
	        	jsonResult = jsonResult(false, "数据字典未启用！请联系管理员");
			}
        } catch (Exception e) {
        	e.printStackTrace();
        	jsonResult = jsonResult(false, "数据字典获取失败！请联系管理员");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }

    }


    /**
     * Sets the dict service.
     *
     * @param dictService the new dict service
     */
    public void setDictService(DictService dictService) {

        this.dictService = dictService;
    }

}
