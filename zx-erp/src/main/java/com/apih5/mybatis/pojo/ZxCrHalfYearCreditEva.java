package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCrHalfYearCreditEva extends BasePojo {
    // 主键
    private String zxCrHalfYearCreditEvaId;

    // 机构ID
    private String orgID;

    // 机构名称
    private String orgName;

    // 评价期次
    private Date period;

    // 评价日期
    private Date dateTime;

    // 最后编辑时间
    private String editTime;

    // 所属公司ID
    private String comID;

    // 所属公司名称
    private String comName;

    // 所属公司排序
    private String comOrders;

    // 审核状态
    private String auditStatus;

    // 明细
    private String items;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    // 所属公司名称
    private String companyName; 

	// 所属公司ID
    private String companyId;

    private String projectId;
    
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

    public String getZxCrHalfYearCreditEvaId() {
        return zxCrHalfYearCreditEvaId == null ? "" : zxCrHalfYearCreditEvaId.trim();
    }

    public void setZxCrHalfYearCreditEvaId(String zxCrHalfYearCreditEvaId) {
        this.zxCrHalfYearCreditEvaId = zxCrHalfYearCreditEvaId == null ? null : zxCrHalfYearCreditEvaId.trim();
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

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getEditTime() {
        return editTime == null ? "" : editTime.trim();
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime == null ? null : editTime.trim();
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getItems() {
        return items == null ? "" : items.trim();
    }

    public void setItems(String items) {
        this.items = items == null ? null : items.trim();
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
