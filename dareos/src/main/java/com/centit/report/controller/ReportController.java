package com.centit.report.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.centit.common.controller.BaseController;
import com.centit.core.utils.TimeUtil;
import com.centit.erp.sale.depositinfo.service.DepositInfoService;
import com.centit.report.service.ReportService;

@Controller
@RequestMapping("/sys/report")
public class ReportController extends BaseController {
	/**
	 * 日志对象
	 */
	private static final Log LOGGER = LogFactory.getLog(ReportController.class);
	
	private static final String webPath = "report/page";

	@Autowired
	private ReportService reportService;
	@Autowired
	private DepositInfoService depositInfoService;
	/**
	 * 销售订单有价格报表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toSaleOrderReport", method = { RequestMethod.GET })
	public ModelAndView toSafeSectorReport(HttpServletRequest request, HttpServletResponse response) {
		String SALE_ORDER_ID = request.getParameter("selRowId");

		String params = "sql="+getOrderMainSql(SALE_ORDER_ID)+"sql2="+getOrderSubSql(SALE_ORDER_ID);
		request.setAttribute("params", params);
		request.setAttribute("reportFileName", "detailed.raq");
		request.setAttribute("RptModel", "SALEREP");
		request.setAttribute("reportFlag", "false");
		return new ModelAndView("report/page/saleOrderReport");
	}
	
	/**
	 * 销售订单无价格报表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toSaleOrderReportNoPrice", method = { RequestMethod.GET })
	public ModelAndView toSaleOrderReportNoPrice(HttpServletRequest request, HttpServletResponse response) {
		String SALE_ORDER_ID = request.getParameter("selRowId");
		
	    String params = "sql="+getOrderMainSql(SALE_ORDER_ID)+"sql2="+getOrderSubSql(SALE_ORDER_ID);
		request.setAttribute("params", params);
		request.setAttribute("reportFileName", "detailedNoPrice.raq");
		request.setAttribute("RptModel", "SALEREPNP");
		request.setAttribute("reportFlag", "false");
		return new ModelAndView("report/page/saleOrderReportNoPrice");
	}
	public String  getOrderMainSql(String SALE_ORDER_ID){
		String sql = "select u.SALE_ORDER_ID,u.SALE_ORDER_NO,u.BILL_TYPE,u.FROM_BILL_ID,u.FROM_BILL_NO,u.BILL_TYPE2,"+
                "u.ORDER_CHANN_ID,u.ORDER_CHANN_NO, u.ORDER_CHANN_NAME,to_char(u.ORDER_DATE,'yyyy-MM-DD') ORDER_DATE,"+
                "u.CHANN_ID,u.CHANN_NO, u.CHANN_NAME,u.PRINT_NAME,u.TRANSPORT,u.TRANSPORT_SETTLE,u.FACTORY_NO,u.PROESS_TYPE,"+
                "u.URGENCY_TYPE,u.SALE_ID,u.SALE_NAME,u.SALEDEPT_NAME,u.SALEDEPT_ID,to_char(u.PRE_RECV_DATE,'yyyy-MM-DD') PRE_RECV_DATE,"+
                "u.CUST_NAME,u.IS_USE_REBATE,u.RECV_CHANN_ID,u.RECV_CHANN_NO,u.RECV_CHANN_NAME,u.PERSON_CON,u.RECV_ADDR,u.RECV_ADDR_NO,"+
                "u.TEL, u.STATE,u.REMARK,u.REMARK2,u.CREATOR,u.CRE_NAME,to_char(u.CRE_TIME,'yyyy-MM-DD HH24:MI:SS') CRE_TIME,"+
                "u.UPDATOR,u.UPD_NAME,to_char(u.UPD_TIME,'yyyy-MM-DD HH24:MI:SS') UPD_TIME,u.LEDGER_ID,u.LEDGER_NAME LEDGER_NAME_old,"+
                "(select ZTJC from t_sys_ztxx t where t.delflag = '0' and t.ztxxid=u.LEDGER_ID)LEDGER_NAME,u.DEL_FLAG,"+
                "to_char(u.AUDIT_TIME,'yyyy-MM-DD HH24:MI:SS') AUDIT_TIME,u.AUDIT_NAME,u.AUDIT_ID,u.DEPT_ID,u.DEPT_NAME,u.ORG_ID,"+
                "u.ORG_NAME,u.AREA_ID,u.AREA_NO,u.AREA_NAME,u.BASE_ADD_FLAG,to_char(u.ORDER_DELIVERY_DATE,'yyyy-MM-DD') ORDER_DELIVERY_DATE,"+
                "u.CUST_TEL,u.CUST_ADDR, u.TOTAL_AMOUNT,u.TOTAL_REBATE,u.NC_ID,u.PROJECT_ID, u.PROJECT_NO,u.PROJECT_NAME,u.ORDER_TRACE_ID,"+
                "u.RETURN_SHOW_FLAG,u.CHANGE_FLAG,a.CHANGE_APPLY_ID,a.CHANGE_APPLY_NO,a.CHAN_REMARK,a.ORDER_REMARK,a.STATE APPLYSTATE"+
                ", de.DESIGNER_ID, de.DESIGNER, de.DESIGNER_NAME,TO_CHAR (SYSDATE, 'yyyy-MM-DD') CURRENT_DATE,"+
                "(select sum(e.ORDER_NUM) from ERP_SALE_ORDER_DTL e where e.SALE_ORDER_ID = u.SALE_ORDER_ID group by e.SALE_ORDER_ID) as ORDER_SUM "+
                "from ERP_SALE_ORDER u "+
                "left join ERP_CHANGE_APPLY a on a.SALE_ORDER_ID = u.SALE_ORDER_ID and a.DEL_FLAG = '0' and a.state in('草稿','提交') "+
                " left join (select t.*,row_number() over(partition by t.sale_order_id order by ASSIGN_TIME desc) rn from ERP_SALE_DESIGNER t) de"+
                " on u.SALE_ORDER_ID = de.SALE_ORDER_ID and rn = 1 "+
                " where u.SALE_ORDER_ID = '"+SALE_ORDER_ID+"'; ";
		return sql;
	}
	public String  getOrderSubSql(String SALE_ORDER_ID){
		String sql = "select u.SALE_ORDER_DTL_ID,u.SALE_ORDER_ID,u.GROUP_NO,u.PRD_ID,u.PRD_NO,u.PRD_NAME,u.HOLE_SPEC,u.PRD_SPEC,u.PRD_TOWARD,"+
			    "u.PRD_QUALITY,u.PRD_COLOR,u.PRD_GLASS,u.PRD_OTHER,u.PRD_SERIES, u.BRAND,u.STD_UNIT, u.PRICE,u.DECT_RATE,u.DECT_PRICE,"+
			    "u.REBATE_PRICE,u.REBATE_AMOUNT,u.ORDER_NUM,u.ORDER_AMOUNT,to_char(u.ADVC_SEND_DATE,'yyyy-MM-DD') ADVC_SEND_DATE,u.REMARK,"+
			    "u.DEL_FLAG,u.FROM_BILL_DTL_ID,u.SENDED_NUM, u.ROW_NO,u.PRD_SIZE,u.PROJECTED_AREA, u.EXPAND_AREA,u.PRD_PLACE,t.sjxmc PRD_PLACE_TEXT,"+
			    "u.IS_NO_REBATE_FLAG,case when u.IS_NO_REBATE_FLAG =1 then '是' else '否' end IS_NO_REBATE_FLAG_TEXT,"+
			    "u.IS_NO_LOCK_FLAG,case when u.IS_NO_LOCK_FLAG =1 then '开锁孔' else '' end IS_NO_LOCK_FLAG_TEXT,u.FIGURE_NO"+
			    " from ERP_SALE_ORDER_DTL u left join t_sys_sjzdmx t on t.SJXZ = u.PRD_PLACE and t.sjzdid='PRO_PLACE' "+
			    " where 1=1 and u.SALE_ORDER_ID='"+SALE_ORDER_ID+"' and u.DEL_FLAG=0 "+
			    " order by u.GROUP_NO asc, u.ROW_NO ASC";
		return sql;
	}
	/**
	 * 销售订单明细报表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toSaleOrderDtlReport", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView toSendOrderReport(HttpServletRequest request, HttpServletResponse response) {
		Map <String ,String> QueryParams=new HashMap<String,String>();
		//String SALE_ORDER_ID = request.getParameter("selRowId");
		String FACTORY_NO = request.getParameter("FACTORY_NO");
		QueryParams.put("FACTORY_NO", FACTORY_NO);
		String BILL_TYPE = request.getParameter("BILL_TYPE");
		QueryParams.put("BILL_TYPE", BILL_TYPE);
		String SALE_NAME = request.getParameter("SALE_NAME");
		QueryParams.put("SALE_NAME", SALE_NAME);
		String STATE = request.getParameter("STATE");
		QueryParams.put("STATE", STATE);
		//
		String ORDER_DATE1 = request.getParameter("ORDER_DATE1");
			ORDER_DATE1=StringUtils.isBlank(ORDER_DATE1)?TimeUtil.getDate():ORDER_DATE1;
		QueryParams.put("ORDER_DATE1", ORDER_DATE1);
		String ORDER_DATE2 = request.getParameter("ORDER_DATE2");
		QueryParams.put("ORDER_DATE2", ORDER_DATE2);
		String ORDER_DELIVERY_DATE1 = request.getParameter("ORDER_DELIVERY_DATE1");
		QueryParams.put("ORDER_DELIVERY_DATE1", ORDER_DELIVERY_DATE1);
		String ORDER_DELIVERY_DATE2 = request.getParameter("ORDER_DELIVERY_DATE2");
		//ORDER_DELIVERY_DATE2=StringUtils.isBlank(ORDER_DELIVERY_DATE2)?TimeUtil.getDate():ORDER_DELIVERY_DATE2;
		QueryParams.put("ORDER_DELIVERY_DATE2", ORDER_DELIVERY_DATE2);
		
		StringBuffer sql=new StringBuffer();
		 sql.append("select E.FACTORY_NO,E.BILL_TYPE,to_char(E.ORDER_DATE,'yyyy-mm-dd') ORDER_DATE,to_char(E.AUDIT_TIME,'yyyy-mm-dd') AUDIT_TIME,E.STATE,E.SALE_NAME,to_char(E.ORDER_DELIVERY_DATE,'yyyy-mm-dd')  ORDER_DELIVERY_DATE,E.CUST_NAME,") ;
		 sql.append("E.CHANN_NAME,E.PRINT_NAME,EL.PRD_NO,EL.PRD_NAME,EL.PRD_SPEC,EL.PRD_QUALITY,EL.STD_UNIT,EL.ORDER_NUM, EL.DECT_PRICE,EL.DECT_RATE,E.CRE_NAME,");
		 sql.append("EL.REBATE_AMOUNT,EL.ORDER_AMOUNT,EL.PRD_TOWARD, EL.PRD_GLASS,EL.PRD_COLOR,EL.PRD_OTHER,EL.REMARK,E.REMARK2,E.TEL,C.CHANN_ABBR, ");
		 sql.append("EL.BRAND,EL.PRD_SERIES,E.RECV_CHANN_NAME,E.RECV_ADDR,E.PERSON_CON,E.DEL_FLAG,EL.GROUP_NO,EL.PRD_NAME ");
		 sql.append(" from erp_sale_order E left join  erp_sale_order_dtl EL on E.sale_order_id = EL.sale_order_id  left join Base_Chann C on C.chann_id=E.chann_id");
		 sql.append(" where E.DEL_FLAG=0 and EL.DEL_FLAG=0  ");
		 if(!StringUtils.isBlank(FACTORY_NO)) {
			 sql.append(" and  E.FACTORY_NO like '%"+FACTORY_NO+"%' ");
		 }
		 if(!StringUtils.isBlank(BILL_TYPE)) {
			 sql.append(" and E.BILL_TYPE like '%"+BILL_TYPE+"%' ");
		 }
		 if(!StringUtils.isBlank(SALE_NAME)) {
			 sql.append(" and E.CRE_NAME like '%"+SALE_NAME+"%'  ");
		 }

		 if(!StringUtils.isBlank(STATE)) {
			 sql.append(" and E.STATE like '%"+STATE+"%'  ");
		 }
		 if(!StringUtils.isBlank(ORDER_DATE1)) {
			 sql.append(" and to_char(E.ORDER_DATE,'yyyy-MM-dd')  >= '"+ORDER_DATE1+"'  ");
		 }

		 if(!StringUtils.isBlank(ORDER_DATE2)) {
			 sql.append(" and to_char(E.ORDER_DATE,'yyyy-MM-dd')  <='"+ORDER_DATE2+"'  ");
		 }

		 if(!StringUtils.isBlank(ORDER_DELIVERY_DATE1)) {

			 sql.append(" and to_char(E.ORDER_DELIVERY_DATE,'yyyy-MM-dd')   >= '"+ORDER_DELIVERY_DATE1+"'  ");
		 }

		 if(!StringUtils.isBlank(ORDER_DELIVERY_DATE2)) {
			 sql.append(" and  to_char(E.ORDER_DELIVERY_DATE,'yyyy-MM-dd')   <= '"+ORDER_DELIVERY_DATE2+"' ");
		 }

		 
		 sql.append("  order by E.ORDER_DATE desc,E.FACTORY_NO desc");
		 String params = "sql="+sql;
		 request.setAttribute("params", params);
		 //报表文件名
		request.setAttribute("reportFileName", "saleOrderDtl.raq");
		request.setAttribute("QueryParams", QueryParams);
//		request.setAttribute("RptModel", "SALEREPNP");
//		request.setAttribute("reportFlag", "false");
		return new ModelAndView("report/page/saleOrderDtlReport");
	}
	
	
	/**
	 * 账户充值审核明细报表
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequestMapping(value = "/toDepositInfoDtlReport", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView toDepositInfoDtlReport(HttpServletRequest request, HttpServletResponse response) {
		Map <String ,String> QueryParams=new HashMap<String,String>();
		String CRE_TIME1 = request.getParameter("CRE_TIME1");
		QueryParams.put("CRE_TIME1", CRE_TIME1);
		String CRE_TIME2 = request.getParameter("CRE_TIME2");
		CRE_TIME2=StringUtils.isBlank(CRE_TIME2)?TimeUtil.getDate():CRE_TIME2;
		QueryParams.put("CRE_TIME2",CRE_TIME2);
		String CHANN_NAME = request.getParameter("CHANN_NAME");
		QueryParams.put("CHANN_NAME", CHANN_NAME);
		String STATE = request.getParameter("STATE");
		QueryParams.put("STATE", STATE);
		StringBuffer sql=new StringBuffer();
		 sql.append("select  u.DEPOSIT_NO,u.DEPOSIT_TYPE,u.CHANN_NAME,u.DEPOSIT_NO,u.STATE,") ;
		 sql.append("u.REMARK,u.CREATOR,u.CRE_NAME,to_char(u.CRE_TIME, 'yyyy-MM-DD') CRE_TIME,");
		 sql.append("u.UPDATOR,u.UPD_NAME,to_char(u.UPD_TIME, 'yyyy-MM-DD') UPD_TIME,");
		 sql.append("u.AUDIT_NAME,to_char(u.AUDIT_TIME, 'yyyy-MM-DD') AUDIT_TIME,u.DEPT_NAME,");
		 sql.append(" u.ORG_NAME, u.LEDGER_NAME, u.DEPOSIT_AMOUNT, u.AUDIT_OPINION,u.DEL_FLAG");
		 sql.append(" from  ERP_DEPOSIT_INFO u  where u.DEL_FLAG=0  ");
		 
		 if(!StringUtils.isBlank(CRE_TIME1)) {
			 sql.append(" and   to_char(u.CRE_TIME,'yyyy-MM-dd')   <= '"+CRE_TIME1+"' ");
		 }
		 if(!StringUtils.isBlank(CRE_TIME2)) {
			 sql.append(" and  to_char(u.CRE_TIME ,'yyyy-MM-dd') >=  '"+CRE_TIME2+"' ");
		 }

		 if(!StringUtils.isBlank(CHANN_NAME)) {
			 sql.append(" and u.CHANN_NAME like '%"+CHANN_NAME+"%'  ");
		 }
		 if(!StringUtils.isBlank(STATE)) {
			 sql.append(" and u.STATE like '%"+STATE+"%'  ");
		 }
		 sql.append("  order by u.CRE_TIME desc,u.DEPOSIT_NO desc");
		 String params = "sql="+sql;
		 request.setAttribute("params", params);
		 //报表文件名
		request.setAttribute("reportFileName", "depositInfoDtl.raq");
		request.setAttribute("QueryParams", QueryParams);
//		request.setAttribute("RptModel", "SALEREPNP");
//		request.setAttribute("reportFlag", "false");
		return new ModelAndView("report/page/depositInfoDtlReport");
	}
	
	/**
	 * 销售订单有价格报表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toGoodsOrderPrint", method = { RequestMethod.GET })
	public ModelAndView toGoodsOrderPrint(HttpServletRequest request, HttpServletResponse response) {
		String GOOD_ORDER_ID = request.getParameter("selRowId");

		String params = "sql=select GOODS_ORDER_NO,BILL_TYPE,to_char(ORDER_DATE, 'yyyy-MM-DD') ORDER_DATE,"
				+ "to_char(PRE_RECV_DATE, 'yyyy-MM-DD')PRE_RECV_DATE,CHANN_NAME,TOTAL_AMOUNT,TOTAL_REBATE,REMARK2 "
				+ " from DRP_GOODS_ORDER where DEL_FLAG=0 and GOODS_ORDER_ID='"+GOOD_ORDER_ID+"';"
		+"sql2=select * from DRP_GOODS_ORDER_DTL where DEL_FLAG=0 and GOODS_ORDER_ID='"+GOOD_ORDER_ID+"' order by group_no asc";
		request.setAttribute("params", params);
		request.setAttribute("reportFileName", "goodsOrderPrint.raq");
		request.setAttribute("RptModel", "SALEREP");
		request.setAttribute("reportFlag", "false");
		return new ModelAndView("report/page/goodsOrderPrint");
	}
	
	/**
	 * 销售订单明细报表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toSaleOrderCountReport", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView toSaleOrderCountReport(HttpServletRequest request, HttpServletResponse response) {
		Map <String ,String> QueryParams=new HashMap<String,String>();
		//String SALE_ORDER_ID = request.getParameter("selRowId");
		String FACTORY_NO = request.getParameter("FACTORY_NO");
		QueryParams.put("FACTORY_NO", FACTORY_NO);
		String BILL_TYPE = request.getParameter("BILL_TYPE");
		QueryParams.put("BILL_TYPE", BILL_TYPE);
		String SALE_NAME = request.getParameter("SALE_NAME");
		QueryParams.put("SALE_NAME", SALE_NAME);
		String STATE = request.getParameter("STATE");
		QueryParams.put("STATE", STATE);
		String orderOrg="116";
		
		String ORDER_DATE1 = request.getParameter("ORDER_DATE1");
			ORDER_DATE1=StringUtils.isBlank(ORDER_DATE1)?TimeUtil.getDate():ORDER_DATE1;
		QueryParams.put("ORDER_DATE1", ORDER_DATE1);
		String ORDER_DATE2 = request.getParameter("ORDER_DATE2");
		QueryParams.put("ORDER_DATE2", ORDER_DATE2);
		String ORDER_DELIVERY_DATE1 = request.getParameter("ORDER_DELIVERY_DATE1");
		QueryParams.put("ORDER_DELIVERY_DATE1", ORDER_DELIVERY_DATE1);
		String ORDER_DELIVERY_DATE2 = request.getParameter("ORDER_DELIVERY_DATE2");
		QueryParams.put("ORDER_DELIVERY_DATE2", ORDER_DELIVERY_DATE2);
		
		StringBuffer sqlparams=new StringBuffer();
		 if(!StringUtils.isBlank(FACTORY_NO)) {
			 sqlparams.append(" and  so.FACTORY_NO like '%"+FACTORY_NO+"%' ");
		 }
		 if(!StringUtils.isBlank(BILL_TYPE)) {
			 sqlparams.append(" and so.BILL_TYPE in("+BILL_TYPE+")");
		 }
		 if(!StringUtils.isBlank(SALE_NAME)) {
			 sqlparams.append(" and so.CRE_NAME like '%"+SALE_NAME+"%'  ");
		 }
		 if(!StringUtils.isBlank(STATE)) {
			 sqlparams.append(" and so.STATE like '%"+STATE+"%'  ");
		 }
		 if(!StringUtils.isBlank(ORDER_DATE1)) {
			 sqlparams.append(" and to_char(so.ORDER_DATE ,'yyyy-MM-dd') >= '"+ORDER_DATE1+"' ");
		 }
		 if(!StringUtils.isBlank(ORDER_DATE2)) {
			 sqlparams.append(" and to_char(so.ORDER_DATE ,'yyyy-MM-dd') <= '"+ORDER_DATE2+"' ");
		 }
		 if(!StringUtils.isBlank(ORDER_DELIVERY_DATE1)) {
			 sqlparams.append(" and to_char(so.ORDER_DELIVERY_DATE ,'yyyy-MM-dd')  >= '"+ORDER_DELIVERY_DATE1+"' ");
		 }
		 if(!StringUtils.isBlank(ORDER_DELIVERY_DATE2)) {
			 sqlparams.append(" and to_char(so.ORDER_DELIVERY_DATE ,'yyyy-MM-dd')  <= '"+ORDER_DELIVERY_DATE2+"' ");
		 }
		StringBuffer ds1=new StringBuffer();
		ds1.append("select so.sale_order_id,so.FACTORY_NO,so.BILL_TYPE,so.ORDER_DATE,so.AUDIT_TIME,so.STATE,so.CRE_NAME, ");
		ds1.append(" so.ORDER_DELIVERY_DATE,so.CHANN_NAME,bc.chann_abbr,so.FROM_BILL_NO,so.PROESS_TYPE,so.TOTAL_AMOUNT,so.URGENCY_TYPE ") ;
		ds1.append(" from erp_sale_order so  left join base_chann bc on bc.chann_id=so.chann_id ");
		ds1.append(" where so.DEL_FLAG=0 and so.ledger_id='"+orderOrg+"' ");
		ds1.append(sqlparams.toString());
		ds1.append("  order by so.ORDER_DATE desc; ");
		 
		StringBuffer ds2=new StringBuffer();
		ds2.append(" select  sale_order_id,two_name,order_num  from ( ");
		ds2.append(" select so.sale_order_id,bp.two_id,bp.two_name,sum(sod.order_num) order_num ");
		ds2.append(" from ERP_SALE_ORDER so  left join ERP_SALE_ORDER_DTL sod on so.sale_order_id = sod.sale_order_id ");
		ds2.append(" left join base_product bp on bp.prd_id=sod.prd_id ");
		ds2.append(" where so.del_flag=0 and sod.del_flag=0  and sod.order_num>0 and so.ledger_id='"+orderOrg+"' ");
		ds2.append(sqlparams.toString());
		ds2.append(" group by so.sale_order_id,bp.two_id,bp.two_name ");
		ds2.append(" order by so.sale_order_id ,bp.two_id); ");
		
		StringBuffer ds3=new StringBuffer();		 
		ds3.append(" select prd_id,prd_name from base_product where par_prd_id='0145' order by prd_id; ");
		
		StringBuffer ds4=new StringBuffer();
		ds4.append(" select sjxmc from t_sys_sjzdmx where sjzdid='PRO_SERIES' order by keycode; ");
		
		StringBuffer ds5=new StringBuffer();
		ds5.append(" select so.sale_order_id,sod.prd_series,sum(sod.order_num*bp.task_rario) order_num  ");
		ds5.append(" from ERP_SALE_ORDER so  left join ERP_SALE_ORDER_DTL sod on so.sale_order_id = sod.sale_order_id ");
		ds5.append(" left join base_product bp on bp.prd_id=sod.prd_id ");
		ds5.append(" where so.del_flag=0 and sod.del_flag=0  and sod.order_num>0 and so.ledger_id='"+orderOrg+"' ");
		ds5.append(sqlparams.toString());
		ds5.append(" group by so.sale_order_id,sod.prd_series ");
	    
		String params = "sql1="+ds1+"sql2="+ds2+"sql3="+ds3+"sql4="+ds4+"sql5="+ds5;
	    request.setAttribute("params", params);
		 //报表文件名
		request.setAttribute("reportFileName", "saleOrderCountReport.raq");
		request.setAttribute("QueryParams", QueryParams);
		return new ModelAndView("report/page/saleOrderCountReport");
	}

}
