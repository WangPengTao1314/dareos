/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ProductAction.java
 */
package com.centit.base.product.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.centit.base.product.model.ProductTree;
import com.centit.base.product.service.ProductService;
import com.centit.common.controller.BaseController;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.core.po.PageDesc;
import com.centit.sys.model.UserBean;
import com.google.gson.Gson;

/**
 * The Class ProductAction.
 *
 * @module 系统管理s
 * @func 货品信息
 * @version 1.1
 * @author 刘曰刚
 */
@Controller
@RequestMapping("/common/selproduct")
public class SelProductController extends BaseController {

	// 日志记录
	/** The logger. */
	private Logger logger = Logger.getLogger(SelProductController.class);

	@Autowired
	private ProductService productService;

	private static final String webPathSel = "common/selproduct";

	@RequestMapping("/toSelPrd")
	public String toSelPrd(HttpServletRequest request,
			HttpServletResponse response) {

		request.setAttribute("ledger_id", ParamUtil.get(request, "ledger_id"));
		request.setAttribute("paramUrl", ParamUtil.utf8Decoder(request, "paramUrl"));
		return view(webPathSel, "selproductFrame");
	}

	@RequestMapping(value="/toList",method= {RequestMethod.POST,RequestMethod.GET})
	public String toList(
			HttpServletRequest request, HttpServletResponse response,PageDesc pageDesc) {

		return view(webPathSel,"selproductList");
	}

	/**
	 * 显示树.
	 *
	 * @return the action forward
	 */
	@RequestMapping(value = "/showTree")
	public String showTree(HttpServletRequest request,
			HttpServletResponse response) {
		String ledger_id=request.getParameter("ledger_id");
		List<ProductTree> trees;
		try {
			trees = productService.getTree(ledger_id);
			String treeData = new Gson().toJson(trees);
			request.setAttribute("tree", treeData);
			return view(webPathSel, "selproductTree");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取货品信息失败！");
			return view(Consts.WEB_PATH, Consts.FAILURE);
		}
	}

	@RequestMapping(value = "/getPrdList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getPrdList(HttpServletRequest request,
			HttpServletResponse response, PageDesc pageDesc) {
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		UserBean userBean = ParamUtil.getUserBean(request);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("prdInfo", ParamUtil.utf8Decoder(request, "prdInfo"));
		params.put("parPrdId", ParamUtil.utf8Decoder(request, "parPrdId"));
		params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		params.put("FINAL_NODE_FLAG", BusinessConsts.YJLBJ_FLAG_TRUE);
		params.put("STATE", BusinessConsts.JC_STATE_ENABLE);
		// 查询货品时增加渠道货品过滤
		String ledger_id = ParamUtil.get(request, "ledger_id");
		params.put("LEDGERSQL", " exists(select 1 from BASE_PRODUCT_LEDGER a where a.del_flag=0 and a.ledger_id='"+ledger_id+"' and a.PRD_ID = p.PRD_ID)");
		StringBuffer sql = new StringBuffer();
		params.put("sql", sql.toString());
		ParamUtil.setOrderField(request, params, "p.PRD_NO", "ASC");
		List<Map<String,String>> list = productService.query(params);
		jsonResult = jsonResult(true, JSONObject.toJSONString(list));
		if (null != writer) {
			writer.write(jsonResult);
			writer.close();
		}
	}
}
