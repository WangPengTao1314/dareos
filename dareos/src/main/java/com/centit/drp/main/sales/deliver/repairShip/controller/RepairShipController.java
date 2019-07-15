package com.centit.drp.main.sales.deliver.repairShip.controller;

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
import com.centit.common.service.BookkeepingService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.QXUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.project.management.service.ProjectConsts;
import com.centit.drp.main.sales.deliver.order.model.OrderModel;
import com.centit.drp.main.sales.deliver.order.model.SendDtlModel;
import com.centit.drp.main.sales.deliver.order.service.OrderService;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * 发货管理
 * 返修发货
 * @author wang_pt
 *
 */
@Controller
@RequestMapping("/rework/deliver")
public class RepairShipController extends BaseController {
	
	private static final String webPath = "drp/main/deliver/repairShip";
	
	
	@Autowired
	OrderService orderService;
	@Autowired
	BookkeepingService bookkeepingService;
	/** 权限对象维护**/
    //维护
    private static String PVG_BWS="BS0010801";//查询
    private static String PVG_EDIT="BS0010802";//新增/修改
    private static String PVG_DELETE="BS0010803";//删除
    //审核
    private static String PVG_BWS_QUERY="BS0010901";
    private static String PVG_BWS_AUDIT="BS0010902";
	
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
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)&&!QXUtil.checkMKQX(userBean, PVG_BWS_QUERY)))
    	{
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
		request.setAttribute("module", ParamUtil.utf8Decoder(request, "module"));
		return view(webPath, "RepairShip_Frame");
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
		if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)&&!QXUtil.checkMKQX(userBean, PVG_BWS_QUERY))){
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "SEND_ORDER_NO", params);// 发货编号
		ParamUtil.putStr2Map(request, "SALE_ORDER_NO", params);// 订单编号
		ParamUtil.putStr2Map(request, "CHANN_NAME", params);// 经销商
		ParamUtil.putStr2Map(request, "STATE", params);// 单据状态 
		params.put("BILL_TYPE","返修订单");
		ParamUtil.putStr2Map(request, "pagesize", params);
		String module =ParamUtil.get(request,"module");
		String search = ParamUtil.get(request, "search");
		//params.put("QXJBCON", ParamUtil.getPvgCon(search,module,PVG_BWS,PVG_EDIT,PVG_DELETE,PVG_BWS_QUERY, PVG_BWS_AUDIT,"", userBean));
		params.put("QXJBCON", QXUtil.getQXTJ(userBean, PVG_BWS));
		params.put("XTYHID", userBean.getXTYHID());
		if ("wh".equals(module)) {
			params.put("STATES", "state in ('"+ProjectConsts.SENDORDER_WAITCHECK+"','"+ProjectConsts.SENDORDER_OVWER+"','"+ProjectConsts.SENDORDER_DRAFT+"','"+ProjectConsts.SENDORDER_CHECKPASS+"','"+ProjectConsts.SENDORDER_REJECT+"')");
		}else if("sh".equals(module)){
			params.put("STATES", "state in ('"+ProjectConsts.SENDORDER_CHECKPASS+"','"+ProjectConsts.SENDORDER_WAITCHECK+"','"+ProjectConsts.SENDORDER_OVWER+"')");
		}
		
		//// 字段排序
		ParamUtil.setOrderField(request, params, "cre_time2", "DESC");
		orderService.pageQuery2(params, pageDesc);
		request.setAttribute("params", params);
		request.setAttribute("pvg",setPvg(userBean));
		request.setAttribute("page", pageDesc);
		return view(webPath, "RepairShip_List");
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
    	if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_EDIT))) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		int pageNo = ParamUtil.getInt(request, "pageNo", 1);
		ParamUtil.putStr2Map(request, "SEND_ORDER_ID", params);// 发货单id
		ParamUtil.putStr2Map(request, "SALE_ORDER_ID", params);// 发货单id
		String send_order_id = ParamUtil.get(request, "SEND_ORDER_ID");
		String sale_order_id = ParamUtil.get(request, "SALE_ORDER_ID");
		Map<String, Object> entry = null;
		if (StringUtils.isNotEmpty(send_order_id)) {
			entry = orderService.querySun(params);
		}else {
			if (StringUtils.isNotEmpty(sale_order_id)) {
				entry = orderService.getSaleData(params);
			}
		}
		//ZTMC 账套
		request.setAttribute("ADMIN", userBean.getXTYHID());
		request.setAttribute("entry", entry);
		request.setAttribute("pageNo", pageNo);

		return view(webPath, "RepairShip_Edit");
	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/edit" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void edit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean,PVG_EDIT)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			// 多条记录
			String jsonDataMulti = request.getParameter("jsonDataMulti");
			//String sale_order_id = request.getParameter("sale_order_id");
			String jsonData = ParamUtil.get(request, "parentJsonData");
			OrderModel model = LogicUtil.StrToObj(jsonData, OrderModel.class);
			
			String BILL_TYPE="返修订单";
				// 单挑记录
				List<SendDtlModel> modelList=null;
				if (!StringUtil.isEmpty(jsonDataMulti)) {
					modelList = new Gson().fromJson(jsonDataMulti, new TypeToken<List<SendDtlModel>>() {
					}.getType());
				}
				orderService.modify(model,modelList, request,BILL_TYPE);
		} catch(ServiceException e){
			jsonResult = jsonResult(false, e.getMessage());
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
	 * 删除.
	 * 
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);//
    	if(Consts.FUN_CHEK_PVG &&(!QXUtil.checkMKQX(userBean, PVG_DELETE))) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		String send_order_id = ParamUtil.get(request, "SEND_ORDER_ID");
		if (StringUtils.isNotEmpty(send_order_id)) {
			try {
				orderService.delete(send_order_id, userBean);

			} catch (Exception e) {
				jsonResult = jsonResult(false, "删除失败");
			}
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
    	if(Consts.FUN_CHEK_PVG&&(!QXUtil.checkMKQX(userBean, PVG_BWS)&&!QXUtil.checkMKQX(userBean, PVG_BWS_QUERY))){
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "SEND_ORDER_ID", params);
		Map<String, Object> entry = orderService.querySun(params);
		//ZTMC 账套
		request.setAttribute("ZTMC", userBean.getLoginZTMC());
		request.setAttribute("entry", entry);
		return view(webPath, "RepairShip_Detail");
	}

	/**
	 * 跳转审核界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/toCheck" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String toCheck(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		ParamUtil.putStr2Map(request, "SEND_ORDER_ID", params);
		// 添加自定义校验该订单是否存在？
		Map<String, Object> entry = orderService.querySun(params);
		
		//ZTMC 账套
		request.setAttribute("ZTMC", userBean.getLoginZTMC());
		request.setAttribute("entry", entry);
		return view(webPath, "RepairShip_Check");
	}

	/**
	 * 审核
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/check" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void check(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(!QXUtil.checkMKQX(userBean, PVG_BWS_AUDIT)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {
			String status = request.getParameter("STATUS");
			String send_order_id = request.getParameter("SEND_ORDER_ID");
			//
			String jsonData = ParamUtil.get(request, "jsonData");
			OrderModel model = LogicUtil.StrToObj(jsonData, OrderModel.class);
			model.setState(status);
			model.setSend_order_id(send_order_id);
			model.setUpdator(userBean.getXTYHID());
			model.setUpd_name(userBean.getXM());
			//bookkeepingService.reworkAmount(String saleOrderId);
			orderService.sendAudit(model, send_order_id,userBean);
		}catch(ServiceException e){
			jsonResult = jsonResult(false, e.getMessage());
		} catch (RuntimeException e) {
			e.printStackTrace();
			jsonResult = jsonResult(false, "审核失败");
		}
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}

	}
	
	/**
	 * 提交
	 * 
	 * @param request  the request
	 * @param response the response
	 */
	@RequestMapping("/submit")
	public void submit(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = ParamUtil.getUserBean(request);
    	if(Consts.FUN_CHEK_PVG && !QXUtil.checkMKQX(userBean, PVG_EDIT)) {
    		throw new ServiceException("对不起，您没有权限 !");
    	}
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		OrderModel model=new OrderModel();
		Map <String,Object>map=new HashMap<String, Object>();
		String send_order_id = ParamUtil.get(request, "SEND_ORDER_ID");
		//校验	
		map.put("SEND_ORDER_ID", send_order_id);
		map=orderService.checkSendNum(map);
		if(Double.valueOf(map.get("ALLSEND_NUM").toString())<=0) {
			jsonResult = jsonResult(false, "提交失败,请至少填写一条单据的明细数量或货物已发完！！！");
		 }else {
				try {
					if (StringUtils.isEmpty(send_order_id)) {
						jsonResult = jsonResult(false, "操作失败，未找到发货的信息，请联系管理员！");
					}
					model.setSend_order_id(send_order_id);
					model.setState("待审核");
					orderService.submit(model,userBean);
				} catch(ServiceException e){
					jsonResult = jsonResult(false, e.getMessage());
				} catch (Exception e) {
					jsonResult = jsonResult(false, "提交失败");
				}
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
		//维护
		pvgMap.put("PVG_BWS", QXUtil.checkPvg(userBean, PVG_BWS));
		pvgMap.put("PVG_EDIT", QXUtil.checkPvg(userBean, PVG_EDIT));
		pvgMap.put("PVG_DELETE", QXUtil.checkPvg(userBean, PVG_DELETE));
		//审核
		pvgMap.put("PVG_BWS_AUDIT", QXUtil.checkPvg(userBean, PVG_BWS_AUDIT));
		pvgMap.put("PVG_BWS_QUERY", QXUtil.checkPvg(userBean, PVG_BWS_QUERY));
		return pvgMap;
	}
	 

}
