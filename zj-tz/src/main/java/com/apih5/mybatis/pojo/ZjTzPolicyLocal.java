package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzPolicyLocal extends BasePojo {
    private String policyId;

    private String title;

    private String symbolNo;

    private String projectId;

    private String projectName;

    private String projectShortName;

    private String provinceId;

    private String releaseRankId;

    private String releaseRankName;

    private Date sysDate;

    private String departmentName;

    private Date releaseDate;

    private Date effectDate;

    private String registerUser;

    private String effectiveId;

    private String effectiveName;

    private String report;

    private int pushInfoReply = 0;

    private int pushInfoAll = 0;

    private String pushInfo;

    private String homeShow;

    private Date homeShowStartDate;

    private Date homeShowEndDate;

    private String releaseId;

    private String releaseName;

    private String statusId;

    private String statusName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private List<ZjTzPolicyLocalReply> zjTzPolicyLocalReplyList;

    private String projectIdSql;

    private String ext1SeeFlag;
    
    private String typeName;
    
    private String provinceName;
    
    
    public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getPolicyId() {
        return policyId == null ? "" : policyId.trim();
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId == null ? null : policyId.trim();
    }

    public String getTitle() {
        return title == null ? "" : title.trim();
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSymbolNo() {
        return symbolNo == null ? "" : symbolNo.trim();
    }

    public void setSymbolNo(String symbolNo) {
        this.symbolNo = symbolNo == null ? null : symbolNo.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProjectShortName() {
        return projectShortName == null ? "" : projectShortName.trim();
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName == null ? null : projectShortName.trim();
    }

    public String getProvinceId() {
        return provinceId == null ? "" : provinceId.trim();
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId == null ? null : provinceId.trim();
    }

    public String getReleaseRankId() {
        return releaseRankId == null ? "" : releaseRankId.trim();
    }

    public void setReleaseRankId(String releaseRankId) {
        this.releaseRankId = releaseRankId == null ? null : releaseRankId.trim();
    }

    public String getReleaseRankName() {
        return releaseRankName == null ? "" : releaseRankName.trim();
    }

    public void setReleaseRankName(String releaseRankName) {
        this.releaseRankName = releaseRankName == null ? null : releaseRankName.trim();
    }

    public Date getSysDate() {
        return sysDate;
    }

    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName.trim();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public String getRegisterUser() {
        return registerUser == null ? "" : registerUser.trim();
    }

    public void setRegisterUser(String registerUser) {
        this.registerUser = registerUser == null ? null : registerUser.trim();
    }

    public String getEffectiveId() {
        return effectiveId == null ? "" : effectiveId.trim();
    }

    public void setEffectiveId(String effectiveId) {
        this.effectiveId = effectiveId == null ? null : effectiveId.trim();
    }

    public String getEffectiveName() {
        return effectiveName == null ? "" : effectiveName.trim();
    }

    public void setEffectiveName(String effectiveName) {
        this.effectiveName = effectiveName == null ? null : effectiveName.trim();
    }

    public String getReport() {
        return report == null ? "" : report.trim();
    }

    public void setReport(String report) {
        this.report = report == null ? null : report.trim();
    }

    public Integer getPushInfoReply() {
        return pushInfoReply;
    }

    public void setPushInfoReply(Integer pushInfoReply) {
        this.pushInfoReply = pushInfoReply;
    }

    public Integer getPushInfoAll() {
        return pushInfoAll;
    }

    public void setPushInfoAll(Integer pushInfoAll) {
        this.pushInfoAll = pushInfoAll;
    }

    public String getPushInfo() {
        return pushInfo == null ? "" : pushInfo.trim();
    }

    public void setPushInfo(String pushInfo) {
        this.pushInfo = pushInfo == null ? null : pushInfo.trim();
    }

    public String getHomeShow() {
        return homeShow == null ? "" : homeShow.trim();
    }

    public void setHomeShow(String homeShow) {
        this.homeShow = homeShow == null ? null : homeShow.trim();
    }

    public Date getHomeShowStartDate() {
        return homeShowStartDate;
    }

    public void setHomeShowStartDate(Date homeShowStartDate) {
        this.homeShowStartDate = homeShowStartDate;
    }

    public Date getHomeShowEndDate() {
        return homeShowEndDate;
    }

    public void setHomeShowEndDate(Date homeShowEndDate) {
        this.homeShowEndDate = homeShowEndDate;
    }

    public String getReleaseId() {
        return releaseId == null ? "" : releaseId.trim();
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId == null ? null : releaseId.trim();
    }

    public String getReleaseName() {
        return releaseName == null ? "" : releaseName.trim();
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName == null ? null : releaseName.trim();
    }

    public String getStatusId() {
        return statusId == null ? "" : statusId.trim();
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId == null ? null : statusId.trim();
    }

    public String getStatusName() {
        return statusName == null ? "" : statusName.trim();
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public String getDelFlag() {
        return delFlag == null ? "" : delFlag.trim();
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser == null ? "" : createUser.trim();
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateUserName() {
        return createUserName == null ? "" : createUserName.trim();
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser == null ? "" : modifyUser.trim();
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getModifyUserName() {
        return modifyUserName == null ? "" : modifyUserName.trim();
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
    }

    public List<ZjTzFile> getZjTzFileList() {
        return zjTzFileList;
    }

    public void setZjTzFileList(List<ZjTzFile> zjTzFileList) {
        this.zjTzFileList = zjTzFileList;
    }

    public List<ZjTzPolicyLocalReply> getZjTzPolicyLocalReplyList() {
        return zjTzPolicyLocalReplyList;
    }

    public void setZjTzPolicyLocalReplyList(List<ZjTzPolicyLocalReply> zjTzPolicyLocalReplyList) {
        this.zjTzPolicyLocalReplyList = zjTzPolicyLocalReplyList;
    }

    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }

    public String getExt1SeeFlag() {
        return ext1SeeFlag == null ? "" : ext1SeeFlag.trim();
    }

    public void setExt1SeeFlag(String ext1SeeFlag) {
        this.ext1SeeFlag = ext1SeeFlag == null ? null : ext1SeeFlag.trim();
    }

}