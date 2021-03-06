/**

 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ChannAction.java
 */
package com.centit.erp.sale.creditreq.controller;

import java.io.PrintWriter;
import java.util.HashMap;
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

import com.centit.common.controller.BaseController;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.erp.sale.creditreq.model.CreditReqModel;
import com.centit.erp.sale.creditreq.service.CreditReqService;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



/**
 * The Class ChannAction.
 * 
 * @module 系统管理
 * @func 品牌信息
 * @version 1.0
 */
@Controller
@RequestMapping("/erp/creditreq")
public class CreditReqController extends BaseController {
	@Autowired
	private CreditReqService creditReqService;
	
//	private Logger logger = Logger.getLogger(DepositInfoController.class);
	/** 权限对象维护**/
    //增删改查
    private static String PVG_BWS="BS0011601";
    private static String PVG_BWS_AUDIT="BS0011701";
    private static String PVG_EDIT="BS0011602";
    private static String PVG_DELETE="BS0011603";
    /**权限 end*/
    
    /**审批维护必须维护字段**/
    //提交
    private static String PVG_COMMIT="BS0011604";
    //审核
    private static String PVG_AUDIT="BS0011702";
    
    private static final String webPath = "erp/sale/creditreq";
	/**
	 * 品牌信息框架页面
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
    @GetMapping("/toFrame")
	public String toFrame(HttpServletRequest request, HttpServletResponse response){
		UserBean userBean =  ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)&&!QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		//设置跳转时需要的一些公用属性
    	ParamUtil.setCommAttributeEx(request);
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
		request.setAttribute("HEAD", ParamUtil.utf8Decoder(request, "HEAD"));
		return view(webPath,"CreditReq_Frame");
	}
	
	/**
     * 查询结果列表.
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@RequestMapping(value = { "/toList"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String toList( HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {
		UserBean userBean = ParamUtil.getUserBean(request);
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)&&!QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT))){
    		throw new ServiceException("对不起，您没有权限 !");
    	}
    	Map<String, Object> params = LogicUtil.getParameterMap(request);
    			
        //只查询0的记录。1为删除。0为正常
		params.put("delFlag", BusinessConsts.DEL_FLAG_COMMON);
        String search = ParamUtil.get(request,"search");
        String module = ParamUtil.get(request,"module");
        if(StringUtils.isEmpty(search)){
        	if ("wh".equals(module)) {
        		params.put("queryState", "'未提交','退回'");
    		}else if("sh".equals(module)){
    			params.put("queryState", "'提交'");
    		}
        }
        // 查询经销商分管和帐套分管
        params.put("chrgSql", " u.CHANN_ID in ("+userBean.getCHANNS_CHARG()+") and u.ledger_id in ("+userBean.getFGZTXXIDS()+")");
		if ("wh".equals(module)) {
//			params.put("queryMenuState", " u.STATE in ('未提交','提交','退回')");
			//维护时，如果没有选择状态，默认查询未提交和退回
//			params.put("queryState", "'未提交','退回'");
		}else if("sh".equals(module)){
			params.put("queryMenuState", " u.STATE in ('审核通过','提交','退回')");
		}
		// 字段排序
		ParamUtil.setOrderField(request, params, "u.CRE_TIME", "DESC");
		//权限级别和审批流的封装以及状态的封装
		//params.put("QXJBCON", ParamUtil.getPvgCon(search,module,PVG_BWS,PVG_BWS_AUDIT,AUD_TAB_KEY, AUD_TAB_NAME,AUD_BUSS_TYPE,AUD_BUSS_STATE,userBean));
		ParamUtil.putStr2Map(request, "STATE2", params);
		creditReqService.pageQuery(params, pageDesc);
    	request.setAttribute("params", params);
    	request.setAttribute("pvg",setPvg(userBean));
        request.setAttribute("page", pageDesc);
        request.setAttribute("module", module);
        return view(webPath,"CreditReq_List");
    }
    
    /**
     * 查看人员详细信息.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     */
	@GetMapping("/toDetail")
    public String toDetail( HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean =  ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)&&!QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT))){
    		throw new ServiceException("对不起，您没有权限 !");
    	}
    	String creditReqId = ParamUtil.get(request, "creditReqId");
    	CreditReqModel entry = creditReqService.loadById(creditReqId);
        request.setAttribute("rst", entry);
        return view(webPath,"CreditReq_Detail");
    }
    /**
     * 点击新增或修改按钮跳转到编辑页面.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     * 
     * @return the action forward
     */
	@GetMapping("/toEdit")
    public String toEdit( HttpServletRequest request, HttpServletResponse response) {
    	UserBean userBean =  ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG&&!QXUtil.checkMKQX(userBean, PVG_EDIT)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        String creditReqId = ParamUtil.get(request, "creditReqId");
        if (StringUtils.isNotEmpty(creditReqId)) {
            CreditReqModel entry = creditReqService.loadById(creditReqId);
            request.setAttribute("rst", entry);
        }
        request.setAttribute("xtyhId", userBean.getXTYHID());
        return view(webPath,"CreditReq_Edit");
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
    public void edit( HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean,PVG_EDIT)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
    	try {
    		String creditReqId = ParamUtil.get(request, "creditReqId");
            String jsonData = ParamUtil.get(request, "jsonData");
            System.err.println(jsonData);
            CreditReqModel model = null;
            if (StringUtils.isNotEmpty(jsonData)) {
                model = new Gson().fromJson(jsonData, new TypeToken <CreditReqModel>() {
                }.getType());
                
                //新增的时候
//                if(StringUtils.isEmpty(creditReqId)){
//                	Map<String,String> Map = creditReqService.loadById(model.getcreditReqId());
//                    if(null != Map && !Map.isEmpty()){
//                    	throw new ServiceException("品牌ID重复，请重新输入");
//                    }
//                }
                
    			creditReqService.txEdit(creditReqId, model, userBean); 
            }
        }catch(ServiceException e){
        	jsonResult = jsonResult(false, e.getMessage());
        }catch (RuntimeException e) {
        	e.printStackTrace();
            jsonResult = jsonResult(false, "保存失败");
        }catch (Exception e) {
			e.printStackTrace();
		}
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
    /**
     * 删除
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
	@PostMapping("/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response){
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_DELETE)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        String creditReqId = ParamUtil.get(request, "creditReqId");

        if (StringUtils.isNotEmpty(creditReqId)) {
            try {
                creditReqService.delete(creditReqId, userBean);
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
     * 修改状态为提交
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
	@PostMapping("/toCommit")
    public void toCommit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, PVG_COMMIT)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
        	String creditReqId = request.getParameter("creditReqId");
        	CreditReqModel model =new CreditReqModel();
        	model.setCreditReqId(creditReqId);
        	model.setUpdator(userBean.getXTYHID());
        	model.setUpdName(userBean.getXM());
        	model.setUpdTime("sysdate");
        	model.setState(BusinessConsts.COMMIT);
            creditReqService.txUpdateById(model);
            jsonResult = jsonResult(true, "状态修改成功!");
        } catch (Exception e) {
            jsonResult = jsonResult(false, "状态修改失败!");
        }
        if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
    }
	
	/**
     * 修改状态为审核.
     * 
     * @param mapping the mapping
     * @param form the form
     * @param request the request
     * @param response the response
     */
	@PostMapping("/toAudit")
    public void toAudit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, PVG_AUDIT)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
        PrintWriter writer = getWriter(response);
        String jsonResult = jsonResult();
        try {
        	String creditReqId = request.getParameter("creditReqId");
        	String remark = request.getParameter("remark");
        	String type = request.getParameter("type");
        	CreditReqModel model = new CreditReqModel();
        	model.setCreditReqId(creditReqId);
        	model.setAuditId(userBean.getXTYHID());
        	model.setAuditName(userBean.getXM());
        	model.setAuditTime("sysdate");
        	model.setAuditOpinion(remark);
        	model.setState(type);
            creditReqService.audit(model);
            jsonResult = jsonResult(true, "状态修改成功!");
        }catch(ServiceException s){
       	 jsonResult = jsonResult(false, s.getMessage());
       } catch (Exception e) {
            jsonResult = jsonResult(false, "状态修改失败!");
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
		pvgMap.put("PVG_BWS", QXUtil.checkPvg(userBean, PVG_BWS));
		pvgMap.put("PVG_BWS_AUDIT", QXUtil.checkPvg(userBean, PVG_BWS_AUDIT));
		pvgMap.put("PVG_EDIT", QXUtil.checkPvg(userBean, PVG_EDIT));
		pvgMap.put("PVG_DELETE", QXUtil.checkPvg(userBean, PVG_DELETE));
		pvgMap.put("PVG_COMMIT", QXUtil.checkPvg(userBean, PVG_COMMIT));
		pvgMap.put("PVG_AUDIT", QXUtil.checkPvg(userBean, PVG_AUDIT));
		return pvgMap;
	}

}
