package com.centit.drp.main.wap.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centit.common.controller.BaseController;
import com.centit.commons.model.Consts;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.commons.util.security.MD5Encrypt;
import com.centit.drp.main.wap.service.SysWapLoginService;
import com.centit.erp.sale.marketcardsale.model.MarketcardSaleChldModel;
import com.centit.sys.model.UserBean;
import com.centit.sys.model.ZTWHModel;

@RestController
@RequestMapping("/wap/login")
public class SysWapLoginAction extends BaseController {
	
	private Logger logger = Logger.getLogger(SysWapLoginAction.class);
	
	private static final String webPath = "base/community";
	
	@Autowired
	private SysWapLoginService sysWapLoginService;
	
	
	 /**
		 * Auth login.
		 * 
		 * @param mapping the mapping
		 * @param form the form
		 * @param request the request
		 * @param response the response
		 * 
		 * @return the action forward
		 */
		public String authLogin(HttpServletRequest request, HttpServletResponse response) {
			String YHM = StringUtil.nullToSring(request.getParameter("S_NAME")).trim();
			String KL = request.getParameter("S_PWD");
			String ZTXXID = request.getParameter("S_ZTID");
			String ZTMC = request.getParameter("S_ZTMC");
	        String USERCSS = request.getParameter("userCss");
	        String card_no = request.getParameter("card_no");
	        
	        if(!StringUtil.isEmpty(KL)){
	           KL = MD5Encrypt.MD5(KL);
	        }
	    
	        //LogicUtil.actLog("系统管理","登录","admin","成功","这是一条测试'数据，'阿基多'");
	         if(StringUtil.isEmpty(YHM)||StringUtil.isEmpty(KL)) {
	        	request.setAttribute("msg", "登录失败,用户名或者密码为空！");
				return view(webPath, Consts.FAILURE);
	        }
			 
			if (StringUtil.isEmpty(ZTXXID)) {
				request.setAttribute("msg", "登录失败,当前用户没有分管任何帐套！");
				return view(webPath, Consts.FAILURE);

			}
		 
			try {
				UserBean aUserBean = null;
	            System.err.println("开始登陆!!");
				try {
					// 这里需求修改
					aUserBean = sysWapLoginService.initQXComm(YHM, KL, ZTXXID);
				} catch (Exception ex) {
					logger.error(ex);
					request.setAttribute("msg", "登陆失败，程序出错！");
					return view(webPath, Consts.FAILURE);

				}
				if (aUserBean == null) {
					request.setAttribute("msg", "登陆失败，用户名或密码不正确！");
					return view(webPath, Consts.FAILURE);
				}
				aUserBean.setLoginZTXXID(ZTXXID);
				aUserBean.setLoginZTMC(ZTMC);
		 
			  
				// 设置session失效时间和session切换
				HttpSession session = request.getSession(true);
				if(null!=session){
	              session.invalidate();
	            }
				
//				try{
//					Map<String,String> card = this.sysWapLoginService.loadCardByNo(card_no);
//					request.setAttribute("rst", card);
//				}catch(Exception e){
//					e.printStackTrace();
//				}
				
	            session = request.getSession(true);
	            System.err.println("session\u5931\u6548\u65F6\u95F4:="+ (int) (0.5D * 3600D));
				session.setMaxInactiveInterval((int) (2D * 3600D));
				session.removeAttribute("UserCSS");
				session.removeAttribute("UserBean");
				aUserBean.setRealYHKL(KL);
			 
				// 创建小数位参数对象
				session.setAttribute("UserBean_MD5KL", KL);
				session.setAttribute("UserBean", aUserBean);
				session.setAttribute("UserCSS", USERCSS);
				return view(webPath, "erp/sale/mobilecardsale/Mobile_Card_Edit");
			} catch (Exception ex) {
				logger.error(ex);
				System.err.println("LoginServlet in Error:" + ex.toString());
				request.setAttribute("msg", "登陆失败，程序出错！");
				return view(webPath, Consts.FAILURE);
			}
		}
		
		/**
		 * 到卡券编辑页面
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public String toCardEdit(HttpServletRequest request, HttpServletResponse response) {
			UserBean userBean = ParamUtil.getUserBean(request);
			if(null == userBean) {
				request.setAttribute("msg", "用户已失效，请重新登录！");
				return view(webPath, Consts.FAILURE);
			}
			return view(webPath, "wapindex");
		}
		
		 
		
		/**
		 * 卡券列表
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public String cardList(HttpServletRequest request, HttpServletResponse response) {
			UserBean userBean = ParamUtil.getUserBean(request);
			if (null == userBean) {
				request.setAttribute("msg", "用户已失效，请重新登录！");
				return view(webPath, Consts.FAILURE);
			}
//			List<MarketcardSaleChldModel> list = sysWapLoginService.queryCradList(userBean);
//			request.setAttribute("page", list);
			return view(webPath, "cardlist");
		}
		
		 
		
		
		/**
		 * 注销
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public String loginOut(HttpServletRequest request, HttpServletResponse response) {
			try {
				HttpSession session = (HttpSession) request.getSession();
				if (session.getAttribute("UserBean") != null) {
					session.removeAttribute("UserBean");
				}
				// 复位会话
				session.invalidate();
			 
			} catch (Exception e) {
				logger.info(e);
			}
			return view(webPath, "login");
		}
		
		
		 
		
		/**
		 * 获取帐套信息 Description :.
		 * 
		 * @param mapping the mapping
		 * @param form the form
		 * @param request the request
		 * @param response the response
		 */
		public void getZtxx(HttpServletRequest request, HttpServletResponse response) {
			PrintWriter writer = getWriter(response);
			String jsonResult = jsonResult();
			try {
				String userId = ParamUtil.get(request, "userId");
				StringBuffer rst = new StringBuffer();
//				if (!StringUtil.isEmpty(userId)) {
//					List<ZTWHModel> ztxxList = sysWapLoginService.getZtxx(userId);
//					if (null != ztxxList && !ztxxList.isEmpty()) {
//						rst.append(ztxxList.get(0).getZTXXID());
//					}
//				}
				jsonResult = jsonResult(true, rst.toString());
			} catch (Exception e) {
				logger.error(e);
				jsonResult = jsonResult(false, "获取帐套信息失败");
			}
			if (null != writer) {
				writer.write(jsonResult);
				writer.close();
			}
		}
		
		
		public SysWapLoginService getSysWapLoginService() {
			return sysWapLoginService;
		}
		public void setSysWapLoginService(SysWapLoginService sysWapLoginService) {
			this.sysWapLoginService = sysWapLoginService;
		}

}
