package com.centit.system.user.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.centit.common.controller.BaseController;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.UserBean;
import com.centit.system.user.entity.RYXXModel;
import com.centit.system.user.entity.RYXXTree;
import com.centit.system.user.service.RYXXService;

/**
 * 
 * @ClassName: RYXXController 
 * @Description: 系统人员
 * @author: liu_yg
 * @date: 2019年2月22日 下午3:32:37
 */
@Controller
@RequestMapping("/sys/user")
public class RYXXController extends BaseController {

	@Autowired
    private RYXXService ryxxService;
	
	private static final String webPath = "sys/ryxx";

    /**
     * 
     * @Title: toList 
     * @Description: 查询列表
     * @author: liu_yg
     * @date: 2019年2月22日 下午3:33:23 
     * @param request
     * @param response
     * @param pageDesc
     * @return
     * @return: ResponseBean
     */
    @RequestMapping(value = { "/toList"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String list(HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {
    	Map<String,Object> params=LogicUtil.getParameterMap(request);
        params.put("RYBHQuery", StringUtil.creCon("u.RYBH", String.valueOf(params.get("RYBH")), ","));
        params.put("JGMCQuery", StringUtil.creCon("u.JGMC", String.valueOf(params.get("JGMC")), ","));
        params.put("BMMCQuery", StringUtil.creCon("u.BMMC", String.valueOf(params.get("BMMC")), ","));
        params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
        params.put("IS_DRP_LEDGER", "0"); //过滤掉分销商的人员

        //字段排序
        ParamUtil.setOrderField(request, params, "u.RYBH", "asc");
        
        ryxxService.pageQuery(params,pageDesc);
        
        System.out.println(pageDesc.getFooterHtml());
        System.out.println(pageDesc.getToolbarHtml());

        request.setAttribute("params", params);
        request.setAttribute("page", pageDesc);
        return view(webPath, "ryxxwh_List");
    }

    
    /**
     * 跳转页面
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/toFrames")
    public String toFrames(HttpServletRequest request, HttpServletResponse response) {

    	request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
    	return view(webPath, "ryxxwh_Frame");
    }

    /**
     * 
     * @Title: getObjById 
     * @Description: 根据ID获取对象信息
     * @author: liu_yg
     * @date: 2019年2月22日 下午3:33:52 
     * @param request
     * @param response
     * @return
     * @return: ResponseBean
     */
//	@PostMapping("/getObjById/{ryxxId}")
//    public ResponseBean getObjById(HttpServletRequest request, HttpServletResponse response,@PathVariable String ryxxId) {
//        RYXXModel obj = ryxxService.getObjById(ryxxId);
//        return ResponseBean.Success(obj);
//    }
	
	/**
	 * 跳转详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/toDetail")
	public String toDetail( HttpServletRequest request, HttpServletResponse response) {

        String RYXXID = ParamUtil.get(request, "RYXXID");
        Map<String,String> entry = ryxxService.loadById(RYXXID);
        request.setAttribute("rst", entry);

        return view(webPath, "ryxxwh_Detail");
    }
	
	
	@GetMapping("/toEdit")
	public String toEdit(HttpServletRequest request, HttpServletResponse response) {
        String RYXXID = ParamUtil.get(request, "RYXXID");
        if (StringUtils.isNotEmpty(RYXXID)) {
        	Map<String,String> entry = ryxxService.loadById(RYXXID);
            request.setAttribute("rst", entry);
        }
        return view(webPath, "ryxxwh_Edit");
    }



    /**
     * 
     * @Title: edit 
     * @Description: 编辑
     * @author: liu_yg
     * @date: 2019年2月22日 下午3:37:50 
     * @param request
     * @param response
     * @param model
     * @return
     * @return: ResponseBean
     */
	@PostMapping("/edit")
    public void edit(HttpServletRequest request, HttpServletResponse response) {
        UserBean userBean = ParamUtil.getUserBean(request);
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        String jsonData = ParamUtil.get(request, "jsonData");
        String RYXXID = ParamUtil.get(request, "RYXXID");
        RYXXModel model = LogicUtil.StrToObj(jsonData, RYXXModel.class);
        model.setRYXXID(RYXXID);
        if (!StringUtils.isNotEmpty(model.getJGXXID()) && !StringUtils.isNotEmpty(model.getBMXXID())) {
        	jsonResult = jsonResult(false, "请选择上级机构或部门");
        } else {
            if(StringUtils.isEmpty(model.getRYXXID())&&(ryxxService.getCountBH(model.getRYBH())>0)){
            	jsonResult = jsonResult(false, "编号有重复值，请重新输入");
            }else{
            	try {
            		if(ryxxService.loadBMZT(model.getBMXXID()))
                	{
            			if (ryxxService.loadJGZT(model.getJGXXID())) {
                    		ryxxService.edit(model, userBean); 
            			}else{
            				jsonResult = jsonResult(false, "不可选择停用的机构作为所属机构");
            			}
                	}else{
                		jsonResult = jsonResult(false, "不可选择停用的部门作为所属部门");
                	}                   
                } catch (RuntimeException e) {
                	e.printStackTrace();
                	jsonResult = jsonResult(false, "保存失败");
                }
            }           
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 
     * @Title: delete 
     * @Description: 删除
     * @author: liu_yg
     * @date: 2019年2月22日 下午3:38:40 
     * @param request
     * @param response
     * @param RYXXID
     * @return
     * @return: ResponseBean
     */
   	@PostMapping("/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        UserBean userBean = ParamUtil.getUserBean(request);
        String ryxxId = ParamUtil.get(request, "RYXXID");

        if (StringUtils.isNotEmpty(ryxxId)) {
            try {
                ryxxService.delete(ryxxId, userBean);
            } catch (RuntimeException e) {
            	e.printStackTrace();
                jsonResult = jsonResult(false, "删除失败");
            }
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }


    /**
     * 
     * @Title: showTree 
     * @Description: 部门树
     * @author: liu_yg
     * @date: 2019年2月22日 下午3:47:16 
     * @param request
     * @param response
     * @param RYXXID
     * @return
     * @return: ResponseBean
     */
   	@GetMapping("/showTree")
   	public String showTree( HttpServletRequest request, HttpServletResponse response) {

        List <RYXXTree> trees = null;
        try {
        	UserBean userBean = ParamUtil.getUserBean(request);
            String theme = userBean.getUSERSTYLE();
            String ctx = request.getContextPath();
            trees = ryxxService.getTree(ctx, theme);

            String treeData = JSONObject.toJSONString(trees);
            request.setAttribute("tree", treeData);
        } catch (Exception e) {
        	e.printStackTrace();
            request.setAttribute("msg", "获取部门失败！");
            return view(webPath, Consts.FAILURE);
        }
        return view(webPath, "ryxxwh_Tree");
    }


    /**
     * 
     * @Title: changeState 
     * @Description: 启用/停用
     * @author: liu_yg
     * @date: 2019年2月22日 下午3:48:04 
     * @param request
     * @param response
     * @param RYXXID
     * @return
     * @return: ResponseBean
     */
	@PostMapping("/changeState")
    public void changeState(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        boolean isChanged = false;
        UserBean userBean = ParamUtil.getUserBean(request);
        String RYXXID = request.getParameter("RYXXID");

        Map<String,String> entry = ryxxService.loadById(RYXXID);

        String state = entry.get("RYZT");
        Map <String, String> params = new HashMap <String, String>();

        try {
            String changedState = "";
            params.put("RYXXID", RYXXID);
            params.put("UPDATER", userBean.getXTYHID());
            if (BusinessConsts.JC_STATE_ENABLE.equals(state)) {
                changedState = BusinessConsts.JC_STATE_DISENABLE;
                params.put("RYZT", BusinessConsts.JC_STATE_DISENABLE);
                ryxxService.changeState(params);
                isChanged = true;
            }
            else {
                changedState = BusinessConsts.JC_STATE_ENABLE;
                params.put("RYZT", BusinessConsts.JC_STATE_ENABLE);
                ryxxService.changeState(params);
                isChanged = true;
            }
            if (isChanged) {
                jsonResult = jsonResult(true, changedState);
            } else {
                jsonResult = jsonResult(false, "状态不用修改");
            }
        } catch (RuntimeException e) {
        	e.printStackTrace();
            jsonResult = jsonResult(false, "状态修改失败");
        }

        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
}
