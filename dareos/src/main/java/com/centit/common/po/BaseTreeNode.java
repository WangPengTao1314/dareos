package com.centit.common.po;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: BaseTreeNode 
 * @Description: 树形结构基类
 * @author: zhu_hj
 * @date: 2018年5月22日 下午2:01:10
 */
public class BaseTreeNode implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String pid;
	
	private String name;
	
	private String isParent;//是否是父节点
	
	private List<BaseTreeNode> children;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BaseTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<BaseTreeNode> children) {
		this.children = children;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	
	
}
