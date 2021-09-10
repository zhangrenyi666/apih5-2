package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZjLzehTempTaskCommunicate extends BasePojo {
    // 主键
    private String zjLzehTempTaskCommunicateId;

    // 临时任务ID
    private String zjLzehTempTaskManageId;

    // 发送人ID
    private String sendPersonId;

    // 接收人ID
    private String receivePersoId;

    // 发送人
    private String sendPerson;

    // 接收人
    private String receivePerson;

    // 消息内容
    private String messageContent;

    // 父级ID
    private String parentId;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private Date createTime;

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getZjLzehTempTaskCommunicateId() {
        return zjLzehTempTaskCommunicateId == null ? "" : zjLzehTempTaskCommunicateId.trim();
    }

    public void setZjLzehTempTaskCommunicateId(String zjLzehTempTaskCommunicateId) {
        this.zjLzehTempTaskCommunicateId = zjLzehTempTaskCommunicateId == null ? null : zjLzehTempTaskCommunicateId.trim();
    }

    public String getZjLzehTempTaskManageId() {
        return zjLzehTempTaskManageId == null ? "" : zjLzehTempTaskManageId.trim();
    }

    public void setZjLzehTempTaskManageId(String zjLzehTempTaskManageId) {
        this.zjLzehTempTaskManageId = zjLzehTempTaskManageId == null ? null : zjLzehTempTaskManageId.trim();
    }

    public String getSendPersonId() {
        return sendPersonId == null ? "" : sendPersonId.trim();
    }

    public void setSendPersonId(String sendPersonId) {
        this.sendPersonId = sendPersonId == null ? null : sendPersonId.trim();
    }

    public String getReceivePersoId() {
        return receivePersoId == null ? "" : receivePersoId.trim();
    }

    public void setReceivePersoId(String receivePersoId) {
        this.receivePersoId = receivePersoId == null ? null : receivePersoId.trim();
    }

    public String getSendPerson() {
        return sendPerson == null ? "" : sendPerson.trim();
    }

    public void setSendPerson(String sendPerson) {
        this.sendPerson = sendPerson == null ? null : sendPerson.trim();
    }

    public String getReceivePerson() {
        return receivePerson == null ? "" : receivePerson.trim();
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson == null ? null : receivePerson.trim();
    }

    public String getMessageContent() {
        return messageContent == null ? "" : messageContent.trim();
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    public String getParentId() {
        return parentId == null ? "" : parentId.trim();
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
