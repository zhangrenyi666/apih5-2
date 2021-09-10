package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZjLzehManageTaskPlanItem extends BasePojo {
    // 主键
    private String zjLzehManageTaskPlanItemId;

    // 事项描述
    private String matterDescription;

    // 控制要求
    private String controlDemand;

    // 计划完成时间
    private Date planMakespan;

    // 实际完成时间
    private Date realMakespan;

    // 责任部门
    private String dutyDepartment;

    // 责任人
    private String dutyPerson;

    // 分管领导
    private String managgeLead;

    // 配合人员
    private String coordPerson;

    // 配合部门
    private String coordDepartment;

    // 完成情况
    private String completion;

    //完成情况说明
    private String completeDes;

    // 经营目标计划ID
    private String zjLzehManageTaskPlanId;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //文件列表（文件工具）
    private List<ZjLzehFile> fileList;

    public List<ZjLzehFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZjLzehFile> fileList) {
        this.fileList = fileList;
    }

    public String getZjLzehManageTaskPlanItemId() {
        return zjLzehManageTaskPlanItemId == null ? "" : zjLzehManageTaskPlanItemId.trim();
    }

    public void setZjLzehManageTaskPlanItemId(String zjLzehManageTaskPlanItemId) {
        this.zjLzehManageTaskPlanItemId = zjLzehManageTaskPlanItemId == null ? null : zjLzehManageTaskPlanItemId.trim();
    }

    public String getMatterDescription() {
        return matterDescription == null ? "" : matterDescription.trim();
    }

    public void setMatterDescription(String matterDescription) {
        this.matterDescription = matterDescription == null ? null : matterDescription.trim();
    }

    public String getControlDemand() {
        return controlDemand == null ? "" : controlDemand.trim();
    }

    public void setControlDemand(String controlDemand) {
        this.controlDemand = controlDemand == null ? null : controlDemand.trim();
    }

    public Date getPlanMakespan() {
        return planMakespan;
    }

    public void setPlanMakespan(Date planMakespan) {
        this.planMakespan = planMakespan;
    }

    public Date getRealMakespan() {
        return realMakespan;
    }

    public void setRealMakespan(Date realMakespan) {
        this.realMakespan = realMakespan;
    }

    public String getDutyDepartment() {
        return dutyDepartment == null ? "" : dutyDepartment.trim();
    }

    public void setDutyDepartment(String dutyDepartment) {
        this.dutyDepartment = dutyDepartment == null ? null : dutyDepartment.trim();
    }

    public String getDutyPerson() {
        return dutyPerson == null ? "" : dutyPerson.trim();
    }

    public void setDutyPerson(String dutyPerson) {
        this.dutyPerson = dutyPerson == null ? null : dutyPerson.trim();
    }

    public String getManaggeLead() {
        return managgeLead == null ? "" : managgeLead.trim();
    }

    public void setManaggeLead(String managgeLead) {
        this.managgeLead = managgeLead == null ? null : managgeLead.trim();
    }

    public String getCoordPerson() {
        return coordPerson == null ? "" : coordPerson.trim();
    }

    public void setCoordPerson(String coordPerson) {
        this.coordPerson = coordPerson == null ? null : coordPerson.trim();
    }

    public String getCoordDepartment() {
        return coordDepartment == null ? "" : coordDepartment.trim();
    }

    public void setCoordDepartment(String coordDepartment) {
        this.coordDepartment = coordDepartment == null ? null : coordDepartment.trim();
    }

    public String getCompletion() {
        return completion == null ? "" : completion.trim();
    }

    public void setCompletion(String completion) {
        this.completion = completion == null ? null : completion.trim();
    }

    public String getZjLzehManageTaskPlanId() {
        return zjLzehManageTaskPlanId == null ? "" : zjLzehManageTaskPlanId.trim();
    }

    public void setZjLzehManageTaskPlanId(String zjLzehManageTaskPlanId) {
        this.zjLzehManageTaskPlanId = zjLzehManageTaskPlanId == null ? null : zjLzehManageTaskPlanId.trim();
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

    public String getCompleteDes() {
        return completeDes == null ? "" : completeDes;
    }

    public void setCompleteDes(String completeDes) {
        this.completeDes = completeDes;
    }

}
