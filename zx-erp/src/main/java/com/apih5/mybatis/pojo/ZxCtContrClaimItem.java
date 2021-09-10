package com.apih5.mybatis.pojo;

import com.apih5.framework.entity.BasePojo;

public class ZxCtContrClaimItem extends BasePojo {
	// 主键
	private String id;

	// 索赔主表ID
	private String claimID;

	// 索赔明细编号
	private String claimDetailNo;

	// 索赔明细名称
	private String claimDetailName;

	// 索赔明细内容
	private String claimDetailContent;

	// 备注
	private String remarks;

	// 排序
	private int sort = 0;

	public String getId() {
		return id == null ? "" : id.trim();
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getClaimID() {
		return claimID == null ? "" : claimID.trim();
	}

	public void setClaimID(String claimID) {
		this.claimID = claimID == null ? null : claimID.trim();
	}

	public String getClaimDetailNo() {
		return claimDetailNo == null ? "" : claimDetailNo.trim();
	}

	public void setClaimDetailNo(String claimDetailNo) {
		this.claimDetailNo = claimDetailNo == null ? null : claimDetailNo.trim();
	}

	public String getClaimDetailName() {
		return claimDetailName == null ? "" : claimDetailName.trim();
	}

	public void setClaimDetailName(String claimDetailName) {
		this.claimDetailName = claimDetailName == null ? null : claimDetailName.trim();
	}

	public String getClaimDetailContent() {
		return claimDetailContent == null ? "" : claimDetailContent.trim();
	}

	public void setClaimDetailContent(String claimDetailContent) {
		this.claimDetailContent = claimDetailContent == null ? null : claimDetailContent.trim();
	}

	public String getRemarks() {
		return remarks == null ? "" : remarks.trim();
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
