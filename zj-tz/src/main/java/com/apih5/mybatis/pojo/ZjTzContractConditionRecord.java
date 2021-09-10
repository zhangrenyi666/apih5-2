package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzContractConditionRecord extends BasePojo {
    private String contractConditionRecordId;

    private String sizeControlRecordId;

    private String sizeControlId;

    private String projectId;

    private Date registerDate;

    private String registrant;

    private String investId;

    private String investName;

    private BigDecimal juShare;

    private String juId;

    private String juName;

    private String zcbId;

    private String zcbName;

    private BigDecimal zcbShare;

    private String ext1;

    private String ext2;

    private String ext3;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    public String getContractConditionRecordId() {
        return contractConditionRecordId == null ? "" : contractConditionRecordId.trim();
    }

    public void setContractConditionRecordId(String contractConditionRecordId) {
        this.contractConditionRecordId = contractConditionRecordId == null ? null : contractConditionRecordId.trim();
    }

    public String getSizeControlRecordId() {
        return sizeControlRecordId == null ? "" : sizeControlRecordId.trim();
    }

    public void setSizeControlRecordId(String sizeControlRecordId) {
        this.sizeControlRecordId = sizeControlRecordId == null ? null : sizeControlRecordId.trim();
    }

    public String getSizeControlId() {
        return sizeControlId == null ? "" : sizeControlId.trim();
    }

    public void setSizeControlId(String sizeControlId) {
        this.sizeControlId = sizeControlId == null ? null : sizeControlId.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegistrant() {
        return registrant == null ? "" : registrant.trim();
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant == null ? null : registrant.trim();
    }

    public String getInvestId() {
        return investId == null ? "" : investId.trim();
    }

    public void setInvestId(String investId) {
        this.investId = investId == null ? null : investId.trim();
    }

    public String getInvestName() {
        return investName == null ? "" : investName.trim();
    }

    public void setInvestName(String investName) {
        this.investName = investName == null ? null : investName.trim();
    }

    public BigDecimal getJuShare() {
        return juShare;
    }

    public void setJuShare(BigDecimal juShare) {
        this.juShare = juShare;
    }

    public String getJuId() {
        return juId == null ? "" : juId.trim();
    }

    public void setJuId(String juId) {
        this.juId = juId == null ? null : juId.trim();
    }

    public String getJuName() {
        return juName == null ? "" : juName.trim();
    }

    public void setJuName(String juName) {
        this.juName = juName == null ? null : juName.trim();
    }

    public String getZcbId() {
        return zcbId == null ? "" : zcbId.trim();
    }

    public void setZcbId(String zcbId) {
        this.zcbId = zcbId == null ? null : zcbId.trim();
    }

    public String getZcbName() {
        return zcbName == null ? "" : zcbName.trim();
    }

    public void setZcbName(String zcbName) {
        this.zcbName = zcbName == null ? null : zcbName.trim();
    }

    public BigDecimal getZcbShare() {
        return zcbShare;
    }

    public void setZcbShare(BigDecimal zcbShare) {
        this.zcbShare = zcbShare;
    }

    public String getExt1() {
        return ext1 == null ? "" : ext1.trim();
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    public String getExt2() {
        return ext2 == null ? "" : ext2.trim();
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    public String getExt3() {
        return ext3 == null ? "" : ext3.trim();
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
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

}

