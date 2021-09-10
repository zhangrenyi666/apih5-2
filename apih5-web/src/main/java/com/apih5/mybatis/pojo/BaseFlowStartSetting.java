package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

import cn.hutool.json.JSONArray;

public class BaseFlowStartSetting extends BasePojo {
    private String startSettingId;

    private String apih5FlowId;

    private String apih5FlowName;

    private String apih5WorkId;

    private String apih5NodeId;

    private String apih5NodeName;

    private String label;

    private String value;

    private String remarks;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private JSONArray nextNodes;

    public String getStartSettingId() {
        return startSettingId == null ? "" : startSettingId.trim();
    }

    public void setStartSettingId(String startSettingId) {
        this.startSettingId = startSettingId == null ? null : startSettingId.trim();
    }

    public String getApih5FlowId() {
        return apih5FlowId == null ? "" : apih5FlowId.trim();
    }

    public void setApih5FlowId(String apih5FlowId) {
        this.apih5FlowId = apih5FlowId == null ? null : apih5FlowId.trim();
    }

    public String getApih5FlowName() {
        return apih5FlowName == null ? "" : apih5FlowName.trim();
    }

    public void setApih5FlowName(String apih5FlowName) {
        this.apih5FlowName = apih5FlowName == null ? null : apih5FlowName.trim();
    }

    public String getApih5WorkId() {
        return apih5WorkId == null ? "" : apih5WorkId.trim();
    }

    public void setApih5WorkId(String apih5WorkId) {
        this.apih5WorkId = apih5WorkId == null ? null : apih5WorkId.trim();
    }

    public String getApih5NodeId() {
        return apih5NodeId == null ? "" : apih5NodeId.trim();
    }

    public void setApih5NodeId(String apih5NodeId) {
        this.apih5NodeId = apih5NodeId == null ? null : apih5NodeId.trim();
    }

    public String getApih5NodeName() {
        return apih5NodeName == null ? "" : apih5NodeName.trim();
    }

    public void setApih5NodeName(String apih5NodeName) {
        this.apih5NodeName = apih5NodeName == null ? null : apih5NodeName.trim();
    }

    public String getLabel() {
        return label == null ? "" : label.trim();
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getValue() {
        return value == null ? "" : value.trim();
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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

    public JSONArray getNextNodes() {
        return nextNodes;
    }

    public void setNextNodes(JSONArray nextNodes) {
        this.nextNodes = nextNodes;
    }
}

