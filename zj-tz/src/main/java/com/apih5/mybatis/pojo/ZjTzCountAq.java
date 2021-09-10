package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjTzCountAq extends BasePojo {
    private String countAqId;

    private String managerUnit;

    private String projectId;

    private String projectName;

    private String proUnitName;

    private Integer totalNum;

    private Integer finishNum;

    private Integer unfinishNum;

    private String finishRate;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getCountAqId() {
        return countAqId == null ? "" : countAqId.trim();
    }

    public void setCountAqId(String countAqId) {
        this.countAqId = countAqId == null ? null : countAqId.trim();
    }

    public String getManagerUnit() {
        return managerUnit == null ? "" : managerUnit.trim();
    }

    public void setManagerUnit(String managerUnit) {
        this.managerUnit = managerUnit == null ? null : managerUnit.trim();
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

    public String getProUnitName() {
        return proUnitName == null ? "" : proUnitName.trim();
    }

    public void setProUnitName(String proUnitName) {
        this.proUnitName = proUnitName == null ? null : proUnitName.trim();
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public Integer getUnfinishNum() {
        return unfinishNum;
    }

    public void setUnfinishNum(Integer unfinishNum) {
        this.unfinishNum = unfinishNum;
    }

    public String getFinishRate() {
        return finishRate == null ? "" : finishRate.trim();
    }

    public void setFinishRate(String finishRate) {
        this.finishRate = finishRate == null ? null : finishRate.trim();
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

