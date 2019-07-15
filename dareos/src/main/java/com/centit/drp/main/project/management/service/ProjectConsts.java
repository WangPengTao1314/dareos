package com.centit.drp.main.project.management.service;

/**
 *  工程项目业务常量
 * @author wang_pt
 *
 */
public interface ProjectConsts {
	
	/**
	 *  项目节点维护
	 */
	
	public static final String  PROJECT_SAMPLE_INFOR = "样品信息";//样品信息
	public static final String PROJECT_MODEL_HOUSES ="样板房";//样板房
	public static final String PROJECT_CONT_SIG ="合同签订";//合同签订
	public static final String PROJECT_HANDOVER ="项目交接";//项目交接
	public static final String PROJECT_MATER_PURC ="材料申购";//材料申购
	public static final String PROJECT_SIG_CONT ="安装合同签订";//安装合同签订
	public static final String PROJECT_BULK_ORDER ="大货下单";//大货下单
	public static final String PROJECT_DELIVER_GOODS ="发货";//发货
	public static final String PROJECT_VISA ="签证";//签证
	public static final String PROJECT_REPLEN ="补货";//补货
	public static final String PROJECT_REPAIR ="维修";//维修
	
	//指令状态
	public static final String PROJECT_WAIT_DOWNSEND = "待下发";//待下发
	//账套id
	public static final String RING_FLOUR_AGE = "'113'";//盛世年轮
	public static final String WOODEN_DOORS = "'116'";//木门
	public static final String WARDROBE_CUPBOARD = "'107','10801'";//衣橱柜
	//发货指令
	
	public static final String SENDORDER_DRAFT="草稿";
	public static final String SENDORDER_WAITCHECK="待审核";
	public static final String SENDORDER_CHECKPASS="已通过";
	public static final String SENDORDER_REJECT="已拒绝";//
	public static final String SENDORDER_OVWER="已完成";
	
}
