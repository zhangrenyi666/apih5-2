package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import cn.hutool.json.JSONObject;
import com.apih5.framework.entity.BasePojo;

public class ZjTzNoteInstructSugRecord extends BasePojo {
    private String noteInstructSugRecordId;

    private String noteInstructSugId;

    private String personDeptKey;

    private String personDeptName;

    private String personKey;

    private String personName;

    private String contentDesc;

    private Date sendTime;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<JSONObject> personList;

    public String getNoteInstructSugRecordId() {
        return noteInstructSugRecordId == null ? "" : noteInstructSugRecordId.trim();
    }

    public void setNoteInstructSugRecordId(String noteInstructSugRecordId) {
        this.noteInstructSugRecordId = noteInstructSugRecordId == null ? null : noteInstructSugRecordId.trim();
    }

    public String getNoteInstructSugId() {
        return noteInstructSugId == null ? "" : noteInstructSugId.trim();
    }

    public void setNoteInstructSugId(String noteInstructSugId) {
        this.noteInstructSugId = noteInstructSugId == null ? null : noteInstructSugId.trim();
    }

    public String getPersonDeptKey() {
        return personDeptKey == null ? "" : personDeptKey.trim();
    }

    public void setPersonDeptKey(String personDeptKey) {
        this.personDeptKey = personDeptKey == null ? null : personDeptKey.trim();
    }

    public String getPersonDeptName() {
        return personDeptName == null ? "" : personDeptName.trim();
    }

    public void setPersonDeptName(String personDeptName) {
        this.personDeptName = personDeptName == null ? null : personDeptName.trim();
    }

    public String getPersonKey() {
        return personKey == null ? "" : personKey.trim();
    }

    public void setPersonKey(String personKey) {
        this.personKey = personKey == null ? null : personKey.trim();
    }

    public String getPersonName() {
        return personName == null ? "" : personName.trim();
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public String getContentDesc() {
        return contentDesc == null ? "" : contentDesc.trim();
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc == null ? null : contentDesc.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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

}

