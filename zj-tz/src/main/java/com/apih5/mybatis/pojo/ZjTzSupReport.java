package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import cn.hutool.json.JSONObject;
import com.apih5.framework.entity.BasePojo;

public class ZjTzSupReport extends BasePojo {
    private String reportId;

    private String otherId;

    private String reportUserKey;

    private String reportUserName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<JSONObject> personList;

    private String comprehensiveSupId;

    public String getReportId() {
        return reportId == null ? "" : reportId.trim();
    }

    public void setReportId(String reportId) {
        this.reportId = reportId == null ? null : reportId.trim();
    }

    public String getOtherId() {
        return otherId == null ? "" : otherId.trim();
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId == null ? null : otherId.trim();
    }

    public String getReportUserKey() {
        return reportUserKey == null ? "" : reportUserKey.trim();
    }

    public void setReportUserKey(String reportUserKey) {
        this.reportUserKey = reportUserKey == null ? null : reportUserKey.trim();
    }

    public String getReportUserName() {
        return reportUserName == null ? "" : reportUserName.trim();
    }

    public void setReportUserName(String reportUserName) {
        this.reportUserName = reportUserName == null ? null : reportUserName.trim();
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

    public List<JSONObject> getPersonList() {
        return personList;
    }

    public void setPersonList(List<JSONObject> personList) {
        this.personList = personList;
    }

    public String getComprehensiveSupId() {
        return comprehensiveSupId == null ? "" : comprehensiveSupId.trim();
    }

    public void setComprehensiveSupId(String comprehensiveSupId) {
        this.comprehensiveSupId = comprehensiveSupId == null ? null : comprehensiveSupId.trim();
    }

}

