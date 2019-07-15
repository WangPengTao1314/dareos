/**

 * 项目名称：平台 

 * 系统名：账务基础数据 

 * 文件名：ZTWHAction.java 

 */

package com.centit.sys.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.common.controller.BaseController;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.core.po.PageDesc;
import com.centit.sys.model.UserBean;
import com.centit.sys.model.ZTWHModel;
import com.centit.sys.model.ZTWHTree;
import com.centit.sys.service.ZTWHService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * 
 * @module 财务管理
 * @fuc 帐套信息维护
 */
@Controller
@RequestMapping("/sys/ztxx")
public class ZTWHController extends BaseController {

	@Autowired
    private ZTWHService ztwhService;

	private static final String webPath = "sys/ztxxwh";

    /**
     * 帐套信息框架.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@RequestMapping(value = { "/toFrame"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toFrame(HttpServletRequest request, HttpServletResponse response) {
    	//request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
        return view(webPath, "ztxxwh_Frame");
    }


    /**
     * 帐套列表.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@RequestMapping(value = { "/toList"}, method = { RequestMethod.GET, RequestMethod.POST })

    public String toList(HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {

        Map <String, Object> params = new HashMap <String, Object>();
        ParamUtil.putStr2Map(request, "ZTXXID", params);
        ParamUtil.putStr2Map(request, "ZTBH", params);
        params.put("ZTMC", ParamUtil.utf8Decoder(request, "ZTMC"));
        ParamUtil.putStr2Map(request, "SJZT", params);
        ParamUtil.putStr2Map(request, "ZZSH", params);
        params.put("ZTLB", ParamUtil.utf8Decoder(request, "ZTLB"));
        params.put("ZT", ParamUtil.utf8Decoder(request, "ZT"));
        //初期化时只查询未提交和未审核通过的数据ZT
        //if ("".equals(search)) {
        	// params.put("conditionString", " STATE != '" + Consts.JC_STATE_DISENABLE + "'");
       // }
        //字段排序
        params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
        ParamUtil.setOrderField(request, params);
        ztwhService.pageQuery(params,pageDesc);
        //String QXJBCON = QXUtil.getQXTJ(userBean, PrivateConsts.ZTXXWH);
        //params.put("QXJBCON", QXJBCON);
        request.setAttribute("params", params);
        request.setAttribute("page", pageDesc);
        return view(webPath, "ztxxwh_List");
    }
    
    /**
     * To list one.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@RequestMapping(value = { "/toListOne"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toListOne( HttpServletRequest request, HttpServletResponse response) {

    	Map <String, Object> params = new HashMap <String, Object>();
    	ParamUtil.putStr2Map(request, "ZTXXID", params);
        ParamUtil.putStr2Map(request, "ZTBH", params);
        ParamUtil.putStr2Map(request, "ZTMC", params);
        ParamUtil.putStr2Map(request, "SJZT", params);
        //String sjztmc = ParamUtil.get(request, "SJZT");
        //params.put("SJZT", sjztmc);
        ParamUtil.putStr2Map(request, "ZZSH", params);
        ParamUtil.putStr2Map(request, "ZTLB", params);
        ParamUtil.putStr2Map(request, "ZT", params);

        //字段排序
        params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
        ParamUtil.setOrderField(request, params);
       // String QXJBCON = QXUtil.getQXTJ(userBean, PrivateConsts.ZTXXWH);
        //params.put("QXJBCON", QXJBCON);
        return view(webPath, "ztxxwh_List");
    }


    /**
     * 帐套详细信息.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@RequestMapping(value = { "/toDetail"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toDetail(HttpServletRequest request, HttpServletResponse response) {
        String ztxxID = ParamUtil.get(request, "ZTXXID");
        Map <String, Object> entry = ztwhService.getOneRecord(ztxxID);
        request.setAttribute("rst", entry);
        return view(webPath, "ztxxwh_Detail");
    }


    /**
     * 帐套信息新增或修改.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@RequestMapping(value = { "/toEdit"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toEdit(HttpServletRequest request, HttpServletResponse response) {
        String ztxxid = ParamUtil.get(request, "ZTXXID");
        String status = "add";
        if (StringUtils.isNotEmpty(ztxxid)) {
        	Map <String, Object> entry = ztwhService.getOneRecord(ztxxid);
            status = "modify";
            request.setAttribute("rst", entry);
        }
        request.setAttribute("status", status); // 
        return view(webPath, "ztxxwh_Edit");
    }


    /**
     * To save.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@RequestMapping(value = { "/toSave"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toSave(HttpServletRequest request, HttpServletResponse response) {

        String jsonData = ParamUtil.get(request, "jsonData");
        ZTWHModel ztwh = null;
        if (StringUtils.isNotEmpty(jsonData)) {
            try {
                ztwh = new Gson().fromJson(jsonData, new TypeToken <ZTWHModel>() {
                }.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Map <String, Object> params = new HashMap <String, Object>();
        params.put("ZTBH", ztwh.getZTBH());
        params.put("ZTMC", ztwh.getZTMC());
        params.put("ZTJC", ztwh.getZTJC());
        params.put("YJZBJ", Integer.toString(ztwh.getYJZBJ()));
        params.put("SJZT", "".equals(ztwh.getSJZT()) ? null : ztwh.getSJZT());
        params.put("ZZSH", ztwh.getZZSH());
        params.put("YYZZH", ztwh.getYYZZH());
        params.put("ZTLB", ztwh.getZTLB());
        String wldwxxid = ztwh.getWLDWXXID();
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        String ztwhid = ParamUtil.get(request, "ZTXXID");
        int flag = 1;
        if (StringUtils.isEmpty(ztwhid)) {
            List <ZTWHModel> bhList = ztwhService.getAllBH();
            String bh = ztwh.getZTBH();
            String ztmc = ztwh.getZTMC();
            for (int i = 0; i < bhList.size(); i++) {
                if (bh.equals(bhList.get(i).getZTBH())) {
                    flag = 0;
                    break;
                }
                if (ztmc.equals(bhList.get(i).getZTMC())) {
                    flag = 2;
                    break;
                }
            }
        }
        UserBean userBean = ParamUtil.getUserBean(request);
        if (flag == 0) {
            jsonResult = jsonResult(false, "帐套编号有重复值，请重新输入！");
        } else if (flag == 2) {
            jsonResult = jsonResult(false, "帐套名称有重复值，请重新输入！");
        } else {
            if (StringUtils.isNotEmpty(ztwhid)) {
                if (ztwh.getZTBH().equals(ztwh.getSJZT())) {
                    jsonResult = jsonResult(false, "帐套编号和上级帐套不能一样！");
                } else {
                    try {
                    	params.put("ZTXXID", ztwhid);
                        params.put("UPDATER", userBean.getXM());
                        ztwhService.updateById(params);
                        request.setAttribute("msg", "保存成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                        jsonResult = jsonResult(false, "新增保存失败");
                    }
                }
            } else {
                try {
                	if (ztwh.getSJZT() != "") {
                		String SJZT = ztwh.getSJZT();
                		if (ztwhService.loadZTZT(SJZT)) {
                			//制单人   
                        	if(wldwxxid!=null && !"".equals(wldwxxid)){ //只要wldwxxid不为空的话，就说明帐套是从wldwxx中选的
                        		params.put("ZTXXID", wldwxxid);
                        	}else{
                        		params.put("ZTXXID", ztwh.getZTBH());
                        	}
                            params.put("STATE", BusinessConsts.JC_STATE_DEFAULT);
                            params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
                            params.put("CREATER", userBean.getYHBH());
                            params.put("CRENAME", userBean.getXM());
                            ztwhService.insertRecord(params);
                            request.setAttribute("msg", "保存成功");
                		}else {
                            jsonResult = jsonResult(false, "不可选择停用的帐套作为上级帐套");
                        }
                	}
                } catch (Exception e) {
                    jsonResult = jsonResult(false, "新增保存失败");
                    e.printStackTrace();
                }
            }
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
        return view(Consts.WEB_PATH, Consts.SUCCESS);
    }


    /**
     * 更新状态.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
	@RequestMapping(value = { "/updateZTStatus"}, method = { RequestMethod.GET, RequestMethod.POST })
    public void updateZTStatus(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        Map <String, Object> params = new HashMap <String, Object>();

        String ztxxid = ParamUtil.get(request, "ztxxid");
        params.put("ztxxid", ztxxid);
        String flag = ParamUtil.get(request, "flag");
        if ("1".equals(flag)) {
            params.put("STATE", BusinessConsts.JC_STATE_ENABLE);

        } else {
            params.put("STATE", BusinessConsts.JC_STATE_DISENABLE);
        }
        
        try {
        	if (!"1".equals(flag)||ztwhService.querySjForInt(params)) {
        		ztwhService.updateZTStatus(params);
                request.setAttribute("msg", "更新成功！");
                jsonResult = jsonResult(true,(String)params.get("STATE"));
            }else {
                jsonResult = jsonResult(false, "请先启用上级");
            }
        	
            
        } catch (Exception ex) {
            jsonResult = jsonResult(false, "更新失败!");
            ex.printStackTrace();
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 删除.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
	@RequestMapping(value = { "/delete"}, method = { RequestMethod.GET, RequestMethod.POST })
    public void delete(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        String ztxxid = ParamUtil.get(request, "ZTXXID");
        UserBean userBean = ParamUtil.getUserBean(request);
        Map <String, Object> params = new HashMap <String, Object>();
        int countRecord = 0;
        try {
            countRecord = ztwhService.getCountRecord(ztxxid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (countRecord > 0) {
            jsonResult = jsonResult(false, "此帐套有下级帐套，请删除完下级帐套再删除!");
        } else {
            try {
                params.put("UPDATER", userBean.getXM());
                params.put("ztxxid", ztxxid);
                ztwhService.deleteById(params);
                request.setAttribute("msg", "删除成功！");
            } catch (Exception ex) {
                jsonResult = jsonResult(false, "删除失败!");
            }
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
    
    /**
     * 显示树.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@RequestMapping(value = { "/showTree"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String showTree(HttpServletRequest request, HttpServletResponse response) {

        List <ZTWHTree> trees;
        try {
            trees = ztwhService.queryTree();
            String treeData = new Gson().toJson(trees);
            request.setAttribute("tree", treeData); // 
            return view(webPath, "ztwh_tree");
        } catch (Exception e) {
            request.setAttribute("msg", "获取帐套失败！");
            return view(Consts.WEB_PATH, Consts.FAILURE);
        }
    }

}
