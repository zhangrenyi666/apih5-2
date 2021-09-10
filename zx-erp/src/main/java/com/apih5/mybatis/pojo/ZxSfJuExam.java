package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfJuExam extends BasePojo {
    // 主键
    private String zxSfJuExamId;

    // 单位ID
    private String examID;

    // 总分
    private BigDecimal score;

    // 被考核单位领导
    private String unitManage;

    // 填报人
    private String creator;

    // 单位名称
    private String examName;

    // 机构ID
    private String orgID;

    // 考核日期
    private Date examDate;

    // 机构名称
    private String orgName;

    // 编辑时间
    private Date editTime;

    // 状态
    private String auditStatus;

    // 公司
    private String isCompany;

    // 集团
    private String isGroup;

    // 所属公司ID
    private String companyId;

    // 所属公司名称
    private String companyName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    // 附件
    private List<ZxErpFile> fileList;
    
    //明细
    private List<ZxSfJuExamItem> zxSfJuExamItemList;

    public List<ZxSfJuExamItem> getZxSfJuExamItemList() {
		return zxSfJuExamItemList;
	}

	public void setZxSfJuExamItemList(List<ZxSfJuExamItem> zxSfJuExamItemList) {
		this.zxSfJuExamItemList = zxSfJuExamItemList;
	}

	public List<ZxErpFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ZxErpFile> fileList) {
		this.fileList = fileList;
	}

	public String getZxSfJuExamId() {
        return zxSfJuExamId == null ? "" : zxSfJuExamId.trim();
    }

    public void setZxSfJuExamId(String zxSfJuExamId) {
        this.zxSfJuExamId = zxSfJuExamId == null ? null : zxSfJuExamId.trim();
    }

    public String getExamID() {
        return examID == null ? "" : examID.trim();
    }

    public void setExamID(String examID) {
        this.examID = examID == null ? null : examID.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getUnitManage() {
        return unitManage == null ? "" : unitManage.trim();
    }

    public void setUnitManage(String unitManage) {
        this.unitManage = unitManage == null ? null : unitManage.trim();
    }

    public String getCreator() {
        return creator == null ? "" : creator.trim();
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getExamName() {
        return examName == null ? "" : examName.trim();
    }

    public void setExamName(String examName) {
        this.examName = examName == null ? null : examName.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getIsCompany() {
        return isCompany == null ? "" : isCompany.trim();
    }

    public void setIsCompany(String isCompany) {
        this.isCompany = isCompany == null ? null : isCompany.trim();
    }

    public String getIsGroup() {
        return isGroup == null ? "" : isGroup.trim();
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup == null ? null : isGroup.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
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
