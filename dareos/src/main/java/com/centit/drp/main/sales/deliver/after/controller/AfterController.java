package com.centit.drp.main.sales.deliver.after.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.common.controller.BaseController;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.sales.deliver.after.model.AfterModel;
import com.centit.drp.main.sales.deliver.after.service.AfterService;
import com.centit.sys.model.UserBean;
/**
 * 售后管理
 * 售后问题反馈单
 * @author wang_pt
 *
 */
@Controller
@RequestMapping("/deliver/after")
public class AfterController extends BaseController {

	private static final String webPath = "drp/main/deliver/after";

	@Autowired
	private AfterService afterService;
	 
	/**
	 * 经销商
	 */
	//查询
    private static String PVG_BWS_FX="FX0020701";
    //新增  修改
    private static String PVG_EDIT_FX="FX0020702";
   
    private static String PVG_DELETE_FX="FX0020703";
   
	
	/**
	 * 总部
	 */
	//查询
    private static String PVG_BWS="BS0011001";
    //新增
    private static String PVG_EDIT="BS0011002";
    //修改
    private static String PVG_DELETE="BS0011003";
    //问题处理
    private static String PVG_BWS_AUDIT="BS0011004";
  
    
	/**
	 * 跳转页面 销售订单选取
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/toFrames" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toFrames(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)&&!QXUtil.checkMKQX(userBean, PVG_BWS_FX)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
		request.setAttribute("module", ParamUtil.utf8Decoder(request, "module"));
		return view(webPath, "After_Frame");
	}

	/**
	 * To list.
	 * 
	 * @param request  the request
	 * @param response the response
	 * @return the action forward
	 */
	@RequestMapping(value = { "/toList" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toList(HttpServletRequest request, HttpServletResponse response, PageDesc pageDesc) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)&&!QXUtil.checkMKQX(userBean, PVG_BWS_FX))){
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		//
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "PROBLEM_FEEDBACK_NO", params);//问题单号
		ParamUtil.putStr2Map(request, "SALE_ORDER_NO", params);//订单编号
		ParamUtil.putStr2Map(request, "PROBLEM_TYPE", params);//问题类型
		ParamUtil.putStr2Map(request, "CUST_NAME", params);//顾客名称
		ParamUtil.putStr2Map(request, "CHANN_NAME", params);//经销商
		ParamUtil.putStr2Map(request, "STATE", params);//状态
		//
		ParamUtil.putStr2Map(request, "pagesize", params);
		String search = ParamUtil.get(request, "search");
		String module = ParamUtil.get(request, "module");
		//params.put("QXJBCON", ParamUtil.getPvgCon(search,module,PVG_BWS,PVG_EDIT,PVG_DELETE,PVG_BWS_AUDIT, PVG_BWS_FX, PVG_EDIT_FX, userBean));
		if("1".equals(userBean.getIS_DRP_LEDGER())) {
			params.put("QXJBCON", QXUtil.getQXTJ(userBean, PVG_BWS));
			params.put("XTYHID", "STATE in ('草稿','处理中','已处理','已退回') and (creator='"+userBean.getXTYHID()+"')");
		}else {
			params.put("XTYHID", "(STATE in ('已处理','处理中','已退回') or creator='"+userBean.getXTYHID()+"')");
		}
		//if ("wh".equals(module)) {
			//params.put("STATES", "STATE in ('草稿','处理中','已处理','已退回')");
		//}else if("sh".equals(module)){
			//params.put("STATES", "STATE in ('已处理','处理中','已退回')");
		//}
		
		afterService.pageQuery(params, pageDesc);
		request.setAttribute("params", params);
		request.setAttribute("pvg",setPvg(userBean));
		request.setAttribute("IS_DRP_LEDGER", userBean.getIS_DRP_LEDGER());
		request.setAttribute("CHANN_ID", userBean.getCHANN_ID());
		request.setAttribute("page", pageDesc);
		return view(webPath, "After_List");
	}

	/**
	 * to modify page.
	 * 
	 * @param request  the request
	 * @param response the response
	 * @return the action forward
	 */
	@RequestMapping(value = { "/toEdit" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toEdit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT)&&!QXUtil.checkMKQX(userBean, PVG_EDIT_FX))) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "PROBLEM_FEEDBACK_ID", params);// 发货单id
		String problem_feedback_id = ParamUtil.get(request, "PROBLEM_FEEDBACK_ID");
		Map<String, Object> entry = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(problem_feedback_id)) {
			entry=afterService.query(params);
		}else {
			if("1".equals(userBean.getIS_DRP_LEDGER())) {
				entry.put("CHANN_ID", userBean.getCHANN_ID());
				entry.put("CHANN_NO", userBean.getCHANN_NO());
				entry.put("CHANN_NAME", userBean.getCHANN_NAME());
			}
		}
		request.setAttribute("IS_DRP_LEDGER",userBean.getIS_DRP_LEDGER());
		request.setAttribute("XTYHID",userBean.getXTYHID());
		request.setAttribute("ADMIN", userBean.getXTYHID());
		request.setAttribute("entry", entry);
		return view(webPath, "After_Edit");
	}

	/**
	 * 编辑
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/edit" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void edit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG &&(!QXUtil.checkMKQX(userBean,PVG_EDIT)&&!QXUtil.checkMKQX(userBean,PVG_EDIT_FX))) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String jsonData = ParamUtil.get(request, "jsonData");
			if(StringUtils.isBlank(jsonData)) {
				jsonResult = jsonResult(false, "保存失败");
			}else {
				AfterModel model = LogicUtil.StrToObj(jsonData, AfterModel.class);
				afterService.edit(model, request);
				
			}
		}catch(ServiceException e) {
			jsonResult=jsonResult(false,e.getMessage());
		}catch (RuntimeException e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "保存失败");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}

	/**
	 * 删除.
	 * 
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG &&(!QXUtil.checkMKQX(userBean, PVG_DELETE)&&!QXUtil.checkMKQX(userBean, PVG_DELETE_FX))) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String problem_feedback_id = ParamUtil.get(request, "PROBLEM_FEEDBACK_ID");
			try {
				if (StringUtils.isEmpty(problem_feedback_id)) {
					jsonResult = jsonResult(false, "删除失败,反馈单据不存在！！！");
				}
				afterService.delete(problem_feedback_id, userBean);
			}catch(ServiceException e) {
				jsonResult=jsonResult(false,e.getMessage());
			}  catch (Exception e) {
				jsonResult = jsonResult(false, "删除失败");
			}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}
	
	/**
	 * 删除.
	 * 
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/submit")
	public void submit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG &&(!QXUtil.checkMKQX(userBean, PVG_EDIT)&&!QXUtil.checkMKQX(userBean, PVG_EDIT_FX))) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		AfterModel model=new AfterModel();//
		String problem_feedback_id = ParamUtil.get(request, "PROBLEM_FEEDBACK_ID");
		String problem_feedback_no = ParamUtil.get(request, "PROBLEM_FEEDBACK_NO");
		String order_org = ParamUtil.get(request, "ORDER_ORG");
			try {
				if (StringUtils.isEmpty(problem_feedback_id)) {
					jsonResult = jsonResult(false, "提交失败,反馈单据不存在！！！");
				}else {
					model.setProblem_feedback_id(problem_feedback_id);
					model.setState("处理中");
					model.setProblem_feedback_no(problem_feedback_no);
					model.setOrder_org(order_org);
					afterService.edit(model, request);
				}
			}catch(ServiceException e) {
				jsonResult=jsonResult(false,e.getMessage());
			}catch (Exception e) {
				jsonResult = jsonResult(false, "提交失败");
			}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}
	/**
	 * 发货单明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/toDetail" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String showDetail(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean =  ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)&&!QXUtil.checkMKQX(userBean, PVG_BWS_FX))){
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "PROBLEM_FEEDBACK_ID", params);
		String PROBLEM_FEEDBACK_ID=request.getParameter("PROBLEM_FEEDBACK_ID");
		if(!StringUtils.isBlank(PROBLEM_FEEDBACK_ID)) {
			Map<String, Object> entry = afterService.query(params);
		   request.setAttribute("entry", entry);
		}
		request.setAttribute("XTYHID",userBean.getXTYHID());
		request.setAttribute("IS_DRP_LEDGER",userBean.getIS_DRP_LEDGER());
		return view(webPath, "After_Detail");
	}

	/**
	 * 跳转处理界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/toHandle" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toCheck(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, PVG_BWS)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "PROBLEM_FEEDBACK_ID", params);
		// 添加自定义校验该订单是否存在？
		Map<String, Object> entry = afterService.query(params);
		request.setAttribute("entry", entry);
		request.setAttribute("XTYHID", userBean.getXTYHID());
		request.setAttribute("IS_DRP_LEDGER",userBean.getIS_DRP_LEDGER());
		return view(webPath, "After_Handle");
	}

	/**
	 * 处理
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/handle" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void check(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String jsonData = ParamUtil.get(request, "jsonData");
		AfterModel model = LogicUtil.StrToObj(jsonData, AfterModel.class);
		try {
			afterService.editHandle(model, request);
			jsonResult = jsonResult(true, "保存成功");
		} catch(ServiceException e) {
			jsonResult=jsonResult(false,e.getMessage());
		} catch (RuntimeException e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "保存失败");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}
	/**
	 * deleteFile
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/deleteFile" }, method = {RequestMethod.POST })
	public void deleteFile(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String att_id = ParamUtil.get(request, "att_id");
			afterService.deleteFile(att_id, userBean);
		} catch (Exception e) {
			jsonResult = jsonResult(false, "删除失败");
		}

		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}
	
	/**
	 * 设置权限Map
	 * @param UserBean the userBean
	 * @return Map<String,String>
	 */
	private Map<String, String> setPvg(UserBean userBean) {
		Map<String, String> pvgMap = new HashMap<String, String>();
		//总部
		pvgMap.put("PVG_BWS", QXUtil.checkPvg(userBean, PVG_BWS));
		pvgMap.put("PVG_BWS_AUDIT", QXUtil.checkPvg(userBean, PVG_BWS_AUDIT));
		pvgMap.put("PVG_EDIT", QXUtil.checkPvg(userBean, PVG_EDIT));
		pvgMap.put("PVG_DELETE", QXUtil.checkPvg(userBean, PVG_DELETE));
		//经销商
		pvgMap.put("PVG_BWS_FX", QXUtil.checkPvg(userBean, PVG_BWS_FX));
		pvgMap.put("PVG_EDIT_FX", QXUtil.checkPvg(userBean, PVG_EDIT_FX));
		pvgMap.put("PVG_DELETE_FX", QXUtil.checkPvg(userBean, PVG_DELETE_FX));
		return pvgMap;
	}
	

}
