/**
 *@module 系统模块
 *@func 系统模块
 *@version 1.1
 *@author zhuxw
 */
package com.centit.commons.model;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * The Interface Consts.
 *
 * @module 系统参数常量
 * @func 系统参数常量
 */
public interface Consts {

	/** 调试信息开关. */
	public static final boolean DEBUG = Properties.getBoolean("DEBUG");

	/** 缺省密码. */
	public static final String DEF_PWD = Properties.getString("DEF_PWD");

	/** 缺省分頁記錄數. */
	public static final int PAGE_SIZE = Properties.getInt("PAGE_SIZE");

	/** 是否显示帐套. */
	public static final String ACCOUNT_DISPLAY = Properties.getString("ACCOUNT_DISPLAY");

	/** 默认帐套编号. */
	public static final String ACCOUNT_CODE = Properties.getString("ACCOUNT_CODE");

	/** 默认帐套名称. */
	public static final String ACCOUNT_DISPLAY_NAME = Properties.getString("ACCOUNT_DISPLAY_NAME");

	/** 默认登录CSS. */
	public static final String DEFAULT_CSS = Properties.getString("DEFAULT_CSS");

	/** 是否单点登录. */
	public static final String SINGN_LOGIN = Properties.getString("SINGN_LOGIN");

	/** 是否采用大窗口. */
	public static final String MAX_WINDOWS = Properties.getString("MAX_WINDOWS");
	/** 是否记录操作日志 */
	public static final boolean ACT_LOG = Properties.getBoolean("ACT_LOG");

	/** 渠道初始化编号 */
	public static final String INI_CHANN_ROLE_NO = Properties.getString("INI_CHANN_ROLE_NO");

	/** 用户Session标识. */
	public static final String USR_SESS = "UserBean";

	/** The Constant PARAM_COVER. */
	public static final String PARAM_COVER = "paramCover";

	/** 公共页面链接：空白页面. */
	public static String BLANK = "blank";

	/** 公共页面链接：预置页面，提示：正在开发中……. */
	public static String INPROGRESS = "inprogress";

	/** 公共页面链接：操作失败警示页面,返回时不刷新原页面. */
	public static String FAILURE = "failure";

	/** 公共页面链接: 操作成功提示页面,参数以_下划线标识. */
	public static String SUCCESS = "success";

	/** 公共页面链接: 操作成功提示页面,参数以_下划线标识. */
	public static String LOGIN = "login";

	/** 公共页面链接：操作失败警示页面,返回时刷新原页面. */
	public static String FAILEXT = "failext";

	/** 公共页面链接: 操作成功提示页面,普通参数. */
	public static String SUCCEXT = "succext";

	public static String WEB_PATH = "../commons/";

	/** 公共页面链接: 翻页工具栏. */
	public static String PAGE_FOOTER = "pfooter";

	/** 数据库类型. */
	public static final String DBTYPE = Properties.getString("DBTYPE");

	/** 系统表的模式名. */
//    public static final String SYSSCHEMA = Properties.getString("SYSSCHEMA");

    /** 邮件服务器地址. */
    public static final String MAIL_SERVER_HOST = Properties.getString("MAIL_SERVER_HOST");

    /** 邮件服务器端口. */
    public static final String MAIL_SERVER_PORT = Properties.getString("MAIL_SERVER_PORT");

    /** 邮件是否需要身份验证. */
    public static final boolean MAIL_VALIDATE = Properties.getBoolean("MAIL_VALIDATE");

    /** 默认发送邮件地址. */
    public static final String DEF_FROM_MAIL_AD = Properties.getString("DEF_FROM_MAIL_AD");

    /** 默认邮件用户名. */
    public static final String DEF_MAIL_USERNAME = Properties.getString("DEF_MAIL_USERNAME");

    /** 默认邮件密码. */
    public static final String DEF_MAIL_PASSWORD = Properties.getString("DEF_MAIL_PASSWORD");

    /** JobserverIp. */
    public static final String JOB_SERVER_IP = Properties.getString("JOB_SERVER_IP");

    /** PRE_SEVJOBS 一次锁定JOBS. */
    public static final String PRE_SEVJOBS = Properties.getString("PRE_SEVJOBS");

	/** 调试信息开关. */
	public static final boolean FUN_CHEK_PVG = Properties.getBoolean("FUN_CHEK_PVG");

	/** 最大冻结数量. */
	public static final String MAX_FREEZE_DATE = Properties.getString("MAX_FREEZE_DATE");

	/** 控制是否调用外系统. */
	public static final String INVOKE_SYS_FLG = Properties.getString("INVOKE_SYS_FLG");

	/** web service wsdl. */
	public static final String WS_WSDL = Properties.getString("WS_WSDL");

	/** web service 命名空间. */
	public static final String WS_NAME_SPACE = Properties.getString("WS_NAME_SPACE");

	/** web service 服务名. */
	public static final String WS_SER_NAME = Properties.getString("WS_SER_NAME");

	/** web service 端口号. */
	public static final String WS_PORT_NO = Properties.getString("WS_PORT_NO");

	/** web service 验证用户名. */
	public static final String WS_USERNAME = Properties.getString("WS_USERNAME");

	/** web service 验证密码. */
	public static final String WS_PASSWORD = Properties.getString("WS_PASSWORD");

	/** web service 验证用户名. */
	public static final String DM_USERNAME = Properties.getString("DM_USERNAME");

	/** web service 验证密码. */
	public static final String DM_PASSWORD = Properties.getString("DM_PASSWORD");

	/** DM web service . */
	public static final String DM_WSDL = Properties.getString("DM_WSDL");

	/** 不走区域服务中心流程. */
	public static final String IS_OLD_FLOW = Properties.getString("IS_OLD_FLOW");

	/** 控制订货订单合并成一个销售订单功能. */
	public static final String INVOKE_GOODS_MERGE = Properties.getString("INVOKE_GOODS_MERGE");

	public static final String SLEEMON_REPORT_URL = Properties.getString("SLEEMON_REPORT_URL");
	/** 图片服务器地址 内网 **/
	public static final String APP_SERVER_URL_NET = Properties.getString("APP_SERVER_URL_NET");
	/** 图片服务器地址 内网 **/
	public static final String PIC_SERVER_URL = Properties.getString("PIC_SERVER_URL");

	/** 图片服务器地址 外网 **/
	public static final String PIC_SERVER_URL_NET = Properties.getString("PIC_SERVER_URL_NET");

	/** 报表服务器地址 内网 **/
	public static final String RUNQIAN_REPORT_URL = Properties.getString("RUNQIAN_REPORT_URL");

	/** 报表服务器地址 外网 **/
	public static final String RUNQIAN_REPORT_URL_NET = Properties.getString("RUNQIAN_REPORT_URL_NET");

	public static final  String REBATE_PRD_NUMBERS="07-12,07-11,07-10,06-82,06-20,06-08,06-06,06-05,06-02,06-01,05,03,02,01,22,06-14,06-91,04";

	public static final boolean CTR_LARGESS = false;
	public static final  String LARGESS_PRD_NUMBERS="07-12,06-31,06-20,06-82,07-11,06-08,01,06-06,07-10,06-05,06-83,09-30,02,05,06-01,06-02,03,09-28,09-31,09-27,09-26";

	public static final String IS_NO_DELIVER_PLAN_FLAG = Properties.getString("IS_NO_DELIVER_PLAN_FLAG");

	public static final String USER_ONLY_ORDER_NO = Properties.getString("USER_ONLY_ORDER_NO");

	public static final String IS_NO_ADVC_DATE_FLAG = Properties.getString("IS_NO_ADVC_DATE_FLAG");

	public static final String IS_NO_SPCL_FLAG = Properties.getString("IS_NO_SPCL_FLAG");
	//flash报表打印地址
	public static final String FLASH_PRINT_URL = Properties.getString("FLASH_PRINT_URL");
	
	//NC接口访问地址
	public static String PARAM_URL = Properties.getString("PARAM_URL");
	
	//webservice地址
	public static String WEBSERVICE_URL = Properties.getString("WEBSERVICE_URL");

	/**
	 * 配置文件中的Key值
	 */
	/** 上传路径*/
	public static final String UPLOADPATH_ROOT = Properties.getString("dir.upload");
	
	public static final String PROPERTIES_KEY_UPLOADPATH = "dir.upload";
	/**
	 * 流程编号与帐套对应
	 */
	public static final JSONObject FLOW_CONF = JSONObject.parseObject(Properties.getString("FLOW_CONF"));

	/**
	 * 厂编与帐套对应
	 */
	public static final JSONObject FACTORY_NO_CONF = JSONObject.parseObject(Properties.getString("FACTORY_NO_CONF"));

	/** 厂编与帐套对应 */
	public static final JSONObject LEDGER_ID_CONF = JSONObject.parseObject(Properties.getString("LEDGER_ID_CONF"));

	/**
	 * 预订单、销售订单权限对应状态过滤
	 */
	/**
	 * //经销商页面追加通用权限过滤，总部页面追加渠道分管、帐套分管权限
		// 预订单维护
		map.put("FX0020307", "草稿");
		map.put("FX0020305", "待经销商确认");
		//预订单处理
		map.put("BS0010304", "审图中");
		map.put("BS0010305", "报价中");
		map.put("BS0010306", "订单审核");
		map.put("BS0010307", "已审核");
		// 销售订单维护
		map.put("BS0010404", "待处理");
		map.put("BS0010405", "待处理");
		map.put("BS0010407", "分派设计师");
		map.put("BS0010408", "设计师拆件");//追加分配的设计师用户过滤权限
		map.put("BS0010405", "待生产");//追加分配的设计师用户过滤权限
		// 总部销售订单变更
		map.put("BS0010506", "提交");// 申请单状态
		map.put("BS0010504", "审图中");
		map.put("BS0010505", "报价中");
		//经销商销售订单变更
		map.put("FX0020602", "待经销商确认");
		//发货指令审核
		map.put("BS0010702", "待审核");
		//返修发货审核
		map.put("BS0010902", "待审核");
		// 问题反馈单处理
		map.put("BS0011004", "处理中");
		//返修单审核
		map.put("BS0012202", "待审核");
		//账户充值
		map.put("BS0011302", "提交");
		//临时额度审核
		map.put("BS0011702", "提交");
	 */
	public static final JSONObject BILL_STATE_FILTER = JSONObject.parseObject(Properties.getString("BILL_STATE_FILTER"));
	/**
	 * 首页待办菜单信息参数
	 * Map<String,Map<String,String>> param=new HashMap<String, Map<String,String>>();
		Map<String,String> map1=new HashMap<String, String>();
		map1.put("menuId", "DR0104");
		map1.put("menuName", "预订单维护");
		map1.put("menuUrl", "drp/goodsorderhd/toFrame?module=wh&channel=agency");
		map1.put("state", "'退回','草稿'");
		map1.put("tableName", "DRP_GOODS_ORDER");
		map1.put("qxType", "1");// 1代表经销商&& 帐套分管
		param.put("FX0020307", map1);
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "DR0104");
		map1.put("menuName", "预订单维护");
		map1.put("menuUrl", "drp/goodsorderhd/toFrame?module=wh&channel=agency");
		map1.put("state", "待经销商确认");
		map1.put("tableName", "DRP_GOODS_ORDER");
		map1.put("qxType", "1");
		param.put("FX0020305", map1);
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0201");
		map1.put("menuName", "预订单处理");
		map1.put("menuUrl", "drp/goodsorderhd/toFrame?module=sh=ordersCentre");
		map1.put("state", "审图中");
		map1.put("tableName", "DRP_GOODS_ORDER");
		map1.put("qxType", "2");//2代表用户分管经销商&&帐套分管
		param.put("BS0010304", map1);
		
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0201");
		map1.put("menuName", "预订单处理");
		map1.put("menuUrl", "drp/goodsorderhd/toFrame?module=sh=ordersCentre");
		map1.put("state", "报价中");
		map1.put("tableName", "DRP_GOODS_ORDER");
		map1.put("qxType", "2");
		param.put("BS0010305", map1);
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0201");
		map1.put("menuName", "预订单处理");
		map1.put("menuUrl", "drp/goodsorderhd/toFrame?module=sh=ordersCentre");
		map1.put("state", "订单审核");
		map1.put("tableName", "DRP_GOODS_ORDER");
		map1.put("qxType", "2");
		param.put("BS0010306", map1);
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0201");
		map1.put("menuName", "预订单处理");
		map1.put("menuUrl", "drp/goodsorderhd/toFrame?module=sh=ordersCentre");
		map1.put("state", "已审核");
		map1.put("tableName", "DRP_GOODS_ORDER");
		map1.put("qxType", "2");
		param.put("BS0010307", map1);
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0202");
		map1.put("menuName", "销售订单维护");
		map1.put("menuUrl", "erp/saleorder/toFrame?module=wh");
		map1.put("state", "'草稿','退回'");
		map1.put("tableName", "ERP_SALE_ORDER");
		map1.put("qxType", "2");
		param.put("BS0010402", map1);
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0202");
		map1.put("menuName", "销售订单维护");
		map1.put("menuUrl", "erp/saleorder/toFrame?module=wh");
		map1.put("state", "待处理");
		map1.put("tableName", "ERP_SALE_ORDER");
		map1.put("qxType", "2");
		param.put("BS0010404", map1);
		
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0202");
		map1.put("menuName", "销售订单维护");
		map1.put("menuUrl", "erp/saleorder/toFrame?module=wh");
		map1.put("state", "分派设计师");
		map1.put("tableName", "ERP_SALE_ORDER");
		map1.put("qxType", "2");
		param.put("BS0010407", map1);
		
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0202");
		map1.put("menuName", "销售订单维护");
		map1.put("menuUrl", "erp/saleorder/toFrame?module=wh");
		map1.put("state", "设计师拆件");
		map1.put("tableName", "ERP_SALE_ORDER");
		map1.put("qxType", "3");// 特殊查询
		map1.put("sql", " u.SALE_ORDER_ID in (select s.SALE_ORDER_ID from ERP_SALE_DESIGNER s where s.FINISH_FLAG='0' and DESIGNER = 'XTYHID')");
		param.put("BS0010408", map1);
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0202");
		map1.put("menuName", "销售订单维护");
		map1.put("menuUrl", "erp/saleorder/toFrame?module=wh");
		map1.put("state", "待生产");
		map1.put("tableName", "ERP_SALE_ORDER");
		map1.put("qxType", "2");
		param.put("BS0010405", map1);
		
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0203");
		map1.put("menuName", "销售订单变更");
		map1.put("menuUrl", "erp/changeapply/toFrame?module=sh");
		map1.put("state", "提交");
		map1.put("tableName", "(select s.CHANN_ID,S.LEDGER_ID,c.STATE,c.DEL_FLAG from ERP_CHANGE_APPLY c left join ERP_SALE_ORDER s on c.SALE_ORDER_ID= s.SALE_ORDER_ID)");
		map1.put("qxType", "2");
		param.put("BS0010506", map1);
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0203");
		map1.put("menuName", "销售订单变更");
		map1.put("menuUrl", "erp/changeapply/toFrame?module=sh");
		map1.put("state", "审图中");
		map1.put("tableName", "ERP_SALE_ORDER");
		map1.put("qxType", "2");
		param.put("BS0010504", map1);
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0203");
		map1.put("menuName", "销售订单变更");
		map1.put("menuUrl", "erp/changeapply/toFrame?module=sh");
		map1.put("state", "报价中");
		map1.put("tableName", "ERP_SALE_ORDER");
		map1.put("qxType", "2");
		param.put("BS0010505", map1);
		
		
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "DR0107");
		map1.put("menuName", "销售订单变更");
		map1.put("menuUrl", "erp/changeapply/toFrame?module=wh");
		map1.put("state", "待经销商确认");
		map1.put("tableName", "ERP_SALE_ORDER");
		map1.put("qxType", "1");
		param.put("FX0020602", map1);
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0302");
		map1.put("menuName", "发货指令审核");
		map1.put("menuUrl", "deliver/order/toFrames?module=sh");
		map1.put("state", "待审核");
		map1.put("tableName", "ERP_SEND_ORDER");
		map1.put("qxType", "2");
		param.put("BS0010702", map1);
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0304");
		map1.put("menuName", "返修发货审核");
		map1.put("menuUrl", "rework/deliver/toFrames?module=sh");
		map1.put("state", "待审核");
		map1.put("tableName", "ERP_SEND_ORDER");
		map1.put("qxType", "3");
		map1.put("sql", " u.BILL_TYPE = '返修订单' ");
		param.put("BS0010902", map1);
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0402");
		map1.put("menuName", "问题反馈单处理");
		map1.put("menuUrl", "deliver/after/toFrames?module=sh");
		map1.put("state", "处理中");
		map1.put("tableName", "DRP_PROBLEM_FEEDBACK");
		map1.put("qxType", "4");//经销商分管
		param.put("BS0011004", map1);
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0404");
		map1.put("menuName", "返修单审核");
		map1.put("menuUrl", "deliver/rework/toFrames?module=sh");
		map1.put("state", "待审核");
		map1.put("tableName", "ERP_SALE_ORDER");
		map1.put("qxType", "2");
		param.put("BS0012202", map1);
		
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0502");
		map1.put("menuName", "账户充值审核");
		map1.put("menuUrl", "/erp/depositinfo/toFrame?module=sh");
		map1.put("state", "提交");
		map1.put("tableName", "ERP_DEPOSIT_INFO");
		map1.put("qxType", "2");
		param.put("BS0011302", map1);
		
		
		map1 = new HashMap<String, String>();
		map1.put("menuId", "BS0602");
		map1.put("menuName", "授信额度审核");
		map1.put("menuUrl", "erp/creditreq/toFrame?module=sh");
		map1.put("state", "提交");
		map1.put("tableName", "ERP_CREDIT_REQ");
		map1.put("qxType", "2");
		param.put("BS0011702", map1);
		
		System.out.println(JSONObject.toJSON(param));
	 */
	
	public static final JSONObject FIRST_TO_DO_MENUINFO = JSONObject.parseObject(Properties.getString("FIRST_TO_DO_MENUINFO"));


	/** 不查看价格的设计师角色ID.NOPRICE_XTJSID={"XT":"XT005","DR1":"DR002","DR2":"DR004"} */
	public static final JSONObject NOPRICE_XTJSID = JSONObject.parseObject(Properties.getString("NOPRICE_XTJSID"));
	/** 不查看价格的设计师角色ID.总部标识. */
	public static final String XT = Properties.getString("XT");
	/** 不查看价格的设计师角色ID.一级经销商标识. */
	public static final String FSTDR = Properties.getString("FSTDR");
	/** 不查看价格的设计师角色ID.二级经销商标识. */
	public static final String SECDR = Properties.getString("SECDR");

}
