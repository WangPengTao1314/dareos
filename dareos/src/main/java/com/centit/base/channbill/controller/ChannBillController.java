/**

 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ChannAction.java
 */
package com.centit.base.channbill.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.base.channbill.service.ChannBillService;
import com.centit.common.controller.BaseController;
import com.centit.common.po.BookkeepingModel;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.UserBean;



/**
 * 客户明细账单
 * @author liu_yg
 *
 */
@Controller
@RequestMapping("/base/channbill")
public class ChannBillController extends BaseController {
	@Autowired
	private ChannBillService channBillService;
	
//	private Logger logger = Logger.getLogger(DepositInfoController.class);
	/** 权限对象维护**/
    //增删改查
    private static String PVG_BWS="FX0020901";
    private static String PVG_BWS_AUDIT="BS0011501";
    private static String PVG_EDIT="";
    private static String PVG_DELETE="";
    /**权限 end*/
    
    /**审批维护必须维护字段**/
    //提交
    private static String PVG_COMMIT="";
    //审核
    private static String PVG_AUDIT="";
    
    private static final String webPath = "base/channbill";
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
		return view(webPath,"ChannBill_Frame");
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
    	
    	//判断是否是总部用户
    	if("1".equals(userBean.getIS_DRP_LEDGER())){
    		//如果是经销商用户，查询当前登录经销商的信息
    		params.put("channIds", "'"+userBean.getCHANN_ID()+"'");
    		params.put("channId", userBean.getCHANN_ID());
    		params.put("channName", userBean.getCHANN_NAME());
    	}else{
    		params.put("channIds", userBean.getCHANNS_CHARG());
    	}
    	params.put("ledgerIds", userBean.getFGZTXXIDS());
		
		// 正常查询冻结和释放冻结的单据不查
    	params.put("noTypeSql", "'"+BusinessConsts.AMOUNT_TYPE_FREEZ+"','"+BusinessConsts.AMOUNT_TYPE_RELEASE+"'");
		// 字段排序
		ParamUtil.setOrderField(request, params, "u.OPTION_TIME", "DESC");
		channBillService.pageQuery(params, pageDesc);
    	request.setAttribute("params", params);
    	request.setAttribute("pvg",setPvg(userBean));
        request.setAttribute("page", pageDesc);
        request.setAttribute("IS_DRP_LEDGER", userBean.getIS_DRP_LEDGER());
        request.setAttribute("XTYHID", userBean.getXTYHID());
        request.setAttribute("CHANN_ID", userBean.getCHANN_ID());
        return view(webPath,"ChannBill_List");
    }
    
    /**
     * 查看详细信息.
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
    	String bookkeepingId = ParamUtil.get(request, "bookkeepingId");
    	BookkeepingModel entry = channBillService.loadById(bookkeepingId);
        request.setAttribute("rst", entry);
        return view(webPath,"ChannBill_Detail");
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
