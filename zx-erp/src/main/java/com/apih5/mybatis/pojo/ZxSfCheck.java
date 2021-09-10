package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfCheck extends BasePojo {
    // 主键
    private String zxSfCheckId;

    // 机构名称
    private String orgName;

    // 机构ID
    private String orgID;

    // 项目名称
    private String projName;

    // 项目ID
    private String projID;

    // 检查日期
    private Date checkDate;

    // 检查内容
    private String checkContext;

    // 检查组长
    private String checkGroup;

    // 检查成员
    private String checkEmployee;

    // 编辑时间
    private Date editTime;

    // isGroup
    private String isGroup;

    // isCompany
    private String isCompany;

    // 检查类型
    private String checkType;

    // 报表ID
    private String reportID;

    // 状态
    private String status;

    // 是否下达
    private String isSend;

    // 是否上报
    private String isReported;

    // sendID
    private String sendID;

    // 复查状态
    private String checkAgainStatus;

    // 所属公司ID
    private String companyId;

    // 所属公司Name
    private String companyName;

    // 备注
    private String remarks;

    //存在安全隐患数量
    private long riskNum;
    //已整改数量
    private long zGaiNum;

    // 排序
    private int sort=0;
    
    // 附件
    private List<ZxErpFile> fileList;
    
   // 明细List
    private List<ZxSfCheckItem> zxSfCheckItemList;

    // 项目总数
    private int orgNum=0;

    // 已检查项目数
    private int checkNum=0;

    // 未检查项目数
    private int noCheckNum=0;

    // 检查覆盖率
    private BigDecimal checkFGL;

    // 开始日期
    private Date startDate;

    // 结束日期
    private Date endDate;

    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public long getRiskNum() {
        return riskNum;
    }

    public void setRiskNum(long riskNum) {
        this.riskNum = riskNum;
    }

    public long getzGaiNum() {
        return zGaiNum;
    }

    public void setzGaiNum(long zGaiNum) {
        this.zGaiNum = zGaiNum;
    }

    public int getOrgNum() {
        return orgNum;
    }

    public void setOrgNum(int orgNum) {
        this.orgNum = orgNum;
    }

    public int getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }

    public int getNoCheckNum() {
        return noCheckNum;
    }

    public void setNoCheckNum(int noCheckNum) {
        this.noCheckNum = noCheckNum;
    }

    public BigDecimal getCheckFGL() {
        return checkFGL;
    }

    public void setCheckFGL(BigDecimal checkFGL) {
        this.checkFGL = checkFGL;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }





    public List<ZxErpFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ZxErpFile> fileList) {
		this.fileList = fileList;
	}

	public List<ZxSfCheckItem> getZxSfCheckItemList() {
		return zxSfCheckItemList;
	}

	public void setZxSfCheckItemList(List<ZxSfCheckItem> zxSfCheckItemList) {
		this.zxSfCheckItemList = zxSfCheckItemList;
	}



    public String getZxSfCheckId() {
        return zxSfCheckId == null ? "" : zxSfCheckId.trim();
    }

    public void setZxSfCheckId(String zxSfCheckId) {
        this.zxSfCheckId = zxSfCheckId == null ? null : zxSfCheckId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getProjName() {
        return projName == null ? "" : projName.trim();
    }

    public void setProjName(String projName) {
        this.projName = projName == null ? null : projName.trim();
    }

    public String getProjID() {
        return projID == null ? "" : projID.trim();
    }

    public void setProjID(String projID) {
        this.projID = projID == null ? null : projID.trim();
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckContext() {
        return checkContext == null ? "" : checkContext.trim();
    }

    public void setCheckContext(String checkContext) {
        this.checkContext = checkContext == null ? null : checkContext.trim();
    }

    public String getCheckGroup() {
        return checkGroup == null ? "" : checkGroup.trim();
    }

    public void setCheckGroup(String checkGroup) {
        this.checkGroup = checkGroup == null ? null : checkGroup.trim();
    }

    public String getCheckEmployee() {
        return checkEmployee == null ? "" : checkEmployee.trim();
    }

    public void setCheckEmployee(String checkEmployee) {
        this.checkEmployee = checkEmployee == null ? null : checkEmployee.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getIsGroup() {
        return isGroup == null ? "" : isGroup.trim();
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup == null ? null : isGroup.trim();
    }

    public String getIsCompany() {
        return isCompany == null ? "" : isCompany.trim();
    }

    public void setIsCompany(String isCompany) {
        this.isCompany = isCompany == null ? null : isCompany.trim();
    }

    public String getCheckType() {
        return checkType == null ? "" : checkType.trim();
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType == null ? null : checkType.trim();
    }

    public String getReportID() {
        return reportID == null ? "" : reportID.trim();
    }

    public void setReportID(String reportID) {
        this.reportID = reportID == null ? null : reportID.trim();
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getIsSend() {
        return isSend == null ? "" : isSend.trim();
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend == null ? null : isSend.trim();
    }

    public String getIsReported() {
        return isReported == null ? "" : isReported.trim();
    }

    public void setIsReported(String isReported) {
        this.isReported = isReported == null ? null : isReported.trim();
    }

    public String getSendID() {
        return sendID == null ? "" : sendID.trim();
    }

    public void setSendID(String sendID) {
        this.sendID = sendID == null ? null : sendID.trim();
    }

    public String getCheckAgainStatus() {
        return checkAgainStatus == null ? "" : checkAgainStatus.trim();
    }

    public void setCheckAgainStatus(String checkAgainStatus) {
        this.checkAgainStatus = checkAgainStatus == null ? null : checkAgainStatus.trim();
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
