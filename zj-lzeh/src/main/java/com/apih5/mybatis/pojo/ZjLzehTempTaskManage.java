package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.apih5.framework.entity.TreeNodeEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZjLzehTempTaskManage extends BasePojo {
    // 主键
    private String zjLzehTempTaskManageId;

    // 主任务ID
    private String parentId;

    // 任务名称
    private String taskName;

    // 任务描述
    private String taskDescribe;

    // 分配人（创建人）
    private String allotPerson;

    // 开始日期
    private Date beginDate;

    // 要求完成日期
    private Date requireComplateDate;

    // 实际完成日期
    private Date realCompalteDate;

    // 完成情况说明
    private String complateExplain;

    // 状态
    private String status;

    // 分配人ID
    private String allotPersonId;

    // 分配对象ID
    private String implementPersonId;

    // 分配对象
    private String implementPerson;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;


    private BigDecimal  persent;

    private List<ZjLzehTempTaskManage> childList;

    private List<ZjLzehTempTaskManage> children;
    //用户机构树list
    private List<TreeNodeEntity> launcherList;

    private List<ZjLzehFile> fileList;

    public List<TreeNodeEntity> getLauncherList() {
        return launcherList;
    }

    public void setLauncherList(List<TreeNodeEntity> launcherList) {
        this.launcherList = launcherList;
    }

    public List<ZjLzehTempTaskManage> getChildren() {
        return children;
    }

    public void setChildren(List<ZjLzehTempTaskManage> children) {
        this.children = children;
    }



    public List<ZjLzehFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZjLzehFile> fileList) {
        this.fileList = fileList;
    }

    public BigDecimal getPersent() {
        return persent;
    }

    public void setPersent(BigDecimal persent) {
        this.persent = persent;
    }

    public List<ZjLzehTempTaskManage> getChildList() {
        return childList;
    }

    public void setChildList(List<ZjLzehTempTaskManage> childList) {
        this.childList = childList;
    }

    public String getZjLzehTempTaskManageId() {
        return zjLzehTempTaskManageId == null ? "" : zjLzehTempTaskManageId.trim();
    }

    public void setZjLzehTempTaskManageId(String zjLzehTempTaskManageId) {
        this.zjLzehTempTaskManageId = zjLzehTempTaskManageId == null ? null : zjLzehTempTaskManageId.trim();
    }

    public String getParentId() {
        return parentId == null ? "" : parentId.trim();
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getTaskName() {
        return taskName == null ? "" : taskName.trim();
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getTaskDescribe() {
        return taskDescribe == null ? "" : taskDescribe.trim();
    }

    public void setTaskDescribe(String taskDescribe) {
        this.taskDescribe = taskDescribe == null ? null : taskDescribe.trim();
    }

    public String getAllotPerson() {
        return allotPerson == null ? "" : allotPerson.trim();
    }

    public void setAllotPerson(String allotPerson) {
        this.allotPerson = allotPerson == null ? null : allotPerson.trim();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getRequireComplateDate() {
        return requireComplateDate;
    }

    public void setRequireComplateDate(Date requireComplateDate) {
        this.requireComplateDate = requireComplateDate;
    }

    public Date getRealCompalteDate() {
        return realCompalteDate;
    }

    public void setRealCompalteDate(Date realCompalteDate) {
        this.realCompalteDate = realCompalteDate;
    }

    public String getComplateExplain() {
        return complateExplain == null ? "" : complateExplain.trim();
    }

    public void setComplateExplain(String complateExplain) {
        this.complateExplain = complateExplain == null ? null : complateExplain.trim();
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAllotPersonId() {
        return allotPersonId == null ? "" : allotPersonId.trim();
    }

    public void setAllotPersonId(String allotPersonId) {
        this.allotPersonId = allotPersonId == null ? null : allotPersonId.trim();
    }

    public String getImplementPersonId() {
        return implementPersonId == null ? "" : implementPersonId.trim();
    }

    public void setImplementPersonId(String implementPersonId) {
        this.implementPersonId = implementPersonId == null ? null : implementPersonId.trim();
    }

    public String getImplementPerson() {
        return implementPerson == null ? "" : implementPerson.trim();
    }

    public void setImplementPerson(String implementPerson) {
        this.implementPerson = implementPerson == null ? null : implementPerson.trim();
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
