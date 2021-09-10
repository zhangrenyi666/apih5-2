package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjTzProShareholderInfo extends BasePojo {
    private String shareholderInfoId;

    private String projectId;

    private String shareholderName;

    private BigDecimal proportion;

    private BigDecimal contributionAmount;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private String executivePersonnelId;

    private String shareholderType;

    private BigDecimal constructionProportion;

    public String getShareholderInfoId() {
        return shareholderInfoId == null ? "" : shareholderInfoId.trim();
    }

    public void setShareholderInfoId(String shareholderInfoId) {
        this.shareholderInfoId = shareholderInfoId == null ? null : shareholderInfoId.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getShareholderName() {
        return shareholderName == null ? "" : shareholderName.trim();
    }

    public void setShareholderName(String shareholderName) {
        this.shareholderName = shareholderName == null ? null : shareholderName.trim();
    }

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    public BigDecimal getContributionAmount() {
        return contributionAmount;
    }

    public void setContributionAmount(BigDecimal contributionAmount) {
        this.contributionAmount = contributionAmount;
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
    
    public String getExecutivePersonnelId() {
        return executivePersonnelId == null ? "" : executivePersonnelId.trim();
    }

    public void setExecutivePersonnelId(String executivePersonnelId) {
        this.executivePersonnelId = executivePersonnelId == null ? null : executivePersonnelId.trim();
    }
    
    public String getShareholderType() {
        return shareholderType == null ? "" : shareholderType.trim();
    }

    public void setShareholderType(String shareholderType) {
        this.shareholderType = shareholderType == null ? null : shareholderType.trim();
    }

    public BigDecimal getConstructionProportion() {
        return constructionProportion;
    }

    public void setConstructionProportion(BigDecimal constructionProportion) {
        this.constructionProportion = constructionProportion;
    }

}

