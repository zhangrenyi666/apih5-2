package com.apih5.mybatis.pojo;

import java.util.Date;

import com.apih5.framework.entity.BasePojo;

public class ZxCmFilterOrg extends BasePojo {
	// 主键
	private String filterOrgId;

	// 项目ID
	private String orgID;

	// 项目名称
	private String orgName;

	// 业务类型
	private String businessType;

	// 最后编辑时间
	private Date editTime;

	// 备注
	private String remark;

	// 排序
	private int sort = 0;

	public String getFilterOrgId() {
		return filterOrgId == null ? "" : filterOrgId.trim();
	}

	public void setFilterOrgId(String filterOrgId) {
		this.filterOrgId = filterOrgId == null ? null : filterOrgId.trim();
	}

	public String getOrgID() {
		return orgID == null ? "" : orgID.trim();
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID == null ? null : orgID.trim();
	}

	public String getOrgName() {
		return orgName == null ? "" : orgName.trim();
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName == null ? null : orgName.trim();
	}

	public String getBusinessType() {
		return businessType == null ? "" : businessType.trim();
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType == null ? null : businessType.trim();
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getRemark() {
		return remark == null ? "" : remark.trim();
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
