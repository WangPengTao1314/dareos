/**
 * prjName:喜临门营销平台
 * ucName:推广促销方案维护
 * fileName:PrmtplanAction
*/
package com.centit.erp.sale.prmtplan.controller;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.common.controller.BaseController;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.erp.sale.prmtplan.model.PrmtplaAreaModel;
import com.centit.erp.sale.prmtplan.model.PrmtplanModel;
import com.centit.erp.sale.prmtplan.model.PrmtplanModelChld;
import com.centit.erp.sale.prmtplan.service.PrmtplanService;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * *@serviceImpl
 * *@func  推广促销
 * *@version 1.1
 * *@author zzb
 * *@createdate 2013-08-23 16:04:28
 */
@Controller
@RequestMapping("/erp/prmtplan")
public class PrmtplanController extends BaseController {
	/**日志**/
	private Logger logger = Logger.getLogger(PrmtplanController.class);
	/** 权限对象**/
	/** 维护*/
    //增删改查
    private static String PVG_BWS="BS0011801";
    private static String PVG_EDIT="BS0011802";
    private static String PVG_DELETE="BS0011803";
    //启用,停用
    private static String PVG_START_STOP="BS0011804";

    /**审批 end**/
    /** 业务service*/
	@Autowired
	private PrmtplanService prmtplanService;
	
	private static final String webPath = "erp/sale/prmtplan";
	
	/**
	 * * to 框架页面
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	@GetMapping("/toFrame")
	public String toFrame(HttpServletRequest request, HttpServletResponse response) {
		logger.info("");
		UserBean userBean =  ParamUtil.getUserBean(request);
        if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		ParamUtil.setCommAttributeEx(request);
		String FLAG = ParamUtil.get(request, "FLAG");
		request.setAttribute("FLAG",FLAG);
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
		return view(webPath,"Prmtplan_Frame");
	}
	/**
	 * * query List data
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	@RequestMapping(value = { "/toList"}, method = { RequestMethod.GET, RequestMethod.POST })
	public String toList(	HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {
		UserBean userBean =  ParamUtil.getUserBean(request);
        if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS))){
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        
       
        
		Map<String,Object> params = new HashMap<String,Object>();
		ParamUtil.putStr2Map(request, "PRMT_PLAN_NO", params);
		ParamUtil.putStr2Map(request, "PRMT_PLAN_NAME", params);
		ParamUtil.putStr2Map(request, "PRMT_TYPE", params);
		ParamUtil.putStr2Map(request, "EFFCT_DATE_BEG", params);
		ParamUtil.putStr2Map(request, "EFFCT_DATE_END", params);
		ParamUtil.putStr2Map(request, "CRE_TIME_BEGIN", params);
		ParamUtil.putStr2Map(request, "CRE_TIME_END", params);
	    ParamUtil.putStr2Map(request, "STATE", params);
       //只查询0的记录。1为删除。0为正常
        params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);
        String search = ParamUtil.get(request,"search");
        if (StringUtil.isEmpty(search)) {
			// 状态为未提交
			params.put("searchSTATE", BusinessConsts.JC_STATE_DEFAULT);
		} 
		String module = ParamUtil.get(request,"module");
	    
	    //字段排序
		ParamUtil.setOrderField(request, params, "u.CRE_TIME", "DESC");
		ParamUtil.putStr2Map(request, "pageSize", params);
		prmtplanService.pageQuery(params, pageDesc);
		request.setAttribute("module", module);
        request.setAttribute("params", params);
		request.setAttribute("pvg",setPvg(userBean));
		request.setAttribute("page", pageDesc);
		return view(webPath,"Prmtplan_List");
	}
	
	 /**
     * * 明细列表
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@RequestMapping(value = { "/childList"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String childList(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
        if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        String PRMT_PLAN_ID =ParamUtil.get(request, "PRMT_PLAN_ID");
        if(!StringUtil.isEmpty(PRMT_PLAN_ID))
        {
        	 List <PrmtplanModelChld> result = prmtplanService.childQuery(PRMT_PLAN_ID);
             request.setAttribute("rst", result);
        }
        request.setAttribute("pvg",setPvg(userBean));
        return view(webPath,"Prmtplan_List_PrdGroup");
    }
	
	 /**
     * *
     * * to 编辑框架页面
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@GetMapping("/toEditFrame")
    public String toEditFrame(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        //设置跳转时需要的一些公用属性
        ParamUtil.setCommAttributeEx(request);
        request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
        return view(webPath,"Prmtplan_Edit_Frame");
    }
	
	/**
	 * * to 主表编辑页面初始化
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	@GetMapping("/toEdit")
	public String toEdit(HttpServletRequest request, HttpServletResponse response){
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}	
		String PRMT_PLAN_ID = ParamUtil.get(request, "selRowId");
		if(!StringUtil.isEmpty(PRMT_PLAN_ID)){
			Map<String,String> entry = prmtplanService.load(PRMT_PLAN_ID);
			request.setAttribute("rst", entry);
			request.setAttribute("isNew", false);
		}else{
			request.setAttribute("isNew", true);
		}
		
		return view(webPath,"Prmtplan_Edit");
	}
	
	/**
     * * 编辑框架页面加载子页面
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@GetMapping("/modifyToChildEdit")
    public String modifyToChildEdit(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        String PRMT_PLAN_ID = ParamUtil.get(request, "PRMT_PLAN_ID");
        if(!StringUtil.isEmpty(PRMT_PLAN_ID))
        {
        	 List <PrmtplanModelChld> result = prmtplanService.childQuery(PRMT_PLAN_ID);
             request.setAttribute("rst", result);
        }
        return view(webPath,"Prmtplan_Edit_PrdGroup");
    }
	
	 /**
     * * to 直接编辑明细页面
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@GetMapping("/toChildEdit")
    public String toChildEdit(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
    	//多个Id批量查询，用逗号隔开
        String PRMT_PRD_GROUP_IDs = request.getParameter("PRMT_PRD_GROUP_IDS");
        //没有零星领料Id可以直接跳转。
        if (!StringUtil.isEmpty(PRMT_PRD_GROUP_IDs)) {
		  Map<String,String> params = new HashMap<String,String>();
		  params.put("PRMT_PRD_GROUP_IDS",PRMT_PRD_GROUP_IDs);
          List <PrmtplanModelChld> list = prmtplanService.loadChilds(params);
          request.setAttribute("rst", list);
        }
        return view(webPath,"Prmtplan_Edit_PrdGroup");
    }
	
	/**
	 * * 主表 新增/修改数据
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@PostMapping("/edit")
	public void edit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean,PVG_EDIT))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}	
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
            String PRMT_PLAN_ID = ParamUtil.get(request, "PRMT_PLAN_ID");
            String parentJsonData = ParamUtil.get(request, "parentJsonData");
            PrmtplanModel model = new Gson().fromJson(parentJsonData, new TypeToken <PrmtplanModel>() {}.getType());
            String jsonDate = ParamUtil.get(request, "childJsonData");
            List <PrmtplanModelChld> chldModelList = null;
            if (!StringUtil.isEmpty(jsonDate)) {
                chldModelList = new Gson().fromJson(jsonDate, new TypeToken <List <PrmtplanModelChld>>() {
                }.getType());
            }
            prmtplanService.txEdit(PRMT_PLAN_ID, model, chldModelList, userBean);
        } catch (Exception e) {
            jsonResult = jsonResult(false, "保存失败");
            e.printStackTrace();
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
	}
	
	/**
     * * 子表 新增/修改数据
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
	@PostMapping("/childEdit")
    public void childEdit(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
            String PRMT_PLAN_ID = request.getParameter("PRMT_PLAN_ID");
            String jsonDate = request.getParameter("childJsonData");
            if (!StringUtil.isEmpty(jsonDate)) {
                List <PrmtplanModelChld> modelList = new Gson().fromJson(jsonDate, new TypeToken <List <PrmtplanModelChld>>() {
                }.getType());
                prmtplanService.txChildEdit(PRMT_PLAN_ID, modelList);
            }
        } catch (Exception e) {
            jsonResult = jsonResult(false, "保存失败");
            e.printStackTrace();
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
	
    /**
	 * * 主表 删除
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@PostMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_DELETE))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}	
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String PRMT_PLAN_ID = ParamUtil.get(request, "PRMT_PLAN_ID");
			Map <String, String> params = new HashMap <String, String>();
            params.put("PRMT_PLAN_ID", PRMT_PLAN_ID);
            params.put("UPD_NAME", userBean.getXM());
            params.put("UPDATOR", userBean.getXTYHID());
            params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_DROP);
			prmtplanService.txDelete(params);
		} catch (Exception e) {
		    e.printStackTrace();
			jsonResult = jsonResult(false, "删除失败!");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}
	
	 /**
     * * 明细批量删除
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
	@PostMapping("/childDelete")
    public void childDelete(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_DELETE))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
            String PRMT_PRD_GROUP_IDs = request.getParameter("PRMT_PRD_GROUP_IDS");
            //批量删除，多个Id之间用逗号隔开
            prmtplanService.txBatch4DeleteChild(PRMT_PRD_GROUP_IDs);
		} catch (Exception e) {
            jsonResult = jsonResult(false, "删除失败");
            e.printStackTrace();
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
	
	/**
	 * * to 详细信息
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	@GetMapping("/toDetail")
	public String toDetail(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
        if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}	
		String PRMT_PLAN_ID = ParamUtil.get(request, "PRMT_PLAN_ID");
		if(!StringUtil.isEmpty(PRMT_PLAN_ID)){
			Map<String,String> entry = prmtplanService.load(PRMT_PLAN_ID);
			request.setAttribute("rst", entry);
		}
		return view(webPath,"Prmtplan_Detail");
	}
	
	 /**
     * * 生效区域 明细列表
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@PostMapping("/areaList")
    public String areaList(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
        if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        String PRMT_PLAN_ID =ParamUtil.get(request, "PRMT_PLAN_ID");
        if(!StringUtil.isEmpty(PRMT_PLAN_ID))
        {
        	 List <PrmtplaAreaModel> result = prmtplanService.areaQuery(PRMT_PLAN_ID);
             request.setAttribute("rst", result);
        }
        request.setAttribute("pvg",setPvg(userBean));
        return view(webPath,"Prmtplan_List_Area");
    }
    
    
    
    /**
     * * 编辑框架页面加载 生效区域子页面
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@PostMapping("/modifyToAreaEdit")
    public String modifyToAreaEdit(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        String PRMT_PLAN_ID = ParamUtil.get(request, "PRMT_PLAN_ID");
        if(!StringUtil.isEmpty(PRMT_PLAN_ID))
        {
        	 List <PrmtplaAreaModel> result = prmtplanService.areaQuery(PRMT_PLAN_ID);
             request.setAttribute("rst", result);
        }
        return view(webPath,"Prmtplan_Edit_Area");
    }
	
	 /**
     * * to 直接编辑 生效区域明细页面
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@GetMapping("/toAreaEdit")
    public String toAreaEdit(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
    	//多个Id批量查询，用逗号隔开
        String PRMT_EFFCT_AREA_IDS = request.getParameter("PRMT_EFFCT_AREA_IDS");
        //没有Id可以直接跳转。
        if (!StringUtil.isEmpty(PRMT_EFFCT_AREA_IDS)) {
		  Map<String,String> params = new HashMap<String,String>();
		  params.put("PRMT_EFFCT_AREA_IDS",PRMT_EFFCT_AREA_IDS);
          List <PrmtplaAreaModel> list = prmtplanService.loadAreas(params);
          request.setAttribute("rst", list);
        }
        
        request.setAttribute("ZTXXID", userBean.getLoginZTXXID());
        return view(webPath,"Prmtplan_Edit_Area");
    }
    
	/**
     * * 生效区域  新增/修改数据
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
	@PostMapping("/areaEdit")
    public void areaEdit(HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        String errorMsg = null;
        try {
            String PRMT_PLAN_ID = request.getParameter("PRMT_PLAN_ID");
            String jsonDate = request.getParameter("childJsonData");
            if (!StringUtil.isEmpty(jsonDate)) {
                List <PrmtplaAreaModel> modelList = new Gson().fromJson(jsonDate, new TypeToken <List <PrmtplaAreaModel>>() {
                }.getType());
                
                errorMsg =  vailChild(PRMT_PLAN_ID,modelList);
                if(null == errorMsg ){
                	 prmtplanService.txAreaEdit(PRMT_PLAN_ID, modelList);
                }else{
                	 jsonResult = jsonResult(false, errorMsg);
                }
               
            }
        } catch (Exception e) {
            jsonResult = jsonResult(false, "保存失败");
            e.printStackTrace();
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
    
    /**
	 * 明细重复判断
	 * 
	 * @return
	 */
	private String vailChild(String pId, List<PrmtplaAreaModel> modelList) {
		String returnMessage = null;
		int size = modelList.size();
		StringBuffer CHRGNOS = new StringBuffer();
		String MIXIDS = null;

		// 如果页面的之无重复，在和同一区域下的数据库的值判断
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				PrmtplaAreaModel model = modelList.get(i);
				CHRGNOS.append("'" + model.getAREA_NO() + "'");
				if ((i + 1) != size) {
					CHRGNOS.append(",");
				}
				
				if(!StringUtil.isEmpty(model.getPRMT_EFFCT_AREA_ID())){
					if(null != MIXIDS){
						MIXIDS+=",";
						MIXIDS += "'" + model.getPRMT_EFFCT_AREA_ID() + "'";
					}else{
						MIXIDS = "'" + model.getPRMT_EFFCT_AREA_ID() + "'";
					}
					
					
				}
			}
			Map<String, String> paramsMx = new HashMap<String, String>();
			paramsMx.put("PRMT_PLAN_ID", pId);
			paramsMx.put("CHRGNOS", CHRGNOS.toString());
			paramsMx.put("MIXIDS", MIXIDS);
			paramsMx.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
			
			List<Map<String,String>> list = prmtplanService.findAreaList(paramsMx);
			if (null != list && list.size() > 0) {
				returnMessage = "生效区域编号与已有的记录重复";
			}
		}

		return returnMessage;
	}
	 /**
     * * 生效区域  批量删除
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
	@PostMapping("/areaDelete")
    public void areaDelete(
    		HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_DELETE))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
            String PRMT_EFFCT_AREA_IDS = request.getParameter("PRMT_EFFCT_AREA_IDS");
            //批量删除，多个Id之间用逗号隔开
            prmtplanService.txBatch4DeleteArea(PRMT_EFFCT_AREA_IDS);
		} catch (Exception e) {
            jsonResult = jsonResult(false, "删除失败");
            e.printStackTrace();
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
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
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_START_STOP))){
			throw new ServiceException("对不起，您没有权限 !");
		}
		PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        boolean isChanged = false;
        
        String PRMT_PLAN_ID = request.getParameter("PRMT_PLAN_ID");

        Map<String,String> entry = prmtplanService.load(PRMT_PLAN_ID);

        String state = entry.get("STATE");
        Map <String, String> params = new HashMap <String, String>();

        try {
            String changedState = "";
            params.put("PRMT_PLAN_ID", PRMT_PLAN_ID);
            params.put("UPDATER", userBean.getXTYHID());
            if (BusinessConsts.JC_STATE_ENABLE.equals(state)) {
                changedState = BusinessConsts.JC_STATE_DISENABLE;
                params.put("STATE", BusinessConsts.JC_STATE_DISENABLE);
                prmtplanService.txUpdateById(params);
                isChanged = true;
            }
            else {
                changedState = BusinessConsts.JC_STATE_ENABLE;
                params.put("STATE", BusinessConsts.JC_STATE_ENABLE);
                prmtplanService.txUpdateById(params);
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
    
		/**
	 * * 设置权限Map
	 * @param UserBean the userBean
	 * @return Map<String,String>
	 */
	 private Map<String,String> setPvg(UserBean userBean)
	    {
	    	Map<String,String>pvgMap=new HashMap<String,String>();
	    	pvgMap.put("PVG_BWS",QXUtil.checkPvg(userBean, PVG_BWS));
	    	pvgMap.put("PVG_EDIT",QXUtil.checkPvg(userBean, PVG_EDIT));
	    	pvgMap.put("PVG_DELETE",QXUtil.checkPvg(userBean, PVG_DELETE)  );
	    	pvgMap.put("PVG_START_STOP",QXUtil.checkPvg(userBean, PVG_START_STOP) );
	    	return  pvgMap;
	   }
}