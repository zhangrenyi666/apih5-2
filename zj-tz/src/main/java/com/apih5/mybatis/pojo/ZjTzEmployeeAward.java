package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjTzEmployeeAward extends BasePojo {
    private String awardId;

    private String employeeInfoId;

    private Date awardTime;

    private String awardRank;

    private String awardName;

    private String awardReason;

    private String awardAppUnit;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getAwardId() {
        return awardId == null ? "" : awardId.trim();
    }

    public void setAwardId(String awardId) {
        this.awardId = awardId == null ? null : awardId.trim();
    }

    public String getEmployeeInfoId() {
        return employeeInfoId == null ? "" : employeeInfoId.trim();
    }

    public void setEmployeeInfoId(String employeeInfoId) {
        this.employeeInfoId = employeeInfoId == null ? null : employeeInfoId.trim();
    }

    public Date getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
    }

    public String getAwardRank() {
        return awardRank == null ? "" : awardRank.trim();
    }

    public void setAwardRank(String awardRank) {
        this.awardRank = awardRank == null ? null : awardRank.trim();
    }

    public String getAwardName() {
        return awardName == null ? "" : awardName.trim();
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName == null ? null : awardName.trim();
    }

    public String getAwardReason() {
        return awardReason == null ? "" : awardReason.trim();
    }

    public void setAwardReason(String awardReason) {
        this.awardReason = awardReason == null ? null : awardReason.trim();
    }

    public String getAwardAppUnit() {
        return awardAppUnit == null ? "" : awardAppUnit.trim();
    }

    public void setAwardAppUnit(String awardAppUnit) {
        this.awardAppUnit = awardAppUnit == null ? null : awardAppUnit.trim();
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

