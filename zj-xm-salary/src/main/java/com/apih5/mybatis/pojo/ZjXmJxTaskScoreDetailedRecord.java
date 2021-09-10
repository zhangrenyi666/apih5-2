package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmJxTaskScoreDetailedRecord extends BasePojo {
    private String recordId;

    private String detailedId;

    private String auditeeKey;

    private String auditeeName;

    private String auditeeDeptId;

    private String auditeeDeptName;

    private String auditeeProId;

    private String auditeeProName;

    private String hrUserKey;

    private String hrName;

    private String appealStatus;

    private String appealOpinion;

    private String hrOpinion;

    private String remarks;

    private Integer sort;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getRecordId() {
        return recordId == null ? "" : recordId.trim();
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getDetailedId() {
        return detailedId == null ? "" : detailedId.trim();
    }

    public void setDetailedId(String detailedId) {
        this.detailedId = detailedId == null ? null : detailedId.trim();
    }

    public String getAuditeeKey() {
        return auditeeKey == null ? "" : auditeeKey.trim();
    }

    public void setAuditeeKey(String auditeeKey) {
        this.auditeeKey = auditeeKey == null ? null : auditeeKey.trim();
    }

    public String getAuditeeName() {
        return auditeeName == null ? "" : auditeeName.trim();
    }

    public void setAuditeeName(String auditeeName) {
        this.auditeeName = auditeeName == null ? null : auditeeName.trim();
    }

    public String getAuditeeDeptId() {
        return auditeeDeptId == null ? "" : auditeeDeptId.trim();
    }

    public void setAuditeeDeptId(String auditeeDeptId) {
        this.auditeeDeptId = auditeeDeptId == null ? null : auditeeDeptId.trim();
    }

    public String getAuditeeDeptName() {
        return auditeeDeptName == null ? "" : auditeeDeptName.trim();
    }

    public void setAuditeeDeptName(String auditeeDeptName) {
        this.auditeeDeptName = auditeeDeptName == null ? null : auditeeDeptName.trim();
    }

    public String getAuditeeProId() {
        return auditeeProId == null ? "" : auditeeProId.trim();
    }

    public void setAuditeeProId(String auditeeProId) {
        this.auditeeProId = auditeeProId == null ? null : auditeeProId.trim();
    }

    public String getAuditeeProName() {
        return auditeeProName == null ? "" : auditeeProName.trim();
    }

    public void setAuditeeProName(String auditeeProName) {
        this.auditeeProName = auditeeProName == null ? null : auditeeProName.trim();
    }

    public String getHrUserKey() {
        return hrUserKey == null ? "" : hrUserKey.trim();
    }

    public void setHrUserKey(String hrUserKey) {
        this.hrUserKey = hrUserKey == null ? null : hrUserKey.trim();
    }

    public String getHrName() {
        return hrName == null ? "" : hrName.trim();
    }

    public void setHrName(String hrName) {
        this.hrName = hrName == null ? null : hrName.trim();
    }

    public String getAppealStatus() {
        return appealStatus == null ? "" : appealStatus.trim();
    }

    public void setAppealStatus(String appealStatus) {
        this.appealStatus = appealStatus == null ? null : appealStatus.trim();
    }

    public String getAppealOpinion() {
        return appealOpinion == null ? "" : appealOpinion.trim();
    }

    public void setAppealOpinion(String appealOpinion) {
        this.appealOpinion = appealOpinion == null ? null : appealOpinion.trim();
    }

    public String getHrOpinion() {
        return hrOpinion == null ? "" : hrOpinion.trim();
    }

    public void setHrOpinion(String hrOpinion) {
        this.hrOpinion = hrOpinion == null ? null : hrOpinion.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

}

