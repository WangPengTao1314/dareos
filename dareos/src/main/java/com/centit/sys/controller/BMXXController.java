/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：BMXXAction.java
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.common.controller.BaseController;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.BMXXModel;
import com.centit.sys.model.BMXXTree;
import com.centit.sys.model.UserBean;
import com.centit.sys.service.BMXXService;

/**
 * 
 * @ClassName: BMXXController
 * @Description: 用一句话描述这个类的作用
 * @author wang_dw
 * @date 2019年3月1日 下午3:15:10
 *
 */
@Controller
@RequestMapping("sys/bmxx")
public class BMXXController extends BaseController {

	@Autowired
	private BMXXService bmxxService;
	
	private static final String webPath = "sys/bmxx";
	
    /**
     * 机构信息框架页面.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@RequestMapping(value = "/toFrames", method = { RequestMethod.GET, RequestMethod.POST })
    public String toFrames(HttpServletRequest request, HttpServletResponse response) {

    	request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
        return view(webPath, "bmxxwh_Frame");
    }


    /**
     * 查询结果列表.
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

        String flag = ParamUtil.get(request, "flag");//部门机构标识
        String BMXXID = ParamUtil.get(request, "BMXXID");//部门信息ID
        Map<String,Object> params=LogicUtil.getParameterMap(request);
        params.put("SJBMMCQuery", StringUtil.creCon("BMMC", String.valueOf(params.get("SJBMMC")), ","));
        String BMMC=String.valueOf(params.get("BMMC"));
        if(!StringUtil.isEmpty(BMMC)) {
        	params.put("BMMCQuery",BMMC);
        }
        params.put("JGMC", ParamUtil.utf8Decoder(request, "JGMC"));
        params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);

        if ("0".equals(flag)) {
            params.put("JGXXID", BMXXID);
        } else {
            params.put("BMXXID", BMXXID);
        }

        // 字段排序
        ParamUtil.setOrderField(request, params);
        bmxxService.pageQuery(params, pageDesc);
        request.setAttribute("params", params);
        request.setAttribute("page", pageDesc);
        return view(webPath, "bmxxwh_List");
    }


    /**
     * 明细.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = { "/toDetail"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toDetail(String BMXXID, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(BMXXID)) {
            Map entry = bmxxService.load(BMXXID);
            request.setAttribute("rst", entry);
        }
        return view(webPath, "bmxxwh_Detail");
    }


    /**
     * 删除.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = { "/delete"}, method = { RequestMethod.GET, RequestMethod.POST })
    public void delete(String BMXXID, HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        UserBean userBean = ParamUtil.getUserBean(request);

        if (StringUtils.isNotEmpty(BMXXID)) {
            try {
                Map <String, String> params = new HashMap <String, String>();
                params.put("BMXXID", BMXXID);
                if (bmxxService.ckxjbm(params) > 0) {
                    jsonResult = jsonResult(false, "该部门有生效的下级部门，不能删除！");
                    if (null != writer) {
                        writer.write(jsonResult);
                        writer.close();
                    }
                    return;
                }
//                bmxxService.txDelete(BMXXID, userBean);
//                bmxxService.clearCaches(BMXXID);
                bmxxService.txNewDelete(BMXXID,userBean);
            } catch (Exception ex) {
                ex.printStackTrace();
                jsonResult = jsonResult(false, "删除失败");
            }
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 编辑.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = { "/toEdit"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toEdit(String BMXXID, HttpServletRequest request, HttpServletResponse response) {

        if (StringUtils.isNotEmpty(BMXXID)) {
            Map entry = bmxxService.load(BMXXID);
            request.setAttribute("rst", entry);
        }
        return view(webPath, "bmxxwh_Edit");
    }


    /**
     * 编辑.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @PostMapping("/edit")
    public void edit(String BMXXID, HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        try {
        	UserBean userBean = ParamUtil.getUserBean(request);
            String jsonData = ParamUtil.get(request, "jsonData");
            BMXXModel model = null;
            if (StringUtils.isNotEmpty(jsonData)) {
                model = new Gson().fromJson(jsonData, new TypeToken <BMXXModel>() {
                }.getType());
            }

//            String BMXXID = ParamUtil.get(request, "BMXXID");
            String SJBM = model.getSJBM();
            String JGXXID = model.getJGXXID();
            if (StringUtils.isEmpty(BMXXID) && (bmxxService.getBmxxExits(model.getBMBH())) > 0) {
                jsonResult = jsonResult(false, "部门编号已经存在");
            } else {
            	if(bmxxService.loadJGZT(JGXXID))
            	{
            		try {
            			if (bmxxService.loadBMZT(SJBM)) {
                			bmxxService.txEdit(BMXXID, model, userBean);
                        }else{
                        	jsonResult = jsonResult(false, "不可选择停用的部门作为上级部门");
                        }
                		
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        jsonResult = jsonResult(false, "保存失败");
                    }
            	}else{
            		jsonResult = jsonResult(false, "不可选择停用的机构作为所属机构");
            	}
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
            jsonResult = jsonResult(false, "保存失败");
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 部门树.
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

        List <BMXXTree> tree = null;
        try {
        	UserBean userBean = ParamUtil.getUserBean(request);
            String theme = userBean.getUSERSTYLE();
            String ctx = request.getContextPath();
            tree = bmxxService.getTree(ctx, theme);
            String treeData = new Gson().toJson(tree);
            System.out.println(treeData);
            request.setAttribute("tree", treeData);

        } catch (Exception e) {
            request.setAttribute("msg", "部门失败！");
            return view(webPath, Consts.FAILURE);
        }
        return view(webPath, "bmxx_Tree");
    }


    /**
     * 停用/启用.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
    @SuppressWarnings("unchecked")
    @PostMapping("/changeState")
    public void changeState(String BMXXID, HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();

        boolean isChanged = false;
        UserBean userBean = ParamUtil.getUserBean(request);
//        String BMXXID = request.getParameter("BMXXID");
        //下级部门停用校验标记，0为校验前，1为校验后，校验后不需要再次校验
        String ckstate = request.getParameter("ckstate");

        Map <String, String> entry = bmxxService.load(BMXXID);

        String state = entry.get("BMZT");
        Map <String, String> params = new HashMap <String, String>();

        try {
            String changedState = "";
            params.put("BMXXID", BMXXID);
            params.put("UPDATER", userBean.getXTYHID());

            if (BusinessConsts.JC_STATE_ENABLE.equals(state)) {
                //校验该部门是否有未停用的下级部门和人员如果有则不许停用
                if (ckstate.equals("0") && bmxxService.ckxjbm(params) > 0) {
                    jsonResult = jsonResult(false, "stateError1");
                    if (null != writer) {
                        writer.write(jsonResult);
                        writer.close();
                    }
                    return;
                }
                changedState = BusinessConsts.JC_STATE_DISENABLE;
                params.put("BMZT", BusinessConsts.JC_STATE_DISENABLE);
                bmxxService.txUpdateAllById(params);
                isChanged = true;
            } else if (BusinessConsts.JC_STATE_DISENABLE.equals(state)) {
                //校验是否有上级部门是停用状态有则需要提示
                if (bmxxService.cksjbm(params) > 0) {
                    jsonResult = jsonResult(false, "该部门有未生效的上级部门，请先生效上级部门！");
                    if (null != writer) {
                        writer.write(jsonResult);
                        writer.close();
                    }
                    return;
                }
                changedState = BusinessConsts.JC_STATE_ENABLE;
                params.put("BMZT", BusinessConsts.JC_STATE_ENABLE);
                bmxxService.txUpdateById(params);
                isChanged = true;
            } else {
                changedState = BusinessConsts.JC_STATE_ENABLE;
                params.put("BMZT", BusinessConsts.JC_STATE_ENABLE);
                bmxxService.txUpdateById(params);
                isChanged = true;
            }
            if (isChanged) {
                jsonResult = jsonResult(true, changedState);
            } else {
                jsonResult = jsonResult(false, "状态不用修改");
            }
        } catch (RuntimeException e) {
            jsonResult = jsonResult(false, "状态修改失败");
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
    
    /**
	 * 根据id查部门信息 Description :
	 * 
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return
	 */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = { "/selectZTbyId"}, method = { RequestMethod.GET, RequestMethod.POST })
    public void selectZTbyId(HttpServletRequest request, HttpServletResponse response) {
		String bmxxId = ParamUtil.get(request, "BMXXID");
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String XX = "";
		try {
			if (StringUtils.isNotEmpty(bmxxId)) {
				Map <String, String> entry = bmxxService.load(bmxxId);
		        String JGMC = entry.get("JGMC");
		        String JGXXID = entry.get("JGXXID");
		        String ZTXXID = entry.get("ZTXXID");
		        
		        StringBuffer bf = new StringBuffer();
				bf.append(JGMC+","+JGXXID+","+ZTXXID+",");
			    XX = bf.substring(0, bf.length() - 1);
			    
				if (StringUtils.isNotEmpty(XX)) {
					jsonResult = jsonResult(true, XX);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (writer != null) {
			writer.print(jsonResult);
			writer.close();
		}
	}
}
