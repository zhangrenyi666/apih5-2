package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzRules extends BasePojo {
    private String rulesId;

    private String title;

    private String symbolNo;

    private String releaseRankId;

    private String releaseRankName;

    private String departmentName;

    private Date releaseDate;

    private String abolishSymbolNo;

    private String effectiveId;

    private String effectiveName;

    private String registeredUser;

    private String remarks;

    private String homeShow;

    private Date homeShowTime;

    private Date homeShowStartDate;

    private Date homeShowEndDate;

    private String releaseId;

    private String releaseName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private List<Date> homeShowStartDateList;

    private Date homeShowStartDateSearch;

    private Date homeShowEndDateSearch;
    
    private ZjTzImportDoc zjTzImportDoc;
    
    private String typeName;
    
    
    
    public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public ZjTzImportDoc getZjTzImportDoc() {
		return zjTzImportDoc;
	}

	public void setZjTzImportDoc(ZjTzImportDoc zjTzImportDoc) {
		this.zjTzImportDoc = zjTzImportDoc;
	}

	public String getRulesId() {
        return rulesId == null ? "" : rulesId.trim();
    }

    public void setRulesId(String rulesId) {
        this.rulesId = rulesId == null ? null : rulesId.trim();
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

    public String getAbolishSymbolNo() {
        return abolishSymbolNo == null ? "" : abolishSymbolNo.trim();
    }

    public void setAbolishSymbolNo(String abolishSymbolNo) {
        this.abolishSymbolNo = abolishSymbolNo == null ? null : abolishSymbolNo.trim();
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

    public String getRegisteredUser() {
        return registeredUser == null ? "" : registeredUser.trim();
    }

    public void setRegisteredUser(String registeredUser) {
        this.registeredUser = registeredUser == null ? null : registeredUser.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getHomeShow() {
        return homeShow == null ? "" : homeShow.trim();
    }

    public void setHomeShow(String homeShow) {
        this.homeShow = homeShow == null ? null : homeShow.trim();
    }

    public Date getHomeShowTime() {
        return homeShowTime;
    }

    public void setHomeShowTime(Date homeShowTime) {
        this.homeShowTime = homeShowTime;
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

    public List<Date> getHomeShowStartDateList() {
        return homeShowStartDateList;
    }

    public void setHomeShowStartDateList(List<Date> homeShowStartDateList) {
        this.homeShowStartDateList = homeShowStartDateList;
    }

	public Date getHomeShowStartDateSearch() {
		return homeShowStartDateSearch;
	}

	public void setHomeShowStartDateSearch(Date homeShowStartDateSearch) {
		this.homeShowStartDateSearch = homeShowStartDateSearch;
	}

	public Date getHomeShowEndDateSearch() {
		return homeShowEndDateSearch;
	}

	public void setHomeShowEndDateSearch(Date homeShowEndDateSearch) {
		this.homeShowEndDateSearch = homeShowEndDateSearch;
	}

}